package org.usfirst.frc.team4946.robot.commands.intake;

import org.usfirst.frc.team4946.robot.commands.drivetrain.SetSpeed;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RunDiagonalIntake extends CommandGroup {

    public RunDiagonalIntake(double m_speed) {
    	
    	addParallel(new SetDiagonalIntake(m_speed/2), 0.3);
    	addSequential(new SetInternalIntake(m_speed/2), 0.3);
    	
    	addParallel(new SetBothIntakes(0.37), 0.5);
    	addSequential(new SetSpeed(0.5, 0.0), 0.5);
    	
    	addParallel(new SetBothIntakes(0.0));
    	addSequential(new SetSpeed(0.0, 0.0));
    }
}
