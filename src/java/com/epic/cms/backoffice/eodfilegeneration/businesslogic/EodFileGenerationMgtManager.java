/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.eodfilegeneration.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.backoffice.eodfilegeneration.bean.SettlementFileBean;
import com.epic.cms.backoffice.eodfilegeneration.bean.SettlementFileParamBean;
import com.epic.cms.backoffice.eodfilegeneration.bean.TxnToEodBean;
import com.epic.cms.backoffice.eodfilegeneration.persistance.EodFileGenerationMgtPersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author chanuka
 */
public class EodFileGenerationMgtManager {

    private Connection CMSCon;
    private EodFileGenerationMgtPersistance eodFileGenMgtPersistance = null;
    private List<TxnToEodBean> txnToEodBeanlist = null;
    private List<SettlementFileBean> settlementFileBeanList = null;
    private SystemAuditManager sysAuditManager;
    private List<String> terminalList = null;

    public List<TxnToEodBean> getAllTxnToEod(String terminalId, String startTime, String endTime) throws Exception {

        try {
            eodFileGenMgtPersistance = new EodFileGenerationMgtPersistance();
            CMSCon = DBConnectionToOnlineDB.getConnection();
            CMSCon.setAutoCommit(false);
            txnToEodBeanlist = eodFileGenMgtPersistance.getAllTxnToEod(CMSCon, terminalId, startTime, endTime);
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
        return txnToEodBeanlist;
    }

    public SettlementFileParamBean getSettlementFileParam() throws Exception {

        SettlementFileParamBean settlementFileParamBean = null;
        try {
            eodFileGenMgtPersistance = new EodFileGenerationMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            settlementFileParamBean = eodFileGenMgtPersistance.getSettlementFileParam(CMSCon);
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
        return settlementFileParamBean;
    }

    public List<String> getAllTeminalDetails(String startTime, String endTime) throws Exception {

        try {
            eodFileGenMgtPersistance = new EodFileGenerationMgtPersistance();
            CMSCon = DBConnectionToOnlineDB.getConnection();
            CMSCon.setAutoCommit(false);
            terminalList = eodFileGenMgtPersistance.getAllTeminalDetails(CMSCon, startTime, endTime);
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
        return terminalList;
    }

    public boolean insertSettlementFile(SettlementFileBean SFileBean, SystemAuditBean systemAuditBean) throws Exception {

        boolean flag = false;
        try {
            eodFileGenMgtPersistance = new EodFileGenerationMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            flag = eodFileGenMgtPersistance.insertSettlementFile(CMSCon, SFileBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
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
        return flag;
    }

    public List<SettlementFileBean> loadAllGeneratedEodFiles() throws Exception {

        try {
            eodFileGenMgtPersistance = new EodFileGenerationMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            settlementFileBeanList = eodFileGenMgtPersistance.loadAllGeneratedEodFiles(CMSCon);
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
        return settlementFileBeanList;
    }
}
