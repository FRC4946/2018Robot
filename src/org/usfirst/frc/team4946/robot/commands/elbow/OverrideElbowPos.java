package org.usfirst.frc.team4946.robot.commands.elbow;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.commands.RumbleJoysticks;

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

		if (height < RobotConstants.ELEVATOR_INTERFERE_MIN) {

			if (Robot.elbowSubsystem.getElbowIsUp()) {
				Robot.elevatorSubsystem.unlock();
				Robot.elbowSubsystem.set(false);
			} else {
				Robot.elevatorSubsystem.lock();
				Robot.elbowSubsystem.set(true);
			}
		} else if (height > RobotConstants.ELEVATOR_INTERFERE_MAX) {
			Robot.elbowSubsystem.toggle();
			Robot.elevatorSubsystem.setMinimumIsUpper(Robot.elbowSubsystem.getElbowIsUp());
		} else {
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
		Robot.elbowSubsystem.off();
	}
}
