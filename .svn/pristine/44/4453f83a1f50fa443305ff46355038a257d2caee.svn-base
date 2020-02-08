/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.installementpayment.persistance;

import com.epic.cms.application.common.persistance.StatusPersistence;
import com.epic.cms.backoffice.installementpayment.bean.BTPaymentConfirmBean;
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
 * @author sajith_g
 */
public class BTPaymentConfirmPersistence {

    private ArrayList<BTPaymentConfirmBean> requestList;
    private BTPaymentConfirmBean viewBean;

    public List<BTPaymentConfirmBean> searchRequests(Connection con, BTPaymentConfirmBean filledBean) throws SQLException, Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;

        requestList = new ArrayList<BTPaymentConfirmBean>();

        try {

            String query = "SELECT A.DUEDATE,B.BANKNAME,A.OTHERBANKNAMEONCARD,A.OTHERBANKCARDNUMBER,A.OTHERBANKCARDEXPIRY,A.REQUESTID,A.CARDNUMBER, A.TRANSFERAMOUNT, A.STATUS as rstatus, A.PAYMENTPLAN, A.REQUESTEDUSER, A.LASTUPDATEDUSER, A.LASTUPDATEDTIME, "
                    + "A.INSTALLMENTAMOUNT, A.REMARKS, A.PAYMENTPLAN,  p.description as pdes, p.duration,p.interestrate "
                    + "FROM BALANCETRASFERREQUEST A,PAYMENTPLAN P,  userlevel ul1, userlevel ul2, userrole ur1, userrole ur2, systemuser su1,systemuser su2,BANK B "
                    + "WHERE A.STATUS = ? and A.PAYMENTPLAN = P.PAYMENTPLANCODE "
                    + "and B.BANKCODE=A.BANKCODE "
                    + "and A.REQUESTEDUSER != ? and ul1.levelid = ur1.levelid and ur1.userrole = su1.userrolecode and su1.username = A.REQUESTEDUSER "
                    + "and ul2.levelid = ur2.levelid and ur2.userrole = su2.userrolecode and su2.username = ? "
                    + "and (ul1.levelid <= ul2.levelid or ur2.userrole = su1.dualauthuserrole) ";

            String condition = "";

            if (!filledBean.getCardno().equals("") || filledBean.getCardno().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "A.CARDNUMBER = '" + filledBean.getCardno() + "'";
            }

            if (!filledBean.getTxnamount().equals("") || filledBean.getTxnamount().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "A.INSTALLMENTAMOUNT = '" + filledBean.getTxnamount() + "'";
            }

            if (!filledBean.getPaymentplan().equals("") || filledBean.getPaymentplan().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "A.PAYMENTPLAN = '" + filledBean.getPaymentplan() + "'";
            }

            if (filledBean.getFromdate() != null) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }

                SimpleDateFormat datefmt = new SimpleDateFormat("dd-MMM-yy");
                String converted1 = datefmt.format(filledBean.getFromdate());
                datefmt.parse(converted1);

