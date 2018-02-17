package org.usfirst.frc.team4946.robot.commands.elevator;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorWithJoystick_Open extends Command {

	/**
	 * Uses the operator joystick to control the elevator height
	 */
	public ElevatorWithJoystick_Open() {
		requires(Robot.elevatorSubsystem);
	}

	protected void initialize() {
		Robot.elevatorSubsystem.disablePID();
	}

	protected void execute() {
		Robot.elevatorSubsystem.set(Robot.m_oi.getOperatorStick().getRawAxis(0));
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.elevatorSubsystem.set(0.0);
	}

	protected void interrupted() {
		end();
	}
}
