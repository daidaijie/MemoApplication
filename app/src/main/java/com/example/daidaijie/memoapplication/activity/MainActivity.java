package com.example.daidaijie.memoapplication.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.daidaijie.memoapplication.R;
import com.example.daidaijie.memoapplication.adapter.MenoAdapter;
import com.example.daidaijie.memoapplication.bean.MenoBean;
import com.example.daidaijie.memoapplication.model.MenoModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycleView)
    RecyclerView mRecycleView;
    MenoAdapter mMenoAdapter;

    private MenoModel mMenoModel;
    private Realm mRealm;
    private RealmResults<MenoBean> mMenoBeen;
    private RealmChangeListener mChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mRealm = Realm.getDefaultInstance();

        mToolbar.setTitle("备忘录\ue412\uE412\uE412");
        setSupportActionBar(mToolbar);

        mMenoModel = MenoModel.getInstance();
        mMenoBeen = mMenoModel.getmMenoBeen(mRealm);

        mMenoAdapter = new MenoAdapter(this, mMenoBeen);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));

        mMenoAdapter.setmOnItemClickLitener(new MenoAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = MemoActivity.getIntent(MainActivity.this, mMenoBeen.get(position).getUUID());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                String[] infos = {"删除"};
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setItems(infos, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mMenoModel.removeByPos(mRealm, mMenoBeen.get(position).getUUID());
                            }
                        }).create();
                dialog.show();

            }
        });
        mRecycleView.setAdapter(mMenoAdapter);

        mChangeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object element) {
                mMenoAdapter.notifyDataSetChanged();
            }
        };

        mMenoBeen.addChangeListener(mChangeListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addMemo) {
            Intent intent = MemoActivity.getIntent(this);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMenoBeen.removeChangeListener(mChangeListener);
        mRealm.close();
    }
}
