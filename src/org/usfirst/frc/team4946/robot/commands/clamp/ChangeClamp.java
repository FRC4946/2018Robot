package org.usfirst.frc.team4946.robot.commands.clamp;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ChangeClamp extends Command {
	boolean position;
	
    public ChangeClamp(boolean position) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.elevatorClampSubsystem);
    	
    	this.position = position;
    }
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	/**
    	 * @param sets the solenoid position to the imputed position 
    	 */
    	Robot.elevatorClampSubsystem.moveSolenoid(position);
    	
    	
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
    	end();
    }
}