package com.q.sample.cleanarchitecture.data.datasources.cached;

import com.q.sample.cleanarchitecture.data.datasources.SettingInfoDataSource;
import com.q.sample.cleanarchitecture.data.entities.SettingInfoEntity;

public class SettingInfoCache implements SettingInfoDataSource
{
    private SettingInfoEntity settingInfoEntity;

    public SettingInfoCache() {
        settingInfoEntity = null;
    }

    @Override
    public SettingInfoEntity loadSettingInfo() throws Exception {
        return settingInfoEntity;
    }

    @Override
    public void saveSettingInfo(SettingInfoEntity settingInfoEntity) throws Exception {
        this.settingInfoEntity = settingInfoEntity;
    }
}
