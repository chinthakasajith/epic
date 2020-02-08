/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.bean;

/**
 * this class use to wrote store bankBrachbean
 * @author ayesh
 */
public class BankBranchBean {

    private String branchName;
    private String address1;
    private String address2;
    private String address3;
    private String description;
    private String contactPer;
    private String contactNo;
    private String bankCode;
    private String bankName;
    private String displayDigit;
    private String lastupdateuser;
    private String lastupdatetime;

    public String getDisplayDigit() {
        return displayDigit;
    }

    public void setDisplayDigit(String displayDigit) {
        this.displayDigit = displayDigit;
    }

    
    
    public String getLastupdatetime() {
        return lastupdatetime;
    }

    public void setLastupdatetime(String lastupdatetime) {
        this.lastupdatetime = lastupdatetime;
    }  
    
    /**
     * get bank code
     * @return String
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * set bank code
     * @param bankCode String
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    /**
     * get bank name 
     * @return String
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * set bank name 
     * @param bankName -String
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * get address1
     * @return String
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * set address1
     * @param address1 - String
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * get address2
     * @return String
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * set address2
     * @param address2 -String
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     * get address3
     * @return String
     */
    public String getAddress3() {
        return address3;
    }

    /**
     * set address3
     * @param address3 String
     */
    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    /**
     * get branch name
     * @return String
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * set branch name
     * @param branchName String
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    /**
     * get contact no
     * @return String
     */
    public String getContactNo() {
        return contactNo;
    }

    /**
     * set contact no
     * @param contactNo String
     */
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    /**
     * get Contact person
     * @return String
     */
    public String getContactPer() {
        return contactPer;
    }

    /**
     * set contact person
     * @param contactPer String
     */
    public void setContactPer(String contactPer) {
        this.contactPer = contactPer;
    }

    /**
     * get description
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * set description
     * @param description String
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * get last update user 
     * @return String
     */
    public String getLastupdateuser() {
        return lastupdateuser;
    }

    /**
     * set last update user
     * @param lastupdateuser String
     */
    public void setLastupdateuser(String lastupdateuser) {
        this.lastupdateuser = lastupdateuser;
    }
}
