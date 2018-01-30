package org.usfirst.frc.team4946.robot.commands;

import java.nio.file.Path;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DrivePathPID extends Command {
	
	Path m_leftPath;
	Path m_rightPath;
	double m_pathLength;

    public DrivePathPID(Path leftPath, Path rightPath) {
    	requires(Robot.driveTrainSubsystem);
    	m_leftPath = leftPath;
    	m_rightPath = rightPath;
    }

    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
