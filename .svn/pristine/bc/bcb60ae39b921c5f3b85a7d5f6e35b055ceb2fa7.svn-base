/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.cardlimitincrement.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.FeeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.FeeMgtManager;
import com.epic.cms.backoffice.cardlimitincrement.bean.CommonCardParameterBean;
import com.epic.cms.backoffice.cardlimitincrement.bean.TempLimitIncrementBean;
import com.epic.cms.backoffice.cardlimitincrement.persistance.TempLimitIncrementPersistance;
import com.epic.cms.callcenter.callcentersearch.bean.CallHistoryBean;
import com.epic.cms.callcenter.callcentersearch.businesslogic.CallCenterMgtManager;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import com.epic.cms.system.util.variable.MessageVarList;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nalin
 */
public class TempLimitIncrementManager {

    private TempLimitIncrementPersistance tempIncremantPerr = null;
    private Connection CMSCon, CMSConOnline;
    private SystemAuditManager sysAuditManager;
    private TempLimitIncrementBean tempBean = null, updateBean = null;
    private List<TempLimitIncrementBean> tempList = null, cardList = null;
    private CommonCardParameterBean commonBean = null;
    private SystemUserBean CMSSysUser = null;
    private FeeMgtManager feeManager = null;
    private CallCenterMgtManager callCenterManager;
    private boolean successUpdate = false, successInc1 = false, successInc2 = false, successInc3 = false;

    public TempLimitIncrementBean tempLimitUpdate(TempLimitIncrementBean tempBean, SystemAuditBean systemAuditBean, FeeBean feeBean, CallHistoryBean callhistoryBean) throws Exception {
        boolean successUpdate = false;
        try {
            tempIncremantPerr = new TempLimitIncrementPersistance();
            sysAuditManager = new SystemAuditManager();
            callCenterManager = new CallCenterMgtManager();
            feeManager = new FeeMgtManager();
            CMSCon = DBConnection.getConnection();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();

            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            tempBean = tempIncremantPerr.getRealTimeCardDetails(CMSCon, tempBean);
            tempBean = tempIncremantPerr.getOnlineCardDetails(CMSConOnline, tempBean);

            this.incrementCreater(tempBean);//do it in approval

            if (tempBean.isFlag()) {

                successUpdate = tempIncremantPerr.tempLimitUpdate(CMSCon, tempBean);//do it in approval
                tempIncremantPerr.tempLimitUpdateToOnlime(CMSConOnline, tempBean);//do it in approval

                sysAuditManager.insertAudit(systemAuditBean, CMSCon);//do it in approval
                callCenterManager.insertCallHistory(callhistoryBean, CMSCon);//do it in request
                feeManager.feeCountMgt(feeBean);
            }
            CMSCon.commit();
            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();

                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
        }
        return tempBean;
    }

    public TempLimitIncrementBean tempLimitUpdate1(TempLimitIncrementBean tempBean, SystemAuditBean systemAuditBean, FeeBean feeBean) throws Exception {
        boolean successUpdate = false;
        try {
            tempIncremantPerr = new TempLimitIncrementPersistance();
            sysAuditManager = new SystemAuditManager();
            callCenterManager = new CallCenterMgtManager();
            feeManager = new FeeMgtManager();
            CMSCon = DBConnection.getConnection();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();

            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            tempBean = tempIncremantPerr.getRealTimeCardDetails(CMSCon, tempBean);
//            tempBean = tempIncremantPerr.getOnlineCardDetails(CMSConOnline, tempBean);

            this.incrementCreater(tempBean);//do it in approval

            if (tempBean.isFlag()) {

                successUpdate = tempIncremantPerr.tempLimitUpdate(CMSCon, tempBean);//do it in approval
                tempIncremantPerr.tempLimitUpdateToOnlime(CMSConOnline, tempBean);//do it in approval
                successInc2 = tempIncremantPerr.updateTemplimitInc(CMSCon, tempBean);

                sysAuditManager.insertAudit(systemAuditBean, CMSCon);//do it in approval
//                callCenterManager.insertCallHistory(callhistoryBean, CMSCon);//do it in request
                feeManager.feeCountMgt(feeBean);
            }
            CMSCon.commit();
            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();

                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
        }
        return tempBean;
    }

