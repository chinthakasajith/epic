/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.payment.persistance;

import com.epic.cms.backoffice.payment.bean.ChequeBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author badrika
 */
public class ChequeRealisePersistance {

    private HashMap<String, String> statusList;
    private List<ChequeBean> searchList;

    public HashMap<String, String> getStatusList(Connection con) throws SQLException, Exception {

        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(" select STATUSCODE, DESCRIPTION from STATUS where STATUSCATEGORY = 'CHEQ'");

            rs = ps.executeQuery();
            statusList = new HashMap<String, String>();

            while (rs.next()) {

                statusList.put(rs.getString("STATUSCODE"), rs.getString("DESCRIPTION"));

            }

        } catch (SQLException sqex) {
            throw sqex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();

        }

        return statusList;
    }

    public List<ChequeBean> searchCheque(Connection con, ChequeBean searchBean) throws Exception {



        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query



        try {

            strSqlBasic = "SELECT P.CHEQUENUMBER, P.CHEQUEBANK, SUM(P.AMOUNT) as amount, max(P.PAYMENTDATE) as paydate, P.CURRENCYTYPE, P.CHEQUESTATUS, "
                    + "C.DESCRIPTION as CDES, S.DESCRIPTION as SDES, B.BANKNAME "
                    + "from PAYMENT P, CURRENCY C, STATUS S, BANK B "
                    + "where P.CURRENCYTYPE = C.CURRENCYNUMCODE and P.CHEQUESTATUS = S.STATUSCODE and P.CHEQUEBANK = B.BANKCODE ";

            String condition = "";


            if (!searchBean.getCheqNum().equals("") || searchBean.getCheqNum().length() > 0 ) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "P.CHEQUENUMBER = '" + searchBean.getCheqNum() + "'";
            }

            if (!searchBean.getCheqBank().equals("") || searchBean.getCheqBank().length() > 0 ) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "P.CHEQUEBANK = '" + searchBean.getCheqBank() + "'";
            }

            //date convertions
            if (!searchBean.getPayDateFrom().equals("") && !searchBean.getPayDateTo().equals("")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "P.PAYMENTDATE >= to_Date('" + this.stringTodate(searchBean.getPayDateFrom()) + "','yy-mm-dd') AND P.PAYMENTDATE <= to_Date('" + this.stringTodate(searchBean.getPayDateTo()) + "','yy-mm-dd')";
            }

            if (!searchBean.getPayDateFrom().equals("")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "P.PAYMENTDATE >= to_Date('" + this.stringTodate(searchBean.getPayDateFrom()) + "','yy-mm-dd')";
            }
            if (!searchBean.getPayDateTo().contentEquals("")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "P.PAYMENTDATE <= to_Date('" + this.stringTodate(searchBean.getPayDateTo()) + "','yy-mm-dd')";
            }

            if (!searchBean.getCurType().equals("") || searchBean.getCurType().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "P.CURRENCYTYPE = '" + searchBean.getCurType() + "'";
            }

            if (!searchBean.getAmount().equals("") || searchBean.getAmount().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "P.AMOUNT = '" + searchBean.getAmount() + "'";
            }

            if (!searchBean.getStatus().equals("") || searchBean.getStatus().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "P.CHEQUESTATUS = '" + searchBean.getStatus() + "'";
            }

            if (!condition.equals("")) {
                strSqlBasic += " AND " + condition + " AND P.PAYMENTTYPE = 'CHEQUE' AND P.PAYMENTSTATUS = 'PCOM' group by P.CHEQUENUMBER ,P.CHEQUEBANK,"
                        + "P.CURRENCYTYPE,P.CHEQUESTATUS,C.DESCRIPTION,S.DESCRIPTION,B.BANKNAME ORDER BY paydate desc ";
            } else {
                strSqlBasic += " AND P.PAYMENTTYPE = 'CHEQUE' AND P.PAYMENTSTATUS = 'PCOM' group by P.CHEQUENUMBER ,P.CHEQUEBANK,"
                        + "P.CURRENCYTYPE,P.CHEQUESTATUS,C.DESCRIPTION,S.DESCRIPTION,B.BANKNAME ORDER BY paydate desc ";
            }

            stmt = con.prepareStatement(strSqlBasic);
            System.out.println(strSqlBasic);

            rs = stmt.executeQuery();

            searchList = new ArrayList<ChequeBean>();

            while (rs.next()) {

                ChequeBean bean = new ChequeBean();

                bean.setCheqNum(rs.getString("CHEQUENUMBER"));
                bean.setCheqBank(rs.getString("CHEQUEBANK"));
                bean.setCheqBankName(rs.getString("BANKNAME"));
                bean.setPayDate(rs.getString("paydate"));
                bean.setCurType(rs.getString("CURRENCYTYPE"));
                bean.setCurTypeDes(rs.getString("CDES"));
                bean.setAmount(rs.getString("amount"));
                bean.setStatus(rs.getString("CHEQUESTATUS"));
                bean.setStatusDes(rs.getString("SDES"));

                searchList.add(bean);
            }

        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
                throw e;
            }
        }

        return searchList;
    }

    private String stringTodate(String date) throws ParseException {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        Date convertedDate = dateFormat.parse(dateString);

        return (dateFormat.format(convertedDate));
    }
    
    //private int chequeRealise(String checnum){
     public int chequeRealiseOrReturn(Connection con, String checnum, String rtOrRe) throws Exception {
        PreparedStatement ps = null;
        int i = -1;
        try {
            ps = con.prepareStatement("UPDATE PAYMENT SET CHEQUESTATUS = ?, RETURNORREALIZEDDATE = SYSDATE WHERE CHEQUENUMBER = ? ");

            if(rtOrRe.equals("realise")){
                ps.setString(1, "CQRL");
            }else if(rtOrRe.equals("return")){
                ps.setString(1, "CQRT");
            }
            
            ps.setString(2, checnum);

            i = ps.executeUpdate();
            
        } catch (SQLException ex) {
            throw ex;
        } finally {
            ps.close();
        }
        return i;
    }
    
    //
}
