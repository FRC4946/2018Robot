package org.usfirst.frc.team4946.robot.commands;

import org.usfirst.frc.team4946.robot.commands.intake.RunIntake;
import org.usfirst.frc.team4946.robot.commands.output.RunOutput;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class OutputCubeWithIntake extends CommandGroup {

	/**
	 * Runs both intake motors - external and internal
	 */
    public OutputCubeWithIntake() {
    	
//    	addSequential(new ElbowDown());
    	addParallel(new RunOutput(-0.7)); //The 1.0 argument may need to be inverted
    	addSequential(new RunIntake(-0.7));
    }
}
