package com.exmobile.employeefamilyandroid.bean;

import java.util.ArrayList;

/**
 * Created by 张聪聪 on 2016/5/26.
 * Email：2353410167@qq.com
 */
public class RespSalaryYear {

    private int Code;
    private String Message;
    private ArrayList<SalaryYear> Result;

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

    public ArrayList<SalaryYear> getResult() {
        return Result;
    }

    public void setResult(ArrayList<SalaryYear> result) {
        Result = result;
    }

    public RespSalaryYear() {

    }

    public RespSalaryYear(int code, String message, ArrayList<SalaryYear> result) {

        Code = code;
        Message = message;
        Result = result;
    }
}
