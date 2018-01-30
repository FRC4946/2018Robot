package org.usfirst.frc.team4946.robot.pathplanning.data.actions;

import org.usfirst.frc.team4946.robot.pathplanning.data.actions.Action.ActionOptions;

public abstract class Action<T extends Enum<T> & ActionOptions> {

	public interface ActionOptions {
	}

	public static enum Behaviour {
		kSequential, kParallel
	}

	protected Action(T options) {
		this.options = options;
	}

	public Enum<T> options;
	public Behaviour behaviour = Behaviour.kSequential;
	public int timeout = -1;
	public int data = -1;

	public abstract String getName();
}
