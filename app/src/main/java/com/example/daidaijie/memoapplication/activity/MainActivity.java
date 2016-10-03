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

    private int editPosition;

    private static final int REQUEST_CREATE = 200;
    private static final int REQUEST_EDIT = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mRealm = Realm.getDefaultInstance();

        mToolbar.setTitle("备忘录\ue412\uE412\uE412");
        setSupportActionBar(mToolbar);

        mMenoModel = new MenoModel(mRealm);
        mMenoBeen = mMenoModel.getmMenoBeen();

        mMenoAdapter = new MenoAdapter(this, mMenoBeen);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        mMenoAdapter.setmOnItemClickLitener(new MenoAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = MemoActivity.getIntent(MainActivity.this, mMenoBeen.get(position).getUUID());
                startActivityForResult(intent, REQUEST_EDIT);
                editPosition = position;
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                String[] infos = {"删除"};
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setItems(infos, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mMenoModel.removeByPos(mMenoBeen.get(position).getUUID());
                            }
                        }).create();
                dialog.show();

            }
        });
        mRecycleView.setAdapter(mMenoAdapter);
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
            startActivityForResult(intent, REQUEST_CREATE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CREATE) {
                mRecycleView.scrollToPosition(0);
                mMenoAdapter.notifyDataSetChanged();
            }
        } else {
            if (requestCode == REQUEST_EDIT) {
                mMenoAdapter.notifyItemChanged(editPosition);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
