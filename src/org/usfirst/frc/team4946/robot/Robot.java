/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4946.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import org.usfirst.frc.team4946.robot.pathplanning.FileIO;
import org.usfirst.frc.team4946.robot.pathplanning.data.ScriptBundle;
import org.usfirst.frc.team4946.robot.subsystems.DriveTrainSubsystem;
import org.usfirst.frc.team4946.robot.subsystems.DriveTrainTransmissionSubsystem;
import org.usfirst.frc.team4946.robot.subsystems.ElbowSubsystem;
import org.usfirst.frc.team4946.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team4946.robot.subsystems.ElevatorTransmissionSubsystem;
import org.usfirst.frc.team4946.robot.subsystems.ExternalIntakeSubsystem;
import org.usfirst.frc.team4946.robot.subsystems.InternalIntakeSubsystem;
import org.xml.sax.SAXException;

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
	public static ElbowSubsystem elbowSubsystem;
	public static ElevatorSubsystem elevatorSubsystem;
	public static ElevatorTransmissionSubsystem elevatorTransmissionSubsystem;
	public static ExternalIntakeSubsystem externalIntakeSubsystem;
	public static InternalIntakeSubsystem internalIntakeSubsystem;

	public static OI m_oi;

	public static boolean isAutonomous = false;

	private CommandGroup m_autoCommand = new CommandGroup();
	private ScriptBundle m_script = new ScriptBundle();
	private Timer m_prefsUpdateTimer = new Timer();
	private Preferences m_robotPrefs;
	// private SendableGroupedData m_autoDashboard;
	private int m_count = 0;

	private PrintWriter m_csvFile;
	private long m_enableTime;

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
		elbowSubsystem = new ElbowSubsystem();
		elevatorSubsystem = new ElevatorSubsystem();
		elevatorTransmissionSubsystem = new ElevatorTransmissionSubsystem();
		externalIntakeSubsystem = new ExternalIntakeSubsystem();
		internalIntakeSubsystem = new InternalIntakeSubsystem();

		// This MUST occur AFTER the subsystems and instantiated
		m_oi = new OI();

		// m_autoDashboard = new SendableGroupedData("Auto");
		// SmartDashboard.putData(m_autoDashboard);

		// USB camera
		// UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
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

		if (isAutonomous)
			m_csvFile.close();

		m_prefsUpdateTimer.reset();
		m_prefsUpdateTimer.start();
		isAutonomous = false;

		loadShuffleboard();
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
			// m_autoDashboard.putString("Script", "No script!");
			// m_autoDashboard.putString("Notes", "");
			SmartDashboard.putString("Script", "No script!");
			SmartDashboard.putString("Notes", "");
		} else {
			try {

				m_script = FileIO.loadScript(file);
				// m_autoDashboard.putString("Script", m_script.name);
				// m_autoDashboard.putString("Notes", m_script.notes);
				SmartDashboard.putString("Script", m_script.name);
				SmartDashboard.putString("Notes", m_script.notes);
			} catch (ParserConfigurationException | SAXException | IOException e) {
				m_script = null;
				// m_autoDashboard.putString("Script", "ERROR loading " + file.getName());
				// m_autoDashboard.putString("Notes", "");
				SmartDashboard.putString("Script", "ERROR loading " + file.getName());
				SmartDashboard.putString("Notes", "");
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
		//Next few lines are temp, to get working directory
		java.lang.Runtime rt = java.lang.Runtime.getRuntime();
		try {
			java.lang.Process p = rt.exec("pwd");
			System.out.println("Working Directory: " + p.exitValue());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		try {
			m_csvFile = new PrintWriter(new File(dateFormat.format(date) + ".csv"));
			System.out.println("CSV succesfully written to:" + new File(dateFormat.format(date) + ".csv").getAbsolutePath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			m_csvFile = null;
		}

		m_enableTime = System.currentTimeMillis();

		if (m_csvFile != null) {
			StringBuilder sb = new StringBuilder();
			sb.append("Time");
			sb.append(',');
			sb.append("Battery Voltage");
			sb.append(',');
			sb.append("Gyro Angle");
			sb.append(',');
			sb.append("Gyro SP");
			sb.append(',');
			sb.append("Gyro Out");
			sb.append(',');
			sb.append("Gyro Err");
			sb.append(',');
			sb.append("Elevator Height");
			sb.append(',');
			sb.append("Elevator SP");
			sb.append(',');
			sb.append("Elevator Out");
			sb.append(',');
			sb.append("Elevator Err");
			sb.append('\n');
			m_csvFile.write(sb.toString());
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
		elevatorSubsystem.disablePID();
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
		// SmartDashboard.putNumber("Counter", m_count);

		// Drive Train
		SmartDashboard.putNumber("Gyro Angle", driveTrainSubsystem.getGyroAngle() % 360);
		SmartDashboard.putNumber("Gyro Setpoint", driveTrainSubsystem.getGyroPIDSetpoint());
		SmartDashboard.putNumber("Gyro Output", driveTrainSubsystem.getGyroPIDOutput());
		SmartDashboard.putNumber("Gyro Error", driveTrainSubsystem.getGyroPIDError());

		SmartDashboard.putNumber("Left Enc", driveTrainSubsystem.getLeftEncDist());
		SmartDashboard.putNumber("Right Enc", driveTrainSubsystem.getRightEncDist());

		// Elevator
		SmartDashboard.putNumber("Elevator Position", elevatorSubsystem.getHeight());
		SmartDashboard.putNumber("Elevator Setpoint", elevatorSubsystem.getSetpoint());
		SmartDashboard.putNumber("Elevator Output", elevatorSubsystem.getSpeed());
		SmartDashboard.putNumber("Elevator Error", elevatorSubsystem.getError());

		// Intake
		SmartDashboard.putBoolean("Has Cube", internalIntakeSubsystem.getHasCube());

		if (isAutonomous && m_csvFile != null) {
			StringBuilder sb = new StringBuilder();
			sb.append(System.currentTimeMillis() - m_enableTime);
			sb.append(',');
			sb.append(RobotController.getBatteryVoltage());
			sb.append(',');
			sb.append(driveTrainSubsystem.getGyroAngle() % 360);
			sb.append(',');
			sb.append(driveTrainSubsystem.getGyroPIDSetpoint());
			sb.append(',');
			sb.append(driveTrainSubsystem.getGyroPIDOutput());
			sb.append(',');
			sb.append(driveTrainSubsystem.getGyroPIDError());
			sb.append(',');
			sb.append(elevatorSubsystem.getHeight());
			sb.append(',');
			sb.append(elevatorSubsystem.getSetpoint());
			sb.append(',');
			sb.append(elevatorSubsystem.getSpeed());
			sb.append(',');
			sb.append(elevatorSubsystem.getError());
			sb.append('\n');
			m_csvFile.write(sb.toString());
		}
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
