/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.persistance;

import com.epic.cms.reportexp.cardapplication.bean.ActivatedCardReportBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author asela
 */
public class ActivatedCardReportPersistance {

   ResultSet rs;

    public Map<String, String> getAllPriorityLevelMap(Connection con) throws SQLException {

        Map<String, String> priorityLevelMap = new HashMap<String, String>();
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

    public Map<String, String> getAllCardTypesMap(Connection con) throws SQLException {

        Map<String, String> cardTypesMap = new HashMap<String, String>();
        Statement st = null;

        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT CARDTYPECODE , DESCRIPTION FROM CARDTYPE WHERE STATUS = '" + StatusVarList.ACTIVE_STATUS + "'");

            while (rs.next()) {
                cardTypesMap.put(rs.getString("CARDTYPECODE"), rs.getString("DESCRIPTION"));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            st.close();
        }

        return cardTypesMap;
    }

    public List<ActivatedCardReportBean> getAllCardProductList(Connection con) throws SQLException {

        List<ActivatedCardReportBean> searchBeanList = new ArrayList<ActivatedCardReportBean>();
        Statement st = null;

        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT PRODUCTCODE , CARDTYPE , DESCRIPTION  FROM CARDPRODUCT WHERE STATUS = '" + StatusVarList.ACTIVE_STATUS + "'");

            while (rs.next()) {
                ActivatedCardReportBean activatedCardReportBean = new ActivatedCardReportBean();
                activatedCardReportBean.setCardProduct(rs.getString("PRODUCTCODE"));
                activatedCardReportBean.setCardType(rs.getString("CARDTYPE"));
                activatedCardReportBean.setCardProductDesc(rs.getString("DESCRIPTION"));

                searchBeanList.add(activatedCardReportBean);
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            st.close();
        }
        return searchBeanList;
    }

    public Map<String, String> getAllBranchListMap(Connection con) throws SQLException {

        Map<String, String> branchListMap = new HashMap<String, String>();
        Statement st = null;

        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT B.BRANCHCODE , B.DESCRPTION FROM BANKBRANCH B , COMMONPARAMETER COMMN WHERE B.BANKCODE = COMMN.BANKCODE");

            while (rs.next()) {
                branchListMap.put(rs.getString("BRANCHCODE"), rs.getString("DESCRPTION"));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            st.close();
        }

        return branchListMap;
    }

    public List<String> getUserList(Connection con) throws SQLException {

        List<String> searchBeanList = new ArrayList<String>();
        Statement st = null;
        StringBuffer sb = new StringBuffer(); 
        
        sb.append("SELECT SU.USERNAME ");
        sb.append("FROM SYSTEMUSER SU , USERAPPLICATIONSECTION UAS , SECTIONPAGE SP ");
        sb.append("WHERE SU.USERROLECODE=UAS.USERROLECODE AND UAS.SECTIONCODE=SP.SECTIONCODE AND SP.PAGECODE = 'CCCCSH' ");
        sb.append("ORDER BY SU.USERNAME ASC ");
    
        try {
            st = con.createStatement();
            rs = st.executeQuery(sb.toString());

            while (rs.next()) {
                String username = rs.getString("USERNAME");
                searchBeanList.add(username);
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            st.close();
        }
        return searchBeanList;
    }
    
    public List<ActivatedCardReportBean> getCardActivatedReportDetails(Connection con, ActivatedCardReportBean searchBean) throws SQLException {

        List<ActivatedCardReportBean> searchBeanList = new ArrayList<ActivatedCardReportBean>();
        Statement st = null;

        try {
            st = con.createStatement();
            StringBuffer sb = new StringBuffer();
            
            sb.append("SELECT C.CARDNUMBER , ST.DESCRIPTION AS STATUSDES , C.CARDTYPE , CD.DESCRIPTION AS CARDDOMAINDESC , CP.DESCRIPTION AS CARDPRODUCTDESC , C.EXPIERYDATE , ");
            sb.append("C.APPLICATIONID , C.IDTYPE , APD.DOCUMENTNAME AS IDTYPEDES , C.IDNUMBER , C.NOGENARATEDUSER , C.CREATEDTIME , CT.DESCRIPTION AS CARDTYPEDESC , BB.DESCRPTION AS BRANCH ");
            sb.append("FROM BANKBRANCH BB , CARDTYPE CT , STATUS ST , CARDPRODUCT CP , CARDDOMAIN CD , APPLICATIONDOCUMENT APD , CARDAPPLICATIONHISTORY CAH , CARD C ");
            sb.append(" ,CARDAPPLICATION CA ");
            sb.append(" WHERE CA.BRANCHCODE = BB.BRANCHCODE AND C.CARDTYPE=CT.CARDTYPECODE AND ST.STATUSCODE=C.CARDSTATUS ");
            sb.append(" AND CP.PRODUCTCODE = C.CARDPRODUCT AND CD.DOMAINID = C.CARDDOMAIN AND C.ACTIVATIONDATE IS NOT NULL AND APD.DOCUMENTTYPECODE = C.IDTYPE ");
            sb.append(" AND CAH.APPLICATIONID = C.APPLICATIONID AND C.APPLICATIONID = CA.APPLICATIONID AND CAH.APPLICATIONLEVEL = '" + StatusVarList.CARD_ACTIVE + "'");
            if(null != searchBean.getNic() && !searchBean.getNic().equals("")){
                sb.append(" AND UPPER(C.IDNUMBER) LIKE '%").append(searchBean.getNic().toUpperCase()).append("%' AND UPPER(C.IDTYPE) = 'NIC'");
            }
            if(null != searchBean.getDrivingLicence() && !searchBean.getDrivingLicence().equals("")){
                sb.append(" AND UPPER(C.IDNUMBER) LIKE '%").append(searchBean.getDrivingLicence().toUpperCase()).append("%' AND UPPER(C.IDTYPE) = 'DRL'");
            }
//            if(null != searchBean.getPassport() && !searchBean.getPassport().equals("")){
//                sb.append(" AND UPPER(C.IDNUMBER) LIKE '%").append(searchBean.getPassport().toUpperCase()).append("%' AND UPPER(C.IDTYPE) = 'PAS'");
//            }
            if(null != searchBean.getApplicationId() && !searchBean.getApplicationId().equals("")){
                sb.append(" AND UPPER(C.APPLICATIONID) LIKE '%").append(searchBean.getApplicationId().toUpperCase()).append("%' ");
                    }  
            if(null != searchBean.getPriorityLevel() && !searchBean.getPriorityLevel().equals("")){
                sb.append(" AND C.PRIORITYLEVEL = '").append(searchBean.getPriorityLevel()).append("' ");
            }
            if(null != searchBean.getBranch() && !searchBean.getBranch().equals("")){
                sb.append(" AND UPPER(BB.BRANCHCODE) = '").append(searchBean.getBranch().toUpperCase()).append("' ");
            }
            if (null != searchBean.getCardType() && !searchBean.getCardType().equals("")) {
                sb.append(" AND C.CARDTYPE = '").append(searchBean.getCardType()).append("'");
            }
            if(null != searchBean.getCardProduct() && !searchBean.getCardProduct().equals("")){
                sb.append(" AND C.CARDPRODUCT = '").append(searchBean.getCardProduct()).append("'");
            }
            if(null != searchBean.getCardNumber() && !searchBean.getCardNumber().equals("")){
                sb.append(" AND UPPER(C.CARDNUMBER) LIKE '%").append(searchBean.getCardNumber().toUpperCase()).append("%'");
            }
            if(null != searchBean.getUser() && !searchBean.getUser().equals("")){
                sb.append(" AND CAH.LASTUPDATEDUSER = '").append(searchBean.getUser()).append("'");
            }            
            if(null != searchBean.getFromDate() && !searchBean.getFromDate().equals("")){
                sb.append(" AND TO_DATE('").append(searchBean.getFromDate()).append("','YYYY-MM-DD') <= C.ACTIVATIONDATE");
            }
            if(null != searchBean.getTodate() && !searchBean.getTodate().equals("")){
                sb.append(" AND C.ACTIVATIONDATE <=  TO_DATE('").append(searchBean.getTodate()).append("','YYYY-MM-DD')");
            }
            
            rs = st.executeQuery(sb.toString());

            while (rs.next()) {                
              ActivatedCardReportBean activatedCardReportBean = new ActivatedCardReportBean();
              
              activatedCardReportBean.setApplicationId(rs.getString("APPLICATIONID"));
              activatedCardReportBean.setCardNumber(rs.getString("CARDNUMBER"));
              activatedCardReportBean.setCardStatusDes(rs.getString("STATUSDES"));
              activatedCardReportBean.setCardTypeDes(rs.getString("CARDTYPEDESC"));
              activatedCardReportBean.setCardDomainDes(rs.getString("CARDDOMAINDESC"));
              activatedCardReportBean.setCardProductDesc(rs.getString("CARDPRODUCTDESC"));
              activatedCardReportBean.setExpiryDate(rs.getString("EXPIERYDATE"));
              activatedCardReportBean.setIdType(rs.getString("IDTYPE"));
              activatedCardReportBean.setIdNumber(rs.getString("IDNUMBER"));
              activatedCardReportBean.setUser(rs.getString("NOGENARATEDUSER"));
              activatedCardReportBean.setGaneratedDate(rs.getString("CREATEDTIME"));
              activatedCardReportBean.setIdTypeDes(rs.getString("IDTYPEDES"));
              
              
              if(rs.getString("IDTYPE").equals("NIC")){
              activatedCardReportBean.setNic(rs.getString("IDNUMBER"));    
              }
              if(rs.getString("IDTYPE").equals("DRL")){
              activatedCardReportBean.setDrivingLicence(rs.getString("IDNUMBER"));    
              }
              if(rs.getString("IDTYPE").equals("PAS")){
              activatedCardReportBean.setPassport(rs.getString("IDNUMBER"));    
              }
             
              searchBeanList.add(activatedCardReportBean);
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
