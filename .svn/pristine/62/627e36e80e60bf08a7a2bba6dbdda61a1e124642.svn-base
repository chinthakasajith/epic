/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.documentverify.persistance;

import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationAssignBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DocumentUploadBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.ApplicationVerificationBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.CheckedApplicantDetailsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.CheckedEstDetailsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.CorporateVerifyBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.DebitCheckBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.DebitVerifyBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.PreviousApplicationDetailsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.SearchDocumentVerifyBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.SignatureUploadedBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.VerificationPointsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.VerifyApplicantDetailBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author chanuka
 */
public class DocumentVerifyPersistance {

    private List<ApplicationAssignBean> searchAssignAppList = null;
    private VerifyApplicantDetailBean verifyCusBean = null;
    private DebitCheckBean debitBean = null;
    private CheckedApplicantDetailsBean checkedApplicationBean = null;
    private CheckedApplicantDetailsBean bankapplicationVerifyBean = null;
    private List<PreviousApplicationDetailsBean> previousDetailsBean = null;
    private List<VerificationPointsBean> verificationPointsBeanList = null;
    private List<DocumentUploadBean> uploadedDocumentTypeList = null;
    private CorporateVerifyBean verifyBean = null;
    private CheckedEstDetailsBean corporateEstVerifyBean = null;
    private ApplicationVerificationBean applicationVerificationBean = null;

