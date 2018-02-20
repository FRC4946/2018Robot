package org.usfirst.frc.team4946.robot.pathplanning.data.actions;

public class ArmAction extends Action<ArmAction.Options> {

	public static enum Options implements Action.ActionOptions {
		kArmDown, kArmUp
	}

	public ArmAction() {
		this(Options.kArmDown);
	}

	public ArmAction(Options options) {
		super(options);
	}

	@Override
	public String getName() {
		return "Arm";
	}

}
