package com.epic.cms.camm.applicationproccessing.cooperate.bean;

/**
 *
 * @author jeevan
 */
public class DocumentUploadCorporateBean {
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

    public String getVerificationCategory() {
        return verificationCategory;
    }

    public void setVerificationCategory(String verificationCategory) {
        this.verificationCategory = verificationCategory;
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
    
    
}
