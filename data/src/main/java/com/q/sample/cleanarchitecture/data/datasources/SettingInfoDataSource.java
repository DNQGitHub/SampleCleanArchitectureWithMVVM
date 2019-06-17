package com.q.sample.cleanarchitecture.data.datasources;

import com.q.sample.cleanarchitecture.data.entities.SettingInfoEntity;

public interface SettingInfoDataSource
{
    SettingInfoEntity loadSettingInfo() throws Exception;
    void saveSettingInfo(SettingInfoEntity settingInfoEntity) throws Exception;
}
