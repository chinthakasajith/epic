/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.AquireBinBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.AquireBinFormBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author badrika
 */
public class AquireBinManagementPersistance {

    private ArrayList<AquireBinBean> AquireBinBeanList;
    private ArrayList<AquireBinFormBean> ChannelList, CardTypeList, entryModeList, statusList;
    private HashMap<String, String> cardKeyList = null;
    // private ArrayList<AquireBinFormBean> CardTypeList;
    //  private ArrayList<AquireBinFormBean> entryModeList,statusList;

    /**
     * to get all aquire bin detail list
     * @param con
     * @return
     * @throws Exception 
     */
//    public List<AquireBinBean> getAllAquireBinDetailList(Connection con) throws Exception {
//
//        ResultSet rs = null;
//        PreparedStatement ps = null;
//
//        try {
//            ps = con.prepareStatement("SELECT BIN.BINNO,BIN.OWNERSHIP,BIN.SENDCHANNELID,BIN.CARDKEYID,"
//                    + " BIN.STATUS,OW.DESCRIPTION AS OWNERDES,ST.DESCRIPTION AS STDES,"
//                    + " SCH.CHANNELNAME AS SENDCHANELDES,CK.DESCRIPTION AS CKDESCRIPTION"
//                    + " FROM ECMS_ONLINE_BIN BIN,ECMS_ONLINE_BIN_OWNERSHIP OW,ECMS_ONLINE_STATUS ST,"
//                    + " ECMS_ONLINE_CHANNELS SCH,ECMS_ONLINE_CARDKEYS CK  "
//                    + " WHERE BIN.OWNERSHIP=OW.CODE AND BIN.STATUS=ST.CODE "
//                    + " AND BIN.SENDCHANNELID=SCH.CHANNELID AND CK.CARDKEYID = BIN.CARDKEYID");
//
//            rs = ps.executeQuery();
//            AquireBinBeanList = new ArrayList<AquireBinBean>();
//
//            while (rs.next()) {
//                AquireBinBean resultBean = new AquireBinBean();
//
//                resultBean.setBinNumber(rs.getString("BINNO"));
//                resultBean.setOwnership(rs.getString("OWNERSHIP"));
//                resultBean.setOwnershipDes(rs.getString("OWNERDES"));
//                resultBean.setSendChanel(rs.getString("SENDCHANNELID"));
//                resultBean.setSendChanelDes(rs.getString("SENDCHANELDES"));
//                resultBean.setStatus(rs.getString("STATUS"));
//                resultBean.setStatusDes(rs.getString("STDES"));
//                resultBean.setCardKey(Integer.toString(rs.getInt("CARDKEYID")));
//                resultBean.setCardKeyDes(rs.getString("CKDESCRIPTION"));
//
//                AquireBinBeanList.add(resultBean);
//
//            }
//
//        } catch (Exception ex) {
//            throw ex;
//        } finally {
//            rs.close();
//            ps.close();
//
//        }
//
//        return AquireBinBeanList;
//    }

    /**
     * to get all aquire bin detail list
     * @param con
     * @return
     * @throws Exception 
     */
    public List<AquireBinBean> getAllAquireBinDetailList(Connection con) throws Exception {

        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement("SELECT BIN.BINNO,BIN.OWNERSHIP,BIN.SENDCHANNELID,"
                    + " BIN.STATUS,OW.DESCRIPTION AS OWNERDES,ST.DESCRIPTION AS STDES,"
                    + " SCH.CHANNELNAME AS SENDCHANELDES,CK.DESCRIPTION AS CKDESCRIPTION"
                    + " FROM ECMS_ONLINE_BIN BIN,ECMS_ONLINE_BIN_OWNERSHIP OW,ECMS_ONLINE_STATUS ST,"
                    + " ECMS_ONLINE_CHANNELS SCH,ECMS_ONLINE_CARDKEYS CK  "
                    + " WHERE BIN.OWNERSHIP=OW.CODE AND BIN.STATUS=ST.CODE "
                    + " AND BIN.SENDCHANNELID=SCH.CHANNELID ");

            rs = ps.executeQuery();
            AquireBinBeanList = new ArrayList<AquireBinBean>();

            while (rs.next()) {
                AquireBinBean resultBean = new AquireBinBean();

                resultBean.setBinNumber(rs.getString("BINNO"));
                resultBean.setOwnership(rs.getString("OWNERSHIP"));
                resultBean.setOwnershipDes(rs.getString("OWNERDES"));
                resultBean.setSendChanel(rs.getString("SENDCHANNELID"));
                resultBean.setSendChanelDes(rs.getString("SENDCHANELDES"));
                resultBean.setStatus(rs.getString("STATUS"));
                resultBean.setStatusDes(rs.getString("STDES"));
                //resultBean.setCardKey(Integer.toString(rs.getInt("CARDKEYID")));
                resultBean.setCardKeyDes(rs.getString("CKDESCRIPTION"));

                AquireBinBeanList.add(resultBean);

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();

        }

        return AquireBinBeanList;
    }    
    
    
    /**
     * to get all channel list
     * @param con
     * @return
     * @throws Exception 
     */
    public List<AquireBinFormBean> getChannelList(Connection con) throws Exception {

        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement("SELECT CHANNELID, CHANNELNAME FROM ECMS_ONLINE_CHANNELS");

            rs = ps.executeQuery();
            ChannelList = new ArrayList<AquireBinFormBean>();

            while (rs.next()) {
                AquireBinFormBean resultBean = new AquireBinFormBean();

                resultBean.setChannelID(rs.getString("CHANNELID"));
                resultBean.setChannelName(rs.getString("CHANNELNAME"));

                ChannelList.add(resultBean);

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();

        }

        return ChannelList;
    }

