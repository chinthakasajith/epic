/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.cooperate.persistance;

import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationAssignBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardCategoryBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.SearchUserAssignAppBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author badrika
 */
public class CorporateCardPersistance {
    private ArrayList<ApplicationAssignBean> searchAssignAppList;
    private ResultSet rs;
    private List<CardCategoryBean> cardCategoryLst;
    
    
     public List<CardCategoryBean> getCardCategories(Connection con) throws Exception {
        PreparedStatement ps = null;
        try {
            String query = "SELECT CC.CATEGORYCODE,CC.DESCRIPTION "
                    + " FROM CARDCATEGORY CC WHERE CC.STATUS ='ACT' AND (CC.CATEGORYCODE = 'E' OR CC.CATEGORYCODE = 'C') ";

            ps = con.prepareStatement(query);

            rs = ps.executeQuery();

            cardCategoryLst = new ArrayList<CardCategoryBean>();
            while (rs.next()) {
                CardCategoryBean bean = new CardCategoryBean();
                bean.setCategoryCode(rs.getString("CATEGORYCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                cardCategoryLst.add(bean);
            }

            return cardCategoryLst;
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
    
     public List<ApplicationAssignBean> corparateCardApplicationsSearch(Connection con, SearchUserAssignAppBean searchBean) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query



        try {



            strSqlBasic = " SELECT a.APPLICATIONID,  a.IDENTIFICATIONTYPE ,  a.IDENTIFICATIONNO,  a.PRIORITYLEVELCODE, "
                    + " cc.DESCRIPTION AS CARDCATEGORY,  pl.DESCRIPTION AS PRIORITYNAME,  a.REFERANCIALEMPNO,  a.BRANCHCODE, "
                    + " bk.DESCRPTION AS BRANCHNAME   ,  a.ASSIGNUSER, a.ASSIGNSTATUS,  st.DESCRIPTION , "
                    + " a.LASTUPDATEDUSER ,  a.LASTUPDATEDTIME  ,  a.CREATETIME ,ad.DOCUMENTNAME  "
                    + "FROM CARDAPPLICATION a ,cardapplicationstatus cs, STATUS st ,PRIORITYLEVEL pl  ,BANKBRANCH bk ,CARDCATEGORY cc ,APPLICATIONDOCUMENT ad ";


            String condition = "";


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
            if (!searchBean.getCardcategory().contentEquals("") || searchBean.getCardcategory().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.CARDCATEGORY = '" + searchBean.getCardcategory() + "'";
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

                strSqlBasic += " WHERE a.applicationid = cs.applicationid AND a.IDENTIFICATIONTYPE = ad.DOCUMENTTYPECODE AND a.CARDCATEGORY=ad.CARDCATEGORYCODE AND cs.applicationstatus = st.statuscode AND a.PRIORITYLEVELCODE = pl.PRIORITYLEVELCODE AND a.BRANCHCODE = bk.BRANCHCODE(+) AND (bk.bankcode = (SELECT BANKCODE FROM COMMONPARAMETER) OR a.branchcode is null) AND a.CARDCATEGORY= cc.CATEGORYCODE AND (cs.applicationstatus ='" + StatusVarList.INITIAL_STATUS + "' OR cs.applicationstatus ='" + StatusVarList.APP_PROCESS + "') AND a.CARDAPPLIACTIONDOMAIN='" + StatusVarList.CREDIT + "' AND (a.CARDCATEGORY='" + StatusVarList.CARD_CATEGORY_COPORATE + "' OR a.CARDCATEGORY='" + StatusVarList.CARD_CATEGORY_ESTABLISHMENT + "') AND " + condition + " ORDER BY a.APPLICATIONID";
            } else {
                strSqlBasic += " WHERE a.applicationid = cs.applicationid AND a.IDENTIFICATIONTYPE = ad.DOCUMENTTYPECODE AND a.CARDCATEGORY=ad.CARDCATEGORYCODE AND cs.applicationstatus = st.statuscode AND a.PRIORITYLEVELCODE = pl.PRIORITYLEVELCODE AND a.BRANCHCODE = bk.BRANCHCODE(+) AND (bk.bankcode = (SELECT BANKCODE FROM COMMONPARAMETER) OR a.branchcode is null) AND a.CARDCATEGORY = cc.CATEGORYCODE AND (cs.applicationstatus ='" + StatusVarList.INITIAL_STATUS + "' OR cs.applicationstatus ='" + StatusVarList.APP_PROCESS + "') AND a.CARDAPPLIACTIONDOMAIN='" + StatusVarList.CREDIT + "' AND (a.CARDCATEGORY='" + StatusVarList.CARD_CATEGORY_COPORATE + "' OR a.CARDCATEGORY='" + StatusVarList.CARD_CATEGORY_ESTABLISHMENT + "') ORDER BY a.APPLICATIONID ";
            }


            stmt = con.prepareStatement(strSqlBasic);
            rs = stmt.executeQuery();

            searchAssignAppList = new ArrayList<ApplicationAssignBean>();

            while (rs.next()) {


                ApplicationAssignBean resultAssign = new ApplicationAssignBean();

                resultAssign.setApplicationId(rs.getString("APPLICATIONID"));
                resultAssign.setAssignUser(rs.getString("ASSIGNUSER"));
                resultAssign.setCreatedTime(rs.getTimestamp("CREATETIME"));
                resultAssign.setIdentityNo(rs.getString("IDENTIFICATIONNO"));
                resultAssign.setIdentityType(rs.getString("DOCUMENTNAME"));
                resultAssign.setLastUpdatedTime(rs.getTimestamp("LASTUPDATEDTIME"));
                resultAssign.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                resultAssign.setPriorityLevel(rs.getString("PRIORITYLEVELCODE"));
                resultAssign.setCardCategory(rs.getString("CARDCATEGORY"));
                resultAssign.setPriorityDescription(rs.getString("PRIORITYNAME"));
                resultAssign.setReferralBranchId(rs.getString("BRANCHCODE"));
                resultAssign.setReferralBranchName(rs.getString("BRANCHNAME"));
                resultAssign.setReferralEmpNo(rs.getString("REFERANCIALEMPNO"));
                resultAssign.setAssignStatus(rs.getString("DESCRIPTION"));



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
     
        //convert string to Date type
    private String stringTodate(String date) throws ParseException {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        Date convertedDate = dateFormat.parse(dateString);


        return (dateFormat.format(convertedDate));



    }
    
}
