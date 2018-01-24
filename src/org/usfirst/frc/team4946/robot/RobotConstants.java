package org.usfirst.frc.team4946.robot;

public class RobotConstants {


	public static final double k_leftDistEncP = 0.1;
	public static final double k_leftDistEncI = 0.01;
	public static final double k_leftDistEncD = 0.0;
	
	public static final int ENCODER_PPR = 128;
	public static final double WHEEL_DIA = 6; //dummy (inches) 
	public static final double GEARBOX_REDUCTION_HIGH = 1; //dummy
	public static final double GEARBOX_REDUCTION_LOW = 1; //dummy
	public static final double DRIVETRAIN_MAX_ACCEL = 5.0; //dummy (inches per second squared)
	public static final double ROBOT_SAMPLE_TIME = 0.020; //(seconds)
	//public static final double ENCODER_DISTANCE_PER_PULSE = WHEEL_DIA * Math.PI
			// / ENCODER_PPR * GEARBOX_REDUCTION;
}
