package com.exmobile.employeefamilyandroid.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.adapter.YearListAdapter;
import com.exmobile.employeefamilyandroid.bean.Culture;
import com.exmobile.employeefamilyandroid.bean.SalaryYear;
import com.exmobile.employeefamilyandroid.event.Events;
import com.exmobile.employeefamilyandroid.event.RxBus;
import com.exmobile.employeefamilyandroid.presenter.ListYearPresenter;
import com.exmobile.mvpbase.adapter.BaseListAdapter;
import com.exmobile.mvpbase.domain.Page;
import com.exmobile.mvpbase.ui.fragment.BaseListFragment;
import com.exmobile.mvpbase.widget.ErrorLayout;

import java.util.Iterator;
import java.util.List;

import nucleus.factory.RequiresPresenter;

/**
 * Created by 张聪聪 on 2016/5/26.
 * Email：2353410167@qq.com
 */
@RequiresPresenter(ListYearPresenter.class)
public class YearListFragment extends BaseListFragment<SalaryYear, ListYearPresenter> implements BaseListAdapter.OnItemClickListener {


    @Override
    protected BaseListAdapter<SalaryYear> onSetupAdapter() {
        return new YearListAdapter(getContext(), BaseListAdapter.NEITHER);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView.setPadding(0, setDividerSize(), 0, 0);
        mAdapter.setOnItemClickListener(this);
    }


    @Override
    public int setDividerSize() {
        return getResources().getDimensionPixelSize(R.dimen.min_divider_height);
    }


    @Override
    public void onItemClick(int position, long id, View view) {
//        UIManager.showNoticeDetail(getActivity(),mAdapter.getItem(position));


        Events<String> events = Events.just(mAdapter.getItem(position).getSalaryYear());
        events.what = Events.EventEnum.DELIVER_YEAR;
        RxBus.getInstance().send(events);

        getActivity().finish();
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
    public void onLoadResultData(List<SalaryYear> result) {
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
        Iterator<SalaryYear> iterator = result.iterator();
        final List<SalaryYear> data = mAdapter.getDataSet();
        while (iterator.hasNext()) {
            SalaryYear obj = iterator.next();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getSalaryYear().equals(obj.getSalaryYear())) {
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

