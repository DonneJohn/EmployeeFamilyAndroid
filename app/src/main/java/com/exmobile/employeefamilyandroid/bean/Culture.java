package com.exmobile.employeefamilyandroid.bean;

import com.exmobile.mvpbase.domain.Entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/10.
 */
public class Culture extends Entity {
    private String RowID;
    private String CompanyCulture;//简介
    private String CompanyCultureTitle; //标题

    public String getRowID() {
        return RowID;
    }

    public void setRowID(String rowID) {
        RowID = rowID;
    }

    public String getCompanyCulture() {
        return CompanyCulture;
    }

    public void setCompanyCulture(String companyCulture) {
        CompanyCulture = companyCulture;
    }

    public String getCompanyCultureTitle() {
        return CompanyCultureTitle;
    }

    public void setCompanyCultureTitle(String companyCultureTitle) {
        CompanyCultureTitle = companyCultureTitle;
    }
}
