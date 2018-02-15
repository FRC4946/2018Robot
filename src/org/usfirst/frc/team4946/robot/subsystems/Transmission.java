package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Transmission extends Subsystem {

	private DoubleSolenoid m_gearShift;

	public void initDefaultCommand() {
	}

	public Transmission() {
		m_gearShift = new DoubleSolenoid(RobotMap.PCM_DRIVE_GEARLEFT, RobotMap.PCM_DRIVE_GEARRIGHT);
	}
	
	/**
	 * Toggles between low and high gear on the robot.
	 */
	public void toggleGear() {
		
		if(m_gearShift.get() == Value.kForward || m_gearShift.get() == Value.kOff) {
    		m_gearShift.set(Value.kReverse);
    	} else {
    		m_gearShift.set(Value.kForward);
    	}
	}
/**
 * @param sets the gearShift to the returned value 
 */


	/**
	 * @return the state of the gear-shifting solenoid. 
	 */
	public Value getGearState() {
		return m_gearShift.get();
	}}
/**
 * @return the value of the gear shift, true or false
 */

	
