package org.usfirst.frc.team4946.robot;

import org.usfirst.frc.team4946.robot.util.FunctionEvaluator;

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
	public static String elevatorPFunc;
	public static String elevatorIFunc;
	public static String elevatorDFunc;

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

	public static class DynamicPIDTunings {
		public String name;
		public FunctionEvaluator kP;
		public FunctionEvaluator kI;
		public FunctionEvaluator kD;
		public FunctionEvaluator kMinOutput;
		public FunctionEvaluator kMaxOutput;
		public FunctionEvaluator kAbsTolerance;

		public DynamicPIDTunings(String name) {
			this(name, "0", "0", "0", "0", "0", "0");
		}

		public DynamicPIDTunings(String name, String defaultP, String defaultI, String defaultD, String defaultMin,
				String defaultMax, String defaultTol) {
			this.name = name;
			kP = new FunctionEvaluator(defaultP);
			kI = new FunctionEvaluator(defaultI);
			kD = new FunctionEvaluator(defaultD);
			kMinOutput = new FunctionEvaluator(defaultMin);
			kMaxOutput = new FunctionEvaluator(defaultMax);
			kAbsTolerance = new FunctionEvaluator(defaultTol);
		}

		public void loadPrefs(Preferences prefs) {
			kP.setFormula(prefs.getString(name + " P", kP.getFormula()));
			kI.setFormula(prefs.getString(name + " I", kI.getFormula()));
			kD.setFormula(prefs.getString(name + " D", kD.getFormula()));
			kMinOutput.setFormula(prefs.getString(name + " Min Output", kMinOutput.getFormula()));
			kMaxOutput.setFormula(prefs.getString(name + " Max Output", kMaxOutput.getFormula()));
			kAbsTolerance.setFormula(prefs.getString(name + " Abs Tolerance", kAbsTolerance.getFormula()));
		}

		public void repopulatePrefs(Preferences prefs) {
			prefs.putString(name + " P", kP.getFormula());
			prefs.putString(name + " I", kI.getFormula());
			prefs.putString(name + " D", kD.getFormula());
			prefs.putString(name + " Min Output", kMinOutput.getFormula());
			prefs.putString(name + " Max Output", kMaxOutput.getFormula());
			prefs.putString(name + " Abs Tolerance", kAbsTolerance.getFormula());
		}
	}

	public static DynamicPIDTunings kElevator = new DynamicPIDTunings("Elevator", "0", "0", "0", "-0.2", "0.7", "3.5");
	public static PIDTunings kTurn = new PIDTunings("Turn", 0.008, 0.000003, 0.0, -0.4, 0.4, 1.0);
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
		driveP = prefs.getDouble("Drive P", 0.45);
		driveI = prefs.getDouble("Drive I", 0.0);
		driveD = prefs.getDouble("Drive D", 0.0);
		driveKVel = prefs.getDouble("Drive KVel", 1.0 / 60.0);
		driveKAccel = prefs.getDouble("Drive KAccel", 1.0 / 100.0);

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
