package com.q.sample.cleanarchitecture.data.datasources.local.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.q.sample.cleanarchitecture.data.adapters.SettingInfoAdapter;
import com.q.sample.cleanarchitecture.data.datasources.SettingInfoDataSource;
import com.q.sample.cleanarchitecture.data.entities.SettingInfoEntity;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingInfoPreference implements SettingInfoDataSource
{
    private static SettingInfoPreference self;

    private SharedPreferences sharedPreferences;

    public SettingInfoPreference(Context context) {
        sharedPreferences = context.getSharedPreferences("database", Context.MODE_PRIVATE);
    }

    @Override
    public SettingInfoEntity loadSettingInfo() throws Exception {
        String     settingJSONString = sharedPreferences.getString("setting_info", "{}");
        JSONObject jsonObject        = new JSONObject(settingJSONString);
        if(jsonObject.length() == 0)
            return null;
        return SettingInfoAdapter.convertFrom(jsonObject);
    }

    @Override
    public void saveSettingInfo(SettingInfoEntity settingInfoEntity) throws Exception {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("setting_info", settingInfoEntity.toString());
        editor.apply();
    }
}
