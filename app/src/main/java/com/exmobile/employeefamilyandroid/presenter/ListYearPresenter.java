package com.exmobile.employeefamilyandroid.presenter;

import android.os.Bundle;

import com.exmobile.employeefamilyandroid.AppManager;
import com.exmobile.employeefamilyandroid.ServerAPI;
import com.exmobile.employeefamilyandroid.bean.RespSalaryYear;
import com.exmobile.employeefamilyandroid.ui.fragment.YearListFragment;
import com.exmobile.mvpbase.manager.DeviceManager;
import com.exmobile.mvpbase.presenter.BaseListPresenter;
import com.exmobile.mvpbase.ui.fragment.BaseListFragment;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 张聪聪 on 2016/5/26.
 * Email：2353410167@qq.com
 */
public class ListYearPresenter extends BaseListPresenter<YearListFragment> {
    private static final int REQUEST_REMOTE_PAGE_DATA = 1;

    @Override
    public void requestData(int mode, int pageNum) {
        // 判读网络情况
        if (!DeviceManager.hasInternet(getView().getContext())) {
            getView().onNetworkInvalid(mode);
            return;
        }

        start(REQUEST_REMOTE_PAGE_DATA, AppManager.LOCAL_LOGINED_USER.getCompanyInfo().get(0).getRowID(), mode,null, null);

    }


    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        add(afterTakeView().subscribe(this::init, Throwable::printStackTrace));
        this.<String, Integer,Object , Object>restartable(REQUEST_REMOTE_PAGE_DATA, (company, mode, o1, o2) -> {
            Observable<RespSalaryYear> observable;


            observable = ServerAPI.getEmployeeAPI(AppManager.LOCAL_LOGINED_USER.getAPI_IP()).getYearList(company);
            return observable.subscribeOn(Schedulers.io())
                    .compose(this.<RespSalaryYear>deliverFirst())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(split(
                            (view, page) -> {
                                if (page != null && page.getResult() != null && page.getResult().size() > 0)
                                    view.onLoadResultData(page.getResult());

                                view.onLoadFinishState(mode);

                            },
                            (view, error) -> {
                                error.printStackTrace();
                                view.onLoadErrorState(mode);
                            }));
        });
    }


    /**
     * 第一次绑定view所要做的工作
     *
     * @param view
     */
    private void init(YearListFragment view) {

        start(REQUEST_REMOTE_PAGE_DATA, AppManager.LOCAL_LOGINED_USER.getCompanyInfo().get(0).getRowID(), BaseListFragment.LOAD_MODE_DEFAULT, null,null);

    }
}
