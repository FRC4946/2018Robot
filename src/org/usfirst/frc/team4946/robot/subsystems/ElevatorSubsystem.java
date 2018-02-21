package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.RobotMap;
import org.usfirst.frc.team4946.robot.commands.elevator.ElevatorWithJoystick;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 */
public class ElevatorSubsystem extends Subsystem {

	private WPI_TalonSRX m_motorLeft = new WPI_TalonSRX(RobotMap.CAN_ELEVATOR_LEFT);
	private WPI_TalonSRX m_motorRight = new WPI_TalonSRX(RobotMap.CAN_ELEVATOR_RIGHT);
	private SpeedControllerGroup m_motorGroup = new SpeedControllerGroup(m_motorLeft, m_motorRight);
	private DoubleSolenoid m_break = new DoubleSolenoid(RobotMap.PCM_ELEVATOR_BREAK, RobotMap.PCM_ELEVATOR_UNLOCK);

	private AnalogPotentiometer m_analogPot = new AnalogPotentiometer(RobotMap.ANALOG_ELEVATOR_POT,
			RobotConstants.ELEVATOR_SCALING_VALUE, RobotConstants.ELEVATOR_OFFSET_VALUE);

	private PIDController m_PIDController = new PIDController(RobotConstants.elevatorP, RobotConstants.elevatorI,
			RobotConstants.elevatorD, m_analogPot, m_motorGroup);

	private double minHeight = RobotConstants.ELEVATOR_MINIMUM_HEIGHT;
	private boolean m_isLocked = false;

	public ElevatorSubsystem() {
		m_analogPot.setPIDSourceType(PIDSourceType.kDisplacement);
		m_PIDController.setInputRange(RobotConstants.ELEVATOR_MINIMUM_HEIGHT, RobotConstants.ELEVATOR_MAXIMUM_HEIGHT);
		m_PIDController.setOutputRange(-RobotConstants.ELEVATOR_MAX_OUTPUT + 0.3,
				RobotConstants.ELEVATOR_MAX_OUTPUT + 0.2);
		m_PIDController.setAbsoluteTolerance(2.5);

		m_motorGroup.setInverted(true);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new ElevatorWithJoystick());
	}

	/**
	 * Enable the PID controller
	 */
	public void enablePID() {
		m_PIDController.enable();
	}

	/**
	 * Disable the PID controller
	 */
	public void disablePID() {
		m_PIDController.disable();
	}

	/**
	 * @return {@code true} if the controller is enabled
	 */
	public boolean getPIDEnabled() {
		return m_PIDController.isEnabled();
	}

	/**
	 * Update the PID tunings on the elevator
	 */
	public void updatePIDTunings() {
		m_PIDController.setP(RobotConstants.elevatorP);
		m_PIDController.setI(RobotConstants.elevatorI);
		m_PIDController.setD(RobotConstants.elevatorD);
	}

	public void setMinimumIsUpper(boolean isUpper) {
		if (isUpper)
			minHeight = RobotConstants.ELEVATOR_INTERFERE_MAX;
		else
			minHeight = RobotConstants.ELEVATOR_MINIMUM_HEIGHT;
	}

	/**
	 * Gets the position of the elevator
	 * 
	 * @return the position of the elevator
	 */
	public double getHeight() {
		return m_analogPot.get();
	}

	private void setBrake(boolean isBreak) {
		if (isBreak)
			m_break.set(Value.kForward);
		else
			m_break.set(Value.kReverse);
	}

	public void off() {
		m_break.set(Value.kOff);
	}

	/**
	 * Manually sets the speed of the motors.
	 * 
	 * @param speed
	 *            The fraction of the motor's maximum speed the motors are to spin
	 *            at. Ranges between -1.0 and 1.0
	 */
	public void set(double speed) {
		if (m_isLocked) {
			m_motorGroup.set(0);
			return;
		}

		speed *= 0.8;

		// // Limit the rate of changing elevator speed
		// double potentialAccel = (speed - getSpeed()) / 0.02;
		// double appliedAccel = Math.signum(potentialAccel)
		// * Math.min(Math.abs(potentialAccel), RobotConstants.ELEVATOR_MAX_ACCEL);
		// double appliedSpeed = getSpeed() + (appliedAccel * 0.02);
		// // Calculate our distance from the end of the elevator
		// double endPointDistance;
		// if (appliedSpeed > 0)
		// endPointDistance = Math.abs(getHeight() -
		// RobotConstants.ELEVATOR_MAXIMUM_HEIGHT);
		// else
		// endPointDistance = Math.abs(getHeight() - minHeight);
		//
		// // If we are close to either endpoint, linearly decrease the speed to prevent
		// // slamming into the hard stop
		// if (endPointDistance < 10)
		// appliedSpeed = Math.signum(appliedSpeed) * Math.min(Math.abs(appliedSpeed),
		// endPointDistance / 10);
		//
		// if (getHeight() > RobotConstants.ELEVATOR_MAXIMUM_HEIGHT && appliedSpeed > 0)
		// appliedSpeed = 0;
		//
		// if (getHeight() < minHeight && appliedSpeed < 0)
		// appliedSpeed = 0.15;

		m_motorGroup.set(speed);
	}

	/**
	 * @return the average speed of both elevator MotorControllerGroups
	 */
	public double getSpeed() {
		return m_motorGroup.get();
	}

	/**
	 * Sets the elevator height setpoint.
	 * 
	 * @param d_point
	 *            the height setpoint in inches.
	 */
	public void setSetpoint(double d_point) {
		if (!m_isLocked)
			m_PIDController.setSetpoint(d_point);
	}

	/**
	 * @return Whether or not the current height matches the height setpoint.
	 */
	public boolean getOnTarget() {
		return m_PIDController.onTarget();
	}

	public double getSetpoint() {
		return m_PIDController.getSetpoint();
	}

	public void lock() {
		set(0);
		setSetpoint(getSetpoint());
		setBrake(true);
		m_isLocked = true;
	}

	public void unlock() {
		setBrake(false);
		m_isLocked = false;
	}

	public boolean isLocked() {
		return m_isLocked;
	}

	public double getMinHeight() {
		return minHeight;
	}

}
