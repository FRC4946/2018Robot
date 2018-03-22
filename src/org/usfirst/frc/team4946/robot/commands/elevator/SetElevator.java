package org.usfirst.frc.team4946.robot.commands.elevator;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetElevator extends Command {

	private double m_speed;

	public SetElevator(double speed) {
		requires(Robot.elevatorSubsystem);
		m_speed = speed;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.elevatorSubsystem.disablePID();
		Robot.elevatorSubsystem.setBrake(false);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		if (Robot.elevatorSubsystem.getHeight() < 20 && m_speed < 0)
			Robot.elevatorSubsystem.set(0);
		else if (Robot.elevatorSubsystem.getHeight() > 80 && m_speed > 0)
			Robot.elevatorSubsystem.set(0);
		else
			Robot.elevatorSubsystem.set(m_speed);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.elevatorSubsystem.set(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
