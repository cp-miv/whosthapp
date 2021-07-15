package tk.mbird.whosthapp.ui;

import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.ViewModel;

import java.lang.ref.WeakReference;

public class BaseViewModel<TNavigator extends BaseNavigator> extends ViewModel implements Observable {

    private WeakReference<TNavigator> mNavigator;


    public BaseViewModel() {
    }


    public TNavigator getNavigator() {
        return mNavigator != null ? mNavigator.get() : null;
    }

    public void setNavigator(TNavigator navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }


    private transient PropertyChangeRegistry mCallbacks;

    @Override
    public synchronized void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        if (mCallbacks == null) {
            mCallbacks = new PropertyChangeRegistry();
        }

        mCallbacks.add(callback);
    }

    @Override
    public synchronized void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        if (mCallbacks != null) {
            mCallbacks.remove(callback);
        }
    }

    public synchronized void notifyChange() {
        if (mCallbacks != null) {
            mCallbacks.notifyCallbacks(this, 0, null);
        }
    }

    public void notifyPropertyChanged(int fieldId) {
        if (mCallbacks != null) {
            mCallbacks.notifyCallbacks(this, fieldId, null);
        }
    }
}
