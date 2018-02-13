package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.RobotMap;
import org.usfirst.frc.team4946.robot.commands.elevator.ClosedLoopControl;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 */ 
public class ElevatorSubsystem extends Subsystem {

	WPI_TalonSRX m_elevatorMotorTopLeft = new WPI_TalonSRX(RobotMap.CAN_ELEVATOR_TOPLEFT);
	WPI_TalonSRX m_elevatorMotorTopRight = new WPI_TalonSRX(RobotMap.CAN_ELEVATOR_TOPRIGHT);
	WPI_TalonSRX m_elevatorMotorBottomLeft = new WPI_TalonSRX(RobotMap.CAN_ELEVATOR_BOTLEFT);
	WPI_TalonSRX m_elevatorMotorBottomRight = new WPI_TalonSRX(RobotMap.CAN_ELEVATOR_BOTRIGHT);

	SpeedControllerGroup m_elevatorMotorGroupLeft = new SpeedControllerGroup(
			Robot.elevatorSubsystem.m_elevatorMotorTopLeft, Robot.elevatorSubsystem.m_elevatorMotorBottomLeft);

	SpeedControllerGroup m_elevatorMotorGroupRight = new SpeedControllerGroup(
			Robot.elevatorSubsystem.m_elevatorMotorTopRight, Robot.elevatorSubsystem.m_elevatorMotorBottomRight);

	SpeedControllerGroup m_elevatorMotorGroupAll = new SpeedControllerGroup(
			Robot.elevatorSubsystem.m_elevatorMotorGroupLeft, Robot.elevatorSubsystem.m_elevatorMotorGroupRight);

	AnalogPotentiometer m_elevatorAnalogPotentiometer = new AnalogPotentiometer(RobotMap.ANALOG_ELEVATOR_POT,
			RobotConstants.ELEVATOR_SCALING_VALUE, RobotConstants.ELEVATOR_OFFSET_VALUE);

	public PIDController m_elevatorPIDController = new PIDController(0.0, 0.0, 0.0, m_elevatorAnalogPotentiometer,
			m_elevatorMotorGroupAll);

	public ElevatorSubsystem() {
		m_elevatorPIDController.setInputRange(RobotConstants.ELEVATOR_MINIMUM_HEIGHT,
				RobotConstants.ELEVATOR_MAXIMUM_HEIGHT);
		m_elevatorPIDController.setOutputRange(-RobotConstants.ELEVATOR_MAX_OUTPUT, RobotConstants.ELEVATOR_MAX_OUTPUT);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new ClosedLoopControl());
	}

	/**
	 * Gets the position of the elevator
	 * 
	 * @return the position of the elevator
	 */
	public double getElevatorPos() {
		return m_elevatorAnalogPotentiometer.get();
	}

	/**
	 * Manually moves the elevator without employing any form of PID.
	 * 
	 * 
	 * @param move
	 *            the velocity the elevator is intended to move at. Accepts values
	 *            between -1.0 and 1.0.
	 */
	public void manualMoveElevator(double move) {
		double pos = m_elevatorAnalogPotentiometer.get();

		if (pos > RobotConstants.ELEVATOR_MINIMUM_HEIGHT && pos < RobotConstants.ELEVATOR_MAXIMUM_HEIGHT) {
			m_elevatorMotorGroupAll.set(move);
		} else if (pos < RobotConstants.ELEVATOR_MINIMUM_HEIGHT) {
			m_elevatorMotorGroupAll.set(0.2);
		} else if (pos > RobotConstants.ELEVATOR_MAXIMUM_HEIGHT) {
			m_elevatorMotorGroupAll.set(-0.2);
		}

	}

	/**
	 * Manually sets the speed of the motors.
	 * 
	 * @param d_speed
	 *            The fraction of the motor's maximum speed the motors are to spin
	 *            at. Ranges between -1.0 and 1.0
	 * 
	 * 
	 */
	public void set(double d_speed) {
		m_elevatorMotorGroupLeft.set(0.2);
		m_elevatorMotorGroupRight.set(-0.2);

	}

	public double getSpeed() {
		return (Math.abs(m_elevatorMotorGroupLeft.get()) + Math.abs(m_elevatorMotorGroupRight.get())) / 2;
	}

	public void setSetpoint(double d_point) {
		m_elevatorPIDController.setSetpoint(d_point);
	}

	public boolean getOnTarget() {
		return m_elevatorPIDController.onTarget();
	}
	
}
