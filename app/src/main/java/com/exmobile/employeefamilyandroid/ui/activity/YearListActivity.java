package com.exmobile.employeefamilyandroid.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.ui.fragment.YearListFragment;
import com.exmobile.mvpbase.ui.activity.BaseHoldBackActivity;

/**
 * Created by 张聪聪 on 2016/5/26.
 * Email：2353410167@qq.com
 */
public class YearListActivity extends BaseHoldBackActivity {
    @Override
    protected String onSetTitle() {
        return "历史查询";
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_year_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, Fragment.instantiate(this, YearListFragment.class.getName())).commit();
    }
}
