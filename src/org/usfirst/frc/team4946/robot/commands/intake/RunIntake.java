package org.usfirst.frc.team4946.robot.commands.intake;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class RunIntake extends Command {
	
	double speed = 1.0;
	boolean m_hasCube;
	Timer m_timer;

	/**
	 * 
	 * Runs the intake at a specified velocity
	 * 
	 * @param speed the velocity (not speed) to run the intake at
	 */
    public RunIntake(double speed) {
    	requires(Robot.externalIntakeSubsystem);
    	this.speed = speed;
    }

    protected void initialize() {
    	m_hasCube = Robot.externalIntakeSubsystem.getHasCube();
    }

    protected void execute() {
    	
    	Robot.externalIntakeSubsystem.set(speed);
    	
    	if (m_hasCube != Robot.upperOutputSubsystem.getHasCube()) {
    		
    		m_timer.reset();
        	m_timer.start();
        	
        	while(m_timer.get() <= 0.5) {
        		
        		if(Robot.upperOutputSubsystem.getHasCube()) {
        			Robot.m_oi.getDriveStick().setRumble(RumbleType.kLeftRumble, 0.7);
        			Robot.m_oi.getOperatorStick().setRumble(RumbleType.kLeftRumble, 0.7);
        		} else {
        			Robot.m_oi.getDriveStick().setRumble(RumbleType.kRightRumble, 0.7);
        			Robot.m_oi.getOperatorStick().setRumble(RumbleType.kRightRumble, 0.7);
        		}
        	}
        	
        	m_timer.stop();
    	}
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.externalIntakeSubsystem.stop();
    }

    protected void interrupted() {
    	end();
    }
}
