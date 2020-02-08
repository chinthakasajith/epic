/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.creditscore.persistance;

import com.epic.cms.camm.applicationconfig.creditscore.bean.CreditScoreFieldBean;
import com.epic.cms.system.util.datetime.SystemDateTime;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *this class use for wrote all the persistance that relate to credit score field
 * @author ayesh
 */
public class CreditScoreFieldPersistance {

    private ResultSet rs = null;

    /**
     * get all distinct form list
     * @return List<String>
     */
    public List<String> getCreditScoreFormName(Connection con) throws SQLException, Exception {

        List<String> formList = new ArrayList<String>();
        PreparedStatement ps = null;
        try {

            String query = "SELECT DISTINCT FORMNAME FROM CREDITSCOREFORMFIELDS";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                formList.add(rs.getString("FORMNAME"));
            }
            return formList;
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
     * get field id according to form name
     * @param  formName - String
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<String> getCreditScoreFieldName(Connection con, String formName) throws SQLException, Exception {

        List<String> fieldList = new ArrayList<String>();
        PreparedStatement ps = null;
        try {

            String query = "SELECT FIELDNAME FROM CREDITSCOREFORMFIELDS WHERE FORMNAME=?";

            ps = con.prepareStatement(query);
            ps.setString(1, formName);
            rs = ps.executeQuery();

            while (rs.next()) {
                fieldList.add(rs.getString("fIELDNAME"));
            }
            return fieldList;
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
    
    
   /////////////////////////////THIS IS FROM UPUL////////////////////////// 
    /**
     * getCreditScoreFieldName
     * @param con
     * @param formName
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<CreditScoreFieldBean> getAllActiveCreditScoreFieldName(Connection con) throws SQLException, Exception {

        List<CreditScoreFieldBean> fieldList = new ArrayList<CreditScoreFieldBean>();
        
        PreparedStatement ps = null;
        try {

            String query = "SELECT CF.FIELDID,CF.FIELDNAME FROM CREDITSCOREFIELD CF WHERE CF.STATUS=?";

            ps = con.prepareStatement(query);
            ps.setString(1, StatusVarList.ACTIVE_STATUS);
            rs = ps.executeQuery();

            while (rs.next()) {
                CreditScoreFieldBean scoreFieldBean=new CreditScoreFieldBean();
                scoreFieldBean.setFieldCode(rs.getString("FIELDID"));
                scoreFieldBean.setFieldDes(rs.getString("FIELDNAME"));
                
                fieldList.add(scoreFieldBean);
            }
            return fieldList;
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
     * getCreditScoreFieldName
     * @param con
     * @param formName
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    
      public String getCreditScoreFieldDataType(Connection con, String fieldName) throws SQLException, Exception {

        String dataType = null;
        PreparedStatement ps = null;
        try {

            String query = "SELECT DATATYPE FROM CREDITSCOREFIELD WHERE FIELDID=?";

            ps = con.prepareStatement(query);
            ps.setString(1, fieldName);
            rs = ps.executeQuery();

            while (rs.next()) {
               dataType=rs.getString("DATATYPE");
            }
            return dataType;
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
    
    
    
    /////////////////////////////THIS IS FROM UPUL////////////////////////// 

    /**
     * insert new field to credit score
     * @param bean
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int insertNewCreditScoreField(CreditScoreFieldBean bean, Connection con) throws SQLException, Exception {
        int row = -1;
        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO CREDITSCOREFIELD CR (CR.FIELDID,"
                    + "CR.FIELDDESCRIPTION,CR.FIELDNAME,CR.FORMNAME,CR.STATUS,CR.DATATYPE,CR.LASPUPDATEDUSER) "
                    + "VALUES(?,?,?,?,?,?,?)";

            ps = con.prepareStatement(query);
            ps.setString(1, bean.getFieldCode());
            ps.setString(2, bean.getFieldDes());
            ps.setString(3, bean.getFieldName());
            ps.setString(4, bean.getFormName());
            ps.setString(5, bean.getStatus());
            ps.setString(6, bean.getDataType());
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
     * get all detail about credit score field details
     * @param con
     * @return
     * @throws Exception 
     */
    public List<CreditScoreFieldBean> getAllCreditScoreFieldDetails(Connection con) throws Exception {
        PreparedStatement ps = null;
        try {
            List<CreditScoreFieldBean> creditScbeanList = new ArrayList<CreditScoreFieldBean>();
            String query = "SELECT CR.FIELDID,CR.FIELDDESCRIPTION,CR.FIELDNAME,"
                    + "CR.FORMNAME,ST.DESCRIPTION AS STATUS,CR.LASPUPDATEDUSER,CR.DATATYPE,"
                    + "CR.LASTUPDATEDTIME,CR.CREATEDDATE,CR.STATUS AS CRSTATUS FROM CREDITSCOREFIELD CR,"
                    + "STATUS ST WHERE ST.STATUSCODE= CR.STATUS";

            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                CreditScoreFieldBean bean = new CreditScoreFieldBean();
                bean.setFieldCode(rs.getString("FIELDID"));
                bean.setFieldDes(rs.getString("FIELDDESCRIPTION"));
                bean.setFieldName(rs.getString("FIELDNAME"));
                bean.setFormName(rs.getString("FORMNAME"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setLastUpdateUser(rs.getString("LASPUPDATEDUSER"));
                bean.setLastUpDateDate(rs.getDate("LASTUPDATEDTIME"));
                bean.setCreateDate(rs.getDate("CREATEDDATE"));
                bean.setDataType(rs.getString("DATATYPE"));
                bean.setStatusDes(rs.getString("CRSTATUS"));
                creditScbeanList.add(bean);

            }
            return creditScbeanList;

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
     * delete score field persistance 
     * @param con
     * @param appID
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteCreditScoreField(Connection con, String ID) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM CREDITSCOREFIELD CSF where CSF.FIELDID =?";

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
     * update credit score field 
     * @param con
     * @param bean
     * @return
     * @throws Exception 
     */
    public int updateCreditScoreField(Connection con, CreditScoreFieldBean bean) throws Exception {
        int resultID = -1;
        PreparedStatement ps = null;
        try {
            String query = "UPDATE CREDITSCOREFIELD CSF SET "
                    + "CSF.FIELDDESCRIPTION=?,CSF.FORMNAME=?,CSF.FIELDNAME=?,"
                    + "CSF.STATUS=?,CSF.LASPUPDATEDUSER=?,CSF.LASTUPDATEDTIME=?,CSF.DATATYPE=? "
                    + "WHERE CSF.FIELDID=? ";
            ps = con.prepareStatement(query);

            ps.setString(1, bean.getFieldDes());
            ps.setString(2, bean.getFormName());
            ps.setString(3, bean.getFieldName());
            ps.setString(4, bean.getStatus());
            ps.setString(5, bean.getLastUpdateUser());
            ps.setTimestamp(6, SystemDateTime.getSystemDataAndTime());
            ps.setString(7, bean.getDataType());
            ps.setString(8, bean.getFieldCode());


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
}
