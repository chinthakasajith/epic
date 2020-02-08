/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.securityquesmgt.persistance;

import com.epic.cms.admin.controlpanel.securityquesmgt.bean.SecurityQuestionBean;
import com.epic.cms.system.util.datetime.SystemDateTime;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *  
 * @author nisansala
 */
public class SecurityQuestionPersistance {

    /**
     * retrieve all security questions 
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<SecurityQuestionBean> getAllSecurityQuestion(Connection con) throws SQLException, Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            List<SecurityQuestionBean> questionList = new ArrayList<SecurityQuestionBean>();
            String query = "SELECT SQ.QUESTIONNO,SQ.QUESTION,SQ.PRIORITYLEVEL,PL.DESCRIPTION AS PRIORITY_DES,"
                    + "SQ.ISSUACCSTATUS,SQ.QUESTIONTYPE,SQ.TABLENAME,SQ.FIELDNAME1,SQ.FIELDNAME2,SQ.FIELDNAME3,SQ.FIELDNAME4,"
                    + "SQ.CARDCATEGORY,CC.DESCRIPTION AS CATEGORY_DES,"
                    + "SQ.LASTUPDATEDUSER,SQ.LASTUPDATEDTIME,SQ.CREATEDTIME,SQ.STATUS,ST.DESCRIPTION AS STATUS_DES "
                    + "FROM SECURITYQUESTION SQ, STATUS ST, PRIORITYLEVEL PL, CARDCATEGORY CC "
                    + "WHERE SQ.STATUS=ST.STATUSCODE AND PL.PRIORITYLEVELCODE=SQ.PRIORITYLEVEL AND SQ.CARDCATEGORY=CC.CATEGORYCODE AND SQ.ISSUACCSTATUS='ISS' "
                    + "ORDER BY SQ.QUESTIONNO";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                SecurityQuestionBean bean = new SecurityQuestionBean();

                bean.setQuestionNo(rs.getString("QUESTIONNO"));
                bean.setQuestion(rs.getString("QUESTION"));
                bean.setPriorityLevel(rs.getString("PRIORITYLEVEL"));
                bean.setPriorityDes(rs.getString("PRIORITY_DES"));
                bean.setIssueAcquireStatus(rs.getString("ISSUACCSTATUS"));
                bean.setQuestionType(rs.getString("QUESTIONTYPE"));
                bean.setTableName(rs.getString("TABLENAME"));
                bean.setField1(rs.getString("FIELDNAME1"));
                bean.setField2(rs.getString("FIELDNAME2"));
                bean.setField3(rs.getString("FIELDNAME3"));
                bean.setField4(rs.getString("FIELDNAME4"));
                bean.setCardCategory(rs.getString("CARDCATEGORY"));
                bean.setCardCategoryDes(rs.getString("CATEGORY_DES"));
                bean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                bean.setLastUpdatedTime(rs.getTimestamp("LASTUPDATEDTIME"));
                bean.setCreatedTime(rs.getTimestamp("CREATEDTIME"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setStatusDes(rs.getString("STATUS_DES"));

                questionList.add(bean);

            }
            return questionList;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }
    
    //getAllACQSecurityQuestions
    public List<SecurityQuestionBean> getAllACQSecurityQuestions(Connection con) throws SQLException, Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            List<SecurityQuestionBean> questionList = new ArrayList<SecurityQuestionBean>();
            String query = "SELECT SQ.QUESTIONNO,SQ.QUESTION,SQ.PRIORITYLEVEL,PL.DESCRIPTION AS PRIORITY_DES,"
                    + "SQ.ISSUACCSTATUS,SQ.QUESTIONTYPE,SQ.TABLENAME,SQ.FIELDNAME1,SQ.FIELDNAME2,SQ.FIELDNAME3,SQ.FIELDNAME4,"                    
                    + "SQ.LASTUPDATEDUSER,SQ.LASTUPDATEDTIME,SQ.CREATEDTIME,SQ.STATUS,ST.DESCRIPTION AS STATUS_DES "
                    + "FROM SECURITYQUESTION SQ, STATUS ST, PRIORITYLEVEL PL "
                    + "WHERE SQ.STATUS=ST.STATUSCODE AND PL.PRIORITYLEVELCODE=SQ.PRIORITYLEVEL AND SQ.ISSUACCSTATUS='ACQ' "
                    + "ORDER BY SQ.QUESTIONNO";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                SecurityQuestionBean bean = new SecurityQuestionBean();

                bean.setQuestionNo(rs.getString("QUESTIONNO"));
                bean.setQuestion(rs.getString("QUESTION"));
                bean.setPriorityLevel(rs.getString("PRIORITYLEVEL"));
                bean.setPriorityDes(rs.getString("PRIORITY_DES"));
                bean.setIssueAcquireStatus(rs.getString("ISSUACCSTATUS"));
                bean.setQuestionType(rs.getString("QUESTIONTYPE"));
                bean.setTableName(rs.getString("TABLENAME"));
                bean.setField1(rs.getString("FIELDNAME1"));
                bean.setField2(rs.getString("FIELDNAME2"));
                bean.setField3(rs.getString("FIELDNAME3"));
                bean.setField4(rs.getString("FIELDNAME4"));
                bean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                bean.setLastUpdatedTime(rs.getTimestamp("LASTUPDATEDTIME"));
                bean.setCreatedTime(rs.getTimestamp("CREATEDTIME"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setStatusDes(rs.getString("STATUS_DES"));

                questionList.add(bean);

            }
            return questionList;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    /**
     * to view one security question
     * @param con
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public SecurityQuestionBean viewOneSecurityQuestion(Connection con, String id) throws SQLException, Exception {

        SecurityQuestionBean bean = new SecurityQuestionBean();

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT SQ.QUESTIONNO,SQ.QUESTION,SQ.PRIORITYLEVEL,PL.DESCRIPTION AS PRIORITY_DES,"
                    + "SQ.ISSUACCSTATUS,SQ.QUESTIONTYPE,SQ.TABLENAME,SQ.FIELDNAME1,SQ.FIELDNAME2,SQ.FIELDNAME3,SQ.FIELDNAME4,"
                    + "SQ.CARDCATEGORY,CC.DESCRIPTION AS CATEGORY_DES,"
                    + "SQ.LASTUPDATEDUSER,SQ.LASTUPDATEDTIME,SQ.CREATEDTIME,SQ.STATUS,ST.DESCRIPTION AS STATUS_DES "
                    + "FROM SECURITYQUESTION SQ, STATUS ST, PRIORITYLEVEL PL,CARDCATEGORY CC "
                    + "WHERE SQ.STATUS=ST.STATUSCODE AND PL.PRIORITYLEVELCODE=SQ.PRIORITYLEVEL AND SQ.CARDCATEGORY=CC.CATEGORYCODE AND SQ.QUESTIONNO=? ";

            ps = con.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                bean.setQuestionNo(rs.getString("QUESTIONNO"));
                bean.setQuestion(rs.getString("QUESTION"));
                bean.setPriorityLevel(rs.getString("PRIORITYLEVEL"));
                bean.setPriorityDes(rs.getString("PRIORITY_DES"));
                bean.setIssueAcquireStatus(rs.getString("ISSUACCSTATUS"));
                bean.setQuestionType(rs.getString("QUESTIONTYPE"));
                bean.setTableName(rs.getString("TABLENAME"));
                bean.setField1(rs.getString("FIELDNAME1"));
                bean.setField2(rs.getString("FIELDNAME2"));
                bean.setField3(rs.getString("FIELDNAME3"));
                bean.setField4(rs.getString("FIELDNAME4"));
                bean.setCardCategory(rs.getString("CARDCATEGORY"));
                bean.setCardCategoryDes(rs.getString("CATEGORY_DES"));
                bean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                bean.setLastUpdatedTime(rs.getTimestamp("LASTUPDATEDTIME"));
                bean.setCreatedTime(rs.getTimestamp("CREATEDTIME"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setStatusDes(rs.getString("STATUS_DES"));

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
    
    //to Acquire quiz
    public SecurityQuestionBean viewOneAcquireSecurityQuestion(Connection con, String id) throws SQLException, Exception {

        SecurityQuestionBean bean = new SecurityQuestionBean();

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT SQ.QUESTIONNO,SQ.QUESTION,SQ.PRIORITYLEVEL,PL.DESCRIPTION AS PRIORITY_DES,"
                    + "SQ.ISSUACCSTATUS,SQ.QUESTIONTYPE,SQ.TABLENAME,SQ.FIELDNAME1,SQ.FIELDNAME2,SQ.FIELDNAME3,SQ.FIELDNAME4,"                    
                    + "SQ.LASTUPDATEDUSER,SQ.LASTUPDATEDTIME,SQ.CREATEDTIME,SQ.STATUS,ST.DESCRIPTION AS STATUS_DES,sq.LASTUPDATEDUSER AS USERR "
                    + "FROM SECURITYQUESTION SQ, STATUS ST, PRIORITYLEVEL PL "
                    + "WHERE SQ.STATUS=ST.STATUSCODE AND PL.PRIORITYLEVELCODE=SQ.PRIORITYLEVEL AND SQ.QUESTIONNO=? ";

            ps = con.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                bean.setQuestionNo(rs.getString("QUESTIONNO"));
                bean.setQuestion(rs.getString("QUESTION"));
                bean.setPriorityLevel(rs.getString("PRIORITYLEVEL"));
                bean.setPriorityDes(rs.getString("PRIORITY_DES"));
                bean.setIssueAcquireStatus(rs.getString("ISSUACCSTATUS"));
                bean.setQuestionType(rs.getString("QUESTIONTYPE"));
                bean.setTableName(rs.getString("TABLENAME"));
                bean.setField1(rs.getString("FIELDNAME1"));
                bean.setField2(rs.getString("FIELDNAME2"));
                bean.setField3(rs.getString("FIELDNAME3"));
                bean.setField4(rs.getString("FIELDNAME4"));
                bean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                bean.setLastUpdatedTime(rs.getTimestamp("LASTUPDATEDTIME"));
                bean.setCreatedTime(rs.getTimestamp("CREATEDTIME"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setStatusDes(rs.getString("STATUS_DES"));
                bean.setLastUpdatedUser(rs.getString("USERR"));

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
     * to delete a security question
     * @param con
     * @param questionNo
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteSecurityQuestion(Connection con, String questionNo) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM SECURITYQUESTION WHERE QUESTIONNO =?";

            ps = con.prepareStatement(query);
            ps.setString(1, questionNo);

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

    public int getQuestionNumber(Connection con) throws SQLException, Exception {
        int i = -1;
        int num = -1;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT MAX(QUESTIONNO) FROM securityquestion";

            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            while (rs.next()) {
                num = rs.getInt("MAX(QUESTIONNO)");
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

    /**
     * to insert a new security question
     * @param bean
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public Boolean insertNewQuestion(SecurityQuestionBean bean, Connection con) throws SQLException, Exception {
        Boolean success = false;
        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO SECURITYQUESTION (QUESTIONNO,QUESTION,PRIORITYLEVEL,ISSUACCSTATUS,QUESTIONTYPE,"
                    + "TABLENAME,FIELDNAME1,FIELDNAME2,FIELDNAME3,FIELDNAME4,STATUS,CARDCATEGORY,LASTUPDATEDUSER,"
                    + "CREATEDTIME) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";



            ps = con.prepareStatement(query);
            ps.setString(1, bean.getQuestionNo());
            ps.setString(2, bean.getQuestion());
            ps.setString(3, bean.getPriorityLevel());
            ps.setString(4, bean.getIssueAcquireStatus());
            ps.setString(5, bean.getQuestionType());
            ps.setString(6, bean.getTableName());
            ps.setString(7, bean.getField1());
            ps.setString(8, bean.getField2());
            ps.setString(9, bean.getField3());
            ps.setString(10, bean.getField4());
            ps.setString(11, bean.getStatus());
            ps.setString(12, bean.getCardCategory());
            ps.setString(13, bean.getLastUpdatedUser());
            ps.setTimestamp(14, SystemDateTime.getSystemDataAndTime());

            ps.executeUpdate();
            success = true;

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

        return success;
    }

    /**
     * to update a security question
     * @param question
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int updateSecurityQuestion(SecurityQuestionBean question, Connection con) throws SQLException, Exception {
        int row = -1;
        PreparedStatement ps = null;

        try {
            String query = "UPDATE SECURITYQUESTION SET "
                    + "QUESTIONNO=?,QUESTION=?,PRIORITYLEVEL=?,ISSUACCSTATUS=?,QUESTIONTYPE=?,"
                    + "TABLENAME=?,FIELDNAME1=?,FIELDNAME2=?,FIELDNAME3=?,FIELDNAME4=?,STATUS=?,CARDCATEGORY=?,LASTUPDATEDUSER=?,"
                    + "LASTUPDATEDTIME=? WHERE QUESTIONNO=? ";

            ps = con.prepareStatement(query);

            ps.setString(1, question.getQuestionNo());
            ps.setString(2, question.getQuestion());
            ps.setString(3, question.getPriorityLevel());
            ps.setString(4, question.getIssueAcquireStatus());
            ps.setString(5, question.getQuestionType());
            ps.setString(6, question.getTableName());
            ps.setString(7, question.getField1());
            ps.setString(8, question.getField2());
            ps.setString(9, question.getField3());
            ps.setString(10, question.getField4());
            ps.setString(11, question.getStatus());
            ps.setString(12, question.getCardCategory());
            ps.setString(13, question.getLastUpdatedUser());
            ps.setTimestamp(14, SystemDateTime.getSystemDataAndTime());
            ps.setString(15, question.getQuestionNo());

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
     * to get table names for the given category
     * @param cardCategory
     * @return 
     */
    public HashMap<String, String> tablesToCardCategory(String cardCategory) {
        HashMap<String, String> tables = new HashMap<String, String>();
        if (cardCategory.equals("M")) {
            tables.put("1", "CARDAPPLICATION");
            tables.put("2", "CARDAPPLICATIONPERSONALDETAILS");
            tables.put("3", "CARDAPPLICATIONSTATUS");
            tables.put("4", "CARD");
            tables.put("5", "CARDACCOUNT");
            tables.put("6", "CARDCUSTOMER");
            tables.put("7", "CARDMAINCUSTOMERDETAIL");
        } else if (cardCategory.equals("S")) {
            tables.put("1", "CARDAPPLICATION");
            tables.put("2", "CARDAPPLICATIONSTATUS");
            tables.put("3", "CARD");
            tables.put("4", "CARDACCOUNT");
            tables.put("5", "CARDCUSTOMER");
            tables.put("6", "SUPPLEMENTARYAPPLICATION");
            tables.put("7", "CARDSUPPCUSTOMERDETAILS");
        } else if (cardCategory.equals("C")) {
            tables.put("1", "No tables");
        }
        return tables;
    }
    
