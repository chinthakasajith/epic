/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.ApplicationModuleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.ApplicationSectionPageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.persistance.ApplicationSectionPagePersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.variable.TaskVarList;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author upul
 */
public class ApplicationSectionPageManager {

    private Connection CMSCon;
    private List<ApplicationSectionPageBean> sectionPageList;
    private ApplicationSectionPageBean sectionPageBean;
    private ApplicationSectionPagePersistance sectionPagePersistance;
    private List<ApplicationModuleBean> applicationModuleList;
    private SystemAuditManager sysAuditManager;

    /**
     * find section page by application code
     * @param applicationCode
     * @return
     * @throws Exception 
     */
    public List<ApplicationSectionPageBean> getSectionPageByApplicationCode(String applicationCode) throws Exception {
        try {
            sectionPagePersistance = new ApplicationSectionPagePersistance();
            //create Db connection
            CMSCon = DBConnection.getConnection();
            //begin transaction
            CMSCon.setAutoCommit(false);
            //get result from persistance 
            sectionPageList = sectionPagePersistance.findSectionPageByApplicationCode(CMSCon, applicationCode);
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
        return sectionPageList;
    }

    /**
     * get application module list in active status
     * @return
     * @throws Exception 
     */
    public List<ApplicationModuleBean> getApplicationModuleList() throws Exception {
        try {
            //create Db connection
            CMSCon = DBConnection.getConnection();
            //begin transaction
            CMSCon.setAutoCommit(false);
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
        return applicationModuleList;
    }

    /**
     * assignApplicationSection
     * @param application
     * @param assignArray
     * @param sysUser
     * @param auditBean
     * @return
     * @throws Exception 
     */
    public List<String> assignApplicationSection(String application, String[] assignArray, String[] unAassignArray, String sysUser, SystemAuditBean auditBean) throws Exception {
        try {
            List<String> existSecList = new ArrayList<String>();
            int result = -1;
            sectionPagePersistance = new ApplicationSectionPagePersistance();
            sysAuditManager = new SystemAuditManager();
            //create Db connection
            CMSCon = DBConnection.getConnection();
            //begin transaction
            CMSCon.setAutoCommit(false);
            //unasiign array check wheter there is a item in the array to remove
            if (unAassignArray != null && unAassignArray.length > 0) {
                for (String sectionRem : unAassignArray) {
                    //delete all sections
                    try {
                        //check the existance of the removing item in the table 
                        boolean isExist = sectionPagePersistance.findExistanceOfUserApplicationSection(CMSCon, application, sectionRem);
                        //if not in the table delete item
                        if (!isExist) {
                            //check the existance of the removing item in the table 
                            isExist = sectionPagePersistance.findExistanceOfApplicationSection(CMSCon, application, sectionRem);
                            //if not in the table delete item
                            if (isExist) {
                                //delete application section list     
                                result = sectionPagePersistance.deleteApplicationSection(CMSCon, application, sectionRem);
                                auditBean.setDescription("Delete section :" + sectionRem + "  from Application " + application + " by : " + auditBean.getLastUpdateduser());
                                auditBean.setTaskCode(TaskVarList.DELETE);
                                //insert a value to audittrace
                                sysAuditManager.insertAudit(auditBean, CMSCon);
                            }
                            //else cannot delete and return those items to the servlet
                        } else {
                            existSecList.add(sectionRem);
                        }
                    } catch (Exception ex) {
                        throw ex;
                    }
                }
            }
            //if there is no exception and success deletion move to insert
            if (result == -1 || result == 1) {
                if (assignArray != null && assignArray.length > 0) {
                    for (String section : assignArray) {
                        ApplicationSectionPageBean sectionPageBean = new ApplicationSectionPageBean();
                        sectionPageBean.setSectionCode(section);
                        sectionPageBean.setApplicationCode(application);
                        try {
                            //check the existance of the removing item in the table 
                            boolean isExist = sectionPagePersistance.findExistanceOfApplicationSection(CMSCon, application, section);
                            //if not in the table delete item
                            if (!isExist) {
                                //select from
                                //insert all sections to the databese
                                result = sectionPagePersistance.insertApplicationSection(CMSCon, sectionPageBean,sysUser);

                                auditBean.setDescription("Insert section :" + section + "  to Application " + application + " by : " + auditBean.getLastUpdateduser());
                                auditBean.setTaskCode(TaskVarList.ADD);
                                //insert a value to audittrace
                                sysAuditManager.insertAudit(auditBean, CMSCon);

                            }
                            //if result !=1 throw exception and break
                            if (result != 1 && result != -1) {
                                throw new SQLException();
                            }
                        } catch (Exception sex) {
                            throw sex;
                        }
                    }
                }
            }
            CMSCon.commit();
            return existSecList;
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
    }

    /**
     * assignSectionPage
     * @param section
     * @param assignArray
     * @param sysUser
     * @param auditBean
     * @return
     * @throws Exception 
     */
    public List<String> assignSectionPage(String section, String[] assignArray, String[] unAssignArray, String sysUser, SystemAuditBean auditBean) throws Exception {
        try {
            List<String> existSecList = new ArrayList<String>();
            int result = -1;
            sectionPagePersistance = new ApplicationSectionPagePersistance();
            //create Db connection
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            //begin transaction
            CMSCon.setAutoCommit(false);
            //unasiign array check wheter there is a item in the array to remove
            if (unAssignArray != null && unAssignArray.length > 0) {
                for (String pageRem : unAssignArray) {
                    //delete all sections
                    try {
                        //check the existance of the removing item in the table 
                        boolean isExist = sectionPagePersistance.findExistanceOfUserSectionPage(CMSCon, section, pageRem);
                        //if not in the table delete item
                        if (!isExist) {
                            //check the existance of the removing item in the table 
                            isExist = sectionPagePersistance.findExistanceOfSectionPage(CMSCon, section, pageRem);

                            //if not in the table delete item
                            if (isExist) {
                                result = sectionPagePersistance.deleteSectionPage(CMSCon, section, pageRem);
                                auditBean.setDescription("Delete page :" + pageRem + "  from section " + section + " by : " + auditBean.getLastUpdateduser());
                                auditBean.setTaskCode(TaskVarList.DELETE);
                                //insert a value to audittrace
                                sysAuditManager.insertAudit(auditBean, CMSCon);
                                //else cannot delete and return those items to the servlet
                            }
                        } else {
                            existSecList.add(pageRem);
                        }
                    } catch (Exception ex) {
                        throw ex;
                    }
                }
            }
            //if there is no exception and success deletion move to insert
            if (result == -1 || result == 1) {
                if (assignArray != null && assignArray.length > 0) {
                    for (String page : assignArray) {
                        ApplicationSectionPageBean sectionPageBean = new ApplicationSectionPageBean();
                        sectionPageBean.setSectionCode(section);
                        sectionPageBean.setPageCode(page);
                        try {
                            //check the existance of the removing item in the table 
                            boolean isExist = sectionPagePersistance.findExistanceOfSectionPage(CMSCon, section, page);
                            //if not in the table delete item
                            if (!isExist) {
                                //select from
                                //insert all sections to the databese
                                result = sectionPagePersistance.insertSectionPage(CMSCon, sectionPageBean,sysUser);
                                auditBean.setDescription("Insert Page :" + page + "  to section " + section + " by : " + auditBean.getLastUpdateduser());
                                auditBean.setTaskCode(TaskVarList.ADD);
                                //insert a value to audittrace
                                sysAuditManager.insertAudit(auditBean, CMSCon);

                            }
                            //if result !=1 throw exception and break
                            if (result != 1 && result != -1) {
                                throw new SQLException();
                            }
                        } catch (Exception sex) {
                            throw sex;
                        }
                    }
                }
            }
            CMSCon.commit();
            return existSecList;
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


    }
}
