package org.usfirst.frc.team4946.robot.commands;

import org.usfirst.frc.team4946.robot.commands.clamp.ChangeClamp;
import org.usfirst.frc.team4946.robot.commands.intake.IntakeUntilCube;
import org.usfirst.frc.team4946.robot.commands.output.OutputIntakeUntilCube;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class IntakeAndClampCube extends CommandGroup {

    public IntakeAndClampCube() {
    	addSequential(new ChangeClamp(false));
    	addParallel(new IntakeUntilCube(1.0)); //The 1.0 argument may need to be inverted
    	addSequential(new OutputIntakeUntilCube(1.0)); //Argument may need to be inverted
    	addSequential(new ChangeClamp(true));
    }
}
