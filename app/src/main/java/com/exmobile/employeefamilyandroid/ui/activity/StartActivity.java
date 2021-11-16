package com.exmobile.employeefamilyandroid.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.IntentCompat;
import android.util.Log;

import com.exmobile.employeefamilyandroid.AppManager;
import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.UIManager;
import com.exmobile.employeefamilyandroid.bean.CompanyInfoBean;
import com.exmobile.employeefamilyandroid.bean.User;
import com.exmobile.employeefamilyandroid.event.Events;
import com.exmobile.employeefamilyandroid.event.RxBus;
import com.exmobile.employeefamilyandroid.presenter.StartPresenter;
import com.exmobile.mvpbase.model.SharePreferenceManager;
import com.exmobile.mvpbase.model.SharePreferenceManager.LocalUser;
import com.exmobile.mvpbase.ui.activity.BaseActivity;
import com.exmobile.mvpbase.utils.Utilities;
import com.trello.rxlifecycle.ActivityEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import nucleus.factory.RequiresPresenter;

@RequiresPresenter(StartPresenter.class)
public class StartActivity extends BaseActivity<StartPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        RxBus.with(this).setEvent(Events.EventEnum.DELIVER_LOGIN)
                .setEndEvent(ActivityEvent.DESTROY)
                .onNext(events -> {
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                    finish();


                })
                .create();

        loadLocalUser();
        if (AppManager.isLogined()) {
            getPresenter().login(AppManager.LOCAL_LOGINED_USER.getStaffID(),AppManager.LOCAL_LOGINED_USER.getStaffMobile(),AppManager.LOCAL_LOGINED_USER.getStaffPassword());

        } else {
            UIManager.jumptoLogin(this);
            finish();
        }

    }

    /**
     * 从SharePreference登录用户
     */
    private void loadLocalUser() {
        SharedPreferences preferences = SharePreferenceManager.getLocalUser(this);
        SharedPreferences rememberUser = SharePreferenceManager.getRememberUser(this);
        // 首先是否有登录记录
        boolean isLogin = preferences.getBoolean(LocalUser.KEY_LOGIN_STATE, false);
        if (!isLogin) return;
        // 其次,登录记录是否过期
        long lastLoginTime = preferences.getLong(LocalUser.KEY_LAST_LOGIN_TIME, -1);
        if (lastLoginTime == -1) return;
        if (new Date().getTime() - lastLoginTime > 30L * 24 * 60 * 60 * 1000) return;
        // 验证信息是否完善

        User user = new User();
        user.setRowID(preferences.getString(LocalUser.KEY_ROWID, null));
        if (user.getRowID() == null) return;
        user.setStaffID(rememberUser.getString(LocalUser.KEY_STAFFID, null));
        if (Utilities.isEmpty(user.getStaffID())) return;
        user.setStaffMobile(rememberUser.getString(LocalUser.KEY_STAFFMOBILE, null));
        if (user.getStaffMobile() == null) return;
        user.setStaffPassword(rememberUser.getString(LocalUser.KEY_STAFFPASSWORD, null));
        if (user.getStaffPassword() == null) return;


        user.setStaffName(preferences.getString(LocalUser.KEY_STAFFNAME, null));


        user.setStaffSex(preferences.getString(LocalUser.KEY_STAFFSEX, null));
        user.setFK_Company(preferences.getString(LocalUser.KEY_FK_COMPANY, null));
        user.setFK_Department(preferences.getString(LocalUser.KEY_FK_DEPARTMENT, null));

        user.setFK_Role(preferences.getString(LocalUser.KEY_FK_ROLE, null));
        user.setFK_Title(preferences.getString(LocalUser.KEY_FK_TITLE, null));
        user.setStaffIcon(preferences.getString(LocalUser.KEY_STAFFICON, null));
        user.setStaffEmail(preferences.getString(LocalUser.KEY_STAFFEMAIL, null));

        user.setStaffPhone(preferences.getString(LocalUser.KEY_STAFFPHONE, null));
        user.setStaffPhoneExt(preferences.getString(LocalUser.KEY_STAFFPHONEEXT, null));
        user.setStaffWeChatID(preferences.getString(LocalUser.KEY_STAFFWECHATID, null));
        user.setCurrentIP(preferences.getString(LocalUser.KEY_CURRENTIP, null));
        user.setFirstTimeLogin(preferences.getString(LocalUser.KEY_FIRSTTIMELOGIN, null));
        user.setAPI_IP(preferences.getString(LocalUser.KEY_API_IP, null));
        user.setTitleName(preferences.getString(LocalUser.KEY_TITLENAME, null));

        user.setStaffQQ(preferences.getString(LocalUser.KEY_STAFFQQ, null));
        user.setStafBirthday(preferences.getString(LocalUser.KEY_STAFBIRTHDAY, null));

        CompanyInfoBean companyInfoBean = new CompanyInfoBean();
        companyInfoBean.setRowID(preferences.getString(LocalUser.KEY_COMPANY_ROWID, null));
        companyInfoBean.setLogoName(preferences.getString(LocalUser.KEY_COMPANY_LOGONAME, null));
        companyInfoBean.setCompanyName(rememberUser.getString(LocalUser.KEY_COMPANY_COMPANYNAME, null));
        companyInfoBean.setCompanyColor(preferences.getInt(LocalUser.KEY_COMPANY_COMPANYCOLOR, 1));
        companyInfoBean.setCompanyNeedIDCode(preferences.getString(LocalUser.KEY_COMPANY_COMPANYNEEDIDCODE, null));

        ArrayList<CompanyInfoBean> companyInfoBeanArrayList = new ArrayList<>();
        companyInfoBeanArrayList.add(companyInfoBean);
        user.setCompanyInfo(companyInfoBeanArrayList);

        HashSet<String> tags = new HashSet<>();
        tags.add(Utilities.transTag(user.getFK_Company()));
        tags.add(Utilities.transTag(user.getFK_Department()));
        tags.add(user.getStaffPhone());
        JPushInterface.setAliasAndTags(this, user.getStaffMobile(), tags, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
//                Log.i("JPush",i+"--------------------------");
//                Log.i("JPush",s+"--------------------------");
            }
        });
        AppManager.LOCAL_LOGINED_USER = user;
    }

    private SharedPreferences preferences, userPreference;
    private static final String CHANGE_THEME = "CHANGE_THEME";
    public void onLoadSuccessful(User user) {
        preferences = SharePreferenceManager.getRememberUser(this);
        userPreference = SharePreferenceManager.getLocalUser(this);
        SharedPreferences.Editor editor = userPreference.edit();
        editor.putString(SharePreferenceManager.LocalUser.KEY_ROWID, user.getRowID());

        SharedPreferences.Editor rememberEditor = preferences.edit();

            rememberEditor.putString(SharePreferenceManager.LocalUser.KEY_STAFFID, user.getStaffID());


//            rememberEditor.putString(SharePreferenceManager.LocalUser.KEY_STAFFMOBILE, user.getStaffMobile());
//            rememberEditor.putString(SharePreferenceManager.LocalUser.KEY_STAFFPASSWORD, user.getPasswordMofied());


            editor.putString(SharePreferenceManager.LocalUser.KEY_STAFFNAME, user.getStaffName());
            editor.putString(SharePreferenceManager.LocalUser.KEY_STAFFSEX, user.getStaffSex());
            editor.putString(SharePreferenceManager.LocalUser.KEY_FK_COMPANY, user.getFK_Company());
            editor.putString(SharePreferenceManager.LocalUser.KEY_FK_DEPARTMENT, user.getFK_Department());
            editor.putString(SharePreferenceManager.LocalUser.KEY_FK_ROLE, user.getFK_Role());
            editor.putString(SharePreferenceManager.LocalUser.KEY_FK_TITLE, user.getFK_Title());
            editor.putString(SharePreferenceManager.LocalUser.KEY_STAFFICON, user.getStaffIcon());
            editor.putString(SharePreferenceManager.LocalUser.KEY_STAFFEMAIL, user.getStaffEmail());
            editor.putString(SharePreferenceManager.LocalUser.KEY_STAFFPHONE, user.getStaffPhone());
            editor.putString(SharePreferenceManager.LocalUser.KEY_STAFFPHONEEXT, user.getStaffPhoneExt());
            editor.putString(SharePreferenceManager.LocalUser.KEY_STAFFWECHATID, user.getStaffWeChatID());
            editor.putString(SharePreferenceManager.LocalUser.KEY_CURRENTIP, user.getCurrentIP());
            editor.putString(SharePreferenceManager.LocalUser.KEY_FIRSTTIMELOGIN, user.getFirstTimeLogin());
            editor.putString(SharePreferenceManager.LocalUser.KEY_API_IP, user.getAPI_IP());
            editor.putString(SharePreferenceManager.LocalUser.KEY_TITLENAME, user.getTitleName());


            editor.putLong(SharePreferenceManager.LocalUser.KEY_LAST_LOGIN_TIME, new Date().getTime());
            editor.putBoolean(SharePreferenceManager.LocalUser.KEY_LOGIN_STATE, true);

            editor.putString(SharePreferenceManager.LocalUser.KEY_STAFFQQ, user.getStaffQQ());
            editor.putString(SharePreferenceManager.LocalUser.KEY_STAFBIRTHDAY, user.getStafBirthday());

            editor.putString(SharePreferenceManager.LocalUser.KEY_PASSWORDMOFIED, user.getPasswordMofied());

            CompanyInfoBean companyInfoBean = user.getCompanyInfo().get(0);
            editor.putString(SharePreferenceManager.LocalUser.KEY_COMPANY_ROWID, companyInfoBean.getRowID());
            editor.putString(SharePreferenceManager.LocalUser.KEY_COMPANY_LOGONAME, companyInfoBean.getLogoName());

            rememberEditor.putString(SharePreferenceManager.LocalUser.KEY_COMPANY_COMPANYNAME, companyInfoBean.getCompanyName());

            editor.putInt(SharePreferenceManager.LocalUser.KEY_COMPANY_COMPANYCOLOR, companyInfoBean.getCompanyColor());
            editor.putString(SharePreferenceManager.LocalUser.KEY_COMPANY_COMPANYNEEDIDCODE, companyInfoBean.getCompanyNeedIDCode());


        editor.apply();
        rememberEditor.apply();


        HashSet<String> tags = new HashSet<>();
        tags.add(Utilities.transTag(user.getFK_Company()));
        tags.add(Utilities.transTag(user.getFK_Department()));
        tags.add(user.getStaffPhone());
        JPushInterface.setAliasAndTags(this, user.getStaffMobile(), tags, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.i("JPush", i + "--------------------------");
                Log.i("JPush", s + "--------------------------");
            }
        });

        AppManager.LOCAL_LOGINED_USER = user;

        //设置主题

        int themeId = user.getCompanyInfo().get(0).getCompanyColor();
        SharedPreferences preferences = SharePreferenceManager.getApplicationSetting(this);

        SharedPreferences.Editor themeEditor = preferences.edit();
        switch (themeId) {

            case 1:
                themeEditor.putInt(SharePreferenceManager.ApplicationSetting.KEY_THEME, SharePreferenceManager.ApplicationSetting.ApplicationTheme.DEFAULT.getKey());
                break;
            case 2:
                themeEditor.putInt(SharePreferenceManager.ApplicationSetting.KEY_THEME, SharePreferenceManager.ApplicationSetting.ApplicationTheme.BLUEGREEN.getKey());
                break;
            case 3:
                themeEditor.putInt(SharePreferenceManager.ApplicationSetting.KEY_THEME, SharePreferenceManager.ApplicationSetting.ApplicationTheme.GREEN.getKey());
                break;
            case 4:
                themeEditor.putInt(SharePreferenceManager.ApplicationSetting.KEY_THEME, SharePreferenceManager.ApplicationSetting.ApplicationTheme.BLUE.getKey());
                break;

            case 5:
                themeEditor.putInt(SharePreferenceManager.ApplicationSetting.KEY_THEME, SharePreferenceManager.ApplicationSetting.ApplicationTheme.YELLOW.getKey());
                break;

            case 6:
                themeEditor.putInt(SharePreferenceManager.ApplicationSetting.KEY_THEME, SharePreferenceManager.ApplicationSetting.ApplicationTheme.GRAYBLACK.getKey());
                break;

            case 7:
                themeEditor.putInt(SharePreferenceManager.ApplicationSetting.KEY_THEME, SharePreferenceManager.ApplicationSetting.ApplicationTheme.PURPLE.getKey());
                break;
            case 8:
                themeEditor.putInt(SharePreferenceManager.ApplicationSetting.KEY_THEME, SharePreferenceManager.ApplicationSetting.ApplicationTheme.RED.getKey());
                break;
            default:
                themeEditor.putInt(SharePreferenceManager.ApplicationSetting.KEY_THEME, SharePreferenceManager.ApplicationSetting.ApplicationTheme.DEFAULT.getKey());
        }

        themeEditor.apply();




        new Handler().postDelayed(() -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(CHANGE_THEME, true);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.enter, R.anim.exit);
        }, 2000);
    }

    public void onLoadFailed(String message) {
        UIManager.jumptoLogin(this);
        finish();
    }
}
