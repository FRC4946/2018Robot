package org.usfirst.frc.team4946.robot.commands.arm;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.commands.misc.RumbleJoysticks;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OverrideArmPos extends Command {

	private int m_count;

	public OverrideArmPos() {
		requires(Robot.armSubsystem);
	}

	protected void initialize() {

		double height = Robot.elevatorSubsystem.getHeight();

		// Always allow flipping down
		if (Robot.armSubsystem.getElbowIsUp()) {
			Robot.armSubsystem.setElbow(false);
			Robot.elevatorSubsystem.limitMaxHeight(false);
			Robot.elevatorSubsystem.limitMinHeight(false);
			Robot.armSubsystem.m_isElbowOveridden = true;
			Robot.armSubsystem.setClamp(true);
		}

		// If the elevator is below the arms...
		else if (height < RobotConstants.ELEVATOR_INTERFERE_MIN) {
			Robot.armSubsystem.toggleElbow();
			Robot.elevatorSubsystem.limitMaxHeight(Robot.armSubsystem.getElbowIsUp());
			Robot.elevatorSubsystem.limitMinHeight(false);
			Robot.armSubsystem.m_isElbowOveridden = true;
			// disengage when elbow up, engage when down
			Robot.armSubsystem.setClamp(!Robot.armSubsystem.getElbowIsUp());
		}

		// If the elevator is above the arms
		else if (height > RobotConstants.ELEVATOR_INTERFERE_MAX) {
			Robot.armSubsystem.toggleElbow();
			Robot.elevatorSubsystem.limitMaxHeight(false);
			Robot.elevatorSubsystem.limitMinHeight(Robot.armSubsystem.getElbowIsUp());
			Robot.armSubsystem.m_isElbowOveridden = true;
			Robot.armSubsystem.setClamp(!Robot.armSubsystem.getElbowIsUp());
		}

		// If the elevator is at the height where the arms will collide, rumble
		else {
			Robot.elevatorSubsystem.limitMaxHeight(false);
			Robot.elevatorSubsystem.limitMinHeight(false);
			new RumbleJoysticks(1.0, 0.5).start();
		}

		m_count = 0;
	}

	protected void execute() {
		m_count++;
	}

	@Override
	protected boolean isFinished() {
		return m_count > RobotConstants.PNEUMATIC_FIRING_COUNT;
	}

	protected void end() {
		Robot.armSubsystem.elbowOff();
		Robot.armSubsystem.clampOff();
	}
}
