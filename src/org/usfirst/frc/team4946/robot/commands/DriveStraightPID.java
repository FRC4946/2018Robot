package org.usfirst.frc.team4946.robot.commands;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraightPID extends Command {

	double m_speed; //in case the turning is too abrupt
	double m_distanceToGo;
	double m_distanceInit;
	
    public DriveStraightPID(double speed, double distance) {
    	requires(Robot.DriveTrain); //This is a comment... :(
    	m_speed = speed;
    	m_distanceToGo = distance;
    }

    protected void initialize() {
    	m_distanceInit = Robot.DriveTrain.getEncoderDistance();
    	Robot.DriveTrain.setMaxSpeed(m_speed);
    	
    	Robot.DriveTrain.setGyroSetpoint(Robot.DriveTrain.getGyroAngle());
    	Robot.DriveTrain.setDistSetpoint(m_distanceInit + m_distanceToGo);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.DriveTrain.getEncOnTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.DriveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
