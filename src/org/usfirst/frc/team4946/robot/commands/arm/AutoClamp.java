package org.usfirst.frc.team4946.robot.commands.arm;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoClamp extends Command {
	
	int m_count;
	boolean m_initElevatorState;
	boolean m_currElevatorState;

    public AutoClamp() {
    	requires(Robot.armSubsystem);
    	requires(Robot.intakeSubsystem);
    	requires(Robot.elevatorSubsystem);
    }

    protected void initialize() {
    	
    	m_count = 0;
    	
    	m_initElevatorState = Robot.elevatorSubsystem.getHeight() <= RobotConstants.ELEVATOR_INTERFERE_MAX;
    	
    	//Disengage the clamp if the clamp is not overridden and there's a cube detected.
    	if(!Robot.armSubsystem.getClampOveridden() && Robot.intakeSubsystem.getBannerHasCube()) 
    		Robot.armSubsystem.setClamp(false);
    }

    protected void execute() {
    
    	m_currElevatorState = Robot.elevatorSubsystem.getHeight() <= RobotConstants.ELEVATOR_INTERFERE_MAX;
    	
    	//When the elevator moves to a point upwards or downards of the arms in the upward position, we reset 
    	//whether the clamp as been overridden to false. 
    	if(m_currElevatorState != m_initElevatorState) {
    		
    		m_initElevatorState = m_currElevatorState;
    		Robot.armSubsystem.setClampOveridden(false);
    		initialize();
    	}
    	
    	m_count++;
    }

    protected boolean isFinished() {
        return Robot.armSubsystem.getClampOveridden() || m_count >= RobotConstants.PNEUMATIC_FIRING_COUNT;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
