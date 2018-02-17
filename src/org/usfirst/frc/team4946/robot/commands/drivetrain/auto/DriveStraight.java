package org.usfirst.frc.team4946.robot.commands.drivetrain.auto;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Drive
 */
public class DriveStraight extends Command {

	double m_speed; //in case the turning is too abrupt
	double m_distanceToGo;
	double m_distanceInit;
	
	
	/** 
	 * moves the robot in a straight line
	 * 
	 * @param distance the distance the robot drives
	 * @param speed the speed the robot employs while traversing the specified distance
	 */
    public DriveStraight(double distance, double speed) {
        
    	requires(Robot.driveTrainSubsystem);
    	m_distanceToGo = distance;
    	m_speed = speed;
    }

    protected void initialize() {
    	m_distanceInit = Robot.driveTrainSubsystem.getEncoderDistance();
    	Robot.driveTrainSubsystem.enablePID();
    }

    protected void execute() {
    	
    	Robot.driveTrainSubsystem.arcadeDrive(m_speed, 0.0);
    }

    protected boolean isFinished() {
        return ((m_distanceInit + m_distanceToGo) - Robot.driveTrainSubsystem.getEncoderDistance()) <= 1.0;
    }

    protected void end() {
    	Robot.driveTrainSubsystem.stop();
    	Robot.driveTrainSubsystem.disablePID();
    }

    protected void interrupted() {
    	end();
    }
}
