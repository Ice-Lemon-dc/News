package com.dc.news.application;

import com.alibaba.android.arouter.launcher.ARouter;
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
                //添加各种状态页
                .addCallback(new ErrorCallback())
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new CustomCallback())
                //设置默认状态页
                .setDefaultCallback(LoadingCallback.class)
                .commit();

        if (BuildConfig.DEBUG) {
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
    }
}
