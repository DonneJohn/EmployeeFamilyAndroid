package com.exmobile.employeefamilyandroid.presenter;

import android.os.Bundle;

import com.exmobile.employeefamilyandroid.AppManager;
import com.exmobile.employeefamilyandroid.ServerAPI;
import com.exmobile.employeefamilyandroid.bean.RespMessage;
import com.exmobile.employeefamilyandroid.bean.RespVersion;
import com.exmobile.employeefamilyandroid.ui.activity.MainActivity;

import nucleus.presenter.RxPresenter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 张聪聪 on 2016/7/20.
 * Email：2353410167@qq.com
 */
public class MainPresenter extends RxPresenter<MainActivity> {
    public static final int REQUEST_VERSION = 1;

    @Override
    public void create(Bundle bundle) {
        super.create(bundle);

        this.<Object, Object, Object, Object>restartable(REQUEST_VERSION, (o1, o2, o3, o4) -> {
            return ServerAPI.getEmployeeAPI(AppManager.LOCAL_LOGINED_USER.getAPI_IP()).getVersion()
                    .subscribeOn(Schedulers.io())
                    .compose(this.<RespVersion>deliverLatestCache())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(split(
                            (view, data) -> {
                                if (data.getCode() == 1) {
                                    view.onGetVersionSuccessful(data);
                                } else {
                                    view.onGetVersionFailed(data.getMessage());
                                }
                            },
                            (view, error) -> {
                                error.printStackTrace();
                            }));
        });
    }


    public void getVersion() {
        start(REQUEST_VERSION, null, null, null, null);
    }
}
