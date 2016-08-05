package com.example.daidaijie.memoapplication;

import android.app.Application;
import android.content.Context;

/**
 * Created by daidaijie on 2016/8/5.
 */
public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

    }

    public static Context getContext() {
        return context;
    }

}
