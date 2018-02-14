package org.usfirst.frc.team4946.robot.commands.autonomous;

import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.commands.CubeAndLiftIntake;
import org.usfirst.frc.team4946.robot.commands.drivetrain.DriveStraightPID;
import org.usfirst.frc.team4946.robot.commands.drivetrain.TurnPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class S2C6_rightSide_cubeSix extends CommandGroup {

	private double speedDrive_var = RobotConstants.ROBOT_DRIVE_AUTO_SPEED;
	private double speedIntake_var = RobotConstants.ROBOT_DRIVE_AUTO_SPEED;

	public S2C6_rightSide_cubeSix() {

		// right side, cube 6
		addSequential(new TurnPID(-90));
		addSequential(new DriveStraightPID(speedDrive_var, 51.4278));
		addSequential(new TurnPID(-90));
		addSequential(new DriveStraightPID(speedDrive_var, 24.203));
		addParallel(new CubeAndLiftIntake(speedIntake_var));

	}
}
