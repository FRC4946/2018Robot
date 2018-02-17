package org.usfirst.frc.team4946.robot.commands.intake;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.commands.elevator.MoveToHeight;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CubeAndLiftIntake extends CommandGroup {
	
	/**
	 * Sucks a power-cube in and then lifts it by one-inch for ease of driving. The
	 * external intake is also flipped to its up position - perpendicular to the
	 * ground.
	 */
    public CubeAndLiftIntake(double m_speed) {
      
    	m_speed *= 0.8;
    	
    	while(!Robot.internalIntakeSubsystem.getHasCube()) {
    		addParallel(new SetIntake(m_speed));
    	}
    	
    	addParallel(new SetIntake(0.0));
    	addSequential(new MoveToHeight(2.0, 0.4));
    	
    }

}
