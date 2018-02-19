package org.usfirst.frc.team4946.robot.commands.autonomous;

import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.commands.drivetrain.auto.DriveStraightPID;
import org.usfirst.frc.team4946.robot.commands.drivetrain.auto.TurnPID;
import org.usfirst.frc.team4946.robot.commands.intake.IntakeWithTimer;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class autoScript_2 extends CommandGroup {

	private double autoSpeed = RobotConstants.ROBOT_DRIVE_AUTO_SPEED;
	private double elevatorMaxHeight = RobotConstants.ELEVATOR_SCALE_HEIGHT;
	private double elevatorMinHeight = RobotConstants.ELEVATOR_MINIMUM_HEIGHT;
	
	private int startPosition = 1; //1 - 4
	private boolean scaleLeft = false;
	private boolean switchLeft = false;
	private boolean switchAndScale = false;
	
	//Drive Distances
	private double currentDriveDistance = 0.00;
	
    public autoScript_2() {
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
    	
    	if(startPosition == 1)	    	//Start from scale left
    	{
    		addSequential(new TurnPID(180));
    		addSequential(new DriveStraightPID(autoSpeed, 180));
    		addSequential(new TurnPID(-90));
    		addParallel(new DriveStraightPID(autoSpeed, 2.00));
    		addSequential(new IntakeWithTimer(1.00, 0.50));
    	}
    	else if(startPosition == 2)	    	//Start from switch left
    	{
    		addSequential(new TurnPID(180));	
    	}
    	else if(startPosition == 3)	    	//Start from switch right
    	{
    		addSequential(new TurnPID(180));
    	}
    	else	    	//Start from scale right
    	{
    		addSequential(new TurnPID(180));
    		addSequential(new DriveStraightPID(autoSpeed, 200));
    	}
    	
    }
}
