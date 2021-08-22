package com.apprisingsoftware.mathviewers.diffeq;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DiffEqGrapherPanel extends JPanel {

	private int windowSize;
	private double offset;
	private double speed;
	private double percentageToResize;
	private Timer timer = new Timer(20, (event) -> update());

	private DiffEqIterator iter;
	private ArrayList<Pos> points = new ArrayList<Pos>();
	private double minimum = Double.MIN_VALUE, maximum = Double.MAX_VALUE;

	public DiffEqGrapherPanel(DiffEqIterator iter, int windowSize, double offset, double speed, double percentageToResize) {
		super();
		setPreferredSize(new Dimension(windowSize, windowSize));
		setSize(getPreferredSize());
		setVisible(true);

		this.iter = iter;
		this.windowSize = windowSize;
		this.offset = offset;
		this.speed = speed;
		this.percentageToResize = percentageToResize;

		timer.start();
	}

	public void update() {
		for (int r=0; r<speed/offset; r++) {
			iter.iterate();
			Pos point = new Pos(windowSize+offset, iter.getValue(0));
			points.add(point);
			if (points.size() > windowSize/offset+1) {
				points.remove(0);
			}
			boolean resize = percentageToResize <= 0 || iter.getTime()/iter.getDelay()*offset < windowSize*percentageToResize;
			if (resize) {
				minimum = Double.MAX_VALUE;
				maximum = Double.MIN_VALUE;
			}
			for (Pos pos : points) {
				pos.x -= offset;
				if (resize) {
					if (pos.y < minimum)
						minimum = pos.y;
					if (pos.y > maximum)
						maximum = pos.y;
				}
			}
			repaint();
		}
		System.out.println(Arrays.toString(iter.getValues()));
	}

	@Override public void paintComponent(Graphics G) {
		Graphics2D g = (Graphics2D)G;
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(Color.BLACK);
		for (Pos point : points) {
			double xPos = point.x, yPos = windowSize * (point.y - maximum) / (minimum - maximum);
			g.fill(new Ellipse2D.Double(xPos-2, yPos-2, 4, 4));
		}
	}

}
