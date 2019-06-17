package com.q.sample.cleanarchitecture.domain.usecases;

import android.os.AsyncTask;

import com.q.sample.cleanarchitecture.domain.models.SettingInfoModel;
import com.q.sample.cleanarchitecture.domain.repositories.SettingInfoRepository;

public class SaveSettingInfoUseCase
{
    private SettingInfoRepository settingInfoRepository;

    public SaveSettingInfoUseCase(SettingInfoRepository settingInfoRepository) {
        this.settingInfoRepository = settingInfoRepository;
    }

    public void execute(SettingInfoModel settingInfo) throws Exception {
        if(settingInfo != null) {
            settingInfoRepository.saveSettingInfo(settingInfo);
        }
    }

    public void enqueue(Callback callback, SettingInfoModel settingInfo) {
        Executor executor = new Executor(callback);
        executor.execute(settingInfo);
    }

    final class Executor extends AsyncTask<SettingInfoModel, Void, Void> {
        Callback callback;
        Throwable error;

        public Executor(Callback callback) {
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(SettingInfoModel... settingInfoModels) {
            try {
                SaveSettingInfoUseCase.this.execute(settingInfoModels[0]);
            } catch(Exception e) {
                error = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            if(callback != null) {
                if(error != null)
                    callback.onFailure(error);
                else
                    callback.onSuccess();
            }
        }
    }

    public interface Callback {
        void onSuccess();
        void onFailure(Throwable error);
    }
}