                condition += "A.LASTUPDATEDTIME >= '" + converted1 + "'";
            }//1            

            if (filledBean.getTodate() != null) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }

                SimpleDateFormat datefmt = new SimpleDateFormat("dd-MMM-yy");
                String converted = datefmt.format(filledBean.getTodate());
                datefmt.parse(converted);

                condition += "A.LASTUPDATEDTIME <= '" + converted + "'";

            }//2

            if (!condition.equals("")) {
                query += " AND " + condition;
            }

            ps = con.prepareStatement(query);

            ps.setString(1, StatusVarList.DA_REQUSET_INITIATE);
            ps.setString(2, filledBean.getLoggeduser());
            ps.setString(3, filledBean.getLoggeduser());

            rs = ps.executeQuery();

            while (rs.next()) {
                
                //separate the month and year acceding to expiry date
                String otherCardExpiryDate = rs.getString("OTHERBANKCARDEXPIRY");
                String otherCardExpiryMonth = otherCardExpiryDate.substring(0, 2);
                String otherCardExpiryYear = otherCardExpiryDate.substring(2, 4);
                viewBean = new BTPaymentConfirmBean();

                viewBean.setPlandurarion(rs.getString("DURATION"));

                viewBean.setCardno(rs.getString("CARDNUMBER"));
                viewBean.setOtherbankCardNumber(rs.getString("OTHERBANKCARDNUMBER"));
                viewBean.setOtherbankCardExpiryMonth(otherCardExpiryMonth);
                viewBean.setOtherbankCardExpiryYear(otherCardExpiryYear);
                viewBean.setOtherbankNameOnCard(rs.getString("OTHERBANKNAMEONCARD"));
                viewBean.setBankcode(rs.getString("BANKNAME"));
                viewBean.setRequestid(rs.getString("REQUESTID"));
                viewBean.setDueDate(this.convertStringDate(rs.getDate("DUEDATE")));
                viewBean.setTxnamount(rs.getString("TRANSFERAMOUNT"));
                viewBean.setPaymentplan(rs.getString("pdes"));
                viewBean.setInterestrate(rs.getString("interestrate"));
                viewBean.setRequesteduser(rs.getString("REQUESTEDUSER"));
                viewBean.setInstallment(rs.getString("INSTALLMENTAMOUNT"));
                viewBean.setRemark(rs.getString("REMARKS"));
                viewBean.setPaymentplanCode(rs.getString("PAYMENTPLAN"));

                StatusPersistence sp = new StatusPersistence();
                String reqstatus = sp.getStatusDescription(con, rs.getString("rstatus"));
                viewBean.setStatusCode(reqstatus);

                viewBean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                viewBean.setLastUpdatedTime(rs.getDate("LASTUPDATEDTIME"));

                System.out.print(rs.getDate("LASTUPDATEDTIME").toString());

                requestList.add(viewBean);
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
        return requestList;
    }

    public boolean approveRequest(Connection CMSCon, BTPaymentConfirmBean bean) throws Exception {
        boolean successInsert = false;
        PreparedStatement insertStat = null;

        boolean successInc2 = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            insertStat = CMSCon.prepareStatement("INSERT INTO INSTALLMENT("
                    + "CARDNUMBER," //1
                    + "CREATEDTIME,"
                    + "LASTUPDATEDTIME,"
                    + "CURRINSTALLMENTPERIOD," //2
                    + "LASTUPDATEDUSER," //3
                    + "NOOFINSTALLMENTSPAID," //4
                    + "PAYMENTPLAN," //5
                    + "REMAININGNOOFINSTALLMENTS," //6
                    + "STATUS," //7
                    + "TOTALPAYMENTSDONE )" //8
                    + " VALUES(?,SYSDATE,SYSDATE,?,?,?,?,?,?,?)");

            insertStat.setString(1, bean.getCardno());
            insertStat.setString(2, "1");
            insertStat.setString(3, bean.getLastUpdatedUser());
            insertStat.setString(4, "0");
            insertStat.setString(5, bean.getPaymentplanCode());
            insertStat.setString(6, bean.getPlandurarion());
            insertStat.setString(7, StatusVarList.ACTIVE_STATUS);
            insertStat.setString(8, "0");

            insertStat.execute();
            successInsert = true;

            //////////////////////////////// updateRequestTable
            ps = CMSCon.prepareStatement("UPDATE BALANCETRASFERREQUEST SET "
                    + "STATUS=?, " //1
                    + "APPROVEDUSER=?, " //2
                    + "LASTUPDATEDUSER=?, " //3
                    + "LASTUPDATEDTIME=SYSDATE, "
                    + "REMARKS=? " //4
                    + "WHERE " //5
                    + "REQUESTID=? " //6
                    + "AND STATUS=? ");     //7

            ps.setString(1, StatusVarList.DA_REQUSET_APPROVE);
            ps.setString(2, bean.getLastUpdatedUser());
            ps.setString(3, bean.getLastUpdatedUser());
            ps.setString(4, bean.getRemark());
            ps.setString(5, bean.getRequestid());
            ps.setString(6, StatusVarList.DA_REQUSET_INITIATE);

            rs = ps.executeQuery();
            successInc2 = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
            if (rs != null) {
                rs.close();
                ps.close();
            }

        }
        return (successInsert & successInc2);

    }

    public boolean updateRequestTable(Connection CMSCon, BTPaymentConfirmBean bean) throws Exception { // this part is included in aprove request method
        boolean successInc2 = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            ps = CMSCon.prepareStatement("UPDATE BALANCETRASFERREQUEST SET "
                    + "STATUS=?, " //1
                    + "APPROVEDUSER=?, " //2
                    + "LASTUPDATEDUSER=?, " //3
                    + "LASTUPDATEDTIME=SYSDATE, "
                    + "REMARKS=? " //4
                    + "WHERE " //5
                    + "STATUS=? ");     //6

            ps.setString(1, StatusVarList.DA_REQUSET_APPROVE);
            ps.setString(2, bean.getLastUpdatedUser());
            ps.setString(3, bean.getLastUpdatedUser());
            ps.setString(4, bean.getRemark());
            ps.setString(5, StatusVarList.DA_REQUSET_INITIATE);

            rs = ps.executeQuery();
            successInc2 = true;

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
        return successInc2;
    }

    public boolean rejectRequest(Connection CMSCon, BTPaymentConfirmBean bean) throws Exception {
        boolean successInc2 = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            ps = CMSCon.prepareStatement("UPDATE BALANCETRASFERREQUEST SET "
                    + "STATUS=?, " //1
                    + "APPROVEDUSER=?, " //2
                    + "LASTUPDATEDUSER=?, " //3
                    + "LASTUPDATEDTIME=SYSDATE, "
                    + "REJECTREMARK=? " //4
                    + "WHERE " //5
                    + "STATUS=? "
                    + "AND REQUESTID=? ");     //6

            ps.setString(1, StatusVarList.DA_REQUSET_REJECT);
            ps.setString(2, bean.getLastUpdatedUser());
            ps.setString(3, bean.getLastUpdatedUser());
            ps.setString(4, bean.getRemark());
            ps.setString(5, StatusVarList.DA_REQUSET_INITIATE);
            ps.setString(6, bean.getRequestid());

            rs = ps.executeQuery();
            successInc2 = true;

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
        return successInc2;
    }

    public String getResponsemessage(Connection CMSCon, String responseCode) throws Exception {
        PreparedStatement getcurrencyList = null;
        String responseMsg = null;
        ResultSet rs = null;
        try {
            getcurrencyList = CMSCon.prepareStatement("SELECT DESCRIPTION FROM ECMS_ONLINE_RESPONSE_CODE WHERE CODE = ?");

            getcurrencyList.setString(1, responseCode);
            rs = getcurrencyList.executeQuery();

            while (rs.next()) {

                responseMsg = rs.getString("DESCRIPTION");
            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getcurrencyList.close();

        }
        return responseMsg;
    }
    
    private String convertStringDate(java.sql.Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return date != null ? (dateFormat.format(date)) : "";
    }
}
