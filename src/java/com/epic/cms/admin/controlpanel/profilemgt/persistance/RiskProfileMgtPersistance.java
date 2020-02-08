/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.profilemgt.persistance;

import com.epic.cms.admin.controlpanel.profilemgt.bean.RiskProfileBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.RiskProfileBinBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.RiskProfileTypeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CountryMgtBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.MerchantCategoryBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nalin
 */
public class RiskProfileMgtPersistance {

    private ResultSet rs;

    /**
     * get All risk Profile details from DB
     * @param con
     * @return
     * @throws Exception 
     */
    public List<RiskProfileBean> getRiskProfileDetails(Connection con) throws Exception {
        PreparedStatement getRiskProf = null;
        List<RiskProfileBean> riskProfList = new ArrayList<RiskProfileBean>();
        try {
            getRiskProf = con.prepareStatement("SELECT R.RISKPROFILECODE, R.PROFILETYPE,R.DESCRIPTION,"
                    + " R.ACCOUNTPROFILECODE,R.CUSTOMERPROFILECODE,R.MERCHANTPROFILECODE,R.STATUS,R.PERIOD,R.MAXIMUMSINGLETXNLIMIT,"
                    + " R.MAXIMUMSINGLECASHLIMIT,R.MINIMUMSINGLETXNLIMIT,R.MINIMUMSINGLECASHTXNLIMIT,"
                    + " R.MAXIMUMTXNCOUNT,R.MAXIMUMTOTALCASHTXNAMONT,R.MAXIMUMTOTALTXNAMOUNT,"
                    + " R.MAXIMUMCASHTXNCOUNT,R.EXTRAAUTHAMOUNT,R.MAXIMUMPINCOUNT,R.LASTUPDATEDUSER,R.LASTUPDATEDTIME,"
                    + " R.CREATEDTIME, S.DESCRIPTION AS SDESCRIPTION,T.DESCRIPTION AS TDESCRIPTION "
                    + " FROM RISKPROFILE R, STATUS S, RISKPROFILETYPE T"
                    + " WHERE R.STATUS=S.STATUSCODE AND R.PROFILETYPE=T.RISKPROFILETYPECODE");

            rs = getRiskProf.executeQuery();

            while (rs.next()) {

                RiskProfileBean bean = new RiskProfileBean();


                bean.setRiskProfCode(rs.getString("RISKPROFILECODE"));
                bean.setProfileType(rs.getString("PROFILETYPE"));
                bean.setProfileTypeDescription(rs.getString("TDESCRIPTION"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setAccountProfCode(rs.getString("ACCOUNTPROFILECODE"));
                bean.setCustomerProfCode(rs.getString("CUSTOMERPROFILECODE"));
                bean.setMerchantProfCode(rs.getString("MERCHANTPROFILECODE"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setStatusDes(rs.getString("SDESCRIPTION"));
                bean.setPeroid(rs.getString("PERIOD"));
                bean.setMaxSingleTxnLimit(rs.getString("MAXIMUMSINGLETXNLIMIT"));
                bean.setMaxSingleCashLimit(rs.getString("MAXIMUMSINGLECASHLIMIT"));
                bean.setMinSingleTxnLimit(rs.getString("MINIMUMSINGLETXNLIMIT"));
                bean.setMinSingleCashLimit(rs.getString("MINIMUMSINGLECASHTXNLIMIT"));
                bean.setMaxTxnCount(rs.getString("MAXIMUMTXNCOUNT"));
                bean.setMaxTotTxnAmount(rs.getString("MAXIMUMTOTALTXNAMOUNT"));
                bean.setMaxTotCashTxnAmount(rs.getString("MAXIMUMTOTALCASHTXNAMONT"));
                bean.setMaxCashCount(rs.getString("MAXIMUMCASHTXNCOUNT"));
                bean.setExtraAuthLimit(rs.getString("EXTRAAUTHAMOUNT"));
                bean.setMaxPinCount(Integer.toString(rs.getInt("MAXIMUMPINCOUNT")));
                bean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                bean.setLastUpdatedDate(rs.getDate("LASTUPDATEDTIME"));
                bean.setCreatedDate(rs.getDate("CREATEDTIME"));




                riskProfList.add(bean);

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getRiskProf.close();

        }
        return riskProfList;
    }

    /**
     * get Risk Profile type and description
     * @param con
     * @return
     * @throws Exception 
     */
    public List<RiskProfileTypeBean> getRiskProfileType(Connection con) throws Exception {
        PreparedStatement getRiskProfType = null;
        List<RiskProfileTypeBean> riskTypeList = new ArrayList<RiskProfileTypeBean>();
        try {
            getRiskProfType = con.prepareStatement("SELECT T.RISKPROFILETYPECODE,T.DESCRIPTION"
                    + " FROM RISKPROFILETYPE T ");

            rs = getRiskProfType.executeQuery();

            while (rs.next()) {

                RiskProfileTypeBean bean = new RiskProfileTypeBean();


                bean.setRiskProfTypeCode(rs.getString("RISKPROFILETYPECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                riskTypeList.add(bean);

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getRiskProfType.close();

        }
        return riskTypeList;
    }

    /**
     * get all risk profile according to the given Profile Type
     * @param con
     * @param profileType
     * @return
     * @throws Exception 
     */
    public List<RiskProfileBean> getSelectedProfileTypes(Connection con, String profileType) throws Exception {
        PreparedStatement getSelectedProf = null;
        List<RiskProfileBean> riskTypeList = new ArrayList<RiskProfileBean>();
        try {
            getSelectedProf = con.prepareStatement("SELECT R.RISKPROFILECODE,R.PROFILETYPE,R.DESCRIPTION"
                    + " FROM RISKPROFILE R WHERE PROFILETYPE =? ORDER BY R.DESCRIPTION ASC  ");

            if (profileType.equals("RPCRD")) {
                getSelectedProf.setString(1, "RPACT");
            }
            if (profileType.equals("RPACT")) {
                getSelectedProf.setString(1, "RPCUS");
            }
            if (profileType.equals("RPTER")) {
                getSelectedProf.setString(1, "RPMER");
            }

            rs = getSelectedProf.executeQuery();

            while (rs.next()) {

                RiskProfileBean bean = new RiskProfileBean();

                bean.setRiskProfCode(rs.getString("RISKPROFILECODE"));
                bean.setProfileType(rs.getString("PROFILETYPE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                riskTypeList.add(bean);

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getSelectedProf.close();

        }
        return riskTypeList;
    }

    /**
     * get Blocked Country list
     * @param con
     * @param profileTypeCode
     * @return
     * @throws Exception 
     */
    public List<CountryMgtBean> getSelectedCountry(Connection con, String profileTypeCode) throws Exception {
        PreparedStatement getSelectedCoun = null;
        List<CountryMgtBean> countryList = new ArrayList<CountryMgtBean>();
        try {
            getSelectedCoun = con.prepareStatement("SELECT C.COUNTRYALPHA3CODE,C.DESCRIPTION"
                    + " FROM RISKPROFILEBLOCKEDCOUNTRY R, COUNTRY C WHERE R.COUNTRYCODE=C.COUNTRYALPHA3CODE AND"
                    + " R.RISKPROFILECODE =? ORDER BY C.DESCRIPTION ASC");


            getSelectedCoun.setString(1, profileTypeCode);


            rs = getSelectedCoun.executeQuery();

            while (rs.next()) {

                CountryMgtBean bean = new CountryMgtBean();

                bean.setAlphaSecond(rs.getString("COUNTRYALPHA3CODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                countryList.add(bean);

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getSelectedCoun.close();

        }
        return countryList;
    }

    public List<CountryMgtBean> getNotBlockedCountry(Connection con, String profileTypeCode) throws Exception {
        PreparedStatement getNBCoun = null;
        List<CountryMgtBean> countryList = new ArrayList<CountryMgtBean>();
        try {
            getNBCoun = con.prepareStatement("SELECT COUNTRYALPHA3CODE, DESCRIPTION FROM COUNTRY "
                    + " WHERE COUNTRYALPHA3CODE NOT IN (SELECT C.COUNTRYALPHA3CODE"
                    + " FROM RISKPROFILEBLOCKEDCOUNTRY R, COUNTRY C WHERE R.COUNTRYCODE=C.COUNTRYALPHA3CODE AND"
                    + " R.RISKPROFILECODE =?  ) ORDER BY DESCRIPTION ASC");


            getNBCoun.setString(1, profileTypeCode);


            rs = getNBCoun.executeQuery();

            while (rs.next()) {

                CountryMgtBean bean = new CountryMgtBean();

                bean.setAlphaSecond(rs.getString("COUNTRYALPHA3CODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                countryList.add(bean);

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getNBCoun.close();

        }
        return countryList;
    }

    /**
     * get Blocked Transaction Types
     * @param con
     * @param profileTypeCode
     * @return
     * @throws Exception 
     */
    public List<TypeMgtBean> getSelectedTxnType(Connection con, String profileTypeCode) throws Exception {
        PreparedStatement getSelectedTxn = null;
        List<TypeMgtBean> txnTypeList = new ArrayList<TypeMgtBean>();
        try {
            getSelectedTxn = con.prepareStatement("SELECT T.TRANSACTIONCODE,T.DESCRIPTION"
                    + " FROM RISKPROFILEBLOCKEDTXNTYPE R, TRANSACTIONTYPE T WHERE R.TXNTYPECODE=T.TRANSACTIONCODE AND"
                    + " R.RISKPROFILECODE =? ORDER BY T.DESCRIPTION ASC");


            getSelectedTxn.setString(1, profileTypeCode);


            rs = getSelectedTxn.executeQuery();

            while (rs.next()) {

                TypeMgtBean bean = new TypeMgtBean();

                bean.setTransactionTypeCode(rs.getString("TRANSACTIONCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                txnTypeList.add(bean);

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getSelectedTxn.close();

        }
        return txnTypeList;
    }

    public List<TypeMgtBean> getNotBlockedTxnType(Connection con, String profileTypeCode) throws Exception {
        PreparedStatement getNBTxn = null;
        List<TypeMgtBean> txnTypeList = new ArrayList<TypeMgtBean>();
        try {
            getNBTxn = con.prepareStatement("SELECT TRANSACTIONCODE, DESCRIPTION FROM TRANSACTIONTYPE WHERE"
                    + " TRANSACTIONCODE NOT IN (SELECT T.TRANSACTIONCODE"
                    + " FROM RISKPROFILEBLOCKEDTXNTYPE R, TRANSACTIONTYPE T WHERE R.TXNTYPECODE=T.TRANSACTIONCODE AND"
                    + " R.RISKPROFILECODE =? ) ORDER BY DESCRIPTION ASC ");


            getNBTxn.setString(1, profileTypeCode);


            rs = getNBTxn.executeQuery();

            while (rs.next()) {

                TypeMgtBean bean = new TypeMgtBean();

                bean.setTransactionTypeCode(rs.getString("TRANSACTIONCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                txnTypeList.add(bean);

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getNBTxn.close();

        }
        return txnTypeList;
    }

    /**
     * get Blocked Mcc List
     * @param con
     * @param profileTypeCode
     * @return
     * @throws Exception 
     */
    public List<MerchantCategoryBean> getSelectedMcc(Connection con, String profileTypeCode) throws Exception {
        PreparedStatement getSelectedMcc = null;
        List<MerchantCategoryBean> mccList = new ArrayList<MerchantCategoryBean>();
        try {
            getSelectedMcc = con.prepareStatement("SELECT M.MCCCODE,M.DESCRIPTION"
                    + " FROM RISKPROFILEBLOCKEDMCC R, MCC M WHERE R.MCC=M.MCCCODE AND"
                    + " R.RISKPROFILECODE =? ORDER BY M.DESCRIPTION ASC ");


            getSelectedMcc.setString(1, profileTypeCode);


            rs = getSelectedMcc.executeQuery();

            while (rs.next()) {

                MerchantCategoryBean bean = new MerchantCategoryBean();

                bean.setmCCCode(rs.getString("MCCCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                mccList.add(bean);

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getSelectedMcc.close();

        }
        return mccList;
    }

    public List<MerchantCategoryBean> getNotBlockedMcc(Connection con, String profileTypeCode) throws Exception {
        PreparedStatement getNBMcc = null;
        List<MerchantCategoryBean> mccList = new ArrayList<MerchantCategoryBean>();
        try {
            getNBMcc = con.prepareStatement("SELECT MCCCODE, DESCRIPTION FROM"
                    + " MCC WHERE MCCCODE NOT IN( SELECT M.MCCCODE"
                    + " FROM RISKPROFILEBLOCKEDMCC R, MCC M WHERE R.MCC=M.MCCCODE AND"
                    + " R.RISKPROFILECODE =?) ORDER BY DESCRIPTION ASC ");


            getNBMcc.setString(1, profileTypeCode);


            rs = getNBMcc.executeQuery();

            while (rs.next()) {

                MerchantCategoryBean bean = new MerchantCategoryBean();

                bean.setmCCCode(rs.getString("MCCCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                mccList.add(bean);

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getNBMcc.close();

        }
        return mccList;
    }

    /**
     * get Blocked Currency list
     * @param con
     * @param profileTypeCode
     * @return
     * @throws Exception 
     */
    public List<CurrencyBean> getSelectedCurrency(Connection con, String profileTypeCode) throws Exception {
        PreparedStatement getSelectedCun = null;
        List<CurrencyBean> currencyList = new ArrayList<CurrencyBean>();
        try {
            getSelectedCun = con.prepareStatement("SELECT C.CURRENCYNUMCODE,C.DESCRIPTION"
                    + " FROM RISKPROFILECURRENCY R, CURRENCY C WHERE R.CURRENCYCODE=C.CURRENCYNUMCODE AND"
                    + " R.RISKPROFILECODE =? ORDER BY C.DESCRIPTION ASC ");


            getSelectedCun.setString(1, profileTypeCode);


            rs = getSelectedCun.executeQuery();

            while (rs.next()) {

                CurrencyBean bean = new CurrencyBean();

                bean.setCurrencyCode(Integer.toString(rs.getInt("CURRENCYNUMCODE")));
                bean.setCurrencyDes(rs.getString("DESCRIPTION"));

                currencyList.add(bean);

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getSelectedCun.close();

        }
        return currencyList;
    }

    public List<CurrencyBean> getNotBlockedCurrency(Connection con, String profileTypeCode) throws Exception {
        PreparedStatement getNBCun = null;
        List<CurrencyBean> currencyList = new ArrayList<CurrencyBean>();
        try {
            getNBCun = con.prepareStatement("SELECT CURRENCYNUMCODE, DESCRIPTION FROM CURRENCY"
                    + " WHERE CURRENCYNUMCODE NOT IN ( SELECT C.CURRENCYNUMCODE "
                    + " FROM RISKPROFILECURRENCY R, CURRENCY C WHERE R.CURRENCYCODE=C.CURRENCYNUMCODE AND"
                    + " R.RISKPROFILECODE =? ) ORDER BY DESCRIPTION ASC ");


            getNBCun.setString(1, profileTypeCode);


            rs = getNBCun.executeQuery();

            while (rs.next()) {

                CurrencyBean bean = new CurrencyBean();

                bean.setCurrencyCode(Integer.toString(rs.getInt("CURRENCYNUMCODE")));
                bean.setCurrencyDes(rs.getString("DESCRIPTION"));

                currencyList.add(bean);

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getNBCun.close();

        }
        return currencyList;
    }

    public List<RiskProfileBinBean> getSelectedBin(Connection con, String profileTypeCode) throws Exception {
        PreparedStatement getSelectedBin = null;
        List<RiskProfileBinBean> binList = new ArrayList<RiskProfileBinBean>();
        try {
            getSelectedBin = con.prepareStatement("SELECT C.BIN,C.DESCRIPTION"
                    + " FROM RISKPROFILEBLOCKEDBIN R, CARDBIN C WHERE R.BIN=C.BIN AND"
                    + " R.RISKPROFILECODE =? ORDER BY C.DESCRIPTION ASC ");


            getSelectedBin.setString(1, profileTypeCode);


            rs = getSelectedBin.executeQuery();

            while (rs.next()) {

                RiskProfileBinBean bean = new RiskProfileBinBean();

                bean.setBinCode(rs.getString("BIN"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                binList.add(bean);

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getSelectedBin.close();

        }
        return binList;
    }

    public List<RiskProfileBinBean> getNotBlockedBin(Connection con, String profileTypeCode) throws Exception {
        PreparedStatement getNBBin = null;
        List<RiskProfileBinBean> binList = new ArrayList<RiskProfileBinBean>();
        try {
            getNBBin = con.prepareStatement("SELECT BIN, DESCRIPTION FROM"
                    + " CARDBIN WHERE BIN NOT IN( SELECT C.BIN"
                    + " FROM RISKPROFILEBLOCKEDBIN R, CARDBIN C WHERE R.BIN=C.BIN AND"
                    + " R.RISKPROFILECODE =?) ORDER BY DESCRIPTION ASC ");


            getNBBin.setString(1, profileTypeCode);


            rs = getNBBin.executeQuery();

            while (rs.next()) {

                RiskProfileBinBean bean = new RiskProfileBinBean();

                bean.setBinCode(rs.getString("BIN"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                binList.add(bean);

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getNBBin.close();

        }
        return binList;
    }

    public RiskProfileBean getSelectedProfileData(Connection CMSCon, RiskProfileBean riskProfBean) throws Exception {

        PreparedStatement getSelectedProfData = null;

        try {

            getSelectedProfData = CMSCon.prepareStatement(" SELECT R.PERIOD,R.MAXIMUMSINGLETXNLIMIT,"
                    + " R.MAXIMUMSINGLECASHLIMIT,R.MINIMUMSINGLETXNLIMIT,R.MINIMUMSINGLECASHTXNLIMIT,"
                    + " R.MAXIMUMTXNCOUNT,R.MAXIMUMTOTALCASHTXNAMONT,R.MAXIMUMTOTALTXNAMOUNT,"
                    + " R.MAXIMUMCASHTXNCOUNT,R.EXTRAAUTHAMOUNT,R.MAXIMUMPINCOUNT"
                    + " FROM RISKPROFILE R WHERE R.RISKPROFILECODE =?");

            getSelectedProfData.setString(1, riskProfBean.getSelectedRiskProfCode());
            rs = getSelectedProfData.executeQuery();

            while (rs.next()) {

                riskProfBean.setPeroid(rs.getString("PERIOD"));
                riskProfBean.setMaxSingleTxnLimit(rs.getString("MAXIMUMSINGLETXNLIMIT"));
                riskProfBean.setMaxSingleCashLimit(rs.getString("MAXIMUMSINGLECASHLIMIT"));
                riskProfBean.setMinSingleTxnLimit(rs.getString("MINIMUMSINGLETXNLIMIT"));
                riskProfBean.setMinSingleCashLimit(rs.getString("MINIMUMSINGLECASHTXNLIMIT"));
                riskProfBean.setMaxTxnCount(rs.getString("MAXIMUMTXNCOUNT"));
                riskProfBean.setMaxTotTxnAmount(rs.getString("MAXIMUMTOTALTXNAMOUNT"));
                riskProfBean.setMaxTotCashTxnAmount(rs.getString("MAXIMUMTOTALCASHTXNAMONT"));
                riskProfBean.setMaxCashCount(rs.getString("MAXIMUMCASHTXNCOUNT"));
                riskProfBean.setExtraAuthLimit(rs.getString("EXTRAAUTHAMOUNT"));
                riskProfBean.setMaxPinCount(Integer.toString(rs.getInt("MAXIMUMPINCOUNT")));


            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getSelectedProfData.close();

        }
        return riskProfBean;
    }

    public String getDescription(Connection CMSCon, String rpCode) throws Exception {
        String des = null;
        PreparedStatement getDes = null;
        try {

            getDes = CMSCon.prepareStatement("SELECT R.DESCRIPTION FROM RISKPROFILE R"
                    + " WHERE R.RISKPROFILECODE =?");

            getDes.setString(1, rpCode);
            rs = getDes.executeQuery();

            while (rs.next()) {

                des = rs.getString("DESCRIPTION");

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getDes.close();

        }
        return des;

    }

    /**
     * insert risk profile to the DB
     * @param CMSCon
     * @param riskProfBean
     * @return
     * @throws Exception 
     */
    public boolean insertRiskProfile(Connection CMSCon, RiskProfileBean riskProfBean) throws Exception {
        boolean success = false;
        PreparedStatement insertRP = null;
        try {
            insertRP = CMSCon.prepareStatement("INSERT INTO RISKPROFILE(RISKPROFILECODE,PROFILETYPE,DESCRIPTION,"
                    + " ACCOUNTPROFILECODE,CUSTOMERPROFILECODE,STATUS,PERIOD,MAXIMUMSINGLETXNLIMIT,MAXIMUMSINGLECASHLIMIT,"
                    + " MAXIMUMTXNCOUNT,MAXIMUMCASHTXNCOUNT,EXTRAAUTHAMOUNT,MINIMUMSINGLETXNLIMIT,MINIMUMSINGLECASHTXNLIMIT,"
                    + " MAXIMUMTOTALCASHTXNAMONT,MAXIMUMTOTALTXNAMOUNT,MAXIMUMPINCOUNT,MERCHANTPROFILECODE,LASTUPDATEDUSER,LASTUPDATEDTIME,CREATEDTIME)"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,SYSDATE)");

            insertRP.setString(1, riskProfBean.getRiskProfCode());
            insertRP.setString(2, riskProfBean.getProfileType());
            insertRP.setString(3, riskProfBean.getDescription());
            insertRP.setString(4, riskProfBean.getAccountProfCode());
            insertRP.setString(5, riskProfBean.getCustomerProfCode());
            insertRP.setString(6, riskProfBean.getStatus());
            insertRP.setString(7, riskProfBean.getPeroid());
            insertRP.setString(8, riskProfBean.getMaxSingleTxnLimit());
            insertRP.setString(9, riskProfBean.getMaxSingleCashLimit());
            insertRP.setString(10, riskProfBean.getMaxTxnCount());
            insertRP.setString(11, riskProfBean.getMaxCashCount());
            insertRP.setString(12, riskProfBean.getExtraAuthLimit());
            insertRP.setString(13, riskProfBean.getMinSingleTxnLimit());
            insertRP.setString(14, riskProfBean.getMinSingleCashLimit());
            insertRP.setString(15, riskProfBean.getMaxTotCashTxnAmount());
            insertRP.setString(16, riskProfBean.getMaxTotTxnAmount());

            if (riskProfBean.getProfileType().equals(StatusVarList.CARD_RISK_PROFILE)) {
                insertRP.setInt(17, Integer.parseInt(riskProfBean.getMaxPinCount()));
            } else {
                insertRP.setInt(17, 0);
            }
            insertRP.setString(18, riskProfBean.getMerchantProfCode());
            insertRP.setString(19, riskProfBean.getLastUpdatedUser());

            insertRP.executeUpdate();

            success = true;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertRP.close();
        }

        return success;
    }

    public boolean insertRiskProfileToOnline(Connection CMSConOnline, RiskProfileBean riskProfBean) throws Exception {
        boolean success = false;
        PreparedStatement insertRPOnline = null;
        try {
            insertRPOnline = CMSConOnline.prepareStatement("INSERT INTO ECMS_ONLINE_RISK_PROFILE(CODE,PROFILETYPE,DESCRIPTION,PERIOD,"
                    + " EXTRAAUTHRATE,MAXPINCOUNT,STATUS,MAXTXNCOUNT,MAXCASHTXNCOUNT,MAXTOTALTXNAMOUNT,MAXTOTALCASHTXNAMOUNT,"
                    + " MAXPERTXNAMOUNT,MAXPERCASHTXNAMOUNT,MINPERTXNAMOUNT,MINPERCASHTXNAMOUNT,LASTUPDATEDUSER,LASTUPDATEDTIME,CREATEDTIME)"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,SYSDATE)");

            insertRPOnline.setString(1, riskProfBean.getRiskProfCode());
            insertRPOnline.setString(2, riskProfBean.getProfileType());
            insertRPOnline.setString(3, riskProfBean.getDescription());
            insertRPOnline.setString(4, riskProfBean.getPeroid());
            if (riskProfBean.getExtraAuthLimit().equals("")) {
                insertRPOnline.setString(5, null);
            } else {
                insertRPOnline.setDouble(5, Double.parseDouble(riskProfBean.getExtraAuthLimit()));
            }
            if (riskProfBean.getProfileType().equals(StatusVarList.CARD_RISK_PROFILE)) {
                insertRPOnline.setInt(6, Integer.parseInt(riskProfBean.getMaxPinCount()));
            } else {
                insertRPOnline.setInt(6, 0);
            }

            insertRPOnline.setInt(7, riskProfBean.getStatusToOnline());
            insertRPOnline.setDouble(8, Double.parseDouble(riskProfBean.getMaxTxnCount()));
            insertRPOnline.setDouble(9, Double.parseDouble(riskProfBean.getMaxCashCount()));
            insertRPOnline.setDouble(10, Double.parseDouble(riskProfBean.getMaxTotTxnAmount()));
            insertRPOnline.setDouble(11, Double.parseDouble(riskProfBean.getMaxTotCashTxnAmount()));
            insertRPOnline.setDouble(12, Double.parseDouble(riskProfBean.getMaxSingleTxnLimit()));
            insertRPOnline.setDouble(13, Double.parseDouble(riskProfBean.getMaxSingleCashLimit()));
            insertRPOnline.setDouble(14, Double.parseDouble(riskProfBean.getMinSingleTxnLimit()));
            insertRPOnline.setDouble(15, Double.parseDouble(riskProfBean.getMinSingleCashLimit()));
            insertRPOnline.setString(16, riskProfBean.getLastUpdatedUser());




            insertRPOnline.executeUpdate();
            success = true;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertRPOnline.close();
        }

        return success;
    }

    /**
     * insert Blocked MCC
     * @param CMSCon
     * @param riskProfCode
     * @param merchantCatogoryCode
     * @return
     * @throws Exception 
     */
    public boolean insertToMcc(Connection CMSCon, String riskProfCode, String merchantCatogoryCode) throws Exception {
        boolean success = false;
        PreparedStatement insertMcc = null;
        try {
            insertMcc = CMSCon.prepareStatement("INSERT INTO RISKPROFILEBLOCKEDMCC(RISKPROFILECODE,MCC) VALUES(?,?) ");
            insertMcc.setString(1, riskProfCode);
            insertMcc.setString(2, merchantCatogoryCode);

            insertMcc.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertMcc.close();
        }
        return success;
    }

    public boolean insertToMccOnline(Connection CMSConOnline, String riskProfCode, String merchantCatogoryCode) throws Exception {
        boolean success = false;
        PreparedStatement insertMccOnline = null;
        try {
            insertMccOnline = CMSConOnline.prepareStatement("INSERT INTO ECMS_ONLINE_RP_BLOCKED_MCC(RPCODE,MCC) VALUES(?,?) ");

            insertMccOnline.setString(1, riskProfCode);
            insertMccOnline.setString(2, merchantCatogoryCode);

            insertMccOnline.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertMccOnline.close();
        }
        return success;
    }

    /**
     * insert Blocked Transaction Types
     * @param CMSCon
     * @param riskProfCode
     * @param txnTypeCode
     * @return
     * @throws Exception 
     */
    public boolean insertToTxnType(Connection CMSCon, String riskProfCode, String txnTypeCode) throws Exception {
        boolean success = false;
        PreparedStatement insertTxn = null;
        try {
            insertTxn = CMSCon.prepareStatement("INSERT INTO RISKPROFILEBLOCKEDTXNTYPE(RISKPROFILECODE,TXNTYPECODE) "
                    + " VALUES(?,?) ");
            insertTxn.setString(1, riskProfCode);
            insertTxn.setString(2, txnTypeCode);

            insertTxn.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertTxn.close();
        }
        return success;
    }

    public boolean insertToTxnTypeOnline(Connection CMSConOnline, String riskProfCode, String txnTypeCode) throws Exception {
        boolean success = false;
        PreparedStatement insertTxnOnline = null;
        try {
            insertTxnOnline = CMSConOnline.prepareStatement("INSERT INTO ECMS_ONLINE_RP_BLOCKED_TXNTYPE(RPCODE,TXNTYPECODE) "
                    + " VALUES(?,?) ");

            insertTxnOnline.setString(1, riskProfCode);
            insertTxnOnline.setInt(2, Integer.parseInt(txnTypeCode));

            insertTxnOnline.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertTxnOnline.close();
        }
        return success;
    }

    /**
     * insert Blocked Country list
     * @param CMSCon
     * @param riskProfCode
     * @param countryCode
     * @return
     * @throws Exception 
     */
    public boolean insertToCountry(Connection CMSCon, String riskProfCode, String countryCode) throws Exception {
        boolean success = false;
        PreparedStatement insertCoun = null;
        try {
            insertCoun = CMSCon.prepareStatement("INSERT INTO RISKPROFILEBLOCKEDCOUNTRY(RISKPROFILECODE,COUNTRYCODE)"
                    + " VALUES(?,?) ");
            insertCoun.setString(1, riskProfCode);
            insertCoun.setString(2, countryCode);

            insertCoun.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertCoun.close();
        }
        return success;
    }

    public boolean insertToCountryOnline(Connection CMSConOnline, String riskProfCode, String countryCode) throws Exception {
        boolean success = false;
        PreparedStatement insertCounOnline = null;
        try {
            insertCounOnline = CMSConOnline.prepareStatement("INSERT INTO ECMS_ONLINE_RP_BLOCKED_COUNTRY(RPCODE,COUNTRYCODE)"
                    + " VALUES(?,?) ");
            insertCounOnline.setString(1, riskProfCode);
            insertCounOnline.setString(2, zeroPadding(countryCode));

            insertCounOnline.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertCounOnline.close();
        }
        return success;
    }

    /**
     * insert Blocked Currency List
     * @param CMSCon
     * @param riskProfCode
     * @param currencyCode
     * @return
     * @throws Exception 
     */
    public boolean insertToCurrency(Connection CMSCon, String riskProfCode, String currencyCode) throws Exception {
        boolean success = false;
        PreparedStatement insertCur = null;
        try {
            insertCur = CMSCon.prepareStatement("INSERT INTO RISKPROFILECURRENCY(RISKPROFILECODE,CURRENCYCODE)"
                    + " VALUES(?,?) ");
            insertCur.setString(1, riskProfCode);
            insertCur.setInt(2, Integer.parseInt(currencyCode));

            insertCur.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertCur.close();
        }
        return success;
    }

    public boolean insertToCurrencyOnline(Connection CMSConOnline, String riskProfCode, String currencyCode) throws Exception {
        boolean success = false;
        PreparedStatement insertCurOnline = null;
        try {
            insertCurOnline = CMSConOnline.prepareStatement("INSERT INTO ECMS_ONLINE_RP_BLOCKED_CURR(RPCODE,CURRENCYCODE)"
                    + " VALUES(?,?) ");
            insertCurOnline.setString(1, riskProfCode);
            insertCurOnline.setString(2, zeroPadding(currencyCode));

            insertCurOnline.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertCurOnline.close();
        }
        return success;
    }

    public boolean insertToBin(Connection CMSCon, String riskProfCode, String binCode) throws Exception {
        boolean success = false;
        PreparedStatement insertBin = null;
        try {
            insertBin = CMSCon.prepareStatement("INSERT INTO RISKPROFILEBLOCKEDBIN(RISKPROFILECODE,BIN)"
                    + " VALUES(?,?) ");
            insertBin.setString(1, riskProfCode);
            insertBin.setString(2, binCode);

            insertBin.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertBin.close();
        }
        return success;
    }

    public boolean insertToBinOnline(Connection CMSConOnline, String riskProfCode, String binCode) throws Exception {
        boolean success = false;
        PreparedStatement insertBinOnline = null;
        try {
            insertBinOnline = CMSConOnline.prepareStatement("INSERT INTO ECMS_ONLINE_RP_BLOCKED_BIN(RPCODE,BIN)"
                    + " VALUES(?,?) ");
            insertBinOnline.setString(1, riskProfCode);
            insertBinOnline.setString(2, binCode);

            insertBinOnline.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertBinOnline.close();
        }
        return success;
    }

    /**
     * update Risk Profile 
     * @param CMSCon
     * @param riskProfBean
     * @return
     * @throws Exception 
     */
    public boolean updateRiskProfile(Connection CMSCon, RiskProfileBean riskProfBean) throws Exception {
        boolean success = false;
        PreparedStatement updateRp = null;
        try {
            updateRp = CMSCon.prepareStatement("UPDATE RISKPROFILE SET PROFILETYPE=?,DESCRIPTION=?,"
                    + " ACCOUNTPROFILECODE=?,CUSTOMERPROFILECODE=?,STATUS=?,PERIOD=?,MAXIMUMSINGLETXNLIMIT=?,"
                    + " MAXIMUMSINGLECASHLIMIT=?,MAXIMUMTXNCOUNT=?,MAXIMUMCASHTXNCOUNT=?,EXTRAAUTHAMOUNT=?,"
                    + " LASTUPDATEDUSER=?,MINIMUMSINGLETXNLIMIT=?,MINIMUMSINGLECASHTXNLIMIT=?,"
                    + " MAXIMUMTOTALCASHTXNAMONT=?,MAXIMUMTOTALTXNAMOUNT=?,MERCHANTPROFILECODE=?,"
                    + " MAXIMUMPINCOUNT=?,LASTUPDATEDTIME=SYSDATE"
                    + " WHERE RISKPROFILECODE=? ");



            updateRp.setString(1, riskProfBean.getProfileType());
            updateRp.setString(2, riskProfBean.getDescription());
            updateRp.setString(3, riskProfBean.getAccountProfCode());
            updateRp.setString(4, riskProfBean.getCustomerProfCode());
            updateRp.setString(5, riskProfBean.getStatus());
            updateRp.setString(6, riskProfBean.getPeroid());
            updateRp.setString(7, riskProfBean.getMaxSingleTxnLimit());
            updateRp.setString(8, riskProfBean.getMaxSingleCashLimit());
            updateRp.setString(9, riskProfBean.getMaxTxnCount());
            updateRp.setString(10, riskProfBean.getMaxCashCount());
            updateRp.setString(11, riskProfBean.getExtraAuthLimit());
            updateRp.setString(12, riskProfBean.getLastUpdatedUser());
            updateRp.setString(13, riskProfBean.getMinSingleTxnLimit());
            updateRp.setString(14, riskProfBean.getMinSingleCashLimit());
            updateRp.setString(15, riskProfBean.getMaxTotCashTxnAmount());
            updateRp.setString(16, riskProfBean.getMaxTotTxnAmount());
            updateRp.setString(17, riskProfBean.getMerchantProfCode());
            updateRp.setString(18, riskProfBean.getMaxPinCount());

            updateRp.setString(19, riskProfBean.getRiskProfCode());


            updateRp.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            updateRp.close();
        }
        return success;

    }

    public boolean updateRiskProfileToOnline(Connection CMSConOnline, RiskProfileBean riskProfBean) throws Exception {
        boolean success = false;
        PreparedStatement updateRpOnline = null;
        try {
            updateRpOnline = CMSConOnline.prepareStatement("UPDATE ECMS_ONLINE_RISK_PROFILE SET PROFILETYPE=?,DESCRIPTION=?,PERIOD=?,"
                    + " EXTRAAUTHRATE=?,MAXPINCOUNT=?,STATUS=?,MAXTXNCOUNT=?,MAXCASHTXNCOUNT=?,MAXTOTALTXNAMOUNT=?,"
                    + " MAXTOTALCASHTXNAMOUNT=?,"
                    + " MAXPERTXNAMOUNT=?,MAXPERCASHTXNAMOUNT=?,MINPERTXNAMOUNT=?,MINPERCASHTXNAMOUNT=?,LASTUPDATEDUSER=?,"
                    + " LASTUPDATEDTIME=SYSDATE"
                    + " WHERE CODE=? ");




            updateRpOnline.setString(1, riskProfBean.getProfileType());
            updateRpOnline.setString(2, riskProfBean.getDescription());
            updateRpOnline.setString(3, riskProfBean.getPeroid());
            if (riskProfBean.getExtraAuthLimit().equals("")) {
                updateRpOnline.setString(4, null);
            } else {
                updateRpOnline.setDouble(4, Double.parseDouble(riskProfBean.getExtraAuthLimit()));
            }
            if (riskProfBean.getProfileType().equals(StatusVarList.CARD_RISK_PROFILE)) {
                updateRpOnline.setInt(5, Integer.parseInt(riskProfBean.getMaxPinCount()));
            } else {
                updateRpOnline.setInt(5, 0);
            }

            updateRpOnline.setInt(6, riskProfBean.getStatusToOnline());
            updateRpOnline.setDouble(7, Double.parseDouble(riskProfBean.getMaxTxnCount()));
            updateRpOnline.setDouble(8, Double.parseDouble(riskProfBean.getMaxCashCount()));
            updateRpOnline.setDouble(9, Double.parseDouble(riskProfBean.getMaxTotTxnAmount()));
            updateRpOnline.setDouble(10, Double.parseDouble(riskProfBean.getMaxTotCashTxnAmount()));
            updateRpOnline.setDouble(11, Double.parseDouble(riskProfBean.getMaxSingleTxnLimit()));
            updateRpOnline.setDouble(12, Double.parseDouble(riskProfBean.getMaxSingleCashLimit()));
            updateRpOnline.setDouble(13, Double.parseDouble(riskProfBean.getMinSingleTxnLimit()));
            updateRpOnline.setDouble(14, Double.parseDouble(riskProfBean.getMinSingleCashLimit()));
            updateRpOnline.setString(15, riskProfBean.getLastUpdatedUser());
            updateRpOnline.setString(16, riskProfBean.getRiskProfCode());


            updateRpOnline.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            updateRpOnline.close();
        }
        return success;

    }

    public boolean deleteCountry(Connection CMSCon, String riskProfCode) throws Exception {
        boolean success = false;
        PreparedStatement deleteCoun = null;
        try {
            deleteCoun = CMSCon.prepareStatement("DELETE RISKPROFILEBLOCKEDCOUNTRY WHERE RISKPROFILECODE=?");



            deleteCoun.setString(1, riskProfCode);


            deleteCoun.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            try {
                ex.printStackTrace();
                throw ex;
            } catch (Exception e) {
                ex.printStackTrace();
                throw e;
            }
        } finally {
            deleteCoun.close();
        }
        return success;
    }

    public boolean deleteCountryFromOnline(Connection CMSConOnline, String riskProfCode) throws Exception {
        boolean success = false;
        PreparedStatement deleteCounOnline = null;
        try {
            deleteCounOnline = CMSConOnline.prepareStatement("DELETE ECMS_ONLINE_RP_BLOCKED_COUNTRY WHERE RPCODE=?");



            deleteCounOnline.setString(1, riskProfCode);


            deleteCounOnline.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            try {
                ex.printStackTrace();
                throw ex;
            } catch (Exception e) {
                ex.printStackTrace();
                throw e;
            }
        } finally {
            deleteCounOnline.close();
        }
        return success;
    }

    public boolean deleteMcc(Connection CMSCon, String riskProfCode) throws Exception {
        boolean success = false;
        PreparedStatement deleteMcc = null;
        try {
            deleteMcc = CMSCon.prepareStatement("DELETE RISKPROFILEBLOCKEDMCC WHERE RISKPROFILECODE=?");



            deleteMcc.setString(1, riskProfCode);


            deleteMcc.executeUpdate();

            success = true;
        } catch (SQLException ex) {
            try {
                ex.printStackTrace();
                throw ex;
            } catch (Exception e) {
                ex.printStackTrace();
                throw e;
            }
        } finally {
            deleteMcc.close();
        }
        return success;
    }

    public boolean deleteMccFromOnline(Connection CMSConOnline, String riskProfCode) throws Exception {
        boolean success = false;
        PreparedStatement deleteMccOnline = null;
        try {
            deleteMccOnline = CMSConOnline.prepareStatement("DELETE ECMS_ONLINE_RP_BLOCKED_MCC WHERE RPCODE=?");



            deleteMccOnline.setString(1, riskProfCode);


            deleteMccOnline.executeUpdate();

            success = true;
        } catch (SQLException ex) {
            try {
                ex.printStackTrace();
                throw ex;
            } catch (Exception e) {
                ex.printStackTrace();
                throw e;
            }
        } finally {
            deleteMccOnline.close();
        }
        return success;
    }

    public boolean deleteTxn(Connection CMSCon, String riskProfCode) throws Exception {
        boolean success = false;
        PreparedStatement deleteTxn = null;
        try {
            deleteTxn = CMSCon.prepareStatement("DELETE RISKPROFILEBLOCKEDTXNTYPE WHERE RISKPROFILECODE=?");



            deleteTxn.setString(1, riskProfCode);


            deleteTxn.executeUpdate();

            success = true;
        } catch (SQLException ex) {
            try {
                ex.printStackTrace();
                throw ex;
            } catch (Exception e) {
                ex.printStackTrace();
                throw e;
            }
        } finally {
            deleteTxn.close();
        }
        return success;
    }

    public boolean deleteTxnFromOnline(Connection CMSConOnline, String riskProfCode) throws Exception {
        boolean success = false;
        PreparedStatement deleteTxnOnline = null;
        try {
            deleteTxnOnline = CMSConOnline.prepareStatement("DELETE ECMS_ONLINE_RP_BLOCKED_TXNTYPE WHERE RPCODE=?");



            deleteTxnOnline.setString(1, riskProfCode);


            deleteTxnOnline.executeUpdate();

            success = true;
        } catch (SQLException ex) {
            try {
                ex.printStackTrace();
                throw ex;
            } catch (Exception e) {
                ex.printStackTrace();
                throw e;
            }
        } finally {
            deleteTxnOnline.close();
        }
        return success;
    }

    public boolean deleteCurrency(Connection CMSCon, String riskProfCode) throws Exception {
        boolean success = false;
        PreparedStatement deleteCur = null;
        try {
            deleteCur = CMSCon.prepareStatement("DELETE RISKPROFILECURRENCY WHERE RISKPROFILECODE=?");



            deleteCur.setString(1, riskProfCode);


            deleteCur.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            try {
                ex.printStackTrace();
                throw ex;
            } catch (Exception e) {
                ex.printStackTrace();
                throw e;
            }
        } finally {
            deleteCur.close();
        }
        return success;
    }

    public boolean deleteCurrencyFromOnline(Connection CMSConOnline, String riskProfCode) throws Exception {
        boolean success = false;
        PreparedStatement deleteCurOnline = null;
        try {
            deleteCurOnline = CMSConOnline.prepareStatement("DELETE ECMS_ONLINE_RP_BLOCKED_CURR WHERE RPCODE=?");



            deleteCurOnline.setString(1, riskProfCode);


            deleteCurOnline.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            try {
                ex.printStackTrace();
                throw ex;
            } catch (Exception e) {
                ex.printStackTrace();
                throw e;
            }
        } finally {
            deleteCurOnline.close();
        }
        return success;
    }

    public boolean deleteBin(Connection CMSCon, String riskProfCode) throws Exception {
        boolean success = false;
        PreparedStatement deleteBin = null;
        try {
            deleteBin = CMSCon.prepareStatement("DELETE RISKPROFILEBLOCKEDBIN WHERE RISKPROFILECODE=?");



            deleteBin.setString(1, riskProfCode);


            deleteBin.executeUpdate();

            success = true;
        } catch (SQLException ex) {
            try {
                ex.printStackTrace();
                throw ex;
            } catch (Exception e) {
                ex.printStackTrace();
                throw e;
            }
        } finally {
            deleteBin.close();
        }
        return success;
    }

    public boolean deleteBinFromOnline(Connection CMSConOnline, String riskProfCode) throws Exception {
        boolean success = false;
        PreparedStatement deleteBinOnline = null;
        try {
            deleteBinOnline = CMSConOnline.prepareStatement("DELETE ECMS_ONLINE_RP_BLOCKED_BIN WHERE RPCODE=?");



            deleteBinOnline.setString(1, riskProfCode);


            deleteBinOnline.executeUpdate();

            success = true;
        } catch (SQLException ex) {
            try {
                ex.printStackTrace();
                throw ex;
            } catch (Exception e) {
                ex.printStackTrace();
                throw e;
            }
        } finally {
            deleteBinOnline.close();
        }
        return success;
    }

    /**
     * Delete Risk Profile from DB 
     * @param CMSCon
     * @param riskProfBean
     * @return
     * @throws Exception 
     */
    public boolean deleteRiskProfile(Connection CMSCon, RiskProfileBean riskProfBean) throws Exception {
        boolean success = false;
        PreparedStatement deleteRP = null;
        try {
            deleteRP = CMSCon.prepareStatement("DELETE RISKPROFILE WHERE RISKPROFILECODE=?");



            deleteRP.setString(1, riskProfBean.getRiskProfCode());


            deleteRP.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            try {
                ex.printStackTrace();
                throw ex;
            } catch (Exception e) {
                ex.printStackTrace();
                throw e;
            }
        } finally {
            deleteRP.close();
        }
        return success;
    }

    public boolean deleteRiskProfileFromOnline(Connection CMSCon, RiskProfileBean riskProfBean) throws Exception {
        boolean success = false;
        PreparedStatement deleteRPOnline = null;
        try {
            deleteRPOnline = CMSCon.prepareStatement("DELETE ECMS_ONLINE_RISK_PROFILE WHERE CODE=?");



            deleteRPOnline.setString(1, riskProfBean.getRiskProfCode());


            deleteRPOnline.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            try {
                ex.printStackTrace();
                throw ex;
            } catch (Exception e) {
                ex.printStackTrace();
                throw e;
            }
        } finally {
            deleteRPOnline.close();
        }
        return success;
    }

    public String getOnlineTxnCode(Connection CMSCon, String txnCode) throws Exception {

        PreparedStatement getOnLTxn = null;
        String onlineTxnCode = null;

        try {

            String query = "SELECT ONLINETXNTYPE FROM TRANSACTIONTYPE "
                    + " WHERE TRANSACTIONCODE = ? ";

            getOnLTxn = CMSCon.prepareStatement(query);
            getOnLTxn.setString(1, txnCode);
            rs = getOnLTxn.executeQuery();

            while (rs.next()) {
                onlineTxnCode = rs.getString("ONLINETXNTYPE");
            }


        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getOnLTxn.close();
        }
        return onlineTxnCode;
    }

    public String getCountryNumCode(Connection CMSCon, String countryCode) throws Exception {

        PreparedStatement getCounNum = null;
        String countryNumCode = null;

        try {

            String query = "SELECT COUNTRYNUMCODE FROM COUNTRY "
                    + " WHERE COUNTRYALPHA3CODE = ? ";

            getCounNum = CMSCon.prepareStatement(query);
            getCounNum.setString(1, countryCode);
            rs = getCounNum.executeQuery();

            while (rs.next()) {
                countryNumCode = Integer.toString(rs.getInt("COUNTRYNUMCODE"));
            }


        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getCounNum.close();
        }
        return countryNumCode;
    }

    public List<RiskProfileBean> getGivenTypeRiskProfile(Connection con, String rpType) throws Exception {


        PreparedStatement getRPStat = null;
        List<RiskProfileBean> riskProfList = new ArrayList<RiskProfileBean>();
        try {
            getRPStat = con.prepareStatement("SELECT R.RISKPROFILECODE,R.DESCRIPTION"
                    + " FROM RISKPROFILE R"
                    + " WHERE R.PROFILETYPE=?");

            getRPStat.setString(1, rpType);
            rs = getRPStat.executeQuery();

            while (rs.next()) {

                RiskProfileBean bean = new RiskProfileBean();


                bean.setRiskProfCode(rs.getString("RISKPROFILECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                riskProfList.add(bean);

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getRPStat.close();

        }
        return riskProfList;
    }

    public boolean checkRiskProfileCodeExsistance(Connection CMSCon, String riskProfileCode) throws Exception {

        boolean isExsist = false;
        PreparedStatement checkStat = null;
        try {
            checkStat = CMSCon.prepareStatement(" SELECT DESCRIPTION FROM RISKPROFILE"
                    + " WHERE RISKPROFILECODE = ? ");


            checkStat.setString(1, riskProfileCode);
            rs = checkStat.executeQuery();

            while (rs.next()) {

                isExsist = true;
            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            checkStat.close();

        }

        return isExsist;

    }

    public List<RiskProfileBinBean> getAllBin(Connection CMSCon) throws Exception {
        List<RiskProfileBinBean> binList = new ArrayList<RiskProfileBinBean>();
        PreparedStatement getBin = null;
        try {

            getBin = CMSCon.prepareStatement("SELECT C.BIN,C.DESCRIPTION FROM CARDBIN C "
                    + " WHERE C.STATUS=?");

            getBin.setString(1, StatusVarList.ACTIVE_STATUS);
            rs = getBin.executeQuery();

            while (rs.next()) {

                RiskProfileBinBean bean = new RiskProfileBinBean();

                bean.setBinCode(rs.getString("BIN"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                binList.add(bean);
            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getBin.close();

        }
        return binList;
    }

    public String zeroPadding(String code) {
        int len = code.length();

        if (len < 3 && len == 2) {
            code = "0" + code;
        } else if (len < 3 && len == 1) {
            code = "00" + code;
        }

        return code;
    }
}
