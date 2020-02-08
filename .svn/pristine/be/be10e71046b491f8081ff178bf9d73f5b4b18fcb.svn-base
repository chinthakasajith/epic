/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.eodprocessmgt.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.backoffice.eodprocessmgt.bean.EodProcessCategoryBean;
import com.epic.cms.backoffice.eodprocessmgt.persistance.EodProcessCategoryPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nalin
 */
public class EodProcessCategoryManager {

    private List<EodProcessCategoryBean> categoryList = null;
    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;
    private EodProcessCategoryPersistance categoryPerr;

    public List<EodProcessCategoryBean> getProcessCategoryDetails() throws Exception {

        try {

            categoryList = new ArrayList<EodProcessCategoryBean>();
            categoryPerr = new EodProcessCategoryPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            categoryList = categoryPerr.getProcessCategoryDetails(CMSCon);

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
        return categoryList;
    }
    
     public String eodProcessCategoryInsert(EodProcessCategoryBean categoryBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;
        String categoryId = null;
        try {

            categoryPerr = new EodProcessCategoryPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            flag = categoryPerr.eodProcessCategoryInsert(categoryBean, CMSCon);
            
            if(flag){
                
                categoryId = categoryPerr.getCategoryId(categoryBean.getLastUpdatedUser(),CMSCon);
            }
            if(!categoryId.isEmpty()){
            systemAuditBean.setUniqueId(categoryId);
            systemAuditBean.setDescription("Add Category Managment. Category Id : '" + categoryId + "'; by : " + categoryBean.getLastUpdatedUser());
            
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            }

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
        return categoryId;
    }
     
     public boolean eodProcessCategoryUpdate(EodProcessCategoryBean categoryBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;
        try {

            categoryPerr = new EodProcessCategoryPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            flag = categoryPerr.eodProcessCategoryUpdate(categoryBean, CMSCon);
            
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
        return flag;
    }

    public boolean eodProcessCategoryDelete(EodProcessCategoryBean categoryBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;
        try {

            categoryPerr = new EodProcessCategoryPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            flag = categoryPerr.eodProcessCategoryDelete(categoryBean, CMSCon);
            
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
        return flag;
    }
}

