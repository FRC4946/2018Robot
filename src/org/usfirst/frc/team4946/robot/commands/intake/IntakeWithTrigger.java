package org.usfirst.frc.team4946.robot.commands.intake;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.commands.arm.SetClamp;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeWithTrigger extends Command {

	public IntakeWithTrigger() {
		requires(Robot.intakeSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		// Calculate the speed to spin the motors
		double inSpeed = Robot.m_oi.getDriveStick().getRawAxis(2);
		double outSpeed = Math.max(Robot.m_oi.getDriveStick().getRawAxis(3),
				Robot.m_oi.getOperatorStick().getRawAxis(3));
		if (inSpeed > 0.05)
			outSpeed = Robot.m_oi.getDriveStick().getRawAxis(3);
		double speed = inSpeed - outSpeed;

		// If we're trying to intake but we have a cube, rumble
		// if (speed > 0 && Robot.intakeSubsystem.getHasCube()) {
		//
		// Robot.intakeSubsystem.setOuter(0.0);
		// Robot.intakeSubsystem.setInner(0.0);
		// Robot.m_oi.setDriveStickRumble(1.0);
		// Robot.m_oi.setOperateStickRumble(1.0);
		// }

		// Otherwise, spin the intakes
		// else {

		// Only run the external intake if the elevator is low to the ground
		if (Robot.elevatorSubsystem.getHeight() < RobotConstants.ELEVATOR_INTERFERE_MIN + 6) {

			Robot.m_oi.setDriveStickRumble(0.0);
			Robot.m_oi.setOperateStickRumble(0.0);
			Robot.intakeSubsystem.setOuter(speed * 0.8);

			// Engage the clamp
			if (speed >= 0.05 && !Robot.armSubsystem.getClampIsEngaged() && !Robot.intakeSubsystem.getHasCube())
				new SetClamp(true).start();

			// Disengage the clamp
			if (speed <= -0.05 && !Robot.armSubsystem.getClampIsEngaged())
				new SetClamp(true).start();

		} else {

			// If the drivers try to intake when the elevator is up, allow the motors to
			// spin but rumble the joystick to warn the driver
			if (speed > 0.1) {
				Robot.m_oi.setDriveStickRumble(1.0);
				Robot.m_oi.setOperateStickRumble(1.0);
			} else {
				Robot.m_oi.setDriveStickRumble(0.0);
				Robot.m_oi.setOperateStickRumble(0.0);
			}

			Robot.intakeSubsystem.setOuter(0.0);
		}

		// Always run the internal
		Robot.intakeSubsystem.setInner(speed + 0.15);
	}

	// } 

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {

		Robot.intakeSubsystem.setOuter(0.0);
		Robot.intakeSubsystem.setInner(0.0);
		Robot.m_oi.setDriveStickRumble(0.0);
		Robot.m_oi.setOperateStickRumble(0.0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
