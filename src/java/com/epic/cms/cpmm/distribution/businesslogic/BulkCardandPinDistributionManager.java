/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.cpmm.distribution.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.cpmm.distribution.persistance.BulkCardandPinDistributionPersistance;
import com.epic.cms.cpmm.distribution.bean.BulkCardDistributionBean;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nalin
 */
public class BulkCardandPinDistributionManager {

    private List<BulkCardDistributionBean> bulkDistList = null;
    private BulkCardandPinDistributionPersistance bulkCardPerr = null;
    private SystemAuditManager sysAuditManager = null;
    private Connection CMSCon = null;

    public List<BulkCardDistributionBean> searchCardDistributionDetails(BulkCardDistributionBean bulkDistBean, SystemAuditBean systemAuditBean) throws Exception {
        try {
            bulkDistList = new ArrayList<BulkCardDistributionBean>();
            sysAuditManager = new SystemAuditManager();
            bulkCardPerr = new BulkCardandPinDistributionPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bulkDistList = bulkCardPerr.searchCardDistributionDetails(bulkDistBean, CMSCon);

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
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
        return bulkDistList;
    }

    public List<BulkCardDistributionBean> searchPinDistributionDetails(BulkCardDistributionBean bulkDistBean, SystemAuditBean systemAuditBean) throws Exception {
        try {
            bulkDistList = new ArrayList<BulkCardDistributionBean>();
            sysAuditManager = new SystemAuditManager();
            bulkCardPerr = new BulkCardandPinDistributionPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bulkDistList = bulkCardPerr.searchPinDistributionDetails(bulkDistBean, CMSCon);

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
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
        return bulkDistList;
    }

    public boolean proceedCardDistribution(String[] batchList, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            sysAuditManager = new SystemAuditManager();
            bulkCardPerr = new BulkCardandPinDistributionPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            success = bulkCardPerr.proceedCardDistribution(CMSCon, batchList, systemAuditBean.getUserName());

            for (int i = 0; i < batchList.length; i++) {

                success = bulkCardPerr.updateCardDistributionStatus(CMSCon, batchList[i], systemAuditBean.getUserName());

            }

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
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
        return success;
    }

    public boolean proceedPinDistribution(String[] batchList, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            sysAuditManager = new SystemAuditManager();
            bulkCardPerr = new BulkCardandPinDistributionPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            success = bulkCardPerr.proceedPinDistribution(CMSCon, batchList, systemAuditBean.getUserName());

            for (int i = 0; i < batchList.length; i++) {

                success = bulkCardPerr.updatePinDistributionStatus(CMSCon, batchList[i], systemAuditBean.getUserName());

            }

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
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
        return success;
    }
}
