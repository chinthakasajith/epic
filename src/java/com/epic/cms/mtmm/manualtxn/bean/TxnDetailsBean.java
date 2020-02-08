/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.mtmm.manualtxn.bean;

/**
 *
 * @author nalin
 */
public class TxnDetailsBean {
    
    private String txnId;
    private String mti;
    private String responceMti;
    private String listnerType;
    private String channelType;
    private String payType;
    private String onOffStatus;
    private String serviceCode;
    private String cardNumber;
    private String expiryDate;
    private String txnCurrency;
    private String txnAmount;
    private String status;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getListnerType() {
        return listnerType;
    }

    public void setListnerType(String listnerType) {
        this.listnerType = listnerType;
    }

    public String getMti() {
        return mti;
    }

    public void setMti(String mti) {
        this.mti = mti;
    }

    public String getOnOffStatus() {
        return onOffStatus;
    }

    public void setOnOffStatus(String onOffStatus) {
        this.onOffStatus = onOffStatus;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getResponceMti() {
        return responceMti;
    }

    public void setResponceMti(String responceMti) {
        this.responceMti = responceMti;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(String txnAmount) {
        this.txnAmount = txnAmount;
    }

    public String getTxnCurrency() {
        return txnCurrency;
    }

    public void setTxnCurrency(String txnCurrency) {
        this.txnCurrency = txnCurrency;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }
    
}
