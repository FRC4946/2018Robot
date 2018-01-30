package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.RobotMap;
import org.usfirst.frc.team4946.robot.commands.DriveWithJoysticks;
import org.usfirst.frc.team4946.robot.util.NullPIDOutput;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
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
	
	//Create motors, controller groups, and drives
	private WPI_TalonSRX m_frontLeft, 
			m_midLeft, 
			m_rearLeft,
			m_frontRight,
			m_midRight,
			m_rearRight;
	
	private SpeedControllerGroup m_left,
			m_right;

	//Create encoders and gyro
	private Encoder m_leftEnc,
		m_rightEnc;
	
	private ADXRS450_Gyro m_driveGyro;
	private NullPIDOutput m_gyroPIDOutput;
	//PID
	//PIDSourceGroup m_encPID = new PIDSourceGroup(m_leftEnc, m_rightEnc);
    //m_encPID.setPIDSourceType();
	private PIDController m_leftPID,
		m_rightPID, 
		m_gyroPID;
	
	private double distancePerPulse;
	
	public DriveTrain() {
		
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
		
		m_driveGyro = new ADXRS450_Gyro();
		m_gyroPIDOutput = new NullPIDOutput();
		
		m_leftEnc.setPIDSourceType(PIDSourceType.kDisplacement);
		m_rightEnc.setPIDSourceType(PIDSourceType.kDisplacement);
		m_driveGyro.setPIDSourceType(PIDSourceType.kDisplacement);
		
		m_leftPID = new PIDController(RobotConstants.k_leftDistEncP, RobotConstants.k_leftDistEncI, RobotConstants.k_leftDistEncD, m_leftEnc, m_left);
		m_rightPID = new PIDController(RobotConstants.k_leftDistEncP, RobotConstants.k_leftDistEncI, RobotConstants.k_leftDistEncD, m_rightEnc, m_right);
		m_gyroPID = new PIDController(0.0, 0.0, 0.0, m_driveGyro, m_gyroPIDOutput);
		m_gyroPID.setContinuous();
		
		calibrateGyro();
		resetPID();
		enablePID();
		
		m_leftPID.setAbsoluteTolerance(0.5); //Dummy
		m_rightPID.setAbsoluteTolerance(0.5); //Dummy
		m_gyroPID.setAbsoluteTolerance(1.0); //Dummy
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoysticks());
	}
	
	
	//drive function with no throttle
	public void arcadeDrive(double speed, double rotate) {
		arcadeDrive(speed, rotate, 1.0); 
	}
	
	public void calibrateGyro() {
		m_driveGyro.calibrate();
	}
	
	public double getGyroAngle() {
		return m_driveGyro.getAngle();
	}
	
	public double getGyroPIDOutput() {
		return m_gyroPIDOutput.getOutput();
	}

	public void setGyroSetpoint(double setpoint) {
		m_gyroPID.setSetpoint(setpoint);
	}
	
	//drive function with throttle
	public void arcadeDrive(double speed, double rotate, double throttle) {
		speed *= (0.5 + (0.5*throttle));
		rotate *= (0.5 + (0.5*throttle));
		
		//m_drive.arcadeDrive(spd,  rot);
		m_left.set(speed - rotate);
		m_right.set(speed + rotate);
	}
	
	public void tankDrive(double left, double right) {
		m_left.set(left);
		m_right.set(right);
	}

	public void stop() {
		m_left.set(0.0);
		m_right.set(0.0);
	}
	
	public void setDistSetpoint(double distSetpoint) {
		m_leftPID.setSetpoint(distSetpoint);
		m_rightPID.setSetpoint(distSetpoint); 
	}
	
	public double getDistSetpoint() {
		return (m_leftPID.getSetpoint() + m_rightPID.getSetpoint())/2;
	}
	
	public boolean getEncOnTarget() {
		return m_leftPID.onTarget() && m_rightPID.onTarget();
	}
	
	public boolean getGyroOnTarget() {
		return m_gyroPID.onTarget();
	}
	
	public void resetEncs() {
		m_leftEnc.reset();
		m_rightEnc.reset();
	}
	
	public void resetGyro() {
		m_driveGyro.reset();
	}
	
	public void resetGyroPID() {
		m_gyroPID.reset();
	}
	
	public void enablePID() {
		m_leftPID.enable();
		m_rightPID.enable();
	}
	
	public void disablePID() {
		m_leftPID.disable();
		m_rightPID.disable();
	}
	
	public void resetPID() {
		m_leftPID.reset();
		m_rightPID.reset();
	}
	
	public void setEncoderDPP() {	
		if(Robot.transmission.getGearState()) {
			
			distancePerPulse = RobotConstants.WHEEL_DIA * Math.PI
					/ RobotConstants.ENCODER_PPR * RobotConstants.GEARBOX_REDUCTION_HIGH;
			m_leftEnc.setDistancePerPulse(distancePerPulse);
			m_rightEnc.setDistancePerPulse(distancePerPulse);
			
		} else {
			
			distancePerPulse = RobotConstants.WHEEL_DIA * Math.PI
					/ RobotConstants.ENCODER_PPR * RobotConstants.GEARBOX_REDUCTION_LOW;
			m_leftEnc.setDistancePerPulse(distancePerPulse);
			m_rightEnc.setDistancePerPulse(distancePerPulse);
			
		}
	}
	
	public double getEncoderDistance() {
		return (m_leftEnc.getDistance() + m_rightEnc.getDistance())/2.0;
	}

	public void setMaxSpeed(double speed) {
		m_leftPID.setOutputRange(-speed, speed);
		m_rightPID.setOutputRange(-speed, speed);
	}
}
