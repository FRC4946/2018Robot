package org.usfirst.frc.team4946.robot.pathplanning.data.actions;

public class IntakeAction extends Action<IntakeAction.Options> {

	public static enum Options implements Action.ActionOptions {
		IntakeOn, IntakeUntil
	}

	public IntakeAction() {
		this(Options.IntakeOn);
	}

	public IntakeAction(Options options) {
		super(options);
		data = 1;
		timeout = 1;
	}

	@Override
	public String getName() {
		return "Intake";
	}

	@Override
	public Options getDefaultOption() {
		return Options.IntakeOn;
	}

}
