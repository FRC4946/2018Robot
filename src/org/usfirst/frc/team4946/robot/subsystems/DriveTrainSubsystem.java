package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.RobotMap;
import org.usfirst.frc.team4946.robot.commands.drivetrain.DriveWithJoystick;
import org.usfirst.frc.team4946.robot.util.NullPIDOutput;
import org.usfirst.frc.team4946.robot.util.imu.SkewIMU;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrainSubsystem extends Subsystem {

	private WPI_TalonSRX m_frontLeft, m_midLeft, m_rearLeft, m_frontRight, m_midRight, m_rearRight;
	private SpeedControllerGroup m_left, m_right;

	private Encoder m_leftEnc, m_rightEnc;

	private GyroBase m_driveGyro;

	private PIDController m_leftPID, m_rightPID, m_gyroPID;
	private NullPIDOutput m_gyroPIDOutput;
	private double distancePerPulse;

	public DriveTrainSubsystem() {

		m_frontLeft = new WPI_TalonSRX(RobotMap.CAN_DRIVE_LEFTFRONT);
		m_midLeft = new WPI_TalonSRX(RobotMap.CAN_DRIVE_LEFTMID);
		m_rearLeft = new WPI_TalonSRX(RobotMap.CAN_DRIVE_LEFTBACK);
		m_frontRight = new WPI_TalonSRX(RobotMap.CAN_DRIVE_RIGHTFRONT);
		m_midRight = new WPI_TalonSRX(RobotMap.CAN_DRIVE_RIGHTMID);
		m_rearRight = new WPI_TalonSRX(RobotMap.CAN_DRIVE_RIGHTBACK);

		m_left = new SpeedControllerGroup(m_frontLeft, m_midLeft, m_rearLeft);
		m_right = new SpeedControllerGroup(m_frontRight, m_midRight, m_rearRight);

		m_leftEnc = new Encoder(RobotMap.DIO_DRIVE_LEFTENC1, RobotMap.DIO_DRIVE_LEFTENC2);
		m_rightEnc = new Encoder(RobotMap.DIO_DRIVE_RIGHTENC1, RobotMap.DIO_DRIVE_RIGHTENC2);

		m_driveGyro = new SkewIMU();

		m_gyroPIDOutput = new NullPIDOutput();

		m_leftEnc.setPIDSourceType(PIDSourceType.kDisplacement);
		m_rightEnc.setPIDSourceType(PIDSourceType.kDisplacement);
		m_driveGyro.setPIDSourceType(PIDSourceType.kDisplacement);

		m_leftPID = new PIDController(RobotConstants.leftDriveP, RobotConstants.leftDriveI, RobotConstants.leftDriveD,
				m_leftEnc, m_left);
		m_rightPID = new PIDController(RobotConstants.leftDriveP, RobotConstants.leftDriveI, RobotConstants.leftDriveD,
				m_rightEnc, m_right);
		m_gyroPID = new PIDController(0.0, 0.0, 0.0, m_driveGyro, m_gyroPIDOutput);
		m_gyroPID.setInputRange(0, 360);
		m_gyroPID.setOutputRange(-1.0, 1.0);
		m_gyroPID.setContinuous();

		calibrateGyro();
		resetPID();
		disablePID();

		m_leftPID.setAbsoluteTolerance(0.5); // Dummy
		m_rightPID.setAbsoluteTolerance(0.5); // Dummy
		m_gyroPID.setAbsoluteTolerance(1.0); // Dummy
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoystick());
	}

	/**
	 * Drive the robot with a single stick (Like an arcade game)
	 * 
	 * @param speed
	 *            the forward/backward speed
	 * @param rotate
	 *            the turning speed
	 */
	public void arcadeDrive(double speed, double rotate) {
		arcadeDrive(speed, rotate, 1.0);
	}

	/**
	 * Drive the robot with a single stick (Like an arcade game)
	 * 
	 * @param speed
	 *            the forward/backward speed
	 * @param rotate
	 *            the turning speed
	 * @param throttle
	 *            the scaling factor
	 */
	public void arcadeDrive(double speed, double rotate, double throttle) {

		speed *= (0.5 + (0.5 * throttle));
		rotate *= (0.5 + (0.5 * throttle));

		if (Math.abs(speed) < 0.125)
			speed = 0.0;

		if (Math.abs(rotate) < 0.125)
			rotate = 0.0;

		m_left.set(-(speed + rotate));
		m_right.set(-(speed - rotate));
	}

	/**
	 * @return the average speed of both drivetrain MotorControllerGroups
	 */
	public double getAvgSpeed() {
		return ((m_left.get() + m_right.get()) / 2);
	}

	/**
	 * @return the speed of the left drivetrain MotorControllerGroup
	 */
	public double getSpeedLeft() {
		return m_left.get();
	}

	/**
	 * @return the speed of the right drivetrain MotorControllerGroup
	 */
	public double getSpeedRight() {
		return m_right.get();
	}

	/**
	 * Calibrates the gyro
	 */
	public void calibrateGyro() {
		m_driveGyro.calibrate();
	}

	/**
	 * @return fetches the angle of gyro
	 */
	public double getGyroAngle() {
		return m_driveGyro.getAngle();
	}

	/**
	 * @return the PID output of the gyro
	 */
	public double getGyroPIDOutput() {
		return m_gyroPIDOutput.getOutput();
	}

	/**
	 * Sets a set point for the gyro
	 * 
	 * @param setpoint
	 *            to set the point of the gyro to
	 */
	public void setGyroSetpoint(double setpoint) {
		m_gyroPID.setSetpoint(setpoint);
	}

	/**
	 * Drive the robot with two sticks (eg. one for left, one for right)
	 * 
	 * @param left
	 *            the speed for the left side
	 * @param right
	 *            the speed for the right side
	 */
	public void tankDrive(double left, double right) {
		m_left.set(left);
		m_right.set(right);
	}

	/**
	 * Update the PID tunings on the DriveTrain
	 */
	public void updatePIDTunings() {
		m_leftPID.setP(RobotConstants.leftDriveP);
		m_leftPID.setI(RobotConstants.leftDriveI);
		m_leftPID.setD(RobotConstants.leftDriveD);
		m_rightPID.setP(RobotConstants.leftDriveP);
		m_rightPID.setI(RobotConstants.leftDriveI);
		m_rightPID.setD(RobotConstants.leftDriveD);
	}

	/**
	 * Reset the encoder distances to 0
	 * 
	 * @see Encoder#reset()
	 */
	public void resetEncoders() {
		m_leftEnc.reset();
		m_rightEnc.reset();
	}

	/**
	 * Stop the robot completely by setting speed to 0.0.
	 */
	public void stop() {
		m_left.set(0.0);
		m_right.set(0.0);
	}

	/**
	 * Sets the encoder distance setpoint.
	 * 
	 * @param distSetpoint
	 *            The distance for the robot to drive in inches.
	 */
	public void setDistSetpoint(double distSetpoint) {
		m_leftPID.setSetpoint(distSetpoint);
		m_rightPID.setSetpoint(distSetpoint);
	}

	/**
	 * @return The current encoder setpoint.
	 */
	public double getDistSetpoint() {
		return (m_leftPID.getSetpoint() + m_rightPID.getSetpoint()) / 2;
	}

	/**
	 * @return Whether or not the current distance matches the distance setpoint on
	 *         both sides.
	 */
	public boolean getDistOnTarget() {
		return m_leftPID.onTarget() && m_rightPID.onTarget();
	}

	/**
	 * @return Whether or not the current angle matches the gyro setpoint.
	 */
	public boolean getGyroOnTarget() {
		return m_gyroPID.onTarget();
	}

	/**
	 * Resets the encoders.
	 */
	public void resetEncs() {
		m_leftEnc.reset();
		m_rightEnc.reset();
	}

	/**
	 * Resets the gyro.
	 */
	public void resetGyro() {
		m_driveGyro.reset();
	}

	/**
	 * Resets the gyroPID object.
	 */
	public void resetGyroPID() {
		m_gyroPID.reset();
	}

	/**
	 * Enables the drivetrain PID objects.
	 */
	public void enablePID() {
		m_leftPID.enable();
		m_rightPID.enable();
	}

	/**
	 * Disables the drivetrain PID objects.
	 */
	public void disablePID() {
		m_leftPID.disable();
		m_rightPID.disable();
	}

	/**
	 * Resets the drivetrain PID objects.
	 */
	public void resetPID() {
		m_leftPID.reset();
		m_rightPID.reset();
	}

	/**
	 * Sets the encoder count to distance conversion, based on the gear settings.
	 */
	public void setEncoderDPP() {

		if (Robot.transmissionSubsystem.getGearState() == Value.kReverse) {

			distancePerPulse = RobotConstants.WHEEL_DIA * Math.PI / RobotConstants.ENCODER_PPR
					* RobotConstants.DRIVETRAIN_GEARBOX_REDUCTION_HIGH;
			m_leftEnc.setDistancePerPulse(distancePerPulse);
			m_rightEnc.setDistancePerPulse(distancePerPulse);

		} else {

			distancePerPulse = RobotConstants.WHEEL_DIA * Math.PI / RobotConstants.ENCODER_PPR
					* RobotConstants.DRIVETRAIN_GEARBOX_REDUCTION_LOW;
			m_leftEnc.setDistancePerPulse(distancePerPulse);
			m_rightEnc.setDistancePerPulse(distancePerPulse);
		}
	}

	/**
	 * @return the average distance traveled between both encoders
	 */
	public double getEncoderDistance() {
		return (m_leftEnc.getDistance() + m_rightEnc.getDistance()) / 2.0;
	}

	/**
	 * Sets the maximum speed output for both PID objects
	 * 
	 * @param speed
	 *            The upper bound for the output range of the PIDObjects. The
	 *            negative speed is set as the lower bound.
	 */
	public void setMaxSpeed(double speed) {
		m_leftPID.setOutputRange(-speed, speed);
		m_rightPID.setOutputRange(-speed, speed);
	}
}
