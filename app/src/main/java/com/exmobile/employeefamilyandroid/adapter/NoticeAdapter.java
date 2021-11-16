package com.exmobile.employeefamilyandroid.adapter;

import android.content.Context;
import android.support.annotation.BinderThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.bean.Notice;
import com.exmobile.mvpbase.adapter.BaseListAdapter;
import com.exmobile.mvpbase.utils.Utilities;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zcc on 2016/5/10 18:36.
 */
public class NoticeAdapter extends BaseListAdapter<Notice> {
    public NoticeAdapter(Context context, int mode) {
        super(context, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new NoticeViewHolder(mInflater.inflate(R.layout.list_item_notice, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {

        Notice item = items.get(position);

        NoticeViewHolder holder = (NoticeViewHolder) h;

        if (item.getNoticeTop().equals("1")) {
            holder.ivItemTop.setVisibility(View.VISIBLE);
            position = 0;

        }

        holder.tvItemTitle.setText(item.getCompanyNoticeTitle());
        holder.tvItemContent.setText(item.getNoticeShortText());
        holder.tvItemDate.setText(Utilities.dateFormat2(item.getCreateTime()));


    }


    class NoticeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_item_title)
        TextView tvItemTitle;
        @BindView(R.id.tv_item_content)
        TextView tvItemContent;
        @BindView(R.id.iv_item_top)
        ImageView ivItemTop;
        @BindView(R.id.tv_item_date)
        TextView tvItemDate;

        public NoticeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
