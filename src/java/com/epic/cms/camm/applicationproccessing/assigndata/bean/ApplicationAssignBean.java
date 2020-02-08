/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.assigndata.bean;

import java.util.Date;

/**
 *
 * @author chanuka
 */
public class ApplicationAssignBean {

    private String applicationId;
    private String cardNumber;
    private String applicationDomain;
    private String applicationDomainDes;
    private String cardCategory;
    private String cardCategoryDes;
    private String identityType;
    private String identityNo;
    private String priorityLevel;
    private String referralEmpNo;
    private String referralBranchId;
    private String assignUser;
    private String assignStatus;
    private String verificationStatus;
    private String lastUpdatedUser;
    private Date LastUpdatedTime;
    private Date createdTime;
    private String priorityDescription;
    private String referralBranchName;
    private String assignStatusDescription;
    private String expirydate;
    private String cardCategoryCode;
    private String identityTypeName;

    public String getIdentityTypeName() {
        return identityTypeName;
    }

    public void setIdentityTypeName(String identityTypeName) {
        this.identityTypeName = identityTypeName;
    }

    public String getCardCategoryCode() {
        return cardCategoryCode;
    }

    public void setCardCategoryCode(String cardCategoryCode) {
        this.cardCategoryCode = cardCategoryCode;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }

    
   

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    
    public String getApplicationDomainDes() {
        return applicationDomainDes;
    }

    public void setApplicationDomainDes(String applicationDomainDes) {
        this.applicationDomainDes = applicationDomainDes;
    }

    
    public String getApplicationDomain() {
        return applicationDomain;
    }

    public void setApplicationDomain(String applicationDomain) {
        this.applicationDomain = applicationDomain;
    }

    
    public String getCardCategoryDes() {
        return cardCategoryDes;
    }

    public void setCardCategoryDes(String cardCategoryDes) {
        this.cardCategoryDes = cardCategoryDes;
    }

    
    public String getCardCategory() {
        return cardCategory;
    }

    public void setCardCategory(String cardCategory) {
        this.cardCategory = cardCategory;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public String getAssignStatusDescription() {
        return assignStatusDescription;
    }

    public void setAssignStatusDescription(String assignStatusDescription) {
        this.assignStatusDescription = assignStatusDescription;
    }

    public String getPriorityDescription() {
        return priorityDescription;
    }

    public void setPriorityDescription(String priorityDescription) {
        this.priorityDescription = priorityDescription;
    }

   
    public String getReferralBranchName() {
        return referralBranchName;
    }

    public void setReferralBranchName(String referralBranchName) {
        this.referralBranchName = referralBranchName;
    }

    public String getAssignStatus() {
        return assignStatus;
    }

    public void setAssignStatus(String assignStatus) {
        this.assignStatus = assignStatus;
    }

    public Date getLastUpdatedTime() {
        return LastUpdatedTime;
    }

    public void setLastUpdatedTime(Date LastUpdatedTime) {
        this.LastUpdatedTime = LastUpdatedTime;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getAssignUser() {
        return assignUser;
    }

    public void setAssignUser(String assignUser) {
        this.assignUser = assignUser;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
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

    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }

    public String getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(String priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getReferralBranchId() {
        return referralBranchId;
    }

    public void setReferralBranchId(String referralBranchId) {
        this.referralBranchId = referralBranchId;
    }

    public String getReferralEmpNo() {
        return referralEmpNo;
    }

    public void setReferralEmpNo(String referralEmpNo) {
        this.referralEmpNo = referralEmpNo;
    }
}
