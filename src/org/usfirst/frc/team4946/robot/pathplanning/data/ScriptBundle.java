package org.usfirst.frc.team4946.robot.pathplanning.data;

import java.util.ArrayList;

import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.commands.CubeAndLiftIntake;
import org.usfirst.frc.team4946.robot.commands.elevator.ElevatorSetHeight;
import org.usfirst.frc.team4946.robot.commands.intake.RunIntake;
import org.usfirst.frc.team4946.robot.commands.output.RunOutput;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.Action;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.Action.Behaviour;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.DelayAction;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.DriveAction;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.ElevatorAction;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.IntakeAction;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.OutputAction;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScriptBundle {
	public String name = "";

	public ArrayList<Action<?>> LL = new ArrayList<>();
	public ArrayList<Action<?>> LR = new ArrayList<>();
	public ArrayList<Action<?>> RL = new ArrayList<>();
	public ArrayList<Action<?>> RR = new ArrayList<>();

	public CommandGroup getAuto(String data) {
		CommandGroup auto = new CommandGroup();

		ArrayList<Action<?>> script = null;
		switch (data.toLowerCase().substring(0, 1)) {
		case "ll":
			script = LL;
			break;
		case "lr":
			script = LR;
			break;
		case "rl":
			script = RL;
			break;
		case "rr":
			script = RR;
			break;
		default:
			return null;
		}

		for (Action<?> a : script) {
			Command c = null;

			// Delay
			if (a instanceof DelayAction) {
				// c = new Wait();
				// TODO: Implement me
			}

			else if (a instanceof DriveAction) {
				// TODO: Implement me
			}

			// Elevator
			else if (a instanceof ElevatorAction) {
				double height = RobotConstants.ELEVATOR_MINIMUM_HEIGHT;

				if (a.options == ElevatorAction.Options.kMoveToBottom)
					height = RobotConstants.ELEVATOR_MINIMUM_HEIGHT;
				else if (a.options == ElevatorAction.Options.kMoveToSwitch)
					height = RobotConstants.ELEVATOR_SWITCH_HEIGHT;
				else if (a.options == ElevatorAction.Options.kMoveToScale)
					height = RobotConstants.ELEVATOR_SCALE_HEIGHT;
				else if (a.options == ElevatorAction.Options.kMoveToCustom)
					height = a.data;

				c = new ElevatorSetHeight(height, 0.5);
			}

			// Intake
			else if (a instanceof IntakeAction) {
				if (a.options == IntakeAction.Options.kIntakeOn)
					c = new RunIntake(1.0);
				else if (a.options == IntakeAction.Options.kIntakeUntil)
					c = new CubeAndLiftIntake(0.5);
			}

			// Output
			else if (a instanceof OutputAction) {
				c = new RunOutput(-1.0);
			}

			if (a.timeout < 0.0001)
				a.timeout = 15;

			if (a.behaviour == Behaviour.kParallel)
				auto.addParallel(c, a.timeout);
			else
				auto.addSequential(c, a.timeout);
		}

		return auto;
	}
}
