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
import com.epic.cms.camm.applicationproccessing.assigndata.persistance.ApplicationAssignPersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author chanuka
 */
public class ApplicationAssignManager {

    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;
    private ApplicationAssignPersistance appAssignPersist;
    private HashMap<String, String> applicationDomainList;
    private List<String> userList = null;
    private HashMap<String, String> priorityLevelList = null;
    private HashMap<String, String> branchesDeatilsList = null;
    private List<UserAssignApplicationBean> userAssignApps = null;
    private List<ApplicationAssignBean> searchList = null;
    private List<ApplicationAssignBean> previousApplicationList = null;
    private HashMap<String, String> identificationList = null;
    private HashMap<String,String> dataCaptureUserList=null;
    String maxId = null;
    String cardCategory=null;

    /**
     * 
     * @return
     * @throws Exception 
     */
    public List<String> getAllUserList(String pageCode) throws Exception {
        try {
            appAssignPersist = new ApplicationAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            userList = appAssignPersist.getAllUsers(CMSCon,pageCode);
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
    //get Assign user list according to application type
    public HashMap<String,String> getAssignUserList(String pageCode,String cat) throws Exception{
        try {
            appAssignPersist = new ApplicationAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            dataCaptureUserList=appAssignPersist.getAssignUserList(CMSCon, pageCode, cat);
            CMSCon.commit();
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        }finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return dataCaptureUserList;
    }
    //get card category to application id
    public String getCardCategoryByApplicationId(String id) throws Exception{
        try {
            appAssignPersist = new ApplicationAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardCategory=appAssignPersist.getCardAppCategoryByApplicationId(CMSCon, id);
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        }finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return cardCategory;
    }
    

    /**
     * 
     * @return
     * @throws Exception 
     */
    public HashMap<String, String> getAllPriorityLevels() throws Exception {
        try {
            appAssignPersist = new ApplicationAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            priorityLevelList = appAssignPersist.getAllPriorityLevels(CMSCon);
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
            appAssignPersist = new ApplicationAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            applicationDomainList = appAssignPersist.getAllApplicationDomain(CMSCon);
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
            appAssignPersist = new ApplicationAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            prefix = appAssignPersist.getApplicationDomainPrefix(CMSCon, domainId);
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
            appAssignPersist = new ApplicationAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            identificationList = appAssignPersist.getAllIdentificationType(CMSCon);
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
            appAssignPersist = new ApplicationAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            branchesDeatilsList = appAssignPersist.getAllBranchesDetails(CMSCon);
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
            appAssignPersist = new ApplicationAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            userAssignApps = appAssignPersist.getAllUserAssignAppsDetails(CMSCon,cardDomain);
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
            appAssignPersist = new ApplicationAssignPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            searchList = appAssignPersist.generalSearch(CMSCon, searchBean);
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
            appAssignPersist = new ApplicationAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            searchList = appAssignPersist.generalSearch(CMSCon, searchBean);
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

            appAssignPersist = new ApplicationAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            previousApplicationList = appAssignPersist.getPreviousApplication(CMSCon, identificationNo);
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

            appAssignPersist = new ApplicationAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            maxId = appAssignPersist.getMaxFromCardApplication(CMSCon, domain);
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

            appAssignPersist = new ApplicationAssignPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            flag = appAssignPersist.insertAssignApplication(CMSCon, assignBean);
            if(assignBean!=null && assignBean.getCardCategory()!=null && assignBean.getCardCategory().equals(StatusVarList.CARD_APP_CATEGORY_ESTABLISMENT_CODE)){
              flag = appAssignPersist.insertEstablishmentKey(CMSCon, assignBean);  
            }
            flag = appAssignPersist.insertAssignStatus(CMSCon, assignBean);
            flag = appAssignPersist.insertApplicationHistory(CMSCon, historyBean);
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

            appAssignPersist = new ApplicationAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            flag = appAssignPersist.insertApplicationHistory(CMSCon, historyBean);

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

            appAssignPersist = new ApplicationAssignPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            flag = appAssignPersist.updateAssignApplication(CMSCon, assignBean);
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
            appAssignPersist = new ApplicationAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            identificationList = appAssignPersist.getAllIdentificationTypeByVerificationCategory(CMSCon,category);
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
    
    
    
    
    
    public HashMap<String, String> getIdentificationTypeByCardCategory(String cardCAtegory,String verifyCategory) throws Exception {
        try {
            appAssignPersist = new ApplicationAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            identificationList = appAssignPersist.getIdentificationTypeByCardCategory(CMSCon,cardCAtegory,verifyCategory);
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

    public boolean checkUniqueEstablishment(String identityNo) throws Exception{
        boolean flag = false;
        try {
            appAssignPersist = new ApplicationAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            flag = appAssignPersist.checkUniqueEstablishment(CMSCon, identityNo);
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
    
}
