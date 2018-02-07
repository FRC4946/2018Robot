package org.usfirst.frc.team4946.robot.commands.intake;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OuterIntakeUntilCube extends Command {

	double m_speed;
	
	/**
	 * 
	 * Runs the intake at a specified velocity until a cube is detected
	 * @param speed the velocity to run the intake at
	 */
    public OuterIntakeUntilCube(double speed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.externalIntakeSubsystem);
    	m_speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.externalIntakeSubsystem.set(m_speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.externalIntakeSubsystem.getHasCube();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.externalIntakeSubsystem.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
