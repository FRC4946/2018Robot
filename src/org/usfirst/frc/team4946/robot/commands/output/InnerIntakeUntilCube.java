package org.usfirst.frc.team4946.robot.commands.output;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class InnerIntakeUntilCube extends Command {

	double m_speed;
	
	/**Runs the inner intake at a specified velocity until a cube is detected
	 * 
	 * @param m_speed the velocity at which to run the inner intake
	 */
    public InnerIntakeUntilCube(double m_speed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.upperOutputSubsystem);
    	
    	this.m_speed = m_speed;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.upperOutputSubsystem.set(m_speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.upperOutputSubsystem.getHasCube();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.upperOutputSubsystem.disableMechanism();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
