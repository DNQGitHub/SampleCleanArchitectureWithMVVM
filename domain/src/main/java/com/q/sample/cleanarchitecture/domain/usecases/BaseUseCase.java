package com.q.sample.cleanarchitecture.domain.usecases;

public abstract class BaseUseCase<T>
{
    public interface Callback<T> {
        void onSuccess(T obj);
        void onFailure(Throwable throwable);
    }
}
