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
public class ApplicationHistoryBean {

    private String applicationId;
    private String applicationLevel;
    private String status;
    private String remarks;
    private String lastUpdatedUser;
    private Date lastUpdatedTime;

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationLevel() {
        return applicationLevel;
    }

    public void setApplicationLevel(String applicationLevel) {
        this.applicationLevel = applicationLevel;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    

}
