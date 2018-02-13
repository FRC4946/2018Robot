package org.usfirst.frc.team4946.robot.commands.autonomous;

import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.commands.drivetrain.DriveStraightPID;
import org.usfirst.frc.team4946.robot.commands.drivetrain.TurnPID;
import org.usfirst.frc.team4946.robot.commands.intake.IntakeUntilCube;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class S2C6_leftSide_cubeSix extends CommandGroup {
	
	private double speedDrive_var = RobotConstants.ROBOT_DRIVE_AUTO_SPEED;
	private double speedIntake_var = RobotConstants.ROBOT_DRIVE_AUTO_SPEED;

    public S2C6_leftSide_cubeSix() {
    	
    	//left side, cube 6
    	addSequential(new TurnPID(90));
    	addSequential(new DriveStraightPID(speedDrive_var,173.8748));
    	addSequential(new TurnPID(90));
    	addSequential(new DriveStraightPID(speedDrive_var, 24.203));
    	addSequential(new IntakeUntilCube(speedIntake_var));
    	
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
    }
}
