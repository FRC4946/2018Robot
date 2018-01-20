package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.RobotMap;
import org.usfirst.frc.team4946.robot.commands.DriveWithJoysticks;
import org.usfirst.frc.team4946.robot.util.PIDSourceGroup;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {

	// Create motors, controller groups, and drives
	private WPI_TalonSRX m_frontLeft = new WPI_TalonSRX(RobotMap.k_LeftFront),
			m_midLeft = new WPI_TalonSRX(RobotMap.k_LeftMid), m_rearLeft = new WPI_TalonSRX(RobotMap.k_LeftBack),
			m_frontRight = new WPI_TalonSRX(RobotMap.k_RightFront), m_midRight = new WPI_TalonSRX(RobotMap.k_RightMid),
			m_rearRight = new WPI_TalonSRX(RobotMap.k_RightBack);

	private SpeedControllerGroup m_left = new SpeedControllerGroup(m_frontLeft, m_midLeft, m_rearLeft),
			m_right = new SpeedControllerGroup(m_frontRight, m_midRight, m_rearRight);

	// Create encoders and gyro
	private Encoder m_leftEnc = new Encoder(RobotMap.k_DIO_LeftEnc1, RobotMap.k_DIO_LeftEnc2);
	private Encoder m_rightEnc = new Encoder(RobotMap.k_DIO_RightEnc1, RobotMap.k_DIO_RightEnc2);

	// Create Solenoid
	private Solenoid m_gearShift = new Solenoid(RobotMap.k_DIO_GearShifter);

	// PID
	private PIDSourceGroup m_encPID = new PIDSourceGroup(m_leftEnc, m_rightEnc);
	// m_encPID.setPIDSourceType();
	private PIDController m_distPID = new PIDController(RobotConstants.leftDriveP, RobotConstants.leftDriveI,
			RobotConstants.leftDriveD, m_encPID, /* (PIDOutput) m_drive */ null);

	public DriveTrain() {

		m_encPID.setPIDSourceType(PIDSourceType.kDisplacement);
		m_distPID.setAbsoluteTolerance(0.5); // Dummy
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoysticks());
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

		m_left.set(speed - rotate);
		m_right.set(speed + rotate);
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
		m_distPID.setP(RobotConstants.leftDriveP);
		m_distPID.setI(RobotConstants.leftDriveI);
		m_distPID.setD(RobotConstants.leftDriveD);
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

	public void toggleGear() {
		m_gearShift.set(!getGearState());
	}

	public boolean getGearState() {
		return m_gearShift.get();
	}

	public void setDistSetpoint(double distSetpoint) {
		m_distPID.setSetpoint(distSetpoint);
	}

	public double getDistSetpoint() {
		return m_distPID.getSetpoint();
	}

	public boolean getDistOnTarget() {
		return m_distPID.onTarget();
	}
}
