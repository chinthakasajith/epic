/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.creditscore.persistance;

import com.epic.cms.camm.applicationconfig.creditscore.bean.CreditScoreBean;
import com.epic.cms.camm.applicationconfig.creditscore.bean.CreditScoreRuleBean;
import com.epic.cms.system.util.logs.LogFileCreator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mahesh_m
 */
public class CreditScorePersistance {

    private ResultSet rs = null;

    public String getScoreCardRuleCode(Connection con,String cardType) throws Exception {
        PreparedStatement ps = null;
        String scoreCardCode = null;
        try {

            String query = "SELECT SCORECARDCODE FROM SCORECARD WHERE CARDTYPECODE = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, cardType);
            rs = ps.executeQuery();
            while (rs.next()) {
                scoreCardCode = rs.getString("SCORECARDCODE");
            }
            return scoreCardCode;
        } catch (SQLException e) {
            LogFileCreator.writeErrorToCreditScoreLog(e);
            throw e;
        } catch (Exception e2) {
            LogFileCreator.writeErrorToCreditScoreLog(e2);
            throw e2;
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
    public String getRequestedCardType(Connection con,String applicationId ,String cardCategoryCode) throws Exception {
        PreparedStatement ps = null;
        String scoreCardCode = null;
        try {
            String query="";
            if(cardCategoryCode!=null && cardCategoryCode.equals("E")){
                query = "SELECT REQUESTCARDTYPE FROM ESTABLISHMENTDETAILS WHERE APPLICATIONID = ?";            

            }else{
                query = "SELECT REQUESTCARDTYPE FROM CARDAPPLICATIONPERSONALDETAILS WHERE APPLICATIONID = ?";            
            }
            ps = con.prepareStatement(query);
            ps.setString(1, applicationId);
            rs = ps.executeQuery();
            while (rs.next()) {
                scoreCardCode = rs.getString("REQUESTCARDTYPE");
            }
            return scoreCardCode;
        } catch (SQLException e) {
            LogFileCreator.writeErrorToCreditScoreLog(e);
            throw e;
        } catch (Exception e2) {
            LogFileCreator.writeErrorToCreditScoreLog(e2);
            throw e2;
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
    public List<String> getCreditScoreRuleList(Connection con, String scoreCardCode) throws Exception {
        PreparedStatement ps = null;
        List<String> creditScoreRuleList = new ArrayList<String>();
        try {

            String query = "SELECT SR.SCORECARDRULECODE FROM SCORECARDRULES SR WHERE SCORECARDCODE = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, scoreCardCode);
            rs = ps.executeQuery();

            while (rs.next()) {
                creditScoreRuleList.add(rs.getString("SCORECARDRULECODE"));
            }
            return creditScoreRuleList;
        } catch (SQLException e) {
            LogFileCreator.writeErrorToCreditScoreLog(e);
            throw e;
        } catch (Exception e2) {
            LogFileCreator.writeErrorToCreditScoreLog(e2);
            throw e2;
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

    public CreditScoreBean getFieldId(Connection con, CreditScoreBean bean) throws Exception {
        PreparedStatement ps = null;
        String fieldid = null;
        try {

            String query = "SELECT CSR.FIELDID FROM CREDITSCORERULE CSR WHERE RULECODE = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, bean.getRuleCode());

            rs = ps.executeQuery();

            while (rs.next()) {
                bean.setFieldid(rs.getString("FIELDID"));
            }
            return bean;
        } catch (SQLException e) {
            LogFileCreator.writeErrorToCreditScoreLog(e);
            throw e;
        } catch (Exception e2) {
            LogFileCreator.writeErrorToCreditScoreLog(e2);
            throw e2;
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

    public CreditScoreBean getFieldNameAndFormName(Connection con, CreditScoreBean bean) throws Exception {
        PreparedStatement ps = null;
        try {

            String query = "SELECT CSF.FIELDNAME,CSF.FORMNAME,CSF.DATATYPE FROM CREDITSCOREFIELD CSF WHERE FIELDID = ? AND STATUS = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, bean.getFieldid());
            ps.setString(2, "ACT");
            rs = ps.executeQuery();

            while (rs.next()) {
                bean.setFieldName(rs.getString("FIELDNAME"));
                bean.setFormName(rs.getString("FORMNAME"));
                bean.setDataType(rs.getString("DATATYPE"));
            }

            return bean;
        } catch (SQLException e) {
            LogFileCreator.writeErrorToCreditScoreLog(e);
            throw e;
        } catch (Exception e2) {
            LogFileCreator.writeErrorToCreditScoreLog(e2);
            throw e2;
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

    public CreditScoreBean getTableNameAndColmnName(Connection con, CreditScoreBean bean) throws Exception {
        PreparedStatement ps = null;
        try {

            String query = "SELECT CSFF.TABLENAME,CSFF.COLUMNNAME FROM CREDITSCOREFORMFIELDS CSFF WHERE FORMNAME = ? AND FIELDNAME = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, bean.getFormName());
            ps.setString(2, bean.getFieldName());
            rs = ps.executeQuery();

            while (rs.next()) {
                bean.setTablename(rs.getString("TABLENAME").trim());
                bean.setColumnName(rs.getString("COLUMNNAME").trim());
            }
            return bean;
        } catch (SQLException e) {
            LogFileCreator.writeErrorToCreditScoreLog(e);
            throw e;
        } catch (Exception e2) {
            LogFileCreator.writeErrorToCreditScoreLog(e2);
            throw e2;
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

    public CreditScoreBean getValue(Connection con, CreditScoreBean bean, String applicationId) throws Exception {
        PreparedStatement ps = null;
        try {

            String query = "SELECT " + bean.getColumnName() + " FROM " + bean.getTablename() + " WHERE APPLICATIONID = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, applicationId);
            rs = ps.executeQuery();

            while (rs.next()) {
                bean.setValue(rs.getString(bean.getColumnName()));
            }
            return bean;
        } catch (SQLException e) {
            LogFileCreator.writeErrorToCreditScoreLog(e);
            throw e;
        } catch (Exception e2) {
            LogFileCreator.writeErrorToCreditScoreLog(e2);
            throw e2;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
            }
        }
    }

    public CreditScoreRuleBean getCreditScoreRuleDetails(Connection con, String ruleCode) throws Exception {
        PreparedStatement ps = null;
        CreditScoreRuleBean bean = new CreditScoreRuleBean();
        try {

            String query = "SELECT CSR.CONDITIONCODE,CSR.VALUE,CSR.SCORE,CSR.MAXVALUE,CSR.FIRSTRULECODE,CSR.SECONDRULECODE FROM CREDITSCORERULE CSR WHERE CSR.RULECODE = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, ruleCode);
            rs = ps.executeQuery();

            while (rs.next()) {
                bean.setCondition(rs.getString("CONDITIONCODE"));
                bean.setValue(rs.getString("VALUE"));
                bean.setScore(rs.getString("SCORE"));
                bean.setMaxValue(rs.getString("MAXVALUE"));
                bean.setRuleNoOne(rs.getString("FIRSTRULECODE"));
                bean.setRuleNoTwo(rs.getString("SECONDRULECODE"));
            }
            return bean;
        } catch (SQLException e) {
            LogFileCreator.writeErrorToCreditScoreLog(e);
            throw e;
        } catch (Exception e2) {
            LogFileCreator.writeErrorToCreditScoreLog(e2);
            throw e2;
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

    public boolean insertToApplicationCreditScore(Connection con, String applicationId, String ruleCode, String creditScore) throws Exception {
        PreparedStatement ps = null;
        try {

            String query = "INSERT INTO APPLICATIONCREDITSCORE (APPLICATIONID,RULECODE,SCORE,LASTUPDATEDTIME,CREATEDDATE) VALUES (?,?,?,SYSDATE,SYSDATE)";
            ps = con.prepareStatement(query);
            ps.setString(1, applicationId);
            ps.setString(2, ruleCode);
            ps.setString(3, creditScore);

            rs = ps.executeQuery();

            return true;
        } catch (SQLException e) {
            return false;
        } catch (Exception e2) {
            return false;
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

    public boolean updateApplicationCreditScore(Connection con, String applicationId, String ruleCode, String creditScore) throws Exception {
        PreparedStatement ps = null;
        try {

            String query = "UPDATE APPLICATIONCREDITSCORE SET SCORE = ?,LASTUPDATEDTIME = SYSDATE WHERE APPLICATIONID = ? AND RULECODE = ?";
            ps = con.prepareStatement(query);

            ps.setString(1, creditScore);
            ps.setString(2, applicationId);
            ps.setString(3, ruleCode);


            rs = ps.executeQuery();

            return true;
        } catch (SQLException e) {
            return false;
        } catch (Exception e2) {
            return false;
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

    public boolean isRecordExist(Connection con, String applicationId, String ruleCode) throws Exception {
        PreparedStatement ps = null;
        try {

            String query = "SELECT COUNT(*) AS COUNT FROM APPLICATIONCREDITSCORE WHERE APPLICATIONID = ? AND RULECODE = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, applicationId);
            ps.setString(2, ruleCode);

            rs = ps.executeQuery();

            if (rs.next()) {
                int count = Integer.parseInt(rs.getString("COUNT").trim());

                if (count > 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        } catch (Exception e2) {
            return false;
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
