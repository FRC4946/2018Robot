package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class UpperOutput extends Subsystem {
	
	private WPI_TalonSRX m_leftMotor = new WPI_TalonSRX(RobotMap.CAN_INTAKE_LEFTMOTOR);
	private WPI_TalonSRX m_rightMotor = new WPI_TalonSRX(RobotMap.CAN_INTAKE_LEFTMOTOR);
	
	private DigitalInput m_cubeSwitch = new DigitalInput(RobotMap.DIO_OUTPUT_SWITCH);

	/**
	 * Manually sets the speed of the motors.
	 * 
	 * @param d_speed
	 *            The fraction of the motor's maximum speed the motors are to spin
	 *            at. Ranges between -1.0 and 1.0
	 */
	public void spin(double d_speed) { // Takes values from -1.0 to 1.0. Spins the motors, positive is forwards,
										// negative is backwards (Maybe, or it might be the other way around).
		m_leftMotor.set(d_speed);
		m_rightMotor.set(-1.0 * d_speed);
	}
	
	/**
	 * Sets the speed of both motors to 0
	 */
	public void stop() { // Disables both motors
		m_leftMotor.set(0);
		m_rightMotor.set(0); // Equivalent to .disable
	}
	
	
	/**
	 * Disables both motors.
	 */
	public void disableMechanism() {
		m_leftMotor.set(0);
		m_rightMotor.set(0); //Equivalent to .disable
	}
	
	/**
	 * Manually sets the speed of the motors.
	 * @param d_speed
	 * 			Speed of the motors
	 */
	public void set(double d_speed) { //Takes values from -1.0 to 1.0. Spins the motors.
										//positive is forwards, negative is backwards (Maybe, or it might be the other way around)
		m_leftMotor.set(d_speed);
		m_rightMotor.set(-1.0*d_speed);		
	}
	
	/**
	 * 
	 * @return true if detects the cube, false for no cube
	 */
	public boolean getHasCube() {
		return m_cubeSwitch.get();
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

