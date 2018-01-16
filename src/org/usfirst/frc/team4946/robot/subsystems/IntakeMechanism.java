package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotMap;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeMechanism extends Subsystem {
	
	private static VictorSP m_leftMotor = new VictorSP(RobotMap.i_intakeLeftMotorPort);
	private static VictorSP m_rightMotor = new VictorSP(RobotMap.i_intakeLeftMotorPort);
	
	public void disableMechanism() { //Disables both motors
		m_leftMotor.set(0);
		m_rightMotor.set(0); //Equivalent to .disable
	}
	
	public void spin(double d_speed) { //Takes values from -1.0 to 1.0. Spins the motors, positive is forwards, negative is backwards (Maybe, or it might be the other way around).
		m_leftMotor.set(d_speed);
		m_rightMotor.set(-1.0*d_speed);		
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

