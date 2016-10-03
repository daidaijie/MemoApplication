package com.example.daidaijie.memoapplication.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.daidaijie.memoapplication.App;
import com.example.daidaijie.memoapplication.bean.MenoBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by daidaijie on 2016/8/4.
 */
public class MenoModel {

    private static MenoModel ourInstance = new MenoModel();

    public static MenoModel getInstance() {
        return ourInstance;
    }

    private MenoModel() {
    }

    public RealmResults<MenoBean> getmMenoBeen(Realm realm) {
        RealmResults<MenoBean> mMenoBeen = realm.where(MenoBean.class).findAll();
        return mMenoBeen;
    }

    public void removeByPos(Realm realm, String uuid) {
        final MenoBean menoBean = realm.where(MenoBean.class).equalTo("mUUID", uuid).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                menoBean.deleteFromRealm();
            }
        });
    }

    public void updateOrCopy(Realm realm, final MenoBean bean) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(bean);
            }
        });
    }

    public MenoBean getMenoBean(Realm realm, String uuid) {
        return realm.where(MenoBean.class).equalTo("mUUID", uuid).findFirst();
    }

}
