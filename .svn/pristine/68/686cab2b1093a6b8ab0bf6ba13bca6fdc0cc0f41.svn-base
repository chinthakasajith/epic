/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.assigndata.persistance;

import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationAssignBean;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationHistoryBean;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.SearchAssignAppBean;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.UserAssignApplicationBean;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author chanuka
 */
public class ApplicationAssignPersistance {

    private List<String> userList = null;
    private HashMap<String, String> priorityLevelList = null;
    private HashMap<String, String> applicationDomainList = null;
    private HashMap<String, String> branchesDeatilsList = null;
    private List<UserAssignApplicationBean> userAssignApps = null;
    private List<ApplicationAssignBean> searchAssignAppList = null;
    private List<ApplicationAssignBean> previousApplicationList = null;
    private HashMap<String, String> identificationList = null;
    private HashMap<String, String> dataCaptureUserList=null;
    private String cardCategory=null;

    // insert queries......................................................................................
    public boolean insertApplicationHistory(Connection con, ApplicationHistoryBean historyBean) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;

        if (historyBean != null) {
            try {
                insertStat = con.prepareStatement("INSERT INTO CARDAPPLICATIONHISTORY(APPLICATIONID,APPLICATIONLEVEL,STATUS,REMARKS,LASTUPDATEDUSER) "
                        + "VALUES(?,?,?,?,?) ");

                insertStat.setString(1, historyBean.getApplicationId());
                insertStat.setString(2, historyBean.getApplicationLevel());
                insertStat.setString(3, historyBean.getStatus());

                insertStat.setString(4, historyBean.getRemarks());

                insertStat.setString(5, historyBean.getLastUpdatedUser());

                insertStat.executeUpdate();
                success = true;

            } catch (Exception ex) {
                throw ex;
            } finally {
                insertStat.close();
            }
        } else {
            success = true;
        }

