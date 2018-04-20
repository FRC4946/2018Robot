package org.usfirst.frc.team4946.robot.commands.autonomous;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.commands.drivetrain.auto.AutoDriveStraight;
import org.usfirst.frc.team4946.robot.commands.drivetrain.auto.TurnPID;
import org.usfirst.frc.team4946.robot.commands.elevator.SetElevator;
import org.usfirst.frc.team4946.robot.commands.intake.SetIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**only run in pos 1 or 3
 *
 */
public class AutoDriveCross_Scale extends CommandGroup {

    public AutoDriveCross_Scale() {
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
    	addSequential(new AutoDriveStraight(200, 0.8), 3.0); //drives towards scale
    	
    	if (Robot.startPos == 3) { //Turns
    		addSequential(new TurnPID(-90.0), 1.5);	
    	} else {
    		addSequential(new TurnPID(90.0), 1.5);
    	}
    	
    	addSequential(new AutoDriveStraight(162, 0.8), 2.0); //Drives parallel to scale
    	
    	if (Robot.startPos == 3) { //Turns aggain
    		addParallel(new TurnPID(90.0), 1.5);	
    	} else {
    		addParallel(new TurnPID(-90.0), 1.5);
    	}
    	
    	addParallel(new SetElevator(RobotConstants.ELEVATOR_SCALE_HIGHHEIGHT), 1.5); //Drives and sets elevator
    	addParallel(new AutoDriveStraight(104, 0.8), 1.0);
    	
    	addSequential(new SetIntake(-1.0), 1.5); //Sets intake
    	
    }
}
