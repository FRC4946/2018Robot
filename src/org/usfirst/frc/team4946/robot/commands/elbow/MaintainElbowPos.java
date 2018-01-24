package org.usfirst.frc.team4946.robot.commands.elbow;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MaintainElbowPos extends CommandGroup {

    public MaintainElbowPos() {
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
    	requires(Robot.elbowSubsystem);
    	requires(Robot.elevatorSubsystem);
    	requires(Robot.intakeSubsystem);
    	
    	if(Robot.intakeSubsystem.getHasCube() || Robot.elevatorSubsystem.getElevatorPos() 
    			>= 3.0 + RobotConstants.ELEVATOR_MINIMUM_HEIGHT) {
    		addSequential(new ElbowUp());
    	} else {
    		addSequential(new ElbowDown());
    	}
    }
}
