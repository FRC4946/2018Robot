package org.usfirst.frc.team4946.robot.commands.autonomous;

import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.commands.drivetrain.auto.DriveStraightPID;
import org.usfirst.frc.team4946.robot.commands.drivetrain.auto.TurnPID;
import org.usfirst.frc.team4946.robot.commands.elevator.MoveToHeight;
import org.usfirst.frc.team4946.robot.commands.internalIntake.SetIntIntake;

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
    	addParallel(new MoveToHeight(RobotConstants.ELEVATOR_MAXIMUM_HEIGHT, 0.8));
    	addSequential(new SetIntIntake(-1.0));
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	addSequential(new SetIntIntake(0.0));
    	addParallel(new MoveToHeight(RobotConstants.ELEVATOR_MINIMUM_HEIGHT, 0.8));
    	
    }
}