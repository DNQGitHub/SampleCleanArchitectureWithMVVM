package com.q.sample.cleanarchitecture.domain.models;

public class SettingInfoModel
{
	public static final float MIN_TEXT_SIZE = 12f;
	public static final float MAX_TEXT_SIZE = 50f;
	public static final float DEFAULT_TEXT_SIZE = (MAX_TEXT_SIZE - MIN_TEXT_SIZE) / 2;
	public static final int DEFAULT_TEXT_COLOR = 0xFFFFFFFF;
	public static final int DEFAULT_BACKGROUND_COLOR = 0xFF000000;

	private float textSize;
	private int textColor;
	private int backgroundColor;

	public SettingInfoModel() {
		textSize = DEFAULT_TEXT_SIZE;
		textColor = DEFAULT_TEXT_COLOR;
		backgroundColor = DEFAULT_BACKGROUND_COLOR;
	}

	public float getTextSize() {
		return textSize;
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	public int getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
}
