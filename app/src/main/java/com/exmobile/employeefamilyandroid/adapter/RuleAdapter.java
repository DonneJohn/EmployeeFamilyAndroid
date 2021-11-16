package com.exmobile.employeefamilyandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.bean.Rule;
import com.exmobile.mvpbase.adapter.BaseListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/10.
 */
public class RuleAdapter extends BaseListAdapter<Rule>{
    public RuleAdapter(Context context, int mode) {
        super(context, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new NoticeListViewHolder(mInflater.inflate(R.layout.list_item_culture, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        NoticeListViewHolder holder = (NoticeListViewHolder) h;
        Rule item = items.get(position);
        holder.tvTitle.setText(item.getCompanyRuleTitle());
        holder.tvContent.setText(item.getRuleShortText());
    }

    class NoticeListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_culture_item_title)
        TextView tvTitle;
        @BindView(R.id.tv_culture_item_content)
        TextView tvContent;

        public NoticeListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
