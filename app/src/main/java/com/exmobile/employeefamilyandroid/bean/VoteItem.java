package com.exmobile.employeefamilyandroid.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/10.
 */
public class VoteItem implements Serializable {
    private String RowID;//选项的RowID
    private String VoteItemText;
    private String VoteItemTitle;

    public String getRowID() {
        return RowID;
    }

    public void setRowID(String rowID) {
        RowID = rowID;
    }

    public String getVoteItemText() {
        return VoteItemText;
    }

    public void setVoteItemText(String voteItemText) {
        VoteItemText = voteItemText;
    }

    public String getVoteItemTitle() {
        return VoteItemTitle;
    }

    public void setVoteItemTitle(String voteItemTitle) {
        VoteItemTitle = voteItemTitle;
    }
}
