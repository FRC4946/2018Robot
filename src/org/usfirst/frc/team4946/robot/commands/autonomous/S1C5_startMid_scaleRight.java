package org.usfirst.frc.team4946.robot.commands.autonomous;

import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.commands.drivetrain.DriveStraightPID;
import org.usfirst.frc.team4946.robot.commands.drivetrain.TurnPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class S1C5_startMid_scaleRight extends CommandGroup {
	
	private double speedDrive_var = RobotConstants.ROBOT_DRIVE_AUTO_SPEED;

    public S1C5_startMid_scaleRight() {
    	
    	//Case 5, Start Mid, Scale Right
    	addSequential(new DriveStraightPID(speedDrive_var, 29.1111));
    	addSequential(new TurnPID(90.0000));
    	addSequential(new DriveStraightPID(speedDrive_var, 88.1620));
    	addSequential(new TurnPID(-90.0000));
    	addSequential(new DriveStraightPID(speedDrive_var, 29.1112));
    	addSequential(new TurnPID(90.0000));
    	addSequential(new DriveStraightPID(speedDrive_var, 36.7341));
    	addSequential(new TurnPID(-90.0000));
    	addSequential(new DriveStraightPID(speedDrive_var, 151.3760));
    		//Place Cube
    	
    }
}