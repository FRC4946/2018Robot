package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotMap;
import org.usfirst.frc.team4946.robot.commands.intake.IntakeWithTrigger;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeSubsystem extends Subsystem {

	private WPI_TalonSRX m_leftInnerMotor = new WPI_TalonSRX(RobotMap.CAN_OUTPUT_LEFT);
	private WPI_TalonSRX m_rightInnerMotor = new WPI_TalonSRX(RobotMap.CAN_OUTPUT_RIGHT);

	private WPI_TalonSRX m_leftOuterMotor = new WPI_TalonSRX(RobotMap.CAN_INTAKE_LEFTMOTOR);
	private WPI_TalonSRX m_rightOuterMotor = new WPI_TalonSRX(RobotMap.CAN_INTAKE_RIGHTMOTOR);
	
	private DigitalInput m_leftCubeSwitch = new DigitalInput(RobotMap.DIO_INTAKE_SWITCH1);
	private DigitalInput m_rightCubeSwitch = new DigitalInput(RobotMap.DIO_INTAKE_SWITCH2);
	private DigitalInput m_cubeBannerSensor = new DigitalInput(RobotMap.DIO_INTAKE_BANNER);
	
	public void initDefaultCommand() {
		setDefaultCommand(new IntakeWithTrigger());
	}

	/**
	 * Manually sets the speed of the internal intake motors.
	 * 
	 * @param d_speed
	 *            Speed of the motors
	 */
	public void setInner(double d_speed) {
		m_leftInnerMotor.set(d_speed);
		m_rightInnerMotor.set(-d_speed); 
	}
	
	/**
	 * Manually sets the speed of the motors
	 * 
	 * @param d_speed
	 *            Speed of the motors
	 */
	public void setOuter(double d_speed) {
		m_leftOuterMotor.set(d_speed * 0.85);
		m_rightOuterMotor.set(-d_speed * 1.1);
	}
	
	/**
	 * Sets the speed of all intake motors to 0
	 */
	public void stop() {
		setInner(0.0);
		setOuter(0.0);
	}

	/** 
	 * 
	 * @return true if limit switches detect the cube, false for no cube
	 */
	public boolean getHasCube() {
		return !m_leftCubeSwitch.get() || !m_rightCubeSwitch.get();
	}
	
	/** 
	 * 
	 * @return true if banner sensor detects the cube, false for no cube
	 */
	public boolean getBannerHasCube() {
		return m_cubeBannerSensor.get();
	}
	
	/**
	 * Sets the outer intake motors in reverse directions to reorient the cube
	 * 
	 * @param d_speed
	 * 			  Speed of the motors
	 */
	public void diagonalSpin(double d_speed) {
		m_leftOuterMotor.set(d_speed);
		m_rightOuterMotor.set(d_speed);
	}

}
