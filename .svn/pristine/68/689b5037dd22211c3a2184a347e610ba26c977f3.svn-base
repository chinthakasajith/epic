/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cms.backoffice.installement.btpayment.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.ApplicationModuleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SectionBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.TaskBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.UserRoleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.UserRolePrivilegeBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.application.common.persistance.StatusPersistence;
import com.epic.cms.backoffice.installement.btpayment.bean.BTPaymentPlanBean;
import com.epic.cms.backoffice.installement.btpayment.persistance.BTPaymentPlanPersistance;
import com.epic.cms.backoffice.installementpayment.bean.PaymentPlanBean;
import com.epic.cms.backoffice.installementpayment.persistance.PaymentPlanPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author sajith_g
 */
public class BTPaymentPlanManager {
    
    private Connection CMSCon;
    private BTPaymentPlanPersistance paymentplanPerObj = null;
    private SystemAuditManager sysAuditManager;
    private StatusPersistence statusPst;
    private List<StatusBean> statusList;
    private List<BTPaymentPlanBean> paymentBTPlanList;
    private List<UserRolePrivilegeBean> appSecPageTaskList = null;
    private List<ApplicationModuleBean> userRoleAppliacationList = null;
    private List<SectionBean> sectionList = null;
    private List<PageBean> pageList = null;
    private List<TaskBean> taskList = null;
    private List<UserRoleBean> userRoleListUnassignedToSchema = null;
    private List<UserRoleBean> userRoleListAssignedToSchema = null;
    
    /**
     * this method used to insert the payment plan
     *
     * @param paymentPlan
     * @param systemAuditBean
     * @return success-boolean
     * @throws Exception
     */
    public boolean insertPaymentPlan(BTPaymentPlanBean paymentPlan, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            paymentplanPerObj = new BTPaymentPlanPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            success = paymentplanPerObj.insertPaymentPlan(CMSCon, paymentPlan);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return success;
    }

    /**
     * this method used to get all the payment plans
     *
     * @return paymentPlanList
     * @throws Exception
     */
    public List<BTPaymentPlanBean> getAllPaymentPlans() throws Exception {
        try {
            paymentplanPerObj = new BTPaymentPlanPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            paymentBTPlanList = paymentplanPerObj.getAllPaymentPlans(CMSCon);
            CMSCon.commit();
        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return paymentBTPlanList;
    }
    
    /**
     * this method used to get all the active payment plans
     *
     * @return paymentPlanList
     * @throws Exception
     */
    public List<BTPaymentPlanBean> getAllActivePaymentPlans() throws Exception {
        try {
            paymentplanPerObj = new BTPaymentPlanPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            paymentBTPlanList = paymentplanPerObj.getAllActivePaymentPlans(CMSCon);
            CMSCon.commit();
        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return paymentBTPlanList;
    }

    /**
     * this method used to delete the payment plan
     *
     * @param paymentPlan
     * @param systemAuditBean
     * @return success-boolean
     * @throws Exception
     */
    public boolean deletePaymentPlan(BTPaymentPlanBean paymentPlan, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            paymentplanPerObj = new BTPaymentPlanPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            success = paymentplanPerObj.deletePaymentPlan(CMSCon, paymentPlan);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return success;
    }

    /**
     * this method used to update the payment plan
     *
     * @param paymentPlan
     * @param systemAuditBean
     * @return success-boolean
     * @throws Exception
     */
    public boolean updatePaymentPlan(BTPaymentPlanBean paymentPlan, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            paymentplanPerObj = new BTPaymentPlanPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            success = paymentplanPerObj.updatePaymentPlan(CMSCon, paymentPlan);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return success;
    }
}
