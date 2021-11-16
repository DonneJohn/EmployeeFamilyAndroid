package com.exmobile.employeefamilyandroid.bean;

import java.util.ArrayList;

/**
 * Created by 张聪聪 on 2016/5/26.
 * Email：2353410167@qq.com
 */
public class RespSalaryDetail {

    private int Code;
    private String Message;
    private ArrayList<SalaryDetail> Result;

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

    public ArrayList<SalaryDetail> getResult() {
        return Result;
    }

    public void setResult(ArrayList<SalaryDetail> result) {
        Result = result;
    }

    public RespSalaryDetail() {

    }

    public RespSalaryDetail(int code, String message, ArrayList<SalaryDetail> result) {

        Code = code;
        Message = message;
        Result = result;
    }
}
