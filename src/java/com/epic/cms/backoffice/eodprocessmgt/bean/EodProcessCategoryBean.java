/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.eodprocessmgt.bean;

import java.util.Date;

/**
 *
 * @author nalin
 */
public class EodProcessCategoryBean {
    
    private String categoryCode;
    private String description;
    private String dependancyStatus;
    private String status;
    private String StatusDes;
    
    private String lastUpdatedUser;
    private Date lastUpdatedTime;
    private Date createdTime;

    public String getStatusDes() {
        return StatusDes;
    }

    public void setStatusDes(String StatusDes) {
        this.StatusDes = StatusDes;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getDependancyStatus() {
        return dependancyStatus;
    }

    public void setDependancyStatus(String dependancyStatus) {
        this.dependancyStatus = dependancyStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
