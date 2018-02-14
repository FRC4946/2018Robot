package org.usfirst.frc.team4946.robot.commands;

import org.usfirst.frc.team4946.robot.commands.elevator.ElevatorSetHeight;
import org.usfirst.frc.team4946.robot.commands.intake.OuterIntakeUntilCube;
import org.usfirst.frc.team4946.robot.commands.output.InnerIntakeUntilCube;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CubeAndLiftIntake extends CommandGroup {

	/**
	 * Sucks a power-cube in and then lifts it by one-inch for ease of driving. The
	 * external intake is also flipped to its up position - perpendicular to the
	 * ground.
	 */
	public CubeAndLiftIntake() {
		this(0.7);
	}

	public CubeAndLiftIntake(double intakeSpeed) {

		addParallel(new OuterIntakeUntilCube(intakeSpeed)); // The 1.0 argument may need to be inverted
		addSequential(new InnerIntakeUntilCube(intakeSpeed));
		// addParallel(new ElbowUp());
		addSequential(new ElevatorSetHeight(1.0, 0.4), 0.5);
		addSequential(new RumbleJoysticks(), 0.5);
	}
}
