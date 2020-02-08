/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.cpmm.cardembossing.bean;

/**
 *
 * @author nalin
 */
public class CardQualityMgtBean {

    private String cardNumber;
    private String cardType;
    private String cardProduct;
    private String cardDomain;
    private String nameOnCard;
    private String cardExpDate;
    private String customerId;
    private String lastUpdatedUser;
    private String remarks;

    public String getCardDomain() {
        return cardDomain;
    }

    public void setCardDomain(String cardDomain) {
        this.cardDomain = cardDomain;
    }   
    
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCardExpDate() {
        return cardExpDate;
    }

    public void setCardExpDate(String cardExpDate) {
        this.cardExpDate = cardExpDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardProduct() {
        return cardProduct;
    }

    public void setCardProduct(String cardProduct) {
        this.cardProduct = cardProduct;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
