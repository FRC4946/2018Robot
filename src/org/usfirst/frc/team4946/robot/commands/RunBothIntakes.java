package org.usfirst.frc.team4946.robot.commands;

import org.usfirst.frc.team4946.robot.commands.intake.RunIntake;
import org.usfirst.frc.team4946.robot.commands.output.RunOutput;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RunBothIntakes extends CommandGroup {

	double speed;
	
	
	public RunBothIntakes(double speed) {
		
		this.speed = speed;
		
		addSequential(new RunOutput(speed));
		addParallel(new RunIntake(speed));
	}
}