    public List<ApplicationAssignBean> documentVerifySearch(Connection con, SearchDocumentVerifyBean searchBean) throws Exception {

        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query

        try {

            strSqlBasic = "SELECT DISTINCT a.APPLICATIONID,a.IDENTIFICATIONTYPE,ad.DOCUMENTNAME,a.IDENTIFICATIONNO,a.PRIORITYLEVELCODE , pl.DESCRIPTION AS PRIORITYNAME, a.REFERANCIALEMPNO,a.BRANCHCODE , bk.DESCRPTION AS BRANCHNAME , "
                    + "a.ASSIGNUSER, a.ASSIGNSTATUS , st.DESCRIPTION ,cs.APPLICATIONSTATUS AS VERIFYSTATUS ,a.CARDCATEGORY,cc.DESCRIPTION AS CARDCATEGORYDES ,a.LASTUPDATEDUSER,a.LASTUPDATEDTIME,a.CREATETIME "
                    + "FROM CARDAPPLICATION a ,STATUS st,PRIORITYLEVEL pl ,BANKBRANCH bk ,CARDAPPLICATIONSTATUS cs,CARDCATEGORY cc,APPLICATIONDOCUMENT ad";

            String condition = "";

            if (!searchBean.getCardCategory().contentEquals("") || searchBean.getCardCategory().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.CARDCATEGORY = '" + searchBean.getCardCategory() + "'";
            }
            if (!searchBean.getApplicationId().contentEquals("") || searchBean.getApplicationId().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.APPLICATIONID LIKE '%" + searchBean.getApplicationId() + "%'";
            }

            if (!searchBean.getIdentityNo().contentEquals("") || searchBean.getIdentityNo().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.IDENTIFICATIONNO LIKE '%" + searchBean.getIdentityNo() + "%'";
            }

            if (!searchBean.getPriorityLevel().contentEquals("") || searchBean.getPriorityLevel().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.PRIORITYLEVELCODE = '" + searchBean.getPriorityLevel() + "'";
            }

            if (!searchBean.getStatus().contentEquals("") || searchBean.getStatus().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "cs.APPLICATIONSTATUS = '" + searchBean.getStatus() + "'";
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
                strSqlBasic += " WHERE a.ASSIGNSTATUS = st.STATUSCODE AND a.IDENTIFICATIONTYPE=ad.DOCUMENTTYPECODE AND a.CARDCATEGORY=ad.CARDCATEGORYCODE AND a.PRIORITYLEVELCODE = pl.PRIORITYLEVELCODE AND a.BRANCHCODE = bk.BRANCHCODE(+) AND (bk.bankcode = (SELECT BANKCODE FROM COMMONPARAMETER) OR a.branchcode is null) AND a.APPLICATIONID = cs.APPLICATIONID AND a.CARDCATEGORY = cc.CATEGORYCODE AND (cs.APPLICATIONSTATUS ='" + StatusVarList.APP_CHECK_COMPLETE + "' OR cs.APPLICATIONSTATUS ='" + StatusVarList.APP_ONHOLD + "') AND a.CARDAPPLIACTIONDOMAIN='CREDIT' AND " + condition + " ORDER BY a.APPLICATIONID";
            } else {
                strSqlBasic += " WHERE a.ASSIGNSTATUS = st.STATUSCODE AND a.IDENTIFICATIONTYPE=ad.DOCUMENTTYPECODE AND a.CARDCATEGORY=ad.CARDCATEGORYCODE AND a.PRIORITYLEVELCODE = pl.PRIORITYLEVELCODE AND a.BRANCHCODE = bk.BRANCHCODE(+) AND (bk.bankcode = (SELECT BANKCODE FROM COMMONPARAMETER) OR a.branchcode is null) AND a.APPLICATIONID = cs.APPLICATIONID AND a.CARDCATEGORY = cc.CATEGORYCODE AND (cs.APPLICATIONSTATUS ='" + StatusVarList.APP_CHECK_COMPLETE + "' OR cs.APPLICATIONSTATUS ='" + StatusVarList.APP_ONHOLD + "') AND a.CARDAPPLIACTIONDOMAIN='CREDIT' ORDER BY a.APPLICATIONID";
            }

            stmt = con.prepareStatement(strSqlBasic);
            rs = stmt.executeQuery();

            searchAssignAppList = new ArrayList<ApplicationAssignBean>();

            while (rs.next()) {

                ApplicationAssignBean resultAssign = new ApplicationAssignBean();

                resultAssign.setCardCategory(rs.getString("CARDCATEGORY"));
                resultAssign.setCardCategoryDes(rs.getString("CARDCATEGORYDES"));
                resultAssign.setApplicationId(rs.getString("APPLICATIONID"));
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
                resultAssign.setVerificationStatus(rs.getString("VERIFYSTATUS"));
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

    public List<ApplicationAssignBean> getAllDebitVerifyList(Connection con, SearchDocumentVerifyBean searchBean) throws Exception {

        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query

        try {

            strSqlBasic = "SELECT a.APPLICATIONID,a.IDENTIFICATIONTYPE,a.IDENTIFICATIONNO,a.PRIORITYLEVELCODE , pl.DESCRIPTION AS PRIORITYNAME, a.REFERANCIALEMPNO,a.BRANCHCODE , bk.DESCRPTION AS BRANCHNAME , "
                    + "a.ASSIGNUSER, a.ASSIGNSTATUS , st.DESCRIPTION ,cs.APPLICATIONSTATUS AS VERIFYSTATUS ,a.CARDCATEGORY,cc.DESCRIPTION AS CARDCATEGORYDES ,a.LASTUPDATEDUSER,a.LASTUPDATEDTIME,a.CREATETIME "
                    + "FROM CARDAPPLICATION a ,STATUS st,PRIORITYLEVEL pl ,BANKBRANCH bk ,CARDAPPLICATIONSTATUS cs,CARDCATEGORY cc";

            String condition = "";

            if (!searchBean.getCardCategory().contentEquals("") || searchBean.getCardCategory().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.CARDCATEGORY = '" + searchBean.getCardCategory() + "'";
            }
            if (!searchBean.getApplicationId().contentEquals("") || searchBean.getApplicationId().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.APPLICATIONID LIKE '%" + searchBean.getApplicationId() + "%'";
            }

            if (!searchBean.getIdentityNo().contentEquals("") || searchBean.getIdentityNo().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.IDENTIFICATIONNO LIKE '%" + searchBean.getIdentityNo() + "%'";
            }

            if (!searchBean.getPriorityLevel().contentEquals("") || searchBean.getPriorityLevel().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.PRIORITYLEVELCODE = '" + searchBean.getPriorityLevel() + "'";
            }

            if (!searchBean.getStatus().contentEquals("") || searchBean.getStatus().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "cs.APPLICATIONSTATUS = '" + searchBean.getStatus() + "'";
            }

            if (!searchBean.getFromDate().contentEquals("") && !searchBean.getToDate().contentEquals("")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.CREATETIME >= to_Date('" + this.stringTodate(searchBean.getFromDate()) + "','yy-mm-dd') AND a.CREATETIME < to_Date('" + this.stringTodate(searchBean.getToDate()) + "','yy-mm-dd')+1";
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
                    condition += "a.CREATETIME < to_Date('" + this.stringTodate(searchBean.getToDate()) + "','yy-mm-dd')+1";
                }
            }

            if (!condition.equals("")) {
                strSqlBasic += " WHERE a.ASSIGNSTATUS = st.STATUSCODE AND a.PRIORITYLEVELCODE = pl.PRIORITYLEVELCODE AND a.BRANCHCODE = bk.BRANCHCODE(+) AND (bk.bankcode = (SELECT BANKCODE FROM COMMONPARAMETER) OR a.branchcode is null) AND a.APPLICATIONID = cs.APPLICATIONID AND a.CARDCATEGORY = cc.CATEGORYCODE AND (cs.APPLICATIONSTATUS ='" + StatusVarList.APP_FILLING_COMPLETE + "' OR cs.APPLICATIONSTATUS ='" + StatusVarList.APP_ONHOLD + "') AND a.CARDAPPLIACTIONDOMAIN='DEBIT' AND " + condition + " ORDER BY a.APPLICATIONID";
            } else {
                strSqlBasic += " WHERE a.ASSIGNSTATUS = st.STATUSCODE AND a.PRIORITYLEVELCODE = pl.PRIORITYLEVELCODE AND a.BRANCHCODE = bk.BRANCHCODE(+) AND (bk.bankcode = (SELECT BANKCODE FROM COMMONPARAMETER) OR a.branchcode is null) AND a.APPLICATIONID = cs.APPLICATIONID AND a.CARDCATEGORY = cc.CATEGORYCODE AND (cs.APPLICATIONSTATUS ='" + StatusVarList.APP_FILLING_COMPLETE + "' OR cs.APPLICATIONSTATUS ='" + StatusVarList.APP_ONHOLD + "') AND a.CARDAPPLIACTIONDOMAIN='DEBIT' ORDER BY a.APPLICATIONID";
            }

            stmt = con.prepareStatement(strSqlBasic);
            rs = stmt.executeQuery();

            searchAssignAppList = new ArrayList<ApplicationAssignBean>();

            while (rs.next()) {

                ApplicationAssignBean resultAssign = new ApplicationAssignBean();

                resultAssign.setCardCategory(rs.getString("CARDCATEGORY"));
                resultAssign.setCardCategoryDes(rs.getString("CARDCATEGORYDES"));
                resultAssign.setApplicationId(rs.getString("APPLICATIONID"));
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
                resultAssign.setVerificationStatus(rs.getString("VERIFYSTATUS"));

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

    public List<ApplicationAssignBean> getAllBulkVerifyList(Connection con, SearchDocumentVerifyBean searchBean) throws Exception {

        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query

        try {

            strSqlBasic = "SELECT a.CARDNUMBER,a.APPLICATIONID,a.IDENTIFICATIONTYPE,a.IDENTIFICATIONNO, a.REFERANCIALEMPNO,a.BRANCHCODE , bk.DESCRPTION AS BRANCHNAME , "
                    + "a.ASSIGNUSER, a.ASSIGNSTATUS , st.DESCRIPTION ,cs.APPLICATIONSTATUS AS VERIFYSTATUS ,a.LASTUPDATEDUSER,a.LASTUPDATEDTIME,a.CREATETIME "
                    + "FROM CARDAPPLICATION a ,STATUS st,BANKBRANCH bk ,CARDAPPLICATIONSTATUS cs,CARD ct";

            String condition = "";

            if (!searchBean.getApplicationId().contentEquals("") || searchBean.getApplicationId().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.APPLICATIONID LIKE '%" + searchBean.getApplicationId() + "%'";
            }

            if (!searchBean.getIdentityNo().contentEquals("") || searchBean.getIdentityNo().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.IDENTIFICATIONNO LIKE '%" + searchBean.getIdentityNo() + "%'";
            }

            if (!searchBean.getCardNumber().contentEquals("") || searchBean.getCardNumber().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.CARDNUMBER LIKE '%" + searchBean.getCardNumber() + "%'";
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
                strSqlBasic += " WHERE a.CARDNUMBER=ct.CARDNUMBER AND a.ASSIGNSTATUS = st.STATUSCODE AND a.BRANCHCODE = bk.BRANCHCODE(+) AND (bk.bankcode = (SELECT BANKCODE FROM COMMONPARAMETER) OR a.branchcode is null) AND a.APPLICATIONID = cs.APPLICATIONID AND (cs.APPLICATIONSTATUS ='" + StatusVarList.BULK_FILLING_COMPLETE + "' OR cs.APPLICATIONSTATUS ='" + StatusVarList.APP_ONHOLD + "') AND a.CARDAPPLIACTIONDOMAIN='DEBIT' AND " + condition + " ORDER BY a.APPLICATIONID";
            } else {
                strSqlBasic += " WHERE a.CARDNUMBER=ct.CARDNUMBER AND a.ASSIGNSTATUS = st.STATUSCODE AND a.BRANCHCODE = bk.BRANCHCODE(+) AND (bk.bankcode = (SELECT BANKCODE FROM COMMONPARAMETER) OR a.branchcode is null) AND a.APPLICATIONID = cs.APPLICATIONID AND (cs.APPLICATIONSTATUS ='" + StatusVarList.BULK_FILLING_COMPLETE + "' OR cs.APPLICATIONSTATUS ='" + StatusVarList.APP_ONHOLD + "') AND a.CARDAPPLIACTIONDOMAIN='DEBIT' ORDER BY a.APPLICATIONID";
            }

            stmt = con.prepareStatement(strSqlBasic);
            rs = stmt.executeQuery();

            searchAssignAppList = new ArrayList<ApplicationAssignBean>();

            while (rs.next()) {

                ApplicationAssignBean resultAssign = new ApplicationAssignBean();

                resultAssign.setCardNumber(rs.getString("CARDNUMBER"));
                resultAssign.setApplicationId(rs.getString("APPLICATIONID"));
                resultAssign.setAssignUser(rs.getString("ASSIGNUSER"));
                resultAssign.setCreatedTime(rs.getTimestamp("CREATETIME"));
                resultAssign.setIdentityNo(rs.getString("IDENTIFICATIONNO"));
                resultAssign.setIdentityType(rs.getString("IDENTIFICATIONTYPE"));
                resultAssign.setLastUpdatedTime(rs.getTimestamp("LASTUPDATEDTIME"));
                resultAssign.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                resultAssign.setReferralBranchId(rs.getString("BRANCHCODE"));
                resultAssign.setReferralBranchName(rs.getString("BRANCHNAME"));
                resultAssign.setReferralEmpNo(rs.getString("REFERANCIALEMPNO"));
                resultAssign.setAssignStatus(rs.getString("ASSIGNSTATUS"));
                resultAssign.setAssignStatusDescription(rs.getString("DESCRIPTION"));
                resultAssign.setVerificationStatus(rs.getString("VERIFYSTATUS"));

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

    public List<ApplicationAssignBean> corporateCardSearch(Connection CMSCon, SearchDocumentVerifyBean searchBean) throws Exception {

        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query

        try {

            strSqlBasic = "SELECT A.APPLICATIONID,A.IDENTIFICATIONTYPE,A.IDENTIFICATIONNO,A.PRIORITYLEVELCODE ,"
                    + " PL.DESCRIPTION AS PRIORITYNAME, A.REFERANCIALEMPNO,A.BRANCHCODE , BK.DESCRPTION AS BRANCHNAME , "
                    + " A.ASSIGNUSER, A.ASSIGNSTATUS , ST.DESCRIPTION ,CS.APPLICATIONSTATUS AS VERIFYSTATUS ,A.CARDCATEGORY,"
                    + " CC.DESCRIPTION AS CARDCATEGORYDES ,A.LASTUPDATEDUSER,A.LASTUPDATEDTIME,A.CREATETIME "
                    + " FROM CARDAPPLICATION A ,STATUS ST,PRIORITYLEVEL PL ,BANKBRANCH BK ,CARDAPPLICATIONSTATUS CS,CARDCATEGORY CC";

            String condition = "";

            if (!searchBean.getCardCategory().contentEquals("") || searchBean.getCardCategory().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "A.CARDCATEGORY = '" + searchBean.getCardCategory() + "'";
            }
            if (!searchBean.getApplicationId().contentEquals("") || searchBean.getApplicationId().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "A.APPLICATIONID LIKE '%" + searchBean.getApplicationId() + "%'";
            }

            if (!searchBean.getIdentityNo().contentEquals("") || searchBean.getIdentityNo().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "A.IDENTIFICATIONNO LIKE '%" + searchBean.getIdentityNo() + "%'";
            }

            if (!searchBean.getPriorityLevel().contentEquals("") || searchBean.getPriorityLevel().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "A.PRIORITYLEVELCODE = '" + searchBean.getPriorityLevel() + "'";
            }

            if (!searchBean.getStatus().contentEquals("") || searchBean.getStatus().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CS.APPLICATIONSTATUS = '" + searchBean.getStatus() + "'";
            }

            if (!searchBean.getFromDate().contentEquals("") && !searchBean.getToDate().contentEquals("")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "A.CREATETIME >= to_Date('" + this.stringTodate(searchBean.getFromDate()) + "','yy-mm-dd') AND a.CREATETIME <= to_Date('" + this.stringTodate(searchBean.getToDate()) + "','yy-mm-dd')";
            } else {

                if (!searchBean.getFromDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "A.CREATETIME >= to_Date('" + this.stringTodate(searchBean.getFromDate()) + "','yy-mm-dd')";
                }
                if (!searchBean.getToDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "A.CREATETIME <= to_Date('" + this.stringTodate(searchBean.getToDate()) + "','yy-mm-dd')";
                }
            }

            if (!condition.equals("")) {
                strSqlBasic += " WHERE A.ASSIGNSTATUS = ST.STATUSCODE AND A.PRIORITYLEVELCODE = PL.PRIORITYLEVELCODE "
                        + " AND A.BRANCHCODE = BK.BRANCHCODE(+) AND (BK.BANKCODE = (SELECT BANKCODE FROM COMMONPARAMETER)"
                        + " OR A.BRANCHCODE is null) AND A.APPLICATIONID = CS.APPLICATIONID AND A.CARDCATEGORY = CC.CATEGORYCODE "
                        + " AND (CS.APPLICATIONSTATUS ='" + StatusVarList.APP_CHECK_COMPLETE + "' "
                        + " OR CS.APPLICATIONSTATUS ='" + StatusVarList.APP_ONHOLD + "') AND A.CARDAPPLIACTIONDOMAIN='CREDIT' "
                        + " AND " + condition + " AND (A.CARDCATEGORY = '" + StatusVarList.CARD_CATEGORY_ESTABLISHMENT + "' "
                        + " OR A.CARDCATEGORY = '" + StatusVarList.CARD_CATEGORY_COPORATE + "')  ORDER BY A.APPLICATIONID";
            } else {
                strSqlBasic += " WHERE A.ASSIGNSTATUS = ST.STATUSCODE AND A.PRIORITYLEVELCODE = PL.PRIORITYLEVELCODE AND "
                        + " A.BRANCHCODE = BK.BRANCHCODE(+) AND (BK.BANKCODE = (SELECT BANKCODE FROM COMMONPARAMETER) "
                        + " OR A.BRANCHCODE is null) AND A.APPLICATIONID = CS.APPLICATIONID AND A.CARDCATEGORY = CC.CATEGORYCODE "
                        + " AND (CS.APPLICATIONSTATUS ='" + StatusVarList.APP_CHECK_COMPLETE + "' "
                        + " OR CS.APPLICATIONSTATUS ='" + StatusVarList.APP_ONHOLD + "') AND A.CARDAPPLIACTIONDOMAIN='CREDIT' "
                        + " AND (A.CARDCATEGORY = '" + StatusVarList.CARD_CATEGORY_ESTABLISHMENT + "' "
                        + " OR A.CARDCATEGORY = '" + StatusVarList.CARD_CATEGORY_COPORATE + "') ORDER BY A.APPLICATIONID";
            }

            stmt = CMSCon.prepareStatement(strSqlBasic);
            rs = stmt.executeQuery();

            searchAssignAppList = new ArrayList<ApplicationAssignBean>();

            while (rs.next()) {

                ApplicationAssignBean resultAssign = new ApplicationAssignBean();

                resultAssign.setCardCategory(rs.getString("CARDCATEGORY"));
                resultAssign.setCardCategoryDes(rs.getString("CARDCATEGORYDES"));
                resultAssign.setApplicationId(rs.getString("APPLICATIONID"));
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
                resultAssign.setVerificationStatus(rs.getString("VERIFYSTATUS"));

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
                throw e;
            }
        }

        return searchAssignAppList;
    }

    public VerifyApplicantDetailBean getAllVerifyCustomerDetails(Connection con, String applicationId, String applicationTypeCode) throws Exception {

        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query

        try {
            if(applicationTypeCode!=null && applicationTypeCode.equals("M")){
                strSqlBasic = "SELECT  ca.STAFFSTATUS, cd.APPLICATIONID,cd.NAMEINFULL,cd.DATEOFBIRTH,cd.MARITALSTATUS," //cd.FIRSTNAME,cd.LASTNAME,cd.MIDDLENAME
                        + "cd.ADDRESS1,cd.HOMETELEPHONENO,cd.MOBILENO,ce.OFFICETELEPHONE,ce.DESIGNATION,ce.COMPANYNAME,ce.SERVICEYEARS,"
                        + "ce.OFFICEADDRESS1,ca.IDENTIFICATIONTYPE,ca.IDENTIFICATIONNO,cd.RELATIVENAME,cd.RELATIONSHIP,cd.RELADDRESS1,"
                        + "cd.RELRESIDANCEPHONE,cd.RELMOBILENO FROM CARDAPPLICATIONPERSONALDETAILS cd,"
                        + "CARDAPPLICATIONEMPDETAILS ce,CARDAPPLICATION ca WHERE cd.APPLICATIONID=? "
                        + "AND cd.APPLICATIONID = ce.APPLICATIONID "
                        + "AND cd.APPLICATIONID = ca.APPLICATIONID";                
            }else  if(applicationTypeCode!=null && applicationTypeCode.equals("E")){
                strSqlBasic ="SELECT cd.*"
                        + " FROM ESTABLISHMENTDETAILS cd WHERE cd.APPLICATIONID=?";
            }else{
                strSqlBasic = "SELECT ca.STAFFSTATUS, cd.APPLICATIONID,cd.NAMEINFULL,cd.DATEOFBIRTH,cd.MARITALSTATUS," //cd.FIRSTNAME,cd.LASTNAME,cd.MIDDLENAME
                    + "cd.ADDRESS1,cd.HOMETELEPHONENO,cd.MOBILENO , "
                    + "ca.IDENTIFICATIONTYPE,ca.IDENTIFICATIONNO,cd.RELATIVENAME,cd.RELATIONSHIP,cd.RELADDRESS1,"
                    + "cd.RELRESIDANCEPHONE,cd.RELMOBILENO FROM CARDAPPLICATIONPERSONALDETAILS cd,"
                    + "CARDAPPLICATION ca WHERE cd.APPLICATIONID=? "
                    + "AND cd.APPLICATIONID = ca.APPLICATIONID";
            }


            stmt = con.prepareStatement(strSqlBasic);
            stmt.setString(1, applicationId);

            rs = stmt.executeQuery();

            verifyCusBean = new VerifyApplicantDetailBean();

            while (rs.next()) {
                VerifyApplicantDetailBean resultCustomer = new VerifyApplicantDetailBean();

                if(applicationTypeCode!=null && applicationTypeCode.equals("E")){
                    
                resultCustomer.setCompanyName(rs.getString("NAMEOFTHECOMPANY"));
                resultCustomer.setNatureOfTheBusiness(rs.getString("NATUREOFBUSINESS"));
                resultCustomer.setAnnualTurnover(rs.getString("ANNUALTURNOVER"));
                resultCustomer.setShareCapital(rs.getString("SHARECAPITAL"));
                resultCustomer.setNetProfit(rs.getString("NETPROFIT"));
                //resultCustomer.setNetAssets(rs.getString("NETASSETS"));
                
                resultCustomer.setApplicationId(rs.getString("APPLICATIONID")); 
                resultCustomer.setAddress1(rs.getString("BUSINESSADDRESS1"));
                resultCustomer.setAddress2(rs.getString("BUSINESSADDRESS2"));
                resultCustomer.setAddress3(rs.getString("BUSINESSADDRESS3"));               
                resultCustomer.setContactPersLandTelNumber(rs.getString("CONTACTNUMBERSLAND"));
                resultCustomer.setContactPersMobileNumber(rs.getString("CONTACTNUMBERSMOBILE"));
                resultCustomer.setEmail(rs.getString("CONTACTEMAIL"));
                resultCustomer.setFaxNo(rs.getString("CONTACTFAXNUMBER"));
                //resultCustomer.setCardType(rs.getString("REQUESTCARDTYPE"));
                //resultCustomer.setCardProduct(rs.getString("REQUESTCARDPRODUCT"));
                //resultCustomer.setCreditLimit(rs.getString("REQUESTCREDITLIMIT"));
                //resultCustomer.setCardCurrency(rs.getString("CURRENCYTYPE"));
                resultCustomer.setContactPersonFullName(rs.getString("CONTACTPERSON"));
                resultCustomer.setBusinessRegNumber(rs.getString("BUSINESSREGNUMBER"));
                    
                    
                    
                }else{
                    resultCustomer.setApplicationId(rs.getString("APPLICATIONID"));
                    //                resultCustomer.setFirstName(rs.getString("FIRSTNAME"));
                    //                resultCustomer.setLastName(rs.getString("LASTNAME"));
                    //                resultCustomer.setMiddleName(rs.getString("MIDDLENAME"));
                                    //check middle name is enterd or not
                    //                if (rs.getString("MIDDLENAME") != null) {
                    //                    resultCustomer.setFullName(rs.getString("FIRSTNAME").concat(" ").concat(rs.getString("MIDDLENAME")).concat(" ").concat(rs.getString("LASTNAME")));
                    //                }else{
                    //                    resultCustomer.setFullName(rs.getString("FIRSTNAME").concat(" ").concat(rs.getString("LASTNAME")));
                    //                }
                    resultCustomer.setFullName(rs.getString("NAMEINFULL"));
                    resultCustomer.setBirthday(rs.getString("DATEOFBIRTH"));
                    resultCustomer.setMaritalStatus(rs.getString("MARITALSTATUS"));
                    resultCustomer.setApplicantAddress(rs.getString("ADDRESS1"));
                    resultCustomer.setHomeTelNumber(rs.getString("HOMETELEPHONENO"));
                    resultCustomer.setMobileNumber(rs.getString("MOBILENO"));
                    if(applicationTypeCode!=null && applicationTypeCode.equals("M")){

                        resultCustomer.setOfficeTelNumber(rs.getString("OFFICETELEPHONE"));
                        resultCustomer.setCompany(rs.getString("COMPANYNAME"));
                        resultCustomer.setDesignation(rs.getString("DESIGNATION"));
                        resultCustomer.setServiceLength(rs.getString("SERVICEYEARS"));
                    }
                    resultCustomer.setIdentificationType(rs.getString("IDENTIFICATIONTYPE"));
                    resultCustomer.setIdentificationNumber(rs.getString("IDENTIFICATIONNO"));
                    resultCustomer.setRefereeFullName(rs.getString("RELATIVENAME"));
                    resultCustomer.setRelationship(rs.getString("RELATIONSHIP"));
                    resultCustomer.setRefereeAddress(rs.getString("RELADDRESS1"));
                    resultCustomer.setRefereeHomeTel(rs.getString("RELRESIDANCEPHONE"));
                    resultCustomer.setRefereeMobile(rs.getString("RELMOBILENO"));
                    resultCustomer.setStaffStatus(rs.getString("STAFFSTATUS"));                
                }
                verifyCusBean = resultCustomer;

            }

        } catch (SQLException exSql) {
            throw exSql;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }

        return verifyCusBean;
    }

    public DebitCheckBean getDebitDetailBean(Connection con, String applicationId) throws Exception {

        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query

        try {

            strSqlBasic = "SELECT ca.CARDNUMBER,cd.SIDENTIFICATIONNO,cd.SIDENTIFICATIONTYPE,cd.STITLE,cd.SFIRSTNAME,cd.SLASTNAME,cd.SMIDDLENAME,cd.SDATEOFBIRTH,cd.SADDRESS1,cd.SAREA,"
                    + "cd.SHOMETELEPHONENO,cd.SMOBILENO,cd.SMOTHERSMAIDENNAME,cd.SNAMEONCARD,ca.CARDCATEGORY,"
                    + "cc.DESCRIPTION as CARDCATEGORYDES, cd.APPLICATIONID,cd.FIRSTNAME,cd.LASTNAME,cd.ADDRESS1,"
                    + "cd.MIDDLENAME,cd.DATEOFBIRTH,cd.HOMETELEPHONENO,cd.MOBILENO,ca.IDENTIFICATIONTYPE,ca.IDENTIFICATIONNO,"
                    + "cd.title,cd.NAMEONCARD,cd.MOTHERSMAIDENNAME,cd.AREA FROM DEBITCARDAPPLICATIONDETAILS cd,"
                    + "CARDAPPLICATION ca,CARDCATEGORY cc WHERE cd.APPLICATIONID=?"
                    + "AND cd.APPLICATIONID = ca.APPLICATIONID AND cc.CATEGORYCODE=ca.CARDCATEGORY";

            stmt = con.prepareStatement(strSqlBasic);
            stmt.setString(1, applicationId);

            rs = stmt.executeQuery();

            debitBean = new DebitCheckBean();

            while (rs.next()) {

                DebitCheckBean resultCustomer = new DebitCheckBean();

                resultCustomer.setApplicationId(rs.getString("APPLICATIONID"));
                resultCustomer.setCardNumber(rs.getString("CARDNUMBER"));
                resultCustomer.setCardCategory(rs.getString("CARDCATEGORYDES"));
                resultCustomer.setTitle(rs.getString("title"));
                resultCustomer.setFirstName(rs.getString("FIRSTNAME"));
                resultCustomer.setLastName(rs.getString("LASTNAME"));
                resultCustomer.setMiddleName(rs.getString("MIDDLENAME"));
                resultCustomer.setNameOnCard(rs.getString("NAMEONCARD"));
                resultCustomer.setMothersMaidenName(rs.getString("MOTHERSMAIDENNAME"));
//                resultCustomer.setAccountNo(rs.getString("MIDDLENAME"));
//                resultCustomer.setAccountType(rs.getString("MIDDLENAME"));
                resultCustomer.setDateOfBirth(rs.getString("DATEOFBIRTH"));
                resultCustomer.setAddress(rs.getString("ADDRESS1"));
                resultCustomer.setArea(rs.getString("AREA"));
                resultCustomer.setResidentPhone(rs.getString("HOMETELEPHONENO"));
                resultCustomer.setMobileNo(rs.getString("MOBILENO"));
                resultCustomer.setIdentificationType(rs.getString("IDENTIFICATIONTYPE"));
                resultCustomer.setIdentificationNo(rs.getString("IDENTIFICATIONNO"));

                resultCustomer.setSidentificationNo(rs.getString("SIDENTIFICATIONNO"));
                resultCustomer.setSidentificationType(rs.getString("SIDENTIFICATIONTYPE"));
                resultCustomer.setStitle(rs.getString("STITLE"));
                resultCustomer.setSfirstName(rs.getString("SFIRSTNAME"));
                resultCustomer.setSlastName(rs.getString("SLASTNAME"));
                resultCustomer.setSmiddleName(rs.getString("SMIDDLENAME"));
                resultCustomer.setSdateOfBirth(rs.getString("SDATEOFBIRTH"));
                resultCustomer.setSaddress(rs.getString("SADDRESS1"));
                resultCustomer.setSarea(rs.getString("SAREA"));
                resultCustomer.setShomeTel(rs.getString("SHOMETELEPHONENO"));
                resultCustomer.setSmobileNo(rs.getString("SMOBILENO"));
                resultCustomer.setSmothersMaidenName(rs.getString("SMOTHERSMAIDENNAME"));
                resultCustomer.setSnameOnCard(rs.getString("SNAMEONCARD"));

                debitBean = resultCustomer;

            }

        } catch (SQLException exSql) {
            throw exSql;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }

        return debitBean;
    }

    public CheckedApplicantDetailsBean getAllPreviousCheckedDetails(Connection con, String applicationId) throws Exception {

        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query

        try {

            strSqlBasic = "SELECT APPLICATIONID,EMPLOYMENTDETAILS,SALARYCONFIRMATION,NICNUMBERFROMEMP,LENGTHOFSERVICE,"
                    + "EMPLOYMENTSTATUS,FULLNAME,NICNUMBER,HOMETELEPHONENO,MAILINGADDRESS,REFERENCEDETAILS,"
                    + "ADDRESSFROMRESI,MOBILENO,OWNERSHIP,PLACEOFWORK,DETAILSOFREFERENCE,VEHICLE,"
                    + "PERSONALDETAILSATRESIDENT,RELATIONSHIP,RESIDENCE,WORKPLACE,PERSONALDETAILSATREFREE,"
                    + "ADDRESSFROMREF,DOCIDENTIFICATION,DOCSALARYSLIP,DOCCONFIRMATIONOFEMPLOYEE,DOCUTILITYBILL,"
                    + "DOCMARRIAGECERTIFICATE,DOCBIRTHCERTIFICATE ,DOCSALARYSLIP1,DOCSALARYSLIP2,DOCBANKSTATEMENT,DOCSTAFFDECFORM ,COLLATERALCONFIRMATIONLETTER "
                    + ",SURETYAGREEMENTFORM,BOARDRESOLUTIONFORM,SURETYAGREEMENTFORM,BOARDRESOLUTIONFORM,BUSINESSREGFORM "
                    + "FROM CARDAPPLICATIONVERIFY WHERE APPLICATIONID=? ";

            stmt = con.prepareStatement(strSqlBasic);
            stmt.setString(1, applicationId);

            rs = stmt.executeQuery();

            checkedApplicationBean = new CheckedApplicantDetailsBean();

            while (rs.next()) {

                CheckedApplicantDetailsBean resultChecked = new CheckedApplicantDetailsBean();

                resultChecked.setApplicationId(rs.getString("APPLICATIONID"));
                resultChecked.setEmpDetails(rs.getString("EMPLOYMENTDETAILS"));
                resultChecked.setSalaryConfirm(rs.getString("SALARYCONFIRMATION"));
                resultChecked.setEmpNic(rs.getString("NICNUMBERFROMEMP"));
                resultChecked.setServiceLength(rs.getString("LENGTHOFSERVICE"));
                resultChecked.setEmpStatus(rs.getString("EMPLOYMENTSTATUS"));
                resultChecked.setFullname(rs.getString("FULLNAME"));
                resultChecked.setNic(rs.getString("NICNUMBER"));
                resultChecked.setHomeTelNo(rs.getString("HOMETELEPHONENO"));
                resultChecked.setMailingAddress(rs.getString("MAILINGADDRESS"));
                resultChecked.setReferenceDetails(rs.getString("REFERENCEDETAILS"));
                resultChecked.setAddressFromResidence(rs.getString("ADDRESSFROMRESI"));
                resultChecked.setMobileNo(rs.getString("MOBILENO"));
                resultChecked.setOwenership(rs.getString("OWNERSHIP"));
                resultChecked.setPlaceOfWork(rs.getString("PLACEOFWORK"));
                resultChecked.setDetailsOfReference(rs.getString("DETAILSOFREFERENCE"));
                resultChecked.setRelatioship(rs.getString("RELATIONSHIP"));
                resultChecked.setResidence(rs.getString("RESIDENCE"));
                resultChecked.setWorkPlace(rs.getString("WORKPLACE"));
                resultChecked.setPersonalDetailsReferee(rs.getString("PERSONALDETAILSATREFREE"));
                resultChecked.setAddressFromReferee(rs.getString("ADDRESSFROMREF"));
                resultChecked.setVehicle(rs.getString("VEHICLE"));
                resultChecked.setApplicantPersonal(rs.getString("PERSONALDETAILSATRESIDENT"));
                resultChecked.setDocIdentification(rs.getString("DOCIDENTIFICATION"));
                resultChecked.setDocSalarySlip1(rs.getString("DOCSALARYSLIP1"));
                resultChecked.setDocSalarySlip2(rs.getString("DOCSALARYSLIP2"));
                resultChecked.setDocBankStatement(rs.getString("DOCBANKSTATEMENT"));
                resultChecked.setDocStaffDecForm(rs.getString("DOCSTAFFDECFORM"));
                
                resultChecked.setDocConfirmationLetter(rs.getString("DOCCONFIRMATIONOFEMPLOYEE"));
                resultChecked.setDocUtilityBill(rs.getString("DOCUTILITYBILL"));
                resultChecked.setDocMarriageCertificate(rs.getString("DOCMARRIAGECERTIFICATE"));
                resultChecked.setCollateralConfirmationLetter(rs.getString("COLLATERALCONFIRMATIONLETTER"));
                
                resultChecked.setSuretyAgreementForm(rs.getString("SURETYAGREEMENTFORM"));
                resultChecked.setBoardResolutionForm(rs.getString("BOARDRESOLUTIONFORM"));
                resultChecked.setBusinessRegForm(rs.getString("BUSINESSREGFORM"));
                
                checkedApplicationBean = resultChecked;

            }

        } catch (SQLException exSql) {
            throw exSql;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }

        return checkedApplicationBean;
    }

    public List<PreviousApplicationDetailsBean> getPreviousCustomerDetails(Connection con, String identificationNo) throws Exception {

        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query

        try {

            strSqlBasic = "SELECT ca.APPLICATIONID,ca.IDENTIFICATIONTYPE,ca.IDENTIFICATIONNO,cs.APPLICATIONSTATUS,st.DESCRIPTION, cp.ADDRESS1,cp.MOBILENO ,"
                    + "cp.HOMETELEPHONENO,ce.COMPANYNAME,ce.DESIGNATION FROM CARDAPPLICATION ca,CARDAPPLICATIONSTATUS cs,"
                    + "CARDAPPLICATIONPERSONALDETAILS cp,CARDAPPLICATIONEMPDETAILS ce,STATUS st WHERE  cs.APPLICATIONID = ca.APPLICATIONID "
                    + " AND cp.APPLICATIONID(+) = ca.APPLICATIONID AND ce.APPLICATIONID(+) = ca.APPLICATIONID AND cs.APPLICATIONSTATUS = st.STATUSCODE AND ca.IDENTIFICATIONNO =?";

            stmt = con.prepareStatement(strSqlBasic);
            stmt.setString(1, identificationNo);

            rs = stmt.executeQuery();

            previousDetailsBean = new ArrayList<PreviousApplicationDetailsBean>();

            while (rs.next()) {

                PreviousApplicationDetailsBean resultCustomer = new PreviousApplicationDetailsBean();

                resultCustomer.setApplicationId(rs.getString("APPLICATIONID"));
                resultCustomer.setApplicantAddress(rs.getString("ADDRESS1"));
                resultCustomer.setHomeTelNumber(rs.getString("HOMETELEPHONENO"));
                resultCustomer.setMobileNumber(rs.getString("MOBILENO"));
                resultCustomer.setCompany(rs.getString("COMPANYNAME"));
                resultCustomer.setDesignation(rs.getString("DESIGNATION"));
                resultCustomer.setIdentificationType(rs.getString("IDENTIFICATIONTYPE"));
                resultCustomer.setIdentificationNumber(rs.getString("IDENTIFICATIONNO"));
                resultCustomer.setApplicationStatus(rs.getString("APPLICATIONSTATUS"));
                resultCustomer.setApplicationStatusDes(rs.getString("DESCRIPTION"));

                previousDetailsBean.add(resultCustomer);

            }

        } catch (SQLException exSql) {
            throw exSql;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }

        return previousDetailsBean;
    }

    public List<VerificationPointsBean> getVerificationPoints(Connection con,String cardCategoryCode) throws Exception {

        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query

        try {
            if(cardCategoryCode!=null && !cardCategoryCode.trim().isEmpty()){
                strSqlBasic = "SELECT VP.FIELDNAME,VP.DESCRIPTION,VP.DOCUMENTTYPE,VP.STATUS ,VP.CARDCATEGORYCODE,VP.ISMANDATORY,VP.ISSTAFF "
                    + " FROM VERIFICATIONPOINT VP "
                    + " WHERE VP.CARDCATEGORYCODE=?";  
                stmt = con.prepareStatement(strSqlBasic);
                stmt.setString(1, cardCategoryCode);
            }else{
                strSqlBasic = "SELECT vp.FIELDNAME,vp.DESCRIPTION,vp.DOCUMENTTYPE,vp.STATUS, VP.CARDCATEGORYCODE ,VP.ISMANDATORY,VP.ISSTAFF "
                    + "FROM VERIFICATIONPOINT vp";
                stmt = con.prepareStatement(strSqlBasic);

            }

            rs = stmt.executeQuery();

            verificationPointsBeanList = new ArrayList<VerificationPointsBean>();

            while (rs.next()) {

                VerificationPointsBean resultPoints = new VerificationPointsBean();

                resultPoints.setFieldName(rs.getString("FIELDNAME"));
                resultPoints.setDescription(rs.getString("DESCRIPTION"));
                resultPoints.setDocumentType(rs.getString("DOCUMENTTYPE"));
                resultPoints.setStatus(rs.getString("STATUS"));
                resultPoints.setCardCategoryCode(rs.getString("CARDCATEGORYCODE"));
                resultPoints.setIsMandatory(rs.getBoolean("ISMANDATORY"));
                if(rs.getString("ISSTAFF")!=null && rs.getString("ISSTAFF").endsWith("1")){
                    resultPoints.setIsStaff("YES");
                }else{
                    resultPoints.setIsStaff("NO");
                }


                verificationPointsBeanList.add(resultPoints);

            }

        } catch (SQLException exSql) {
            throw exSql;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }

        return verificationPointsBeanList;
    }

    public List<DocumentUploadBean> getuploadedDocumentsType(Connection con, String applicationId) throws Exception {

        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query

        try {

            strSqlBasic = "SELECT cd.APPLICATIONID,cd.VERIFICATIONCATEGORY,cd.FILENAME,cd.DOCUMENTTYPE FROM CARDAPPLICATIONDOCUMENT cd WHERE cd.APPLICATIONID=?";

            stmt = con.prepareStatement(strSqlBasic);
            stmt.setString(1, applicationId);

            rs = stmt.executeQuery();

            uploadedDocumentTypeList = new ArrayList<DocumentUploadBean>();

            while (rs.next()) {

                DocumentUploadBean resultBean = new DocumentUploadBean();

                resultBean.setApplicationId(rs.getString("APPLICATIONID"));
                resultBean.setVerificationCategory(rs.getString("VERIFICATIONCATEGORY"));
                resultBean.setDocumentType(rs.getString("DOCUMENTTYPE"));
                resultBean.setFileName(rs.getString("FILENAME"));

                uploadedDocumentTypeList.add(resultBean);

            }

        } catch (SQLException exSql) {
            throw exSql;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }

        return uploadedDocumentTypeList;
    }

    public SignatureUploadedBean getuploadedSignatures(Connection con, String applicationId) throws Exception {

        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query

        SignatureUploadedBean signBean = null;

        try {

            strSqlBasic = "SELECT cd.APPLICATIONID,cd.JOINTSIGNATURE,cd.PRIMARYSIGNATURE FROM CARDAPPLICATION cd WHERE cd.APPLICATIONID=?";

            stmt = con.prepareStatement(strSqlBasic);
            stmt.setString(1, applicationId);

            rs = stmt.executeQuery();

            while (rs.next()) {

                signBean = new SignatureUploadedBean();

                signBean.setApplicationId(rs.getString("APPLICATIONID"));
                signBean.setPrimarySignature(rs.getString("PRIMARYSIGNATURE"));
                signBean.setJointSignature(rs.getString("JOINTSIGNATURE"));

            }

        } catch (SQLException exSql) {
            throw exSql;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }

        return signBean;
    }

    public boolean updateCardApplicationCrib(Connection con, String applicationId, String cribFileName, String user, Date updateTime) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE CARDAPPLICATION SET CRIBREPORT=?,LASTUPDATEDUSER=?,LASTUPDATEDTIME=? "
                    + " WHERE APPLICATIONID=? ");

            updateStat.setString(1, cribFileName);
            updateStat.setString(2, user);
            updateStat.setTimestamp(3, new Timestamp(updateTime.getTime()));
            updateStat.setString(4, applicationId);

            updateStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    public boolean updateCardApplicationStatus(Connection con, CheckedApplicantDetailsBean checkedApplicantBean, String applicationStatus) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE CARDAPPLICATIONSTATUS SET APPLICATIONSTATUS=?"
                    + " WHERE APPLICATIONID=? ");

            updateStat.setString(1, applicationStatus);
            updateStat.setString(2, checkedApplicantBean.getApplicationId());

            updateStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    public boolean updateDebitCardApplicationStatus(Connection con, DebitVerifyBean checkedApplicantBean, String applicationStatus) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE CARDAPPLICATIONSTATUS SET APPLICATIONSTATUS=?"
                    + " WHERE APPLICATIONID=? ");

            updateStat.setString(1, applicationStatus);
            updateStat.setString(2, checkedApplicantBean.getApplicationId());

            updateStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    public boolean insertVerifyDetailsOfApplication(Connection con, CheckedApplicantDetailsBean checkedApplicantBean) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO CARDAPPLICATIONVERIFY(APPLICATIONID,EMPLOYMENTDETAILS, "
                    + "SALARYCONFIRMATION,NICNUMBERFROMEMP,LENGTHOFSERVICE,EMPLOYMENTSTATUS,FULLNAME,NICNUMBER,"
                    + "HOMETELEPHONENO,MAILINGADDRESS,REFERENCEDETAILS,ADDRESSFROMRESI,MOBILENO,OWNERSHIP,PLACEOFWORK,"
                    + "DETAILSOFREFERENCE,VEHICLE,PERSONALDETAILSATRESIDENT,RELATIONSHIP,RESIDENCE,WORKPLACE,PERSONALDETAILSATREFREE,"
                    + "ADDRESSFROMREF,DOCIDENTIFICATION,DOCSALARYSLIP1,DOCCONFIRMATIONOFEMPLOYEE,DOCUTILITYBILL,"
                    + "DOCMARRIAGECERTIFICATE,DOCBIRTHCERTIFICATE,LASTUPDATEDUSER,LASTUPDATEDTIME,DOCSALARYSLIP2,DOCBANKSTATEMENT,DOCSTAFFDECFORM,COLLATERALCONFIRMATIONLETTER"
                    + ",SURETYAGREEMENTFORM,BOARDRESOLUTIONFORM) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

            insertStat.setString(1, checkedApplicantBean.getApplicationId());
            insertStat.setString(2, checkedApplicantBean.getEmpDetails());
            insertStat.setString(3, checkedApplicantBean.getSalaryConfirm());
            insertStat.setString(4, checkedApplicantBean.getEmpNic());
            insertStat.setString(5, checkedApplicantBean.getServiceLength());
            insertStat.setString(6, checkedApplicantBean.getEmpStatus());
            insertStat.setString(7, checkedApplicantBean.getFullname());
            insertStat.setString(8, checkedApplicantBean.getNic());
            insertStat.setString(9, checkedApplicantBean.getHomeTelNo());
            insertStat.setString(10, checkedApplicantBean.getMailingAddress());
            insertStat.setString(11, checkedApplicantBean.getReferenceDetails());
            insertStat.setString(12, checkedApplicantBean.getAddressFromResidence());
            insertStat.setString(13, checkedApplicantBean.getMobileNo());
            insertStat.setString(14, checkedApplicantBean.getOwenership());
            insertStat.setString(15, checkedApplicantBean.getPlaceOfWork());
            insertStat.setString(16, checkedApplicantBean.getDetailsOfReference());
            insertStat.setString(17, checkedApplicantBean.getVehicle());
            insertStat.setString(18, checkedApplicantBean.getApplicantPersonal());
            insertStat.setString(19, checkedApplicantBean.getRelatioship());
            insertStat.setString(20, checkedApplicantBean.getResidence());
            insertStat.setString(21, checkedApplicantBean.getWorkPlace());
            insertStat.setString(22, checkedApplicantBean.getPersonalDetailsReferee());
            insertStat.setString(23, checkedApplicantBean.getAddressFromReferee());
            insertStat.setString(24, checkedApplicantBean.getDocIdentification());
            insertStat.setString(25, checkedApplicantBean.getDocSalarySlip1());
            insertStat.setString(26, checkedApplicantBean.getDocConfirmationLetter());
            insertStat.setString(27, checkedApplicantBean.getDocUtilityBill());
            insertStat.setString(28, checkedApplicantBean.getDocMarriageCertificate());
            insertStat.setString(29, checkedApplicantBean.getDocBirthCertificate());
            insertStat.setString(30, checkedApplicantBean.getLastUpdatedUser());
            insertStat.setTimestamp(31, new Timestamp(checkedApplicantBean.getLastUpdatedTime().getTime()));
            
            insertStat.setString(32, checkedApplicantBean.getDocSalarySlip2());
            insertStat.setString(33, checkedApplicantBean.getDocBankStatement());
            insertStat.setString(34, checkedApplicantBean.getDocStaffDecForm());
            insertStat.setString(35, checkedApplicantBean.getCollateralConfirmationLetter());
            
            insertStat.setString(36, checkedApplicantBean.getSuretyAgreementForm());
            insertStat.setString(37, checkedApplicantBean.getBoardResolutionForm());



            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public boolean insertDebitVerifyDetails(Connection con, DebitVerifyBean checkedApplicantBean) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO CARDAPPLICATIONVERIFY(APPLICATIONID,DEBITDOCID,DEBITACCOUNTNO,"
                    + "DEBITCARDHOLDERID,DEBITCARDHOLDERNAME,LASTUPDATEDUSER,LASTUPDATEDTIME,DEBITJOINTNIC,DEBITJOINTACCOUNTNO,"
                    + "DEBITJOINTCARDHOLDERID,DEBITJOINTCARDHOLDERNAME) VALUES(?,?,?,?,?,?,?,?,?,?,?) ");

            insertStat.setString(1, checkedApplicantBean.getApplicationId());
            insertStat.setString(2, checkedApplicantBean.getDocId());
            insertStat.setString(3, checkedApplicantBean.getAccontNo());
            insertStat.setString(4, checkedApplicantBean.getId());
            insertStat.setString(5, checkedApplicantBean.getName());
            insertStat.setString(6, checkedApplicantBean.getLastUpdatedUser());
            insertStat.setTimestamp(7, new Timestamp(checkedApplicantBean.getLastUpdatedTime().getTime()));
            insertStat.setString(8, checkedApplicantBean.getsNic());
            insertStat.setString(9, checkedApplicantBean.getsAccNo());
            insertStat.setString(10, checkedApplicantBean.getsId());
            insertStat.setString(11, checkedApplicantBean.getsName());

            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public boolean updateVerifyDetailsOfApplication(Connection con, CheckedApplicantDetailsBean checkedApplicantBean) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("UPDATE CARDAPPLICATIONVERIFY SET EMPLOYMENTDETAILS=?, "
                    + "SALARYCONFIRMATION=?,NICNUMBERFROMEMP=?,LENGTHOFSERVICE=?,EMPLOYMENTSTATUS=?,FULLNAME=?,NICNUMBER=?,"
                    + "HOMETELEPHONENO=?,MAILINGADDRESS=?,REFERENCEDETAILS=?,ADDRESSFROMRESI=?,MOBILENO=?,OWNERSHIP=?,PLACEOFWORK=?,"
                    + "DETAILSOFREFERENCE=?,VEHICLE=?,PERSONALDETAILSATRESIDENT=?,RELATIONSHIP=?,RESIDENCE=?,WORKPLACE=?,PERSONALDETAILSATREFREE=?,"
                    + "ADDRESSFROMREF=?,DOCIDENTIFICATION=?,DOCSALARYSLIP1=?,DOCCONFIRMATIONOFEMPLOYEE=?,DOCUTILITYBILL=?,"
                    + "DOCMARRIAGECERTIFICATE=?,DOCBIRTHCERTIFICATE=?,LASTUPDATEDUSER=?,LASTUPDATEDTIME=?,DOCSALARYSLIP2=?,DOCBANKSTATEMENT=?,DOCSTAFFDECFORM=?,COLLATERALCONFIRMATIONLETTER=? "
                    + ",SURETYAGREEMENTFORM=?,BOARDRESOLUTIONFORM=?, BUSINESSREGFORM=?   WHERE APPLICATIONID =?");

            insertStat.setString(1, checkedApplicantBean.getEmpDetails());
            insertStat.setString(2, checkedApplicantBean.getSalaryConfirm());
            insertStat.setString(3, checkedApplicantBean.getEmpNic());
            insertStat.setString(4, checkedApplicantBean.getServiceLength());
            insertStat.setString(5, checkedApplicantBean.getEmpStatus());
            insertStat.setString(6, checkedApplicantBean.getFullname());
            insertStat.setString(7, checkedApplicantBean.getNic());
            insertStat.setString(8, checkedApplicantBean.getHomeTelNo());
            insertStat.setString(9, checkedApplicantBean.getMailingAddress());
            insertStat.setString(10, checkedApplicantBean.getReferenceDetails());
            insertStat.setString(11, checkedApplicantBean.getAddressFromResidence());
            insertStat.setString(12, checkedApplicantBean.getMobileNo());
            insertStat.setString(13, checkedApplicantBean.getOwenership());
            insertStat.setString(14, checkedApplicantBean.getPlaceOfWork());
            insertStat.setString(15, checkedApplicantBean.getDetailsOfReference());
            insertStat.setString(16, checkedApplicantBean.getVehicle());
            insertStat.setString(17, checkedApplicantBean.getApplicantPersonal());
            insertStat.setString(18, checkedApplicantBean.getRelatioship());
            insertStat.setString(19, checkedApplicantBean.getResidence());
            insertStat.setString(20, checkedApplicantBean.getWorkPlace());
            insertStat.setString(21, checkedApplicantBean.getPersonalDetailsReferee());
            insertStat.setString(22, checkedApplicantBean.getAddressFromReferee());
            insertStat.setString(23, checkedApplicantBean.getDocIdentification());
            insertStat.setString(24, checkedApplicantBean.getDocSalarySlip1());
            insertStat.setString(25, checkedApplicantBean.getDocConfirmationLetter());
            insertStat.setString(26, checkedApplicantBean.getDocUtilityBill());
            insertStat.setString(27, checkedApplicantBean.getDocMarriageCertificate());
            insertStat.setString(28, checkedApplicantBean.getDocBirthCertificate());
            insertStat.setString(29, checkedApplicantBean.getLastUpdatedUser());
            insertStat.setTimestamp(30, new Timestamp(checkedApplicantBean.getLastUpdatedTime().getTime()));
            
            insertStat.setString(31, checkedApplicantBean.getDocSalarySlip2());
            insertStat.setString(32, checkedApplicantBean.getDocBankStatement());
            insertStat.setString(33, checkedApplicantBean.getDocStaffDecForm());
            insertStat.setString(34, checkedApplicantBean.getCollateralConfirmationLetter());
            insertStat.setString(35, checkedApplicantBean.getSuretyAgreementForm());
            insertStat.setString(36, checkedApplicantBean.getBoardResolutionForm());
            insertStat.setString(37, checkedApplicantBean.getBusinessRegForm());
            
            insertStat.setString(38, checkedApplicantBean.getApplicationId());


            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public boolean updateDebitVerifyDetails(Connection con, DebitVerifyBean debitVerifyBean) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("UPDATE CARDAPPLICATIONVERIFY SET DEBITDOCID=?, "
                    + "DEBITACCOUNTNO=?,DEBITCARDHOLDERID=?,DEBITCARDHOLDERNAME=?,LASTUPDATEDUSER=?,LASTUPDATEDTIME=?,"
                    + "DEBITJOINTNIC=?,DEBITJOINTACCOUNTNO=?,DEBITJOINTCARDHOLDERID=?,DEBITJOINTCARDHOLDERNAME=? "
                    + "WHERE APPLICATIONID =?");

            insertStat.setString(1, debitVerifyBean.getDocId());
            insertStat.setString(2, debitVerifyBean.getAccontNo());
            insertStat.setString(3, debitVerifyBean.getId());
            insertStat.setString(4, debitVerifyBean.getName());
            insertStat.setString(5, debitVerifyBean.getLastUpdatedUser());
            insertStat.setTimestamp(6, new Timestamp(debitVerifyBean.getLastUpdatedTime().getTime()));
            insertStat.setString(7, debitVerifyBean.getsNic());
            insertStat.setString(8, debitVerifyBean.getsAccNo());
            insertStat.setString(9, debitVerifyBean.getsId());
            insertStat.setString(10, debitVerifyBean.getsName());

            insertStat.setString(11, debitVerifyBean.getApplicationId());

            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public CheckedApplicantDetailsBean getBankApplicationVerifyDetails(Connection con, String cardType) throws Exception {

        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query

        try {

            strSqlBasic = "SELECT cd.CARDTYPE,cd.EMPLOYMENTDETAILS,cd.SALARYCONFIRMATION,cd.NICNUMBERFROMEMP,"
                    + "cd.LENGTHOFSERVICE,cd.EMPLOYMENTSTATUS,cd.FULLNAME,cd.NICNUMBER,cd.HOMETELEPHONENO,"
                    + "cd.MAILINGADDRESS,cd.REFERENCEDETAILS,cd.ADDRESSFROMRESI,cd.MOBILENO,cd.OWNERSHIP,cd.PLACEOFWORK,"
                    + "cd.DETAILSOFREFERENCE,cd.VEHICLE,cd.PERSONALDETAILSATRESIDENT,cd.RELATIONSHIP,cd.RESIDENCE,"
                    + "cd.WORKPLACE,cd.PERSONALDETAILSATREFREE,cd.ADDRESSFROMREF,cd.DOCIDENTIFICATION,cd.DOCSALARYSLIP,"
                    + "cd.DOCCONFIRMATIONOFEMPLOYEE,cd.DOCUTILITYBILL,cd.DOCMARRIAGECERTIFICATE,cd.DOCBIRTHCERTIFICATE"
                    + " FROM BANKAPPLICATIONVERIFY cd WHERE cd.CARDTYPE=? ";

            stmt = con.prepareStatement(strSqlBasic);
            stmt.setString(1, cardType);

            rs = stmt.executeQuery();

            bankapplicationVerifyBean = new CheckedApplicantDetailsBean();

            while (rs.next()) {

                CheckedApplicantDetailsBean resultCustomer = new CheckedApplicantDetailsBean();

                resultCustomer.setCardType(rs.getString("CARDTYPE"));
                resultCustomer.setEmpDetails(rs.getString("EMPLOYMENTDETAILS"));
                resultCustomer.setSalaryConfirm(rs.getString("SALARYCONFIRMATION"));
                resultCustomer.setEmpNic(rs.getString("NICNUMBERFROMEMP"));
                resultCustomer.setServiceLength(rs.getString("LENGTHOFSERVICE"));
                resultCustomer.setEmpStatus(rs.getString("EMPLOYMENTSTATUS"));
                resultCustomer.setFullname(rs.getString("FULLNAME"));
                resultCustomer.setNic(rs.getString("NICNUMBER"));
                resultCustomer.setHomeTelNo(rs.getString("HOMETELEPHONENO"));
                resultCustomer.setMailingAddress(rs.getString("MAILINGADDRESS"));
                resultCustomer.setReferenceDetails(rs.getString("REFERENCEDETAILS"));
                resultCustomer.setAddressFromResidence(rs.getString("ADDRESSFROMRESI"));
                resultCustomer.setMobileNo(rs.getString("MOBILENO"));
                resultCustomer.setOwenership(rs.getString("OWNERSHIP"));
                resultCustomer.setPlaceOfWork(rs.getString("PLACEOFWORK"));
                resultCustomer.setDetailsOfReference(rs.getString("DETAILSOFREFERENCE"));
                resultCustomer.setVehicle(rs.getString("VEHICLE"));
                resultCustomer.setApplicantPersonal(rs.getString("PERSONALDETAILSATRESIDENT"));
                resultCustomer.setRelatioship(rs.getString("RELATIONSHIP"));
                resultCustomer.setResidence(rs.getString("RESIDENCE"));
                resultCustomer.setWorkPlace(rs.getString("WORKPLACE"));
                resultCustomer.setPersonalDetailsReferee(rs.getString("PERSONALDETAILSATREFREE"));
                resultCustomer.setAddressFromReferee(rs.getString("ADDRESSFROMREF"));
                resultCustomer.setDocIdentification(rs.getString("DOCIDENTIFICATION"));
                resultCustomer.setDocSalarySlip1(rs.getString("DOCSALARYSLIP"));
                resultCustomer.setDocConfirmationLetter(rs.getString("DOCCONFIRMATIONOFEMPLOYEE"));
                resultCustomer.setDocUtilityBill(rs.getString("DOCUTILITYBILL"));
                resultCustomer.setDocMarriageCertificate(rs.getString("DOCMARRIAGECERTIFICATE"));
                resultCustomer.setDocBirthCertificate(rs.getString("DOCBIRTHCERTIFICATE"));

                bankapplicationVerifyBean = resultCustomer;

            }

        } catch (SQLException exSql) {
            throw exSql;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }

        return bankapplicationVerifyBean;
    }

    public boolean isExistCardApplication(Connection con, String applicationId) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        ResultSet rs = null;// Holds the Sql query
        try {
            updateStat = con.prepareStatement("SELECT COUNT(*) AS COUNT FROM CARDAPPLICATIONVERIFY WHERE APPLICATIONID=? ");

            updateStat.setString(1, applicationId);

            rs = updateStat.executeQuery();

            while (rs.next()) {
                int count = Integer.parseInt(rs.getString("COUNT"));

                if (count > 0) {
                    success = true;
                }

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    public boolean isRejectedCardApplication(Connection con, String applicationId) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        ResultSet rs = null;// Holds the Sql query
        try {
            updateStat = con.prepareStatement("SELECT APPLICATIONSTATUS FROM CARDAPPLICATIONSTATUS WHERE APPLICATIONID=? ");

            updateStat.setString(1, applicationId);

            rs = updateStat.executeQuery();

            while (rs.next()) {

                String status = rs.getString("APPLICATIONSTATUS");

                if (status.equals(StatusVarList.APP_VERIFY_REJECT)) {
                    success = true;
                }

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    public String isCribFileUpload(Connection con, String applicationId) throws Exception {

        String cribReport = null;
        PreparedStatement updateStat = null;
        ResultSet rs = null;// Holds the Sql query
        try {
            updateStat = con.prepareStatement("SELECT ca.CRIBREPORT FROM CARDAPPLICATION ca WHERE APPLICATIONID=? ");

            updateStat.setString(1, applicationId);

            rs = updateStat.executeQuery();

            while (rs.next()) {

                cribReport = rs.getString("CRIBREPORT");
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return cribReport;
    }

    ////////////////////////////////Corporate Establishment Methods ////////////////////////////////////////////
    public CorporateVerifyBean getAllEstablishmentDetails(Connection CMSCon, String corporateAppId) throws Exception {

        PreparedStatement getEsDtl = null;
        ResultSet rs = null;// Holds the Sql query
        verifyBean = new CorporateVerifyBean();
        try {
            getEsDtl = CMSCon.prepareStatement("SELECT C.APPLICATIONID,C.COMPANYNAME,C.ADDRESS1,C.ADDRESS2,C.ADDRESS3,"
                    + " C.AREA,C.DISTRICT,C.PROVINCE,C.OFFICEPHONE1,C.OFFICEFAX,C.OFFICEEMAIL,C.CONTACTPERSON,C.CONTACTPERSONPOSITION,"
                    + " BRCNUMBER,REGISTEREDDATE,REGISTEREDPLACE,TYPEOFCOMPANY,NUMBEROFEMPLOYEES,CAPITALINVESTED,"
                    + " C.ANNUALTURNOVER,C.ANNUALTURNOVERYEAR,C.AUDITORNAME,C.AUDITORADDRESS1,C.AUDITORADDRESS2,C.AUDITORADDRESS3,"
                    + " C.AUDITORAREA,C.AUDITORDISTRICT,C.AUDITORPROVINCE,C.AUDITOROFFICEPHONE1,C.AUDITOROFFICEPHONE2,"
                    + " C.AUDITORCONTACTPERSONPOSITION,C.AUDITORFAX,C.AUDITOROFFICEMAIL,C.AUDITORCONTACTPERSON,"
                    + " A.DESCRIPTION AS ADESCRIPTION,O.DESCRIPTION AS ODESCRIPTION"
                    + " FROM CARDESTAPPLICATIONDETAILS C,AREA A,OCCUPATIONTYPE O"
                    + " WHERE C.AREA=A.AREACODE AND C.CONTACTPERSONPOSITION = O.OCCUPATIONTYPECODE AND"
                    + " C.AUDITORCONTACTPERSONPOSITION = O.OCCUPATIONTYPECODE AND C.APPLICATIONID = ? ");

            getEsDtl.setString(1, corporateAppId);

            rs = getEsDtl.executeQuery();

            while (rs.next()) {

                verifyBean.setApplicationId(rs.getString("APPLICATIONID"));
                verifyBean.setCompanyName(rs.getString("COMPANYNAME"));
                verifyBean.setAddress1(rs.getString("ADDRESS1"));
                verifyBean.setAddress2(rs.getString("ADDRESS2"));
                verifyBean.setAddress3(rs.getString("ADDRESS3"));
                verifyBean.setArea(rs.getString("AREA"));
                verifyBean.setDistrict(rs.getString("DISTRICT"));
                verifyBean.setProvince(rs.getString("PROVINCE"));
                verifyBean.setOfficePhoneNo1(rs.getString("OFFICEPHONE1"));
                verifyBean.setOfficeFax(rs.getString("OFFICEFAX"));
                verifyBean.setOfficeEmail(rs.getString("OFFICEEMAIL"));
                verifyBean.setContactPerson(rs.getString("CONTACTPERSON"));
                verifyBean.setContactPersonPosition(rs.getString("CONTACTPERSONPOSITION"));
                verifyBean.setBrc(rs.getString("BRCNUMBER"));
                verifyBean.setRegisterDate(rs.getString("REGISTEREDDATE"));
                verifyBean.setRegisterPlace(rs.getString("REGISTEREDPLACE"));
                verifyBean.setCompanyType(rs.getString("TYPEOFCOMPANY"));
                //verifyBean.setNatureOfBusiness(rs.getString(""));
                verifyBean.setNoOfEmployees(rs.getString("NUMBEROFEMPLOYEES"));
                verifyBean.setCapitalInvested(rs.getString("CAPITALINVESTED"));
                verifyBean.setAnnualTurnover(rs.getString("ANNUALTURNOVER"));
                verifyBean.setAnnualTurnoverYear(rs.getString("ANNUALTURNOVERYEAR"));
                verifyBean.setAuditorName(rs.getString("AUDITORNAME"));
                verifyBean.setAuditorAddress1(rs.getString("AUDITORADDRESS1"));
                verifyBean.setAuditorAddress2(rs.getString("AUDITORADDRESS2"));
                verifyBean.setAuditorAddress3(rs.getString("AUDITORADDRESS3"));
                verifyBean.setAuditorArea(rs.getString("AUDITORAREA"));
                verifyBean.setAuditorDistrict(rs.getString("AUDITORDISTRICT"));
                verifyBean.setAuditorProvince(rs.getString("AUDITORPROVINCE"));
                verifyBean.setAuditorOfficePhone1(rs.getString("AUDITOROFFICEPHONE1"));
                verifyBean.setAuditorOfficePhone2(rs.getString("AUDITOROFFICEPHONE2"));
                verifyBean.setAuditorOfficeContactPersonPosition(rs.getString("AUDITORCONTACTPERSONPOSITION"));
                verifyBean.setAuditorOfficeFax(rs.getString("AUDITORFAX"));
                verifyBean.setAuditorOfficeEmail(rs.getString("AUDITOROFFICEMAIL"));
                verifyBean.setAuditorOfficeContactPerson(rs.getString("AUDITORCONTACTPERSON"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                rs.close();;
            }
            getEsDtl.close();
        }
        return verifyBean;
    }

    public List<VerificationPointsBean> getCorporateVerificationPoints(Connection CMSCon, String cardCategoryCode) throws Exception {

        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query

        try {
            if(cardCategoryCode!=null && !cardCategoryCode.trim().isEmpty()){
                strSqlBasic = "SELECT VP.FIELDNAME,VP.DESCRIPTION,VP.ESTABLISHMENTDOCS,VP.STATUS ,VP.CARDCATEGORYCODE "
                    + " FROM VERIFICATIONPOINT VP "
                    + " WHERE VP.CARDCATEGORYCODE=?";  
                stmt = CMSCon.prepareStatement(strSqlBasic);
                stmt.setString(1, cardCategoryCode);
            }else{
                strSqlBasic = "SELECT VP.FIELDNAME,VP.DESCRIPTION,VP.ESTABLISHMENTDOCS,VP.STATUS ,VP.CARDCATEGORYCODE "
                    + " FROM VERIFICATIONPOINT VP";
                stmt = CMSCon.prepareStatement(strSqlBasic);
            }
            

            rs = stmt.executeQuery();

            verificationPointsBeanList = new ArrayList<VerificationPointsBean>();

            while (rs.next()) {

                VerificationPointsBean resultPoints = new VerificationPointsBean();

                resultPoints.setFieldName(rs.getString("FIELDNAME"));
                resultPoints.setDescription(rs.getString("DESCRIPTION"));
                resultPoints.setDocumentType(rs.getString("ESTABLISHMENTDOCS"));
                resultPoints.setStatus(rs.getString("STATUS"));
                resultPoints.setCardCategoryCode(rs.getString("CARDCATEGORYCODE"));
                resultPoints.setIsMandatory(rs.getBoolean("ISMANDATORY"));

                verificationPointsBeanList.add(resultPoints);

            }

        } catch (SQLException exSql) {
            throw exSql;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }

        return verificationPointsBeanList;
    }

    public CheckedEstDetailsBean getPreviousEstCheckedDetails(Connection CMSCon, String applicationId) throws Exception {

        CheckedEstDetailsBean checkedBean = null;
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query

        try {

            stmt = CMSCon.prepareStatement("SELECT APPLICATIONID,COMPANYNAME,BRC,TYPEOFCOMPANY,NUMBEROFEMPLOYEES,"
                    + " CAPITALINVESTED,ANNUALTURNOVER,REGISTEDDATE,AUDITORNAME,AUDITORADDRESS,DOCBRC"
                    + " FROM CARDAPPLICATIONVERIFY WHERE APPLICATIONID = ?");
            stmt.setString(1, applicationId);

            rs = stmt.executeQuery();

            checkedApplicationBean = new CheckedApplicantDetailsBean();

            while (rs.next()) {

                checkedBean = new CheckedEstDetailsBean();

                checkedBean.setApplicationId(rs.getString("APPLICATIONID"));
                checkedBean.setCompanyName(rs.getString("COMPANYNAME"));
                checkedBean.setBrc(rs.getString("BRC"));
                checkedBean.setTypeOfCompany(rs.getString("TYPEOFCOMPANY"));
                checkedBean.setNoOfEmployee(rs.getString("NUMBEROFEMPLOYEES"));
                checkedBean.setCapitalInvested(rs.getString("CAPITALINVESTED"));
                checkedBean.setAnnualTurnOver(rs.getString("ANNUALTURNOVER"));
                checkedBean.setDateOfReg(rs.getString("REGISTEDDATE"));
                checkedBean.setAuditorName(rs.getString("AUDITORNAME"));
                checkedBean.setAuditorAddress(rs.getString("AUDITORADDRESS"));
                checkedBean.setDocBrc(rs.getString("DOCBRC"));

            }

        } catch (SQLException exSql) {
            throw exSql;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }

        return checkedBean;
    }

    public CheckedEstDetailsBean getCorporateBankApplicationVerifyDetails(Connection CMSCon) throws Exception {

        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query

        try {
            stmt = CMSCon.prepareStatement("SELECT COMPANYNAME,BRC,TYPEOFCOMPANY,"
                    + " NUMBEROFEMPLOYEES,CAPITALINVESTED,ANNUALTURNOVER,REGISTEDDATE,"
                    + " AUDITORNAME,AUDITORADDRESS,DOCBRC FROM BANKAPPLICATIONVERIFY"
                    + " WHERE CARDTYPE = ?  ");

            stmt.setString(1, StatusVarList.CARD_CATEGORY_ESTABLISHMENT);
            rs = stmt.executeQuery();
            corporateEstVerifyBean = new CheckedEstDetailsBean();

            while (rs.next()) {

                corporateEstVerifyBean.setCompanyName(rs.getString("COMPANYNAME"));
                corporateEstVerifyBean.setBrc(rs.getString("BRC"));
                corporateEstVerifyBean.setTypeOfCompany(rs.getString("TYPEOFCOMPANY"));
                corporateEstVerifyBean.setNoOfEmployee(rs.getString("NUMBEROFEMPLOYEES"));
                corporateEstVerifyBean.setCapitalInvested(rs.getString("CAPITALINVESTED"));
                corporateEstVerifyBean.setAnnualTurnOver(rs.getString("ANNUALTURNOVER"));
                corporateEstVerifyBean.setDateOfReg(rs.getString("REGISTEDDATE"));
                corporateEstVerifyBean.setAuditorName(rs.getString("AUDITORNAME"));
                corporateEstVerifyBean.setAuditorAddress(rs.getString("AUDITORADDRESS"));
                corporateEstVerifyBean.setDocBrc(rs.getString("DOCBRC"));
            }

        } catch (SQLException exSql) {
            throw exSql;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
                throw e;
            }
        }

        return corporateEstVerifyBean;
    }

    public boolean updateCorporateEstVerifyDetailsOfApplication(Connection CMSCon, CheckedEstDetailsBean checkedBean) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;

        try {
            updateStat = CMSCon.prepareStatement("UPDATE CARDAPPLICATIONVERIFY SET COMPANYNAME= ?,BRC,TYPEOFCOMPANY= ?,"
                    + " NUMBEROFEMPLOYEES= ?,CAPITALINVESTED= ?,ANNUALTURNOVER= ?,REGISTEDDATE= ?,AUDITORNAME= ?,"
                    + " AUDITORADDRESS= ?,DOCBRC= ?"
                    + " WHERE APPLICATIONID = ? ");

            updateStat.setString(1, checkedBean.getCompanyName());
            updateStat.setString(2, checkedBean.getBrc());
            updateStat.setString(3, checkedBean.getTypeOfCompany());
            updateStat.setString(4, checkedBean.getNoOfEmployee());
            updateStat.setString(5, checkedBean.getCapitalInvested());
            updateStat.setString(6, checkedBean.getAnnualTurnOver());
            updateStat.setString(7, checkedBean.getDateOfReg());
            updateStat.setString(8, checkedBean.getAuditorName());
            updateStat.setString(9, checkedBean.getAuditorAddress());
            updateStat.setString(10, checkedBean.getDocBrc());
            updateStat.setString(11, checkedBean.getApplicationId());

            updateStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    public boolean insertCorporateEstVerifyDetailsOfApplication(Connection CMSCon, CheckedEstDetailsBean checkedBean) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = CMSCon.prepareStatement("INSERT INTO CARDAPPLICATIONVERIFY (APPLICATIONID,COMPANYNAME,"
                    + " BRC,TYPEOFCOMPANY,NUMBEROFEMPLOYEES,CAPITALINVESTED,ANNUALTURNOVER,REGISTEDDATE,AUDITORNAME,"
                    + " AUDITORADDRESS,DOCBRC) VALUES (?,?,?,?,?,?,?,?,?,?,?)");

            insertStat.setString(1, checkedBean.getApplicationId());
            insertStat.setString(2, checkedBean.getCompanyName());
            insertStat.setString(3, checkedBean.getBrc());
            insertStat.setString(4, checkedBean.getTypeOfCompany());
            insertStat.setString(5, checkedBean.getNoOfEmployee());
            insertStat.setString(6, checkedBean.getCapitalInvested());
            insertStat.setString(7, checkedBean.getAnnualTurnOver());
            insertStat.setString(8, checkedBean.getDateOfReg());
            insertStat.setString(9, checkedBean.getAuditorName());
            insertStat.setString(10, checkedBean.getAuditorAddress());
            insertStat.setString(11, checkedBean.getDocBrc());

            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public boolean updateCorporateCardApplicationStatus(Connection con, CheckedEstDetailsBean checkedApplicantBean, String applicationStatus) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE CARDAPPLICATIONSTATUS SET APPLICATIONSTATUS=?"
                    + " WHERE APPLICATIONID=? ");

            updateStat.setString(1, applicationStatus);
            updateStat.setString(2, checkedApplicantBean.getApplicationId());

            updateStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    private String stringTodate(String date) throws ParseException {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        Date convertedDate = dateFormat.parse(dateString);

        return (dateFormat.format(convertedDate));

    }

    public ApplicationVerificationBean getApplicantVerificationDetails(Connection con, String applicationId) throws SQLException, Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query

        try {

            strSqlBasic = "SELECT CC.TITLE,CC.FIRSTNAME,CC.LASTNAME,CC.MIDDLENAME,CC.NAMEONCARD,"
                    + "CC.GENDER,CC.DATEOFBIRTH,CA.IDENTIFICATIONTYPE,CA.IDENTIFICATIONNO,"
                    + "CC.NATIONALITY,CC.JOBTITLE,CC.MOTHERSMAIDENNAME,CC.ADDRESS1,CC.ADDRESS2,"
                    + "CC.ADDRESS3,CC.CITY,CC.HOMETELEPHONENO,CC.ACADEMICQUALIFICATION,CC.BLOODGROUP,"
                    + "CC.RELIGION,CC.EMPDURATION,CC.ANNUALSALARY,CC.FIXEDALLOWANCES "
                    + "FROM CARDCOOPRATEAPPLICATIONDETAILS CC,CARDAPPLICATION CA WHERE "
                    + "CC.APPLICATIONID = CA.APPLICATIONID AND CC.APPLICATIONID = ?";

            stmt = con.prepareStatement(strSqlBasic);
            stmt.setString(1, applicationId);

            rs = stmt.executeQuery();

            applicationVerificationBean = new ApplicationVerificationBean();

            while (rs.next()) {

                applicationVerificationBean.setTitle(rs.getString("TITLE"));
                applicationVerificationBean.setName(rs.getString("FIRSTNAME") + " " + rs.getString("MIDDLENAME") + " " + rs.getString("LASTNAME"));
                applicationVerificationBean.setNameOnCard(rs.getString("NAMEONCARD"));
                applicationVerificationBean.setGender(rs.getString("GENDER"));
                applicationVerificationBean.setDateOfBirth(rs.getString("DATEOFBIRTH"));
                applicationVerificationBean.setIdType(rs.getString("IDENTIFICATIONTYPE"));
                applicationVerificationBean.setIdNumber(rs.getString("IDENTIFICATIONNO"));
                applicationVerificationBean.setNationality(rs.getString("NATIONALITY"));
                applicationVerificationBean.setJobTitle(rs.getString("JOBTITLE"));
                applicationVerificationBean.setMothersMaidenName(rs.getString("MOTHERSMAIDENNAME"));
                applicationVerificationBean.setAddress(rs.getString("ADDRESS1") + ", " + rs.getString("ADDRESS2") + ", " + rs.getString("ADDRESS3"));
                applicationVerificationBean.setCity(rs.getString("CITY"));
                applicationVerificationBean.setTelephone(rs.getString("HOMETELEPHONENO"));
                applicationVerificationBean.setEducationLevel(rs.getString("ACADEMICQUALIFICATION"));
                applicationVerificationBean.setBloodGroup(rs.getString("BLOODGROUP"));
                applicationVerificationBean.setReligion(rs.getString("RELIGION"));
                applicationVerificationBean.setEmploymentDuration(rs.getString("EMPDURATION"));
                applicationVerificationBean.setAnnualSalary(rs.getString("ANNUALSALARY"));
                applicationVerificationBean.setFixedAllowances(rs.getString("FIXEDALLOWANCES"));

            }

        } catch (SQLException exSql) {
            throw exSql;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }

        return applicationVerificationBean;
    }
}
