package org.usfirst.frc.team4946.robot.commands.arm;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ToggleClamp extends Command {

	private Command m_clampCommand;

	protected void initialize() {
		m_clampCommand = new SetClamp(!Robot.armSubsystem.getClampIsEngaged());
		m_clampCommand.start();
	}

	protected boolean isFinished() {
		return m_clampCommand.isCompleted();
	}
}
