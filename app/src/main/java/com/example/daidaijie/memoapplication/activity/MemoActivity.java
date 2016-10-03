package com.example.daidaijie.memoapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.daidaijie.memoapplication.R;
import com.example.daidaijie.memoapplication.bean.MenoBean;
import com.example.daidaijie.memoapplication.model.MenoModel;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class MemoActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.menoTitleEditText)
    EditText mMenoTitleEditText;
    @BindView(R.id.saveButton)
    Button mSaveButton;
    @BindView(R.id.menoContentEdidText)
    EditText mMenoContentEdidText;

    private MenoBean mMenoBean;

    private static final String EXTRA_UUID = "com.example.daidaijie.memoapplication.activity.mUUID";

    private static final String EXTRA_MODE = "com.example.daidaijie.memoapplication.activity.mMode";

    private Realm mRealm;

    private int mMode;

    private String uuid;

    public static final int MODE_EDIT = 1;
    public static final int MODE_CREATE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);
        ButterKnife.bind(this);
        mRealm = Realm.getDefaultInstance();

        mMode = getIntent().getIntExtra(EXTRA_MODE, 0);
        uuid = getIntent().getStringExtra(EXTRA_UUID);

        mToolbar.setTitle("编辑备忘录\ue40a\uE40A\uE40A");

        if (mMode == MODE_EDIT) {
            mMenoBean = MenoModel.getInstance().getMenoBean(mRealm, uuid);
            mMenoTitleEditText.setText(mMenoBean.getTitle());
            mMenoContentEdidText.setText(mMenoBean.getContent());
        } else {
            mMenoBean = new MenoBean();
        }


        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title = mMenoTitleEditText.getText().toString();
                if (title.trim().isEmpty()) {
                    Toast.makeText(MemoActivity.this, "\ue059标题不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String content = mMenoContentEdidText.getText().toString();
                if (content.trim().isEmpty()) {
                    Toast.makeText(MemoActivity.this, "\uE059内容不能为空", Toast.LENGTH_SHORT).show();
                }

                if (mMode == MODE_EDIT) {
                    mRealm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            MenoBean menoBean = MenoModel.getInstance().getMenoBean(realm, uuid);
                            menoBean.setTitle(title);
                            menoBean.setContent(content);
                            menoBean.setChangeTime(new Date().getTime());
                        }
                    });
                } else {
                    mMenoBean.setTitle(title);
                    mMenoBean.setContent(content);
                    mMenoBean.setCreateTime(new Date().getTime());
                    mMenoBean.setChangeTime(new Date().getTime());
                    MenoModel.getInstance().updateOrCopy(mRealm, mMenoBean);
                    uuid = mMenoBean.getUUID();
                    mMode = MODE_EDIT;
                }
                Toast.makeText(MemoActivity.this, "\ue405保存成功", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, MemoActivity.class);
        intent.putExtra(EXTRA_MODE, MODE_CREATE);
        return intent;
    }

    public static Intent getIntent(Context context, String UUID) {
        Intent intent = new Intent(context, MemoActivity.class);
        intent.putExtra(EXTRA_UUID, UUID);
        intent.putExtra(EXTRA_MODE, MODE_EDIT);
        return intent;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
