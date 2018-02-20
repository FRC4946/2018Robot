package org.usfirst.frc.team4946.robot.commands.autonomous;

import org.usfirst.frc.team4946.robot.commands.Wait;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DelayedCommand extends CommandGroup {

	public DelayedCommand(double delay, Command command, double timeout) {

		System.out.println("Delay: " + delay);
		addSequential(new Wait(delay, false));
		addSequential(command, timeout);
	}

	public DelayedCommand(double delay, Command command) {
		
		System.out.println("Delay: " + delay);
		addSequential(new Wait(delay, false));
		addSequential(command);
	}
}
