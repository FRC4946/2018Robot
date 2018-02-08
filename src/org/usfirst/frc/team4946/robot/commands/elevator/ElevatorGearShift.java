package org.usfirst.frc.team4946.robot.commands.elevator;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorGearShift extends Command {

	boolean position;
	
    public ElevatorGearShift(boolean position) {
    	requires(Robot.elevatorTransmissionSubsystem);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	this.position = position;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.elevatorTransmissionSubsystem.moveSolenoid(position);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.elevatorTransmissionSubsystem.moveSolenoid(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
