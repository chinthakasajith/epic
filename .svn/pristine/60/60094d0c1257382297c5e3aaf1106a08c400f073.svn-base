/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.persistance;

import com.epic.cms.admin.controlpanel.transactionmgt.bean.ChannelIncomeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.OnlineTxnTypeBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.TypeMgtBean;
import com.epic.cms.switchcontrol.chanelconfig.bean.ChannelTypeBean;
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
public class TxnTypeMgtPersistance {

    private ResultSet rs;

    /**
     * Get Transaction Type details from table
     * @param con
     * @return
     * @throws Exception 
     */
    public List<TypeMgtBean> getTxnTypeDetails(Connection con) throws Exception {
        PreparedStatement getAllByCatStat = null;
        List<TypeMgtBean> txnTypeDetails = new ArrayList<TypeMgtBean>();
        try {
            getAllByCatStat = con.prepareStatement("SELECT TXN.TRANSACTIONCODE, TXN.DESCRIPTION, "
                    + " TXN.FINANCIALSTATUS, STAT.DESCRIPTION AS STDESCRIPTION, TXN.STATUS, TXN.LASTUPDATEDUSER, "
                    + " TXN.LASTUPDATEDDATE, TXN.CREATETIME, TXN.VISACODE, TXN.MASTERCODE, TXN.AMEXCODE, TXN.ONLINETXNTYPE "
                    + " FROM TRANSACTIONTYPE TXN, STATUS STAT"
                    + " WHERE TXN.STATUS=STAT.STATUSCODE ORDER BY TXN.DESCRIPTION ASC");

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                TypeMgtBean type = new TypeMgtBean();


                type.setTransactionTypeCode(rs.getString("TRANSACTIONCODE"));

                type.setDescription(rs.getString("DESCRIPTION"));

                type.setFinancialStatus(rs.getString("FINANCIALSTATUS"));

                type.setStatusCode(rs.getString("STDESCRIPTION"));

                type.setTrueStatusCode(rs.getString("STATUS"));

                type.setLastUpdateUser(rs.getString("LASTUPDATEDUSER"));

                type.setLastUpdateDate(rs.getDate("LASTUPDATEDDATE"));

                type.setCreateDate(rs.getDate("CREATETIME"));

                type.setVisaCode(rs.getString("VISACODE"));

                type.setMasterCode(rs.getString("MASTERCODE"));

                type.setAmexCode(rs.getString("AMEXCODE"));

                type.setOnlineTxnCode(rs.getString("ONLINETXNTYPE"));


                txnTypeDetails.add(type);

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getAllByCatStat.close();

        }
        return txnTypeDetails;
    }

    /**
     * get transaction type details from online db
     * @param conOnline
     * @return
     * @throws Exception 
     */
    public List<OnlineTxnTypeBean> getOnlineTxnType(Connection conOnline) throws Exception {
        PreparedStatement getAllByCatStat = null;
        List<OnlineTxnTypeBean> onlineTxnTypeList = new ArrayList<OnlineTxnTypeBean>();
        try {
            getAllByCatStat = conOnline.prepareStatement("SELECT TC.TYPECODE, TC.DESCRYPTION "
                    + " FROM ECMS_ONLINE_TXNTYPE TC ");

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                OnlineTxnTypeBean type = new OnlineTxnTypeBean();


                type.setTxnCode(Integer.toString(rs.getInt("TYPECODE")));
                type.setDescription(rs.getString("DESCRYPTION"));




                onlineTxnTypeList.add(type);

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getAllByCatStat.close();

        }
        return onlineTxnTypeList;
    }

