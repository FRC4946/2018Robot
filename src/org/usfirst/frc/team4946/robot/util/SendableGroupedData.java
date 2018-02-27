package org.usfirst.frc.team4946.robot.util;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class SendableGroupedData implements Sendable {

	private String subsystem = "";
	private String name = "";
	private SendableBuilder builder;

	public SendableGroupedData(String name) {
		this(name, "Ungrouped");
	}

	public SendableGroupedData(String name, String subsystem) {
		this.name = name;
		this.subsystem = subsystem;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getSubsystem() {
		return subsystem;
	}

	@Override
	public void setSubsystem(String subsystem) {
		this.subsystem = subsystem;
	}

	public void putBoolean(String key, boolean value) {
		builder.addBooleanProperty(key, () -> value, null/*e -> value = e*/);
	}
	
	public void putString(String key, String value) {
		builder.addStringProperty(key, () -> value, null/*e -> value = e*/);
	}
	
	public void putDouble(String key, double value) {
		builder.addDoubleProperty(key, () -> value, null/*e -> value = e*/);
	}

	@Override
	public void initSendable(SendableBuilder builder) {
		this.builder = builder;
		this.builder.setSmartDashboardType("Subsystem");
	}

}
