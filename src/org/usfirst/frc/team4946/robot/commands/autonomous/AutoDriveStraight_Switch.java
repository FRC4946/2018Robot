package org.usfirst.frc.team4946.robot.commands.autonomous;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.commands.drivetrain.auto.AutoDriveStraight;
import org.usfirst.frc.team4946.robot.commands.drivetrain.auto.TurnPID;
import org.usfirst.frc.team4946.robot.commands.elevator.SetElevator;
import org.usfirst.frc.team4946.robot.commands.intake.SetIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoDriveStraight_Switch extends CommandGroup {

    public AutoDriveStraight_Switch() {
        
    	addSequential(new AutoDriveStraight(137, 0.8), 2.5);
    	
    	if(Robot.startPos == 1) {
    		
    		addSequential(new TurnPID(90.0), 1.0);
    		
    	} else if (Robot.startPos == 3) {
    		
    		addSequential(new TurnPID(-90.0), 1.0);
    		
    	}
    	
    	addSequential(new SetElevator(RobotConstants.ELEVATOR_SWITCH_HEIGHT), 1.0);
    	addSequential(new SetIntake(-1.0), 1.0);
    }
    
    
}
