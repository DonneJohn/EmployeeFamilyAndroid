package com.exmobile.employeefamilyandroid.bean;

/**
 * Created by 张聪聪 on 2016/6/30.
 * Email：2353410167@qq.com
 */
public class JpushBean {

    private String NoticeID;

    public String getNoticeID() {
        return NoticeID;
    }

    public void setNoticeID(String noticeID) {
        NoticeID = noticeID;
    }

    public JpushBean() {

    }

    public JpushBean(String noticeID) {

        NoticeID = noticeID;
    }
}
