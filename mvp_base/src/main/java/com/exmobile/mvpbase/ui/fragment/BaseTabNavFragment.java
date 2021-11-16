package com.exmobile.mvpbase.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exmobile.mvpbase.R;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import nucleus.presenter.Presenter;


/**
 * 自定义导航元素的viewpager
 * 使用场景：表情选项卡，轮番
 *
 * @author thanatos
 * @create 2016-01-05
 **/
public abstract class BaseTabNavFragment extends BaseTabFragment implements ViewPager.OnPageChangeListener {

    protected LinearLayout mNavLayout;
    protected TextView tvTitle;
    protected ArrayList<String> titles;

    private boolean isContinue = true;
    private AtomicInteger what = new AtomicInteger(0);
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_dot_nav, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mNavLayout = (LinearLayout) view.findViewById(R.id.tab_nav);
        tvTitle = (TextView) view.findViewById(R.id.tv_nav_title);
        super.onViewCreated(view, savedInstanceState);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        isContinue = false;
                        break;
                    case MotionEvent.ACTION_UP:
                        isContinue = true;
                        break;
                    default:
                        isContinue = true;
                        break;
                }


                return false;
            }
        });

        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    if (isContinue) {
                        viewHandler.sendEmptyMessage(what.get());
                        whatOption();
                    }
                }
            }

        }).start();

    }


    private void whatOption() {
        what.incrementAndGet();
        if (what.get() > mTabs.size() - 1) {
            what.getAndAdd(-4);
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {

        }
    }

    private final Handler viewHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            mViewPager.setCurrentItem(msg.what);
            super.handleMessage(msg);
        }

    };


    @Override
    public void addTab(String title, Class<? extends Fragment> fragment, int catalog) {
        super.addTab(title, fragment, catalog);
        View view = setupTabItemView(title);
        mNavLayout.addView(view);
        mTabs.get(mTabs.size() - 1).view = view;
    }

    @Override
    public void addTab(String title, Class<? extends Fragment> fragment) {
        super.addTab(title, fragment);
        View view = setupTabItemView(title);
        mNavLayout.addView(view);
        mTabs.get(mTabs.size() - 1).view = view;
    }

    @Override
    public void addTab(String title, Fragment fragment) {
        super.addTab(title, fragment);
        View view = setupTabItemView(title);

        mNavLayout.addView(view);
        mTabs.get(mTabs.size() - 1).view = view;
    }

    public void setCurrentItem(int index) {
        mViewPager.setCurrentItem(index);
        mTabs.get(index).view.setSelected(true);
        tvTitle.setText(titles.get(0));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tvTitle.setText(titles.get(position));
        for (int i = 0; i < mTabs.size(); i++) {
            if (position == i)
                mTabs.get(i).view.setSelected(true);
            else
                mTabs.get(i).view.setSelected(false);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {


    }


}
