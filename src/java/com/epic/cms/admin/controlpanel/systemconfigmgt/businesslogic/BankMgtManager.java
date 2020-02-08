/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.BankBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.persistance.BankMgtPersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author badrika
 */
public class BankMgtManager {

    private Connection cmsCon;
    private BankMgtPersistance perObj;
    private List<StatusBean> statusList = null;
    private BankBean bankBean;
    private SystemAuditManager sysAuditManager;

    /**
     * to take bank list
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<BankBean> getBankNames() throws SQLException, Exception {

        try {
            List<BankBean> bankList = null;
            perObj = new BankMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            bankList = perObj.getBankNames(cmsCon);

            cmsCon.commit();
            return bankList;
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

    /**
     * to take status list(active and deactive)
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<StatusBean> getStatusList() throws SQLException, Exception {
        statusList = new ArrayList<StatusBean>();
        try {

            perObj = new BankMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            statusList = perObj.getStatustList(cmsCon);

            cmsCon.commit();
            return statusList;
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

    /**
     * to add a new bank
     * @param systemAuditBean
     * @param bean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int addNewBank(SystemAuditBean systemAuditBean, BankBean bean) throws SQLException, Exception {

        int rowCount = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new BankMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            rowCount = perObj.addNewBank(cmsCon, bean);
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

    /**
     * to delete a bank
     * @param id
     * @param systemAuditBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteBank(String id, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        int rowCount = -1;
        try {
            perObj = new BankMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            sysAuditManager.insertAudit(systemAuditBean, cmsCon);
            rowCount = perObj.deleteBanks(cmsCon, id);
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

    /**
     * to update bank details
     * @param systemAuditBean
     * @param bean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int UpdateBank(SystemAuditBean systemAuditBean, BankBean bean) throws SQLException, Exception {

        int rowCount = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new BankMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            //--
            rowCount = perObj.updateBanks(cmsCon, bean);
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

    /**
     * to view one selected bank details
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public BankBean viewSelectedBank(String id) throws SQLException, Exception {

        try {
            perObj = new BankMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            bankBean = perObj.viewSelectedBankDetails(cmsCon, id);
            cmsCon.commit();
            return bankBean;
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
