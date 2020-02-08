/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.profilemgt.businesslogic;

import com.epic.cms.admin.controlpanel.profilemgt.bean.BillingStatementProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.persistance.BillingStatementProfilePersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author badrika
 */
public class BillingStatementProfileManager {

    private BillingStatementProfilePersistance perObj;
    private Connection cmsCon;
    private SystemAuditManager sysAuditManager;

    public List<BillingStatementProfileBean> getBillingStatementProfDetails() throws SQLException, Exception {

        try {
            List<BillingStatementProfileBean> billingStatementList = null;
            perObj = new BillingStatementProfilePersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            billingStatementList = perObj.getBillingStatementProfDetails(cmsCon);

            cmsCon.commit();
            return billingStatementList;
        } catch (Exception e) {
            try {
                cmsCon.rollback();
                throw e;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    public int addBillingStatementProfile(SystemAuditBean systemAuditBean, BillingStatementProfileBean bean) throws SQLException, Exception {

        int rowCount = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new BillingStatementProfilePersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            rowCount = perObj.addBillingStatementProfile(cmsCon, bean);
            sysAuditManager.insertAudit(systemAuditBean, cmsCon);


            cmsCon.commit();
            return rowCount;
        } catch (Exception e) {
            try {
                cmsCon.rollback();
                throw e;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }
    
       public int deleteProfile(String id, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        int rowCount = -1;
        try {
            perObj = new BillingStatementProfilePersistance();
            sysAuditManager = new SystemAuditManager();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            sysAuditManager.insertAudit(systemAuditBean, cmsCon);
            rowCount = perObj.deleteProfile(cmsCon, id);
            cmsCon.commit();

        } catch (SQLException ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return rowCount;
    }
    
    
        public int updateProfile(SystemAuditBean systemAuditBean, BillingStatementProfileBean bean) throws SQLException, Exception {

        int rowCount = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new BillingStatementProfilePersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            //--
            rowCount = perObj.updateProfile(cmsCon, bean);
            sysAuditManager.insertAudit(systemAuditBean, cmsCon);
            cmsCon.commit();
            return rowCount;
        } catch (Exception e) {
            try {
                cmsCon.rollback();
                throw e;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }
    
    
    
    
    
    
    
    
    
    
    
}
