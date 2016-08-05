package com.example.daidaijie.memoapplication.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.daidaijie.memoapplication.R;
import com.example.daidaijie.memoapplication.bean.MenoBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by daidaijie on 2016/8/4.
 */
public class MenoAdapter extends RecyclerView.Adapter<MenoAdapter.ViewHolder> {

    private Activity mActivity;
    private List<MenoBean> mMenoBeans;

    public MenoAdapter(Activity activity, List<MenoBean> menoBeen) {
        mActivity = activity;
        mMenoBeans = menoBeen;
    }

    //设置Item点击回调接口
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setmOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        View view = inflater.inflate(R.layout.item_memo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        MenoBean menoBean = mMenoBeans.get(mMenoBeans.size() - position - 1);
        holder.mTitleTextView.setText(menoBean.getTitle());
        holder.mTimeTextView.setText("创建时间　　: " + menoBean.getCreateTimeString());
        if (menoBean.getChangeTimeString() != null) {
            holder.mChangeTimeTextView.setText("上次修改时间: " + menoBean.getChangeTimeString());
        } else {
            holder.mChangeTimeTextView.setText("上次修改时间: " + menoBean.getCreateTimeString());
        }
        holder.mContentTextView.setText("内容: " + menoBean.getContent());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickLitener.onItemClick(holder.mView, position);
            }
        });
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnItemClickLitener.onItemLongClick(holder.mView, position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMenoBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.titleTextView)
        TextView mTitleTextView;
        @BindView(R.id.timeTextView)
        TextView mTimeTextView;
        @BindView(R.id.divLine)
        View mDivLine;
        @BindView(R.id.myView)
        LinearLayout mView;
        @BindView(R.id.changeTimeTextView)
        TextView mChangeTimeTextView;
        @BindView(R.id.contentTextView)
        TextView mContentTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}