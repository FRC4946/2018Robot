/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4946.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	
	//Port numbers for Robot:
	
	// Port numbers for motors on the intake mechanism
	public static final int i_intakeLeftMotorPort = 4; //These numbers probably aren't correct
	public static final int i_intakeRightMotorPort = 3;//     ||  <--These are ditto marks...
	
	//Port numbers for inputs:
	
	public static final int USB_DS_DRIVESTICK = 0;
	public static final int USB_DS_OPERATORSTICK = 0;

	public static final int k_LeftFront = 0;
	public static final int k_RightFront = 2;
	public static final int k_LeftBack = 1;
	public static final int k_RightBack = 3;
	public static final int k_LeftMid = 4;
	public static final int k_RightMid = 5;
	
	public static final int k_DIO_LeftEnc1 = 0;
	public static final int k_DIO_LeftEnc2 = 1;
	public static final int k_DIO_RightEnc1 = 2;
	public static final int k_DIO_RightEnc2 = 3;
	
	public static final int k_DIO_GearShifter = 0;
}
