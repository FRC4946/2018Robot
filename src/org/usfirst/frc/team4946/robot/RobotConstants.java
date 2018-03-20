package org.usfirst.frc.team4946.robot;

import edu.wpi.first.wpilibj.Preferences;

public class RobotConstants {
	public static final int PNEUMATIC_FIRING_COUNT = 20;
	public static final int ENCODER_PPR = 128;
	public static final double WHEEL_DIA = 6.0;
	public static final double DRIVETRAIN_GEARBOX_REDUCTION = 1;
	public static final double DISTANCE_PER_PULSE = WHEEL_DIA * Math.PI / ENCODER_PPR * DRIVETRAIN_GEARBOX_REDUCTION;

	public static final double ELEVATOR_SCALING_VALUE = 127.59;
	public static final double ELEVATOR_OFFSET_VALUE = 0.7349;

	public static final double ELEVATOR_MINIMUM_HEIGHT = 6;
	public static final double ELEVATOR_INTERFERE_MIN = 14;
	public static final double ELEVATOR_INTERFERE_MAX = 32;
	public static final double ELEVATOR_SWITCH_HEIGHT = 40.0;
	public static final double ELEVATOR_SCALE_LOWHEIGHT = 60.0;
	public static final double ELEVATOR_SCALE_HIGHHEIGHT = 88.0;
	public static final double ELEVATOR_RUNG_HEIGHT = 78.0;
	public static final double ELEVATOR_MAXIMUM_HEIGHT = 96;

	public static double driveP;
	public static double driveI;
	public static double driveD;
	public static double driveKVel;
	public static double driveKAccel;

	public static class PIDTunings {
		public String name;
		public double kP;
		public double kI;
		public double kD;
		public double kMinOutput;
		public double kMaxOutput;
		public double kAbsTolerance;

		public PIDTunings(String name) {
			this(name, 0, 0, 0, 0, 0, 0);
		}

		public PIDTunings(String name, double defaultP, double defaultI, double defaultD, double defaultMin,
				double defaultMax, double defaultTol) {
			this.name = name;
			kP = defaultP;
			kI = defaultI;
			kD = defaultD;
			kMinOutput = defaultMin;
			kMaxOutput = defaultMax;
			kAbsTolerance = defaultTol;
		}

		public void loadPrefs(Preferences prefs) {
			kP = prefs.getDouble(name + " P", kP);
			kI = prefs.getDouble(name + " I", kI);
			kD = prefs.getDouble(name + " D", kD);
			kMinOutput = prefs.getDouble(name + " Min Output", kMinOutput);
			kMaxOutput = prefs.getDouble(name + " Max Output", kMaxOutput);
			kAbsTolerance = prefs.getDouble(name + " Abs Tolerance", kAbsTolerance);
		}

		public void repopulatePrefs(Preferences prefs) {
			prefs.putDouble(name + " P", kP);
			prefs.putDouble(name + " I", kI);
			prefs.putDouble(name + " D", kD);
			prefs.putDouble(name + " Min Output", kMinOutput);
			prefs.putDouble(name + " Max Output", kMaxOutput);
			prefs.putDouble(name + " Abs Tolerance", kAbsTolerance);
		}
	}

	public static PIDTunings kElevator = new PIDTunings("Elevator", 0.1, 0.0001, 0.0, -0.2, 0.7, 3.5);
	public static PIDTunings kTurn = new PIDTunings("Turn", 0.1, 0.0001, 0.0, -0.2, 0.7, 3.5);
	public static PIDTunings kPathTurn = new PIDTunings("Path Turn", 0.0, 0.0, 0.0, 0.5, 0.5, 0);

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
		driveKVel = prefs.getDouble("Drive KVel", 0.0);
		driveKAccel = prefs.getDouble("Drive KAccel", 0.0);
		// pathTurnP = prefs.getDouble("Path Turn P", 0.0);
		// pathTurnI = prefs.getDouble("Path Turn I", 0.0);
		// pathTurnD = prefs.getDouble("Path Turn D", 0.0);
		// turnP = prefs.getDouble("Turn P", 0.1);
		// turnI = prefs.getDouble("Turn I", 0.0001);
		// turnD = prefs.getDouble("Turn D", 0.0);
		// elevator.kP = prefs.getDouble("Elevator P", 0.1);
		// elevator.kI = prefs.getDouble("Elevator I", 0.0001);
		// elevator.kD = prefs.getDouble("Elevator D", 0.0);
		// elevator.kMinOutput = prefs.getDouble("Elevator Min Output", -0.2);
		// elevator.kMaxOutput = prefs.getDouble("Elevator Max Output", 0.7);
		// elevator.kAbsTolerance = prefs.getDouble("Elevator Abs Tolerance", 3.5);

		kPathTurn.loadPrefs(prefs);
		kTurn.loadPrefs(prefs);
		kElevator.loadPrefs(prefs);
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
		prefs.putDouble("Drive KVel", driveKVel);
		prefs.putDouble("Drive KAccel", driveKAccel);
		// prefs.putDouble("Path Turn P", pathTurnP);
		// prefs.putDouble("Path Turn I", pathTurnI);
		// prefs.putDouble("Path Turn D", pathTurnD);
		// prefs.putDouble("Turn P", turnP);
		// prefs.putDouble("Turn I", turnI);
		// prefs.putDouble("Turn D", turnD);
		// prefs.putDouble("Elevator P", elevator.kP);
		// prefs.putDouble("Elevator I", elevator.kI);
		// prefs.putDouble("Elevator D", elevator.kD);

		kPathTurn.repopulatePrefs(prefs);
		kTurn.repopulatePrefs(prefs);
		kElevator.repopulatePrefs(prefs);
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
