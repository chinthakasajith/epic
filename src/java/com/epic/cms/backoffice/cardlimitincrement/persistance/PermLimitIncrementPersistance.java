/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.cardlimitincrement.persistance;

import com.epic.cms.backoffice.cardlimitincrement.bean.PermLimitIncrementBean;
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
 * @author badrika
 */
public class PermLimitIncrementPersistance {

    private List<PermLimitIncrementBean> cardList;
    private PermLimitIncrementBean viewBean, updateBean;
    private boolean successUpdate = false;

    public List<PermLimitIncrementBean> searchCards(Connection con, PermLimitIncrementBean filledBean) throws SQLException, Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;

        cardList = new ArrayList<PermLimitIncrementBean>();

        try {

            String query = "SELECT PLI.CARDNO,PLI.INCREMENTTYPE,PLI.AMOUNT,PLI.STATUS,PLI.REMARK,PLI.REQUESTEDUSER,PLI.LASTUPDATEDUSER,PLI.LASTUPDATEDTIME,"
                    + "PLI.ONLINEAVALABLECREDITLIMIT, PLI.ONLINEAVALABLECASHLIMIT,PLI.ONLINECREDITLIMIT,PLI.ONLINECASHLIMIT,PLI.ONLINETEMPCREDITAMOUNT,"
                    + "PLI.ONLINETEMPCASHAMOUNT,ST.DESCRIPTION "
                    + "FROM PERMENANTLIMITINCREMENT PLI,STATUS ST WHERE PLI.STATUS=ST.STATUSCODE ";

            String condition = "";

            if (!filledBean.getCardNumber().equals("") || filledBean.getCardNumber().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "PLI.CARDNO = '" + filledBean.getCardNumber() + "'";
            }

            if (!filledBean.getIncType().equals("") || filledBean.getIncType().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "PLI.INCREMENTTYPE = '" + filledBean.getIncType() + "'";
            }

            if (!filledBean.getAmount().equals("") || filledBean.getAmount().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "PLI.AMOUNT = '" + filledBean.getAmount() + "'";
            }

            if (filledBean.getFromDate() != null) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }

                SimpleDateFormat datefmt = new SimpleDateFormat("dd-MMM-yy");
                String converted1 = datefmt.format(filledBean.getFromDate());
                datefmt.parse(converted1);

