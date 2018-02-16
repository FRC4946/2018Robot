package org.usfirst.frc.team4946.robot.commands;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.commands.elevator.ElevatorSetHeight;
import org.usfirst.frc.team4946.robot.commands.intake.RunIntake;
import org.usfirst.frc.team4946.robot.commands.output.RunOutput;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CubeAndLiftIntake extends CommandGroup {

    public CubeAndLiftIntake(double m_speed) {
      
    	m_speed *= 0.8;
    	
    	while(!Robot.upperOutputSubsystem.getHasCube()) {
    		
    		addParallel(new RunIntake(m_speed));
    		addParallel(new RunOutput(m_speed));
    	}
    	
    	addParallel(new RunIntake(0.0));
    	addParallel(new RunOutput(0.0));
    	addSequential(new ElevatorSetHeight(2.0, 0.4));
    	
    }
}
