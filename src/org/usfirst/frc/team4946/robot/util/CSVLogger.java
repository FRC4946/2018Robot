package org.usfirst.frc.team4946.robot.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CSVLogger {

	private List<SendableSubtable> m_tables = new ArrayList<SendableSubtable>();
	private BufferedWriter m_writer;
	private long m_startTime = 0;

	/**
	 * Create a new {@code CSVLogger}
	 */
	public CSVLogger() {
		File dir = new File("/home/lvuser/mylogs/");
		if (!dir.exists())
			dir.mkdir();
	}

	/**
	 * Add the specified {@link SendableSubtable} to the logger
	 * 
	 * @param table
	 *            the sub-table to add
	 */
	public void addTable(SendableSubtable table) {
		m_tables.add(table);
	}

	/**
	 * Remove a {@link SendableSubtable} from the logger
	 * 
	 * @param table
	 *            the sub-table to remove
	 */
	public void removeTable(SendableSubtable table) {
		m_tables.remove(table);
	}

	/**
	 * Create a new file with the current timestamp, and start logging
	 * 
	 * @throws IOException
	 */
	public void start() throws IOException {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");

		File f = new File("/home/lvuser/mylogs/" + format.format(date) + ".csv");
		f.createNewFile();

		m_writer = new BufferedWriter(new FileWriter(f));
		m_startTime = System.currentTimeMillis();

		m_writer.append("Timestamp (ms), ");

		for (SendableSubtable table : m_tables)
			for (String key : table.getKeys())
				m_writer.append(key + ", ");

		m_writer.append("\n");
	}

	/**
	 * Write a snapshot of the current data to the logger
	 */
	public void snapshot() {
		try {
			m_writer.append((System.currentTimeMillis() - m_startTime) + ", ");
			for (SendableSubtable table : m_tables)
				for (Object value : table.getValues())
					m_writer.append(value + ", ");
			m_writer.append("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Stop logging and close the file
	 * 
	 * @throws IOException
	 */
	public void stop() throws IOException {
		if (m_writer != null)
			m_writer.close();
	}

}
