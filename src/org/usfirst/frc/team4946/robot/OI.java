/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4946.robot;

import org.usfirst.frc.team4946.robot.commands.ToggleCube;
import org.usfirst.frc.team4946.robot.commands.ElevatorSetHeight;
import org.usfirst.frc.team4946.robot.commands.ElevatorJoystickCtrl;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	private static Joystick driveStick = new Joystick(RobotMap.USB_DS_DRIVESTICK);
	private static Joystick operatorStick = new Joystick(RobotMap.USB_DS_OPERATORSTICK);

	//// CREATING BUTTONS
	// Tester test
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());

	// Button creation:
	Button intakeButtonIn = new JoystickButton(driveStick, 1); // 1 is the button number for the cube intake Button
	Button intakeButtonOut = new JoystickButton(driveStick, 2); // 2 is the button number for the cube output button

	Button elevatorButtonUp = new JoystickButton(operatorStick, 1);
	
	Button toggleElevatorOpenLoop = new JoystickButton(operatorStick, 3); //Activates open loop controls for elevator

	// Button-command linking:
	public OI() {
		intakeButtonIn.whileHeld(new ToggleCube(-1.0)); // Pulls in cube
		intakeButtonOut.whileHeld(new ToggleCube(1.0)); // Pushes out cube
		
		toggleElevatorOpenLoop.whenPressed(new ElevatorJoystickCtrl());
	}

	public static Joystick getDriveStick() {
		return driveStick;
	}

	public static Joystick getOperatorStick() {
		return operatorStick;
	}

	public Button getElevatorButton() {
		return elevatorButtonUp;
	}
	private Joystick k_driveStick = new Joystick(RobotMap.USB_DS_DRIVESTICK); 
	private Joystick k_operatorStick = new Joystick(RobotMap.USB_DS_OPERATORSTICK);
	
	public Joystick getDriveJoystick() {
		return k_driveStick;
	}
}
