package com.exmobile.employeefamilyandroid.ui.activity;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.exmobile.employeefamilyandroid.AppManager;
import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.UIManager;
import com.exmobile.employeefamilyandroid.bean.RespVersion;
import com.exmobile.employeefamilyandroid.interf.OnTabReselectListener;
import com.exmobile.employeefamilyandroid.presenter.MainPresenter;
import com.exmobile.employeefamilyandroid.utils.DialogHelp;
import com.exmobile.employeefamilyandroid.utils.DownAPKService;
import com.exmobile.employeefamilyandroid.widget.MyFragmentTabHost;
import com.exmobile.mvpbase.model.SharePreferenceManager;
import com.exmobile.mvpbase.model.SharePreferenceManager.ApplicationSetting;
import com.exmobile.mvpbase.model.SharePreferenceManager.ApplicationSetting.ApplicationTheme;
import com.exmobile.mvpbase.ui.activity.BaseActivity;
import com.exmobile.mvpbase.utils.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import nucleus.factory.RequiresPresenter;

@RequiresPresenter(MainPresenter.class)
public class MainActivity extends BaseActivity<MainPresenter> implements TabHost.OnTabChangeListener, View.OnTouchListener {

    @BindView(android.R.id.tabhost)
    public MyFragmentTabHost mTabHost;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private MainTab[] tabs;

    //jpush
    public static boolean isForeground = false;

    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.exmobile.employeefamilyandroid.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();


        registerMessageReceiver();

        checkUpVersion();
    }

    private void checkUpVersion() {
        getPresenter().getVersion();
    }


    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public void onGetVersionSuccessful(RespVersion data) {
        getPresenter().stop(MainPresenter.REQUEST_VERSION);
        int localVersionCode = AppManager.getPackageInfo().versionCode;
        if (data.getResult().get(0).getVersioncode() > localVersionCode) {
            DialogHelp.getConfirmDialog(this, data.getResult().get(0).getDescription(), (dialog, which) -> {

                DownAPKService
                        .startService(
                                this,
                                data.getResult().get(0).getVersionname(),
                                data.getResult().get(0).getUrl());
            }, null).show();
        }

    }

    public void onGetVersionFailed(String message) {
        if (message != null) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                Toast.makeText(MainActivity.this, messge + "..." + extras, Toast.LENGTH_SHORT).show();

                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!Utilities.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");

                    String api = AppManager.LOCAL_LOGINED_USER.getAPI_IP();
                    UIManager.gotoDetail(MainActivity.this, api.substring(0, api.length() - 4) + "Appweb/NoticeDetail.aspx?RowID=" + extras, messge);
                }
