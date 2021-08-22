package com.apprisingsoftware.mathviewers.complex;

import java.awt.Dimension;
import java.awt.Frame;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ComplexViewerApplet extends JApplet {

	public static void main(String[] args) {
		JApplet app = new ComplexViewerApplet();
		app.init();
	}

	@Override public void init() {
		JFrame frame = new JFrame();
		JPanel panel = new ComplexViewerPanel();
		frame.add(panel);
		frame.setSize(new Dimension(panel.getWidth(), panel.getHeight()));
		frame.setTitle("Complex Viewing Frame");
		frame.setVisible(true);
	}

}
