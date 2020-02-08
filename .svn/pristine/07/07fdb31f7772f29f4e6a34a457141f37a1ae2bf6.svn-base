/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.eodlettergeneration.persistance;

import com.epic.cms.backoffice.eodlettergeneration.bean.LetterDetailsBean;
import com.epic.cms.backoffice.eodlettergeneration.bean.LetterFieldDetailsBean;
import com.epic.cms.templatemgt.lettertamplate.bean.LetterBean;
import com.epic.cms.templatemgt.lettertamplate.bean.LetterFieldBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author chinthaka_r
 */
public class LetterGenerationPersistance {

    private ResultSet rs = null;
    private LetterBean letterBean = null;
    private List<LetterDetailsBean> searchedletterList = null;

    //store details about letters have to be generated
    public int saveLetterDetailsHaveToBeGenerated(Connection cmsCon, LetterDetailsBean bean, String uniqueRefId) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        String query = "INSERT INTO LETTERDETAILS (IDTYPE,ID,TEMPLATECODE,REFNUMBER,STATUS,CREATEDDATE,LASTUPDATEDDATE,LASTUPDATEDUSER) VALUES (?,?,?,?,?,SYSDATE,SYSDATE,?)";
        try {
            ps = cmsCon.prepareStatement(query);
            ps.setString(1, bean.getIdType());
            ps.setString(2, bean.getId());
            ps.setString(3, bean.getTmpCode());
            ps.setString(4, uniqueRefId);
            ps.setString(5, "NO");
            ps.setString(6, bean.getLastUpdatedUser());
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

    //get parameter values to fill letter template
    public LetterFieldDetailsBean getLetterParamValues(Connection cmsCon, String letterRefId) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        LetterFieldDetailsBean bean = null;
        PreparedStatement ps = null;
        //String query = "SELECT REFNUMBER,TEMPLATECODE,NAMEONCARD,CARDNUMBER,CREDITLIMIT FROM LETTERDETAILS JOIN  CARD ON LETTERDETAILS.ID =CARD.IDNUMBER WHERE LETTERDETAILS.REFNUMBER=?";
        String query = "SELECT PV.TITLE,"
                + "  PV.FIRSTNAME,"
                + "  PV.LASTNAME,"
                + "  PV.IDENTIFICATIONTYPE,"
                + "  PV.IDENTIFICATIONNO,"
                + "  PV.ADDRESS1,"
                + "  PV.ADDRESS2,"
                + "  PV.ADDRESS3,"
                + "  PV.NAMEONCARD,"
                + "  PV.REQUESTCREDITLIMIT,"
                + "  LD.TEMPLATECODE,"
                + "  LD.REFNUMBER "
                + "FROM PERSONALDETAILSOFAPPLICANTVIEW PV "
                + "JOIN LETTERDETAILS LD "
                + "ON PV.IDENTIFICATIONNO=LD.ID WHERE LD.REFNUMBER= ?";
        try {
            ps = cmsCon.prepareStatement(query);
            ps.setString(1, letterRefId);
            rs = ps.executeQuery();

            while (rs.next()) {
                bean = new LetterFieldDetailsBean();

                bean.setRefNo(rs.getString("REFNUMBER"));
                bean.setTmpCode(rs.getString("TEMPLATECODE"));
                bean.setTitle(rs.getString("TITLE"));
                bean.setName(rs.getString("NAMEONCARD"));
                bean.setAddress1(rs.getString("ADDRESS1"));
                bean.setAddress2(rs.getString("ADDRESS2"));
                bean.setAddress3(rs.getString("ADDRESS3"));
            }
            //set formatted date in bean
            bean.setDate(sdf.format(new Date()));
            return bean;
        } catch (SQLDataException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (SQLException ex) {
                    throw ex;
                }
            }
        }
    }

    //get template code to letterRefNumber
    public List<String> getTemplateCodeAndIdFromLetterRefNumber(Connection cmsCon, String refNum) throws Exception {
        List<String> tmpCodeAndId = null;
        PreparedStatement ps = null;
        String query = "SELECT TEMPLATECODE,ID FROM LETTERDETAILS WHERE REFNUMBER =?";
        try {
            tmpCodeAndId=new ArrayList<>();
            ps = cmsCon.prepareStatement(query);
            ps.setString(1, refNum);
            rs = ps.executeQuery();
            while (rs.next()) {
                tmpCodeAndId.add(rs.getString("TEMPLATECODE"));
                tmpCodeAndId.add(rs.getString("ID"));
            }
        } catch (SQLDataException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (SQLException ex) {
                    throw ex;
                }
            }
        }
        return tmpCodeAndId;
    }

    //get dinamic values
    public String getDynamicValue(Connection cmsCon, String tableName, String fieldName,String idColumnName, String identificationNum) throws SQLException, Exception {

        PreparedStatement ps = null;
        String query;
        String dynamicValue = null;

        query = "SELECT "+fieldName+" AS DYNAMICVALUE FROM "+tableName+" WHERE "+idColumnName+"= '"+identificationNum+"'";

        try {

            ps = cmsCon.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {

                dynamicValue = rs.getString("DYNAMICVALUE");
            }

            return dynamicValue;

        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (SQLException sqlEx) {
                    throw sqlEx;
                }
            }
        }
    }

    public LetterBean viewSelectedLetterConf(Connection cmsCon, String tmpCode) throws Exception {
        letterBean = new LetterBean();
        PreparedStatement ps = null;
        String query = "SELECT LT.TEMPLATECODE,LT.DESCRIPTION,LT.STATUS,LT.LASTUPDATEDUSER,LT.CREATEDDATE,LT.LASTUPDATEDTIME,LT.TITLE,LT.BODY FROM LETTERTEMPLATE LT WHERE TEMPLATECODE=?";
        try {
            ps = cmsCon.prepareStatement(query);
            ps.setString(1, tmpCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                letterBean.setTemplateCode(rs.getString("TEMPLATECODE"));
                letterBean.setDescription(rs.getString("DESCRIPTION"));
                letterBean.setStatus(rs.getString("STATUS"));
                letterBean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                letterBean.setCreatedTime(rs.getTimestamp("CREATEDDATE"));
                letterBean.setLastUpdatedTime(rs.getTimestamp("LASTUPDATEDTIME"));
                letterBean.setTitle(rs.getString("TITLE"));
                letterBean.setBody(rs.getString("BODY"));

            }
            return letterBean;
        } catch (SQLDataException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (SQLException ex) {
                    throw ex;
                }
            }
        }
    }

    //save generated letters 
    public int saveGeneratedLetters(Connection cmsCon, Map<String, String> letterList) throws Exception {
        int count = 0;
        PreparedStatement ps = null;
        String query = "UPDATE LETTERDETAILS SET LETTERDATA= ?,STATUS='"+"ACT"+"' WHERE REFNUMBER=?";

        try {
            System.out.println(letterList.size());
            ps = cmsCon.prepareStatement(query);
            for (Map.Entry<String, String> e : letterList.entrySet()) {

                ps.setString(1, e.getValue());
                ps.setString(2, e.getKey());

                count += ps.executeUpdate();
            }
        } catch (SQLDataException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (SQLException ex) {
                    throw ex;
                }
            }
        }
        return count;

    }

    //get all generated letter details 
    public List<LetterDetailsBean> getAllLetterDetails(Connection cmsCon) throws Exception {
        PreparedStatement ps = null;
        searchedletterList = new ArrayList<LetterDetailsBean>();
        String query = "SELECT LD.IDTYPE,LD.ID,LD.TEMPLATECODE,LD.REFNUMBER,LD.STATUS,LD.LASTUPDATEDDATE,LT.DESCRIPTION AS TMPDES,ST.DESCRIPTION AS STDES "
                + "FROM LETTERDETAILS LD "
                + "JOIN LETTERTEMPLATE LT ON LD.TEMPLATECODE=LT.TEMPLATECODE "
                + "JOIN STATUS ST ON LD.STATUS=ST.STATUSCODE "
                + "WHERE LD.STATUS='NO' ";
        try {
            ps = cmsCon.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                LetterDetailsBean bean = new LetterDetailsBean();
                bean.setId(rs.getString("ID"));
                bean.setIdType(rs.getString("IDTYPE"));
                bean.setRefNum(rs.getString("REFNUMBER"));
                bean.setTmpCode(rs.getString("TEMPLATECODE"));
                bean.setTmpDes(rs.getString("TMPDES"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setStatusDes(rs.getString("STDES"));

                searchedletterList.add(bean);
            }
            return searchedletterList;

        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (SQLException sqlEx) {
                    throw sqlEx;
                }
            }
        }
    }

    public List<String> getTableValues(Connection cmsCon, String fieldName) throws SQLException, Exception {
        PreparedStatement ps = null;
        String query;
        List<String> dynamicValues = null;
        query = "SELECT TABLEFIELD, TABLENAME,IDTABLEFIELD FROM LETTERFIELDDETAILS WHERE FIELDNAME=?";

        try {

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, fieldName);

            rs = ps.executeQuery();
            dynamicValues = new ArrayList<String>();

            while (rs.next()) {
                dynamicValues.add(rs.getString("TABLENAME"));
                dynamicValues.add(rs.getString("TABLEFIELD"));
                dynamicValues.add(rs.getString("IDTABLEFIELD"));
            }

            return dynamicValues;

        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (SQLException sqlEx) {
                    throw sqlEx;
                }
            }
        }
    }

    //get all fieldnames from letterFieldDtails table

    public List<String> getAllFieldNamesFromLetterFieldDetails(Connection cmsCon) throws Exception {
        List<String> fieldValues = null;
        PreparedStatement ps = null;
        String query = "SELECT FIELDNAME FROM LETTERFIELDDETAILS";
        try {
            fieldValues=new ArrayList<String>();
            ps = cmsCon.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                fieldValues.add(rs.getString("FIELDNAME"));
            }
        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (SQLException sqlEx) {
                    throw sqlEx;
                }
            }
        }
        return fieldValues;
    }
}
