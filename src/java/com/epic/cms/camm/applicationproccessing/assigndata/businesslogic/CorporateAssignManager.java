/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.assigndata.businesslogic;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationAssignBean;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationHistoryBean;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.SearchAssignAppBean;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.UserAssignApplicationBean;
import com.epic.cms.camm.applicationproccessing.assigndata.persistance.CorporateAssignPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
/**
 *
 * @author asela
 */
public class CorporateAssignManager {
    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;
    private CorporateAssignPersistance coopAssignPersist;
    private HashMap<String, String> applicationDomainList;
    private List<String> userList = null;
    private HashMap<String, String> priorityLevelList = null;
    private HashMap<String, String> branchesDeatilsList = null;
    private List<UserAssignApplicationBean> userAssignApps = null;
    private List<ApplicationAssignBean> searchList = null;
    private List<ApplicationAssignBean> previousApplicationList = null;
    private HashMap<String, String> identificationList = null;
    String maxId = null;

    /**
     * 
     * @return
     * @throws Exception 
     */
    public List<String> getAllUserList(String pageCode) throws Exception {
        try {
            coopAssignPersist = new CorporateAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            userList = coopAssignPersist.getAllUsers(CMSCon,pageCode);
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
        return userList;
    }

    /**
     * 
     * @return
     * @throws Exception 
     */
    public HashMap<String, String> getAllPriorityLevels() throws Exception {
        try {
            coopAssignPersist = new CorporateAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            priorityLevelList = coopAssignPersist.getAllPriorityLevels(CMSCon);
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

    public HashMap<String, String> getAllApplicationDoamin() throws Exception {
        try {
            coopAssignPersist = new CorporateAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            applicationDomainList = coopAssignPersist.getAllApplicationDomain(CMSCon);
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
        return applicationDomainList;
    }

    public String getApplicationDomainPrefix(String domainId) throws Exception {

        String prefix = null;
        try {
            coopAssignPersist = new CorporateAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            prefix = coopAssignPersist.getApplicationDomainPrefix(CMSCon, domainId);
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
        return prefix;
    }

    /**
     * 
     * @return
     * @throws Exception 
     */
    public HashMap<String, String> getAllIdentificationType() throws Exception {
        try {
            coopAssignPersist = new CorporateAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            identificationList = coopAssignPersist.getAllIdentificationType(CMSCon);
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
        return identificationList;
    }

    /**
     * 
     * @return
     * @throws Exception 
     */
    public HashMap<String, String> getAllBranchesDetails() throws Exception {
        try {
            coopAssignPersist = new CorporateAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            branchesDeatilsList = coopAssignPersist.getAllBranchesDetails(CMSCon);
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
        return branchesDeatilsList;
    }

    public List<UserAssignApplicationBean> getAllUserAssignAppsDetails(String cardDomain) throws Exception {
        try {
            coopAssignPersist = new CorporateAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            userAssignApps = coopAssignPersist.getAllUserAssignAppsDetails(CMSCon,cardDomain);
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
        return userAssignApps;
    }

    public List<ApplicationAssignBean> getAllSearchList(SearchAssignAppBean searchBean, SystemAuditBean systemAuditBean) throws Exception {
        try {
            coopAssignPersist = new CorporateAssignPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            searchList = coopAssignPersist.generalSearch(CMSCon, searchBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }


        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;

        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }


        return searchList;
    }

    public List<ApplicationAssignBean> getAllSearchList(SearchAssignAppBean searchBean) throws Exception {
        try {
            coopAssignPersist = new CorporateAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            searchList = coopAssignPersist.generalSearch(CMSCon, searchBean);
            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }


        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;

        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }


        return searchList;
    }

    public List<ApplicationAssignBean> getPreviousApplication(String identificationNo) throws Exception {
        try {

            coopAssignPersist = new CorporateAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            previousApplicationList = coopAssignPersist.getPreviousApplication(CMSCon, identificationNo);
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }


        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;

        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }


        return previousApplicationList;
    }

    public String getMaxFromCardApplication(String domain) throws Exception {

        try {

            coopAssignPersist = new CorporateAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            maxId = coopAssignPersist.getMaxFromCardApplication(CMSCon, domain);
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;

        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }

        return maxId;
    }

    public boolean insertAssignApplication(ApplicationAssignBean assignBean, ApplicationHistoryBean historyBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;

        try {

            coopAssignPersist = new CorporateAssignPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            flag = coopAssignPersist.insertAssignApplication(CMSCon, assignBean);
            flag = coopAssignPersist.insertAssignStatus(CMSCon, assignBean);
            flag = coopAssignPersist.insertApplicationHistory(CMSCon, historyBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
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
        return flag;
    }

    public boolean insertApplicationHistory(ApplicationHistoryBean historyBean) throws Exception {
        boolean flag = false;

        try {

            coopAssignPersist = new CorporateAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            flag = coopAssignPersist.insertApplicationHistory(CMSCon, historyBean);

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
        return flag;
    }

    public boolean updateAssignApplication(ApplicationAssignBean assignBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;

        try {

            coopAssignPersist = new CorporateAssignPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            flag = coopAssignPersist.updateAssignApplication(CMSCon, assignBean);
//            flag = appAssignPersist.updateApplicationStatus(CMSCon, assignBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
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
        return flag;
    }
    
    public HashMap<String, String> getAllIdentificationTypeByVerificationCategory(String category) throws Exception {
        try {
            coopAssignPersist = new CorporateAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            identificationList = coopAssignPersist.getAllIdentificationTypeByVerificationCategory(CMSCon,category);
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
        return identificationList;
    }    
}
