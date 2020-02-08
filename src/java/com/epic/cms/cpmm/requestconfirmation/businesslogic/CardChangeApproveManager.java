/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.cpmm.requestconfirmation.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.FeeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.FeeMgtManager;
import com.epic.cms.callcenter.customer.bean.CustomerContactDetailsChngeHolderBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.cpmm.requestconfirmation.bean.RequestConfirmationBean;
import com.epic.cms.cpmm.requestconfirmation.persistance.CardChangeApprovePersistence;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author nisansala
 */
public class CardChangeApproveManager {

    private Connection CMSCon;
    private Connection onlineCMSCon;
    private CardChangeApprovePersistence cdChngPer;
    private List<RequestConfirmationBean> requestList;
    SystemAuditManager sysAuditManager = null;
    FeeMgtManager feemgr = null;

    public List<RequestConfirmationBean> searchRequests(RequestConfirmationBean inputBean) throws SQLException, Exception {
        try {
            cdChngPer = new CardChangeApprovePersistence();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            requestList = cdChngPer.searchRequests(CMSCon, inputBean);
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
        return requestList;
    }

    public RequestConfirmationBean viewOneRequest(String cardNo, String operation) throws SQLException, Exception {
        RequestConfirmationBean reqBean;
        try {
            cdChngPer = new CardChangeApprovePersistence();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            reqBean = cdChngPer.viewOneRequest(CMSCon, cardNo, operation);
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
        return reqBean;
    }

    public int approveCardChange(RequestConfirmationBean confBean, String approveStatus, String operation, FeeBean fee) throws SQLException, Exception {
        int row1, row2, row = -1;
        try {
            cdChngPer = new CardChangeApprovePersistence();
            feemgr = new FeeMgtManager();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            row1 = cdChngPer.approveCardChange(confBean, approveStatus, operation, CMSCon);
            row2 = feemgr.feeCountMgt(fee);
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
        if (row1 == 1 && row2 == 1) {
            row = 1;
        }
        return row;
    }

    public void updateApprovedCustomerDetails(CustomerPersonalBean customerPersonalBean, CustomerContactDetailsChngeHolderBean changeTracker, 
            String cardCategory, String requestId) throws Exception {
        
        try {
            cdChngPer = new CardChangeApprovePersistence();
            feemgr = new FeeMgtManager();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            if(cardCategory!=null){
                if(cardCategory.equals(StatusVarList.CARD_CATEGORY_MAIN) ||
                        cardCategory.equals(StatusVarList.CARD_CATEGORY_COPORATE) ||
                        cardCategory.equals(StatusVarList.CARD_AGAINST_FD_CODE)){
                    
                    cdChngPer.updateMainCardCustomerDetails(customerPersonalBean, changeTracker, CMSCon);
                    
                }else if(cardCategory.equals(StatusVarList.CARD_CATEGORY_SUPPLEMENTARY)){
                    cdChngPer.updateSupCardCustomerDetails(customerPersonalBean, changeTracker, CMSCon); 
                
                }else if(cardCategory.equals(StatusVarList.CARD_CATEGORY_ESTABLISHMENT)){
                    cdChngPer.updateEstCardCustomerDetails(customerPersonalBean, changeTracker, CMSCon);              
                }
            }
            
            cdChngPer.updateCustomerContactDetailsChnageRequest(requestId, customerPersonalBean.getLastUpdateUser(),
                    CMSCon, StatusVarList.DA_REQUSET_APPROVE);   
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
        
    }

    public void updateRejectCardCustomerPersonalDetails(String userName, String remark, String requestId) throws Exception {
        
        try {
            cdChngPer = new CardChangeApprovePersistence();
            feemgr = new FeeMgtManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            cdChngPer.updateCustomerContactDetailsChnageRequest(requestId, userName,
                    CMSCon, StatusVarList.DA_REQUSET_REJECT);   
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
        
    }

    
   
}
