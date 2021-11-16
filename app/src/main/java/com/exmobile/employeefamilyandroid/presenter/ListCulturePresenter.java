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
     * 第一次绑定view所要做的工作
     *
     * @param view
     */
    private void init(CultureListFragment view) {
        // 读取缓存
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
                            Log.w("traffic", "没有缓存文件");
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
     * 访问网络数据之前解除读取缓存
     *
     * @param view
     * @param subscriptor
     */
    public void requestData(CultureListFragment view, Subscription subscriptor) {
        // 判读网络情况
        if (!DeviceManager.hasInternet(view.getContext())) return;

        view.onLoadingState(BaseListFragment.LOAD_MODE_DEFAULT);

        // 得及时解除订阅才行, 使用restartableFirst而不是用restartableLastCache,不然每次
        // views emit, 这里的订阅者都会有响应.

        start(REQUEST_CULTURE_DATA, 0, subscriptor, BaseListFragment.LOAD_MODE_DEFAULT, null);
    }

    @Override
    public void requestData(int mode, int pageNum) {
        // 判读网络情况
        if (!DeviceManager.hasInternet(getView().getContext())) {
            getView().onNetworkInvalid(mode);
            return;
        }

        // 使用add方法将订阅者们添加到SubscriptList里面管理确实可行,
        // 但是如果一个这个方法我不断地调用,就会不断地添加订阅者,可能
        // 一个操作导致重复添加,造成内存溢出,所以最好使用restartable

        // 还是不能缓存函数,因为参数每次都不一样,除非使用可以传参的方式
        // 在CmmPresenter中已经解决

        start(REQUEST_CULTURE_DATA, pageNum, null, mode, null);
    }

    /**
     * 取消缓存读取
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
