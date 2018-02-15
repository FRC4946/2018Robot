package org.usfirst.frc.team4946.robot.commands.output;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class InnerIntakeUntilCube extends Command {

	double m_speed;

	/**
	 * Runs the inner intake at a specified velocity until a cube is detected
	 * 
	 * @param m_speed
	 *            the velocity at which to run the inner intake
	 */
	public InnerIntakeUntilCube(double speed) {

		requires(Robot.upperOutputSubsystem);
		m_speed = speed;
	}

	protected void initialize() {
	}

	protected void execute() {
		Robot.upperOutputSubsystem.set(m_speed);
	}

	protected boolean isFinished() {
		return Robot.upperOutputSubsystem.getHasCube();
	}

	protected void end() {
		Robot.upperOutputSubsystem.stop();
	}

	protected void interrupted() {
		end();
	}
}
