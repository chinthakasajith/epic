/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.sysmsg.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.switchcontrol.sysmsg.bean.ViewAlertBean;
import com.epic.cms.switchcontrol.sysmsg.bean.ViewFailAlertBean;
import com.epic.cms.switchcontrol.sysmsg.persistance.ViewFailAlertPersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author nisansala
 */
public class ViewFailAlertManager {

    ViewFailAlertPersistance failAlertPer;
    private Connection CMSCon;
    private Connection onlineCMSCon;
    private List<ViewFailAlertBean> searchAlertList;
    private SystemAuditManager sysAuditManager;
    ViewAlertBean alerBean;

    public List<ViewFailAlertBean> searchAlert(ViewFailAlertBean alertBean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        try {
            failAlertPer = new ViewFailAlertPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            onlineCMSCon = DBConnectionToOnlineDB.getConnection();
            CMSCon.setAutoCommit(false);
            onlineCMSCon.setAutoCommit(false);

            searchAlertList = failAlertPer.searchAlertData(onlineCMSCon, alertBean);
            CMSCon.commit();
            onlineCMSCon.commit();
        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                onlineCMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }


        } catch (Exception ex) {
            CMSCon.rollback();
            onlineCMSCon.rollback();
            throw ex;

        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }

            }
            if (onlineCMSCon != null) {
                try {
                    onlineCMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }

        }
        return searchAlertList;
    }

    public int updateReadStatus(String txnID) throws SQLException, Exception {

        int row = 0;
        try {
            failAlertPer = new ViewFailAlertPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            onlineCMSCon = DBConnectionToOnlineDB.getConnection();
            onlineCMSCon.setAutoCommit(false);

            row = failAlertPer.updateReadStatus(txnID, onlineCMSCon);
            //sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            onlineCMSCon.commit();
            return row;
        } catch (Exception e) {
            try {
                onlineCMSCon.rollback();
                throw e;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (onlineCMSCon != null) {
                try {
                    onlineCMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }
    
    public int getCountTxn(String condition) throws Exception {
        int count;
        try {
            failAlertPer = new ViewFailAlertPersistance();
            onlineCMSCon = DBConnectionToOnlineDB.getConnection();
            onlineCMSCon.setAutoCommit(false);
            count = failAlertPer.getCountTxn(onlineCMSCon, condition);
            onlineCMSCon.commit();
        } catch (Exception ex) {
            try {
                onlineCMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (onlineCMSCon != null) {
                try {
                    onlineCMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return count;
    }
    
    public List<ViewFailAlertBean> getTxnAlertDetails(String condition, int start, int end) throws Exception {
        try {
            failAlertPer = new ViewFailAlertPersistance();
            onlineCMSCon = DBConnectionToOnlineDB.getConnection();
            onlineCMSCon.setAutoCommit(false);
            searchAlertList = failAlertPer.getTxnAlertDetails(onlineCMSCon, condition, start, end);
            onlineCMSCon.commit();
        } catch (Exception ex) {
            try {
                onlineCMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (onlineCMSCon != null) {
                try {
                    onlineCMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return searchAlertList;
    }
}
