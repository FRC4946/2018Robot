package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class RampSubsystem extends Subsystem {

	private Solenoid m_rampRelease = new Solenoid(RobotMap.PCM_RAMP_DEPLOY);

	public void initDefaultCommand() {
	}

	public void deployRamp(boolean isDeployed) {
		m_rampRelease.set(isDeployed);
	}
}