    public boolean tempLimitIncrementInsert(TempLimitIncrementBean tempBean, SystemAuditBean systemAuditBean, CallHistoryBean callhistoryBean) throws Exception {
        boolean successInsert = false;
        try {
            tempIncremantPerr = new TempLimitIncrementPersistance();
            sysAuditManager = new SystemAuditManager();
            callCenterManager = new CallCenterMgtManager();
            CMSCon = DBConnection.getConnection();

            CMSCon.setAutoCommit(false);

            successInsert = tempIncremantPerr.tempLimitIncrementInsert(CMSCon, tempBean);

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            callCenterManager.insertCallHistory(callhistoryBean, CMSCon);
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
        return successInsert;
    }

    public TempLimitIncrementBean getCardDetails(String cardNumber) throws Exception {

        try {

            tempIncremantPerr = new TempLimitIncrementPersistance();
            tempBean = new TempLimitIncrementBean();
            CMSCon = DBConnection.getConnection();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();

            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            tempBean = tempIncremantPerr.getCardDetails(CMSCon, cardNumber);
            tempBean = tempIncremantPerr.getOnlineCardDetails(CMSConOnline, tempBean);

            CMSCon.commit();
            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
        }
        return tempBean;
    }

    public List<TempLimitIncrementBean> checkLimitIncrementHistory() throws Exception {
        try {
            tempIncremantPerr = new TempLimitIncrementPersistance();
            tempList = new ArrayList<TempLimitIncrementBean>();
            CMSCon = DBConnection.getConnection();

            CMSCon.setAutoCommit(false);

            tempList = tempIncremantPerr.checkLimitIncrementHistory(CMSCon);

            CMSCon.commit();

        } catch (Exception ex) {
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
        return tempList;
    }

    public Date getDbDate() throws Exception {
        Date currentDate = null;
        try {
            tempIncremantPerr = new TempLimitIncrementPersistance();
            CMSCon = DBConnection.getConnection();

            CMSCon.setAutoCommit(false);

            currentDate = tempIncremantPerr.getDbDate(CMSCon);

            CMSCon.commit();
        } catch (Exception ex) {
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
        return currentDate;
    }

    public CommonCardParameterBean getCommonParameters() throws Exception {

        try {

            tempIncremantPerr = new TempLimitIncrementPersistance();
            commonBean = new CommonCardParameterBean();
            CMSCon = DBConnection.getConnection();

            CMSCon.setAutoCommit(false);

            commonBean = tempIncremantPerr.getCommonParameters(CMSCon);

            CMSCon.commit();
        } catch (Exception ex) {
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
        return commonBean;
    }

    public SystemUserBean checkDualAuthorisation(SystemUserBean sysUserBean) throws Exception {

        try {
            tempIncremantPerr = new TempLimitIncrementPersistance();
            CMSSysUser = new SystemUserBean();
            CMSCon = DBConnection.getConnection();

            CMSCon.setAutoCommit(false);

            CMSSysUser = tempIncremantPerr.checkDualAuthorisation(CMSCon, sysUserBean);

            CMSCon.commit();

        } catch (Exception ex) {
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

        return CMSSysUser;
    }

    public TempLimitIncrementBean incrementCreater(TempLimitIncrementBean tempBean) throws Exception {

        try {
            tempBean.setFlag(true);
            double crdLimit = Double.parseDouble(tempBean.getCreditLimit());
            double cashLimit = Double.parseDouble(tempBean.getCashLimit());
            double avlCreditLimit = Double.parseDouble(tempBean.getAvlCreditLimit());
            double avlCashLimit = Double.parseDouble(tempBean.getAvlCashLimit());
            double onlineAvlCreditLimit = Double.parseDouble(tempBean.getOnlineAvlCreditLimit());
            double onlineAvlCashLimit = Double.parseDouble(tempBean.getOnlineAvlCashLimit());

            double incrementedAmount = 0;
            double amount = 0;
            double rate = 0;

            if (tempBean.getCreditOrCash().equals("CREDIT")) {

                if (!tempBean.getAmount().contentEquals("")) {

                    amount = Double.parseDouble(tempBean.getAmount());
                    incrementedAmount = amount;
                } else if (!tempBean.getRate().contentEquals("")) {

                    rate = Double.parseDouble(tempBean.getRate());
                    incrementedAmount = crdLimit * rate / 100;
                }

                if (tempBean.getIncordec().equals("INC")) {
                    avlCreditLimit = avlCreditLimit + incrementedAmount;
                    onlineAvlCreditLimit = onlineAvlCreditLimit + incrementedAmount;

                } else if (tempBean.getIncordec().equals("DEC") && avlCreditLimit - incrementedAmount >=0) {
                    avlCreditLimit = avlCreditLimit - incrementedAmount;
                    onlineAvlCreditLimit = onlineAvlCreditLimit - incrementedAmount;

                }

//                avlCreditLimit = avlCreditLimit + incrementedAmount;
//                onlineAvlCreditLimit = onlineAvlCreditLimit + incrementedAmount;
                tempBean.setFlag(true);

            }
            if (tempBean.getCreditOrCash().equals("CASH")) {

                if (!tempBean.getAmount().contentEquals("")) {

                    amount = Double.parseDouble(tempBean.getAmount());
                    incrementedAmount = amount;
                } else if (!tempBean.getRate().contentEquals("")) {

                    rate = Double.parseDouble(tempBean.getRate());
                    incrementedAmount = cashLimit * rate / 100;
                }

                if (tempBean.getIncordec().equals("INC")) {
                    if (avlCreditLimit >= avlCashLimit + incrementedAmount) {

                        if (onlineAvlCreditLimit >= onlineAvlCashLimit + incrementedAmount) {
                            avlCashLimit = avlCashLimit + incrementedAmount;
                            onlineAvlCashLimit = onlineAvlCashLimit + incrementedAmount;
                            tempBean.setFlag(true);

                        } else {

                            tempBean.setFlag(false);
                            double possibleIncrementAmount = onlineAvlCreditLimit - onlineAvlCashLimit;
                            tempBean.setErrorMsg(MessageVarList.TOTAL_CASH_LIMIT_HIGHERTHAN_CREDIT_LIMIT + " " + possibleIncrementAmount);

                        }
                    } else {

                        double possibleIncrementAmount = avlCreditLimit - avlCashLimit;
                        tempBean.setFlag(false);
                        tempBean.setErrorMsg(MessageVarList.TOTAL_CASH_LIMIT_HIGHERTHAN_CREDIT_LIMIT + " " + possibleIncrementAmount);

                    }

                } else if (tempBean.getIncordec().equals("DEC")) {
                    if (avlCashLimit - incrementedAmount >=0 && avlCreditLimit >= avlCashLimit - incrementedAmount) {

                        if (onlineAvlCashLimit - incrementedAmount >=0 && onlineAvlCreditLimit >= onlineAvlCashLimit - incrementedAmount) {
                            avlCashLimit = avlCashLimit - incrementedAmount;
                            onlineAvlCashLimit = onlineAvlCashLimit - incrementedAmount;
                            tempBean.setFlag(true);

                        } else {

                            tempBean.setFlag(false);
//                            tempBean.setErrorMsg(MessageVarList.TOTAL_CASH_LIMIT_HIGHERTHAN_CREDIT_LIMIT + " " + onlineAvlCashLimit);
                            tempBean.setErrorMsg("Decrement amount should be less than " + onlineAvlCashLimit);

                        }
                    } else {

                        tempBean.setFlag(false);
//                        tempBean.setErrorMsg(MessageVarList.TOTAL_CASH_LIMIT_HIGHERTHAN_CREDIT_LIMIT + " " + avlCashLimit);
                        tempBean.setErrorMsg("Decrement amount should be less than" + avlCashLimit);

                    }
                    

                }

            }

            if (tempBean.isFlag()) {

                tempBean.setAvlCreditLimit(Double.toString(avlCreditLimit));
                tempBean.setAvlCashLimit(Double.toString(avlCashLimit));
                tempBean.setOnlineAvlCreditLimit(Double.toString(onlineAvlCreditLimit));
                tempBean.setOnlineAvlCashLimit(Double.toString(onlineAvlCashLimit));
                tempBean.setIncrementedAmount(Double.toString(incrementedAmount));
            }

        } catch (Exception ex) {
            throw ex;
        }
        return tempBean;
    }

    public List<TempLimitIncrementBean> searchCards(TempLimitIncrementBean filledBean) throws SQLException, Exception {

        try {
            tempIncremantPerr = new TempLimitIncrementPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardList = tempIncremantPerr.searchCards(CMSCon, filledBean);
            CMSCon.commit();
            return cardList;
        } catch (Exception e) {
            try {
                CMSCon.rollback();
                throw e;
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
    }

    public TempLimitIncrementBean getOnlineDetails(String cardNum) throws SQLException, Exception {

        try {
            tempIncremantPerr = new TempLimitIncrementPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSConOnline.setAutoCommit(false);
            updateBean = tempIncremantPerr.getOnlineDetails(CMSConOnline, cardNum);
            CMSConOnline.commit();
            return updateBean;

        } catch (Exception e) {
            try {

                CMSConOnline.rollback();
                throw e;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSConOnline != null) {
                try {
                    CMSConOnline.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
    }

    public boolean updateTempLimitIncDetails(TempLimitIncrementBean filledBean, String cardNum) throws SQLException, Exception {

        try {
            tempIncremantPerr = new TempLimitIncrementPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            successUpdate = tempIncremantPerr.updateTempLimitIncDetails(CMSCon, filledBean, cardNum);
            CMSCon.commit();
            return successUpdate;
        } catch (Exception e) {
            try {
                CMSCon.rollback();
                throw e;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
    }

    public boolean limitIncrement(TempLimitIncrementBean filledBean, SystemAuditBean systemAuditBean) throws SQLException, Exception {

        try {
            sysAuditManager = new SystemAuditManager();
            tempIncremantPerr = new TempLimitIncrementPersistance();
            CMSCon = DBConnection.getConnection();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            CMSConOnline.setAutoCommit(false);

            successInc1 = tempIncremantPerr.limitIncrement(CMSCon, filledBean);
            successInc2 = tempIncremantPerr.updateTemplimitInc(CMSCon, filledBean);
            successInc3 = tempIncremantPerr.limitIncrementToOnline(CMSConOnline, filledBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSConOnline.commit();

            return (successInc1 && successInc2 && successInc3);

        } catch (Exception e) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
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
            if (CMSConOnline != null) {
                try {
                    CMSConOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

}
