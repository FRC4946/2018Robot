package org.usfirst.frc.team4946.robot.commands.elbow;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OverrideClampPos extends Command {

	int m_count;
	
    public OverrideClampPos() {
    	requires(Robot.elbowSubsystem);
    }

    protected void initialize() {
    	
    	m_count = 0;
    	
    	//If clamp is not disengaged, do so
    	if(Robot.elbowSubsystem.getClampIsEngaged()) {
    		Robot.elbowSubsystem.setClamp(false);
    	}
    	
    	//If the clamp is disengaged and the elevator is at the bottom (or close enough), engage the clamp
    	else if(Robot.elevatorSubsystem.getHeight() <= RobotConstants.ELEVATOR_MINIMUM_HEIGHT + 1.0) {
    		Robot.elbowSubsystem.setClamp(true);
    	}
    	
    	//If the clamp is disengaged and the elevator is not at the bottom, do nothing.
    }

    protected void execute() {
    	m_count++;
    }

    protected boolean isFinished() {
        return m_count >= RobotConstants.PNEUMATIC_FIRING_COUNT;
    }

    protected void end() {
    	Robot.elbowSubsystem.clampOff();
    }
    
    protected void interrupted() {
    	
    }
}
