package com.exmobile.employeefamilyandroid.ui.activity;

import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.ui.fragment.NoticeFragment;
import com.exmobile.employeefamilyandroid.ui.fragment.ApplicationFragment;
import com.exmobile.employeefamilyandroid.ui.fragment.AverageFragment;
import com.exmobile.employeefamilyandroid.ui.fragment.MeFragment;


public enum MainTab {

    ANNOUNCEMENT(0, R.string.main_tab_announcement, R.drawable.tab_icon_notice,
            NoticeFragment.class),

    AVERAGE(1, R.string.main_tab_average, R.drawable.tab_icon_average,
            AverageFragment.class),

    APPLICATION(2, R.string.main_tab_application, R.drawable.tab_icon_application,
            ApplicationFragment.class),


    ME(3, R.string.main_tab_me, R.drawable.tab_icon_me,
            MeFragment.class);

    private int idx;
    private int resName;
    private int resIcon;
    private Class<?> clz;

    private MainTab(int idx, int resName, int resIcon, Class<?> clz) {
        this.idx = idx;
        this.resName = resName;
        this.resIcon = resIcon;
        this.clz = clz;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getResName() {
        return resName;
    }

    public void setResName(int resName) {
        this.resName = resName;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }
}
