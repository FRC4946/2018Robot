package org.usfirst.frc.team4946.robot.commands.elevator;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;

import edu.wpi.first.wpilibj.command.Command;


public class ElevatorSetHeight extends Command {

	double m_elevatorSpeed;
	double m_height;
	
	/**
	 * Sets the elevator to a certain height using PID.
	 */
    public ElevatorSetHeight(double height, double speed) {
    	requires(Robot.elevatorSubsystem);
    	m_height = height;
    	m_elevatorSpeed = speed;
    }

    protected void initialize() {
    	
    	if(m_height > RobotConstants.ELEVATOR_MAXIMUM_HEIGHT) {
    		m_height = RobotConstants.ELEVATOR_MAXIMUM_HEIGHT;
    	} else if (m_height < RobotConstants.ELEVATOR_MINIMUM_HEIGHT){
    		m_height = RobotConstants.ELEVATOR_MINIMUM_HEIGHT;
    	}
    	
    	Robot.elevatorSubsystem.set(m_elevatorSpeed);
    	Robot.elevatorSubsystem.setSetpoint(m_height);	
    }

    protected void execute() {
    	
    	if (Robot.elevatorSubsystem.getElevatorPos() < 1.0) {
    		RobotConstants.setElevatorIsLowest(true);
    	} else {
    		RobotConstants.setElevatorIsLowest(false);
    	}
    }

    protected boolean isFinished() {
        return Robot.elevatorSubsystem.getOnTarget();
    }

    protected void end() {
    	Robot.elevatorSubsystem.setSetpoint(Robot.elevatorSubsystem.getElevatorPos());
    }

    protected void interrupted() {
    	end();
    }
}
