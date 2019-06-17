package com.q.sample.cleanarchitecture.views.customs;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.q.sample.cleanarchitecture.utilities.Log;

public class RainbowColorPicker extends View
{
	private Background background;
	private Integer selectedColor;

	private OnSelectColorCallback onSelectColorCallback;

	public RainbowColorPicker(Context context) {
		super(context);
		init();
	}

	public RainbowColorPicker(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public RainbowColorPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	@TargetApi(21)
	public RainbowColorPicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init();
	}

	private void init() {
		background = new Background();
	}

	public Integer getSelectedColor() {
		return selectedColor;
	}

	public void setSelectedColor(int color) {
		if(selectedColor != null && selectedColor.intValue() == color) return;
		selectedColor = color;
	}

	public void setOnSelectColorCallback(OnSelectColorCallback callback) {
		onSelectColorCallback = callback;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_MOVE:
				float touchX = event.getX();
				float touchY = event.getY();

				if(touchX >= getPaddingLeft() && touchX <= getWidth() - getPaddingRight() && touchY >= getPaddingTop() && touchY <= getHeight() - getPaddingBottom())
				{
					int newColor = background.getColor((int) touchX, (int) touchY);
					onSelectColor(newColor);
				}
				break;
		}
		return true;
	}

	private void onSelectColor(int newColor) {
		if(selectedColor != newColor)
		{
			if(onSelectColorCallback != null)
			{
				onSelectColorCallback.onSelectColor(newColor);
			}
			setSelectedColor(newColor);
		}
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		int backgroundWidth  = getWidth() - getPaddingLeft() - getPaddingRight();
		int backgroundHeight = getHeight() - getPaddingTop() - getPaddingBottom();
		background.setSize(backgroundWidth, backgroundHeight);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		background.draw(canvas);
	}

	public interface OnSelectColorCallback
	{
		void onSelectColor(int newColor);
	}

	class Background
	{
		private final int[] DEFAULT_COLORS = new int[] {Color.rgb(243, 73, 74),
		                                                Color.rgb(255, 120, 78),
		                                                Color.rgb(255, 192, 63),
		                                                Color.rgb(89, 191, 66),
		                                                Color.rgb(51, 203, 204),
		                                                Color.rgb(41, 124, 204),
		                                                Color.rgb(134, 66, 167)};
		int[] colors;
		Paint paint;
		LinearGradient shader;
		Bitmap bitmap;
		Canvas bitmapCanvas;

		public Background() {
			colors = DEFAULT_COLORS;
			paint = new Paint();
			setSize(10, 10);
		}

		void draw(Canvas canvas) {
			paint.setAntiAlias(true);
			paint.setShader(shader);
			bitmapCanvas.drawRect(0, 0, bitmap.getWidth(), bitmap.getHeight(), paint);
			canvas.drawBitmap(bitmap, 0, 0, paint);
		}

		int getColor(int x, int y) {
			if(x < 0) x = 0;
			else if(x >= bitmap.getWidth()) x = bitmap.getWidth() - 1;
			if(y < 0) y = 0;
			else if(y >= bitmap.getHeight()) y = bitmap.getHeight() - 1;
			return bitmap.getPixel(x, y);
		}

		void setSize(int width, int height) {
			shader = new LinearGradient(0, 0, width, height, colors, null, Shader.TileMode.MIRROR);
			bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			bitmapCanvas = new Canvas(bitmap);
			bitmapCanvas.drawRect(0, 0, bitmap.getWidth(), bitmap.getHeight(), paint);
		}
	}
}
