package org.usfirst.frc.team4946.robot.commands.autonomous;

import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.commands.drivetrain.auto.DriveStraight;
import org.usfirst.frc.team4946.robot.commands.drivetrain.auto.DriveStraightPID;
import org.usfirst.frc.team4946.robot.commands.drivetrain.auto.TurnPID;
import org.usfirst.frc.team4946.robot.commands.elevator.MoveToHeight;
import org.usfirst.frc.team4946.robot.commands.intake.IntakeWithTimer;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class autoScript_1 extends CommandGroup {

	private double autoSpeed = RobotConstants.ROBOT_DRIVE_AUTO_SPEED;
	private double elevatorMaxHeight = RobotConstants.ELEVATOR_SCALE_HEIGHT;
	private double elevatorMinHeight = RobotConstants.ELEVATOR_MINIMUM_HEIGHT;
	
	private int startPosition = 1; //1 - 3
	private boolean scaleLeft = false;
	private boolean switchLeft = false;
	private boolean switchAndScale = false;
	
	//Drive Distances
	private double currentDriveDistance = 0.00;
	
    public autoScript_1() {
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
    	
    	//Middle pos does not go to scale
    	//Left and right is mirrored
    	//left and right go 2 to scale
    	//if scale and switch are on same side left and right go one switch and one scale    	
    	
    	if(startPosition == 2)
    	{
    		//Middle auto
    		
    		addSequential(new DriveStraightPID(autoSpeed, 48.00));
    		
    		if(switchLeft)
    		{
    			//Go to left side of switch
    			
    			addSequential(new TurnPID(-90));
    			addSequential(new DriveStraightPID(autoSpeed, 78.00));
    			addSequential(new TurnPID(90));
    			
    			//Eject CUbse
    			addSequential(new IntakeWithTimer(-1.00, 0.50));
    		}
    		else
    		{
    			//Go to right side of switch
    			
    			addSequential(new TurnPID(90));
    			addSequential(new DriveStraightPID(autoSpeed, 78.00));
    			addSequential(new TurnPID(-90));
    			
    			//Eject Cube
    			addSequential(new IntakeWithTimer(-1.00, 0.50));
    			
    		}
    	}
    	else if(startPosition == 1)
    	{
    		//Left Auto
    		
    		if (scaleLeft) {
    			
    			//Auto for is the scale is on the left
    			
    			addSequential(new DriveStraight(autoSpeed, 120));
    			
    		} else {
    			
    			//Auto for if the scale is on the right
    			
    			addSequential(new DriveStraight(autoSpeed, 96));
    			addSequential(new TurnPID(90));
    			addSequential(new DriveStraight(autoSpeed, 96));
    			addSequential(new TurnPID(-90));
    			addSequential(new DriveStraight(autoSpeed, 24));
    			
    		}
    		
    		//Move Elevator and then Eject Cube
    		addParallel(new MoveToHeight(elevatorMaxHeight));
			addSequential(new IntakeWithTimer(-1.0, 0.5));

    	}
    	else
    	{
    		//Right Auto
    		
    		if (scaleLeft) {
    			
    			//auto code for if the scale is on the left
    			
    			addSequential(new DriveStraight(autoSpeed, 96));
    			addSequential(new TurnPID(-90));
    			addSequential(new DriveStraight(autoSpeed, 96));
    			addSequential(new TurnPID(90));
    			addSequential(new DriveStraight(autoSpeed, 24));
    			
    		} else {
    			
    			//auto code for if the scale is on the right
    			
    			addSequential(new DriveStraight(autoSpeed, 120));
    		}
    		
    		//Move Elevator and then Eject Cube
    		addParallel(new MoveToHeight(elevatorMaxHeight));
    		addSequential(new IntakeWithTimer(-1.0, 0.5));
    	}
    	
    }
}
