/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.bean;

import java.util.Date;

/**
 *
 * @author nalin
 */
public class FeeBean {

    private String feeCode;
    private String feeDes;
    private String feeCategory;
    private String feeType;
    private String currency;
    private String currencyDes;
    private String crordr;
    private String status;
    private String statusDes;
    private String flatFee;
    private String percentage;
    private String option;
    private String minAmount;
    private String maxAmount;
    private String lastUpdateUser;
    private Date lastUpdateDate;
    private Date createDate;
    private String oldValue;
    private String cardNo;
    private String effectiveday;

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public String getCrordr() {
        return crordr;
    }

    public void setCrordr(String crordr) {
        this.crordr = crordr;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getFeeCategory() {
        return feeCategory;
    }

    public void setFeeCategory(String feeCategory) {
        this.feeCategory = feeCategory;
    }

    public String getFeeCode() {
        return feeCode;
    }

    public void setFeeCode(String feeCode) {
        this.feeCode = feeCode;
    }

    public String getFeeDes() {
        return feeDes;
    }

    public void setFeeDes(String feeDes) {
        this.feeDes = feeDes;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getFlatFee() {
        return flatFee;
    }

    public void setFlatFee(String flatFee) {
        this.flatFee = flatFee;
    }

    public String getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(String maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(String minAmount) {
        this.minAmount = minAmount;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDes() {
        return statusDes;
    }

    public void setStatusDes(String statusDes) {
        this.statusDes = statusDes;
    }

    public String getCurrencyDes() {
        return currencyDes;
    }

    public void setCurrencyDes(String currencyDes) {
        this.currencyDes = currencyDes;
    }

    public String getEffectiveday() {
        return effectiveday;
    }

    public void setEffectiveday(String effectiveday) {
        this.effectiveday = effectiveday;
    }
    
    
}
