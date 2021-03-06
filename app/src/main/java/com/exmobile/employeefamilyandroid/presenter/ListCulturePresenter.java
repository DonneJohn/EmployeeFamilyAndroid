package com.exmobile.employeefamilyandroid.presenter;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;

import com.exmobile.employeefamilyandroid.AppManager;
import com.exmobile.employeefamilyandroid.ServerAPI;
import com.exmobile.employeefamilyandroid.bean.Culture;
import com.exmobile.employeefamilyandroid.bean.RespCulture;
import com.exmobile.employeefamilyandroid.ui.fragment.CultureListFragment;
import com.exmobile.mvpbase.manager.DeviceManager;
import com.exmobile.mvpbase.presenter.BaseListPresenter;
import com.exmobile.mvpbase.ui.fragment.BaseListFragment;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/5/10.
 */
public class ListCulturePresenter extends BaseListPresenter<CultureListFragment> {
    public static final String CACHE_KEY_All = "AllCultureListData";

    public String cache_key = CACHE_KEY_All;

    private static final int REQUEST_CULTURE_DATA = 1;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        add(afterTakeView().subscribe(this::init, Throwable::printStackTrace));

//        this.<Integer, Subscription, Integer, Object>restartable(REQUEST_CULTURE_DATA, (pageNum, subscriber, mode, o4) -> {
//            Observable<RespCulture> observable = ServerAPI.getEmployeeAPI(AppManager.LOCAL_LOGINED_USER.getAPI_IP()).getCultureList(pageNum + 1, AppManager.LOCAL_LOGINED_USER.getFK_Company());
//            return observable.subscribeOn(Schedulers.io())
//                    .compose(this.<RespCulture>deliverFirst())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(split(
//                            (view, page) -> {
//                                if (subscriber != null)
//                                    dismissReadCache(view, subscriber);
//                                if (page != null && page.getResult() != null && page.getResult().size() > 0)
//                                    this.<Culture>cacheData(view.getContext(), page.getResult(), cache_key);
//                                view.onLoadResultData(page.getResult());
//                                view.onLoadFinishState(CultureListFragment.LOAD_MODE_DEFAULT);
//                            },
//                            (view, error) -> {
//                                error.printStackTrace();
//                                view.onLoadErrorState(mode);
//                            }));
//        });
    }

    /**
     * ???????????????view??????????????????
     *
     * @param view
     */
    private void init(CultureListFragment view) {
        // ????????????
        view.onLoadingState(BaseListFragment.LOAD_MODE_CACHE);
        Subscription mReadCacheSubscriptor = this.<List<Culture>>getCacheFile(view.getContext(), cache_key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (data) -> {
                            view.onLoadResultData(data);
                            view.onLoadFinishState(getView().LOAD_MODE_CACHE);
                        },
                        (error) -> {
                            Log.w("traffic", "??????????????????");
                        });

        // request remote data
        view.getView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                getView().getView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                requestData(view, mReadCacheSubscriptor);
            }
        });
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param view
     * @param subscriptor
     */
    public void requestData(CultureListFragment view, Subscription subscriptor) {
        // ??????????????????
        if (!DeviceManager.hasInternet(view.getContext())) return;

        view.onLoadingState(BaseListFragment.LOAD_MODE_DEFAULT);

        // ???????????????????????????, ??????restartableFirst????????????restartableLastCache,????????????
        // views emit, ?????????????????????????????????.

        start(REQUEST_CULTURE_DATA, 0, subscriptor, BaseListFragment.LOAD_MODE_DEFAULT, null);
    }

    @Override
    public void requestData(int mode, int pageNum) {
        // ??????????????????
        if (!DeviceManager.hasInternet(getView().getContext())) {
            getView().onNetworkInvalid(mode);
            return;
        }

        // ??????add??????????????????????????????SubscriptList????????????????????????,
        // ????????????????????????????????????????????????,??????????????????????????????,??????
        // ??????????????????????????????,??????????????????,??????????????????restartable

        // ????????????????????????,??????????????????????????????,?????????????????????????????????
        // ???CmmPresenter???????????????

        start(REQUEST_CULTURE_DATA, pageNum, null, mode, null);
    }

    /**
     * ??????????????????
     *
     * @param subscriptor
     */
    public void dismissReadCache(CultureListFragment view, Subscription subscriptor) {
        if (!subscriptor.isUnsubscribed()) {
            view.onLoadFinishState(CultureListFragment.LOAD_MODE_CACHE);
            subscriptor.unsubscribe();
        }
    }
}
