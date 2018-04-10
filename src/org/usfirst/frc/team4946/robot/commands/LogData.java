package org.usfirst.frc.team4946.robot.commands;

import java.io.IOException;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LogData extends Command {

	public LogData() {
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		try {
			Robot.dataLogger.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.dataLogger.snapshot();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		try {
			Robot.dataLogger.stop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