        return success;
    }

    public boolean insertAssignApplication(Connection con, ApplicationAssignBean assignBean) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO CARDAPPLICATION(APPLICATIONID,CARDCATEGORY,IDENTIFICATIONTYPE, "
                    + "IDENTIFICATIONNO,PRIORITYLEVELCODE,REFERANCIALEMPNO,BRANCHCODE,ASSIGNUSER,ASSIGNSTATUS,"
                    + "LASTUPDATEDUSER,CARDAPPLIACTIONDOMAIN,PASSPORTEXPIRYDATE) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?) ");


            insertStat.setString(1, assignBean.getApplicationId());
            insertStat.setString(2, assignBean.getCardCategory());
            insertStat.setString(3, assignBean.getIdentityType());

            insertStat.setString(4, assignBean.getIdentityNo());

            insertStat.setString(5, assignBean.getPriorityLevel());
            insertStat.setString(6, assignBean.getReferralEmpNo());
            insertStat.setString(7, assignBean.getReferralBranchId());
            insertStat.setString(8, assignBean.getAssignUser());
            insertStat.setString(9, StatusVarList.INITIAL_STATUS);
            insertStat.setString(10, assignBean.getLastUpdatedUser());
            insertStat.setString(11, assignBean.getApplicationDomain());
            insertStat.setString(12, assignBean.getExpirydate());


            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public boolean insertAssignStatus(Connection con, ApplicationAssignBean assignBean) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO CARDAPPLICATIONSTATUS(APPLICATIONID,TAB01STATUS, "
                    + "TAB02STATUS,TAB03STATUS,TAB04STATUS,APPLICATIONSTATUS,TAB05STATUS,TAB06STATUS) "
                    + "VALUES(?,?,?,?,?,?,?,?) ");

            insertStat.setString(1, assignBean.getApplicationId());
            insertStat.setString(2, "0");

            insertStat.setString(3, "0");

            insertStat.setString(4, "0");
            insertStat.setString(5, "0");
            insertStat.setString(6, StatusVarList.INITIAL_STATUS);
            insertStat.setString(7, "0");
            insertStat.setString(8, "0");


            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    // update queries......................................................................................
    public boolean updateAssignApplication(Connection con, ApplicationAssignBean assignBean) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE CARDAPPLICATION SET CARDCATEGORY=?,IDENTIFICATIONTYPE=?, "
                    + " IDENTIFICATIONNO=?,PRIORITYLEVELCODE=?,REFERANCIALEMPNO=?,BRANCHCODE=?, "
                    + " ASSIGNUSER=?,LASTUPDATEDUSER=?,LASTUPDATEDTIME=? "
                    + " WHERE APPLICATIONID=? ");

            updateStat.setString(1, assignBean.getCardCategory());
            updateStat.setString(2, assignBean.getIdentityType());
            updateStat.setString(3, assignBean.getIdentityNo());
            updateStat.setString(4, assignBean.getPriorityLevel());
            updateStat.setString(5, assignBean.getReferralEmpNo());
            updateStat.setString(6, assignBean.getReferralBranchId());
            updateStat.setString(7, assignBean.getAssignUser());
            updateStat.setString(8, assignBean.getLastUpdatedUser());
            updateStat.setTimestamp(9, new Timestamp(assignBean.getLastUpdatedTime().getTime()));
            updateStat.setString(10, assignBean.getApplicationId());


            updateStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    public boolean updateApplicationStatus(Connection con, ApplicationAssignBean assignBean) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE CARDAPPLICATIONSTATUS SET APPLICATIONSTATUS=? WHERE APPLICATIONID=? ");

            updateStat.setString(1, assignBean.getAssignStatus());
            updateStat.setString(2, assignBean.getApplicationId());

            updateStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    public List<String> getAllUsers(Connection con, String pageCode) throws Exception {

        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;
        try {
            getAllUserRole = con.prepareStatement("SELECT TC.USERNAME FROM SYSTEMUSER TC,STATUS ST WHERE TC.STATUS = ST.STATUSCODE AND"
                    + " TC.status=? AND tc.userrolecode IN "
                    + "(SELECT USP.userrolecode FROM USERSECTIONPAGE USP WHERE USP.PAGECODE = ?)");

            getAllUserRole.setString(1, StatusVarList.ACTIVE_STATUS);
            getAllUserRole.setString(2, pageCode);
            rs = getAllUserRole.executeQuery();
            userList = new ArrayList<String>();

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
    //get Assign user list according to application type
    public HashMap<String,String> getAssignUserList(Connection con,String pageCode,String cat) throws Exception{
        ResultSet rs=null;
        PreparedStatement getAssignUserList=null;
        try {
            getAssignUserList=con.prepareStatement(" select username from systemuser where systemuser.STATUS= ? AND (systemuser.USERROLECODE IN (SELECT USERROLECODE FROM USERPAGETASK WHERE USERPAGETASK.PAGECODE= ? AND USERPAGETASK.TASKCODE=?))");
            getAssignUserList.setString(1, StatusVarList.ACTIVE_STATUS);
            getAssignUserList.setString(2, pageCode);
            if(cat.equals("M")){
                getAssignUserList.setString(3, TaskVarList.MAIN_APP_DATA_CAPTURE);
                
            }else if(cat.equals("S")){
                getAssignUserList.setString(3, TaskVarList.SUP_APP_DATA_CAPTURE);
            }else if(cat.equals("F")){
                getAssignUserList.setString(3, TaskVarList.FD_APP_DATA_CAPTURE);
            }else if(cat.equals("C")){
                getAssignUserList.setString(3, TaskVarList.COP_APP_DATA_CAPTURE);
            }else if(cat.equals("E")){
                getAssignUserList.setString(3, TaskVarList.EST_APP_DATA_CAPTURE);
            }
            rs=getAssignUserList.executeQuery();
            dataCaptureUserList=new HashMap<String,String>();
            while(rs.next()){
                dataCaptureUserList.put(rs.getString("username"), rs.getString("username"));
            }
        } catch (Exception ex) {
            throw ex;
        }finally{
            rs.close();
            getAssignUserList.close();
        }
        return dataCaptureUserList;
    }
    
    //get card category to application id
    public String getCardAppCategoryByApplicationId(Connection con,String id) throws Exception{
        ResultSet rs=null;
        PreparedStatement getCardCategory=null;
        try {
            getCardCategory=con.prepareStatement("SELECT CARDCATEGORY FROM CARDAPPLICATION WHERE APPLICATIONID=?");
            getCardCategory.setString(1, id);
            rs=getCardCategory.executeQuery();
            while (rs.next()) {                
                cardCategory=rs.getString("CARDCATEGORY");
            }
        } catch (Exception ex) {
            throw ex;
        }finally{
            rs.close();
            getCardCategory.close();
        }
        return cardCategory;
    }

    // select queries......................................................................................
    public HashMap<String, String> getAllIdentificationType(Connection con) throws Exception {

        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;
        try {
            getAllUserRole = con.prepareStatement("SELECT TC.DOCUMENTTYPECODE,TC.DOCUMENTNAME "
                    + " FROM APPLICATIONDOCUMENT TC, STATUS ST WHERE TC.STATUS = ST.STATUSCODE AND TC.VERIFICATIONCATEGORY= 'ID'");

            rs = getAllUserRole.executeQuery();
            identificationList = new HashMap<String, String>();

            while (rs.next()) {

                identificationList.put(rs.getString("DOCUMENTTYPECODE"), rs.getString("DOCUMENTNAME"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();

        }

        return identificationList;
    }
    
    public HashMap<String, String> getIdentificationTypeByCardCategory(Connection con,String cardCategory, String varifyCategory) throws Exception {

        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;
        try {
            if (varifyCategory == null || varifyCategory.trim().isEmpty()) {
                getAllUserRole = con.prepareStatement("SELECT TC.DOCUMENTTYPECODE,TC.DOCUMENTNAME "
                        + " FROM APPLICATIONDOCUMENT TC, STATUS ST WHERE TC.STATUS = ST.STATUSCODE AND CARDCATEGORYCODE=?");
                getAllUserRole.setString(1, cardCategory);

            } else {
                getAllUserRole = con.prepareStatement("SELECT TC.DOCUMENTTYPECODE,TC.DOCUMENTNAME "
                        + " FROM APPLICATIONDOCUMENT TC, STATUS ST WHERE TC.STATUS = ST.STATUSCODE AND TC.VERIFICATIONCATEGORY=?  AND CARDCATEGORYCODE=?");
                getAllUserRole.setString(1, varifyCategory);
                getAllUserRole.setString(2, cardCategory);

            }
            rs = getAllUserRole.executeQuery();
            identificationList = new HashMap<String, String>();

            while (rs.next()) {

                identificationList.put(rs.getString("DOCUMENTTYPECODE"), rs.getString("DOCUMENTNAME"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();

        }

        return identificationList;
    }
    
    public HashMap<String, String> getAllPriorityLevels(Connection con) throws Exception {
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

    public HashMap<String, String> getAllApplicationDomain(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;
        try {
            getAllUserRole = con.prepareStatement("SELECT AD.DOMAINID,AD.DESCRIPTION FROM CARDDOMAIN AD");

            rs = getAllUserRole.executeQuery();
            applicationDomainList = new HashMap<String, String>();

            while (rs.next()) {

                applicationDomainList.put(rs.getString("DOMAINID"), rs.getString("DESCRIPTION"));


            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();

        }

        return applicationDomainList;
    }

    public String getApplicationDomainPrefix(Connection con, String domainId) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;
        String prefix = null;
        try {
            getAllUserRole = con.prepareStatement("SELECT AD.DOMAINPRIFIX FROM CARDDOMAIN AD WHERE AD.DOMAINID=?");

            getAllUserRole.setString(1, domainId);
            rs = getAllUserRole.executeQuery();


            while (rs.next()) {

                prefix = rs.getString("DOMAINPRIFIX");


            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();

        }

        return prefix;
    }

    public HashMap<String, String> getAllBranchesDetails(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;
        try {
            getAllUserRole = con.prepareStatement("SELECT TC.BRANCHCODE,TC.DESCRPTION FROM BANKBRANCH TC where tc.bankcode = (select BANKCODE from commonparameter)");

            rs = getAllUserRole.executeQuery();
            branchesDeatilsList = new HashMap<String, String>();

            while (rs.next()) {

                branchesDeatilsList.put(rs.getString("BRANCHCODE"), rs.getString("DESCRPTION"));


            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();

        }

        return branchesDeatilsList;
    }

    public String getMaxFromCardApplication(Connection con, String domain) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;
        String maxId = null;
        try {
            getAllUserRole = con.prepareStatement("select coalesce(Max(cc.APPLICATIONID),'0') as MAXAPPLICATIONID from CARDAPPLICATION cc where cc.CARDAPPLIACTIONDOMAIN=?");

            getAllUserRole.setString(1, domain);
            rs = getAllUserRole.executeQuery();


            while (rs.next()) {

                maxId = rs.getString("MAXAPPLICATIONID");

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();

        }

        return maxId;
    }

    public List<UserAssignApplicationBean> getAllUserAssignAppsDetails(Connection con, String cardDomain) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;
        try {
            getAllUserRole = con.prepareStatement("SELECT DISTINCT card.assignuser,(SELECT COUNT(*) FROM CARDAPPLICATION ca"
                    + " WHERE ca.assignuser = card.assignuser AND ca.CARDAPPLIACTIONDOMAIN = '" + cardDomain + "' AND ca.ASSIGNSTATUS  !='" + StatusVarList.APP_REJECT + "' ) AS assignedapps,"
                    + " (SELECT COUNT(*) FROM CARDAPPLICATION ca , CARDAPPLICATIONSTATUS cs  "
                    + "WHERE ca.assignuser = card.assignuser AND ca.applicationid  = cs.applicationid AND ca.CARDAPPLIACTIONDOMAIN = '" + cardDomain + "' "
                    + "AND (cs.applicationstatus ='" + StatusVarList.APP_PROCESS + "' OR cs.applicationstatus ='" + StatusVarList.APP_INITIATE + "') )AS pending "
                    + " FROM CARDAPPLICATION card");

            rs = getAllUserRole.executeQuery();
            userAssignApps = new ArrayList<UserAssignApplicationBean>();

            while (rs.next()) {

                UserAssignApplicationBean temp = new UserAssignApplicationBean();

                temp.setUserName(rs.getString("assignuser"));
                temp.setAssignedApps(rs.getString("assignedapps"));
                temp.setPendingApps(rs.getString("pending"));

                userAssignApps.add(temp);

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();

        }

        return userAssignApps;
    }

    public List<ApplicationAssignBean> generalSearch(Connection con, SearchAssignAppBean searchBean) throws Exception {



        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query



        try {



            strSqlBasic = "SELECT a.CARDAPPLIACTIONDOMAIN,adoc.DOCUMENTNAME,ad.DESCRIPTION as CARDAPPLIACTIONDOMAINDES, a.APPLICATIONID,a.CARDCATEGORY,CC.DESCRIPTION AS CARDCATEGORYDES,a.IDENTIFICATIONTYPE,a.IDENTIFICATIONNO,a.PRIORITYLEVELCODE , pl.DESCRIPTION AS PRIORITYNAME, a.REFERANCIALEMPNO,a.BRANCHCODE , bk.DESCRPTION AS BRANCHNAME , "
                    + "a.ASSIGNUSER, a.ASSIGNSTATUS , st.DESCRIPTION ,a.LASTUPDATEDUSER,a.LASTUPDATEDTIME,a.CREATETIME "
                    + "FROM CARDAPPLICATION a left OUTER JOIN BANKBRANCH bk on a.BRANCHCODE = bk.BRANCHCODE,CARDAPPLICATIONSTATUS CS,STATUS st,PRIORITYLEVEL pl ,CARDCATEGORY CC,CARDDOMAIN AD ,APPLICATIONDOCUMENT adoc";

            String condition = "";

            if (!searchBean.getApplicationDomain().contentEquals("") || searchBean.getApplicationDomain().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.CARDAPPLIACTIONDOMAIN = '" + searchBean.getApplicationDomain() + "'";
            }

            if (!searchBean.getApplicationId().contentEquals("") || searchBean.getApplicationId().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.APPLICATIONID = '" + searchBean.getApplicationId() + "'";
            }

            if (!searchBean.getPriorityLevel().contentEquals("") || searchBean.getPriorityLevel().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.PRIORITYLEVELCODE = '" + searchBean.getPriorityLevel() + "'";
            }


            if (!searchBean.getAssignUser().contentEquals("") || searchBean.getAssignUser().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.ASSIGNUSER = '" + searchBean.getAssignUser() + "'";
            }



            if (!searchBean.getFromDate().contentEquals("") && !searchBean.getToDate().contentEquals("")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.CREATETIME >= to_Date('" + this.stringTodate(searchBean.getFromDate()) + "','yy-mm-dd') AND a.CREATETIME <= to_Date('" + this.stringTodate(searchBean.getToDate()) + "','yy-mm-dd')";
            } else {

                if (!searchBean.getFromDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "a.CREATETIME >= to_Date('" + this.stringTodate(searchBean.getFromDate()) + "','yy-mm-dd')";
                }
                if (!searchBean.getToDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "a.CREATETIME <= to_Date('" + this.stringTodate(searchBean.getToDate()) + "','yy-mm-dd')";
                }
            }




            if (!condition.equals("")) {
                strSqlBasic += " WHERE a.CARDAPPLIACTIONDOMAIN = AD.DOMAINID AND a.IDENTIFICATIONTYPE=adoc.DOCUMENTTYPECODE AND a.CARDCATEGORY=adoc.CARDCATEGORYCODE AND  CS.APPLICATIONID = a.APPLICATIONID AND a.ASSIGNSTATUS = st.STATUSCODE AND a.PRIORITYLEVELCODE = pl.PRIORITYLEVELCODE  AND (bk.BANKCODE in (select BANKCODE from COMMONPARAMETER) OR  a.branchcode is null) AND a.CARDCATEGORY = CC.CATEGORYCODE AND CS.APPLICATIONSTATUS IN ('" + StatusVarList.APP_INITIATE + "','" + StatusVarList.APP_PROCESS + "') AND " + condition + " ORDER BY a.APPLICATIONID";
            } else {
                strSqlBasic += " WHERE a.CARDAPPLIACTIONDOMAIN = AD.DOMAINID AND a.IDENTIFICATIONTYPE=adoc.DOCUMENTTYPECODE AND a.CARDCATEGORY=adoc.CARDCATEGORYCODE AND CS.APPLICATIONID = a.APPLICATIONID AND a.ASSIGNSTATUS = st.STATUSCODE AND a.PRIORITYLEVELCODE = pl.PRIORITYLEVELCODE  AND (bk.BANKCODE in (select BANKCODE from COMMONPARAMETER) OR  a.branchcode is null) AND a.CARDCATEGORY = CC.CATEGORYCODE AND CS.APPLICATIONSTATUS IN ('" + StatusVarList.APP_INITIATE + "','" + StatusVarList.APP_PROCESS + "') ORDER BY a.APPLICATIONID";
            }


            stmt = con.prepareStatement(strSqlBasic);
            rs = stmt.executeQuery();

            searchAssignAppList = new ArrayList<ApplicationAssignBean>();

            while (rs.next()) {


                ApplicationAssignBean resultAssign = new ApplicationAssignBean();

                resultAssign.setApplicationDomain(rs.getString("CARDAPPLIACTIONDOMAIN"));
                resultAssign.setApplicationDomainDes(rs.getString("CARDAPPLIACTIONDOMAINDES"));
                resultAssign.setApplicationId(rs.getString("APPLICATIONID"));
                resultAssign.setCardCategory(rs.getString("CARDCATEGORY"));
                resultAssign.setCardCategoryDes(rs.getString("CARDCATEGORYDES"));
                resultAssign.setAssignUser(rs.getString("ASSIGNUSER"));
                resultAssign.setCreatedTime(rs.getTimestamp("CREATETIME"));
                resultAssign.setIdentityNo(rs.getString("IDENTIFICATIONNO"));
                resultAssign.setIdentityType(rs.getString("IDENTIFICATIONTYPE"));
                resultAssign.setLastUpdatedTime(rs.getTimestamp("LASTUPDATEDTIME"));
                resultAssign.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                resultAssign.setPriorityLevel(rs.getString("PRIORITYLEVELCODE"));
                resultAssign.setReferralBranchId(rs.getString("BRANCHCODE"));
                resultAssign.setPriorityDescription(rs.getString("PRIORITYNAME"));
                resultAssign.setReferralBranchName(rs.getString("BRANCHNAME"));
                resultAssign.setReferralEmpNo(rs.getString("REFERANCIALEMPNO"));
                resultAssign.setAssignStatus(rs.getString("ASSIGNSTATUS"));
                resultAssign.setAssignStatusDescription(rs.getString("DESCRIPTION"));
                resultAssign.setIdentityTypeName(rs.getString("DOCUMENTNAME"));
   
                searchAssignAppList.add(resultAssign);

            }

        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }

        return searchAssignAppList;
    }

    public List<ApplicationAssignBean> getPreviousApplication(Connection con, String identificationNo) throws Exception {


        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query

        try {

            strSqlBasic = "SELECT a.APPLICATIONID,a.CARDCATEGORY,CC.DESCRIPTION AS CARDCATEGORYDES,a.IDENTIFICATIONTYPE,a.IDENTIFICATIONNO,a.PRIORITYLEVELCODE , pl.DESCRIPTION AS PRIORITYNAME, a.REFERANCIALEMPNO,a.BRANCHCODE , bk.DESCRPTION AS BRANCHNAME , "
                    + "a.ASSIGNUSER, a.ASSIGNSTATUS , st.DESCRIPTION ,a.LASTUPDATEDUSER,a.LASTUPDATEDTIME,a.CREATETIME,a.PASSPORTEXPIRYDATE "
                    + "FROM CARDAPPLICATION a left OUTER JOIN BANKBRANCH bk on a.BRANCHCODE = bk.BRANCHCODE,STATUS st,PRIORITYLEVEL pl ,CARDCATEGORY CC "
                    + "WHERE a.ASSIGNSTATUS = st.STATUSCODE AND a.PRIORITYLEVELCODE = pl.PRIORITYLEVELCODE AND "
                    + "(bk.BANKCODE in (select BANKCODE from COMMONPARAMETER) OR  a.branchcode is null) AND a.CARDCATEGORY = CC.CATEGORYCODE AND a.IDENTIFICATIONNO=? ORDER BY a.APPLICATIONID";


            stmt = con.prepareStatement(strSqlBasic);
            stmt.setString(1, identificationNo);
            rs = stmt.executeQuery();

            previousApplicationList = new ArrayList<ApplicationAssignBean>();

            while (rs.next()) {


                ApplicationAssignBean resultAssign = new ApplicationAssignBean();

                resultAssign.setApplicationId(rs.getString("APPLICATIONID"));
                resultAssign.setCardCategory(rs.getString("CARDCATEGORY"));
                resultAssign.setCardCategoryDes(rs.getString("CARDCATEGORYDES"));
                resultAssign.setAssignUser(rs.getString("ASSIGNUSER"));
                resultAssign.setCreatedTime(rs.getTimestamp("CREATETIME"));
                resultAssign.setIdentityNo(rs.getString("IDENTIFICATIONNO"));
                resultAssign.setIdentityType(rs.getString("IDENTIFICATIONTYPE"));
                resultAssign.setLastUpdatedTime(rs.getTimestamp("LASTUPDATEDTIME"));
                resultAssign.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                resultAssign.setPriorityLevel(rs.getString("PRIORITYLEVELCODE"));
                resultAssign.setReferralBranchId(rs.getString("BRANCHCODE"));
                resultAssign.setPriorityDescription(rs.getString("PRIORITYNAME"));
                resultAssign.setReferralBranchName(rs.getString("BRANCHNAME"));
                resultAssign.setReferralEmpNo(rs.getString("REFERANCIALEMPNO"));
                resultAssign.setAssignStatus(rs.getString("ASSIGNSTATUS"));
                resultAssign.setAssignStatusDescription(rs.getString("DESCRIPTION"));
                resultAssign.setExpirydate(rs.getString("PASSPORTEXPIRYDATE"));


                previousApplicationList.add(resultAssign);

            }

        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }

        return previousApplicationList;
    }
    
    public HashMap<String, String> getAllIdentificationTypeByVerificationCategory(Connection con, String category) throws Exception {

        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;
        try {
            getAllUserRole = con.prepareStatement("SELECT TC.DOCUMENTTYPECODE,TC.DOCUMENTNAME "
                    + " FROM APPLICATIONDOCUMENT TC, STATUS ST WHERE TC.STATUS = ST.STATUSCODE AND TC.VERIFICATIONCATEGORY= ?");
            getAllUserRole.setString(1, category);
            rs = getAllUserRole.executeQuery();
            identificationList = new HashMap<String, String>();

            while (rs.next()) {

                identificationList.put(rs.getString("DOCUMENTTYPECODE"), rs.getString("DOCUMENTNAME"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();

        }

        return identificationList;
    }    

//convert string to Date type
    private String stringTodate(String date) throws ParseException {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        Date convertedDate = dateFormat.parse(dateString);


        return (dateFormat.format(convertedDate));



    }

    public boolean insertEstablishmentKey(Connection con, ApplicationAssignBean assignBean) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO ESTABLISHMENTDETAILS(BUSINESSREGNUMBER,APPLICATIONID,CREATEDDATE,LASTUPDATEDUSER) "
                    + "VALUES(?,?,CURRENT_TIMESTAMP,?)");

            insertStat.setString(1, assignBean.getIdentityNo());
            insertStat.setString(2, assignBean.getApplicationId());
            insertStat.setString(3,  assignBean.getLastUpdatedUser());

            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }
    
    
    public boolean checkUniqueEstablishment(Connection con, String identityNo) throws Exception {
        boolean success = true;
        ResultSet rs = null;
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement("SELECT * FROM ESTABLISHMENTDETAILS WHERE BUSINESSREGNUMBER=? ");
            pst.setString(1, identityNo);
            rs = pst.executeQuery();

            while (rs.next()) {
               success=false; 
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            pst.close();
            rs.close();
        }
        return success;
    }
}
