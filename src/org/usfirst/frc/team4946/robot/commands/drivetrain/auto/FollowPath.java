package org.usfirst.frc.team4946.robot.commands.drivetrain.auto;

import org.usfirst.frc.team4946.robot.Robot;
import org.usfirst.frc.team4946.robot.RobotConstants;
import org.usfirst.frc.team4946.robot.pathplanning.data.Segment;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.DriveAction;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FollowPath extends Command {

	private DriveAction m_path;
	private int curSegIndex;
	private double initLeftEnc;
	private double initRightEnc;

	private double lastLeftErr;
	private double lastRightErr;

	private double kp;
	private double kd;
	private double kvel;
	private double kaccel;

	public FollowPath(DriveAction path) {
		requires(Robot.driveTrainSubsystem);
		m_path = path;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		initLeftEnc = Robot.driveTrainSubsystem.getLeftEncDist();
		initRightEnc = Robot.driveTrainSubsystem.getRightEncDist();

		lastLeftErr = 0;
		lastRightErr = 0;

		kp = RobotConstants.driveP;
		kd = RobotConstants.driveD;
		kvel = RobotConstants.driveKVel;
		kaccel = RobotConstants.driveKAccel;

		//kvel = 1.0 / 60.0;
		//kaccel = 1.0 / 100.0;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Segment left = m_path.left.get(curSegIndex);
		Segment right = m_path.right.get(curSegIndex);

		double lError = left.pos - (Robot.driveTrainSubsystem.getLeftEncDist() - initLeftEnc);
		double lOutput = kp * lError + kd * ((lError - lastLeftErr) / left.dt - left.vel)
				+ (kvel * left.vel + kaccel * left.accel);
		lastLeftErr = lError;

		double rError = right.pos - (Robot.driveTrainSubsystem.getRightEncDist() - initRightEnc);
		double rOutput = kp * rError + kd * ((rError - lastRightErr) / right.dt - right.vel)
				+ (kvel * right.vel + kaccel * right.accel);
		lastRightErr = rError;

		// double angleError = left.heading - Robot.driveTrainSubsystem.getGyroAngle();

		curSegIndex++;
		// SmartDashboard.putNumber("Left Enc:", distance_so_far);
		// SmartDashboard.putNumber("FollowerGoal", segment.pos);
		// SmartDashboard.putNumber("FollowerError", error);

		System.out.println(left.heading + "\t" + lOutput + "\t" + rOutput);
		Robot.driveTrainSubsystem.tankDrive(lOutput, rOutput);
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
}
