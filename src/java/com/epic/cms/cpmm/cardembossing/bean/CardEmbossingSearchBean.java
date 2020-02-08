/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.cpmm.cardembossing.bean;

/**
 *
 * @author chanuka
 */
public class CardEmbossingSearchBean {

    private String cardNumber = null;
    private String batchId = null;
    private String identityType = null;
    private String identityNo = null;
    private String priorityLevel = null;
    private String cardtype = null;
    private String cardtypeDes = null;
    private String cardProduct = null;
    private String status = null;
    private String generatedUser = null;
    private String embossType = null;
    private String domainId = null;
    private String branchId = null;
    
    private String categoryKey;
    private String categoryValue;
    

    public String getCardtypeDes() {
        return cardtypeDes;
    }

    public void setCardtypeDes(String cardtypeDes) {
        this.cardtypeDes = cardtypeDes;
    }

    
    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    
    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    
    public String getEmbossType() {
        return embossType;
    }

    public void setEmbossType(String embossType) {
        this.embossType = embossType;
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

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public String getGeneratedUser() {
        return generatedUser;
    }

    public void setGeneratedUser(String generatedUser) {
        this.generatedUser = generatedUser;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public String getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(String priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(String categoryKey) {
        this.categoryKey = categoryKey;
    }

    public String getCategoryValue() {
        return categoryValue;
    }

    public void setCategoryValue(String categoryValue) {
        this.categoryValue = categoryValue;
    }
    
    
}
