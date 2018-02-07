package org.usfirst.frc.team4946.robot.commands.drivetrain;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnPID extends Command {
	
	double m_setAngle;
	double m_speed;
	
    public TurnPID(double angle, double speed) {
    	
    	requires(Robot.driveTrainSubsystem);
    	m_setAngle = angle;
    	m_speed = speed;
    }

    protected void initialize() {
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
    }

    protected void interrupted() {
    	end();
    }
}
