package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.RobotMap;
import org.usfirst.frc.team4946.robot.commands.ElevatorCommand;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ElevatorSubsystem extends Subsystem {

	
	WPI_TalonSRX m_elevatorMotorLeft = new WPI_TalonSRX (RobotMap.m_elevatorMotorLeft);
	WPI_TalonSRX m_elevatorMotorRight = new WPI_TalonSRX (RobotMap.m_elevatorMotorRight);
	SpeedControllerGroup elevatorMotorGroup = new SpeedControllerGroup 
			(Robot.ElevatorSubsystem.m_elevatorMotorLeft, Robot.ElevatorSubsystem.m_elevatorMotorRight);
	AnalogPotentiometer elevatorAnalogPotentiometer = new AnalogPotentiometer
			(RobotMap.elevatorAnalogPotentiometerPort1,RobotMap.elevatorAnalogPotentiometerPort2,
					RobotMap.elevatorAnalogPotentiometerPort3);
	
	public PIDController m_elevatorPIDController = new PIDController 
			(0.0,0.0,0.0,elevatorAnalogPotentiometer,elevatorMotorGroup);
	//dummy numbers 
	
	public double getElevatorPos() {
		return elevatorAnalogPotentiometer.get();
	}
	
	public void manualMoveElevator(double ButtonValue) {
		double pos = elevatorAnalogPotentiometer.get();
		
		if (pos > RobotConstants.ELEVATOR_MINIMUM_HEIGHT 
				&& pos < RobotConstants.ELEVATOR_MAXIMUM_HEIGHT) {
			elevatorMotorGroup.set(ButtonValue);
		} 
		else if (pos < RobotConstants.ELEVATOR_MINIMUM_HEIGHT) {
			elevatorMotorGroup.set(8.0);
		}
		else if (pos > RobotConstants.ELEVATOR_MAXIMUM_HEIGHT) {
			elevatorMotorGroup.set(24.0);
		}
		
	//oof idk about this
		
	}

	
	
	public void set(double speed) {
		m_elevatorMotorLeft.set(speed);
		m_elevatorMotorRight.set(speed);
	}
	
	
	

	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	
    	setDefaultCommand(new ElevatorCommand());
    	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

