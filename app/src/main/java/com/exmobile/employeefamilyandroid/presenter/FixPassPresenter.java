package com.exmobile.employeefamilyandroid.presenter;

import android.os.Bundle;

import com.exmobile.employeefamilyandroid.AppManager;
import com.exmobile.employeefamilyandroid.ServerAPI;
import com.exmobile.employeefamilyandroid.bean.RespMessage;
import com.exmobile.employeefamilyandroid.ui.activity.FixPassActivity;

import nucleus.presenter.RxPresenter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/4/15.
 */
public class FixPassPresenter extends RxPresenter<FixPassActivity> {
    private static final int REQUEST_UPDATEPASS2 = 1;

    @Override
    public void create(Bundle bundle) {
        super.create(bundle);

        this.<String, String, String, Object>restartable(REQUEST_UPDATEPASS2, (userId, originalPassword, password, o4) -> {
            return ServerAPI.getEmployeeAPI(AppManager.LOCAL_LOGINED_USER.getAPI_IP()).updatePassword(userId, originalPassword, password)
                    .subscribeOn(Schedulers.io())
                    .compose(this.<RespMessage>deliverLatestCache())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(split(
                            (view, data) -> {
                                if (data.getCode() == 1) {
                                    view.onFixSuccessful(data);
                                } else {
                                    view.onFixFailed(data.getMessage());
                                }
                            },
                            (view, error) -> {
                                error.printStackTrace();
                                view.onFixFailed("");
                            }));
        });
    }


    public void updatePass(String userId, String originalPassword, String password) {
        start(REQUEST_UPDATEPASS2, userId, originalPassword, password, null);
    }
}
