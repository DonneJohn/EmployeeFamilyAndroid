package com.exmobile.employeefamilyandroid.bean;

import java.util.ArrayList;

/**
 * Created by 张聪聪 on 2016/6/8.
 * Email：2353410167@qq.com
 */
public class RespCultureBanner {
    private int Code;
    private String Message;

    public RespCultureBanner() {
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

    public ArrayList<CultureBanner> getResult() {
        return Result;
    }

    public void setResult(ArrayList<CultureBanner> result) {
        Result = result;
    }

    public RespCultureBanner(int code, String message, ArrayList<CultureBanner> result) {

        Code = code;
        Message = message;
        Result = result;
    }

    private ArrayList<CultureBanner> Result;
}
