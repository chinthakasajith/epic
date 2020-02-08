/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.profilemgt.businesslogic;

import com.epic.cms.admin.controlpanel.profilemgt.bean.RiskProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.RiskProfileBinBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.RiskProfileTypeBean;
import com.epic.cms.admin.controlpanel.profilemgt.persistance.RiskProfileMgtPersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CountryMgtBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.MerchantCategoryBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nalin
 */
public class RiskProfileManager {

    private RiskProfileMgtPersistance riskProfPer;
    private Connection CMSCon;
    private Connection CMSConOnline;
    private SystemAuditManager sysAuditManager;
    private List<RiskProfileBean> riskProfList = null;
    private List<RiskProfileTypeBean> riskTypeList = null;
    private List<CountryMgtBean> countryList = null;
    private List<TypeMgtBean> txnTypeList = null;
    private List<MerchantCategoryBean> mccList = null;
    private List<CurrencyBean> currencyList = null;
    private List<RiskProfileBinBean> binList = null;

    /**
     * get All risk Profile Details
     *
     * @return
     * @throws Exception
     */
    public List<RiskProfileBean> getRiskProfileDetails() throws Exception {
        try {
            riskProfList = new ArrayList<RiskProfileBean>();
            riskProfPer = new RiskProfileMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            riskProfList = riskProfPer.getRiskProfileDetails(CMSCon);

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

    /**
     * get risk Profile type code & description
     *
     * @return
     * @throws Exception
     */
    public List<RiskProfileTypeBean> getRiskProfileType() throws Exception {
        try {
            riskTypeList = new ArrayList<RiskProfileTypeBean>();
            riskProfPer = new RiskProfileMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            riskTypeList = riskProfPer.getRiskProfileType(CMSCon);

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
        return riskTypeList;
    }

    /**
     * get profile types according to the user selection
     *
     * @param profileType
     * @return
     * @throws Exception
     */
    public List<RiskProfileBean> getSelectedProfileTypes(String profileType) throws Exception {
        try {
            riskProfList = new ArrayList<RiskProfileBean>();
            riskProfPer = new RiskProfileMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            riskProfList = riskProfPer.getSelectedProfileTypes(CMSCon, profileType);

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

    /**
     * get blocked country
     *
     * @param profileTypeCode
     * @return
     * @throws Exception
     */
    public List<CountryMgtBean> getSelectedCountry(String profileTypeCode) throws Exception {
        try {
            countryList = new ArrayList<CountryMgtBean>();
            riskProfPer = new RiskProfileMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            countryList = riskProfPer.getSelectedCountry(CMSCon, profileTypeCode);

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
        return countryList;
    }

    /**
     * get not blocked country
     *
     * @param profileTypeCode
     * @return
     * @throws Exception
     */
    public List<CountryMgtBean> getNotBlockedCountry(String profileTypeCode) throws Exception {
        try {
            countryList = new ArrayList<CountryMgtBean>();
            riskProfPer = new RiskProfileMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            countryList = riskProfPer.getNotBlockedCountry(CMSCon, profileTypeCode);

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
        return countryList;
    }

    /**
     * get blocked transaction type
     *
     * @param profileTypeCode
     * @return
     * @throws Exception
     */
    public List<TypeMgtBean> getSelectedTxnType(String profileTypeCode) throws Exception {
        try {
            txnTypeList = new ArrayList<TypeMgtBean>();
            riskProfPer = new RiskProfileMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            txnTypeList = riskProfPer.getSelectedTxnType(CMSCon, profileTypeCode);

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

    /**
     * get not blocked transaction type
     *
     * @param profileTypeCode
     * @return
     * @throws Exception
     */
    public List<TypeMgtBean> getNotBlockedTxnType(String profileTypeCode) throws Exception {
        try {
            txnTypeList = new ArrayList<TypeMgtBean>();
            riskProfPer = new RiskProfileMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            txnTypeList = riskProfPer.getNotBlockedTxnType(CMSCon, profileTypeCode);

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

    /**
     * get blocked mcc list
     *
     * @param profileTypeCode
     * @return
     * @throws Exception
     */
    public List<MerchantCategoryBean> getSelectedMcc(String profileTypeCode) throws Exception {
        try {
            mccList = new ArrayList<MerchantCategoryBean>();
            riskProfPer = new RiskProfileMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            mccList = riskProfPer.getSelectedMcc(CMSCon, profileTypeCode);

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
        return mccList;
    }

    /**
     * get not blocked mcc list
     *
     * @param profileTypeCode
     * @return
     * @throws Exception
     */
    public List<MerchantCategoryBean> getNotBlockedMcc(String profileTypeCode) throws Exception {
        try {
            mccList = new ArrayList<MerchantCategoryBean>();
            riskProfPer = new RiskProfileMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            mccList = riskProfPer.getNotBlockedMcc(CMSCon, profileTypeCode);

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
        return mccList;
    }

    /**
     * get blocked currency list
     *
     * @param profileTypeCode
     * @return
     * @throws Exception
     */
    public List<CurrencyBean> getSelectedCurrency(String profileTypeCode) throws Exception {
        try {
            currencyList = new ArrayList<CurrencyBean>();
            riskProfPer = new RiskProfileMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            currencyList = riskProfPer.getSelectedCurrency(CMSCon, profileTypeCode);

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

    /**
     * get not blocked currency list
     *
     * @param profileTypeCode
     * @return
     * @throws Exception
     */
    public List<CurrencyBean> getNotBlockedCurrency(String profileTypeCode) throws Exception {
        try {
            currencyList = new ArrayList<CurrencyBean>();
            riskProfPer = new RiskProfileMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            currencyList = riskProfPer.getNotBlockedCurrency(CMSCon, profileTypeCode);

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

    public List<RiskProfileBinBean> getSelectedBin(String profileTypeCode) throws Exception {
        try {
            binList = new ArrayList<RiskProfileBinBean>();
            riskProfPer = new RiskProfileMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            binList = riskProfPer.getSelectedBin(CMSCon, profileTypeCode);

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
        return binList;
    }

    public List<RiskProfileBinBean> getNotBlockedBin(String profileTypeCode) throws Exception {
        try {
            binList = new ArrayList<RiskProfileBinBean>();
            riskProfPer = new RiskProfileMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            binList = riskProfPer.getNotBlockedBin(CMSCon, profileTypeCode);

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
        return binList;
    }

    public RiskProfileBean getSelectedProfileData(RiskProfileBean riskProfBean) throws Exception {
        RiskProfileBean riskBean = new RiskProfileBean();
        try {

            riskProfPer = new RiskProfileMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            riskBean = riskProfPer.getSelectedProfileData(CMSCon, riskProfBean);

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
        return riskBean;
    }

    public String getDescription(String rpCode) throws Exception {
        String des = null;
        try {
            riskProfPer = new RiskProfileMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            des = riskProfPer.getDescription(CMSCon, rpCode);

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
        return des;
    }

    /**
     * insert risk profile
     *
     * @param riskProfBean
     * @param systemAuditBean
     * @return
     * @throws Exception
     */
    public String insertRiskProfile(RiskProfileBean riskProfBean, SystemAuditBean systemAuditBean) throws Exception {

        boolean success = false;
        boolean successToOnline = false;
        String tag = "";

        try {
            riskProfPer = new RiskProfileMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();

            if (riskProfBean != null) {

                if ((riskProfBean.getStatus()).equals("ACT")) {
                    riskProfBean.setStatusToOnline(1);
                } else if ((riskProfBean.getStatus()).equals("DEA")) {
                    riskProfBean.setStatusToOnline(2);
                }
            }

            CMSCon.setAutoCommit(false);
            CMSConOnline.setAutoCommit(false);

            boolean isExsist = riskProfPer.checkRiskProfileCodeExsistance(CMSCon, riskProfBean.getRiskProfCode());

            if (isExsist) {

                success = riskProfPer.updateRiskProfile(CMSCon, riskProfBean);
                if (success) {
                    successToOnline = riskProfPer.updateRiskProfileToOnline(CMSConOnline, riskProfBean);
                }
                if (successToOnline) {
                    tag = "update";
                }

            } else {
                success = riskProfPer.insertRiskProfile(CMSCon, riskProfBean);
                if (success) {
                    successToOnline = riskProfPer.insertRiskProfileToOnline(CMSConOnline, riskProfBean);
                }
                if (successToOnline) {
                    tag = "add";
                }
            }

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
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
        return tag;
    }

    /**
     * insert blocked Merchant categories
     *
     * @param riskProfCode
     * @param assignMerchantCategorylist
     * @return
     * @throws Exception
     */
//    public boolean insertToMcc(String riskProfCode, String[] assignMerchantCategorylist) throws Exception {
//        boolean success = false;
//        boolean successToOnline = false;
//        try {
//            riskProfPer = new RiskProfileMgtPersistance();
//            CMSCon = DBConnection.getConnection();
//            CMSConOnline = DBConnectionToOnlineDB.getConnection();
//
//            CMSCon.setAutoCommit(false);
//            CMSConOnline.setAutoCommit(false);
//
//            for (int i = 0; i < assignMerchantCategorylist.length; i++) {
//
//                success = riskProfPer.insertToMcc(CMSCon, riskProfCode, assignMerchantCategorylist[i]);
//
//            }
//            if (success) {
//
//                for (int i = 0; i < assignMerchantCategorylist.length; i++) {
//
//                    successToOnline = riskProfPer.insertToMccOnline(CMSConOnline, riskProfCode, assignMerchantCategorylist[i]);
//
//                }
//            }
//            CMSCon.commit();
//            CMSConOnline.commit();
//        } catch (Exception ex) {
//            CMSCon.rollback();
//            CMSConOnline.rollback();
//            throw ex;
//        } finally {
//            DBConnection.dbConnectionClose(CMSCon);
//            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
//        }
//        return successToOnline;
//    }
    /**
     * insert blocked Transaction Type
     *
     * @param riskProfCode
     * @param assignTxnTypeList
     * @return
     * @throws Exception
     */
//    public boolean insertToTxnType(String riskProfCode, String[] assignTxnTypeList) throws Exception {
//        boolean success = false;
//        boolean successToOnline = false;
//        try {
//            riskProfPer = new RiskProfileMgtPersistance();
//            CMSCon = DBConnection.getConnection();
//            CMSConOnline = DBConnectionToOnlineDB.getConnection();
//
//            CMSCon.setAutoCommit(false);
//            CMSConOnline.setAutoCommit(false);
//
//            for (int i = 0; i < assignTxnTypeList.length; i++) {
//
//                success = riskProfPer.insertToTxnType(CMSCon, riskProfCode, assignTxnTypeList[i]);
//
//            }
//            if (success) {
//
//                for (int i = 0; i < assignTxnTypeList.length; i++) {
//
//                    String onlineTxnCode = riskProfPer.getOnlineTxnCode(CMSCon, assignTxnTypeList[i]);
//                    successToOnline = riskProfPer.insertToTxnTypeOnline(CMSConOnline, riskProfCode, onlineTxnCode);
//
//                }
//
//            }
//
//            CMSCon.commit();
//            CMSConOnline.commit();
//        } catch (Exception ex) {
//            CMSCon.rollback();
//            CMSConOnline.rollback();
//            throw ex;
//        } finally {
//            DBConnection.dbConnectionClose(CMSCon);
//            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
//        }
//        return successToOnline;
//    }
    /**
     * insert blocked Countries
     *
     * @param riskProfCode
     * @param assignCountryList
     * @return
     * @throws Exception
     */
//    public boolean insertToCountry(String riskProfCode, String[] assignCountryList) throws Exception {
//        boolean success = false;
//        boolean successToOnline = false;
//        try {
//            riskProfPer = new RiskProfileMgtPersistance();
//            CMSCon = DBConnection.getConnection();
//            CMSConOnline = DBConnectionToOnlineDB.getConnection();
//
//            CMSCon.setAutoCommit(false);
//            CMSConOnline.setAutoCommit(false);
//
//            for (int i = 0; i < assignCountryList.length; i++) {
//
//                success = riskProfPer.insertToCountry(CMSCon, riskProfCode, assignCountryList[i]);
//
//            }
//            if (success) {
//
//                for (int i = 0; i < assignCountryList.length; i++) {
//
//                    successToOnline = riskProfPer.insertToCountryOnline(CMSConOnline, riskProfCode, assignCountryList[i]);
//
//                }
//
//            }
//
//            CMSCon.commit();
//            CMSConOnline.commit();
//        } catch (Exception ex) {
//            CMSCon.rollback();
//            CMSConOnline.rollback();
//            throw ex;
//        } finally {
//            DBConnection.dbConnectionClose(CMSCon);
//            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
//        }
//        return successToOnline;
//    }
    /**
     * insert blocked Currencies
     *
     * @param riskProfCode
     * @param assignCurrencyList
     * @return
     * @throws Exception
     */
//    public boolean insertToCurrency(String riskProfCode, String[] assignCurrencyList) throws Exception {
//        boolean success = false;
//        boolean successToOnline = false;
//        try {
//            riskProfPer = new RiskProfileMgtPersistance();
//            CMSCon = DBConnection.getConnection();
//            CMSConOnline = DBConnectionToOnlineDB.getConnection();
//
//            CMSCon.setAutoCommit(false);
//            CMSConOnline.setAutoCommit(false);
//
//            for (int i = 0; i < assignCurrencyList.length; i++) {
//
//                success = riskProfPer.insertToCurrency(CMSCon, riskProfCode, assignCurrencyList[i]);
//
//            }
//
//            if (success) {
//
//                for (int i = 0; i < assignCurrencyList.length; i++) {
//
//                    success = riskProfPer.insertToCurrencyOnline(CMSConOnline, riskProfCode, assignCurrencyList[i]);
//
//                }
//
//            }
//
//            CMSCon.commit();
//            CMSConOnline.commit();
//        } catch (Exception ex) {
//            CMSCon.rollback();
//            CMSConOnline.rollback();
//            throw ex;
//        } finally {
//            DBConnection.dbConnectionClose(CMSCon);
//            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
//        }
//        return successToOnline;
//    }
    /**
     * update risk profile
     *
     * @param riskProfBean
     * @param country
     * @param mcc
     * @param txn
     * @param currency
     * @param systemAuditBean
     * @return
     * @throws Exception
     */
    public boolean updateRiskProfile(RiskProfileBean riskProfBean, SystemAuditBean systemAuditBean) throws Exception {

        boolean success = false;
        boolean successToOnline = false;

        try {
            riskProfPer = new RiskProfileMgtPersistance();
            sysAuditManager = new SystemAuditManager();

            CMSCon = DBConnection.getConnection();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();

            if (riskProfBean != null) {

                if ((riskProfBean.getStatus()).equals("ACT")) {
                    riskProfBean.setStatusToOnline(1);
                } else if ((riskProfBean.getStatus()).equals("DEA")) {
                    riskProfBean.setStatusToOnline(2);
                }
            }

            CMSCon.setAutoCommit(false);
            CMSConOnline.setAutoCommit(false);

            /// edit risk profile
            success = riskProfPer.updateRiskProfile(CMSCon, riskProfBean);

            if (success) {

                successToOnline = riskProfPer.updateRiskProfileToOnline(CMSConOnline, riskProfBean);

            }

//            /// delete block country list
//            if (riskProfPer.deleteCountry(CMSCon, riskProfBean.getRiskProfCode())) {
//
//                riskProfPer.deleteCountryFromOnline(CMSConOnline, riskProfBean.getRiskProfCode());
//
//            }
//            if (country != null) {
//                for (int i = 0; i < country.length; i++) {
//                    // add new blocked country list 
//                    riskProfPer.insertToCountry(CMSCon, riskProfBean.getRiskProfCode(), country[i]);
//                    String countryNumCode = riskProfPer.getCountryNumCode(CMSCon, country[i]);
//                    riskProfPer.insertToCountryOnline(CMSConOnline, riskProfBean.getRiskProfCode(), countryNumCode);
//                }
//            }
//            /// delete block mcc list
//            if (riskProfPer.deleteMcc(CMSCon, riskProfBean.getRiskProfCode())) {
//
//                riskProfPer.deleteMccFromOnline(CMSConOnline, riskProfBean.getRiskProfCode());
//
//            }
//            if (mcc != null) {
//                for (int i = 0; i < mcc.length; i++) {
//                    // add new blocked mcc list 
//                    riskProfPer.insertToMcc(CMSCon, riskProfBean.getRiskProfCode(), mcc[i]);
//                    riskProfPer.insertToMccOnline(CMSConOnline, riskProfBean.getRiskProfCode(), mcc[i]);
//                }
//            }
//            /// delete txntype list
//            if (riskProfPer.deleteTxn(CMSCon, riskProfBean.getRiskProfCode())) {
//
//                riskProfPer.deleteTxnFromOnline(CMSConOnline, riskProfBean.getRiskProfCode());
//
//            }
//            if (txn != null) {
//                for (int i = 0; i < txn.length; i++) {
//                    // add new blocked txntype list 
//                    riskProfPer.insertToTxnType(CMSCon, riskProfBean.getRiskProfCode(), txn[i]);
//
//                    String onlineTxnCode = riskProfPer.getOnlineTxnCode(CMSCon, txn[i]);
//                    riskProfPer.insertToTxnTypeOnline(CMSConOnline, riskProfBean.getRiskProfCode(), onlineTxnCode);
//                }
//            }
//            // delete currency list
//            if (riskProfPer.deleteCurrency(CMSCon, riskProfBean.getRiskProfCode())) {
//
//                riskProfPer.deleteCurrencyFromOnline(CMSConOnline, riskProfBean.getRiskProfCode());
//
//            }
//            if (currency != null) {
//                for (int i = 0; i < currency.length; i++) {
//                    // add new blocked currency list 
//                    riskProfPer.insertToCurrency(CMSCon, riskProfBean.getRiskProfCode(), currency[i]);
//                    riskProfPer.insertToCurrencyOnline(CMSConOnline, riskProfBean.getRiskProfCode(), currency[i]);
//                }
//            }
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
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
        return successToOnline;
    }

    /**
     * Delete Risk Profile
     *
     * @param riskProfBean
     * @param systemAuditBean
     * @return
     * @throws Exception
     */
    public boolean deleteRiskProfile(RiskProfileBean riskProfBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        boolean countrySuccess = false;
        boolean mccSuccess = false;
        boolean txnSuccess = false;
        boolean currencySuccess = false;
        boolean countryOnlineSuccess = false;
        boolean mccOnlineSuccess = false;
        boolean txnOnlineSuccess = false;
        boolean currencyOnlineSuccess = false;
        try {
            riskProfPer = new RiskProfileMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            CMSConOnline.setAutoCommit(false);

            countrySuccess = riskProfPer.deleteCountry(CMSCon, riskProfBean.getRiskProfCode());
            mccSuccess = riskProfPer.deleteMcc(CMSCon, riskProfBean.getRiskProfCode());
            txnSuccess = riskProfPer.deleteTxn(CMSCon, riskProfBean.getRiskProfCode());
            currencySuccess = riskProfPer.deleteCurrency(CMSCon, riskProfBean.getRiskProfCode());
            riskProfPer.deleteBin(CMSCon, riskProfBean.getRiskProfCode());

            countryOnlineSuccess = riskProfPer.deleteCountryFromOnline(CMSConOnline, riskProfBean.getRiskProfCode());
            mccOnlineSuccess = riskProfPer.deleteMccFromOnline(CMSConOnline, riskProfBean.getRiskProfCode());
            txnOnlineSuccess = riskProfPer.deleteTxnFromOnline(CMSConOnline, riskProfBean.getRiskProfCode());
            currencyOnlineSuccess = riskProfPer.deleteCurrencyFromOnline(CMSConOnline, riskProfBean.getRiskProfCode());
            riskProfPer.deleteBinFromOnline(CMSConOnline, riskProfBean.getRiskProfCode());

            if (countryOnlineSuccess | mccOnlineSuccess | txnOnlineSuccess | currencyOnlineSuccess) {

                riskProfPer.deleteRiskProfileFromOnline(CMSConOnline, riskProfBean);

            }

            if (countrySuccess | mccSuccess | txnSuccess | currencySuccess) {

                success = riskProfPer.deleteRiskProfile(CMSCon, riskProfBean);
            }

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
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
        return success;
    }

    public List<RiskProfileBean> getGivenTypeRiskProfile(String rpType) throws Exception {
        try {
            riskProfList = new ArrayList<RiskProfileBean>();
            riskProfPer = new RiskProfileMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            riskProfList = riskProfPer.getGivenTypeRiskProfile(CMSCon, rpType);

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

    public boolean insertRiskProfileAssignData(RiskProfileBean riskProfBean, SystemAuditBean systemAuditBean, String[] mcc, String[] txn, String[] country, String[] currency, String[] bin) throws Exception {


        boolean successToOnline = false;

        try {
            riskProfPer = new RiskProfileMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            CMSConOnline.setAutoCommit(false);




            if (mcc != null) {
                for (int i = 0; i < mcc.length; i++) {

                    riskProfPer.insertToMcc(CMSCon, riskProfBean.getRiskProfCode(), mcc[i]);
                }
                for (int i = 0; i < mcc.length; i++) {

                    successToOnline = riskProfPer.insertToMccOnline(CMSConOnline, riskProfBean.getRiskProfCode(), mcc[i]);
                }
            }

            if (txn != null) {
                for (int i = 0; i < txn.length; i++) {

                    riskProfPer.insertToTxnType(CMSCon, riskProfBean.getRiskProfCode(), txn[i]);
                }
                for (int i = 0; i < txn.length; i++) {

                    String onlineTxnCode = riskProfPer.getOnlineTxnCode(CMSCon, txn[i]);
                    successToOnline = riskProfPer.insertToTxnTypeOnline(CMSConOnline, riskProfBean.getRiskProfCode(), onlineTxnCode);
                }
            }

            if (country != null) {
                for (int i = 0; i < country.length; i++) {

                    riskProfPer.insertToCountry(CMSCon, riskProfBean.getRiskProfCode(), country[i]);
                }
                for (int i = 0; i < country.length; i++) {

                    String countryNumCode = riskProfPer.getCountryNumCode(CMSCon, country[i]);
                    successToOnline = riskProfPer.insertToCountryOnline(CMSConOnline, riskProfBean.getRiskProfCode(), countryNumCode);
                }
            }

            if (currency != null) {
                for (int i = 0; i < currency.length; i++) {

                    riskProfPer.insertToCurrency(CMSCon, riskProfBean.getRiskProfCode(), currency[i]);

                }
                for (int i = 0; i < currency.length; i++) {

                    successToOnline = riskProfPer.insertToCurrencyOnline(CMSConOnline, riskProfBean.getRiskProfCode(), currency[i]);

                }
            }
            if (bin != null) {
                for (int i = 0; i < bin.length; i++) {

                    riskProfPer.insertToBin(CMSCon, riskProfBean.getRiskProfCode(), bin[i]);
                }
                for (int i = 0; i < bin.length; i++) {

                    successToOnline = riskProfPer.insertToBinOnline(CMSConOnline, riskProfBean.getRiskProfCode(), bin[i]);
                }
            }


            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
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
        return successToOnline;
    }

    public boolean updateRiskProfileAssignData(RiskProfileBean riskProfBean, String[] country, String[] mcc, String[] txn, String[] currency, String[] bin, SystemAuditBean systemAuditBean) throws Exception {

        boolean success = false;
        boolean successToOnline = false;

        try {
            riskProfPer = new RiskProfileMgtPersistance();
            sysAuditManager = new SystemAuditManager();

            CMSCon = DBConnection.getConnection();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();


            CMSCon.setAutoCommit(false);
            CMSConOnline.setAutoCommit(false);

            /// edit risk profile

            /// delete block country list
            if (riskProfPer.deleteCountry(CMSCon, riskProfBean.getRiskProfCode())) {

                riskProfPer.deleteCountryFromOnline(CMSConOnline, riskProfBean.getRiskProfCode());

            }
            if (country != null) {
                for (int i = 0; i < country.length; i++) {
                    // add new blocked country list 
                    riskProfPer.insertToCountry(CMSCon, riskProfBean.getRiskProfCode(), country[i]);
                    String countryNumCode = riskProfPer.getCountryNumCode(CMSCon, country[i]);
                    riskProfPer.insertToCountryOnline(CMSConOnline, riskProfBean.getRiskProfCode(), countryNumCode);
                }
            }
            /// delete block mcc list
            if (riskProfPer.deleteMcc(CMSCon, riskProfBean.getRiskProfCode())) {

                riskProfPer.deleteMccFromOnline(CMSConOnline, riskProfBean.getRiskProfCode());

            }
            if (mcc != null) {
                for (int i = 0; i < mcc.length; i++) {
                    // add new blocked mcc list 
                    riskProfPer.insertToMcc(CMSCon, riskProfBean.getRiskProfCode(), mcc[i]);
                    riskProfPer.insertToMccOnline(CMSConOnline, riskProfBean.getRiskProfCode(), mcc[i]);
                }
            }
            /// delete txntype list
            if (riskProfPer.deleteTxn(CMSCon, riskProfBean.getRiskProfCode())) {

                riskProfPer.deleteTxnFromOnline(CMSConOnline, riskProfBean.getRiskProfCode());

            }
            if (txn != null) {
                for (int i = 0; i < txn.length; i++) {
                    // add new blocked txntype list 
                    riskProfPer.insertToTxnType(CMSCon, riskProfBean.getRiskProfCode(), txn[i]);

                    String onlineTxnCode = riskProfPer.getOnlineTxnCode(CMSCon, txn[i]);
                    riskProfPer.insertToTxnTypeOnline(CMSConOnline, riskProfBean.getRiskProfCode(), onlineTxnCode);
                }
            }
            // delete currency list
            if (riskProfPer.deleteCurrency(CMSCon, riskProfBean.getRiskProfCode())) {

                riskProfPer.deleteCurrencyFromOnline(CMSConOnline, riskProfBean.getRiskProfCode());

            }
            if (currency != null) {
                for (int i = 0; i < currency.length; i++) {
                    // add new blocked currency list 
                    riskProfPer.insertToCurrency(CMSCon, riskProfBean.getRiskProfCode(), currency[i]);
                    riskProfPer.insertToCurrencyOnline(CMSConOnline, riskProfBean.getRiskProfCode(), currency[i]);
                }
            }

            /// delete block bin list
            if (riskProfPer.deleteBin(CMSCon, riskProfBean.getRiskProfCode())) {

                riskProfPer.deleteBinFromOnline(CMSConOnline, riskProfBean.getRiskProfCode());

            }
            if (bin != null) {
                for (int i = 0; i < bin.length; i++) {
                    // add new blocked mcc list 
                    riskProfPer.insertToBin(CMSCon, riskProfBean.getRiskProfCode(), bin[i]);
                    riskProfPer.insertToBinOnline(CMSConOnline, riskProfBean.getRiskProfCode(), bin[i]);
                }
            }

            successToOnline = true;
            success = true;
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
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
        return successToOnline;
    }

    public List<RiskProfileBinBean> getAllBin() throws Exception {

        try {

            riskProfPer = new RiskProfileMgtPersistance();
            CMSCon = DBConnection.getConnection();
            binList = new ArrayList<RiskProfileBinBean>();

            CMSCon.setAutoCommit(false);

            binList = riskProfPer.getAllBin(CMSCon);

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
        return binList;
    }
}
