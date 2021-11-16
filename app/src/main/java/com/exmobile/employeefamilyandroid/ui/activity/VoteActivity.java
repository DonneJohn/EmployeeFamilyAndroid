package com.exmobile.employeefamilyandroid.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.ui.fragment.VoteListFragment;
import com.exmobile.mvpbase.ui.activity.BaseHoldBackActivity;

public class VoteActivity extends BaseHoldBackActivity {
    @Override
    protected String onSetTitle() {
        return "在线调查";
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_vote;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, Fragment.instantiate(this, VoteListFragment.class.getName()))
                .commit();
    }
}
