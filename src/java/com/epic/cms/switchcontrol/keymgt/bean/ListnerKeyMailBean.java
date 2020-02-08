package com.epic.cms.switchcontrol.keymgt.bean;

/**
 *
 * @author jeevan
 */
public class ListnerKeyMailBean {
    private String listenerId;
    
    private String listnerTypeCode;
    private String listnerTypeDesc;
    private String listnerTypeStatus;
    
    private String comKeyId;
    private String comKeyDesc;
    private String comKeyType;
    private String zmk;
    private String zmk_kvc;
    
    private String lastUpdatedUser;

    public String getListnerTypeCode() {
        return listnerTypeCode;
    }

    public void setListnerTypeCode(String listnerTypeCode) {
        this.listnerTypeCode = listnerTypeCode;
    }
    
    public String getListnerTypeDesc() {
        return listnerTypeDesc;
    }

    public void setListnerTypeDesc(String listnerTypeDesc) {
        this.listnerTypeDesc = listnerTypeDesc;
    }

    public String getListnerTypeStatus() {
        return listnerTypeStatus;
    }

    public void setListnerTypeStatus(String listnerTypeStatus) {
        this.listnerTypeStatus = listnerTypeStatus;
    }

    public String getComKeyId() {
        return comKeyId;
    }

    public void setComKeyId(String comKeyId) {
        this.comKeyId = comKeyId;
    }

    public String getComKeyDesc() {
        return comKeyDesc;
    }

    public void setComKeyDesc(String comKeyDesc) {
        this.comKeyDesc = comKeyDesc;
    }

    public String getComKeyType() {
        return comKeyType;
    }

    public void setComKeyType(String comKeyType) {
        this.comKeyType = comKeyType;
    }

    public String getZmk() {
        return zmk;
    }

    public void setZmk(String zmk) {
        this.zmk = zmk;
    }

    public String getZmk_kvc() {
        return zmk_kvc;
    }

    public void setZmk_kvc(String zmk_kvc) {
        this.zmk_kvc = zmk_kvc;
    }

    public String getListenerId() {
        return listenerId;
    }

    public void setListenerId(String listenerId) {
        this.listenerId = listenerId;
    }

    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }
    
    
}
