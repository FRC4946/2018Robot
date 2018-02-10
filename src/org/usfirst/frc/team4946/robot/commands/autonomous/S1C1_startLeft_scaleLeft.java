package org.usfirst.frc.team4946.robot.commands.autonomous;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.commands.drivetrain.DriveStraightPID;
import org.usfirst.frc.team4946.robot.commands.drivetrain.TurnPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class S1C1_startLeft_scaleLeft extends CommandGroup {

	private double speedDrive_var = RobotConstants.ROBOT_DRIVE_AUTO_SPEED;
	
    public S1C1_startLeft_scaleLeft() {
    	
    	//Case 1, Start Left, Scale Left
    	addSequential(new DriveStraightPID(speedDrive_var, 58.2223));
    	addSequential(new TurnPID(-90.0000));
    	addSequential(new DriveStraightPID(speedDrive_var, 36.7341));
    	addSequential(new TurnPID(90.0000));
    	addSequential(new DriveStraightPID(speedDrive_var, 151.3780));
    		//Place Cube
    }
}
