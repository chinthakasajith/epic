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
import com.epic.cms.callcenter.card.persistance.CardRequestPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *
 * @author nisansala
 */
public class CardRequestManager {

    private Connection CMSCon;
    private Connection onlineCMSCon;
    private CardBean card = null;
    private SystemAuditManager sysAuditManager;
    private CardRequestPersistance cdReqPer;
    private HashMap<String, String> cardCategory = null;
    private HashMap<String, String> blockReasons = null;
    private CallCenterMgtManager callCenterManager;

    public HashMap<String, String> getCardCategory() throws Exception {
        try {
            cdReqPer = new CardRequestPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardCategory = cdReqPer.getCardCategory(CMSCon);
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
        return cardCategory;
    }

    public int ReplaceCardOrReissuePIN(String reasonCode, SystemAuditBean systemAuditBean, CardBean card, CallHistoryBean callhistoryBean) throws Exception {
        int row;
        try {
            cdReqPer = new CardRequestPersistance();
            sysAuditManager = new SystemAuditManager();
            callCenterManager = new CallCenterMgtManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            row = cdReqPer.ReplaceCardOrReissuePIN(CMSCon, reasonCode, card, callhistoryBean.getCallLogId());
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

    public String checkEligibilityToRenew(String cardNo) throws Exception {
        String eligible;
        try {

            cdReqPer = new CardRequestPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            eligible = cdReqPer.checkEligibilityToRenew(CMSCon, cardNo);
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
        return eligible;
    }

    public CardBean getCardDetails(String cardNo) throws Exception {
        try {
            cdReqPer = new CardRequestPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            card = cdReqPer.getCardDetails(CMSCon, cardNo);


            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return card;
    }
}
