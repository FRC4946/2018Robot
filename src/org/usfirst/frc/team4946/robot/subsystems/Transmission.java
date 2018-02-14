package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Transmission extends Subsystem {

	private Solenoid m_gearShift;

	public void initDefaultCommand() {
	}

	public Transmission() {
		m_gearShift = new Solenoid(RobotMap.PCM_DRIVE_GEAR);
	}
	
	/**
	 * Toggles between low and high gear on the robot.
	 */
	public void toggleGear() {
		m_gearShift.set(!getGearState());
	}
/**
 * @param sets the gearShift to the returned value 
 */


	/**
	 * @return the state of the gear-shifting solenoid. 
	 */
	public boolean getGearState() {
		return m_gearShift.get();
	}}
/**
 * @return the value of the gear shift, true or false
 */

	
