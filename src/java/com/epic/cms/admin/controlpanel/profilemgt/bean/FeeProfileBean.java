/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.profilemgt.bean;

import java.util.Date;

/**
 * this class use for wrote all the bean that relate to fee profile management 
 * @author nisansala
 */
public class FeeProfileBean {

    private String feeProCode;
    private String feeProDes;
    private String feeCategory;
    private String effectiveDate;
    private String staus;
    private String stausDes;
    
    private Date lastUpdateDate;
    private Date createDate;
    private String lastUpdateUser;
    
    
    private String currencyCode;
    private String joinFee;
    private String annualFee;
    private String renewalFee;
    private String replaceFee;
    private String latePayFee;
    private String legalFee;
    private String retunChgeFee;
    private String rejectAutoFee;
    private String limitExceedFee;
    private String tempLimitFee;
    private String perLimitIncreaseFee;
    private String cardTypeFee;
    private String statementCopyFee;    
    private String currencyDes;

    public String getAnnualFee() {
        return annualFee;
    }

    public void setAnnualFee(String annualFee) {
        this.annualFee = annualFee;
    }

    public String getCardTypeFee() {
        return cardTypeFee;
    }

    public void setCardTypeFee(String cardTypeFee) {
        this.cardTypeFee = cardTypeFee;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyDes() {
        return currencyDes;
    }

    public void setCurrencyDes(String currencyDes) {
        this.currencyDes = currencyDes;
    }

    public String getJoinFee() {
        return joinFee;
    }

    public void setJoinFee(String joinFee) {
        this.joinFee = joinFee;
    }

    public String getLatePayFee() {
        return latePayFee;
    }

    public void setLatePayFee(String latePayFee) {
        this.latePayFee = latePayFee;
    }

    public String getLegalFee() {
        return legalFee;
    }

    public void setLegalFee(String legalFee) {
        this.legalFee = legalFee;
    }

    public String getLimitExceedFee() {
        return limitExceedFee;
    }

    public void setLimitExceedFee(String limitExceedFee) {
        this.limitExceedFee = limitExceedFee;
    }

    public String getPerLimitIncreaseFee() {
        return perLimitIncreaseFee;
    }

    public void setPerLimitIncreaseFee(String perLimitIncreaseFee) {
        this.perLimitIncreaseFee = perLimitIncreaseFee;
    }

    public String getRejectAutoFee() {
        return rejectAutoFee;
    }

    public void setRejectAutoFee(String rejectAutoFee) {
        this.rejectAutoFee = rejectAutoFee;
    }

    public String getRenewalFee() {
        return renewalFee;
    }

    public void setRenewalFee(String renewalFee) {
        this.renewalFee = renewalFee;
    }

    public String getReplaceFee() {
        return replaceFee;
    }

    public void setReplaceFee(String replaceFee) {
        this.replaceFee = replaceFee;
    }

    public String getRetunChgeFee() {
        return retunChgeFee;
    }

    public void setRetunChgeFee(String retunChgeFee) {
        this.retunChgeFee = retunChgeFee;
    }

    public String getStatementCopyFee() {
        return statementCopyFee;
    }

    public void setStatementCopyFee(String statementCopyFee) {
        this.statementCopyFee = statementCopyFee;
    }

    public String getTempLimitFee() {
        return tempLimitFee;
    }

    public void setTempLimitFee(String tempLimitFee) {
        this.tempLimitFee = tempLimitFee;
    }
    
    

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getFeeCategory() {
        return feeCategory;
    }

    public void setFeeCategory(String feeCategory) {
        this.feeCategory = feeCategory;
    }

    public String getFeeProCode() {
        return feeProCode;
    }

    public void setFeeProCode(String feeProCode) {
        this.feeProCode = feeProCode;
    }

    public String getFeeProDes() {
        return feeProDes;
    }

    public void setFeeProDes(String feeProDes) {
        this.feeProDes = feeProDes;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public String getStaus() {
        return staus;
    }

    public void setStaus(String staus) {
        this.staus = staus;
    }

    public String getStausDes() {
        return stausDes;
    }

    public void setStausDes(String stausDes) {
        this.stausDes = stausDes;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
    
    

    
}
