package org.usfirst.frc.team4946.robot.commands.autonomous;

import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.commands.drivetrain.DriveStraightPID;
import org.usfirst.frc.team4946.robot.commands.drivetrain.TurnPID;
import org.usfirst.frc.team4946.robot.commands.elevator.MoveToHeight;
import org.usfirst.frc.team4946.robot.commands.output.RunOutput;

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
    	addParallel(new MoveToHeight(RobotConstants.ELEVATOR_MAXIMUM_HEIGHT, 0.8));
    	addSequential(new RunOutput(-1.0));
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	addSequential(new RunOutput(0.0));
    	addParallel(new MoveToHeight(RobotConstants.ELEVATOR_MINIMUM_HEIGHT, 0.8));
    	
    }
}
