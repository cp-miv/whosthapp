package tk.mbird.whosthapp;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class BaseApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return tk.mbird.whosthapp.di.DaggerAppComponent.factory().create(this);
        //return null;
    }
}