/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.cardlimitincrement.persistance;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean;
import com.epic.cms.backoffice.cardlimitincrement.bean.CommonCardParameterBean;
import com.epic.cms.backoffice.cardlimitincrement.bean.PermLimitIncrementBean;
import com.epic.cms.backoffice.cardlimitincrement.bean.TempLimitIncrementBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.Date;
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
public class TempLimitIncrementPersistance {

    private ResultSet rs;
    private List<TempLimitIncrementBean> cardList;
    private TempLimitIncrementBean viewBean, updateBean;
    private boolean successUpdate = false;

    public boolean tempLimitUpdate(Connection CMSCon, TempLimitIncrementBean tempBean) throws Exception {
        boolean successUpdate = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = CMSCon.prepareStatement("UPDATE CARD SET"
                    + " OTBCREDIT=?,OTBCASH=?,TEMPCREDITAMOUNT=?,TEMPCASHAMOUNT=?,"
                    + " LASTUPDATEDUSER=?,LASTUPDATEDTIME=SYSDATE"
                    + " WHERE CARDNUMBER=? ");

            updateStat.setDouble(1, Double.parseDouble(tempBean.getAvlCreditLimit()));
            updateStat.setDouble(2, Double.parseDouble(tempBean.getAvlCashLimit()));
            if (tempBean.getCreditOrCash().equals("CREDIT")) {

                updateStat.setDouble(3, Double.parseDouble(tempBean.getIncrementedAmount()));
                updateStat.setDouble(4, 0);
            }
            if (tempBean.getCreditOrCash().equals("CASH")) {

                updateStat.setDouble(3, 0);
                updateStat.setDouble(4, Double.parseDouble(tempBean.getIncrementedAmount()));
            }
            updateStat.setString(5, tempBean.getLastUpdatedUser());
            updateStat.setString(6, tempBean.getCardNumber());

            updateStat.executeUpdate();
            successUpdate = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return successUpdate;

    }

    public boolean tempLimitUpdateToOnlime(Connection CMSConOnline, TempLimitIncrementBean tempBean) throws Exception {
        boolean successUpdate = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = CMSConOnline.prepareStatement("UPDATE ECMS_ONLINE_CARD SET"
                    + " OTBCREDIT=?,OTBCASH=?,TEMPCREDITAMOUNT=?,TEMPCASHAMOUNT=?,"
                    + " LASTUPDATEUSER=?,LASTUPDATETIME=SYSDATE"
                    + " WHERE CARDNUMBER=? ");

            updateStat.setDouble(1, Double.parseDouble(tempBean.getOnlineAvlCreditLimit()));
            updateStat.setDouble(2, Double.parseDouble(tempBean.getOnlineAvlCashLimit()));

            if (tempBean.getCreditOrCash().equals("CREDIT")) {

                updateStat.setDouble(3, Double.parseDouble(tempBean.getIncrementedAmount()));
                updateStat.setDouble(4, 0);
            }
            if (tempBean.getCreditOrCash().equals("CASH")) {

                updateStat.setDouble(3, 0);
                updateStat.setDouble(4, Double.parseDouble(tempBean.getIncrementedAmount()));
            }

            updateStat.setString(5, tempBean.getLastUpdatedUser());
            updateStat.setString(6, tempBean.getCardNumber());

            updateStat.executeUpdate();
            successUpdate = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return successUpdate;

    }

