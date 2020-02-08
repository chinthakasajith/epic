/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardBinBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardDomainsBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.ProductionModeBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 *
 * @author chanuka
 */
public class CardBinMgtPersistance {
    
    private List<CardBinBean> cardBinBeanLst = null;
    private List<ProductionModeBean> productionModeList = null;
    private List<CardDomainsBean> cardDomainsList = null;
    private HashMap<String, String> currencyMap;
    
//    public List<CardBinBean> getAllCardBinDetailsList(Connection con) throws Exception {
//        
//        ResultSet rs = null;
//        PreparedStatement getAllUserRole = null;
//        
//        try {
//            getAllUserRole = con.prepareStatement("SELECT TC.BIN,TC.DESCRIPTION,TC.CARDTYPE,CT.DESCRIPTION AS CARDTYPEDES, TC.ONUSSTATUS, TC.CARDDOMAIN, "
//                    + "TC.PRODUCTIONMODE,PM.DESCRIPTION AS PMDES, TC.STATUS, ST.DESCRIPTION AS STDES,TC.CURRENCYTYPECODE,CR.DESCRIPTION AS CURRDES,"
//                    + "TC.LASTUPDATEDUSER,TC.LASTUPDATEDTIME "
//                    + "FROM CARDBIN TC,CARDTYPE CT,STATUS ST,PRODUCTIONMODE PM,CURRENCY CR "
//                    + "WHERE TC.CARDTYPE=CT.CARDTYPECODE AND TC.STATUS=ST.STATUSCODE AND TC.PRODUCTIONMODE=PM.PRODUCTIONMODECODE "
//                    + "AND TC.CURRENCYTYPECODE=CR.CURRENCYNUMCODE");
//            
//            rs = getAllUserRole.executeQuery();
//            cardBinBeanLst = new ArrayList<CardBinBean>();
//            
//            while (rs.next()) {
//                CardBinBean resultBean = new CardBinBean();
//                
//                resultBean.setBinNumber(rs.getString("BIN"));
//                resultBean.setDescription(rs.getString("DESCRIPTION"));
//                resultBean.setCardType(rs.getString("CARDTYPE"));
//                resultBean.setCardTypeDes(rs.getString("CARDTYPEDES"));
//                
//                resultBean.setOnus(rs.getString("ONUSSTATUS"));
//                resultBean.setBinType(rs.getString("CARDDOMAIN"));
//                resultBean.setProductionMode(rs.getString("PRODUCTIONMODE"));
//                resultBean.setProductionModeDes(rs.getString("PMDES"));
//                resultBean.setStatus(rs.getString("STATUS"));
//                resultBean.setStatusDes(rs.getString("STDES"));
//                resultBean.setCurrTypeCode(rs.getString("CURRENCYTYPECODE"));
//                resultBean.setCurrTypeDes(rs.getString("CURRDES"));
//                resultBean.setLastupdateUser(rs.getString("LASTUPDATEDUSER"));
//                resultBean.setLastUpdateDate(rs.getTimestamp("LASTUPDATEDTIME"));
//                
//                cardBinBeanLst.add(resultBean);
//                
//            }
//            
//        } catch (Exception ex) {
//            throw ex;
//        } finally {
//            rs.close();
//            getAllUserRole.close();
//            
//        }
//        
//        return cardBinBeanLst;
//    }

