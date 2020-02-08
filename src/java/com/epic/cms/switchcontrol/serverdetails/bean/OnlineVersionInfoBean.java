/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.serverdetails.bean;

/**
 *
 * @author mahesh_m
 */
public class OnlineVersionInfoBean {
    private String versionNo;
    private String buildModuleNo;
    private String tpsLevel;
    private String securityModuleNo;

    public String getBuildModuleNo() {
        return buildModuleNo;
    }

    public void setBuildModuleNo(String buildModuleNo) {
        this.buildModuleNo = buildModuleNo;
    }

    public String getSecurityModuleNo() {
        return securityModuleNo;
    }

    public void setSecurityModuleNo(String securityModuleNo) {
        this.securityModuleNo = securityModuleNo;
    }

    public String getTpsLevel() {
        return tpsLevel;
    }

    public void setTpsLevel(String tpsLevel) {
        this.tpsLevel = tpsLevel;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }
    
}
