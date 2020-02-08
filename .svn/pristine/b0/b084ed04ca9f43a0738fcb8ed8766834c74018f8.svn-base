/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.persistance;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean;
import com.epic.cms.camm.applicationconfirmation.bean.RejectReasonBean;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationRejectReportBean;
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
public class ApplicationRejectReportPersistance {

    ResultSet rs;
    HashMap<String, String> cardDomain = null;
    HashMap<String, String> bnkBranches = null;
    HashMap<String, String> cdProduct = null;
    SystemUserBean sysUsrBean = null;
    private List<RejectReasonBean> rejectReasons;

    public List<ApplicationRejectReportBean> getApplicationRejectDetails(Connection con, ApplicationRejectReportBean inputBean) throws Exception {
        List<ApplicationRejectReportBean> searchList = new ArrayList<ApplicationRejectReportBean>();
        PreparedStatement ps = null;

        try {
            String defaultQuery = "SELECT CA.IDENTIFICATIONTYPE,CA.IDENTIFICATIONNO,CA.REJECTREASONCODE,ARR.DESCRIPTION AS REJ_DES,CA.BRANCHCODE,CA.PRIORITYLEVELCODE,"
                    + "CA.CREATETIME,CA.CARDAPPLIACTIONDOMAIN,CA.CREDITSCORE,"
                    + "CA.APPLICATIONID,"
                    + "PL.DESCRIPTION AS PRIORITY_DES,CD.DESCRIPTION AS DOMAIN_DES,BB.DESCRPTION AS BRANCH_DES "
                    + "FROM CARDAPPLICATION CA,PRIORITYLEVEL PL,CARDDOMAIN CD,BANKBRANCH BB,APPLICATIONREJECTREASON ARR "
                    + "WHERE CA.PRIORITYLEVELCODE = PL.PRIORITYLEVELCODE "
                    + "AND CA.CARDAPPLIACTIONDOMAIN = CD.DOMAINID "
                    + "AND ARR.REASONCODE = CA.REJECTREASONCODE "
                    + "AND CA.BRANCHCODE = BB.BRANCHCODE "
                    + "AND BB.BANKCODE = (SELECT BANKCODE FROM COMMONPARAMETER) ";

            String condition = "";

            if (!inputBean.getNic().contentEquals("") || inputBean.getNic().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CA.IDENTIFICATIONNO LIKE '%" + inputBean.getNic() + "%' ";
                condition += "AND CA.IDENTIFICATIONTYPE = 'NIC'";
            }
            if (!inputBean.getPassport().contentEquals("") || inputBean.getPassport().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CA.IDENTIFICATIONNO LIKE '%" + inputBean.getPassport() + "%' ";
                condition += "AND CA.IDENTIFICATIONTYPE = 'PAS'";
            }
//            if (!inputBean.getLicence().contentEquals("") || inputBean.getLicence().length() > 0) {
//                if (!condition.equals("")) {
//                    condition += " AND ";
//                }
//                condition += "CA.IDENTIFICATIONNO LIKE '%" + inputBean.getLicence() + "%' ";
//                condition += "AND CA.IDENTIFICATIONTYPE = 'DRL'";
//
//            }
            if (!inputBean.getApplicationId().contentEquals("") || inputBean.getApplicationId().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CA.APPLICATIONID = '" + inputBean.getApplicationId() + "'";
            }
            if (!inputBean.getBranchCode().contentEquals("") || inputBean.getBranchCode().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CA.BRANCHCODE = '" + inputBean.getBranchCode() + "'";
            }
            if (!inputBean.getRejReason().contentEquals("") || inputBean.getRejReason().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CA.REJECTREASONCODE = '" + inputBean.getRejReason() + "'";
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
                defaultQuery += "  AND " + condition;
            } else {
            }

            ps = con.prepareStatement(defaultQuery);
            rs = ps.executeQuery();

            searchList = new ArrayList<ApplicationRejectReportBean>();

            while (rs.next()) {
                ApplicationRejectReportBean resultBean = new ApplicationRejectReportBean();

                resultBean.setBranchCode(rs.getString("BRANCHCODE"));
                resultBean.setBranchName(rs.getString("BRANCH_DES"));
                resultBean.setPriorityLevelCode(rs.getString("PRIORITYLEVELCODE"));
                resultBean.setPriorityLevelDes(rs.getString("PRIORITY_DES"));
                resultBean.setDomainCode(rs.getString("CARDAPPLIACTIONDOMAIN"));
                resultBean.setDomainDes(rs.getString("DOMAIN_DES"));
                resultBean.setIdNo(rs.getString("IDENTIFICATIONNO"));
                resultBean.setIdType(rs.getString("IDENTIFICATIONTYPE"));
                resultBean.setApplicationId(rs.getString("APPLICATIONID"));
                resultBean.setCreditScore(rs.getString("CREDITSCORE"));
                
                resultBean.setRejectDes(rs.getString("REJ_DES"));

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

    public List<RejectReasonBean> getRejectReasonList(Connection con) throws Exception {
        PreparedStatement getAllByCatStat = null;
        rejectReasons = new ArrayList<RejectReasonBean>();
        try {
            getAllByCatStat = con.prepareStatement("SELECT REASONCODE,DESCRIPTION "
                    + " FROM APPLICATIONREJECTREASON");

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                RejectReasonBean bean = new RejectReasonBean();

                bean.setReasonCode(rs.getString("REASONCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                
                rejectReasons.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return rejectReasons;
    }
}
