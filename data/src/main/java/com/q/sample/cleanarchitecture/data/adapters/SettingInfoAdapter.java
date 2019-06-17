package com.q.sample.cleanarchitecture.data.adapters;

import android.graphics.Color;

import com.q.sample.cleanarchitecture.data.entities.SettingInfoEntity;
import com.q.sample.cleanarchitecture.domain.models.SettingInfoModel;

import org.json.JSONObject;

public final class SettingInfoAdapter
{
    private SettingInfoAdapter() {}

    public static SettingInfoEntity convertFrom(SettingInfoModel settingInfoModel) {
        if(settingInfoModel == null) return null;

        SettingInfoEntity settingInfoEntity = new SettingInfoEntity();
        settingInfoEntity.setTextSize(settingInfoModel.getTextSize());
        settingInfoEntity.setTextColor(String.format(SettingInfoEntity.COLOR_STRING_FORMAT, settingInfoModel.getTextColor()));
        settingInfoEntity.setBackgroundColor(String.format(SettingInfoEntity.COLOR_STRING_FORMAT, settingInfoModel.getBackgroundColor()));

        return settingInfoEntity;
    }

    public static SettingInfoEntity convertFrom(JSONObject jsonObject) {
        if(jsonObject == null) return null;
        SettingInfoEntity settingInfoEntity = new SettingInfoEntity();

        settingInfoEntity.setTextSize((float)jsonObject.optDouble(SettingInfoEntity._TEXT_SIZE, SettingInfoEntity.DEFAULT_TEXT_SIZE));
        settingInfoEntity.setTextColor(jsonObject.optString(SettingInfoEntity._TEXT_COLOR, SettingInfoEntity.DEFAULT_TEXT_COLOR));
        settingInfoEntity.setBackgroundColor(jsonObject.optString(SettingInfoEntity._BACKGROUND_COLOR, SettingInfoEntity.DEFAULT_BACKGROUND_COLOR));

        return settingInfoEntity;
    }

    public static SettingInfoModel convertFrom(SettingInfoEntity settingInfoEntity) {
        if(settingInfoEntity == null) return null;

        SettingInfoModel settingInfoModel = new SettingInfoModel();
        settingInfoModel.setTextSize(settingInfoEntity.getTextSize());
        settingInfoModel.setTextColor(Color.parseColor(settingInfoEntity.getTextColor()));
        settingInfoModel.setBackgroundColor(Color.parseColor(settingInfoEntity.getBackgroundColor()));

        return settingInfoModel;
    }
}
