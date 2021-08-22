package com.apprisingsoftware.mathviewers.complex;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Ellipse2D;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class ComplexViewerPanel extends JPanel {

	// Window and applet stats
	public static final int windowSize = 800;
	public static final double axisLength = 2*Math.PI;
	public static final int updateDelay = 20;
	public static final double radius = windowSize/70;

	// Appearance
	public static final Font font = new Font("Dialog", Font.BOLD, 30);
	public static final Color pointColor = new Color(0, 0, 0);
	public static final Color baseColor = new Color(0, 0, 255);
	public static final Color exponentiationColor = new Color(0, 255, 0);
	public static final Color logarithmColor = new Color(255, 0, 0);

	// Instance variables
	private boolean spacePressed = false;
	private Timer timer = new Timer(updateDelay, (event) -> update());
	private Complex point = new Complex(0, 0);
	private Complex base = new Complex(0, 0);
	private Complex exponentiation, logarithm;

	public ComplexViewerPanel() {
		// Set up window
		super();
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(windowSize, windowSize));
		setSize(this.getPreferredSize());

		// Add listeners and display window
		getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "SPACE");
		getActionMap().put("SPACE", new AbstractAction() {
			@Override public void actionPerformed(ActionEvent ae) {
				spacePressed = true;
			}
		});
		getInputMap().put(KeyStroke.getKeyStroke("released SPACE"), "released SPACE");
		getActionMap().put("released SPACE", new AbstractAction() {
			@Override public void actionPerformed(ActionEvent ae) {
				spacePressed = false;
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override public void mouseReleased(MouseEvent mouse) {
				if (spacePressed || mouse.getButton() == MouseEvent.BUTTON2 || mouse.getButton() == MouseEvent.BUTTON3)
					base = screenToComplex(mouse.getX(), mouse.getY());
				else
					point = screenToComplex(mouse.getX(), mouse.getY());
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override public void mouseDragged(MouseEvent mouse) {
				if (spacePressed || mouse.getButton() == MouseEvent.BUTTON2 || mouse.getButton() == MouseEvent.BUTTON3) {
					base = screenToComplex(mouse.getX(), mouse.getY());
				}
				else {
					point = screenToComplex(mouse.getX(), mouse.getY());
				}
			}
		});
		setVisible(true);

		// Start main loop
		timer.start();
	}

	public void update() {
		exponentiation = point.exponentiate(base);
		logarithm = point.logarithm(base);
		repaint();
	}

	@Override public void paintComponent(Graphics G) {
		// Set up Graphics object
		Graphics2D g = (Graphics2D)G;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Clear window
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		// Draw axes
		g.setColor(Color.BLACK);
		g.drawLine(0, getHeight()/2, getWidth(), getHeight()/2);
		g.drawLine(getWidth()/2, 0, getWidth()/2, getHeight());
		for (int i=-6; i<=6; i++) {
			g.drawLine((int)complexToScreenX(i), (int)(getHeight()*(31/64.0)), (int)complexToScreenX(i), (int)(getHeight()*(33/64.0)));
			g.drawLine((int)(getWidth()*(31/64.0)), (int)complexToScreenY(i), (int)(getWidth()*(33/64.0)), (int)complexToScreenY(i));
		}
		for (double i : new double[] {Math.E, Math.PI, -Math.E, -Math.PI}) {
			g.drawLine((int)complexToScreenX(i), (int)(getHeight()*(63/128.0)), (int)complexToScreenX(i), (int)(getHeight()*(65/128.0)));
			g.drawLine((int)(getWidth()*(63/128.0)), (int)complexToScreenY(i), (int)(getWidth()*(65/128.0)), (int)complexToScreenY(i));
		}

		// Draw points
		g.setColor(pointColor);
		drawComplex(g, point);
		g.setColor(baseColor);
		drawComplex(g, base);
		if (exponentiation != null) {
			g.setColor(exponentiationColor);
			drawComplex(g, exponentiation);
		}
		if (logarithm != null) {
			g.setColor(logarithmColor);
			drawComplex(g, logarithm);
		}
	}
	public void drawComplex(Graphics2D g, Complex point) {
		Ellipse2D ellipse = new Ellipse2D.Double(complexToScreenX(point.real) - radius/2, complexToScreenY(point.imag) - radius/2, radius, radius);
		g.fill(ellipse);
	}
	public void drawText(Graphics2D g2, Pos center, String text) {
		g2.setColor(Color.BLACK);
		g2.setFont(font);
		FontRenderContext frc = g2.getFontRenderContext();
		GlyphVector gv = g2.getFont().createGlyphVector(frc, text);
		Rectangle rect = gv.getPixelBounds(null, 0, 0);
		int length = rect.width;
		int height = rect.height;
		g2.drawString(text, center.x-length/2, center.y+height/2);
	}

	public static Complex screenToComplex(int x, int y) {
		double xn = x, yn = y;
		xn -= windowSize / 2;
		yn -= windowSize / 2;
		double factorx = 2*axisLength / windowSize;
		double factory = -2*axisLength / windowSize;
		xn *= factorx;
		yn *= factory;
		return new Complex(xn, yn);
	}
	public static double complexToScreenX(double num) {
		double x = num;
		double factor = windowSize / (2*axisLength);
		x *= factor;
		x += windowSize / 2;
		return x;
	}
	public static double complexToScreenY(double num) {
		double x = num;
		double factor = -windowSize / (2*axisLength);
		x *= factor;
		x += windowSize / 2;
		return x;
	}

}
