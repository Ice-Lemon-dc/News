package com.dc.base.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dc.base.loadsir.EmptyCallback;
import com.dc.base.loadsir.ErrorCallback;
import com.dc.base.loadsir.LoadingCallback;
import com.dc.base.viewmodel.BaseViewModel;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

public abstract class BaseActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity implements IBaseView{
    protected VM viewModel;
    protected V viewDataBinding;
    private LoadService mLoadService;

    public abstract
    @LayoutRes
    int getLayoutId();

    protected abstract VM getViewModel();

    public abstract int getBindingVariable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
        performDataBinding();
    }

    private void initViewModel() {
        viewModel = getViewModel();
        if (viewModel != null) {
            viewModel.attachUI(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewModel != null && viewModel.isUIAttached()) {
            viewModel.detachUI();
        }
    }

    protected void onRetryBtnClick() {

    }

    @Override
    public void onRefreshEmpty() {
        if(mLoadService != null) {
            mLoadService.showCallback(EmptyCallback.class);
        }
    }

    @Override
    public void onRefreshFailure(String message) {
        if(mLoadService != null) {
            mLoadService.showCallback(ErrorCallback.class);
        }
    }

    @Override
    public void showLoading() {
        if (mLoadService != null) {
            mLoadService.showCallback(LoadingCallback.class);
        }
    }

    @Override
    public void showContent() {
        if (mLoadService != null) {
            mLoadService.showSuccess();
        }
    }

    public void setLoadSir(View view) {
        // You can change the callback on sub thread directly.
        mLoadService = LoadSir.getDefault().register(view, (Callback.OnReloadListener) v -> onRetryBtnClick());
    }

    private void performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        if(viewModel == null){
            this.viewModel = getViewModel();
        }
        if(getBindingVariable() > 0) {
            viewDataBinding.setVariable(getBindingVariable(), viewModel);
        }
        viewDataBinding.executePendingBindings();
    }
}
