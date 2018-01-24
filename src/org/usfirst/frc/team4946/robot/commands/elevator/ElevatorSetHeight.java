package org.usfirst.frc.team4946.robot.commands.elevator;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorSetHeight extends Command {

	double m_elevatorSpeed;
	double m_height;
	
    public ElevatorSetHeight(double height, double speed) {
    	requires(Robot.elevatorSubsystem);
   
    	m_height = height;
    	m_elevatorSpeed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	if(m_height > RobotConstants.ELEVATOR_MAXIMUM_HEIGHT) {
    		m_height = RobotConstants.ELEVATOR_MAXIMUM_HEIGHT;
    	} else if (m_height < RobotConstants.ELEVATOR_MINIMUM_HEIGHT){
    		m_height = RobotConstants.ELEVATOR_MINIMUM_HEIGHT;
    	}
    	
    	Robot.elevatorSubsystem.set(m_elevatorSpeed);
    	Robot.elevatorSubsystem.setSetpoint(m_height);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.elevatorSubsystem.getOnTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.elevatorSubsystem.setSetpoint(Robot.elevatorSubsystem.getElevatorPos());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
