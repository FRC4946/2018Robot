package org.usfirst.frc.team4946.robot.commands.elevator;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class ClosedLoopControl extends Command {

	Joystick m_joy;
	double m_currentPos;double m_move;
	
	/**
	 * Uses PID in conjunction with input from the operator stick to control the height of the elevator. 
	 */
    public ClosedLoopControl() {
    	
    	requires(Robot.elevatorSubsystem);
    	m_joy = Robot.m_oi.getOperatorStick();
    }

    protected void initialize() {
    }

    protected void execute() {
    	
    	m_currentPos = Robot.elevatorSubsystem.getElevatorPos();
    	m_move = 10*m_joy.getRawAxis(1);
    			
    	if ((m_currentPos + m_move) > RobotConstants.ELEVATOR_MAXIMUM_HEIGHT) {
    		
    		Robot.elevatorSubsystem.setSetpoint(RobotConstants.ELEVATOR_MAXIMUM_HEIGHT);
    		
    	} else if ((m_currentPos + m_move) < RobotConstants.ELEVATOR_MINIMUM_HEIGHT) {
    		
    		Robot.elevatorSubsystem.setSetpoint(RobotConstants.ELEVATOR_MINIMUM_HEIGHT);
    		
    	} else {
    		
        	Robot.elevatorSubsystem.setSetpoint(m_currentPos + m_move);		
    	}

//    	if (m_currentPos < 1.0) {
//    		RobotConstants.setElevatorIsLowest(true);
//    	} else {
//    		RobotConstants.setElevatorIsLowest(false);
//    	}
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.elevatorSubsystem.setSetpoint(Robot.elevatorSubsystem.getElevatorPos());
    }

    protected void interrupted() {
    	end();
    }
}
