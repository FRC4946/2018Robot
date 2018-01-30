package org.usfirst.frc.team4946.robot.util;

import java.util.ArrayList;

public class NDimensionalPoint {
	
	ArrayList<Double> m_coordsList;
	
	public void addDimension(double coord) {
		m_coordsList.add(coord);
	}
	
	public void addDimension(String coord) {
		m_coordsList.add(Double.parseDouble(coord));
	}
}
