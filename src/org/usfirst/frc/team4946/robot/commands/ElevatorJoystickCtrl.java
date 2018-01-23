package org.usfirst.frc.team4946.robot.commands;

import org.usfirst.frc.team4946.robot.OI;
import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorJoystickCtrl extends Command {

	Joystick j_joy;
	
    public ElevatorJoystickCtrl() {
    	requires(Robot.elevatorSubsystem);

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	j_joy = OI.getOperatorStick();
    	Robot.elbowSubsystem.setElbowUp();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	Robot.elevatorSubsystem.set(j_joy.getRawAxis(0));
    	if((RobotConstants.ELEVATOR_MAXIMUM_HEIGHT - Robot.elevatorSubsystem.getElevatorPos()) 
    			<= 6*(1 + Robot.elevatorSubsystem.getSpeed())) {
    		
    		Robot.elevatorSubsystem.setSetpoint(RobotConstants.ELEVATOR_MAXIMUM_HEIGHT - 2);
    		
    	} else if((Robot.elevatorSubsystem.getElevatorPos() - RobotConstants.ELEVATOR_MINIMUM_HEIGHT) 
    			<= 6*(1 + Robot.elevatorSubsystem.getSpeed())) {
    		
    		Robot.elevatorSubsystem.setSetpoint(RobotConstants.ELEVATOR_MINIMUM_HEIGHT + 2);
    		
    	}
    	
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
