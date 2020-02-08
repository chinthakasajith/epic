/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.documentverify.bean;

/**
 *
 * @author chanuka
 */
public class DebitUploadDocsBean {
    
    private String applicationId = null;
    private String isPrimaryNicUploaded = null;
    private String isPrimarySignatureUpload = null;
    private String isJointSignatureUpload = null;
    private String isJointNicUpload = null;
    private String primaryNicFileName = null;
    private String primarySignFileName = null;
    private String joinNicFileName = null;
    private String joinSignFileName = null;

    public String getJoinNicFileName() {
        return joinNicFileName;
    }

    public void setJoinNicFileName(String joinNicFileName) {
        this.joinNicFileName = joinNicFileName;
    }

    public String getJoinSignFileName() {
        return joinSignFileName;
    }

    public void setJoinSignFileName(String joinSignFileName) {
        this.joinSignFileName = joinSignFileName;
    }

    public String getPrimaryNicFileName() {
        return primaryNicFileName;
    }

    public void setPrimaryNicFileName(String primaryNicFileName) {
        this.primaryNicFileName = primaryNicFileName;
    }

    public String getPrimarySignFileName() {
        return primarySignFileName;
    }

    public void setPrimarySignFileName(String primarySignFileName) {
        this.primarySignFileName = primarySignFileName;
    }

    
    
    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getIsJointNicUpload() {
        return isJointNicUpload;
    }

    public void setIsJointNicUpload(String isJointNicUpload) {
        this.isJointNicUpload = isJointNicUpload;
    }

    public String getIsJointSignatureUpload() {
        return isJointSignatureUpload;
    }

    public void setIsJointSignatureUpload(String isJointSignatureUpload) {
        this.isJointSignatureUpload = isJointSignatureUpload;
    }

    public String getIsPrimaryNicUploaded() {
        return isPrimaryNicUploaded;
    }

    public void setIsPrimaryNicUploaded(String isPrimaryNicUploaded) {
        this.isPrimaryNicUploaded = isPrimaryNicUploaded;
    }

    public String getIsPrimarySignatureUpload() {
        return isPrimarySignatureUpload;
    }

    public void setIsPrimarySignatureUpload(String isPrimarySignatureUpload) {
        this.isPrimarySignatureUpload = isPrimarySignatureUpload;
    }

    
    
    
    
}
