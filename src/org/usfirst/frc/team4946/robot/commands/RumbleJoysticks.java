package org.usfirst.frc.team4946.robot.commands;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;


public class RumbleJoysticks extends Command {

	Timer m_timer;
	/**
	 * Rumbles the driver and operator controllers on their right side. 
	 */
    public RumbleJoysticks() {
        
    }

    protected void initialize() {
    	
    	m_timer = new Timer();
    	
    	m_timer.reset();
		m_timer.start();
    }

    protected void execute() {
    	
		while(m_timer.get() <= 1.0) {
			
			Robot.m_oi.getDriveStick().setRumble(RumbleType.kLeftRumble, 0.6);
			Robot.m_oi.getDriveStick().setRumble(RumbleType.kRightRumble, 0.6);
			
			Robot.m_oi.getOperatorStick().setRumble(RumbleType.kLeftRumble, 0.6);
			Robot.m_oi.getOperatorStick().setRumble(RumbleType.kRightRumble, 0.6);
		}
		
		Robot.m_oi.getDriveStick().setRumble(RumbleType.kLeftRumble, 0.0);
		Robot.m_oi.getDriveStick().setRumble(RumbleType.kRightRumble, 0.0);
		
		Robot.m_oi.getOperatorStick().setRumble(RumbleType.kLeftRumble, 0.0);
		Robot.m_oi.getOperatorStick().setRumble(RumbleType.kRightRumble, 0.0);
		
		m_timer.stop();
		m_timer.reset();
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
