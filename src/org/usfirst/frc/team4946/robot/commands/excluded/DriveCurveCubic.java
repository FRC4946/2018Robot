package org.usfirst.frc.team4946.robot.commands.drivetrain.auto;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 */

public class DriveCurveCubic extends Command {
	//https://www.chiefdelphi.com/forums/showthread.php?t=105211
	double m_speed;
	double m_distanceInit;
	double m_angleInit;
	double m_angleToGo;
	double m_firstDistanceToGo;
	double m_secondDistanceToGo;
	double m_distXTraveled;
	double m_finalSlope;
	double m_cubicCoefficient;
	double m_quadraticCoefficient;

    public DriveCurveCubic(double dist1, double dist2, double speed) {
    	requires(Robot.driveTrainSubsystem);

    	m_speed = speed;
    	m_firstDistanceToGo = dist1; //y
    	m_secondDistanceToGo = dist2; //x
    }

    protected void initialize() {
    	m_distanceInit = Robot.driveTrainSubsystem.getEncoderDistance();
    	m_angleInit = Robot.driveTrainSubsystem.getGyroAngle();	
       	m_angleToGo = m_angleInit + 5;
    	m_finalSlope = 0; // probably a dummy
    	Robot.driveTrainSubsystem.setMaxSpeed(m_speed);
    	
    	//Generate the cubic fxn which follows the form: f(x) = ax^3 + bx^2 by finding the coefficients
    	//The cubic coefficient has the equation: 
    	m_cubicCoefficient = ((m_finalSlope*m_secondDistanceToGo - 2*m_firstDistanceToGo))/Math.pow(m_secondDistanceToGo, 3);
    	m_quadraticCoefficient = ((3*m_firstDistanceToGo - m_finalSlope*m_secondDistanceToGo))/Math.pow(m_secondDistanceToGo, 2);
    	
    	//Get the robot started (the initial slope of motion is vertical; undefined)
    	Robot.driveTrainSubsystem.setGyroSetpoint(m_angleInit + 5);
    	Robot.driveTrainSubsystem.setDistSetpoint(m_distanceInit + 6);
    	m_distXTraveled += Math.cos(m_angleToGo)*(Robot.driveTrainSubsystem.getEncoderDistance() - m_distanceInit);
    	Robot.driveTrainSubsystem.enableDrivePID();
    }

    protected void execute() {
    	
    	//Get the distance traveled thus far and the angle the robot is currently at
    	m_distanceInit = Robot.driveTrainSubsystem.getEncoderDistance();
    	m_angleInit = Robot.driveTrainSubsystem.getGyroAngle();	
    	//Calculate the angle to turn to. It's the current angle plus the angle indicated by the derivative 
    	//which is being converted from radians to degrees
    	//The derivative will be 3ax^2 + 2bx, and the angle to add on is (180/pi)*arctan(3ax^2 + 2bx)
    	m_angleToGo = m_angleInit + 180*(Math.atan(3*m_cubicCoefficient*Math.pow(m_distXTraveled + 0.1, 2) + 2*m_quadraticCoefficient*m_distXTraveled))/Math.PI;
    	
    	//Set the gyro setpoint and keep the robot moving forward
    	Robot.driveTrainSubsystem.setGyroSetpoint(m_angleToGo);
    	Robot.driveTrainSubsystem.setDistSetpoint(m_distanceInit + 1.0);
    	//Update the x-coordinate of the robot
    	m_distXTraveled += Math.cos(m_angleToGo)*(Robot.driveTrainSubsystem.getEncoderDistance() - m_distanceInit);
    	
    }

    protected boolean isFinished() {
        return Math.abs(m_secondDistanceToGo - m_distXTraveled) <= 1.0;
    }

    protected void end() {
    	Robot.driveTrainSubsystem.disableDrivePID();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
