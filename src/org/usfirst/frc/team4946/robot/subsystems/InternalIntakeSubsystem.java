package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotMap;
import org.usfirst.frc.team4946.robot.commands.intake.IntakeWithTrigger;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class InternalIntakeSubsystem extends Subsystem {

	private WPI_TalonSRX m_leftMotor = new WPI_TalonSRX(RobotMap.CAN_OUTPUT_LEFT);
	private WPI_TalonSRX m_rightMotor = new WPI_TalonSRX(RobotMap.CAN_OUTPUT_RIGHT);

	private DigitalInput m_leftCubeSwitch = new DigitalInput(RobotMap.DIO_INTAKE_SWITCH1);
	private DigitalInput m_rightCubeSwitch = new DigitalInput(RobotMap.DIO_INTAKE_SWITCH2);

	public void initDefaultCommand() {
		setDefaultCommand(new IntakeWithTrigger());
	}

	/**
	 * Sets the speed of both motors to 0
	 */
	public void stop() {
		m_leftMotor.set(0);
		m_rightMotor.set(0);
	}

	/**
	 * Manually sets the speed of the motors.
	 * 
	 * @param d_speed
	 *            Speed of the motors
	 */
	public void set(double d_speed) {
		m_leftMotor.set(d_speed);
		m_rightMotor.set(-d_speed);
	}

	/**
	 * 
	 * @return true if detects the cube, false for no cube
	 */
	public boolean getHasCube() {
		return !m_leftCubeSwitch.get() || !m_rightCubeSwitch.get();
	}
}
