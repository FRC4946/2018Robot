package org.usfirst.frc.team4946.robot.pathplanning.data.actions;

public class ArmAction extends Action<ArmAction.Options> {

	public static enum Options implements Action.ActionOptions {
		ArmDown, ArmUp
	}

	public ArmAction() {
		this(Options.ArmDown);
	}

	public ArmAction(Options options) {
		super(options);
		timeout = 0.5;
	}

	@Override
	public String getName() {
		return "Arm";
	}

	@Override
	public Options getDefaultOption() {
		return Options.ArmDown;
	}

}
