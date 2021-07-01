package com.free.player.ad;

import android.app.Application;

import com.free.player.common.BaseApplication;

public class App extends BaseApplication {
    private static App app;

    public static Application appContext() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}
