/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.capturedata.bean;

/**
 *
 * @author janaka_h
 */
public class DocumentUploadBean {

    private String applicationId;
    private String verificationCategory;
    private String documentType;
    private String fileName;
    


    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getVerificationCategory() {
        return verificationCategory;
    }

    public void setVerificationCategory(String verificationCategory) {
        this.verificationCategory = verificationCategory;
    }
}
