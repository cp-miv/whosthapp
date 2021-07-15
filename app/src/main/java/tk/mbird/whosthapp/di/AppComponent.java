package tk.mbird.whosthapp.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import tk.mbird.whosthapp.BaseApplication;

@Singleton
@Component(modules = {AppModule.class, AndroidInjectionModule.class, ActivityBuildersModule.class, ViewModelFactoryModule.class})
public interface AppComponent extends AndroidInjector<BaseApplication> {

    @Component.Factory
    interface Factory {

        AppComponent create(@BindsInstance BaseApplication application);
    }
}
