package org.usfirst.frc.team4946.robot.pathplanning;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.usfirst.frc.team4946.robot.pathplanning.data.actions.DriveAction.Segment;

public class PathfileParser {

	int m_count;
	BufferedReader m_fileReader;
	ArrayList<ArrayList<Double>> m_listofLists;
	ArrayList<Double> m_posList;
	ArrayList<Double> m_velList;
	ArrayList<Double> m_accelList;
	ArrayList<Double> m_jerkList;
	ArrayList<Segment> m_pointList;
	String m_line;
	String m_numbersList[];

	public PathfileParser(String path) throws FileNotFoundException {

		m_fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(path), Charset.forName("UTF-8")));
		m_count = 0;
		m_listofLists.add(m_posList);
		m_listofLists.add(m_velList);
		m_listofLists.add(m_accelList);
		m_listofLists.add(m_jerkList);
	}

	public void readFile() throws IOException {

		while ((m_line = m_fileReader.readLine()) != null) {

			m_numbersList = m_line.split(" ");

			for (int i = 0; i < m_numbersList.length; i++) {

				m_listofLists.get(m_count).add(Double.parseDouble(m_numbersList[i]));
				// m_pointList.get(m_pointList.size() - 1).addDimension(m_numbersList[i]);
				m_count++;

				if (m_count == 4) {
					m_count = 0;
					// m_pointList.add(new NDimensionalPoint());
				}
			}
		}
	}

	public ArrayList<Double> getPosList() {
		return m_posList;
	}

	public ArrayList<Double> getVelList() {
		return m_velList;
	}

	public ArrayList<Double> getAccelList() {
		return m_accelList;
	}

	public ArrayList<Double> getJerkList() {
		return m_jerkList;
	}

	public ArrayList<Segment> getpointList() {
		return m_pointList;
	}
}