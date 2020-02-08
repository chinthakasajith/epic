/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.card.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.callcenter.callcentersearch.bean.CallHistoryBean;
import com.epic.cms.callcenter.callcentersearch.businesslogic.CallCenterMgtManager;
import com.epic.cms.callcenter.card.bean.CardBean;
import com.epic.cms.callcenter.card.persistance.CardClosePersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *
 * @author nisansala
 */
public class CardCloseManager {

    private Connection CMSCon;
    private Connection onlineCMSCon;
    private CardBean card = null;
    private SystemAuditManager sysAuditManager;
    private CardClosePersistance cdclsPer;
    private HashMap<String, String> cardCategory = null;
    private HashMap<String, String> blockReasons = null;
    private CallCenterMgtManager callCenterManager;

    public CardBean getCardDetails(String cardNo) throws Exception {
        try {
            cdclsPer = new CardClosePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            card = cdclsPer.getCardDetails(CMSCon, cardNo);


            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return card;
    }

    public int closeCard(String reasonCode, CardBean card, SystemAuditBean systemAuditBean, String logID, CallHistoryBean callhistoryBean) throws Exception {
        int row;
        try {
            cdclsPer = new CardClosePersistance();
            sysAuditManager = new SystemAuditManager();
            callCenterManager = new CallCenterMgtManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            row = cdclsPer.closeCard(CMSCon, reasonCode, card, logID);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            callCenterManager.insertCallHistory(callhistoryBean, CMSCon);
            CMSCon.commit();
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return row;
    }
}
