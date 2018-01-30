package org.usfirst.frc.team4946.robot.pathplanning.data.point;

public class Point {
	
	protected double x;
	protected double y;
	protected int size = 2;

	public Point() {
		this(0, 0);
	}

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Point(Point point) {
		this.x = point.x;
		this.y = point.y;
		this.size = point.size;
	}

	public Point(java.awt.Point point) {
		this.x = point.x;
		this.y = point.y;
	}

	public double distance(Point p) {
		return Math.sqrt((x - p.x) * (x - p.x) + (y - p.y) * (y - p.y));
	}

	public boolean contains(Point p) {
		return distance(p) < size;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

}
