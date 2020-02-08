/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.persistance;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean;
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
public class ApplicationSummaryPersistance {

    ResultSet rs;
    HashMap<String, String> cardDomain = null;
    HashMap<String, String> bnkBranches = null;
    HashMap<String, String> cdProduct = null;
    private HashMap<String, String> productModes = null;
    private HashMap<String, String> cardDomainList = null;
    private HashMap<String, String> appStatusList = null;
    SystemUserBean sysUsrBean = null;
    private HashMap<String, String> priorityLevelList = null;

    public HashMap<String, String> getBranchNames(Connection con) throws SQLException {
        PreparedStatement ps = null;
        bnkBranches = new HashMap<String, String>();

        try {
            ps = con.prepareStatement("SELECT BRANCHCODE,DESCRPTION FROM BANKBRANCH WHERE BANKCODE = (SELECT BANKCODE FROM COMMONPARAMETER)");
            rs = ps.executeQuery();

            while (rs.next()) {
                bnkBranches.put(rs.getString("BRANCHCODE"), rs.getString("DESCRPTION"));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }

        return bnkBranches;
    }

    public HashMap<String, String> getPriorityLevels(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;
        try {
            getAllUserRole = con.prepareStatement("SELECT TC.PRIORITYLEVELCODE,TC.DESCRIPTION "
                    + "FROM PRIORITYLEVEL TC");

            rs = getAllUserRole.executeQuery();
            priorityLevelList = new HashMap<String, String>();

            while (rs.next()) {

                priorityLevelList.put(rs.getString("PRIORITYLEVELCODE"), rs.getString("DESCRIPTION"));


            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();

        }

        return priorityLevelList;
    }

    public HashMap<String, String> getCardDomains(Connection con) throws Exception {

        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;

        try {
            getAllUserRole = con.prepareStatement("SELECT TC.DOMAINID,TC.DESCRIPTION FROM CARDDOMAIN TC");

            rs = getAllUserRole.executeQuery();
            cardDomainList = new HashMap<String, String>();

            while (rs.next()) {

                cardDomainList.put(rs.getString("DOMAINID"), rs.getString("DESCRIPTION"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();

        }

        return cardDomainList;
    }
    
    public HashMap<String, String> getApplicationStatus(Connection con) throws Exception {
        
        PreparedStatement getAllUserRole = null;

        try {
            getAllUserRole = con.prepareStatement("SELECT S.STATUSCODE,S.DESCRIPTION,S.STATUSCATEGORY FROM STATUS S "
                    + "WHERE S.STATUSCATEGORY = 'ASGN'");

            rs = getAllUserRole.executeQuery();
            appStatusList = new HashMap<String, String>();

            while (rs.next()) {

                appStatusList.put(rs.getString("STATUSCODE"), rs.getString("DESCRIPTION"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();

        }

        return appStatusList;
    }
    
    public List<ApplicationSummaryBean> getApplicationSummary(Connection con,ApplicationSummaryBean inputBean) throws SQLException,Exception{
        List<ApplicationSummaryBean> searchList = new ArrayList<ApplicationSummaryBean>();
        PreparedStatement ps = null;
        
        try {
            String  defaultQuery = "SELECT CA.BRANCHCODE,MIN(CA.PRIORITYLEVELCODE),MIN(CA.CREATETIME),MIN(CA.CARDAPPLIACTIONDOMAIN),"
                    + "MIN(CA.ASSIGNSTATUS),MIN(CA.APPLICATIONID),MIN(CAS.APPLICATIONSTATUS),S.DESCRIPTION AS STATUS_DES,"
                    + "PL.DESCRIPTION AS PRIORITY_DES,CD.DESCRIPTION AS DOMAIN_DES,BB.DESCRPTION AS BRANCH_DES,COUNT(*) "
                    + "FROM CARDAPPLICATION CA,CARDAPPLICATIONSTATUS CAS,STATUS S,PRIORITYLEVEL PL,CARDDOMAIN CD,BANKBRANCH BB "
                    + "WHERE CA.APPLICATIONID = CAS.APPLICATIONID AND CAS.APPLICATIONSTATUS = S.STATUSCODE "
                    + "AND CA.PRIORITYLEVELCODE = PL.PRIORITYLEVELCODE "
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
            if (!inputBean.getPriorityLevelCode().contentEquals("") || inputBean.getPriorityLevelCode().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CA.PRIORITYLEVELCODE = '" + inputBean.getPriorityLevelCode() + "'";
            }
            if (!inputBean.getDomainCode().contentEquals("") || inputBean.getDomainCode().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CA.CARDAPPLIACTIONDOMAIN = '" + inputBean.getDomainCode().toUpperCase() + "'";
            }
            if (!inputBean.getStatusCode().contentEquals("") || inputBean.getStatusCode().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CAS.APPLICATIONSTATUS = '" + inputBean.getStatusCode() + "'";
            }
            if (!inputBean.getFromDate().contentEquals("") && !inputBean.getToDate().contentEquals("")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CA.CREATETIME >= to_Date('" + this.stringTodate(inputBean.getFromDate().toString()) + "','yy-mm-dd') AND CA.CREATETIME <= to_Date('" + this.stringTodate(inputBean.getToDate()) + "','yy-mm-dd')";
            } else {

                if (!inputBean.getFromDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "CA.CREATETIME >= to_Date('" + this.stringTodate(inputBean.getFromDate()) + "','yy-mm-dd')";
                }
                if (!inputBean.getToDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "CA.CREATETIME <= to_Date('" + this.stringTodate(inputBean.getToDate()) + "','yy-mm-dd')";
                }
            }

            if (!condition.equals("")) {
                defaultQuery += "  AND " + condition + "GROUP BY BB.DESCRPTION,CD.DESCRIPTION,PL.DESCRIPTION,S.DESCRIPTION,CA.BRANCHCODE"
                        + " ORDER BY BRANCH_DES,DOMAIN_DES,PRIORITY_DES,STATUS_DES ASC";
            } else {
                defaultQuery += "GROUP BY BB.DESCRPTION,CD.DESCRIPTION,PL.DESCRIPTION,S.DESCRIPTION,CA.BRANCHCODE"
                        + " ORDER BY BRANCH_DES,DOMAIN_DES,PRIORITY_DES,STATUS_DES ASC";
            }

            ps = con.prepareStatement(defaultQuery);
            rs = ps.executeQuery();

            searchList = new ArrayList<ApplicationSummaryBean>();
            
            while (rs.next()) {
                ApplicationSummaryBean resultBean = new ApplicationSummaryBean();

                resultBean.setBranchCode(rs.getString("BRANCHCODE"));
                resultBean.setBranchName(rs.getString("BRANCH_DES"));
                resultBean.setPriorityLevelCode(rs.getString("MIN(CA.PRIORITYLEVELCODE)"));
                resultBean.setPriorityLevelDes(rs.getString("PRIORITY_DES"));
                resultBean.setDomainCode(rs.getString("MIN(CA.CARDAPPLIACTIONDOMAIN)"));
                resultBean.setDomainDes(rs.getString("DOMAIN_DES"));
                resultBean.setStatusCode(rs.getString("MIN(CA.ASSIGNSTATUS)"));
                resultBean.setStatusDes(rs.getString("STATUS_DES"));
                resultBean.setTotal(rs.getString("COUNT(*)"));               

                searchList.add(resultBean);
            }
         } catch (SQLException ex) {
            throw ex;
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
}
