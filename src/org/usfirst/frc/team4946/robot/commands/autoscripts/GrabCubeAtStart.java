package org.usfirst.frc.team4946.robot.commands.autoscripts;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GrabCubeAtStart extends CommandGroup {

	int cube;
	double move;
	
    public GrabCubeAtStart(int cube) {
        this.cube = cube;
    	// Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParalle(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
        
        move = ((((double) cube/6.0)*153.5) - ((1/12)*153.3));
        //addSequential();
        
        
    }
}
