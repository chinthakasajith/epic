/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.keymailer.persistance;

import com.epic.cms.switchcontrol.keymailer.bean.KeyBean;
import com.epic.cms.switchcontrol.keymailer.bean.TerminalKeyMailerBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nalin
 */
public class TerminalKeyMailerPersistance {

    private List<TerminalKeyMailerBean> searchList = null;

    public List<TerminalKeyMailerBean> searchTerminal(Connection con, TerminalKeyMailerBean keyMailerBean) throws SQLException, Exception {
        //hold the statement to be executed
        PreparedStatement ps = null;
        String defaultQuery = null;
        ResultSet rs = null;

        try {
            defaultQuery = " SELECT T.TERMINALID,T.TERMINALNAME,T.SERIALNO,T.MANUFACTURER,T.MODEL,M.DESCRIPTION AS MODELDES,"
                    + " T.ALLOCATIONSTATUS,T.TERMINALSTATUS,S.DESCRIPTION AS ALLODES,"
                    + " SS.DESCRIPTION AS TERMINALDES, T.MERCHANTID,ML.DESCRIPTION AS MERCHANTDES "
                    + " FROM STATUS S,STATUS SS,TERMINALMODLE M,TERMINAL T "
                    + " LEFT JOIN MERCHANTLOCATION ML ON T.MERCHANTID = ML.MERCHANTID "
                    + " WHERE T.MODEL = M.MODLECODE AND T.ALLOCATIONSTATUS = S.STATUSCODE AND"
                    + " T.TERMINALSTATUS = SS.STATUSCODE"
                    + " AND T.ALLOCATIONSTATUS = ? AND TERMINALSTATUS= ? ";


            String condition = "";

            if (!keyMailerBean.getTerminalID().contentEquals("") || keyMailerBean.getTerminalID().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "T.TERMINALID LIKE '%" + keyMailerBean.getTerminalID() + "%'";
            }
            if (!keyMailerBean.getMerchantID().contentEquals("") || keyMailerBean.getMerchantID().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "T.MERCHANTID LIKE '%" + keyMailerBean.getMerchantID() + "%'";
            }
            if (!keyMailerBean.getTerminalName().contentEquals("") || keyMailerBean.getTerminalName().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "UPPER(T.TERMINALNAME) LIKE '%" + keyMailerBean.getTerminalName().toUpperCase() + "%'";
            }
            if (!keyMailerBean.getSerialNo().contentEquals("") || keyMailerBean.getSerialNo().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "T.SERIALNO = '" + keyMailerBean.getSerialNo() + "'";
            }
            if (!keyMailerBean.getModel().contentEquals("") || keyMailerBean.getModel().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "T.MODEL = '" + keyMailerBean.getModel() + "'";
            }


            if (!condition.equals("")) {
                defaultQuery += "  AND " + condition + " ORDER BY T.TERMINALID";
            } else {
                defaultQuery += " ORDER BY T.TERMINALID";
            }

            ps = con.prepareStatement(defaultQuery);

            ps.setString(1, StatusVarList.ALLOCATION_YES);
            ps.setString(2, StatusVarList.ACTIVE_STATUS);
            rs = ps.executeQuery();

            searchList = new ArrayList<TerminalKeyMailerBean>();

            while (rs.next()) {
                TerminalKeyMailerBean resultBean = new TerminalKeyMailerBean();

                resultBean.setTerminalID(rs.getString("TERMINALID"));
                resultBean.setMerchantID(rs.getString("MERCHANTID"));
                resultBean.setTerminalName(rs.getString("TERMINALNAME"));
                resultBean.setSerialNo(rs.getString("SERIALNO"));
                resultBean.setManufacturer(rs.getString("MANUFACTURER"));
                resultBean.setModel(rs.getString("MODELDES"));
                resultBean.setMerchantDes(rs.getString("MERCHANTDES"));

                searchList.add(resultBean);
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

        return searchList;
    }

    public TerminalKeyMailerBean verifyTerminalData(Connection con, String terminalID) throws SQLException, Exception {

        TerminalKeyMailerBean terminalBean = new TerminalKeyMailerBean();
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            String query = "SELECT DISTINCT T.TERMINALID,T.TERMINALNAME,T.SERIALNO,T.MANUFACTURER,TM.DESCRIPTION AS MANUFACTDES,"
                    + " T.MODEL,M.DESCRIPTION AS MODELDES,T.INSTALLATIONDATE,T.ACTIVATIONDATE,T.ALLOCATIONSTATUS,"
                    + " ML.DESCRIPTION AS MERCHANTDES,"
                    + " S.DESCRIPTION AS STATUSDES,SS.DESCRIPTION AS TERMNLSTATUSDES,T.TERMINALSTATUS,T.MERCHANTID,"
                    + " T.CURRENCYCODE,T.MCC ,"
                    //                    + " C.DESCRIPTION AS CURRENCYDES, "
                    + " MCAT.DESCRIPTION AS MCATDESCRIPTION"
                    + " FROM STATUS S,STATUS SS,TERMINALMODLE M,TERMINALMANUFACTURE TM,CURRENCY C,TERMINAL T, MCC MCAT,"
                    + " MERCHANTLOCATION ML"
                    + " WHERE T.MODEL = M.MODLECODE AND T.ALLOCATIONSTATUS = S.STATUSCODE AND "
                    + " T.MANUFACTURER=TM.MANUFACTURECODE AND T.TERMINALSTATUS = SS.STATUSCODE"
                    + " AND MCAT.MCCCODE= T.MCC AND T.MERCHANTID = ML.MERCHANTID AND T.TERMINALID=? ";


            ps = con.prepareStatement(query);
            ps.setString(1, terminalID);
            rs = ps.executeQuery();

            while (rs.next()) {
                terminalBean.setTerminalID(rs.getString("TERMINALID"));
                terminalBean.setMerchantID(rs.getString("MERCHANTID"));
                terminalBean.setMerchantDes(rs.getString("MERCHANTDES"));
                terminalBean.setTerminalName(rs.getString("TERMINALNAME"));
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
                terminalBean.setMccDes(rs.getString("MCATDESCRIPTION"));
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

    public KeyBean getTMKKey(Connection conOnline, String terminalID) throws Exception {

        KeyBean keyBean = new KeyBean();
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = conOnline.prepareStatement("SELECT T.TMK,T.TMK_KVC FROM ECMS_ONLINE_TERMINAL T WHERE TID = ? ");

            ps.setString(1, terminalID);
            rs = ps.executeQuery();

            while (rs.next()) {

                keyBean.setTmk(rs.getString("TMK"));
                keyBean.setTmkKVC(rs.getString("TMK_KVC"));
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
        return keyBean;
    }

    public boolean updateTMKKey(Connection conOnline, KeyBean keyBean) throws Exception {
        boolean isSuccess = false;
        PreparedStatement ps = null;
        try {
            ps = conOnline.prepareStatement("UPDATE ECMS_ONLINE_TERMINAL"
                    + " SET TMK =?,TMK_KVC=?,LASTUPDATEUSER=?,LASTUPDATETIME=SYSDATE WHERE TID=?");

            ps.setString(1, keyBean.getTmk());
            ps.setString(2, keyBean.getTmkKVC());
            ps.setString(3, keyBean.getLastUpdatedUser());
            ps.setString(4, keyBean.gettId());
            
            ps.executeUpdate();
            isSuccess = true;
            
        } catch (SQLException ex) {
            throw ex;
        } catch (Exception e) {
            throw e;
        } finally {
            ps.close();
        }

        return isSuccess;
    }
    
     public boolean updateTPKKey(Connection conOnline, KeyBean keyBean) throws Exception {
        boolean isSuccess = false;
        PreparedStatement ps = null;
        try {
            ps = conOnline.prepareStatement("UPDATE ECMS_ONLINE_TERMINAL"
                    + " SET TPK =?,TPK_KVC=?,LASTUPDATEUSER=?,LASTUPDATETIME=SYSDATE WHERE TID=?");

            ps.setString(1, keyBean.getELMK_KEY());
            ps.setString(2, keyBean.getTpkKVC());
            ps.setString(3, keyBean.getLastUpdatedUser());
            ps.setString(4, keyBean.gettId());
            
            ps.executeUpdate();
            isSuccess = true;
            
        } catch (SQLException ex) {
            throw ex;
        } catch (Exception e) {
            throw e;
        } finally {
            ps.close();
        }

        return isSuccess;
    }

    private String convertStringDate(java.sql.Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return (dateFormat.format(date));
    }
}
