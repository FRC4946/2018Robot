package org.usfirst.frc.team4946.robot.commands.autonomous;

import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.commands.drivetrain.auto.DriveStraightPID;
import org.usfirst.frc.team4946.robot.commands.drivetrain.auto.TurnPID;
import org.usfirst.frc.team4946.robot.commands.intake.CubeAndLiftIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class S2C2_rightSide_cubeTwo extends CommandGroup {

	private double speedDrive_var = RobotConstants.ROBOT_DRIVE_AUTO_SPEED;
	private double speedIntake_var = RobotConstants.ROBOT_DRIVE_AUTO_SPEED;

	public S2C2_rightSide_cubeTwo() {

		// right side, cube 2
		addSequential(new TurnPID(-90));
		addSequential(new DriveStraightPID(speedDrive_var, 149.3854));
		addSequential(new TurnPID(-90));
		addSequential(new DriveStraightPID(speedDrive_var, 24.203));
		addSequential(new CubeAndLiftIntake(speedIntake_var));

	}
}
