package tk.mbird.whosthapp.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import tk.mbird.whosthapp.ui.dial.DialActivity;
import tk.mbird.whosthapp.di.dial.DialViewModelModule;
import tk.mbird.whosthapp.di.dial.DialModule;
import tk.mbird.whosthapp.di.dial.DialScope;

@Module
public abstract class ActivityBuildersModule {

    @DialScope
    @ContributesAndroidInjector(modules = {DialViewModelModule.class, DialModule.class})
    abstract DialActivity contributeDialActivity();
}