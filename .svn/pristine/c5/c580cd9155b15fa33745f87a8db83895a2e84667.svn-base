/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.prem.bulkcardmgt.bulkcardrequest.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.ECMSOnlineTransBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.EasyPaymentRequestBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.persistance.EasyPaymentRequestPersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sajith_g
 */
public class EasyPaymentRequestManager {
    
    private Connection CMSCon;
    private EasyPaymentRequestPersistance easyPaymentRequestPersistance = null;     
    private List<ECMSOnlineTransBean> OnlineTransReqList = null;
    private SystemAuditManager sysAuditManager=null;
    
    public List<ECMSOnlineTransBean> getOnlineTransReq(ECMSOnlineTransBean eCMSOnlineTransBean) throws Exception {
        try {
            easyPaymentRequestPersistance = new EasyPaymentRequestPersistance();
            CMSCon = DBConnectionToOnlineDB.getConnection();
            CMSCon.setAutoCommit(false);
            OnlineTransReqList = easyPaymentRequestPersistance.getOnlineTransReq(CMSCon, eCMSOnlineTransBean);

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
    
    public Boolean insertEasyPayReq(EasyPaymentRequestBean bean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        String batchId = "";
        try {

            //if the insert become success row will return 1
            Boolean success = false;
            Boolean success2 = false;
            int success1 = -1;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            easyPaymentRequestPersistance = new EasyPaymentRequestPersistance();
            success2 = easyPaymentRequestPersistance.insertEasyPayReq(bean, CMSCon);
            sysAuditManager = new SystemAuditManager();
            success1 = sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

            if (success2 && success1 == 1) {
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
