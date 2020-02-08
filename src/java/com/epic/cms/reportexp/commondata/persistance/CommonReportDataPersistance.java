/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.commondata.persistance;

import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
public class CommonReportDataPersistance {

    ResultSet rs;

    public HashMap<String, String> getAllPriorityLevelMap(Connection con) throws SQLException, Exception {

        HashMap<String, String> priorityLevelMap = new HashMap<String, String>();
        Statement st = null;

        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT PRIORITYLEVELCODE , DESCRIPTION FROM PRIORITYLEVEL");

            while (rs.next()) {
                priorityLevelMap.put(rs.getString("PRIORITYLEVELCODE"), rs.getString("DESCRIPTION"));
            }

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            st.close();
        }

        return priorityLevelMap;
    }

    public HashMap<String, String> getAllCardTypesMap(Connection con) throws SQLException, Exception {

        HashMap<String, String> cardTypesMap = new HashMap<String, String>();
        Statement st = null;

        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT CARDTYPECODE , DESCRIPTION FROM CARDTYPE WHERE STATUS = '" + StatusVarList.ACTIVE_STATUS + "'");

            while (rs.next()) {
                cardTypesMap.put(rs.getString("CARDTYPECODE"), rs.getString("DESCRIPTION"));
            }

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            st.close();
        }

        return cardTypesMap;
    }

    public HashMap<String, String> getAllCardProductMap(Connection con) throws SQLException, Exception {

        HashMap<String, String> map = new HashMap<String, String>();
        Statement st = null;

        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT PRODUCTCODE , CARDTYPE , DESCRIPTION  FROM CARDPRODUCT WHERE STATUS = '" + StatusVarList.ACTIVE_STATUS + "'");

            while (rs.next()) {
                map.put(rs.getString("PRODUCTCODE"), rs.getString("DESCRIPTION"));
            }
            return map;
        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            st.close();
        }

    }

    public HashMap<String, String> getAllBranchListMap(Connection con) throws SQLException {

        HashMap<String, String> branchListMap = new HashMap<String, String>();
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

    public List<String> getUserList(Connection con) throws SQLException, Exception {

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
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            st.close();
        }
        return searchBeanList;
    }
    
    public HashMap<String, String> getCardDomainMap(Connection con) throws SQLException,Exception {

        ResultSet rs = null;
        PreparedStatement ps = null;
        HashMap<String,String> map = new HashMap<String, String>();

        try {
            ps = con.prepareStatement("SELECT TC.DOMAINID,TC.DESCRIPTION FROM CARDDOMAIN TC");
            rs = ps.executeQuery();
           
            while (rs.next()) {
                map.put(rs.getString("DOMAINID"), rs.getString("DESCRIPTION"));
            }
            return map;
        } catch (SQLException ex) {
            throw ex;            
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();

        }
    }
    
    
}