//                setCostomMsg(showMsg.toString());
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                Toast.makeText(MainActivity.this, messge + "..." + extras, Toast.LENGTH_SHORT).show();

                if (!Utilities.isEmpty(extras)) {


                    String api = AppManager.LOCAL_LOGINED_USER.getAPI_IP();
                    UIManager.gotoDetail(MainActivity.this, api.substring(0, api.length() - 4) + "Appweb/NoticeDetail.aspx?RowID=" + extras, messge);
                }

            }
        }
    }

    private void initView() {

        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        if (Build.VERSION.SDK_INT > 10) {
            mTabHost.getTabWidget().setShowDividers(0);
        }

        initTabs();

        mTabHost.setCurrentTab(0);
        mTabHost.setOnTabChangedListener(this);
    }

    private int[] icons;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initTabs() {

        //set theme

        SharedPreferences preferences = SharePreferenceManager.getApplicationSetting(this);
        int theme = preferences.getInt(ApplicationSetting.KEY_THEME, ApplicationTheme.DEFAULT.getKey());
        if (theme == ApplicationTheme.DEFAULT.getKey()) {
            icons = new int[]{R.drawable.tab_icon_notice, R.drawable.tab_icon_average, R.drawable.tab_icon_application, R.drawable.tab_icon_me};
        } else if (theme == ApplicationTheme.YELLOW.getKey()) {
            icons = new int[]{R.drawable.tab_icon_notice_yellow, R.drawable.tab_icon_average_yellow, R.drawable.tab_icon_application_yellow,
                    R.drawable.tab_icon_me_yellow};
        } else if (theme == ApplicationTheme.BLUEGREEN.getKey()) {
            icons = new int[]{R.drawable.tab_icon_notice_bluegreen, R.drawable.tab_icon_average_bluegreen, R.drawable.tab_icon_application_bluegreen,
                    R.drawable.tab_icon_me_bluegreen};
        } else if (theme == ApplicationTheme.BLUE.getKey()) {

            icons = new int[]{R.drawable.tab_icon_notice_blue, R.drawable.tab_icon_average_blue, R.drawable.tab_icon_application_blue,
                    R.drawable.tab_icon_me_blue};
        } else if (theme == ApplicationTheme.GREEN.getKey()) {

            icons = new int[]{R.drawable.tab_icon_notice_green, R.drawable.tab_icon_average_green, R.drawable.tab_icon_application_green, R.drawable.tab_icon_me_green};
        } else if (theme == ApplicationTheme.GRAYBLACK.getKey()) {
            icons = new int[]{R.drawable.tab_icon_notice_grayblack, R.drawable.tab_icon_average_grayblack, R.drawable.tab_icon_application_grayblack,
                    R.drawable.tab_icon_me_grayblack};
        } else if (theme == ApplicationTheme.PURPLE.getKey()) {
            icons = new int[]{R.drawable.tab_icon_notice_purple, R.drawable.tab_icon_average_purple, R.drawable.tab_icon_application_purple, R.drawable.tab_icon_me_purple};
        } else if (theme == ApplicationTheme.RED.getKey()) {
            icons = new int[]{R.drawable.tab_icon_notice_red, R.drawable.tab_icon_average_red, R.drawable.tab_icon_application_red, R.drawable.tab_icon_me_red};
        }


        tabs = MainTab.values();
        setTitle(tabs[0].getResName());
        final int size = tabs.length;
        for (int i = 0; i < size; i++) {
            MainTab mainTab = tabs[i];
            TabHost.TabSpec tab = mTabHost.newTabSpec(getString(mainTab.getResName()));
            View indicator = LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.tab_indicator, null);
            ImageView title = (ImageView) indicator.findViewById(R.id.tab_title);

            Drawable drawable = this.getResources().getDrawable(icons[i]);
            title.setImageDrawable(drawable);
//            if (i == 2) {
//                indicator.setVisibility(View.INVISIBLE);
//                mTabHost.setNoTabChangedTag(getString(mainTab.getResName()));
//            }
            tab.setIndicator(indicator);
            tab.setContent(tag -> {
                return new View(MainActivity.this);
            });
            mTabHost.addTab(tab, mainTab.getClz(), null);


            mTabHost.getTabWidget().getChildAt(i).setOnTouchListener(this);
        }

    }


    @Override
    public void onTabChanged(String tabId) {
        final int size = mTabHost.getTabWidget().getTabCount();
        for (int i = 0; i < size; i++) {
            View v = mTabHost.getTabWidget().getChildAt(i);
            if (i == mTabHost.getCurrentTab()) {
                v.setSelected(true);
                tvTitle.setText(tabs[i].getResName());
            } else {
                v.setSelected(false);
            }
        }
        supportInvalidateOptionsMenu();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean consumed = false;
        // use getTabHost().getCurrentTabView to decide if the current tab is
        // touched again
        if (event.getAction() == MotionEvent.ACTION_DOWN
                && v.equals(mTabHost.getCurrentTabView())) {
            // use getTabHost().getCurrentView() to get a handle to the view
            // which is displayed in the tab - and to get this views context
            Fragment currentFragment = getCurrentFragment();
            if (currentFragment != null
                    && currentFragment instanceof OnTabReselectListener) {
                OnTabReselectListener listener = (OnTabReselectListener) currentFragment;
                listener.onTabReselect();
                consumed = true;
            }
        }
        return consumed;
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentByTag(
                mTabHost.getCurrentTabTag());
    }


    private boolean isBacking = false;
    private Toast mBackToast;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (isBacking) {
                if (mBackToast != null)
                    mBackToast.cancel();
                finish();
                android.os.Process.killProcess(Process.myPid());
                System.exit(0);
            } else {
                isBacking = true;
                mBackToast = Toast.makeText(this, "再按一次退出" + getResources().getString(R.string.app_name), Toast.LENGTH_LONG);
                mBackToast.show();
                new Handler().postDelayed(() -> {
                    isBacking = false;
                    if (mBackToast != null)
                        mBackToast.cancel();
                }, 2000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }


}
