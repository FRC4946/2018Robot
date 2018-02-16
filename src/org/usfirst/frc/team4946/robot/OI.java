/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4946.robot;

import org.usfirst.frc.team4946.robot.commands.CubeAndLiftIntake;
import org.usfirst.frc.team4946.robot.commands.OutputCubeWithIntake;
import org.usfirst.frc.team4946.robot.commands.drivetrain.ToggleDriveGear;
import org.usfirst.frc.team4946.robot.commands.elbow.ToggleElbowPos;
import org.usfirst.frc.team4946.robot.commands.elevator.ElevatorGearShift;
import org.usfirst.frc.team4946.robot.commands.elevator.ElevatorJoystickCtrl;
import org.usfirst.frc.team4946.robot.commands.elevator.ElevatorSetHeight;
import org.usfirst.frc.team4946.robot.commands.intake.RunDiagonalIntake;
import org.usfirst.frc.team4946.robot.commands.intake.RunIntake;
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

	Button cubeInButton = new JoystickButton(driveStick, 5); // 1 is the button number for the cube intake Button
	Button cubeOutButton = new JoystickButton(driveStick, 6); // 2 is the button number for the cube output button
	Button triggerDiagonalCube = new JoystickButton(driveStick, 4);
	
	Button togglePneumaticArms = new JoystickButton(operatorStick, 1);
	Button driveGearToggle = new JoystickButton(operatorStick, 2);
	Button toggleElevatorOpenLoop = new JoystickButton(operatorStick, 3); // Activates open loop controls for elevator
	Button elevatorGearToggle = new JoystickButton(operatorStick, 4); 
	
	Button elevatorPreset1 = new JoystickButton(operatorStick, 5); // moves elevator to preset height
	Button elevatorPreset2 = new JoystickButton(operatorStick, 6); // moves elevator to preset height
	Button elevatorPreset3 = new JoystickButton(operatorStick, 7); // moves elevator to preset height

	public OI() {

		cubeInButton.whileHeld(new CubeAndLiftIntake());
		cubeOutButton.whileHeld(new OutputCubeWithIntake());

		toggleElevatorOpenLoop.whileHeld(new ElevatorJoystickCtrl());
		triggerDiagonalCube.whenPressed(new RunDiagonalIntake(0.5));
		triggerDiagonalCube.whenReleased(new RunIntake(0.0));
		
		cubeInButton.whenPressed(new RunIntake(0.5));
		cubeInButton.whenPressed(new RunOutput(0.4));
		
		cubeInButton.whenReleased(new RunIntake(0.0));
		cubeInButton.whenReleased(new RunOutput(0.0));
		
		cubeOutButton.whenPressed(new RunIntake(-0.5));
		cubeOutButton.whenPressed(new RunOutput(-0.4));
		
		cubeOutButton.whenReleased(new RunIntake(0.0));
		cubeOutButton.whenReleased(new RunOutput(0.0));
		
		togglePneumaticArms.whenPressed(new ToggleElbowPos());
		driveGearToggle.whenPressed(new ToggleDriveGear());
		elevatorGearToggle.whenPressed(new ElevatorGearShift());
		
		elevatorPreset1.whileHeld(new ElevatorSetHeight(RobotConstants.ELEVATOR_BOTTOM_THRESHOLD, 0.8));
		elevatorPreset2.whileHeld(new ElevatorSetHeight(RobotConstants.ELEVATOR_SWITCH_HEIGHT, 0.8));
		elevatorPreset3.whileHeld(new ElevatorSetHeight(RobotConstants.ELEVATOR_SCALE_HEIGHT, 0.8));
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
