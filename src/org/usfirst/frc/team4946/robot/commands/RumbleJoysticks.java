package org.usfirst.frc.team4946.robot.commands;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RumbleJoysticks extends Command {

	private double m_rumbleValue;
	private double m_timeout;
	private Timer m_timer;

	public RumbleJoysticks(double rumbleValue) {
		this(rumbleValue, -1);
	}

	public RumbleJoysticks(double rumbleValue, double timeout) {
		m_rumbleValue = rumbleValue;
		m_timeout = timeout;
		m_timer = new Timer();
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		m_timer.reset();
		m_timer.start();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.m_oi.getDriveStick().setRumble(RumbleType.kLeftRumble, m_rumbleValue);
		Robot.m_oi.getDriveStick().setRumble(RumbleType.kRightRumble, m_rumbleValue);
		Robot.m_oi.getOperatorStick().setRumble(RumbleType.kLeftRumble, m_rumbleValue);
		Robot.m_oi.getOperatorStick().setRumble(RumbleType.kRightRumble, m_rumbleValue);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (m_timeout < 0)
			return false;
		return m_timer.get() > m_timeout;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
