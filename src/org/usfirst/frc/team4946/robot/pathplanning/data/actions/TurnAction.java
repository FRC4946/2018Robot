package org.usfirst.frc.team4946.robot.pathplanning.data.actions;

public class TurnAction extends Action<TurnAction.Options> {

	public static enum Options implements Action.ActionOptions {
		TurnOnSpot
	}

	public TurnAction() {
		this(Options.TurnOnSpot);
	}

	public TurnAction(Options options) {
		super(options);
		data = 90;
		timeout = 1;
	}

	@Override
	public String getName() {
		return "Turn";
	}

	@Override
	public Options getDefaultOption() {
		return Options.TurnOnSpot;
	}

}
