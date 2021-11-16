package com.exmobile.employeefamilyandroid;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;

import com.exmobile.employeefamilyandroid.bean.User;

import cn.jpush.android.api.JPushInterface;

//import com.exmobile.familyhealth.bean.User;


/**
 * Created by Administrator on 2016/4/5.
 */
public class AppManager extends Application {

    public static Context context;
    public static Resources resources;
    public static SharedPreferences preferences;
    public static User LOCAL_LOGINED_USER;


    public static final String BUNDLE_TYPE = "BUNDLE_TYPE";

    private static AppManager instance = null;

    public static AppManager getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        resources = getResources();
        preferences = getSharedPreferences(context.getPackageName(), MODE_PRIVATE);
//        LeakCanary.install(this);

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    public static PackageInfo getPackageInfo() {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isLogined() {
        return LOCAL_LOGINED_USER != null;
    }

}
