package com.exmobile.employeefamilyandroid.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.adapter.SalaryDetailAdapter;
import com.exmobile.employeefamilyandroid.bean.Culture;
import com.exmobile.employeefamilyandroid.bean.SalaryDetail;
import com.exmobile.employeefamilyandroid.bean.SalaryItem;
import com.exmobile.employeefamilyandroid.presenter.ListSalaryDetailPresenter;
import com.exmobile.employeefamilyandroid.ui.activity.SalaryItemActivity;
import com.exmobile.employeefamilyandroid.ui.activity.SalaryItemDetailActivity;
import com.exmobile.mvpbase.adapter.BaseListAdapter;
import com.exmobile.mvpbase.domain.Page;
import com.exmobile.mvpbase.ui.fragment.BaseListFragment;
import com.exmobile.mvpbase.widget.ErrorLayout;

import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;

/**
 * Created by 张聪聪 on 2016/5/26.
 * Email：2353410167@qq.com
 */
@RequiresPresenter(ListSalaryDetailPresenter.class)
public class SalaryDetailListFragment extends BaseListFragment<SalaryDetail,ListSalaryDetailPresenter>{

    public String fK_Company , accessRowID;
    @Override
    protected BaseListAdapter<SalaryDetail> onSetupAdapter() {
        return new SalaryDetailAdapter(mContext,BaseListAdapter.NEITHER);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView.setPadding(0, setDividerSize(), 0, 0);
    }


    @Override
    public int setDividerSize() {
        return getResources().getDimensionPixelSize(R.dimen.middle_divider_height);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (fK_Company == null || accessRowID == null) {

            Bundle args = getArguments();
           SalaryItem salaryItem = (SalaryItem) args.getSerializable(SalaryItemDetailActivity.BUNDLE_KEY_SALARY_ITEM);
            fK_Company = salaryItem.getFK_Company();
            accessRowID =salaryItem.getAccessRowID();
        }
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
    public void onLoadResultData(List<SalaryDetail> result) {
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
        Iterator<SalaryDetail> iterator = result.iterator();
        final List<SalaryDetail> data = mAdapter.getDataSet();
        while (iterator.hasNext()) {
            SalaryDetail obj = iterator.next();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getItemName().equals(obj.getItemName())) {
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