    public List<CardBinBean> getAllCardBinDetailsList(Connection con) throws Exception {
        
        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;
        
        try {
            getAllUserRole = con.prepareStatement("SELECT TC.BIN,TC.DESCRIPTION,TC.CARDTYPE,CT.DESCRIPTION AS CARDTYPEDES, TC.ONUSSTATUS, TC.CARDDOMAIN, "
                    + "TC.STATUS, ST.DESCRIPTION AS STDES,TC.CURRENCYTYPECODE,CR.DESCRIPTION AS CURRDES,"
                    + "TC.LASTUPDATEDUSER,TC.LASTUPDATEDTIME "
                    + "FROM CARDBIN TC,CARDTYPE CT,STATUS ST,CURRENCY CR "
                    + "WHERE TC.CARDTYPE=CT.CARDTYPECODE AND TC.STATUS=ST.STATUSCODE AND "
                    + "TC.CURRENCYTYPECODE=CR.CURRENCYNUMCODE");
            
            rs = getAllUserRole.executeQuery();
            cardBinBeanLst = new ArrayList<CardBinBean>();
            
            while (rs.next()) {
                CardBinBean resultBean = new CardBinBean();
                
                resultBean.setBinNumber(rs.getString("BIN"));
                resultBean.setDescription(rs.getString("DESCRIPTION"));
                resultBean.setCardType(rs.getString("CARDTYPE"));
                resultBean.setCardTypeDes(rs.getString("CARDTYPEDES"));
                
                resultBean.setOnus(rs.getString("ONUSSTATUS"));
                resultBean.setBinType(rs.getString("CARDDOMAIN"));
//                resultBean.setProductionMode(rs.getString("PRODUCTIONMODE"));
//                resultBean.setProductionModeDes(rs.getString("PMDES"));
                resultBean.setStatus(rs.getString("STATUS"));
                resultBean.setStatusDes(rs.getString("STDES"));
                resultBean.setCurrTypeCode(rs.getString("CURRENCYTYPECODE"));
                resultBean.setCurrTypeDes(rs.getString("CURRDES"));
                resultBean.setLastupdateUser(rs.getString("LASTUPDATEDUSER"));
                resultBean.setLastUpdateDate(rs.getTimestamp("LASTUPDATEDTIME"));
                
                cardBinBeanLst.add(resultBean);
                
            }
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();
            
        }
        
        return cardBinBeanLst;
    }
    
    
    
    //  ----  To get productionmode list --------------
    public List<ProductionModeBean> getProductionModeList(Connection con) throws Exception {
        
        ResultSet rs = null;
        PreparedStatement getAllproductionmode = null;
        
        try {
            getAllproductionmode = con.prepareStatement("SELECT PRODUCTIONMODECODE, DESCRIPTION FROM PRODUCTIONMODE");
            
            rs = getAllproductionmode.executeQuery();
            productionModeList = new ArrayList<ProductionModeBean>();
            
            while (rs.next()) {
                ProductionModeBean resultBean = new ProductionModeBean();
                
                resultBean.setProductionModeCode(rs.getString("PRODUCTIONMODECODE"));
                resultBean.setDescription(rs.getString("DESCRIPTION"));
                
                
                productionModeList.add(resultBean);
                
            }
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllproductionmode.close();
            
        }
        
        return productionModeList;
    }

    //getCardDomainsList
    public List<CardDomainsBean> getCardDomainsList(Connection con) throws Exception {
        
        ResultSet rs = null;
        PreparedStatement getAllcardDomains = null;
        
        try {
            getAllcardDomains = con.prepareStatement("SELECT DOMAINID, DESCRIPTION FROM CARDDOMAIN");
            
            rs = getAllcardDomains.executeQuery();
            cardDomainsList = new ArrayList<CardDomainsBean>();
            
            while (rs.next()) {
                CardDomainsBean resultBean = new CardDomainsBean();
                
                resultBean.setDomainID(rs.getString("DOMAINID"));
                resultBean.setDescrip(rs.getString("DESCRIPTION"));
                
                
                cardDomainsList.add(resultBean);
                
            }
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllcardDomains.close();
            
        }
        
        return cardDomainsList;
    }
    //----------

    public boolean insertCardBin(Connection con, CardBinBean cardBinBean) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO CARDBIN(BIN,DESCRIPTION,CARDTYPE,ONUSSTATUS,CARDDOMAIN"
                    + ",STATUS,CURRENCYTYPECODE,LASTUPDATEDUSER,LASTUPDATEDTIME,CREATEDTIME) VALUES(?,?,?,?,?,?,?,?,SYSDATE,SYSDATE) ");
            
            insertStat.setString(1, cardBinBean.getBinNumber());
            insertStat.setString(2, cardBinBean.getDescription());
            insertStat.setString(3, cardBinBean.getCardType());
            
