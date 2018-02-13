package org.usfirst.frc.team4946.robot.commands.autonomous;

import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.commands.drivetrain.DriveStraightPID;
import org.usfirst.frc.team4946.robot.commands.drivetrain.TurnPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class S1C4_startLeft_scaleRight extends CommandGroup {

	private double speedDrive_var = RobotConstants.ROBOT_DRIVE_AUTO_SPEED;
	
    public S1C4_startLeft_scaleRight() {
    	
    	//Case 4, Start Left, Scale Right
    	addSequential(new DriveStraightPID(speedDrive_var, 27.1111));
    	addSequential(new TurnPID(90.0000));
    	addSequential(new DriveStraightPID(speedDrive_var, 176.3258));
    	addSequential(new TurnPID(-90.0000));
    	addSequential(new DriveStraightPID(speedDrive_var, 31.1112));
    	addSequential(new TurnPID(90.0000));
    	addSequential(new DriveStraightPID(speedDrive_var, 36.7341));
    	addSequential(new TurnPID(-90.0000));
    	addSequential(new DriveStraightPID(speedDrive_var, 151.3780));
    		//Place Cube
    	
    }
}