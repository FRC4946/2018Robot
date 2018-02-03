package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
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

		
	}
	public void set(double d_speed) { 
		m_leftMotor.set(d_speed);
		m_rightMotor.set(-1.0 * d_speed);
	}
/**
 * @param takes the values of -1.0 and 1.0 and spins the motor forwards if + and backwards if -
 */

	public void stop() { 		
		m_leftMotor.set(0);
		m_rightMotor.set(0); 
	}/**
	 * @param sets the motor speeds to 0 and stops both motors
	 */

	public boolean getHasCube() {
		return m_cubeSwitch.get();
	}
	/**
	 * @returns true if the cube is in the intake spot, or false if the cube is not in the intake spot
	 */
}