    public TempLimitIncrementBean getCardDetails(Connection CMSCon, String cardNumber) throws Exception {
        PreparedStatement cardStat = null;
        TempLimitIncrementBean tempBean = new TempLimitIncrementBean();
        try {
            cardStat = CMSCon.prepareStatement("SELECT C.CREDITLIMIT,C.CASHLIMIT,C.OTBCREDIT,C.OTBCASH,S.DESCRIPTION "
                    + " FROM CARD C,STATUS S WHERE C.CARDNUMBER=? AND C.CARDSTATUS=S.STATUSCODE AND C.CARDSTATUS=?");

            cardStat.setString(1, cardNumber);
            cardStat.setString(2, StatusVarList.ACTIVE_STATUS);
            rs = cardStat.executeQuery();

            while (rs.next()) {

                tempBean.setCardNumber(cardNumber);
                tempBean.setCreditLimit(Double.toString(rs.getDouble("CREDITLIMIT")));
                tempBean.setCashLimit(Double.toString(rs.getDouble("CASHLIMIT")));
                tempBean.setAvlCreditLimit(Double.toString(rs.getInt("OTBCREDIT")));
                tempBean.setAvlCashLimit(Double.toString(rs.getInt("OTBCASH")));
                tempBean.setStatusDes(rs.getString("DESCRIPTION"));
                tempBean.setFlag(true);

            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    cardStat.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }

        return tempBean;
    }

    public TempLimitIncrementBean getOnlineCardDetails(Connection CMSConOnline, TempLimitIncrementBean tempBean) throws Exception {
        PreparedStatement cardStat = null;
        //TempLimitIncrementBean tempBean = new TempLimitIncrementBean();
        try {
            cardStat = CMSConOnline.prepareStatement("SELECT C.CREDITLIMIT,C.CASHLIMIT,C.OTBCREDIT,C.OTBCASH "
                    + " FROM ECMS_ONLINE_CARD C WHERE C.CARDNUMBER=? AND C.STATUS=?");

            cardStat.setString(1, tempBean.getCardNumber());
            cardStat.setInt(2, StatusVarList.ONLINE_ACTIVE_STATUS);
            rs = cardStat.executeQuery();

            while (rs.next()) {

                tempBean.setOnlineCreditLimit(Double.toString(rs.getDouble("CREDITLIMIT")));
                tempBean.setOnlineCashLimit(Double.toString(rs.getDouble("CASHLIMIT")));
                tempBean.setOnlineAvlCreditLimit(Double.toString(rs.getInt("OTBCREDIT")));
                tempBean.setOnlineAvlCashLimit(Double.toString(rs.getInt("OTBCASH")));

            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    cardStat.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }

        return tempBean;
    }

    public boolean tempLimitIncrementInsert(Connection CMSCon, TempLimitIncrementBean tempBean) throws Exception {
        boolean successInsert = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = CMSCon.prepareStatement("INSERT INTO TEMPLIMITINCREMENT(CARDNO,AMOUNT,"
                    + " STARTDATE,ENDDATE,STATUS,REASON,INCREMENTTYPE,LASTUPDATEDUSER,LASTUPDATEDTIME,"
                    + " CREATEDTIME, ONLINEAVALABLECREDITLIMIT, ONLINEAVALABLECASHLIMIT, ONLINETEMPCREDITAMOUNT, "
                    + " ONLINETEMPCASHAMOUNT, ONLINECREDITLIMIT, ONLINECASHLIMIT, REQUESTEDUSER,INCORDEC )"
                    + " VALUES(?,?,?,?,?,?,?,?,SYSDATE,SYSDATE,?,?,?,?,?,?,?,? )");

            insertStat.setString(1, tempBean.getCardNumber());
//            insertStat.setString(2, tempBean.getIncrementedAmount());
            insertStat.setString(2, tempBean.getAmount());
            insertStat.setDate(3, tempBean.getStartDate());
            insertStat.setDate(4, tempBean.getEndDate());
            insertStat.setString(5, tempBean.getStatus());
            insertStat.setString(6, tempBean.getRemarks());
            insertStat.setString(7, tempBean.getCreditOrCash());
            insertStat.setString(8, tempBean.getLastUpdatedUser());

            insertStat.setString(9, tempBean.getOnlineAvlCreditLimit());
            insertStat.setString(10, tempBean.getOnlineAvlCashLimit());
            insertStat.setString(11, tempBean.getOnlineTempCrediAmt());
            insertStat.setString(12, tempBean.getOnlineTempCashAmt());
            insertStat.setString(13, tempBean.getOnlineCreditLimit());
            insertStat.setString(14, tempBean.getOnlineCashLimit());
            insertStat.setString(15, tempBean.getLastUpdatedUser());
            insertStat.setString(16, tempBean.getIncordec());

            insertStat.execute();
            successInsert = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return successInsert;

    }

    public int insertLimitIncrement(Connection CMSCon, TempLimitIncrementBean tempBean) throws Exception {
        int i = -1;
        PreparedStatement insertStat = null;
        try {

            insertStat = CMSCon.prepareStatement("INSERT INTO PERMENANTLIMITINCREMENT(CARDNO, INCREMENTTYPE, AMOUNT, STATUS, REMARK, REQUESTEDUSER, "
                    + "LASTUPDATEDUSER, CREATEDTIME, LASTUPDATEDTIME, ONLINEAVALABLECREDITLIMIT, ONLINEAVALABLECASHLIMIT, ONLINETEMPCREDITAMOUNT, "
                    + "ONLINETEMPCASHAMOUNT, ONLINECREDITLIMIT, ONLINECASHLIMIT ) VALUES(?,?,?,?,?,?,?,SYSDATE,SYSDATE,?,?,?,?,?,?)");

            insertStat.setString(1, tempBean.getCardNumber());
            insertStat.setString(2, tempBean.getCreditOrCash());
            insertStat.setString(3, tempBean.getAmount());
            insertStat.setString(4, tempBean.getStatus());
            insertStat.setString(5, tempBean.getRemark());
            insertStat.setString(6, tempBean.getLastUpdatedUser());
            insertStat.setString(7, tempBean.getLastUpdatedUser());
            insertStat.setString(8, tempBean.getOnlineAvlCreditLimit());
            insertStat.setString(9, tempBean.getOnlineAvlCashLimit());
            insertStat.setString(10, tempBean.getOnlineTempCrediAmt());
            insertStat.setString(11, tempBean.getOnlineTempCashAmt());
            insertStat.setString(12, tempBean.getOnlineCreditLimit());
            insertStat.setString(13, tempBean.getOnlineCashLimit());

            i = insertStat.executeUpdate();

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (insertStat != null) {
                    insertStat.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return i;
    }

    public List<TempLimitIncrementBean> checkLimitIncrementHistory(Connection CMSCon) throws Exception {
        PreparedStatement checkStat = null;
        List<TempLimitIncrementBean> tempList = new ArrayList<TempLimitIncrementBean>();
        try {
            checkStat = CMSCon.prepareStatement("SELECT  T.CARDNO,T.AMOUNT,T.STARTDATE,T.ENDDATE,T.INCREMENTTYPE,T.STATUS"
                    + " FROM TEMPLIMITINCREMENT T WHERE T.STATUS != 'EXP'");

            //checkStat.setString(1, cardNumber);
            rs = checkStat.executeQuery();

            while (rs.next()) {

                TempLimitIncrementBean tempBean = new TempLimitIncrementBean();

                tempBean.setCardNumber(rs.getString("CARDNO"));
                tempBean.setStatus(rs.getString("STATUS"));
                tempBean.setIncrementedAmount(rs.getString("AMOUNT"));
                tempBean.setStartDate(rs.getDate("STARTDATE"));
                tempBean.setEndDate(rs.getDate("ENDDATE"));
                tempBean.setCreditOrCash(rs.getString("INCREMENTTYPE"));

                tempList.add(tempBean);

            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    checkStat.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return tempList;
    }

    public Date getDbDate(Connection CMSCon) throws Exception {
        Date currentDate = null;
        PreparedStatement dateStat = null;
        try {
            dateStat = CMSCon.prepareStatement("SELECT TO_CHAR(SYSDATE, 'YYYY-MM-DD')AS A FROM DUAL");

            rs = dateStat.executeQuery();

            while (rs.next()) {
                currentDate = rs.getDate("A");
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    dateStat.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return currentDate;
    }

    public CommonCardParameterBean getCommonParameters(Connection CMSCon) throws Exception {
        CommonCardParameterBean commeonBean = new CommonCardParameterBean();
        PreparedStatement commonParaStat = null;
        try {

            commonParaStat = CMSCon.prepareStatement("SELECT C.MAXIMUMTEMPCREDITLIMITAMOUNT,"
                    + " C.MAXIMUMTEMPCASHLIMITAMOUNT"
                    + " FROM COMMONCARDPARAMETER C");

            rs = commonParaStat.executeQuery();

            while (rs.next()) {
                commeonBean.setMaxTempCreditLimitAmount(Double.toString(rs.getDouble("MAXIMUMTEMPCREDITLIMITAMOUNT")));
                commeonBean.setMaxTempCashLimitAmount(Double.toString(rs.getDouble("MAXIMUMTEMPCASHLIMITAMOUNT")));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    commonParaStat.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return commeonBean;
    }

    public SystemUserBean checkDualAuthorisation(Connection CMSCon, SystemUserBean sysUserBean) throws Exception {
        SystemUserBean CMSSysUser = new SystemUserBean();
        PreparedStatement ps = null;
        try {
            String query = "SELECT USERNAME,PASSWORD FROM SYSTEMUSER WHERE "
                    + " USERNAME='" + sysUserBean.getUserName() + "' AND "
                    + " PASSWORD='" + sysUserBean.getPassword() + "'";

            ps = CMSCon.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {

                CMSSysUser.setUserName(rs.getString("USERNAME"));
                CMSSysUser.setPassword(rs.getString("PASSWORD"));
                CMSSysUser.setFlag(true);
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
        return CMSSysUser;
    }

    public TempLimitIncrementBean getRealTimeCardDetails(Connection CMSCon, TempLimitIncrementBean tempBean) throws Exception {
        PreparedStatement cardStat = null;
        //TempLimitIncrementBean tempBean = new TempLimitIncrementBean();
        try {
            cardStat = CMSCon.prepareStatement("SELECT C.CREDITLIMIT,C.CASHLIMIT,C.OTBCREDIT,C.OTBCASH,S.DESCRIPTION "
                    + " FROM CARD C,STATUS S WHERE C.CARDNUMBER=? AND C.CARDSTATUS=S.STATUSCODE");

            cardStat.setString(1, tempBean.getCardNumber());
            rs = cardStat.executeQuery();

            while (rs.next()) {

                tempBean.setCreditLimit(Double.toString(rs.getDouble("CREDITLIMIT")));
                tempBean.setCashLimit(Double.toString(rs.getDouble("CASHLIMIT")));
                tempBean.setAvlCreditLimit(Double.toString(rs.getInt("OTBCREDIT")));
                tempBean.setAvlCashLimit(Double.toString(rs.getInt("OTBCASH")));

            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    cardStat.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }

        return tempBean;
    }

    public List<TempLimitIncrementBean> searchCards(Connection con, TempLimitIncrementBean filledBean) throws SQLException, Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;

        cardList = new ArrayList<TempLimitIncrementBean>();

        try {

            String query = "SELECT PLI.CARDNO,PLI.INCREMENTTYPE,PLI.AMOUNT,PLI.STATUS,PLI.REASON,PLI.REQUESTEDUSER,PLI.LASTUPDATEDUSER,PLI.LASTUPDATEDTIME,"
                    + "PLI.ONLINEAVALABLECREDITLIMIT, PLI.ONLINEAVALABLECASHLIMIT,PLI.ONLINECREDITLIMIT,PLI.ONLINECASHLIMIT,PLI.STARTDATE, PLI.ENDDATE, "
                    + "PLI.ONLINETEMPCREDITAMOUNT, PLI.ONLINETEMPCASHAMOUNT, PLI.INCORDEC, ST.DESCRIPTION "
                    + "FROM TEMPLIMITINCREMENT PLI,STATUS ST, userlevel ul1, userlevel ul2, userrole ur1, userrole ur2, systemuser su1,systemuser su2 "
                    + "WHERE PLI.STATUS in( ?,? ) and PLI.STATUS=ST.STATUSCODE "
                    + "and PLI.REQUESTEDUSER != ? and ul1.levelid = ur1.levelid and ur1.userrole = su1.userrolecode and su1.username = PLI.REQUESTEDUSER "
                    + "and ul2.levelid = ur2.levelid and ur2.userrole = su2.userrolecode and su2.username = ? "
                    + "and (ul1.levelid <= ul2.levelid or ur2.userrole = su1.dualauthuserrole) ";

            String condition = "";

            if (!filledBean.getCardNumber().equals("") || filledBean.getCardNumber().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "PLI.CARDNO = '" + filledBean.getCardNumber() + "'";
            }

            if (!filledBean.getIncordec().equals("") || filledBean.getIncordec().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "PLI.INCORDEC = '" + filledBean.getIncordec() + "'";
            }

            if (!filledBean.getCreditOrCash().equals("") || filledBean.getCreditOrCash().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "PLI.INCREMENTTYPE = '" + filledBean.getCreditOrCash() + "'";
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
            ps.setString(1, StatusVarList.ACTIVE_STATUS);
            ps.setString(2, StatusVarList.INITIAL_STATUS);
            ps.setString(3, filledBean.getLoggeduser());
            ps.setString(4, filledBean.getLoggeduser());            

            rs = ps.executeQuery();

            while (rs.next()) {

                viewBean = new TempLimitIncrementBean();

                viewBean.setCardNumber(rs.getString("CARDNO"));
                viewBean.setCreditOrCash(rs.getString("INCREMENTTYPE"));
                viewBean.setIncordec(rs.getString("INCORDEC"));
                viewBean.setAmount(rs.getString("AMOUNT"));
                viewBean.setStatus(rs.getString("STATUS"));
                viewBean.setStatusDes(rs.getString("DESCRIPTION"));
                viewBean.setRemark(rs.getString("REASON"));
                viewBean.setRequestedUser(rs.getString("REQUESTEDUSER"));
                viewBean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                viewBean.setLastUpdatedTime(rs.getDate("LASTUPDATEDTIME"));
                viewBean.setStartDate(rs.getDate("STARTDATE"));
                viewBean.setEndDate(rs.getDate("ENDDATE"));

                viewBean.setOnlineAvlCreditLimit(rs.getString("ONLINEAVALABLECREDITLIMIT"));
                viewBean.setOnlineAvlCashLimit(rs.getString("ONLINEAVALABLECASHLIMIT"));
                viewBean.setOnlineCreditLimit(rs.getString("ONLINECREDITLIMIT"));
                viewBean.setOnlineCashLimit(rs.getString("ONLINECASHLIMIT"));
                viewBean.setOnlineTempCrediAmt(rs.getString("ONLINETEMPCREDITAMOUNT"));
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

    public TempLimitIncrementBean getOnlineDetails(Connection onlinecon, String cardnum) throws SQLException, Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "SELECT OTBCREDIT,OTBCASH,TEMPCREDITAMOUNT,TEMPCASHAMOUNT FROM ECMS_ONLINE_CARD WHERE CARDNUMBER=?";

            ps = onlinecon.prepareStatement(query);
            ps.setString(1, cardnum);

            rs = ps.executeQuery();

            while (rs.next()) {
                updateBean = new TempLimitIncrementBean();

                updateBean.setOnlineAvlCreditLimit(rs.getString("OTBCREDIT"));
                updateBean.setOnlineAvlCashLimit(rs.getString("OTBCASH"));
                updateBean.setOnlineTempCrediAmt(rs.getString("TEMPCREDITAMOUNT"));
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

    public boolean updateTempLimitIncDetails(Connection cmscon, TempLimitIncrementBean bean, String cardNum) throws SQLException, Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            String query = "UPDATE TEMPLIMITINCREMENT SET ONLINEAVALABLECREDITLIMIT=?, ONLINEAVALABLECASHLIMIT=?, ONLINETEMPCREDITAMOUNT=?, "
                    + "ONLINETEMPCASHAMOUNT=?,LASTUPDATEDUSER=?,LASTUPDATEDTIME=SYSDATE WHERE CARDNO=? AND STATUS='ACT'";

            ps = cmscon.prepareStatement(query);
            ps.setString(1, bean.getOnlineAvlCreditLimit());
            ps.setString(2, bean.getOnlineAvlCashLimit());
            ps.setString(3, bean.getOnlineTempCrediAmt());
            ps.setString(4, bean.getOnlineTempCashAmt());
            ps.setString(5, bean.getLastUpdatedUser());

            ps.setString(6, cardNum);

            rs = ps.executeQuery();
            successUpdate = true;

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

    public boolean limitIncrement(Connection CMSCon, TempLimitIncrementBean bean) throws Exception {
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

    public boolean updateTemplimitInc(Connection CMSCon, TempLimitIncrementBean bean) throws Exception {
        boolean successInc2 = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            ps = CMSCon.prepareStatement("UPDATE TEMPLIMITINCREMENT SET STATUS=?, APPROVEDUSER=?, LASTUPDATEDUSER=?, LASTUPDATEDTIME=SYSDATE, "
                    + "ONLINEAVALABLECREDITLIMIT=?, ONLINEAVALABLECASHLIMIT=?, ONLINECREDITLIMIT=?, ONLINECASHLIMIT=? WHERE CARDNO=? AND STATUS=? ");

            ps.setString(1, StatusVarList.DA_REQUSET_APPROVE);
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

    public boolean limitIncrementToOnline(Connection onlineCon, TempLimitIncrementBean bean) throws Exception {
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
