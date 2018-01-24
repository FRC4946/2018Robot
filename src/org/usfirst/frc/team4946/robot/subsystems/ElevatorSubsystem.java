package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.Robot;
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

	WPI_TalonSRX m_elevatorMotorLeft = new WPI_TalonSRX(RobotMap.CAN_ELEVATOR_LEFTMOTOR);
	WPI_TalonSRX m_elevatorMotorRight = new WPI_TalonSRX(RobotMap.CAN_ELEVATOR_RIGHTMOTOR);

	SpeedControllerGroup elevatorMotorGroup = new SpeedControllerGroup(Robot.elevatorSubsystem.m_elevatorMotorLeft,
			Robot.elevatorSubsystem.m_elevatorMotorRight);

	AnalogPotentiometer elevatorAnalogPotentiometer = new AnalogPotentiometer(RobotMap.ANALOG_ELEVATOR_POT,
			RobotConstants.ELEVATOR_SCALING_VALUE, RobotConstants.ELEVATOR_OFFSET_VALUE);

	public PIDController m_elevatorPIDController = new PIDController(0.0, 0.0, 0.0, elevatorAnalogPotentiometer,
			elevatorMotorGroup);
	// dummy numbers

	public ElevatorSubsystem() {
		m_elevatorPIDController.setInputRange(RobotConstants.ELEVATOR_MINIMUM_HEIGHT,
				RobotConstants.ELEVATOR_MAXIMUM_HEIGHT);
		m_elevatorPIDController.setOutputRange(-RobotConstants.ELEVATOR_MAX_OUTPUT, RobotConstants.ELEVATOR_MAX_OUTPUT);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new ElevatorJoystickCtrl());
	}

	public double getElevatorPos() {
		return elevatorAnalogPotentiometer.get();
	}

	public void manualMoveElevator(double ButtonValue) {
		double pos = elevatorAnalogPotentiometer.get();

		if (pos > RobotConstants.ELEVATOR_MINIMUM_HEIGHT && pos < RobotConstants.ELEVATOR_MAXIMUM_HEIGHT) {
			elevatorMotorGroup.set(ButtonValue);
		} else if (pos < RobotConstants.ELEVATOR_MINIMUM_HEIGHT) {
			elevatorMotorGroup.set(0.2);
		} else if (pos > RobotConstants.ELEVATOR_MAXIMUM_HEIGHT) {
			elevatorMotorGroup.set(-0.2);
		}

	}

	/*
	 * Sets motor speed manually
	 * 
	 * 
	 */

	public void set(double d_speed) {
		m_elevatorMotorLeft.set(d_speed);
		m_elevatorMotorRight.set(-d_speed);
	}

	public double getSpeed() {
		return (Math.abs(m_elevatorMotorLeft.get()) + Math.abs(m_elevatorMotorRight.get())) / 2;
	}

	public void setSetpoint(double d_point) {
		m_elevatorPIDController.setSetpoint(d_point);
	}

	public boolean getOnTarget() {
		return m_elevatorPIDController.onTarget();
	}
}
