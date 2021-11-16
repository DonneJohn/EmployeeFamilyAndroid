package com.exmobile.employeefamilyandroid.presenter;

import android.os.Bundle;

import com.exmobile.employeefamilyandroid.AppManager;
import com.exmobile.employeefamilyandroid.ServerAPI;
import com.exmobile.employeefamilyandroid.bean.RespMessage;
import com.exmobile.employeefamilyandroid.ui.activity.FirstPhoneCheckActivity;
import com.exmobile.employeefamilyandroid.ui.activity.FirstPhoneCheckActivity;

import nucleus.presenter.RxPresenter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zcc on 2016/4/14.
 */
public class FindPassPresenter extends RxPresenter<FirstPhoneCheckActivity> {

    private static final int REQUEST_VERIFY_CODE = 1;
    private static final int REQUEST_VERIFY_ConFIRM_MESSAGE_CODE = 2;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        this.<String, String, Object, Object>restartable(REQUEST_VERIFY_CODE, (mobile, nouse, o3, o4) -> {
            return ServerAPI.getEmployeeAPI(AppManager.LOCAL_LOGINED_USER.getAPI_IP()).getMessageCode(mobile)
                    .subscribeOn(Schedulers.io())
                    .compose(this.<RespMessage>deliverLatestCache())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(split(
                            (view, data) -> {
                                if (data.getCode() == 1) {
                                    view.onGetMessageSuccessful(data);
                                } else {
                                    view.onGetMessageFailed(data.getMessage());
                                }
                            },
                            (view, error) -> {
                                error.printStackTrace();
                                view.onGetMessageFailed(null);
                            }));
        });

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
                                view.onGetMessageFailed(null);
                            }));
        });
    }

    public void getVerifyCode(String mobile) {
        start(REQUEST_VERIFY_CODE, mobile, null, null, null);
    }

    public void confirmMessageConde(String rowID) {
        start(REQUEST_VERIFY_ConFIRM_MESSAGE_CODE, rowID, null, null, null);
    }
}
