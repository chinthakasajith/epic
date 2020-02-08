/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.responcecodemgt.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.switchcontrol.responcecodemgt.bean.ResponceCodeMappingBean;
import com.epic.cms.switchcontrol.responcecodemgt.persistance.ResponceCodeMappingPersistance;
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
public class ResponceCodeMappingManager {
    
     private List<ResponceCodeMappingBean> mappingList = null;
    private Connection CMSCon = null;
    private Connection CMSConOnline = null;
    private SystemAuditManager sysAuditManager = null;
    private ResponceCodeMappingPersistance mappingPerr = null;

    public List<ResponceCodeMappingBean> getDBCodes() throws Exception {
        try {
            mappingList = new ArrayList<ResponceCodeMappingBean>();
            mappingPerr = new ResponceCodeMappingPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSConOnline.setAutoCommit(false);

            mappingList = mappingPerr.getDBCodes(CMSConOnline);

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
        return mappingList;
    }
    
    public boolean insertDBCode(ResponceCodeMappingBean mappingBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;

        try {
             mappingPerr = new ResponceCodeMappingPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();

            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = mappingPerr.insertDBCode(CMSConOnline, mappingBean);
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
    
     public boolean updateDBCode(ResponceCodeMappingBean mappingBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;

        try {
            mappingPerr = new ResponceCodeMappingPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();

            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = mappingPerr.updateDBCode(CMSConOnline, mappingBean);
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
     
     public boolean deleteDBCode(ResponceCodeMappingBean mappingBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            mappingPerr = new ResponceCodeMappingPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();

            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = mappingPerr.deleteDBCode(CMSConOnline, mappingBean);
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
