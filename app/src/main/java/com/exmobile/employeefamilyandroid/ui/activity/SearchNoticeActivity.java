package com.exmobile.employeefamilyandroid.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.ui.fragment.NoticeSearchFragment;
import com.exmobile.mvpbase.ui.activity.BaseHoldBackActivity;

/**
 * Created by：张聪聪 on 2016/5/11 14:45.
 * Email：2353410167@qq.com
 */
public class SearchNoticeActivity extends BaseHoldBackActivity {
    public static final String BUNDLE_KEYWORD = "BUNDLE_KEYWORD";

    @Override
    protected String onSetTitle() {
        return "搜索结果";
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_fragment_base;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String keyword = getIntent().getStringExtra(BUNDLE_KEYWORD);
        Bundle args = new Bundle();
        args.putString(BUNDLE_KEYWORD,keyword);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, Fragment.instantiate(this,NoticeSearchFragment.class.getName(),args)).commit();
    }
}
