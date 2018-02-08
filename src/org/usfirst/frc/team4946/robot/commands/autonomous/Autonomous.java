package org.usfirst.frc.team4946.robot.commands.autonomous;

import org.usfirst.frc.team4946.robot.commands.drivetrain.DriveStraightPID;
import org.usfirst.frc.team4946.robot.commands.drivetrain.TurnPID;
import org.usfirst.frc.team4946.robot.commands.intake.IntakeUntilCube;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Autonomous extends CommandGroup {

    public Autonomous() {
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
    
    public void Auto_FirstStage()
    {
    	//Move From Start to scale and place cube
    }
    
    public void Auto_SecondStage()
    {
    	
    	//cubes are listed in order from left to right
    	
    	//speed is always 0.2
    	
    
    	//right side, cube 6
    	addSequential(new TurnPID(-90));
    	addSequential(new DriveStraightPID(0.2 , 51.4278));
    	addSequential(new TurnPID(-90));
    	addSequential(new DriveStraightPID(0.2 , 24.203));
    	addSequential(new IntakeUntilCube(0.2));
    	
    	//right side, cube 5
    	addSequential(new TurnPID(-90));
    	addSequential(new DriveStraightPID(0.2 ,75.9172));
    	addSequential(new TurnPID(-90));
    	addSequential(new DriveStraightPID(0.2 , 24.203));
    	addSequential(new IntakeUntilCube(0.2));
    	
    	//right side, cube 4
    	addSequential(new TurnPID(-90));
    	addSequential(new DriveStraightPID(0.2 ,100.4066));
    	addSequential(new TurnPID(-90));
    	addSequential(new DriveStraightPID(0.2 , 24.203));
    	addSequential(new IntakeUntilCube(0.2));
    	
    	//right side, cube 3
    	addSequential(new TurnPID(-90));
    	addSequential(new DriveStraightPID(0.2 ,124.896));
    	addSequential(new TurnPID(-90));
    	addSequential(new DriveStraightPID(0.2 , 24.203));
    	addSequential(new IntakeUntilCube(0.2));
    	
    	//right side, cube 2
    	addSequential(new TurnPID(-90));
    	addSequential(new DriveStraightPID(0.2 ,149.3854));
    	addSequential(new TurnPID(-90));
    	addSequential(new DriveStraightPID(0.2 , 24.203));
    	addSequential(new IntakeUntilCube(0.2));
    	
    	//right side, cube 1
    	addSequential(new TurnPID(-90));
    	addSequential(new DriveStraightPID(0.2 ,173.8748));
    	addSequential(new TurnPID(-90));
    	addSequential(new DriveStraightPID(0.2 , 24.203));
    	addSequential(new IntakeUntilCube(0.2));
    	
    	
    	//LEFT SIDE//
    	
    	//left side, cube 1
    	addSequential(new TurnPID(90));
    	addSequential(new DriveStraightPID(0.2 , 51.4278));
    	addSequential(new TurnPID(90));
    	addSequential(new DriveStraightPID(0.2 , 24.203));
    	addSequential(new IntakeUntilCube(0.2));
    	
    	//left side, cube 2
    	addSequential(new TurnPID(90));
    	addSequential(new DriveStraightPID(0.2 ,75.9172));
    	addSequential(new TurnPID(90));
    	addSequential(new DriveStraightPID(0.2 , 24.203));
    	addSequential(new IntakeUntilCube(0.2));
    	
    	//left side, cube 3
    	addSequential(new TurnPID(90));
    	addSequential(new DriveStraightPID(0.2 ,100.4066));
    	addSequential(new TurnPID(90));
    	addSequential(new DriveStraightPID(0.2 , 24.203));
    	addSequential(new IntakeUntilCube(0.2));
    	
    	//left side, cube 4
    	addSequential(new TurnPID(90));
    	addSequential(new DriveStraightPID(0.2 ,124.896));
    	addSequential(new TurnPID(90));
    	addSequential(new DriveStraightPID(0.2 , 24.203));
    	addSequential(new IntakeUntilCube(0.2));
    	
    	//left side, cube 5
    	addSequential(new TurnPID(90));
    	addSequential(new DriveStraightPID(0.2 ,149.3854));
    	addSequential(new TurnPID(90));
    	addSequential(new DriveStraightPID(0.2 , 24.203));
    	addSequential(new IntakeUntilCube(0.2));
    	
    	//left side, cube 6
    	addSequential(new TurnPID(90));
    	addSequential(new DriveStraightPID(0.2 ,173.8748));
    	addSequential(new TurnPID(90));
    	addSequential(new DriveStraightPID(0.2 , 24.203));
    	addSequential(new IntakeUntilCube(0.2));
    
    	
    	//Move from scale and fetch new cube
    }
    
    public void Auto_ThirdStage()
    {
    	//Move to switch and place new cube
    }
}
