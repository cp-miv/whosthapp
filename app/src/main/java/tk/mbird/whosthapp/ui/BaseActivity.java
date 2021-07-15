package tk.mbird.whosthapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.ContentView;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.ParameterizedType;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import tk.mbird.whosthapp.viewmodels.ViewModelProviderFactory;

public abstract class BaseActivity<TActivityViewModel extends BaseViewModel, TViewDataBinding extends ViewDataBinding> extends AppCompatActivity implements HasAndroidInjector {

    @Inject
    public ViewModelProviderFactory mViewModelFactory;

    private TActivityViewModel mActivityViewModel;
    private TViewDataBinding mViewDataBinding;

    @LayoutRes
    private int mContentLayoutId;
    @LayoutRes
    private int mBindingVariable;

    private ViewModelProvider mViewModelProvider;

    @ContentView
    public BaseActivity(@LayoutRes int contentLayoutId, @LayoutRes int bindingVariable) {
        super(contentLayoutId);

        this.mContentLayoutId = contentLayoutId;
        this.mBindingVariable = bindingVariable;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initInstance();
        this.initDataBinding();
    }

    protected void initInstance() {
        AndroidInjection.inject(this);

        this.mViewModelProvider = new ViewModelProvider(this, this.mViewModelFactory);
        this.mActivityViewModel = createActivityViewModel();

        this.mViewDataBinding = DataBindingUtil.setContentView(this, this.mContentLayoutId);
        this.mViewDataBinding.setLifecycleOwner(this);
    }

    protected void initDataBinding() {
        mViewDataBinding.setVariable(this.mBindingVariable, this.mActivityViewModel);
        mViewDataBinding.executePendingBindings();

        ButterKnife.bind(this);
    }

    protected TActivityViewModel createActivityViewModel() {
        Class<TActivityViewModel> viewModelType = (Class<TActivityViewModel>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        TActivityViewModel viewModel = this.mViewModelProvider.get(viewModelType);
        return viewModel;
    }


    protected TActivityViewModel getActivityViewModel() {
        return mActivityViewModel;
    }

    protected TViewDataBinding getViewDataBinding() {
        return mViewDataBinding;
    }

    protected ViewModelProvider getViewModelProvider() {
        return mViewModelProvider;
    }


    @Inject
    DispatchingAndroidInjector<Object> androidInjector;

    @Override
    public AndroidInjector<Object> androidInjector() {
        return androidInjector;
    }
}
