/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.customertemplate.bean;

/**
 *
 * @author janaka_h
 */
public class TemplateBean {
    
    
    private String TemplateCatagoryCode;
    private String TemplateCode;
    private String Description;
    private String status;
    private String lastUpdatedUser;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getTemplateCatagoryCode() {
        return TemplateCatagoryCode;
    }

    public void setTemplateCatagoryCode(String TemplateCatagoryCode) {
        this.TemplateCatagoryCode = TemplateCatagoryCode;
    }

    public String getTemplateCode() {
        return TemplateCode;
    }

    public void setTemplateCode(String TemplateCode) {
        this.TemplateCode = TemplateCode;
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
