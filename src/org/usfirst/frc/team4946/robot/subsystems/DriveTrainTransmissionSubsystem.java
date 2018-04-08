package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrainTransmissionSubsystem extends Subsystem {

	private Solenoid m_gearShift;
	private boolean m_isHigh;

	public DriveTrainTransmissionSubsystem() {
		m_gearShift = new Solenoid(RobotMap.PCM_DRIVE_SOLENOID);
	}

	public void initDefaultCommand() {
	}

	/**
	 * Set the gear of the drive train
	 * 
	 * @param isHigh
	 *            high gear ({@code true}) or low gear ({@code false})
	 */
	public void set(boolean isHigh) {

		if (isHigh)
			m_gearShift.set(true); //low
		else
			m_gearShift.set(false); //high

		m_isHigh = isHigh;
	}

	/**
	 * @return {@code true} if the gear is high (fast)
	 */
	public boolean getGearIsHigh() {
		return m_isHigh;
	}

	/**
	 * Toggle between low and high gear
	 */
	public void toggle() {
		set(!m_isHigh);
	}

	/**
	 * Turn off the solenoid
	 */
	public void off() {
		m_gearShift.set(false);
	}
}
