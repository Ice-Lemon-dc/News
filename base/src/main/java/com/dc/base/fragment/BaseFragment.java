package com.dc.base.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.dc.base.loadsir.EmptyCallback;
import com.dc.base.loadsir.ErrorCallback;
import com.dc.base.utils.ToastUtil;
import com.dc.base.viewmodel.BaseViewModel;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

public abstract class BaseFragment <V extends ViewDataBinding, VM extends BaseViewModel>extends Fragment implements IBasePagingView{

    protected VM viewModel;
    protected V viewDataBinding;
    protected String mFragmentTag = "";
    private LoadService mLoadService;

    private boolean isShowedContent = false;

    public abstract int getBindingVariable();

    public abstract @LayoutRes int getLayoutId();

    public abstract VM getViewModel();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParameters();
    }

    /***
     *   初始化参数
     */
    protected void initParameters() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return viewDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = getViewModel();
        if (viewModel != null) {
            viewModel.attachUI(this);
        }
        if(getBindingVariable() > 0) {
            viewDataBinding.setVariable(getBindingVariable(), viewModel);
            viewDataBinding.executePendingBindings();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (viewModel != null && viewModel.isUIAttached()) {
            viewModel.detachUI();
        }
    }

    @Override
    public void onRefreshEmpty() {
        if(mLoadService != null){
            mLoadService.showCallback(EmptyCallback.class);
        }
    }

    @Override
    public void onRefreshFailure(String message) {
        if (mLoadService != null) {
            if(!isShowedContent) {
                mLoadService.showCallback(ErrorCallback.class);
            } else {
                ToastUtil.show(getContext(), message);
            }
        }
    }

    @Override
    public void showContent() {
        if (mLoadService != null) {
            isShowedContent = true;
            mLoadService.showSuccess();
        }
    }

    protected void onRetryBtnClick() {

    }

    public void setLoadSir(View view) {
        // You can change the callback on sub thread directly.
        mLoadService = LoadSir.getDefault().register(view, (Callback.OnReloadListener) v -> onRetryBtnClick());
    }

    protected abstract String getFragmentTag();
}
