/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.assigndata.bean;

/**
 *
 * @author chanuka
 */
public class UserAssignApplicationBean {

    private String userName;
    private String assignedApps;
    private String pendingApps;

    public String getPendingApps() {
        return pendingApps;
    }

    public void setPendingApps(String pendingApps) {
        this.pendingApps = pendingApps;
    }

    public String getAssignedApps() {
        return assignedApps;
    }

    public void setAssignedApps(String assignedApps) {
        this.assignedApps = assignedApps;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    
}
