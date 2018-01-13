package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.RobotMap;
import org.usfirst.frc.team4946.robot.commands.DriveWithJoysticks;
import org.usfirst.frc.team4946.robot.util.PIDSourceGroup;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class DriveTrain extends Subsystem {
	
	//Create motors, controller groups, and drives
	WPI_TalonSRX m_frontLeft = new WPI_TalonSRX(RobotMap.k_LeftFront);
	WPI_TalonSRX m_rearLeft = new WPI_TalonSRX(RobotMap.k_LeftBack);
	SpeedControllerGroup m_left = new SpeedControllerGroup(m_frontLeft, m_rearLeft);

	WPI_TalonSRX m_frontRight = new WPI_TalonSRX(RobotMap.k_RightFront);
	WPI_TalonSRX m_rearRight = new WPI_TalonSRX(RobotMap.k_RightBack);
	SpeedControllerGroup m_right = new SpeedControllerGroup(m_frontRight, m_rearRight);

	DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);

	//Create encoders and gyro
	Encoder m_leftEnc = new Encoder(RobotMap.k_DIO_LeftEnc1, RobotMap.k_DIO_LeftEnc2);
	Encoder m_rightEnc = new Encoder(RobotMap.k_DIO_RightEnc1, RobotMap.k_DIO_RightEnc2);
	
	//Create Solenoid
	Solenoid m_gearShift = new Solenoid(RobotMap.k_DIO_GearShifter);
	
	//PID
	PIDSourceGroup m_encPID = new PIDSourceGroup(m_leftEnc, m_rightEnc);
	PIDController m_distPIDLeft = new PIDController(RobotConstants.k_leftDistEncP, RobotConstants.k_leftDistEncI, RobotConstants.k_leftDistEncD, m_encPID, m_left);
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		// Default command uses the joystick to drive
		setDefaultCommand(new DriveWithJoysticks());
	}
	
	//drive function with no throttle
	public void driveRobot(double spd, double rot) {
		driveRobot(spd, rot, 1.0); 
	}
	
	//drive function with throttle
	public void driveRobot(double spd, double rot, double throttle) {
		spd *= (0.5 + (0.5*throttle));
		rot *= (0.5 + (0.5*throttle));
		m_drive.arcadeDrive(spd,  rot);
	}

	public void toggleGear() {
		m_gearShift.set(!getGearState());
	}
	
	public boolean getGearState() {
		return m_gearShift.get();
	}
}
