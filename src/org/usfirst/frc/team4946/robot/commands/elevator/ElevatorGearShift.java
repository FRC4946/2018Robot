package org.usfirst.frc.team4946.robot.commands.elevator;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;


public class ElevatorGearShift extends Command {
	
	/**
	 * Toggles the gear state on the elevator
	 */
    public ElevatorGearShift() {
    	requires(Robot.elevatorTransmissionSubsystem);
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.elevatorTransmissionSubsystem.toggleSolenoid();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.elevatorTransmissionSubsystem.moveSolenoid(Value.kOff);
    }

    protected void interrupted() {
    	end();
    }
}
