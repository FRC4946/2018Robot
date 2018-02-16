package org.usfirst.frc.team4946.robot.commands.autonomous;

import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.commands.CubeAndLiftIntake;
import org.usfirst.frc.team4946.robot.commands.drivetrain.DriveStraightPID;
import org.usfirst.frc.team4946.robot.commands.drivetrain.TurnPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class S2C5_rightSide_cubeFive extends CommandGroup {

	private double speedDrive_var = RobotConstants.ROBOT_DRIVE_AUTO_SPEED;
	private double speedIntake_var = RobotConstants.ROBOT_DRIVE_AUTO_SPEED;

	public S2C5_rightSide_cubeFive() {

		// right side, cube 5
		addSequential(new TurnPID(-90));
		addSequential(new DriveStraightPID(speedDrive_var, 75.9172));
		addSequential(new TurnPID(-90));
		addSequential(new DriveStraightPID(speedDrive_var, 24.203));
		addSequential(new CubeAndLiftIntake(speedIntake_var));

	}
}