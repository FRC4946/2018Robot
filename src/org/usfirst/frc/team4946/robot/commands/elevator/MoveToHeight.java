package org.usfirst.frc.team4946.robot.commands.elevator;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;

import edu.wpi.first.wpilibj.command.Command;

public class MoveToHeight extends Command {

	double m_height;

	/**
	 * Sets the elevator to a certain height using PID.
	 * 
	 * @param height
	 *            the height setpoint in inches
	 */
	public MoveToHeight(double height) {
		requires(Robot.elevatorSubsystem);
		m_height = height;
	}

	protected void initialize() {
		Robot.elevatorSubsystem.enablePID();

		if (m_height > RobotConstants.ELEVATOR_MAXIMUM_HEIGHT) {
			m_height = RobotConstants.ELEVATOR_MAXIMUM_HEIGHT;
		} else if (m_height < RobotConstants.ELEVATOR_MINIMUM_HEIGHT) {
			m_height = RobotConstants.ELEVATOR_MINIMUM_HEIGHT;
		}
		// Alternatively, consider using Math.min() and max(). Functionaly identical,
		// just gives you some options for your toolbelt.

	}

	protected void execute() {

		// I'm pretty sure this should be here in execute so that we're constantly
		// feeding the controller so the watchdog doesn't starve. This might constantly
		// reset the integral term though in which case this should go in initialize
		Robot.elevatorSubsystem.setSetpoint(m_height);
	}

	protected boolean isFinished() {
		return Robot.elevatorSubsystem.getOnTarget();
	}

	protected void end() {
		Robot.elevatorSubsystem.setSetpoint(Robot.elevatorSubsystem.getElevatorPos());
	}

	protected void interrupted() {
		end();
	}
}
