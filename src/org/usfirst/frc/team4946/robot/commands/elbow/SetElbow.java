package org.usfirst.frc.team4946.robot.commands.elbow;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetElbow extends Command {
	boolean position;
	int count;

	public SetElbow(boolean position) {
		requires(Robot.elbowSubsystem);
	}

	protected void initialize() {
		count = 0;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.elbowSubsystem.setElbowDown();
		count++;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return count > 5;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.elbowSubsystem.off();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
