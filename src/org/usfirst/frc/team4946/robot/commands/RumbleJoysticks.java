package org.usfirst.frc.team4946.robot.commands;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RumbleJoysticks extends Command {

	double m_rumbleValue;
	
    public RumbleJoysticks(double rumbleValue) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	m_rumbleValue = rumbleValue;
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.m_oi.getDriveStick().setRumble(RumbleType.kLeftRumble, m_rumbleValue);
    	Robot.m_oi.getDriveStick().setRumble(RumbleType.kRightRumble, m_rumbleValue);
    	Robot.m_oi.getOperatorStick().setRumble(RumbleType.kLeftRumble, m_rumbleValue);
    	Robot.m_oi.getOperatorStick().setRumble(RumbleType.kRightRumble, m_rumbleValue);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}