package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeMechanism extends Subsystem {
	
	private WPI_TalonSRX m_leftMotor = new WPI_TalonSRX(RobotMap.CAN_INTAKE_LEFT_MOTOR);
	private WPI_TalonSRX m_rightMotor = new WPI_TalonSRX(RobotMap.CAN_INTAKE_LEFT_MOTOR);

	private DigitalInput m_cubeDetector = new DigitalInput(RobotMap.DIO_INTAKE_DETECTOR);
	
	public void disableMechanism() { //Disables both motors
		m_leftMotor.set(0);
		m_rightMotor.set(0); //Equivalent to .disable
	}
	
	public void spin(double d_speed) { //Takes values from -1.0 to 1.0. Spins the motors, positive is forwards, negative is backwards (Maybe, or it might be the other way around).
		m_leftMotor.set(d_speed);
		m_rightMotor.set(-1.0*d_speed);		
	}

	public boolean getHasCube() {
		return m_cubeDetector.get();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

