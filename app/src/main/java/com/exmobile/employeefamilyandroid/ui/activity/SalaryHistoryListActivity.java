package com.exmobile.employeefamilyandroid.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.UIManager;
import com.exmobile.employeefamilyandroid.ui.fragment.MonthListFragment;
import com.exmobile.mvpbase.ui.activity.BaseHoldBackActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 张聪聪 on 2016/5/25.
 * Email：2353410167@qq.com
 */
public class SalaryHistoryListActivity extends BaseHoldBackActivity {
    @Override
    protected String onSetTitle() {
      return "历史查询";
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_salary_history_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ButterKnife.bind(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, Fragment.instantiate(this, MonthListFragment.class.getName())).commit();
//        RxBus.with(this)
//                .setEndEvent(ActivityEvent.DESTROY)
//                .setEvent(Events.EventEnum.DELIVER_YEAR)
//                .onNext((event) -> {
//                    String year= event.<String>getMessage();
//
//                }).create();

    }

//    @OnClick(R.id.tv_choice_year)
//    void choiceYear(){
//        UIManager.gotoGetYearList(this);
//    }
}
