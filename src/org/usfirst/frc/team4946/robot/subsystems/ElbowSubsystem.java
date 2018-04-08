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
	private boolean m_isClampClosed;

	public ElbowSubsystem() {
		m_elbowValve = new DoubleSolenoid(RobotMap.PCM_ELBOW_UP, RobotMap.PCM_ELBOW_DOWN);
		m_isElbowUp = false;
		
		m_clampValve = new DoubleSolenoid(RobotMap.PCM_CLAMP_CLOSE, RobotMap.PCM_CLAMP_OPEN);
		m_isClampClosed = false;
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
	 *            closed position ({@code true}) or open position ({@code false})
	 */
	public void setClamp(boolean isClosed) {
		
		if (isClosed) {
			m_clampValve.set(Value.kForward);
		} else {
			m_clampValve.set(Value.kReverse);
		}

		m_isClampClosed = isClosed;
	}

	/**
	 * Set the clamp to the closed position
	 */
	public void setClampClosed() {
		setElbow(true);
	}

	/**
	 * Set the clamp to the open position
	 */
	public void setClampOpen() {
		setElbow(false);
	}
	
	/**
	 * Toggle clamp between closed and open position
	 */
	public void toggleClamp() {
		setElbow(!m_isClampClosed);
	}

	/**
	 * @return {@code true} if the clamp is closed
	 */
	public boolean getClampIsClosed() {
		return m_isClampClosed;
	}

	/**
	 * Turn off the clamp solenoid
	 */
	public void clampOff() {
		m_clampValve.set(Value.kOff);
	}
}
