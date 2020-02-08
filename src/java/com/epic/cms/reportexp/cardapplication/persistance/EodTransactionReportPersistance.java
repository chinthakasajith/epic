/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.persistance;

import com.epic.cms.reportexp.cardapplication.bean.EodTransactionReportBean;
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
public class EodTransactionReportPersistance {
           ResultSet rs ;
 
    public Map<String , String> getTotalAmountMap(Connection con , EodTransactionReportBean etrb) throws SQLException {

        Map<String , String> totalCountMap = new HashMap<String , String>();
        Statement st = null;
        
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT COUNT(ET.TRANSACTIONAMOUNT) AS COUNT , SUM(ET.TRANSACTIONAMOUNT) AS TRANSACTIONAMOUNT ");
        sb.append("FROM EOD E , EODTRANSACTION ET ");
        sb.append("WHERE E.EODID=ET.EODID AND E.ENDTIME = TO_DATE('").append(etrb.getEodDate()).append("','yyyy-mm-dd')");
        
        try {
            st = con.createStatement();
            rs = st.executeQuery(sb.toString());

            while (rs.next()) {                
                totalCountMap.put(rs.getString("COUNT") , rs.getString("TRANSACTIONAMOUNT") );
            }
                    
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            st.close();
        }

        return totalCountMap;
    }

    public List<EodTransactionReportBean> getEodTransactionReportDetails(Connection con , EodTransactionReportBean etrb) throws SQLException {

        List<EodTransactionReportBean> eodTransactionReportDetailsList = new ArrayList<EodTransactionReportBean>();
        Statement st = null;
        
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ET.EODTRANSACTIONID , ET.CARDNUMBER , ET.ACCOUNTNO , ET.TOACCOUNTNO , ET.MID , ET.TID , ET.CRDR , TT.DESCRIPTION AS TRANSACTIONTYPES , C.DESCRIPTION AS CURRENCYTYPE , ET.TRANSACTIONAMOUNT ");
        sb.append("FROM EOD E , EODTRANSACTION ET , TRANSACTIONTYPE TT , CURRENCY C ");
        sb.append("WHERE E.EODID=ET.EODID AND ET.TRANSACTIONTYPE=TT.TRANSACTIONCODE AND C.CURRENCYNUMCODE=ET.CURRENCYTYPE AND E.ENDTIME = TO_DATE('").append(etrb.getEodDate()).append("','yyyy-mm-dd') ");
       
        try {
            st = con.createStatement();
            rs = st.executeQuery(sb.toString());

            while (rs.next()) {                
                EodTransactionReportBean eodTransactionReportBean = new EodTransactionReportBean();
                
                eodTransactionReportBean.setTransactionId(Integer.parseInt(rs.getString("EODTRANSACTIONID")));
                eodTransactionReportBean.setCardNumber(rs.getString("CARDNUMBER"));
                eodTransactionReportBean.setFromAccountNumber(rs.getString("ACCOUNTNO"));
                eodTransactionReportBean.setToAccountNumber(rs.getString("TOACCOUNTNO"));
                eodTransactionReportBean.setMid(rs.getString("MID"));
                eodTransactionReportBean.setTid(rs.getString("TID"));
                eodTransactionReportBean.setCrdr(rs.getString("CRDR"));
                eodTransactionReportBean.setTransactionType(rs.getString("TRANSACTIONTYPES"));
                eodTransactionReportBean.setCurrencyType(rs.getString("CURRENCYTYPE"));
                eodTransactionReportBean.setTransactionAmount(Integer.parseInt(rs.getString("TRANSACTIONAMOUNT")));
                
                eodTransactionReportDetailsList.add(eodTransactionReportBean);
            }
                    
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            st.close();
        }

        return eodTransactionReportDetailsList;
    }    
    
}
