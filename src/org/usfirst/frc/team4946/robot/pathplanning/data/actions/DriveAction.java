package org.usfirst.frc.team4946.robot.pathplanning.data.actions;

import java.util.ArrayList;

public class DriveAction extends Action<DriveAction.Options> {

	public static enum Options implements Action.ActionOptions {
		kDrive
	}

	public ArrayList<Segment> left;
	public ArrayList<Segment> right;

	public DriveAction() {
		this(Options.kDrive);
	}

	public DriveAction(Options options) {
		super(options);
		left = new ArrayList<>();
		right = new ArrayList<>();
	}

	public void addSegment(boolean isL, Segment seg) {
		if (isL)
			left.add(seg);
		else
			right.add(seg);
	}

	@Override
	public String getName() {
		return "Drive";
	}

	public static class Segment {
		public double pos;
		public double vel;
		public double accel;
		public double jerk;
		public double heading;
		public double dt;
		public double x;
		public double y;

		public Segment() {
		}

		public Segment(double pos, double x, double y, double heading) {
			this.pos = pos;
			this.x = x;
			this.y = y;
			this.heading = heading;
		}
	}
}
