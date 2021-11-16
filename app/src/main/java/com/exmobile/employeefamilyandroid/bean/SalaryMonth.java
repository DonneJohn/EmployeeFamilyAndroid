package com.exmobile.employeefamilyandroid.bean;

import com.exmobile.mvpbase.domain.Entity;

/**
 * Created by 张聪聪 on 2016/5/25.
 * Email：2353410167@qq.com
 */
public class SalaryMonth extends Entity{

    private String Title;
    private String theYear;
    private String theMonth;

    public SalaryMonth() {
    }

    public SalaryMonth(String title, String theYear, String theMonth) {

        Title = title;
        this.theYear = theYear;
        this.theMonth = theMonth;
    }

    public String getTitle() {

        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTheYear() {
        return theYear;
    }

    public void setTheYear(String theYear) {
        this.theYear = theYear;
    }

    public String getTheMonth() {
        return theMonth;
    }

    public void setTheMonth(String theMonth) {
        this.theMonth = theMonth;
    }
}
