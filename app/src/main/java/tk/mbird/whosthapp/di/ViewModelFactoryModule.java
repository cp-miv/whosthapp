package tk.mbird.whosthapp.di;

import android.util.Log;

import androidx.lifecycle.ViewModelProvider;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import tk.mbird.whosthapp.viewmodels.ViewModelProviderFactory;


@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory provideViewModelFactory(ViewModelProviderFactory viewModelFactory);

}