/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.AssignedTasksBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.AssignedTasksDescriptionBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageTaskBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.TaskBean;
import com.epic.cms.admin.controlpanel.sysusermgt.persistance.PageManagementPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mahesh_m
 */
public class PageMgtManager {

    private PageManagementPersistance pagePersis;
    private Connection CMSCon;
    private List<TaskBean> taskBeanList;
    private List<PageBean> pageBeanList;
    private SystemAuditManager sysAuditManager;

    public List<TaskBean> getTaskDescriptionList() throws Exception {
        try {
            pagePersis = new PageManagementPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            taskBeanList = pagePersis.getTaskDetails(CMSCon);

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
        return taskBeanList;
    }

    public List<TaskBean> getTaskDescriptionNotAssignedList(String pageCode) throws Exception {
        try {
            pagePersis = new PageManagementPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            taskBeanList = pagePersis.getNotAssignedTaskDescription(CMSCon, pageCode);

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
        return taskBeanList;
    }

    public List<PageBean> getPageDetails() throws Exception {
        try {
            pageBeanList = new ArrayList<PageBean>();
            pagePersis = new PageManagementPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            pageBeanList = pagePersis.getPageDetails(CMSCon);


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
        return pageBeanList;
    }

    public List<TaskBean> getAssignedtasks(String pageCode) throws Exception {
//           List<AssignedTasksDescriptionBean> assigneTaskDescriptionList = new ArrayList<AssignedTasksDescriptionBean>();
        List<TaskBean> assgnedList = new ArrayList<TaskBean>();
        List<TaskBean> assgnedDescriptionList = new ArrayList<TaskBean>();
        try {

            pagePersis = new PageManagementPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            assgnedList = pagePersis.getAssignedList(CMSCon, pageCode);

            for (int i = 0; i < assgnedList.size(); i++) {
                TaskBean bean = new TaskBean();
                bean = pagePersis.getTaskDescriptionByCode(CMSCon, assgnedList.get(i).getTaskCode());

                assgnedDescriptionList.add(bean);
            }



            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return assgnedDescriptionList;
    }

    public boolean inserPage(PageBean page, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            sysAuditManager = new SystemAuditManager();
            pagePersis = new PageManagementPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            success = pagePersis.insertPage(CMSCon, page);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
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
        return success;
    }

    public boolean inserToPageTask(String pageCode, String[] assigned) throws Exception {
        boolean success = false;
        try {
            pagePersis = new PageManagementPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            for (int i = 0; i < assigned.length; i++) {

                success = pagePersis.insertToPagetask(CMSCon, pageCode, assigned[i]);
                CMSCon.commit();
            }


        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return success;
    }

    public boolean inserToPageTaskUpdate(String pageCode, String[] assigned) throws Exception {
        boolean success = false;
        boolean taskAvailable = false;
        try {
            pagePersis = new PageManagementPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            List<PageTaskBean> pageTaskBeanList = new ArrayList<PageTaskBean>();

            pageTaskBeanList = pagePersis.getPageTaskDetailsByPageCodes(CMSCon, pageCode);

            if (!pageTaskBeanList.isEmpty()) {

                for (int i = 0; i < pageTaskBeanList.size(); i++) {
                    for (int j = 0; j < assigned.length; j++) {
                        if (pageTaskBeanList.get(i).getTaskCode().equals(assigned[j])) {
                            taskAvailable = true;
                            break;
                        }
                    }
                    if (taskAvailable) {
                        continue;
                    } else {
                    }

                }

            } else {

                for (int i = 0; i < assigned.length; i++) {
                    success = pagePersis.insertToPagetask(CMSCon, pageCode, assigned[i]);
                    CMSCon.commit();

                }
            }



        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return success;
    }

    ///////upul////
    /**
     * 
     * @param application
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<PageBean> getAllPagesBySection(String section) throws SQLException, Exception {

        try {
            List<PageBean> sectionPageList = null;
            pagePersis = new PageManagementPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sectionPageList = pagePersis.getAllPagesBySection(CMSCon, section);
            CMSCon.commit();
            return sectionPageList;

        } catch (SQLException ex) {
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

    }

    //////upul////
    /**
     * getAllRemainingSection
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<PageBean> getAllRemainingPages() throws SQLException, Exception {

        try {
            List<PageBean> sectionRemList = null;
            pagePersis = new PageManagementPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sectionRemList = pagePersis.getAllRemainingPages(CMSCon);
            CMSCon.commit();
            return sectionRemList;

        } catch (SQLException ex) {
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

    }

    public boolean updatePage(PageBean page, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            pagePersis = new PageManagementPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            success = pagePersis.updatePage(CMSCon, page);
//            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return success;
    }

    public boolean deletePage(PageBean page, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        List<PageTaskBean> pageTaskList = new ArrayList<PageTaskBean>();
        try {
            pagePersis = new PageManagementPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);


            pageTaskList = pagePersis.getPageTaskDetailsByPageCodes(CMSCon, page.getPageCode());

            if (pagePersis.deletePageTasks(CMSCon, page.getPageCode())) {//delete all records from PAGETASK table

                try {
                    success = pagePersis.deletePage(CMSCon, page);
                    success = true;
                } catch (SQLException e) {//insert deleted data to PAGETASK table(rallback)
                    for (int i = 0; i < pageTaskList.size(); i++) {
                        pagePersis.insertToPagetaskAllData(CMSCon, pageTaskList.get(i));
                    }
                    throw e;
                }
                sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            } else {
                success = false;
            }

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
        return success;
    }

    public List<String> assignPageTask(String pageCode, String[] assignArray, String[] unAssignArray, String sysUser, SystemAuditBean auditBean) throws Exception {
        try {



            List<String> existSecList = new ArrayList<String>();

            int result = -1;
            pagePersis = new PageManagementPersistance();
            //create Db connection
            CMSCon = DBConnection.getConnection();
            //begin transaction
            CMSCon.setAutoCommit(false);



            //unasiign array check wheter there is a item in the array to remove
            if (unAssignArray != null && unAssignArray.length > 0) {



                for (String pageRem : unAssignArray) {
                    //delete all sections
                    try {
                        //check the existance of the removing item in the table 
                        boolean isExist = pagePersis.findExistanceOfTask(CMSCon, pageCode, pageRem);


                        if (isExist) {
                            existSecList.add(pageRem);
                        }

                    } catch (Exception ex) {
                        throw ex;
                    }
                }
            }


            //if there is no exception and success deletion move to insert

  

            if (assignArray != null && assignArray.length > 0) {

                for (String taskCode : assignArray) {
                    PageTaskBean pageTaskBean = new PageTaskBean();
                    pageTaskBean.setPageCode(pageCode);
                    pageTaskBean.setTaskCode(taskCode);

                    try {

                        //check the existance of the removing item in the table 
                        boolean isExist = pagePersis.findExistanceOfTask(CMSCon, pageCode, taskCode);

                        //if not in the table delete item
                        if (!isExist) {
                            //select from
                            //insert all sections to the databese
                            pagePersis.insertToPagetask(CMSCon, pageCode, taskCode);
                        }


                    } catch (Exception sex) {

                        throw sex;
                    }


                }
            }



            CMSCon.commit();

            return existSecList;


        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

    }
    
    
    public void deletePageTask(List<String> existList,String pageCode)throws Exception{
            try {
            List<PageBean> sectionRemList = null;
            pagePersis = new PageManagementPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            
                for (int i = 0; i < existList.size(); i++) {
                    try {
                        System.out.println("task code = "+existList.get(i));
                        System.out.println("page code = "+pageCode);
                        pagePersis.deletePageTasksByTaskCode(CMSCon, existList.get(i), pageCode);
                        
                    } catch (SQLException ex) {
                        throw ex;
                    }
                }
                CMSCon.commit();
           

        } catch (SQLException ex) {
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

      
       
    }
    
}
