package org.usfirst.frc.team4946.robot.commands;

import org.usfirst.frc.team4946.robot.commands.intake.RunIntake;
import org.usfirst.frc.team4946.robot.commands.output.RunOutput;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RunBothIntakes extends CommandGroup {

	public RunBothIntakes() {
		addParallel(new RunIntake(0.5));
		addSequential(new RunOutput(0.5));
	}
}
