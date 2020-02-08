/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.numbergenaration.bean;

import java.util.Date;

/**
 *
 * @author janaka_h
 */
public class NumberGenarationDataBean {
    
    private String applicationId;
   private String nameOnCard;
   private String cardType;
   private String cardProduct;
   private String priorityLevel;
   private Date createTime;
   

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
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

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(String priorityLevel) {
        this.priorityLevel = priorityLevel;
    }
   
   
    
}
