/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4946.robot;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.usfirst.frc.team4946.robot.commands.arm.SetClamp;
import org.usfirst.frc.team4946.robot.pathplanning.FileIO;
import org.usfirst.frc.team4946.robot.pathplanning.data.ScriptBundle;
import org.usfirst.frc.team4946.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team4946.robot.subsystems.DriveTrainSubsystem;
import org.usfirst.frc.team4946.robot.subsystems.DriveTrainTransmissionSubsystem;
import org.usfirst.frc.team4946.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team4946.robot.subsystems.ElevatorTransmissionSubsystem;
import org.usfirst.frc.team4946.robot.subsystems.IntakeSubsystem;
import org.usfirst.frc.team4946.robot.util.CSVLogger;
import org.usfirst.frc.team4946.robot.util.SendableSubtable;
import org.xml.sax.SAXException;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {

	public static DriveTrainSubsystem driveTrainSubsystem;
	public static DriveTrainTransmissionSubsystem driveTransmissionSubsystem;
	public static ArmSubsystem armSubsystem;
	public static ElevatorSubsystem elevatorSubsystem;
	public static ElevatorTransmissionSubsystem elevatorTransmissionSubsystem;
	public static IntakeSubsystem intakeSubsystem;
	// public static RampSubsystem rampSubsystem;

	public static OI m_oi;

	public static boolean isAutonomous = false;

	private CommandGroup m_autoCommand = new CommandGroup();
	private ScriptBundle m_script = new ScriptBundle();
	private Timer m_prefsUpdateTimer = new Timer();
	private Preferences m_robotPrefs;
	private SendableSubtable m_mainTable;
	private SendableSubtable m_autoTable;
	private SendableSubtable m_driveTable;
	private SendableSubtable m_elevatorTable;
	private SendableSubtable m_armsTable;
	private SendableSubtable m_intakeTable;
	public static CSVLogger dataLogger;
	private int m_count = 0;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		// Load all of the robot preferences from the NetworkTables,
		// and then repopulate them to ensure they are visible on the
		// SmartDashboard
		m_robotPrefs = Preferences.getInstance();
		RobotConstants.updatePrefs(m_robotPrefs);

		driveTrainSubsystem = new DriveTrainSubsystem();
		driveTransmissionSubsystem = new DriveTrainTransmissionSubsystem();
		armSubsystem = new ArmSubsystem();
		elevatorSubsystem = new ElevatorSubsystem();
		elevatorTransmissionSubsystem = new ElevatorTransmissionSubsystem();
		intakeSubsystem = new IntakeSubsystem();
		// rampSubsystem = new RampSubsystem();

		// This MUST occur AFTER the subsystems and instantiated
		m_oi = new OI();

		m_mainTable = new SendableSubtable("Robot");
		m_autoTable = new SendableSubtable("Auto");
		m_driveTable = new SendableSubtable("Drive");
		m_elevatorTable = new SendableSubtable("Elevator");
		m_armsTable = new SendableSubtable("Arms");
		m_intakeTable = new SendableSubtable("Intake");
		SmartDashboard.putData(m_mainTable);
		SmartDashboard.putData(m_autoTable);
		SmartDashboard.putData(m_driveTable);
		SmartDashboard.putData(m_elevatorTable);
		SmartDashboard.putData(m_armsTable);
		SmartDashboard.putData(m_intakeTable);

		dataLogger = new CSVLogger();
		dataLogger.addTable(m_mainTable);
		dataLogger.addTable(m_autoTable);
		dataLogger.addTable(m_driveTable);
		dataLogger.addTable(m_elevatorTable);
		dataLogger.addTable(m_armsTable);
		dataLogger.addTable(m_intakeTable);

		// USB camera
		CameraServer.getInstance().startAutomaticCapture();
		// camera.setResolution(640, 480);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {

		// Turn off the motors and engage the brake when we enter disabled
		elevatorSubsystem.disablePID();
		// rampSubsystem.deployRamp(false);

		m_prefsUpdateTimer.reset();
		m_prefsUpdateTimer.start();
		isAutonomous = false;

		loadShuffleboard();

		new SetClamp(true).start();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		updateSmartDashboard();

		// Every 2 seconds, update the robot preferences and the loaded xml file
		if (m_prefsUpdateTimer.hasPeriodPassed(2)) {
			loadShuffleboard();
		}

		m_count = (m_count + 1) % 1000;
	}

	private void loadShuffleboard() {
		RobotConstants.updatePrefs(m_robotPrefs);

		File file = FileIO.lastFileModified("/home/lvuser/AutoPathPlanner");
		if (file == null) {
			m_autoTable.putString("Script", "No script!");
			m_autoTable.putString("Notes", "");
			// SmartDashboard.putString("Script", "No script!");
			// SmartDashboard.putString("Notes", "");
		} else {
			try {

				m_script = FileIO.loadScript(file);
				m_autoTable.putString("Script", m_script.name);
				m_autoTable.putString("Notes", m_script.notes);
				// SmartDashboard.putString("Script", m_script.name);
				// SmartDashboard.putString("Notes", m_script.notes);
			} catch (ParserConfigurationException | SAXException | IOException e) {
				m_script = null;
				m_autoTable.putString("Script", "ERROR loading " + file.getName());
				m_autoTable.putString("Notes", "");
				// SmartDashboard.putString("Script", "ERROR loading " + file.getName());
				// SmartDashboard.putString("Notes", "");
				e.printStackTrace();
			}
		}
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString code to get the
	 * auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons to
	 * the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		isAutonomous = true;

		driveTrainSubsystem.resetEncoders();
		RobotConstants.updatePrefs(m_robotPrefs);
		driveTrainSubsystem.updatePIDTunings();
		elevatorSubsystem.updatePIDTunings();
		elevatorSubsystem.disablePID();
		driveTransmissionSubsystem.set(true);

		String data = DriverStation.getInstance().getGameSpecificMessage();

		if (m_script == null)
			return;

		// Load the auto
		m_autoCommand = m_script.getAuto(data);
		if (m_autoCommand != null) {

			System.out.println("Starting auto");
			m_autoCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		updateSmartDashboard();
	}

	@Override
	public void teleopInit() {

		if (m_autoCommand != null)
			m_autoCommand.cancel();

		isAutonomous = false;

		driveTrainSubsystem.resetEncoders();
		RobotConstants.updatePrefs(m_robotPrefs);
		driveTrainSubsystem.updatePIDTunings();
		elevatorSubsystem.updatePIDTunings();
		// elevatorSubsystem.disablePID();
		// elevatorSubsystem.setSetpoint(elevatorSubsystem.getHeight());
		elevatorTransmissionSubsystem.set(false);
		driveTransmissionSubsystem.set(true);
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		updateSmartDashboard();

	}

	public void updateSmartDashboard() {
		SmartDashboard.putNumber("Counter", m_count);

		m_mainTable.putDouble("Battery Voltage", RobotController.getBatteryVoltage());

		// Drive Train
		m_driveTable.putDouble("Gyro Angle", driveTrainSubsystem.getGyroAngle() % 360);
		m_driveTable.putDouble("Gyro Setpoint", driveTrainSubsystem.getGyroPIDSetpoint());
		m_driveTable.putDouble("Gyro Output", driveTrainSubsystem.getGyroPIDOutput());
		m_driveTable.putDouble("Gyro Error", driveTrainSubsystem.getGyroPIDError());
		m_driveTable.putDouble("Left Enc", driveTrainSubsystem.getLeftEncDist());
		m_driveTable.putDouble("Right Enc", driveTrainSubsystem.getRightEncDist());
		m_driveTable.putDouble("Left Voltage %", driveTrainSubsystem.getSpeedLeft());
		m_driveTable.putDouble("Right Voltage %", driveTrainSubsystem.getSpeedRight());

		// Elevator
		m_elevatorTable.putDouble("Elevator Position", elevatorSubsystem.getHeight());
		m_elevatorTable.putDouble("Elevator Setpoint", elevatorSubsystem.getSetpoint());
		m_elevatorTable.putDouble("Elevator Output", elevatorSubsystem.getSpeed());
		m_elevatorTable.putDouble("Elevator Error", elevatorSubsystem.getError());

		// Arms
		m_armsTable.putBoolean("Clamp Engaged", armSubsystem.getClampIsEngaged());
		m_armsTable.putBoolean("Elbow Up", armSubsystem.getElbowIsUp());

		// Intake
		m_intakeTable.putBoolean("Has Cube", intakeSubsystem.getHasCube());

	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {

	}

	public boolean isAuto() {
		return isAutonomous();
	}

	public boolean isTeleop() {
		return isOperatorControl();
	}
}
