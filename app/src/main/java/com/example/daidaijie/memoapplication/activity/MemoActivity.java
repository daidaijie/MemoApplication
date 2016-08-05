package com.example.daidaijie.memoapplication.activity;

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

public class MemoActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.menoTitleEditText)
    EditText mMenoTitleEditText;
    @BindView(R.id.saveButton)
    Button mSaveButton;

    MenoBean mMenoBean;
    @BindView(R.id.menoContentEdidText)
    EditText mMenoContentEdidText;

    private int mode;

    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);
        ButterKnife.bind(this);


        mToolbar.setTitle("编辑备忘录\ue40a\uE40A\uE40A");

        Intent intent = getIntent();
        mode = intent.getIntExtra("mode", 0);
        if (mode == 1) {
            mMenoBean = (MenoBean) intent.getSerializableExtra("memo");
            pos = intent.getIntExtra("pos", MenoModel.getInstance().mMenoBeen.size());

            mMenoTitleEditText.setText(mMenoBean.getTitle());
            mMenoContentEdidText.setText(mMenoBean.getContent());

        } else {
            mMenoBean = new MenoBean();
        }


        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mMenoTitleEditText.getText().toString();
                if (title.trim().isEmpty()) {
                    Toast.makeText(MemoActivity.this, "\ue059标题不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String content = mMenoContentEdidText.getText().toString();
                if (content.trim().isEmpty()) {
                    Toast.makeText(MemoActivity.this, "\uE059内容不能为空", Toast.LENGTH_SHORT).show();
                }
                mMenoBean.setTitle(title);
                mMenoBean.setContent(content);
                if (mode == 1) {
                    mMenoBean.setChangeTime(new Date().getTime());
                    MenoModel.getInstance().changeByPos(mMenoBean, pos);
                } else {
                    mMenoBean.setCreateTime(new Date().getTime());
                    mMenoBean.setChangeTime(new Date().getTime());
                    MenoModel.getInstance().addByPos(mMenoBean);
                    mode = 1;
                    pos = MenoModel.getInstance().mMenoBeen.size() - 1;
                }
                Toast.makeText(MemoActivity.this, "\ue405保存成功", Toast.LENGTH_SHORT).show();
            }
        });

        setResult(201);
    }
}
