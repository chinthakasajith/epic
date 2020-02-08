/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardTypeMgtBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CreditScoreRecommandBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *this persistence class use to wrote all the persistence that relate to 
 * credit score recommendation process
 * @author ayesh
 */
public class CreditScoreRecommendPersistence {

    private ResultSet rs = null;

    /**
     * get available card type details from database
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<CardTypeMgtBean> getCardTypeDetails(Connection con) throws SQLException, Exception {


        List<CardTypeMgtBean> allCrad = new ArrayList<CardTypeMgtBean>();

        PreparedStatement ps = null;
        try {
            String query = "SELECT CT.CARDTYPECODE,CT.DESCRIPTION FROM CARDTYPE CT WHERE STATUS =?";

            ps = con.prepareStatement(query);
            ps.setString(1, StatusVarList.ACTIVE_STATUS);
            rs = ps.executeQuery();

            while (rs.next()) {
                CardTypeMgtBean bean = new CardTypeMgtBean();
                bean.setCardTypeCode(rs.getString("CARDTYPECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                allCrad.add(bean);
            }

            return allCrad;
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

    /**
     * get all available card product details
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<CardProductBean> getCardroductDetails(Connection con, String cardType) throws SQLException, Exception {


        List<CardProductBean> allCrad = new ArrayList<CardProductBean>();

        PreparedStatement ps = null;
        try {
            String query = "SELECT CP.PRODUCTCODE,CP.DESCRIPTION FROM CARDPRODUCT CP "
                    + "WHERE CP.STATUS=? AND CARDTYPE=? AND CARDDOMAIN=?";

            ps = con.prepareStatement(query);
            ps.setString(1, StatusVarList.ACTIVE_STATUS);
            ps.setString(2, cardType);
            ps.setString(3, StatusVarList.CREDIT);
            rs = ps.executeQuery();

            while (rs.next()) {
                CardProductBean bean = new CardProductBean();
                bean.setProductCode(rs.getString("PRODUCTCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                allCrad.add(bean);
            }

            return allCrad;
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

    /**
     * check given card type is exist
     * @param con
     * @param cardType-Card type code
     * @param cardProduct-card product code
     * @return number of rows
     * @throws SQLException
     * @throws Exception 
     */
    public int isCradTypeExist(Connection con, String cardType) throws SQLException, Exception {




        PreparedStatement ps = null;
        try {
            String query = "SELECT count(*) AS ROWCOUNT FROM RECOMMENDSCHEM WHERE CARDTYPE=?";

            ps = con.prepareStatement(query);
            ps.setString(1, cardType);

            rs = ps.executeQuery();
            int count = -1;
            while (rs.next()) {
                count = Integer.parseInt(rs.getString("ROWCOUNT"));
            }

            return count;
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

    /**
     * check given card type code and card product code already exist
     * @param con
     * @param cardType
     * @param cardProduct
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int isCradTypeANDProductExist(Connection con, String cardType, String cardProduct) throws SQLException, Exception {


        PreparedStatement ps = null;
        try {
            String query = "SELECT count(*) AS ROWCOUNT FROM RECOMMENDSCHEM WHERE CARDTYPE=? AND CARDPRODUCT=? ";

            ps = con.prepareStatement(query);
            ps.setString(1, cardType);
            ps.setString(2, cardProduct);

            rs = ps.executeQuery();
            int count = -1;
            while (rs.next()) {
                count = Integer.parseInt(rs.getString("ROWCOUNT"));
            }

            return count;
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

    /**
     * get available ranges for given card type and card product
     * @param con
     * @param cardType - card type code
     * @param cardProduct - card product code
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<CreditScoreRecommandBean> getAvailableRanges(Connection con, String cardType) throws SQLException, Exception {


        List<CreditScoreRecommandBean> rengeList = new ArrayList<CreditScoreRecommandBean>();

        PreparedStatement ps = null;
        try {

            String query = "SELECT RE.SCORELOWERLIMIT,RE.SCOREHIGHLIMIT FROM RECOMMENDSCHEM RE WHERE RE.CARDTYPE=? ";

            ps = con.prepareStatement(query);
            ps.setString(1, cardType);

            rs = ps.executeQuery();

            while (rs.next()) {
                CreditScoreRecommandBean bean = new CreditScoreRecommandBean();
                bean.setHighLimit(rs.getString("SCOREHIGHLIMIT"));
                bean.setLowLimit(rs.getString("SCORELOWERLIMIT"));
                rengeList.add(bean);
            }

            return rengeList;
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

    /*
     * get all given card type ranges without current one
     */
    public List<CreditScoreRecommandBean> getCurrentRanges(Connection con, String ID, String cardType) throws SQLException, Exception {


        List<CreditScoreRecommandBean> rengeList = new ArrayList<CreditScoreRecommandBean>();

        PreparedStatement ps = null;
        try {

            String query = "SELECT SCORELOWERLIMIT,SCOREHIGHLIMIT FROM RECOMMENDSCHEM WHERE ID!=? AND CARDTYPE=? ";

            ps = con.prepareStatement(query);
            ps.setString(1, ID);
            ps.setString(2, cardType);

            rs = ps.executeQuery();

            while (rs.next()) {
                CreditScoreRecommandBean bean = new CreditScoreRecommandBean();
                bean.setHighLimit(rs.getString("SCOREHIGHLIMIT"));
                bean.setLowLimit(rs.getString("SCORELOWERLIMIT"));
                rengeList.add(bean);
            }

            return rengeList;
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

    /*
     * get current range for given ID
     */
    public CreditScoreRecommandBean getCurrentrange(Connection con, String ID) throws SQLException, Exception {


        CreditScoreRecommandBean bean = new CreditScoreRecommandBean();

        PreparedStatement ps = null;
        try {

            String query = "SELECT RE.SCORELOWERLIMIT,RE.SCOREHIGHLIMIT FROM RECOMMENDSCHEM RE WHERE RE.ID=? ";

            ps = con.prepareStatement(query);
            ps.setString(1, ID);

            rs = ps.executeQuery();

            while (rs.next()) {
                bean.setHighLimit(rs.getString("SCOREHIGHLIMIT"));
                bean.setLowLimit(rs.getString("SCORELOWERLIMIT"));
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

    /**
     * get all recommended details 
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<CreditScoreRecommandBean> getAllRecommended(Connection con) throws SQLException, Exception {


        List<CreditScoreRecommandBean> allCrad = new ArrayList<CreditScoreRecommandBean>();

        PreparedStatement ps = null;
        try {
            String query = "SELECT RE.ID,RE.SCORELOWERLIMIT,RE.SCOREHIGHLIMIT,"
                    + "CT.DESCRIPTION AS CARDTYPE,RE.CREDITLIMIT,CP.DESCRIPTION"
                    + " AS CARDPRODUCT FROM RECOMMENDSCHEM RE,CARDTYPE CT,CARDPRODUCT CP "
                    + "WHERE CT.CARDTYPECODE=RE.CARDTYPE AND CP.PRODUCTCODE=RE.CARDPRODUCT";

            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                CreditScoreRecommandBean bean = new CreditScoreRecommandBean();
                bean.setId(rs.getString("ID"));
                bean.setLowLimit(rs.getString("SCORELOWERLIMIT"));
                bean.setHighLimit(rs.getString("SCOREHIGHLIMIT"));
                bean.setCardtype(rs.getString("CARDTYPE"));
                bean.setCreditlimit(rs.getString("CREDITLIMIT"));
                bean.setCardproduct(rs.getString("CARDPRODUCT"));
                allCrad.add(bean);
            }

            return allCrad;
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

    /**
     * get selected row data
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public CreditScoreRecommandBean getSelectedRecommended(Connection con, String ID) throws SQLException, Exception {


        CreditScoreRecommandBean bean = new CreditScoreRecommandBean();

        PreparedStatement ps = null;
        try {
            String query = "SELECT RE.ID,RE.CARDTYPE,RE.SCORELOWERLIMIT,RE.SCOREHIGHLIMIT,"
                    + "CT.DESCRIPTION AS CARDTYPEDES,RE.CREDITLIMIT,RE.CARDPRODUCT,CP.DESCRIPTION"
                    + " AS CARDPRODUCTDES, RE.LASTUPDATEDUSER FROM RECOMMENDSCHEM RE,CARDTYPE CT,CARDPRODUCT CP "
                    + "WHERE CT.CARDTYPECODE=RE.CARDTYPE AND CP.PRODUCTCODE=RE.CARDPRODUCT AND RE.ID=?";

            ps = con.prepareStatement(query);
            ps.setString(1, ID);
            rs = ps.executeQuery();

            while (rs.next()) {

                bean.setId(rs.getString("ID"));
                bean.setLowLimit(rs.getString("SCORELOWERLIMIT"));
                bean.setHighLimit(rs.getString("SCOREHIGHLIMIT"));
                bean.setCardtype(rs.getString("CARDTYPEDES"));
                bean.setCardTypeCode(rs.getString("CARDTYPE"));
                bean.setCreditlimit(rs.getString("CREDITLIMIT"));
                bean.setCardproduct(rs.getString("CARDPRODUCTDES"));
                bean.setCardproductCode(rs.getString("CARDPRODUCT"));
                bean.setCardTypeCode(rs.getString("CARDTYPE"));
                bean.setLastUpdateUser(rs.getString("LASTUPDATEDUSER"));
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

    /**
     * add new credit score recommendation
     * @param con
     * @param bean
     * @return
     * @throws Exception 
     */
    public int addNewCrediScoreRecommandation(Connection con, CreditScoreRecommandBean bean) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "INSERT INTO RECOMMENDSCHEM RE (RE.ID, RE.SCORELOWERLIMIT,"
                    + "RE.SCOREHIGHLIMIT,RE.CARDTYPE,RE.CREDITLIMIT,RE.LASTUPDATEDUSER,RE.CARDPRODUCT)"
                    + " VALUES(?,?,?,?,?,?,?)";

            ps = con.prepareStatement(query);
            ps.setString(1, bean.getId());
            ps.setString(2, bean.getLowLimit());
            ps.setString(3, bean.getHighLimit());
            ps.setString(4, bean.getCardtype());
            ps.setString(5, bean.getCreditlimit());
            ps.setString(6, bean.getLastUpdateUser());
            ps.setString(7, bean.getCardproduct());



            i = ps.executeUpdate();



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

    /**
     * update credit score recommended 
     * @param con
     * @param bean
     * @return
     * @throws Exception 
     */
    public int updateCreditScoreRecommend(Connection con, CreditScoreRecommandBean bean) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "UPDATE RECOMMENDSCHEM SET SCORELOWERLIMIT=?,SCOREHIGHLIMIT=?,"
                    + "CARDTYPE=?,CREDITLIMIT=?,LASTUPDATEDUSER=?,CARDPRODUCT=?"
                    + " WHERE ID=?";

            ps = con.prepareStatement(query);
            ps.setString(1, bean.getLowLimit());
            ps.setString(2, bean.getHighLimit());
            ps.setString(3, bean.getCardtype());
            ps.setString(4, bean.getCreditlimit());
            ps.setString(5, bean.getLastUpdateUser());
            ps.setString(6, bean.getCardproduct());
            ps.setString(7, bean.getId());

            i = ps.executeUpdate();



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

    /**
     * delete recommended credit score 
     * @param con
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteRecommendedCreditScore(Connection con, String id) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM RECOMMENDSCHEM RE where RE.ID=? ";

            ps = con.prepareStatement(query);
            ps.setString(1, id);

            i = ps.executeUpdate();

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
        return i;
    }
}
