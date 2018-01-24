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

	// Let's establish a naming convention here.
	// Each constant will be of the format INTERFACE_SUBSYSTEM_COMPONENT.
	// Remember also that all of these should be public static final ints.

	// For example,
	// PWM_DRIVETRAIN_FLMOTOR
	// CAN_SHOOTER_MOTOR
	// DIO_SHOOTER_ENCODER
	// PCM_SHOOTER_HOOD
	// RELAY_VISION_LED
	//

	public static final int ANALOG_ELEVATOR_POT = 1;

	public static final int USB_DS_DRIVESTICK = 0;
	public static final int USB_DS_OPERATORSTICK = 0;

	public static final int CAN_DRIVE_LEFTFRONT = 0;
	public static final int CAN_DRIVE_RIGHTFRONT = 2;
	public static final int CAN_DRIVE_LEFTBACK = 1;
	public static final int CAN_DRIVE_RIGHTBACK = 3;
	public static final int CAN_DRIVE_LEFTMID = 4;
	public static final int CAN_DRIVE_RIGHTMID = 5;
	public static final int CAN_INTAKE_LEFTMOTOR = 6;
	public static final int CAN_INTAKE_RIGHTMOTOR = 7;
	public static final int CAN_ELEVATOR_LEFTMOTOR = 8;
	public static final int CAN_ELEVATOR_RIGHTMOTOR = 9;

	public static final int DIO_DRIVE_LEFTENC1 = 0;
	public static final int DIO_DRIVE_LEFTENC2 = 1;
	public static final int DIO_DRIVE_RIGHTENC1 = 2;
	public static final int DIO_DRIVE_RIGHTENC2 = 3;

	public static final int PCM_DRIVE_LEFTSOLENOID = 0;
}
