package com.common.library.communicate;

import android.app.Application;

/**
 * user: zhangjianfeng
 * date: 06/09/2017
 * version: 7.3
 */

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        CommunicateManager.init();
    }
}
