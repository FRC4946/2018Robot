package org.usfirst.frc.team4946.robot.commands;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OpenLoop extends Command {

	Joystick j_joy;
	
    public OpenLoop(Joystick j_joy) {
    	
    	requires(Robot.ElevatorSubsystem);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	this.j_joy = j_joy;

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	Robot.ElevatorSubsystem.set(j_joy.getRawAxis(1));
    	
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
