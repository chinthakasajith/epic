/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.cooperate.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationHistoryBean;
import com.epic.cms.camm.applicationproccessing.assigndata.persistance.ApplicationAssignPersistance;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardBankDetailsBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.ApplicationCheckingManager;
import com.epic.cms.camm.applicationproccessing.capturedata.persistance.CustomerPersonalPersistance;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.EstCardManagerDetailsBean;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.EstablishCardApplicationBean;
import com.epic.cms.camm.applicationproccessing.cooperate.persistance.CorporateDataCapturePersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author badrika
 */
public class CorporateDataCaptureManager {
    private CorporateDataCapturePersistance persistance;
    private CustomerPersonalPersistance persistance2;
    private Connection CMSCon;
    private EstablishCardApplicationBean companyBean;
    private SystemAuditManager sysAuditManager;
    private ApplicationAssignPersistance historyPersistance;
    private List<EstCardManagerDetailsBean> managerDetailsList;
    private ApplicationCheckingManager checkingManager;
    private Connection CMSCon2;
    
      public int insertCompanyData(EstablishCardApplicationBean comapnyBean, SystemAuditBean systemAuditBean) throws Exception {
        int isAdd = -1;
        try {
            EstablishCardApplicationBean bean = this.getAllDetailsCompany(comapnyBean.getAppId());

            persistance = new CorporateDataCapturePersistance();
            persistance2 = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysAuditManager = new SystemAuditManager();
          //  checkingManager = new ApplicationCheckingManager();


            if (bean != null) {
                isAdd = persistance.updateCompanyRecord(CMSCon, comapnyBean);
            } else {
                isAdd = persistance.insertcomapnyRecord(CMSCon, comapnyBean);


                ApplicationHistoryBean historyBean = new ApplicationHistoryBean();

                historyBean.setApplicationId(comapnyBean.getAppId());
                historyBean.setApplicationLevel("DTEN");
                historyBean.setLastUpdatedUser(comapnyBean.getLastUpdatedUser());


                if (isAdd == 1) {
                    historyBean.setRemarks(comapnyBean.getAppId() + ": Application processing started");
                    historyBean.setStatus("HCOM");
                } else {
                    historyBean.setRemarks(comapnyBean.getAppId() + ": Application processing failed");
                    historyBean.setStatus("HFAL");
                }
                historyPersistance = new ApplicationAssignPersistance();
                historyPersistance.insertApplicationHistory(CMSCon, historyBean);
            }


            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            persistance2.updateStatusOfCardApplicationStatus(comapnyBean.getAppId(), "TAB01STATUS", CMSCon);
            persistance2.updateCardApplicationStatus(comapnyBean.getAppId(), StatusVarList.APP_PROCESS, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

        return isAdd;
    }
      
      
       public EstablishCardApplicationBean getAllDetailsCompany(String applicationId) throws Exception {
        try {
            persistance = new CorporateDataCapturePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            companyBean = persistance.getAllDetailsCompany(CMSCon, applicationId);
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


        return companyBean;
    }
       
       
        public int insertManagerDetailList(List<EstCardManagerDetailsBean> sessionManagerDetailList, String appId, SystemAuditBean systemAuditBean) throws Exception {
        int isAdd = 0;
        try {
            persistance = new CorporateDataCapturePersistance();
            persistance2 = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysAuditManager = new SystemAuditManager();
   //         checkingManager = new ApplicationCheckingManager();

            List<EstCardManagerDetailsBean> lst = this.getManagerDetails(appId);
            if (lst != null) {
                isAdd = persistance.updateManagerDetailsRecords(CMSCon, sessionManagerDetailList, appId);
            } else {
                isAdd = persistance.insertManagerDetailsRecords(CMSCon, sessionManagerDetailList);
            }


            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            if (isAdd == 1) {
                persistance2.updateStatusOfCardApplicationStatus(appId, "TAB02STATUS", CMSCon);
            }
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

        return isAdd;
    }
        
        public List<EstCardManagerDetailsBean> getManagerDetails(String applicationId) throws Exception {
        managerDetailsList = new ArrayList<EstCardManagerDetailsBean>();
        try {
            persistance = new CorporateDataCapturePersistance();
            CMSCon2 = DBConnection.getConnection();
            CMSCon2.setAutoCommit(false);
            managerDetailsList = persistance.getManagerDetails(CMSCon2, applicationId);


            CMSCon2.commit();

        } catch (SQLException ex) {
            try {
                CMSCon2.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon2.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon2);
        }
        return managerDetailsList;
    }
       
        public int insertBankDetailList(List<CardBankDetailsBean> sessionBankDetailList, String appId, SystemAuditBean systemAuditBean) throws Exception {
        int isAdd = 0;
        try {
            persistance2 = new CustomerPersonalPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysAuditManager = new SystemAuditManager();
            checkingManager = new ApplicationCheckingManager();

            List<CardBankDetailsBean> lst = checkingManager.getCardBankDetailsDetails(appId);
            if (lst != null) {
                isAdd = persistance2.updateBankDetailsRecords(CMSCon, sessionBankDetailList, appId);
            } else {
                isAdd = persistance2.insertBankDetailsRecords(CMSCon, sessionBankDetailList);
            }


            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            if (isAdd == 1) {
                persistance2.updateStatusOfCardApplicationStatus(appId, "TAB03STATUS", CMSCon);
                persistance2.updateCardApplicationStatus(appId, StatusVarList.APP_FILLING_COMPLETE, CMSCon);
            }
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

        return isAdd;
    }
       
       
       
       //
    
}
