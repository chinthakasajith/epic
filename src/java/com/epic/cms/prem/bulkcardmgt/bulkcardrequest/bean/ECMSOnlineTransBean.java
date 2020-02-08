/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean;

/**
 *
 * @author sajith_g
 */
public class ECMSOnlineTransBean {
    
    private String cardNumber;
    private String txnId;
    private String rrn;
    private String txnCurrency;
    private String currenctexponent;
    private String formattedTxnAmount;
    private String txnDate;
    private String txnTime;
    private String accepterName;
    private String subType;
    private String txnAmount;
    private String statusCode;
    private String statusDes;
    private String toAmount;
    private String fromAmount;
    private String fromDate;
    private String txnCurrencyCode;

    public String getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(String txnDate) {
        this.txnDate = txnDate;
    }

    public String getTxnTime() {
        return txnTime;
    }

    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }

    public String getAccepterName() {
        return accepterName;
    }

    public void setAccepterName(String accepterName) {
        this.accepterName = accepterName;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(String txnAmount) {
        this.txnAmount = txnAmount;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDes() {
        return statusDes;
    }

    public void setStatusDes(String statusDes) {
        this.statusDes = statusDes;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getToAmount() {
        return toAmount;
    }

    public void setToAmount(String toAmount) {
        this.toAmount = toAmount;
    }

    public String getFromAmount() {
        return fromAmount;
    }

    public void setFromAmount(String fromAmount) {
        this.fromAmount = fromAmount;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getRrn() {
        return rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public String getTxnCurrency() {
        return txnCurrency;
    }

    public void setTxnCurrency(String txnCurrency) {
        this.txnCurrency = txnCurrency;
    }

    public String getCurrenctexponent() {
        return currenctexponent;
    }

    public void setCurrenctexponent(String currenctexponent) {
        this.currenctexponent = currenctexponent;
    }

    public String getFormattedTxnAmount() {
        return formattedTxnAmount;
    }

    public void setFormattedTxnAmount(String formattedTxnAmount) {
        this.formattedTxnAmount = formattedTxnAmount;
    }

    public String getTxnCurrencyCode() {
        return txnCurrencyCode;
    }

    public void setTxnCurrencyCode(String txnCurrencyCode) {
        this.txnCurrencyCode = txnCurrencyCode;
    }
    
    
}
