package org.usfirst.frc.team4946.robot.commands.arm;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OverrideClampPos extends Command {

	int m_count;

	public OverrideClampPos() {
		requires(Robot.armSubsystem);
	}

	protected void initialize() {

		m_count = 0;

		// If clamp is not disengaged, do so
		if (Robot.armSubsystem.getClampIsEngaged()) {
			Robot.armSubsystem.setClamp(false);

		}

		// If the clamp is disengaged and the elevator is at the bottom (or close
		// enough), engage the clamp
		else if (Robot.elevatorSubsystem.getHeight() <= RobotConstants.ELEVATOR_MINIMUM_HEIGHT + 1.0) {
			Robot.armSubsystem.setClamp(true);
		}

		// If the clamp is disengaged and the elevator is not at the bottom, do nothing.
	}

	protected void execute() {
		m_count++;
	}

	protected boolean isFinished() {
		return m_count >= RobotConstants.PNEUMATIC_FIRING_COUNT;
	}

	protected void end() {
		Robot.armSubsystem.clampOff();
	}

	protected void interrupted() {

	}
}
