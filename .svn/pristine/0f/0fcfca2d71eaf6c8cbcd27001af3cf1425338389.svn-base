/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardTypeMgtBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardCategoryBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *this class use to wrote all the persistence method that relate to card type management 
 * @author ayesh
 */
public class CardTypeMgtPersistence {

    private ResultSet rs = null;
    
    private List<CardCategoryBean> cardCategoryLst;

    /**
     * add new card type to database
     * @param con
     * @param bean
     * @return
     * @throws Exception 
     */
    public int addNewCardType(Connection con, CardTypeMgtBean bean) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "INSERT INTO CARDTYPE CT (CT.CARDTYPECODE, CT.DESCRIPTION,"
                    + "CT.STATUS)"
                    + " VALUES(?,?,?)";

            ps = con.prepareStatement(query);
            ps.setString(1, bean.getCardTypeCode());
            ps.setString(2, bean.getDescription());
            ps.setString(3, bean.getStatus());



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
     * get all card type detail list from database
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<CardTypeMgtBean> getAllCardType(Connection con) throws SQLException, Exception {


        List<CardTypeMgtBean> allCrad = new ArrayList<CardTypeMgtBean>();

        PreparedStatement ps = null;
        try {
            String query = "SELECT CT.CARDTYPECODE,CT.DESCRIPTION,ST.DESCRIPTION"
                    + " AS STATUS,CT.STATUS AS CTSTATUS FROM CARDTYPE CT,STATUS ST WHERE ST.STATUSCODE=CT.STATUS";

            ps = con.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                CardTypeMgtBean bean = new CardTypeMgtBean();
                bean.setCardTypeCode(rs.getString("CARDTYPECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setStatusDes(rs.getString("CTSTATUS"));

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
     * delete selected card type from database 
     * @param con
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteCardtype(Connection con, String id) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM CARDTYPE BB where CARDTYPECODE=? ";

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

    /**
     * get selected card type row from database
     * @param con
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public CardTypeMgtBean getSelectedCradtype(Connection con, String id) throws SQLException, Exception {

        CardTypeMgtBean bean = new CardTypeMgtBean();

        PreparedStatement ps = null;
        try {
            String query = "SELECT CT.CARDTYPECODE,CT.DESCRIPTION,ST.DESCRIPTION AS STATUS"
                    + " FROM CARDTYPE CT,STATUS ST WHERE ST.STATUSCODE=CT.STATUS AND CT.CARDTYPECODE=?";

            ps = con.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                bean.setCardTypeCode(rs.getString("CARDTYPECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setStatus(rs.getString("STATUS"));
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
     * update card type persistence
     * @param con
     * @param bean
     * @return
     * @throws Exception 
     */
    public int updateCardType(Connection con, CardTypeMgtBean bean) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "UPDATE CARDTYPE SET DESCRIPTION=?,STATUS=?"
                    + " WHERE CARDTYPECODE=?";

            ps = con.prepareStatement(query);
            ps.setString(1, bean.getDescription());
            ps.setString(2, bean.getStatus());
            ps.setString(3, bean.getCardTypeCode());

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

    public List<CardCategoryBean> getAllCardCategory(Connection con) throws Exception {
        PreparedStatement ps = null;
        try {
            String query = "SELECT CC.CATEGORYCODE,CC.DESCRIPTION "
                    + " FROM CARDCATEGORY CC WHERE CC.STATUS ='ACT'";

            ps = con.prepareStatement(query);

            rs = ps.executeQuery();

            cardCategoryLst = new ArrayList<CardCategoryBean>();
            while (rs.next()) {
                CardCategoryBean bean = new CardCategoryBean();
                bean.setCategoryCode(rs.getString("CATEGORYCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                cardCategoryLst.add(bean);
            }

            return cardCategoryLst;
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
    
      public List<CardCategoryBean> getCreditCardCategory(Connection con) throws Exception {
        PreparedStatement ps = null;
        try {
            String query = "SELECT CC.CATEGORYCODE,CC.DESCRIPTION "
                    + " FROM CARDCATEGORY CC WHERE CC.STATUS ='ACT' and (CC.CATEGORYCODE = 'M' OR CC.CATEGORYCODE = 'S' OR CC.CATEGORYCODE = 'F' OR CC.CATEGORYCODE = 'E' OR CC.CATEGORYCODE = 'C')";

            ps = con.prepareStatement(query);

            rs = ps.executeQuery();

            cardCategoryLst = new ArrayList<CardCategoryBean>();
            while (rs.next()) {
                CardCategoryBean bean = new CardCategoryBean();
                bean.setCategoryCode(rs.getString("CATEGORYCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                cardCategoryLst.add(bean);
            }

            return cardCategoryLst;
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
