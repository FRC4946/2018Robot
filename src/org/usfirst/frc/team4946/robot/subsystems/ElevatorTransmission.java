package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ElevatorTransmission extends Subsystem {

	Solenoid m_transmissionSolenoid = new Solenoid (RobotMap.PCM_ELEVATOR_GEAR);
	
    public void initDefaultCommand() {
        
    }
   
    /**
     * Moves solenoid to change gear of elevator
     * @param position true is closed, false is open
     */
    public void moveSolenoid (boolean position) { //True Is Closed, False is Open
    	m_transmissionSolenoid.set(position);
    }
    
    /**
     * @return the position of the solenoid. True is closed, false is open.
     */
    public boolean getSolenoidPos() {
    	return m_transmissionSolenoid.get();
    }
}