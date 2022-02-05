package com.dc.news.application;

import com.billy.cc.core.component.CC;
import com.dc.base.BaseApplication;
import com.dc.base.BuildConfig;
import com.dc.base.loadsir.CustomCallback;
import com.dc.base.loadsir.EmptyCallback;
import com.dc.base.loadsir.ErrorCallback;
import com.dc.base.loadsir.LoadingCallback;
import com.dc.base.loadsir.TimeoutCallback;
import com.kingja.loadsir.core.LoadSir;

public class NewsApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        setDebug(BuildConfig.DEBUG);

        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())//添加各种状态页
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new CustomCallback())
                .setDefaultCallback(LoadingCallback.class)//设置默认状态页
                .commit();

        CC.enableDebug(BuildConfig.DEBUG);
        CC.enableVerboseLog(BuildConfig.DEBUG);
        CC.enableRemoteCC(BuildConfig.DEBUG);
    }
}
