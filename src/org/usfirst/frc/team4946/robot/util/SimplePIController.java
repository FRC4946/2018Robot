package org.usfirst.frc.team4946.robot.util;

import edu.wpi.first.wpilibj.PIDSource;

public class SimplePIController {

	private double lastTime;
	private double m_inputVal = 0.0, m_output = 0.0, m_setpoint;
	private double integralTerm = 0.0;
	private double kp = 0.0, ki = 0.0;
	private double m_minimumOutput = -1.0, m_maximumOutput = 1.0;
	private double m_minimumInput = 0.0, m_maximumInput = 0.0;

	private PIDSource m_inputSource;
	private boolean m_isContinuous = false;
	private double m_tolerance = 0.0;

	public SimplePIController(double newKP, double newKI, PIDSource inputSource) {
		kp = Math.abs(newKP);
		ki = Math.abs(newKI);
		m_inputSource = inputSource;
	}

	public void setTunings(double Kp, double Ki) {
		kp = Math.abs(Kp);
		ki = Math.abs(Ki);
	}

	public void setOutputRange(double minimumOutput, double maximumOutput) {
		if (minimumOutput > maximumOutput)
			return; // If the lower boundary is greater than the upper boundary,
					// return
		m_minimumOutput = minimumOutput;
		m_maximumOutput = maximumOutput;
	}

	public void setInputRange(double minimumInput, double maximumInput) {
		if (minimumInput > maximumInput)
			return; // If the lower boundary is greater than the upper boundary,
					// return
		m_minimumInput = minimumInput;
		m_maximumInput = maximumInput;
		this.setSetpoint(m_setpoint);
	}

	public void setTolerence(double tolerence) {
		m_tolerance = tolerence;
	}

	public void setContinuous(boolean isContinuous) {
		m_isContinuous = isContinuous;
	}

	public void setDirection(boolean isForwards) {
		if (isForwards) {
			kp = Math.abs(kp);
			ki = Math.abs(ki);
		} else if (!isForwards) {
			kp = -1.0 * Math.abs(kp);
			ki = -1.0 * Math.abs(ki);
		}
	}

	public double getProportional() {
		return kp;
	}

	public double getIntegral() {
		return ki;
	}

	public PIDSource getInputSource() {
		return m_inputSource;
	}

	public double getInputValue() {
		this.updateInputVal();
		return m_inputVal;
	}

	public double getSetpoint() {
		return m_setpoint;
	}

	public void compute() {
		this.updateInputVal();

		// Calculate the elapsed time since the function was last called
		double now = System.currentTimeMillis();
		double timeChange = (double) (now - lastTime); // Should hopefully
														// always be 20ms, but
														// we can't be sure
		lastTime = now;

		// Compute all the working error variables
		double error = m_setpoint - m_inputVal;
		integralTerm += ki * (error * timeChange);

		if (m_isContinuous) {
			if (Math.abs(error) > (m_maximumInput - m_minimumInput) / 2) {
				if (error > 0) {
					error = error - m_maximumInput + m_minimumInput;
				} else {
					error = error + m_maximumInput - m_minimumInput;
				}
			}
		}

		// Limit the integral, to prevent huge buildup after we exceed the
		// maximum output
		if (integralTerm > m_maximumOutput)
			integralTerm = m_maximumOutput;
		else if (integralTerm < m_minimumOutput)
			integralTerm = m_minimumOutput;

		// Compute the output, limiting it to the boundaries
		m_output = (kp * error) + integralTerm;
		if (m_output > m_maximumOutput)
			m_output = m_maximumOutput;
		else if (m_output < m_minimumOutput)
			m_output = m_minimumOutput;
	}

	public double getOutput() {
		this.compute(); // Make sure that the output is up-to-date
		return m_output;
	}

	public void setSetpoint(double newSetpoint) {

		// Make sure that the setPoint is within the input limits
		if (m_maximumInput > m_minimumInput && m_isContinuous) {
			double range = (m_maximumInput - m_minimumInput);

			if (newSetpoint > m_maximumInput) {
				m_setpoint = newSetpoint % range;
				// m_setpoint = m_maximumInput;
			} else if (newSetpoint < m_minimumInput) {
				while (newSetpoint < m_minimumInput)
					newSetpoint += range;
				m_setpoint = newSetpoint;
				// m_setpoint = m_minimumInput;
			} else {
				m_setpoint = newSetpoint;
			}
		} else {
			m_setpoint = newSetpoint;
		}
	}

	public void updateInputVal() {
		m_inputVal = m_inputSource.pidGet();

		double range = m_maximumInput - m_minimumInput;

		// If need be, adjust the input to fit in the input range.
		// Rather than saying "If it's too big, set it to the biggest allowed,"
		// We want the controller to be able to utilize its continuous
		// functionality.
		// Therefore, we just re-map the input to fit into the input range using
		// the modulus operator.
		if (m_isContinuous) {

			while (m_inputVal < m_minimumInput)
				m_inputVal += range;

			m_inputVal %= m_maximumInput;

		}
	}

	public boolean onTarget() {
		this.updateInputVal();
		// if (m_isContinuous)
		// return (Math.abs(m_setpoint - m_inputVal) < m_tolerance / 100
		// * Math.abs(m_maximumInput - m_minimumInput));
		// return Math.abs(m_setpoint - m_inputVal) < m_tolerance;

		return (Math.abs(m_setpoint - m_inputVal) < m_tolerance); // If the
																	// setpoint
																	// is in the
																	// right
																	// spot
		// && integralTerm < ((m_maximumInput - m_minimumInput) / 2.0) / 100.0;
		// // And the I term is less than 1/100 of the output
	}

	public double getError() {
		this.updateInputVal();
		return m_setpoint - m_inputVal;
	}
}
