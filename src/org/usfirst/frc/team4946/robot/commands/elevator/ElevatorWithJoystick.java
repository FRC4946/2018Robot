package org.usfirst.frc.team4946.robot.commands.elevator;

import org.usfirst.frc.team4946.robot.Robot;

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

		// Get the current setpoint
		double speed = -Robot.m_oi.getOperatorStick().getRawAxis(5);

		if (Math.abs(speed) < 0.1)
			speed = 0;

		if (Robot.elevatorTransmissionSubsystem.getIsClimb()) {
			if (Robot.elevatorSubsystem.getPIDEnabled())
				Robot.elevatorSubsystem.disablePID();

			if (Math.abs(speed) < 0.05) {
				Robot.elevatorSubsystem.setBrake(true);
				Robot.elevatorSubsystem.set(0);
			} else {
				Robot.elevatorSubsystem.setBrake(false);
				Robot.elevatorSubsystem.set(speed);
			}
		} else {
			Robot.elevatorSubsystem.updatePIDTunings();
			double setpoint = Robot.elevatorSubsystem.getHeight() + 12 * speed;

			// Limit the setpoint to the bounds, and apply it to the elevator
			setpoint = Math.min(setpoint, Robot.elevatorSubsystem.getMaxHeight());
			setpoint = Math.max(setpoint, Robot.elevatorSubsystem.getMinHeight());
			Robot.elevatorSubsystem.setSetpoint(setpoint);

			// If PID is done, apply break and turn off PID (PID off, brake on)
			if (Robot.elevatorSubsystem.getOnTarget()) {
				Robot.elevatorSubsystem.disablePID();
				return;
			}

			// Otherwise, the PID is not on target and has some moving to do.
			// Ensure it is enabled (PID on, Brake off)
			if (!Robot.elevatorSubsystem.getPIDEnabled())
				Robot.elevatorSubsystem.enablePID();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.elevatorSubsystem.disablePID();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
