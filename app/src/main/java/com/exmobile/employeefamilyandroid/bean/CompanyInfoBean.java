package com.exmobile.employeefamilyandroid.bean;

/**
 * Created by 张聪聪 on 2016/5/24.
 * Email：2353410167@qq.com
 */
public class CompanyInfoBean {

    private String RowID;
    private String LogoName;
    private String CompanyName;
    private int CompanyColor;
    private String CompanyNeedIDCode;

    public String getRowID() {
        return RowID;
    }

    public void setRowID(String rowID) {
        RowID = rowID;
    }

    public String getLogoName() {
        return LogoName;
    }

    public void setLogoName(String logoName) {
        LogoName = logoName;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public int getCompanyColor() {
        return CompanyColor;
    }

    public void setCompanyColor(int companyColor) {
        CompanyColor = companyColor;
    }

    public String getCompanyNeedIDCode() {
        return CompanyNeedIDCode;
    }

    public void setCompanyNeedIDCode(String companyNeedIDCode) {
        CompanyNeedIDCode = companyNeedIDCode;
    }

    public CompanyInfoBean() {
    }

    public CompanyInfoBean(String rowID, String logoName, String companyName, int companyColor, String companyNeedIDCode) {

        RowID = rowID;
        LogoName = logoName;
        CompanyName = companyName;
        CompanyColor = companyColor;
        CompanyNeedIDCode = companyNeedIDCode;
    }
}
