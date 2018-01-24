package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Transmission extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	//Create Solenoid
	private Solenoid m_gearShiftLeft,
		m_gearShiftRight;
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void Transmission()
    {
		m_gearShiftLeft = new Solenoid(RobotMap.DIO_DRIVE_LEFTSOLENOID);
		m_gearShiftRight = new Solenoid(RobotMap.DIO_DRIVE_RIGHTSOLENOID);
    }
    
	public void toggleGear() {
		m_gearShiftLeft.set(!getGearState());
		m_gearShiftRight.set(!getGearState());
	}
	
	public boolean getGearState() {
		return m_gearShiftLeft.get();
	}
}