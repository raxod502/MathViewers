package com.apprisingsoftware.mathviewers.diffeq;

public class DiffEqIterator {

	private double[] values;
	private double delay;
	private double precision;
	private double time;
	private DiffEq eq;

	public DiffEqIterator(double[] initialValues, double delay, double precision, DiffEq equation) {
		values = initialValues;
		this.delay = delay;
		this.precision = precision;
		time = 0;
		eq = equation;
	}

	public void iterate() {
		for (int r=0; r<precision; r++) {
			eq.run(this);
			double[] newValues = new double[values.length];
			newValues[values.length-1] = values[values.length-1];
			for (int i = values.length - 2; i >= 0; i--) {
				newValues[i] = values[i] + values[i+1]*(delay/precision);
			}
			time += delay/precision;
			values = newValues;
		}
	}

	public void setValue(int index, double value) {
		values[index] = value;
	}
	public double getValue(int index) {
		return values[index];
	}
	public double[] getValues() {
		return values;
	}
	public double getTime() {
		return time;
	}
	public double getDelay() {
		return delay;
	}

}
