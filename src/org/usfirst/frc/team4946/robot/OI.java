/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4946.robot;

import org.usfirst.frc.team4946.robot.commands.drivetrain.SetDriveGear;
import org.usfirst.frc.team4946.robot.commands.elbow.OverrideElbowPos;
import org.usfirst.frc.team4946.robot.commands.elevator.SetElevatorGear;
import org.usfirst.frc.team4946.robot.commands.elevator.preset.MoveToRung;
import org.usfirst.frc.team4946.robot.commands.intake.SetIntake;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
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

	Button driveA = new JoystickButton(driveStick, 1);
	Button driveB = new JoystickButton(driveStick, 2);
	Button driveX = new JoystickButton(driveStick, 3);
	Button driveY = new JoystickButton(driveStick, 4);

	Button driveLB = new JoystickButton(driveStick, 5);
	Button driveRB = new JoystickButton(driveStick, 6);

	Button operatorA = new JoystickButton(operatorStick, 1);
	Button operatorB = new JoystickButton(operatorStick, 2);
	Button operatorX = new JoystickButton(operatorStick, 3);
	Button operatorY = new JoystickButton(operatorStick, 4);
	Button operatorLB = new JoystickButton(operatorStick, 5);
	Button operatorRB = new JoystickButton(operatorStick, 6);
	Button operatorBack = new JoystickButton(operatorStick, 7);
	Button operatorStart = new JoystickButton(operatorStick, 8);
	// Button operatorL3 = new JoystickButton(operatorStick, 9);

	public OI() {

		driveA.whileHeld(new SetIntake(-0.2));
		driveB.whileHeld(new SetIntake(-0.4));
		driveX.whileHeld(new SetIntake(-0.6));
		driveY.whileHeld(new SetIntake(-0.8));

		driveRB.whenPressed(new OverrideElbowPos());
		driveLB.whenPressed(new SetDriveGear(false));
		driveLB.whenReleased(new SetDriveGear(true));

		operatorA.whileHeld(new SetIntake(-0.2));
		operatorB.whileHeld(new SetIntake(-0.4));
		operatorX.whileHeld(new SetIntake(-0.6));
		operatorY.whileHeld(new SetIntake(-0.8));

		operatorRB.whileHeld(new MoveToRung());
		operatorLB.whenPressed(new SetElevatorGear(false));
		operatorLB.whenReleased(new SetElevatorGear(true));

		// operatorLB.whileHeld(new LiftRobot());
		operatorBack.whenPressed(new SetElevatorGear(false));
		operatorStart.whenPressed(new SetElevatorGear(true));
	}

	/**
	 * @return the driver joystick.
	 */
	public Joystick getDriveStick() {
		return driveStick;
	}

	public void setDriveStickRumble(double rumble) {
		driveStick.setRumble(RumbleType.kLeftRumble, rumble);
		driveStick.setRumble(RumbleType.kRightRumble, rumble);
	}

	/**
	 * @return the operator joystick.
	 */
	public Joystick getOperatorStick() {
		return operatorStick;
	}

	public void setOperateStickRumble(double rumble) {
		operatorStick.setRumble(RumbleType.kLeftRumble, rumble);
		operatorStick.setRumble(RumbleType.kRightRumble, rumble);
	}
}
