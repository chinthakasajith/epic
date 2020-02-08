/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.mtmm.merchantmgt.merchantlocation.businesslogic;

import com.epic.cms.admin.controlpanel.profilemgt.bean.RiskProfileBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.MerchantCategoryBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
import com.epic.cms.mtmm.merchantmgt.merchantlocation.bean.MerchantLocManualTxnBean;
import com.epic.cms.mtmm.merchantmgt.merchantlocation.bean.MerchantLocationBean;
import com.epic.cms.mtmm.merchantmgt.merchantlocation.bean.MerchantLocationMccBean;
import com.epic.cms.mtmm.merchantmgt.merchantlocation.bean.MerchantLocationTransactionBean;
import com.epic.cms.mtmm.merchantmgt.merchantlocation.persistance.MerchantLocationPersistance;
import com.epic.cms.system.util.config.DBConnection;

import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import com.epic.cms.system.util.variable.TaskVarList;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nalin
 */
public class MerchantLocationManager {

    private Connection CMSCon;
    private Connection CMSOnline;
    private SystemAuditManager sysAuditManager;
    private List<MerchantLocationBean> beanList = null;
    private List<MerchantCategoryBean> merchantCatogoryList = null;
    private List<MerchantCategoryBean> newMerchantCatogoryList = null;
    private List< TypeMgtBean> txnTypeList = null;
    private List< TypeMgtBean> newTxnTypeList = null;
    private List<CurrencyBean> currencyList = null;
    private List<CurrencyBean> newCurrencyList = null;
    private List<RiskProfileBean> riskProfList = null;
    private MerchantLocManualTxnBean manualBean = null;

