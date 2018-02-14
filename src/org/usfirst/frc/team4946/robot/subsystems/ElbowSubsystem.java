package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotMap;
import org.usfirst.frc.team4946.robot.commands.elbow.ManageElbowPos;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ElbowSubsystem extends Subsystem {

	DoubleSolenoid m_elbowValve;

	public ElbowSubsystem() {
		m_elbowValve = new DoubleSolenoid(RobotMap.PCM_ELBOW_LEFT, RobotMap.PCM_ELBOW_RIGHT);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new ManageElbowPos());
	}

	public void set(boolean isDown) {
		if (isDown)
			m_elbowValve.set(Value.kReverse);
		else
			m_elbowValve.set(Value.kForward);
	}

	public void off() {
		m_elbowValve.set(Value.kOff);
	}

	/**
	 * Sets the external intake to the down position
	 */
	public void setElbowDown() {
		m_elbowValve.set(Value.kReverse);
	}

	/**
	 * Lifts the external intake up
	 */
	public void setElbowUp() {
		m_elbowValve.set(Value.kForward);
	}

	/**
	 * @return the current elbow position. kOff indicates the down position.
	 *         kForward indicates the up position
	 */
	public Value getElbowValue() {
		return m_elbowValve.get();
	}
}
