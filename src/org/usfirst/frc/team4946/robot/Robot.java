/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4946.robot;

import org.usfirst.frc.team4946.robot.pathplanning.data.ScriptBundle;
import org.usfirst.frc.team4946.robot.subsystems.DriveTrain;
import org.usfirst.frc.team4946.robot.subsystems.ElbowSubsystem;
import org.usfirst.frc.team4946.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team4946.robot.subsystems.ElevatorTransmission;
import org.usfirst.frc.team4946.robot.subsystems.ExternalIntake;
import org.usfirst.frc.team4946.robot.subsystems.Transmission;
import org.usfirst.frc.team4946.robot.subsystems.UpperOutput;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
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

	public static DriveTrain driveTrainSubsystem;
	public static ElbowSubsystem elbowSubsystem;
	public static ElevatorSubsystem elevatorSubsystem;
	public static ElevatorTransmission elevatorTransmissionSubsystem;
	public static ExternalIntake externalIntakeSubsystem;
	public static UpperOutput upperOutputSubsystem;
	public static Transmission transmissionSubsystem;

	public static OI m_oi;

	private CommandGroup m_autoCommand = new CommandGroup();
	private ScriptBundle m_script = new ScriptBundle();

	private Timer m_prefsUpdateTimer = new Timer();
	private Preferences m_robotPrefs;

	String switchAndScale;

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

		driveTrainSubsystem = new DriveTrain();
		elbowSubsystem = new ElbowSubsystem();
		elevatorSubsystem = new ElevatorSubsystem();
		elevatorTransmissionSubsystem = new ElevatorTransmission();
		externalIntakeSubsystem = new ExternalIntake();
		upperOutputSubsystem = new UpperOutput();
		transmissionSubsystem = new Transmission();

		// This MUST occur AFTER the subsystems and instantiated
		m_oi = new OI();

		switchAndScale = DriverStation.getInstance().getGameSpecificMessage();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {
		m_prefsUpdateTimer.reset();
		m_prefsUpdateTimer.start();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		updateSmartDashboard();

		// Every 3 seconds, update the robot preferences
		// No idea if this is a good idea or not. Worth experimenting with
		// though.
		if (m_prefsUpdateTimer.hasPeriodPassed(3)) {
			RobotConstants.updatePrefs(m_robotPrefs);
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

		driveTrainSubsystem.resetEncoders();
		RobotConstants.updatePrefs(m_robotPrefs);
		driveTrainSubsystem.updatePIDTunings();

		String data = DriverStation.getInstance().getGameSpecificMessage();

		// Load the auto
		m_autoCommand = m_script.getAuto(data);
		if (m_autoCommand != null)
			m_autoCommand.start();
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
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autoCommand != null) {
			m_autoCommand.cancel();

		}
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

		SmartDashboard.putNumber("Gyro Angle", driveTrainSubsystem.getGyroAngle());

		SmartDashboard.putNumber("Elevator Position", elevatorSubsystem.getElevatorPos());
		SmartDashboard.putBoolean("Elevator Soldnoid Position", elevatorTransmissionSubsystem.getSolenoidPos());

		SmartDashboard.putBoolean("Ex.Intake Cube", externalIntakeSubsystem.getHasCube());
		SmartDashboard.putBoolean("Up.Output Cube", upperOutputSubsystem.getHasCube());

	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {

	}
}
