package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.RobotMap;
import org.usfirst.frc.team4946.robot.commands.elevator.ElevatorJoystickCtrl;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 */ 
public class ElevatorSubsystem extends Subsystem {

	WPI_TalonSRX m_elevatorMotorLeft = new WPI_TalonSRX(RobotMap.CAN_ELEVATOR_LEFT);
	WPI_TalonSRX m_elevatorMotorRight = new WPI_TalonSRX(RobotMap.CAN_ELEVATOR_RIGHT);

	SpeedControllerGroup m_elevatorMotorGroup = new SpeedControllerGroup(m_elevatorMotorLeft, m_elevatorMotorRight);

	AnalogPotentiometer m_elevatorAnalogPotentiometer = new AnalogPotentiometer(RobotMap.ANALOG_ELEVATOR_POT,
			RobotConstants.ELEVATOR_SCALING_VALUE, RobotConstants.ELEVATOR_OFFSET_VALUE);

	public PIDController m_elevatorPIDController = new PIDController(0.0, 0.0, 0.0, m_elevatorAnalogPotentiometer,
			m_elevatorMotorGroup);

	public ElevatorSubsystem() {
		m_elevatorPIDController.setInputRange(RobotConstants.ELEVATOR_MINIMUM_HEIGHT,
				RobotConstants.ELEVATOR_MAXIMUM_HEIGHT);
		m_elevatorPIDController.setOutputRange(-RobotConstants.ELEVATOR_MAX_OUTPUT, RobotConstants.ELEVATOR_MAX_OUTPUT);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new ElevatorJoystickCtrl());
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
			m_elevatorMotorGroup.set(move);
		} else if (pos < RobotConstants.ELEVATOR_MINIMUM_HEIGHT) {
			m_elevatorMotorGroup.set(0.2);
		} else if (pos > RobotConstants.ELEVATOR_MAXIMUM_HEIGHT) {
			m_elevatorMotorGroup.set(-0.2);
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
		
		m_elevatorMotorLeft.set(d_speed);
		m_elevatorMotorRight.set(-d_speed);
	}

	/**
	 * @return the average speed of both elevator MotorControllerGroups
	 */
	public double getSpeed() {
		
		return (m_elevatorMotorGroup.get());
	}
	
	/**
	 * @return the speed of the left elevator MotorControllerGroup
	 */
	public double getLeftSpeed() {
		return m_elevatorMotorGroup.get();
	}
	
	/**
	 * @return the speed of the right elevator MotorControllerGroup
	 */
	public double getRightSpeed() {
		//return m_elevatorMotorGroupRight.get();
		return 1.0;
	}
	
	/**
	 * Sets the elevator height setpoint.
	 * @param d_point
	 * 				the height setpoint in inches.
	 */
	public void setSetpoint(double d_point) {
		m_elevatorPIDController.setSetpoint(d_point);
	}

	/**
	 * @return Whether or not the current height matches the height setpoint.
	 */
	public boolean getOnTarget() {
		return m_elevatorPIDController.onTarget();
	}
	
}
