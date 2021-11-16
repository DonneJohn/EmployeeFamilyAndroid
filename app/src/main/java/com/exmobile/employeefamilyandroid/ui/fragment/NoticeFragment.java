package com.exmobile.employeefamilyandroid.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.exmobile.employeefamilyandroid.AppManager;
import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.UIManager;
import com.exmobile.employeefamilyandroid.adapter.NoticeAdapter;
import com.exmobile.employeefamilyandroid.bean.Culture;
import com.exmobile.employeefamilyandroid.bean.Notice;
import com.exmobile.employeefamilyandroid.presenter.NoticePresenter;
import com.exmobile.mvpbase.adapter.BaseListAdapter;
import com.exmobile.mvpbase.domain.Page;
import com.exmobile.mvpbase.manager.DeviceManager;
import com.exmobile.mvpbase.ui.fragment.BaseListFragment;
import com.exmobile.mvpbase.utils.Utilities;
import com.exmobile.mvpbase.widget.ErrorLayout;

import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nucleus.factory.RequiresPresenter;

/**
 * Created by zcc on 2016/5/9 14:57.
 */
@RequiresPresenter(NoticePresenter.class)
public class NoticeFragment extends BaseListFragment<Notice, NoticePresenter> implements BaseListAdapter.OnItemClickListener {

    @BindView(R.id.et_search)
    EditText etSearch;

    @BindView(R.id.ll_search)
    LinearLayout llSearch;

    @Override
    protected BaseListAdapter onSetupAdapter() {
        return new NoticeAdapter(getContext(), BaseListAdapter.ONLY_FOOTER);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notice, container, false);
    }

    @OnClick(R.id.tv_search)
    void search() {

        String keyword = etSearch.getText().toString();

        if (Utilities.isEmpty(keyword)) {
            etSearch.setError("请输入搜索关键字");
        } else {


            UIManager.gotoSearchNotice(getActivity(), keyword);
        }
    }


    @Override
    public void onItemClick(int position, long id, View view) {
//        UIManager.showNoticeDetail(getActivity(),mAdapter.getItem(position));

        String api = AppManager.LOCAL_LOGINED_USER.getAPI_IP();
        UIManager.gotoDetail(getContext(), api.substring(0, api.length() - 4) + "Appweb/NoticeDetail.aspx?RowID=" + mAdapter.getItem(position).getRowID(), "公告详情");
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
    public void onLoadResultData(List<Notice> result) {
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
        Iterator<Notice> iterator = result.iterator();
        final List<Notice> data = mAdapter.getDataSet();
        while (iterator.hasNext()) {
            Notice obj = iterator.next();
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


    /**
     * when load data broken
     */
    public void onLoadErrorState(int mode) {
        switch (mode) {
            case LOAD_MODE_DEFAULT:
                mSwipeRefreshLayout.setEnabled(true);
                if (mAdapter.getDataSize() > 0) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mState = STATE_NONE;
                    Toast.makeText(mContext, "数据加载失败", Toast.LENGTH_SHORT).show();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mErrorLayout.setState(ErrorLayout.EMPTY_DATA);
                }
                break;
            case LOAD_MODE_UP_DRAG:
                mAdapter.setState(BaseListAdapter.STATE_LOAD_ERROR);
                break;
        }

    }

}
