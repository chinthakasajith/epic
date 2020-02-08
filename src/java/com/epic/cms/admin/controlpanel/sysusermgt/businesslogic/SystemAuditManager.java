/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SectionBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.TaskBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.UserRoleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.persistance.PageManagementPersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.persistance.SectionMgtPersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.persistance.SystemAuditPersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.persistance.SystemUserRolePersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.persistance.TaskManagementPersistance;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.application.common.persistance.StatusPersistence;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author upul
 */
public class SystemAuditManager {

    private SystemAuditPersistance perSystemAudit;
    private SystemUserRolePersistance perUserRole;
    private SectionMgtPersistance perSection;
    private PageManagementPersistance perPage;
    private List<SectionBean> sectionList;
    private List<UserRoleBean> userRoleList;
    private List<PageBean> pageList;
    private TaskManagementPersistance perTask;
    private List<TaskBean> taskList;
    private List<StatusBean> statusList;
    private StatusPersistence perStatus;
    private List<SystemAuditBean> searchList;
    private List<SystemAuditBean> systemAuditList;
    private Connection CMSCon;

    /**
     * inser audittrace when doing a task
     * @param auditBean
     * @param con
     * @return
     * @throws Exception 
     */
    public int insertAudit(SystemAuditBean auditBean, Connection con) throws Exception {
        int resultId = -1;
        try {

            perSystemAudit = new SystemAuditPersistance();

            //get connection from other method
            resultId = perSystemAudit.insertRECSystemAudit(con, auditBean);


        } catch (Exception ex) {

            throw ex;
        } finally {
        }
        return resultId;
    }
    public int insertLoginAudit(SystemAuditBean auditBean, Connection con) throws Exception {
        int resultId = -1;
        try {

            perSystemAudit = new SystemAuditPersistance();

            //get connection from other method
            resultId = perSystemAudit.insertRECSystemLoginAudit(con, auditBean);


        } catch (Exception ex) {

            throw ex;
        } finally {
        }
        return resultId;
    }

    /**
     * get all system audit detail form systemaudit  table
     * @return
     * @throws Exception 
     */
    public List<SystemAuditBean> getAllSystemAudit() throws Exception {
        try {
            CMSCon = DBConnection.getConnection();
            perSystemAudit = new SystemAuditPersistance();

            CMSCon.setAutoCommit(false);

            //////systemAuditList = perSystemAudit.getAllRECSystemAudit(CMSCon);



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


        return systemAuditList;
    }

    /**
     * getAllUserRole from userrole persistance
     * @return
     * @throws Exception 
     */
    public List<UserRoleBean> getAllUserRole() throws Exception {
        try {
            CMSCon = DBConnection.getConnection();
            perUserRole = new SystemUserRolePersistance();
            perUserRole.getAllUserRole(CMSCon);
            CMSCon.setAutoCommit(false);


            userRoleList = perUserRole.getAllUserRole(CMSCon);


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


        return userRoleList;
    }

    /**
     * getAllSection from section persistance
     * @return
     * @throws Exception 
     */
    public List<SectionBean> getAllSection() throws Exception {
        try {
            CMSCon = DBConnection.getConnection();
            perSection = new SectionMgtPersistance();
            CMSCon.setAutoCommit(false);

            sectionList = perSection.getAllSection(CMSCon);

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


        return sectionList;
    }

    /**
     * getAllPage from pagepaersistance
     * @return
     * @throws Exception 
     */
    public List<PageBean> getAllPage() throws Exception {
        try {
            CMSCon = DBConnection.getConnection();
            perPage = new PageManagementPersistance();
            CMSCon.setAutoCommit(false);

            pageList = perPage.getPageDetails(CMSCon);

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


        return pageList;
    }

    /**
     * getAllTask from task table
     * @return
     * @throws Exception 
     */
    public List<TaskBean> getAllTask() throws Exception {
        try {
            CMSCon = DBConnection.getConnection();
            perTask = new TaskManagementPersistance();
            CMSCon.setAutoCommit(false);

            taskList = perTask.gettaskDetails(CMSCon);

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

        return taskList;
    }

    /**
     * getAllSearchList
     * @param sysaudit
     * @param fromDate
     * @param toDate
     * @return
     * @throws Exception 
     */
    public List<SystemAuditBean> getAllSearchList(SystemAuditBean sysaudit, String fromDate, String toDate) throws Exception {
        try {
            CMSCon = DBConnection.getConnection();
            perSystemAudit = new SystemAuditPersistance();
            CMSCon.setAutoCommit(false);

            searchList = perSystemAudit.generalSearch(CMSCon, sysaudit, fromDate, toDate);

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

    public int getCount(String condition) throws Exception {
        int count;
        try {
            perSystemAudit = new SystemAuditPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            count = perSystemAudit.getCount(CMSCon, condition);
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
        return count;
    }

    public List<SystemAuditBean> getAuditDetails(String condition, int start, int end) throws Exception {

        try {
            perSystemAudit = new SystemAuditPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            searchList = perSystemAudit.getAuditDetails(CMSCon, condition, start, end);
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
        return searchList;
    }

    public SystemAuditBean viewOneAuditRecord(String auditId) throws Exception {
        SystemAuditBean resultAudit;
        try {
            perSystemAudit = new SystemAuditPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            resultAudit = perSystemAudit.viewOneAuditRecord(CMSCon,auditId);
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
        return resultAudit;
    }
}
