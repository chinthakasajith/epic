/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cms.camm.applicationproccessing.capturedata.bean;

/**
 *
 * @author prageeth_s
 */
public class AssetLiabilityTypeBean {
    
    private String typeCode;
    private String description;
    private String lastUpdateduser;


    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastUpdateduser() {
        return lastUpdateduser;
    }

    public void setLastUpdateduser(String lastUpdateduser) {
        this.lastUpdateduser = lastUpdateduser;
    }
    
    
}
