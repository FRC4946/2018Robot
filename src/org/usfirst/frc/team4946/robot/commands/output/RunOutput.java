package org.usfirst.frc.team4946.robot.commands.output;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RunOutput extends Command {

	double speed;
	Timer m_timer = new Timer();
	
    public RunOutput(double speed) {
    	
    	requires(Robot.upperOutputSubsystem);
    	this.speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.upperOutputSubsystem.set(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !Robot.upperOutputSubsystem.getHasCube();
    }

    // Called once after isFinished returns true
    protected void end() {
    	m_timer.start();
    	
    	while(m_timer.get() <= 0.5) {
    		Robot.m_oi.getDriveStick().setRumble(RumbleType.kRightRumble, 0.7);
    		Robot.m_oi.getOperatorStick().setRumble(RumbleType.kRightRumble, 0.7);
    	}
    	
    	Robot.upperOutputSubsystem.disableMechanism();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
