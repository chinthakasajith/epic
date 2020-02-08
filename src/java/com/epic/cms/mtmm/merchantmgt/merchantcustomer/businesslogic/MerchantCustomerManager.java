/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.mtmm.merchantmgt.merchantcustomer.businesslogic;

import com.epic.cms.admin.controlpanel.profilemgt.bean.CommissionProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.FeeProfileBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.BankBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.BillingCycleMgtBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.MerchantCategoryBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantBankBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantBankBranchBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantCustomerBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantCustomerMccBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantCustomerTransactionBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantPaymentCycleBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantPaymentModeBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.persistance.MerchantCustomerPersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.variable.TaskVarList;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author nalin
 */
public class MerchantCustomerManager {

    private MerchantCustomerPersistance merchCusPer = null;
    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;
    private List<MerchantCustomerBean> beanList = null;
    private List<MerchantCustomerBean> branchList = null;
    private List<MerchantCategoryBean> merchantCatogoryList = null;
    private List<MerchantCategoryBean> newMerchantCatogoryList = null;
    private List< TypeMgtBean> txnTypeList = null;
    private List< TypeMgtBean> newTxnTypeList = null;
    private List<CurrencyBean> currencyList = null;
    private List<CurrencyBean> newCurrencyList = null;
    private MerchantCustomerBean merchantBean = null;
    private List<CommissionProfileBean> commissionList = null;
    private List<BillingCycleMgtBean> statementList = null;
    private List<MerchantPaymentCycleBean> paymentList = null;
    private List<MerchantPaymentModeBean> paymentModeList = null;
    private List<BankBean> bankList = null;
    private HashMap<String, String> accountTypeList = null;
    private List<MerchantBankBean> merchantBankList = null;
    private List<MerchantBankBranchBean> merchantBankBranchList = null;
    private HashMap<String, String> currencyTypeList = null;

