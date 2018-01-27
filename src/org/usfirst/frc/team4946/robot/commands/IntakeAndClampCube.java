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
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	addSequential(new ChangeClamp(false));
    	addParallel(new IntakeUntilCube(1.0)); //The 1.0 argument may need to be inverted
    	addSequential(new OutputIntakeUntilCube(1.0)); //Argument may need to be inverted
    	addSequential(new ChangeClamp(true));
    }
}
