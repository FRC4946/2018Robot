package org.usfirst.frc.team4946.robot.commands.drivetrain;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraight extends Command {

	double m_speed; //in case the turning is too abrupt
	double m_distanceToGo;
	double m_distanceInit;
	
	
	/*
	 * @param distance the distance the robot drives
	 * @param speed the speed the robot employs while traversing the specified distance
	 * 
	 * moves the robot in a straight line
	 * 
	 */
    public DriveStraight(double distance, double speed) {
        
    	requires(Robot.driveTrainSubsystem);
    	m_distanceToGo = distance;
    	m_speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_distanceInit = Robot.driveTrainSubsystem.getEncoderDistance();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	Robot.driveTrainSubsystem.arcadeDrive(m_speed, 0.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return ((m_distanceInit + m_distanceToGo) - Robot.driveTrainSubsystem.getEncoderDistance()) <= 1.0;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrainSubsystem.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
