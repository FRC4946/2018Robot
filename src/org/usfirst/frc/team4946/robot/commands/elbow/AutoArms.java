package org.usfirst.frc.team4946.robot.commands.elbow;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoArms extends Command {

	private double m_initHeight;

	public AutoArms() {
		requires(Robot.elbowSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		m_initHeight = Robot.elevatorSubsystem.getHeight();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (Robot.elevatorSubsystem.getHeight() > RobotConstants.ELEVATOR_INTERFERE_MAX + 4) {

			if (m_initHeight < RobotConstants.ELEVATOR_INTERFERE_MAX + 2 && Robot.elbowSubsystem.m_isOveridden)
				Robot.elbowSubsystem.m_isOveridden = false;

			if (!Robot.elbowSubsystem.m_isOveridden) {
				Robot.elbowSubsystem.setElbow(true);
				Robot.elevatorSubsystem.limitMinHeight(true);
				Robot.elevatorSubsystem.limitMaxHeight(false);
			}
		}

		// else if (Robot.elevatorSubsystem.getHeight() <
		// RobotConstants.ELEVATOR_INTERFERE_MAX + 14) {
		//
		// if (m_initHeight > RobotConstants.ELEVATOR_INTERFERE_MAX + 18 &&
		// Robot.elbowSubsystem.m_isOveridden)
		// Robot.elbowSubsystem.m_isOveridden = false;
		//
		// if (!Robot.elbowSubsystem.m_isOveridden
		// || Robot.elevatorSubsystem.getSetpoint() <
		// Robot.elevatorSubsystem.getHeight())
		// Robot.elbowSubsystem.set(false);
		//
		// }
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}