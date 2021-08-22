package com.apprisingsoftware.mathviewers.calculusart;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public final class CalculusArtGenerator2 {

	public static final int imageSize = 1600;

	public static void main(String[] args) {
		System.out.print("Generating art... ");
		saveImage(getArt(), "art.png");
		System.out.println("Done!");
	}

	private static BufferedImage getArt() {
		int[][] pixels = new int[imageSize][imageSize];
		for (int x=0; x<imageSize; x++) {
			for (int y=0; y<imageSize; y++) {
				double X = (double)x / imageSize - 0.5;
				double Y = -(double)y / imageSize + 0.5;
				pixels[x][y] = new Color(getR(X, Y), getG(X, Y), getB(X, Y)).getRGB();
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

	private static int getR(double x, double y) {
		return getColor(x, y, new double[] { 17, 21 }, 0.5, 0.15);
	}

	private static int getG(double x, double y) {
		return getColor(x, y, new double[] { 15, 19 }, 0.7, 0.1);
	}

	private static int getB(double x, double y) {
		return getColor(x, y, new double[] { 23, 9 }, 0.6, 0.05);
	}

	private static int getColor(double x, double y, double[] F, double GX, double GY) {
		double value = 0;
		for (double f : F) {
			value += getFunction(x, y, f, GX, GY);
		}
		value = (value + 1) / 2;
		value = Math.min(Math.max(value, 0), 1);
		return (int)(value * 255);
	}

	private static double getFunction(double x, double y, double F, double GX, double GY) {
		final double a = 1;
		final double b = 10;

		x *= 30 * Math.exp(Math.abs(x) * -GX); y *= 30 * Math.exp(Math.abs(y) * -GY);
		double value = Math.pow(x*x + x*y + a*x - b*b, 2) - (b*b - x*x) * Math.pow(x - y + a, 2);
		value = Math.sin(value / 5000 * F);
		return value;
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
