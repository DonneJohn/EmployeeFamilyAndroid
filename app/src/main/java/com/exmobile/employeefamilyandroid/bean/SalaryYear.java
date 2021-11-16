package com.exmobile.employeefamilyandroid.bean;

import com.exmobile.mvpbase.domain.Entity;

/**
 * Created by 张聪聪 on 2016/5/26.
 * Email：2353410167@qq.com
 */
public class SalaryYear extends Entity {

    private String SalaryYear;

    public String getSalaryYear() {
        return SalaryYear;
    }

    public void setSalaryYear(String salaryYear) {
        SalaryYear = salaryYear;
    }

    public SalaryYear() {

    }

    public SalaryYear(String salaryYear) {

        SalaryYear = salaryYear;
    }
}
