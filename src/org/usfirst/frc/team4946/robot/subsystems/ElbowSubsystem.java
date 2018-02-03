package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotMap;
import org.usfirst.frc.team4946.robot.commands.elbow.ManageElbowPos;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ElbowSubsystem extends Subsystem {

    DoubleSolenoid m_elbowSwitch;
	
    public ElbowSubsystem() {
    	m_elbowSwitch = new DoubleSolenoid(RobotMap.PCM_ELBOW_LEFT, RobotMap.PCM_ELBOW_RIGHT);
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new ManageElbowPos());
    }
    
    public void setElbowDown() {
    	m_elbowSwitch.set(Value.kOff);
    }
    
    public void setElbowUp() {
    	m_elbowSwitch.set(Value.kForward);
    }
    
    public Value getElbowValue() {
    	return m_elbowSwitch.get();
    }
}

