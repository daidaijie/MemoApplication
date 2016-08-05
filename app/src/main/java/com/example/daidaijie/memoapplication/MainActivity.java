package com.example.daidaijie.memoapplication;

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

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycleView)
    RecyclerView mRecycleView;
    MenoAdapter mMenoAdapter;

    private MenoModel mMenoModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mToolbar.setTitle("备忘录");
        setSupportActionBar(mToolbar);

        mMenoModel = MenoModel.getInstance();
        mMenoAdapter = new MenoAdapter(this, mMenoModel.mMenoBeen);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        mMenoAdapter.setmOnItemClickLitener(new MenoAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, MemoActivity.class);
                intent.putExtra("memo", mMenoModel.mMenoBeen.get(position));
                intent.putExtra("mode", 1);
                intent.putExtra("pos", position);
                startActivityForResult(intent, 200);
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                String[] infos = {"删除"};
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setItems(infos, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mMenoModel.removeByPos(position);
                                mMenoAdapter.notifyDataSetChanged();
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
            Intent intent = new Intent(MainActivity.this, MemoActivity.class);
            intent.putExtra("mode", 0);
            startActivityForResult(intent, 200);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200 && resultCode == 201) {
            mMenoAdapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
