package org.usfirst.frc.team4946.robot.commands.autonomous;

import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.commands.drivetrain.DriveStraightPID;
import org.usfirst.frc.team4946.robot.commands.drivetrain.TurnPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class S3C3_Left_Close extends CommandGroup {

	double speedDrive_var = RobotConstants.ROBOT_DRIVE_AUTO_SPEED;
	
    public S3C3_Left_Close() {
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
    	//Case 3, Left side, close
    	addSequential(new TurnPID(-90.0000));
    	addSequential(new DriveStraightPID(speedDrive_var, 66.5605));
    	addSequential(new TurnPID(-90.0000));
    	addSequential(new DriveStraightPID(speedDrive_var, 16.1630));
    		//Place cube

    }
}
