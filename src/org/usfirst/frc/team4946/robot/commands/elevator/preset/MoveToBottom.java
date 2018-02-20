package org.usfirst.frc.team4946.robot.commands.elevator.preset;

import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.commands.elevator.MoveToHeight;
import org.usfirst.frc.team4946.robot.commands.elevator.SetElevatorGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MoveToBottom extends CommandGroup {

	public MoveToBottom() {
		addParallel(new SetElevatorGear(false));
		addSequential(new MoveToHeight(RobotConstants.ELEVATOR_MINIMUM_HEIGHT));
	}
}
