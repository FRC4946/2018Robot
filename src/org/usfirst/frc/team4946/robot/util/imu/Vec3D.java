package org.usfirst.frc.team4946.robot.util.imu;

class Vec3D {
	public double i, j, k;

	Vec3D() {
		this(0, 0, 0);
	}

	Vec3D(double i, double j, double k) {
		this.i = i;
		this.j = j;
		this.k = k;
	}

	Vec3D unit() {
		return new Vec3D(i / mag(), j / mag(), k / mag());
	}

	double mag() {
		return Math.sqrt(i * i + j * j + k * k);
	}

	double dotProduct(Vec3D b) {
		return (i * b.i) + (j * b.j) + (k * b.k);
	}
}