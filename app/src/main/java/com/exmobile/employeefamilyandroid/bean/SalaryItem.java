package com.exmobile.employeefamilyandroid.bean;

import com.exmobile.mvpbase.domain.Entity;

/**
 * Created by 张聪聪 on 2016/5/26.
 * Email：2353410167@qq.com
 */
public class SalaryItem extends Entity{

    private String TemplateName;
    private String FK_Company;
    private String AccessRowID;

    public String getTemplateName() {
        return TemplateName;
    }

    public void setTemplateName(String templateName) {
        TemplateName = templateName;
    }

    public String getFK_Company() {
        return FK_Company;
    }

    public void setFK_Company(String FK_Company) {
        this.FK_Company = FK_Company;
    }

    public String getAccessRowID() {
        return AccessRowID;
    }

    public void setAccessRowID(String accessRowID) {
        AccessRowID = accessRowID;
    }

    public SalaryItem() {

    }

    public SalaryItem(String templateName, String FK_Company, String accessRowID) {

        TemplateName = templateName;
        this.FK_Company = FK_Company;
        AccessRowID = accessRowID;
    }
}
