package org.usfirst.frc.team4946.robot.commands.arm;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoClamp extends Command {

	int m_count;
	boolean m_prevBannerState;
	boolean m_currBannerState;

	public AutoClamp() {
		requires(Robot.armSubsystem);
	}

	protected void initialize() {

		m_count = 0;
		m_prevBannerState = Robot.intakeSubsystem.getHasCube();
	}

	protected void execute() {

		m_currBannerState = Robot.intakeSubsystem.getHasCube();

		// When the cube is detected to have entered the intake, disengage the arms
		if (m_currBannerState && !m_prevBannerState) {
			Robot.armSubsystem.setClamp(false);
			m_count = 0;
		}

		m_prevBannerState = m_currBannerState;
		
		m_count++;
		
		if (m_count >= RobotConstants.PNEUMATIC_FIRING_COUNT) {
			Robot.armSubsystem.clampOff();
		}
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
