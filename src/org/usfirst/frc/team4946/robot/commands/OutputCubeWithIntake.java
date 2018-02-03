package org.usfirst.frc.team4946.robot.commands;

import org.usfirst.frc.team4946.robot.commands.intake.OuterIntakeUntilCube;
import org.usfirst.frc.team4946.robot.commands.output.InnerIntakeUntilCube;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class OutputCubeWithIntake extends CommandGroup {

    public OutputCubeWithIntake() {
    	addParallel(new OuterIntakeUntilCube(0.7)); //The 1.0 argument may need to be inverted
    	addSequential(new InnerIntakeUntilCube(0.7));
    	addSequential(new RumbleJoystickRight());
    }
}
