package com.q.sample.cleanarchitecture.domain.repositories;

import com.q.sample.cleanarchitecture.domain.models.SettingInfoModel;

public interface SettingInfoRepository
{
    SettingInfoModel loadSettingInfo() throws Exception;
    void saveSettingInfo(SettingInfoModel settingInfoModel) throws Exception;
}
