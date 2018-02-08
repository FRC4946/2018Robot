package org.usfirst.frc.team4946.robot.commands.elevator;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorJoystickCtrl extends Command {

	Joystick j_joy;
	
	/**
	 * Uses the operator joystick to control the elevator height
	 */
    public ElevatorJoystickCtrl() {
    	requires(Robot.elevatorSubsystem);

    }

    protected void initialize() {
    	j_joy = Robot.m_oi.getOperatorStick();
    }

    protected void execute() {
    	
    	Robot.elevatorSubsystem.set(j_joy.getRawAxis(0));
    	
    	if((RobotConstants.ELEVATOR_MAXIMUM_HEIGHT - Robot.elevatorSubsystem.getElevatorPos()) 
    			<= 6*(1 + Robot.elevatorSubsystem.getSpeed())) {
    		
    		Robot.elevatorSubsystem.setSetpoint(RobotConstants.ELEVATOR_MAXIMUM_HEIGHT - 2);
    		
    	} else if((Robot.elevatorSubsystem.getElevatorPos() - RobotConstants.ELEVATOR_MINIMUM_HEIGHT) 
    			<= 6*(1 + Robot.elevatorSubsystem.getSpeed())) {
    		
    		Robot.elevatorSubsystem.setSetpoint(RobotConstants.ELEVATOR_MINIMUM_HEIGHT + 2);
    		
    	}
    	
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
