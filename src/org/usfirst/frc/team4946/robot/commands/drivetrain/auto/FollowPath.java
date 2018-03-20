package org.usfirst.frc.team4946.robot.commands.drivetrain.auto;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.pathplanning.data.Segment;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.DriveAction;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class FollowPath extends Command {

	private DriveAction m_path;
	private int curSegIndex;
	private double initLeftEnc;
	private double initRightEnc;
	private double initAngle;

	private double lastLErr;
	private double lastRErr;
	private double lastAngErr;

	private double lIntegral;
	private double rIntegral;
	private double angIntegral;

	private double kP;
	private double kI;
	private double kD;
	private double kVel;
	private double kAccel;
	private double kTurnP;
	private double kTurnI;
	private double kTurnD;

	public FollowPath(DriveAction path) {
		requires(Robot.driveTrainSubsystem);
		m_path = path;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.driveTransmissionSubsystem.set(true);

		initLeftEnc = Robot.driveTrainSubsystem.getLeftEncDist();
		initRightEnc = Robot.driveTrainSubsystem.getRightEncDist();
		initAngle = Robot.driveTrainSubsystem.getGyroAngle();

		lastLErr = 0;
		lastRErr = 0;
		lastAngErr = 0;

		lIntegral = 0;
		rIntegral = 0;
		angIntegral = 0;

		kP = RobotConstants.driveP;
		kI = RobotConstants.driveI;
		kD = RobotConstants.driveD;
		kVel = RobotConstants.driveKVel;
		kAccel = RobotConstants.driveKAccel;
		kTurnP = RobotConstants.pathTurnP;
		kTurnI = RobotConstants.pathTurnI;
		kTurnD = RobotConstants.pathTurnD;

		// kvel = 1.0 / 60.0;
		// kaccel = 1.0 / 100.0;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Segment left = m_path.left.get(curSegIndex);
		Segment right = m_path.right.get(curSegIndex);

		// Calculate the base output speed for the left wheels
		double lErr = left.pos - (Robot.driveTrainSubsystem.getLeftEncDist() - initLeftEnc);
		lIntegral += lErr * kI;
		double lOutput = kP * lErr + lIntegral + kD * ((lErr - lastLErr) / left.dt - left.vel)
				+ (kVel * left.vel + kAccel * left.accel);
		lastLErr = lErr;

		// Calculate the base output speed for the right wheels
		double rErr = right.pos - (Robot.driveTrainSubsystem.getRightEncDist() - initRightEnc);
		rIntegral += rErr * kI;
		double rOutput = kP * rErr + rIntegral + kD * ((rErr - lastRErr) / right.dt - right.vel)
				+ (kVel * right.vel + kAccel * right.accel);
		lastRErr = rErr;

		// Calculate the heading correction to apply to the two sides of the drive base
		double angErr = conformAngle((left.heading - 90) - (Robot.driveTrainSubsystem.getGyroAngle() - initAngle));
		angIntegral += angErr * kTurnI;
		double dHeading = m_path.left.get(Math.min(m_path.left.size() - 1, curSegIndex + 1)).heading
				- m_path.left.get(Math.max(0, curSegIndex - 1)).heading;
		dHeading /= (curSegIndex == 0 || curSegIndex == m_path.left.size() - 1) ? left.dt : 2 * left.dt;
		double turnOutput = angErr * kTurnP + angIntegral + kTurnD * ((angErr - lastAngErr) / left.dt - dHeading);

		// Apply the heading correction
		lOutput += turnOutput;
		rOutput -= turnOutput;

		curSegIndex++;

		// System.out.println(left.heading + "\t" + lOutput + "\t" + rOutput);
		Robot.driveTrainSubsystem.tankDrive(lOutput, rOutput);

		// Logging
		SmartDashboard.putNumber("Left Out", lOutput);
		SmartDashboard.putNumber("Right Out", rOutput);
		SmartDashboard.putNumber("Left Error", lErr);
		SmartDashboard.putNumber("Right Error", rErr);
		SmartDashboard.putNumber("Heading Error", angErr);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return curSegIndex >= m_path.left.size();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveTrainSubsystem.tankDrive(0.0, 0.0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}

	private double conformAngle(double angle) {
		angle %= 360;
		if (angle > 180)
			return angle - 360;
		else if (angle < -180)
			return angle + 360;
		return angle;
	}
}
