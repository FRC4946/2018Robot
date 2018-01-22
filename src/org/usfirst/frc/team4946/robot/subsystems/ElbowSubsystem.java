package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ElbowSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	Solenoid m_leftElbow = new Solenoid(RobotMap.PCM_ELBOW_LEFT);
	Solenoid m_rightElbow = new Solenoid(RobotMap.PCM_ELBOW_RIGHT);
	

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void setElbowDown() {
    	m_leftElbow.set(true);
    	m_rightElbow.set(true);
    }
    
    public void setElbowUp() {
    	m_leftElbow.set(false);
    	m_rightElbow.set(false);
    }
    
    public boolean getElbowPos() {
    	return m_leftElbow.get();
    }
}

