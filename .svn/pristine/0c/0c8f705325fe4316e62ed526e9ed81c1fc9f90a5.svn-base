/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.persistance;

import com.epic.cms.admin.controlpanel.transactionmgt.bean.FeeTypeBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.StandingOrderTypesBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.UtilityProviderBean;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.system.util.logs.LogFileCreator;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 *
 * @author asitha_l
 */
public class StandingOrderTypesPersistance {

    private ResultSet rs;
    private HashMap<String, String> currencyMap = null;
    private HashMap<String, String> areasMap = null;
    private HashMap<String, String> bankNames = null;
    private String commonParam = null;
    private Map<String, String> branchList = null;
    private HashMap<String, String> payTypeList = null;
    private StandingOrderTypesBean standingOrderTypesBean;
    private List<UtilityProviderBean> utilityProviderList;
    private UtilityProviderBean utilityProviderBean;
    private List<FeeTypeBean> feeTypeList;
    private FeeTypeBean feeTypeBean;

    public List<StatusBean> getStatustList(Connection cmsCon) throws SQLException, Exception {
        List<StatusBean> statustList = new ArrayList<StatusBean>();
        PreparedStatement ps = null;

        try {
            String query = "SELECT STATUSCODE, DESCRIPTION FROM STATUS WHERE STATUSCATEGORY = ?";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, StatusVarList.GENESTATUCAT);
            rs = ps.executeQuery();

            while (rs.next()) {
                StatusBean bean = new StatusBean();

                bean.setStatusCode(rs.getString("STATUSCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                statustList.add(bean);
            }

            return statustList;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public HashMap<String, String> getCurrency(Connection con) throws SQLException {
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement("SELECT CURRENCYNUMCODE,DESCRIPTION FROM CURRENCY");
            rs = ps.executeQuery();
            currencyMap = new HashMap<String, String>();

            while (rs.next()) {
                currencyMap.put(rs.getString("CURRENCYNUMCODE"), rs.getString("DESCRIPTION"));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }
        currencyMap = sortAlphabetically(currencyMap);
        return currencyMap;
    }

    public HashMap<String, String> getAreas(Connection con) throws SQLException {
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement("SELECT AREACODE,DESCRIPTION FROM AREA");
            rs = ps.executeQuery();
            areasMap = new HashMap<String, String>();

            while (rs.next()) {
                areasMap.put(rs.getString("AREACODE"), rs.getString("DESCRIPTION"));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }
        areasMap = sortAlphabetically(areasMap);
        return areasMap;
    }

    public HashMap<String, String> getBanks(Connection con) throws SQLException {
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement("SELECT BANKCODE,BANKNAME FROM BANK");
            rs = ps.executeQuery();
            bankNames = new HashMap<String, String>();

            while (rs.next()) {
                bankNames.put(rs.getString("BANKCODE"), rs.getString("BANKNAME"));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }
        bankNames = sortAlphabetically(bankNames);
        return bankNames;
    }

    private static HashMap<String, String> sortAlphabetically(HashMap<String, String> input) {
        Map<String, String> tempMap = new HashMap<String, String>();
        for (String wsState : input.keySet()) {
            tempMap.put(wsState, input.get(wsState));
        }
        List<String> mapKeys = new ArrayList<String>(tempMap.keySet());
        List<String> mapValues = new ArrayList<String>(tempMap.values());
        HashMap<String, String> sortedMap = new LinkedHashMap<String, String>();
        TreeSet<String> sortedSet = new TreeSet<String>(mapValues);
        Object[] sortedArray = sortedSet.toArray();
        int size = sortedArray.length;
        for (int i = 0; i < size; i++) {
            sortedMap.put(mapKeys.get(mapValues.indexOf(sortedArray[i])),
                    (String) sortedArray[i]);
        }
        return sortedMap;
    }

    public String getCommonparam(Connection con) throws SQLException {
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement("SELECT BANKCODE,BASECURRENCY FROM COMMONPARAMETER");
            rs = ps.executeQuery();

            while (rs.next()) {
                commonParam = rs.getString("BANKCODE") + "," + rs.getString("BASECURRENCY");
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return commonParam;
    }

    public Map<String, String> getBranchList(Connection con, String bankCode) throws SQLException {
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement("SELECT BRANCHCODE,DESCRPTION FROM BANKBRANCH WHERE BANKCODE = ?");
            ps.setString(1, bankCode);
            rs = ps.executeQuery();
            branchList = new HashMap<String, String>();

            while (rs.next()) {
                branchList.put(rs.getString("BRANCHCODE"), rs.getString("DESCRPTION"));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }
        return branchList;
    }

    public Boolean insertNewStandingOrderType(StandingOrderTypesBean bean, Connection con) throws SQLException, Exception {
        Boolean success = false;
        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO STANDINGORDERTYPE (ORDERID,"
                    + "ORDERNAME,CURRENCYTYPE,BANKCODE,BRANCHCODE,"
                    + "ACCOUNTNO,CONTACTPERSON,"
                    + "CONTACTNO,STATUS,LASTUPDATEDUSER,"
                    + "MINIMUMTXNAMOUNT,MAXIMUMTXNAMOUNT,CATEGORY,PROVIDERID,FEECODE)"
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            ps = con.prepareStatement(query);
            ps.setString(1, bean.getOrderID());
            ps.setString(2, bean.getOrderName());
            ps.setString(3, bean.getCurrencyType());
            ps.setString(4, bean.getBankCode());
            ps.setString(5, bean.getBranchCode());
            ps.setString(6, bean.getAccNumber());
            ps.setString(7, bean.getContactPerson());
            ps.setString(8, bean.getContactNumber());
            ps.setString(9, bean.getStatusCode());
            ps.setString(10, bean.getLastUpdatedUser());
            ps.setString(11, bean.getMinTransactionAmt());
            ps.setString(12, bean.getMaxTransactionAmt());
            ps.setString(13, bean.getCategory());
            ps.setString(14, bean.getUtilityProvider());
            ps.setString(15, bean.getFeeType());

            int row = ps.executeUpdate();
            if (row == 1) {
                success = true;
            }
        } catch (SQLException ex) {
            LogFileCreator.writeErrorToLog(ex);
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return success;
    }

    public List<StandingOrderTypesBean> getAllStandingOrderData(Connection con) throws SQLException, Exception {
        PreparedStatement ps = null;

        try {
            List<StandingOrderTypesBean> standingOrderList = new ArrayList<StandingOrderTypesBean>();
            String query = "SELECT SOT.ORDERID,SOT.ORDERNAME,"
                    + "SOT.CURRENCYTYPE,S.DESCRIPTION AS STATUSDES,SOT.MINIMUMTXNAMOUNT,"
                    + "SOT.MAXIMUMTXNAMOUNT,SOT.STATUS,SOT.BANKCODE,SOT.ACCOUNTNO,"
                    + "SOT.CONTACTPERSON,SOT.CONTACTNO,"
                    + "SOT.BRANCHCODE,SOT.CATEGORY,SOT.PROVIDERID,SOT.FEECODE,FT.DESCRIPTION AS FTDES FROM STANDINGORDERTYPE SOT,STATUS S,FEETYPE FT "
                    + "WHERE S.STATUSCODE=SOT.STATUS AND FT.FEETYPECODE=SOT.FEECODE";

            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                StandingOrderTypesBean bean = new StandingOrderTypesBean();

                bean.setOrderID(rs.getString("ORDERID"));
                bean.setOrderName(rs.getString("ORDERNAME"));
                bean.setCurrencyType(rs.getString("CURRENCYTYPE"));
                bean.setMinTransactionAmt(rs.getString("MINIMUMTXNAMOUNT"));
                bean.setMaxTransactionAmt(rs.getString("MAXIMUMTXNAMOUNT"));
                bean.setStatusCode(rs.getString("STATUS"));
                bean.setBankCode(rs.getString("BANKCODE"));
                bean.setAccNumber(rs.getString("ACCOUNTNO"));
                bean.setContactPerson(rs.getString("CONTACTPERSON"));
                bean.setContactNumber(rs.getString("CONTACTNO"));
                bean.setBranchCode(rs.getString("BRANCHCODE"));
                bean.setStatus(rs.getString("STATUSDES"));
                bean.setCategory(rs.getString("CATEGORY"));
                bean.setUtilityProvider(rs.getString("PROVIDERID"));
                bean.setFeeType(rs.getString("FEECODE"));
                bean.setFeeTypeDes(rs.getString("FTDES"));

                standingOrderList.add(bean);
            }
            return standingOrderList;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public HashMap<String, String> getPayTypeList(Connection con, String bankCode, String commonParam) throws SQLException {
        PreparedStatement ps = null;
        String quary;
        if (bankCode.equals(commonParam)) {
            quary = "SELECT PAYMENTMODECODE,DESCRIPTION FROM paymentmode WHERE ISSUERORACQUIRE='ISS' AND DESCRIPTION='DIRECT ACCOUNT'";
        } else {
            quary = "SELECT PAYMENTMODECODE,DESCRIPTION FROM paymentmode WHERE ISSUERORACQUIRE='ISS' AND NOT DESCRIPTION='DIRECT ACCOUNT'";
        }
        try {
            ps = con.prepareStatement(quary);
            rs = ps.executeQuery();
            payTypeList = new HashMap<String, String>();

            while (rs.next()) {
                payTypeList.put(rs.getString("PAYMENTMODECODE"), rs.getString("DESCRIPTION"));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }
        return payTypeList;
    }

    public StandingOrderTypesBean getStandingOrderTypesById(Connection con, String id) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs2 = null;
        try {
            ps = con.prepareStatement("SELECT SOT.ORDERID,"
                    + "  SOT.ORDERNAME,"
                    + "  SOT.ACCOUNTNO,"
                    + "  SOT.CONTACTPERSON,"
                    + "  SOT.CONTACTNO,"
                    + "  SOT.MINIMUMTXNAMOUNT,"
                    + "  SOT.MAXIMUMTXNAMOUNT,"
                    + "  SOT.LASTUPDATEDUSER ,"
                    + "  SOT.LASTUPDATEDTIME,"
                    + "  SOT.CREATEDTIME,"
                    + "  SOT.CATEGORY,"
                    + "  SOT.FEECODE,"
                    + "  STATUS.DESCRIPTION AS STATUS,"
                    + "  CURRENCY.DESCRIPTION AS CURRENCY,"
                    + "  FEETYPE.DESCRIPTION AS FEETYPEDES "
                    + "FROM STANDINGORDERTYPE SOT,"
                    + "  STATUS,"
                    + "  CURRENCY,"
                    + "  FEETYPE "
                    + "WHERE SOT.STATUS     =STATUS.STATUSCODE "
                    + "AND SOT.CURRENCYTYPE =CURRENCY.CURRENCYNUMCODE "
                    + "AND SOT.FEECODE=FEETYPE.FEETYPECODE "
                    + "AND SOT.ORDERID = ?");
            ps.setString(1, id);
            rs = ps.executeQuery();
            standingOrderTypesBean = new StandingOrderTypesBean();

            while (rs.next()) {

                standingOrderTypesBean.setOrderID(rs.getString("ORDERID"));
                standingOrderTypesBean.setOrderName(rs.getString("ORDERNAME"));
                //standingOrderTypesBean.setBankName(rs.getString("BANKNAME"));
                //standingOrderTypesBean.setBranchName(rs.getString("BRANCHNAME"));
                standingOrderTypesBean.setAccNumber(rs.getString("ACCOUNTNO"));
                standingOrderTypesBean.setContactPerson(rs.getString("CONTACTPERSON"));
                standingOrderTypesBean.setContactNumber(rs.getString("CONTACTNO"));
                standingOrderTypesBean.setMinTransactionAmt(rs.getString("MINIMUMTXNAMOUNT"));
                standingOrderTypesBean.setMaxTransactionAmt(rs.getString("MAXIMUMTXNAMOUNT"));
                standingOrderTypesBean.setStatus(rs.getString("STATUS"));
                standingOrderTypesBean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                standingOrderTypesBean.setLastUpdatedTime(rs.getString("LASTUPDATEDTIME"));
                standingOrderTypesBean.setCreatedTime(rs.getString("CREATEDTIME"));
                standingOrderTypesBean.setCurrencyType(rs.getString("CURRENCY"));
                standingOrderTypesBean.setFeeTypeDes(rs.getString("FEETYPEDES"));
                standingOrderTypesBean.setCategory(rs.getString("CATEGORY"));

            }
            //get bank details for utility payments
            if (standingOrderTypesBean.getCategory().equals("UTILITY")) {
                try {
                    ps = con.prepareStatement("SELECT SOT.BANKCODE,SOT.BRANCHCODE,BANK.BANKNAME,BANKBRANCH.DESCRPTION AS BRANCHNAME,UP.DESCRIPTION AS UPDES FROM STANDINGORDERTYPE SOT,BANK,BANKBRANCH,UTILITYPROVIDER UP WHERE SOT.BANKCODE=BANK.BANKCODE AND SOT.BRANCHCODE=BANKBRANCH.BRANCHCODE AND SOT.PROVIDERID=UP.PROVIDERID AND SOT.ORDERID=?");
                    ps.setString(1, id);
                    rs2 = ps.executeQuery();
                    while (rs2.next()) {
                        standingOrderTypesBean.setBankName(rs2.getString("BANKNAME"));
                        standingOrderTypesBean.setBranchName(rs2.getString("BRANCHNAME"));
                        standingOrderTypesBean.setUtilityProviderDes(rs2.getString("UPDES"));
                    }
                } catch (Exception ex) {
                    throw ex;
                }finally{
                    rs2.close();
                }

            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }
        return standingOrderTypesBean;

    }

    public boolean deleteStandingOrder(Connection con, String id) throws SQLException, Exception {
        PreparedStatement ps = null;
        boolean success = false;
        try {
            String query = "DELETE FROM STANDINGORDERTYPE WHERE ORDERID =?";

            ps = con.prepareStatement(query);
            ps.setString(1, id);

            int i = ps.executeUpdate();
            if (i == 1) {
                success = true;
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return success;
    }

    public boolean updateStandingOrderType(StandingOrderTypesBean bean, Connection con) throws SQLException, Exception {
        PreparedStatement ps = null;
        boolean success = false;
        try {
            String query = "UPDATE STANDINGORDERTYPE  SET "
                    + "ORDERNAME=?,CURRENCYTYPE=?,BANKCODE=?,BRANCHCODE=?,"
                    + "ACCOUNTNO=?,CONTACTPERSON=?,"
                    + "CONTACTNO=?,STATUS=?,LASTUPDATEDUSER=?,"
                    + "MINIMUMTXNAMOUNT=?,MAXIMUMTXNAMOUNT=?,CATEGORY=?,PROVIDERID=?,FEECODE=? WHERE ORDERID=?";

            ps = con.prepareStatement(query);

            ps = con.prepareStatement(query);

            ps.setString(1, bean.getOrderName());
            ps.setString(2, bean.getCurrencyType());
            ps.setString(3, bean.getBankCode());
            ps.setString(4, bean.getBranchCode());
            ps.setString(5, bean.getAccNumber());
            ps.setString(6, bean.getContactPerson());
            ps.setString(7, bean.getContactNumber());
            ps.setString(8, bean.getStatusCode());
            ps.setString(9, bean.getLastUpdatedUser());
            ps.setString(10, bean.getMinTransactionAmt());
            ps.setString(11, bean.getMaxTransactionAmt());
            ps.setString(12, bean.getCategory());
            ps.setString(13, bean.getUtilityProvider());
            ps.setString(14, bean.getFeeType());
            ps.setString(15, bean.getOrderID());

            int i = ps.executeUpdate();
            if (i == 1) {
                success = true;
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return success;
    }

    //load utility providers
    public List<UtilityProviderBean> getUtilityProviderList(Connection con) throws Exception {
        utilityProviderList = new ArrayList<UtilityProviderBean>();
        PreparedStatement ps = null;
        try {
            String query = "SELECT PROVIDERID,DESCRIPTION FROM UTILITYPROVIDER";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                utilityProviderBean = new UtilityProviderBean();
                utilityProviderBean.setProviderId(rs.getString("PROVIDERID"));
                utilityProviderBean.setDescription(rs.getString("DESCRIPTION"));

                utilityProviderList.add(utilityProviderBean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return utilityProviderList;
    }

    //load fee types 
    public List<FeeTypeBean> getAllFeeTypes(Connection con) throws Exception {
        feeTypeList = new ArrayList<FeeTypeBean>();
        PreparedStatement ps = null;
        try {
            String query = "SELECT FEETYPECODE,DESCRIPTION FROM FEETYPE";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                feeTypeBean = new FeeTypeBean();
                feeTypeBean.setFeeTypeCode(rs.getString("FEETYPECODE"));
                feeTypeBean.setDescription(rs.getString("DESCRIPTION"));
                feeTypeList.add(feeTypeBean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return feeTypeList;
    }

}
