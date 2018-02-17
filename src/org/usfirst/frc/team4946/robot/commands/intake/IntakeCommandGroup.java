package org.usfirst.frc.team4946.robot.commands.intake;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.commands.RumbleJoysticks;
import org.usfirst.frc.team4946.robot.commands.elevator.MoveToHeight;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class IntakeCommandGroup extends CommandGroup {

    public IntakeCommandGroup() {
    	
    	if(!Robot.internalIntakeSubsystem.getHasCube()) {
    		
    		addSequential(new RumbleJoysticks(0.7), 1.0);
    		addSequential(new RumbleJoysticks(0.0));
    		
    	} else {
        
	    	if(Robot.elevatorSubsystem.getElevatorPos() <= RobotConstants.ELEVATOR_MINIMUM_HEIGHT) {
	    		
	    		while(!Robot.internalIntakeSubsystem.getHasCube()) {
	    			
		    		addSequential(new SetIntake(0.4));
	    		}
	    		
	    		addParallel(new SetIntake(0.0));
	    		addSequential(new MoveToHeight(2.0, 0.4));
	    		
	    	} else {
	    		
	    		while(!Robot.internalIntakeSubsystem.getHasCube()) {
	    			
		    		addSequential(new SetIntake(0.4));
	    		}
	    		
	    		addSequential(new SetIntake(0.0));
	    	}
    	}
    }
}
