package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ElevatorTransmission extends Subsystem {

	Solenoid m_transmissionSolenoid = new Solenoid(RobotMap.PCM_ELEVATOR_GEAR);

	public void initDefaultCommand() {

	}

	/**
	 * Sets solenoid to change gear of elevator
	 * 
	 * @param position
	 *            true is closed, false is open
	 */
	public void setSolenoid(boolean position) {
		m_transmissionSolenoid.set(position);
	}

	/**
	 * Toggles the position of the elevator gearshift.
	 */
	public void toggleSolenoid() {
		m_transmissionSolenoid.set(!getSolenoidPos());
	}

	/**
	 * @return the position of the solenoid. True is closed, false is open.
	 */
	public boolean getSolenoidPos() {
		return m_transmissionSolenoid.get();
	}
}