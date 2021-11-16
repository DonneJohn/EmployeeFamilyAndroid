package com.exmobile.employeefamilyandroid.bean;

import java.util.ArrayList;

/**
 * Created by 张聪聪 on 2016/7/20.
 * Email：2353410167@qq.com
 */
public class RespVersion {

    private int Code;
    private String Message;
    private ArrayList<VersionBean> Result;

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<VersionBean> getResult() {
        return Result;
    }

    public void setResult(ArrayList<VersionBean> result) {
        Result = result;
    }
}