    public List<MerchantLocationBean> getMerchantLocationDetails() throws Exception {
        try {
            beanList = new ArrayList<MerchantLocationBean>();
            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            beanList = merchLocPer.getMerchantLocationDetails(CMSCon);

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
        return beanList;
    }

    public List<MerchantCategoryBean> getMccByMerchantCustomerNumber(String merchantCustomerNumber) throws Exception {
        try {
            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            merchantCatogoryList = merchLocPer.getMccByMerchantCustomerNumber(CMSCon, merchantCustomerNumber);

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
        return merchantCatogoryList;
    }

    public List<TypeMgtBean> getTxnTypeByMerchantCustomerNumber(String merchantCustomerNumber) throws Exception {
        try {
            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            txnTypeList = merchLocPer.getTxnTypeByMerchantCustomerNumber(CMSCon, merchantCustomerNumber);

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
        return txnTypeList;
    }

    public List<CurrencyBean> getCurrencyByMerchantCustomerNumber(String merchantCustomerNumber) throws Exception {
        try {
            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            currencyList = merchLocPer.getCurrencyByMerchantCustomerNumber(CMSCon, merchantCustomerNumber);

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

    public List<MerchantLocationBean> searchMerchantLocationDetails(MerchantLocationBean merchantLocBean) throws Exception {
        try {
            beanList = new ArrayList<MerchantLocationBean>();
            sysAuditManager = new SystemAuditManager();
            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            beanList = merchLocPer.searchMerchantLocationDetails(merchantLocBean, CMSCon);


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
        return beanList;
    }

    public String insertMerchantLocation(MerchantLocationBean merchantLocBean, SystemAuditBean systemAuditBean) throws Exception {

        boolean success = false;
        boolean isSuccess = false;
        String tag = "";

        try {
            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection(); /// connection for online DB

            if (merchantLocBean != null) {

                if ((merchantLocBean.getStatus()).equals("ACT")) {
                    merchantLocBean.setStatusToOnline(1);
                } else if ((merchantLocBean.getStatus()).equals("DEA")) {
                    merchantLocBean.setStatusToOnline(2);
                }
            }

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            boolean isExsist = merchLocPer.checkMerchantLocationExsistance(CMSCon, merchantLocBean.getMerchantId());

            if (isExsist) {

                success = merchLocPer.updateMerchantLocation(CMSCon, merchantLocBean);
                isSuccess = merchLocPer.updateMerchantLocationOnline(CMSOnline, merchantLocBean, merchLocPer.getCountryNumCode(CMSCon, merchantLocBean.getCountry()));
                systemAuditBean.setDescription("Update: '" + merchantLocBean.getMerchantId() + "' Merchant Id by : " + merchantLocBean.getLastUpdatedUser());
                systemAuditBean.setTaskCode(TaskVarList.UPDATE);
                sysAuditManager.insertAudit(systemAuditBean, CMSCon);

                if (success & isSuccess) {
                    tag = "update";
                }

            } else {

                String merchantAccountNo = merchLocPer.getMaxAccnoFromMerchantLocation(CMSCon);
                String newAccontno = this.zeroPadding((Integer.parseInt(merchantAccountNo) + 1) + "", 12);
                //String newAccontno = ISOUtil.zeropad((Integer.parseInt(merchantAccountNo) + 1) + "", 12);
                merchantLocBean.setMerchantAccountNo(newAccontno);
                success = merchLocPer.insertMerchantLocation(CMSCon, merchantLocBean);
                isSuccess = merchLocPer.insertMerchantOnline(CMSOnline, merchantLocBean, merchLocPer.getCountryNumCode(CMSCon, merchantLocBean.getCountry()));
                sysAuditManager.insertAudit(systemAuditBean, CMSCon);

                if (success & isSuccess) {
                    tag = "add".concat(newAccontno);
                }
            }


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
        return tag;
    }

    public boolean insertToMcc(String merchantId, String[] assignMerchantCategorylist, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        boolean isSuccess = false;
        try {
            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection(); /// connection for online DB

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            if (assignMerchantCategorylist != null) {
                for (int i = 0; i < assignMerchantCategorylist.length; i++) {

                    success = merchLocPer.insertToMcc(CMSCon, merchantId, assignMerchantCategorylist[i], systemAuditBean.getLastUpdateduser());
                }
            }

//            for (int i = 0; i < assignMerchantCategorylist.length; i++) {
//
//                isSuccess = merchLocPer.insertToMccOnline(CMSOnline, merchantId, assignMerchantCategorylist[i]);
//
//
//            }

//            if (!success && !isSuccess) {
//                success = false;
//            }
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();
            CMSOnline.commit();


        } catch (Exception ex) {
            CMSCon.rollback();
            CMSOnline.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);

        }

        return success;

    }

    public boolean insertToTxnType(String merchantId, String[] assignTxnTypeList, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        boolean isSuccess = false;
        try {
            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection(); /// connection for online DB

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            if (assignTxnTypeList != null) {
                for (int i = 0; i < assignTxnTypeList.length; i++) {

                    success = merchLocPer.insertToTxnType(CMSCon, merchantId, assignTxnTypeList[i], systemAuditBean.getLastUpdateduser());

                }

//                for (int i = 0; i < assignTxnTypeList.length; i++) {
//
                //                  int onlineTxnTypeCode = merchLocPer.getOnlineTxnCode(CMSCon, assignTxnTypeList[i]);
//                   isSuccess = merchLocPer.insertToTxnTypeOnline(CMSOnline, merchantId, onlineTxnTypeCode);
//
//                }
            }

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();
            CMSOnline.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            CMSOnline.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
        }
        return success;
    }

    public boolean insertToCurrency(String merchantId, String[] assignCurrencyList, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        boolean isSuccess = false;
        try {
            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection(); /// connection for online DB

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            if (assignCurrencyList != null) {
                for (int i = 0; i < assignCurrencyList.length; i++) {

                    success = merchLocPer.insertToCurrency(CMSCon, merchantId, assignCurrencyList[i], systemAuditBean.getLastUpdateduser());

                }
            }
//            for (int i = 0; i < assignTxnTypeList.length; i++) {
//
//                int onlineTxnTypeCode = merchLocPer.getOnlineTxnCode(CMSCon, assignTxnTypeList[i]);
//                isSuccess = merchLocPer.insertToTxnTypeOnline(CMSOnline, merchantId, onlineTxnTypeCode);
//
//            }

//            if (!success && !isSuccess) {
//                success = false;
//            }
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();
            CMSOnline.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            CMSOnline.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
        }
        return success;
    }

    public List<MerchantCategoryBean> getNotAssignedMccList(String merchantId, String merchantCustomerNumber) throws Exception {
        try {
            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            merchantCatogoryList = merchLocPer.getNotAssignedMccList(CMSCon, merchantId, merchantCustomerNumber);

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
        return merchantCatogoryList;
    }

    public List<MerchantCategoryBean> getAssignedMccList(String merchantId) throws Exception {
        try {
            merchantCatogoryList = new ArrayList<MerchantCategoryBean>();
            newMerchantCatogoryList = new ArrayList<MerchantCategoryBean>();
            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            merchantCatogoryList = merchLocPer.getAssignedMccList(CMSCon, merchantId);

            for (int i = 0; i < merchantCatogoryList.size(); i++) {
                MerchantCategoryBean bean = new MerchantCategoryBean();
                bean = merchLocPer.getMccDescriptionByCode(CMSCon, merchantCatogoryList.get(i).getmCCCode());

                newMerchantCatogoryList.add(bean);
            }


            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return newMerchantCatogoryList;
    }

    public List<TypeMgtBean> getNotAssignedTxnTypeList(String merchantId, String merchantCustomerNumber) throws Exception {
        try {
            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            txnTypeList = merchLocPer.getNotAssignedTxnTypeList(CMSCon, merchantId, merchantCustomerNumber);

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
        return txnTypeList;
    }

    public List<TypeMgtBean> getAssignedTxnTypeList(String merchantId) throws Exception {
        try {
            txnTypeList = new ArrayList< TypeMgtBean>();
            newTxnTypeList = new ArrayList< TypeMgtBean>();
            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            txnTypeList = merchLocPer.getAssignedTxnTypeList(CMSCon, merchantId);

            for (int i = 0; i < txnTypeList.size(); i++) {
                TypeMgtBean bean = new TypeMgtBean();
                bean = merchLocPer.getTxnDescriptionByCode(CMSCon, txnTypeList.get(i).getTransactionTypeCode());

                newTxnTypeList.add(bean);
            }

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
        return newTxnTypeList;
    }

    public List<CurrencyBean> getAssignedCurrencyList(String merchantId) throws Exception {
        try {
            currencyList = new ArrayList< CurrencyBean>();
            newCurrencyList = new ArrayList< CurrencyBean>();
            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            currencyList = merchLocPer.getAssignedCurrencyList(CMSCon, merchantId);

            for (int i = 0; i < currencyList.size(); i++) {
                CurrencyBean bean = new CurrencyBean();
                bean = merchLocPer.getCurrencyDescriptionByCode(CMSCon, currencyList.get(i).getCurrencyCode());

                newCurrencyList.add(bean);
            }

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
        return newCurrencyList;
    }

    public List<CurrencyBean> getNotAssignedCurrencyList(String merchantId, String merchantCustomerNumber) throws Exception {
        try {
            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            currencyList = merchLocPer.getNotAssignedCurrencyList(CMSCon, merchantId, merchantCustomerNumber);

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

    public boolean deleteMerchantLocation(MerchantLocationBean merchantLocBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            String tId = merchLocPer.getCommonManualTerminal(CMSCon);

            success = merchLocPer.deleteMerchantLocationManualTerminalTxn(CMSCon, merchantLocBean.getMerchantId(), tId);
            success = merchLocPer.deleteMerchantLocationManualTerminal(CMSCon, merchantLocBean.getMerchantId(), tId);

            success = merchLocPer.deleteMerchantLocationManualTerminalTxnOnline(CMSOnline, merchantLocBean.getMerchantId(), tId);
            success = merchLocPer.deleteMerchantLocationManualTerminalOnline(CMSOnline, merchantLocBean.getMerchantId(), tId);

            success = merchLocPer.deleteMerchantLocationMcc(CMSCon, merchantLocBean);
            success = merchLocPer.deleteMerchantLocationTxn(CMSCon, merchantLocBean);
            success = merchLocPer.deleteMerchantLocationCurrency(CMSCon, merchantLocBean);

            success = merchLocPer.deleteMerchantLocation(CMSCon, merchantLocBean);
            success = merchLocPer.deleteMerchantLocationOnline(CMSOnline, merchantLocBean);




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

    public boolean updateMerchantLocation(MerchantLocationBean merchantLocBean, SystemAuditBean systemAuditBean) throws Exception {

        boolean success = false;
        boolean isSuccess = false;

        try {
            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection(); /// connection for online DB

            if (merchantLocBean != null) {

                if ((merchantLocBean.getStatus()).equals("ACT")) {
                    merchantLocBean.setStatusToOnline(1);
                } else if ((merchantLocBean.getStatus()).equals("DEA")) {
                    merchantLocBean.setStatusToOnline(2);
                }
            }

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            success = merchLocPer.updateMerchantLocation(CMSCon, merchantLocBean);
            isSuccess = merchLocPer.updateMerchantLocationOnline(CMSOnline, merchantLocBean, merchLocPer.getCountryNumCode(CMSCon, merchantLocBean.getCountry()));

            if (!success && !isSuccess) {
                success = false;
            }

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();
            CMSOnline.commit();

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
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
        }
        return success;
    }

    public List<String> assignMcc(String merchantId, String[] assignArray, String[] unAssignArray, String sysUser, SystemAuditBean auditBean) throws Exception {
        try {



            List<String> existSecList = new ArrayList<String>();
            // List<String> existSecListOnline = new ArrayList<String>();

            int result = -1;
            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            //create Db connection
            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection(); /// connection for online DB
            //begin transaction
            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);


            //unasign array check wheter there is a item in the array to remove
            if (unAssignArray != null && unAssignArray.length > 0) {



                for (String mccRem : unAssignArray) {
                    //delete all sections
                    try {
                        //check the existance of the removing item in the table 
                        boolean isExist = merchLocPer.findExistanceOfMcc(CMSCon, merchantId, mccRem);
                        //boolean isExistOnline = merchLocPer.findExistanceOfMccOnline(CMSOnline, merchantId, mccRem);

                        if (isExist) {
                            existSecList.add(mccRem);
                            // existSecListOnline.add(mccRem);
                        }

                    } catch (Exception ex) {
                        throw ex;
                    }
                }
            }


            //if there is no exception and success deletion move to insert



            if (assignArray != null && assignArray.length > 0) {

                for (String mcc : assignArray) {
                    MerchantLocationMccBean merchantLocMccBean = new MerchantLocationMccBean();
                    merchantLocMccBean.setMerchantId(merchantId);
                    merchantLocMccBean.setMcc(mcc);

                    try {

                        //check the existance of the removing item in the table 
                        boolean isExist = merchLocPer.findExistanceOfMcc(CMSCon, merchantId, mcc);
                        //boolean isExistOnline = merchLocPer.findExistanceOfMccOnline(CMSOnline, merchantId, mcc);

                        //if not in the table delete item
                        if (!isExist) {
                            //select from
                            //insert all sections to the databese
                            merchLocPer.insertToMcc(CMSCon, merchantId, mcc, auditBean.getLastUpdateduser());
                            //  merchLocPer.insertToMccOnline(CMSOnline, merchantId, mcc);

                        }


                    } catch (Exception sex) {

                        throw sex;
                    }


                }
            }
            CMSCon.commit();
            CMSOnline.commit();

            return existSecList;


        } catch (Exception ex) {
            CMSCon.rollback();
            CMSOnline.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
        }

    }

    public List<String> assignTxn(String merchantId, String[] assignArray, String[] unAssignArray, String sysUser, SystemAuditBean auditBean) throws Exception {
        try {



            List<String> existSecList = new ArrayList<String>();

            int result = -1;
            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            //create Db connection
            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection(); /// connection for online DB
            //begin transaction
            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);


            //unasiign array check wheter there is a item in the array to remove
            if (unAssignArray != null && unAssignArray.length > 0) {



                for (String txnRem : unAssignArray) {
                    //delete all sections
                    try {
                        //check the existance of the removing item in the table 
                        boolean isExist = merchLocPer.findExistanceOfTxn(CMSCon, merchantId, txnRem);
//                        int onlineTxnTypeCode = merchLocPer.getOnlineTxnCode(CMSCon, txnRem);
//                        boolean isExistOnline = merchLocPer.findExistanceOfTxnOnline(CMSOnline, merchantId, onlineTxnTypeCode);


                        if (isExist) {
                            existSecList.add(txnRem);
                        }

                    } catch (Exception ex) {
                        throw ex;
                    }
                }
            }


            //if there is no exception and success deletion move to insert



            if (assignArray != null && assignArray.length > 0) {

                for (String txn : assignArray) {
                    MerchantLocationTransactionBean merchantLocTxnBean = new MerchantLocationTransactionBean();
                    merchantLocTxnBean.setMerchantId(merchantId);
                    merchantLocTxnBean.setTxnCode(txn);

                    try {

                        //check the existance of the removing item in the table 
                        boolean isExist = merchLocPer.findExistanceOfTxn(CMSCon, merchantId, txn);
//                        int onlineTxnTypeCode = merchLocPer.getOnlineTxnCode(CMSCon, txn);
//                        boolean isExistOnline = merchLocPer.findExistanceOfTxnOnline(CMSOnline, merchantId, onlineTxnTypeCode);
                        //if not in the table delete item
                        if (!isExist) {
                            //select from
                            //insert all sections to the databese
                            merchLocPer.insertToTxnType(CMSCon, merchantId, txn, auditBean.getLastUpdateduser());
                            // merchLocPer.insertToTxnTypeOnline(CMSOnline, merchantId, onlineTxnTypeCode);
                        }


                    } catch (Exception sex) {

                        throw sex;
                    }


                }
            }
            CMSCon.commit();
            CMSOnline.commit();

            return existSecList;


        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
        }

    }

    public List<String> assignCurrency(String merchantId, String[] assignArray, String[] unAssignArray, String sysUser, SystemAuditBean auditBean) throws Exception {
        try {



            List<String> existSecList = new ArrayList<String>();

            int result = -1;
            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            //create Db connection
            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection(); /// connection for online DB
            //begin transaction
            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);


            //unasiign array check wheter there is a item in the array to remove
            if (unAssignArray != null && unAssignArray.length > 0) {



                for (String currencyRem : unAssignArray) {
                    //delete all sections
                    try {
                        //check the existance of the removing item in the table 
                        boolean isExist = merchLocPer.findExistanceOfCurrency(CMSCon, merchantId, currencyRem);
                        //int onlineTxnTypeCode = merchLocPer.getOnlineTxnCode(CMSCon, currencyRem);
                        // boolean isExistOnline = merchLocPer.findExistanceOfTxnOnline(CMSOnline, merchantId, onlineTxnTypeCode);


                        if (isExist) {
                            existSecList.add(currencyRem);
                        }

                    } catch (Exception ex) {
                        throw ex;
                    }
                }
            }


            //if there is no exception and success deletion move to insert



            if (assignArray != null && assignArray.length > 0) {

                for (String currency : assignArray) {
//                    MerchantLocationTransactionBean merchantLocTxnBean = new MerchantLocationTransactionBean();
//                    merchantLocTxnBean.setMerchantId(merchantId);
//                    merchantLocTxnBean.setTxnCode(txn);

                    try {

                        //check the existance of the removing item in the table 
                        boolean isExist = merchLocPer.findExistanceOfCurrency(CMSCon, merchantId, currency);
//                        int onlineTxnTypeCode = merchLocPer.getOnlineTxnCode(CMSCon, txn);
//                        boolean isExistOnline = merchLocPer.findExistanceOfTxnOnline(CMSOnline, merchantId, onlineTxnTypeCode);
                        //if not in the table delete item
                        if (!isExist) {
                            //select from
                            //insert all sections to the databese
                            merchLocPer.insertToCurrency(CMSCon, merchantId, currency, auditBean.getLastUpdateduser());
                            // merchLocPer.insertToTxnTypeOnline(CMSOnline, merchantId, onlineTxnTypeCode);
                        }


                    } catch (Exception sex) {

                        throw sex;
                    }


                }
            }
            CMSCon.commit();
            CMSOnline.commit();

            return existSecList;


        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
        }

    }

    public void deleteMcc(List<String> existList, String merchantId) throws Exception {
        try {
            //List<MerchantCustomerBean> sectionRemList = null;
            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection(); /// connection for online DB

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            for (int i = 0; i < existList.size(); i++) {
                try {
                    //System.out.println("task code = "+existList.get(i));
                    //System.out.println("page code = "+pageCode);
                    merchLocPer.deleteMerchantMccByMcc(CMSCon, existList.get(i), merchantId);
                    // merchLocPer.deleteMerchantMccByMccOnline(CMSOnline, existList.get(i), merchantId);

                } catch (SQLException ex) {
                    throw ex;
                }
            }
            CMSCon.commit();
            CMSOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSOnline.rollback();

                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null | CMSOnline != null) {
                try {
                    CMSCon.close();
                    CMSOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }



    }

    public void deleteTxn(List<String> existList, String merchantId) throws Exception {
        try {
            //List<MerchantCustomerBean> sectionRemList = null;
            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection(); /// connection for online DB

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            for (int i = 0; i < existList.size(); i++) {
                try {
                    //System.out.println("task code = "+existList.get(i));
                    //System.out.println("page code = "+pageCode);
                    merchLocPer.deleteMerchantTxnByTxnCode(CMSCon, existList.get(i), merchantId);
//                    int onlineTxnTypeCode = merchLocPer.getOnlineTxnCode(CMSCon, existList.get(i));
//                    merchLocPer.deleteMerchantTxnByTxnCodeOnline(CMSOnline, onlineTxnTypeCode, merchantId);

                } catch (SQLException ex) {
                    throw ex;
                }
            }
            CMSCon.commit();
            CMSOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSOnline.rollback();

                throw ex;

            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null | CMSOnline != null) {
                try {
                    CMSCon.close();
                    CMSOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }



    }

    public void deleteCurrency(List<String> existList, String merchantId) throws Exception {
        try {
            //List<MerchantCustomerBean> sectionRemList = null;
            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection(); /// connection for online DB

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            for (int i = 0; i < existList.size(); i++) {
                try {
                    //System.out.println("task code = "+existList.get(i));
                    //System.out.println("page code = "+pageCode);
                    merchLocPer.deleteMerchantCurrencyByCurrencyCode(CMSCon, existList.get(i), merchantId);
//                    int onlineTxnTypeCode = merchLocPer.getOnlineTxnCode(CMSCon, existList.get(i));
//                    merchLocPer.deleteMerchantTxnByTxnCodeOnline(CMSOnline, onlineTxnTypeCode, merchantId);

                } catch (SQLException ex) {
                    throw ex;
                }
            }
            CMSCon.commit();
            CMSOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSOnline.rollback();

                throw ex;

            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null | CMSOnline != null) {
                try {
                    CMSCon.close();
                    CMSOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    public List<RiskProfileBean> getTerminalRiskProfile(String rpType, String merchantId) throws Exception {
        try {
            riskProfList = new ArrayList<RiskProfileBean>();
            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            riskProfList = merchLocPer.getTerminalRiskProfile(CMSCon, rpType, merchantId);

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
        return riskProfList;
    }

    public List<MerchantCategoryBean> getMccOfMerchant(String merchantId) throws Exception {
        try {
            merchantCatogoryList = new ArrayList<MerchantCategoryBean>();
            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            merchantCatogoryList = merchLocPer.getMccOfMerchant(CMSCon, merchantId);

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
        return merchantCatogoryList;
    }

    public List<CurrencyBean> getCurrencyOfMerchant(String merchantId) throws Exception {
        try {
            currencyList = new ArrayList<CurrencyBean>();
            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            currencyList = merchLocPer.getCurrencyOfMerchant(CMSCon, merchantId);

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

    public List<TypeMgtBean> getTxnTypeOfMerchant(String merchantId) throws Exception {
        try {
            txnTypeList = new ArrayList<TypeMgtBean>();
            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            txnTypeList = merchLocPer.getTxnTypeOfMerchant(CMSCon, merchantId);

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
        return txnTypeList;
    }

    public List<TypeMgtBean> getAssignTxnTypeOfMerchantTerminal(String merchantId) throws Exception {
        try {
            txnTypeList = new ArrayList<TypeMgtBean>();
            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            String tId = merchLocPer.getCommonManualTerminal(CMSCon);
            txnTypeList = merchLocPer.getAssignTxnTypeOfMerchantTerminal(CMSCon, merchantId, tId);

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
        return txnTypeList;
    }

    public List<TypeMgtBean> getNotAssignTxnTypeOfMerchantTerminal(String merchantId) throws Exception {
        try {
            txnTypeList = new ArrayList<TypeMgtBean>();
            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            String tId = merchLocPer.getCommonManualTerminal(CMSCon);
            txnTypeList = merchLocPer.getNotAssignTxnTypeOfMerchantTerminal(CMSCon, merchantId, tId);

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
        return txnTypeList;
    }

    public boolean insertMerchantLocationManualTxn(MerchantLocManualTxnBean manualBean, String[] txnList, SystemAuditBean auditBean) throws Exception {

        boolean success = false;
        try {

            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection(); /// connection for online DB

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            manualBean.setTerminalId(merchLocPer.getCommonManualTerminal(CMSCon));

            success = merchLocPer.insertMerchantLocationManualTxn(CMSCon, manualBean);
            success = merchLocPer.insertMerchantLocationManualTxnToOnline(CMSOnline, manualBean);

            if (success) {
                if (txnList != null) {

                    for (int i = 0; i < txnList.length; i++) {

                        success = merchLocPer.insertToTerminalTxnType(CMSCon, manualBean, txnList[i]);
                    }
                    for (int i = 0; i < txnList.length; i++) {

                        int onlineTxnTypeCode = merchLocPer.getOnlineTxnCode(CMSCon, txnList[i]);
                        success = merchLocPer.insertToTerminalTxnTypeToOnline(CMSOnline, manualBean, onlineTxnTypeCode);
                    }
                }
            }

            CMSCon.commit();
            CMSOnline.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            CMSOnline.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
        }
        return success;
    }

    public boolean checkExsistanceOfManualTerminal(String mId) throws Exception {
        boolean isExsist = false;
        try {

            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            CMSCon = DBConnection.getConnection();

            CMSCon.setAutoCommit(false);

            isExsist = merchLocPer.checkExsistanceOfManualTerminal(CMSCon, mId);

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
        return isExsist;
    }

    public MerchantLocManualTxnBean getMerchantManualTerminalData(String mId) throws Exception {

        try {

            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            manualBean = new MerchantLocManualTxnBean();
            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection(); /// connection for online DB

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            String commmonTid = merchLocPer.getCommonManualTerminal(CMSCon);
            manualBean = merchLocPer.getMerchantManualTerminalData(CMSOnline, mId, commmonTid);

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
        return manualBean;
    }

    public boolean updateMerchantLocationManualTxn(MerchantLocManualTxnBean manualBean, String[] txnList, SystemAuditBean auditBean) throws Exception {
        boolean success = true;

        try {

            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection(); /// connection for online DB

            success = merchLocPer.UpdateMerchantLocationManualTxn(CMSCon, manualBean);
            success = merchLocPer.updateMerchantLocationManualTxnToOnline(CMSOnline, manualBean);

            String tId = merchLocPer.getCommonManualTerminal(CMSCon);
            manualBean.setTerminalId(tId);
            success = merchLocPer.deleteMerchantLocationManualTerminalTxn(CMSCon, manualBean.getMerchantId(), tId);
            success = merchLocPer.deleteMerchantLocationManualTerminalTxnOnline(CMSOnline, manualBean.getMerchantId(), tId);

            if (success) {
                if (txnList != null) {

                    for (int i = 0; i < txnList.length; i++) {

                        success = merchLocPer.insertToTerminalTxnType(CMSCon, manualBean, txnList[i]);
                    }
                    for (int i = 0; i < txnList.length; i++) {

                        int onlineTxnTypeCode = merchLocPer.getOnlineTxnCode(CMSCon, txnList[i]);
                        success = merchLocPer.insertToTerminalTxnTypeToOnline(CMSOnline, manualBean, onlineTxnTypeCode);
                    }
                }
            }

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);


            CMSCon.commit();
            CMSOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSOnline.rollback();

                throw ex;

            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null | CMSOnline != null) {
                try {
                    DBConnection.dbConnectionClose(CMSCon);
                    DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return success;

    }

    public boolean deleteManualTerminal(String mId) throws Exception {
        boolean success = false;
        try {
            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            String tId = merchLocPer.getCommonManualTerminal(CMSCon);

            success = merchLocPer.deleteMerchantLocationManualTerminalTxn(CMSCon, mId, tId);
            success = merchLocPer.deleteMerchantLocationManualTerminal(CMSCon, mId, tId);

            success = merchLocPer.deleteMerchantLocationManualTerminalTxnOnline(CMSOnline, mId, tId);
            success = merchLocPer.deleteMerchantLocationManualTerminalOnline(CMSOnline, mId, tId);

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

    public String getRedemPointValue(String merchantCusNum) throws Exception {
        String redemPoint = null;
        try {

            MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            redemPoint = merchLocPer.getRedemPointValue(CMSCon, merchantCusNum);

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
        return redemPoint;
    }

    public String getCommonMerchant() throws Exception {
        String commonMid = null;
        try {
            
             MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            commonMid = merchLocPer.getCommonMerchant(CMSCon);

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
        return  commonMid;
    }
    
    public String getCommonManualTerminal() throws Exception {
        String commonTid = null;
        try {
            
             MerchantLocationPersistance merchLocPer = new MerchantLocationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            commonTid = merchLocPer.getCommonManualTerminal(CMSCon);

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
        return  commonTid;
    }

    public String zeroPadding(String input, int minLength) throws Exception {

        try {
            int length = input.length();
            if (length < minLength) {

                while (length++ < minLength) {
                    input = "0" + input;
                }
            }
        } catch (Exception ex) {
            throw ex;
        }
        return input;
    }
}
