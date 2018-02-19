package org.usfirst.frc.team4946.robot.commands.intake;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeWithTimer extends Command {

	private Timer m_timer;
	private double m_speed;
	private double m_time;
	
    public IntakeWithTimer(double speed, double time) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.externalIntakeSubsystem);
    	requires(Robot.internalIntakeSubsystem);
    	
    	this.m_time = time;
    	this.m_speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.externalIntakeSubsystem.set(m_speed);	
    	Robot.internalIntakeSubsystem.set(m_speed);	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return m_timer.get() >= m_time;
    }

    // Called once after isFinished returns true
    protected void end() {
    	m_timer.stop();
    	Robot.externalIntakeSubsystem.set(0.00);
    	Robot.internalIntakeSubsystem.set(0.00);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
