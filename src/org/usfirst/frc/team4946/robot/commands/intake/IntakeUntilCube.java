package org.usfirst.frc.team4946.robot.commands.intake;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeUntilCube extends Command {

	private double m_internalSpeed;
	private double m_externalSpeed;

	public IntakeUntilCube() {
		this(0.6, 1.0);
	}

	public IntakeUntilCube(double speed) {
		this(speed * 0.6, speed);
	}

	public IntakeUntilCube(double extSpeed, double intSpeed) {
		requires(Robot.intakeSubsystem);
		m_externalSpeed = extSpeed;
		m_internalSpeed = intSpeed;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.intakeSubsystem.setOuter(m_externalSpeed);
		Robot.intakeSubsystem.setInner(m_internalSpeed);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Robot.intakeSubsystem.getBannerHasCube();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.intakeSubsystem.setOuter(0.0);
		Robot.intakeSubsystem.setInner(0.0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
