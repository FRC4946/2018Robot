package org.usfirst.frc.team4946.robot.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SendableSubtable implements Sendable {

	private String m_subsystem = "N/A";
	private String m_name = "";
	private SendableBuilder m_builder = null;
	private Map<String, Object> m_data = new HashMap<>();

	/**
	 * Create a sub-table on the {@link SmartDashboard} to group data
	 * 
	 * @param name
	 *            the name of the new sub-table
	 */
	public SendableSubtable(String name) {
		this.m_name = name;
	}

	@Override
	public String getName() {
		return m_name;
	}

	@Override
	public void setName(String name) {
		this.m_name = name;
	}

	@Override
	public String getSubsystem() {
		return m_subsystem;
	}

	@Override
	public void setSubsystem(String subsystem) {
		this.m_subsystem = subsystem;
	}

	@Override
	public void initSendable(SendableBuilder builder) {
		this.m_builder = builder;
	}

	/**
	 * Publish a {@code boolean} on the table
	 * 
	 * @param key
	 *            the key with which the specified value is associated
	 * @param value
	 *            the value
	 */
	public void putBoolean(String key, boolean value) {
		if (m_builder == null)
			SmartDashboard.putData(this);

		if (m_data.put(key, value) == null)
			m_builder.addBooleanProperty(key, () -> Boolean.parseBoolean(m_data.get(key).toString()),
					(v) -> m_data.put(key, v));
	}

	/**
	 * Publish a {@link String} on the table
	 * 
	 * @param key
	 *            the key with which the specified value is associated
	 * @param value
	 *            the value
	 */
	public void putString(String key, String value) {
		if (m_builder == null)
			SmartDashboard.putData(this);

		if (m_data.put(key, value) == null)
			m_builder.addStringProperty(key, () -> m_data.get(key).toString(), (v) -> m_data.put(key, v));
	}

	/**
	 * Publish a {@code double} on the table
	 * 
	 * @param key
	 *            the key with which the specified value is associated
	 * @param value
	 *            the value
	 */
	public void putDouble(String key, double value) {
		if (m_builder == null)
			SmartDashboard.putData(this);

		if (m_data.put(key, value) == null)
			m_builder.addDoubleProperty(key, () -> Double.parseDouble(m_data.get(key).toString()),
					(v) -> m_data.put(key, v));
	}

	/**
	 * @return a {@link List} of all of the keys in the sub-table, in the same order
	 *         as the values from {@link #getValues()}
	 */
	public List<String> getKeys() {
		List<String> keys = new ArrayList<String>();

		for (String key : m_data.keySet())
			keys.add(key);

		return keys;
	}

	/**
	 * @return a {@link List} of all of the values in the sub-table, in the same
	 *         order as the keys from {@link #getKeys()}
	 */
	public List<Object> getValues() {
		return new ArrayList<Object>(m_data.values());
	}
}
