package org.usfirst.frc.team4946.robot.util;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class AvgPIDSource implements PIDSource {

	private PIDSource m_sourceA;
	private PIDSource m_sourceB;

	
	public AvgPIDSource(PIDSource a, PIDSource b){
		m_sourceA = a;
		m_sourceB = b;
	}
	
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		m_sourceA.setPIDSourceType(pidSource);
		m_sourceB.setPIDSourceType(pidSource);
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return m_sourceA.getPIDSourceType();
	}

	@Override
	public double pidGet() {
		return (m_sourceA.pidGet() + m_sourceB.pidGet())/2.0;
	}

}
