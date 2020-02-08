/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.payment.persistance;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.backoffice.payment.bean.PaymentBatchBean;
import com.epic.cms.backoffice.payment.bean.PaymentBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author badrika
 */
public class PaymentPersistance {

    private PaymentBatchBean bean;

    public PaymentBatchBean getLastBatchInfo(Connection con, String ip, String user) throws SQLException, Exception {

        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(" select * from (SELECT BATCHID, STATUS, BATCHCREATEDDATE FROM PAYMENTBATCH WHERE IP=? and CREATEDUSER=? order by CREATEDDATE desc) where rownum < 2 ");

            ps.setString(1, ip);
            ps.setString(2, user);

            rs = ps.executeQuery();

            bean = new PaymentBatchBean();
            PaymentBatchBean beanNew = new PaymentBatchBean();

            while (rs.next()) {

                bean.setBatchId(rs.getString("BATCHID"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setCreatedDate(rs.getString("BATCHCREATEDDATE"));

                beanNew = this.PaymentsOfBatch(con, String.valueOf(rs.getString("BATCHID")));

                bean.setTotalTxnCount(beanNew.getTotalTxnCount());
                bean.setTotalTxnAmount(beanNew.getTotalTxnAmount());

            }

        } catch (SQLException sqex) {
            throw sqex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();

        }

        return bean;
    }
    
     public PaymentBatchBean getLastBatchInfoUser(Connection con,  String ip, String user) throws SQLException, Exception {

        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(" select * from (SELECT BATCHID, STATUS, BATCHCREATEDDATE FROM PAYMENTBATCH WHERE IP<>? and CREATEDUSER=? order by CREATEDDATE desc) where rownum < 2 ");
         
            ps.setString(1, ip);
            ps.setString(2, user);

            rs = ps.executeQuery();

            bean = new PaymentBatchBean();
            PaymentBatchBean beanNew = new PaymentBatchBean();

            while (rs.next()) {

                bean.setBatchId(rs.getString("BATCHID"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setCreatedDate(rs.getString("BATCHCREATEDDATE"));

                beanNew = this.PaymentsOfBatch(con, String.valueOf(rs.getString("BATCHID")));

                bean.setTotalTxnCount(beanNew.getTotalTxnCount());
                bean.setTotalTxnAmount(beanNew.getTotalTxnAmount());

            }

        } catch (SQLException sqex) {
            throw sqex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();

        }

        return bean;
    }
     
      public PaymentBatchBean getLastBatchInfoIp(Connection con, String ip, String user) throws SQLException, Exception {

        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(" select * from (SELECT BATCHID, STATUS, BATCHCREATEDDATE FROM PAYMENTBATCH WHERE IP=? and CREATEDUSER<>? order by CREATEDDATE desc) where rownum < 2 ");

            ps.setString(1, ip);            
            ps.setString(2, user);

            rs = ps.executeQuery();

            bean = new PaymentBatchBean();
            PaymentBatchBean beanNew = new PaymentBatchBean();

            while (rs.next()) {

                bean.setBatchId(rs.getString("BATCHID"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setCreatedDate(rs.getString("BATCHCREATEDDATE"));

                beanNew = this.PaymentsOfBatch(con, String.valueOf(rs.getString("BATCHID")));

                bean.setTotalTxnCount(beanNew.getTotalTxnCount());
                bean.setTotalTxnAmount(beanNew.getTotalTxnAmount());

            }

        } catch (SQLException sqex) {
            throw sqex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();

        }

        return bean;
    }

    public int insertNewBatch(Connection con, int batId, String ip, String user) throws Exception, SQLException {
        int i = -1;
        PreparedStatement ps = null;

        try {

            String query = "INSERT INTO PAYMENTBATCH (BATCHID, BATCHCREATEDDATE, CREATEDUSER, IP, STATUS, BRANCH, CREATEDDATE, LASTUPDATEDUSER, "
                    + "LASTUPDATEDTIME, BATCHEXPIRYTIME ) "
                    + "VALUES(?, SYSDATE, ?, ?, ?, ?, SYSDATE, ?, SYSDATE, TO_DATE(?,'yyyy-MM-dd-HH24:mi:ss'))";

            ps = con.prepareStatement(query);

            ps.setInt(1, batId);
            ps.setString(2, user);
            ps.setString(3, ip);
            ps.setString(4, StatusVarList.BATCH_OPEN);
            ps.setString(5, this.getBranchFromUser(user, con)[0]);
            ps.setString(6, user);
            
            Date exp = this.getExpiryTime(con);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
            String expstr = df.format(exp);
            
            
            ps.setString(7, expstr); //this.getExpiryTime(con)
            


            i = ps.executeUpdate();

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
        return i;
    }
    
    private Date getExpiryTime(Connection con) throws Exception, SQLException{
    
        int timeout = -1;
        Date exptime = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = con.prepareStatement("SELECT PAYMENTBATCHTIMEOUT FROM COMMONPARAMETER");
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                timeout = rs.getShort("PAYMENTBATCHTIMEOUT");
            }
            
            //get date and do the rest
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
            Date today = new Date();
            Date time = new Date(today.getTime()); 
            
            String timeStr = df.format(time);
            
            String hour = timeStr.substring(11, 13);
            
            int newhour = Integer.parseInt(hour) + timeout;
            
            String newtime = timeStr.substring(0, 11) + String.valueOf(newhour) + timeStr.substring(13);
            
            exptime = df.parse(newtime);
            
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
        
        return exptime;
    
    }

    //
    private String[] getBranchFromUser(String user, Connection con) throws Exception {
        String[] branch = new String[2];
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT SU.BRANCHNAME, SU.BANKNAME, B.BANKCODE FROM SYSTEMUSER SU, BANK B "
                    + "WHERE USERNAME=? AND SU.BANKNAME = B.BANKNAME ");

            ps.setString(1, user);

            rs = ps.executeQuery();

            if (rs.next()) {

                branch[0] = rs.getString("BRANCHNAME");
                branch[1] = rs.getString("BANKCODE");

            }

        } catch (SQLException sqex) {
            throw sqex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            try {
                ps.close();
            } catch (Exception e) {
            }
        }

        return branch;
    }
     private String getBankName(String bankcode, Connection con) throws Exception {
        String bankname = "";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT BANKNAME from BANK where BANKCODE = ? ");

            ps.setString(1, bankcode);

            rs = ps.executeQuery();

            if (rs.next()) {

                bankname = rs.getString("BANKNAME");

            }

        } catch (SQLException sqex) {
            throw sqex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            try {
                ps.close();
            } catch (Exception e) {
            }
        }

        return bankname;
    }

