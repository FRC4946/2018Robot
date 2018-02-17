package org.usfirst.frc.team4946.robot.commands.intake;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeWithTimer extends Command {

	private Timer timer;
	private double speed;
	private double time;
	
    public IntakeWithTimer(double speed, double time) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.externalIntakeSubsystem);
    	requires(Robot.internalIntakeSubsystem);
    	
    	this.time = time;
    	this.speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.externalIntakeSubsystem.set(speed);	
    	Robot.internalIntakeSubsystem.set(speed);	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timer.get() >= time;
    }

    // Called once after isFinished returns true
    protected void end() {
    	timer.stop();
    	Robot.externalIntakeSubsystem.set(0.00);
    	Robot.internalIntakeSubsystem.set(0.00);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
