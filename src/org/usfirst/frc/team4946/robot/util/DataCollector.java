package org.usfirst.frc.team4946.robot.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.usfirst.frc.team4946.robot.Robot;

public class DataCollector  {
	
	BufferedWriter m_dataWriter;
	BufferedReader m_dataReader;
	File m_file;
	String m_line;
	String m_secondLine;
	String[] m_firstValueArray;
	String[] m_secondValueArray;
	ArrayList<Double> m_velList, m_accelList, m_jerkList;
	double m_deltaTime;
	
	public DataCollector(String path) throws IOException {
		
		m_velList = new ArrayList<Double>();
		m_accelList = new ArrayList<Double>();
		m_jerkList = new ArrayList<Double>();
		
		m_velList.add(0.0); 
		m_accelList.add(0.0);
		m_jerkList.add(0.0);
		
		m_file = new File(path);
		m_dataWriter = new BufferedWriter(new FileWriter(m_file));
		m_dataReader = new BufferedReader(new FileReader(m_file));
		
		if(!m_file.exists()) {
			m_file.createNewFile();
		}
		
		m_dataWriter.write("Left Raw Enc, Right Raw Enc, Time, Left Dist Enc, Right Dist Enc");
		//, Left Dist Enc, "+ "Right Dist Enc, Dist, Vel, Accel, Jerk"
	}
	
	public void writeRawEnc(double leftRawEnc, double rightRawEnc, double time, double leftDistEnc, double rightDistEnc) throws IOException {
		
		m_dataWriter.newLine();
		m_dataWriter.write(leftRawEnc + " " + rightRawEnc + " " + time 
				+ " " + leftDistEnc + " " + rightDistEnc);
	}
	
	public void writeSecData() throws IOException {
		
		m_dataWriter.newLine();
		m_dataWriter.write("");
		m_dataWriter.newLine();
		m_dataWriter.write("Left Vel, Right Vel, Left Accel, "
				+ "Right Accel, Left Jerk, Right Jerk, Avg Vel, Avg Accel, Avg Jerk");
		
		m_dataReader.readLine(); 
		m_line = m_dataReader.readLine();
		
		while((m_secondLine = m_dataReader.readLine()) != null) {
			
			m_firstValueArray = m_line.split(" ");
			m_secondValueArray = m_secondLine.split(" ");
			
			m_deltaTime = Double.parseDouble(m_secondValueArray[2]) - Double.parseDouble(m_firstValueArray[2]);
					
			m_velList.add((Double.parseDouble(m_secondValueArray[3]) - Double.parseDouble(m_firstValueArray[3])) //Left
					/(m_deltaTime));
			m_accelList.add((m_velList.get(m_velList.size() - 1) - m_velList.get(m_velList.size() - 3))/m_deltaTime);
			m_jerkList.add((m_accelList.get(m_accelList.size() - 1) - m_accelList.get(m_accelList.size() - 3))/m_deltaTime);
			
			m_velList.add((Double.parseDouble(m_secondValueArray[4]) - Double.parseDouble(m_firstValueArray[4])) //Right
					/(m_deltaTime));
			m_accelList.add((m_velList.get(m_velList.size() - 1) - m_velList.get(m_velList.size() - 3))/m_deltaTime);
			m_jerkList.add((m_accelList.get(m_accelList.size() - 1) - m_accelList.get(m_accelList.size() - 3))/m_deltaTime);
		
			m_firstValueArray = m_secondValueArray;
		}
		
		for(int i = 1; i < m_velList.size(); i += 2) {
			
			m_dataWriter.write(m_velList.get(i) + " " + m_velList.get(i + 1) + " " 
					+ m_accelList.get(i) + " " + m_accelList.get(i + 1) + " " 
					+ m_jerkList.get(i) + " " + m_jerkList.get(i + 1) + " "
					+ (((m_velList.get(i) + m_velList.get(i + 1))/2.0)) + " "
					+ (((m_accelList.get(i) + m_accelList.get(i + 1))/2.0)) + " " 
					+ (((m_jerkList.get(i) + m_jerkList.get(i + 1))/2.0)) + " ");
			
		}
	}
}
