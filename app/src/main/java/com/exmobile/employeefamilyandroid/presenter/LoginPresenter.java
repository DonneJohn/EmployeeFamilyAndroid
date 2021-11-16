package com.exmobile.employeefamilyandroid.presenter;

import android.os.Bundle;

import com.exmobile.employeefamilyandroid.AppManager;
import com.exmobile.employeefamilyandroid.ServerAPI;
import com.exmobile.employeefamilyandroid.bean.RespMessage;
import com.exmobile.employeefamilyandroid.ui.activity.LoginActivity;

import nucleus.presenter.RxPresenter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zcc on 2016/5/10 14:00.
 */
public class LoginPresenter extends RxPresenter<LoginActivity> {
    private static final int REQUEST_VERIFY_ConFIRM_MESSAGE_CODE = 2;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        this.<String, Object, Object, Object>restartable(REQUEST_VERIFY_ConFIRM_MESSAGE_CODE, (rowID, O2, o3, o4) -> {
            return ServerAPI.getEmployeeAPI(AppManager.LOCAL_LOGINED_USER.getAPI_IP()).confirmMessageCode(rowID)
                    .subscribeOn(Schedulers.io())
                    .compose(this.<RespMessage>deliverLatestCache())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(split(
                            (view, data) -> {
                                if (data.getCode() == 1) {
                                    view.onConFirmMessageSuccessful(data);
                                } else {
                                    view.onConFirmMessageFailed(data.getMessage());
                                }
                            },
                            (view, error) -> {
                                error.printStackTrace();
                                view.onConFirmMessageFailed(null);
                            }));
        });


    }



    public void confirmMessageConde(String rowID) {
        start(REQUEST_VERIFY_ConFIRM_MESSAGE_CODE, rowID, null, null, null);
    }


}

