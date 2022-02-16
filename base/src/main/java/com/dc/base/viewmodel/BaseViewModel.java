package com.dc.base.viewmodel;

import androidx.lifecycle.ViewModel;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public class BaseViewModel<V> extends ViewModel implements IBaseViewModel<V> {
    private Reference<V> mUIRef;

    @Override
    public void attachUI(V ui) {
        mUIRef = new WeakReference<>(ui);
    }

    @Override
    public V getPageView() {
        return null;
    }

    @Override
    public boolean isUIAttached() {
        return false;
    }

    @Override
    public void detachUI() {

    }
}
