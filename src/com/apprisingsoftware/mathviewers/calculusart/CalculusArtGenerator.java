package com.apprisingsoftware.mathviewers.calculusart;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public final class CalculusArtGenerator {

	public static final int imageSize = 128000;

	public static void main(String[] args) {
		saveImage(getArt(), "art.png");
	}

	private static BufferedImage getArt() {
		int[][] pixels = new int[imageSize][imageSize];
		for (int x=0; x<imageSize; x++) {
			for (int y=0; y<imageSize; y++) {
				double X = (double)x / imageSize - 0.5;
				double Y = (double)y / imageSize - 0.5;
				pixels[x][y] = new Color((int)(getR(X, Y) * 255), (int)(getG(X, Y) * 255), (int)(getB(X, Y) * 255)).getRGB();
			}
		}
		BufferedImage art = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_ARGB);
		for (int x=0; x<imageSize; x++) {
			for (int y=0; y<imageSize; y++) {
				art.setRGB(x, y, pixels[x][y]);
			}
		}
		return art;
	}

	private static double getR(double x, double y) {
		return Math.min(Math.max(getFunction(x, y, 3), 0), 1);
	}

	private static double getG(double x, double y) {
		return Math.min(Math.max(getFunction(x, y, 0.5), 0), 1);
	}

	private static double getB(double x, double y) {
		return Math.min(Math.max(getFunction(x, y, 7), 0), 1);
	}

	private static double getFunction(double x, double y, double F) {
		final double scaleFactor = 15;
		final double a = 1;
		final double b = 10;

		x *= scaleFactor; y *= scaleFactor;
		double value = Math.pow(x*x + x*y + a*x - b*b, 2) - (b*b - x*x) * Math.pow(x - y + a, 2);
		return Math.sin(value * F);
	}

	private static void saveImage(BufferedImage image, String path) {
		try {
			ImageIO.write(image, "png", new File(path));
		}
		catch (IOException exception) {
			System.err.println("[ERROR] Could not save image at '" + path + "'");
		}
	}

}
