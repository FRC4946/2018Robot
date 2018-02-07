package org.usfirst.frc.team4946.robot.commands.autoscripts;

import org.usfirst.frc.team4946.robot.commands.drivetrain.DriveStraightPID;
import org.usfirst.frc.team4946.robot.commands.drivetrain.TurnPID;
import org.usfirst.frc.team4946.robot.commands.intake.IntakeUntilCube;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GrabCubeAtStart extends CommandGroup {

	int cube;
	double move;
	
    public GrabCubeAtStart(int cube) {
    	
        this.cube = cube;
        
        move = ((((double) cube/6.0)*153.5) - ((1/12)*153.3));
        
        addSequential(new TurnPID(0.0));
        
        addSequential(new DriveStraightPID(0.6, 79.24));
        
        addSequential(new TurnPID(90.0)); // may need to be changed to -90
        
        addSequential(new DriveStraightPID(0.6, 42.723 + move));
        
        addSequential(new TurnPID(180)); // Turn values may need to be changed
        
        addParallel(new IntakeUntilCube(-1.0)); //Argument may need to be changed
        
        addSequential(new DriveStraightPID(0.6, 51.24));
        
        
    }
}
