package com.exmobile.employeefamilyandroid.presenter;

import android.os.Bundle;

import com.exmobile.employeefamilyandroid.AppManager;
import com.exmobile.employeefamilyandroid.ServerAPI;
import com.exmobile.employeefamilyandroid.bean.RespMessage;
import com.exmobile.employeefamilyandroid.ui.activity.VoteDetailActivity;

import nucleus.presenter.RxPresenter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/5/12.
 */
public class VoteDetailPresenter extends RxPresenter<VoteDetailActivity> {
    private static final int REQUEST_SUBMIT_VOTE = 1;

    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        this.<String, Object, Object, Object>restartable(REQUEST_SUBMIT_VOTE, (data, o2, o3, o4) -> {
            return ServerAPI.getEmployeeAPI(AppManager.LOCAL_LOGINED_USER.getAPI_IP()).submitVoteResult(AppManager.LOCAL_LOGINED_USER.getRowID(), data)
                    .subscribeOn(Schedulers.io())
                    .compose(this.<RespMessage>deliverLatestCache())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(split((view, result) -> {
                                if (result.getCode() == 1) {
                                    view.onLoadSuccessful(result.getResult());
                                } else {
                                    view.onLoadFailed(result.getMessage());
                                }
                            },
                            (view, error) -> {
                                view.onLoadFailed(null);
                                error.printStackTrace();
                            }));
        });
    }

    public void submitVote(String data){
        start(REQUEST_SUBMIT_VOTE, data, null, null, null);
    }
}
