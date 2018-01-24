/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4946.robot;

import org.usfirst.frc.team4946.robot.subsystems.DriveTrain;
import org.usfirst.frc.team4946.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team4946.robot.subsystems.IntakeMechanism;
import org.usfirst.frc.team4946.robot.subsystems.OutputMechanism;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {

	public static IntakeMechanism intakeSubsystem;
	public static DriveTrain driveTrainSubsystem;
	public static ElevatorSubsystem elevatorSubsystem;
	public static OutputMechanism outputsubsystem;
	public static OI m_oi;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	private Timer m_prefsUpdateTimer = new Timer();
	private Preferences m_robotPrefs;

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

		intakeSubsystem = new IntakeMechanism();
		driveTrainSubsystem = new DriveTrain();
		elevatorSubsystem = new ElevatorSubsystem();
		outputsubsystem = new OutputMechanism();

		// This MUST occur AFTER the subsystems and instantiated
		m_oi = new OI();
		// m_chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		// SmartDashboard.putData("Auto mode", m_chooser);
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

		// Every 3 seconds, update the robot preferences
		// No idea if this is a good idea or not. Worth experimenting with
		// though.
		if (m_prefsUpdateTimer.hasPeriodPassed(3))
			RobotConstants.updatePrefs(m_robotPrefs);
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

		m_autonomousCommand = m_chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		 * switch(autoSelected) { case "My Auto": autonomousCommand = new
		 * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
		 * ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
