/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.persistance.CurrencyMgtPersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mahesh_m
 */
public class CurrencyMgtManager {

    private CurrencyMgtPersistance currPer;
    private Connection CMSCon, OnlineCon;
    private SystemAuditManager sysAuditManager;
    private List<CurrencyBean> currDetailList = null;

    public List<CurrencyBean> getCurrencyDetails() throws Exception {
        try {
            currDetailList = new ArrayList<CurrencyBean>();
            currPer = new CurrencyMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            currDetailList = currPer.getCurrencyDetails(CMSCon);

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
        return currDetailList;
    }

    public boolean inserCurrency(CurrencyBean currency, SystemAuditBean systemAuditBean) throws Exception {
        boolean success1 = false;
        boolean success2 = false;
        try {
            currPer = new CurrencyMgtPersistance();
            sysAuditManager = new SystemAuditManager();

            CMSCon = DBConnection.getConnection();
            OnlineCon = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            OnlineCon.setAutoCommit(false);

            success1 = currPer.insertCurrency(CMSCon, currency);
            success2 = currPer.insertCurencyToOnline(OnlineCon, currency);

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            if (success1 && success2) {
                CMSCon.commit();
                OnlineCon.commit();
            } else {
                CMSCon.rollback();
                OnlineCon.rollback();
            }

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                OnlineCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                OnlineCon.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnection.dbConnectionClose(OnlineCon);
        }
        return (success1 && success2);
    }

    public boolean updateCurrency(CurrencyBean currency, SystemAuditBean systemAuditBean) throws Exception {
        boolean success1 = false;
        boolean success2 = false;
        try {
            currPer = new CurrencyMgtPersistance();
            sysAuditManager = new SystemAuditManager();

            CMSCon = DBConnection.getConnection();
            OnlineCon = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            OnlineCon.setAutoCommit(false);

            success1 = currPer.updateCurrency(CMSCon, currency);
            success2 = currPer.updateCurencyToOnline(OnlineCon, currency);

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            if (success1 && success2) {
                CMSCon.commit();
                OnlineCon.commit();
            } else {
                CMSCon.rollback();
                OnlineCon.rollback();
            }


        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                OnlineCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                OnlineCon.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnection.dbConnectionClose(OnlineCon);
        }
        return (success1 && success2);
    }

    public boolean deleteCurrency(CurrencyBean currency, SystemAuditBean systemAuditBean) throws Exception {

        boolean success1 = false;
        boolean success2 = false;
        try {
            currPer = new CurrencyMgtPersistance();
            sysAuditManager = new SystemAuditManager();

            CMSCon = DBConnection.getConnection();
            OnlineCon = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            OnlineCon.setAutoCommit(false);

            success1 = currPer.deleteCurrency(CMSCon, currency);
            success2 = currPer.deleteFromOnline(OnlineCon, currency);

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            if (success1 && success2) {
                CMSCon.commit();
                OnlineCon.commit();
            } else {
                CMSCon.rollback();
                OnlineCon.rollback();
            }

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                OnlineCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                OnlineCon.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnection.dbConnectionClose(OnlineCon);
        }
        return (success1 && success2);
    }
}
