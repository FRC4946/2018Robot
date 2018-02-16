package org.usfirst.frc.team4946.robot.commands.intake;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class RunIntake extends Command {

	double speed = 1.0;
	Timer m_timer;

	/**
	 * Runs the intake at a specified velocity
	 * 
	 * @param speed
	 *            the velocity to run the intake at, ranging from -1.0 to 1.0.
	 */
<<<<<<< HEAD
    public RunIntake(double speed) {
    	
    	requires(Robot.externalIntakeSubsystem);
    	this.speed = speed;
    }
=======
	public RunIntake(double speed) {
		requires(Robot.externalIntakeSubsystem);
		this.speed = speed;
	}
>>>>>>> branch 'master' of https://github.com/FRC4946/2018Robot.git

<<<<<<< HEAD
    protected void initialize() {
    	m_hasCube = Robot.upperOutputSubsystem.getHasCube();
    }
=======
	protected void initialize() {
	}
>>>>>>> branch 'master' of https://github.com/FRC4946/2018Robot.git

	protected void execute() {
		Robot.externalIntakeSubsystem.set(speed);
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.externalIntakeSubsystem.stop();
	}

	protected void interrupted() {
		end();
	}
}
