package com.exmobile.mvpbase.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.exmobile.mvpbase.R;

import nucleus.presenter.Presenter;

public abstract class BaseHoldBackActivity<P extends Presenter> extends BaseActivity<P> {

    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view;

        setContentView(view = getLayoutInflater().inflate(onBindLayout(), null));

        setTitle(onSetTitle());
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        mToolbar.setTitle("");
//        mToolbar.setSubtitle(onSetTitle());
        tvTitle.setText(onSetTitle());
        mToolbar.setNavigationIcon(R.mipmap.icon_back);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    protected abstract String onSetTitle();

    protected abstract int onBindLayout();


}
