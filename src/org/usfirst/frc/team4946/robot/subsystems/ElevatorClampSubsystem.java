package org.usfirst.frc.team4946.robot.subsystems;

import org.usfirst.frc.team4946.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ElevatorClampSubsystem extends Subsystem {

	Solenoid m_clampSolenoid = new Solenoid(RobotMap.CLAMP_SOLENOID);

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void moveSolenoid(boolean position) { //True Is Closed, False is Open
    	m_clampSolenoid.set(position);
    }
    
}