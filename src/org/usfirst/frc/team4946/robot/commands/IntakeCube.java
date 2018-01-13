package org.usfirst.frc.team4946.robot.commands;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeCube extends Command {

	Timer timer = new Timer();
	
    public IntakeCube() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	requires(Robot.intakeMechanismSubsystem);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	timer.start();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	if (!(timer.get() > 5)) {
    		Robot.intakeMechanismSubsystem.spin(-1.0);
    	} else {
    		timer.stop();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
    	boolean out = false;
    	
    	if (timer.get() > 5) {
    		out = true;
    	} 
    	return out;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intakeMechanismSubsystem.disableMechanism();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
