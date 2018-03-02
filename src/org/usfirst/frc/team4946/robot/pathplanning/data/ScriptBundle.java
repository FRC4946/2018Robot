package org.usfirst.frc.team4946.robot.pathplanning.data;

import java.util.ArrayList;

import org.usfirst.frc.team4946.robot.commands.autonomous.DelayedCommand;
import org.usfirst.frc.team4946.robot.commands.autonomous.Wait;
import org.usfirst.frc.team4946.robot.commands.drivetrain.auto.FollowPath;
import org.usfirst.frc.team4946.robot.commands.drivetrain.auto.TurnPID;
import org.usfirst.frc.team4946.robot.commands.elbow.SetElbowPos;
import org.usfirst.frc.team4946.robot.commands.elevator.MoveToHeight;
import org.usfirst.frc.team4946.robot.commands.elevator.preset.MoveToBottom;
import org.usfirst.frc.team4946.robot.commands.elevator.preset.MoveToScale;
import org.usfirst.frc.team4946.robot.commands.elevator.preset.MoveToSwitch;
import org.usfirst.frc.team4946.robot.commands.intake.IntakeUntilCube;
import org.usfirst.frc.team4946.robot.commands.intake.SetIntake;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.Action;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.Action.Behaviour;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.ArmAction;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.DelayAction;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.DriveAction;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.ElevatorAction;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.IntakeAction;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.OutputAction;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.TurnAction;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScriptBundle {
	public String name = "";
	public String notes = "";

	public ArrayList<Action<?>> LL = new ArrayList<>();
	public ArrayList<Action<?>> LR = new ArrayList<>();
	public ArrayList<Action<?>> RL = new ArrayList<>();
	public ArrayList<Action<?>> RR = new ArrayList<>();

	public CommandGroup getAuto(String data) {
		CommandGroup auto = new CommandGroup();

		ArrayList<Action<?>> script = null;
		switch (data.toLowerCase().substring(0, 2)) {
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
			System.out.println("Invalid game data");
			return null;
		}

		for (Action<?> a : script) {
			System.out.println("Adding: " + a.getName() + " - "
					+ (a.behaviour == Behaviour.kParallel ? "Parallel" : "Sequential"));

			Command c = null;

			// Delay
			if (a instanceof DelayAction) {
				c = new Wait(a.timeout);
			}

			// Drive
			else if (a instanceof DriveAction) {
				c = new FollowPath((DriveAction) a);
			}

			// Elevator
			else if (a instanceof ElevatorAction) {
				if (a.option == ElevatorAction.Option.ToBottom)
					c = new MoveToBottom();
				else if (a.option == ElevatorAction.Option.ToSwitch)
					c = new MoveToSwitch();
				else if (a.option == ElevatorAction.Option.ToScaleLow)
					c = new MoveToScale(true);
				else if (a.option == ElevatorAction.Option.ToScaleHigh)
					c = new MoveToScale(false);
				else if (a.option == ElevatorAction.Option.ToCustom)
					c = new MoveToHeight(a.data);
			}

			// Elbow
			else if (a instanceof ArmAction) {
				if (a.option == ArmAction.Option.ArmUp)
					c = new SetElbowPos(true, a.timeout);
				else
					c = new SetElbowPos(false, a.timeout);
			}

			// Intake
			else if (a instanceof IntakeAction) {
				if (a.option == IntakeAction.Option.IntakeOn)
					c = new SetIntake(a.data);
				else if (a.option == IntakeAction.Option.IntakeUntil)
					c = new IntakeUntilCube(a.data);
			}

			// Output
			else if (a instanceof OutputAction) {
				c = new SetIntake(-a.data);
			}

			// Turn PID
			else if (a instanceof TurnAction) {
				c = new TurnPID(a.data);
			}

			if (c == null)
				continue;

			// If it's negative, no timeout
			if (a.timeout < 0) {
				if (a.behaviour == Behaviour.kParallel)
					auto.addParallel(new DelayedCommand(a.delay, c));
				else
					auto.addSequential(new DelayedCommand(a.delay, c));

			} else {

				if (a.behaviour == Behaviour.kParallel)
					auto.addParallel(new DelayedCommand(a.delay, c, a.timeout));
				else
					auto.addSequential(new DelayedCommand(a.delay, c, a.timeout));
			}

		}

		return auto;
	}
}
