package org.usfirst.frc.team4946.robot.commands.elevator;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClosedLoopControl extends Command {

	
	Joystick m_joy;
	double m_currentPos;double m_move;
	
    public ClosedLoopControl() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis); 	
    	requires(Robot.elevatorSubsystem);
    
    	m_joy = Robot.m_oi.getOperatorStick();
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	m_currentPos = Robot.elevatorSubsystem.getElevatorPos();
    	m_move = 6*m_joy.getRawAxis(1);
    			
    	if ((m_currentPos + m_move) > RobotConstants.ELEVATOR_MAXIMUM_HEIGHT) {
    		Robot.elevatorSubsystem.setSetpoint(RobotConstants.ELEVATOR_MAXIMUM_HEIGHT);
    	}else if((m_currentPos + m_move) < RobotConstants.ELEVATOR_MINIMUM_HEIGHT) {
    		Robot.elevatorSubsystem.setSetpoint(RobotConstants.ELEVATOR_MINIMUM_HEIGHT);
    	}else{
        	Robot.elevatorSubsystem.setSetpoint(m_currentPos + m_move);		
    	}

    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
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
