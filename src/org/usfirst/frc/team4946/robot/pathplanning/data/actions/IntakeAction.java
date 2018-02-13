package org.usfirst.frc.team4946.robot.pathplanning.data.actions;

public class IntakeAction extends Action<IntakeAction.Options> {

	public static enum Options implements Action.ActionOptions {
		kIntakeOn, kIntakeUntil
	}

	public IntakeAction() {
		this(Options.kIntakeOn);
	}

	public IntakeAction(Options options) {
		super(options);
	}

	@Override
	public String getName() {
		return "Intake";
	}

}
