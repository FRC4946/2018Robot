package org.usfirst.frc.team4946.robot.pathplanning;

public class TrapezoidMotionProfile {
	
	double m_maxAccel;
	double m_maxVel;
	double m_distToTarget;
	double m_distInit;
	double m_sampleTime;
	double m_count;
	double m_accelSP;
	double m_velSP;
	double m_v2;
	
	public TrapezoidMotionProfile(double aDis, double aVelMax, double aAccLim, double aSampleTime, double distInit) {
		
		m_maxAccel = aAccLim;
		m_maxVel = aVelMax;
		m_distToTarget = aDis;
		m_sampleTime = aSampleTime;
		m_distInit = distInit;
	
		m_count = 0;	
		m_accelSP = 0;
		m_v2 = 0;
	}
	
	public double getVel(double fDisGone) {
		
		m_velSP = fhan(-m_distToTarget, m_v2, m_maxAccel, m_sampleTime);
		
		//Uses the acceleration setpoint to calculate a velocity setpoint based off the current speed
		m_v2 += m_sampleTime * m_velSP;
		m_v2 = Math.min(m_v2, m_maxVel);
		m_accelSP = m_v2;
		
		m_distToTarget -= fDisGone - m_distInit;
		m_count++;
		
		return m_accelSP;
	}
	
	public double getAccel() {
		return m_velSP;
	}
	
	public double getTargetDist() {
		return m_distToTarget;
	}
	
	public double getCount() {
		return m_count;
	}
	
	public double fhan(double v1, double v2, double r0, double h0) {
		// from Han J. From PID to active disturbance rejection control[J]. IEEE transactions on Industrial Electronics, 2009, 56(3): 900-906.
		double d = r0 * h0 * h0;
		double a0 = h0 * v2;
		double y = v1 + a0;
		double a1 = Math.sqrt(Math.max(0, d * (d + 8 * Math.abs(y))));
		double a2 = a0 + Math.signum(y) * (a1 - d) / 2;
		double sy = (Math.signum(y + d) - Math.signum(y - d)) / 2;
		double a = (a0 + y - a2) * sy + a2;
		double sa = (Math.signum(a + d) - Math.signum(a - d)) / 2;
		//return an acceleration setpoint
		return -r0 * (a / d - Math.signum(a)) * sa - r0 * Math.signum(a);
	}
	
}

