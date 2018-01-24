/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4946.robot;

import org.usfirst.frc.team4946.robot.commands.elevator.ElevatorJoystickCtrl;
import org.usfirst.frc.team4946.robot.commands.intake.RunIntake;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	private Joystick driveStick = new Joystick(RobotMap.USB_DS_DRIVESTICK);
	private Joystick operatorStick = new Joystick(RobotMap.USB_DS_OPERATORSTICK);

	// Button creation:
	Button intakeButtonIn = new JoystickButton(driveStick, 1); // 1 is the button number for the cube intake Button
	Button intakeButtonOut = new JoystickButton(driveStick, 2); // 2 is the button number for the cube output button
	Button toggleElevatorOpenLoop = new JoystickButton(operatorStick, 3); // Activates open loop controls for elevator

	// Button-command linking:
	public OI() {
		intakeButtonIn.whileHeld(new RunIntake(-1.0)); // Pulls in cube
		intakeButtonOut.whileHeld(new RunIntake(1.0)); // Pushes out cube

		toggleElevatorOpenLoop.whenPressed(new ElevatorJoystickCtrl());
	}

	public Joystick getDriveStick() {
		return driveStick;
	}

	public Joystick getOperatorStick() {
		return operatorStick;
	}

}
