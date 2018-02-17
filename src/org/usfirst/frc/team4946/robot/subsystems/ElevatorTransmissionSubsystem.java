package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ElevatorTransmissionSubsystem extends Subsystem {
	DoubleSolenoid m_transmissionSolenoid1 = new DoubleSolenoid(RobotMap.PCM_ELEVATOR_GEARLEFT,
			RobotMap.PCM_ELEVATOR_GEARRIGHT);

	public void initDefaultCommand() {

	}

	/**
	 * Moves solenoid to change gear of elevator
	 * 
	 * @param position
	 *            true is closed, false is open
	 */
	public void set(Value position) {
		m_transmissionSolenoid1.set(position);
	}

	/**
	 * Toggles the position of the elevator gearshift.
	 */
	public void toggleSolenoid() {

		if (m_transmissionSolenoid1.get() == Value.kForward || m_transmissionSolenoid1.get() == Value.kOff) {
			m_transmissionSolenoid1.set(Value.kReverse);
		} else {
			m_transmissionSolenoid1.set(Value.kForward);
		}
	}

}