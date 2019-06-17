package com.q.sample.cleanarchitecture.views.customs;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.q.sample.cleanarchitecture.R;
import com.q.sample.cleanarchitecture.utilities.ColorUtils;
import com.q.sample.cleanarchitecture.utilities.ResolutionAdapter;

public class SeekBar extends View
{
	private static final float DEFAULT_MIN_VALUE = 0;
	private static final float DEFAULT_MAX_VALUE = 100;
	private static final int DEFAULT_INDICATOR_RADIUS = (int)ResolutionAdapter.convertDpToPx(15);
	private static final int DEFAULT_BAR_STROKE_WIDTH = (int)ResolutionAdapter.convertDpToPx(2);
	private static final int DEFAULT_PROGRESS_STROKE_WIDTH = (int)ResolutionAdapter.convertDpToPx(6);
	private static final int DEFAULT_HINT_TEXT_SIZE = (int)ResolutionAdapter.convertSpToPx(10);
	private static final int MIN_VALUE_BAR_LENGTH = 2 * 2 * DEFAULT_INDICATOR_RADIUS;

	private ValueBar valueBar;
	private ValueIndicator valueIndicator;
	private ValueHint valueHint;

	private float minValue;
	private float maxValue;
	private float value;

	private OnChangeValueCallback onChangeValueCallback;

	public SeekBar(Context context) {
		super(context);
		init(context, null, 0, 0);
	}

