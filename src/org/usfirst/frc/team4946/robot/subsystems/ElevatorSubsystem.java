package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.RobotMap;
import org.usfirst.frc.team4946.robot.commands.elevator.ElevatorWithJoystick;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * 
 */
public class ElevatorSubsystem extends Subsystem {

	private SpeedControllerGroup m_motors;
	private DoubleSolenoid m_brake;
	private AnalogPotentiometer m_analogPot;

	private PIDController m_PIDController;

	private boolean m_isBrake = false;

	// Min and max height are used for changing the bounds of the elevator when the
	// robot is in certain configurations:
	//
	// 1. When elevator is above arms and arms are up, minimum = interference_max
	// 2. When elevator is below arms and arms are up, maximum = interference_min
	// 3. Whenever the arms are down, full range
	private double maxHeight = RobotConstants.ELEVATOR_MAXIMUM_HEIGHT;
	private double minHeight = RobotConstants.ELEVATOR_MINIMUM_HEIGHT;

	public ElevatorSubsystem() {

		// Setup the acutators and sensors
		m_motors = new SpeedControllerGroup(new WPI_TalonSRX(RobotMap.CAN_ELEVATOR_LEFT),
				new WPI_TalonSRX(RobotMap.CAN_ELEVATOR_RIGHT));
		m_brake = new DoubleSolenoid(RobotMap.PCM_ELEVATOR_BREAK, RobotMap.PCM_ELEVATOR_UNLOCK);
		m_analogPot = new AnalogPotentiometer(RobotMap.ANALOG_ELEVATOR_POT, RobotConstants.ELEVATOR_SCALING_VALUE,
				RobotConstants.ELEVATOR_OFFSET_VALUE);
		m_analogPot.setPIDSourceType(PIDSourceType.kDisplacement);

		// Setup the PID controller
		m_PIDController = new PIDController(0, 0, 0, m_analogPot, m_motors);
		m_PIDController.setInputRange(RobotConstants.ELEVATOR_MINIMUM_HEIGHT, RobotConstants.ELEVATOR_MAXIMUM_HEIGHT);
		updatePIDTunings();

		m_motors.setInverted(true);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new ElevatorWithJoystick());
	}

	/**
	 * Enable the PID controller
	 */
	public void enablePID() {
		setBrake(false);
		m_PIDController.enable();
	}

	/**
	 * Disable the PID controller
	 */
	public void disablePID() {
		setBrake(true);
		m_PIDController.disable();
		m_motors.set(0);
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
		m_PIDController.setP(RobotConstants.kElevator.kP.f(getHeight()));
		m_PIDController.setI(RobotConstants.kElevator.kI.f(getHeight()));
		m_PIDController.setD(RobotConstants.kElevator.kD.f(getHeight()));
		m_PIDController.setOutputRange(RobotConstants.kElevator.kMinOutput.f(getHeight()),
				RobotConstants.kElevator.kMaxOutput.f(getHeight()));
		m_PIDController.setAbsoluteTolerance(RobotConstants.kElevator.kAbsTolerance.f(getHeight()));

		SmartDashboard.putNumber("What My P", m_PIDController.getP());
		SmartDashboard.putNumber("What My I", m_PIDController.getI());
		SmartDashboard.putNumber("What My D", m_PIDController.getD());
		SmartDashboard.putNumber("What My min", RobotConstants.kElevator.kMinOutput.f(getHeight()));
		SmartDashboard.putNumber("What My max", RobotConstants.kElevator.kMaxOutput.f(getHeight()));
		SmartDashboard.putNumber("What My tol", RobotConstants.kElevator.kAbsTolerance.f(getHeight()));

	}

	public void limitMinHeight(boolean shouldLimit) {
		if (shouldLimit)
			minHeight = RobotConstants.ELEVATOR_INTERFERE_MAX;
		else
			minHeight = RobotConstants.ELEVATOR_MINIMUM_HEIGHT;
	}

	public void limitMaxHeight(boolean shouldLimit) {
		if (shouldLimit)
			maxHeight = RobotConstants.ELEVATOR_INTERFERE_MIN;
		else
			maxHeight = RobotConstants.ELEVATOR_MAXIMUM_HEIGHT;
	}

	public double getMaxHeight() {
		return maxHeight;
	}

	public double getMinHeight() {
		return minHeight;
	}

	/**
	 * Gets the position of the elevator
	 * 
	 * @return the position of the elevator
	 */
	public double getHeight() {
		return m_analogPot.get();
	}

	public void setBrake(boolean isBrake) {
		m_isBrake = isBrake;
		if (m_isBrake)
			m_brake.set(Value.kForward);
		else
			m_brake.set(Value.kReverse);
	}

	public void off() {
		m_brake.set(Value.kOff);
	}

	/**
	 * Manually sets the speed of the motors.
	 * 
	 * @param speed
	 *            The fraction of the motor's maximum speed the motors are to spin
	 *            at. Ranges between -1.0 and 1.0
	 */
	public void set(double speed) {
		// speed *= 0.8;
		//
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

		m_motors.set(speed);

	}

	/**
	 * @return the speed of the elevator motors
	 */
	public double getSpeed() {
		return m_motors.get();
	}

	/**
	 * Sets the elevator height setpoint.
	 * 
	 * @param d_point
	 *            the height setpoint in inches.
	 */
	public void setSetpoint(double d_point) {
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

	public double getError() {
		return m_PIDController.getError();
	}
}
