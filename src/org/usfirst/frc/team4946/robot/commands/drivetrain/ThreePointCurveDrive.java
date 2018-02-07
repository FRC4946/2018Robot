package org.usfirst.frc.team4946.robot.commands.drivetrain;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.util.NDimensionalPoint;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Uses three points to generate a circle encompassing all three points and drive along the generated curvature.
 */
public class ThreePointCurveDrive extends Command {

	NDimensionalPoint m_point1;
	NDimensionalPoint m_point2;
	NDimensionalPoint m_point3;
	double m_speed;
	double m_radius;
	double m_distToGo;
	double m_distInit;
	double m_rightSpeed;
	double m_leftSpeed;
	
    public ThreePointCurveDrive(NDimensionalPoint point1, NDimensionalPoint point2, 
    		NDimensionalPoint point3, double speed) {
    	
        requires(Robot.driveTrainSubsystem);
        
        m_point1 = point1;
        m_point2 = point2;
        m_point3 = point3;
        m_speed = speed;
    }

    protected void initialize() {
    	
    	double slope1, slope2, yintercept1, yintercept2, midpointx1, midpointy1, midpointx2, 
    		midpointy2, xintersection, yintersection, angle, point1to3dist, angleTraveled = 0.0;
    	
    	slope1 = -1/((m_point2.getDimension(2) - m_point1.getDimension(2))/
    			(m_point2.getDimension(1) - m_point1.getDimension(1)));
    	slope2 = -1/((m_point3.getDimension(2) - m_point2.getDimension(2))/
    			(m_point3.getDimension(1) - m_point2.getDimension(1)));
    	
    	midpointx1 = (m_point1.getDimension(1) + m_point2.getDimension(2))/2;
    	midpointy1 = (m_point1.getDimension(2) + m_point2.getDimension(2))/2;
    	
    	midpointx2 = (m_point2.getDimension(1) + m_point3.getDimension(1))/2;
    	midpointy2 = (m_point2.getDimension(2) + m_point3.getDimension(2))/2;
    	
    	yintercept1 = midpointy1 - midpointx1*slope1;
    	yintercept2 = midpointy2 - midpointx2*slope2;
    	
    	xintersection = (yintercept2 - yintercept1)/(slope1 - slope2);
    	yintersection = slope1*xintersection + yintercept1;
    	
    	m_radius = Math.sqrt(Math.pow(midpointx2 - xintersection,  2) + 
    			Math.pow(midpointy2 - yintersection, 2));
    	
    	point1to3dist = Math.sqrt(Math.pow(m_point3.getDimension(0) - m_point1.getDimension(0),  2) + 
    			(Math.pow(m_point3.getDimension(1) - m_point1.getDimension(1), 2)));
    	
    	angleTraveled = Math.acos((Math.pow(point1to3dist, 2) - 2*Math.pow(m_radius, 2))/(-2*Math.pow(m_radius, 2))); //radians
    	
    	m_distToGo = m_radius*angleTraveled;
    	
    	m_rightSpeed = m_speed*(1 - ((m_distToGo)/(2*m_radius)));
    	m_leftSpeed = m_speed*(1 + ((m_distToGo)/(2*m_radius)));
    	
    	m_distInit = Robot.driveTrainSubsystem.getEncoderDistance();
    }

    protected void execute() {
    	Robot.driveTrainSubsystem.arcadeDrive(m_speed, m_speed - m_leftSpeed);
    }

    protected boolean isFinished() {
        return (Robot.driveTrainSubsystem.getEncoderDistance() >= m_distInit + m_distToGo);
    }

    protected void end() {
    	
    }

    protected void interrupted() {
    	Robot.driveTrainSubsystem.stop();
    }
}
