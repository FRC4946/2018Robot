package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.RobotMap;
import org.usfirst.frc.team4946.robot.commands.elevator.ElevatorWithJoystick;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 */
public class ElevatorSubsystem extends Subsystem {

	private WPI_TalonSRX m_elevatorMotorLeft = new WPI_TalonSRX(RobotMap.CAN_ELEVATOR_LEFT);
	private WPI_TalonSRX m_elevatorMotorRight = new WPI_TalonSRX(RobotMap.CAN_ELEVATOR_RIGHT);

	private SpeedControllerGroup m_elevatorMotorGroup = new SpeedControllerGroup(m_elevatorMotorLeft,
			m_elevatorMotorRight);

	private AnalogPotentiometer m_elevatorAnalogPotentiometer = new AnalogPotentiometer(RobotMap.ANALOG_ELEVATOR_POT,
			RobotConstants.ELEVATOR_SCALING_VALUE, RobotConstants.ELEVATOR_OFFSET_VALUE);

	private PIDController m_elevatorPIDController = new PIDController(RobotConstants.elevatorP,
			RobotConstants.elevatorI, RobotConstants.elevatorD, m_elevatorAnalogPotentiometer, m_elevatorMotorGroup);

	public ElevatorSubsystem() {
		m_elevatorPIDController.setInputRange(RobotConstants.ELEVATOR_MINIMUM_HEIGHT,
				RobotConstants.ELEVATOR_MAXIMUM_HEIGHT);
		m_elevatorPIDController.setOutputRange(-RobotConstants.ELEVATOR_MAX_OUTPUT, RobotConstants.ELEVATOR_MAX_OUTPUT);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new ElevatorWithJoystick());
	}

	/**
	 * Enable the PID controller
	 */
	public void enablePID() {
		m_elevatorPIDController.enable();
	}

	/**
	 * Disable the PID controller
	 */
	public void disablePID() {
		m_elevatorPIDController.disable();
	}

	/**
	 * @return {@code true} if the controller is enabled
	 */
	public boolean getPIDEnabled() {
		return m_elevatorPIDController.isEnabled();
	}

	/**
	 * Update the PID tunings on the elevator
	 */
	public void updatePIDTunings() {
		m_elevatorPIDController.setP(RobotConstants.elevatorP);
		m_elevatorPIDController.setI(RobotConstants.elevatorI);
		m_elevatorPIDController.setD(RobotConstants.elevatorD);
	}

	/**
	 * Gets the position of the elevator
	 * 
	 * @return the position of the elevator
	 */
	public double getElevatorPos() {
		return m_elevatorAnalogPotentiometer.get();
	}

	public boolean isAtBottom() {
		return getElevatorPos() <= RobotConstants.ELEVATOR_BOTTOM_THRESHOLD;
	}

	/**
	 * Manually sets the speed of the motors.
	 * 
	 * @param d_speed
	 *            The fraction of the motor's maximum speed the motors are to spin
	 *            at. Ranges between -1.0 and 1.0
	 */
	public void set(double d_speed) {
		m_elevatorMotorLeft.set(d_speed);
		m_elevatorMotorRight.set(-d_speed);
	}

	/**
	 * @return the average speed of both elevator MotorControllerGroups
	 */
	public double getSpeed() {
		return m_elevatorMotorGroup.get();
	}

	/**
	 * Sets the elevator height setpoint.
	 * 
	 * @param d_point
	 *            the height setpoint in inches.
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
