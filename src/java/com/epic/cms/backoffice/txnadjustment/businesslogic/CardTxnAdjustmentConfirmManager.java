/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cms.backoffice.txnadjustment.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.backoffice.installementpayment.bean.EasyPaymentConfirmBean;
import com.epic.cms.backoffice.txnadjustment.bean.TransactionAdjustment;
import com.epic.cms.backoffice.txnadjustment.bean.TransactionAdjustmentParty;
import com.epic.cms.backoffice.txnadjustment.persistance.CardTxnAdjustmentConfirmPersistence;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Badrika
 */
public class CardTxnAdjustmentConfirmManager {
    private CardTxnAdjustmentConfirmPersistence perobj;
    private Connection CMSCon,CMSOnline;
    private List<TransactionAdjustment> requestList;
    private SystemAuditManager sysAuditManager;
    
    public List<TransactionAdjustment> searchRequests(TransactionAdjustmentParty party, TransactionAdjustment filledBean) throws SQLException, Exception {

        try {
            perobj = new CardTxnAdjustmentConfirmPersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            requestList = perobj.searchRequests(party, CMSCon, filledBean);
            CMSCon.commit();
            return requestList;
        } catch (Exception e) {
            try {
                CMSCon.rollback();
                throw e;
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
    }
    
    public boolean approveRequest(TransactionAdjustment filledBean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        boolean successInsert = false;
        try {
            perobj = new CardTxnAdjustmentConfirmPersistence();
            sysAuditManager = new SystemAuditManager();

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            successInsert = perobj.approveRequest(CMSCon, filledBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            if (successInsert) {
                CMSCon.commit();
            } else {
                CMSCon.rollback();
            }

        } catch (Exception e) {
            try {

                throw e;
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
        return successInsert;
    }

    public boolean rejectRequest(TransactionAdjustment filledBean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        boolean successInsert = false;
        try {
            perobj = new CardTxnAdjustmentConfirmPersistence();
            sysAuditManager = new SystemAuditManager();

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            successInsert = perobj.rejectRequest(CMSCon, filledBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();

        } catch (Exception e) {
            try {
                CMSCon.rollback();
                throw e;
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
        return successInsert;
    }

    public String getResponsemessage(String responseCode) throws Exception {
        String responseMsg = null;
        try {

            CMSOnline = new DBConnectionToOnlineDB().getConnection();
            perobj = new CardTxnAdjustmentConfirmPersistence();

            CMSOnline.setAutoCommit(false);

            responseMsg = perobj.getResponsemessage(CMSOnline, responseCode);

            CMSOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSOnline.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSOnline);
        }
        return responseMsg;
    }

    
}
