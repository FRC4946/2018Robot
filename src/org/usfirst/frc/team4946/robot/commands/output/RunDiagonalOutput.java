package org.usfirst.frc.team4946.robot.commands.output;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RunDiagonalOutput extends Command {

	Timer m_timer;
	double m_speed;
	boolean m_hasCube;
	
    public RunDiagonalOutput(double speed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.upperOutputSubsystem);
    	m_speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    
    	m_timer = new Timer();
    	m_hasCube = Robot.upperOutputSubsystem.getHasCube();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	Robot.upperOutputSubsystem.diagonalSpin(m_speed);
    	
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
