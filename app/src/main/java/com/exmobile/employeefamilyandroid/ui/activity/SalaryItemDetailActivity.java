package com.exmobile.employeefamilyandroid.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.UIManager;
import com.exmobile.employeefamilyandroid.bean.SalaryItem;
import com.exmobile.employeefamilyandroid.ui.fragment.SalaryDetailListFragment;
import com.exmobile.mvpbase.ui.activity.BaseHoldBackActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 张聪聪 on 2016/5/26.
 * Email：2353410167@qq.com
 */
public class SalaryItemDetailActivity extends BaseHoldBackActivity {
    public static final String BUNDLE_KEY_SALARY_ITEM = "BUNDLE_KEY_SALARY_ITEM";

    @Override
    protected String onSetTitle() {
        return "工资详情";
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_salaryitem_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        SalaryItem salaryItem = (SalaryItem) getIntent().getSerializableExtra(BUNDLE_KEY_SALARY_ITEM);
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_KEY_SALARY_ITEM,salaryItem);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, Fragment.instantiate(this, SalaryDetailListFragment.class.getName(),args)).commit();
    }

    @OnClick(R.id.tv_history_salary)
    void gotoHistorySalary(){
        UIManager.gotoSalaryHistoryList(this);
    }
}
