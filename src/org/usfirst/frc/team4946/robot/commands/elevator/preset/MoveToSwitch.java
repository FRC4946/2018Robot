package org.usfirst.frc.team4946.robot.commands.elevator.preset;

import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.commands.elevator.MoveToHeight;
import org.usfirst.frc.team4946.robot.commands.elevator.SetElevatorGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MoveToSwitch extends CommandGroup {

	public MoveToSwitch() {
		addParallel(new SetElevatorGear(false));
		addSequential(new MoveToHeight(RobotConstants.ELEVATOR_SWITCH_HEIGHT));
	}
}
