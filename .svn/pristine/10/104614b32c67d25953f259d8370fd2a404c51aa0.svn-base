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
import com.epic.cms.callcenter.card.persistance.CardActivationPersistance;
import com.epic.cms.cpmm.requestconfirmation.bean.RequestConfirmationBean;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author nalin
 */
public class CardActivationManager {

    private Connection CMSCon;
    private Connection CMSOnline;
    private CardBean cardBean = null;
    private CardActivationPersistance cardPerr = null;
    private SystemAuditManager sysAuditManager = null;
    private CallCenterMgtManager callCenterManager;

    public CardBean getCardDetailsToActivation(String cardNumber) throws Exception {

        try {
            cardBean = new CardBean();
            cardPerr = new CardActivationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            cardBean = cardPerr.getCardDetailsToActivation(CMSCon, cardNumber);

            CMSCon.commit();

        } catch (SQLException ex) {

            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

        return cardBean;
    }

    public RequestConfirmationBean getCardBlockDetails(RequestConfirmationBean newCardBean) throws Exception {
        RequestConfirmationBean cardBean = null;
        try {
//            
            cardBean = new RequestConfirmationBean();
            cardPerr = new CardActivationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            cardBean = cardPerr.getCardBlockDetails(CMSCon, newCardBean);

            CMSCon.commit();

        } catch (SQLException ex) {

            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

        return cardBean;
    }

    public boolean blockCardActivate(RequestConfirmationBean cardBean, SystemAuditBean systemAuditBean) throws Exception {

        boolean success = false;
        boolean successOnline = false;

        try {

            cardPerr = new CardActivationPersistance();
            sysAuditManager = new SystemAuditManager();
            callCenterManager = new CallCenterMgtManager();
            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            success = cardPerr.blockCardActivate(CMSCon, cardBean);
            if (success) {

                cardPerr.deactivateCardBlockDetails(CMSCon, CMSOnline, cardBean);
            }
            if (cardBean.getCurrentStatus().equals(StatusVarList.ACTIVE_STATUS)) {
                cardBean.setNewStatusOnline("1");
            } else {
                cardBean.setNewStatusOnline("2");
            }
            if (success) {
                successOnline = cardPerr.cardActivateOnline(CMSOnline, cardBean);
            }

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
//            callCenterManager.insertCallHistory(callhistoryBean, CMSCon);

            CMSCon.commit();
            CMSOnline.commit();

        } catch (Exception ex) {

            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw e;
            } finally {
                DBConnection.dbConnectionClose(CMSCon);
                DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
            }
        }
        return successOnline;
    }

    public boolean cardActivate(RequestConfirmationBean cardBean, SystemAuditBean systemAuditBean) throws Exception {

        boolean success = false;
        boolean successOnline = false;

        try {

            cardPerr = new CardActivationPersistance();
            sysAuditManager = new SystemAuditManager();
            callCenterManager = new CallCenterMgtManager();
            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            success = cardPerr.cardActivate(CMSCon, cardBean);

            if (success) {
                cardBean.setNewStatusOnline("1");
                successOnline = cardPerr.cardActivateOnline(CMSOnline, cardBean);
            }
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
//            callCenterManager.insertCallHistory(callhistoryBean, CMSCon); //comment by badrika

            CMSCon.commit();
            CMSOnline.commit();

        } catch (Exception ex) {

            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw e;
            } finally {
                DBConnection.dbConnectionClose(CMSCon);
                DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
            }
        }
        return successOnline;
    }

    public boolean updateRequset(RequestConfirmationBean cardBean, String operation) throws Exception {

        boolean success = false;

        try {

            cardPerr = new CardActivationPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            success = cardPerr.requsetApprove(CMSCon, cardBean, operation);

            CMSCon.commit();

        } catch (Exception ex) {

            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw e;
            } finally {
                DBConnection.dbConnectionClose(CMSCon);
            }
        }
        return success;
    }

    public int requsetReject(CardBean cardBean, String operation) throws Exception {

        int success = -1;

        try {

            cardPerr = new CardActivationPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            success = cardPerr.requsetReject(CMSCon, cardBean, operation);

            CMSCon.commit();

        } catch (Exception ex) {

            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw e;
            } finally {
                DBConnection.dbConnectionClose(CMSCon);
            }
        }
        return success;
    }

}
