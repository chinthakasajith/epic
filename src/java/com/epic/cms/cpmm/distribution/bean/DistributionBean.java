/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.cpmm.distribution.bean;

/**
 *
 * @author nalin
 */
public class DistributionBean {
    
    private String cardNumber;
    private String cardType;
    private String cardTypeDes;
    private String cardProduct;
    private String cardProductDes;
    private String cardCategory;
    private String cardCategoryDes;
    private String lastUpdatedUser;
    private String applicationId;

    public String getCardCategory() {
        return cardCategory;
    }

    public void setCardCategory(String cardCategory) {
        this.cardCategory = cardCategory;
    }

    public String getCardCategoryDes() {
        return cardCategoryDes;
    }

    public void setCardCategoryDes(String cardCategoryDes) {
        this.cardCategoryDes = cardCategoryDes;
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

    public String getCardProductDes() {
        return cardProductDes;
    }

    public void setCardProductDes(String cardProductDes) {
        this.cardProductDes = cardProductDes;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardTypeDes() {
        return cardTypeDes;
    }

    public void setCardTypeDes(String cardTypeDes) {
        this.cardTypeDes = cardTypeDes;
    }

    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }
    
    
}
