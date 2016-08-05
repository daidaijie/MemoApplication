package com.example.daidaijie.memoapplication.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.daidaijie.memoapplication.App;
import com.example.daidaijie.memoapplication.bean.MenoBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daidaijie on 2016/8/4.
 */
public class MenoModel {

    private static MenoModel ourInstance = new MenoModel();
    public List<MenoBean> mMenoBeen;
    private Gson mGson;

    private Context mContext;
    private static final String MEMO = "MenoBeen";
    private static final String TAG_BEEN = "App";

    public static MenoModel getInstance() {
        return ourInstance;
    }

    private MenoModel() {
        mContext = App.getContext();
        mGson = new Gson();
        init();
    }

    public void init() {
        SharedPreferences sharedPreferences = mContext.
                getSharedPreferences(TAG_BEEN, Context.MODE_PRIVATE);
        String gsonString = sharedPreferences.getString(MEMO, "");

        if (gsonString.isEmpty()) {
            mMenoBeen = new ArrayList<>();
            return;
        }
        mMenoBeen = mGson.fromJson(gsonString, new TypeToken<List<MenoBean>>() {
        }.getType());
    }

    public void removeByPos(int position) {
        mMenoBeen.remove(position);
        save();
    }

    public void addByPos(MenoBean bean) {
        mMenoBeen.add(bean);
        save();
    }

    public void changeByPos(MenoBean bean, int pos) {
        mMenoBeen.set(pos, bean);
        save();
    }

    private void save() {
        SharedPreferences sharedPreferences = mContext.
                getSharedPreferences(TAG_BEEN, Context.MODE_PRIVATE);
        String jsonString = mGson.toJson(mMenoBeen);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MEMO, jsonString);
        editor.commit();
    }
}
