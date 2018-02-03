package org.usfirst.frc.team4946.robot.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
	
public class DataCollector {
	
	
	static Scanner m_dataReader;
	static BufferedWriter m_dataWriter;
	static File m_file;
	static String m_line;
	static String m_secondLine;
	static double m_deltaTime;
		
	public DataCollector(String path) throws IOException {
			
		m_file = new File(path);
		
		
		if(!m_file.exists()) {
			m_file.createNewFile();
		}
			
		m_dataWriter.write("Left Raw Enc, Right Raw Enc, Time, Left Dist Enc, Right Dist Enc");
	}
		
	public void writeRawEnc(double leftRawEnc, double rightRawEnc, double time, double leftDistEnc, double rightDistEnc) throws IOException {
			
		m_dataWriter.newLine();
		m_dataWriter.write(leftRawEnc + " " + rightRawEnc + " " + time 
				+ " " + leftDistEnc + " " + rightDistEnc);	
	}
		
	public static void writeSecData() throws IOException {
			
		ArrayList<Double>m_velList = new ArrayList<Double>();
		ArrayList<Double>m_accelList = new ArrayList<Double>();
		ArrayList<Double>m_jerkList = new ArrayList<Double>();
			
		m_velList.add(0.0);
		m_velList.add(0.0);
		m_accelList.add(0.0);
		m_accelList.add(0.0);
		m_jerkList.add(0.0);
		m_jerkList.add(0.0);
			
		String[] m_firstValueArray = {"", "", "", "", ""};
		String[] m_secondValueArray = {"", "", "", "", ""};
		
		m_dataReader = new Scanner(new FileReader(m_file));
		m_dataReader.nextLine();
		m_line = m_dataReader.nextLine();
			
		while(m_dataReader.hasNextLine()) {
					
			m_secondLine = m_dataReader.nextLine();
			m_firstValueArray = m_line.split(" ");
			m_secondValueArray = m_secondLine.split(" ");
					
			m_deltaTime = Double.parseDouble(m_secondValueArray[2]) - Double.parseDouble(m_firstValueArray[2]);
					
			m_velList.add((Double.parseDouble(m_secondValueArray[3]) - Double.parseDouble(m_firstValueArray[3]))/(m_deltaTime));
			m_accelList.add((m_velList.get(m_velList.size() - 1) - m_velList.get(m_velList.size() - 3))/m_deltaTime);				
			m_jerkList.add((m_accelList.get(m_accelList.size() - 1) - m_accelList.get(m_accelList.size() - 3))/m_deltaTime);
					
			m_velList.add((Double.parseDouble(m_secondValueArray[4]) - Double.parseDouble(m_firstValueArray[4])) //Right
					/(m_deltaTime));
			m_accelList.add((m_velList.get(m_velList.size() - 1) - m_velList.get(m_velList.size() - 3))/m_deltaTime);
			m_jerkList.add((m_accelList.get(m_accelList.size() - 1) - m_accelList.get(m_accelList.size() - 3))/m_deltaTime);
				
			m_firstValueArray = m_secondValueArray;
		}
		
		m_dataWriter = new BufferedWriter(new FileWriter(m_file, true));
		m_dataWriter.newLine();
		m_dataWriter.write("");
		m_dataWriter.newLine();
		m_dataWriter.write("Left Vel, Right Vel, Left Accel, "
		+ "Right Accel, Left Jerk, Right Jerk, Avg Vel, Avg Accel, Avg Jerk");
					
		for(int i = 2; i < m_velList.size() - 1; i += 2) {
			m_dataWriter.newLine();
			m_dataWriter.write(m_velList.get(i) + " " + m_velList.get(i + 1) + " " //Left and right velocity
			+ m_accelList.get(i) + " " + m_accelList.get(i + 1) + " " //Left and right accel
			+ m_jerkList.get(i) + " " + m_jerkList.get(i + 1) + " " //Left and right jerk
			+ (((m_velList.get(i) + m_velList.get(i + 1))/2.0)) + " " //Average velocity of both sides
			+ (((m_accelList.get(i) + m_accelList.get(i + 1))/2.0)) + " " //Average accel of both sides
			+ (((m_jerkList.get(i) + m_jerkList.get(i + 1))/2.0)) + " ");
		}
		
		m_dataWriter.flush();
		m_dataWriter.close();
		m_dataReader.close();
	}
} 