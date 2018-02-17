package org.usfirst.frc.team4946.robot;

import edu.wpi.first.wpilibj.Preferences;

public class RobotConstants {
	public static final int PNEUMATIC_FIRING_COUNT = 5;

	public static final double TURNING_POINT = 0.0; // Dummy
	public static final int ENCODER_PPR = 128;
	public static final double WHEEL_DIA = 6.0;
	public static final double DRIVETRAIN_GEARBOX_REDUCTION_HIGH = 1 / 1.1; // dummy
	public static final double DRIVETRAIN_GEARBOX_REDUCTION_LOW = 1 / 2.5; // dummy
	public static final double DRIVETRAIN_MAX_ACCEL = 5.0; // dummy (inches per second squared)
	public static final double ROBOT_SAMPLE_TIME = 0.020; // (seconds)
	public static final double ROBOT_WIDTH = 38; // inches
	public static final double ROBOT_DRIVE_AUTO_SPEED = 0.2;
	// public static final double ENCODER_DISTANCE_PER_PULSE = WHEEL_DIA * Math.PI
	// / ENCODER_PPR * GEARBOX_REDUCTION;

	public static final double ELEVATOR_MAX_OUTPUT = 1.0;
	public static final double ELEVATOR_SCALING_VALUE = 67.1; // Dummy
	public static final double ELEVATOR_OFFSET_VALUE = 5.0; // Dummy

	public static final double ELEVATOR_MINIMUM_HEIGHT = 8.0; // Dummy
	public static final double ELEVATOR_CARRY_HEIGHT = 9.0; // Dummy
	public static final double ELEVATOR_BOTTOM_THRESHOLD = 10.0; // Dummy
	public static final double ELEVATOR_SWITCH_HEIGHT = 15.0; // Dummy
	public static final double ELEVATOR_SCALE_HEIGHT = 20.0; // Dummy
	public static final double ELEVATOR_MAXIMUM_HEIGHT = 24.0; // Dummy

	public static boolean elevatorSafetyEnabled = false;

	public static double driveP;
	public static double driveI;
	public static double driveD;
	public static double elevatorP;
	public static double elevatorI;
	public static double elevatorD;

	/**
	 * Load all of the preferences from the file saved on the roboRIO. These
	 * preferences are editable using the RobotPreferences widget on the
	 * SmartDashboard, and can be changed without having to recompile and reupload
	 * code to the robot.
	 * 
	 * @param prefs
	 *            the {@link Preferences} to read the date from
	 */
	public static void loadPrefs(Preferences prefs) {

		// Remember to put your default values here!
		driveP = prefs.getDouble("Drive P", 0.1);
		driveI = prefs.getDouble("Drive I", 0.0001);
		driveD = prefs.getDouble("Drive D", 0.0);
		elevatorP = prefs.getDouble("Elevator P", 0.1);
		elevatorI = prefs.getDouble("Elevator I", 0.0001);
		elevatorD = prefs.getDouble("Elevator D", 0.0);
	}

	/**
	 * Re-upload all of the robot preferences to the RobotPreferences widget on the
	 * SmartDashboard. This is important because if an entry is deleted on the
	 * SmartDashboard, it will not reappear unless the user can recreate it with the
	 * exact same key, or it is programmatically repopulated with a default value,
	 * as we are doing here.
	 * 
	 * @param prefs
	 *            the {@link Preferences} to write the values to
	 */
	public static void repopulatePrefs(Preferences prefs) {
		prefs.putDouble("Drive P", driveP);
		prefs.putDouble("Drive I", driveI);
		prefs.putDouble("Drive D", driveD);
		prefs.putDouble("Elevator P", elevatorP);
		prefs.putDouble("Elevator I", elevatorI);
		prefs.putDouble("Elevator D", elevatorD);
	}

	/**
	 * Updates the preferences.
	 * 
	 * @param prefs
	 *            the {@link Preferences} to update the values of
	 */
	public static void updatePrefs(Preferences prefs) {
		loadPrefs(prefs);
		repopulatePrefs(prefs);
	}
}
