package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ElbowSubsystem extends Subsystem {

	private DoubleSolenoid m_elbowValve;
	private boolean m_isHigh;

	public ElbowSubsystem() {
		m_elbowValve = new DoubleSolenoid(RobotMap.PCM_ELBOW_LEFT, RobotMap.PCM_ELBOW_RIGHT);
	}

	public void initDefaultCommand() {

	}

	public void set(boolean isHigh) {
		if (isHigh)
			m_elbowValve.set(Value.kReverse);
		else
			m_elbowValve.set(Value.kForward);

		m_isHigh = isHigh;
	}

	public void off() {
		m_elbowValve.set(Value.kOff);
	}

	/**
	 * Sets the external intake to the down position
	 */
	public void setElbowDown() {
		set(false);
	}

	/**
	 * Lifts the external intake up
	 */
	public void setElbowUp() {
		set(true);
	}

	/**
	 * @return the current elbow position. kOff indicates the down position.
	 *         kForward indicates the up position
	 */
	public boolean getElbowValue() {

		return m_isHigh;
	}

	public void toggleSolenoid() {
		set(!m_isHigh);
	}
}
