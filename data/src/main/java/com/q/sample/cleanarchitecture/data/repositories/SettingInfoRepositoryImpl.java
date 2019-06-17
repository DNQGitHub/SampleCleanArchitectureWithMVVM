package com.q.sample.cleanarchitecture.data.repositories;

import android.content.Context;
import android.util.Log;

import com.q.sample.cleanarchitecture.data.adapters.SettingInfoAdapter;
import com.q.sample.cleanarchitecture.data.datasources.SettingInfoDataSource;
import com.q.sample.cleanarchitecture.data.datasources.cached.SettingInfoCache;
import com.q.sample.cleanarchitecture.data.datasources.local.preferences.SettingInfoPreference;
import com.q.sample.cleanarchitecture.data.entities.SettingInfoEntity;
import com.q.sample.cleanarchitecture.domain.models.SettingInfoModel;
import com.q.sample.cleanarchitecture.domain.repositories.SettingInfoRepository;

public class SettingInfoRepositoryImpl implements SettingInfoRepository
{
    private static SettingInfoRepositoryImpl self;
    private SettingInfoDataSource settingInfoPreference;
    private SettingInfoDataSource settingInfoCache;

    private SettingInfoRepositoryImpl(Context context) {
        settingInfoPreference = new SettingInfoPreference(context);
        settingInfoCache = new SettingInfoCache();
    }

    public static SettingInfoRepository getInstance(Context context) {
        if(self == null) {
            synchronized(SettingInfoRepositoryImpl.class) {
                if(self == null) {
                    self = new SettingInfoRepositoryImpl(context);
                }
            }
        }
        return self;
    }

    @Override
    public SettingInfoModel loadSettingInfo() throws Exception{
        SettingInfoEntity settingInfoEntity = settingInfoCache.loadSettingInfo();
        if(settingInfoEntity == null) {
            settingInfoEntity = settingInfoPreference.loadSettingInfo();
            if(settingInfoEntity != null)
                settingInfoCache.saveSettingInfo(settingInfoEntity);
        }
        return SettingInfoAdapter.convertFrom(settingInfoEntity);
    }

    @Override
    public void saveSettingInfo(SettingInfoModel settingInfoModel) throws Exception {
        SettingInfoEntity settingInfoEntity = SettingInfoAdapter.convertFrom(settingInfoModel);
        settingInfoPreference.saveSettingInfo(settingInfoEntity);
        settingInfoCache.saveSettingInfo(settingInfoEntity);
    }
}
