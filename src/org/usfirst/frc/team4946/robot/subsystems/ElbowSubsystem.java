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
    
    /**
	 * Sets the external intake to the down position
	 */
    public void setElbowDown() {
    	m_elbowSwitch.set(Value.kOff);
    }
    
    /**
	 * Lifts the external intake up
	 */
    public void setElbowUp() {
    	m_elbowSwitch.set(Value.kForward);
    }
    
    /**
	 * @return the current elbow position. kOff indicates the down position. kForward indicates the up position
	 */
    public Value getElbowValue() {
    	return m_elbowSwitch.get();
    }
}

