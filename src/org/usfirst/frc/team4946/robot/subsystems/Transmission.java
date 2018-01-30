package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Transmission extends Subsystem {

	// Create Solenoid
	private Solenoid m_gearShift;

	public void initDefaultCommand() {
	}

	public Transmission() {
		m_gearShift = new Solenoid(RobotMap.PCM_DRIVE_GEAR);
	}

	public void toggleGear() {
		m_gearShift.set(!getGearState());
	}

	public boolean getGearState() {
		return m_gearShift.get();
	}
}