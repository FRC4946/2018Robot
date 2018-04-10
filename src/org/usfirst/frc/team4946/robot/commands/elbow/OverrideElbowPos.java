package org.usfirst.frc.team4946.robot.commands.elbow;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.commands.misc.RumbleJoysticks;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OverrideElbowPos extends Command {

	private int m_count;

	public OverrideElbowPos() {
		requires(Robot.elbowSubsystem);
	}

	protected void initialize() {

		double height = Robot.elevatorSubsystem.getHeight();

		// Always allow flipping down
		if (Robot.elbowSubsystem.getElbowIsUp()) {
			Robot.elbowSubsystem.setElbow(false);
			Robot.elevatorSubsystem.limitMaxHeight(false);
			Robot.elevatorSubsystem.limitMinHeight(false);
			Robot.elbowSubsystem.m_isOveridden = true;
			Robot.elbowSubsystem.setClamp(true);
		}

		// If the elevator is below the arms...
		else if (height < RobotConstants.ELEVATOR_INTERFERE_MIN) {
			Robot.elbowSubsystem.toggleElbow();
			Robot.elevatorSubsystem.limitMaxHeight(Robot.elbowSubsystem.getElbowIsUp());
			Robot.elevatorSubsystem.limitMinHeight(false);
			Robot.elbowSubsystem.m_isOveridden = true;
			//disengage when elbow up, engage when down
			Robot.elbowSubsystem.setClamp(!Robot.elbowSubsystem.getElbowIsUp()); 		
		}

		// If the elevator is above the arms
		else if (height > RobotConstants.ELEVATOR_INTERFERE_MAX) {
			Robot.elbowSubsystem.toggleElbow();
			Robot.elevatorSubsystem.limitMaxHeight(false);
			Robot.elevatorSubsystem.limitMinHeight(Robot.elbowSubsystem.getElbowIsUp());
			Robot.elbowSubsystem.m_isOveridden = true;
			Robot.elbowSubsystem.setClamp(!Robot.elbowSubsystem.getElbowIsUp());
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
		Robot.elbowSubsystem.elbowOff();
		Robot.elbowSubsystem.clampOff();
	}
}
