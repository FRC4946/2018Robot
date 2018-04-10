package org.usfirst.frc.team4946.robot.commands.elevator;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;

import edu.wpi.first.wpilibj.command.Command;

public class MoveToHeight extends Command {

	double m_height;
	int onTargetCount = 0;

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
	}

	protected void execute() {
		Robot.elevatorSubsystem.updatePIDTunings();

		double setpoint = m_height;

		// Limit the setpoint to the appropriate bounds, and apply it to the elevator
		setpoint = Math.min(setpoint, RobotConstants.ELEVATOR_MAXIMUM_HEIGHT);
		setpoint = Math.max(setpoint, RobotConstants.ELEVATOR_MINIMUM_HEIGHT);

		// If the elbow is up or the clamp is engaged and the elevator is below, limit
		// upwards movement
		if (Robot.elevatorSubsystem.getHeight() < RobotConstants.ELEVATOR_INTERFERE_MAX) {

			// If we're trying to move up, open the clamp
			if (m_height > Robot.elevatorSubsystem.getHeight())
				Robot.armSubsystem.setClamp(false);

			if (Robot.armSubsystem.getElbowIsUp() || Robot.armSubsystem.getClampIsEngaged())
				setpoint = Math.min(setpoint, RobotConstants.ELEVATOR_INTERFERE_MIN);
		}

		// If we're above the arms and the arms are up or clamp is closed, limit
		// downwards movement
		if (Robot.elevatorSubsystem.getHeight() > RobotConstants.ELEVATOR_INTERFERE_MIN)
			if (Robot.armSubsystem.getElbowIsUp() || Robot.armSubsystem.getClampIsEngaged())
				setpoint = Math.max(setpoint, RobotConstants.ELEVATOR_INTERFERE_MAX);

		Robot.elevatorSubsystem.setSetpoint(setpoint);
	}

	protected boolean isFinished() {
		return Robot.elevatorSubsystem.getOnTarget() && Robot.elevatorSubsystem.getSpeed() < 0.1 && Robot.isAutonomous;
	}

	protected void end() {
		Robot.elevatorSubsystem.disablePID();
	}

	protected void interrupted() {
		end();
	}
}
