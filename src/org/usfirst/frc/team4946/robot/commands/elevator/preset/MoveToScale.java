package org.usfirst.frc.team4946.robot.commands.elevator.preset;

import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.commands.elevator.MoveToHeight;
import org.usfirst.frc.team4946.robot.commands.elevator.SetElevatorGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MoveToScale extends CommandGroup {

	public MoveToScale(boolean isLow) {
		addParallel(new SetElevatorGear(false));

		if (isLow)
			addSequential(new MoveToHeight(RobotConstants.ELEVATOR_SCALE_LOWHEIGHT));
		else
			addSequential(new MoveToHeight(RobotConstants.ELEVATOR_SCALE_HIGHHEIGHT));
	}
}
