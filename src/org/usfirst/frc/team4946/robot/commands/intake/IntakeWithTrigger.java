package org.usfirst.frc.team4946.robot.commands.intake;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;

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

		// // If the safety is disabled, just run the motors blindly
		// if (!RobotConstants.elevatorSafetyEnabled) {
		// Robot.externalIntakeSubsystem.set(speed * 0.6);
		// Robot.internalIntakeSubsystem.set(speed);
		// }
		//
		// // If the safety is enabled...
		// else {

		// If we're trying to intake but we have a cube, rumble
		if (speed > 0 && Robot.internalIntakeSubsystem.getHasCube()) {
			Robot.externalIntakeSubsystem.set(0.0);
			Robot.internalIntakeSubsystem.set(0.0);
			Robot.m_oi.setDriveStickRumble(1.0);
			Robot.m_oi.setOperateStickRumble(1.0);
		}

		// Otherwise, spin the intakes
		else {

			// Only run the external intake if the elevator is low to the ground
			if (Robot.elevatorSubsystem.getHeight() < RobotConstants.ELEVATOR_INTERFERE_MIN)
				Robot.externalIntakeSubsystem.set(speed * 0.6);
			else
				Robot.externalIntakeSubsystem.set(0.0);

			Robot.internalIntakeSubsystem.set(speed + 0.07);
			Robot.m_oi.setDriveStickRumble(0.0);
			Robot.m_oi.setOperateStickRumble(0.0);
			// }
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
