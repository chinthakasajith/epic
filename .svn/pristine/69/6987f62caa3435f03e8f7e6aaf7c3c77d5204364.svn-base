/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cms.prem.bulkcardmgt.bulkcardrequest.persistance;

import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.BTPaymentRequestBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.ECMSOnlineTransBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.EasyPaymentRequestBean;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sajith_g
 */
public class BTPaymentRequestPersistance {
    public List<ECMSOnlineTransBean> getOnlineTransReq(Connection con, ECMSOnlineTransBean eCMSOnlineTransBean) throws Exception {
        PreparedStatement getCardStat = null;
        ResultSet rs = null;
        List<ECMSOnlineTransBean> transBeanList = new ArrayList<ECMSOnlineTransBean>();
        ECMSOnlineTransBean onlineTrnReq = null;
        StringBuilder query = new StringBuilder();
        query.append("select eot.txnid,eot.rrn,eot.txndate,eot.txntime,eot.acceptorname,eot.txnsubtype,eot.txnamount,eos.code,eos.description "
                + "from ecms_online_transaction eot,ecms_online_status eos where eos.code=eot.status and eot.cardno=");
        query.append(eCMSOnlineTransBean.getCardNumber());
        if (eCMSOnlineTransBean.getToAmount()!=null && !eCMSOnlineTransBean.getToAmount().equals("") && eCMSOnlineTransBean.getFromAmount()!= null && !eCMSOnlineTransBean.getFromAmount().equals("")) {
            query.append(" and eot.txnamount between ");
            query.append(eCMSOnlineTransBean.getToAmount());
            query.append(" and ");
            query.append(eCMSOnlineTransBean.getFromAmount());
        }
        if (eCMSOnlineTransBean.getFromDate()!=null && !eCMSOnlineTransBean.getFromDate().equals("")) {
            query.append(" and eot.txndate= ");
            query.append(eCMSOnlineTransBean.getFromDate().substring(4,8));
            query.append(" and SUBSTR(eot.rrn, 1, 1)= ");
            query.append(eCMSOnlineTransBean.getFromDate().substring(3, 4));
        }

        try {
            getCardStat = con.prepareStatement(query.toString());

            rs = getCardStat.executeQuery();

            while (rs.next()) {

                onlineTrnReq = new ECMSOnlineTransBean();
                onlineTrnReq.setCardNumber(eCMSOnlineTransBean.getCardNumber());
                onlineTrnReq.setTxnDate(rs.getString("txndate"));
                onlineTrnReq.setTxnTime(rs.getString("txntime"));
                onlineTrnReq.setAccepterName(rs.getString("acceptorname"));
                onlineTrnReq.setSubType(rs.getString("txnsubtype"));
                onlineTrnReq.setTxnAmount(rs.getString("txnamount"));
                onlineTrnReq.setStatusCode(rs.getString("code"));
                onlineTrnReq.setStatusDes(rs.getString("description"));
                onlineTrnReq.setTxnId(rs.getString("txnid"));
                onlineTrnReq.setRrn(rs.getString("rrn"));
                transBeanList.add(onlineTrnReq);

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getCardStat.close();

        }

        return transBeanList;
    }
    
    public int insertBTPayReq(BTPaymentRequestBean bean, Connection con) throws SQLException, Exception {
        int row = -1;
        Boolean success = false;
        int maxReqID=0;
        PreparedStatement ps = null;
        String otherBankCardExpiryDate=bean.getOtherbankCardExpiryMonth()+bean.getOtherbankCardExpiryYear();

        try {
            
            maxReqID=getRequestID(con);
            
            String query = "INSERT INTO BALANCETRASFERREQUEST (CARDNUMBER,OTHERBANKCARDNUMBER,OTHERBANKCARDEXPIRY,OTHERBANKNAMEONCARD,DUEDATE,TRANSFERAMOUNT,PAYMENTPLAN,INSTALLMENTAMOUNT,STATUS,LASTUPDATEDTIME,CREATEDTIME,REQUESTEDUSER,LASTUPDATEDUSER,BANKCODE,CURRENCYNUMCODE,REQUESTID"
                    + ") VALUES(?,?,?,?,?,?,?,?,?,SYSDATE,SYSDATE,?,?,?,?,?)";


            ps = con.prepareStatement(query);

            ps.setString(1, bean.getCardNumber());
            ps.setString(2, bean.getOtherbankCardNumber());
            ps.setString(3, otherBankCardExpiryDate);
            ps.setString(4, bean.getOtherbankNameOnCard());
            if (bean.getDueDate() != null && !bean.getDueDate().trim().isEmpty()) {
                ps.setTimestamp(5, new Timestamp(this.stringTodate(bean.getDueDate()).getTime()));
            } else {
                ps.setTimestamp(5, null);
            }
            //ps.setTimestamp(5, new Timestamp(this.stringTodate(bean.getDueDate())).getTime());
            ps.setFloat(6, bean.getTxnAmount());
            ps.setString(7, bean.getPaymentPlan());
            ps.setString(8, bean.getInstallmentAmount());
            ps.setString(9, bean.getStatus());
            ps.setString(10, bean.getRequestedUser());
            ps.setString(11, bean.getLastUpdatedUser());
            ps.setString(12, bean.getBankcode());
            ps.setInt(13, bean.getCurrencyNumCode());
            ps.setInt(14, maxReqID);
            

            row = ps.executeUpdate();

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }

        return maxReqID;
    }
    
    public int getRequestID(Connection con) throws Exception{
        PreparedStatement getCardStat = null;
        ResultSet rs = null;
        StringBuilder query = new StringBuilder();
        query.append("SELECT  BTIP_SEQ.NEXTVAL FROM DUAL");
        int maxRequestID=0;
        

        try {
            getCardStat = con.prepareStatement(query.toString());

            rs = getCardStat.executeQuery();

            while (rs.next()) {
                
                maxRequestID=rs.getInt("NEXTVAL");

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getCardStat.close();

        }

        return maxRequestID;
    }
    
    public java.util.Date stringTodate(String date) throws ParseException {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        java.util.Date convertedDate = dateFormat.parse(dateString);

        return convertedDate;

    }
}
