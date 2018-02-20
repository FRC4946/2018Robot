package org.usfirst.frc.team4946.robot.commands.elevator.preset;

import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.commands.elevator.MoveToHeight;
import org.usfirst.frc.team4946.robot.commands.elevator.SetElevatorGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MoveToRung extends CommandGroup {

	public MoveToRung() {
		addParallel(new SetElevatorGear(false));
		addSequential(new MoveToHeight(RobotConstants.ELEVATOR_RUNG_HEIGHT));
	}
}
