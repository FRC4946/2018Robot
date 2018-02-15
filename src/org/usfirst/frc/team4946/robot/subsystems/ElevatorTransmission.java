package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ElevatorTransmission extends Subsystem {

	DoubleSolenoid m_transmissionSolenoid = new DoubleSolenoid (RobotMap.PCM_ELEVATOR_GEARLEFT, RobotMap.PCM_ELEVATOR_GEARRIGHT);
	
    public void initDefaultCommand() {
        
    }
   
    /**
     * Moves solenoid to change gear of elevator
     * @param position true is closed, false is open
     */
    public void moveSolenoid (Value position) { 
    	m_transmissionSolenoid.set(position);
    }
    
    /**
     * Toggles the position of the elevator gearshift.
     */
    public void toggleSolenoid() {
    	
    	if(m_transmissionSolenoid.get() == Value.kForward || m_transmissionSolenoid.get() == Value.kOff) {
    		m_transmissionSolenoid.set(Value.kReverse);
    	} else {
    		m_transmissionSolenoid.set(Value.kForward);
    	}
    }
    
    /**
     * @return the position of the solenoid. True is closed, false is open.
     */
    public Value getSolenoidPos() {
    	return m_transmissionSolenoid.get();
    }
}