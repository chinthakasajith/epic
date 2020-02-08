/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.mtmm.terminalmgt.terminaldata.persistance;

import com.epic.cms.admin.controlpanel.profilemgt.bean.RiskProfileBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.MerchantCategoryBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
import com.epic.cms.mtmm.terminalmgt.terminaldata.bean.MerchantSearchBean;
import com.epic.cms.mtmm.terminalmgt.terminaldata.bean.TerminalDataCaptureBean;
import com.epic.cms.system.util.datetime.SystemDateTime;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author nisansala
 */
public class TerminalDataCapturePersistance {

    private HashMap<String, String> modelList;
    private List<TerminalDataCaptureBean> terminalBeanList;
    private HashMap<String, String> allocateList = null;
    private HashMap<String, String> terminalStatusList = null;
    private HashMap<String, String> manufacturerList = null;

    /**
     * to retrieve all the terminal models and their descriptions
     * @param con
     * @return
     * @throws Exception 
     */
    public HashMap<String, String> getAllModelList(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("SELECT TM.MODLECODE,TM.DESCRIPTION FROM TERMINALMODLE TM");

            rs = ps.executeQuery();
            manufacturerList = new HashMap<String, String>();

            while (rs.next()) {
                manufacturerList.put(rs.getString("MODLECODE"), rs.getString("DESCRIPTION"));
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return manufacturerList;
    }

    /**
     * to retrieve all the terminal manufacturers and their descriptions
     * @param con
     * @return
     * @throws Exception 
     */
    public HashMap<String, String> getAllManufacturers(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("SELECT TM.MANUFACTURECODE,TM.DESCRIPTION FROM TERMINALMANUFACTURE TM");

            rs = ps.executeQuery();
            modelList = new HashMap<String, String>();

            while (rs.next()) {
                modelList.put(rs.getString("MANUFACTURECODE"), rs.getString("DESCRIPTION"));
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return modelList;
    }

    /**
     * to retrieve all the terminal allocation status and their descriptions
     * @param con
     * @return
     * @throws Exception 
     */
    public HashMap<String, String> getAllocationStatus(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("SELECT S.STATUSCODE,S.DESCRIPTION FROM STATUS S WHERE STATUSCATEGORY='ALLO'");

            rs = ps.executeQuery();
            allocateList = new HashMap<String, String>();

            while (rs.next()) {
                allocateList.put(rs.getString("STATUSCODE"), rs.getString("DESCRIPTION"));
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return allocateList;
    }

    /**
     * to retrieve all the terminal status and their descriptions
     * @param con
     * @return
     * @throws Exception 
     */
    public HashMap<String, String> getTerminalStatus(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("SELECT S.STATUSCODE,S.DESCRIPTION FROM STATUS S WHERE STATUSCATEGORY='GENR'");

            rs = ps.executeQuery();
            terminalStatusList = new HashMap<String, String>();

            while (rs.next()) {
                terminalStatusList.put(rs.getString("STATUSCODE"), rs.getString("DESCRIPTION"));
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }

        return terminalStatusList;
    }

    public HashMap<String, String> getModelsToManufacturer(Connection con, String manufacturer) throws Exception {
        HashMap<String, String> difManufactermModels = new HashMap<String, String>();
        ResultSet rs = null;
        PreparedStatement ps = null;


        try {
            ps = con.prepareStatement("SELECT TM.MODLECODE,TM.DESCRIPTION FROM TERMINALMODLE TM WHERE TM.MANUFACTURECODE=?");

            ps.setString(1, manufacturer);
            rs = ps.executeQuery();

            while (rs.next()) {
                difManufactermModels.put(rs.getString("MODLECODE"), rs.getString("DESCRIPTION"));
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return difManufactermModels;
    }

    /**
     * to get assigned transactions to the terminal
     * @param con
     * @param TID
     * @return
     * @throws Exception 
     */
    public List<TerminalDataCaptureBean> getAssignedTransactions(Connection con, String TID) throws Exception {

        List<TerminalDataCaptureBean> assignTxn = new ArrayList<TerminalDataCaptureBean>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT TT.TXNCODE,TP.DESCRIPTION,TP.ONLINETXNTYPE FROM TERMINALTXN TT,TRANSACTIONTYPE TP WHERE TT.TXNCODE = TP.TRANSACTIONCODE AND TT.TERMINALID=? ";
            ps = con.prepareStatement(query);
            ps.setString(1, TID);
            rs = ps.executeQuery();

            while (rs.next()) {
                TerminalDataCaptureBean bean = new TerminalDataCaptureBean();
                bean.setTransactions(rs.getString(1));
                bean.setTransactionDes(rs.getString(2));
                bean.setOnlineTxnCode(rs.getString(3));
                assignTxn.add(bean);
            }
        } catch (SQLException ex) {

            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return assignTxn;
    }

    public List<TerminalDataCaptureBean> getNotAssignedTransactions(Connection con, String MID, String TID) throws Exception {

        List<TerminalDataCaptureBean> assignTxn = new ArrayList<TerminalDataCaptureBean>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT DISTINCT MLT.TRANSACTIONCODE,TP.DESCRIPTION,TP.ONLINETXNTYPE FROM TRANSACTIONTYPE TP,MERCHANTLOCATIONTRANSACTION MLT,TERMINAL T"
                    + " WHERE MLT.TRANSACTIONCODE = TP.TRANSACTIONCODE AND T.MERCHANTID=? AND MLT.TRANSACTIONCODE NOT IN(SELECT TT.TXNCODE"
                    + " FROM TERMINALTXN TT WHERE TT.TERMINALID=?)";
            ps = con.prepareStatement(query);
            ps.setString(1, MID);
            ps.setString(2, TID);
            rs = ps.executeQuery();

            while (rs.next()) {
                TerminalDataCaptureBean bean = new TerminalDataCaptureBean();
                bean.setTransactions(rs.getString(1));
                bean.setTransactionDes(rs.getString(2));
                bean.setOnlineTxnCode(rs.getString(3));
                assignTxn.add(bean);
            }
        } catch (SQLException ex) {

            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return assignTxn;
    }

    public List<TypeMgtBean> getAllTxnTypes(Connection con) throws Exception {

        List<TypeMgtBean> txnTypeList = new ArrayList<TypeMgtBean>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT TP.TRANSACTIONCODE,TP.DESCRIPTION,TP.ONLINETXNTYPE FROM TRANSACTIONTYPE TP";
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                TypeMgtBean bean = new TypeMgtBean();
                bean.setTransactionTypeCode(rs.getString(1));
                bean.setDescription(rs.getString(2));
                bean.setOnlineTxnCode(rs.getString(3));
                txnTypeList.add(bean);
            }
        } catch (SQLException ex) {

            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return txnTypeList;
    }

    /**
     * to get details about one terminal
     * @param con
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public TerminalDataCaptureBean viewOneTerminalData(Connection con, String id) throws SQLException, Exception {

        TerminalDataCaptureBean terminalBean = new TerminalDataCaptureBean();
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            String query = "SELECT DISTINCT T.TERMINALID,T.TERMINALNAME,T.SERIALNO,T.MANUFACTURER,TM.DESCRIPTION AS MANUFACTDES,"
                    + "T.MODEL,M.DESCRIPTION AS MODELDES,T.INSTALLATIONDATE,T.ACTIVATIONDATE,T.ALLOCATIONSTATUS,ML.DESCRIPTION AS MERCHANTDES,"
                    + "S.DESCRIPTION AS STATUSDES,SS.DESCRIPTION AS TERMNLSTATUSDES,T.TERMINALSTATUS,T.MERCHANTID,T.CURRENCYCODE,T.MCC "
                    + "FROM STATUS S,STATUS SS,TERMINALMODLE M,TERMINALMANUFACTURE TM,CURRENCY C,TERMINAL T "
                    + "LEFT JOIN MERCHANTLOCATION ML ON T.MERCHANTID = ML.MERCHANTID "
                    + "WHERE T.MODEL = M.MODLECODE AND T.ALLOCATIONSTATUS = S.STATUSCODE AND "
                    + "T.MANUFACTURER=TM.MANUFACTURECODE AND T.TERMINALSTATUS = SS.STATUSCODE AND T.TERMINALID=? ";

            /**
             *  String query = "SELECT T.TERMINALID,T.TERMINALNAME,T.SERIALNO,T.MANUFACTURER,TM.DESCRIPTION AS MANUFACTDES,"
            + "T.MODEL,M.DESCRIPTION AS MODELDES,T.INSTALLATIONDATE,T.ACTIVATIONDATE,T.ALLOCATIONSTATUS,ML.DESCRIPTION AS MERCHANTDES,"
            + "S.DESCRIPTION AS STATUSDES,SS.DESCRIPTION AS TERMNLSTATUSDES,T.TERMINALSTATUS,T.MERCHANTID,T.CURRENCYCODE,C.DESCRIPTION AS CURRENCYDES "
            + "FROM STATUS S,STATUS SS,TERMINALMODLE M,TERMINALMANUFACTURE TM,CURRENCY C,TERMINAL T "
            + "LEFT JOIN MERCHANTLOCATION ML ON T.MERCHANTID = ML.MERCHANTID "
            + "WHERE T.MODEL = M.MODLECODE AND T.ALLOCATIONSTATUS = S.STATUSCODE AND "
            + "T.MANUFACTURER=TM.MANUFACTURECODE AND T.TERMINALSTATUS = SS.STATUSCODE AND C.CURRENCYNUMCODE=T.CURRENCYCODE AND T.TERMINALID=? ";
             */
            ps = con.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                terminalBean.setTerminalID(rs.getString("TERMINALID"));
                terminalBean.setMerchantID(rs.getString("MERCHANTID"));
                terminalBean.setMerchantDes(rs.getString("MERCHANTDES"));
                terminalBean.setName(rs.getString("TERMINALNAME"));
                terminalBean.setSerialNo(rs.getString("SERIALNO"));
                terminalBean.setManufacturer(rs.getString("MANUFACTURER"));
                terminalBean.setManufactDes(rs.getString("MANUFACTDES"));
                terminalBean.setModel(rs.getString("MODEL"));
                terminalBean.setModelDes(rs.getString("MODELDES"));
                terminalBean.setInstallationDate(this.convertStringDate(rs.getDate("INSTALLATIONDATE")));
                if (rs.getString("ACTIVATIONDATE") != null) {
                    terminalBean.setActivationDate(this.convertStringDate(rs.getDate("ACTIVATIONDATE")));
                } else {
                    terminalBean.setActivationDate("---- -- --");
                }
                terminalBean.setAllocationStatus(rs.getString("ALLOCATIONSTATUS"));
                terminalBean.setAlloStatus(rs.getString("STATUSDES"));
                terminalBean.setTerminalStatus(rs.getString("TERMINALSTATUS"));
                terminalBean.setTerminalStatusDes(rs.getString("TERMNLSTATUSDES"));
                terminalBean.setCurrency(rs.getString("CURRENCYCODE"));
                terminalBean.setMcc(rs.getString("MCC"));
                //terminalBean.setCurrencyDes(rs.getString("CURRENCYDES"));
            }
            return terminalBean;

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

    /**
     * to search terminals
     * @param con
     * @param terminalBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<TerminalDataCaptureBean> searchTerminalData(Connection con, TerminalDataCaptureBean terminalBean) throws SQLException, Exception {
        //hold the statement to be executed
        PreparedStatement ps = null;
        String defaultQuery = null;
        ResultSet rs = null;

        try {
            defaultQuery = " SELECT T.TERMINALID,T.TERMINALNAME,T.SERIALNO,T.MANUFACTURER,T.MODEL,M.DESCRIPTION AS MODELDES,"
                    + " T.ALLOCATIONSTATUS,T.TERMINALSTATUS,S.DESCRIPTION AS ALLODES,SS.DESCRIPTION AS TERMINALDES, T.MERCHANTID,ML.DESCRIPTION AS MERCHANTDES "
                    + " FROM STATUS S,STATUS SS,TERMINALMODLE M,TERMINAL T "
                    + " LEFT JOIN MERCHANTLOCATION ML ON T.MERCHANTID = ML.MERCHANTID "
                    + " WHERE T.MODEL = M.MODLECODE AND T.ALLOCATIONSTATUS = S.STATUSCODE AND"
                    + " T.TERMINALSTATUS = SS.STATUSCODE AND T.TERMINALID <> (SELECT TERMINALID FROM COMMONPARAMETER)";


            String condition = "";

            if (!terminalBean.getTerminalID().contentEquals("") || terminalBean.getTerminalID().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "T.TERMINALID LIKE '%" + terminalBean.getTerminalID() + "%'";
            }
            if (!terminalBean.getMerchantID().contentEquals("") || terminalBean.getMerchantID().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "T.MERCHANTID LIKE '%" + terminalBean.getMerchantID() + "%'";
            }
            if (!terminalBean.getName().contentEquals("") || terminalBean.getName().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "UPPER(T.TERMINALNAME) LIKE '%" + terminalBean.getName().toUpperCase() + "%'";
            }
            if (!terminalBean.getSerialNo().contentEquals("") || terminalBean.getSerialNo().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "T.SERIALNO = '" + terminalBean.getSerialNo() + "'";
            }
            if (!terminalBean.getModel().contentEquals("") || terminalBean.getModel().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "T.MODEL = '" + terminalBean.getModel() + "'";
            }
            if (!terminalBean.getAllocationStatus().contentEquals("") || terminalBean.getAllocationStatus().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "T.ALLOCATIONSTATUS = '" + terminalBean.getAllocationStatus() + "'";
            }
            if (!terminalBean.getTerminalStatus().contentEquals("") || terminalBean.getTerminalStatus().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "T.TERMINALSTATUS = '" + terminalBean.getTerminalStatus() + "'";
            }

            if (!condition.equals("")) {
                defaultQuery += "  AND " + condition + " ORDER BY T.TERMINALID";
            } else {
                defaultQuery += " ORDER BY T.TERMINALID";
            }

            ps = con.prepareStatement(defaultQuery);
            rs = ps.executeQuery();

            terminalBeanList = new ArrayList<TerminalDataCaptureBean>();

            while (rs.next()) {
                TerminalDataCaptureBean resultBean = new TerminalDataCaptureBean();

                resultBean.setTerminalID(rs.getString("TERMINALID"));
                resultBean.setMerchantID(rs.getString("MERCHANTID"));
                resultBean.setName(rs.getString("TERMINALNAME"));
                resultBean.setSerialNo(rs.getString("SERIALNO"));
                resultBean.setManufacturer(rs.getString("MANUFACTURER"));
                resultBean.setModel(rs.getString("MODELDES"));
                resultBean.setAlloStatus(rs.getString("ALLODES"));
                resultBean.setTerminalStatus(rs.getString("TERMINALSTATUS"));
                resultBean.setTerminalStatusDes(rs.getString("TERMINALDES"));
                resultBean.setMerchantDes(rs.getString("MERCHANTDES"));

                terminalBeanList.add(resultBean);
            }

        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                ps.close();

            } catch (Exception e) {
            }
        }

        return terminalBeanList;
    }

    public List<MerchantSearchBean> searchMerchants(Connection con, MerchantSearchBean searchBean) throws SQLException, Exception {
        //hold the statement to be executed
        PreparedStatement ps = null;
        String defaultQuery = null;
        ResultSet rs = null;

        List<MerchantSearchBean> merSearchList = new ArrayList<MerchantSearchBean>();

        try {
            defaultQuery = "SELECT ML.MERCHANTID,MC.MERCHANTNAME,ML.DESCRIPTION,ML.STATUS,S.DESCRIPTION AS STATUSDES "
                    + "FROM MERCHANTLOCATION ML,MERCHANTCUSTOMER MC,STATUS S "
                    + "WHERE ML.MERCHANTCUSTOMERNO = MC.MERCHANTCUSTOMERNO AND S.STATUSCODE = ML.STATUS "
                    + " AND ML.MERCHANTID NOT IN (SELECT  MERCHANTID FROM COMMONPARAMETER)";

            String condition = "";

            if (!searchBean.getMerchantId().contentEquals("") || searchBean.getMerchantId().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "ML.MERCHANTID LIKE '%" + searchBean.getMerchantId() + "%'";
            }
            if (!searchBean.getMerchantname().contentEquals("") || searchBean.getMerchantname().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "UPPER(MC.MERCHANTNAME) LIKE '%" + searchBean.getMerchantname().toUpperCase() + "%'";
            }
            if (!searchBean.getMerchantLocation().contentEquals("") || searchBean.getMerchantLocation().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "UPPER(ML.DESCRIPTION) LIKE '%" + searchBean.getMerchantLocation().toUpperCase() + "%'";
            }
            if (!searchBean.getMerchantStatus().contentEquals("") || searchBean.getMerchantStatus().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "ML.STATUS = '" + searchBean.getMerchantStatus() + "'";
            }

            if (!condition.equals("")) {
                defaultQuery += "  AND " + condition + " ORDER BY ML.MERCHANTID";
            } else {
                defaultQuery += " ORDER BY ML.MERCHANTID";
            }

            ps = con.prepareStatement(defaultQuery);
            rs = ps.executeQuery();


            while (rs.next()) {
                MerchantSearchBean bean = new MerchantSearchBean();

                bean.setMerchantId(rs.getString("MERCHANTID"));
                bean.setMerchantname(rs.getString("MERCHANTNAME"));
                bean.setMerchantLocation(rs.getString("DESCRIPTION"));
                bean.setMerchantStatus(rs.getString("STATUS"));
                bean.setMerchantStatusDes(rs.getString("STATUSDES"));

                merSearchList.add(bean);

            }

        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                ps.close();

            } catch (Exception e) {
            }
        }

        return merSearchList;
    }

    public List<TerminalDataCaptureBean> getAllTerminalData(Connection con) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            List<TerminalDataCaptureBean> terminalList = new ArrayList<TerminalDataCaptureBean>();

            String query = "SELECT T.TERMINALID,T.TERMINALNAME,T.SERIALNO,T.MANUFACTURER,TM.DESCRIPTION AS MANUFACTDES,"
                    + "T.MODEL,M.DESCRIPTION AS MODELDES,T.INSTALLATIONDATE,T.ACTIVATIONDATE,T.ALLOCATIONSTATUS,ML.DESCRIPTION AS MERCHANTDES,"
                    + "S.DESCRIPTION AS STATUSDES,SS.DESCRIPTION AS TERMNLSTATUSDES,T.TERMINALSTATUS, T.MERCHANTID,T.LASTUPDATEDUSER AS USERR "
                    + "FROM STATUS S,STATUS SS,TERMINALMODLE M,TERMINALMANUFACTURE TM,TERMINAL T "
                    + "LEFT JOIN MERCHANTLOCATION ML ON T.MERCHANTID = ML.MERCHANTID "
                    + "WHERE T.MODEL = M.MODLECODE AND T.ALLOCATIONSTATUS = S.STATUSCODE AND "
                    + "T.MANUFACTURER=TM.MANUFACTURECODE AND T.TERMINALSTATUS = SS.STATUSCODE";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                TerminalDataCaptureBean terminalBean = new TerminalDataCaptureBean();
                terminalBean.setTerminalID(rs.getString("TERMINALID"));
                terminalBean.setMerchantID(rs.getString("MERCHANTID"));
                terminalBean.setMerchantDes(rs.getString("MERCHANTDES"));
                terminalBean.setName(rs.getString("TERMINALNAME"));
                terminalBean.setSerialNo(rs.getString("SERIALNO"));
                terminalBean.setManufacturer(rs.getString("MANUFACTURER"));
                terminalBean.setManufactDes(rs.getString("MANUFACTDES"));
                terminalBean.setModel(rs.getString("MODEL"));
                terminalBean.setModelDes(rs.getString("MODELDES"));
                terminalBean.setActivationDate(this.convertStringDate(rs.getDate("INSTALLATIONDATE")));
                if (rs.getDate("ACTIVATIONDATE") != null) {
                    terminalBean.setActivationDate(this.convertStringDate(rs.getDate("ACTIVATIONDATE")));
                }
//                terminalBean.setInstallationDate(rs.getString("INSTALLATIONDATE"));
//                terminalBean.setActivationDate(rs.getString("ACTIVATIONDATE"));
                terminalBean.setAllocationStatus(rs.getString("ALLOCATIONSTATUS"));
                terminalBean.setAlloStatus(rs.getString("STATUSDES"));
                terminalBean.setTerminalStatus(rs.getString("TERMINALSTATUS"));
                terminalBean.setTerminalStatusDes(rs.getString("TERMNLSTATUSDES"));
                terminalBean.setLastUpdateUser(rs.getString("USERR"));
                terminalList.add(terminalBean);
            }

            return terminalList;
        } catch (Exception ex) {
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

    public int deleteTerminal(Connection con, String terminalID) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM TERMINAL where TERMINALID =?";

            ps = con.prepareStatement(query);
            ps.setString(1, terminalID);

            i = ps.executeUpdate();

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
        return i;
    }

    public int deactivateTerminal(Connection con, String terminalID) throws SQLException, Exception {
        int row = -1;
        PreparedStatement ps = null;

        try {
            String query = "UPDATE TERMINAL SET "
                    + "TERMINALSTATUS = ? "
                    + "WHERE TERMINALID = ?";

            ps = con.prepareStatement(query);
            ps.setString(1, "DEL");
            ps.setString(2, terminalID);

            row = ps.executeUpdate();

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

        return row;
    }

    public String checkTerminalStatus(Connection con, String id) throws SQLException, Exception {
        String status = null;


        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT TERMINALSTATUS FROM TERMINAL WHERE TERMINALID=?";

            ps = con.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                status = rs.getString("TERMINALSTATUS");
            }


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


        return status;
    }

    public Boolean insertTerminal(TerminalDataCaptureBean terminalBean, Connection con) throws SQLException, Exception {
        Boolean success = false;
        PreparedStatement ps0 = null;
        ResultSet rs = null;
        String mid = "";
        PreparedStatement ps = null;

        try {
            String query0 = "select MERCHANTID from COMMONPARAMETER ";

            ps0 = con.prepareStatement(query0);

            rs = ps0.executeQuery();

            while (rs.next()) {
                mid = rs.getString("MERCHANTID");
            }

            String query = "INSERT INTO TERMINAL (TERMINALID,TERMINALNAME,SERIALNO,MANUFACTURER,"
                    + "MODEL,INSTALLATIONDATE,ALLOCATIONSTATUS,TERMINALSTATUS,LASTUPDATEDUSER,CREATEDTIME,MERCHANTID) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";

            ps = con.prepareStatement(query);
            ps.setString(1, terminalBean.getTerminalID());
            ps.setString(2, terminalBean.getName());
            ps.setString(3, terminalBean.getSerialNo());
            ps.setString(4, terminalBean.getManufacturer());
            ps.setString(5, terminalBean.getModel());
            ps.setTimestamp(6, new Timestamp(this.stringTodate(terminalBean.getInstallationDate()).getTime()));
            ps.setString(7, terminalBean.getAllocationStatus());
            ps.setString(8, terminalBean.getTerminalStatus());
            ps.setString(9, terminalBean.getLastUpdateUser());
            ps.setTimestamp(10, SystemDateTime.getSystemDataAndTime());
            ps.setString(11, mid);


            int i = ps.executeUpdate();
            if (i == 1) {
                success = true;
            }


        } catch (Exception ex) {
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

    public Boolean insertTerminalData(TerminalDataCaptureBean terminalBean, Connection con) throws SQLException, Exception {
        Boolean success = false;
        PreparedStatement ps = null;

        try {
            String query = "UPDATE TERMINAL SET MERCHANTID=?,CURRENCYCODE=?,MCC=?,"
                    + " LASTUPDATEDUSER=?,LASTUPDATEDTIME=SYSDATE,ALLOCATIONSTATUS=? WHERE TERMINALID=? ";

            ps = con.prepareStatement(query);

            ps.setString(1, terminalBean.getMerchantID());
            ps.setString(2, zeroPadding(terminalBean.getCurrency()));
            ps.setString(3, terminalBean.getMcc());
            ps.setString(4, terminalBean.getLastUpdateUser());
            ps.setString(5, terminalBean.getAlloStatus());
            ps.setString(6, terminalBean.getTerminalID());

            if (ps.executeUpdate() == 1) {
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

    public boolean insertToTxnType(Connection CMSCon, String terminalId, String txnTypeCode, String merchantId) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = CMSCon.prepareStatement("INSERT INTO TERMINALTXN(TERMINALID,TXNCODE,MERCHANTID) VALUES(?,?,?) ");
            insertStat.setString(1, terminalId);
            insertStat.setString(2, txnTypeCode);
            insertStat.setString(3, merchantId);

            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public boolean insertOnlineToTxnType(Connection ConToOnline, String terminalId, String txnTypeCode, String merchantId) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = ConToOnline.prepareStatement("INSERT INTO ECMS_ONLINE_TERMINAL_TXN(TID,TXNTYPECODE,MID) VALUES(?,?,?) ");
            insertStat.setString(1, terminalId);
            insertStat.setInt(2, Integer.parseInt(txnTypeCode));
            insertStat.setString(3, merchantId);

            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public Boolean insertTerminalOnlineDB(Connection con, String terminalId, String merchantId) throws SQLException, Exception {
        Boolean success = false;
        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO ECMS_ONLINE_TERMINAL (TID,MID,STATUS) VALUES(?,?,?)";

            ps = con.prepareStatement(query);
            ps.setString(1, terminalId);
            ps.setString(2, merchantId);
            ps.setString(3, "2");

            ps.executeUpdate();

            success = true;

        } catch (Exception ex) {
            success = false;
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

    public Boolean insertOnlineTerminal(Connection con, TerminalDataCaptureBean terminalBean) throws SQLException, Exception {
        Boolean success = false;
        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO ECMS_ONLINE_TERMINAL (TID,MID,CURRENCYCODE,MCC,"
                    + " TNAME,RISKPROFILECODE,STATUS,LASTUPDATEUSER,CREATETIME,LASTUPDATETIME)"
                    + " VALUES(?,?,?,?,?,?,?,?,SYSDATE,SYSDATE)";

            ps = con.prepareStatement(query);

            ps.setString(1, terminalBean.getTerminalID());
            ps.setString(2, terminalBean.getMerchantID());
            ps.setString(3, zeroPadding(terminalBean.getCurrency()));
            ps.setString(4, terminalBean.getMcc());
            ps.setString(5, terminalBean.getName());
            ps.setString(6, terminalBean.getRiskProfileCode());
            ps.setInt(7, 2);
            ps.setString(8, terminalBean.getLastUpdateUser());

            ps.executeUpdate();

            success = true;

        } catch (SQLException ex) {
            success = false;
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

    public int updateTerminal(TerminalDataCaptureBean terminalBean, String[] transactions, Connection con) throws SQLException, Exception {
        int row = -1;
        PreparedStatement ps = null;

        try {
            String query = "UPDATE TERMINAL SET "
                    + "TERMINALNAME=?,SERIALNO=?,MANUFACTURER=?,"
                    + "MODEL=?,INSTALLATIONDATE=?,LASTUPDATEDUSER=?,CREATEDTIME=? "
                    + "WHERE TERMINALID=? ";

            //,ACTIVATIONDATE=?----
            ps = con.prepareStatement(query);


            ps.setString(1, terminalBean.getName());
            ps.setString(2, terminalBean.getSerialNo());
            ps.setString(3, terminalBean.getManufacturer());
            ps.setString(4, terminalBean.getModel());
            ps.setTimestamp(5, new Timestamp(this.stringTodate(terminalBean.getInstallationDate()).getTime()));
            //ps.setTimestamp(6, new Timestamp(this.stringTodate(terminalBean.getActivationDate()).getTime()));
            ps.setString(6, terminalBean.getLastUpdateUser());
            ps.setTimestamp(7, SystemDateTime.getSystemDataAndTime());
            ps.setString(8, terminalBean.getTerminalID());

            row = ps.executeUpdate();

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

        return row;
    }

    /**
     * to update transactions assigned to terminals
     * @param transactions
     * @param con
     * @param tid
     * @return
     * @throws Exception 
     */
    public int updateTerminalTxn(String[] transactions, Connection con, String tid) throws Exception {
        int k;

        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM TERMINALTXN where TERMINALID =?");

            ps.setString(1, tid);

            k = ps.executeUpdate();

            for (int i = 0; i < transactions.length; i++) {

                ps = con.prepareStatement("INSERT INTO TERMINALTXN "
                        + "(TERMINALID,TXNCODE ) "
                        + " VALUES (?,?) ");

                ps.setString(1, tid);
                ps.setString(2, transactions[i]);

                int j = 0;
                j = ps.executeUpdate();
                if (j == 0) {
                    throw new Exception();
                }
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                ps.close();

            } catch (Exception e) {
                throw e;
            }
        }
        return k;

    }

    public int updateTerminalTxnOnline(String[] transactions, List<TerminalDataCaptureBean> assignedBean, Connection con, String tid,String mid) throws Exception {
        int k;

        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM ECMS_ONLINE_TERMINAL_TXN WHERE TID =?");

            ps.setString(1, tid);

            k = ps.executeUpdate();

            for (int i = 0; i < transactions.length; i++) {

                ps = con.prepareStatement("INSERT INTO ECMS_ONLINE_TERMINAL_TXN "
                        + "(TID,TXNTYPECODE,MID ) "
                        + " VALUES (?,?,?) ");


                System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK " + assignedBean.get(i).getOnlineTxnCode());
                ps.setString(1, tid);
                ps.setString(2, assignedBean.get(i).getOnlineTxnCode());
                ps.setString(3, mid);

                int j = 0;
                j = ps.executeUpdate();
                if (j == 0) {
                    throw new Exception();
                }
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                ps.close();

            } catch (Exception e) {
                throw e;
            }
        }
        return k;

    }

    public Boolean isTerminalAllocated(Connection con, String terminalId) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllByCatStat = null;
        Boolean status = false;
        try {
            getAllByCatStat = con.prepareStatement("SELECT COUNT (*) AS COUNT FROM TERMINAL WHERE TERMINALID = ? AND ALLOCATIONSTATUS = ?");
            getAllByCatStat.setString(1, terminalId);
            getAllByCatStat.setString(2, StatusVarList.ALLOCATION_NO);
            rs = getAllByCatStat.executeQuery();

            if (rs.next()) {
                int count = Integer.parseInt(rs.getString("COUNT").trim());

                if (count > 0) {
                    status = true;
                } else {
                    status = false;
                }
            } else {
                status = false;
            }
        } catch (SQLException ex) {
            status = false;
            throw ex;

        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return status;
    }

    public Boolean isTerminalActive(Connection con, String terminalId) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllByCatStat = null;
        Boolean status = false;
        try {
            getAllByCatStat = con.prepareStatement("SELECT COUNT (*) AS COUNT FROM TERMINAL WHERE TERMINALID = ?"
                    + "  AND TERMINALSTATUS = ? ");
            getAllByCatStat.setString(1, terminalId);
            getAllByCatStat.setString(2, StatusVarList.ACTIVE_STATUS);


            rs = getAllByCatStat.executeQuery();

            if (rs.next()) {
                int count = Integer.parseInt(rs.getString("COUNT").trim());

                if (count > 0) {
                    status = true;
                } else {
                    status = false;
                }
            } else {
                status = false;
            }
        } catch (SQLException ex) {
            status = false;
            throw ex;

        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return status;
    }

    public Boolean isMerchantActive(Connection con, String merchantId) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllByCatStat = null;
        Boolean status = false;
        try {
            getAllByCatStat = con.prepareStatement("SELECT COUNT (*) AS COUNT FROM MERCHANTLOCATION WHERE MERCHANTID = ? AND STATUS = ?");
            getAllByCatStat.setString(1, merchantId);
            getAllByCatStat.setString(2, StatusVarList.ACTIVE_STATUS);
            rs = getAllByCatStat.executeQuery();

            if (rs.next()) {
                int count = Integer.parseInt(rs.getString("COUNT").trim());

                if (count > 0) {
                    status = true;
                } else {
                    status = false;
                }
            } else {
                status = false;
            }
        } catch (SQLException ex) {
            status = false;
            throw ex;

        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return status;
    }

    public Boolean isTerminalAvailableOnlineDB(Connection con, String terminalId) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllByCatStat = null;
        Boolean status = false;
        try {
            getAllByCatStat = con.prepareStatement("SELECT COUNT (*) AS COUNT FROM ECMS_ONLINE_TERMINAL WHERE TID = ? ");
            getAllByCatStat.setString(1, terminalId);
            rs = getAllByCatStat.executeQuery();

            if (rs.next()) {
                int count = Integer.parseInt(rs.getString("COUNT").trim());

                if (count > 0) {
                    status = true;
                } else {
                    status = false;
                }
            } else {
                status = false;
            }
        } catch (SQLException ex) {
            status = false;
            throw ex;

        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return status;
    }

    public Boolean updateTerminalTable(Connection con, String terminalId, String merchantId) throws SQLException, Exception {
        PreparedStatement ps = null;
        Boolean status = false;

        try {
            String query = "UPDATE TERMINAL SET ALLOCATIONSTATUS = ?,MERCHANTID = ? WHERE TERMINALID = ?";

            ps = con.prepareStatement(query);


            ps.setString(1, StatusVarList.ALLOCATION_YES);
            ps.setString(2, merchantId);
            ps.setString(3, terminalId);

            ps.executeUpdate();
            status = true;
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

        return status;
    }

    public Boolean updateTerminalTableOnlineTable(Connection con, String terminalId, String merchantId) throws SQLException, Exception {
        PreparedStatement ps = null;
        Boolean status = false;

        try {
            String query = "UPDATE ECMS_ONLINE_TERMINAL SET MID = ? WHERE TID = ?";

            ps = con.prepareStatement(query);


            ps.setString(1, merchantId);
            ps.setString(2, terminalId);

            ps.executeUpdate();
            status = true;
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

        return status;
    }

    public Boolean updateOnlineTerminal(Connection con, TerminalDataCaptureBean terminalBean) throws SQLException, Exception {
        PreparedStatement ps = null;
        Boolean status = false;

        try {
            String query = "UPDATE ECMS_ONLINE_TERMINAL SET MID = ?, CURRENCYCODE =?,"
                    + " MCC=?,STATUS=?,LASTUPDATEUSER=?,LASTUPDATETIME=SYSDATE WHERE TID = ?";

            ps = con.prepareStatement(query);



            ps.setString(1, terminalBean.getMerchantID());
            ps.setString(2, zeroPadding(terminalBean.getCurrency()));
            ps.setString(3, terminalBean.getMcc());
            ps.setInt(4, 2);
            ps.setString(5, terminalBean.getLastUpdateUser());
            ps.setString(6, terminalBean.getTerminalID());

            ps.executeUpdate();
            status = true;
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

        return status;
    }

    public Boolean updateTerminalStatus(Connection con, String terminalId, String terStatus) throws SQLException, Exception {
        PreparedStatement ps = null;
        Boolean status = false;

        try {
            String query = "UPDATE TERMINAL SET TERMINALSTATUS = ? WHERE TERMINALID = ?";

            ps = con.prepareStatement(query);


            ps.setString(1, terStatus);
            ps.setString(2, terminalId);

            ps.executeUpdate();
            status = true;
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

        return status;
    }

    public Boolean updateTerminalStatusToActivate(Connection con, String terminalId, String terStatus) throws SQLException, Exception {
        PreparedStatement ps = null;
        Boolean status = false;

        try {
            String query = "UPDATE TERMINAL SET TERMINALSTATUS = ?, ACTIVATIONDATE = SYSDATE WHERE TERMINALID = ?";

            ps = con.prepareStatement(query);


            ps.setString(1, terStatus);
            ps.setString(2, terminalId);

            ps.executeUpdate();
            status = true;
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

        return status;
    }

    public Boolean updateterminalStatusOnlineDb(Connection con, String terminalId, String terStatus) throws SQLException, Exception {
        PreparedStatement ps = null;
        Boolean status = false;

        try {
            String query = "UPDATE ECMS_ONLINE_TERMINAL SET STATUS = ? WHERE TID = ?";

            ps = con.prepareStatement(query);


            ps.setString(1, terStatus);
            ps.setString(2, terminalId);

            ps.executeUpdate();
            status = true;
        } catch (SQLException ex) {

            throw ex;
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }

        return status;
    }

    /**
     * 
     * @param con
     * @param terminalId
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public Boolean updateterminalAllocationStatus(Connection con, String terminalId, String merchantId) throws SQLException, Exception {
        PreparedStatement ps = null;
        Boolean status = false;

        try {
            String query = "UPDATE TERMINAL SET ALLOCATIONSTATUS = ?,MERCHANTID = ? WHERE TERMINALID = ?";

            ps = con.prepareStatement(query);


            ps.setString(1, StatusVarList.ALLOCATION_NO);
            ps.setString(2, merchantId);
            ps.setString(3, terminalId);

            ps.executeUpdate();
            status = true;
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

        return status;
    }

    public Boolean deleteTerminalTxn(Connection con, String terminalId) throws SQLException, Exception {
        PreparedStatement ps = null;
        Boolean status = false;

        try {
            String query = "DELETE FROM TERMINALTXN WHERE TERMINALID =?";

            ps = con.prepareStatement(query);

            ps.setString(1, terminalId);

            ps.executeUpdate();
            status = true;
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

        return status;
    }

    public Boolean deleteTerminalOnlineTxn(Connection con, String terminalId) throws SQLException, Exception {
        PreparedStatement ps = null;
        Boolean status = false;

        try {
            String query = "DELETE FROM ECMS_ONLINE_TERMINAL_TXN WHERE TID = ? ";

            ps = con.prepareStatement(query);

            ps.setString(1, terminalId);

            ps.executeUpdate();
            status = true;
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

        return status;
    }

    public Date stringTodate(String date) throws ParseException {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date convertedDate = dateFormat.parse(dateString);


        return convertedDate;


    }

    public List<MerchantCategoryBean> getMccList(Connection CMSCon, String merchantId) throws Exception {

        List<MerchantCategoryBean> mccList = new ArrayList<MerchantCategoryBean>();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            String query = "SELECT MC.MCCCODE, MC.DESCRIPTION FROM MERCHANTLOCATIONMCC ML,MCC MC "
                    + " WHERE MC.MCCCODE=ML.MCC AND ML.MERCHANTID = ? ";


            ps = CMSCon.prepareStatement(query);
            ps.setString(1, merchantId);
            rs = ps.executeQuery();

            while (rs.next()) {
                MerchantCategoryBean mccBean = new MerchantCategoryBean();

                mccBean.setmCCCode(rs.getString("MCCCODE"));
                mccBean.setDescription(rs.getString("DESCRIPTION"));

                mccList.add(mccBean);
            }

            return mccList;
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

    public List<CurrencyBean> getCurrencyList(Connection CMSCon, String merchantId) throws Exception {

        List<CurrencyBean> currencyList = new ArrayList<CurrencyBean>();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            String query = "SELECT C.CURRENCYNUMCODE, C.DESCRIPTION FROM MERCHANTLOCATIONCURRENCY MC,CURRENCY C "
                    + " WHERE C.CURRENCYNUMCODE=MC.CURRENCYCODE AND MC.MERCHANTID = ? ";


            ps = CMSCon.prepareStatement(query);
            ps.setString(1, merchantId);
            rs = ps.executeQuery();

            while (rs.next()) {
                CurrencyBean currencyBean = new CurrencyBean();

                currencyBean.setCurrencyCode(Integer.toString(rs.getInt("CURRENCYNUMCODE")));
                currencyBean.setCurrencyDes(rs.getString("DESCRIPTION"));

                currencyList.add(currencyBean);
            }

            return currencyList;
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

    public List<TypeMgtBean> getTxnList(Connection CMSCon, String merchantId) throws Exception {

        List<TypeMgtBean> txnTypeList = new ArrayList<TypeMgtBean>();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            String query = "SELECT T.TRANSACTIONCODE, T.DESCRIPTION FROM MERCHANTLOCATIONTRANSACTION MT,TRANSACTIONTYPE T "
                    + " WHERE T.TRANSACTIONCODE=MT.TRANSACTIONCODE AND MT.MERCHANTID = ? ";


            ps = CMSCon.prepareStatement(query);
            ps.setString(1, merchantId);
            rs = ps.executeQuery();

            while (rs.next()) {
                TypeMgtBean txnTypeBean = new TypeMgtBean();

                txnTypeBean.setTransactionTypeCode(rs.getString("TRANSACTIONCODE"));
                txnTypeBean.setDescription(rs.getString("DESCRIPTION"));

                txnTypeList.add(txnTypeBean);
            }

            return txnTypeList;
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

    public String getOnlineTxnCode(Connection CMSCon, String txnCode) throws Exception {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String onlineTxnCode = null;

        try {

            String query = "SELECT ONLINETXNTYPE FROM TRANSACTIONTYPE "
                    + " WHERE TRANSACTIONCODE = ? ";

            ps = CMSCon.prepareStatement(query);
            ps.setString(1, txnCode);
            rs = ps.executeQuery();

            while (rs.next()) {
                onlineTxnCode = rs.getString("ONLINETXNTYPE");
            }


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
        return onlineTxnCode;
    }

    public List<RiskProfileBean> getTerminalRiskProfile(Connection CMSCon, String merchantId) throws Exception {
        ResultSet rs = null;
        PreparedStatement ps = null;
        List<RiskProfileBean> riskProfileList = new ArrayList<RiskProfileBean>();
        try {

            ps = CMSCon.prepareStatement("SELECT DISTINCT R.RISKPROFILECODE, R.DESCRIPTION "
                    + " FROM RISKPROFILE R,MERCHANTLOCATION M"
                    + " WHERE (SELECT ML.RISKPROFILE FROM MERCHANTLOCATION ML WHERE ML.MERCHANTID =? )= R.MERCHANTPROFILECODE");

            ps.setString(1, merchantId);
            rs = ps.executeQuery();

            while (rs.next()) {

                RiskProfileBean bean = new RiskProfileBean();

                bean.setRiskProfCode(rs.getString("RISKPROFILECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                riskProfileList.add(bean);

            }

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
        return riskProfileList;
    }

    public boolean isTransactionsSettled(Connection CMSConOnline, String terminalId, String merchantId, String txnCode) throws Exception {
        ResultSet rs = null;
        PreparedStatement ps = null;
        boolean isSetteled = true;
        try {
            ps = CMSConOnline.prepareStatement("SELECT T.TXNID FROM ECMS_ONLINE_TRANSACTION T "
                    + " WHERE T.MID=? AND T.TID=? AND T.TXNTYPECODE=? AND T.STATUS <> ?");

            ps.setString(1, merchantId);
            ps.setString(2, terminalId);
            ps.setInt(3, Integer.parseInt(txnCode));
            ps.setInt(4, StatusVarList.ONLINE_TXN_SETTLED_STATUS);

            rs = ps.executeQuery();

            while (rs.next()) {
                isSetteled = false;

            }

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
        return isSetteled;
    }

    public HashMap getFinancialTxnIds(Connection CMSCon) throws Exception {
        ResultSet rs = null;
        PreparedStatement ps = null;
        HashMap<String, String> map = new HashMap<String, String>();

        try {
            ps = CMSCon.prepareStatement("SELECT ONLINETXNTYPE FROM TRANSACTIONTYPE"
                    + " WHERE FINANCIALSTATUS= ?");

            ps.setString(1, StatusVarList.YESSTATUS);
            rs = ps.executeQuery();

            while (rs.next()) {

                map.put(rs.getString("ONLINETXNTYPE"), rs.getString("ONLINETXNTYPE"));

            }

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
        return map;
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

    private String convertStringDate(java.sql.Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return (dateFormat.format(date));
    }
    
    public List<TerminalDataCaptureBean> getTerminalDataFromOnlineTable(Connection con, String terminalId) throws SQLException, Exception {
        PreparedStatement ps = null;
       // Boolean status = false;
        List<TerminalDataCaptureBean> list = new ArrayList<TerminalDataCaptureBean>();
        try {
            String query = "SELECT TERMINALID,TERMINALSTATUS,ACTIVATIONDATE,LASTUPDATEDUSER,ALLOCATIONSTATUS,MERCHANTID FROM TERMINAL WHERE TERMINALID = ?";
            ResultSet rs = null;
            ps = con.prepareStatement(query);

            ps.setString(1, terminalId);

            rs = ps.executeQuery();

            while (rs.next()) {
                TerminalDataCaptureBean bean = new TerminalDataCaptureBean();
                bean.setTerminalID(rs.getString("TERMINALSTATUS"));
                bean.setTerminalStatus(rs.getString("TERMINALSTATUS"));
                bean.setActivationDate(rs.getString("ACTIVATIONDATE"));
                bean.setLastUpdateUser("LASTUPDATEDUSER");
                bean.setAllocationStatus(rs.getString("ALLOCATIONSTATUS"));
                bean.setMerchantID(rs.getString("MERCHANTID"));
                list.add(bean);
            }

            return list;

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

        //return status;
    }    
    
}
