/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.customertemplate.bean;

/**
 *
 * @author janaka_h
 */
public class TemplateCatagoryBean {
    
    private String catagoryCode;
    private String catagoryName;
    private String status;
    private String lastUpdatedUser;

    public String getCatagoryCode() {
        return catagoryCode;
    }

    public void setCatagoryCode(String catagoryCode) {
        this.catagoryCode = catagoryCode;
    }

    public String getCatagoryName() {
        return catagoryName;
    }

    public void setCatagoryName(String catagoryName) {
        this.catagoryName = catagoryName;
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
