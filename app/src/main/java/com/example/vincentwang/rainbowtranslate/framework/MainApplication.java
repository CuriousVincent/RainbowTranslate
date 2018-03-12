package com.example.vincentwang.rainbowtranslate.framework;

import android.app.Application;

import com.example.vincentwang.rainbowtranslate.factory.ServiceFactory;

/**
 * Created by vincentwang on 2017/7/26.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ServiceFactory.init(this);

    }
}
