/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.merchant.bean;

import java.sql.Date;

/**
 *
 * @author ruwan_e
 */
public class MerchantLocationReportBean {
    private String merchantId;
    private String merchantCustomerNo;
    private String merchantAccountNo;
    private String description;
    
    private String contactPersonTitle;
    private String contactPersonFirstName;
    private String contactPersonMiddleName;
    private String contactPersonLastName;
    private String contactPersonPosistion;
    
    private String address1;
    private String address2;
    private String address3;
    private String city;
    private String postalCode;
    private String country;
    private String telephoneNo;
    private String faxNo;
    private String email;
    
    private Date activationDate;
    private String status;
    private String riskProfile;
    private String feeProfile;
    private String commissionProfile;
    private String lastUpdatedUser;
    private String lastUpdatedTime;
    private String createdTime;
    
    private String statementCycle;
    private String paymentCycle;
    private String paymentMode;
    private String statementMaintenanceStatus;
    
    private String bankCode;
    private String branchName;
    private String accountNumber;
    private String accountType;
    private String accountName;
    private String currencyCode;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Date getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCommissionProfile() {
        return commissionProfile;
    }

    public void setCommissionProfile(String commissionProfile) {
        this.commissionProfile = commissionProfile;
    }

    public String getContactPersonFirstName() {
        return contactPersonFirstName;
    }

    public void setContactPersonFirstName(String contactPersonFirstName) {
        this.contactPersonFirstName = contactPersonFirstName;
    }

    public String getContactPersonLastName() {
        return contactPersonLastName;
    }

    public void setContactPersonLastName(String contactPersonLastName) {
        this.contactPersonLastName = contactPersonLastName;
    }

    public String getContactPersonMiddleName() {
        return contactPersonMiddleName;
    }

    public void setContactPersonMiddleName(String contactPersonMiddleName) {
        this.contactPersonMiddleName = contactPersonMiddleName;
    }

    public String getContactPersonPosistion() {
        return contactPersonPosistion;
    }

    public void setContactPersonPosistion(String contactPersonPosistion) {
        this.contactPersonPosistion = contactPersonPosistion;
    }

    public String getContactPersonTitle() {
        return contactPersonTitle;
    }

    public void setContactPersonTitle(String contactPersonTitle) {
        this.contactPersonTitle = contactPersonTitle;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public String getFeeProfile() {
        return feeProfile;
    }

    public void setFeeProfile(String feeProfile) {
        this.feeProfile = feeProfile;
    }

    public String getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(String lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }

    public String getMerchantAccountNo() {
        return merchantAccountNo;
    }

    public void setMerchantAccountNo(String merchantAccountNo) {
        this.merchantAccountNo = merchantAccountNo;
    }

    public String getMerchantCustomerNo() {
        return merchantCustomerNo;
    }

    public void setMerchantCustomerNo(String merchantCustomerNo) {
        this.merchantCustomerNo = merchantCustomerNo;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getPaymentCycle() {
        return paymentCycle;
    }

    public void setPaymentCycle(String paymentCycle) {
        this.paymentCycle = paymentCycle;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getRiskProfile() {
        return riskProfile;
    }

    public void setRiskProfile(String riskProfile) {
        this.riskProfile = riskProfile;
    }

    public String getStatementCycle() {
        return statementCycle;
    }

    public void setStatementCycle(String statementCycle) {
        this.statementCycle = statementCycle;
    }

    public String getStatementMaintenanceStatus() {
        return statementMaintenanceStatus;
    }

    public void setStatementMaintenanceStatus(String statementMaintenanceStatus) {
        this.statementMaintenanceStatus = statementMaintenanceStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTelephoneNo() {
        return telephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }
    
    
    
    
}
