package com.q.sample.cleanarchitecture.domain.usecases;

import android.os.AsyncTask;

import com.q.sample.cleanarchitecture.domain.models.SettingInfoModel;
import com.q.sample.cleanarchitecture.domain.repositories.SettingInfoRepository;
import com.q.sample.cleanarchitecture.domain.throwables.SettingInfoNotFoundThrowable;

public final class LoadSettingInfoUseCase
{
    private SettingInfoRepository settingInfoRepository;

    public LoadSettingInfoUseCase(SettingInfoRepository settingInfoRepository) {
        this.settingInfoRepository = settingInfoRepository;
    }

    public SettingInfoModel execute() throws Exception{
        return settingInfoRepository.loadSettingInfo();
    }

    public void enqueue(final Callback callback) {
        Executor executor = new Executor(callback);
        executor.execute();
    }

    final class Executor extends AsyncTask<Void, Throwable, SettingInfoModel> {

        private Callback callback;
        private Throwable error;

        public Executor(Callback callback) {
            this.callback = callback;
        }

        @Override
        protected SettingInfoModel doInBackground(Void... voids) {
            try {
                return LoadSettingInfoUseCase.this.execute();
            } catch(Exception e) {
                error = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(SettingInfoModel settingInfoModel) {
            if(callback != null) {
                if(error != null)
                    callback.onFailure(error);
                else if(settingInfoModel == null)
                    callback.onFailure(new SettingInfoNotFoundThrowable());
                else
                    callback.onSuccess(settingInfoModel);
            }
        }
    }

    public interface Callback {
        void onSuccess(SettingInfoModel settingInfoModel);
        void onFailure(Throwable error);
    }
}
