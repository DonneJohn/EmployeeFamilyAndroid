package com.exmobile.employeefamilyandroid.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.UIManager;
import com.exmobile.employeefamilyandroid.ui.activity.MainActivity;
import com.exmobile.mvpbase.ui.fragment.BaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by abu on 2016/5/9 15:02.
 */
public class ApplicationFragment extends BaseFragment {

    @OnClick({R.id.layout_application_notice, R.id.layout_application_culture, R.id.layout_application_rule, R.id.layout_application_vote, R.id.layout_application_contact})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_application_notice:
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.mTabHost.setCurrentTab(0);
                break;
            case R.id.layout_application_culture:
                UIManager.gotoCulture(getContext());
                break;
            case R.id.layout_application_rule:
                UIManager.gotoRule(getContext());
                break;
            case R.id.layout_application_vote:
                UIManager.gotoVote(getContext());
                break;
            case R.id.layout_application_contact:
                UIManager.gotoContact(getContext());
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_application, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
