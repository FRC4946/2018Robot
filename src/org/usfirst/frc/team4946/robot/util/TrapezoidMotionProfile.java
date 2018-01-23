package org.usfirst.frc.team4946.robot.util;

import org.usfirst.frc.team4946.robot.Robot;

public class TrapezoidMotionProfile {
	
	
	double fAccMax;
	double fVelMax;
	double fDisTarget;
	double fSampleTime;
	double iSampleCnt;
	double iAccCnt;
	double fAccTime;
	double fAccDis;
	double fVelSP;
	double v1;
	double v2;
	double fAccSP;
	
	public TrapezoidMotionProfile(double aDis, double aVelMax, double aAccLim, double aSampleTime) {
		fAccMax = aAccLim;
		fVelMax = aVelMax;
		fDisTarget = aDis;
		fSampleTime = aSampleTime;
		fAccTime = fVelMax / fAccMax;
		fAccDis = fAccMax * fAccTime * fAccTime / 2;
		
		if (fAccDis > (fDisTarget / 2)) {
			fAccDis = fDisTarget / 2;
			fAccTime = Math.sqrt(2 * fAccDis / fAccMax);
			fVelMax = fAccMax * fAccTime;
		}
		
		iSampleCnt = 0;
		iAccCnt = fAccTime / fSampleTime;
		fVelSP = 0;
		
		v1 = 0;
		v2 = 0;
	}
	
	double getVel() {
		fAccSP = fhan(-fDisTarget, v2, fAccMax, fSampleTime);
		v1 = v1 + v2 * fSampleTime;
		v2 = v2 + fSampleTime * fAccSP;
		v2 = Math.min(v2, fVelMax);
		fVelSP = v2;
		fDisTarget -= fVelSP*fSampleTime;
		iSampleCnt = iSampleCnt + 1;
		return fVelSP;
	}
	
	double getAccel() {
		return fAccSP;
	}
	
	double getDist() {
		return fDisTarget;
	}
	
	double getCount() {
		return iSampleCnt;
	}
	
	double fhan(double v1, double v2, double r0, double h0) {
		// from Han J. From PID to active disturbance rejection control[J]. IEEE transactions on Industrial Electronics, 2009, 56(3): 900-906.
		double d = r0 * h0 * h0;
		double a0 = h0 * v2;
		double y = v1 + a0;
		double a1 = Math.sqrt(Math.max(0, d * (d + 8 * Math.abs(y))));
		double a2 = a0 + Math.signum(y) * (a1 - d) / 2;
		double sy = (Math.signum(y + d) - Math.signum(y - d)) / 2;
		double a = (a0 + y - a2) * sy + a2;
		double sa = (Math.signum(a + d) - Math.signum(a - d)) / 2;
		return -r0 * (a / d - Math.signum(a)) * sa - r0 * Math.signum(a);
	}
	
}

