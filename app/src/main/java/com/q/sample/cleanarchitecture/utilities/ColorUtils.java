package com.q.sample.cleanarchitecture.utilities;

public final class ColorUtils
{
	private ColorUtils() {}

	public static int getColor(int alpha, int red, int green, int blue) {
		if(alpha > 255 || alpha < 0 || red > 255 || red < 0 || green > 255 || green < 0 || blue > 255 || blue < 0)
		{
			throw new IllegalArgumentException("Can't not get color");
		}
		int color = (alpha & 0xff) << 24 | (red & 0xff) << 16 | (green & 0xff) << 8 | (blue & 0xff);
		return color;

	}

	public static int getAlpha(int color) {
		return (color >> 24) & 0xff;
	}

	public static int getRed(int color) {
		return (color >> 16) & 0xff;
	}

	public static int getGreen(int color) {
		return (color >> 8) & 0xff;
	}

	public static int getBlue(int color) {
		return (color >> 0) & 0xff;
	}

	public static int setAlpha(int color, int alpha) {
		int red = getRed(color);
		int green = getGreen(color);
		int blue = getBlue(color);
		return getColor(alpha, red, green, blue);
	}

	public static int setRed(int color, int red) {
		int alpha = getAlpha(color);
		int green = getGreen(color);
		int blue = getBlue(color);
		return getColor(alpha, red, green, blue);
	}

	public static int setGreen(int color, int green) {
		int alpha = getAlpha(color);
		int red = getRed(color);
		int blue = getBlue(color);
		return getColor(alpha, red, green, blue);
	}

	public static int setBlue(int color, int blue) {
		int alpha = getAlpha(color);
		int red = getRed(color);
		int green = getGreen(color);
		return getColor(alpha, red, green, blue);
	}

	public static int getInverse(int color) {
		int alpha = getAlpha(color);
		int red = 255 - getRed(color);
		int green = 255 - getGreen(color);
		int blue = 255 - getBlue(color);
		return getColor(alpha, red, green, blue);
	}
}
