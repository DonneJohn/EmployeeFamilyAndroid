package com.exmobile.employeefamilyandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.bean.SalaryMonth;
import com.exmobile.mvpbase.adapter.BaseListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by 张聪聪 on 2016/5/25.
 * Email：2353410167@qq.com
 */
public class MonthListAdapter extends BaseListAdapter<SalaryMonth> {

    public MonthListAdapter(Context context, int mode) {
        super(context, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new MonthViewHolder(mInflater.inflate(R.layout.list_item_month, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        SalaryMonth itemt = items.get(position);
        MonthViewHolder holder = (MonthViewHolder) h;
        holder.tvMonthItem.setText(itemt.getTitle());

    }

    class MonthViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_month_item)
        TextView tvMonthItem;

        public MonthViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}