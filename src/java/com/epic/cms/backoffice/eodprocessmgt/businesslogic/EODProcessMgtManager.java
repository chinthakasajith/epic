/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.eodprocessmgt.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.backoffice.eodprocessmgt.bean.EODProcessMgtBean;
import com.epic.cms.backoffice.eodprocessmgt.persistance.EODProcessMgtPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author nisansala
 */
public class EODProcessMgtManager {

    //declaring variables
    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;
    private EODProcessMgtPersistance eodPer;

    public List<EODProcessMgtBean> getAllEODProcessData() throws SQLException, Exception {
        try {
            List<EODProcessMgtBean> mrchntList = null;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            eodPer = new EODProcessMgtPersistance();

            //assign the merchant details to the erchant bean instance
            mrchntList = eodPer.getAllEODProcessData(CMSCon);

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

    public Boolean insertNewEODProcess(EODProcessMgtBean process, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        String processId = "";
        try {

            //if the insert become success row will return 1
            int success1 = -1;
            Boolean success2, success = false;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            eodPer = new EODProcessMgtPersistance();
            success2 = eodPer.insertNewEODProcess(process, CMSCon);
            if (success2) {
                processId = eodPer.getProcessId(CMSCon, systemAuditBean.getUserName());
                systemAuditBean.setUniqueId(processId);
                systemAuditBean.setDescription("Add EOD Process. EOD Process Code: '" + processId + "'; by: " + systemAuditBean.getUserName());
            }
            sysAuditManager = new SystemAuditManager();
            success1 = sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            
            CMSCon.commit();

            if (success2 && success1 == 1) {
                success = true;
            }
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

    public int updateEODProcess(EODProcessMgtBean process, SystemAuditBean systemAuditBean) throws SQLException, Exception {

        int row = -1;
        try {

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            eodPer = new EODProcessMgtPersistance();

            row = eodPer.updateEODProcess(process, CMSCon);
            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();


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
        return row;
    }

    public HashMap<String, String> getProcessCategoryList() throws SQLException, Exception {
        HashMap<String, String> processCategory;

        try {

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            eodPer = new EODProcessMgtPersistance();
            processCategory = eodPer.getProcessCategoryList(CMSCon);
            CMSCon.commit();
            return processCategory;

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

    public boolean deleteEODProcess(String processId, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        boolean success = false;
        try {
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            eodPer = new EODProcessMgtPersistance();
            eodPer.deleteEODProcess(CMSCon, processId);
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
