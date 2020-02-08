/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CreditLimitSchemaUserRoleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.ApplicationModuleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.ApplicationSectionBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageTaskBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SectionBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SectionPageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.TaskBean;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.application.common.persistance.StatusPersistence;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.UserRoleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.UserRolePrivilegeBean;
import java.sql.SQLException;
import com.epic.cms.admin.controlpanel.sysusermgt.persistance.SystemUserRolePersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.variable.OracleMessage;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author chanuka
 */
public class SystemUserRoleManager {

    private Connection CMSCon;
    private SystemUserRolePersistance sysUserPerObj;
    private SystemAuditManager sysAuditManager;
    private StatusPersistence statusPst;
    private List<StatusBean> statusList;
    private List<UserRoleBean> userRoleList;
    private List<UserRolePrivilegeBean> appSecPageTaskList = null;
    private List<ApplicationModuleBean> userRoleAppliacationList = null;
    private List<SectionBean> sectionList = null;
    private List<PageBean> pageList = null;
    private List<TaskBean> taskList = null;
    private List<UserRoleBean> userRoleListUnassignedToSchema = null;
    private List<UserRoleBean> userRoleListAssignedToSchema = null;

    /**
     * this method used to get all the status by status category code
     *
     * @param categoryCode
     * @return statusList-List<StatusBean>
     * @throws Exception
     */
    public List<StatusBean> getStatusByUserRole(String categoryCode) throws Exception {
        try {
            statusPst = new StatusPersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            statusList = statusPst.getStatusByCategoryCode(CMSCon, categoryCode);

            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return statusList;
    }

    /**
     * this method used to delete the user role
     *
     * @param userRoleBean
     * @param systemAuditBean
     * @return success-boolean
     * @throws Exception
     */
    public boolean deleteUserRole(UserRoleBean userRoleBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            sysUserPerObj = new SystemUserRolePersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            success = sysUserPerObj.deleteUserRole(CMSCon, userRoleBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return success;
    }

    /**
     * this method used to get all the user roles
     *
     * @return userRoleList-List<UserRoleBean>
     * @throws Exception
     */
    public List<UserRoleBean> getAllUserRole() throws Exception {
        try {
            sysUserPerObj = new SystemUserRolePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            userRoleList = sysUserPerObj.getAllUserRole(CMSCon);
            CMSCon.commit();
        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return userRoleList;
    }

    /**
     * this method used to insert the user role
     *
     * @param userRoleBean
     * @param systemAuditBean
     * @return success-boolean
     * @throws Exception
     */
    public boolean insertUserRole(UserRoleBean userRoleBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            sysUserPerObj = new SystemUserRolePersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            success = sysUserPerObj.insertUserRole(CMSCon, userRoleBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return success;
    }

    //insert duplicate user role code
    public boolean insertDuplicateUserRole(UserRoleBean userRoleBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        List<ApplicationModuleBean> oldUserRoleApplicationList = null;
        List<SectionBean> oldUserRoleSectionsToApplicationList = null;
        List<List> sectionList = new ArrayList<List>();
        try {
            //get user role applications of old user role cose
            oldUserRoleApplicationList = getUserRoleApplicationByUserRole(userRoleBean.getOldUserRoleCode());
            //get user role sections of old user role cose
            for (ApplicationModuleBean oldUserRoleApplicationList1 : oldUserRoleApplicationList) {
                oldUserRoleSectionsToApplicationList = getSectionByApplicationAndUserRoleCode(userRoleBean.getOldUserRoleCode(), oldUserRoleApplicationList1.getApplicationCode());

                sectionList.add(oldUserRoleSectionsToApplicationList);
            }
//            sectionList.size();

            sysUserPerObj = new SystemUserRolePersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            success = sysUserPerObj.insertDuplicateUserRole(CMSCon, userRoleBean, oldUserRoleApplicationList, sectionList);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return success;
    }

    /**
     * this method used to update the user role
     *
     * @param userRoleBean
     * @param systemAuditBean
     * @return success-boolean
     * @throws Exception
     */
    public boolean updateUserRole(UserRoleBean userRoleBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            sysUserPerObj = new SystemUserRolePersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            success = sysUserPerObj.updateUserRole(CMSCon, userRoleBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return success;
    }

    /**
     * this method used to get all assigned user role tasks,pages,sections and
     * applications
     *
     * @param userRoleCode
     * @return appSecPageTaskList -List<UserRolePrivilegeBean>
     * @throws Exception
     */
    public List<UserRolePrivilegeBean> getTasksByUserRoleCode(String userRoleCode) throws Exception {
        try {
            sysUserPerObj = new SystemUserRolePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            appSecPageTaskList = sysUserPerObj.getTasksByUserRoleCode(CMSCon, userRoleCode);
            CMSCon.commit();
        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return appSecPageTaskList;
    }

    /**
     * this method used to get all assigned user role applications
     *
     * @param userRoleCode
     * @return userRoleAppliacationList-List<ApplicationModuleBean>
     * @throws Exception
     */
    public List<ApplicationModuleBean> getApplicationByUserRole(String userRoleCode) throws Exception {
        try {
            sysUserPerObj = new SystemUserRolePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            userRoleAppliacationList = sysUserPerObj.getUserRoleApplicationByUserRoleCode(CMSCon, userRoleCode);
            CMSCon.commit();
        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return userRoleAppliacationList;
    }

    /**
     * this method used to get all unassigned user role applications
     *
     * @param userRoleCode
     * @return userRoleAppliacationList-List<ApplicationModuleBean>
     * @throws Exception
     */
    public List<ApplicationModuleBean> getApplicationNotAssignByUserRole(String userRoleCode) throws Exception {
        try {
            sysUserPerObj = new SystemUserRolePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            userRoleAppliacationList = sysUserPerObj.getUserRoleApplicationNotAssignByUserRoleCode(CMSCon, userRoleCode);
            CMSCon.commit();
        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return userRoleAppliacationList;
    }

    /**
     * this method used to insert all assigned user role applications
     *
     * @param userRoleCode
     * @param assignArray
     * @param sysUser
     * @throws Exception
     */
    public void assignUserRoleApplication(String userRoleCode, String[] assignArray, String sysUser, SystemAuditBean systemAuditBean) throws Exception {
        try {
            sysUserPerObj = new SystemUserRolePersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            for (String string : assignArray) {
                ApplicationModuleBean userRoleApplication = new ApplicationModuleBean();
                userRoleApplication.setApplicationCode(string);
                userRoleApplication.setUserRoleCode(userRoleCode);
                userRoleApplication.setLastUpdateuser(sysUser);
                userRoleApplication.setCreatedTime(new Date());
                try {
                    sysUserPerObj.insertUserRoleApplication(CMSCon, userRoleApplication);
                } catch (Exception ex) {

                    if (!OracleMessage.getMessege(ex.getMessage()).equals(OracleMessage.ALLREADY_ADD)) {
                        throw ex;
                    }

                }

            }
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

    }

    /**
     * this method used to delete all unassigned user role applications
     *
     * @param userRoleCode
     * @param notassignArray
     * @throws Exception
     */
    public void unassignUserRoleApplication(String userRoleCode, String[] notassignArray, SystemAuditBean systemAuditBean) throws Exception {
        int resultId = -1;
        try {
            sysUserPerObj = new SystemUserRolePersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            for (String string : notassignArray) {
                ApplicationModuleBean userRoleApplication = new ApplicationModuleBean();
                userRoleApplication.setApplicationCode(string);
                userRoleApplication.setUserRoleCode(userRoleCode);
                resultId = sysUserPerObj.deleteUserRoleApplication(CMSCon, userRoleApplication);
            }
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

    }

    /**
     * this method used to get all assigned user role applications
     *
     * @param userRoleCode
     * @return userRoleAppliacationList-List<ApplicationModuleBean>
     * @throws Exception
     */
    public List<ApplicationModuleBean> getUserRoleApplicationByUserRole(String userRoleCode) throws Exception {
        try {
            sysUserPerObj = new SystemUserRolePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            userRoleAppliacationList = sysUserPerObj.getApplicationByUserRoleCode(CMSCon, userRoleCode);
            CMSCon.commit();
        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return userRoleAppliacationList;
    }

    /**
     * this method used to get all assigned user role application sections
     *
     * @param userRoleCode
     * @param ApplicationCode
     * @return sectionList-List<SectionBean>
     * @throws Exception
     */
    public List<SectionBean> getSectionByApplicationAndUserRoleCode(String userRoleCode, String ApplicationCode) throws Exception {
        try {
            sysUserPerObj = new SystemUserRolePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            if (!ApplicationCode.equals("") || ApplicationCode.equals(null)) {
                sectionList = sysUserPerObj.getSectionByApplicationAndUserRole(CMSCon, userRoleCode, ApplicationCode);

            }
            CMSCon.commit();
        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return sectionList;
    }

    /**
     * this method used to get all unassigned user role application sections
     *
     * @param userRoleCode
     * @param ApplicationCode
     * @return sectionList-List<SectionBean>
     * @throws Exception
     */
    public List<SectionBean> getSectionByApplicationAndUserRoleNotAssign(String userRoleCode, String ApplicationCode) throws Exception {
        try {
            sysUserPerObj = new SystemUserRolePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            if (!ApplicationCode.equals("") || ApplicationCode.equals(null)) {
                sectionList = sysUserPerObj.getSectionByApplicationAndUserRoleNotAssign(CMSCon, userRoleCode, ApplicationCode);
            }
            CMSCon.commit();
        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return sectionList;
    }

    /**
     * this method used to insert all assigned user role application sections
     *
     * @param userRoleCode
     * @param applicationCode
     * @param assignArray
     * @param sysUser
     * @throws Exception
     */
    public void assignUserRoleApplicationSection(String userRoleCode, String applicationCode, String[] assignArray, String sysUser, SystemAuditBean systemAuditBean) throws Exception {
        try {
            sysUserPerObj = new SystemUserRolePersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            for (String section : assignArray) {

                ApplicationSectionBean applicationSection = new ApplicationSectionBean();

                applicationSection.setUserRoleCode(userRoleCode);
                applicationSection.setApplicationCode(applicationCode);
                applicationSection.setSectionCode(section);
                applicationSection.setLastUpdatedUser(sysUser);
                applicationSection.setCreatedTime(new Date());
                try {
                    sysUserPerObj.insertUserRoleApplicationSections(CMSCon, applicationSection);
                } catch (Exception ex) {

                    if (!OracleMessage.getMessege(ex.getMessage()).equals(OracleMessage.ALLREADY_ADD)) {
                        throw ex;
                    }

                }

            }
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

    }

    /**
     * this method used to delete all unassigned user role application sections
     *
     * @param userRoleCode
     * @param applicationCode
     * @param notAssignArray
     * @throws Exception
     */
    public void UnAssignUserRoleApplicationSection(String userRoleCode, String applicationCode, String[] notAssignArray, SystemAuditBean systemAuditBean) throws Exception {
        try {
            sysUserPerObj = new SystemUserRolePersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            for (String section : notAssignArray) {

                ApplicationSectionBean applicationSection = new ApplicationSectionBean();

                applicationSection.setApplicationCode(applicationCode);
                applicationSection.setUserRoleCode(userRoleCode);
                applicationSection.setSectionCode(section);
                sysUserPerObj.deleteUserRoleApplcationSection(CMSCon, applicationSection);

            }
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

    }

    /**
     * this method used to get all assigned user role section pages
     *
     * @param userRoleCode
     * @param sectionCode
     * @return pageList-List<PageBean>
     * @throws Exception
     */
    public List<PageBean> getPageBySectionAndUserRoleCode(String userRoleCode, String sectionCode) throws Exception {
        try {
            sysUserPerObj = new SystemUserRolePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            if (!sectionCode.equals("") || sectionCode.equals(null)) {
                pageList = sysUserPerObj.getPageBySectionAndUserRole(CMSCon, userRoleCode, sectionCode);

            }
            CMSCon.commit();
        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return pageList;
    }

    /**
     * this method used to get all unassigned user role section pages
     *
     * @param userRoleCode
     * @param sectionCode
     * @return pageList-List<PageBean>
     * @throws Exception
     */
    public List<PageBean> getPageBySectionAndUserRoleNotAssign(String userRoleCode, String sectionCode) throws Exception {
        try {
            sysUserPerObj = new SystemUserRolePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            if (!sectionCode.equals("") || sectionCode.equals(null)) {
                pageList = sysUserPerObj.getPageBySectionAndUserRoleNotAssign(CMSCon, userRoleCode, sectionCode);
            }
            CMSCon.commit();
        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return pageList;
    }

    /**
     * this method used to insert all assigned user role section pages
     *
     * @param userRoleCode
     * @param sectionCode
     * @param assignArray
     * @param sysUser
     * @throws Exception
     */
    public void assignUserRoleSectionPages(String userRoleCode, String sectionCode, String[] assignArray, String sysUser, SystemAuditBean systemAuditBean) throws Exception {
        try {
            sysUserPerObj = new SystemUserRolePersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            for (String page : assignArray) {

                SectionPageBean sectionPageBean = new SectionPageBean();

                sectionPageBean.setUserRoleCode(userRoleCode);
                sectionPageBean.setSectionCode(sectionCode);
                sectionPageBean.setPageCode(page);
                sectionPageBean.setLasUpdatedUser(sysUser);
                sectionPageBean.setCreatedTime(new Date());
                try {
                    sysUserPerObj.insertUserRoleSectionPages(CMSCon, sectionPageBean);
                } catch (Exception ex) {

                    if (!OracleMessage.getMessege(ex.getMessage()).equals(OracleMessage.ALLREADY_ADD)) {
                        throw ex;
                    }

                }

            }
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

    }

    /**
     * this method used to delete all unassigned user role section pages
     *
     * @param userRoleCode
     * @param sectionCode
     * @param notAssignArray
     * @throws Exception
     */
    public void UnAssignUserRoleSectionPages(String userRoleCode, String sectionCode, String[] notAssignArray, SystemAuditBean systemAuditBean) throws Exception {
        try {
            sysUserPerObj = new SystemUserRolePersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            for (String page : notAssignArray) {

                SectionPageBean sectionPageBean = new SectionPageBean();

                sectionPageBean.setSectionCode(sectionCode);
                sectionPageBean.setUserRoleCode(userRoleCode);
                sectionPageBean.setPageCode(page);
                sysUserPerObj.deleteUserRoleSectionPages(CMSCon, sectionPageBean);

            }
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

    }

    /**
     * this method used to get all assigned user role page tasks
     *
     * @param userRoleCode
     * @param pageCode
     * @return taskList-List<TaskBean>
     * @throws Exception
     */
    public List<TaskBean> getTaskByPageAndUserRoleCode(String userRoleCode, String pageCode) throws Exception {
        try {
            sysUserPerObj = new SystemUserRolePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            if (!pageCode.equals("") || pageCode.equals(null)) {
                taskList = sysUserPerObj.getTaskByPageAndUserRole(CMSCon, userRoleCode, pageCode);

            }
            CMSCon.commit();
        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return taskList;
    }

    /**
     * this method used get all unassigned user role page tasks
     *
     * @param userRoleCode
     * @param pageCode
     * @return taskList-List<TaskBean>
     * @throws Exception
     */
    public List<TaskBean> getTaskByPageAndUserRoleNotAssign(String userRoleCode, String pageCode) throws Exception {
        try {
            sysUserPerObj = new SystemUserRolePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            if (!pageCode.equals("") || pageCode.equals(null)) {
                taskList = sysUserPerObj.getTaskByPageAndUserRoleNotAssign(CMSCon, userRoleCode, pageCode);
            }
            CMSCon.commit();
        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return taskList;
    }

    /**
     * this method used to insert all assigned user role page tasks
     *
     * @param userRoleCode
     * @param pageCode
     * @param assignArray
     * @param sysUser
     * @throws Exception
     */
    public void assignUserRolePageTasks(String userRoleCode, String pageCode, String[] assignArray, String sysUser, SystemAuditBean systemAuditBean) throws Exception {
        try {
            sysUserPerObj = new SystemUserRolePersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            for (String task : assignArray) {

                PageTaskBean pageTaskBean = new PageTaskBean();

                pageTaskBean.setUserRoleCode(userRoleCode);
                pageTaskBean.setPageCode(pageCode);
                pageTaskBean.setTaskCode(task);
                pageTaskBean.setLastUpdatedUser(sysUser);
//                pageTaskBean.setCreatedTime(new Date());
                try {
                    sysUserPerObj.insertUserRolePageTasks(CMSCon, pageTaskBean);
                } catch (Exception ex) {

                    if (!OracleMessage.getMessege(ex.getMessage()).equals(OracleMessage.ALLREADY_ADD)) {
                        throw ex;
                    }

                }

            }
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

    }

    /**
     * this method used to delete all unassigned user role page tasks
     *
     * @param userRoleCode
     * @param pageCode
     * @param notAssignArray
     * @throws Exception
     */
    public void UnAssignUserRolePageTasks(String userRoleCode, String pageCode, String[] notAssignArray, SystemAuditBean systemAuditBean) throws Exception {
        try {
            sysUserPerObj = new SystemUserRolePersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            for (String task : notAssignArray) {

                PageTaskBean pageTaskBean = new PageTaskBean();

                pageTaskBean.setPageCode(pageCode);
                pageTaskBean.setUserRoleCode(userRoleCode);
                pageTaskBean.setTaskCode(task);
                sysUserPerObj.deleteUserRolePageTasks(CMSCon, pageTaskBean);

            }
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

    }

    /**
     * this method used to get all unassigned user role schema
     *
     * @param schemaCode
     * @return userRoleListUnassignedToSchema
     * @throws Exception
     */
    public List<UserRoleBean> getUnassignedUserRolesToSchema(String schemaCode) throws Exception {
        try {
            sysUserPerObj = new SystemUserRolePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            userRoleListUnassignedToSchema = sysUserPerObj.getUnassignedUserRolesToSchema(CMSCon, schemaCode);
            CMSCon.commit();
        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return userRoleListUnassignedToSchema;
    }

    /**
     * this method used to get all assigned user role schema
     *
     * @param schemaCode
     * @return userRoleListAssignedToSchema
     * @throws Exception
     */
    public List<UserRoleBean> getAssignedUserRolesToSchema(String schemaCode) throws Exception {
        try {
            sysUserPerObj = new SystemUserRolePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            userRoleListAssignedToSchema = sysUserPerObj.getAssignedUserRolesToSchema(CMSCon, schemaCode);
            CMSCon.commit();
        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return userRoleListAssignedToSchema;
    }

    /**
     * this method used to insert all assigned user role applications
     *
     * @param schema
     * @param assignArray
     * @param sysUser
     * @throws Exception
     */
    public void assignUserRolesToSchema(String schema, String[] assignArray, String sysUser, SystemAuditBean systemAuditBean) throws Exception {
        try {
            sysUserPerObj = new SystemUserRolePersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            for (String string : assignArray) {
                CreditLimitSchemaUserRoleBean bean = new CreditLimitSchemaUserRoleBean();
                bean.setSchemaCode(schema);
                bean.setUserRoleCode(string);
                bean.setStatus(StatusVarList.ACTIVE_STATUS);
                bean.setCreatedDate(new Date());
                bean.setLastUpdatedDate(new Date());
                bean.setLastUpdatedUser(sysUser);
                try {
                    sysUserPerObj.insertSchemaToUserRole(CMSCon, bean);
                } catch (Exception ex) {

                    if (!OracleMessage.getMessege(ex.getMessage()).equals(OracleMessage.ALLREADY_ADD)) {
                        throw ex;
                    }

                }

            }
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

    }
     /**
     * this method used to delete all unassigned user role to schema
     *
     * @param userRoleCode
     * @param notassignArray
     * @throws Exception
     */
    public void unassignUserRolesToSchema(String schema, String[] notassignArray, SystemAuditBean systemAuditBean) throws Exception {
        int resultId = -1;
        try {
            sysUserPerObj = new SystemUserRolePersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            for (String string : notassignArray) {
                CreditLimitSchemaUserRoleBean bean = new CreditLimitSchemaUserRoleBean();
                bean.setSchemaCode(schema);
                bean.setUserRoleCode(string);
                resultId = sysUserPerObj.deleteSchemaUserRole(CMSCon, bean);
            }
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

    }


}
