package org.usfirst.frc.team4946.robot.pathplanning.data.actions;

public class OutputAction extends Action<OutputAction.Options> {

	public static enum Options implements Action.ActionOptions {
		kOutput
	}

	public OutputAction() {
		this(Options.kOutput);
	}

	public OutputAction(Options options) {
		super(options);
	}

	@Override
	public String getName() {
		return "Output";
	}

}
