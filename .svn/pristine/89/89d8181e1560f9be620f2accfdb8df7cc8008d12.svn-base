/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.persistance;

import com.epic.cms.reportexp.cardapplication.bean.EmbossEncodeCardBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author nisansala
 */
public class EmbossAndEncodeCardReportPersistance {

    //initializing variables
    private HashMap<String, String> cardTypeList = null;
    private HashMap<String, String> cardProductList = null;
    private ResultSet rs = null;
    List<String> userList;
    private HashMap<String, String> cdProduct = null;

    public HashMap<String, String> getCardTypes(Connection con) throws Exception {

        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement("SELECT TC.CARDTYPECODE,TC.DESCRIPTION FROM CARDTYPE TC");

            rs = ps.executeQuery();
            cardTypeList = new HashMap<String, String>();

            while (rs.next()) {

                cardTypeList.put(rs.getString("CARDTYPECODE"), rs.getString("DESCRIPTION"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();

        }

        return cardTypeList;
    }

    public HashMap<String, String> getCardProducts(Connection con) throws Exception {

        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement("SELECT CP.PRODUCTCODE,CP.DESCRIPTION FROM CARDPRODUCT CP");

            rs = ps.executeQuery();
            cardProductList = new HashMap<String, String>();

            while (rs.next()) {

                cardProductList.put(rs.getString("PRODUCTCODE"), rs.getString("DESCRIPTION"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();

        }

        return cardProductList;
    }

    public List<String> getEmbossUser(Connection con) throws Exception {

        PreparedStatement getAllUserRole = null;


        try {
            getAllUserRole = con.prepareStatement("SELECT DISTINCT UPT.USERROLECODE,SU.USERNAME "
                    + "FROM USERPAGETASK UPT,SYSTEMUSER SU "
                    + "WHERE UPT.TASKCODE = 'EMFG'"
                    + "AND SU.USERROLECODE = UPT.USERROLECODE");

            rs = getAllUserRole.executeQuery();
            userList = new ArrayList<String>() {
            };

            while (rs.next()) {

                userList.add(rs.getString("USERNAME"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();

        }

        return userList;
    }

    public List<EmbossEncodeCardBean> searchEmbossEncodeCardReportDetails(Connection con, EmbossEncodeCardBean inputBean) throws Exception {
        List<EmbossEncodeCardBean> searchList = new ArrayList<EmbossEncodeCardBean>();
        PreparedStatement ps = null;

        try {
            String defaultQuery = "SELECT C.CARDNUMBER,C.MAINCARDNO,C.CARDTYPE,C.CARDPRODUCT,C.IDTYPE,C.IDNUMBER,C.PRIORITYLEVEL,C.CARDDOMAIN,"
                    + "C.EMBOSSSTATUS,C.ENCODESTATUS,C.APPLICATIONID,C.FILEID,DF.FILENAME,S.DESCRIPTION AS STATUS_DES,CT.DESCRIPTION AS TYPE_DES,"
                    + "CD.DESCRIPTION AS DOMAIN_DES,BB.DESCRPTION AS BRANCH_DES,PL.DESCRIPTION AS PRIORITY_DES,CP.DESCRIPTION AS PRODUCT_DES,"
                    + "AD.DOCUMENTNAME,DF.CREATEDTIME,DF.GENERATEDUSER,CA.BRANCHCODE,C.NAMEONCARD,C.EXPIERYDATE "
                    + "FROM CARD C,DOWNLOADFILE DF,PRIORITYLEVEL PL,CARDDOMAIN CD,BANKBRANCH BB,STATUS S,CARDTYPE CT,CARDPRODUCT CP,"
                    + "APPLICATIONDOCUMENT AD,CARDAPPLICATION CA "
                    + "WHERE C.FILEID = DF.FILEID "
                    + "AND DF.FIETYPE = 'EMBOSS' "
                    + "AND C.PRIORITYLEVEL = PL.PRIORITYLEVELCODE "
                    + "AND C.CARDDOMAIN = CD.DOMAINID "
                    + "AND C.CARDTYPE = CT.CARDTYPECODE "
                    + "AND C.CARDPRODUCT = CP.PRODUCTCODE "
                    + "AND C.IDTYPE = AD.DOCUMENTTYPECODE "
                    + "AND C.EMBOSSSTATUS = S.STATUSCODE "
                    + "AND C.APPLICATIONID = CA.APPLICATIONID "
                    + "AND CA.BRANCHCODE = BB.BRANCHCODE "
                    + "AND BB.BANKCODE = (SELECT BANKCODE FROM COMMONPARAMETER) "
                    + "AND AD.CARDCATEGORYCODE=CA.CARDCATEGORY";

            String condition = "";

            if (!inputBean.getNic().contentEquals("") || inputBean.getNic().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "C.IDNUMBER LIKE '%" + inputBean.getNic() + "%' ";
                condition += "AND C.IDTYPE = 'NIC'";
            }
            if (!inputBean.getPassport().contentEquals("") || inputBean.getPassport().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "C.IDNUMBER LIKE '%" + inputBean.getPassport() + "%' ";
                condition += "AND C.IDTYPE = 'PAS'";
            }
//            if (!inputBean.getLicence().contentEquals("") || inputBean.getLicence().length() > 0) {
//                if (!condition.equals("")) {
//                    condition += " AND ";
//                }
//                condition += "C.IDNUMBER LIKE '%" + inputBean.getLicence() + "%' ";
//                condition += "AND C.IDTYPE = 'DRL'";
//
//            }
            if (!inputBean.getApplicationId().contentEquals("") || inputBean.getApplicationId().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "C.APPLICATIONID = '" + inputBean.getApplicationId() + "'";
            }
            if (!inputBean.getBranchCode().contentEquals("") || inputBean.getBranchCode().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CA.BRANCHCODE = '" + inputBean.getBranchCode() + "'";
            }
            if (!inputBean.getCardNo().contentEquals("") || inputBean.getCardNo().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "C.CARDNUMBER LIKE '%" + inputBean.getCardNo() + "%'";
            }
            if (!inputBean.getPriorityLevelCode().contentEquals("") || inputBean.getPriorityLevelCode().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "C.PRIORITYLEVEL = '" + inputBean.getPriorityLevelCode() + "'";
            }
            if (!inputBean.getDomainCode().contentEquals("") || inputBean.getDomainCode().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "C.CARDDOMAIN = '" + inputBean.getDomainCode() + "'";
            }
            if (!inputBean.getTypeCode().contentEquals("") || inputBean.getTypeCode().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "C.CARDTYPE = '" + inputBean.getTypeCode() + "'";
            }
            if (!inputBean.getProductCode().contentEquals("") || inputBean.getProductCode().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "C.CARDPRODUCT = '" + inputBean.getProductCode() + "'";
            }
            if (!inputBean.getStatus().contentEquals("") || inputBean.getStatus().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "C.EMBOSSSTATUS = '" + inputBean.getStatus() + "'";
            }
            if (!inputBean.getUser().contentEquals("") || inputBean.getUser().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "DF.GENERATEDUSER = '" + inputBean.getUser() + "'";
            }
            if (!inputBean.getFromDate().contentEquals("") && !inputBean.getToDate().contentEquals("")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "DF.CREATEDTIME >= to_Date('" + this.stringTodate(inputBean.getFromDate().toString()) + "','yy-mm-dd') AND DF.CREATEDTIME <= to_Date('" + this.stringTodate(inputBean.getToDate()) + "','yy-mm-dd')";
            } else {

                if (!inputBean.getFromDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "DF.CREATEDTIME >= to_Date('" + this.stringTodate(inputBean.getFromDate()) + "','yy-mm-dd')";
                }
                if (!inputBean.getToDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "DF.CREATEDTIME <= to_Date('" + this.stringTodate(inputBean.getToDate()) + "','yy-mm-dd')";
                }
            }

            if (!condition.equals("")) {
                defaultQuery += "  AND " + condition;
            } else {
            }

            ps = con.prepareStatement(defaultQuery);
            rs = ps.executeQuery();

            searchList = new ArrayList<EmbossEncodeCardBean>();

            while (rs.next()) {
                EmbossEncodeCardBean resultBean = new EmbossEncodeCardBean();

                resultBean.setApplicationId(rs.getString("APPLICATIONID"));
                resultBean.setBranchCode(rs.getString("BRANCHCODE"));
                resultBean.setBranchName(rs.getString("BRANCH_DES"));
                resultBean.setCardNo(rs.getString("CARDNUMBER"));
                resultBean.setDomainCode(rs.getString("CARDDOMAIN"));
                resultBean.setDomainDes(rs.getString("DOMAIN_DES"));
                resultBean.setIdNo(rs.getString("IDNUMBER"));
                resultBean.setIdType(rs.getString("IDTYPE"));
                resultBean.setPriorityLevelCode(rs.getString("PRIORITYLEVEL"));
                resultBean.setPriorityLevelDes(rs.getString("PRIORITY_DES"));
                resultBean.setProductCode(rs.getString("CARDPRODUCT"));
                resultBean.setProductDes(rs.getString("PRODUCT_DES"));
                resultBean.setTypeCode(rs.getString("CARDTYPE"));
                resultBean.setTypeDes(rs.getString("TYPE_DES"));
                resultBean.setStatus(rs.getString("EMBOSSSTATUS"));
                resultBean.setStatusDes(rs.getString("STATUS_DES"));
                resultBean.setUser(rs.getString("GENERATEDUSER"));
                resultBean.setIdTypeDes(rs.getString("DOCUMENTNAME"));
                resultBean.setEmbossDate(rs.getString("CREATEDTIME"));
                resultBean.setFileName(rs.getString("FILENAME"));
                resultBean.setExpiryDate(rs.getString("EXPIERYDATE"));
                resultBean.setNameOnCard(rs.getString("NAMEONCARD"));

                searchList.add(resultBean);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();

        }

        return searchList;
    }

    private String stringTodate(String date) throws java.text.ParseException  {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date convertedDate = dateFormat.parse(dateString);

        return (dateFormat.format(convertedDate));
    }
    
    public HashMap<String, String> getCardProducts(Connection con, String cdType, String cdDomain) throws Exception {


        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement("SELECT PRODUCTCODE,DESCRIPTION FROM CARDPRODUCT WHERE CARDDOMAIN=? AND CARDTYPE=?");
            ps.setString(1, cdDomain);
            ps.setString(2, cdType);

            rs = ps.executeQuery();
            cdProduct = new HashMap<String, String>();

            while (rs.next()) {

                cdProduct.put(rs.getString("PRODUCTCODE"), rs.getString("DESCRIPTION"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }

        return cdProduct;
    }
}
