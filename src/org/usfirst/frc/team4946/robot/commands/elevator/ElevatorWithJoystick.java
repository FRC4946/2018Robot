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
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (RobotConstants.elevatorSafetyEnabled) {
			if (!Robot.elevatorSubsystem.getPIDEnabled())
				Robot.elevatorSubsystem.enablePID();

			double setpoint = Robot.elevatorSubsystem.getHeight();
			setpoint += 10 * Robot.m_oi.getOperatorStick().getRawAxis(1);

			double max = RobotConstants.ELEVATOR_MAXIMUM_HEIGHT;
			double min = Robot.internalIntakeSubsystem.getHasCube() ? RobotConstants.ELEVATOR_CARRY_HEIGHT
					: RobotConstants.ELEVATOR_MINIMUM_HEIGHT;

			setpoint = Math.min(setpoint, max);
			setpoint = Math.max(setpoint, min);
			Robot.elevatorSubsystem.setSetpoint(setpoint);
		} else {

			if (Robot.elevatorSubsystem.getPIDEnabled())
				Robot.elevatorSubsystem.disablePID();
			Robot.elevatorSubsystem.set(Robot.m_oi.getOperatorStick().getRawAxis(0));
		}

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.elevatorSubsystem.set(0.0);
		Robot.elevatorSubsystem.setSetpoint(Robot.elevatorSubsystem.getHeight());
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
