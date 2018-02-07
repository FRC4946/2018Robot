package org.usfirst.frc.team4946.robot.commands.drivetrain;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.pathplanning.TrapezoidMotionProfile;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraightPID extends Command {

	double m_speed; 
	double m_distanceToGo;
	double m_distanceInit;
	TrapezoidMotionProfile m_motionProfiler;
	
	
	/**
	 * Drives the robot forwards in a straight line using PID to control acceleration
	 * @param speed the speed the robot employs while traversing the set distance
	 * @param distance
	 */
    public DriveStraightPID(double speed, double distance) {
    	requires(Robot.driveTrainSubsystem); 
    	m_speed = speed;
    	m_distanceToGo = distance;
    }

    protected void initialize() {
    	
    	m_distanceInit = Robot.driveTrainSubsystem.getEncoderDistance();
    	Robot.driveTrainSubsystem.setMaxSpeed(m_speed);
    	m_motionProfiler = new TrapezoidMotionProfile(m_distanceToGo, m_speed, 
    			RobotConstants.DRIVETRAIN_MAX_ACCEL, RobotConstants.ROBOT_SAMPLE_TIME, Robot.driveTrainSubsystem.getEncoderDistance());
    	
    	Robot.driveTrainSubsystem.setGyroSetpoint(Robot.driveTrainSubsystem.getGyroAngle());
    	Robot.driveTrainSubsystem.setDistSetpoint(m_distanceInit + m_distanceToGo);
    }

    protected void execute() {
    	Robot.driveTrainSubsystem.arcadeDrive(m_motionProfiler.getVel(Robot.driveTrainSubsystem.getEncoderDistance()), 
    			1.0);   
    }

    protected boolean isFinished() {
        return Robot.driveTrainSubsystem.getDistOnTarget();
    }

    protected void end() {
    	Robot.driveTrainSubsystem.stop();
    }

    protected void interrupted() {
    	end();
    }
}
