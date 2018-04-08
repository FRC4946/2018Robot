package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotMap;
import org.usfirst.frc.team4946.robot.commands.elbow.AutoArms;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ElbowSubsystem extends Subsystem {

	private DoubleSolenoid m_elbowValve;
	private boolean m_isElbowUp;
	public boolean m_isOveridden;
	
	private DoubleSolenoid m_clampValve;
	private boolean m_isClampEngaged;

	public ElbowSubsystem() {
		m_elbowValve = new DoubleSolenoid(RobotMap.PCM_ELBOW_UP, RobotMap.PCM_ELBOW_DOWN);
		m_isElbowUp = false;
		
		m_clampValve = new DoubleSolenoid(RobotMap.PCM_CLAMP_CLOSE, RobotMap.PCM_CLAMP_OPEN);
		m_isClampEngaged = false;
	}

	public void initDefaultCommand() {
		// setDefaultCommand(new AutoArms());
	}

	/**
	 * Set the elbow position
	 * 
	 * @param isUp
	 *            up position ({@code true}) or down position ({@code false})
	 */
	public void setElbow(boolean isUp) {
		
		if (isUp) {
			m_elbowValve.set(Value.kForward);
		} else {
			m_elbowValve.set(Value.kReverse);
		}

		m_isElbowUp = isUp;
	}

	/**
	 * Set the intake to the down position
	 */
	public void setElbowDown() {
		setElbow(false);
	}

	/**
	 * Set the intake to the up position
	 */
	public void setElbowUp() {
		setElbow(true);
	}

	/**
	 * Toggle between up and down position on the intake
	 */
	public void toggleElbow() {
		setElbow(!m_isElbowUp);
	}

	/**
	 * @return {@code true} if the intake is up
	 */
	public boolean getElbowIsUp() {
		return m_isElbowUp;
	}

	/**
	 * Turn off the elbow solenoid
	 */
	public void elbowOff() {
		m_elbowValve.set(Value.kOff);
	}
	
	/**
	 * Set the clamp position
	 * 
	 * @param isClosed
	 *            engaged position ({@code true}) or disengaged position ({@code false})
	 */
	public void setClamp(boolean isEngaged) {
		
		if (isEngaged) {
			m_clampValve.set(Value.kForward);
		} else {
			m_clampValve.set(Value.kReverse);
		}

		m_isClampEngaged = isEngaged;
	}

	/**
	 * Set the clamp to the engaged position
	 */
	public void setClampEngaged() {
		setElbow(true);
	}

	/**
	 * Set the clamp to the disengaged position
	 */
	public void setClampDisengaged() {
		setElbow(false);
	}
	
	/**
	 * Toggle clamp between engaged and disengaged position
	 */
	public void toggleClamp() {
		setElbow(!m_isClampEngaged);
	}

	/**
	 * @return {@code true} if the clamp is engaged
	 */
	public boolean getClampIsEngaged() {
		return m_isClampEngaged;
	}

	/**
	 * Turn off the clamp solenoid
	 */
	public void clampOff() {
		m_clampValve.set(Value.kOff);
	}
}
