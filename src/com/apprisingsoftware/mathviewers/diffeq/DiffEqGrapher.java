package com.apprisingsoftware.mathviewers.diffeq;

import java.awt.Dimension;
import java.awt.Frame;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DiffEqGrapher extends JApplet {

	public static void main(String[] args) {
		JApplet app = new DiffEqGrapher();
		app.init();
	}

	private enum Equation {
		POSITION_POS_VELOCITY, POSITION_NEG_VELOCITY,
		POSITION_NEG_ACCELERATION, POSITION_NEG_JERK,
		VELOCITY_POS_ACCELERATION, VELOCITY_NEG_ACCELERATION,
		VELOCITY_NEG_JERK, DECREASING_SINUSOID,
		AIRY_FUNCTION,
		UNNAMED;
	}

	@Override public void init() {
		// Graphing options
		double delay = 0.01;
		double speed = 4;
		double compression = 4;
		int accuracy = 10000;
		int windowSize = 800;
		double percentageToResize = -1.0;
		Equation choice = Equation.AIRY_FUNCTION;
		// Iterator setup
		DiffEqIterator iter;
		switch (choice) {
		case POSITION_POS_VELOCITY: // x' = 2x
			iter = new DiffEqIterator(new double[] {5, 0}, delay/compression, accuracy, (i) -> i.setValue(1, i.getValue(0) * 2)); break;
		case POSITION_NEG_VELOCITY: // x' = -2x
			iter = new DiffEqIterator(new double[] {5, 0}, delay/compression, accuracy, (i) -> i.setValue(1, i.getValue(0) * -2)); break;
		case POSITION_NEG_ACCELERATION: // x'' = -5x
			iter = new DiffEqIterator(new double[] {5, 0, 0}, delay/compression, accuracy, (i) -> i.setValue(2, i.getValue(0) * -5)); break;
		case POSITION_NEG_JERK: // x''' = -10x
			iter = new DiffEqIterator(new double[] {5, 0, 0, 0}, delay/compression, accuracy, (i) -> i.setValue(3, i.getValue(0) * -10)); break;
		case VELOCITY_POS_ACCELERATION: // x'' = 2x'
			iter = new DiffEqIterator(new double[] {0, 5, 0}, delay/compression, accuracy, (i) -> i.setValue(2, i.getValue(1) * 2)); break;
		case VELOCITY_NEG_ACCELERATION: // x'' = -2x'
			iter = new DiffEqIterator(new double[] {0, 5, 0}, delay/compression, accuracy, (i) -> i.setValue(2, i.getValue(1) * -2)); break;
		case VELOCITY_NEG_JERK: // x''' = -5x'
			iter = new DiffEqIterator(new double[] {0, 5, 0, 0}, delay/compression, accuracy, (i) -> i.setValue(3, i.getValue(1) * -5)); break;
		case DECREASING_SINUSOID: // x'' = -x + -x'
			iter = new DiffEqIterator(new double[] {0, 5, 0}, delay/compression, accuracy, (i) -> i.setValue(2, -i.getValue(0) - i.getValue(1))); break;
		case AIRY_FUNCTION: // x'' = -xt
			iter = new DiffEqIterator(new double[] {5, 0, 0}, delay/compression, accuracy, (i) -> i.setValue(2, -i.getValue(0)*i.getTime())); break;
		case UNNAMED:
			/*iter = new DiffEqIterator(new double[] {5, 0, 0.1, 0}, delay/compression, accuracy, (i) -> {
				i.setValue(3, -i.getValue(0)/Math.pow(i.getValue(2), 2));
			}); break;*/
			iter = new DiffEqIterator(new double[] {0, 0, 0, 0, 0}, delay/compression, accuracy, (i) -> i.setValue(0, Math.random()-0.5)); break;
		default: throw new AssertionError();
		}

		JFrame frame = new JFrame();
		JPanel panel = new DiffEqGrapherPanel(iter, windowSize, 1/compression, speed, percentageToResize);
		frame.add(panel);
		frame.setSize(new Dimension(900, 900));
		frame.setTitle("Differential Equation Grapher");
		frame.setVisible(true);
	}

}
