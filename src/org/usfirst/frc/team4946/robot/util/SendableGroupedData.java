package org.usfirst.frc.team4946.robot.util;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SendableGroupedData implements Sendable {

	private String subsystem = "";
	private String name = "";
	private SendableBuilder builder = null;

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
		if (builder == null)
			SmartDashboard.putData(this);
		builder.addBooleanProperty(key, () -> value, null);
	}

	public void putString(String key, String value) {
		if (builder == null)
			SmartDashboard.putData(this);
		builder.addStringProperty(key, () -> value, null);
	}

	public void putDouble(String key, double value) {
		if (builder == null)
			SmartDashboard.putData(this);
		builder.addDoubleProperty(key, () -> value, null);
	}

	@Override
	public void initSendable(SendableBuilder builder) {
		this.builder = builder;
		this.builder.setSmartDashboardType("Subsystem"); // TODO: Don't think this should really be a subsystem
	}

}
