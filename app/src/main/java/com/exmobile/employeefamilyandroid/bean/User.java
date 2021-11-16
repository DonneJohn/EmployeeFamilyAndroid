package com.exmobile.employeefamilyandroid.bean;

import java.util.ArrayList;

/**
 * Created by abu on 2016/5/9 15:59.
 */
public class User {

    private String RowID;
    private String StaffName;
    private String StaffID;
    private String StaffSex;
    private String FK_Company;
    private String FK_Department;
    private String FK_Role;
    private String FK_Title;
    private String StaffIcon;
    private String StaffMobile;
    private String StaffEmail;
    private String StaffQQ;
    private String StafBirthday;
    private String StaffPhone;
    private String StaffPhoneExt;
    private String StaffWeChatID;
    private String StaffPassword;
    private String CurrentIP;
    private String FirstTimeLogin;
    private String API_IP;
    private String TitleName;

    private String PasswordMofied;

    private ArrayList<CompanyInfoBean> CompanyInfo;

    public User(String rowID, String staffName, String staffID, String staffSex, String FK_Company, String FK_Department, String FK_Role, String FK_Title, String staffIcon, String staffMobile, String staffEmail, String staffQQ, String stafBirthday, String staffPhone, String staffPhoneExt, String staffWeChatID, String staffPassword, String currentIP, String firstTimeLogin, String API_IP, String titleName, String passwordMofied, ArrayList<CompanyInfoBean> companyInfo) {
        RowID = rowID;
        StaffName = staffName;
        StaffID = staffID;
        StaffSex = staffSex;
        this.FK_Company = FK_Company;
        this.FK_Department = FK_Department;
        this.FK_Role = FK_Role;
        this.FK_Title = FK_Title;
        StaffIcon = staffIcon;
        StaffMobile = staffMobile;
        StaffEmail = staffEmail;
        StaffQQ = staffQQ;
        StafBirthday = stafBirthday;
        StaffPhone = staffPhone;
        StaffPhoneExt = staffPhoneExt;
        StaffWeChatID = staffWeChatID;
        StaffPassword = staffPassword;
        CurrentIP = currentIP;
        FirstTimeLogin = firstTimeLogin;
        this.API_IP = API_IP;
        TitleName = titleName;
        PasswordMofied = passwordMofied;
        CompanyInfo = companyInfo;
    }



    public String getPasswordMofied() {
        return PasswordMofied;
    }

    public void setPasswordMofied(String passwordMofied) {
        PasswordMofied = passwordMofied;
    }

    public ArrayList<CompanyInfoBean> getCompanyInfo() {
        return CompanyInfo;
    }

    public void setCompanyInfo(ArrayList<CompanyInfoBean> companyInfo) {
        CompanyInfo = companyInfo;
    }

    public String getRowID() {
        return RowID;
    }

    public void setRowID(String rowID) {
        RowID = rowID;
    }

    public String getStaffName() {
        return StaffName;
    }

    public void setStaffName(String staffName) {
        StaffName = staffName;
    }

    public String getStaffID() {
        return StaffID;
    }

    public void setStaffID(String staffID) {
        StaffID = staffID;
    }

    public String getStaffSex() {
        return StaffSex;
    }

    public void setStaffSex(String staffSex) {
        StaffSex = staffSex;
    }

    public String getFK_Company() {
        return FK_Company;
    }

    public void setFK_Company(String FK_Company) {
        this.FK_Company = FK_Company;
    }

    public String getFK_Department() {
        return FK_Department;
    }

    public void setFK_Department(String FK_Department) {
        this.FK_Department = FK_Department;
    }

    public String getFK_Role() {
        return FK_Role;
    }

    public void setFK_Role(String FK_Role) {
        this.FK_Role = FK_Role;
    }

    public String getFK_Title() {
        return FK_Title;
    }

    public void setFK_Title(String FK_Title) {
        this.FK_Title = FK_Title;
    }

    public String getStaffIcon() {
        return StaffIcon;
    }

    public void setStaffIcon(String staffIcon) {
        StaffIcon = staffIcon;
    }

    public String getStaffMobile() {
        return StaffMobile;
    }

    public void setStaffMobile(String staffMobile) {
        StaffMobile = staffMobile;
    }

    public String getStaffEmail() {
        return StaffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        StaffEmail = staffEmail;
    }

    public String getStaffQQ() {
        return StaffQQ;
    }

    public void setStaffQQ(String staffQQ) {
        StaffQQ = staffQQ;
    }

    public String getStafBirthday() {
        return StafBirthday;
    }

    public void setStafBirthday(String stafBirthday) {
        StafBirthday = stafBirthday;
    }

    public String getStaffPhone() {
        return StaffPhone;
    }

    public void setStaffPhone(String staffPhone) {
        StaffPhone = staffPhone;
    }

    public String getStaffPhoneExt() {
        return StaffPhoneExt;
    }

    public void setStaffPhoneExt(String staffPhoneExt) {
        StaffPhoneExt = staffPhoneExt;
    }

    public String getStaffWeChatID() {
        return StaffWeChatID;
    }

    public void setStaffWeChatID(String staffWeChatID) {
        StaffWeChatID = staffWeChatID;
    }

    public String getStaffPassword() {
        return StaffPassword;
    }

    public void setStaffPassword(String staffPassword) {
        StaffPassword = staffPassword;
    }

    public String getCurrentIP() {
        return CurrentIP;
    }

    public void setCurrentIP(String currentIP) {
        CurrentIP = currentIP;
    }

    public String getFirstTimeLogin() {
        return FirstTimeLogin;
    }

    public void setFirstTimeLogin(String firstTimeLogin) {
        FirstTimeLogin = firstTimeLogin;
    }

    public String getAPI_IP() {
        return API_IP;
    }

    public void setAPI_IP(String API_IP) {
        this.API_IP = API_IP;
    }

    public String getTitleName() {
        return TitleName;
    }

    public void setTitleName(String titleName) {
        TitleName = titleName;
    }

    public User() {

    }


}
