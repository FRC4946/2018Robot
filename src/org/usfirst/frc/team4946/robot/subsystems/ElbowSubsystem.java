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
	private boolean m_isUp;

	public ElbowSubsystem() {
		m_elbowValve = new DoubleSolenoid(RobotMap.PCM_ELBOW_LEFT, RobotMap.PCM_ELBOW_RIGHT);
	}

	public void initDefaultCommand() {
	}

	/**
	 * Set the elbow position
	 * 
	 * @param isUp
	 *            up position ({@code true}) or down position ({@code false})
	 */
	public void set(boolean isUp) {
		if (isUp) {
			m_elbowValve.set(Value.kForward);
		} else {
			m_elbowValve.set(Value.kReverse);
		}

		m_isUp = isUp;
	}

	/**
	 * Set the intake to the down position
	 */
	public void setElbowDown() {
		set(false);
	}

	/**
	 * Set the intake to the up position
	 */
	public void setElbowUp() {
		set(true);
	}

	/**
	 * Toggle between up and down position
	 */
	public void toggle() {
		set(!m_isUp);
	}

	/**
	 * @return {@code true} if the elevator is upright
	 */
	public boolean getElbowIsUp() {
		return m_isUp;
	}

	/**
	 * Turn off the solenoid
	 */
	public void off() {
		m_elbowValve.set(Value.kOff);
	}
}
