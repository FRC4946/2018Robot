package org.usfirst.frc.team4946.robot.commands.intake;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.commands.RumbleJoysticks;
import org.usfirst.frc.team4946.robot.commands.elevator.MoveToHeight;
import org.usfirst.frc.team4946.robot.commands.output.RunOutput;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class IntakeCommandGroup extends CommandGroup {

    public IntakeCommandGroup() {
    	
    	if(!Robot.upperOutputSubsystem.getHasCube()) {
    		
    		addSequential(new RumbleJoysticks(0.7), 1.0);
    		addSequential(new RumbleJoysticks(0.0));
    		
    	} else {
        
	    	if(Robot.elevatorSubsystem.getElevatorPos() <= RobotConstants.ELEVATOR_MINIMUM_HEIGHT) {
	    		
	    		while(!Robot.upperOutputSubsystem.getHasCube()) {
	    			
		    		addParallel(new RunIntake(0.5));
		    		addSequential(new RunOutput(0.4));
	    		}
	    		
	    		addParallel(new RunIntake(0.0));
	    		addParallel(new RunOutput(0.0));
	    		addSequential(new MoveToHeight(2.0, 0.4));
	    		
	    	} else {
	    		
	    		while(!Robot.upperOutputSubsystem.getHasCube()) {
	    			
		    		addSequential(new RunOutput(0.4));
	    		}
	    		
	    		addSequential(new RunOutput(0.0));
	    	}
    	}
    }
}
