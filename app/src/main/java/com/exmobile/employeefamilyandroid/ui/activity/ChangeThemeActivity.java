package com.exmobile.employeefamilyandroid.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;

import com.exmobile.employeefamilyandroid.R;
import com.exmobile.mvpbase.model.SharePreferenceManager;
import com.exmobile.mvpbase.model.SharePreferenceManager.ApplicationSetting;
import com.exmobile.mvpbase.model.SharePreferenceManager.ApplicationSetting.ApplicationTheme;
import com.exmobile.mvpbase.ui.activity.BaseHoldBackActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by：张聪聪 on 2016/5/12 13:54.
 * Email：2353410167@qq.com
 */
public class ChangeThemeActivity extends BaseHoldBackActivity {
    private static final String CHANGE_THEME = "CHANGE_THEME";
    @Override
    protected String onSetTitle() {
        return "设置主题";
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_change_theme;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ll_yellow)
    void changeYellow(){
        SharedPreferences preferences = SharePreferenceManager.getApplicationSetting(this);

        SharedPreferences.Editor editor = preferences.edit();

            editor.putInt(ApplicationSetting.KEY_THEME, ApplicationTheme.YELLOW.getKey());

        editor.apply();
        finish();
//        Intent intent = getIntent();
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra(CHANGE_THEME, true);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    @OnClick(R.id.ll_default)
    void changeDefault(){
        SharedPreferences preferences = SharePreferenceManager.getApplicationSetting(this);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(ApplicationSetting.KEY_THEME, ApplicationTheme.DEFAULT.getKey());

        editor.apply();
        finish();
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra(CHANGE_THEME, true);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    @OnClick(R.id.ll_blue_green)
    void changeBlueGreen(){
        SharedPreferences preferences = SharePreferenceManager.getApplicationSetting(this);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(ApplicationSetting.KEY_THEME, ApplicationTheme.BLUEGREEN.getKey());

        editor.apply();
        finish();
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra(CHANGE_THEME, true);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    @OnClick(R.id.ll_blue)
    void changeBlue(){
        SharedPreferences preferences = SharePreferenceManager.getApplicationSetting(this);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(ApplicationSetting.KEY_THEME, ApplicationTheme.BLUE.getKey());

        editor.apply();
        finish();
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra(CHANGE_THEME, true);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    @OnClick(R.id.ll_green)
    void changeGreen(){
        SharedPreferences preferences = SharePreferenceManager.getApplicationSetting(this);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(ApplicationSetting.KEY_THEME, ApplicationTheme.GREEN.getKey());

        editor.apply();
        finish();
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra(CHANGE_THEME, true);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    @OnClick(R.id.ll_grayblack)
    void changeGrayBlack(){
        SharedPreferences preferences = SharePreferenceManager.getApplicationSetting(this);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(ApplicationSetting.KEY_THEME, ApplicationTheme.GRAYBLACK.getKey());

        editor.apply();
        finish();
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra(CHANGE_THEME, true);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    @OnClick(R.id.ll_purple)
    void changePurple(){
        SharedPreferences preferences = SharePreferenceManager.getApplicationSetting(this);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(ApplicationSetting.KEY_THEME, ApplicationTheme.PURPLE.getKey());

        editor.apply();
        finish();
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra(CHANGE_THEME, true);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }
}
