/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4946.robot;

import org.usfirst.frc.team4946.robot.commands.arm.SetClamp;
import org.usfirst.frc.team4946.robot.commands.arm.ToggleElbow;
import org.usfirst.frc.team4946.robot.commands.drivetrain.SetDriveGear;
import org.usfirst.frc.team4946.robot.commands.elevator.SetElevatorGear;
import org.usfirst.frc.team4946.robot.commands.elevator.preset.MoveToScale;
import org.usfirst.frc.team4946.robot.commands.elevator.preset.MoveToSwitch;
import org.usfirst.frc.team4946.robot.commands.intake.SetIntake;
import org.usfirst.frc.team4946.robot.commands.ramp.DeployRamp;
import org.usfirst.frc.team4946.robot.util.POVButton;

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
	Button driveBack = new JoystickButton(driveStick, 7);
	Button driveStart = new JoystickButton(driveStick, 8);
	POVButton driveN = new POVButton(driveStick, 0);
	POVButton driveE = new POVButton(driveStick, 270);
	POVButton driveS = new POVButton(driveStick, 180);
	POVButton driveW = new POVButton(driveStick, 90);

	Button operatorA = new JoystickButton(operatorStick, 1);
	Button operatorB = new JoystickButton(operatorStick, 2);
	Button operatorX = new JoystickButton(operatorStick, 3);
	Button operatorY = new JoystickButton(operatorStick, 4);
	Button operatorLB = new JoystickButton(operatorStick, 5);
	Button operatorRB = new JoystickButton(operatorStick, 6);
	Button operatorBack = new JoystickButton(operatorStick, 7);
	Button operatorStart = new JoystickButton(operatorStick, 8);
	Button operatorL3 = new JoystickButton(operatorStick, 9);
	POVButton operatorN = new POVButton(operatorStick, 0);
	POVButton operatorE = new POVButton(operatorStick, 270);
	POVButton operatorS = new POVButton(operatorStick, 180);
	POVButton operatorW = new POVButton(operatorStick, 90);

	public OI() {

		// driveA.whileHeld(new SetIntake(-0.2));
		// driveB.whileHeld(new SetIntake(-0.4));
		// driveX.whileHeld(new SetIntake(-0.6));
		// driveY.whileHeld(new SetIntake(-0.8));

		driveLB.whenPressed(new ToggleElbow());
		driveRB.whenPressed(new SetClamp(false));
		driveRB.whenReleased(new SetClamp(true));
		driveA.whenPressed(new SetDriveGear(false));
		driveA.whenReleased(new SetDriveGear(true));

		// driveN.whileHeld(new TurnPID(175));
		// driveW.whileHeld(new TurnPID(145));
		// driveS.whileHeld(new TurnPID(120));
		// driveE.whileHeld(new TurnPID(100));

		operatorA.whileHeld(new SetIntake(-0.2));
		operatorB.whileHeld(new SetIntake(-0.4));
		operatorX.whileHeld(new SetIntake(-0.6));
		operatorY.whileHeld(new SetIntake(-0.8));

		operatorLB.whenPressed(new SetElevatorGear(true));
		operatorLB.whenReleased(new SetElevatorGear(false));
		operatorL3.whileHeld(new DeployRamp());

		operatorN.whileHeld(new MoveToScale(false));
		operatorS.whileHeld(new MoveToScale(true));
		operatorE.whileHeld(new MoveToSwitch());
		operatorW.whileHeld(new MoveToSwitch());

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
