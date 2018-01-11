package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.OI;
import org.usfirst.frc.team4946.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.PWMTalonSRX;
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
	
	PWMTalonSRX m_frontLeft = new PWMTalonSRX(RobotMap.k_LeftFront);
	PWMTalonSRX m_rearLeft = new PWMTalonSRX(RobotMap.k_LeftBack);
	SpeedControllerGroup m_left = new SpeedControllerGroup(m_frontLeft, m_rearLeft);

	PWMTalonSRX m_frontRight = new PWMTalonSRX(RobotMap.k_RightFront);
	PWMTalonSRX m_rearRight = new PWMTalonSRX(RobotMap.k_RightBack);
	SpeedControllerGroup m_right = new SpeedControllerGroup(m_frontRight, m_rearRight);

	DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);


	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	
	public void driveRobot(double spd, double rot) {
		m_drive.arcadeDrive(spd, rot);
	}
	
}