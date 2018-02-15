package org.usfirst.frc.team4946.robot.commands.autonomous;

import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.commands.CubeAndLiftIntake;
import org.usfirst.frc.team4946.robot.commands.drivetrain.DriveStraightPID;
import org.usfirst.frc.team4946.robot.commands.drivetrain.TurnPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class S2C3_rightSide_cubeThree extends CommandGroup {

	private double speedDrive_var = RobotConstants.ROBOT_DRIVE_AUTO_SPEED;
	private double speedIntake_var = RobotConstants.ROBOT_DRIVE_AUTO_SPEED;

	public S2C3_rightSide_cubeThree() {

		// right side, cube 3
		addSequential(new TurnPID(-90));
		addSequential(new DriveStraightPID(speedDrive_var, 124.896));
		addSequential(new TurnPID(-90));
		addSequential(new DriveStraightPID(speedDrive_var, 24.203));
		addSequential(new CubeAndLiftIntake(speedIntake_var));

	}
}
