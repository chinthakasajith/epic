/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.customertemplate.bean;

import java.util.Date;

/**
 *
 * @author chanuka
 */
public class CustomerTempBean {

    private String templateCode;
    private String templateName;
    private String valiedFrom;
    private String valiedTo;
    private String productCode;
    private String productType;
    private String currencyCode;
    private String currencyDes;
    private String totalCreditLimit;
    private String status;
    private String statusDes;
    private String LastUpdateduser;
    private String cusRiskProfile;
    private String cusRiskProfDes;
    private String txnProfCode;
    private String txnProfDes;
    private String staff;
    private Date lastUpdatedTime;
    private Date createdTime;

    public String getCusRiskProfDes() {
        return cusRiskProfDes;
    }

    public void setCusRiskProfDes(String cusRiskProfDes) {
        this.cusRiskProfDes = cusRiskProfDes;
    }

    public String getTxnProfCode() {
        return txnProfCode;
    }

    public void setTxnProfCode(String txnProfCode) {
        this.txnProfCode = txnProfCode;
    }

    public String getTxnProfDes() {
        return txnProfDes;
    }

    public void setTxnProfDes(String txnProfDes) {
        this.txnProfDes = txnProfDes;
    }

    public String getCurrencyDes() {
        return currencyDes;
    }

    public void setCurrencyDes(String currencyDes) {
        this.currencyDes = currencyDes;
    }

       
    public String getStatusDes() {
        return statusDes;
    }

    public void setStatusDes(String statusDes) {
        this.statusDes = statusDes;
    }

    
    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    
    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    
    public String getCusRiskProfile() {
        return cusRiskProfile;
    }

    public void setCusRiskProfile(String cusRiskProfile) {
        this.cusRiskProfile = cusRiskProfile;
    }

    
    public String getLastUpdateduser() {
        return LastUpdateduser;
    }

    public void setLastUpdateduser(String LastUpdateduser) {
        this.LastUpdateduser = LastUpdateduser;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTotalCreditLimit() {
        return totalCreditLimit;
    }

    public void setTotalCreditLimit(String totalCreditLimit) {
        this.totalCreditLimit = totalCreditLimit;
    }

    public String getValiedFrom() {
        return valiedFrom;
    }

    public void setValiedFrom(String valiedFrom) {
        this.valiedFrom = valiedFrom;
    }

    public String getValiedTo() {
        return valiedTo;
    }

    public void setValiedTo(String valiedTo) {
        this.valiedTo = valiedTo;
    }

    
}
