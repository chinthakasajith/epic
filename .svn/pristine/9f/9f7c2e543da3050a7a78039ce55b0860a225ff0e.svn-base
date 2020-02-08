/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.DocumentTypeMgtBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.LetterTemplateBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.VerifyCategoryBean;
import com.epic.cms.system.util.datetime.SystemDateTime;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * this persistence use to wrote all the persistence that relate to document type mgt 
 * @author ayesh
 */
public class DocumentTypeMgtPersistance {

    private ResultSet rs = null;

    /**
     * insert new document type to database
     * @param bean-DocumentTypeMgtBean
     * @param con -Connection
     * @return int-number of row that insert
     * @throws SQLException
     * @throws Exception 
     */
    public int insertNewDocumentType(DocumentTypeMgtBean bean, Connection con) throws SQLException, Exception {
        int row = -1;
        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO APPLICATIONDOCUMENT AD (AD.DOCUMENTTYPECODE,"
                    + "AD.DOCUMENTNAME,AD.VERIFICATIONCATEGORY,AD.POSTPIFIX,AD.STATUS,AD.CARDCATEGORYCODE,AD.ISMANDATORY , AD.LASTUPDATEDUSER) "
                    + "VALUES(?,?,?,?,?,?,?,?)";

            ps = con.prepareStatement(query);
            ps.setString(1, bean.getDocCode());
            ps.setString(2, bean.getDocName());
            ps.setString(3, bean.getVerifyCat());
            ps.setString(4, bean.getPostFix());
            ps.setString(5, bean.getStatus());
            ps.setString(6, bean.getCardCategory());
            ps.setBoolean(7, bean.getIsMandatory());
            ps.setString(8, bean.getLastupdateUser());
            

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
     * get all details about document types
     * @param con
     * @return
     * @throws Exception 
     */
    public List<DocumentTypeMgtBean> getAllDocumentTypeDetails(Connection con) throws Exception {
        PreparedStatement ps = null;
        try {
            List<DocumentTypeMgtBean> creditScbeanList = new ArrayList<DocumentTypeMgtBean>();
            String query = "SELECT AP.DOCUMENTTYPECODE, "
                    + "  AP.DOCUMENTNAME, "
                    + "  VC.DESCRIPTON AS CATEGORY, "
                    + "  AP.POSTPIFIX, "
                    + "  AP.LASTUPDATEDUSER, "
                    + "  AP.LASTUPDATEDTIME, "
                    + "  AP.CREATETIME, "
                    + "  AP.ISMANDATORY, "
                    + "  AP.CARDCATEGORYCODE , "
                    + "  CC.DESCRIPTION AS CARDCATEGORYCODEDES, "
                    + "  ST.DESCRIPTION "
                    + "FROM APPLICATIONDOCUMENT AP "
                    + "LEFT JOIN CARDCATEGORY CC "
                    + "ON CC.CATEGORYCODE =AP.CARDCATEGORYCODE "
                    + "LEFT JOIN STATUS ST "
                    + "ON ST.STATUSCODE =AP.STATUS "
                    + "LEFT JOIN VERIFICATIONCATEGORY VC "
                    + "ON VC.VERIFICATIONCATEGORYCODE=AP.VERIFICATIONCATEGORY";

            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                DocumentTypeMgtBean bean = new DocumentTypeMgtBean();
                bean.setDocCode(rs.getString("DOCUMENTTYPECODE"));
                bean.setDocName(rs.getString("DOCUMENTNAME"));
                bean.setVerifyCat(rs.getString("CATEGORY"));
                bean.setPostFix(rs.getString("POSTPIFIX"));
                bean.setStatus(rs.getString("DESCRIPTION"));
                bean.setLastUpdateDate(rs.getString("LASTUPDATEDTIME"));
                bean.setLastupdateUser(rs.getString("LASTUPDATEDUSER"));
                bean.setCreateDate(rs.getString("CREATETIME"));
                bean.setCardCategory(rs.getString("CARDCATEGORYCODE"));
                bean.setCardCategoryDes(rs.getString("CARDCATEGORYCODEDES"));
                bean.setIsMandatory(rs.getBoolean("ISMANDATORY"));
                
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
     * get all verification category from database
     * @param con
     * @return  List-VerifyCategoryBean
     * @throws Exception 
     */
    public List<VerifyCategoryBean> getVerificationCatgory(Connection con) throws Exception {
        PreparedStatement ps = null;
        try {
            List<VerifyCategoryBean> verifyCatList = new ArrayList<VerifyCategoryBean>();
            String query = "SELECT VC.VERIFICATIONCATEGORYCODE,VC.DESCRIPTON FROM VERIFICATIONCATEGORY VC";

            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                VerifyCategoryBean bean = new VerifyCategoryBean();
                bean.setDescirption(rs.getString("DESCRIPTON"));
                bean.setVrifyCode(rs.getString("VERIFICATIONCATEGORYCODE"));
                verifyCatList.add(bean);
            }
            return verifyCatList;

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
     * get all verification category from database
     * @param con
     * @return  List-VerifyCategoryBean
     * @throws Exception 
     */
    public HashMap<String, String> getLetterFiledFormats(Connection con) throws Exception {
        PreparedStatement ps = null;
        HashMap<String, String> formatList;
        try {

            String query = "SELECT LP.FORMATCODE,LP.DESCRIPTION FROM LETTERFORMATS LP";

            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            formatList = new HashMap<String, String>();

            while (rs.next()) {

                formatList.put(rs.getString("FORMATCODE"), rs.getString("DESCRIPTION"));
            }
            return formatList;

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
     * to update letter templates details
     * @param cmsCon
     * @param bean
     * @return
     * @throws Exception 
     */
    public int updateLetterTemplates(Connection cmsCon, LetterTemplateBean bean) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {


            String query = "UPDATE LETTERTEMPLATE SET DESCRIPTION=?,TEMPLATEFORMAT=?,STATUS=?,PROCESSCODE=?,BODY=?,TITLE=?,"
                    + "LASTUPDATEDUSER=?,LASTUPDATEDTIME=? WHERE TEMPLATECODE=?";

            ps = cmsCon.prepareStatement(query);

            ps.setString(1, bean.getDescription());
            ps.setString(2, bean.getTemplateFormat());
            ps.setString(3, bean.getStatus());
            ps.setString(4, bean.getProcessId());
            ps.setString(5, bean.getBodyString());
            ps.setString(6, bean.getTitleString());
            ps.setString(7, bean.getLastUpdatedUser());
            ps.setTimestamp(8, new Timestamp(bean.getLastUpdatedDate().getTime()));
            ps.setString(9, bean.getTemplateCode());

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
     * get all verification category from database
     * @param con
     * @return  List-VerifyCategoryBean
     * @throws Exception 
     */
    public HashMap<String, String> getLetterProcesses(Connection con) throws Exception {
        PreparedStatement ps = null;
        HashMap<String, String> processList;
        try {

            String query = "SELECT EP.PROCESSID,EP.DESCRIPTION FROM EODPROCESS EP WHERE EP.LETTERGENARATIONSTATUS = ?";

            ps = con.prepareStatement(query);
            ps.setString(1, "YES");
            rs = ps.executeQuery();

            processList = new HashMap<String, String>();

            while (rs.next()) {

                processList.put(rs.getString("PROCESSID"), rs.getString("DESCRIPTION"));
            }

            return processList;

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

    public List<LetterTemplateBean> getAllLetterTemplateDetailsList(Connection con) throws Exception {

        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;
        List<LetterTemplateBean> templateBeanList = null;
        try {
            getAllUserRole = con.prepareStatement("SELECT LT.TEMPLATECODE,LT.DESCRIPTION,LT.STATUS,LT.TEMPLATEFORMAT,LT.PROCESSCODE,"
                    + "EP.DESCRIPTION AS PROCESSDES, ST.DESCRIPTION AS STATUSDES ,LT.BODY AS BODYSTRING,LT.TITLE "
                    + "FROM LETTERTEMPLATE LT,EODPROCESS EP,STATUS ST WHERE EP.PROCESSID = LT.PROCESSCODE AND ST.STATUSCODE = LT.STATUS");

            rs = getAllUserRole.executeQuery();
            templateBeanList = new ArrayList<LetterTemplateBean>();

            while (rs.next()) {

                LetterTemplateBean resultBean = new LetterTemplateBean();

                resultBean.setTemplateCode(rs.getString("TEMPLATECODE"));
                resultBean.setDescription(rs.getString("DESCRIPTION"));
                resultBean.setStatus(rs.getString("STATUS"));
                resultBean.setStatusDes(rs.getString("STATUSDES"));
                resultBean.setTemplateFormat(rs.getString("TEMPLATEFORMAT"));
                resultBean.setProcessId(rs.getString("PROCESSCODE"));
                resultBean.setProcessDes(rs.getString("PROCESSDES"));
                resultBean.setBodyString(rs.getString("BODYSTRING"));
                resultBean.setTitleString(rs.getString("TITLE"));


                templateBeanList.add(resultBean);

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();

        }

        return templateBeanList;
    }

    /**
     * get all verification category from database
     * @param con
     * @return  List-VerifyCategoryBean
     * @throws Exception 
     */
    public HashSet<String> getLetterFieldsDetail(Connection con) throws Exception {

        PreparedStatement ps = null;
        HashSet<String> fieldsList;
        try {

            String query = "SELECT LF.FIELDNAME FROM LETTERFIELDDETAILS LF";

            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            fieldsList = new HashSet<String>();

            while (rs.next()) {

                fieldsList.add(rs.getString("FIELDNAME"));
            }
            return fieldsList;

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
     * get selected document type code details.
     * @param con
     * @param docCode-selected document code
     * @return DocumentTypeMgtBean
     * @throws Exception 
     */
    public DocumentTypeMgtBean getSelectedDocumentType(Connection con, String docCode) throws Exception {
        PreparedStatement ps = null;
        try {
            DocumentTypeMgtBean bean = new DocumentTypeMgtBean();
            String query = "SELECT DISTINCT AP.DOCUMENTTYPECODE, " +
                        "  AP.DOCUMENTNAME, " +
                        "  VC.DESCRIPTON, " +
                        "  AP.VERIFICATIONCATEGORY, " +
                        "  AP.POSTPIFIX, " +
                        "  AP.LASTUPDATEDUSER, " +
                        "  AP.LASTUPDATEDTIME, " +
                        "  AP.CREATETIME, " +
                        "  AP.ISMANDATORY, " +
                        "  AP.CARDCATEGORYCODE, " +
                        "  AP.STATUS, " +
                        "  CC.DESCRIPTION AS CARDCATEGORYCODEDES " +
                        "FROM APPLICATIONDOCUMENT AP " +
                        "LEFT JOIN CARDCATEGORY CC " +
                        "ON CC.CATEGORYCODE =AP.CARDCATEGORYCODE " +
                        "LEFT JOIN STATUS ST " +
                        "ON ST.STATUSCODE =AP.STATUS " +
                        "LEFT JOIN VERIFICATIONCATEGORY VC " +
                        "ON VC.VERIFICATIONCATEGORYCODE=AP.VERIFICATIONCATEGORY " +
                        "WHERE  DOCUMENTTYPECODE=?";

            ps = con.prepareStatement(query);
            ps.setString(1, docCode);
            rs = ps.executeQuery();
            while (rs.next()) {

                bean.setDocCode(rs.getString("DOCUMENTTYPECODE"));
                bean.setDocName(rs.getString("DOCUMENTNAME"));
                bean.setVerifyCat(rs.getString("VERIFICATIONCATEGORY"));
                bean.setVerifyCatDes(rs.getString("DESCRIPTON"));
                bean.setPostFix(rs.getString("POSTPIFIX"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setLastUpdateDate(rs.getString("LASTUPDATEDTIME"));
                bean.setLastupdateUser(rs.getString("LASTUPDATEDUSER"));
                bean.setCardCategory(rs.getString("CARDCATEGORYCODE"));
                bean.setIsMandatory(rs.getBoolean("ISMANDATORY"));
                bean.setCreateDate(rs.getString("CREATETIME"));
                bean.setCardCategoryDes(rs.getString("CARDCATEGORYCODEDES"));

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
     * check given document code in already exist
     * @param con
     * @param docCode
     * @return
     * @throws Exception 
     */
    public boolean isAlreadyExist(Connection con, String docCode) throws Exception {
        PreparedStatement ps = null;
        boolean flag = true;
        try {
            String query = "SELECT COUNT(*) AS COUNT FROM APPLICATIONDOCUMENT WHERE DOCUMENTTYPECODE=?";

            ps = con.prepareStatement(query);
            ps.setString(1, docCode);
            String value = "";
            rs = ps.executeQuery();
            while (rs.next()) {
                value = rs.getString("COUNT");
            }

            if (value.equals("1")) {
                flag = true;
            } else {
                flag = false;
            }
            return flag;

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
     * delete document type
     * @param con -Connection
     * @param ID-String document id which want to delete
     * @return int- number of rows that delete
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteCreditScoreField(Connection con, String ID) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM APPLICATIONDOCUMENT AD where AD.DOCUMENTTYPECODE =?";

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
     * update document type
     * @param con
     * @param bean
     * @return  int- number of row that update
     * @throws Exception 
     */
    public int updateDocumentType(Connection con, DocumentTypeMgtBean bean) throws Exception {
        int resultID = -1;
        PreparedStatement ps = null;
        try {
            String query = "UPDATE APPLICATIONDOCUMENT AT SET "
                    + "AT.DOCUMENTNAME=?,AT.VERIFICATIONCATEGORY=?,AT.POSTPIFIX=?,"
                    + "AT.STATUS=?,AT.LASTUPDATEDUSER=?,AT.LASTUPDATEDTIME=? ,AT.CARDCATEGORYCODE=? ,AT.ISMANDATORY=? "
                    + "WHERE AT.DOCUMENTTYPECODE=? ";
            ps = con.prepareStatement(query);

            ps.setString(1, bean.getDocName());
            ps.setString(2, bean.getVerifyCat());
            ps.setString(3, bean.getPostFix());
            ps.setString(4, bean.getStatus());
            ps.setString(5, bean.getLastupdateUser());
            ps.setTimestamp(6, SystemDateTime.getSystemDataAndTime());
            ps.setString(7, bean.getCardCategory());
            ps.setBoolean(8, bean.getIsMandatory());
            ps.setString(9, bean.getDocCode());



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

    /**
     * to add a new bank to the database
     * @param cmsCon
     * @param bean
     * @return
     * @throws Exception 
     */
    public int addNewLetterFormat(Connection cmsCon, LetterTemplateBean bean) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {

            String query = "INSERT INTO LETTERTEMPLATE (TEMPLATECODE,DESCRIPTION,TEMPLATEFORMAT,STATUS,PROCESSCODE,BODY,TITLE,LASTUPDATEDUSER,CREATEDDATE,LASTUPDATEDTIME) VALUES(?,?,?,?,?,?,?,?,SYSDATE,SYSDATE)";

            ps = cmsCon.prepareStatement(query);

            ps.setString(1, bean.getTemplateCode());
            ps.setString(2, bean.getDescription());
            ps.setString(3, bean.getTemplateFormat());
            ps.setString(4, bean.getStatus());
            ps.setString(5, bean.getProcessId());
            ps.setString(6, bean.getBodyString());
            ps.setString(7, bean.getTitleString());
            ps.setString(8, bean.getLastUpdatedUser());

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
     * to delete a bank from database
     * @param cmsCon
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteLetterTemplate(Connection cmsCon, String id) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM LETTERTEMPLATE WHERE TEMPLATECODE=? ";

            ps = cmsCon.prepareStatement(query);
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
