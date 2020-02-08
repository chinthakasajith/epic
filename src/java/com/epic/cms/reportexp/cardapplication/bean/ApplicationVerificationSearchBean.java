/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.bean;

/**
 *
 * @author asela
 */
public class ApplicationVerificationSearchBean {
    
    private String applicationId; 
    private String branchCode;
    private String branchDescription;
    private String priorityLevelDes;
    private String verificationStatusDes;
    private String priorityLevel;
    private String domain;
    private String domainDes;
    private String nic;
    private String passport;
    private String drivingLicence;
    private String verificationStatus;
    private String fromDate;
    private String toDate;
    private String idType;
    private String idNumber;
    
    public String getBranchDescription() {
        return branchDescription;
    }

    public void setBranchDescription(String branchDescription) {
        this.branchDescription = branchDescription;
    }

    public String getPriorityLevelDes() {
        return priorityLevelDes;
    }

    public void setPriorityLevelDes(String priorityLevelDes) {
        this.priorityLevelDes = priorityLevelDes;
    }

    public String getVerificationStatusDes() {
        return verificationStatusDes;
    }

    public void setVerificationStatusDes(String verificationStatusDes) {
        this.verificationStatusDes = verificationStatusDes;
    }


    public String getApplicationId() {
        return applicationId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public String getDrivingLicence() {
        return drivingLicence;
    }

    public void setDrivingLicence(String drivingLicence) {
        this.drivingLicence = drivingLicence;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(String priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getDomainDes() {
        return domainDes;
    }

    public void setDomainDes(String domainDes) {
        this.domainDes = domainDes;
    }
    
    
}