	public SeekBar(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0, 0);
	}

	public SeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr, 0);
	}

	@TargetApi(21)
	public SeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init(context, attrs, defStyleAttr, defStyleRes);
	}

	public void setValue(float value) {
		if(this.value != value)
		{
			this.value = value;
			invalidate();
		}
	}

	public void setOnChangeValueCallback(OnChangeValueCallback callback) {
		this.onChangeValueCallback = callback;
	}

	public void setIndicatorOuterCircleColor(int color) {
		valueIndicator.setOuterCircleColor(color);
		invalidate();
	}

	public void setIndicatorInnerCircleColor(int color) {
		valueIndicator.setInnerCircleColor(color);
		invalidate();
	}

	public void setValueBarColor(int barColor) {
		valueBar.setBarColor(barColor);
		invalidate();
	}

	public void setValueBarProgressColor(int progressColor) {
		valueBar.setProgressColor(progressColor);
		invalidate();
	}

	private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		valueBar = new ValueBar();
		valueIndicator = new ValueIndicator();
		valueHint = new ValueHint(context);

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SeekBar, defStyleAttr, defStyleRes);

		minValue = typedArray.getFloat(R.styleable.SeekBar_min_value, DEFAULT_MIN_VALUE);
		maxValue = typedArray.getFloat(R.styleable.SeekBar_max_value, DEFAULT_MAX_VALUE);
		value = minValue;
		valueBar.barColor = typedArray.getColor(R.styleable.SeekBar_value_bar_color, 0xFF5b5b5b);
		valueBar.progressColor = typedArray.getColor(R.styleable.SeekBar_value_progress_bar_color, 0xFF5b5b5b);
		valueIndicator.outerCircleColor = typedArray.getColor(R.styleable.SeekBar_value_indicator_outer_circle_color, 0x805b5b5b);
		valueIndicator.innerCircleColor = typedArray.getColor(R.styleable.SeekBar_value_indicator_inner_circle_color, 0xFF5b5b5b);

		typedArray.recycle();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		valueBar.draw(canvas);
		valueIndicator.draw(canvas);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int sizeWidth  = MeasureSpec.getSize(widthMeasureSpec);
		int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

		int desiredWidth  = MIN_VALUE_BAR_LENGTH;
		int desiredHeight = 2 * DEFAULT_INDICATOR_RADIUS;

		int width  = sizeWidth < desiredWidth ? desiredWidth : sizeWidth;
		int height = sizeHeight < desiredHeight ? desiredHeight : sizeHeight;

		int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
		if(modeHeight == MeasureSpec.AT_MOST)
			height = desiredHeight;
		setMeasuredDimension(width, height);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);

		valueBar.x = getPaddingLeft() + valueIndicator.outerCircleRadius;
		valueBar.y = getHeight() / 2;
		valueBar.length = getWidth() - getPaddingRight() - getPaddingLeft() - 2 * valueIndicator.outerCircleRadius;

		valueIndicator.x = getPaddingLeft() + valueIndicator.outerCircleRadius;
		valueIndicator.y = getHeight() / 2;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_MOVE:
				float touchX = event.getX();
				if(touchX < valueBar.x)
				{
					touchX = valueBar.x;
				}
				else if(touchX > valueBar.x + valueBar.length)
				{
					touchX = valueBar.x + valueBar.length;
				}
				float newValue = (minValue + (maxValue - minValue) * (touchX - valueBar.x) / valueBar.length);
				onChangeValue(newValue);


				valueHint.setText(String.format("%.1f", newValue));
				valueHint.setX(calculateHintX(event));
				valueHint.setY(calculateHintY(event));
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					valueHint.show();
				}

				break;
			case MotionEvent.ACTION_UP:
				valueHint.hide();
		}
		return true;
	}

	float calculateHintX(MotionEvent motionEvent) {
		float screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
		float hintX       = motionEvent.getRawX() - valueHint.getWidth() / 2;
		if(hintX < 0) hintX = 0;
		else if(hintX + valueHint.getWidth() > screenWidth)
			hintX = hintX - ((hintX + valueHint.getWidth()) - screenWidth);
		return hintX;
	}

	float calculateHintY(MotionEvent motionEvent) {

		int     statusBarHeight  = getStatusBarHeight();
		boolean statusBarVisible = isStatusBarVisible();

		float hintY = motionEvent.getRawY() - motionEvent.getY() + getHeight() / 2 - valueIndicator.outerCircleRadius - valueHint.getHeight();
		if((statusBarVisible && hintY < 0 + statusBarHeight) || hintY < 0)
			hintY = motionEvent.getRawY() - motionEvent.getY() + getHeight() / 2 + valueIndicator.outerCircleRadius;
		return hintY;
	}

	boolean isStatusBarVisible() {
		return ! ((getSystemUiVisibility() & View.SYSTEM_UI_FLAG_FULLSCREEN) == View.SYSTEM_UI_FLAG_FULLSCREEN);
	}

	int getStatusBarHeight() {
		int result     = 0;
		int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		if(resourceId > 0)
		{
			result = getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		View rootView = getRootView();
		if(rootView instanceof ViewGroup)
		{
			((ViewGroup) rootView).addView(valueHint);
		}
	}

	private void onChangeValue(float newValue) {
		if(newValue != value)
		{
			if(onChangeValueCallback != null)
			{
				onChangeValueCallback.onChangeValue(value, newValue);
			}
			setValue(newValue);
		}
	}

	public interface OnChangeValueCallback
	{
		void onChangeValue(float oldValue, float newValue);
	}

	class ValueIndicator
	{
		float x;
		float y;
		int outerCircleRadius;
		int outerCircleColor;
		int innerCircleRadius;
		int innerCircleColor;
		Paint paint;

		public ValueIndicator() {
			x = 0;
			y = 0;
			innerCircleRadius = DEFAULT_INDICATOR_RADIUS * 3 / 4;
			outerCircleRadius = DEFAULT_INDICATOR_RADIUS;
			innerCircleColor = Color.parseColor("#FF5b5b5b");
			outerCircleColor = Color.parseColor("#805b5b5b");
			paint = new Paint();
		}

		public void setOuterCircleColor(int outerCircleColor) {
			this.outerCircleColor = outerCircleColor;
		}

		public void setInnerCircleColor(int innerCircleColor) {
			this.innerCircleColor = innerCircleColor;
		}

		void draw(Canvas canvas) {
			x = valueBar.x + (value - minValue) * valueBar.length / (maxValue - minValue);

			paint.setAntiAlias(true);
			paint.setColor(outerCircleColor);
			canvas.drawCircle(x, y, outerCircleRadius, paint);

			paint.setColor(innerCircleColor);
			canvas.drawCircle(x, y, innerCircleRadius, paint);
		}
	}

	class ValueBar
	{
		float x;
		float y;
		float length;
		int barColor;
		int barStrokeWidth;
		int progressColor;
		int progressStrokeWidth;
		Paint paint;

		public ValueBar() {
			x = 0;
			y = 0;
			length = MIN_VALUE_BAR_LENGTH;
			barColor = Color.parseColor("#FF5b5b5b");
			barStrokeWidth = DEFAULT_BAR_STROKE_WIDTH;
			progressColor = Color.parseColor("#FF5b5b5b");
			progressStrokeWidth = DEFAULT_PROGRESS_STROKE_WIDTH;
			paint = new Paint();
		}

		public void setBarColor(int barColor) {
			this.barColor = barColor;
		}

		public void setProgressColor(int progressColor) {
			this.progressColor = progressColor;
		}

		void draw(Canvas canvas) {
			float progressLength = (value - minValue) * valueBar.length / (maxValue - minValue);
			paint.setAntiAlias(true);
			paint.setStrokeCap(Paint.Cap.ROUND);

			paint.setColor(barColor);
			paint.setStrokeWidth(barStrokeWidth);
			canvas.drawLine(x, y, x + length, y, paint);

			paint.setColor(progressColor);
			paint.setStrokeWidth(progressStrokeWidth);
			canvas.drawLine(x, y, x + progressLength, y, paint);
		}
	}

	class ValueHint extends android.support.v7.widget.AppCompatTextView
	{
		Paint paint;

		public ValueHint(Context context) {
			super(context);
			paint = new Paint();
			setAlpha(0.0f);
			setTextSize(DEFAULT_HINT_TEXT_SIZE);
			setPadding(10, 10, 10, 10);

			ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			setLayoutParams(layoutParams);
		}

		@Override
		protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);

			float screenWidth  = Resources.getSystem().getDisplayMetrics().widthPixels;
			float newX = getX() + oldw / 2 - w / 2;
			if(newX < 0) newX = 0;
			else if(newX + w > screenWidth) newX = newX - ((newX + w) - screenWidth);
			setX(newX);

			if(getY() < SeekBar.this.getY() + SeekBar.this.getHeight() / 2)
			{
				float newY = getY() + oldh - h;
				if((isStatusBarVisible() && newY < 0 + getStatusBarHeight()) || newY < 0) {
					newY = newY + h + valueIndicator.outerCircleRadius * 2;
				}
				setY(newY);
			}
		}

		void show() {
			animate().alpha(1.0f).start();
		}

		void hide() {
			animate().alpha(0.0f).start();
		}

		@Override
		protected void onDraw(Canvas canvas) {
			paint.setStrokeCap(Paint.Cap.ROUND);
			paint.setColor(ColorUtils.setAlpha(valueBar.progressColor, 80));
			canvas.drawRect(0, getPaddingTop(), getWidth(), getHeight() - getPaddingBottom(), paint);
			setTextColor(valueIndicator.innerCircleColor);
			super.onDraw(canvas);
		}
	}
}
