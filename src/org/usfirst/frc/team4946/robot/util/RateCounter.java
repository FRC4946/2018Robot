package org.usfirst.frc.team4946.robot.util;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class RateCounter extends Counter implements PIDSource {
	final double MIN_TIME = 1;

	private double[] m_history = new double[5];
	private double m_maxVal = 1.0;
	private double m_lastAvg;
	private boolean m_isGarbage = false;

	public RateCounter(int channel) {
		super(channel);
		setMaxPeriod(MIN_TIME);
		// setSamplesToAverage(5);
	}

	public void setMaxVal(double val) {
		m_maxVal = val;
	}

	/**
	 * Returns the time between the last two ticks, as a raw RPM counter.
	 */
	public double getRPM() {
		getInstantRPM();

		// If the values are actually garbage, return that last average value
		if (m_isGarbage)
			return m_lastAvg;

		// Reset the average
		double avg = 0;
		// Calculate the average
		for (int i = 0; i < m_history.length; i++)
			avg += m_history[i];
		
		avg /= (double) m_history.length;

		// Update the last average
		m_lastAvg = avg;

		return avg;
	}

	public double getInstantRPM() {
		double period = getPeriod();
		double rate = 0;

		// Calculate RPM
		if (period < MIN_TIME && period > 0) // At least 60rpm
			rate = 1.0 / period * 60.0;

		for (int i = 0; i < m_history.length - 1; i++)
			m_history[i] = m_history[i + 1];

		if (rate > m_maxVal) {
			m_isGarbage = true;
			return m_lastAvg;
		}

		m_history[m_history.length - 1] = rate;
		m_isGarbage = true;

		// Check that the differences between accelerating RPM values are
		// all less than 200
		for (int i = m_history.length - 1; i > 0; i--) {
			if (Math.abs(m_history[i] - m_history[i - 1]) > 1000) {
				// If values are garbage, return the last average
				return m_lastAvg;
			}
		}

		// If the for loop finishes, isGarbage must be false
		m_isGarbage = false;
		return rate;
	}

	public double pidGet() {
		return getRPM();// / m_maxVal;
	}

	@Override
	// Do nothing with this method as it is not used.
	public void setPIDSourceType(PIDSourceType pidSource) {
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kRate;
	}

}
