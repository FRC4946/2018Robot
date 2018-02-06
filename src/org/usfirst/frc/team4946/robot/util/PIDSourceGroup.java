package org.usfirst.frc.team4946.robot.util;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class PIDSourceGroup implements PIDSource {

	ArrayList<PIDSource> pidArray = new ArrayList<PIDSource>();
	PIDSourceType pidType;
	double pidSum = 0.0;
	
	public PIDSourceGroup(PIDSource... pids) {
		
		for(int i = 0; i < pids.length; i++) {
			pidArray.add(pids[i]);
		}
		
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		
		pidType = pidSource;
		
		for(int i = 0; i < pidArray.size(); i++) {
			pidArray.get(i).setPIDSourceType(pidType);
		}
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return pidType;
	}

	@Override
	public double pidGet() {
		
		for(int i = 0; i < pidArray.size(); i++) {
			pidSum += pidArray.get(i).pidGet();
		}
		
		return (pidSum/((double)pidArray.size()));
	}

}
