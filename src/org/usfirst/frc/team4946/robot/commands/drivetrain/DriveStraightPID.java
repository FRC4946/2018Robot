package org.usfirst.frc.team4946.robot.commands.drivetrain;

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
    	requires(Robot.driveTrainSubsystem); //This is a comment... :(
    	m_speed = speed;
    	m_distanceToGo = distance;
    }

    protected void initialize() {
    	m_distanceInit = Robot.driveTrainSubsystem.getEncoderDistance();
    	Robot.driveTrainSubsystem.setMaxSpeed(m_speed);
    	
    	Robot.driveTrainSubsystem.setGyroSetpoint(Robot.driveTrainSubsystem.getGyroAngle());
    	Robot.driveTrainSubsystem.setDistSetpoint(m_distanceInit + m_distanceToGo);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.driveTrainSubsystem.getEncOnTarget();
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
