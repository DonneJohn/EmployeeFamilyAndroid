package com.exmobile.employeefamilyandroid.bean;

import com.exmobile.mvpbase.domain.Entity;

/**
 * Created by zcc on 2016/5/10 18:28.
 */
public class Notice extends Entity {

    private String RowID;
    private String CompanyNotice;
    private String CompanyNoticeTitle;
    private String NoticeShortText;
    private String NoticeTop;
    private String CreateTime;

    public String getRowID() {
        return RowID;
    }

    public void setRowID(String rowID) {
        RowID = rowID;
    }

    public String getCompanyNotice() {
        return CompanyNotice;
    }

    public void setCompanyNotice(String companyNotice) {
        CompanyNotice = companyNotice;
    }

    public String getCompanyNoticeTitle() {
        return CompanyNoticeTitle;
    }

    public void setCompanyNoticeTitle(String companyNoticeTitle) {
        CompanyNoticeTitle = companyNoticeTitle;
    }

    public String getNoticeShortText() {
        return NoticeShortText;
    }

    public void setNoticeShortText(String noticeShortText) {
        NoticeShortText = noticeShortText;
    }

    public String getNoticeTop() {
        return NoticeTop;
    }

    public void setNoticeTop(String noticeTop) {
        NoticeTop = noticeTop;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public Notice() {

    }

    public Notice(String rowID, String companyNotice, String companyNoticeTitle, String noticeShortText, String noticeTop, String createTime) {

        RowID = rowID;
        CompanyNotice = companyNotice;
        CompanyNoticeTitle = companyNoticeTitle;
        NoticeShortText = noticeShortText;
        NoticeTop = noticeTop;
        CreateTime = createTime;
    }
}
