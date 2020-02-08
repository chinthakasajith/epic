/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfirmation.bean;

/**
 *
 * @author mahesh_m
 */
public class ApplicationDetailsbean {
    private String applicationId;
    private String identificationType;
    private String identificationNumber;
    private String priorityLevel;
    private String employeeNumber;
    private String assigneUser;
    private String applicationStatus;
    private String applicationCategory;
    private String applicationCategoryDes;
    private String identityTypeName;
    private String creditOfficerRecCreditLimit;
    private String applicationStatusCode;

    public String getCreditOfficerRecCreditLimit() {
        return creditOfficerRecCreditLimit;
    }

    public void setCreditOfficerRecCreditLimit(String creditOfficerRecCreditLimit) {
        this.creditOfficerRecCreditLimit = creditOfficerRecCreditLimit;
    }



    public String getIdentityTypeName() {
        return identityTypeName;
    }

    public void setIdentityTypeName(String identityTypeName) {
        this.identityTypeName = identityTypeName;
    }

    public String getApplicationCategoryDes() {
        return applicationCategoryDes;
    }

    public void setApplicationCategoryDes(String applicationCategoryDes) {
        this.applicationCategoryDes = applicationCategoryDes;
    }

    public String getApplicationCategory() {
        return applicationCategory;
    }

    public void setApplicationCategory(String applicationCategory) {
        this.applicationCategory = applicationCategory;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getAssigneUser() {
        return assigneUser;
    }

    public void setAssigneUser(String assigneUser) {
        this.assigneUser = assigneUser;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public String getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(String priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getApplicationStatusCode() {
        return applicationStatusCode;
    }

    public void setApplicationStatusCode(String applicationStatusCode) {
        this.applicationStatusCode = applicationStatusCode;
    }
    
    
}
