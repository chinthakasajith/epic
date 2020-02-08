/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.responcecodemgt.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.switchcontrol.responcecodemgt.bean.ResponceCodeBean;
import com.epic.cms.switchcontrol.responcecodemgt.persistance.ResponceCodeMgtPersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nalin
 */
public class ResponceCodeMgtManager {

    private List<ResponceCodeBean> responceList = null;
    private Connection CMSCon = null;
    private Connection CMSConOnline = null;
    private SystemAuditManager sysAuditManager = null;
    private ResponceCodeMgtPersistance responcePerr = null;

    public List<ResponceCodeBean> getResponceCodes() throws Exception {
        try {
            responceList = new ArrayList<ResponceCodeBean>();
            responcePerr = new ResponceCodeMgtPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSConOnline.setAutoCommit(false);

            responceList = responcePerr.getResponceCodes(CMSConOnline);

            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
        }
        return responceList;
    }
    
    public boolean insertResponceCode(ResponceCodeBean responceBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;

        try {
            responcePerr = new ResponceCodeMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();

            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = responcePerr.insertResponceCode(CMSConOnline, responceBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSConOnline.commit();
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSConOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSConOnline.rollback();
                CMSCon.rollback();
                throw e;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return success;
    }
    
     public boolean updateResponceCode(ResponceCodeBean responceBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;

        try {
            responcePerr = new ResponceCodeMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();

            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = responcePerr.updateResponceCode(CMSConOnline, responceBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSConOnline.commit();
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSConOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSConOnline.rollback();
                CMSCon.rollback();
                throw e;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return success;
    }
     
     public boolean deleteResponceCode(ResponceCodeBean responceBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            responcePerr = new ResponceCodeMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();

            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = responcePerr.deleteResponceCode(CMSConOnline, responceBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSConOnline.commit();
            CMSCon.commit();


        } catch (SQLException ex) {
            try {
                CMSConOnline.rollback();
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSConOnline.rollback();
                CMSCon.rollback();
                throw e;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            DBConnection.dbConnectionClose(CMSCon);
        }
        return success;
    }
}
