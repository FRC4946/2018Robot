package org.usfirst.frc.team4946.robot.pathplanning.data.actions;

public class ElevatorAction extends Action<ElevatorAction.Options> {

	public static enum Options implements Action.ActionOptions {
		kMoveToBottom, kMoveToSwitch, kMoveToScaleLow, kMoveToScaleHigh, kMoveToCustom
	}

	public ElevatorAction() {
		this(Options.kMoveToCustom);
	}

	public ElevatorAction(Options options) {
		this(options, 0);
	}

	public ElevatorAction(Options options, int timeout) {
		super(options);
		this.timeout = timeout;
	}

	@Override
	public String getName() {
		return "Elevator";
	}
}
