package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotMap;
import org.usfirst.frc.team4946.robot.commands.intake.IntakeWithTrigger;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ExternalIntakeSubsystem extends Subsystem {

	private WPI_TalonSRX m_leftMotor = new WPI_TalonSRX(RobotMap.CAN_INTAKE_LEFTMOTOR);
	private WPI_TalonSRX m_rightMotor = new WPI_TalonSRX(RobotMap.CAN_INTAKE_RIGHTMOTOR);

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new IntakeWithTrigger());
	}

	/**
	 * Manually sets the speed of the motors
	 * 
	 * @param d_speeandAA
	 *            Speed of the motors
	 */
	public void set(double d_speed) {

		m_leftMotor.set(d_speed * 0.85);
		m_rightMotor.set(-d_speed * 1.1);
	}

	public void diagonalSpin(double d_speed) {
		m_leftMotor.set(d_speed);
		m_rightMotor.set(d_speed);
	}

	/**
	 * Disables both motors
	 */
	public void stop() {
		m_leftMotor.set(0);
		m_rightMotor.set(0);
	}
}
