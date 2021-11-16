package com.exmobile.employeefamilyandroid.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.UIManager;
import com.exmobile.employeefamilyandroid.adapter.SalaryItemAdapter;
import com.exmobile.employeefamilyandroid.bean.Culture;
import com.exmobile.employeefamilyandroid.bean.SalaryItem;
import com.exmobile.employeefamilyandroid.presenter.ListSalaryItemPresenter;
import com.exmobile.employeefamilyandroid.ui.activity.SalaryItemActivity;
import com.exmobile.mvpbase.adapter.BaseListAdapter;
import com.exmobile.mvpbase.domain.Page;
import com.exmobile.mvpbase.ui.fragment.BaseListFragment;
import com.exmobile.mvpbase.widget.ErrorLayout;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;

/**
 * Created by 张聪聪 on 2016/5/26.
 * Email：2353410167@qq.com
 */
@RequiresPresenter(ListSalaryItemPresenter.class)
public class SalaryItemListFragment extends BaseListFragment<SalaryItem, ListSalaryItemPresenter> implements BaseListAdapter.OnItemClickListener {

    public String year;
    public String month;

    @Override
    protected BaseListAdapter<SalaryItem> onSetupAdapter() {
        return new SalaryItemAdapter(mContext, BaseListAdapter.NEITHER);
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
        return getResources().getDimensionPixelSize(R.dimen.middle_divider_height);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (year == null || month == null) {

            Bundle args = getArguments();
            year = args.getString(SalaryItemActivity.BUNDLE_KEY_YEAR);
            month = args.getString(SalaryItemActivity.BUNDLE_KEY_MONTH);


        }

    }

    @Override
    public void onItemClick(int position, long id, View view) {
        UIManager.showSalaryItemDetail(getActivity(),mAdapter.getItem(position));
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
    public void onLoadResultData(List<SalaryItem> result) {
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
        Iterator<SalaryItem> iterator = result.iterator();
        final List<SalaryItem> data = mAdapter.getDataSet();
        while (iterator.hasNext()) {
            SalaryItem obj = iterator.next();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getAccessRowID().equals(obj.getAccessRowID())) {
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
