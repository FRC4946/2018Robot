package org.usfirst.frc.team4946.robot.commands.arm;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;

import edu.wpi.first.wpilibj.command.Command;

public class SetClamp extends Command {
	private boolean m_isEngaged;
	private int m_count;
	private boolean exit = false;

	public SetClamp(boolean isEngaged) {
		requires(Robot.armSubsystem);
		m_isEngaged = isEngaged;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		exit = false;
		m_count = 0;

		// If the elevator is not at the bottom, make sure it is open
		// if (Robot.elevatorSubsystem.getHeight() >
		// RobotConstants.ELEVATOR_INTERFERE_MIN)
		// m_isEngaged = false;
		//
		// If the clamp is already in the correct state, return
		// If the elbow is up, return
		// if (m_isEngaged == Robot.armSubsystem.getClampIsEngaged() ||
		// Robot.armSubsystem.getElbowIsUp()) {
		// exit = true;
		// return;
		// }

		Robot.armSubsystem.setClamp(m_isEngaged);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		m_count++;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return exit || (m_count >= RobotConstants.PNEUMATIC_FIRING_COUNT
				&& Robot.armSubsystem.getClampIsEngaged() == m_isEngaged);
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