    //to acquire security question
     public HashMap<String, String> tablesToAcquireQuestions(String qtype) {
        HashMap<String, String> tables = new HashMap<String, String>();
        if (qtype.equals("U")) {
            tables.put("1", "MERCHANTCUSTOMER");
        } else if (qtype.equals("L")) {
            tables.put("1", "MERCHANTLOCATION");
        } else if (qtype.equals("T")) {
            tables.put("1", "TERMINAL");
        }
        return tables;
    }

    /**
     * to retrieve column names according to the table name
     * @param con
     * @param tableName
     * @return
     * @throws SQLException 
     */
    public HashMap<String, String> columnNames(Connection con, String tableName) throws SQLException {
        HashMap<String, String> columnNames = new HashMap<String, String>();
        ResultSet rs = null;
        PreparedStatement ps = null;

        String query = "SELECT COLUMN_NAME FROM ALL_TAB_COLS WHERE TABLE_NAME =? ORDER BY COLUMN_NAME";

        ps = con.prepareStatement(query);

        ps.setString(1, tableName);
        rs = ps.executeQuery();

        while (rs.next()) {
            columnNames.put(rs.getString(1), rs.getString(1));

        }
        return columnNames;
    }

    public List<String> columnName(Connection con, String tableName) throws SQLException {
        List<String> columnNames = new ArrayList<String>();
        ResultSet rs = null;
        PreparedStatement ps = null;

        String query = "SELECT COLUMN_NAME FROM ALL_TAB_COLS WHERE TABLE_NAME =? ORDER BY COLUMN_NAME";

        ps = con.prepareStatement(query);

        ps.setString(1, tableName);
        rs = ps.executeQuery();

        while (rs.next()) {
            columnNames.add(rs.getString(1));

        }
        return columnNames;
    }
}
