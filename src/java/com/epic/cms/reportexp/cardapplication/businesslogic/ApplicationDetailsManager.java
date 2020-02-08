/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationCustomerEmploymentBean;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationCustomerPersonalBean;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationDetailsBean;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationHistoryBean;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationIncomeBean;
import com.epic.cms.reportexp.cardapplication.persistance.ApplicationDetailsPersistance;
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
public class ApplicationDetailsManager {

    private Connection CMSCon;
    private ApplicationDetailsPersistance appSummeryPer = null;
    private HashMap<String, String> bnkBranches = null;
    private HashMap<String, String> priorityLevelList = null;
    private HashMap<String, String> applicationStatusList = null;
    private HashMap<String, String> domainList = null;
    private List<ApplicationDetailsBean> summeryList = null;
    private List<ApplicationHistoryBean> historyList = null;
    private ApplicationDetailsBean summeryBean = null;
    private ApplicationDetailsBean applicationBean = null;
    private ApplicationCustomerPersonalBean personalBean = null;
    private ApplicationCustomerEmploymentBean employeeBean = null;
    private List<ApplicationIncomeBean> incomeDetailList = null;
    private SystemAuditManager sysAuditManager = null;
    private static ApplicationDetailsManager setInstance;

    public synchronized static ApplicationDetailsManager getInstance() {
        if (setInstance == null) {
            setInstance = new ApplicationDetailsManager();
        }
        return setInstance;
    }

    public HashMap<String, String> getBranchNames() throws Exception {
        try {

            appSummeryPer = new ApplicationDetailsPersistance();
            bnkBranches = new HashMap<String, String>();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bnkBranches = appSummeryPer.getBranchNames(CMSCon);
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
        return bnkBranches;
    }

    public HashMap<String, String> getAllPriorityLevels() throws Exception {
        try {
            appSummeryPer = new ApplicationDetailsPersistance();
            priorityLevelList = new HashMap<String, String>();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            priorityLevelList = appSummeryPer.getAllPriorityLevels(CMSCon);
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

    public HashMap<String, String> getStatusList(String category) throws Exception {
        try {
            appSummeryPer = new ApplicationDetailsPersistance();
            applicationStatusList = new HashMap<String, String>();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            applicationStatusList = appSummeryPer.getStatusList(CMSCon, category);
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
        return applicationStatusList;
    }

    public HashMap<String, String> getAllDomainList() throws Exception {
        try {
            appSummeryPer = new ApplicationDetailsPersistance();
            domainList = new HashMap<String, String>();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            domainList = appSummeryPer.getAllDomainList(CMSCon);

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
        return domainList;
    }

    public List<ApplicationDetailsBean> searchApplicationSummeryReport(ApplicationDetailsBean summeryBean) throws Exception {

        try {
            appSummeryPer = new ApplicationDetailsPersistance();
            summeryList = new ArrayList<ApplicationDetailsBean>();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            summeryList = appSummeryPer.searchApplicationSummeryReport(CMSCon, summeryBean);

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
        return summeryList;
    }

    public List<ApplicationHistoryBean> getApplicationHistory(String applicationID) throws Exception {

        try {

            appSummeryPer = new ApplicationDetailsPersistance();
            historyList = new ArrayList<ApplicationHistoryBean>();

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            historyList = appSummeryPer.getApplicationHistory(CMSCon, applicationID);

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
        return historyList;
    }

    public ApplicationDetailsBean getSearchDetailsDescription(ApplicationDetailsBean detailsBean) throws Exception {

        try {

            appSummeryPer = new ApplicationDetailsPersistance();
            summeryBean = new ApplicationDetailsBean();

            summeryBean = detailsBean;

            if (!detailsBean.getBranch().equals("")) {

                summeryBean = appSummeryPer.getBranchDescription(CMSCon, detailsBean);
            }
            if (!detailsBean.getPriorityLevel().equals("")) {

                summeryBean = appSummeryPer.getPriorityLevelDescription(CMSCon, summeryBean);
            }
            if (!detailsBean.getApplicationStatus().equals("")) {

                summeryBean = appSummeryPer.getStatusDescription(CMSCon, summeryBean);
            }
            if (!detailsBean.getDomain().equals("")) {

                summeryBean = appSummeryPer.getDomainDescription(CMSCon, summeryBean);
            }

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);


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
        return summeryBean;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public ApplicationDetailsBean getApplicationCardDetails(String applicationId) throws Exception {
        try {
            appSummeryPer = new ApplicationDetailsPersistance();
            applicationBean = new ApplicationDetailsBean();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            applicationBean = appSummeryPer.getApplicationCardDetails(CMSCon, applicationId);


            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return applicationBean;
    }

    public ApplicationCustomerPersonalBean getPersonalDetails(String applicationId) throws Exception {
        try {
            appSummeryPer = new ApplicationDetailsPersistance();
            personalBean = new ApplicationCustomerPersonalBean();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            personalBean = appSummeryPer.getPersonalDetails(CMSCon, applicationId);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return personalBean;
    }

    public ApplicationCustomerEmploymentBean getApplicationEmployementDetails(String applicationId) throws Exception {
        try {
            appSummeryPer = new ApplicationDetailsPersistance();
            employeeBean = new ApplicationCustomerEmploymentBean();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            employeeBean = appSummeryPer.getApplicationEmployementDetails(CMSCon, applicationId);


            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return employeeBean;
    }

    public List<ApplicationIncomeBean> getIncomeDetails(String applicationId) throws Exception {
        try {
            appSummeryPer = new ApplicationDetailsPersistance();
            incomeDetailList = new ArrayList<ApplicationIncomeBean>();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            incomeDetailList = appSummeryPer.getIncomeDetails(CMSCon, applicationId);


            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return incomeDetailList;
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
    //////////////////////////////////////////////////////////////////////////////////////////////////////
}
