package org.usfirst.frc.team4946.robot.commands.arm;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.commands.misc.RumbleJoysticks;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SetElbow extends CommandGroup {

	public SetElbow(boolean isUp) {
		if (Robot.armSubsystem.getElbowIsUp() == isUp)
			return;

		// If we're flipping up and the elevator is in the way, rumble
		if (isUp && RobotConstants.ELEVATOR_INTERFERE_MIN < Robot.elevatorSubsystem.getHeight()
				&& RobotConstants.ELEVATOR_INTERFERE_MAX > Robot.elevatorSubsystem.getHeight()) {
			addSequential(new RumbleJoysticks(1.0, 0.5));
			return;
		}

		// In theory this should never be the case, but worth checking anyways
		if (!Robot.armSubsystem.getClampIsEngaged())
			addSequential(new SetClamp(true));

		// Flip the elbow up
		addSequential(new SetElbowPos(isUp));

	}

	private class SetElbowPos extends Command {
		private boolean m_posIsUp;
		private int m_count;

		public SetElbowPos(boolean isUp) {
			requires(Robot.armSubsystem);
			m_posIsUp = isUp;
		}

		protected void initialize() {
			Robot.armSubsystem.setElbow(m_posIsUp);
			m_count = 0;
		}

		protected void execute() {
			m_count++;
		}

		protected boolean isFinished() {
			return m_count > RobotConstants.PNEUMATIC_FIRING_COUNT;
		}

		protected void end() {
			Robot.armSubsystem.elbowOff();
		}

		protected void interrupted() {
			end();
		}
	}
}
