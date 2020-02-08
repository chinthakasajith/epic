/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.customertemplate.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.templatemgt.customertemplate.bean.CustomerTempBean;
import com.epic.cms.admin.templatemgt.customertemplate.bean.TemplateBean;
import com.epic.cms.admin.templatemgt.customertemplate.persistance.CustomerTemplatePersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author janaka_h
 */
public class CustomerTemplateManager {

    private List<CustomerTempBean> searchList;
    private List<CurrencyBean> currencyLst;
    private HashMap<String, String> productType = null;
    private HashMap<String, String> cusRskProf = null;
    private CustomerTemplatePersistance templatePst;
    private Connection CMSCon;
    private List<CustomerTempBean> templateList = null;
    private SystemAuditManager sysAuditManager;

    public List<CustomerTempBean> getAllTemplateLst() throws Exception {
        try {
            templatePst = new CustomerTemplatePersistance();
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

    public List<CurrencyBean> getAllCurrencyLst() throws Exception {
        try {
            templatePst = new CustomerTemplatePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            currencyLst = templatePst.getCurrencyDetails(CMSCon);


            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return currencyLst;
    }

    public int insertTemplate(CustomerTempBean templateBean, SystemAuditBean systemAuditBean) throws Exception {
        int isAdd;
        try {
            templatePst = new CustomerTemplatePersistance();
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

    public int updateTemplate(CustomerTempBean templateBean, SystemAuditBean systemAuditBean) throws Exception {
        int isUpdate;
        try {
            templatePst = new CustomerTemplatePersistance();
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

    public int deleteTemplate(CustomerTempBean templateBean, SystemAuditBean systemAuditBean) throws Exception {
        int isDelete;
        try {
            templatePst = new CustomerTemplatePersistance();
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
    /**
     * to get all product types
     * @return
     * @throws Exception 
     */
    public HashMap<String, String> getAllProductType() throws Exception {
        try {
            
            templatePst = new CustomerTemplatePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            productType = templatePst.getAllProductType(CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            
            CMSCon.rollback();
            throw ex;
            
        } finally {
            
            DBConnection.dbConnectionClose(CMSCon);
        }
        return productType;
    }
    
     public HashMap<String, String> getAllCustomerRskProf() throws Exception {
        try {
            
            templatePst = new CustomerTemplatePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cusRskProf = templatePst.getAllCustomerRskProf(CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            
            CMSCon.rollback();
            throw ex;
            
        } finally {
            
            DBConnection.dbConnectionClose(CMSCon);
        }
        return cusRskProf;
    }
}
