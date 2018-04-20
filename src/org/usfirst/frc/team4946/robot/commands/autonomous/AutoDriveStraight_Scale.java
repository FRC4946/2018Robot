package org.usfirst.frc.team4946.robot.commands.autonomous;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.commands.drivetrain.auto.AutoDriveStraight;
import org.usfirst.frc.team4946.robot.commands.drivetrain.auto.TurnPID;
import org.usfirst.frc.team4946.robot.commands.elevator.SetElevator;
import org.usfirst.frc.team4946.robot.commands.intake.SetIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**Only run in pos 1 or 3
 *
 */
public class AutoDriveStraight_Scale extends CommandGroup {

    public AutoDriveStraight_Scale() {
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
    	
    	addSequential(new AutoDriveStraight(270, 0.8), 3.0); //drive towards scale while raising elevator
    	addParallel(new SetElevator(RobotConstants.ELEVATOR_SCALE_HIGHHEIGHT), 1.0);
    	
    	if (Robot.startPos == 3) { //turn
    		
    		addSequential(new TurnPID(-90.0), 1);	
    		
    	} else {
    		
    		addSequential(new TurnPID(90.0), 1);
    		
    	}
    	
    	addSequential(new AutoDriveStraight(24, 0.8), 1); //drive forwards 
    	addSequential(new SetIntake(-1.0), 1);
    	
    }
}
