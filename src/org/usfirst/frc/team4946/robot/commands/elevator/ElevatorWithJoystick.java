package org.usfirst.frc.team4946.robot.commands.elevator;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorWithJoystick extends Command {

	public ElevatorWithJoystick() {
		requires(Robot.elevatorSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.elevatorSubsystem.enablePID();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		double speed = -Robot.m_oi.getOperatorStick().getRawAxis(5);

		// If the speed is basically zero (w/ deadzone), apply break and turn off PID
		// (Turn off motors)
		if (Math.abs(speed) < 0.1) {
			if (!Robot.elevatorSubsystem.isLocked())
				Robot.elevatorSubsystem.lock();

			Robot.elevatorSubsystem.disablePID();
			return;
		}

		// If the elevator is in break mode, unbreak
		if (Robot.elevatorSubsystem.isLocked())
			Robot.elevatorSubsystem.unlock();

		// If the elevator is not in PID mode, enable PID
		if (!Robot.elevatorSubsystem.getPIDEnabled())
			Robot.elevatorSubsystem.enablePID();

		double setpoint = Robot.elevatorSubsystem.getHeight();
		setpoint += 12 * speed;

		double max = RobotConstants.ELEVATOR_MAXIMUM_HEIGHT;
		double min = RobotConstants.ELEVATOR_MINIMUM_HEIGHT;

		setpoint = Math.min(setpoint, max);
		setpoint = Math.max(setpoint, min);
		Robot.elevatorSubsystem.setSetpoint(setpoint);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.elevatorSubsystem.lock();
		Robot.elevatorSubsystem.set(0.0);
		Robot.elevatorSubsystem.setSetpoint(Robot.elevatorSubsystem.getHeight());
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
