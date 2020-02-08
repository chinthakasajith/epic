/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.customertemplate.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.templatemgt.customertemplate.bean.CustomerTempBean;
import com.epic.cms.admin.templatemgt.customertemplate.bean.TemplateBean;
import com.epic.cms.admin.templatemgt.customertemplate.bean.TemplateCatagoryBean;
import com.epic.cms.admin.templatemgt.customertemplate.persistance.TemplatePersistence;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author janaka_h
 */
public class TemplateManager {
    
    private List<TemplateCatagoryBean> catagoryList ;
    private List<TemplateBean> templateList ;
    private Connection CMSCon;
    private TemplatePersistence templatePst;
    private SystemAuditManager sysAuditManager;

    public List<TemplateCatagoryBean> getAllTemplateCatagoryLst() throws Exception {
        try {
            templatePst = new TemplatePersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            catagoryList = templatePst.getAllTemplateCatagory(CMSCon);


            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return catagoryList;
        
        
    }

    public List<TemplateBean> getAllTemplateLst() throws Exception {
        
        try {
            templatePst = new TemplatePersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            templateList = templatePst.getAllTemplateLis(CMSCon);


            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return templateList;
    }

    public int insertTemplate(CustomerTempBean templateBean, SystemAuditBean systemAuditBean) throws Exception {
        int isAdd;
        try {
            templatePst = new TemplatePersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysAuditManager = new SystemAuditManager();
            
            isAdd = templatePst.insertTemplateRecord(CMSCon, templateBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

        return isAdd;
    }

    public int updateTemplate(TemplateBean templateBean, SystemAuditBean systemAuditBean) throws Exception {
         int isUpdate;
        try {
            templatePst = new TemplatePersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysAuditManager = new SystemAuditManager();
            
            isUpdate = templatePst.updateTemplateRecord(CMSCon, templateBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

        return isUpdate;
    }

    public int deleteTemplate(TemplateBean templateBean, SystemAuditBean systemAuditBean) throws Exception {
        int isDelete ;
        try{
            templatePst = new TemplatePersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysAuditManager = new SystemAuditManager();
            
            isDelete = templatePst.deleteTemplate(CMSCon, templateBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return isDelete;
    }
    
}
