package com.dc.base.fragment;

import com.dc.base.activity.IBaseView;

public interface IBasePagingView extends IBaseView {

    void onLoadMoreFailure(String message);

    void onLoadMoreEmpty();
}
