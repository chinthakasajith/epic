/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationVerificationSearchBean;
import com.epic.cms.reportexp.cardapplication.bean.BankBranchBean;
import com.epic.cms.reportexp.cardapplication.persistance.ApplicationVerificationReportPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author asela
 */
public class ApplicationVerificationReportManager {

    private ApplicationVerificationReportPersistance bankBranchPersistance;
    private List<BankBranchBean> bankBranchList;
    private List<ApplicationVerificationSearchBean> applicationVerificationSearchList;
    private Map<String, String> priorityLevelMap;
    private Connection CMSCon;
    private static ApplicationVerificationReportManager setInstance;
    private SystemAuditManager sysAuditManager = null;

    public synchronized static ApplicationVerificationReportManager getInstance() {
        if (setInstance == null) {
            setInstance = new ApplicationVerificationReportManager();
        }
        return setInstance;
    }

    public List<BankBranchBean> getCommenBankBranchList() throws Exception {
        try {

            bankBranchPersistance = new ApplicationVerificationReportPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bankBranchList = bankBranchPersistance.getCommenBankBranchList(CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return bankBranchList;
    }

    public Map<String, String> getAllPriorityLevelMap() throws Exception {
        try {

            bankBranchPersistance = new ApplicationVerificationReportPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            priorityLevelMap = bankBranchPersistance.getAllPriorityLevelMap(CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return priorityLevelMap;
    }

    public Map<String, String> getAllApplicationDomainMap() throws Exception {
        try {

            bankBranchPersistance = new ApplicationVerificationReportPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            priorityLevelMap = bankBranchPersistance.getAllApplicationDomainMap(CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return priorityLevelMap;
    }

    public List<ApplicationVerificationSearchBean> getVerificationReportDetails(ApplicationVerificationSearchBean searchBean) throws Exception {
        try {

            bankBranchPersistance = new ApplicationVerificationReportPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            applicationVerificationSearchList = bankBranchPersistance.getVerificationReportDetails(CMSCon, searchBean);
            CMSCon.commit();

        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return applicationVerificationSearchList;
    }

    public void insertAuditValues(SystemAuditBean systemAuditBean) throws SQLException, Exception {
        try {
            sysAuditManager = new SystemAuditManager();

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();

        } catch (Exception e) {
            try {
                CMSCon.rollback();
                throw e;
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
    }
}
