package com.epic.cms.admin.controlpanel.systemconfigmgt.bean;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author jeevan
 */
public class TerminalBean {
    private String manufactureCode;
    private String description;
    private String lastUpdatedUser;
    private Date lastUpdatedDate;
    private String createDTime;

    public String getManufactureCode() {
        return manufactureCode;
    }

    public void setManufactureCode(String manufactureCode) {
        this.manufactureCode = manufactureCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }

   
    public String getCreateDTime() {
        return createDTime;
    }

    public void setCreateDTime(String createDTime) {
        this.createDTime = createDTime;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
    
    
}
