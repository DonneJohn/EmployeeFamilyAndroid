package com.exmobile.employeefamilyandroid.bean;

import com.exmobile.mvpbase.domain.Entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/10.
 */
public class Vote extends Entity{
    private String RowID;//投票题目的RowID，注意区分
    private String VoteTitle;
    private String VoteShortText;
    private String IfVoted;  //是否已投票 1.已投票 0.未投票  不允许多次投票
    private String CreateTime;
    private String VoteCategory;

    private ArrayList<VoteItem> VoteItemList;

    public String getRowID() {
        return RowID;
    }

    public void setRowID(String rowID) {
        RowID = rowID;
    }

    public String getVoteTitle() {
        return VoteTitle;
    }

    public void setVoteTitle(String voteTitle) {
        VoteTitle = voteTitle;
    }

    public String getVoteShortText() {
        return VoteShortText;
    }

    public void setVoteShortText(String voteShortText) {
        VoteShortText = voteShortText;
    }

    public ArrayList<VoteItem> getVoteItemList() {
        return VoteItemList;
    }

    public void setVoteItemList(ArrayList<VoteItem> voteItemList) {
        VoteItemList = voteItemList;
    }

    public String getIfVoted() {
        return IfVoted;
    }

    public void setIfVoted(String ifVoted) {
        IfVoted = ifVoted;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getVoteCategory() {
        return VoteCategory;
    }

    public void setVoteCategory(String voteCategory) {
        VoteCategory = voteCategory;
    }
}
