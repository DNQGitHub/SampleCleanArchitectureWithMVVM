package android.arch.lifecycle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class EventLiveData<T> extends MutableLiveData<T>
{
    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer) {
        ObserverWrapper observerWrapper = new ObserverWrapper(observer);
        super.observe(owner, observerWrapper);
    }

    @Override
    public void observeForever(@NonNull Observer<T> observer) {
        ObserverWrapper observerWrapper = new ObserverWrapper(observer);
        super.observeForever(observerWrapper);
    }

    class ObserverWrapper implements Observer<T>{
        int version = START_VERSION;
        Observer<T> observer;

        public ObserverWrapper(Observer<T> observer) {
            this.observer = observer;
            this.version = EventLiveData.this.getVersion();
        }

        @Override
        public void onChanged(@Nullable T t) {
            int dataVersion = EventLiveData.this.getVersion();
            if(this.version != dataVersion) {
                this.version = dataVersion;
                observer.onChanged(t);
            }
        }
    }
}
