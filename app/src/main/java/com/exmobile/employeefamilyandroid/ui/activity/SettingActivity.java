package com.exmobile.employeefamilyandroid.ui.activity;

import android.os.Bundle;

import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.UIManager;
import com.exmobile.mvpbase.ui.activity.BaseHoldBackActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by：张聪聪 on 2016/5/12 11:41.
 * Email：2353410167@qq.com
 */
public class SettingActivity extends BaseHoldBackActivity {
    @Override
    protected String onSetTitle() {
        return "设置";
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_change_theme)
    void changeTheme(){
        UIManager.gotoChangeTheme(this);
    }
}
