/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.callcentersearch.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.callcenter.callcentersearch.bean.TransactionBean;
import com.epic.cms.callcenter.callcentersearch.persistance.CallCenterTerminalMgtPersistance;
import com.epic.cms.mtmm.terminalmgt.terminaldata.bean.TerminalDataCaptureBean;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nalin
 */
public class CallCenterTerminalMgtManager {

    private Connection CMSCon = null;
    private Connection ConToOnline = null;
    private CallCenterTerminalMgtPersistance terminalPerr = null;
    private SystemAuditManager sysAuditManager = null;
    private List<TransactionBean> txnList = null;

    public String getCurrentTerminalStatus(String tid) throws Exception {
        String status = null;
        try {
            CMSCon = DBConnection.getConnection();

            terminalPerr = new CallCenterTerminalMgtPersistance();
            CMSCon.setAutoCommit(false);

            status = terminalPerr.getCurrentTerminalStatus(CMSCon, tid);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }


        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;

        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }

        }
        return status;
    }

    public Boolean updateTerminal(TerminalDataCaptureBean terminalBean, SystemAuditBean systemAuditBean, List<TerminalDataCaptureBean> assignedBean, String[] transactions, String tid) throws Exception {
        boolean success = false;
        try {
            int row = -1;
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            terminalPerr = new CallCenterTerminalMgtPersistance();
            ConToOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon.setAutoCommit(false);

            //
            row = terminalPerr.updateTerminal(terminalBean, transactions, CMSCon);
            terminalPerr.updateTerminalTxn(transactions, CMSCon, tid);
            terminalPerr.updateTerminalTxnOnline(transactions, assignedBean, ConToOnline, tid);

            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();
            success = true;

            //row will indicate the success of updation 

        } catch (SQLException ex) {
            //throws an exception if the rollback fails
            try {
                CMSCon.rollback();
                ConToOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            //throws an exception if some error occurs when closing the connection
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
            if (ConToOnline != null) {
                try {
                    ConToOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return success;
    }

    public int getTerminalTxnCount(String condition, String merchantId) throws Exception {
        int totCount = 0;
        try {
            terminalPerr = new CallCenterTerminalMgtPersistance();
            ConToOnline = DBConnectionToOnlineDB.getConnection();
            ConToOnline.setAutoCommit(false);

            totCount = terminalPerr.getCountTxn(ConToOnline, condition, merchantId);

            ConToOnline.commit();

        } catch (Exception ex) {
            try {

                ConToOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (ConToOnline != null) {
                try {
                    ConToOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return totCount;
    }
    
     public List<TransactionBean> getTerminalTxnExpDetails(String condition, int start, int end, String merchantId) throws Exception {
        try {
            terminalPerr = new CallCenterTerminalMgtPersistance();
            ConToOnline = DBConnectionToOnlineDB.getConnection();
            txnList = new ArrayList<TransactionBean>();

            ConToOnline.setAutoCommit(false);

            txnList = terminalPerr.getTerminalTxnExpDetails(ConToOnline, condition, start, end, merchantId);
           
            ConToOnline.commit();

        } catch (Exception ex) {
            try {
                
                ConToOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if ( ConToOnline != null) {
                try {
                    
                    ConToOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return txnList;
    }
}
