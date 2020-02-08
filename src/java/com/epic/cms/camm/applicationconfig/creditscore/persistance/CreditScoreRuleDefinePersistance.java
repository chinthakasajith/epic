/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.creditscore.persistance;

import com.epic.cms.camm.applicationconfig.creditscore.bean.CreditScoreRuleBean;
import com.epic.cms.system.util.datetime.SystemDateTime;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author upul
 */
public class CreditScoreRuleDefinePersistance {

    private ResultSet rs = null;

    /**
     * get all detail about credit score field details
     * @param con
     * @return
     * @throws Exception 
     */
    public List<CreditScoreRuleBean> getAllCreditScoreRuleDefDetails(Connection con) throws Exception {
        PreparedStatement ps = null;
        try {
            List<CreditScoreRuleBean> crdtScoreRuleList = new ArrayList<CreditScoreRuleBean>();
            String query = " SELECT CR.RULECODE,CR.RULEDESCRIPTION,CR.FIELDID,O.DESCRIPTION AS CONDITIONCODE,"
                    + " S.DESCRIPTION AS STATUS,CR.VALUE,CR.MAXVALUE, "
                    + " CRT.RULEDESCRIPTION AS FSTRULEDES,CRS.RULEDESCRIPTION AS SECONDRULEDES,CR.SCORE,"
                    + " CR.LASTUPDATEDUSER,CR.LASTUPDATEDTIME,CF.FIELDNAME AS CFFIELDNAME "
                    + " FROM CREDITSCORERULE CR,CREDITSCORERULE CRT,CREDITSCORERULE CRS,STATUS S,OPERATOR O,CREDITSCOREFIELD CF"
                    + " WHERE CR.STATUS=S.STATUSCODE AND O.OPERATORCODE=CR.CONDITIONCODE AND"
                    + " CR.FIRSTRULECODE =CRT.RULECODE(+) AND CR.SECONDRULECODE=CRS.RULECODE(+) AND CF.FIELDID=CR.FIELDID ";

            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            while (rs.next()) {
                CreditScoreRuleBean bean = new CreditScoreRuleBean();
                bean.setRuleCode(rs.getString("RULECODE"));
                bean.setRuleName(rs.getString("RULEDESCRIPTION"));
                bean.setFieldName(rs.getString("FIELDID"));
                bean.setCondition(rs.getString("CONDITIONCODE"));
                bean.setValue(rs.getString("VALUE"));
                bean.setMaxValue(rs.getString("MAXVALUE"));
                bean.setRuleNoOne(rs.getString("FSTRULEDES"));
                bean.setRuleNoTwo(rs.getString("SECONDRULEDES"));
                bean.setScore(rs.getString("SCORE"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                bean.setLastUpdatedTime(rs.getDate("LASTUPDATEDTIME"));
                bean.setFieldNameDes(rs.getString("CFFIELDNAME"));

                crdtScoreRuleList.add(bean);

            }


            return crdtScoreRuleList;
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

    /////////////////////////////////////////////////////////////////
    /**
     * getCreditScoreRuleDefFromRuleIDDetails
     * @param con
     * @param ruleId
     * @return
     * @throws Exception 
     */
    public CreditScoreRuleBean getCreditScoreRuleDefFromRuleIDDetails(Connection con, String ruleId) throws Exception {
        PreparedStatement ps = null;
        try {
            CreditScoreRuleBean bean = new CreditScoreRuleBean();
            String query = " SELECT CR.RULECODE,CR.RULEDESCRIPTION,CR.FIELDID,CR.CONDITIONCODE,CR.STATUS,CR.VALUE,CR.MAXVALUE, "
                    + " CR.FIRSTRULECODE AS FSTRULEDES,CR.SECONDRULECODE AS SECONDRULEDES,CR.SCORE,CR.LASTUPDATEDUSER,"
                    + " CR.LASTUPDATEDTIME,S.DESCRIPTION AS STDESCRIPTION ,CF.FIELDNAME AS CFFIELDNAME , OP.DESCRIPTION OPDESCRIPTION "
                    + " FROM CREDITSCORERULE CR,CREDITSCORERULE CRT,CREDITSCORERULE CRS,STATUS S,OPERATOR O,CREDITSCOREFIELD CF, OPERATOR OP "
                    + " WHERE CR.STATUS=S.STATUSCODE AND O.OPERATORCODE=CR.CONDITIONCODE AND"
                    + " CR.FIRSTRULECODE =CRT.RULECODE(+) AND CR.SECONDRULECODE=CRS.RULECODE(+) AND CR.RULECODE=?"
                    + " AND CF.FIELDID=CR.FIELDID AND OP.OPERATORCODE = CR.CONDITIONCODE";

            ps = con.prepareStatement(query);
            ps.setString(1, ruleId);


            rs = ps.executeQuery();
            while (rs.next()) {

                bean.setRuleCode(rs.getString("RULECODE"));
                bean.setRuleName(rs.getString("RULEDESCRIPTION"));
                bean.setFieldName(rs.getString("FIELDID"));
                bean.setCondition(rs.getString("CONDITIONCODE"));
                bean.setValue(rs.getString("VALUE"));
                bean.setMaxValue(rs.getString("MAXVALUE"));
                bean.setRuleNoOne(rs.getString("FSTRULEDES"));
                bean.setRuleNoTwo(rs.getString("SECONDRULEDES"));
                bean.setScore(rs.getString("SCORE"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setStatusDes(rs.getString("STDESCRIPTION"));
                bean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                bean.setLastUpdatedTime(rs.getDate("LASTUPDATEDTIME"));
                bean.setFieldNameDes(rs.getString("CFFIELDNAME"));
                bean.setConditionDes(rs.getString("OPDESCRIPTION"));


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

    ///////////////////////////////
    /**
     * 
     * @param con
     * @param bean
     * @return
     * @throws Exception 
     */
    public int updateCreditScoreRuleDetail(Connection con, CreditScoreRuleBean bean) throws Exception {
        int resultID = -1;
        PreparedStatement ps = null;
        try {
            String query = "UPDATE CREDITSCORERULE CSR SET "
                    + "CSR.RULEDESCRIPTION=?,CSR.FIELDID=?,CSR.CONDITIONCODE=?,"
                    + "CSR.VALUE=?,CSR.MAXVALUE=?,CSR.FIRSTRULECODE=?,CSR.SECONDRULECODE=?,CSR.SCORE=?,CSR.STATUS=?,CSR.LASTUPDATEDUSER=?,CSR.LASTUPDATEDTIME=SYSDATE "
                    + "WHERE CSR.RULECODE=? ";
            ps = con.prepareStatement(query);

            ps.setString(1, bean.getRuleName());
            ps.setString(2, bean.getFieldName());
            ps.setString(3, bean.getCondition());
            ps.setString(4, bean.getValue());
            ps.setString(5, bean.getMaxValue());
            ps.setString(6, bean.getRuleNoOne());
            ps.setString(7, bean.getRuleNoTwo());
            ps.setString(8, bean.getScore());
            ps.setString(9, bean.getStatus());
            ps.setString(10, bean.getLastUpdatedUser());
            ps.setString(11, bean.getRuleCode());


            resultID = ps.executeUpdate();

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

        return resultID;
    }

    //////////////////////////////
    /**
     * insertNewCreditScoreRule
     * @param bean
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int insertNewCreditScoreRule(CreditScoreRuleBean bean, Connection con) throws SQLException, Exception {
        int row = -1;
        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO CREDITSCORERULE  (RULECODE,RULEDESCRIPTION,FIELDID,CONDITIONCODE,"
                    + "VALUE,MAXVALUE,FIRSTRULECODE,SECONDRULECODE,SCORE,STATUS,LASTUPDATEDUSER,LASTUPDATEDTIME) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,SYSDATE )";

            ps = con.prepareStatement(query);
            ps.setString(1, bean.getRuleCode());
            ps.setString(2, bean.getRuleName());
            ps.setString(3, bean.getFieldName());
            ps.setString(4, bean.getCondition());
            ps.setString(5, bean.getValue());
            ps.setString(6, bean.getMaxValue());
            ps.setString(7, bean.getRuleNoOne());
            ps.setString(8, bean.getRuleNoTwo());
            ps.setString(9, bean.getScore());
            ps.setString(10, bean.getStatus());
            ps.setString(11, bean.getLastUpdatedUser());
            


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
     * getAllDistinctCreditScoreRules
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<CreditScoreRuleBean> getAllDistinctCreditScoreRules(Connection con) throws SQLException, Exception {

        List<CreditScoreRuleBean> list = new ArrayList<CreditScoreRuleBean>();
        PreparedStatement ps = null;
        try {

            String query = "SELECT  RULECODE,RULEDESCRIPTION FROM CREDITSCORERULE WHERE STATUS=?";

            ps = con.prepareStatement(query);
            ps.setString(1, StatusVarList.ACTIVE_STATUS);
            rs = ps.executeQuery();

            while (rs.next()) {
                CreditScoreRuleBean bean = new CreditScoreRuleBean();
                bean.setRuleCode(rs.getString("RULECODE"));
                bean.setRuleName(rs.getString("RULEDESCRIPTION"));
                list.add(bean);
            }
            return list;
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
     * delete specific credit score rule 
     * @param con
     * @param ID - rule id that want to delete- String
     * @return int - number of row that deleted
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteCreditScoreRule(Connection con, String ID) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM CREDITSCORERULE CSR where CSR.RULECODE =?";

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
}