    public List<CurrencyBean> getCurrencyDetails(Connection con) throws Exception {
        PreparedStatement getAllByCatStat = null;
        List<CurrencyBean> currencyDetails = new ArrayList<CurrencyBean>();

        ResultSet rs = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT C.CURRENCYNUMCODE,C.DESCRIPTION FROM CURRENCY C");

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                CurrencyBean currency = new CurrencyBean();

                currency.setCurrencyCode(rs.getString("CURRENCYNUMCODE"));
                currency.setCurrencyDes(rs.getString("DESCRIPTION"));

                currencyDetails.add(currency);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return currencyDetails;
    }

    public HashMap<String, String> getAllBankList(Connection con) throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        HashMap<String, String> banklist = new HashMap<String, String>();

        try {

            String query = "SELECT BANKCODE, BANKNAME FROM BANK";
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();



            while (rs.next()) {
                banklist.put(rs.getString("BANKCODE"), rs.getString("BANKNAME"));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }
        return banklist;
    }
   
    public int insertPayment(Connection con, PaymentBean bean) throws Exception, SQLException {
        int i = -1;
        PreparedStatement ps = null;

        String bankandbranch[] = new String[2];

        bankandbranch = this.getBranchFromUser(bean.getLastUpdatedUser(), con);

        try {

            String query = "INSERT INTO PAYMENT (BATCHID, CARDNUMBER, PAYMENTDATE, PAYMENTTYPE, CHEQUENUMBER, CURRENCYTYPE, AMOUNT, CREATEDDATE, "
                    + "LASTUPDATEDDATE, LASTUPDATEDUSER, PAYMENTSTATUS, CARDHOLDERNAME, CHEQUEBANK, BANK, BRANCH, REMARKS, CREATEDUSER, STATUS, CHEQUESTATUS  ) "
                    + "VALUES(?,?,SYSDATE,?,?,?,?,SYSDATE,SYSDATE,?,?,?,?,?,?,?,?,?,?)";

            ps = con.prepareStatement(query);



            ps.setString(1, bean.getBatchId());
            ps.setString(2, bean.getCardNumber());

            ps.setString(3, bean.getPaymentType());
            ps.setString(4, bean.getChequeNumber());
            ps.setString(5, bean.getCurencyType());
            ps.setString(6, bean.getAmount());
            ps.setString(7, bean.getLastUpdatedUser());
            ps.setString(8, StatusVarList.PAY_INIT);
            ps.setString(9, bean.getCardHolderName());
            ps.setString(10, bean.getChequeBank());
            ps.setString(11, bankandbranch[1]);
            ps.setString(12, bankandbranch[0]);
            ps.setString(13, bean.getRemark());
            ps.setString(14, bean.getLastUpdatedUser());
            ps.setString(15, "EPEN");

            if (bean.getPaymentType().equals("CHEQUE")) {
                ps.setString(16, "CQIN");
            } else {
                ps.setString(16, null);
            }

            i = ps.executeUpdate();

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
        return i;
    }

    public int closeBatch(Connection con, String id, String user, PaymentBatchBean batBean) throws Exception, SQLException {
        int i = -1;
        PreparedStatement ps = null;

        try {

            String query = " UPDATE PAYMENTBATCH SET STATUS=?, CLOSEDUSER=?, LASTUPDATEDUSER=?, TOTALTXNCOUNT=?, TOTALTXNAMOUNT=?, BATCHCLOSEDDATE=SYSDATE WHERE BATCHID=? ";

            ps = con.prepareStatement(query);

            ps.setString(1, StatusVarList.BATCH_CLOSED);
            ps.setString(2, user);
            ps.setString(3, user);
            ps.setString(4, batBean.getTotalTxnCount());
            ps.setString(5, batBean.getTotalTxnAmount());
            ps.setString(6, id);


            i = ps.executeUpdate();
            
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
        return i;
    }
    
     public int closePayments(Connection con, String id) throws Exception, SQLException {
        int i = -1;        
        PreparedStatement ps = null;
        
        try {

            String query = " UPDATE PAYMENT SET PAYMENTSTATUS=? WHERE BATCHID=? AND PAYMENTSTATUS = ? ";

            ps = con.prepareStatement(query);

            ps.setString(1, StatusVarList.PAY_COMP);            
            ps.setString(2, id);
            ps.setString(3, StatusVarList.PAY_INIT);

            i = ps.executeUpdate();
            
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
        return i;
    }
    
      public int voidPayment(Connection con, String id, String user ) throws Exception, SQLException {
        int i = -1;
        PreparedStatement ps = null;
       

        try {

            String query = " UPDATE PAYMENT SET PAYMENTSTATUS=?, LASTUPDATEDUSER=?  WHERE TRANSACTIONID=? ";

            ps = con.prepareStatement(query);

            ps.setString(1, StatusVarList.PAY_VOID);            
            ps.setString(2, user);//user
            ps.setString(3, id);

            i = ps.executeUpdate();

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
        return i;
    }

    public int getPaymentId(Connection con) throws SQLException, Exception {

        int num = -1;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT MAX(TRANSACTIONID) FROM PAYMENT";

            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            while (rs.next()) {
                num = rs.getInt("MAX(TRANSACTIONID)");
            }

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
        return num + 1;
    }

    public int getMaxBatchId(Connection con) throws SQLException, Exception {

        int num = -1;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT MAX(BATCHID) FROM PAYMENTBATCH";

            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            while (rs.next()) {
                num = rs.getInt("MAX(BATCHID)");
            }

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
        return num;
    }

    public List<PaymentBatchBean> getAllBatchDetailsForToday(Connection con, String ip) throws Exception {

        List<PaymentBatchBean> list = new ArrayList<PaymentBatchBean>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "SELECT BATCHID, BATCHCREATEDDATE, TOTALTXNCOUNT, TOTALTXNAMOUNT "
                    + "FROM PAYMENTBATCH "
                    + "WHERE IP = ? AND BATCHCREATEDDATE > SYSDATE - 1 "; //AND BATCHCREATEDDATE = ? 

            ps = con.prepareStatement(query);
            ps.setString(1, ip);

            // ps.setDate(1, gatDate(con));

            rs = ps.executeQuery();

            while (rs.next()) {
                bean = new PaymentBatchBean();
                bean.setBatchId(rs.getString("BATCHID"));
                bean.setBatchCreatedDate(rs.getString("BATCHCREATEDDATE"));
                bean.setTotalTxnCount(rs.getString("TOTALTXNCOUNT"));
                bean.setTotalTxnAmount(rs.getString("TOTALTXNAMOUNT"));

                list.add(bean);
            }

            return list;

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

    public Date gatDate(Connection con) throws SQLException, Exception {

        Date today = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "select SYSDATE FROM DUAL";

            ps = con.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                today = rs.getDate("SYSDATE");
            }

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
        return today;
    }
    
       public String gatbatchExptime(Connection con, String batId) throws SQLException, Exception {

      //  Date exptime = null;
      //  Date expnew2 = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String expt="";
        try {
            String query = "select BATCHEXPIRYTIME FROM PAYMENTBATCH where BATCHID = ? ";

            ps = con.prepareStatement(query);
            ps.setString(1, batId);

            rs = ps.executeQuery();

            while (rs.next()) {
                //exptime = rs.getDate("BATCHEXPIRYTIME");
                expt = rs.getString("BATCHEXPIRYTIME");
            }
            
          //  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
          //  String expnew = df.format(exptime);
          //  expnew2 = df.parse(expt);
            

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
        return expt;
    }
       
        public Double[] gatMaxPaymentAmount(Connection con) throws SQLException, Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;        
        Double maxPayAmount[] = new Double[2];
        
        try {
            String query = "select MAXPERPAYMENTAMOUNT, MAXPERBATCHAMOUNT FROM COMMONPARAMETER ";

            ps = con.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                maxPayAmount[0] = Double.parseDouble(rs.getString("MAXPERPAYMENTAMOUNT"));
                maxPayAmount[1] = Double.parseDouble(rs.getString("MAXPERBATCHAMOUNT"));
            }            

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
        return maxPayAmount;
    }

    public List<PaymentBean> getAllPaymentDetails(Connection con, String batid) throws Exception {

        List<PaymentBean> list = new ArrayList<PaymentBean>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            
            String query = "SELECT P.TRANSACTIONID, P.BATCHID, P.CARDHOLDERNAME, P.CARDNUMBER, P.PAYMENTTYPE, P.CHEQUENUMBER, P.CHEQUEBANK, "
                    + "P.PAYMENTDATE, P.CURRENCYTYPE, P.AMOUNT, P.STATUS, P.PAYMENTSTATUS, C.DESCRIPTION as cdes, ST1.DESCRIPTION as sdes, ST2.DESCRIPTION as psdes "
                    + "FROM PAYMENT P, CURRENCY C, STATUS ST1, STATUS ST2 "
                    + "WHERE BATCHID = ? AND PAYMENTSTATUS = ? AND P.CURRENCYTYPE = CURRENCYNUMCODE AND "
                    + "P.STATUS = ST1.STATUSCODE AND P.PAYMENTSTATUS = ST2.STATUSCODE";

            ps = con.prepareStatement(query);
            ps.setString(1, batid);
            ps.setString(2, StatusVarList.PAY_INIT);

            rs = ps.executeQuery();

            while (rs.next()) {
                PaymentBean bean2 = new PaymentBean();
                bean2.setTxnId(rs.getString("TRANSACTIONID"));
                bean2.setBatchId(rs.getString("BATCHID"));
                bean2.setCardHolderName(rs.getString("CARDHOLDERNAME"));
                bean2.setCardNumber(rs.getString("CARDNUMBER"));
                bean2.setPaymentType(rs.getString("PAYMENTTYPE"));
                bean2.setChequeNumber(rs.getString("CHEQUENUMBER"));
                
                if(rs.getString("CHEQUENUMBER") != null){
                    bean2.setChequeBank(rs.getString("CHEQUEBANK"));
                    String bankname = this.getBankName(rs.getString("CHEQUEBANK"), con);
                    bean2.setChequeBankName(bankname);
                }
                                
                bean2.setPaymentDate(rs.getString("PAYMENTDATE"));
                bean2.setCurencyType(rs.getString("CURRENCYTYPE"));
                bean2.setCurencyTypeDes(rs.getString("cdes"));
                bean2.setAmount(rs.getString("AMOUNT"));
                bean2.setStatus(rs.getString("STATUS"));
                bean2.setStatusDes(rs.getString("sdes"));
                bean2.setPayStatus(rs.getString("PAYMENTSTATUS"));
                bean2.setPayStatusDes(rs.getString("psdes"));


                list.add(bean2);
            }

            return list;

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

    public PaymentBatchBean PaymentsOfBatch(Connection con, String batchid) throws Exception {

        //List<PaymentBatchBean> list = new ArrayList<PaymentBatchBean>();
        PaymentBatchBean bean2 = new PaymentBatchBean();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "SELECT COUNT(AMOUNT), SUM(AMOUNT) FROM PAYMENT WHERE BATCHID=? AND PAYMENTSTATUS = ? ";

            ps = con.prepareStatement(query);
            ps.setString(1, batchid);
            ps.setString(2, StatusVarList.PAY_INIT);

            rs = ps.executeQuery();

            while (rs.next()) {

                bean2.setTotalTxnCount(rs.getString("COUNT(AMOUNT)"));
                if (rs.getString("SUM(AMOUNT)") == null) {
                    bean2.setTotalTxnAmount("0");
                } else {
                    bean2.setTotalTxnAmount(rs.getString("SUM(AMOUNT)"));
                }



                // list.add(bean2);
            }

            return bean2;

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

      public String dualAuthUserRole(Connection con, String user) throws Exception {
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        String dualAuth="";

        try {
            String query = "SELECT DUALAUTHUSERROLE from SYSTEMUSER where USERNAME = ? ";

            ps = con.prepareStatement(query);
            ps.setString(1, user);

            rs = ps.executeQuery();

            if (rs.next()) {                
               dualAuth = rs.getString("DUALAUTHUSERROLE");
            }

            return dualAuth;

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
    
    
    
    //
}
