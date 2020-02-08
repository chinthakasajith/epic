/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.BulkCardRequestBean;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationSummaryBean;
import com.epic.cms.reportexp.cardapplication.persistance.ApplicationSummaryPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author nisansala
 */
public class ApplicationSummaryManager {

    private Connection CMSCon;
    private SystemUserBean sysUsrBean = null;
    private SystemAuditManager sysAuditManager = null;
    private HashMap<String, String> cardDomain = null;
    private HashMap<String, String> bnkBranches = null;
    private HashMap<String, String> cdProduct = null;
    private HashMap<String, String> productModes = null;
    private ApplicationSummaryPersistance appSumPer = null;
    private List<CurrencyBean> currency = null;
    private List<BulkCardRequestBean> reqList = null;
    private HashMap<String, String> appStatusList = null;
    private HashMap<String, String> cardDomainList = null;
    private HashMap<String, String> priorityLevelList = null;
    private List<ApplicationSummaryBean> searchList = null;
    private static ApplicationSummaryManager setInstance;

    public synchronized static ApplicationSummaryManager getInstance() {
        if (setInstance == null) {
            setInstance = new ApplicationSummaryManager();
        }
        return setInstance;
    }

    public HashMap<String, String> getBranchNames() throws Exception {
        try {

            appSumPer = new ApplicationSummaryPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bnkBranches = appSumPer.getBranchNames(CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {

            CMSCon.rollback();
            throw ex;

        } finally {

            DBConnection.dbConnectionClose(CMSCon);
        }
        return bnkBranches;
    }

    public HashMap<String, String> getPriorityLevels() throws Exception {
        try {
            appSumPer = new ApplicationSummaryPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            priorityLevelList = appSumPer.getPriorityLevels(CMSCon);
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
        return priorityLevelList;
    }

    public HashMap<String, String> getCardDomains() throws Exception {
        try {
            appSumPer = new ApplicationSummaryPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardDomainList = appSumPer.getCardDomains(CMSCon);
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
        return cardDomainList;
    }

    public HashMap<String, String> getApplicationStatus() throws Exception {
        try {
            appSumPer = new ApplicationSummaryPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            appStatusList = appSumPer.getApplicationStatus(CMSCon);
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
        return appStatusList;
    }

    public List<ApplicationSummaryBean> getApplicationSummary(ApplicationSummaryBean inputBean) throws SQLException, Exception {
        try {
            appSumPer = new ApplicationSummaryPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            searchList = appSumPer.getApplicationSummary(CMSCon, inputBean);
            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            } catch (Exception sqx) {
                throw sqx;
            }
            
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            } catch (Exception sqx) {
                throw sqx;
            }


        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                } catch (Exception e) {
                    throw e;
                } 
            }
        }
        return searchList;
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
