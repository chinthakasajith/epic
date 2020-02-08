/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.numbergenaration.bean;

/**
 *
 * @author janaka_h
 */
public class CardAccountCustomerBean {
    private String customerId;
    private String accountNumber;
    private String cardNumber;
    private String cardType;
    private String primaryStatus;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getPrimaryStatus() {
        return primaryStatus;
    }

    public void setPrimaryStatus(String primaryStatus) {
        this.primaryStatus = primaryStatus;
    }
    
    
    
    
}