    /**
     * to get all card type list
     * @param con
     * @return
     * @throws Exception 
     */
    public List<AquireBinFormBean> getCardTypeList(Connection con) throws Exception {

        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement("SELECT CODE, DESCRIPTION FROM ECMS_ONLINE_CARD_TYPE");

            rs = ps.executeQuery();
            CardTypeList = new ArrayList<AquireBinFormBean>();

            while (rs.next()) {
                AquireBinFormBean resultBean = new AquireBinFormBean();

                resultBean.setCardType(rs.getString("CODE"));
                resultBean.setCardTypeDes(rs.getString("DESCRIPTION"));



                CardTypeList.add(resultBean);

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();

        }

        return CardTypeList;
    }

    public HashMap<String, String> getCardKeyList(Connection conOnline) throws Exception {

        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = conOnline.prepareStatement("SELECT CARDKEYID, DESCRIPTION FROM ECMS_ONLINE_CARDKEYS");

            rs = ps.executeQuery();
            cardKeyList = new HashMap<String, String>();

            while (rs.next()) {

                cardKeyList.put(Integer.toString(rs.getInt("CARDKEYID")), rs.getString("DESCRIPTION"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();

        }

        return cardKeyList;
    }

    /**
     * to get all entry mode list
     * @param con
     * @return
     * @throws Exception 
     */
//    public List<AquireBinFormBean> getEntryModeList(Connection con) throws Exception {
//
//        ResultSet rs = null;
//        PreparedStatement ps = null;
//
//        try {
//            ps = con.prepareStatement("SELECT CODE, DESCRIPTION FROM ECMS_ONLINE_ENTRY_MODE");
//
//            rs = ps.executeQuery();
//            entryModeList = new ArrayList<AquireBinFormBean>();
//
//            while (rs.next()) {
//                AquireBinFormBean resultBean = new AquireBinFormBean();
//
//                resultBean.setEntryMode(rs.getString("CODE"));
//                resultBean.setEntryModeDes(rs.getString("DESCRIPTION"));
//
//
//
//                entryModeList.add(resultBean);
//
//            }
//
//        } catch (Exception ex) {
//            throw ex;
//        } finally {
//            rs.close();
//            ps.close();
//
//        }
//
//        return entryModeList;
//    }
    /**
     * to get all status list
     * @param con
     * @return
     * @throws Exception 
     */
    public List<AquireBinFormBean> getStatusList(Connection con) throws Exception {

        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement("SELECT CODE, DESCRIPTION, CATEGORYCODE FROM ECMS_ONLINE_STATUS WHERE CATEGORYCODE='2' ");

            rs = ps.executeQuery();
            statusList = new ArrayList<AquireBinFormBean>();

            while (rs.next()) {
                AquireBinFormBean resultBean = new AquireBinFormBean();

                resultBean.setStatus(rs.getString("CODE"));
                resultBean.setStatusDes(rs.getString("DESCRIPTION"));



                statusList.add(resultBean);

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();

        }

        return statusList;
    }

    /**
     * to add new record
     * @param con
     * @param aqBean
     * @return
     * @throws Exception 
     */
    public boolean insertAquireBin(Connection con, AquireBinBean aqBean) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO ECMS_ONLINE_BIN(BINNO,OWNERSHIP,SENDCHANNELID,"
                    + "STATUS,CREATETIME,LASTUPDATETIME,LASTUPDATEUSER)"
                    + "VALUES(?,?,?,?,SYSDATE,?,?) ");

            insertStat.setString(1, aqBean.getBinNumber());
            insertStat.setString(2, aqBean.getOwnership());
            insertStat.setString(3, aqBean.getSendChanel());
           // insertStat.setString(4, aqBean.getCardKey());
            insertStat.setString(4, aqBean.getStatus());
            insertStat.setTimestamp(5, new Timestamp(aqBean.getLastUpdatedTime().getTime()));
            insertStat.setString(6, aqBean.getLastUpdatedUser());


            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    /**
     * to update existing record
     * @param con
     * @param aqBean
     * @return
     * @throws Exception 
     */
    public boolean updateAquireBin(Connection con, AquireBinBean aqBean) throws Exception {
        boolean success = false;
        PreparedStatement updatetStat = null;
        try {
            updatetStat = con.prepareStatement("UPDATE ECMS_ONLINE_BIN SET OWNERSHIP=?,SENDCHANNELID=?,"
                    + " STATUS=?,LASTUPDATETIME=?,LASTUPDATEUSER=? WHERE BINNO=? ");


            updatetStat.setString(1, aqBean.getOwnership());
            updatetStat.setString(2, aqBean.getSendChanel());
           // updatetStat.setString(3, aqBean.getCardKey());
            updatetStat.setString(3, aqBean.getStatus());
            updatetStat.setTimestamp(4, new Timestamp(aqBean.getLastUpdatedTime().getTime()));
            updatetStat.setString(5, aqBean.getLastUpdatedUser());

            updatetStat.setString(6, aqBean.getBinNumber());


            updatetStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            updatetStat.close();
        }
        return success;
    }

    /**
     * to delete existing record
     * @param con
     * @param aqBean
     * @return
     * @throws Exception 
     */
    public boolean deleteAquireBin(Connection con, AquireBinBean aqBean) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = con.prepareStatement("DELETE FROM ECMS_ONLINE_BIN WHERE BINNO=? ");
            deleteStat.setString(1, aqBean.getBinNumber());

            deleteStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            deleteStat.close();
        }
        return success;
    }
}
