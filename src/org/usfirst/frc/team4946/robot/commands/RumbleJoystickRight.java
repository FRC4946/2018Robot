package org.usfirst.frc.team4946.robot.commands;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Rumbles the driver and operator controllers on their right side. 
 */
public class RumbleJoystickRight extends Command {

    public RumbleJoystickRight() {
        
    }

    protected void initialize() {
    }

    protected void execute() {
    	
    	Robot.m_oi.getDriveStick().setRumble(RumbleType.kRightRumble, 0.5);
    	Robot.m_oi.getOperatorStick().setRumble(RumbleType.kRightRumble, 0.5);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
