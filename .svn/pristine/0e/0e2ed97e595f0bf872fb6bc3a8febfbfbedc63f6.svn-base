/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.mtmm.manualtxn.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.mtmm.manualtxn.bean.SaleTxnBean;
import com.epic.cms.mtmm.manualtxn.bean.TxnDetailsBean;
import com.epic.cms.mtmm.manualtxn.persistance.SaleTxnPersistance;
import com.epic.cms.mtmm.merchantmgt.merchantlocation.persistance.MerchantLocationPersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author nalin
 */
public class SaleTxnManager {

    private Connection CMSCon = null;
    private Connection CMSOnline = null;
    private SystemAuditManager sysAuditManager;
    private SaleTxnPersistance saleTxnPersistance = null;
    private SaleTxnBean saleTxnBean = null;
    private HashMap<String, String> terminalList = null;
    private List<SaleTxnBean> saleTxnList = null;
    private HashMap<String, String> cardTypeList = null;
    private HashMap<String, String> currencyList = null;
    private TxnDetailsBean detailsBean = null;
    private SaleTxnBean voidBean = null;
    private SaleTxnBean settleBean = null;

    public SaleTxnBean getMerchantDetails(String merchantID) throws Exception {

        try {

            CMSCon = new DBConnection().getConnection();
            saleTxnPersistance = new SaleTxnPersistance();
            saleTxnBean = new SaleTxnBean();

            CMSCon.setAutoCommit(false);

            saleTxnBean = saleTxnPersistance.getMerchantDetails(CMSCon, merchantID);

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
        return saleTxnBean;
    }

    public HashMap<String, String> getActiveManualTerminalOfMerchant(String merchantID) throws Exception {

        try {

            CMSCon = new DBConnection().getConnection();
            saleTxnPersistance = new SaleTxnPersistance();
            terminalList = new HashMap<String, String>();

            CMSCon.setAutoCommit(false);

            terminalList = saleTxnPersistance.getActiveManualTerminalOfMerchant(CMSCon, merchantID);

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
        return terminalList;
    }

    public String getResponsemessage(String responseCode) throws Exception {
        String responseMsg = null;
        try {

            CMSOnline = new DBConnectionToOnlineDB().getConnection();
            saleTxnPersistance = new SaleTxnPersistance();


            CMSOnline.setAutoCommit(false);

            responseMsg = saleTxnPersistance.getResponsemessage(CMSOnline, responseCode);

            CMSOnline.commit();


        } catch (SQLException ex) {
            try {
                CMSOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSOnline.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSOnline);
        }
        return responseMsg;
    }

    public List<SaleTxnBean> getTerminalTxnInfo(String terminalID, String merchantId) throws Exception {

        try {

            CMSOnline = new DBConnectionToOnlineDB().getConnection();
            saleTxnPersistance = new SaleTxnPersistance();
            saleTxnList = new ArrayList<SaleTxnBean>();

            CMSOnline.setAutoCommit(false);

            saleTxnList = saleTxnPersistance.getTerminalTxnInfo(CMSOnline, terminalID, merchantId);

            CMSOnline.commit();


        } catch (SQLException ex) {
            try {
                CMSOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSOnline.rollback();
                throw e;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
        }
        return saleTxnList;
    }

    public List<SaleTxnBean> getManualTerminalTxnInfoToVoid(String terminalID, String merchantId) throws Exception {

        try {

            CMSOnline = new DBConnectionToOnlineDB().getConnection();
            saleTxnPersistance = new SaleTxnPersistance();
            saleTxnList = new ArrayList<SaleTxnBean>();

            CMSOnline.setAutoCommit(false);

            saleTxnList = saleTxnPersistance.getManualTerminalTxnInfoToVoid(CMSOnline, terminalID, merchantId);

            CMSOnline.commit();


        } catch (SQLException ex) {
            try {
                CMSOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSOnline.rollback();
                throw e;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
        }
        return saleTxnList;
    }

    public List<SaleTxnBean> getManualTerminalTxnInfoToSettle(String terminalID, String merchantId) throws Exception {

        try {

            CMSOnline = new DBConnectionToOnlineDB().getConnection();
            saleTxnPersistance = new SaleTxnPersistance();
            saleTxnList = new ArrayList<SaleTxnBean>();

            CMSOnline.setAutoCommit(false);

            saleTxnList = saleTxnPersistance.getManualTerminalTxnInfoToSettle(CMSOnline, terminalID, merchantId);

            CMSOnline.commit();


        } catch (SQLException ex) {
            try {
                CMSOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSOnline.rollback();
                throw e;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
        }
        return saleTxnList;
    }

    public HashMap<String, String> getCardTypes() throws Exception {
        try {
            CMSCon = new DBConnection().getConnection();
            saleTxnPersistance = new SaleTxnPersistance();
            cardTypeList = new HashMap<String, String>();

            CMSCon.setAutoCommit(false);

            cardTypeList = saleTxnPersistance.getCardTypes(CMSCon);

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
        return cardTypeList;
    }

    public HashMap<String, String> getCurrency() throws Exception {
        try {
            CMSCon = new DBConnection().getConnection();
            saleTxnPersistance = new SaleTxnPersistance();
            currencyList = new HashMap<String, String>();

            CMSCon.setAutoCommit(false);

            currencyList = saleTxnPersistance.getCurrency(CMSCon);

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
        return currencyList;
    }

    public TxnDetailsBean getTxnDetails(String txnId, String tid, String mid) throws Exception {

        try {

            CMSOnline = new DBConnectionToOnlineDB().getConnection();
            saleTxnPersistance = new SaleTxnPersistance();
            detailsBean = new TxnDetailsBean();

            CMSOnline.setAutoCommit(false);

            detailsBean = saleTxnPersistance.getTxnDetails(CMSOnline, txnId, tid, mid);

            CMSOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSOnline.rollback();
                throw e;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
        }
        return detailsBean;
    }

    public String getMaxBatchId(String mid, String tid) throws Exception {
        String batchId = null;
        try {

            CMSOnline = new DBConnectionToOnlineDB().getConnection();
            saleTxnPersistance = new SaleTxnPersistance();


            CMSOnline.setAutoCommit(false);

            batchId = saleTxnPersistance.getMaxBatchId(CMSOnline, mid, tid);

            CMSOnline.commit();


        } catch (SQLException ex) {
            try {
                CMSOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSOnline.rollback();
                throw e;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
        }
        return batchId;
    }

    public String generateTxnId() throws Exception {
        String txnId = null;
        try {

            CMSOnline = new DBConnectionToOnlineDB().getConnection();
            saleTxnPersistance = new SaleTxnPersistance();


            CMSOnline.setAutoCommit(false);

            txnId = saleTxnPersistance.generateTxnId(CMSOnline);

            CMSOnline.commit();


        } catch (SQLException ex) {
            try {
                CMSOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSOnline.rollback();
                throw e;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
        }
        return txnId;
    }

    public SaleTxnBean getOriginalTxnData(SaleTxnBean bean) throws Exception {

        try {

            CMSOnline = new DBConnectionToOnlineDB().getConnection();
            saleTxnPersistance = new SaleTxnPersistance();
            voidBean = new SaleTxnBean();

            CMSOnline.setAutoCommit(false);

            voidBean = saleTxnPersistance.getOriginalTxnData(CMSOnline, bean);

            CMSOnline.commit();


        } catch (SQLException ex) {
            try {
                CMSOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSOnline.rollback();
                throw e;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
        }
        return voidBean;
    }

    public boolean insertToManualTxnTable(SaleTxnBean bean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            CMSCon = new DBConnection().getConnection();
            CMSOnline = new DBConnectionToOnlineDB().getConnection();
            saleTxnPersistance = new SaleTxnPersistance();
            sysAuditManager = new SystemAuditManager();

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            success = saleTxnPersistance.insertToManualTxnTable(CMSOnline, bean);

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSOnline.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
        }
        return success;
    }

    public boolean updateManualTxnTable(SaleTxnBean bean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            CMSCon = new DBConnection().getConnection();
            CMSOnline = new DBConnectionToOnlineDB().getConnection();
            saleTxnPersistance = new SaleTxnPersistance();
            sysAuditManager = new SystemAuditManager();

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            success = saleTxnPersistance.updateManualTxnTable(CMSOnline, bean);

            //sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSOnline.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
        }
        return success;
    }

    public boolean voidSelectedTransaction(SaleTxnBean voidBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            CMSCon = new DBConnection().getConnection();
            CMSOnline = new DBConnectionToOnlineDB().getConnection();
            saleTxnPersistance = new SaleTxnPersistance();
            sysAuditManager = new SystemAuditManager();

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            success = saleTxnPersistance.voidSelectedTransaction(CMSOnline, voidBean);

            //sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSOnline.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
        }
        return success;
    }

    public String getCommonManualTerminal() throws Exception {
        String commmonTid = null;
        try {

            CMSCon = new DBConnection().getConnection();
            saleTxnPersistance = new SaleTxnPersistance();
            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();


            CMSCon.setAutoCommit(false);

            commmonTid = merchLocPer.getCommonManualTerminal(CMSCon);

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
        return commmonTid;
    }

    public double getTotalTxnAmount(String[] settleTxnList) throws Exception {
        double totAmount = 0;
        try {

            CMSOnline = new DBConnectionToOnlineDB().getConnection();
            saleTxnPersistance = new SaleTxnPersistance();
            voidBean = new SaleTxnBean();

            CMSOnline.setAutoCommit(false);

            for (int i = 0; i < settleTxnList.length; i++) {

                String amount = saleTxnPersistance.getTxnAmount(CMSOnline, settleTxnList[i]);
                totAmount += Double.parseDouble(amount);
            }
            CMSOnline.commit();



        } catch (SQLException ex) {
            try {
                CMSOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSOnline.rollback();
                throw e;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
        }
        return totAmount;
    }

    public boolean settledSelectedTransaction(SaleTxnBean settleBean, int status, String[] settleTxnList, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            CMSCon = new DBConnection().getConnection();
            CMSOnline = new DBConnectionToOnlineDB().getConnection();
            saleTxnPersistance = new SaleTxnPersistance();
            sysAuditManager = new SystemAuditManager();

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            for (int i = 0; i < settleTxnList.length; i++) {
                success = saleTxnPersistance.settledSelectedTransaction(CMSOnline, settleBean, status, settleTxnList[i]);
            }
            //sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSOnline.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
        }
        return success;
    }

    public boolean settleFailedSelectedTransaction(int status, String[] settleTxnList, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            CMSCon = new DBConnection().getConnection();
            CMSOnline = new DBConnectionToOnlineDB().getConnection();
            saleTxnPersistance = new SaleTxnPersistance();
            sysAuditManager = new SystemAuditManager();

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            for (int i = 0; i < settleTxnList.length; i++) {
                success = saleTxnPersistance.settleFailedSelectedTransaction(CMSOnline, status, settleTxnList[i]);
            }
            //sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSOnline.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
        }
        return success;
    }

    public boolean updateBatchUploadStatus(int status, String txnId, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            CMSCon = new DBConnection().getConnection();
            CMSOnline = new DBConnectionToOnlineDB().getConnection();
            saleTxnPersistance = new SaleTxnPersistance();
            sysAuditManager = new SystemAuditManager();

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);


            success = saleTxnPersistance.updateBatchUploadStatus(CMSOnline, status, txnId);

            //sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSOnline.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
        }
        return success;
    }

    public SaleTxnBean getOriginalTxnDataToBatchUpload(String txnId) throws Exception {

        try {

            CMSOnline = new DBConnectionToOnlineDB().getConnection();
            saleTxnPersistance = new SaleTxnPersistance();
            settleBean = new SaleTxnBean();

            CMSOnline.setAutoCommit(false);

            settleBean = saleTxnPersistance.getOriginalTxnDataToBatchUpload(CMSOnline, txnId);

            CMSOnline.commit();


        } catch (SQLException ex) {
            try {
                CMSOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSOnline.rollback();
                throw e;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
        }
        return settleBean;
    }

    public String getManualTerminal(String merchantId) throws Exception {
        String tId = null;
        try {

            CMSCon = new DBConnection().getConnection();
            saleTxnPersistance = new SaleTxnPersistance();


            CMSCon.setAutoCommit(false);

            tId = saleTxnPersistance.getManualTerminal(CMSCon, merchantId);

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
        return tId;
    }
}
