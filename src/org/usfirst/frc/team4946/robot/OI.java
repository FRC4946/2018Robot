/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4946.robot;

import org.usfirst.frc.team4946.robot.commands.CubeAndLiftIntake;
import org.usfirst.frc.team4946.robot.commands.DriveTrainGearShift;
import org.usfirst.frc.team4946.robot.commands.OutputCubeWithIntake;
import org.usfirst.frc.team4946.robot.commands.elevator.ElevatorGearShift;
import org.usfirst.frc.team4946.robot.commands.elevator.ElevatorJoystickCtrl;
import org.usfirst.frc.team4946.robot.commands.output.RunOutput;

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

	Button cubeInButton = new JoystickButton(driveStick, 1); // 1 is the button number for the cube intake Button
	Button cubeOutButton = new JoystickButton(driveStick, 2); // 2 is the button number for the cube output button
	Button clampButtonOpen = new JoystickButton(operatorStick, 1); // Opens clamp on elevator
	Button clampButtonClosed = new JoystickButton(operatorStick, 2); //Closes clamp on elevator
	Button toggleElevatorOpenLoop = new JoystickButton(operatorStick, 3); // Activates open loop controls for elevator
	Button elevatorGearshiftButton = new JoystickButton(operatorStick, 4); //Shifts gears on the elevator
	Button driveGearshiftButton = new JoystickButton(driveStick, 5); //Shifts gears on the drivetrain

	public OI() {
		
		if(RobotConstants.getElevatorIsLowest()) {
			
			cubeInButton.whileHeld(new CubeAndLiftIntake());
			cubeOutButton.whileHeld(new OutputCubeWithIntake());
		} else {
			
			cubeInButton.whileHeld(new RunOutput(-0.7));
			cubeOutButton.whileHeld(new RunOutput(0.7));
		}

		toggleElevatorOpenLoop.whileHeld(new ElevatorJoystickCtrl());
		elevatorGearshiftButton.whenPressed(new ElevatorGearShift());
		driveGearshiftButton.whenPressed(new DriveTrainGearShift());
	}

	/**
	 * @return the driver joystick.
	 */
	public Joystick getDriveStick() {
		return driveStick;
	}

	/**
	 * @return the operator joystick.
	 */
	public Joystick getOperatorStick() {
		return operatorStick;
	}

}
