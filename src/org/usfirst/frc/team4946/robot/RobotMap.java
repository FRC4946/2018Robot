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
	
	public static final int m_elevatorMotorTopLeft = 0;
	public static final int m_elevatorMotorTopRight = 1;
	public static final int m_elevatorMotorBottomLeft = 2;
	public static final int m_elevatorMotorBottomRight = 3;
	
	public static final int joystick = 0;
	//these are dummy numbers
			
	public static final int elevatorAnalogPotentiometerChannel = 1;
	public static final int DIO_INTAKE_DETECTOR = 0;
	
	public static final int PCM_ELBOW_LEFT = 0;
	public static final int PCM_ELBOW_RIGHT = 1;
	
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	
	// Port numbers for motors on the intake mechanism
	public static final int CAN_INTAKE_LEFT_MOTOR = 6;
	public static final int CAN_INTAKE_RIGHT_MOTOR = 7;
	
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
