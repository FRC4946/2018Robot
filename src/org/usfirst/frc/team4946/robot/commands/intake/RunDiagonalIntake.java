package org.usfirst.frc.team4946.robot.commands.intake;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RunDiagonalIntake extends Command {

	Timer m_timer;
	double m_speed;
	boolean m_hasCube;
	
    public RunDiagonalIntake(double speed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.externalIntakeSubsystem);
    	m_speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    
    	m_timer = new Timer();
    	m_timer.reset();
    	m_timer.start();
    	
    	m_hasCube = Robot.upperOutputSubsystem.getHasCube();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	while(m_timer.get() < 0.3) {
    		Robot.externalIntakeSubsystem.diagonalSpin(m_speed/2);
    	}
    	while(m_timer.get() > 0.3 && m_timer.get() < 0.8) {
    		Robot.externalIntakeSubsystem.set(m_speed);
    		Robot.driveTrainSubsystem.arcadeDrive(0.37, 0.0);
    	}
    	
    	Robot.externalIntakeSubsystem.set(m_speed);
    	Robot.driveTrainSubsystem.arcadeDrive(0.0, 0.0);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	m_timer.stop();
    	m_timer.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}