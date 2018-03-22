package org.usfirst.frc.team4946.robot.commands.elevator;

import org.usfirst.frc.team4946.robot.Robot;

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

		// if (m_height > Robot.elevatorSubsystem.getMaxHeight()) {
		// m_height = Robot.elevatorSubsystem.getMaxHeight();
		// } else if (m_height < Robot.elevatorSubsystem.getMinHeight()) {
		// m_height = Robot.elevatorSubsystem.getMinHeight();
		// }

		// Alternatively, consider using Math.min() and max(). Functionaly identical,
		// just gives you some options for your toolbelt.
		m_height = Math.min(m_height, Robot.elevatorSubsystem.getMaxHeight());
		m_height = Math.max(m_height, Robot.elevatorSubsystem.getMinHeight());

	}

	protected void execute() {
		Robot.elevatorSubsystem.updatePIDTunings();
		Robot.elevatorSubsystem.setSetpoint(m_height);
		
		
		// For teleop
		if(Robot.elevatorSubsystem.getOnTarget())
			onTargetCount++;
		else
			onTargetCount = 0;
		
		if(onTargetCount > 5)
			Robot.elevatorSubsystem.disablePID();
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
