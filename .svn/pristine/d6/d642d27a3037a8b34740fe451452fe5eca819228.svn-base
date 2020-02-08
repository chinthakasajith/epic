/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.MerchantCategoryBean;
import com.epic.cms.admin.controlpanel.transactionmgt.persistance.MerchantCategoryPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * this class handles logic which is relevant to MCC table
 * @author nisansala
 */
public class MerchantCategoryManager {
    //declaring variables
    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;
    private MerchantCategoryPersistance merchntCatPer;
    
    /**
     * call the persistence class to insert a new merchant to MCC table
     * @param merchntCatBean
     * @param systemAuditBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    
    public Boolean insertNewMerchntCatgry(MerchantCategoryBean merchntCatBean,SystemAuditBean systemAuditBean) throws SQLException, Exception{
        try{
            
            //if the insert become success row will return 1
            Boolean success = false;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            merchntCatPer = new MerchantCategoryPersistance();
            success = merchntCatPer.insertNewMrchntCatgryData(merchntCatBean, CMSCon);
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
    
    /**
     * call the persistence class to get all merchant category details from MCC table
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    
    public List<MerchantCategoryBean> getAllMerchntCatgryDetails() throws SQLException, Exception {
        try {
            List<MerchantCategoryBean> mrchntList = null;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            merchntCatPer = new MerchantCategoryPersistance();
            
            //assign the merchant details to the erchant bean instance
            mrchntList = merchntCatPer.getAllMrchntCatgryData(CMSCon);

            CMSCon.commit();
            return mrchntList;

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
    
    /**
     * call the persistence class to update the details in MCC table
     * @param merchntCatBean
     * @param systemAuditBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public boolean updateMerchntCatgryDetails(MerchantCategoryBean merchntCatBean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        boolean success = false;
        try {
            int row = -1;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            merchntCatPer = new MerchantCategoryPersistance();
            //
            row = merchntCatPer.updateMrchntCatgryData(merchntCatBean, CMSCon);
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
    
    /**
     * call the persistence class to delete merchant details from the MCC table
     * @param MCCCode
     * @param systemAuditBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public boolean deleteMerchntCatgryDetails(String MCCCode, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        boolean success = false;
        try {
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            merchntCatPer = new MerchantCategoryPersistance();
            merchntCatPer.deleteMrchntCatgryData(CMSCon, MCCCode);
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
