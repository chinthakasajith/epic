/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cms.prem.bulkcardmgt.bulkcardrequest.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.BTPaymentRequestBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.ECMSOnlineTransBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.EasyPaymentRequestBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.LoanOnCardPaymentRequestBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.persistance.BTPaymentRequestPersistance;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.persistance.EasyPaymentRequestPersistance;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.persistance.LoanOnCardRequestPersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sajith_g
 */
public class LoanOnCardRequestManager {
    private Connection CMSCon;
    private LoanOnCardRequestPersistance loanOnCardRequestPersistance = null;     
    private List<ECMSOnlineTransBean> OnlineTransReqList = null;
    private SystemAuditManager sysAuditManager=null;
    
    public List<ECMSOnlineTransBean> getOnlineTransReq(ECMSOnlineTransBean eCMSOnlineTransBean) throws Exception {
        try {
            loanOnCardRequestPersistance = new LoanOnCardRequestPersistance();
            CMSCon = DBConnectionToOnlineDB.getConnection();
            CMSCon.setAutoCommit(false);
            OnlineTransReqList = loanOnCardRequestPersistance.getOnlineTransReq(CMSCon, eCMSOnlineTransBean);

            CMSCon.commit();
        } catch (Exception ex) {
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
        return OnlineTransReqList;
    }
    
    public Boolean insertLoanOnCardPayReq(LoanOnCardPaymentRequestBean bean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        String batchId = "";
        try {

            Boolean success = false;
            int maxReqId = 0;
            int success1 = -1;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            loanOnCardRequestPersistance = new LoanOnCardRequestPersistance();
            maxReqId = loanOnCardRequestPersistance.insertLoanOnCardPayReq(bean, CMSCon);
            sysAuditManager = new SystemAuditManager();

            if (maxReqId > 0) {
                //set request id as unique id
                systemAuditBean.setUniqueId(String.valueOf(maxReqId));
                success1 = sysAuditManager.insertAudit(systemAuditBean, CMSCon);
                CMSCon.commit();
            }

            if (maxReqId > 0 && success1 == 1) {
                success = true;
            }
            return success;

        } catch (SQLException ex) {

            //throws an exception when rollback fails 
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
    }
}
