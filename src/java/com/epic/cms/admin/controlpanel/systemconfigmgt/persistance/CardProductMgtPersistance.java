/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author chanuka
 */
public class CardProductMgtPersistance {

    //initializing variables
    private HashMap<String, String> cardTypeList = null;
    private HashMap<String, String> cardDomainList = null;
    private List<CardProductBean> cardProductBeanLst = null;
    private List<String> NotAssignBinList = null;
    private HashMap<String, String> AssignbinList = null;
    private List<String> AssignBinList = null;
    private List<String> cardProcuctBinList = null;

    /**
     * to retrieve all the card product details
     * @param con
     * @return
     * @throws Exception 
     */
    public List<CardProductBean> getAllCardProductDetailsList(Connection con) throws Exception {
        
        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;
        
        try {
            getAllUserRole = con.prepareStatement("SELECT cd.DESCRIPTION as domaindes,TC.CARDDOMAIN,TC.PRODUCTCODE,TC.DESCRIPTION,"
                    + "TC.CARDTYPE,CT.DESCRIPTION AS CARDTYPEDES ,TC.STATUS,ST.DESCRIPTION AS STATUSUDES,"
                    + "TC.DISPLAYDIGITS,TC.INTERESTPROFILECODE,VALIDITYPERIOD "
                    + "FROM CARDPRODUCT TC,STATUS ST,CARDTYPE CT,CARDDOMAIN cd "
                    + "WHERE TC.STATUS = ST.STATUSCODE AND TC.CARDTYPE=CT.CARDTYPECODE AND cd.DOMAINID = TC.CARDDOMAIN ");
            
            rs = getAllUserRole.executeQuery();
            cardProductBeanLst = new ArrayList<CardProductBean>();

            //iterate through the list
            while (rs.next()) {
                CardProductBean resultBean = new CardProductBean();
                
                resultBean.setProductCode(rs.getString("PRODUCTCODE"));
                resultBean.setDescription(rs.getString("DESCRIPTION"));
                resultBean.setCardType(rs.getString("CARDTYPE"));
                resultBean.setDomainCode(rs.getString("CARDDOMAIN"));
                resultBean.setDomainDes(rs.getString("domaindes"));
                resultBean.setCardTypeDes(rs.getString("CARDTYPEDES"));
                resultBean.setStatus(rs.getString("STATUS"));
                resultBean.setStatusDes(rs.getString("STATUSUDES"));
                resultBean.setDisplayDigit(rs.getString("DISPLAYDIGITS"));
                resultBean.setValidityPeriod(rs.getString("VALIDITYPERIOD"));

                //add the bean to the bean list
                cardProductBeanLst.add(resultBean);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();
        }
        
        return cardProductBeanLst;
    }

    /**
     * to retrieve the card types
     * @param con
     * @return
     * @throws Exception 
     */
    public HashMap<String, String> getAllCardTypeList(Connection con) throws Exception {
        
        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;
        
        try {
            getAllUserRole = con.prepareStatement("SELECT TC.CARDTYPECODE,TC.DESCRIPTION FROM CARDTYPE TC WHERE TC.STATUS = 'ACT'");
            
            rs = getAllUserRole.executeQuery();
            cardTypeList = new HashMap<String, String>();
            
            while (rs.next()) {
                
                cardTypeList.put(rs.getString("CARDTYPECODE"), rs.getString("DESCRIPTION"));
                
            }
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();
            
        }
        
        return cardTypeList;
    }
    
    public HashMap<String, String> getAllCardDomainList(Connection con) throws Exception {
        
        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;
        
        try {
            getAllUserRole = con.prepareStatement("SELECT TC.DOMAINID,TC.DESCRIPTION FROM CARDDOMAIN TC");
            
            rs = getAllUserRole.executeQuery();
            cardDomainList = new HashMap<String, String>();
            
            while (rs.next()) {
                
                cardDomainList.put(rs.getString("DOMAINID"), rs.getString("DESCRIPTION"));
                
            }
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();
            
        }
        
        return cardDomainList;
    }

    /**
     * to insert a new card product 
     * @param con
     * @param cardProductBean
     * @return
     * @throws Exception 
     */
    public int insertCardProduct(Connection con, CardProductBean cardProductBean) throws Exception {
        int success = -1;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("INSERT INTO CARDPRODUCT(PRODUCTCODE,DESCRIPTION,CARDTYPE,STATUS,DISPLAYDIGITS,CARDDOMAIN,VALIDITYPERIOD,LASTUPDATEDUSER) "
                    + "VALUES(?,?,?,?,?,?,?,?) ");
            
            ps.setString(1, cardProductBean.getProductCode());
            ps.setString(2, cardProductBean.getDescription());
            ps.setString(3, cardProductBean.getCardType());
            ps.setString(4, cardProductBean.getStatus());
            ps.setString(5, cardProductBean.getDisplayDigit());
            ps.setString(6, cardProductBean.getDomainCode());
            ps.setString(7, cardProductBean.getValidityPeriod());
            ps.setString(8, cardProductBean.getLastUpdateUser());
            
            success = ps.executeUpdate();
            
        } catch (SQLException ex) {
            throw ex;
            
        } catch (Exception ex) {
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
        return success;
    }

    /**
     * to insert the assigned BINs for the given card product
     * @param con
     * @param assignBin
     * @param prodCode
     * @return
     * @throws Exception 
     */
    public int insertCardProductBin(Connection con, List<CardProductBean> assignBinList, String prodCode) throws Exception {
        int k = 0;
        int j = 0;
        int res = 0;
        
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM CARDPRODUCTBIN WHERE PRODUCTCODE =?");
            ps.setString(1, prodCode);
            k = ps.executeUpdate();
            
            for (CardProductBean cardProductBean : assignBinList) {
                
                ps = con.prepareStatement("INSERT INTO CARDPRODUCTBIN "
                        + "(PRODUCTCODE,BINCODE,CARDKEYID ) "
                        + " VALUES (?,?,?) ");
                
                ps.setString(1, prodCode);
                ps.setString(2, cardProductBean.getBin());
                ps.setInt(3, Integer.parseInt(cardProductBean.getCardKey()));
                
                
                j = ps.executeUpdate();
                if (j == 0) {
                    throw new Exception();
                }
            }
        } catch (SQLException ex) {
            throw ex;
            
        } catch (Exception ex) {
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
        if (j > 0) {
            res = 1;
        }
        return res;
        
    }

    /**
     * to update the existing card products
     * @param con
     * @param cardProductBean
     * @return
     * @throws Exception 
     */
    public int updateCardProduct(Connection con, CardProductBean cardProductBean) throws Exception {
        int row = -1;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("UPDATE CARDPRODUCT SET CARDDOMAIN=?,CARDTYPE=?,DESCRIPTION=?,"
                    + "STATUS=?,DISPLAYDIGITS=?,VALIDITYPERIOD=?,LASTUPDATEDUSER=? WHERE PRODUCTCODE=? ");
            
            ps.setString(1, cardProductBean.getDomainCode());
            ps.setString(2, cardProductBean.getCardType());
            ps.setString(3, cardProductBean.getDescription());
            ps.setString(4, cardProductBean.getStatus());
            ps.setString(5, cardProductBean.getDisplayDigit());
            ps.setString(6, cardProductBean.getValidityPeriod());
            ps.setString(7, cardProductBean.getLastUpdateUser());
            ps.setString(8, cardProductBean.getProductCode());
            
            row = ps.executeUpdate();
            
        } catch (SQLException ex) {
            throw ex;
            
        } catch (Exception ex) {
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
        return row;
    }

    /**
     * to update the BINs assigned for the given card product
     * @param con
     * @param assignBin
     * @param prodCode
     * @return
     * @throws Exception 
     */
    public int updateCardProductBin(Connection con, List<CardProductBean> assignBinList, String prodCode) throws Exception {
        int k;
        
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM CARDPRODUCTBIN WHERE PRODUCTCODE =?");
            ps.setString(1, prodCode);
            k = ps.executeUpdate();
///****************************/////////////////////
//            for (int i = 0; i < assignBin.length; i++) {
//
//                ps = con.prepareStatement("INSERT INTO CARDPRODUCTBIN "
//                        + "(PRODUCTCODE,BINCODE ) "
//                        + " VALUES (?,?) ");
//
//                ps.setString(1, prodCode);
//                ps.setString(2, assignBin[i]);
//
//                int j = 0;
//                j = ps.executeUpdate();
//                if (j == 0) {
//                    throw new Exception();
//                }
//            }
//******************************************//
            for (CardProductBean cardProductBean : assignBinList) {
                
                ps = con.prepareStatement("INSERT INTO CARDPRODUCTBIN "
                        + "(PRODUCTCODE,BINCODE,CARDKEYID ) "
                        + " VALUES (?,?,?) ");
                
                ps.setString(1, prodCode);
                ps.setString(2, cardProductBean.getBin());
                ps.setInt(3, Integer.parseInt(cardProductBean.getCardKey()));
                
                int j = 0;
                j = ps.executeUpdate();
                if (j == 0) {
                    throw new Exception();
                }
            }            
        } catch (SQLException ex) {
            throw ex;
            
        } catch (Exception ex) {
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
        return k;
    }

    /**
     * to delete a card product
     * @param con
     * @param cardProductBean
     * @return
     * @throws Exception 
     */
    public boolean deleteCardProduct(Connection con, CardProductBean cardProductBean) throws Exception {
        int row, row1 = -1;
        boolean success = false;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM CARDPRODUCTBIN WHERE PRODUCTCODE = ?");
            ps.setString(1, cardProductBean.getProductCode());
            
            
            row = ps.executeUpdate();
            if (row > 0) {
                ps = con.prepareStatement("DELETE FROM CARDPRODUCT WHERE PRODUCTCODE=? ");
                ps.setString(1, cardProductBean.getProductCode());
                
                row1 = ps.executeUpdate();
                if (row1 <= 0) {
                    throw new Exception();
                }
            }
            if (row1 == 1) {
                success = true;
            }
            
        } catch (SQLException ex) {
            throw ex;
            
        } catch (Exception ex) {
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
        return success;
    }

    /**
     * to retrieve the BINs in a given card type which are not assigned to the given product
     * @param con
     * @param category
     * @param prodCode
     * @return
     * @throws Exception 
     */
    public List<CardProductBean> getNotAssignBinList(Connection con, String cardType, String cardDomain, String product) throws Exception {
        List<CardProductBean> BinList = new ArrayList<CardProductBean>();
        CardProductBean bean;
        ResultSet rs = null;
        PreparedStatement ps = null;
        
        try {
            ps = con.prepareStatement("SELECT CB.BIN,CB.DESCRIPTION FROM CARDBIN CB WHERE CB.CARDTYPE = ? AND CB.CARDDOMAIN=? AND CB.STATUS = 'ACT'"
                    + "AND CB.BIN NOT IN (SELECT CPB.BINCODE FROM CARDPRODUCTBIN CPB WHERE CPB.PRODUCTCODE = ? )");

//            String query= "SELECT CB.BIN,CB.DESCRIPTION FROM CARDBIN CB WHERE CB.CARDTYPE = ? AND CB.CARDDOMAIN=? AND CB.STATUS = 'ACT'"
//                    + "AND CB.BIN NOT IN (SELECT CPB.BINCODE FROM CARDPRODUCTBIN CPB WHERE CPB.PRODUCTCODE = ? )";

            /**
             * SELECT CB.BIN,CB.DESCRIPTION FROM CARDBIN CB WHERE CB.CARDTYPE = ? AND CB.STATUS = 'ACT' "
            + "AND CB.BIN NOT IN (SELECT CPB.BINCODE FROM CARDPRODUCTBIN CPB WHERE CPB.PRODUCTCODE = ? )"
             */
            /**
             * "SELECT CB.BIN,CB.DESCRIPTION FROM CARDBIN CB WHERE CB.CARDTYPE = ? AND CB.CARDDOMAIN=? AND CB.STATUS = 'ACT' "
            + "AND CB.BIN NOT IN (SELECT CPB.BINCODE FROM CARDPRODUCTBIN CPB)"
             */
            ps.setString(1, cardType);
            ps.setString(2, cardDomain);
            ps.setString(3, product);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                bean = new CardProductBean();
                
                bean.setBin(rs.getString("BIN"));
                bean.setBinDes(rs.getString("DESCRIPTION"));
                
                BinList.add(bean);
            }
            
        } catch (SQLException ex) {
            throw ex;
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        
        return BinList;
    }

    /**
     * to retrieve the assigned BINs for the given card product
     * @param con
     * @param prodCode
     * @return
     * @throws Exception 
     */
    public List<CardProductBean> getAssignBinList(Connection con, String prodCode) throws Exception {
        List<CardProductBean> BinList = new ArrayList<CardProductBean>();
        CardProductBean bean;
        ResultSet rs = null;
        PreparedStatement ps = null;
        
        try {
            ps = con.prepareStatement("SELECT DISTINCT CPB.PRODUCTCODE,CPB.BINCODE,CB.DESCRIPTION AS BIN_DES "
                    + "FROM CARDPRODUCTBIN CPB,CARDBIN CB "
                    + "WHERE CPB.PRODUCTCODE = ? "
                    + "AND CB.BIN = CPB.BINCODE");
            ps.setString(1, prodCode);
            rs = ps.executeQuery();
            AssignBinList = new ArrayList<String>();
            
            while (rs.next()) {
                bean = new CardProductBean();
                
                bean.setBin(rs.getString("BINCODE"));
                bean.setBinDes(rs.getString("BIN_DES"));
                
                BinList.add(bean);
            }
            
        } catch (SQLException ex) {
            throw ex;
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        
        return BinList;
    }

    /**
     * to retrieve the BINs for the given card type
     * @param con
     * @param category
     * @return
     * @throws Exception 
     */
    public List<CardProductBean> getCardroductBins(Connection con, String type, String domain) throws Exception {
        
        List<CardProductBean> BinList = new ArrayList<CardProductBean>();
        CardProductBean bean;
        ResultSet rs = null;
        PreparedStatement ps = null;
        
        try {
            ps = con.prepareStatement("SELECT CB.BIN,CB.DESCRIPTION FROM CARDBIN CB WHERE CB.CARDTYPE = ? AND CB.CARDDOMAIN=? AND CB.STATUS = 'ACT' ");

            /**\
             * "SELECT CB.BIN,CB.DESCRIPTION FROM CARDBIN CB WHERE CB.CARDTYPE = ? AND CB.CARDDOMAIN=? AND CB.STATUS = 'ACT' "
            + "AND CB.BIN NOT IN (SELECT CPB.BINCODE FROM CARDPRODUCTBIN CPB)"
             */
            ps.setString(1, type);
            ps.setString(2, domain);
            
            rs = ps.executeQuery();
            
            
            while (rs.next()) {
                bean = new CardProductBean();
                
                bean.setBin(rs.getString("BIN"));
                bean.setBinDes(rs.getString("DESCRIPTION"));
                
                BinList.add(bean);
            }
            
        } catch (SQLException ex) {
            throw ex;
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return BinList;
    }
    
    public List<CardProductBean> getCardProductBinsLIst(Connection con, String bin) throws Exception {
        
        List<CardProductBean> BinList = new ArrayList<CardProductBean>();
        CardProductBean bean;
        ResultSet rs = null;
        PreparedStatement ps = null;
        
        try {
            ps = con.prepareStatement("SELECT CB.BIN,CB.DESCRIPTION FROM CARDBIN CB WHERE CB.BIN IN ( ? ) AND CB.STATUS = 'ACT' ");

            /**\
             * "SELECT CB.BIN,CB.DESCRIPTION FROM CARDBIN CB WHERE CB.CARDTYPE = ? AND CB.CARDDOMAIN=? AND CB.STATUS = 'ACT' "
            + "AND CB.BIN NOT IN (SELECT CPB.BINCODE FROM CARDPRODUCTBIN CPB)"
             */
            ps.setString(1, bin);
            
            
            rs = ps.executeQuery();
            
            
            while (rs.next()) {
                bean = new CardProductBean();
                
                bean.setBin(rs.getString("BIN"));
                bean.setBinDes(rs.getString("DESCRIPTION"));
                
                BinList.add(bean);
            }
            
        } catch (SQLException ex) {
            throw ex;
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return BinList;
    }
    
    public List<CardProductBean> getCardKeyLIst(Connection con, String cardProductId, String binId) throws Exception {
        
        List<CardProductBean> CardKeyList = new ArrayList<CardProductBean>();
        CardProductBean bean;
        ResultSet rs = null;
        PreparedStatement ps = null;
        
        try {
            ps = con.prepareStatement("SELECT CPB.CARDKEYID FROM CARDPRODUCTBIN CPB WHERE CPB.PRODUCTCODE = ? AND CPB.BINCODE=? ");

            /**\
             * "SELECT CB.BIN,CB.DESCRIPTION FROM CARDBIN CB WHERE CB.CARDTYPE = ? AND CB.CARDDOMAIN=? AND CB.STATUS = 'ACT' "
            + "AND CB.BIN NOT IN (SELECT CPB.BINCODE FROM CARDPRODUCTBIN CPB)"
             */
            ps.setString(1, cardProductId);
            ps.setString(2, binId);
            
            rs = ps.executeQuery();
            
            
            while (rs.next()) {
                bean = new CardProductBean();
                
                bean.setCardKey(rs.getString("CARDKEYID"));
                CardKeyList.add(bean);
            }
            
        } catch (SQLException ex) {
            throw ex;
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return CardKeyList;
    }    
    
    public List<CardProductBean> getAllAssignCardKeyList(Connection con, int cardKeyId) throws Exception {
        
        List<CardProductBean> CardKeyList = new ArrayList<CardProductBean>();
        CardProductBean bean;
        ResultSet rs = null;
        PreparedStatement ps = null;
        
        try {
            ps = con.prepareStatement("SELECT CK.CARDKEYID,CK.DESCRIPTION FROM ECMS_ONLINE_CARDKEYS CK WHERE CK.CARDKEYID IN ( ? )  ORDER BY DESCRIPTION ASC");

            /**\
             * "SELECT CB.BIN,CB.DESCRIPTION FROM CARDBIN CB WHERE CB.CARDTYPE = ? AND CB.CARDDOMAIN=? AND CB.STATUS = 'ACT' "
            + "AND CB.BIN NOT IN (SELECT CPB.BINCODE FROM CARDPRODUCTBIN CPB)"
             */
            ps.setInt(1, cardKeyId);
            
            rs = ps.executeQuery();
            
            
            while (rs.next()) {
                bean = new CardProductBean();
                
                bean.setCardKey(rs.getString("CARDKEYID"));
                bean.setCardKeyDes(rs.getString("DESCRIPTION"));
                CardKeyList.add(bean);
            }
            
        } catch (SQLException ex) {
            throw ex;
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return CardKeyList;
    }    

    public List<CardProductBean> getAllNotAssignCardKeyList(Connection con, int cardKeyId) throws Exception {
        
        List<CardProductBean> CardKeyList = new ArrayList<CardProductBean>();
        CardProductBean bean;
        ResultSet rs = null;
        PreparedStatement ps = null;
        
        try {
            ps = con.prepareStatement("SELECT CK.CARDKEYID,CK.DESCRIPTION FROM ECMS_ONLINE_CARDKEYS CK WHERE CK.CARDKEYID NOT IN ( ? )  ORDER BY DESCRIPTION ASC");

            /**\
             * "SELECT CB.BIN,CB.DESCRIPTION FROM CARDBIN CB WHERE CB.CARDTYPE = ? AND CB.CARDDOMAIN=? AND CB.STATUS = 'ACT' "
            + "AND CB.BIN NOT IN (SELECT CPB.BINCODE FROM CARDPRODUCTBIN CPB)"
             */
            ps.setInt(1, cardKeyId);
            rs = ps.executeQuery();
            
            
            while (rs.next()) {
                bean = new CardProductBean();
                
                bean.setCardKey(rs.getString("CARDKEYID"));
                bean.setCardKeyDes(rs.getString("DESCRIPTION"));
                CardKeyList.add(bean);
            }
            
        } catch (SQLException ex) {
            throw ex;
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return CardKeyList;
    }    
    
    public List<CardProductBean> getCardKeyLIst(Connection con) throws Exception {
        
        List<CardProductBean> CardKeyList = new ArrayList<CardProductBean>();
        CardProductBean bean;
        ResultSet rs = null;
        PreparedStatement ps = null;
        
        try {
            ps = con.prepareStatement("SELECT CK.CARDKEYID,CK.DESCRIPTION FROM ECMS_ONLINE_CARDKEYS CK ORDER BY DESCRIPTION ASC");

            /**\
             * "SELECT CB.BIN,CB.DESCRIPTION FROM CARDBIN CB WHERE CB.CARDTYPE = ? AND CB.CARDDOMAIN=? AND CB.STATUS = 'ACT' "
            + "AND CB.BIN NOT IN (SELECT CPB.BINCODE FROM CARDPRODUCTBIN CPB)"
             */
            rs = ps.executeQuery();
            
            
            while (rs.next()) {
                bean = new CardProductBean();
                bean.setCardKey(rs.getString("CARDKEYID"));
                bean.setCardKeyDes(rs.getString("DESCRIPTION"));
                CardKeyList.add(bean);
            }
            
        } catch (SQLException ex) {
            throw ex;
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return CardKeyList;
    }    
    
    public List<CardProductBean> getAllCardProductBinList(Connection con) throws Exception {
        
        List<CardProductBean> CardKeyList = new ArrayList<CardProductBean>();
        CardProductBean bean;
        ResultSet rs = null;
        PreparedStatement ps = null;
        
        try {
            ps = con.prepareStatement("SELECT CPB.PRODUCTCODE,CPB.BINCODE,CPB.CARDKEYID FROM CARDPRODUCTBIN CPB ");

            /**\
             * "SELECT CB.BIN,CB.DESCRIPTION FROM CARDBIN CB WHERE CB.CARDTYPE = ? AND CB.CARDDOMAIN=? AND CB.STATUS = 'ACT' "
            + "AND CB.BIN NOT IN (SELECT CPB.BINCODE FROM CARDPRODUCTBIN CPB)"
             */
            rs = ps.executeQuery();
            
            
            while (rs.next()) {
                bean = new CardProductBean();
                bean.setProductCode(rs.getString("PRODUCTCODE"));
                bean.setBin(rs.getString("BINCODE"));
                bean.setCardKey(rs.getString("CARDKEYID"));
                CardKeyList.add(bean);
            }
            
        } catch (SQLException ex) {
            throw ex;
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return CardKeyList;
    }  
    
    public List<CardProductBean> getAllProductionModeList(Connection con) throws Exception {
        
        List<CardProductBean> CardKeyList = new ArrayList<CardProductBean>();
        CardProductBean bean;
        ResultSet rs = null;
        PreparedStatement ps = null;
        
        try {
            ps = con.prepareStatement("SELECT CODE , DESCRIPTION FROM ECMS_ONLINE_CARDPRODUCTIONMODE");

            /**\
             * "SELECT CB.BIN,CB.DESCRIPTION FROM CARDBIN CB WHERE CB.CARDTYPE = ? AND CB.CARDDOMAIN=? AND CB.STATUS = 'ACT' "
            + "AND CB.BIN NOT IN (SELECT CPB.BINCODE FROM CARDPRODUCTBIN CPB)"
             */
            rs = ps.executeQuery();
            
            
            while (rs.next()) {
                bean = new CardProductBean();
                bean.setCardProductionMode(rs.getString("CODE"));
                bean.setCardProductionModeDes(rs.getString("DESCRIPTION"));
                CardKeyList.add(bean);
            }
            
        } catch (SQLException ex) {
            throw ex;
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return CardKeyList;
    }    
 
    public List<CardProductBean> getProductionModeBaseCardList(Connection con, String productionModeCode) throws Exception {
        
        List<CardProductBean> CardKeyList = new ArrayList<CardProductBean>();
        CardProductBean bean;
        ResultSet rs = null;
        PreparedStatement ps = null;

        StringBuffer buff = new StringBuffer();
        buff.append(" SELECT CK.CARDKEYID , CK.DESCRIPTION ");
        buff.append(" FROM ECMS_ONLINE_CARDKEYS CK, ECMS_ONLINE_CARDPRODUCTIONMODE CPM , ECMS_ONLINE_CARDKEYPROFILE CP ");
        buff.append(" WHERE CPM.CODE = CP.PRODUCTIONMODE AND CP.ID = CK.CARDKEYPROFILEID AND CPM.CODE = ? ");
        buff.append(" ORDER BY CK.DESCRIPTION ASC ");
           
        try {
            ps = con.prepareStatement(buff.toString());

            /**\
             * "SELECT CB.BIN,CB.DESCRIPTION FROM CARDBIN CB WHERE CB.CARDTYPE = ? AND CB.CARDDOMAIN=? AND CB.STATUS = 'ACT' "
            + "AND CB.BIN NOT IN (SELECT CPB.BINCODE FROM CARDPRODUCTBIN CPB)"
             */
            
            ps.setString(1, productionModeCode);
            rs = ps.executeQuery();
            
            
            while (rs.next()) {
                bean = new CardProductBean();
                bean.setCardKey(rs.getString("CARDKEYID"));
                bean.setCardKeyDes(rs.getString("DESCRIPTION"));
                CardKeyList.add(bean);
            }
            
        } catch (SQLException ex) {
            throw ex;
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return CardKeyList;
    }    
  
    public List<CardProductBean> getProductionModeList(Connection con, String cardKeyId) throws Exception {
        
        List<CardProductBean> productionModeList = new ArrayList<CardProductBean>();
        CardProductBean bean;
        ResultSet rs = null;
        PreparedStatement ps = null;

        StringBuffer buff = new StringBuffer();
        buff.append(" SELECT CPM.CODE,CPM.DESCRIPTION ");
        buff.append(" FROM ECMS_ONLINE_CARDKEYS CK , ECMS_ONLINE_CARDKEYPROFILE CP , ECMS_ONLINE_CARDPRODUCTIONMODE CPM ");
        buff.append(" WHERE CK.CARDKEYPROFILEID = CP.ID AND CP.PRODUCTIONMODE = CPM.CODE AND CK.CARDKEYID = ? ");
        buff.append(" ORDER BY CPM.DESCRIPTION ASC ");
           
        try {
            ps = con.prepareStatement(buff.toString());

            /**\
             * "SELECT CB.BIN,CB.DESCRIPTION FROM CARDBIN CB WHERE CB.CARDTYPE = ? AND CB.CARDDOMAIN=? AND CB.STATUS = 'ACT' "
            + "AND CB.BIN NOT IN (SELECT CPB.BINCODE FROM CARDPRODUCTBIN CPB)"
             */
            
            ps.setString(1, cardKeyId);
            rs = ps.executeQuery();
            
            
            while (rs.next()) {
                bean = new CardProductBean();
                bean.setCardProductionMode(rs.getString("CODE"));
                bean.setCardProductionModeDes(rs.getString("DESCRIPTION"));
                productionModeList.add(bean);
            }
            
        } catch (SQLException ex) {
            throw ex;
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return productionModeList;
    } 
    
        public HashMap<String,String> getCardCategoryList(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllByCatStat = null;
        
        HashMap<String,String> map = new HashMap<String,String>();
        try {
            getAllByCatStat = con.prepareStatement("SELECT CATEGORYCODE,DESCRIPTION FROM CARDCATEGORY WHERE CARDDOMAIN = ? ORDER BY DESCRIPTION");
            getAllByCatStat.setString(1, StatusVarList.CREDIT);
            rs = getAllByCatStat.executeQuery();
            
            while (rs.next()) {
                map.put(rs.getString("CATEGORYCODE"), rs.getString("DESCRIPTION"));
            }
            return map;
        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getAllByCatStat.close();
        }
    }
        
}
