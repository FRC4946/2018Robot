package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrainTransmissionSubsystem extends Subsystem {

	private DoubleSolenoid m_gearShift;
	private boolean m_isHigh;

	public DriveTrainTransmissionSubsystem() {
		m_gearShift = new DoubleSolenoid(RobotMap.PCM_DRIVE_GEARHIGH, RobotMap.PCM_DRIVE_GEARLOW);
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
			m_gearShift.set(Value.kForward);
		else
			m_gearShift.set(Value.kReverse);

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
		m_gearShift.set(Value.kOff);
	}
}
