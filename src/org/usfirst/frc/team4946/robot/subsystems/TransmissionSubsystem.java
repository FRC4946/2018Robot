package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class TransmissionSubsystem extends Subsystem {

	private DoubleSolenoid m_gearShift;
	private boolean m_isHigh;

	public void initDefaultCommand() {
		
	}

	public TransmissionSubsystem() {
		
		m_gearShift = new DoubleSolenoid(RobotMap.PCM_DRIVE_GEARLEFT, RobotMap.PCM_DRIVE_GEARRIGHT);
		
		if(m_gearShift.get() == Value.kForward)
			m_isHigh = true;
		else
			m_isHigh = false;
	}

	/**
	 * Toggles between low and high gear on the robot.
	 */
	public void toggleGear() {
		setGear(!m_isHigh);
	}
	
	public void setGear(boolean high) {
		
		if(high)
			m_gearShift.set(Value.kReverse);
		else
			m_gearShift.set(Value.kForward);
		
		m_isHigh = high;
	}
	
	public boolean getGearIsHigh() {
		return m_isHigh;
	}
	
	public void setGearOff() {
		
		m_gearShift.set(Value.kOff);
	}

	/**
	 * @return the state of the gear-shifting solenoid.
	 */
	public Value getGearState() {
		return m_gearShift.get();
	}
}