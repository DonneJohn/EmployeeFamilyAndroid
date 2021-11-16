package com.exmobile.employeefamilyandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.bean.Vote;
import com.exmobile.mvpbase.adapter.BaseListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/10.
 */
public class VoteAdapter extends BaseListAdapter<Vote> {

    public VoteAdapter(Context context, int mode) {
        super(context, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new NoticeListViewHolder(mInflater.inflate(R.layout.list_item_vote, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        NoticeListViewHolder holder = (NoticeListViewHolder) h;
        Vote item = items.get(position);
        holder.tvTitle.setText(item.getVoteTitle());
        holder.tvContent.setText(item.getVoteShortText());
        holder.tvTime.setText(item.getCreateTime());
    }

    class NoticeListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_vote_item_title)
        TextView tvTitle;
        @BindView(R.id.tv_vote_item_content)
        TextView tvContent;
        @BindView(R.id.tv_vote_item_time)
        TextView tvTime;

        public NoticeListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
