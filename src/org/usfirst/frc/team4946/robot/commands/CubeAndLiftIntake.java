package org.usfirst.frc.team4946.robot.commands;

import org.usfirst.frc.team4946.robot.commands.intake.IntakeUntilCube;
import org.usfirst.frc.team4946.robot.commands.output.InnerIntakeUntilCube;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CubeAndLiftIntake extends CommandGroup {

    public CubeAndLiftIntake() {
    	
    	addParallel(new IntakeUntilCube(0.7), 1.5); //The 1.0 argument may need to be inverted
    	addParallel(new InnerIntakeUntilCube(0.7), 1.5);
    	//AddParallel (new LiftIntake());
    	addSequential(new RumbleJoystickLeft(), 0.5);
    }
}
