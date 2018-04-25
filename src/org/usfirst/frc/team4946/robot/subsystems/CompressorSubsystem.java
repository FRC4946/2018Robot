package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class CompressorSubsystem extends Subsystem {
	private Compressor m_compressor;

	public CompressorSubsystem() {
		m_compressor = new Compressor(RobotMap.CAN_PCM);

	}

	public void initDefaultCommand() {
	}

	/**
	 * Sets the robot compressor on or off
	 * 
	 * @param isOn
	 *            Whether to turn the compressor on or off (true is on)
	 */
	public void setCompressor(boolean isOn) {
		m_compressor.setClosedLoopControl(isOn);
	}
}
