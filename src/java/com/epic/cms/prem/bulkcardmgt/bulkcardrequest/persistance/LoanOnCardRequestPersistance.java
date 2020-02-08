/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.prem.bulkcardmgt.bulkcardrequest.persistance;

import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.BTPaymentRequestBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.ECMSOnlineTransBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.LoanOnCardPaymentRequestBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sajith_g
 */
public class LoanOnCardRequestPersistance {

    public List<ECMSOnlineTransBean> getOnlineTransReq(Connection con, ECMSOnlineTransBean eCMSOnlineTransBean) throws Exception {
        PreparedStatement getCardStat = null;
        ResultSet rs = null;
        List<ECMSOnlineTransBean> transBeanList = new ArrayList<ECMSOnlineTransBean>();
        ECMSOnlineTransBean onlineTrnReq = null;
        StringBuilder query = new StringBuilder();
        query.append("select eot.txncurrency,eoc.description as currencydes,eoc.exponent,eot.txnid,eot.rrn,eot.txndate,eot.txntime,eot.acceptorname,eot.txnsubtype,eot.txnamount,eos.code,eos.description "
                + "from ecms_online_transaction eot,ecms_online_status eos,ecms_online_currency_code eoc where eot.txncurrency=eoc.nocode and eos.code=eot.status and eot.cardno=");
        query.append(eCMSOnlineTransBean.getCardNumber());
        if (eCMSOnlineTransBean.getToAmount() != null && !eCMSOnlineTransBean.getToAmount().equals("") && eCMSOnlineTransBean.getFromAmount() != null && !eCMSOnlineTransBean.getFromAmount().equals("")) {
            query.append(" and eot.txnamount between ");
            query.append(eCMSOnlineTransBean.getToAmount());
            query.append(" and ");
            query.append(eCMSOnlineTransBean.getFromAmount());
        }
        if (eCMSOnlineTransBean.getFromDate() != null && !eCMSOnlineTransBean.getFromDate().equals("")) {
            query.append(" and eot.txndate= ");
            query.append(eCMSOnlineTransBean.getFromDate().substring(4, 8));
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
                onlineTrnReq.setTxnCurrency(rs.getString("currencydes"));
                onlineTrnReq.setCurrenctexponent(rs.getString("exponent"));
                onlineTrnReq.setTxnCurrencyCode(rs.getString("txncurrency"));
                onlineTrnReq.setFormattedTxnAmount(this.getFormattedCurrency(rs.getString("txnamount"), rs.getString("exponent")));
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

    public String getFormattedCurrency(String txnAmount, String currencyExponent) {
        Double tempVal = Integer.parseInt(txnAmount) / Math.pow(10, Integer.parseInt(currencyExponent));
        return String.format("%.2f", tempVal);
    }

    public int insertLoanOnCardPayReq(LoanOnCardPaymentRequestBean bean, Connection con) throws SQLException, Exception {
        int row = -1;
        Boolean success = false;
        int maxReqID = 0;
        PreparedStatement ps = null;

        try {

            maxReqID = getRequestID(con);

            String query = "INSERT INTO LOANONCARDREQUEST (CARDNUMBER,LOANAMOUNT,REMARKS,STATUS,LASTUPDATEDUSER,PAYMENTPLAN,REQUESTEDUSER,"
                    + "INSTALLMENTAMOUNT,CURRENCYNUMCODE) VALUES(?,?,?,?,?,?,?,?,?)";

            ps = con.prepareStatement(query);

            ps.setString(1, bean.getCardNumber());
            ps.setString(2, bean.getLoanAmount());
            ps.setString(3, bean.getRemarks());
            ps.setString(4, bean.getStatus());
            ps.setString(5, bean.getLastUpdatedUser());
            ps.setString(6, bean.getPaymentPlan());
            ps.setString(7, bean.getRequestedUser());
            ps.setString(8, bean.getInstallmentAmount());
            ps.setInt(9, bean.getTxnCurrencyCode());

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

    public int getRequestID(Connection con) throws Exception {
        PreparedStatement getCardStat = null;
        ResultSet rs = null;
        StringBuilder query = new StringBuilder();
        query.append("SELECT  LOANONCARDREQUEST_SEQ.NEXTVAL FROM DUAL");
        int maxRequestID = 0;

        try {
            getCardStat = con.prepareStatement(query.toString());

            rs = getCardStat.executeQuery();

            while (rs.next()) {

                maxRequestID = rs.getInt("NEXTVAL");

            }

        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getCardStat.close();

        }

        return maxRequestID;
    }

}
