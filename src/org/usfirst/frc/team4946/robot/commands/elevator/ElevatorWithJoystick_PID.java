package org.usfirst.frc.team4946.robot.commands.elevator;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorWithJoystick_PID extends Command {

	/**
	 * Uses PID in conjunction with input from the operator stick to control the
	 * height of the elevator.
	 */
	public ElevatorWithJoystick_PID() {
		requires(Robot.elevatorSubsystem);
	}

	protected void initialize() {
		Robot.elevatorSubsystem.enablePID();
	}

	protected void execute() {
		double setpoint = Robot.elevatorSubsystem.getElevatorPos();
		setpoint += 10 * Robot.m_oi.getOperatorStick().getRawAxis(1);

		double max = RobotConstants.ELEVATOR_MAXIMUM_HEIGHT;
		double min = Robot.internalIntakeSubsystem.getHasCube() ? RobotConstants.ELEVATOR_CARRY_HEIGHT
				: RobotConstants.ELEVATOR_MINIMUM_HEIGHT;

		setpoint = Math.min(setpoint, max);
		setpoint = Math.max(setpoint, min);
		Robot.elevatorSubsystem.setSetpoint(setpoint);
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.elevatorSubsystem.setSetpoint(Robot.elevatorSubsystem.getElevatorPos());
	}

	protected void interrupted() {
		end();
	}
}
