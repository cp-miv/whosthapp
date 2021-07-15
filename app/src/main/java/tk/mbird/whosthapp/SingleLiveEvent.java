package tk.mbird.whosthapp;

import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.atomic.AtomicBoolean;

class SingleLiveEvent<T> extends MutableLiveData<T> {
    private static final String TAG = "SingleLiveEvent";

    private AtomicBoolean pending;

    public SingleLiveEvent() {
        pending = new AtomicBoolean(false);
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {

        if (hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.");

            super.observe(owner, o -> {
                if (pending.compareAndSet(true, false)) {
                    observer.onChanged(o);
                }
            });
        }
    }

    @Override
    public void setValue(T value) {
        pending.set(true);
        super.setValue(value);
    }
}
