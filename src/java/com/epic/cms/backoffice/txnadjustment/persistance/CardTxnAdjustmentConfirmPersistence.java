/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.txnadjustment.persistance;

import com.epic.cms.application.common.persistance.StatusPersistence;
import com.epic.cms.backoffice.txnadjustment.bean.TransactionAdjustment;
import com.epic.cms.backoffice.txnadjustment.bean.TransactionAdjustmentParty;
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
 * @author Badrika
 */
public class CardTxnAdjustmentConfirmPersistence {

    private ArrayList<TransactionAdjustment> requestList;
    private TransactionAdjustment tabean;

    public List<TransactionAdjustment> searchRequests(TransactionAdjustmentParty party, Connection con, TransactionAdjustment filledBean) throws SQLException, Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;

        requestList = new ArrayList<TransactionAdjustment>();

        try {

            String query = "SELECT AD.ID,AD.AMOUNT,AD.adjustmenttype,ad.approveduser,"
                    + "ad.crdr,ad.remarks,AD.REQUESTEDUSER,ad.traceno,ad.lastupdateduser,"
                    + "ad.verificationvale,ad.uniqueid, cr.description as CURRENCYTYPEDES,"
                    + "cr.currencynumcode as CURRENCYCODE, cr.EXPONENT, st.description as statusdes,"
                    + "st.statuscode as status, ad.transactiontype, tt.DESCRIPTION as ttdes "
                    + "FROM ADJUSTMENT AD, CURRENCY CR, TRANSACTIONTYPE tt, status st, userlevel ul1, userlevel ul2, userrole ur1, userrole ur2, systemuser su1,systemuser su2 "
                    + "WHERE CARDORMERCHANT = ? AND ad.STATUS = ? and cr.currencynumcode = ad.currencytype AND st.statuscode = ad.status and ad.transactiontype=tt.TRANSACTIONCODE "
                    + "and AD.REQUESTEDUSER != ? and ul1.levelid = ur1.levelid and ur1.userrole = su1.userrolecode and su1.username = AD.REQUESTEDUSER "
                    + "and ul2.levelid = ur2.levelid and ur2.userrole = su2.userrolecode and su2.username = ? "
                    + "and (ul1.levelid <= ul2.levelid or ur2.userrole = su1.dualauthuserrole) ";

//            String query2 = "SELECT "
//                    + "FROM ADJUSTMENT A,PAYMENTPLAN P, TRANSACTION T, userlevel ul1, userlevel ul2, userrole ur1, userrole ur2, systemuser su1,systemuser su2 "
//                    + "WHERE A.STATUS = ? and A.PAYMENTPLAN = P.PAYMENTPLANCODE and A.RRN = T.RRN and T.cardno = A.cardnumber "
//                    + "and A.REQUESTEDUSER != ? and ul1.levelid = ur1.levelid and ur1.userrole = su1.userrolecode and su1.username = A.REQUESTEDUSER "
//                    + "and ul2.levelid = ur2.levelid and ur2.userrole = su2.userrolecode and su2.username = ? "
//                    + "and (ul1.levelid <= ul2.levelid or ur2.userrole = su1.dualauthuserrole) ";
            String condition = "";

            if (!filledBean.getUniqueId().equals("") || filledBean.getUniqueId().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "ad.uniqueid = '" + filledBean.getUniqueId() + "'";
            }
            if ((filledBean.getAdjustmentType() == null || filledBean.getAdjustmentType().length() <= 0)) {
                filledBean.setAdjustmentType("");
            }

            if (!filledBean.getAdjustmentType().equals("") || filledBean.getAdjustmentType().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "ad.adjustmenttype = '" + filledBean.getAdjustmentType() + "'";
            }

            if (!filledBean.getTransactionType().equals("") || filledBean.getTransactionType().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "ad.transactiontype = '" + filledBean.getTransactionType() + "'";
            }

            if (!filledBean.getAmount().equals("") || filledBean.getAmount().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "AD.AMOUNT = '" + filledBean.getAmount() + "'";
            }

            if (!filledBean.getCrOrdr().equals("") || filledBean.getCrOrdr().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "ad.crdr = '" + filledBean.getCrOrdr() + "'";
            }

            if (!filledBean.getCurrencyCode().equals("") || filledBean.getCurrencyCode().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "ad.currencytype = '" + filledBean.getCurrencyCode() + "'";
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

            ps.setString(1, party.getName());
            ps.setString(2, StatusVarList.MA_PENDING);
            ps.setString(3, filledBean.getLoggeduser());
            ps.setString(4, filledBean.getLoggeduser());

            rs = ps.executeQuery();

            while (rs.next()) {

                tabean = new TransactionAdjustment();

                tabean.setAdjustmentParty(party);
                tabean.setId(rs.getInt("ID"));
                tabean.setAmount(rs.getString("AMOUNT"));
                tabean.setAmountD(rs.getDouble("AMOUNT"));
                tabean.setAdjustmentType(rs.getString("ADJUSTMENTTYPE"));
                tabean.setAdjustmentTypeDes(getAdjustmentDescriptionById(rs.getString("ADJUSTMENTTYPE")));
                tabean.setApprovedUser(rs.getString("APPROVEDUSER"));
                tabean.setCrOrdr(rs.getString("CRDR"));
                tabean.setCurrencyCode(this.zeroPadding(rs.getString("CURRENCYCODE")));
                tabean.setCurrencyDes(rs.getString("CURRENCYTYPEDES"));
                tabean.setRemarks(rs.getString("REMARKS"));
                tabean.setRequestedUser(rs.getString("REQUESTEDUSER"));
                tabean.setStatus(rs.getString("STATUS"));
                tabean.setStatusDes(rs.getString("STATUSDES"));
                tabean.setTraceNo(rs.getString("TRACENO"));
                tabean.setUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                tabean.setVerificationValue(rs.getString("VERIFICATIONVALE"));
                tabean.setUniqueId(rs.getString("UNIQUEID"));
                tabean.setExponent(rs.getDouble("EXPONENT"));
//                tabean.setTransactionType(rs.getString("TRANSACTIONTYPE"));
                tabean.setTransactionType(rs.getString("ttdes"));

                requestList.add(tabean);
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

    /*
     * to put zeros to the front of code if there is less than 3 digits
     */
    private String zeroPadding(String code) {
        int len = code.length();

        if (len < 3 && len == 2) {
            code = "0" + code;
        } else if (len < 3 && len == 1) {
            code = "00" + code;
        }

        return code;
    }

    private String getAdjustmentDescriptionById(String id) {
        if (id.equals("1")) {
            return "TRANSACTION";
        }
        if (id.equals("2")) {
            return "FEE";
        }
        if (id.equals("3")) {
            return "INTEREST";
        }
        if (id.equals("4")) {
            return "COMMISSION";
        }
        return "";
    }
    
     public boolean approveRequest(Connection CMSCon, TransactionAdjustment bean) throws Exception { // this part is included in aprove request method
        boolean successInc2 = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            ps = CMSCon.prepareStatement("UPDATE ADJUSTMENT SET "
                    + "STATUS=?, " //1
                    + "APPROVEDUSER=?, " //2
                    + "LASTUPDATEDUSER=?, " //3
                    + "LASTUPDATEDTIME=SYSDATE, "
                    + "REMARKS=? " //4
                    + "WHERE ID=? " //5
                    + "AND STATUS=? ");     //6

            ps.setString(1, StatusVarList.MA_ACCEPTED);
            ps.setString(2, bean.getUpdatedUser());
            ps.setString(3, bean.getUpdatedUser());
            ps.setString(4, bean.getRemarks());
            ps.setLong(5, bean.getId());
            ps.setString(6, StatusVarList.MA_PENDING);

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

    public boolean rejectRequest(Connection CMSCon, TransactionAdjustment bean) throws Exception {
        boolean successInc2 = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            ps = CMSCon.prepareStatement("UPDATE ADJUSTMENT SET "
                    + "STATUS=?, " //1
                    + "APPROVEDUSER=?, " //2
                    + "LASTUPDATEDUSER=?, " //3
                    + "LASTUPDATEDTIME=SYSDATE, "
                    + "REJECTREMARKS=? " //4
                    + "WHERE ID=? " //5
                    + "AND STATUS=? ");     //6

            ps.setString(1, StatusVarList.MA_REJECTED);
            ps.setString(2, bean.getUpdatedUser());
            ps.setString(3, bean.getUpdatedUser());
            ps.setString(4, bean.getRemarks());
            ps.setLong(5, bean.getId());
            ps.setString(6, StatusVarList.MA_PENDING);

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


}
