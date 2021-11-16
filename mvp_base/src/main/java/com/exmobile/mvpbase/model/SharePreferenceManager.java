package com.exmobile.mvpbase.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.exmobile.mvpbase.R;

/**
 * SharePreference管理类, oop管理众多的首选项文件
 * <p>
 * Created by thanatos on 16/2/2.
 */
public class SharePreferenceManager {

    public static SharedPreferences getApplicationSetting(Context context) {
        return context.getSharedPreferences(ApplicationSetting.FILE_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferences getLocalUser(Context context) {
        return context.getSharedPreferences(LocalUser.FILE_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferences getRememberUser(Context context) {
        return context.getSharedPreferences(LocalUser.REMEMBER_FILE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 应用配置首选项, 保存主题...一些系统级别的配置
     */
    public static class ApplicationSetting {
        public static final String FILE_NAME = "APPLICATION_SETTING";
        public static final String KEY_THEME = "KEY_THEME";


        // ---主题列举---
        public enum ApplicationTheme {
            DEFAULT (1, R.style.DefaultTheme),
            BLUEGREEN(2, R.style.BlueGreenTheme),
            GREEN(3, R.style.GreenTheme),
            BLUE(4, R.style.BlueTheme),
            YELLOW(5, R.style.YellowTheme),
            GRAYBLACK(6, R.style.GrayBlackTheme),
            PURPLE(7, R.style.PurpleTheme),
            RED(8, R.style.RedTheme);

            private int key;
            private int resId;

            ApplicationTheme(int key, int resId) {
                this.key = key;
                this.resId = resId;
            }

            public int getKey() {
                return key;
            }

            public int getResId() {
                return resId;
            }
        }
    }

    /**
     * 专门保存用户信息
     */
    public static class LocalUser {
        public static final String FILE_NAME = "LOCAL_USER";
        public static final String REMEMBER_FILE_NAME = "REMEMBER_FILE_NAME";

        public static final String KEY_LOGIN_STATE = "KEY_LOGIN_STATE";
        public static final String KEY_LAST_LOGIN_TIME = "KEY_LAST_LOGIN_TIME";

        public static final String KEY_ROWID = "KEY_ROWID";
        public static final String KEY_STAFFNAME = "KEY_STAFFNAME";
        public static final String KEY_STAFFID = "KEY_STAFFID";
        public static final String KEY_STAFFSEX = "KEY_STAFFSEX";
        public static final String KEY_FK_COMPANY = "KEY_FK_COMPANY";
        public static final String KEY_FK_DEPARTMENT = "KEY_FK_DEPARTMENT";

        public static final String KEY_FK_ROLE = "KEY_FK_ROLE";
        public static final String KEY_FK_TITLE = "KEY_FK_TITLE";
        public static final String KEY_STAFFICON = "KEY_STAFFICON";

        public static final String KEY_STAFFMOBILE = "KEY_STAFFMOBILE";
        public static final String KEY_STAFFEMAIL = "KEY_STAFFEMAIL";
        public static final String KEY_STAFFPHONE = "KEY_STAFFPHONE";
        public static final String KEY_STAFFPHONEEXT = "KEY_STAFFPHONEEXT";

        public static final String KEY_STAFFWECHATID = "KEY_STAFFWECHATID";
        public static final String KEY_STAFFPASSWORD = "KEY_STAFFPASSWORD";
        public static final String KEY_CURRENTIP = "KEY_CURRENTIP";
        public static final String KEY_FIRSTTIMELOGIN = "KEY_FIRSTTIMELOGIN";
        public static final String KEY_API_IP = "KEY_API_IP";
        public static final String KEY_TITLENAME = "KEY_TITLENAME";



        public static final String KEY_STAFFQQ = "KEY_STAFFQQ";
        public static final String KEY_STAFBIRTHDAY = "KEY_STAFBIRTHDAY";

        public static final String KEY_PASSWORDMOFIED = "KEY_PASSWORDMOFIED";

        public static final String KEY_COMPANY_ROWID = "KEY_COMPANY_ROWID";
        public static final String KEY_COMPANY_LOGONAME = "KEY_COMPANY_LOGONAME";
        public static final String KEY_COMPANY_COMPANYNAME = "KEY_COMPANY_COMPANYNAME";
        public static final String KEY_COMPANY_COMPANYCOLOR = "KEY_COMPANY_COMPANYCOLOR";
        public static final String KEY_COMPANY_COMPANYNEEDIDCODE = "KEY_COMPANY_COMPANYNEEDIDCODE";



    }

}
