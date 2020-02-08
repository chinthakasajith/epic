/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationCreditScoreBean;
import com.epic.cms.reportexp.cardapplication.persistance.ApplicationCreditScoreBreakDownPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author nalin
 */
public class ApplicationCreditScoreBreakDownManager {

    private Connection CMSCon;
    private SystemAuditManager sysAuditManager = null;
    private HashMap<String, String> csUserList = null;
    private ApplicationCreditScoreBreakDownPersistance creditScorePersistance = null;
    private List<ApplicationCreditScoreBean> creditScoreList = null;
    private static ApplicationCreditScoreBreakDownManager setInstance;

    public synchronized static ApplicationCreditScoreBreakDownManager getInstance() {
        if (setInstance == null) {
            setInstance = new ApplicationCreditScoreBreakDownManager();
        }
        return setInstance;
    }

    public HashMap<String, String> getCreditScoreUserList() throws Exception {

        try {

            CMSCon = DBConnection.getConnection();
            creditScorePersistance = new ApplicationCreditScoreBreakDownPersistance();
            csUserList = new HashMap<String, String>();

            CMSCon.setAutoCommit(false);

            csUserList = creditScorePersistance.getCreditScoreUserList(CMSCon);

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

        return csUserList;
    }

    public List<ApplicationCreditScoreBean> searchApplicationCreditScoreReport(ApplicationCreditScoreBean creditScoreBean) throws Exception {

        try {
            creditScorePersistance = new ApplicationCreditScoreBreakDownPersistance();
            creditScoreList = new ArrayList<ApplicationCreditScoreBean>();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            creditScoreList = creditScorePersistance.searchApplicationCreditScoreReport(CMSCon, creditScoreBean);

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
        return creditScoreList;
    }

    public List<ApplicationCreditScoreBean> getApplicationCreditScoreDetails(String applicationId) throws Exception {

        try {
            creditScorePersistance = new ApplicationCreditScoreBreakDownPersistance();
            creditScoreList = new ArrayList<ApplicationCreditScoreBean>();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            creditScoreList = creditScorePersistance.getApplicationCreditScoreDetails(CMSCon, applicationId);

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
        return creditScoreList;
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
