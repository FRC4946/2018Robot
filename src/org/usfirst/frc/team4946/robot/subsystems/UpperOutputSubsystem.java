package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class UpperOutputSubsystem extends Subsystem {

	private WPI_TalonSRX m_leftMotor = new WPI_TalonSRX(RobotMap.CAN_OUTPUT_LEFT);
	private WPI_TalonSRX m_rightMotor = new WPI_TalonSRX(RobotMap.CAN_OUTPUT_RIGHT);

	private DigitalInput m_cubeSwitchA = new DigitalInput(RobotMap.DIO_INTAKE_SWITCH_A);
	private DigitalInput m_cubeSwitchB = new DigitalInput(RobotMap.DIO_INTAKE_SWITCH_B);

	public void initDefaultCommand() {

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
		return m_cubeSwitchA.get() && m_cubeSwitchB.get();
	}
}
