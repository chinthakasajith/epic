/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.capturedata.bean;

/**
 *
 * @author mahesh_m
 */
public class CardApplicationBean {
    private String applicationId;
    private String identificationType;
    private String identificationNumber;
    private String prioritylevelCode;
    private String referencialEmpNum;
    private String branchCode;
    private String creditScore;
    private String assignUser;
    private String assignStatus;
    private String lastUpdatedUser;
    private String lastUpdatedTime;
    private String createdTime;
    private String staffStatus;
    private String documentExist;
    private String appType;
    private String appTypeDes;
    //new by chinthaka
    private String cOfficerRecCrditLimt;
    private String sysRecCreditLimit;

    public String getSysRecCreditLimit() {
        return sysRecCreditLimit;
    }

    public void setSysRecCreditLimit(String sysRecCreditLimit) {
        this.sysRecCreditLimit = sysRecCreditLimit;
    }

    public String getcOfficerRecCrditLimt() {
        return cOfficerRecCrditLimt;
    }

    public void setcOfficerRecCrditLimt(String cOfficerRecCrditLimt) {
        this.cOfficerRecCrditLimt = cOfficerRecCrditLimt;
    }

    public String getcOfficerRecCardProduct() {
        return cOfficerRecCardProduct;
    }

    public void setcOfficerRecCardProduct(String cOfficerRecCardProduct) {
        this.cOfficerRecCardProduct = cOfficerRecCardProduct;
    }
    private String cOfficerRecCardProduct;

    private String cardCategoryCode;
    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getAppTypeDes() {
        return appTypeDes;
    }

    public void setAppTypeDes(String appTypeDes) {
        this.appTypeDes = appTypeDes;
    }

    public String getDocumentExist() {
        return documentExist;
    }

    public void setDocumentExist(String documentExist) {
        this.documentExist = documentExist;
    }

    public String getStaffStatus() {
        return staffStatus;
    }

    public void setStaffStatus(String staffStatus) {
        this.staffStatus = staffStatus;
    }

    
    public String getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(String creditScore) {
        this.creditScore = creditScore;
    }
    
    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getAssignStatus() {
        return assignStatus;
    }

    public void setAssignStatus(String assignStatus) {
        this.assignStatus = assignStatus;
    }

    public String getAssignUser() {
        return assignUser;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }
    
    public void setAssignUser(String assignUser) {
        this.assignUser = assignUser;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public String getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(String lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }

    public String getPrioritylevelCode() {
        return prioritylevelCode;
    }

    public void setPrioritylevelCode(String prioritylevelCode) {
        this.prioritylevelCode = prioritylevelCode;
    }

    public String getReferencialEmpNum() {
        return referencialEmpNum;
    }

    public void setReferencialEmpNum(String referencialEmpNum) {
        this.referencialEmpNum = referencialEmpNum;
    }

    public String getCardCategoryCode() {
        return cardCategoryCode;
    }

    public void setCardCategoryCode(String cardCategoryCode) {
        this.cardCategoryCode = cardCategoryCode;
    }
    
    
}
