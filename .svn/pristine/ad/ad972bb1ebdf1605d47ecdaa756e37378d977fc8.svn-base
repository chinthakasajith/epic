/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.persistance;

import com.epic.cms.reportexp.cardapplication.bean.CardStatusReportBean;
import com.epic.cms.system.util.variable.ApplicationVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author nisansala
 */
public class CardStatusReportPersistance {
    
    ResultSet rs;
    List<String> userList;
    
    HashMap<String,String> status ;
    
    public List<String> getUser(Connection con) throws Exception {

        PreparedStatement ps = null;
        
        try {
            ps = con.prepareStatement("SELECT DISTINCT URA.USERROLECODE,SU.USERNAME "
                    + "FROM USERROLEAPPLICATION URA,SYSTEMUSER SU "
                    + "WHERE SU.USERROLECODE = URA.USERROLECODE "
                    + "AND URA.APPLICATIONCODE IN (?,?,?)"); 
            
            ps.setString(1, ApplicationVarList.CAMM_APPLICATION);
            ps.setString(2, ApplicationVarList.CPMM_APPLICATION);
            ps.setString(3, ApplicationVarList.CALL_CENTER_APPLICATION);
            
            rs = ps.executeQuery();
            userList = new ArrayList<String>();

            while (rs.next()) {

                userList.add(rs.getString("USERNAME"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();

        }

        return userList;
    }
    
    public List<CardStatusReportBean> searchCardStatusReportDetails(Connection con, CardStatusReportBean inputBean) throws Exception {
        List<CardStatusReportBean> searchList = new ArrayList<CardStatusReportBean>();
        PreparedStatement ps = null;

        try {
            String defaultQuery = "SELECT C.CARDNUMBER,C.MAINCARDNO,C.CARDTYPE,C.CARDPRODUCT,C.IDTYPE,C.IDNUMBER,C.PRIORITYLEVEL,C.CARDDOMAIN,"
                    + "C.EMBOSSSTATUS,C.ENCODESTATUS,C.APPLICATIONID,C.FILEID,S.DESCRIPTION AS STATUS_DES,CT.DESCRIPTION AS TYPE_DES,C.CREATEDTIME, "
                    + "CD.DESCRIPTION AS DOMAIN_DES,BB.DESCRPTION AS BRANCH_DES,PL.DESCRIPTION AS PRIORITY_DES,CP.DESCRIPTION AS PRODUCT_DES,"
                    + "CA.BRANCHCODE,AD.DOCUMENTNAME,C.CARDSTATUS,C.NOGENARATEDUSER "
                    + "FROM CARD C,PRIORITYLEVEL PL,CARDDOMAIN CD,BANKBRANCH BB,STATUS S,CARDTYPE CT,CARDPRODUCT CP,"
                    + "APPLICATIONDOCUMENT AD,CARDAPPLICATION CA "
                    + "WHERE C.PRIORITYLEVEL = PL.PRIORITYLEVELCODE "
                    + "AND C.CARDDOMAIN = CD.DOMAINID "
                    + "AND C.CARDTYPE = CT.CARDTYPECODE "
                    + "AND C.CARDPRODUCT = CP.PRODUCTCODE "
                    + "AND C.IDTYPE = AD.DOCUMENTTYPECODE "
                    + "AND C.CARDSTATUS = S.STATUSCODE "
                    + "AND C.APPLICATIONID = CA.APPLICATIONID "
                    + "AND CA.BRANCHCODE = BB.BRANCHCODE "
                    + "AND BB.BANKCODE = (SELECT BANKCODE FROM COMMONPARAMETER)"
                    + "AND AD.CARDCATEGORYCODE=CA.CARDCATEGORY ";

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
                condition += "C.CARDSTATUS = '" + inputBean.getStatus() + "'";
            }
            if (!inputBean.getUser().contentEquals("") || inputBean.getUser().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "C.NOGENARATEDUSER = '" + inputBean.getUser() + "'";
            }
            if (!inputBean.getFromDate().contentEquals("") && !inputBean.getToDate().contentEquals("")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "C.CREATEDTIME >= to_Date('" + this.stringTodate(inputBean.getFromDate().toString()) + "','yy-mm-dd') AND C.CREATEDTIME <= to_Date('" + this.stringTodate(inputBean.getToDate()) + "','yy-mm-dd')";
            } else {

                if (!inputBean.getFromDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "C.CREATEDTIME >= to_Date('" + this.stringTodate(inputBean.getFromDate()) + "','yy-mm-dd')";
                }
                if (!inputBean.getToDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "C.CREATEDTIME <= to_Date('" + this.stringTodate(inputBean.getToDate()) + "','yy-mm-dd')";
                }
            }

            if (!condition.equals("")) {
                defaultQuery += "  AND " + condition;
            } else {
            }

            ps = con.prepareStatement(defaultQuery);
            rs = ps.executeQuery();

            searchList = new ArrayList<CardStatusReportBean>();

            while (rs.next()) {
                CardStatusReportBean resultBean = new CardStatusReportBean();

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
                //resultBean.setStatus(rs.getString("EMBOSSSTATUS"));
                resultBean.setStatusDes(rs.getString("STATUS_DES"));
                resultBean.setUser(rs.getString("NOGENARATEDUSER"));
                resultBean.setEmbossDate(rs.getString("CREATEDTIME"));
                resultBean.setIdTypeDes(rs.getString("DOCUMENTNAME"));

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

    private String stringTodate(String date) throws ParseException  {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date convertedDate = dateFormat.parse(dateString);

        return (dateFormat.format(convertedDate));
    }
    
    public HashMap<String,String> getCardStatus(Connection con) throws Exception {

        PreparedStatement ps = null;
        
        try {
            ps = con.prepareStatement("SELECT S.STATUSCODE,S.DESCRIPTION "
                    + "FROM STATUS S "
                    + "WHERE S.STATUSCATEGORY = 'CARD'"); 
            
                        
            rs = ps.executeQuery();
            status = new HashMap<String, String> ();

            while (rs.next()) {

                status.put(rs.getString("STATUSCODE"),rs.getString("DESCRIPTION"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();

        }

        return status;
    } 
    
}
