package org.usfirst.frc.team4946.robot.commands;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * ROUGH: Given a destination point, the robot calculates a cubic path assuming its current position is the origin.
 * The destination point is defined by two values: the first of which defines the distance to travel along the y-axis,
 * and the second of which defines the distance to travel along the x-axis. They y-axis is arbitrarily defined as whatever axis the 
 * robot first travels on, and the x-axis is that which the robot travels on after turning 90 degrees. Because the starting point 
 * is defined as the origin, the cubic equation follows the general form ax^3 + bx^2. Every cycle, the robot travels an arbitrarily
 * defined unit of distance and turns based on the 
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
    	requires(Robot.DriveTrain);

    	m_speed = speed;
    	m_firstDistanceToGo = dist1; //y
    	m_secondDistanceToGo = dist2; //x
    }

    protected void initialize() {
    	m_distanceInit = Robot.DriveTrain.getEncoderDistance();
    	m_angleInit = Robot.DriveTrain.getGyroAngle();	
       	m_angleToGo = m_angleInit + 5;
    	m_finalSlope = 0; // probably a dummy
    	Robot.DriveTrain.setMaxSpeed(m_speed);
    	
    	//Generate the cubic fxn which follows the form: f(x) = ax^3 + bx^2 by finding the coefficients
    	//The cubic coefficient has the equation: 
    	m_cubicCoefficient = ((m_finalSlope*m_secondDistanceToGo - 2*m_firstDistanceToGo))/Math.pow(m_secondDistanceToGo, 3);
    	m_quadraticCoefficient = ((3*m_firstDistanceToGo - m_finalSlope*m_secondDistanceToGo))/Math.pow(m_secondDistanceToGo, 2);
    	
    	//Get the robot started (the initial slope of motion is vertical; undefined)
    	Robot.DriveTrain.setGyroSetpoint(m_angleInit + 5);
    	Robot.DriveTrain.setDistSetpoint(m_distanceInit + 6);
    	m_distXTraveled += Math.cos(m_angleToGo)*(Robot.DriveTrain.getEncoderDistance() - m_distanceInit);
    }

    protected void execute() {
    	
    	//Get the distance traveled thus far and the angle the robot is currently at
    	m_distanceInit = Robot.DriveTrain.getEncoderDistance();
    	m_angleInit = Robot.DriveTrain.getGyroAngle();	
    	//Calculate the angle to turn to. It's the current angle plus the angle indicated by the derivative 
    	//which is being converted from radians to degrees
    	//The derivative will be 3ax^2 + 2bx, and the angle to add on is (180/pi)*arctan(3ax^2 + 2bx)
    	m_angleToGo = m_angleInit + 180*(Math.atan(3*m_cubicCoefficient*Math.pow(m_distXTraveled + 0.1, 2) + 2*m_quadraticCoefficient*m_distXTraveled))/Math.PI;
    	
    	//Set the gyro setpoint and keep the robot moving forward
    	Robot.DriveTrain.setGyroSetpoint(m_angleToGo);
    	Robot.DriveTrain.setDistSetpoint(m_distanceInit + 0.5);
    	//Update the x-coordinate of the robot
    	m_distXTraveled += Math.cos(m_angleToGo)*(Robot.DriveTrain.getEncoderDistance() - m_distanceInit);
    	
    }

    protected boolean isFinished() {
        return Math.abs(m_secondDistanceToGo - m_distXTraveled) <= 1.0;
    }

    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}