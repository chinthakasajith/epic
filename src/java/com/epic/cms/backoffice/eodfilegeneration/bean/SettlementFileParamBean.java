/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.eodfilegeneration.bean;

/**
 *
 * @author chanuka
 */
public class SettlementFileParamBean {

    private String fileId = null;
    private String fileHeader = null;
    private String batchHeader = null;
    private String txnHeader = null;
    private String fileExtention = null;
    private String fileNamePrefix = null;
    private String batchTailer = null;
    private String fileTailer = null;

    public String getBatchTailer() {
        return batchTailer;
    }

    public void setBatchTailer(String batchTailer) {
        this.batchTailer = batchTailer;
    }

    public String getFileTailer() {
        return fileTailer;
    }

    public void setFileTailer(String fileTailer) {
        this.fileTailer = fileTailer;
    }

    
    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getBatchHeader() {
        return batchHeader;
    }

    public void setBatchHeader(String batchHeader) {
        this.batchHeader = batchHeader;
    }

    public String getFileExtention() {
        return fileExtention;
    }

    public void setFileExtention(String fileExtention) {
        this.fileExtention = fileExtention;
    }

    public String getFileHeader() {
        return fileHeader;
    }

    public void setFileHeader(String fileHeader) {
        this.fileHeader = fileHeader;
    }

    public String getFileNamePrefix() {
        return fileNamePrefix;
    }

    public void setFileNamePrefix(String fileNamePrefix) {
        this.fileNamePrefix = fileNamePrefix;
    }

    public String getTxnHeader() {
        return txnHeader;
    }

    public void setTxnHeader(String txnHeader) {
        this.txnHeader = txnHeader;
    }
}
