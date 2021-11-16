package com.exmobile.employeefamilyandroid.ui.fragment;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.util.TimeUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.UIManager;
import com.exmobile.mvpbase.ui.fragment.BaseFragment;

import java.util.Calendar;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zcc on 2016/5/9 14:59.
 */
public class AverageFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_average, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @OnClick(R.id.tv_average_month)
    void gotoMonthList() {
        Calendar date = Calendar.getInstance();
        String year = date.get(date.YEAR) + "年";
        String month = (date.get(date.MONTH) + 1) + "月";
        UIManager.gotoSalaryItemList(getContext(),year,month);
    }

    @OnClick(R.id.tv_average_history)
    void gotoAverageHistory() {
        UIManager.gotoSalaryHistoryList(getContext());
    }
}
