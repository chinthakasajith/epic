/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.persistance;


import com.epic.cms.reportexp.cardapplication.bean.NumberGenerationReportBean;
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
public class NumberGenerationReportPersistance {
        ResultSet rs ;
  
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

    public Map<String , String> getAllCardTypesMap(Connection con) throws SQLException {

        Map<String , String> cardTypesMap = new HashMap<String, String>();
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
    
    public List<NumberGenerationReportBean> getAllCardProductList(Connection con) throws SQLException{
    
        List<NumberGenerationReportBean> searchBeanList = new ArrayList<NumberGenerationReportBean>();
        Statement st = null;

        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT PRODUCTCODE , CARDTYPE , DESCRIPTION  FROM CARDPRODUCT WHERE STATUS = '" + StatusVarList.ACTIVE_STATUS + "'");

            while (rs.next()) {                
                NumberGenerationReportBean numberGenerationReportBean = new NumberGenerationReportBean();
                numberGenerationReportBean.setCardProduct(rs.getString("PRODUCTCODE"));
                numberGenerationReportBean.setCardType(rs.getString("CARDTYPE"));
                numberGenerationReportBean.setCardProductDesc(rs.getString("DESCRIPTION"));
                
                searchBeanList.add(numberGenerationReportBean);
            }
                    
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            st.close();
        }       
        return searchBeanList ;
    }

