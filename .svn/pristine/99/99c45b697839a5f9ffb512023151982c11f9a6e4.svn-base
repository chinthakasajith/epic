/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.persistance;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationDataEntryBean;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationSummaryBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class ApplicationDataEntryPersistance {

    ResultSet rs;
    HashMap<String, String> cardDomain = null;
    HashMap<String, String> bnkBranches = null;
    HashMap<String, String> cdProduct = null;
    private HashMap<String, String> productModes = null;
    private HashMap<String, String> cardDomainList = null;
    private HashMap<String, String> appStatusList = null;
    List<String> userList;
    SystemUserBean sysUsrBean = null;
    

    public List<ApplicationDataEntryBean> getApplicationDataEntry(Connection con, ApplicationDataEntryBean inputBean) throws Exception {
        List<ApplicationDataEntryBean> searchList = new ArrayList<ApplicationDataEntryBean>();
        PreparedStatement ps = null;

        try {
            String defaultQuery = "SELECT CA.BRANCHCODE,CA.ASSIGNUSER,MIN(CA.CARDAPPLIACTIONDOMAIN),"
                    + "MIN(CA.APPLICATIONID),MIN(CAH.CREATEDTIME),"
                    + "CD.DESCRIPTION AS DOMAIN_DES,BB.DESCRPTION AS BRANCH_DES,COUNT(*) "
                    + "FROM CARDAPPLICATION CA,CARDDOMAIN CD,BANKBRANCH BB,CARDAPPLICATIONHISTORY CAH "
                    + "WHERE CA.APPLICATIONID = CAH.APPLICATIONID "
                    + "AND CAH.STATUS = 'DCOM' "
                    + "AND CA.CARDAPPLIACTIONDOMAIN = CD.DOMAINID "
                    + "AND CA.BRANCHCODE = BB.BRANCHCODE "
                    + "AND BB.BANKCODE = (SELECT BANKCODE FROM COMMONPARAMETER) ";

            String condition = "";

            if (!inputBean.getBranchCode().contentEquals("") || inputBean.getBranchCode().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CA.BRANCHCODE = '" + inputBean.getBranchCode() + "'";
            }
            if (!inputBean.getDomainCode().contentEquals("") || inputBean.getDomainCode().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CA.CARDAPPLIACTIONDOMAIN = '" + inputBean.getDomainCode().toUpperCase() + "'";
            }
            if (!inputBean.getUser().contentEquals("") || inputBean.getUser().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CA.ASSIGNUSER = '" + inputBean.getUser() + "'";
            }
            if (!inputBean.getFromDate().contentEquals("") && !inputBean.getToDate().contentEquals("")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CAH.CREATEDTIME >= to_Date('" + this.stringTodate(inputBean.getFromDate().toString()) + "','yy-mm-dd') AND CAH.CREATEDTIME <= to_Date('" + this.stringTodate(inputBean.getToDate()) + "','yy-mm-dd')";
            } else {

                if (!inputBean.getFromDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "CAH.CREATEDTIME >= to_Date('" + this.stringTodate(inputBean.getFromDate()) + "','yy-mm-dd')";
                }
                if (!inputBean.getToDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "CAH.CREATEDTIME <= to_Date('" + this.stringTodate(inputBean.getToDate()) + "','yy-mm-dd')";
                }
            }

            if (!condition.equals("")) {
                defaultQuery += "  AND " + condition + "GROUP BY BB.DESCRPTION,CA.BRANCHCODE,CD.DESCRIPTION,ASSIGNUSER"
                        + " ORDER BY BRANCH_DES,DOMAIN_DES,ASSIGNUSER ASC";
            } else {
                defaultQuery += "GROUP BY BB.DESCRPTION,CA.BRANCHCODE,CD.DESCRIPTION,ASSIGNUSER"
                        + " ORDER BY BRANCH_DES,DOMAIN_DES,ASSIGNUSER ASC";
            }

            ps = con.prepareStatement(defaultQuery);
            rs = ps.executeQuery();

            searchList = new ArrayList<ApplicationDataEntryBean>();

            while (rs.next()) {
                ApplicationDataEntryBean resultBean = new ApplicationDataEntryBean();

                resultBean.setBranchCode(rs.getString("BRANCHCODE"));
                resultBean.setBranchName(rs.getString("BRANCH_DES"));
                resultBean.setDomainCode(rs.getString("MIN(CA.CARDAPPLIACTIONDOMAIN)"));
                resultBean.setDomainDes(rs.getString("DOMAIN_DES"));
                resultBean.setUser(rs.getString("ASSIGNUSER"));                
                resultBean.setTotal(rs.getString("COUNT(*)"));
                resultBean.setStatusCode(rs.getString("MIN(CA.APPLICATIONID)"));

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

    private String stringTodate(String date) throws ParseException {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date convertedDate = dateFormat.parse(dateString);

        return (dateFormat.format(convertedDate));
    }
    
    public List<String> getDataCapturingUser(Connection con) throws Exception {
        
        PreparedStatement getAllUserRole = null;
        

        try {
            getAllUserRole = con.prepareStatement("SELECT UAS.USERROLECODE,SU.USERNAME "
                    + "FROM USERAPPLICATIONSECTION UAS,SYSTEMUSER SU "
                    + "WHERE UAS.SECTIONCODE = 'CAPTDATA'"
                    + "AND SU.USERROLECODE = UAS.USERROLECODE");

            rs = getAllUserRole.executeQuery();
            userList = new ArrayList<String>() {};

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
}
