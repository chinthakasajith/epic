/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.capturedata.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.BulkCardBean;
import com.epic.cms.camm.applicationproccessing.capturedata.persistance.BulkCardDataCapturePersistance;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.BulkCardRequestBean;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *
 * @author nalin
 */
public class BulkCardDataCaptureManager {

    private Connection CMSCon;
    private BulkCardDataCapturePersistance bcdcPer = null;
    private HashMap<String, String> bnkBranches = null;
    private SystemAuditManager sysAuditManager = null;

    public HashMap<String, String> getBranchNames() throws Exception {
        try {

            bcdcPer = new BulkCardDataCapturePersistance();
            bnkBranches = new HashMap<String, String>();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bnkBranches = bcdcPer.getBranchNames(CMSCon);
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
        return bnkBranches;
    }

    public String checkCardRequestedBranch(String cardNumber) throws Exception {
        String branch = null;
        try {

            bcdcPer = new BulkCardDataCapturePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            branch = bcdcPer.checkCardRequestedBranch(CMSCon, cardNumber);
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
        return branch;
    }

    public boolean checkCardNumberIsExist(String cardNumber) throws Exception {
        boolean isExist = false;
        try {

            bcdcPer = new BulkCardDataCapturePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            isExist = bcdcPer.checkCardNumberIsExist(CMSCon, cardNumber);

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

        return isExist;
    }

    public boolean insertBulkCardApplicationDetails(BulkCardBean bulkCardBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            bcdcPer = new BulkCardDataCapturePersistance();
            BulkCardRequestBean bulkCardRequestBean = new BulkCardRequestBean();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            bulkCardRequestBean = bcdcPer.getBulkCardBatchDetails(CMSCon, bulkCardBean.getCardNumber());

            if (bulkCardRequestBean != null) {
                success = bcdcPer.insertBulkCardToCardApplication(CMSCon, bulkCardBean, bulkCardRequestBean);
                success = bcdcPer.insertBulkCardToDebitCardApplicationDetails(CMSCon, bulkCardBean, bulkCardRequestBean);
                success = bcdcPer.insertToCardApplicationStatus(CMSCon, bulkCardBean);
                success = bcdcPer.insertToCardApplicationHistory(CMSCon, bulkCardBean);
                if (bulkCardBean.isFlag()) {
                    success = bcdcPer.insertToCardApplicationDocument(CMSCon, bulkCardBean);
                }
            }

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
        return success;
    }
}
