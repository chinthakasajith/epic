/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.reportexp.cardapplication.bean.EodTransactionReportBean;
import com.epic.cms.reportexp.cardapplication.persistance.EodTransactionReportPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author asela
 */
public class EodTransactionReportManager {

    private Connection CMSCon;
    private EodTransactionReportPersistance eodTransactionReportPersistance;
    private List<EodTransactionReportBean> searchList;
    private Map<String, String> totalCountMap;
    private SystemAuditManager sysAuditManager = null;
    private static EodTransactionReportManager setInstance;

    public synchronized static EodTransactionReportManager getInstance() {
        if (setInstance == null) {
            setInstance = new EodTransactionReportManager();
        }
        return setInstance;
    }

    public List<EodTransactionReportBean> getEodTransactionReportDetails(EodTransactionReportBean eodTransactionReportBean) throws Exception {
        try {

            eodTransactionReportPersistance = new EodTransactionReportPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            searchList = eodTransactionReportPersistance.getEodTransactionReportDetails(CMSCon, eodTransactionReportBean);
            CMSCon.commit();

        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return searchList;
    }

    public Map<String, String> getTotalAmountMap(EodTransactionReportBean etrb) throws Exception {
        try {
            totalCountMap = new HashMap<String, String>();

            eodTransactionReportPersistance = new EodTransactionReportPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            totalCountMap = eodTransactionReportPersistance.getTotalAmountMap(CMSCon, etrb);
            CMSCon.commit();

        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return totalCountMap;
    }
    
    public void insertAuditValues(SystemAuditBean systemAuditBean) throws SQLException, Exception{
        try {
            sysAuditManager = new SystemAuditManager();

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

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
    }
    
}
