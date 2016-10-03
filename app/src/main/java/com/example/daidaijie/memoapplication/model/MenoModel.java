package com.example.daidaijie.memoapplication.model;

import com.example.daidaijie.memoapplication.bean.MenoBean;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by daidaijie on 2016/8/4.
 */
public class MenoModel {

    private Realm mRealm;

    public MenoModel(Realm realm) {
        mRealm = realm;
    }

    public RealmResults<MenoBean> getmMenoBeen() {
        RealmResults<MenoBean> mMenoBeen = mRealm.where(MenoBean.class).findAll().sort("createTime", Sort.DESCENDING);
        return mMenoBeen;
    }

    public void removeByPos(String uuid) {
        final MenoBean menoBean = mRealm.where(MenoBean.class).equalTo("mUUID", uuid).findFirst();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                menoBean.deleteFromRealm();
            }
        });
    }

    public void updateOrCopy(final MenoBean bean) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(bean);
            }
        });
    }

    public MenoBean getMenoBean(String uuid) {
        return mRealm.where(MenoBean.class).equalTo("mUUID", uuid).findFirst();
    }

}
