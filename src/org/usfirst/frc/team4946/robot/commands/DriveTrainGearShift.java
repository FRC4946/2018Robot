package org.usfirst.frc.team4946.robot.commands;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;


public class DriveTrainGearShift extends Command {

	/**
	 * Toggles the state of the drivetrain gearshift.
	 */
    public DriveTrainGearShift() {
    	requires(Robot.transmissionSubsystem);
    }

    protected void initialize() {
    	
    }

    protected void execute() {
    	Robot.transmissionSubsystem.toggleGear();
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    
    }

    protected void interrupted() {
    	
    }
}
