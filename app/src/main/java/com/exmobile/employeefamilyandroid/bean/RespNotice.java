package com.exmobile.employeefamilyandroid.bean;

import java.util.ArrayList;

/**
 * Created by zcc on 2016/5/10 18:28.
 */
public class RespNotice {

    private int Code;
    private String Message;
    private ArrayList<Notice> Result;

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

    public ArrayList<Notice> getResult() {
        return Result;
    }

    public void setResult(ArrayList<Notice> result) {
        Result = result;
    }

    public RespNotice(int code, String message, ArrayList<Notice> result) {

        Code = code;
        Message = message;
        Result = result;
    }

    public RespNotice() {

    }
}
