/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.creditscore.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardTypeMgtBean;
import com.epic.cms.camm.applicationconfig.creditscore.bean.CreditScoreRuleBean;
import com.epic.cms.camm.applicationconfig.creditscore.bean.CardScoreBean;
import com.epic.cms.camm.applicationconfig.creditscore.bean.ScoreCardAssignRuleBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationBean;
import com.epic.cms.system.util.datetime.SystemDateTime;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *this class use to wrote all the persistence class that relate to card score
 * @author ayesh
 */
public class CardScorePersistance {

    private ResultSet rs = null;

    /**
     * get all the credit score available rules bean list
     * both rule code and description are available
     * @return ruleList
     */
    public List<CreditScoreRuleBean> getAllCreditScoreRule(Connection con) throws SQLException, Exception {
        List<CreditScoreRuleBean> ruleList = new ArrayList<CreditScoreRuleBean>();
        PreparedStatement ps = null;
        try {

            String query = "SELECT CR.RULECODE,CR.RULEDESCRIPTION FROM CREDITSCORERULE CR";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                CreditScoreRuleBean bean = new CreditScoreRuleBean();
                bean.setRuleCode(rs.getString("RULECODE"));
                bean.setRuleName(rs.getString("RULEDESCRIPTION"));
                ruleList.add(bean);
            }
            return ruleList;
        } catch (SQLException e) {
            throw e;
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
    
        public List<CardTypeMgtBean> getCardTypeList(Connection con) throws SQLException, Exception {
        List<CardTypeMgtBean> cardTypeList = new ArrayList<CardTypeMgtBean>();
        PreparedStatement ps = null;
        try {

            String query = "SELECT CT.CARDTYPECODE,CT.DESCRIPTION,CT.STATUS,S.DESCRIPTION AS STATUSDES FROM CARDTYPE CT,STATUS S WHERE CT.STATUS = S.STATUSCODE";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                CardTypeMgtBean bean = new CardTypeMgtBean();
                bean.setCardTypeCode(rs.getString("CARDTYPECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setStatusDes(rs.getString("STATUSDES"));
                cardTypeList.add(bean);
            }
            return cardTypeList;
        } catch (SQLException e) {
            throw e;
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
     * add new Score card to database
     * @param con
     * @param bean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int addNewScoreCard(Connection con, CardScoreBean bean) throws SQLException, Exception {
        int row = -1;

        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO SCORECARD SC (SC.SCORECARDCODE,"
                    + "SC.DESCRIPTION,SC.MINIMUMSCORE,SC.MAXIMUMSCORE,SC.STATUS,SC.CARDTYPECODE,SC.LASTUPDATEDUSER) "
                    + "VALUES(?,?,?,?,?,?,?)";

            ps = con.prepareStatement(query);
            ps.setString(1, bean.getScoreCardCode());
            ps.setString(2, bean.getScoreCardName());
            ps.setString(3, bean.getMinScoreCard());
            ps.setString(4, bean.getMaxScoreCard());
            ps.setString(5, bean.getStatus());
            ps.setString(6, bean.getCardType());
            ps.setString(7, bean.getLastUpdateUser());
            row = ps.executeUpdate();
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
        return row;
    }

    /**
     * add rules with card score code
     * @param con
     * @param bean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int addRules(Connection con, String code, String rule) throws SQLException, Exception {
        int row = -1;

        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO SCORECARDRULES SC (SC.SCORECARDCODE,SC.SCORECARDRULECODE) "
                    + "VALUES(?,?)";

            ps = con.prepareStatement(query);
            ps.setString(1, code);
            ps.setString(2, rule);

            row = ps.executeUpdate();
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
        return row;
    }

    /**
     * get all  details about score card
     * @param con
     * @return List- CardScoreBean
     * @throws Exception 
     */
    public List<CardScoreBean> getAllScoreCardDetails(Connection con) throws Exception {
        PreparedStatement ps = null;
        try {
            List<CardScoreBean> scoreCardAllList = new ArrayList<CardScoreBean>();
            String query = "SELECT SC.SCORECARDCODE,SC.DESCRIPTION,SC.MINIMUMSCORE,"
                    + "SC.MAXIMUMSCORE,ST.DESCRIPTION AS  STATUS,SC.CARDTYPECODE,CT.DESCRIPTION AS CARDTYPEDES,SC.STATUS AS SCSTATUS FROM SCORECARD "
                    + "SC,STATUS ST,CARDTYPE CT "
                    + "WHERE ST.STATUSCODE=SC.STATUS AND SC.CARDTYPECODE = CT.CARDTYPECODE";

            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                CardScoreBean bean = new CardScoreBean();
                bean.setScoreCardCode(rs.getString("SCORECARDCODE"));
                bean.setScoreCardName(rs.getString("DESCRIPTION"));
                bean.setMinScoreCard(rs.getString("MINIMUMSCORE"));
                bean.setMaxScoreCard(rs.getString("MAXIMUMSCORE"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setProduct(rs.getString("CARDTYPECODE"));
                bean.setCardTypeDes(rs.getString("CARDTYPEDES"));
                bean.setStatusCode(rs.getString("SCSTATUS"));
                scoreCardAllList.add(bean);
            }
            return scoreCardAllList;

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
     * get already assigned rule list to card score
     * @param con
     * @param cardBean
     * @return List - ScoreCardAssignRuleBean
     * @throws Exception 
     */
    public List<ScoreCardAssignRuleBean> getAssignedRuleList(Connection con, String cardCode) throws Exception {
        PreparedStatement ps = null;
        try {
            List<ScoreCardAssignRuleBean> scoreCardAllList = new ArrayList<ScoreCardAssignRuleBean>();
            String query = "SELECT SR.SCORECARDCODE,SR.SCORECARDRULECODE FROM "
                    + "SCORECARDRULES SR WHERE SR.SCORECARDCODE =?";

            ps = con.prepareStatement(query);
            ps.setString(1, cardCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                ScoreCardAssignRuleBean bean = new ScoreCardAssignRuleBean();
                bean.setScoreCardCode(rs.getString("SCORECARDCODE"));
                bean.setRuleCode(rs.getString("SCORECARDRULECODE"));

                scoreCardAllList.add(bean);
            }
            return scoreCardAllList;

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
     * delete rules for update
     * @param con
     * @param ID
     * @return int-number of rows that delete
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteRulesUpdate(Connection con, String ID) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM SCORECARDRULES SR where SR.SCORECARDCODE =?";

            ps = con.prepareStatement(query);
            ps.setString(1, ID);

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
     * update card score 
     * @param con
     * @param bean
     * @return int - number of rows that update
     * @throws Exception 
     */
    public int updateCardScore(Connection con, CardScoreBean bean) throws Exception {
        int resultID = -1;
        PreparedStatement ps = null;
        try {
            String query = "UPDATE SCORECARD SC SET "
                    + "SC.DESCRIPTION=?,SC.MINIMUMSCORE=?,SC.MAXIMUMSCORE=?,"
                    + "SC.STATUS=?,SC.CARDTYPECODE=?,SC.LASTUPDATEDTIME=?,SC.LASTUPDATEDUSER=? "
                    + "WHERE SC.SCORECARDCODE=? ";
            ps = con.prepareStatement(query);

            ps.setString(1, bean.getScoreCardName());
            ps.setString(2, bean.getMinScoreCard());
            ps.setString(3, bean.getMaxScoreCard());
            ps.setString(4, bean.getStatus());
            ps.setString(5, bean.getProduct());
            ps.setTimestamp(6, SystemDateTime.getSystemDataAndTime());
            ps.setString(7, bean.getLastUpdateUser());
            ps.setString(8, bean.getScoreCardCode());


            resultID = ps.executeUpdate();

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

        return resultID;
    }

    
    
    
    public int deleteCardScore(Connection con, String ID) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM SCORECARD CSF where CSF.SCORECARDCODE =?";

            ps = con.prepareStatement(query);
            ps.setString(1, ID);

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
    

    
     public boolean deleteCardScore(Connection con, CardScoreBean bean) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = con.prepareStatement("DELETE SCORECARD WHERE SCORECARDCODE=?");



            deleteStat.setString(1, bean.getScoreCardCode());


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
     
     public boolean deleteCardScoreRules(Connection con, CardScoreBean bean) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = con.prepareStatement("DELETE SCORECARDRULES WHERE SCORECARDCODE=?");



            deleteStat.setString(1, bean.getScoreCardCode());


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

    public CardApplicationBean getCardApplicationBean(Connection CMSCon, String applicationId) throws Exception{
        CardApplicationBean bean = new CardApplicationBean();
        PreparedStatement ps = null;
        try {

            String query = "SELECT CARDCATEGORY FROM  CARDAPPLICATION WHERE APPLICATIONID=?";
            ps = CMSCon.prepareStatement(query);
            ps.setString(1, applicationId);
            rs = ps.executeQuery();

            while (rs.next()) {
                bean.setCardCategoryCode(rs.getString("CARDCATEGORY"));
            }
        } catch (SQLException e) {
            throw e;
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
        
        return bean;
    }

}
