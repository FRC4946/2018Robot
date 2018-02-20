package org.usfirst.frc.team4946.robot.util.imu;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;

/**
 * 
 * @author Matthew Reynolds
 *
 */
public class SkewIMU extends GyroBase {

	private final AHRS m_imu;
	private Vec3D m_down;
	private AtomicDouble m_angle;
	private AtomicDouble m_lastRate;
	private AtomicDouble m_rate;
	private static final long kSamplePeriod = 500; // Microseconds, = 0.0005s

	public SkewIMU() {
		this(SPI.Port.kMXP);
	}

	public SkewIMU(Port port) {
		m_imu = new AHRS(port);
		m_angle = new AtomicDouble();
		m_lastRate = new AtomicDouble();
		m_rate = new AtomicDouble();
		m_down = new Vec3D();

		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(() -> {
			synchronized (this) {
				m_lastRate.set(m_rate.get());

				// Calculate the component of the sum of vectors along the 'down' direction
				Vec3D vel = new Vec3D();
				vel.i = m_imu.getRawGyroX();
				vel.j = m_imu.getRawGyroY();
				vel.k = m_imu.getRawGyroZ();
				m_rate.set(m_down.dotProduct(vel));

				double dRate = m_lastRate.get() - m_rate.get();
				m_angle.addAndGet(dRate * kSamplePeriod / 1000000);
				
//				System.out.println(dRate * kSamplePeriod / 1000000);
			}
		}, 0, kSamplePeriod, TimeUnit.MICROSECONDS);
	}

	@Override
	public void calibrate() {

		// TODO: The navX itself doesn't need calibration I think
		
		// Redefine the 'down' direction
		m_down.i = m_imu.getRawAccelX();
		m_down.j = m_imu.getRawAccelY();
		m_down.k = m_imu.getRawAccelZ();
	}

	@Override
	public void reset() {
		m_angle.set(0);
	}

	@Override
	public double getAngle() {
		return m_angle.get();
	}

	@Override
	public double getRate() {
		return m_rate.get();
	}

}
