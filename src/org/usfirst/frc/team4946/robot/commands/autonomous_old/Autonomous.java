package org.usfirst.frc.team4946.robot.commands.autonomous;

import org.usfirst.frc.team4946.robot.commands.drivetrain.auto.DriveStraightPID;
import org.usfirst.frc.team4946.robot.commands.drivetrain.auto.TurnPID;
import org.usfirst.frc.team4946.robot.commands.intake.CubeAndLiftIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Autonomous extends CommandGroup {

	private double speedDrive_var = 0.2;
	private double speedIntake_var = 0.2;

	public Autonomous() {
		// Add Commands here:
		// e.g. addSequential(new Command1());
		// addSequential(new Command2());
		// these will run in order.

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		// addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.
	}

	public void Auto_FirstStage() {
		// Move From Start to scale and place cube

		// Case 1, Start Left, Scale Left
		addSequential(new DriveStraightPID(speedDrive_var, 58.2223));
		addSequential(new TurnPID(-90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 36.7341));
		addSequential(new TurnPID(90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 151.3780));
		// Place Cube

		// Case 2, Start Mid, Scale Left
		addSequential(new DriveStraightPID(speedDrive_var, 27.1111));
		addSequential(new TurnPID(-90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 88.1620));
		addSequential(new TurnPID(90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 31.1112));
		addSequential(new TurnPID(-90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 36.7341));
		addSequential(new TurnPID(90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 151.3780));
		// Place Cube

		// Case 3, Start Right, Scale Left
		addSequential(new DriveStraightPID(speedDrive_var, 27.1111));
		addSequential(new TurnPID(-90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 176.3258));
		addSequential(new TurnPID(90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 31.1112));
		addSequential(new TurnPID(-90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 36.7341));
		addSequential(new TurnPID(90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 151.3780));
		// Place Cube

		// Case 4, Start Left, Scale Right
		addSequential(new DriveStraightPID(speedDrive_var, 27.1111));
		addSequential(new TurnPID(90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 176.3258));
		addSequential(new TurnPID(-90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 31.1112));
		addSequential(new TurnPID(90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 36.7341));
		addSequential(new TurnPID(-90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 151.3780));
		// Place Cube

		// Case 5, Start Mid, Scale Right
		addSequential(new DriveStraightPID(speedDrive_var, 29.1111));
		addSequential(new TurnPID(90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 88.1620));
		addSequential(new TurnPID(-90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 29.1112));
		addSequential(new TurnPID(90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 36.7341));
		addSequential(new TurnPID(-90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 151.3760));
		// Place Cube

		// Case 6, start right, scale right
		addSequential(new DriveStraightPID(speedDrive_var, 58.2223));
		addSequential(new TurnPID(90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 36.7341));
		addSequential(new TurnPID(-90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 151.3760));
		// Place Cube
	}

	public void Auto_SecondStage() {

		// cubes are listed in order from left to right

		// speed is always 0.2

		// right side, cube 6
		addSequential(new TurnPID(-90));
		addSequential(new DriveStraightPID(speedDrive_var, 51.4278));
		addSequential(new TurnPID(-90));
		addSequential(new DriveStraightPID(speedDrive_var, 24.203));
		addParallel(new CubeAndLiftIntake(speedIntake_var));

		// right side, cube 5
		addSequential(new TurnPID(-90));
		addSequential(new DriveStraightPID(0.2, 75.9172));
		addSequential(new TurnPID(-90));
		addSequential(new DriveStraightPID(0.2, 24.203));
		addSequential(new CubeAndLiftIntake(0.2));

		// right side, cube 4
		addSequential(new TurnPID(-90));
		addSequential(new DriveStraightPID(0.2, 100.4066));
		addSequential(new TurnPID(-90));
		addSequential(new DriveStraightPID(0.2, 24.203));
		addSequential(new CubeAndLiftIntake(0.2));

		// right side, cube 3
		addSequential(new TurnPID(-90));
		addSequential(new DriveStraightPID(0.2, 124.896));
		addSequential(new TurnPID(-90));
		addSequential(new DriveStraightPID(0.2, 24.203));
		addSequential(new CubeAndLiftIntake(0.2));

		// right side, cube 2
		addSequential(new TurnPID(-90));
		addSequential(new DriveStraightPID(0.2, 149.3854));
		addSequential(new TurnPID(-90));
		addSequential(new DriveStraightPID(0.2, 24.203));
		addSequential(new CubeAndLiftIntake(0.2));

		// right side, cube 1
		addSequential(new TurnPID(-90));
		addSequential(new DriveStraightPID(0.2, 173.8748));
		addSequential(new TurnPID(-90));
		addSequential(new DriveStraightPID(0.2, 24.203));
		addSequential(new CubeAndLiftIntake(0.2));

		// LEFT SIDE//

		// left side, cube 1
		addSequential(new TurnPID(90));
		addSequential(new DriveStraightPID(0.2, 51.4278));
		addSequential(new TurnPID(90));
		addSequential(new DriveStraightPID(0.2, 24.203));
		addSequential(new CubeAndLiftIntake(0.2));

		// left side, cube 2
		addSequential(new TurnPID(90));
		addSequential(new DriveStraightPID(0.2, 75.9172));
		addSequential(new TurnPID(90));
		addSequential(new DriveStraightPID(0.2, 24.203));
		addSequential(new CubeAndLiftIntake(0.2));

		// left side, cube 3
		addSequential(new TurnPID(90));
		addSequential(new DriveStraightPID(0.2, 100.4066));
		addSequential(new TurnPID(90));
		addSequential(new DriveStraightPID(0.2, 24.203));
		addSequential(new CubeAndLiftIntake(0.2));

		// left side, cube 4
		addSequential(new TurnPID(90));
		addSequential(new DriveStraightPID(0.2, 124.896));
		addSequential(new TurnPID(90));
		addSequential(new DriveStraightPID(0.2, 24.203));
		addSequential(new CubeAndLiftIntake(0.2));

		// left side, cube 5
		addSequential(new TurnPID(90));
		addSequential(new DriveStraightPID(0.2, 149.3854));
		addSequential(new TurnPID(90));
		addSequential(new DriveStraightPID(0.2, 24.203));
		addSequential(new CubeAndLiftIntake(0.2));

		// left side, cube 6
		addSequential(new TurnPID(90));
		addSequential(new DriveStraightPID(0.2, 173.8748));
		addSequential(new TurnPID(90));
		addSequential(new DriveStraightPID(0.2, 24.203));
		addSequential(new CubeAndLiftIntake(0.2));

		// Move from scale and fetch new cube
	}

	public void Auto_ThirdStage() {
		// Move to switch and place new cube

		// Case 1, Right side, close
		addSequential(new TurnPID(90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 66.5605));
		addSequential(new TurnPID(90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 16.1630));
		// Place Cube

		// Case 2, Right side, long
		addSequential(new TurnPID(90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 82.4083));
		addSequential(new TurnPID(90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 215.9959));
		addSequential(new TurnPID(90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 92.4581));
		addSequential(new TurnPID(90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 23.9472));
		// Place Cube

		// Case 3, Left side, close
		addSequential(new TurnPID(-90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 66.5605));
		addSequential(new TurnPID(-90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 16.1630));
		// Place cube

		// Case 4, Left Side, long
		addSequential(new TurnPID(-90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 82.4083));
		addSequential(new TurnPID(-90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 215.9959));
		addSequential(new TurnPID(-90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 92.4581));
		addSequential(new TurnPID(-90.0000));
		addSequential(new DriveStraightPID(speedDrive_var, 23.9472));
		// Place Cube

	}
}
