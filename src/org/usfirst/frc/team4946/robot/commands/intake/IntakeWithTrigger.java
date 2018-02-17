package org.usfirst.frc.team4946.robot.commands.intake;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeWithTrigger extends Command {

	public IntakeWithTrigger() {
		requires(Robot.externalIntakeSubsystem);
		requires(Robot.internalIntakeSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		double speed = Robot.m_oi.getDriveStick().getRawAxis(2) - Robot.m_oi.getDriveStick().getRawAxis(3);

		if (speed > 1 && Robot.internalIntakeSubsystem.getHasCube()) {
			Robot.externalIntakeSubsystem.set(0.0);
			Robot.internalIntakeSubsystem.set(0.0);
			Robot.m_oi.setDriveStickRumble(1.0);
			Robot.m_oi.setOperateStickRumble(1.0);
		} else {
			Robot.externalIntakeSubsystem.set(speed * 0.6);
			Robot.internalIntakeSubsystem.set(speed);
			Robot.m_oi.setDriveStickRumble(0.0);
			Robot.m_oi.setOperateStickRumble(0.0);
		}

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.externalIntakeSubsystem.set(0.0);
		Robot.internalIntakeSubsystem.set(0.0);
		Robot.m_oi.setDriveStickRumble(0.0);
		Robot.m_oi.setOperateStickRumble(0.0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}