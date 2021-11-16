package com.exmobile.mvpbase.ui.activity;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.exmobile.mvpbase.manager.DeviceManager;
import com.exmobile.mvpbase.model.SharePreferenceManager;
import com.exmobile.mvpbase.model.SharePreferenceManager.ApplicationSetting;
import com.exmobile.mvpbase.model.SharePreferenceManager.ApplicationSetting.ApplicationTheme;

import cn.jpush.android.api.JPushInterface;
import nucleus.presenter.Presenter;
import nucleus.view.NucleusActivity;

public abstract class BaseActivity<P extends Presenter> extends NucleusActivity<P> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 主题选择
        SharedPreferences preferences = SharePreferenceManager.getApplicationSetting(this);

        int theme = preferences.getInt(ApplicationSetting.KEY_THEME, ApplicationTheme.DEFAULT.getKey());

        if (theme == ApplicationTheme.DEFAULT.getKey()) {
            setTheme(ApplicationTheme.DEFAULT.getResId());
        } else if (theme == ApplicationTheme.YELLOW.getKey()) {
            setTheme(ApplicationTheme.YELLOW.getResId());
        } else if (theme == ApplicationTheme.BLUEGREEN.getKey()) {
            setTheme(ApplicationTheme.BLUEGREEN.getResId());
        } else if (theme == ApplicationTheme.BLUE.getKey()) {
            setTheme(ApplicationTheme.BLUE.getResId());
        } else if (theme == ApplicationTheme.GREEN.getKey()) {
            setTheme(ApplicationTheme.GREEN.getResId());
        } else if (theme == ApplicationTheme.GRAYBLACK.getKey()) {
            setTheme(ApplicationTheme.GRAYBLACK.getResId());
        } else if (theme == ApplicationTheme.PURPLE.getKey()) {
            setTheme(ApplicationTheme.PURPLE.getResId());
        }else if (theme == ApplicationTheme.RED.getKey()) {
            setTheme(ApplicationTheme.RED.getResId());
        }

        // 方向锁定
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
        if (isFinishing()) {
            DeviceManager.hideSoftInput(this, getCurrentFocus());
        }
    }

    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }


}
