package com.exmobile.employeefamilyandroid.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/10.
 */
public class RespCulture {
    private int Code;
    private String Message;
    private ArrayList<Culture> Result;

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

    public ArrayList<Culture> getResult() {
        return Result;
    }

    public void setResult(ArrayList<Culture> result) {
        Result = result;
    }
}
