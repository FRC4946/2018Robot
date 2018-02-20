package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ElevatorTransmissionSubsystem extends Subsystem {
	private DoubleSolenoid m_transmissionSolenoid;
	private boolean m_isClimb;

	public ElevatorTransmissionSubsystem() {
		m_transmissionSolenoid = new DoubleSolenoid(RobotMap.PCM_ELEVATOR_GEARLEFT, RobotMap.PCM_ELEVATOR_GEARRIGHT);
		m_isClimb = false;
	}

	public void initDefaultCommand() {
	}

	/**
	 * Set the gear of the elevator
	 * 
	 * @param isHigh
	 *            low gear/climb ({@code true}) or high gear/elevator
	 *            ({@code false})
	 */
	public void set(boolean isClimb) {
		if (isClimb)
			m_transmissionSolenoid.set(Value.kReverse);
		else
			m_transmissionSolenoid.set(Value.kForward);

		m_isClimb = isClimb;
	}

	/**
	 * @return {@code true} if the gear is low (climb)
	 */
	public boolean getIsClimb() {
		return m_isClimb;
	}

	/**
	 * Toggle between low and high gear
	 */
	public void toggle() {
		set(!m_isClimb);
	}

	/**
	 * Turn off the solenoid
	 */
	public void off() {
		// m_transmissionSolenoid.set(Value.kOff);
	}

}