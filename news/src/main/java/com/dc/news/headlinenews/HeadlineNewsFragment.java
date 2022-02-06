package com.dc.news.headlinenews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dc.news.R;
import com.dc.news.databinding.FragmentHeadlineNewsBinding;

@Route(path = "/news/HeadlineNewsFragment")
public class HeadlineNewsFragment extends Fragment {

    FragmentHeadlineNewsBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_headline_news, container, false);
        return mBinding.getRoot();
    }
}
