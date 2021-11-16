package com.exmobile.employeefamilyandroid.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.exmobile.employeefamilyandroid.AppManager;
import com.exmobile.employeefamilyandroid.UIManager;
import com.exmobile.employeefamilyandroid.adapter.RuleAdapter;
import com.exmobile.employeefamilyandroid.bean.Culture;
import com.exmobile.employeefamilyandroid.bean.Rule;
import com.exmobile.employeefamilyandroid.presenter.ListRulePresenter;
import com.exmobile.mvpbase.adapter.BaseListAdapter;
import com.exmobile.mvpbase.domain.Page;
import com.exmobile.mvpbase.ui.fragment.BaseListFragment;
import com.exmobile.mvpbase.widget.ErrorLayout;

import java.util.Iterator;
import java.util.List;

import nucleus.factory.RequiresPresenter;

/**
 * Created by Administrator on 2016/5/10.
 */
@RequiresPresenter(ListRulePresenter.class)
public class RuleListFragment extends BaseListFragment<Rule, ListRulePresenter> implements BaseListAdapter.OnItemClickListener{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected BaseListAdapter<Rule> onSetupAdapter() {
        return new RuleAdapter(getContext(), BaseListAdapter.ONLY_FOOTER);
    }

    @Override
    public void onItemClick(int position, long id, View view) {
        String api = AppManager.LOCAL_LOGINED_USER.getAPI_IP();
        UIManager.gotoDetail(getContext(), api.substring(0, api.length() - 4) + "Appweb/RuleDetail.aspx?RowID=" + mAdapter.getItem(position).getRowID(), "规章制度详情");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView.setPadding(0, setDividerSize(), 0, 0);
        mAdapter.setOnItemClickListener(this);
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
    public void onLoadResultData(List<Rule> result) {
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
        Iterator<Rule> iterator = result.iterator();
        final List<Rule> data = mAdapter.getDataSet();
        while (iterator.hasNext()) {
            Rule obj = iterator.next();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getRowID().equals(obj.getRowID())) {
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
