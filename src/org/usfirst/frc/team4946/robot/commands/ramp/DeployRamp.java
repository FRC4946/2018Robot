package org.usfirst.frc.team4946.robot.commands.ramp;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DeployRamp extends Command {

	//private int m_count;

	public DeployRamp() {
		//requires(Robot.rampSubsystem);
	}

	protected void initialize() {
		//Robot.rampSubsystem.deployRamp(true);
	}

	protected void execute() {
		//m_count++;
	}

	protected boolean isFinished() {
		return false;
        //return m_count > RobotConstants.PNEUMATIC_FIRING_COUNT;
	}

	protected void end() {
		 //Robot.rampSubsystem.deployRamp(false);
	}

	protected void interrupted() {
	}
}
