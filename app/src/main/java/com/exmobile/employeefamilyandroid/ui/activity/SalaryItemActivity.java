package com.exmobile.employeefamilyandroid.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.ui.fragment.SalaryItemListFragment;
import com.exmobile.mvpbase.ui.activity.BaseHoldBackActivity;

/**
 * Created by 张聪聪 on 2016/5/26.
 * Email：2353410167@qq.com
 */
public class SalaryItemActivity extends BaseHoldBackActivity {

    public static final String BUNDLE_KEY_YEAR = "BUNDLE_KEY_YEAR";
    public static final String BUNDLE_KEY_MONTH = "BUNDLE_KEY_MONTH";

    private String year;
    private String month;

    @Override
    protected String onSetTitle() {
//        Calendar date = Calendar.getInstance();
//        int year = date.get(date.YEAR);
//        int month = date.get(date.MONTH) + 1;
        return year + "年" + month + "月" + "工资项目列表";
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_month_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initData();
        super.onCreate(savedInstanceState);
        Bundle args = new Bundle();
        args.putString(BUNDLE_KEY_YEAR,year);
        args.putString(BUNDLE_KEY_MONTH,month);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, Fragment.instantiate(this, SalaryItemListFragment.class.getName(),args)).commit();
    }

    private void initData() {
        year = getIntent().getStringExtra(BUNDLE_KEY_YEAR);
        month = getIntent().getStringExtra(BUNDLE_KEY_MONTH);
    }
}
