package com.exmobile.employeefamilyandroid.presenter;

import android.os.Bundle;

import com.exmobile.employeefamilyandroid.ServerAPI;
import com.exmobile.employeefamilyandroid.bean.RespUser;
import com.exmobile.employeefamilyandroid.ui.activity.StartActivity;

import nucleus.presenter.RxPresenter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 张聪聪 on 2016/7/22.
 * Email：2353410167@qq.com
 */
public class StartPresenter extends RxPresenter<StartActivity> {

    private static final int REQUEST_LOGIN = 1;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        this.<String, String, String, Object>restartable(REQUEST_LOGIN, (id, username, password, o4) -> {
            return ServerAPI.getEmployeeAPI(ServerAPI.baseUrl).loginFirst(id, username, password)
                    .subscribeOn(Schedulers.io())
                    .compose(this.<RespUser>deliverLatestCache())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(split(
                            (view, data) -> {


                                if (data.getCode() == 1) {
                                    view.onLoadSuccessful(data.getResult().get(0));
                                } else {

                                    view.onLoadFailed(data.getMessage());
                                }


                            },


                            (view, error) -> {
                                error.printStackTrace();

                                view.onLoadFailed(null);
                            }));
        });
    }

    public void login(String id, String username, String password) {
        start(REQUEST_LOGIN, id, username, password, null);
    }
}
