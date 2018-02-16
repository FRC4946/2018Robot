package org.usfirst.frc.team4946.robot.commands.drivetrain;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TurnPID extends Command {
	
	double m_setAngle;
	double m_speed;
	
    public TurnPID(double angle) {
    	this(angle, 0.75); // TODO: Default val
    }
	
	/**
	 * Turns using PID
	 * @param angle the angle in degrees for the robot to turn to, it is essentially absolute and is not relative to the robots current angle (turning to 90 twice moves the robot to 90 degrees, then does nothing)
	 * @param speed the speed for the robot to turn at
	 */
    public TurnPID(double angle, double speed) {
    	
    	requires(Robot.driveTrainSubsystem);
    	m_setAngle = angle;
    	m_speed = speed;
    }

    protected void initialize() {
    	Robot.driveTrainSubsystem.enablePID();
    	Robot.driveTrainSubsystem.setGyroSetpoint(m_setAngle);
    }

    protected void execute() {
    	Robot.driveTrainSubsystem.arcadeDrive(m_speed, Robot.driveTrainSubsystem.getGyroPIDOutput());
    }

    protected boolean isFinished() {
    	
    	for(int i = 0; i <= 20; i++) {
        	if(!Robot.driveTrainSubsystem.getGyroOnTarget()) {
        		return false;
        	}
    	}
    	return true;
    }

    protected void end() {
    	Robot.driveTrainSubsystem.stop();
    	Robot.driveTrainSubsystem.disablePID();
    }

    protected void interrupted() {
    	end();
    }
}
