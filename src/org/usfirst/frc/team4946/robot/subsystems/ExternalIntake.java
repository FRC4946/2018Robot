package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ExternalIntake extends Subsystem {

	private WPI_TalonSRX m_leftMotor = new WPI_TalonSRX(RobotMap.CAN_INTAKE_LEFTMOTOR);
	private WPI_TalonSRX m_rightMotor = new WPI_TalonSRX(RobotMap.CAN_INTAKE_RIGHTMOTOR);

	private DigitalInput m_cubeSwitch = new DigitalInput(RobotMap.DIO_INTAKE_SWITCH);

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}

	/**
	 * Manually sets the speed of the motors
	 * @param d_speed
	 * 			Speed of the motors
	 */
	public void set(double d_speed) { // Takes values from -1.0 to 1.0. Spins the motors, positive is forwards,
										// negative is backwards (Maybe, or it might be the other way around).
		m_leftMotor.set(d_speed);
		m_rightMotor.set(-1.0 * d_speed);
	}

	/**
	 * Disables both motors
	 */
	public void stop() {
		m_leftMotor.set(0);
		m_rightMotor.set(0); // Equivalent to .disable
	}

	/**
	 * 
	 * @return true if it detects a cube, false if there is no cube
	 */
	public boolean getHasCube() {
		return m_cubeSwitch.get();
	}

}
