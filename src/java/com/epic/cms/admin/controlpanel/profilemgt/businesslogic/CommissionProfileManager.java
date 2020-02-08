/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.profilemgt.businesslogic;

import com.epic.cms.admin.controlpanel.profilemgt.bean.CommissionProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.persistance.commissionProfilePersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * this class use to wrote all commission profile manager method
 * @author ayesh
 */
public class CommissionProfileManager {

    private commissionProfilePersistance comisPer = null;
    private List<CommissionProfileBean> comisList = null;
    private List<CommissionProfileBean> comisTxnList = null;
    private SystemAuditManager sysAuditManager;
    private Connection CMSCon;

    public List<CommissionProfileBean> getCommissionProfileDetails() throws SQLException, Exception {
        try {

            CMSCon = DBConnection.getConnection();
            comisPer = new commissionProfilePersistance();
            comisList = new ArrayList<CommissionProfileBean>();

            CMSCon.setAutoCommit(false);

            comisList = comisPer.getCommissionProfileDetails(CMSCon);

            CMSCon.commit();

        } catch (SQLException sqx) {
            try {
                CMSCon.rollback();
                throw sqx;
            } catch (Exception ex) {
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return comisList;
    }

    public List<CommissionProfileBean> getCommissionTxnList() throws Exception {

        try {

            CMSCon = DBConnection.getConnection();
            comisPer = new commissionProfilePersistance();
            comisTxnList = new ArrayList<CommissionProfileBean>();

            CMSCon.setAutoCommit(false);

            comisTxnList = comisPer.getCommissionTxnList(CMSCon);

            CMSCon.commit();

        } catch (SQLException sqx) {
            try {
                CMSCon.rollback();
                throw sqx;
            } catch (Exception ex) {
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return comisTxnList;

    }
    
    public List<CommissionProfileBean> getAssignedCommissionTxnList(String comProfCode) throws Exception {

        try {

            CMSCon = DBConnection.getConnection();
            comisPer = new commissionProfilePersistance();
            comisTxnList = new ArrayList<CommissionProfileBean>();

            CMSCon.setAutoCommit(false);

            comisTxnList = comisPer.getAssignedCommissionTxnList(CMSCon,comProfCode);

            CMSCon.commit();

        } catch (SQLException sqx) {
            try {
                CMSCon.rollback();
                throw sqx;
            } catch (Exception ex) {
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return comisTxnList;

    }
    
     public List<CommissionProfileBean> getNotAssignedCommissionTxnList(String comProfCode, List<CommissionProfileBean> comTxnList) throws Exception {

        try {

            CMSCon = DBConnection.getConnection();
            comisPer = new commissionProfilePersistance();
            comisTxnList = new ArrayList<CommissionProfileBean>();

            CMSCon.setAutoCommit(false);

            comisTxnList = comisPer.getNotAssignedCommissionTxnList(CMSCon,comProfCode,comTxnList);

            CMSCon.commit();

        } catch (SQLException sqx) {
            try {
                CMSCon.rollback();
                throw sqx;
            } catch (Exception ex) {
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return comisTxnList;

    }

    public boolean commissionProfileInsert(CommissionProfileBean comisBean, List<CommissionProfileBean> comisTxnList, SystemAuditBean systemAuditBean) throws Exception {
        boolean isSuccess = false;
        try {

            CMSCon = DBConnection.getConnection();
            comisPer = new commissionProfilePersistance();
            sysAuditManager = new SystemAuditManager();

            CMSCon.setAutoCommit(false);

            isSuccess = comisPer.commissionProfileInsert(CMSCon, comisBean);

            if (isSuccess) {

                for (int k = 0; k < comisTxnList.size(); k++) {

                    isSuccess = comisPer.commissionProfileTxnInsert(CMSCon, comisTxnList.get(k), comisBean);

                }
            }

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (SQLException ex) {
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
        return isSuccess;
    }

    public boolean commissionProfileUpdate(CommissionProfileBean comisBean, List<CommissionProfileBean> comisTxnList, SystemAuditBean systemAuditBean) throws Exception {
        boolean isSuccess = false;
        try {

            CMSCon = DBConnection.getConnection();
            comisPer = new commissionProfilePersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon.setAutoCommit(false);

            isSuccess = comisPer.commissionProfileUpdate(CMSCon, comisBean);

            if (isSuccess) {

                isSuccess = comisPer.commissionProfileTxnDelete(CMSCon, comisBean);

            }

            if (isSuccess) {

                for (int k = 0; k < comisTxnList.size(); k++) {

                    isSuccess = comisPer.commissionProfileTxnInsert(CMSCon, comisTxnList.get(k), comisBean);

                }
            }

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (SQLException ex) {
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
        return isSuccess;
    }

    public boolean commissionProfileDelete(CommissionProfileBean comisBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean isSuccess = false;
        try {

            CMSCon = DBConnection.getConnection();
            comisPer = new commissionProfilePersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon.setAutoCommit(false);

            isSuccess = comisPer.commissionProfileTxnDelete(CMSCon, comisBean);

            if (isSuccess) {

                isSuccess = comisPer.commisssionProfileDelete(CMSCon, comisBean);

            }



            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (SQLException ex) {
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
        return isSuccess;
    }
}
