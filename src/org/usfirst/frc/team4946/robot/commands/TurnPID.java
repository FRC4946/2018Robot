package org.usfirst.frc.team4946.robot.commands;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnPID extends Command {
	
	double m_setAngle;

    public TurnPID(double angle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.DriveTrain);
    	m_setAngle = angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.DriveTrain.setGyroSetpoint(m_setAngle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.DriveTrain.getGyroOnTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.DriveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
