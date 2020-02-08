/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.cardlimitincrement.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.backoffice.cardlimitincrement.bean.PermLimitIncrementBean;
import com.epic.cms.backoffice.cardlimitincrement.persistance.PermLimitIncrementPersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author badrika
 */
public class PermLimitIncrementManager {

    private PermLimitIncrementPersistance perObj;
    private Connection cmsCon, onlineCon;
    private List<PermLimitIncrementBean> cardList;
    private boolean successUpdate = false;
    private boolean successInc1 = false, successInc2 = false, successInc3 = false;
    private SystemAuditManager sysAuditManager;
    private SystemAuditBean systemAuditBean;
    private PermLimitIncrementBean updateBean;

    public List<PermLimitIncrementBean> searchCards(PermLimitIncrementBean filledBean) throws SQLException, Exception {

        try {
            perObj = new PermLimitIncrementPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            cardList = perObj.searchCards(cmsCon, filledBean);
            cmsCon.commit();
            return cardList;
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

    public PermLimitIncrementBean getOnlineDetails(String cardNum) throws SQLException, Exception {

        try {
            perObj = new PermLimitIncrementPersistance();
            onlineCon = DBConnectionToOnlineDB.getConnection();
            onlineCon.setAutoCommit(false);
            updateBean = perObj.getOnlineDetails(onlineCon, cardNum);
            onlineCon.commit();
            return updateBean;

        } catch (Exception e) {
            try {

                onlineCon.rollback();
                throw e;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (onlineCon != null) {
                try {
                    onlineCon.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
    }

    public boolean updateOnlineDetails(PermLimitIncrementBean filledBean, String cardNum) throws SQLException, Exception {

        try {
            perObj = new PermLimitIncrementPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            successUpdate = perObj.updateOnlineDetails(cmsCon, filledBean, cardNum);
            cmsCon.commit();
            return successUpdate;
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
                    e.printStackTrace();
                    throw e;
                }
            }
        }
    }

    public boolean limitIncrement(PermLimitIncrementBean filledBean, SystemAuditBean systemAuditBean) throws SQLException, Exception {

        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new PermLimitIncrementPersistance();
            cmsCon = DBConnection.getConnection();
            onlineCon = DBConnectionToOnlineDB.getConnection();

            cmsCon.setAutoCommit(false);
            onlineCon.setAutoCommit(false);

            successInc1 = perObj.limitIncrement(cmsCon, filledBean);
            successInc2 = perObj.updatePermlimitInc(cmsCon, filledBean);
            successInc3 = perObj.limitIncrementToOnline(onlineCon, filledBean);
            sysAuditManager.insertAudit(systemAuditBean, cmsCon);


            cmsCon.commit();
            onlineCon.commit();

            return (successInc1 && successInc2 && successInc3);

        } catch (Exception e) {
            try {
                cmsCon.rollback();
                onlineCon.rollback();
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
            if (onlineCon != null) {
                try {
                    onlineCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }
}
