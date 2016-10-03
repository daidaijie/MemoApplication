package com.example.daidaijie.memoapplication;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by daidaijie on 2016/8/5.
 */
public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        RealmConfiguration configuration = new RealmConfiguration.Builder(this.getApplicationContext())
                .schemaVersion(1)
                .build();

        Realm.setDefaultConfiguration(configuration);

    }

    public static Context getContext() {
        return context;
    }

}
