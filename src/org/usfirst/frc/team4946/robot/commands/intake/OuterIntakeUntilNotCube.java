package org.usfirst.frc.team4946.robot.commands.intake;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OuterIntakeUntilNotCube extends Command {

	double m_speed;
	
	/**
	 * Runs the intake at a certain velocity until a cube is detected
	 * 
	 * @param speed 
	 * 			  velocity to run the intake at
	 */
    public OuterIntakeUntilNotCube(double speed) {
    	
    	requires(Robot.externalIntakeSubsystem);
    	m_speed = speed;
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.externalIntakeSubsystem.set(m_speed);
    }

    protected boolean isFinished() {
        return !Robot.externalIntakeSubsystem.getHasCube();
    }

    protected void end() {
    	Robot.externalIntakeSubsystem.stop();
    }

    protected void interrupted() {
    	end();
    }
}