    public List<MerchantCustomerBean> getMerchantCustomerDetails() throws Exception {
        try {
            beanList = new ArrayList<MerchantCustomerBean>();
            merchCusPer = new MerchantCustomerPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            beanList = merchCusPer.getMerchantCustomerDetails(CMSCon);

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

    public MerchantCustomerBean getMerchantCustomer(String merchantCustomerNumber) throws Exception {
        try {
            merchantBean = new MerchantCustomerBean();
            merchCusPer = new MerchantCustomerPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            merchantBean = merchCusPer.getMerchantCustomer(CMSCon, merchantCustomerNumber);

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
        return merchantBean;
    }

    public List<MerchantCategoryBean> getNotAssignedMccList(String merchantCustomerNumber) throws Exception {
        try {
            merchCusPer = new MerchantCustomerPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            merchantCatogoryList = merchCusPer.getNotAssignedMccList(CMSCon, merchantCustomerNumber);

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

    public List<MerchantCategoryBean> getAssignedMccList(String merchantCustomerNumber) throws Exception {
        try {
            merchantCatogoryList = new ArrayList<MerchantCategoryBean>();
            newMerchantCatogoryList = new ArrayList<MerchantCategoryBean>();
            merchCusPer = new MerchantCustomerPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            merchantCatogoryList = merchCusPer.getAssignedMccList(CMSCon, merchantCustomerNumber);

            for (int i = 0; i < merchantCatogoryList.size(); i++) {
                MerchantCategoryBean bean = new MerchantCategoryBean();
                bean = merchCusPer.getMccDescriptionByCode(CMSCon, merchantCatogoryList.get(i).getmCCCode());

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

    public List<TypeMgtBean> getNotAssignedTxnTypeList(String merchantCustomerNumber) throws Exception {
        try {
            merchCusPer = new MerchantCustomerPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            txnTypeList = merchCusPer.getNotAssignedTxnTypeList(CMSCon, merchantCustomerNumber);

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

    public List<TypeMgtBean> getAssignedTxnTypeList(String merchantCustomerNumber) throws Exception {
        try {
            txnTypeList = new ArrayList< TypeMgtBean>();
            newTxnTypeList = new ArrayList< TypeMgtBean>();
            merchCusPer = new MerchantCustomerPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            txnTypeList = merchCusPer.getAssignedTxnTypeList(CMSCon, merchantCustomerNumber);

            for (int i = 0; i < txnTypeList.size(); i++) {
                TypeMgtBean bean = new TypeMgtBean();
                bean = merchCusPer.getTxnDescriptionByCode(CMSCon, txnTypeList.get(i).getTransactionTypeCode());

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

    public List<CurrencyBean> getNotAssignedCurrencyList(String merchantCustomerNumber) throws Exception {
        try {
            merchCusPer = new MerchantCustomerPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            currencyList = merchCusPer.getNotAssignedCurrencyList(CMSCon, merchantCustomerNumber);

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

    public List<CurrencyBean> getAssignedCurrencyList(String merchantCustomerNumber) throws Exception {
        try {
            currencyList = new ArrayList< CurrencyBean>();
            newCurrencyList = new ArrayList< CurrencyBean>();
            merchCusPer = new MerchantCustomerPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            currencyList = merchCusPer.getAssignedCurrencyList(CMSCon, merchantCustomerNumber);

            for (int i = 0; i < currencyList.size(); i++) {
                CurrencyBean bean = new CurrencyBean();
                bean = merchCusPer.getCurrencyDescriptionByCode(CMSCon, currencyList.get(i).getCurrencyCode());

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

    public List<MerchantCustomerBean> getBankBranchList(MerchantCustomerBean merchantBean) throws Exception {
        try {
            merchCusPer = new MerchantCustomerPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            branchList = merchCusPer.getBankBranchList(CMSCon, merchantBean);

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
        return branchList;
    }

    public List<MerchantCustomerBean> searchMerchantCustomerDetails(MerchantCustomerBean merchantBean, SystemAuditBean systemAuditBean) throws Exception {
        try {
            beanList = new ArrayList<MerchantCustomerBean>();
            sysAuditManager = new SystemAuditManager();
            merchCusPer = new MerchantCustomerPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            beanList = merchCusPer.searchMerchantCustomerDetails(merchantBean, CMSCon);

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
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

    public String insertMerchantCustomer(MerchantCustomerBean merchantBean, SystemAuditBean systemAuditBean) throws Exception {

        boolean success = false;
        String tag = "";

        try {
            merchCusPer = new MerchantCustomerPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();

            CMSCon.setAutoCommit(false);

            boolean isExsist = merchCusPer.checkMerchantCustomerExsistance(CMSCon, merchantBean.getMerchantCustomerNumber());

            if (isExsist) {

                success = merchCusPer.updateMerchantCustomer(CMSCon, merchantBean);
                systemAuditBean.setDescription("Update: '" + merchantBean.getMerchantCustomerNumber() + "' Merchant Customer Number by : " + merchantBean.getLastUpdatedUser());
                systemAuditBean.setTaskCode(TaskVarList.UPDATE);
                sysAuditManager.insertAudit(systemAuditBean, CMSCon);

                if (success) {
                    tag = "update";
                }

            } else {

                String merchantAccountNo = merchCusPer.getMaxAccnoFromMerchantCustomer(CMSCon);
                String newAccontno = this.zeroPadding((Integer.parseInt(merchantAccountNo) + 1) + "", 12);
                // String newAccontno = ISOUtil.zeropad((Integer.parseInt(merchantAccountNo) + 1) + "", 12);

                merchantBean.setMerchantAccountNo(newAccontno);
                success = merchCusPer.insertMerchantCustomer(CMSCon, merchantBean);
                sysAuditManager.insertAudit(systemAuditBean, CMSCon);

                if (success) {
                    tag = "add".concat(newAccontno);
                }
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
        return tag;
    }

    public boolean updateMerchantCustomer(MerchantCustomerBean merchantBean, SystemAuditBean systemAuditBean) throws Exception {

        boolean success = false;

        try {
            merchCusPer = new MerchantCustomerPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();

            CMSCon.setAutoCommit(false);
            success = merchCusPer.updateMerchantCustomer(CMSCon, merchantBean);

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
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
        return success;
    }

    public boolean deleteMerchantCustomer(MerchantCustomerBean merchantBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            merchCusPer = new MerchantCustomerPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            success = merchCusPer.deleteMerchantCustomer(CMSCon, merchantBean);
            if (success) {
                merchCusPer.deleteMcc(CMSCon, merchantBean);
                merchCusPer.deleteTxn(CMSCon, merchantBean);
                merchCusPer.deleteCurrency(CMSCon, merchantBean);
            }
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
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
        return success;
    }

    public boolean insertToMcc(String merchantCustomerNumber, String[] assignMerchantCategorylist, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            merchCusPer = new MerchantCustomerPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            if (assignMerchantCategorylist != null) {
                for (int i = 0; i < assignMerchantCategorylist.length; i++) {

                    success = merchCusPer.insertToMcc(CMSCon, merchantCustomerNumber, assignMerchantCategorylist[i], systemAuditBean.getLastUpdateduser());
                    CMSCon.commit();
                }
            }


        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return success;
    }

    public boolean insertToTxnType(String merchantCustomerNumber, String[] assignTxnTypeList, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            merchCusPer = new MerchantCustomerPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            if (assignTxnTypeList != null) {
                for (int i = 0; i < assignTxnTypeList.length; i++) {

                    success = merchCusPer.insertToTxnType(CMSCon, merchantCustomerNumber, assignTxnTypeList[i], systemAuditBean.getLastUpdateduser());
                    CMSCon.commit();
                }
            }


        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return success;
    }

    public boolean insertToCurrency(String merchantCustomerNumber, String[] assignCurrencyList, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            merchCusPer = new MerchantCustomerPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            if (assignCurrencyList != null) {
                for (int i = 0; i < assignCurrencyList.length; i++) {

                    success = merchCusPer.insertToCurrency(CMSCon, merchantCustomerNumber, assignCurrencyList[i], systemAuditBean.getLastUpdateduser());
                    CMSCon.commit();
                }
            }

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return success;
    }

    public List<String> assignMcc(String merchantCustomerNumber, String[] assignArray, String[] unAssignArray, String sysUser, SystemAuditBean auditBean) throws Exception {
        try {



            List<String> existSecList = new ArrayList<String>();

            int result = -1;
            merchCusPer = new MerchantCustomerPersistance();
            //create Db connection
            CMSCon = DBConnection.getConnection();
            //begin transaction
            CMSCon.setAutoCommit(false);



            //unasiign array check wheter there is a item in the array to remove
            if (unAssignArray != null && unAssignArray.length > 0) {



                for (String mccRem : unAssignArray) {
                    //delete all sections
                    try {
                        //check the existance of the removing item in the table 
                        boolean isExist = merchCusPer.findExistanceOfMcc(CMSCon, merchantCustomerNumber, mccRem);


                        if (isExist) {
                            existSecList.add(mccRem);
                        }

                    } catch (Exception ex) {
                        throw ex;
                    }
                }
            }


            //if there is no exception and success deletion move to insert



            if (assignArray != null && assignArray.length > 0) {

                for (String mcc : assignArray) {
                    MerchantCustomerMccBean merchantCusMccBean = new MerchantCustomerMccBean();
                    merchantCusMccBean.setMerchantCustomerNum(merchantCustomerNumber);
                    merchantCusMccBean.setMcc(mcc);

                    try {

                        //check the existance of the removing item in the table 
                        boolean isExist = merchCusPer.findExistanceOfMcc(CMSCon, merchantCustomerNumber, mcc);

                        //if not in the table delete item
                        if (!isExist) {
                            //select from
                            //insert all sections to the databese
                            merchCusPer.insertToMcc(CMSCon, merchantCustomerNumber, mcc, auditBean.getLastUpdateduser());
                        }


                    } catch (Exception sex) {

                        throw sex;
                    }


                }
            }
            CMSCon.commit();

            return existSecList;


        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

    }

    public List<String> assignTxn(String merchantCustomerNumber, String[] assignArray, String[] unAssignArray, String sysUser, SystemAuditBean auditBean) throws Exception {
        try {

            List<String> existSecList = new ArrayList<String>();

            int result = -1;
            merchCusPer = new MerchantCustomerPersistance();
            //create Db connection
            CMSCon = DBConnection.getConnection();
            //begin transaction
            CMSCon.setAutoCommit(false);

            //unasiign array check wheter there is a item in the array to remove
            if (unAssignArray != null && unAssignArray.length > 0) {

                for (String txnRem : unAssignArray) {
                    //delete all sections
                    try {
                        //check the existance of the removing item in the table 
                        boolean isExist = merchCusPer.findExistanceOfTxn(CMSCon, merchantCustomerNumber, txnRem);


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
                    MerchantCustomerTransactionBean merchantCusTxnBean = new MerchantCustomerTransactionBean();
                    merchantCusTxnBean.setMerchantCustomerNum(merchantCustomerNumber);
                    merchantCusTxnBean.setTransactionCode(txn);

                    try {

                        //check the existance of the removing item in the table 
                        boolean isExist = merchCusPer.findExistanceOfTxn(CMSCon, merchantCustomerNumber, txn);

                        //if not in the table delete item
                        if (!isExist) {
                            //select from
                            //insert all sections to the databese
                            merchCusPer.insertToTxnType(CMSCon, merchantCustomerNumber, txn, auditBean.getLastUpdateduser());
                        }


                    } catch (Exception sex) {

                        throw sex;
                    }


                }
            }
            CMSCon.commit();

            return existSecList;


        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

    }

    public List<String> assignCurrency(String merchantCustomerNumber, String[] assignArray, String[] unAssignArray, String sysUser, SystemAuditBean auditBean) throws Exception {
        try {



            List<String> existSecList = new ArrayList<String>();

            int result = -1;
            merchCusPer = new MerchantCustomerPersistance();
            //create Db connection
            CMSCon = DBConnection.getConnection();
            //begin transaction
            CMSCon.setAutoCommit(false);



            //unasiign array check wheter there is a item in the array to remove
            if (unAssignArray != null && unAssignArray.length > 0) {



                for (String CurrencyRem : unAssignArray) {
                    //delete all sections
                    try {
                        //check the existance of the removing item in the table 
                        boolean isExist = merchCusPer.findExistanceOfCurrency(CMSCon, merchantCustomerNumber, CurrencyRem);


                        if (isExist) {
                            existSecList.add(CurrencyRem);
                        }

                    } catch (Exception ex) {
                        throw ex;
                    }
                }
            }


            //if there is no exception and success deletion move to insert



            if (assignArray != null && assignArray.length > 0) {

                for (String currency : assignArray) {
//                    MerchantCustomerTransactionBean merchantCusTxnBean = new MerchantCustomerTransactionBean();
//                    merchantCusTxnBean.setMerchantCustomerNum(merchantCustomerNumber);
//                    merchantCusTxnBean.setTransactionCode(currency );

                    try {

                        //check the existance of the removing item in the table 
                        boolean isExist = merchCusPer.findExistanceOfCurrency(CMSCon, merchantCustomerNumber, currency);

                        //if not in the table delete item
                        if (!isExist) {
                            //select from
                            //insert all sections to the databese
                            merchCusPer.insertToCurrency(CMSCon, merchantCustomerNumber, currency, auditBean.getLastUpdateduser());
                        }


                    } catch (Exception sex) {

                        throw sex;
                    }


                }
            }
            CMSCon.commit();

            return existSecList;


        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

    }

    public void deleteMcc(List<String> existList, String merchantCustomerNumber) throws Exception {
        try {
            //List<MerchantCustomerBean> sectionRemList = null;
            merchCusPer = new MerchantCustomerPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            for (int i = 0; i < existList.size(); i++) {
                try {
                    //System.out.println("task code = "+existList.get(i));
                    //System.out.println("page code = "+pageCode);
                    merchCusPer.deleteMerchantMccByMcc(CMSCon, existList.get(i), merchantCustomerNumber);

                } catch (SQLException ex) {
                    throw ex;
                }
            }
            CMSCon.commit();


        } catch (SQLException ex) {
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



    }

    public void deleteTxn(List<String> existList, String merchantCustomerNumber) throws Exception {
        try {
            //List<MerchantCustomerBean> sectionRemList = null;
            merchCusPer = new MerchantCustomerPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            for (int i = 0; i < existList.size(); i++) {
                try {
                    //System.out.println("task code = "+existList.get(i));
                    //System.out.println("page code = "+pageCode);
                    merchCusPer.deleteMerchantTxnByTxnCode(CMSCon, existList.get(i), merchantCustomerNumber);

                } catch (SQLException ex) {
                    throw ex;
                }
            }
            CMSCon.commit();

        } catch (SQLException ex) {
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
    }

    public void deleteCurrency(List<String> existList, String merchantCustomerNumber) throws Exception {
        try {
            //List<MerchantCustomerBean> sectionRemList = null;
            merchCusPer = new MerchantCustomerPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            for (int i = 0; i < existList.size(); i++) {
                try {
                    //System.out.println("task code = "+existList.get(i));
                    //System.out.println("page code = "+pageCode);
                    merchCusPer.deleteMerchantCurrencyByCurrencyCode(CMSCon, existList.get(i), merchantCustomerNumber);

                } catch (SQLException ex) {
                    throw ex;
                }
            }
            CMSCon.commit();


        } catch (SQLException ex) {
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



    }

    public List<CommissionProfileBean> getCommissionProfileDetails() throws SQLException, Exception {
        try {

            CMSCon = DBConnection.getConnection();
            merchCusPer = new MerchantCustomerPersistance();
            commissionList = new ArrayList<CommissionProfileBean>();

            CMSCon.setAutoCommit(false);

            commissionList = merchCusPer.getCommissionProfileDetails(CMSCon);

            CMSCon.commit();

        } catch (SQLException sqx) {
            try {
                CMSCon.rollback();
                throw sqx;
            } catch (Exception ex) {
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return commissionList;
    }

    public List<BillingCycleMgtBean> getStatementCycle() throws SQLException, Exception {
        try {

            CMSCon = DBConnection.getConnection();
            merchCusPer = new MerchantCustomerPersistance();
            statementList = new ArrayList<BillingCycleMgtBean>();

            CMSCon.setAutoCommit(false);

            statementList = merchCusPer.getStatementCycle(CMSCon);

            CMSCon.commit();

        } catch (SQLException sqx) {
            try {
                CMSCon.rollback();
                throw sqx;
            } catch (Exception ex) {
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return statementList;
    }

    public List<MerchantPaymentCycleBean> getPaymentCycle() throws SQLException, Exception {
        try {

            CMSCon = DBConnection.getConnection();
            merchCusPer = new MerchantCustomerPersistance();
            paymentList = new ArrayList<MerchantPaymentCycleBean>();

            CMSCon.setAutoCommit(false);

            paymentList = merchCusPer.getPaymentCycle(CMSCon);

            CMSCon.commit();

        } catch (SQLException sqx) {
            try {
                CMSCon.rollback();
                throw sqx;
            } catch (Exception ex) {
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return paymentList;
    }

    public List<MerchantPaymentModeBean> getPaymentMode() throws SQLException, Exception {
        try {

            CMSCon = DBConnection.getConnection();
            merchCusPer = new MerchantCustomerPersistance();
            paymentModeList = new ArrayList<MerchantPaymentModeBean>();

            CMSCon.setAutoCommit(false);

            paymentModeList = merchCusPer.getPaymentMode(CMSCon);

            CMSCon.commit();

        } catch (SQLException sqx) {
            try {
                CMSCon.rollback();
                throw sqx;
            } catch (Exception ex) {
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return paymentModeList;
    }

    public List<BankBean> getCommonParaBank() throws SQLException, Exception {
        try {

            CMSCon = DBConnection.getConnection();
            merchCusPer = new MerchantCustomerPersistance();
            bankList = new ArrayList<BankBean>();

            CMSCon.setAutoCommit(false);

            bankList = merchCusPer.getCommonParaBank(CMSCon);

            CMSCon.commit();

        } catch (SQLException sqx) {
            try {
                CMSCon.rollback();
                throw sqx;
            } catch (Exception ex) {
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return bankList;
    }

    public HashMap<String, String> getAllAccountType() throws Exception {
        try {
            merchCusPer = new MerchantCustomerPersistance();
            accountTypeList = new HashMap<String, String>();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            accountTypeList = merchCusPer.getAllAccountType(CMSCon);
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
        return accountTypeList;
    }

    public List<MerchantBankBean> getAllBankLst() throws Exception {
        try {
            merchCusPer = new MerchantCustomerPersistance();
            merchantBankList = new ArrayList<MerchantBankBean>();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            merchantBankList = merchCusPer.getAllBanks(CMSCon);

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

        return merchantBankList;
    }

    public List<MerchantBankBranchBean> getAllBranchList() throws Exception {
        try {
            merchCusPer = new MerchantCustomerPersistance();
            merchantBankBranchList = new ArrayList<MerchantBankBranchBean>();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            merchantBankBranchList = merchCusPer.getAllBankBranches(CMSCon);

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

        return merchantBankBranchList;
    }
    
     public HashMap<String, String> getCurrency() throws Exception {
        try {
            CMSCon = new DBConnection().getConnection();
            merchCusPer = new MerchantCustomerPersistance();
            currencyTypeList = new HashMap<String, String>();

            CMSCon.setAutoCommit(false);

            currencyTypeList = merchCusPer.getCurrency(CMSCon);

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
        return currencyTypeList;
    }
     
   public String getCommonParaRedemValue() throws Exception{
       String redemValue = null;
       try {
           
             CMSCon = new DBConnection().getConnection();
            merchCusPer = new MerchantCustomerPersistance();
            
            CMSCon.setAutoCommit(false);
            
            redemValue = merchCusPer.getCommonParaRedemValue(CMSCon);
            
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
        return redemValue;
   }
   
   public List<FeeProfileBean> getAllFeeProfileDetail(String type) throws SQLException, Exception {
        try {
            List<FeeProfileBean> feeProfBeanList;

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            merchCusPer = new MerchantCustomerPersistance();

            feeProfBeanList = merchCusPer.getAllFeeProfileDetail(CMSCon,type);
            CMSCon.commit();
            return feeProfBeanList;

        } catch (SQLException ex) {
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
