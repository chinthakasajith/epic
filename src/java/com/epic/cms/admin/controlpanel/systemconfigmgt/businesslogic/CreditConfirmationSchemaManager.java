/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CreditConfirmationSchemaBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.persistance.CreditConfirmationSchemaPersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author chinthaka_r
 */
public class CreditConfirmationSchemaManager {
    
     private SystemAuditManager sysAuditManager;
     private Connection CMSCon;
     private CreditConfirmationSchemaPersistance creditConfirmationSchemaPer;
     private List<CreditConfirmationSchemaBean>schemaDetails=null;
     private String lastMaxLimit=null;
     
     public List<CreditConfirmationSchemaBean> getSchemaDetails() throws Exception {
        try {
            creditConfirmationSchemaPer = new CreditConfirmationSchemaPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            schemaDetails = creditConfirmationSchemaPer.getSchemaDetails(CMSCon);


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
        return schemaDetails;
    }
     //get last max limit
    public String getLastMaxLimit() throws Exception{
        try {
            creditConfirmationSchemaPer = new CreditConfirmationSchemaPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            lastMaxLimit=creditConfirmationSchemaPer.getLastMaxLimit(CMSCon);
            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        }finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return lastMaxLimit;
    }
    public boolean insertSchema(CreditConfirmationSchemaBean schemaBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            sysAuditManager = new SystemAuditManager();
            creditConfirmationSchemaPer = new CreditConfirmationSchemaPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            success = creditConfirmationSchemaPer.insertSchema(CMSCon, schemaBean);
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
    public boolean deleteSchema(CreditConfirmationSchemaBean bean ,SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            creditConfirmationSchemaPer = new CreditConfirmationSchemaPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            success = creditConfirmationSchemaPer.deleteSchema(CMSCon, bean);
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
}
