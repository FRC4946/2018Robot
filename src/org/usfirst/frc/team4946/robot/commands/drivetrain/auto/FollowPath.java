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

		// kvel = 1.0 / 60.0;
		// kaccel = 1.0 / 100.0;

		curSegIndex = 0;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Segment left = m_path.left.get(curSegIndex);
		Segment right = m_path.right.get(curSegIndex);

		// =*=*=*=*=*=*=*=*= Distance Controllers =*=*=*=*=*=*=*=*=

		// Calculate the base output speed for the left wheels
		double lErr = left.pos - (Robot.driveTrainSubsystem.getLeftEncDist() - initLeftEnc);
		lIntegral += lErr * RobotConstants.driveI;
		double lOutput = RobotConstants.driveP * lErr + lIntegral
				+ RobotConstants.driveD * ((lErr - lastLErr) / left.dt - left.vel)
				+ (RobotConstants.driveKVel * left.vel + RobotConstants.driveKAccel * left.accel);
		lastLErr = lErr;

		// Calculate the base output speed for the right wheels
		double rErr = right.pos - (Robot.driveTrainSubsystem.getRightEncDist() - initRightEnc);
		rIntegral += rErr * RobotConstants.driveI;
		double rOutput = RobotConstants.driveP * rErr + rIntegral
				+ RobotConstants.driveD * ((rErr - lastRErr) / right.dt - right.vel)
				+ (RobotConstants.driveKVel * right.vel + RobotConstants.driveKAccel * right.accel);
		lastRErr = rErr;

		// =*=*=*=*=*=*=*=*= Heading Controller =*=*=*=*=*=*=*=*=

		// Calculate the error
		double angErr = conformAngle((left.heading - 90) - (Robot.driveTrainSubsystem.getGyroAngle() - initAngle));

		// Calculate the slope of the setpoint heading (dheading/dt)
		double dHeading = m_path.left.get(Math.min(m_path.left.size() - 1, curSegIndex + 1)).heading
				- m_path.left.get(Math.max(0, curSegIndex - 1)).heading;
		dHeading /= (curSegIndex == 0 || curSegIndex == m_path.left.size() - 1) ? left.dt : 2 * left.dt;

		// Calculate the controller output, and limit it to the max range
		angIntegral += angErr * RobotConstants.kPathTurn.kI;
		double turnOutput = angErr * RobotConstants.kPathTurn.kP + angIntegral
				+ RobotConstants.kPathTurn.kD * ((angErr - lastAngErr) / left.dt - dHeading);
		turnOutput = Math.min(turnOutput, RobotConstants.kPathTurn.kMaxOutput);
		turnOutput = Math.max(turnOutput, RobotConstants.kPathTurn.kMinOutput);

		// Combine the distance and heading controllers to calculate the overall
		// output
		lOutput += turnOutput;
		rOutput -= turnOutput;

		lOutput = Math.min(lOutput, 1.0);
		rOutput = Math.max(rOutput, -1.0);

		lOutput = Math.min(lOutput, 1.0);
		rOutput = Math.max(rOutput, -1.0);

		// Robot.driveTrainSubsystem.tankDrive(lOutput, rOutput);
		Robot.driveTrainSubsystem.tankDrive(
				lOutput * RobotConstants.ROBOT_NORMALIZED_VOLTAGE / Robot.driveTrainSubsystem.getVBus(),
				rOutput * RobotConstants.ROBOT_NORMALIZED_VOLTAGE / Robot.driveTrainSubsystem.getVBus());

		curSegIndex++;

		// Logging
		SmartDashboard.putNumber("Left Out", lOutput);
		SmartDashboard.putNumber("Right Out", rOutput);
		SmartDashboard.putNumber("Left Error", lErr);
		SmartDashboard.putNumber("Right Error", rErr);
		// SmartDashboard.putNumber("Heading Error", angErr);
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
