package com.example.daidaijie.memoapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;

import java.lang.reflect.Method;

import cn.nekocode.emojix.Emojix;

/**
 * Created by daidaijie on 2016/8/5.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
        invalidateOptionsMenu();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Emojix.wrap(newBase));
    }


}
