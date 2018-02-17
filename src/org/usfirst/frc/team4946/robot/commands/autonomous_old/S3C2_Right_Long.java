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
public class S3C2_Right_Long extends CommandGroup {

	double speedDrive_var = RobotConstants.ROBOT_DRIVE_AUTO_SPEED;
	
    public S3C2_Right_Long() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	//Case 2, Right side, long
    	addSequential(new TurnPID(90.0000));
    	addSequential(new DriveStraightPID(speedDrive_var, 82.4083));
    	addSequential(new TurnPID(90.0000));
    	addSequential(new DriveStraightPID(speedDrive_var, 215.9959));
    	addSequential(new TurnPID(90.0000));
    	addSequential(new DriveStraightPID(speedDrive_var, 92.4581));
    	addSequential(new TurnPID(90.0000));
    	addSequential(new DriveStraightPID(speedDrive_var, 23.9472));
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
