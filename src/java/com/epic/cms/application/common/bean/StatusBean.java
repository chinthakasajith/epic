/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.application.common.bean;

/**
 *
 * @author chanuka
 */
public class StatusBean {

    private String statusCode;
    private String description;
    private String statusCategoryCode;
    private int sortKey;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSortKey() {
        return sortKey;
    }

    public void setSortKey(int sortKey) {
        this.sortKey = sortKey;
    }

    public String getStatusCategoryCode() {
        return statusCategoryCode;
    }

    public void setStatusCategoryCode(String statusCategoryCode) {
        this.statusCategoryCode = statusCategoryCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
    
    
    
}
