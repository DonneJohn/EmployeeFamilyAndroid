package com.exmobile.employeefamilyandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.bean.SalaryDetail;
import com.exmobile.mvpbase.adapter.BaseListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 张聪聪 on 2016/5/26.
 * Email：2353410167@qq.com
 */
public class SalaryDetailAdapter extends BaseListAdapter<SalaryDetail> {
    public SalaryDetailAdapter(Context context, int mode) {
        super(context, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new SalaryDetailViewHolder(mInflater.inflate(R.layout.list_item_salary_detail, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        SalaryDetail itemt = items.get(position);
        SalaryDetailViewHolder holder = (SalaryDetailViewHolder) h;
        holder.tvItemName.setText(itemt.getItemName());
        holder.tvItemValue.setText(itemt.getItemValue());

    }

    class SalaryDetailViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_name)
        TextView tvItemName;
        @BindView(R.id.tv_item_value)
        TextView tvItemValue;

        public SalaryDetailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

