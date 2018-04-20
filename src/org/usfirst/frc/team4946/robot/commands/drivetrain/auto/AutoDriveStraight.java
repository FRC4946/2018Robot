package org.usfirst.frc.team4946.robot.commands.drivetrain.auto;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoDriveStraight extends Command {
	
    double m_distanceToDrive;
	double m_startingDistance;
	double m_distanceTraveled;
	double m_gyroAngle;
	double m_maxSpeed;

	double m_onTargetCounter = 0;

	public AutoDriveStraight(double distInches) {
		requires(Robot.driveTrainSubsystem);

		m_distanceToDrive = distInches;
	}

	public AutoDriveStraight(double distInches, double maxSpeed) {
		this(distInches);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		m_startingDistance = Robot.driveTrainSubsystem.getEncoderDistance();
		Robot.driveTrainSubsystem.setGyroSetpoint(Robot.driveTrainSubsystem.getGyroAngle());
		Robot.driveTrainSubsystem.setDistSetpoint(m_distanceToDrive
				+ m_startingDistance);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double magnitude = Robot.driveTrainSubsystem.getDrivePIDOutput();
		double curve = Robot.driveTrainSubsystem.getGyroOutput();

		Robot.driveTrainSubsystem.arcadeDrive(magnitude, curve);

		m_distanceTraveled = Robot.driveTrainSubsystem.getEncoderDistance();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {

		if (Robot.driveTrainSubsystem.getDistOnTarget()) {
			m_onTargetCounter++;
			if (m_onTargetCounter > 20) {
				System.out.println("DONEEEE");
				return true;
			}
		} else
			m_onTargetCounter = 0;

		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveTrainSubsystem.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.driveTrainSubsystem.stop();
	}
}
