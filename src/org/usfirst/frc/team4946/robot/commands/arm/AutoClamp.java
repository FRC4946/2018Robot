package org.usfirst.frc.team4946.robot.commands.arm;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoClamp extends Command {

	int m_count;
	boolean m_initBannerState;
	boolean m_currBannerState;

	public AutoClamp() {
		requires(Robot.armSubsystem);
		requires(Robot.intakeSubsystem);
	}

	protected void initialize() {

		m_count = 0;

		m_initBannerState = Robot.intakeSubsystem.getHasCube();

		// Disengage the clamp if the clamp is not overridden and there's a cube
		// detected.
		if (!Robot.armSubsystem.getClampOveridden() && Robot.intakeSubsystem.getHasCube())
			Robot.armSubsystem.setClamp(false);
	}

	protected void execute() {

		m_currBannerState = Robot.intakeSubsystem.getHasCube();

		// When the detection of the cube changes, the clamp is reset to a not
		// overwritten state.
		if (m_currBannerState != m_initBannerState) {

			m_initBannerState = m_currBannerState;
			Robot.armSubsystem.setClampOveridden(false);
			initialize();
		}

		m_count++;
	}

	protected boolean isFinished() {
		return Robot.armSubsystem.getClampOveridden() || m_count >= RobotConstants.PNEUMATIC_FIRING_COUNT;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
