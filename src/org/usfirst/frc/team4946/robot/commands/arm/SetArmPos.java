package org.usfirst.frc.team4946.robot.commands.arm;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;

import edu.wpi.first.wpilibj.command.Command;

public class SetArmPos extends Command {
	private boolean m_posIsUp;
	private int m_count;

	public SetArmPos(boolean isUp) {
		requires(Robot.armSubsystem);
		m_posIsUp = isUp;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.armSubsystem.setElbow(m_posIsUp);

		m_count = 0;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		m_count++;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return m_count > RobotConstants.PNEUMATIC_FIRING_COUNT;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.armSubsystem.elbowOff();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
