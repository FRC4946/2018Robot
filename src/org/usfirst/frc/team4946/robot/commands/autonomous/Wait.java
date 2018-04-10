package org.usfirst.frc.team4946.robot.commands.autonomous;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Wait extends Command {

	private double m_timeout;
	private Timer m_timer;

	public Wait(double timeout) {
		this(timeout, true);
	}

	public Wait(double timeout, boolean shouldBlock) {

		if (shouldBlock) {
			requires(Robot.driveTrainSubsystem);
			requires(Robot.driveTransmissionSubsystem);
			requires(Robot.armSubsystem);
			requires(Robot.elevatorSubsystem);
			requires(Robot.elevatorTransmissionSubsystem);
			requires(Robot.intakeSubsystem);
		}

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
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
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
