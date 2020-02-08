/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.generalledgermgtmgt.bean;

/**
 *
 * @author badrika
 */
public class GeneralLedgerMgtBean {
    private String glAccNo;
    private String description;
    private String status;
    private String statusDes;
    private String lastUpdatedUser;
    private String createdTime;
    private String lastUpdatedTime;
    private String glAccType;
    private String glAccTypeDes;

    public String getGlAccTypeDes() {
        return glAccTypeDes;
    }

    public void setGlAccTypeDes(String glAccTypeDes) {
        this.glAccTypeDes = glAccTypeDes;
    }

    public String getGlAccType() {
        return glAccType;
    }

    public void setGlAccType(String glAccType) {
        this.glAccType = glAccType;
    }

    public String getStatusDes() {
        return statusDes;
    }

    public void setStatusDes(String statusDes) {
        this.statusDes = statusDes;
    }
    
    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGlAccNo() {
        return glAccNo;
    }

    public void setGlAccNo(String glAccNo) {
        this.glAccNo = glAccNo;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
}
