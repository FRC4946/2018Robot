package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.RobotMap;
import org.usfirst.frc.team4946.robot.commands.drivetrain.DriveWithJoystick;
import org.usfirst.frc.team4946.robot.util.NullPIDOutput;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrainSubsystem extends Subsystem {

	public WPI_TalonSRX m_frontLeft;
	private WPI_TalonSRX m_midLeft;
	private WPI_TalonSRX m_rearLeft;
	public WPI_TalonSRX m_frontRight;
	private WPI_TalonSRX m_midRight;
	private WPI_TalonSRX m_rearRight;
	private SpeedControllerGroup m_left, m_right;
	private Encoder m_leftEnc, m_rightEnc;
	private AHRS m_gyro;

	private PIDController /* m_leftPID, m_rightPID, */ m_turnPID;
	private NullPIDOutput m_turnPIDOutput;

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

		m_leftEnc.setDistancePerPulse(RobotConstants.DISTANCE_PER_PULSE);
		m_rightEnc.setDistancePerPulse(RobotConstants.DISTANCE_PER_PULSE);
		m_rightEnc.setReverseDirection(true);

		m_gyro = new AHRS(Port.kMXP);
		m_turnPIDOutput = new NullPIDOutput();

		m_leftEnc.setPIDSourceType(PIDSourceType.kDisplacement);
		m_rightEnc.setPIDSourceType(PIDSourceType.kDisplacement);
		m_gyro.setPIDSourceType(PIDSourceType.kDisplacement);

		// m_leftPID = new PIDController(0, 0, 0, m_leftEnc, m_left);
		// m_leftPID.setAbsoluteTolerance(0.5);
		// m_rightPID = new PIDController(0, 0, 0, m_rightEnc, m_right);
		// m_rightPID.setAbsoluteTolerance(0.5);

		m_turnPID = new PIDController(0, 0, 0, m_gyro, m_turnPIDOutput);
		m_turnPID.setInputRange(0, 360);
		m_turnPID.setContinuous();
		updatePIDTunings();

		calibrateGyro();
		// resetPID();
		// disableDrivePID();

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
	 */
	public void arcadeDrive(double speed, double rotate) {
		if (Math.abs(speed) < 0.125)
			speed = 0.0;

		if (Math.abs(rotate) < 0.125)
			rotate = 0.0;

		double leftSpeed = -speed - rotate;
		double rightSpeed = speed - rotate;

		m_left.set(leftSpeed);
		m_right.set(rightSpeed);
	}

	/**
	 * Drive the robot with a single stick with voltage normalization (Like an
	 * arcade game)
	 * 
	 * @param speed
	 *            the forward/backward speed
	 * @param rotate
	 *            the turning speed
	 * 
	 */
	public void normalizeArcadeDrive(double speed, double rotate) {
		normalizeArcadeDrive(speed, rotate, 10.0);
	}

	/**
	 * Drive the robot with a single stick with voltage normalization (Like an
	 * arcade game)
	 * 
	 * @param speed
	 *            the forward/backward speed
	 * @param rotate
	 * @param voltage
	 *            the voltage to normalize to
	 */
	public void normalizeArcadeDrive(double speed, double rotate, double voltage) {

		if (Math.abs(speed) < 0.125)
			speed = 0.0;

		if (Math.abs(rotate) < 0.125)
			rotate = 0.0;

		double leftSpeed = -speed - rotate;
		double rightSpeed = speed - rotate;

		leftSpeed = Math.min(leftSpeed, 1.0);
		leftSpeed = Math.max(leftSpeed, -1);

		rightSpeed = Math.min(rightSpeed, 1.0);
		rightSpeed = Math.max(rightSpeed, -1);

		m_left.set(leftSpeed * voltage / m_frontLeft.getBusVoltage());
		m_right.set(rightSpeed * voltage / m_frontRight.getBusVoltage());
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
		// m_driveGyro.calibrate();
	}

	/**
	 * @return fetches the angle of gyro
	 */
	public double getGyroAngle() {
		return m_gyro.getAngle();
	}

	/**
	 * @return the PID output of the gyro
	 */
	public double getGyroPIDOutput() {
		return m_turnPIDOutput.getOutput();
	}

	/**
	 * @return the setpoint of the PID controller
	 */
	public double getGyroPIDSetpoint() {
		return m_turnPID.getSetpoint();
	}

	/**
	 * Sets a set point for the gyro
	 * 
	 * @param setpoint
	 *            to set the point of the gyro to
	 */
	public void setGyroSetpoint(double setpoint) {
		m_turnPID.setSetpoint(setpoint);
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
		m_left.set(-left);
		m_right.set(right);
	}

	/**
	 * Update the PID tunings on the DriveTrain
	 */
	public WPI_TalonSRX getFrontLeft() {
		return m_frontLeft;
	}
	
	/**
	 * Update the PID tunings on the DriveTrain
	 */
	public WPI_TalonSRX getFrontRight() {
		return m_frontRight;
	}
	
	/**
	 * Update the PID tunings on the DriveTrain
	 */
	public void updatePIDTunings() {
		// m_leftPID.setP(RobotConstants.driveP);
		// m_leftPID.setI(RobotConstants.driveI);
		// m_leftPID.setD(RobotConstants.driveD);
		// m_rightPID.setP(RobotConstants.driveP);
		// m_rightPID.setI(RobotConstants.driveI);
		// m_rightPID.setD(RobotConstants.driveD);

		m_turnPID.setP(RobotConstants.kTurn.kP);
		m_turnPID.setI(RobotConstants.kTurn.kI);
		m_turnPID.setD(RobotConstants.kTurn.kD);
		m_turnPID.setOutputRange(RobotConstants.kTurn.kMinOutput, RobotConstants.kTurn.kMaxOutput);
		m_turnPID.setAbsoluteTolerance(RobotConstants.kTurn.kAbsTolerance);
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

	// /**
	// * Sets the encoder distance setpoint.
	// *
	// * @param distSetpoint
	// * The distance for the robot to drive in inches.
	// */
	// public void setDistSetpoint(double distSetpoint) {
	// m_leftPID.setSetpoint(distSetpoint);
	// m_rightPID.setSetpoint(distSetpoint);
	// }
	//
	// /**
	// * @return The current encoder setpoint.
	// */
	// public double getDistSetpoint() {
	// return (m_leftPID.getSetpoint() + m_rightPID.getSetpoint()) / 2;
	// }
	//
	// /**
	// * @return Whether or not the current distance matches the distance setpoint
	// on
	// * both sides.
	// */
	// public boolean getDistOnTarget() {
	// return m_leftPID.onTarget() && m_rightPID.onTarget();
	// }

	/**
	 * @return Whether or not the current angle matches the gyro setpoint.
	 */
	public boolean getGyroOnTarget() {
		return m_turnPID.onTarget();
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
		m_gyro.reset();
	}

	/**
	 * Resets the gyroPID object.
	 */
	public void resetGyroPID() {
		m_turnPID.reset();
	}

	// /**
	// * Enables the drivetrain PID objects.
	// */
	// public void enableDrivePID() {
	// m_leftPID.enable();
	// m_rightPID.enable();
	// }

	/**
	 * Enables the gyro PID objects.
	 */
	public void enableGyroPID() {
		m_turnPID.enable();
	}

	// /**
	// * Disables the drivetrain PID objects.
	// */
	// public void disableDrivePID() {
	// m_leftPID.disable();
	// m_rightPID.disable();
	// }

	/**
	 * Disables the gyro PID objects.
	 */
	public void disableGyroPID() {
		m_turnPID.disable();
	}

	// /**
	// * Resets the drivetrain PID objects.
	// */
	// public void resetPID() {
	// m_leftPID.reset();
	// m_rightPID.reset();
	// }

	/**
	 * @return the average distance traveled between both encoders
	 */
	public double getEncoderDistance() {
		return (m_leftEnc.getDistance() + m_rightEnc.getDistance()) / 2.0;
	}

	public double getLeftEncDist() {
		return m_leftEnc.getDistance();
	}

	public double getRightEncDist() {
		return m_rightEnc.getDistance();
	}
	//
	// /**
	// * Sets the maximum speed output for both PID objects
	// *
	// * @param speed
	// * The upper bound for the output range of the PIDObjects. The
	// * negative speed is set as the lower bound.
	// */
	// public void setMaxSpeed(double speed) {
	// m_leftPID.setOutputRange(-speed, speed);
	// m_rightPID.setOutputRange(-speed, speed);
	// }

	public double getGyroPIDError() {
		return m_turnPID.getError();
	}
}
