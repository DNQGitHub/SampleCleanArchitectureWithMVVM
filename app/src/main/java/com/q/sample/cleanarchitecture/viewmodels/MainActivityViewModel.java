package com.q.sample.cleanarchitecture.viewmodels;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.EventLiveData;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.q.sample.cleanarchitecture.data.repositories.SettingInfoRepositoryImpl;
import com.q.sample.cleanarchitecture.domain.models.SettingInfoModel;
import com.q.sample.cleanarchitecture.domain.repositories.SettingInfoRepository;
import com.q.sample.cleanarchitecture.domain.throwables.SettingInfoNotFoundThrowable;
import com.q.sample.cleanarchitecture.domain.usecases.LoadSettingInfoUseCase;
import com.q.sample.cleanarchitecture.domain.usecases.SaveSettingInfoUseCase;
import com.q.sample.cleanarchitecture.utilities.Log;

public class MainActivityViewModel extends AndroidViewModel
{
    private MutableLiveData<SettingInfoModel> settingInfo;
    private EventLiveData<String> message;

    private SettingInfoRepository settingInfoRepository;
    private LoadSettingInfoUseCase loadSettingInfoUseCase;
    private SaveSettingInfoUseCase saveSettingInfoUseCase;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        settingInfo = new MutableLiveData<>();
        message = new EventLiveData<>();

        settingInfoRepository = SettingInfoRepositoryImpl.getInstance(application);
        loadSettingInfoUseCase = new LoadSettingInfoUseCase(settingInfoRepository);
        saveSettingInfoUseCase = new SaveSettingInfoUseCase(settingInfoRepository);

        loadSettingInfo();
    }

    public LiveData<SettingInfoModel> getSettingInfo() {
        return settingInfo;
    }

    public LiveData<String> getMessage() {
        return message;
    }

    public void setTextSize(float textSize) {
        SettingInfoModel settingInfoModel = settingInfo.getValue();
        if(settingInfoModel != null) {
            settingInfoModel.setTextSize(textSize);
            settingInfo.setValue(settingInfoModel);
        }
    }

    public void setTextColor(int textColor) {
        SettingInfoModel settingInfoModel = settingInfo.getValue();
        if(settingInfoModel != null) {
            settingInfoModel.setTextColor(textColor);
            settingInfo.setValue(settingInfoModel);
        }
    }

    public void setBackgroundColor(int backgroundColor) {
        SettingInfoModel settingInfoModel = settingInfo.getValue();
        if(settingInfoModel != null) {
            settingInfoModel.setBackgroundColor(backgroundColor);
            settingInfo.setValue(settingInfoModel);
        }
    }

    private void loadSettingInfo() {
        loadSettingInfoUseCase.enqueue(new LoadSettingInfoUseCase.Callback() {
            @Override
            public void onSuccess(SettingInfoModel settingInfoModel) {
                settingInfo.postValue(settingInfoModel);
                message.setValue("load successful !!");
            }

            @Override
            public void onFailure(Throwable error) {
                if(error instanceof SettingInfoNotFoundThrowable) {
                    settingInfo.postValue(new SettingInfoModel());
                    message.setValue(error.getMessage() + ", create new one !!");
                } else {
                    message.setValue("load error: " + error.getMessage());
                }
            }
        });
    }

    public void saveSettingInfo() {
        saveSettingInfoUseCase.enqueue(new SaveSettingInfoUseCase.Callback() {
            @Override
            public void onSuccess() {
                message.setValue("save successful !!");
            }

            @Override
            public void onFailure(Throwable error) {
                message.setValue("save error: " + error.getMessage());
            }
        }, settingInfo.getValue());
    }
}
