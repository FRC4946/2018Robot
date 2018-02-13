package org.usfirst.frc.team4946.robot.pathplanning.data.actions;

public class DelayAction extends Action<DelayAction.Options> {

	// TODO: Add security so that you cannot have a 0s delay!

	public static enum Options implements Action.ActionOptions {
		kWait
	}

	public DelayAction() {
		this(Options.kWait);
	}

	public DelayAction(Options options) {
		super(options);
		behaviour = Behaviour.kSequential;
	}

	@Override
	public String getName() {
		return "Delay";
	}

}
