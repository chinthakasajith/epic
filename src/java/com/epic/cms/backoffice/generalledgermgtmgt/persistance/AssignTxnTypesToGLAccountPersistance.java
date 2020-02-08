/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.generalledgermgtmgt.persistance;

import com.epic.cms.backoffice.generalledgermgtmgt.bean.AssignTxnBean;
import com.epic.cms.backoffice.generalledgermgtmgt.bean.ChannelLisnrTypeBean;
import com.epic.cms.backoffice.generalledgermgtmgt.bean.TxnTypesBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author badrika
 */
public class AssignTxnTypesToGLAccountPersistance {

    private ResultSet rs;

    public List<ChannelLisnrTypeBean> getChanelLisnrTypeList(Connection OnlineCon, String chorls) throws Exception {

        List<ChannelLisnrTypeBean> allAccont = new ArrayList<ChannelLisnrTypeBean>();
        PreparedStatement ps = null;
        String query = "";

        try {

            if (chorls.equals("1")) {
                query = "SELECT CODE, DESCRIPTION FROM ECMS_ONLINE_CHANNEL_TYPE ";
            } else if (chorls.equals("2")) {
//                query = "SELECT CODE, DESCRIPTION FROM ECMS_ONLINE_LISTENER_TYPE ";
                query = "SELECT L.LISTENERID, LT.DESCRIPTION FROM ECMS_ONLINE_LISTENER_TYPE LT, ECMS_ONLINE_LISTENERS L where LT.CODE = L.LISTENERTYPE ";
            }

            ps = OnlineCon.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                ChannelLisnrTypeBean bean = new ChannelLisnrTypeBean();

                if (chorls.equals("2")) {
                    bean.setCode(rs.getString("LISTENERID"));
                }
                if (chorls.equals("1")) {
                    bean.setCode(rs.getString("CODE"));
                }

                bean.setDescription(rs.getString("DESCRIPTION"));

                allAccont.add(bean);
            }

            return allAccont;

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

    //getTxnTypes(String accNo, String clType)
    public List<TxnTypesBean> getTxnTypes(Connection OnlineCon, String col, String clType, String crGlAcc, String drGlAcc) throws Exception {

        List<TxnTypesBean> txntypes = new ArrayList<TxnTypesBean>();
        PreparedStatement ps = null;
        String query = "";

        try {
            query = "SELECT TXNTYPECODE, DESCRIPTION FROM ECMS_ONLINE_DEFINEDTXN "
                    + "where TXNTYPECODE not in(select TXNTYPE from ECMS_ONLINE_GLACCOUNTS where LCSTATUS=? and LCID=? and CREDITACCOUNT=? and DEBITACCOUNT=?) and MTI not in('0800')";
            ps = OnlineCon.prepareStatement(query);

            ps.setString(1, col);
            ps.setString(2, clType);
            ps.setString(3, crGlAcc);
            ps.setString(4, drGlAcc);

            rs = ps.executeQuery();

            while (rs.next()) {
                TxnTypesBean bean = new TxnTypesBean();
                bean.setTypeCode(rs.getString("TXNTYPECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                txntypes.add(bean);
            }

            return txntypes;

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

    public List<TxnTypesBean> getassinedTxnTypes(Connection OnlineCon, String col, String clType, String crAccNo, String drAccNo) throws Exception {

        List<TxnTypesBean> txntypes = new ArrayList<TxnTypesBean>();
        PreparedStatement ps = null;
        String query = "";

        try {
            query = "SELECT TXNTYPECODE, DESCRIPTION FROM ECMS_ONLINE_DEFINEDTXN "
                    + "where TXNTYPECODE in(select TXNTYPE from ECMS_ONLINE_GLACCOUNTS where LCSTATUS=? and LCID=? and CREDITACCOUNT=? and DEBITACCOUNT=?) and MTI not in ('0800') ";
            ps = OnlineCon.prepareStatement(query);

            ps.setString(1, col);
            ps.setString(2, clType);
            ps.setString(3, crAccNo);
            ps.setString(4, drAccNo);

            rs = ps.executeQuery();

            while (rs.next()) {
                TxnTypesBean bean = new TxnTypesBean();
                bean.setTypeCode(rs.getString("TXNTYPECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                txntypes.add(bean);
            }

            return txntypes;

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

    public int getID(Connection con) throws SQLException, Exception {

        int num = -1;
        PreparedStatement ps = null;
        //ResultSet rs = null;
        try {
            String query = "SELECT MAX(ID) FROM ECMS_ONLINE_GLACCOUNTS";

            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            while (rs.next()) {
                num = rs.getInt("MAX(ID)");
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
        return num + 1;
    }

    public int assignTxns(Connection OnlineCon, Connection cmsCon, AssignTxnBean bean, String txnCode) throws Exception {
        int i = -1;
        int id = 0;
        PreparedStatement ps = null;
        try {

            String query = "INSERT INTO ECMS_ONLINE_GLACCOUNTS (ID,LCSTATUS,LCID,TXNTYPE,CREDITACCOUNT,DEBITACCOUNT) VALUES(?,?,?,?,?,?)";

            ps = OnlineCon.prepareStatement(query);

            id = this.getID(OnlineCon);

            ps.setString(1, String.valueOf(id));

            ps.setString(2, bean.getChanekOrLisnr());
            ps.setString(3, bean.getChOrLsType());
            ps.setString(4, txnCode);
            ps.setString(5, bean.getGlAccNo());//credit
            ps.setString(6, bean.getDrGlAccNo());//debit

            i = ps.executeUpdate();

        } catch (SQLException ex) {
            throw ex;
        }
        //insert data to mibbackend
        try {
            String query2 = "INSERT INTO GLTRANSACTION (ID,LCSTATUS,LCTYPE,TRANSACTIONCODE,CREDITACCOUNT,DEBITACCOUNT) VALUES (?,?,?,?,?,?)";

            ps = cmsCon.prepareStatement(query2);
            ps.setString(1, String.valueOf(id));
            ps.setString(2, bean.getChanekOrLisnr());
            ps.setString(3, bean.getChOrLsType());
            ps.setString(4, txnCode);
            ps.setString(5, bean.getGlAccNo());//credit
            ps.setString(6, bean.getDrGlAccNo());//debit

            i += ps.executeUpdate();

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

    public int deleteglAcconut(Connection OnlineCon, Connection cmsCon, AssignTxnBean bean, String txnCode) throws SQLException, Exception {//meka hithanna passe karanna
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM ECMS_ONLINE_GLACCOUNTS WHERE LCSTATUS=? and LCID=? and TXNTYPE=? and CREDITACCOUNT=? and DEBITACCOUNT=?";

            ps = OnlineCon.prepareStatement(query);
            ps.setString(1, bean.getChanekOrLisnr());
            ps.setString(2, bean.getChOrLsType());
            ps.setString(3, txnCode);
            ps.setString(4, bean.getGlAccNo());
            ps.setString(5, bean.getDrGlAccNo());

            i = ps.executeUpdate();

        } catch (SQLException ex) {
            throw ex;
        }
        try {
            String query2 = "DELETE FROM GLTRANSACTION WHERE LCSTATUS=? and LCTYPE=? and TRANSACTIONCODE=? and CREDITACCOUNT=? and DEBITACCOUNT=?";
            ps = cmsCon.prepareStatement(query2);
            ps.setString(1, bean.getChanekOrLisnr());
            ps.setString(2, bean.getChOrLsType());
            ps.setString(3, txnCode);
            ps.setString(4, bean.getGlAccNo());
            ps.setString(5, bean.getDrGlAccNo());
            
            i+=ps.executeUpdate();

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    //rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return i;
    }

    public List<AssignTxnBean> getOnlineGlAccounts(Connection OnlineCon) throws Exception {

        List<AssignTxnBean> allOnlineGLAccs = new ArrayList<AssignTxnBean>();
        PreparedStatement ps = null;
        String query = "";

        try {
            query = "SELECT min(ID),LCSTATUS,LCID,CREDITACCOUNT,DEBITACCOUNT "
                    + "FROM ECMS_ONLINE_GLACCOUNTS group by LCSTATUS,LCID,CREDITACCOUNT,DEBITACCOUNT";

//            query2 = "SELECT min(gl.ID),gl.LCSTATUS,gl.LCID,gl.ACCOUNT, ct.DESCRIPTION as ctdes, lt.DESCRIPTION as ltdes "
//                    + "FROM ECMS_ONLINE_GLACCOUNTS gl, ECMS_ONLINE_CHANNEL_TYPE ct, ECMS_ONLINE_LISTENER_TYPE lt "
//                    + "where ct.CODE=gl.LCID and lt.CODE=gl.LCID group by gl.LCSTATUS,gl.LCID,gl.ACCOUNT,ct.DESCRIPTION,lt.DESCRIPTION";
            ps = OnlineCon.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {

                AssignTxnBean bean = new AssignTxnBean();
                bean.setId(rs.getString("min(ID)"));

                bean.setChanekOrLisnr(rs.getString("LCSTATUS"));
                if (rs.getString("LCSTATUS").equals("1")) {
                    bean.setChanekOrLisnrDes("Channel");

                } else if (rs.getString("LCSTATUS").equals("2")) {
                    bean.setChanekOrLisnrDes("Listener");

                }
                bean.setChOrLsType(rs.getString("LCID"));
                bean.setGlAccNo(rs.getString("CREDITACCOUNT"));//credit gl acc no
                bean.setDrGlAccNo(rs.getString("DEBITACCOUNT"));//debit gl acc no

                String des = this.getDescription(OnlineCon, bean.getChOrLsType(), bean.getChanekOrLisnr());
                bean.setChOrLsTypeDes(des);

                // bean.setGlAccDes(rs.getString("ACCOUNT"));
                allOnlineGLAccs.add(bean);
            }

            return allOnlineGLAccs;

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

    public String getDescription(Connection OnlineCon, String id, String col) throws Exception {

        PreparedStatement ps = null;
        String des = "";
        String query = "";
        ResultSet rs2 = null;

        try {

            if (col.equals("1")) {
                query = "select DESCRIPTION from ECMS_ONLINE_CHANNEL_TYPE where CODE=?";
            } else if (col.equals("2")) {
                query = "select DESCRIPTION from ECMS_ONLINE_LISTENER_TYPE where CODE=?";
            }

            ps = OnlineCon.prepareStatement(query);

            ps.setString(1, id);

            rs2 = ps.executeQuery();
            while (rs2.next()) {
                des = rs2.getString("DESCRIPTION");
            }
            return des;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs2 != null) {
                    rs2.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }

    }

    public AssignTxnBean viewSelected(Connection OnlineCon, String id, String accNo, String drAccNo, String col, String clType) throws Exception {

        AssignTxnBean bean = new AssignTxnBean();
        PreparedStatement ps = null;
        String query = "";

        try {
            query = "SELECT gl.ID,gl.LCSTATUS,gl.LCID,gl.CREDITACCOUNT,gl.DEBITACCOUNT "
                    + "FROM ECMS_ONLINE_GLACCOUNTS gl "
                    + "where gl.ID=? and gl.CREDITACCOUNT=? and gl.DEBITACCOUNT=? and gl.LCSTATUS=? and gl.LCID=? ";

            ps = OnlineCon.prepareStatement(query);

            ps.setString(1, id);
            ps.setString(2, accNo);
            ps.setString(3, drAccNo);
            ps.setString(4, col);
            ps.setString(5, clType);

            rs = ps.executeQuery();

            while (rs.next()) {

                bean.setId(rs.getString("ID"));
                bean.setChanekOrLisnr(rs.getString("LCSTATUS"));

                if (rs.getString("LCSTATUS").equals("1")) {
                    bean.setChanekOrLisnrDes("Channel");

                } else if (rs.getString("LCSTATUS").equals("2")) {
                    bean.setChanekOrLisnrDes("Listener");

                }
                bean.setChOrLsType(rs.getString("LCID"));

                String des = this.getDescription(OnlineCon, rs.getString("LCID"), rs.getString("LCSTATUS"));
                bean.setChOrLsTypeDes(des);

                bean.setGlAccNo(rs.getString("CREDITACCOUNT"));
                bean.setGlAccDes(rs.getString("CREDITACCOUNT"));

                bean.setDrGlAccNo(rs.getString("DEBITACCOUNT"));

            }

            return bean;

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
}
