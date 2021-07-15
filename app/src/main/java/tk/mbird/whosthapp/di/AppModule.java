package tk.mbird.whosthapp.di;

import android.app.ActionBar;
import android.app.Application;
import android.content.Context;
import android.net.wifi.WifiManager;

import dagger.Binds;
import dagger.BindsInstance;
import dagger.Module;
import dagger.Provides;
import tk.mbird.whosthapp.BaseApplication;

@Module
public abstract class AppModule {

    @Binds
    public abstract Application provideApplication(BaseApplication application);

    @Provides
    public static Context provideContext(Application application) {
        return application.getApplicationContext();
    }
}