            insertStat.setString(4, cardBinBean.getOnus());
            insertStat.setString(5, cardBinBean.getBinType());
           // insertStat.setString(6, cardBinBean.getProductionMode());
            insertStat.setString(6, cardBinBean.getStatus());
            insertStat.setString(7, cardBinBean.getCurrTypeCode());
            insertStat.setString(8, cardBinBean.getLastupdateUser());
            
            
            insertStat.executeUpdate();
            success = true;
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }
    
    public boolean updateCardBin(Connection con, CardBinBean cardBinBean) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE CARDBIN SET CARDTYPE=?, DESCRIPTION=?, ONUSSTATUS=?, CARDDOMAIN=?,"
                    + "STATUS=?,CURRENCYTYPECODE=?,LASTUPDATEDUSER=?,LASTUPDATEDTIME=SYSDATE WHERE BIN=? ");
            
            updateStat.setString(1, cardBinBean.getCardType());
            updateStat.setString(2, cardBinBean.getDescription());
            
            updateStat.setString(3, cardBinBean.getOnus());
            updateStat.setString(4, cardBinBean.getBinType());
           // updateStat.setString(5, cardBinBean.getProductionMode());
            updateStat.setString(5, cardBinBean.getStatus());
            updateStat.setString(6, cardBinBean.getCurrTypeCode());
            updateStat.setString(7, cardBinBean.getLastupdateUser());
            
            
            updateStat.setString(8, cardBinBean.getBinNumber());
            
            updateStat.executeUpdate();
            success = true;
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }
    
    public String getProductCode(Connection con, String binNum) throws Exception {
        
        String proCode = "";
        ResultSet rs = null;
        PreparedStatement ps = null;
        
        try {
            ps = con.prepareStatement("SELECT PRODUCTCODE FROM CARDPRODUCTBIN WHERE BINCODE=?");
            
            ps.setString(1, binNum);
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                proCode = rs.getString("PRODUCTCODE");
            }
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return proCode;
    }
    
    public boolean deleteCardBin(Connection con, CardBinBean cardBinBean) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = con.prepareStatement("DELETE CARDBIN WHERE BIN=? ");
            deleteStat.setString(1, cardBinBean.getBinNumber());
            
            deleteStat.executeUpdate();
            success = true;
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            deleteStat.close();
        }
        return success;
    }
    
    public boolean insertBin(Connection con, Connection conToOnline, CardBinBean cardBinBean) throws SQLException, Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        PreparedStatement insertToOnline = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO CARDBIN(BIN,DESCRIPTION,CARDTYPE,ONUSSTATUS,CARDDOMAIN,STATUS,CURRENCYTYPECODE,LASTUPDATEDUSER) VALUES(?,?,?,?,?,?,?,?)");
            
            insertStat.setString(1, cardBinBean.getBinNumber());
            insertStat.setString(2, cardBinBean.getDescription());
            insertStat.setString(3, cardBinBean.getCardType());
            insertStat.setString(4, cardBinBean.getOnus());
            insertStat.setString(5, cardBinBean.getBinType());
            insertStat.setString(6, cardBinBean.getStatus());
            insertStat.setString(7, cardBinBean.getCurrTypeCode());
            insertStat.setString(8, cardBinBean.getLastupdateUser());
            
            insertStat.executeUpdate();
            
            insertToOnline = conToOnline.prepareStatement("INSERT INTO ECMS_ONLINE_BIN(BINNO,SENDCHANNELID,STATUS,LASTUPDATEUSER,OWNERSHIP)VALUES(?,?,?,?,?)");
            
            String status = cardBinBean.getStatus();
            if ("ACT".equals(status)) {
                status = "1";
            } else {
                status = "2";
            }
            
            String ownership = cardBinBean.getOnus();
            if ("YES".equals(ownership)) {
                ownership = "1";
            } else {
                ownership = "2";
            }
            
            insertToOnline.setString(1, cardBinBean.getBinNumber());
            insertToOnline.setString(2, cardBinBean.getSendingChannel());
            insertToOnline.setString(3, status);
            insertToOnline.setString(4, cardBinBean.getLastupdateUser());
            insertToOnline.setString(5, ownership);
            
            insertToOnline.executeUpdate();
            
            success = true;
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
            insertToOnline.close();
        }
        return success;
    }
    
    public List<CardBinBean> getAllBinDetailsList(Connection con, Connection conToOnline) throws Exception {
        
        ResultSet rs = null, rsOnline = null;
        PreparedStatement stmtBackend = null, stmtOnline = null;
        
        try {
            stmtBackend = con.prepareStatement("SELECT TC.BIN,TC.DESCRIPTION,TC.CARDTYPE,CT.DESCRIPTION AS CARDTYPEDES, TC.ONUSSTATUS, TC.CARDDOMAIN, "
                    + "TC.STATUS, ST.DESCRIPTION AS STDES,TC.CURRENCYTYPECODE,CR.DESCRIPTION AS CURRDES,"
                    + "TC.LASTUPDATEDUSER,TC.LASTUPDATEDTIME "
                    + "FROM CARDBIN TC,CARDTYPE CT,STATUS ST,CURRENCY CR "
                    + "WHERE TC.CARDTYPE=CT.CARDTYPECODE AND TC.STATUS=ST.STATUSCODE "
                    + "AND TC.CURRENCYTYPECODE=CR.CURRENCYNUMCODE");
            
            rs = stmtBackend.executeQuery();
            cardBinBeanLst = new ArrayList<CardBinBean>();
            ArrayList<CardBinBean> cardBinBeanLst1 = new ArrayList<CardBinBean>();
            
            while (rs.next()) {
                CardBinBean resultBean = new CardBinBean();
                
                resultBean.setBinNumber(rs.getString("BIN"));
                resultBean.setDescription(rs.getString("DESCRIPTION"));
                resultBean.setCardType(rs.getString("CARDTYPE"));
                resultBean.setCardTypeDes(rs.getString("CARDTYPEDES"));               
                resultBean.setOnus(rs.getString("ONUSSTATUS"));
                resultBean.setBinType(rs.getString("CARDDOMAIN"));
                resultBean.setStatus(rs.getString("STATUS"));
                resultBean.setStatusDes(rs.getString("STDES"));
                resultBean.setCurrTypeCode(rs.getString("CURRENCYTYPECODE"));
                resultBean.setCurrTypeDes(rs.getString("CURRDES"));
                resultBean.setLastupdateUser(rs.getString("LASTUPDATEDUSER"));
                resultBean.setLastUpdateDate(rs.getTimestamp("LASTUPDATEDTIME"));
                
                cardBinBeanLst.add(resultBean);
                
            }
            
            stmtOnline = conToOnline.prepareStatement("SELECT BIN.BINNO,BIN.SENDCHANNELID,SCH.CHANNELNAME FROM ECMS_ONLINE_BIN BIN,ECMS_ONLINE_CHANNELS SCH "
                    + "WHERE  BIN.SENDCHANNELID=SCH.CHANNELID");
            
            rsOnline = stmtOnline.executeQuery();
            
            while (rsOnline.next()) {
                CardBinBean resultBean = new CardBinBean();
                
                resultBean.setBinNumber(rsOnline.getString("BINNO"));
                resultBean.setSendingChannel(rsOnline.getString("SENDCHANNELID"));
                resultBean.setSendingChannelDes(rsOnline.getString("CHANNELNAME"));
                
                cardBinBeanLst1.add(resultBean);
            }
            
            for (int i = 0; cardBinBeanLst.size() > i; i++) {
                
                String binNo = cardBinBeanLst.get(i).getBinNumber();
                
                for (int j = 0; cardBinBeanLst1.size() > j; j++) {
                    if (binNo.equals(cardBinBeanLst1.get(j).getBinNumber())) {
                        cardBinBeanLst.get(i).setSendingChannelDes(cardBinBeanLst1.get(j).getSendingChannelDes());
                        cardBinBeanLst.get(i).setSendingChannel(cardBinBeanLst1.get(j).getSendingChannel());
                    }
                }
            }        
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            stmtBackend.close();
            
        }
        return cardBinBeanLst;
    }
    
    public boolean deleteBin(Connection con,Connection conToOnline,CardBinBean cardBinBean) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        PreparedStatement onlineDeleteStat = null;
        try {
            deleteStat = con.prepareStatement("DELETE CARDBIN WHERE BIN=? ");
            deleteStat.setString(1, cardBinBean.getBinNumber());
            
            deleteStat.executeUpdate();
            
            onlineDeleteStat = conToOnline.prepareStatement("DELETE ECMS_ONLINE_BIN WHERE BINNO=? ");
            onlineDeleteStat.setString(1, cardBinBean.getBinNumber());
            
            onlineDeleteStat.executeUpdate();
            
            success = true;
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            deleteStat.close();
            onlineDeleteStat.close();
        }
        return success;
    }
    
    public boolean updateBin(Connection con,Connection conToOnline, CardBinBean cardBinBean) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        PreparedStatement updateStatOnline = null;
        try {
            updateStat = con.prepareStatement("UPDATE CARDBIN SET CARDTYPE=?, DESCRIPTION=?, ONUSSTATUS=?, CARDDOMAIN=?,"
                    + "STATUS=?,CURRENCYTYPECODE=?,LASTUPDATEDUSER=?,LASTUPDATEDTIME=SYSDATE WHERE BIN=? ");
            
            updateStat.setString(1, cardBinBean.getCardType());
            updateStat.setString(2, cardBinBean.getDescription());          
            updateStat.setString(3, cardBinBean.getOnus());
            updateStat.setString(4, cardBinBean.getBinType());
            updateStat.setString(5, cardBinBean.getStatus());
            updateStat.setString(6, cardBinBean.getCurrTypeCode());
            updateStat.setString(7, cardBinBean.getLastupdateUser());                 
            updateStat.setString(8, cardBinBean.getBinNumber());         
            updateStat.executeUpdate();
            
            updateStatOnline = conToOnline.prepareStatement("UPDATE ECMS_ONLINE_BIN SET SENDCHANNELID=?, STATUS=?, LASTUPDATEUSER=?, OWNERSHIP=?, "
                    + "LASTUPDATETIME=SYSDATE WHERE BINNO=? ");
            
            String status = cardBinBean.getStatus();
            if ("ACT".equals(status)) {
                status = "1";
            } else {
                status = "2";
            }
            
            String ownership = cardBinBean.getOnus();
            if ("YES".equals(ownership)) {
                ownership = "1";
            } else {
                ownership = "2";
            }
           
            updateStatOnline.setString(1, cardBinBean.getSendingChannel());
            updateStatOnline.setString(2, status);          
            updateStatOnline.setString(3, cardBinBean.getLastupdateUser());
            updateStatOnline.setString(4, ownership);
            updateStatOnline.setString(5, cardBinBean.getBinNumber());           
            updateStatOnline.executeUpdate();
            
            success = true;
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            updateStat.close();
            updateStatOnline.close();
        }
        return success;
    }
    
    public HashMap<String, String> getCurrency(Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT CURRENCYNUMCODE,DESCRIPTION FROM CURRENCY");
            rs = ps.executeQuery();
            currencyMap = new HashMap<String, String>();

            while (rs.next()) {
                currencyMap.put(rs.getString("CURRENCYNUMCODE"), rs.getString("DESCRIPTION"));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }
        currencyMap=sortAlphabetically(currencyMap);
        return currencyMap;
    }
    
    private static HashMap<String, String> sortAlphabetically(HashMap<String, String> input) {
        Map<String, String> tempMap = new HashMap<String, String>();
        for (String wsState : input.keySet()){
            tempMap.put(wsState,input.get(wsState));
        }
        List<String> mapKeys = new ArrayList<String>(tempMap.keySet());
        List<String> mapValues = new ArrayList<String>(tempMap.values());
        HashMap<String, String> sortedMap = new LinkedHashMap<String, String>();
        TreeSet<String> sortedSet = new TreeSet<String>(mapValues);
        Object[] sortedArray = sortedSet.toArray();
        int size = sortedArray.length;
        for (int i=0; i<size; i++){
            sortedMap.put(mapKeys.get(mapValues.indexOf(sortedArray[i])), 
                          (String)sortedArray[i]);
        }
        return sortedMap;
    }
}
