package com.apprisingsoftware.mathviewers.diffeq;

public class Pos {

	public double x;
	public double y;

	public Pos(double x, double y) {
		super();

		this.x = x;
		this.y = y;
	}

	@Override public String toString() {
		return "(" + x + ", " + y + ")";
	}

}
