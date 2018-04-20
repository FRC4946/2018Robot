package org.usfirst.frc.team4946.robot.commands.autonomous;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.commands.drivetrain.auto.AutoDriveStraight;
import org.usfirst.frc.team4946.robot.commands.drivetrain.auto.TurnPID;
import org.usfirst.frc.team4946.robot.commands.elevator.SetElevator;
import org.usfirst.frc.team4946.robot.commands.intake.SetIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**Only run from middle position
 *
 */
public class AutoDriveMid_Switch extends CommandGroup {

    public AutoDriveMid_Switch() {
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
    	
    	//Start in mid position
    	
    	addSequential(new AutoDriveStraight(90, 0.8), 1.5); //Drive forwards
    	
    	if (Robot.scaleRight) { //Turn left or right
    		addSequential(new TurnPID(90.0), 1);	
    	} else {
    		addSequential(new TurnPID(-90.0), 1);
    	}
    	
    	addSequential(new AutoDriveStraight(55, 0.8), 1); //Drive parallel to switch
    	
    	if (Robot.scaleRight) { //turn again
    		addSequential(new TurnPID(-90.0), 1);	
    	} else {
    		addSequential(new TurnPID(90.0), 1);
    	}
    	
    	addParallel(new AutoDriveStraight(18, 0.8), 2); //drive forwards & raise elevator
    	addParallel(new SetElevator(RobotConstants.ELEVATOR_SWITCH_HEIGHT), 1);
    	
    	addSequential(new SetIntake(-1.0), 1); //output cube
    	
    }
}
