package org.usfirst.frc.team4946.robot.pathplanning.data.actions;

import java.util.ArrayList;

import org.usfirst.frc.team4946.robot.pathplanning.TrapezoidMotionProfile;
import org.usfirst.frc.team4946.robot.pathplanning.data.Segment;

/**
 * An {@link Action} describing driving and navigation
 * 
 * @author Matthew Reynolds
 *
 */
public class DriveAction extends Action<DriveAction.Option> {

	/**
	 * <li>{@link Option#FollowPath} follows a path generated by a
	 * {@link TrapezoidMotionProfile}
	 *
	 * @author Matthew Reynolds
	 * @see Action.ActionOption
	 */
	public static enum Option implements Action.ActionOption {
		FollowPath
	}

	public ArrayList<Segment> left;
	public ArrayList<Segment> right;

	/**
	 * Create a {@code DriveAction} with:
	 * <li>Default {@link Option} of {@link Option#FollowPath}
	 * <li>Default {@link Behaviour} of {@link Behaviour#kSequential}
	 */
	public DriveAction() {
		this(Option.FollowPath);
	}

	/**
	 * Create a {@code DriveAction} with:
	 * <li>The specified {@link Option}
	 * <li>Default {@link Behaviour} of {@link Behaviour#kSequential}
	 * 
	 * @param options
	 *            the desired {@code Option}
	 */
	public DriveAction(Option options) {
		super(options);
		left = new ArrayList<>();
		right = new ArrayList<>();
	}

	/**
	 * Add a {@link Segment} to the generated path to be uploaded
	 * 
	 * @param isL
	 *            {@code true} if this {@code Segment} should be appended to the
	 *            left wheel path
	 * @param seg
	 *            the {@code Segment} to append
	 */
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

	@Override
	public String getDataLabel() {
		return "Reverse";
	}

	@Override
	public Option getDefaultOption() {
		return Option.FollowPath;
	}
}
