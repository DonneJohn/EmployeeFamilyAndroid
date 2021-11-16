package com.exmobile.employeefamilyandroid.bean;

/**
 * Created by 张聪聪 on 2016/6/8.
 * Email：2353410167@qq.com
 */
public class CultureBanner {

    private String RowID;
    private String FileName;
    private String FK_Company;
    private String Title;
    private String CreateTime;

    public CultureBanner(String rowID, String fileName, String FK_Company, String title, String createTime) {
        RowID = rowID;
        FileName = fileName;
        this.FK_Company = FK_Company;
        Title = title;
        CreateTime = createTime;
    }

    public CultureBanner() {

    }

    public String getRowID() {

        return RowID;
    }

    public void setRowID(String rowID) {
        RowID = rowID;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getFK_Company() {
        return FK_Company;
    }

    public void setFK_Company(String FK_Company) {
        this.FK_Company = FK_Company;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }
}
