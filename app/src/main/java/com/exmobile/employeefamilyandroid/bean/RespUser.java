package com.exmobile.employeefamilyandroid.bean;

import java.util.ArrayList;

/**
 * Created by zcc on 2016/5/9 15:58.
 */
public class RespUser {



    private int Code;
    private String Message;
    private ArrayList<User> Result;

    public RespUser() {
    }

    public RespUser(int code, String message, ArrayList<User> result) {
        Code = code;
        Message = message;
        Result = result;
    }

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

    public ArrayList<User> getResult() {
        return Result;
    }

    public void setResult(ArrayList<User> result) {
        Result = result;
    }
}
