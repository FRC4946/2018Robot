package org.usfirst.frc.team4946.robot.commands.intake;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OuterIntakeUntilCube extends Command {

	double m_speed;
	
	/**
	 * Runs the intake at a specified velocity until a cube is detected
	 * 
	 * @param speed 
	 * 			  the velocity to run the intake at, ranging from -1.0 to 1.0.
	 */
    public OuterIntakeUntilCube(double speed) {
    	requires(Robot.externalIntakeSubsystem);
    	m_speed = speed;
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.externalIntakeSubsystem.set(m_speed);
    }

    protected boolean isFinished() {
        return Robot.externalIntakeSubsystem.getHasCube();
    }

    protected void end() {
    	Robot.externalIntakeSubsystem.stop();
    }
    
    protected void interrupted() {
    	end();
    }
}
