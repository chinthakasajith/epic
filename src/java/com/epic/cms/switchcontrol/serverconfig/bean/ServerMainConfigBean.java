/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.serverconfig.bean;

import java.util.Date;

/**
 * this bean class is relevant to the ECMS_ONLINE_CONFIG table
 * @author nisansala
 */
public class ServerMainConfigBean {
    private String operateSystemType;
    private String runningMode;
    private String permanentRunningPort;
    private String terminatedRunningPort;
    private String noOfConnections;
    private String initialVector;
    private String reqBufSize;
    private String resBufSize;
    private String alertStatus;
    private String failStatus;
    private String logLevel;
    private String logFilePrefix;
    private String logAutoBackupStatus;
    private String debugStatus;
    private String tempConnectTimeOut;
    private String maxPoolSize;
    private String minPoolSize;
    private String maxQueueSize;
    private String poolBackLog;
    private String maxPINCount;
    private String monitorIP;
    private String monitorPort;
    private String monitorTimeOut;
    private String monitorStatus;
    private String backupPeriod;
    private String backupPath;
    private String hsmType;
    private String pvi;
    private String pinbFormat;
    private String emvVerifyMethod;
    private String enrcMode;
    private String channelId;
    private String channelStatus;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getEmvVerifyMethod() {
        return emvVerifyMethod;
    }

    public void setEmvVerifyMethod(String emvVerifyMethod) {
        this.emvVerifyMethod = emvVerifyMethod;
    }

    public String getEnrcMode() {
        return enrcMode;
    }

    public void setEnrcMode(String enrcMode) {
        this.enrcMode = enrcMode;
    }

    public String getPinbFormat() {
        return pinbFormat;
    }

    public void setPinbFormat(String pinbFormat) {
        this.pinbFormat = pinbFormat;
    }
    
    

    public String getPvi() {
        return pvi;
    }

    public void setPvi(String pvi) {
        this.pvi = pvi;
    }    

    public String getHsmType() {
        return hsmType;
    }

    public void setHsmType(String hsmType) {
        this.hsmType = hsmType;
    }
    
    private String lastUpdatedUser;
    private Date lastUpdatedTime;
    private Date createdTime;

    public String getAlertStatus() {
        return alertStatus;
    }

    public void setAlertStatus(String alertStatus) {
        this.alertStatus = alertStatus;
    }

    public String getBackupPath() {
        return backupPath;
    }

    public void setBackupPath(String backupPath) {
        this.backupPath = backupPath;
    }

    public String getBackupPeriod() {
        return backupPeriod;
    }

    public void setBackupPeriod(String backupPeriod) {
        this.backupPeriod = backupPeriod;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getDebugStatus() {
        return debugStatus;
    }

    public void setDebugStatus(String debugStatus) {
        this.debugStatus = debugStatus;
    }

    public String getFailStatus() {
        return failStatus;
    }

    public void setFailStatus(String failStatus) {
        this.failStatus = failStatus;
    }

    public String getInitialVector() {
        return initialVector;
    }

    public void setInitialVector(String initialVector) {
        this.initialVector = initialVector;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }

    public String getLogAutoBackupStatus() {
        return logAutoBackupStatus;
    }

    public void setLogAutoBackupStatus(String logAutoBackupStatus) {
        this.logAutoBackupStatus = logAutoBackupStatus;
    }

    public String getLogFilePrefix() {
        return logFilePrefix;
    }

    public void setLogFilePrefix(String logFilePrefix) {
        this.logFilePrefix = logFilePrefix;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getMaxPINCount() {
        return maxPINCount;
    }

    public void setMaxPINCount(String maxPINCount) {
        this.maxPINCount = maxPINCount;
    }

    public String getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(String maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public String getMaxQueueSize() {
        return maxQueueSize;
    }

    public void setMaxQueueSize(String maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
    }

    public String getMinPoolSize() {
        return minPoolSize;
    }

    public void setMinPoolSize(String minPoolSize) {
        this.minPoolSize = minPoolSize;
    }

    public String getMonitorIP() {
        return monitorIP;
    }

    public void setMonitorIP(String monitorIP) {
        this.monitorIP = monitorIP;
    }

    public String getMonitorPort() {
        return monitorPort;
    }

    public void setMonitorPort(String monitorPort) {
        this.monitorPort = monitorPort;
    }

    public String getMonitorStatus() {
        return monitorStatus;
    }

    public void setMonitorStatus(String monitorStatus) {
        this.monitorStatus = monitorStatus;
    }

    public String getMonitorTimeOut() {
        return monitorTimeOut;
    }

    public void setMonitorTimeOut(String monitorTimeOut) {
        this.monitorTimeOut = monitorTimeOut;
    }

    public String getNoOfConnections() {
        return noOfConnections;
    }

    public void setNoOfConnections(String noOfConnections) {
        this.noOfConnections = noOfConnections;
    }

    public String getOperateSystemType() {
        return operateSystemType;
    }

    public void setOperateSystemType(String operateSystemType) {
        this.operateSystemType = operateSystemType;
    }

    public String getPermanentRunningPort() {
        return permanentRunningPort;
    }

    public void setPermanentRunningPort(String permanentRunningPort) {
        this.permanentRunningPort = permanentRunningPort;
    }

    public String getPoolBackLog() {
        return poolBackLog;
    }

    public void setPoolBackLog(String poolBackLog) {
        this.poolBackLog = poolBackLog;
    }

    public String getReqBufSize() {
        return reqBufSize;
    }

    public void setReqBufSize(String reqBufSize) {
        this.reqBufSize = reqBufSize;
    }

    public String getResBufSize() {
        return resBufSize;
    }

    public void setResBufSize(String resBufSize) {
        this.resBufSize = resBufSize;
    }

    public String getRunningMode() {
        return runningMode;
    }

    public void setRunningMode(String runningMode) {
        this.runningMode = runningMode;
    }

    public String getTempConnectTimeOut() {
        return tempConnectTimeOut;
    }

    public void setTempConnectTimeOut(String tempConnectTimeOut) {
        this.tempConnectTimeOut = tempConnectTimeOut;
    }

    public String getTerminatedRunningPort() {
        return terminatedRunningPort;
    }

    public void setTerminatedRunningPort(String terminatedRunningPort) {
        this.terminatedRunningPort = terminatedRunningPort;
    }

    public String getChannelStatus() {
        return channelStatus;
    }

    public void setChannelStatus(String channelStatus) {
        this.channelStatus = channelStatus;
    }
    
    
    
}
