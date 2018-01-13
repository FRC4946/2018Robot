package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotMap;
import org.usfirst.frc.team4946.robot.commands.DriveWithJoysticks;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class DriveTrain extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    //setDefaultCommand(new MySpecialCommand());
	
	WPI_TalonSRX m_frontLeft = new WPI_TalonSRX(RobotMap.k_LeftFront);
	WPI_TalonSRX m_rearLeft = new WPI_TalonSRX(RobotMap.k_LeftBack);
	SpeedControllerGroup m_left = new SpeedControllerGroup(m_frontLeft, m_rearLeft);

	WPI_TalonSRX m_frontRight = new WPI_TalonSRX(RobotMap.k_RightFront);
	WPI_TalonSRX m_rearRight = new WPI_TalonSRX(RobotMap.k_RightBack);
	SpeedControllerGroup m_right = new SpeedControllerGroup(m_frontRight, m_rearRight);

	DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);


	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new DriveWithJoysticks());
	}
	
	public void driveRobot(double spd, double rot) {
		m_drive.arcadeDrive(spd, rot);
	}
	
}