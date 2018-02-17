package org.usfirst.frc.team4946.robot.commands.internalIntake;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RunOutput extends Command {

	double speed;
	boolean m_hasCube;
	Timer m_timer = new Timer();
	
	/**
	 * Runs the intake at a specified velocity
	 * 
	 * @param speed 
	 * 			  the velocity at which to run the intake
	 */
    public RunOutput(double speed) {
    	
    	requires(Robot.internalIntakeSubsystem);
    	this.speed = speed;
    }

    protected void initialize() {
    	m_hasCube = Robot.internalIntakeSubsystem.getHasCube();
    }

    protected void execute() {
    	
    	Robot.internalIntakeSubsystem.set(speed);
    	
    	if (m_hasCube != Robot.internalIntakeSubsystem.getHasCube()) {
    		
    		m_timer.reset();
        	m_timer.start();
        	
        	while(m_timer.get() <= 0.5) {
        		
        		if(Robot.internalIntakeSubsystem.getHasCube()) {
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
    	Robot.internalIntakeSubsystem.stop();
    }


    protected void interrupted() {
    	end();
    }
}
