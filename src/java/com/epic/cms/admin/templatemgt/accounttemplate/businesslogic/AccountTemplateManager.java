/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.accounttemplate.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.templatemgt.accounttemplate.bean.AccountTempBean;
import com.epic.cms.admin.templatemgt.accounttemplate.persistance.AccountTemplatePersistance;
import com.epic.cms.admin.templatemgt.customertemplate.bean.CustomerTempBean;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author janaka_h
 */
public class AccountTemplateManager {

    private List<AccountTempBean> templateList = null;
    private AccountTemplatePersistance templatePst;
    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;
    private HashMap<String,String> cardType = null;    
    private HashMap<String, String> accountRskProf = null;
    private HashMap<String, String> interestProf = null;
    private HashMap<String,String> billingCycle = null;
    private HashMap<String, String> billStatementProf = null;
    private List<CustomerTempBean> cusTempList = null;

    public List<AccountTempBean> getAllTemplateLst() throws Exception {
        try {
            templatePst = new AccountTemplatePersistance();
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

    public int insertTemplate(AccountTempBean templateBean, SystemAuditBean systemAuditBean) throws Exception {
        int isAdd;
        try {
            templatePst = new AccountTemplatePersistance();
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

    public int updateTemplate(AccountTempBean templateBean, SystemAuditBean systemAuditBean) throws Exception {
        int isUpdate;
        try {
            templatePst = new AccountTemplatePersistance();
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

    public int deleteTemplate(AccountTempBean templateBean, SystemAuditBean systemAuditBean) throws Exception {
        int isDelete;
        try {
            templatePst = new AccountTemplatePersistance();
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

    public HashMap<String, String> getAllAccountRskProf(String cusTemp) throws Exception {
        try {

            templatePst = new AccountTemplatePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            accountRskProf = templatePst.getAllAccountRskProf(CMSCon,cusTemp);
            CMSCon.commit();

        } catch (Exception ex) {

            CMSCon.rollback();
            throw ex;

        } finally {

            DBConnection.dbConnectionClose(CMSCon);
        }
        return accountRskProf;
    }

    public HashMap<String, String> getAllInterestProf() throws Exception {
        try {

            templatePst = new AccountTemplatePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            interestProf = templatePst.getAllInterestProf(CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {

            CMSCon.rollback();
            throw ex;

        } finally {

            DBConnection.dbConnectionClose(CMSCon);
        }
        return interestProf;
    }

    public HashMap<String, String> getAllBillingCycle() throws Exception {
        try {

            templatePst = new AccountTemplatePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            billingCycle = templatePst.getAllBillingCycle(CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {

            CMSCon.rollback();
            throw ex;

        } finally {

            DBConnection.dbConnectionClose(CMSCon);
        }
        return billingCycle;
    }


    public HashMap<String, String> getAllBillStatementProf() throws Exception {
        try {

            templatePst = new AccountTemplatePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            billStatementProf = templatePst.getAllBillStatementProf(CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {

            CMSCon.rollback();
            throw ex;

        } finally {

            DBConnection.dbConnectionClose(CMSCon);
        }
        return billStatementProf;
    }
    
        
    public HashMap<String, String> getAllCardType() throws Exception {
        try {
            
            templatePst = new AccountTemplatePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardType = templatePst.getAllCardType(CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            
            CMSCon.rollback();
            throw ex;
            
        } finally {
            
            DBConnection.dbConnectionClose(CMSCon);
        }
        return cardType;
    }
    
        public List<CustomerTempBean> getCustomerTemplates(String staff) throws Exception {
        try {

            templatePst = new AccountTemplatePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cusTempList = templatePst.getCustomerTemplates(CMSCon,staff);
            CMSCon.commit();

        } catch (Exception ex) {

            CMSCon.rollback();
            throw ex;

        } finally {

            DBConnection.dbConnectionClose(CMSCon);
        }
        return cusTempList;
    }
}


