package com.exmobile.employeefamilyandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.bean.SalaryItem;
import com.exmobile.employeefamilyandroid.bean.SalaryMonth;
import com.exmobile.mvpbase.adapter.BaseListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 张聪聪 on 2016/5/26.
 * Email：2353410167@qq.com
 */
public class SalaryItemAdapter extends BaseListAdapter<SalaryItem> {
    public SalaryItemAdapter(Context context, int mode) {
        super(context, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new SalaryItemViewHolder(mInflater.inflate(R.layout.list_item_month, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        SalaryItem itemt = items.get(position);
        SalaryItemViewHolder holder = (SalaryItemViewHolder) h;
        holder.tvSalaryItem.setText(itemt.getTemplateName());

    }

    class SalaryItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_month_item)
        TextView tvSalaryItem;

        public SalaryItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
