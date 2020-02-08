/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.keymailer.bean;

/**
 *
 * @author nalin
 */
public class KeyBean {
    
    private String tId;
    private String mId;
    private String tmk;
    private String tmkKVC;
    private String tpk;
    private String tpkKVC;
    private String ELMK_KEY;
    private String lastUpdatedUser;

    public String getELMK_KEY() {
        return ELMK_KEY;
    }

    public void setELMK_KEY(String ELMK_KEY) {
        this.ELMK_KEY = ELMK_KEY;
    }

    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public String getTmk() {
        return tmk;
    }

    public void setTmk(String tmk) {
        this.tmk = tmk;
    }

    public String getTmkKVC() {
        return tmkKVC;
    }

    public void setTmkKVC(String tmkKVC) {
        this.tmkKVC = tmkKVC;
    }

    public String getTpk() {
        return tpk;
    }

    public void setTpk(String tpk) {
        this.tpk = tpk;
    }

    public String getTpkKVC() {
        return tpkKVC;
    }

    public void setTpkKVC(String tpkKVC) {
        this.tpkKVC = tpkKVC;
    }
    
}
