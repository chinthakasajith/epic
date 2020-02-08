/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.BillingCycleMgtBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.MerchantStatementCycleBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.persistance.BillingCycleMgtPersistance;
import com.epic.cms.admin.controlpanel.systemconfigmgt.persistance.MerchantStatementCyclePersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author nisansala
 */
public class MerchantStatementCycleManager {

    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;
    private MerchantStatementCyclePersistance mPer;

    public List<MerchantStatementCycleBean> getAllBillingData() throws SQLException, Exception {
        try {
            List<MerchantStatementCycleBean> billingList = null;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            mPer = new MerchantStatementCyclePersistance();

            //assign the merchant details to the erchant bean instance
            billingList = mPer.getAllBillingData(CMSCon);

            CMSCon.commit();
            return billingList;

        } catch (SQLException ex) {
            //throws an exception if the rollback fails
            try {
                CMSCon.rollback();
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
        }
    }

    public Boolean insertNewBillCycle(MerchantStatementCycleBean statement, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        try {

            //if the insert become success row will return 1
            Boolean success = false;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            mPer = new MerchantStatementCyclePersistance();
            success = mPer.insertNewCycle(statement, CMSCon);
            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

            return success;

        } catch (SQLException ex) {

            //throws an exception when rollback fails 
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }
    
    public boolean updateBillCycle(MerchantStatementCycleBean statement, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        boolean success = false;
        try {
            int row = -1;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            mPer = new MerchantStatementCyclePersistance();
            //
            row = mPer.updateCycle(statement, CMSCon);
            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();
            success = true;

            //row will indicate the success of updation 

        } catch (SQLException ex) {
            //throws an exception if the rollback fails
            try {
                CMSCon.rollback();
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
        }
        return success;
    }
    
    public boolean deleteBillingData(String billCode, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        boolean success = false;
        try {
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            mPer = new MerchantStatementCyclePersistance();
            mPer.deleteCycle(CMSCon, billCode);
            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();
            //'row' indicates the success of deletion
            success = true;
        } catch (SQLException ex) {
            //throws an exception if the rollback fails
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                //throws an exception if some error occurs when closing the connection
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