    public Map<String , String> getAllBranchListMap(Connection con) throws SQLException {

        Map<String , String> branchListMap = new HashMap<String, String>();
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
    
    public List<String> getUserList(Connection con) throws SQLException{
    
        List<String> searchBeanList = new ArrayList<String>();
        Statement st = null;
        StringBuffer sb = new StringBuffer(); 
        
        sb.append("SELECT DISTINCT SU.USERNAME ");
        sb.append("FROM SYSTEMUSER SU , USERAPPLICATIONSECTION UAS , SECTIONPAGE SP ");
        sb.append("WHERE SU.USERROLECODE=UAS.USERROLECODE AND UAS.SECTIONCODE=SP.SECTIONCODE AND SP.PAGECODE IN ('CPNGDC' , 'CDNGNG') ");
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
        return searchBeanList ;
    }

    public List<NumberGenerationReportBean> getNumberGenerationReportDetails(Connection con, NumberGenerationReportBean searchBean) throws SQLException {

        List<NumberGenerationReportBean> searchBeanList = new ArrayList<NumberGenerationReportBean>();
        Statement st = null;

        try {
            st = con.createStatement();
            StringBuffer sb = new StringBuffer();
            
            sb.append("SELECT C.CARDNUMBER , ST.DESCRIPTION AS STATUSDES ,  C.CARDTYPE , CD.DESCRIPTION AS CARDDOMAINDESC , CP.DESCRIPTION AS CARDPRODUCTDESC , C.EXPIERYDATE , ");
            sb.append("C.APPLICATIONID , C.IDTYPE , C.IDNUMBER ,APD.DOCUMENTNAME AS IDTYPEDES , C.NOGENARATEDUSER , C.CREATEDTIME , CT.DESCRIPTION AS CARDTYPEDESC , BB.DESCRPTION AS BRANCH , PL.DESCRIPTION AS PRIORITYLEVEL ");
            sb.append("FROM BANKBRANCH BB , CARDTYPE CT , STATUS ST , CARDPRODUCT CP , PRIORITYLEVEL PL , CARDDOMAIN CD , APPLICATIONDOCUMENT APD , CARD C ");
            sb.append(", CARDAPPLICATION CA ");
            sb.append("WHERE CA.BRANCHCODE = BB.BRANCHCODE AND C.CARDTYPE=CT.CARDTYPECODE AND ST.STATUSCODE=C.CARDSTATUS ");
            sb.append(" AND CP.PRODUCTCODE = C.CARDPRODUCT AND CD.DOMAINID = C.CARDDOMAIN AND PL.PRIORITYLEVELCODE = C.PRIORITYLEVEL AND APD.DOCUMENTTYPECODE = C.IDTYPE AND C.APPLICATIONID = CA.APPLICATIONID AND APD.CARDCATEGORYCODE=CA.CARDCATEGORY ");

            if(null != searchBean.getNic() && !searchBean.getNic().equals("")){
                sb.append(" AND UPPER(C.IDNUMBER) LIKE '%").append(searchBean.getNic().toUpperCase()).append("%' AND UPPER(C.IDTYPE) = 'NIC'");
            }
//            if(null != searchBean.getDrivingLicence() && !searchBean.getDrivingLicence().equals("")){
//                sb.append(" AND UPPER(C.IDNUMBER) LIKE '%").append(searchBean.getDrivingLicence().toUpperCase()).append("%' AND UPPER(C.IDTYPE) = 'DRL'");
//            }
            if(null != searchBean.getPassport() && !searchBean.getPassport().equals("")){
                sb.append(" AND UPPER(C.IDNUMBER) LIKE '%").append(searchBean.getPassport().toUpperCase()).append("%' AND UPPER(C.IDTYPE) = 'PAS'");
            }
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
                sb.append(" AND C.NOGENARATEDUSER = '").append(searchBean.getUser()).append("'");
            }            
            if(null != searchBean.getFromDate() && !searchBean.getFromDate().equals("")){
                sb.append(" AND TO_DATE('").append(searchBean.getFromDate()).append("','YYYY-MM-DD') <= C.CREATEDTIME");
            }
            if(null != searchBean.getTodate() && !searchBean.getTodate().equals("")){
                sb.append(" AND C.CREATEDTIME <=  TO_DATE('").append(searchBean.getTodate()).append("','YYYY-MM-DD')");
            }

            rs = st.executeQuery(sb.toString());

            while (rs.next()) {                
              NumberGenerationReportBean numberGenerationReportBean = new NumberGenerationReportBean();
              
              numberGenerationReportBean.setApplicationId(rs.getString("APPLICATIONID"));
              numberGenerationReportBean.setCardNumber(rs.getString("CARDNUMBER"));
              numberGenerationReportBean.setCardStatusDes(rs.getString("STATUSDES"));
              numberGenerationReportBean.setCardTypeDes(rs.getString("CARDTYPEDESC"));
              numberGenerationReportBean.setCardDomainDes(rs.getString("CARDDOMAINDESC"));
              numberGenerationReportBean.setCardProductDesc(rs.getString("CARDPRODUCTDESC"));
              numberGenerationReportBean.setExpiryDate(rs.getString("EXPIERYDATE"));
              numberGenerationReportBean.setIdType(rs.getString("IDTYPE"));
              numberGenerationReportBean.setIdNumber(rs.getString("IDNUMBER"));
              numberGenerationReportBean.setUser(rs.getString("NOGENARATEDUSER"));
              numberGenerationReportBean.setGaneratedDate(rs.getString("CREATEDTIME"));
              numberGenerationReportBean.setIdTypeDes(rs.getString("IDTYPEDES"));
              
              
              if(rs.getString("IDTYPE").equals("NIC")){
              numberGenerationReportBean.setNic(rs.getString("IDNUMBER"));    
              }
              if(rs.getString("IDTYPE").equals("DRL")){
              numberGenerationReportBean.setDrivingLicence(rs.getString("IDNUMBER"));    
              }
              if(rs.getString("IDTYPE").equals("PAS")){
              numberGenerationReportBean.setPassport(rs.getString("IDNUMBER"));    
              }
              
              numberGenerationReportBean.setBranchDes(rs.getString("BRANCH"));
              numberGenerationReportBean.setPriorityLevelDes(rs.getString("PRIORITYLEVEL"));
    
              searchBeanList.add(numberGenerationReportBean);
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