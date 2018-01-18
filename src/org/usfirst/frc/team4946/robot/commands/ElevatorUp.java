package org.usfirst.frc.team4946.robot.commands;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.RobotMap;
import org.usfirst.frc.team4946.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorUp extends Command {
	
	double m_elevatorSpeed;
	

    public ElevatorUp(double elevatorSpeed) {
    	
    	m_elevatorSpeed = elevatorSpeed;
    	requires(Robot.ElevatorSubsystem);
    	
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	Robot.ElevatorSubsystem.set(m_elevatorSpeed);
    	//this is a dummy number -----^
    	
    	Robot.ElevatorSubsystem.getElevatorPos();
    	Robot.ElevatorSubsystem.setPoint(0.1);
    
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