                condition += "PLI.LASTUPDATEDTIME >= '" + converted1 + "'";
            }//1            

            if (filledBean.getToDate() != null) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }

                SimpleDateFormat datefmt = new SimpleDateFormat("dd-MMM-yy");
                String converted = datefmt.format(filledBean.getToDate());
                datefmt.parse(converted);

                condition += "PLI.LASTUPDATEDTIME <= '" + converted + "'";

            }//2

            if (!condition.equals("")) {
                query += " AND " + condition;
            }

            ps = con.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {

                viewBean = new PermLimitIncrementBean();

                viewBean.setCardNumber(rs.getString("CARDNO"));
                viewBean.setIncType(rs.getString("INCREMENTTYPE"));
                viewBean.setAmount(rs.getString("AMOUNT"));
                viewBean.setStatus(rs.getString("STATUS"));
                viewBean.setStatusDes(rs.getString("DESCRIPTION"));
                viewBean.setRemark(rs.getString("REMARK"));
                viewBean.setRequestedUser(rs.getString("REQUESTEDUSER"));
                viewBean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                viewBean.setLastUpdatedTime(rs.getDate("LASTUPDATEDTIME"));

                viewBean.setOnlineAvlCreditLimit(rs.getString("ONLINEAVALABLECREDITLIMIT"));
                viewBean.setOnlineAvlCashLimit(rs.getString("ONLINEAVALABLECASHLIMIT"));
                viewBean.setOnlineCreditLimit(rs.getString("ONLINECREDITLIMIT"));
                viewBean.setOnlineCashLimit(rs.getString("ONLINECASHLIMIT"));
                viewBean.setOnlineTempCreditAmt(rs.getString("ONLINETEMPCREDITAMOUNT"));
                viewBean.setOnlineTempCashAmt(rs.getString("ONLINETEMPCASHAMOUNT"));

                System.out.print(rs.getDate("LASTUPDATEDTIME").toString());



                cardList.add(viewBean);
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
        return cardList;
    }

    public PermLimitIncrementBean getOnlineDetails(Connection onlinecon, String cardnum) throws SQLException, Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "SELECT OTBCREDIT,OTBCASH,TEMPCREDITAMOUNT,TEMPCASHAMOUNT FROM ECMS_ONLINE_CARD WHERE CARDNUMBER=?";

            ps = onlinecon.prepareStatement(query);
            ps.setString(1, cardnum);

            rs = ps.executeQuery();

            while (rs.next()) {
                updateBean = new PermLimitIncrementBean();

                updateBean.setOnlineAvlCreditLimit(rs.getString("OTBCREDIT"));
                updateBean.setOnlineAvlCashLimit(rs.getString("OTBCASH"));
                updateBean.setOnlineTempCreditAmt(rs.getString("TEMPCREDITAMOUNT"));
                updateBean.setOnlineTempCashAmt(rs.getString("TEMPCASHAMOUNT"));

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
        return updateBean;
    }

    public boolean updateOnlineDetails(Connection cmscon, PermLimitIncrementBean bean, String cardNum) throws SQLException, Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            String query = "UPDATE PERMENANTLIMITINCREMENT SET ONLINEAVALABLECREDITLIMIT=?, ONLINEAVALABLECASHLIMIT=?, ONLINETEMPCREDITAMOUNT=?, "
                    + "ONLINETEMPCASHAMOUNT=?,LASTUPDATEDUSER=?,LASTUPDATEDTIME=SYSDATE WHERE CARDNO=? AND STATUS='ACT'";

            ps = cmscon.prepareStatement(query);
            ps.setString(1, bean.getOnlineAvlCreditLimit());
            ps.setString(2, bean.getOnlineAvlCashLimit());
            ps.setString(3, bean.getOnlineTempCreditAmt());
            ps.setString(4, bean.getOnlineTempCashAmt());
            ps.setString(5, bean.getLastUpdatedUser());
            
            ps.setString(6, cardNum);

            rs = ps.executeQuery();
            successUpdate=true;

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
        return successUpdate;
    }

    public boolean limitIncrement(Connection CMSCon, PermLimitIncrementBean bean) throws Exception {
        boolean successInc = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            ps = CMSCon.prepareStatement("UPDATE CARD SET CREDITLIMIT=?, CASHLIMIT=?, OTBCREDIT=?, OTBCASH=?, LASTUPDATEDUSER=?, "
                    + "LASTUPDATEDTIME=SYSDATE WHERE CARDNUMBER=? ");

            ps.setString(1, bean.getOnlineCreditLimit());
            ps.setString(2, bean.getOnlineCashLimit());
            ps.setString(3, bean.getOnlineAvlCreditLimit());
            ps.setString(4, bean.getOnlineAvlCashLimit());
            ps.setString(5, bean.getLastUpdatedUser());
            
            ps.setString(6, bean.getCardNumber());

            rs = ps.executeQuery();
            successInc = true;


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
        return successInc;
    }

    public boolean updatePermlimitInc(Connection CMSCon, PermLimitIncrementBean bean) throws Exception {
        boolean successInc2 = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            ps = CMSCon.prepareStatement("UPDATE PERMENANTLIMITINCREMENT SET STATUS=?, APPROVEDUSER=?, LASTUPDATEDUSER=?, LASTUPDATEDTIME=SYSDATE, "
                    + "ONLINEAVALABLECREDITLIMIT=?, ONLINEAVALABLECASHLIMIT=?, ONLINECREDITLIMIT=?, ONLINECASHLIMIT=? WHERE CARDNO=? AND STATUS=? ");

            ps.setString(1, StatusVarList.COMPLETE_STATUS);
            ps.setString(2, bean.getApprovedUser());
            ps.setString(3, bean.getLastUpdatedUser());
            ps.setString(4, bean.getOnlineAvlCreditLimit());
            ps.setString(5, bean.getOnlineAvlCashLimit());
            ps.setString(6, bean.getOnlineCreditLimit());
            ps.setString(7, bean.getOnlineCashLimit());
            ps.setString(8, bean.getCardNumber());
            ps.setString(9, StatusVarList.ACTIVE_STATUS);

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

    public boolean limitIncrementToOnline(Connection onlineCon, PermLimitIncrementBean bean) throws Exception {
        boolean successInc3 = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            ps = onlineCon.prepareStatement("UPDATE ECMS_ONLINE_CARD SET CREDITLIMIT=?, CASHLIMIT=?, OTBCREDIT=?, OTBCASH=?, LASTUPDATETIME=SYSDATE, "
                    + "LASTUPDATEUSER=? WHERE CARDNUMBER=? ");

            ps.setString(1, bean.getOnlineCreditLimit());
            ps.setString(2, bean.getOnlineCashLimit());

            ps.setString(3, bean.getOnlineAvlCreditLimit());
            ps.setString(4, bean.getOnlineAvlCashLimit());
            ps.setString(5, bean.getLastUpdatedUser());

            ps.setString(6, bean.getCardNumber());

            rs = ps.executeQuery();
            successInc3 = true;


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
        return successInc3;
    }
}
