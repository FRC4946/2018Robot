package org.usfirst.frc.team4946.robot.util;

import edu.wpi.first.wpilibj.PIDOutput;

public class NullPIDOutput implements PIDOutput {
	
	private double m_output;

	@Override
	public void pidWrite(double output) {
		m_output = output;
	}
	
	public double getOutput() {
		return m_output;
	}

}
