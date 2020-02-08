//<<<<<<< .mine
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.ApplicationModuleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.persistance.ApplicationModuleMgtPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *this class use for wrote all the methods that relate to application module process
 * @author ayesh
 */
public class ApplicationModuleMgtManager {

    private Connection CMSCon;
    private ApplicationModuleMgtPersistance appPerObj = null;
    private SystemAuditManager sysAuditManager;

    /**
     * get all details about application table
     * @return  List<ApplicationModuleBean>
     */
    ///used by upul also
    public List<ApplicationModuleBean> getAllApplicationList() throws SQLException, Exception {

        List<ApplicationModuleBean> allList = null;
        try {
            appPerObj = new ApplicationModuleMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            allList = appPerObj.getAllAppLication(CMSCon);
            CMSCon.commit();
            return allList;


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

    /**
     * check if given appcode already in table
     * @param appCode
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public boolean checkExistApp(String appCode) throws SQLException, Exception {
        boolean flag = false;
        try {
            appPerObj = new ApplicationModuleMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            flag = appPerObj.checkAppAvailable(CMSCon, appCode);
            CMSCon.commit();
            return flag;

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

    /**
     * add new application manager
     * @param bean - ApplicationModuleBean
     * @return int - number of rows that add to database
     * @throws SQLException
     * @throws Exception 
     */
    public int addApplication(ApplicationModuleBean bean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        int i = -1;
        try {
            appPerObj = new ApplicationModuleMgtPersistance();
            sysAuditManager = new SystemAuditManager();

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            i = appPerObj.addNewApplictionModule(CMSCon, bean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            return i;
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

    /**
     * update application management 
     * @param sectionBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int UpdateApp(ApplicationModuleBean appBean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        int i = -1;
        try {
            appPerObj = new ApplicationModuleMgtPersistance();
            sysAuditManager = new SystemAuditManager();

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            i = appPerObj.updateApplication(CMSCon, appBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
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
        return i;
    }

    /**
     * delete row with correspond to application id
     * @param sectionID
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteApplication(String appID, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        int rowCount = -1;
        try {
            appPerObj = new ApplicationModuleMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysAuditManager = new SystemAuditManager();
            
            rowCount = appPerObj.deleteApplication(CMSCon, appID);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
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
        return rowCount;
    }

    /////////upul////////////////////////
    /**
     * getAllActiveApplicationList
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<ApplicationModuleBean> getAllActiveApplicationList() throws SQLException, Exception {

        List<ApplicationModuleBean> allActList = null;
        try {
            appPerObj = new ApplicationModuleMgtPersistance();
            //create Db connection
            CMSCon = DBConnection.getConnection();
            //begin transaction
            CMSCon.setAutoCommit(false);


            allActList = appPerObj.getAllActiveApplicationModules(CMSCon);



            //end of transaction
            CMSCon.commit();

            //return value list
            return allActList;


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
    /////////////////////////////////////
}
