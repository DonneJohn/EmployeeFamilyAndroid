package com.exmobile.employeefamilyandroid.bean;

import java.util.ArrayList;

/**
 * Created by 张聪聪 on 2016/5/25.
 * Email：2353410167@qq.com
 */
public class RespSalaryMonth {

    private int Code;
    private String Message;
    private ArrayList<SalaryMonth> Result;

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

    public ArrayList<SalaryMonth> getResult() {
        return Result;
    }

    public void setResult(ArrayList<SalaryMonth> result) {
        Result = result;
    }

    public RespSalaryMonth() {

    }

    public RespSalaryMonth(int code, String message, ArrayList<SalaryMonth> result) {

        Code = code;
        Message = message;
        Result = result;
    }
}
