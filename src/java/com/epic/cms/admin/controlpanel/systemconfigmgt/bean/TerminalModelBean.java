package com.epic.cms.admin.controlpanel.systemconfigmgt.bean;

import java.util.Date;

/**
 *
 * @author jeevan
 */
public class TerminalModelBean {
    private String modelCode;
    private String modelDesc;
    private String description; // manufacture description
    private String status;
    private String statusDes;
    private String manufactureCode;
    private String lastUpdatedUser;
    private Date lastUpdatedTime;
    private String createdTime;

    
    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDes() {
        return statusDes;
    }

    public void setStatusDes(String statusDes) {
        this.statusDes = statusDes;
    }

    public String getManufactureCode() {
        return manufactureCode;
    }

    public void setManufactureCode(String manufactureCode) {
        this.manufactureCode = manufactureCode;
    }

    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }
    
    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getModelDesc() {
        return modelDesc;
    }

    public void setModelDesc(String modelDesc) {
        this.modelDesc = modelDesc;
    }
    
   
}
