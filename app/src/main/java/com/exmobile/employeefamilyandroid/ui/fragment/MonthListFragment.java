package com.exmobile.employeefamilyandroid.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.UIManager;
import com.exmobile.employeefamilyandroid.adapter.MonthListAdapter;
import com.exmobile.employeefamilyandroid.bean.Culture;
import com.exmobile.employeefamilyandroid.bean.SalaryMonth;
import com.exmobile.employeefamilyandroid.event.Events;
import com.exmobile.employeefamilyandroid.event.RxBus;
import com.exmobile.employeefamilyandroid.presenter.ListMonthPresenter;
import com.exmobile.mvpbase.adapter.BaseListAdapter;
import com.exmobile.mvpbase.domain.Page;
import com.exmobile.mvpbase.ui.fragment.BaseListFragment;
import com.exmobile.mvpbase.widget.ErrorLayout;
import com.trello.rxlifecycle.FragmentEvent;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;

/**
 * Created by 张聪聪 on 2016/5/25.
 * Email：2353410167@qq.com
 */
@RequiresPresenter(ListMonthPresenter.class)
public class MonthListFragment extends BaseListFragment<SalaryMonth, ListMonthPresenter> implements BaseListAdapter.OnItemClickListener {

//    public String year;

    @Override
    protected BaseListAdapter<SalaryMonth> onSetupAdapter() {
        return new MonthListAdapter(getContext(), BaseListAdapter.NEITHER);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView.setPadding(0, setDividerSize(), 0, 0);
        mAdapter.setOnItemClickListener(this);
        ButterKnife.bind(this, view);
    }


    @Override
    public int setDividerSize() {
        return getResources().getDimensionPixelSize(R.dimen.min_divider_height);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (year == null) {
//            Calendar date = Calendar.getInstance();
//            year = date.get(date.YEAR) + "年";
//        }

//        RxBus.with(this)
//                .setEndEvent(FragmentEvent.DESTROY)
//                .setEvent(Events.EventEnum.DELIVER_YEAR)
//                .onNext((event) -> {
//                    year= event.<String>getMessage();
//                    onLoadingState(BaseListFragment.LOAD_MODE_DEFAULT);
//                    getPresenter().requestData(BaseListFragment.LOAD_MODE_DEFAULT, 0);
//                }).create();

    }

    @Override
    public void onItemClick(int position, long id, View view) {
        UIManager.gotoSalaryItemList(getContext(),mAdapter.getItem(position).getTheYear(),mAdapter.getItem(position).getTheMonth());
    }

    /**
     * 上拉加载
     */
    @Override
    public void onLoading() {
        if (mState == STATE_REFRESHING) {
            mAdapter.setState(BaseListAdapter.STATE_REFRESHING);
            return;
        }
        mCurrentPage++;
        mAdapter.setState(BaseListAdapter.STATE_LOADING);
//        Log.d("thanatos", "change adapter state");
        getPresenter().requestData(LOAD_MODE_UP_DRAG, mCurrentPage + 1);
    }


    /**
     * 请求成功，读取缓存成功，加载数据
     *
     * @param result
     */
    public void onLoadResultData(List<SalaryMonth> result) {
        if (result == null) return;
//        Log.d("thanatos", "onLoadResultData - result's size = " + (mAdapter.getDataSize() + result.size()));
//        Log.d("thanatos", "onLoadResultData - result's size = " + result.size());

        if (mCurrentPage == 0)
            mAdapter.clear();

        if (mAdapter.getDataSize() + result.size() == 0) {
            mErrorLayout.setState(ErrorLayout.EMPTY_DATA);
            mAdapter.setState(BaseListAdapter.STATE_HIDE);
            return;
        } else if (result.size() < Page.PAGE_SIZE) {
            mAdapter.setState(BaseListAdapter.STATE_NO_MORE);
        } else {
            mAdapter.setState(BaseListAdapter.STATE_LOAD_MORE);
        }
        Iterator<SalaryMonth> iterator = result.iterator();
        final List<SalaryMonth> data = mAdapter.getDataSet();
        while (iterator.hasNext()) {
            SalaryMonth obj = iterator.next();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getTitle().equals(obj.getTitle())) {
                    data.set(i, obj);
                    iterator.remove();
                    break;
                }
            }
        }
        if (mCurrentPage == 0)
            mAdapter.addItems(0, result);
        else
            mAdapter.addItems(result);
    }
}
