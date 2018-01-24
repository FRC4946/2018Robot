package org.usfirst.frc.team4946.robot.commands;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.commands.clamp.ChangeClamp;
import org.usfirst.frc.team4946.robot.commands.intake.IntakeUntilCube;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class IntakeAndClampCube extends CommandGroup {

    public IntakeAndClampCube() {
    	addSequential(new ChangeClamp(false));
    	addSequential(new IntakeUntilCube(1.0)); //The 1.0 argument may need to be inverted
    	addSequential(new ChangeClamp(true));
    }
}