    /**
     * get channel details from online db
     * @param conOnline
     * @return
     * @throws Exception 
     */
    public List<ChannelTypeBean> getOnlineChannel(Connection conOnline) throws Exception {
        PreparedStatement getAllByCatStat = null;
        List<ChannelTypeBean> onlineChannelList = new ArrayList<ChannelTypeBean>();
        try {
            getAllByCatStat = conOnline.prepareStatement("SELECT C.CODE,C.DESCRIPTION,C.STATUS"
                    + " FROM ECMS_ONLINE_CHANNEL_TYPE C WHERE C.STATUS='1' ");

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                ChannelTypeBean type = new ChannelTypeBean();


                type.setCode(Integer.toString(rs.getInt("CODE")));
                type.setDescription(rs.getString("DESCRIPTION"));
                type.setStatus(Integer.toString(rs.getInt("STATUS")));




                onlineChannelList.add(type);

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getAllByCatStat.close();

        }
        return onlineChannelList;
    }

    /**
     * get channel details 
     * @param con
     * @param transactionTypeCode
     * @return
     * @throws Exception 
     */
    public List<ChannelIncomeBean> getChannelToUpdate(Connection con, String transactionTypeCode) throws Exception {
        List<ChannelIncomeBean> incomeChannelList = new ArrayList<ChannelIncomeBean>();
        PreparedStatement getAllChannelStat = null;

        try {
            getAllChannelStat = con.prepareStatement("SELECT TC.TRANSACTIONCODE,TC.CHANNELID,TC.MTI,"
                    + "TC.PROCESSINGCODE,TC.ONLINETXNTYPE"
                    + " FROM TRANSACTIONTYPECHANNEL TC WHERE TC.TRANSACTIONCODE=? ");

            getAllChannelStat.setString(1, transactionTypeCode);

            rs = getAllChannelStat.executeQuery();

            while (rs.next()) {

                ChannelIncomeBean type = new ChannelIncomeBean();


                type.setTransactionTypeCode(rs.getString("TRANSACTIONCODE"));
                type.setChannelId(rs.getString("CHANNELID"));
                type.setMti(rs.getString("MTI"));
                type.setProcessingCode(rs.getString("PROCESSINGCODE"));
                type.setOnlineTxnCode(rs.getString("ONLINETXNTYPE"));




                incomeChannelList.add(type);

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getAllChannelStat.close();

        }

        return incomeChannelList;
    }

    public String getTxnDescriptionByCode(Connection conOnline, String txnCode) throws Exception {
        String description = null;
        PreparedStatement getAllChannelStat = null;

        try {
            getAllChannelStat = conOnline.prepareStatement(" SELECT TC.DESCRYPTION "
                    + " FROM ECMS_ONLINE_TXNTYPE TC WHERE TC.TYPECODE = ? ");

            getAllChannelStat.setInt(1, Integer.parseInt(txnCode));


            rs = getAllChannelStat.executeQuery();

            while (rs.next()) {

                description = rs.getString("DESCRYPTION");
                if (description != null) {
                    break;
                }
            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getAllChannelStat.close();
        }
        return description;
    }

    /**
     * Insert New Database Table Entry
     * @param con
     * @param type
     * @return
     * @throws Exception 
     */
    public boolean insertTxnType(Connection con, TypeMgtBean type) throws Exception {
        boolean success = false;
        PreparedStatement insertTxnStat = null;
        try {
            insertTxnStat = con.prepareStatement("INSERT INTO TRANSACTIONTYPE(TRANSACTIONCODE,"
                    + "DESCRIPTION,FINANCIALSTATUS,STATUS,LASTUPDATEDUSER,LASTUPDATEDDATE,CREATETIME,"
                    + "VISACODE,MASTERCODE,AMEXCODE,ONLINETXNTYPE)"
                    + " VALUES(?,?,?,?,?,SYSDATE,SYSDATE,?,?,?,?) ");



            insertTxnStat.setString(1, type.getTransactionTypeCode());

            insertTxnStat.setString(2, type.getDescription());

            insertTxnStat.setString(3, type.getFinancialStatus());

            insertTxnStat.setString(4, type.getTrueStatusCode());

            insertTxnStat.setString(5, type.getLastUpdateUser());

            insertTxnStat.setString(6, type.getVisaCode());

            insertTxnStat.setString(7, type.getMasterCode());

            insertTxnStat.setString(8, type.getAmexCode());

            insertTxnStat.setString(9, type.getOnlineTxnCode());

            insertTxnStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertTxnStat.close();
        }
        return success;
    }

    /**
     * insert channel details to db
     * @param con
     * @param channelList
     * @return
     * @throws Exception 
     */
    public boolean insertChannel(Connection con, List<ChannelIncomeBean> channelList) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement(" INSERT INTO TRANSACTIONTYPECHANNEL(TRANSACTIONCODE,CHANNELID,"
                    + " MTI,PROCESSINGCODE,ONLINETXNTYPE) VALUES(?,?,?,?,?)");

            for (int i = 0; i < channelList.size(); i++) {

                success = false;

                ChannelIncomeBean channel = new ChannelIncomeBean();
                channel = channelList.get(i);

                insertStat.setString(1, channel.getTransactionTypeCode());
                insertStat.setString(2, channel.getChannelId());
                insertStat.setString(3, channel.getMti());
                insertStat.setString(4, channel.getProcessingCode());
                insertStat.setString(5, channel.getOnlineTxnCode());


                insertStat.executeUpdate();

                success = true;
            }


        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    /**
     * insert channel details to online db 
     * @param conOnline
     * @param channelList
     * @return
     * @throws Exception 
     */
    public boolean insertChannelToOnline(Connection conOnline, List<ChannelIncomeBean> channelList) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = conOnline.prepareStatement(" INSERT INTO ECMS_ONLINE_DEFINEDTXN(MTI,PROCESSINGCODE,"
                    + " STATUS,TXNTYPECODE,CHANNELTYPECODE,DESCRIPTION) VALUES(?,?,?,?,?,?)");

            for (int i = 0; i < channelList.size(); i++) {

                success = false;

                ChannelIncomeBean channel = new ChannelIncomeBean();
                channel = channelList.get(i);

                insertStat.setString(1, channel.getMti());
                insertStat.setString(2, channel.getProcessingCode());
                insertStat.setInt(3, channel.getStatusToOnline());
                insertStat.setInt(4, Integer.parseInt(channel.getOnlineTxnCode()));
                insertStat.setInt(5, Integer.parseInt(channel.getChannelId()));
                insertStat.setString(6, channel.getTxnDescription());

                insertStat.executeUpdate();

                success = true;
            }


        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    /**
     * Update Database Table Entry
     * @param con
     * @param currency
     * @return
     * @throws Exception 
     */
    public boolean updateTxnType(Connection con, TypeMgtBean type) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE TRANSACTIONTYPE SET DESCRIPTION=?,FINANCIALSTATUS=?,"
                    + " STATUS=?,LASTUPDATEDUSER=?,LASTUPDATEDDATE=SYSDATE,VISACODE=?,MASTERCODE=?,AMEXCODE=?,"
                    + " ONLINETXNTYPE=?,CREATETIME=SYSDATE WHERE TRANSACTIONCODE=? ");

            //updateStat.setString(1, type.getTransactionTypeCode());

            updateStat.setString(1, type.getDescription());
            updateStat.setString(2, type.getFinancialStatus());
            updateStat.setString(3, type.getTrueStatusCode());
            updateStat.setString(4, type.getLastUpdateUser());
            updateStat.setString(5, type.getVisaCode());
            updateStat.setString(6, type.getMasterCode());
            updateStat.setString(7, type.getAmexCode());
            updateStat.setString(8, type.getOnlineTxnCode());
            updateStat.setString(9, type.getTransactionTypeCode());


            updateStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    /**
     * 
     * @param con
     * @param channelList
     * @return
     * @throws Exception 
     */
//    public boolean updateChannel(Connection con, List<ChannelIncomeBean> channelList) throws Exception {
//        boolean success = false;
//        PreparedStatement updateStat = null;
//        try {
//            updateStat = con.prepareStatement("UPDATE TRANSACTIONTYPECHANNEL SET "
//                    + " CHANNELID=?,MTI=?,PROCESSINGCODE=?,ONLINETXNTYPE=? WHERE TRANSACTIONCODE=? ");
//
//            for (int i = 0; i < channelList.size(); i++) {
//
//                success = false;
//
//                ChannelIncomeBean channel = new ChannelIncomeBean();
//                channel = channelList.get(i);
//
//
//                updateStat.setString(1, channel.getChannelId());
//                updateStat.setString(2, channel.getMti());
//                updateStat.setString(3, channel.getProcessingCode());
//                updateStat.setString(4, channel.getOnlineTxnCode());
//                updateStat.setString(5, channel.getTransactionTypeCode());
//
//                updateStat.executeUpdate();
//
//                success = true;
//            }
//
//
//            updateStat.executeUpdate();
//            success = true;
//
//        } catch (SQLException ex) {
//            throw ex;
//        } finally {
//            updateStat.close();
//        }
//        return success;
//    }
    /**
     * update online db channel
     * @param conOnline
     * @param channelList
     * @return
     * @throws Exception 
     */
//    public boolean updateChannelOnline(Connection conOnline, List<ChannelIncomeBean> channelList) throws Exception {
//        boolean success = false;
//        PreparedStatement updateStat = null;
//        try {
//            updateStat = conOnline.prepareStatement("UPDATE ECMS_ONLINE_DEFINEDTXN SET "
//                    + " CHANNELTYPECODE=?,MTI=?,PROCESSINGCODE=?,STATUS=? WHERE TTXNTYPECODE=? ");
//
//            for (int i = 0; i < channelList.size(); i++) {
//
//                success = false;
//
//                ChannelIncomeBean channel = new ChannelIncomeBean();
//                channel = channelList.get(i);
//
//
//                updateStat.setInt(1, Integer.parseInt(channel.getChannelId()));
//                updateStat.setString(2, channel.getMti());
//                updateStat.setString(3, channel.getProcessingCode());
//                updateStat.setInt(4, Integer.parseInt(channel.getStatus()));
//                updateStat.setInt(5, Integer.parseInt(channel.getOnlineTxnCode()));
//
//                updateStat.executeUpdate();
//
//                success = true;
//            }
//
//
//            updateStat.executeUpdate();
//            success = true;
//
//        } catch (SQLException ex) {
//            throw ex;
//        } finally {
//            updateStat.close();
//        }
//        return success;
//    }
    /**
     * Delete Database Table Entry
     * @param con
     * @param type
     * @return
     * @throws Exception 
     */
    public boolean deleteTxnType(Connection con, TypeMgtBean type) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = con.prepareStatement("DELETE TRANSACTIONTYPE WHERE TRANSACTIONCODE=?");



            deleteStat.setString(1, type.getTransactionTypeCode());


            deleteStat.executeUpdate();
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
            deleteStat.close();
        }
        return success;
    }

    public boolean deleteChannel(Connection con, String transactionTypeCode) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = con.prepareStatement("DELETE FROM TRANSACTIONTYPECHANNEL WHERE TRANSACTIONCODE=?");

            deleteStat.setString(1, transactionTypeCode);

            deleteStat.executeUpdate();
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
            deleteStat.close();
        }
        return success;
    }

    public boolean deleteChannelOnline(Connection conOnline, String onlineTxnCode) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = conOnline.prepareStatement("DELETE FROM ECMS_ONLINE_DEFINEDTXN WHERE TXNTYPECODE=?");

            deleteStat.setInt(1, Integer.parseInt(onlineTxnCode));

            deleteStat.executeUpdate();
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
            deleteStat.close();
        }
        return success;
    }

    public boolean checkDataExsistence(Connection conOnline, ChannelIncomeBean incomeBean) throws Exception {
        PreparedStatement getAllByCatStat = null;
        boolean success = false;
        try {
            getAllByCatStat = conOnline.prepareStatement("SELECT T.DESCRIPTION"
                    + " FROM ECMS_ONLINE_DEFINEDTXN T WHERE T.MTI=? AND T.PROCESSINGCODE =? ");

            getAllByCatStat.setString(1, incomeBean.getMti());
            getAllByCatStat.setString(2, incomeBean.getProcessingCode());
            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                success = true;

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getAllByCatStat.close();

        }
        return success;
    }
}
