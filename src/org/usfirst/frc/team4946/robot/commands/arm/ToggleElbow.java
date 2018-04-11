package org.usfirst.frc.team4946.robot.commands.arm;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ToggleElbow extends Command {

	private Command m_elbowCommand;

	protected void initialize() {
		m_elbowCommand = new SetElbow(!Robot.armSubsystem.getElbowIsUp());
		m_elbowCommand.start();
	}

	protected boolean isFinished() {
		return m_elbowCommand.isCompleted();
	}
}
