package org.usfirst.frc.team4946.robot.commands;

import org.usfirst.frc.team4946.robot.commands.elbow.ElbowUp;
import org.usfirst.frc.team4946.robot.commands.elevator.ElevatorSetHeight;
import org.usfirst.frc.team4946.robot.commands.intake.OuterIntakeUntilCube;
import org.usfirst.frc.team4946.robot.commands.output.InnerIntakeUntilCube;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CubeAndLiftIntake extends CommandGroup {

    public CubeAndLiftIntake() {
    	
    	addParallel(new OuterIntakeUntilCube(0.7)); //The 1.0 argument may need to be inverted
    	addSequential(new InnerIntakeUntilCube(0.7));
    	addParallel(new ElbowUp());
    	addSequential(new ElevatorSetHeight(1.0, 0.4), 0.5);
    	addSequential(new RumbleJoystickLeft(), 0.5);
    }
}
