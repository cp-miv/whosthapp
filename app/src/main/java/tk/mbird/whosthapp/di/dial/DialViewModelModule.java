package tk.mbird.whosthapp.di.dial;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import tk.mbird.whosthapp.di.ViewModelKey;
import tk.mbird.whosthapp.ui.dial.DialViewModel;

@Module
public abstract class DialViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DialViewModel.class)
    public abstract ViewModel bindActivityViewModel(DialViewModel viewModel);
}