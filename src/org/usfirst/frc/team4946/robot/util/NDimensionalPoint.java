package org.usfirst.frc.team4946.robot.util;

import java.util.ArrayList;

public class NDimensionalPoint {
	
	ArrayList<Double> dimensionsList;
	
	public NDimensionalPoint(double... dimensions) {
		
		dimensionsList = new ArrayList<Double>();
		
		for(int i = 0; i < dimensions.length; i++) {
			dimensionsList.add(dimensions[i]);
		}
	}
	
	public void setDimension(int dimension, double value) {
		dimensionsList.set(dimension - 1, value);
	}
	
	public double getDimension(int dimension) {
		return dimensionsList.get(dimension - 1);
	}
}
