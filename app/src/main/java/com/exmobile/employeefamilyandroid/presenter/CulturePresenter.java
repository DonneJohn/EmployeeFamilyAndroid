package com.exmobile.employeefamilyandroid.presenter;

import android.os.Bundle;

import com.exmobile.employeefamilyandroid.AppManager;
import com.exmobile.employeefamilyandroid.ServerAPI;
import com.exmobile.employeefamilyandroid.bean.RespCulture;
import com.exmobile.employeefamilyandroid.bean.RespCultureBanner;
import com.exmobile.employeefamilyandroid.ui.activity.CultureActivity;

import nucleus.presenter.Presenter;
import nucleus.presenter.RxPresenter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 张聪聪 on 2016/6/8.
 * Email：2353410167@qq.com
 */
public class CulturePresenter extends RxPresenter<CultureActivity> {

    private static final int REQUEST_CULTURE = 1;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        this.<String, Object, Object, Object>restartable(REQUEST_CULTURE, (company, o2, o3, o4) -> {
            return ServerAPI.getEmployeeAPI(AppManager.LOCAL_LOGINED_USER.getAPI_IP()).getCultureList(company)
                    .subscribeOn(Schedulers.io())
                    .compose(this.<RespCultureBanner>deliverLatestCache())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(split(
                            (view, data) -> {
                                if (data.getCode() == 1) {
                                    view.onGetCultureListSuccess(data.getResult());
                                } else {
                                    view.onGetCultureListError(data.getMessage());
                                }
                            }, (view, error) -> {
                                error.printStackTrace();
                                view.onGetCultureListError(null);
                            }));

        });


    }

    public void getCultureList(String company) {
        start(REQUEST_CULTURE, company, null, null, null);
    }
}
