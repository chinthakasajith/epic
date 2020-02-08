/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.persistance;

import com.epic.cms.reportexp.cardapplication.bean.ApplicationVerificationSearchBean;
import com.epic.cms.reportexp.cardapplication.bean.BankBranchBean;
import java.util.List;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author asela
 */
public class ApplicationVerificationReportPersistance {
    
    ResultSet rs ; 

    public List<BankBranchBean> getCommenBankBranchList(Connection con) throws SQLException {

        List<BankBranchBean> bankBranchList = new ArrayList<BankBranchBean>();
        Statement st = null;

        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT B.BRANCHCODE , B.DESCRPTION FROM BANKBRANCH B , COMMONPARAMETER COMMN WHERE B.BANKCODE = COMMN.BANKCODE");

            while (rs.next()) {                
              BankBranchBean bankBranchBean = new BankBranchBean();
              bankBranchBean.setBranchCode(rs.getString("BRANCHCODE"));
              bankBranchBean.setDescription(rs.getString("DESCRPTION"));
              bankBranchList.add(bankBranchBean);
            }
                    
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            st.close();
        }

        return bankBranchList;
    }
    
    public Map<String , String> getAllPriorityLevelMap(Connection con) throws SQLException {

        Map<String , String> priorityLevelMap = new HashMap<String, String>();
        Statement st = null;

        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT PRIORITYLEVELCODE , DESCRIPTION FROM PRIORITYLEVEL");

            while (rs.next()) {                
                priorityLevelMap.put(rs.getString("PRIORITYLEVELCODE"), rs.getString("DESCRIPTION"));
            }
                    
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            st.close();
        }

        return priorityLevelMap;
    }
    
    public Map<String , String> getAllApplicationDomainMap(Connection con) throws SQLException {

        Map<String , String> applicationDomainMap = new HashMap<String, String>();
        Statement st = null;

        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT DOMAINID , DESCRIPTION FROM CARDDOMAIN");

            while (rs.next()) {                
                applicationDomainMap.put(rs.getString("DOMAINID"), rs.getString("DESCRIPTION"));
            }
                    
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            st.close();
        }

        return applicationDomainMap;
    }
    
    public List<ApplicationVerificationSearchBean> getVerificationReportDetails(Connection con, ApplicationVerificationSearchBean searchBean) throws SQLException {

        List<ApplicationVerificationSearchBean> searchBeanList = new ArrayList<ApplicationVerificationSearchBean>();
        Statement st = null;

        try {
            st = con.createStatement();
            StringBuffer sb = new StringBuffer();
            
            sb.append(" SELECT CA.APPLICATIONID , CA.BRANCHCODE , B.DESCRPTION AS BRANCHDESC ,   CA.PRIORITYLEVELCODE , PL.DESCRIPTION AS PRIORITYDESC ,  CA.CARDAPPLIACTIONDOMAIN , CA.IDENTIFICATIONTYPE , CA.IDENTIFICATIONNO , CAS.APPLICATIONSTATUS , ST.DESCRIPTION AS STATUSDESC, AD.DOCUMENTNAME AS IDENTIFICATIONTYPEDES ");
            sb.append(" FROM CARDAPPLICATION CA , CARDAPPLICATIONSTATUS CAS , CARDAPPLICATIONVERIFY CAV , BANKBRANCH B  , PRIORITYLEVEL PL  , CARDDOMAIN CAD  , STATUS ST , APPLICATIONDOCUMENT AD ");
            sb.append(" WHERE CA.APPLICATIONID = CAS.APPLICATIONID AND CAS.APPLICATIONID = CAV.APPLICATIONID ");
            sb.append(" AND B.BRANCHCODE = CA.BRANCHCODE AND  PL.PRIORITYLEVELCODE = CA.PRIORITYLEVELCODE AND CAD.DOMAINID = CA.CARDAPPLIACTIONDOMAIN  AND ST.STATUSCODE = CAS.APPLICATIONSTATUS ");
            sb.append(" AND CA.IDENTIFICATIONTYPE=AD.DOCUMENTTYPECODE ");
            sb.append(" AND CA.CARDCATEGORY=AD.CARDCATEGORYCODE");
            
            
            
            if(null != searchBean.getVerificationStatus() && !searchBean.getVerificationStatus().equals("")){
                if(searchBean.getVerificationStatus().equals("VCOM")){
                sb.append(" AND CAS.APPLICATIONSTATUS IN ('VCOM','PNO','PMNO','PMYS','SCOM','SVCO','PYES') ");
                }else{
                    sb.append(" AND CAS.APPLICATIONSTATUS = '").append(searchBean.getVerificationStatus()).append("'");
                }
            }else{
                sb.append(" AND CAS.APPLICATIONSTATUS IN ('VCOM','PNO','PMNO','PMYS','SCOM','SVCO','PYES','VREJ','VONH') ");
            }
            
            
            
            if(null != searchBean.getNic() && !searchBean.getNic().equals("")){
                sb.append(" AND UPPER(CA.IDENTIFICATIONNO) LIKE '%").append(searchBean.getNic().toUpperCase()).append("%' AND UPPER(CA.IDENTIFICATIONTYPE) = 'NIC'");
            }
//            if(null != searchBean.getDrivingLicence() && !searchBean.getDrivingLicence().equals("")){
//                sb.append(" AND UPPER(CA.IDENTIFICATIONNO) LIKE '%").append(searchBean.getDrivingLicence().toUpperCase()).append("%' AND UPPER(CA.IDENTIFICATIONTYPE) = 'DRL'");
//            }
            if(null != searchBean.getPassport() && !searchBean.getPassport().equals("")){
                sb.append(" AND UPPER(CA.IDENTIFICATIONNO) LIKE '%").append(searchBean.getPassport().toUpperCase()).append("%' AND UPPER(CA.IDENTIFICATIONTYPE) = 'PAS'");
            }
            if(null != searchBean.getApplicationId() && !searchBean.getApplicationId().equals("")){
                sb.append(" AND CA.APPLICATIONID LIKE '%").append(searchBean.getApplicationId()).append("%' ");
                    }  
            if(null != searchBean.getPriorityLevel() && !searchBean.getPriorityLevel().equals("")){
                sb.append(" AND CA.PRIORITYLEVELCODE = '").append(searchBean.getPriorityLevel()).append("' ");
            }
            if(null != searchBean.getDomain() && !searchBean.getDomain().equals("")){
                sb.append(" AND UPPER(CA.CARDAPPLIACTIONDOMAIN) = '").append(searchBean.getDomain().toUpperCase()).append("' ");
            }
            if(null != searchBean.getBranchCode() && !searchBean.getBranchCode().equals("")){
                sb.append(" AND UPPER(CA.BRANCHCODE) = '").append(searchBean.getBranchCode().toUpperCase()).append("' ");
            }
            if(null != searchBean.getBranchCode() && !searchBean.getBranchCode().equals("")){
                sb.append(" AND UPPER(CA.BRANCHCODE) = '").append(searchBean.getBranchCode().toUpperCase()).append("' ");
            }
            if(null != searchBean.getFromDate() && !searchBean.getFromDate().equals("")){
                sb.append(" AND TO_DATE('").append(searchBean.getFromDate()).append("','YYYY-MM-DD') <= CAV.LASTUPDATEDTIME");
            }
            if(null != searchBean.getToDate() && !searchBean.getToDate().equals("")){
                sb.append(" AND CAV.LASTUPDATEDTIME <=  TO_DATE('").append(searchBean.getToDate()).append("','YYYY-MM-DD')");
            }            

            rs = st.executeQuery(sb.toString());

            while (rs.next()) {                
              ApplicationVerificationSearchBean applicationVerificationSearchBean = new ApplicationVerificationSearchBean();
              
              applicationVerificationSearchBean.setApplicationId(rs.getString("APPLICATIONID"));
              applicationVerificationSearchBean.setBranchCode(rs.getString("BRANCHCODE"));
              applicationVerificationSearchBean.setPriorityLevel(rs.getString("PRIORITYLEVELCODE"));
              applicationVerificationSearchBean.setDomain(rs.getString("CARDAPPLIACTIONDOMAIN"));
              applicationVerificationSearchBean.setIdType(rs.getString("IDENTIFICATIONTYPEDES"));
              applicationVerificationSearchBean.setIdNumber(rs.getString("IDENTIFICATIONNO"));
              applicationVerificationSearchBean.setVerificationStatus(rs.getString("APPLICATIONSTATUS"));
              applicationVerificationSearchBean.setBranchDescription(rs.getString("BRANCHDESC"));
              applicationVerificationSearchBean.setPriorityLevelDes(rs.getString("PRIORITYDESC"));
              applicationVerificationSearchBean.setVerificationStatusDes(rs.getString("STATUSDESC"));

              if(rs.getString("IDENTIFICATIONTYPE").equals("NIC")){
              applicationVerificationSearchBean.setNic(rs.getString("IDENTIFICATIONNO"));    
              }
              if(rs.getString("IDENTIFICATIONTYPE").equals("DRL")){
              applicationVerificationSearchBean.setDrivingLicence(rs.getString("IDENTIFICATIONNO"));    
              }
              if(rs.getString("IDENTIFICATIONTYPE").equals("PAS")){
              applicationVerificationSearchBean.setPassport(rs.getString("IDENTIFICATIONNO"));    
              }
              
              
              searchBeanList.add(applicationVerificationSearchBean);
            }
                    
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            st.close();
        }

        return searchBeanList;
    }
    
}
