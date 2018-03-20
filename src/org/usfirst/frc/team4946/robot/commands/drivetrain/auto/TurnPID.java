package org.usfirst.frc.team4946.robot.commands.drivetrain.auto;

import org.usfirst.frc.team4946.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TurnPID extends Command {

	double m_setAngle;
	int onTargetCount;

	/**
	 * Turns using PID
	 * 
	 * @param angle
	 *            the angle in degrees for the robot to turn to, it is essentially
	 *            absolute and is not relative to the robots current angle (turning
	 *            to 90 twice moves the robot to 90 degrees, then does nothing)
	 */
	public TurnPID(double angle) {
		requires(Robot.driveTrainSubsystem);
		m_setAngle = angle;
	}

	protected void initialize() {
		Robot.driveTrainSubsystem.enableGyroPID();
		Robot.driveTrainSubsystem.setGyroSetpoint((m_setAngle + Robot.driveTrainSubsystem.getGyroAngle()) % 360);
//		Robot.driveTransmissionSubsystem.set(false);
		onTargetCount = 0;

	}

	protected void execute() {
		Robot.driveTrainSubsystem.arcadeDrive(0, Robot.driveTrainSubsystem.getGyroPIDOutput());
	}

	protected boolean isFinished() {
		if (Robot.driveTrainSubsystem.getGyroOnTarget())
			onTargetCount++;
		else
			onTargetCount = 0;

		return onTargetCount > 10;
	}

	protected void end() {
		Robot.driveTrainSubsystem.stop();
		Robot.driveTrainSubsystem.disableGyroPID();
		Robot.driveTransmissionSubsystem.set(true);
	}

	protected void interrupted() {
		end();
	}
}
