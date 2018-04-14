package org.usfirst.frc.team4946.robot.commands.arm;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;

import edu.wpi.first.wpilibj.command.Command;

public class SetClamp extends Command {
	private boolean m_isEngaged;
	private boolean m_desiredState;
	private int m_count;

	public SetClamp(boolean isEngaged) {
		requires(Robot.armSubsystem);
		m_isEngaged = isEngaged;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		m_count = 0;

		m_desiredState = m_isEngaged;
		// If the elevator is not at the bottom, make sure it is open
		// if (Robot.elevatorSubsystem.getHeight() >
		// RobotConstants.ELEVATOR_INTERFERE_MIN)
		// m_isEngaged = false;

		// If the elbow is up, make sure dat boi clamped
		if (Robot.armSubsystem.getElbowIsUp()) {
			m_desiredState = true;
		}

		Robot.armSubsystem.setClamp(m_desiredState);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		m_count++;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return m_count >= RobotConstants.PNEUMATIC_FIRING_COUNT
				&& Robot.armSubsystem.getClampIsEngaged() == m_desiredState;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.armSubsystem.clampOff();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
