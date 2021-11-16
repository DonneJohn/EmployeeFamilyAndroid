package com.exmobile.employeefamilyandroid.presenter;

import android.os.Bundle;

import com.exmobile.employeefamilyandroid.AppManager;
import com.exmobile.employeefamilyandroid.ServerAPI;
import com.exmobile.employeefamilyandroid.bean.RespNotice;
import com.exmobile.employeefamilyandroid.ui.fragment.NoticeSearchFragment;
import com.exmobile.mvpbase.manager.DeviceManager;
import com.exmobile.mvpbase.presenter.BaseListPresenter;
import com.exmobile.mvpbase.ui.fragment.BaseListFragment;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zcc on 2016/5/10 18:38.
 */
public class NoticeSearchPresenter extends BaseListPresenter<NoticeSearchFragment> {



    private static final int REQUEST_REMOTE_PAGE_DATA = 1;





    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        add(afterTakeView().subscribe(this::init, Throwable::printStackTrace));

        // 初始化函数
        this.<Integer, String, Integer, String>restartable(REQUEST_REMOTE_PAGE_DATA,
                (pageNum, company, mode, keyword) -> {
                    Observable<RespNotice> observable;

                        observable = ServerAPI.getEmployeeAPI(AppManager.LOCAL_LOGINED_USER.getAPI_IP()).getNoticeList(pageNum, company, keyword);

                    return observable.subscribeOn(Schedulers.io())
                            .compose(this.<RespNotice>deliverFirst())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(split(
                                    (view, page) -> {



                                        view.onLoadResultData(page.getResult());
                                        view.onLoadFinishState(NoticeSearchFragment.LOAD_MODE_DEFAULT);
                                    },
                                    (view, error) -> {
                                        error.printStackTrace();
                                        if (error instanceof UnknownHostException || error instanceof SocketTimeoutException) {
                                            view.onNetworkInvalid(mode);
                                        } else {
                                            view.onLoadErrorState(mode);
                                        }
                                    }));
                });
    }

    /**
     * 第一次绑定view所要做的工作
     *
     * @param view
     */
    private void init(NoticeSearchFragment view) {

            start(REQUEST_REMOTE_PAGE_DATA, 1, AppManager.LOCAL_LOGINED_USER.getFK_Company(), BaseListFragment.LOAD_MODE_DEFAULT, getView().keyword);

    }

    /**
     * 请求数据
     *
     * @param pageNum which page number
     */
    @Override
    public void requestData(int mode, int pageNum) {
        // 判读网络情况
        if (!DeviceManager.hasInternet(getView().getContext())) {
            getView().onNetworkInvalid(mode);
            return;
        }

            start(REQUEST_REMOTE_PAGE_DATA, pageNum+1, AppManager.LOCAL_LOGINED_USER.getFK_Company(), mode, getView().keyword);
    }






}
