/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.assigndata.bean;

/**
 *
 * @author chanuka
 */
public class SearchAssignAppBean {

    private String applicationId;
    private String applicationDomain;
    private String priorityLevel;
    private String fromDate;
    private String toDate;
    private String assignUser;
    private String cardCategory;
    
    private String idNumber;
    private String applicationType;
    private String currentUserRole;
    private Boolean isSearchForReviewAndConfirmation;

    public Boolean isIsSearchForReviewAndConfirmation() {
        return isSearchForReviewAndConfirmation;
    }

    public void setIsSearchForReviewAndConfirmation(Boolean isSearchForReviewAndConfirmation) {
        this.isSearchForReviewAndConfirmation = isSearchForReviewAndConfirmation;
    }



    public String getCurrentUserRole() {
        return currentUserRole;
    }

    public void setCurrentUserRole(String currentUserRole) {
        this.currentUserRole = currentUserRole;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }       

    public String getApplicationDomain() {
        return applicationDomain;
    }

    public void setApplicationDomain(String applicationDomain) {
        this.applicationDomain = applicationDomain;
    }

    
    public String getCardCategory() {
        return cardCategory;
    }

    public void setCardCategory(String cardCategory) {
        this.cardCategory = cardCategory;
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


    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(String priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
