package com.q.sample.cleanarchitecture.views.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.q.sample.cleanarchitecture.R;
import com.q.sample.cleanarchitecture.domain.models.SettingInfoModel;
import com.q.sample.cleanarchitecture.utilities.ColorUtils;
import com.q.sample.cleanarchitecture.viewmodels.MainActivityViewModel;
import com.q.sample.cleanarchitecture.views.customs.RainbowColorPicker;
import com.q.sample.cleanarchitecture.views.customs.SeekBar;

public class MainActivity extends AppCompatActivity
{
	private TextView textView;
	private SeekBar seekBarTextSize;
	private RainbowColorPicker textColorPicker;
	private TextView textColorIndicator;
	private RainbowColorPicker backgroundColorPicker;
	private TextView backgroundColorIndicator;
	private Button btnApply;

	private MainActivityViewModel viewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mapViews();
		setupViews();
		viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
		viewModel.getSettingInfo().observe(this, new Observer<SettingInfoModel>()
		{
			@Override
			public void onChanged(@Nullable SettingInfoModel settingInfoModel) {
				textView.setTextSize(settingInfoModel.getTextSize());
				textView.setTextColor(settingInfoModel.getTextColor());
				textView.setBackgroundColor(settingInfoModel.getBackgroundColor());

				seekBarTextSize.setValue(settingInfoModel.getTextSize());
				seekBarTextSize.setIndicatorInnerCircleColor(settingInfoModel.getBackgroundColor());
				seekBarTextSize.setIndicatorOuterCircleColor(ColorUtils.setAlpha(settingInfoModel.getBackgroundColor(), 100));
				seekBarTextSize.setValueBarColor(settingInfoModel.getTextColor());
				seekBarTextSize.setValueBarProgressColor(settingInfoModel.getTextColor());

				textColorPicker.setSelectedColor(settingInfoModel.getTextColor());

				textColorIndicator.setBackgroundColor(settingInfoModel.getTextColor());

				backgroundColorPicker.setSelectedColor(settingInfoModel.getBackgroundColor());

				backgroundColorIndicator.setBackgroundColor(settingInfoModel.getBackgroundColor());
			}
		});

		viewModel.getMessage().observe(this, new Observer<String>()
		{
			@Override
			public void onChanged(@Nullable String message) {
				Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void mapViews() {
		textView = findViewById(R.id.textView);
		seekBarTextSize = findViewById(R.id.seekBarTextSize);
		textColorPicker = findViewById(R.id.textColorPicker);
		textColorIndicator = findViewById(R.id.textColorIndicator);
		backgroundColorPicker = findViewById(R.id.backgroundColorPicker);
		backgroundColorIndicator = findViewById(R.id.backgroundColorIndicator);
		btnApply = findViewById(R.id.btnApply);
	}

	private void setupViews() {
		seekBarTextSize.setOnChangeValueCallback(new SeekBar.OnChangeValueCallback()
		{
			@Override
			public void onChangeValue(float oldValue, float newValue) {
				viewModel.setTextSize(newValue);
			}
		});

		textColorPicker.setOnSelectColorCallback(new RainbowColorPicker.OnSelectColorCallback()
		{
			@Override
			public void onSelectColor(int newColor) {
				viewModel.setTextColor(newColor);
			}
		});

		backgroundColorPicker.setOnSelectColorCallback(new RainbowColorPicker.OnSelectColorCallback()
		{
			@Override
			public void onSelectColor(int newColor) {
				viewModel.setBackgroundColor(newColor);
			}
		});

		btnApply.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				viewModel.saveSettingInfo();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
	}
}
