package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotMap;
import org.usfirst.frc.team4946.robot.commands.arm.AutoClamp;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ArmSubsystem extends Subsystem {

	private DoubleSolenoid m_elbowValve;
	private boolean m_isElbowUp;
	public boolean m_isElbowOveridden;

	private DoubleSolenoid m_clampValve;
	private boolean m_isClampEngaged;
	private boolean m_isClampOveridden;
	private Timer m_clampTimer = new Timer();

	public ArmSubsystem() {
		m_elbowValve = new DoubleSolenoid(RobotMap.PCM_ELBOW_UP, RobotMap.PCM_ELBOW_DOWN);
		m_isElbowUp = false;

		m_clampValve = new DoubleSolenoid(RobotMap.PCM_CLAMP_CLOSE, RobotMap.PCM_CLAMP_OPEN);
		m_isClampEngaged = false;
		m_isClampOveridden = false;
		m_clampTimer.reset();
	}

	public void initDefaultCommand() {
		setDefaultCommand(new AutoClamp());
	}

	/**
	 * @return {@code true} if the clamp is being overidden
	 */
	public boolean getClampOveridden() {
		return m_isClampOveridden;
	}

	/**
	 * Sets if the clamp is being overridden
	 * 
	 * @param isOveridden
	 *            if the clamp is being overridden
	 */
	public void setClampOveridden(boolean isOverriden) {
		m_isClampOveridden = isOverriden;
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
	 *            engaged position ({@code true}) or disengaged position
	 *            ({@code false})
	 */
	public void setClamp(boolean isEngaged) {

		// Every time we engage the clamp, reset the timer to 0
		if (isEngaged) {
			m_clampValve.set(Value.kForward);

			m_clampTimer.stop();
			m_clampTimer.reset();
		}

		// Every time we disengage the clamp, start a timer so we know when it's safe to
		// move the elevator
		else {
			m_clampValve.set(Value.kReverse);
			m_clampTimer.start();
		}

		m_isClampEngaged = isEngaged;
	}

	/**
	 * Set the clamp to the engaged position
	 */
	public void setClampEngaged() {
		setClamp(true);
	}

	/**
	 * Set the clamp to the disengaged position
	 */
	public void setClampDisengaged() {
		setClamp(false);
	}

	/**
	 * Toggle clamp between engaged and disengaged position
	 */
	public void toggleClamp() {
		setClamp(!m_isClampEngaged);
	}

	/**
	 * @return {@code true} if the clamp is engaged
	 */
	public boolean getClampIsEngaged() {

		// This should always return true, unless the flag is false and time has passed
		return m_isClampEngaged || m_clampTimer.get() <= 0.5;
	}

	/**
	 * Turn off the clamp solenoid
	 */
	public void clampOff() {
		m_clampValve.set(Value.kOff);
	}
}
