/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.recovery.callcenter.persistance;

import com.epic.cms.admin.controlpanel.securityquesmgt.bean.SecurityQuestionBean;
import com.epic.cms.callcenter.callcentersearch.bean.QuestionAnswerBean;
import com.epic.cms.recovery.callcenter.bean.RecoveryCardDetailsBean;
import com.epic.cms.recovery.callcenter.bean.RecoveryDetailsBean;
import com.epic.cms.recovery.callcenter.bean.RecoveryPaymentDetailsBean;
import com.epic.cms.recovery.callcenter.bean.RecoveryTransactionDetailsBean;
import com.epic.cms.recovery.callcenter.bean.SearchRecoveryBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author chanuka
 */
public class RecoveryCallCenterPersistance {

    private List<RecoveryDetailsBean> recoveryDetailsBeanList = null;
    private List<SecurityQuestionBean> questionBeanList;
    private List<QuestionAnswerBean> answerBeanList;

    public List<RecoveryDetailsBean> getAllRecoveriesList(Connection con, SearchRecoveryBean searchBean) throws Exception {


        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query

        try {



            strSqlBasic = "select cl.accountno,cl.accountstatus,st.description as accountstatusdes,cl.assigneduser,cl.cardnumber,cl.assignstatus,st2.description as assignstatusdes,cl.caseid,cc.description as caseiddes,cl.collectionid,"
                    + "cl.exitdate,cl.indate,cl.queuetype,cq.description as queuetypedes,cl.status,st3.description as statusdes from COLLECTION cl, status st, status st2,status st3, collectorcase cc, collectorqueue cq ";

            String condition = "";

            if (!searchBean.getAccNumber().contentEquals("") || searchBean.getAccNumber().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "cl.accountno = '" + searchBean.getAccNumber() + "'";
            }

            if (!searchBean.getCardNumber().contentEquals("") || searchBean.getCardNumber().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "cl.cardnumber = '" + searchBean.getCardNumber() + "'";
            }

            if (!searchBean.getStatus().contentEquals("") || searchBean.getStatus().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "cl.status = '" + searchBean.getStatus() + "'";
            }



            if (!condition.equals("")) {
                strSqlBasic += " where cl.queuetype = cq.queuecode and cl.caseid = cc.casetypecode and cl.status = st3.statuscode and cl.assignstatus = st2.statuscode and cl.accountstatus = st.statuscode AND " + condition + " ORDER BY cc.SEVERITYVALUE DESC";
            } else {
                strSqlBasic += " where cl.queuetype = cq.queuecode and cl.caseid = cc.casetypecode and cl.status = st3.statuscode and cl.assignstatus = st2.statuscode and cl.accountstatus = st.statuscode ORDER BY cc.SEVERITYVALUE DESC";
            }


            stmt = con.prepareStatement(strSqlBasic);
            rs = stmt.executeQuery();

            recoveryDetailsBeanList = new ArrayList<RecoveryDetailsBean>();

            while (rs.next()) {


                RecoveryDetailsBean resultAssign = new RecoveryDetailsBean();

                resultAssign.setAccNumber(rs.getString("accountno"));
                resultAssign.setAccStatus(rs.getString("accountstatus"));
                resultAssign.setAccStatusDes(rs.getString("accountstatusdes"));
                resultAssign.setAssignStatus(rs.getString("assignstatus"));
                resultAssign.setAssignStatusDes(rs.getString("assignstatusdes"));
                resultAssign.setAssignUser(rs.getString("assigneduser"));
                resultAssign.setCardNumber(rs.getString("cardnumber"));
                resultAssign.setCaseDes(rs.getString("caseiddes"));
                resultAssign.setCaseId(rs.getString("caseid"));
                resultAssign.setCollectionId(rs.getString("collectionid"));
                resultAssign.setExitDate(rs.getTimestamp("exitdate"));
                resultAssign.setInDate(rs.getTimestamp("indate"));
                resultAssign.setQueueDes(rs.getString("queuetypedes"));
                resultAssign.setQueueId(rs.getString("queuetype"));
                resultAssign.setStatus(rs.getString("status"));
                resultAssign.setStatusDes(rs.getString("statusdes"));


                recoveryDetailsBeanList.add(resultAssign);

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

        return recoveryDetailsBeanList;
    }

    public RecoveryCardDetailsBean getRecoveryCardDetailsBean(Connection con, String collectionId) throws SQLException {
        PreparedStatement ps = null;
        RecoveryCardDetailsBean recoveryBean = new RecoveryCardDetailsBean();
        ResultSet rs = null;// Holds the Sql query

        try {

            ps = con.prepareStatement("select cd.cardnumber,cd.cardtype,ct.description as cardtypedes,cd.nameoncard,cd.creditlimit,"
                    + "cd.cardstatus,st.description as cardstatusdes, cd.idnumber,cp.mobileno,"
                    + "cp.hometelephoneno,cp.address1,cp.address2,cp.address3, ca.accountno from CARD cd,CARDACCOUNT ca,"
                    + "CARDAPPLICATIONPERSONALDETAILS cp,CARDTYPE ct,STATUS st "
                    + "where cd.cardnumber = ca.cardnumber and cd.applicationid = cp.applicationid and cd.cardtype=ct.cardtypecode and cd.cardstatus = st.statuscode "
                    + "and cd.cardnumber=(select cl.cardnumber from collection cl where cl.collectionid=?)");

            ps.setString(1, collectionId);
            rs = ps.executeQuery();

            while (rs.next()) {

                recoveryBean.setCollectionId(collectionId);
                recoveryBean.setAccNumber(rs.getString("accountno"));
                recoveryBean.setAddress(rs.getString("address1").concat(",").concat(rs.getString("address2").concat(",").concat(rs.getString("address3"))));
                recoveryBean.setCardHolderName(rs.getString("nameoncard"));
                recoveryBean.setCardLimit(rs.getString("creditlimit"));
                recoveryBean.setCardNumber(rs.getString("cardnumber"));
                recoveryBean.setCardType(rs.getString("cardtype"));
                recoveryBean.setCardTypeDes(rs.getString("cardtypedes"));
                recoveryBean.setContactNumber(rs.getString("hometelephoneno"));
                recoveryBean.setId(rs.getString("idnumber"));
                recoveryBean.setMobileNo(rs.getString("mobileno"));
                recoveryBean.setCardStatus(rs.getString("cardstatus"));
                recoveryBean.setCardStatusDes(rs.getString("cardstatusdes"));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }

        return recoveryBean;
    }

    public List<RecoveryTransactionDetailsBean> getRecoveryTxnDetails(Connection con, String condition, int start, int end) throws Exception {

        ResultSet rs = null;
        PreparedStatement getAllMasterCard = null;
        String strSqlBasic = null;
        List<RecoveryTransactionDetailsBean> recoveryTxnBeanList = null;
        try {

            strSqlBasic = " SELECT * FROM ( SELECT * FROM ( select txn.cardno,txn.status, st.description as statusdes,txn.txnamount,txn.txncurrency, "
                    + "cr.description as txncurrencydes ,txn.txntypecode, tt.descryption as txntypecodedes,txn.txndate, "
                    + "txn.mid,ROWNUM r from ECMS_ONLINE_TRANSACTION txn,ECMS_ONLINE_CURRENCY_CODE cr,ECMS_ONLINE_TXNTYPE tt,"
                    + "ECMS_ONLINE_STATUS st where cr.nocode = txn.txncurrency and tt.typecode = txn.txntypecode "
                    + "and st.code = txn.status AND " + condition + "  )"
                    + " WHERE r <=  " + end + "  ) WHERE r > " + start;




            getAllMasterCard = con.prepareStatement(strSqlBasic);


            rs = getAllMasterCard.executeQuery();
            recoveryTxnBeanList = new ArrayList<RecoveryTransactionDetailsBean>();

            while (rs.next()) {


                RecoveryTransactionDetailsBean recoveryTxnBean = new RecoveryTransactionDetailsBean();

                recoveryTxnBean.setCardNumber((rs.getString("cardno") == null) ? "--" : rs.getString("cardno"));
                recoveryTxnBean.setTxnCurrencyDes((rs.getString("txncurrencydes") == null) ? "--" : rs.getString("txncurrencydes"));
                recoveryTxnBean.setTxnType((rs.getString("txntypecode") == null) ? "--" : rs.getString("txntypecode"));
                recoveryTxnBean.setTxnTypeDes((rs.getString("txntypecodedes") == null) ? "--" : rs.getString("txntypecodedes"));
                recoveryTxnBean.setTxnAmount((rs.getString("txnamount") == null) ? "--" : rs.getString("txnamount"));
                recoveryTxnBean.setTxncurrency((rs.getString("txncurrency") == null) ? "--" : rs.getString("txncurrency"));
                recoveryTxnBean.setTxnDate((rs.getString("txndate") == null) ? "--" : rs.getString("txndate"));
                recoveryTxnBean.setmId((rs.getString("mid") == null) ? "--" : rs.getString("mid"));
                recoveryTxnBean.setTxnStatus((rs.getString("status") == null) ? "--" : rs.getString("status"));
                recoveryTxnBean.setTxnStatusDes((rs.getString("statusdes") == null) ? "--" : rs.getString("statusdes"));


                recoveryTxnBeanList.add(recoveryTxnBean);

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllMasterCard.close();

        }

        return recoveryTxnBeanList;
    }

    public int getCountTxn(Connection con, String condition) throws Exception {

        int count = 0;
        ResultSet rs = null;
        PreparedStatement getAllMasterCard = null;
        String strSqlBasic = null;
        try {


            strSqlBasic = "select count(*) as Totaltxn from ECMS_ONLINE_TRANSACTION txn,ECMS_ONLINE_CURRENCY_CODE cr,ECMS_ONLINE_TXNTYPE tt,"
                    + "ECMS_ONLINE_STATUS st where cr.nocode = txn.txncurrency and tt.typecode = txn.txntypecode "
                    + "and st.code = txn.status AND " + condition;


            getAllMasterCard = con.prepareStatement(strSqlBasic);


            rs = getAllMasterCard.executeQuery();


            while (rs.next()) {

                count = rs.getInt("Totaltxn");

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllMasterCard.close();

        }


        return count;
    }

    public int getCountPayments(Connection con, String condition) throws Exception {

        int count = 0;
        ResultSet rs = null;
        PreparedStatement getAllMasterCard = null;
        String strSqlBasic = null;
        try {


            strSqlBasic = " select count(*) as totalPayments from payment pm,currency cr, paymentmode pt, status st "
                    + "where cr.currencynumcode = pm.currencytype and pt.paymentmodecode = pm.paymenttype and "
                    + "st.statuscode = pm.paymentstatus and " + condition;


            getAllMasterCard = con.prepareStatement(strSqlBasic);


            rs = getAllMasterCard.executeQuery();


            while (rs.next()) {

                count = rs.getInt("totalPayments");

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllMasterCard.close();

        }


        return count;
    }

    public List<RecoveryPaymentDetailsBean> getRecoveryPaymentDetails(Connection con, String condition, int start, int end) throws Exception {

        ResultSet rs = null;
        PreparedStatement getAllMasterCard = null;
        String strSqlBasic = null;
        List<RecoveryPaymentDetailsBean> recoveryPaymentBeanList = null;
        try {

            strSqlBasic = " SELECT * FROM ( SELECT * FROM ( select pm.cardnumber, pm.paymentdate, pm.paymenttype, pt.description as paymenttypedes,"
                    + "pm.currencytype, cr.description as currencytypedes, pm.amount, pm.paymentstatus, st.description as paymentstatusdes,ROWNUM r "
                    + "from payment pm,currency cr, paymentmode pt, status st where cr.currencynumcode = pm.currencytype and "
                    + "pt.paymentmodecode = pm.paymenttype and st.statuscode = pm.paymentstatus and " + condition + "  )"
                    + " WHERE r <=  " + end + "  ) WHERE r > " + start;




            getAllMasterCard = con.prepareStatement(strSqlBasic);


            rs = getAllMasterCard.executeQuery();
            recoveryPaymentBeanList = new ArrayList<RecoveryPaymentDetailsBean>();

            while (rs.next()) {


                RecoveryPaymentDetailsBean recoveryPaymentBean = new RecoveryPaymentDetailsBean();

                recoveryPaymentBean.setCardNumber((rs.getString("cardnumber") == null) ? "--" : rs.getString("cardnumber"));
                recoveryPaymentBean.setAmount((rs.getString("amount") == null) ? "--" : rs.getString("amount"));
                recoveryPaymentBean.setCurrencyType((rs.getString("currencytype") == null) ? "--" : rs.getString("currencytype"));
                recoveryPaymentBean.setCurrencyTypeDes((rs.getString("currencytypedes") == null) ? "--" : rs.getString("currencytypedes"));
                recoveryPaymentBean.setPaymentDate((rs.getTimestamp("paymentdate") == null) ? null : rs.getTimestamp("paymentdate"));
                recoveryPaymentBean.setPaymentStatus((rs.getString("paymentstatus") == null) ? "--" : rs.getString("paymentstatus"));
                recoveryPaymentBean.setPaymentStatusDes((rs.getString("paymentstatusdes") == null) ? "--" : rs.getString("paymentstatusdes"));
                recoveryPaymentBean.setPaymentType((rs.getString("paymenttype") == null) ? "--" : rs.getString("paymenttype"));
                recoveryPaymentBean.setPaymentTypeDes((rs.getString("paymenttypedes") == null) ? "--" : rs.getString("paymenttypedes"));


                recoveryPaymentBeanList.add(recoveryPaymentBean);

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllMasterCard.close();

        }

        return recoveryPaymentBeanList;
    }

    public List<QuestionAnswerBean> getRecoveryQuestionList(Connection con, String issuAccStatus, String collectionStatus, String cardNumber) throws Exception {
        PreparedStatement ps = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        questionBeanList = new ArrayList<SecurityQuestionBean>();
        try {
            String query = "SELECT SQ.QUESTIONNO,SQ.QUESTION,SQ.PRIORITYLEVEL,PL.DESCRIPTION AS PRIORITY_DES,"
                    + "SQ.ISSUACCSTATUS,SQ.TABLENAME,SQ.FIELDNAME1,SQ.FIELDNAME2,SQ.FIELDNAME3,SQ.FIELDNAME4,"
                    + "SQ.CARDCATEGORY,CC.DESCRIPTION AS CATEGORY_DES,"
                    + "SQ.LASTUPDATEDUSER,SQ.LASTUPDATEDTIME,SQ.CREATEDTIME,SQ.STATUS,ST.DESCRIPTION AS STATUS_DES "
                    + "FROM SECURITYQUESTION SQ, STATUS ST, PRIORITYLEVEL PL, CARDCATEGORY CC "
                    + "WHERE SQ.STATUS=ST.STATUSCODE AND PL.PRIORITYLEVELCODE=SQ.PRIORITYLEVEL AND SQ.CARDCATEGORY=CC.CATEGORYCODE "
                    + "AND SQ.ISSUACCSTATUS=? AND SQ.COLLECTIONSTATUS=? ORDER BY SQ.QUESTIONNO ";

            ps = con.prepareStatement(query);
            ps.setString(1, issuAccStatus);
            ps.setString(2, collectionStatus);
            rs = ps.executeQuery();
            while (rs.next()) {
                SecurityQuestionBean bean = new SecurityQuestionBean();

                bean.setQuestionNo(rs.getString("QUESTIONNO"));
                bean.setQuestion(rs.getString("QUESTION"));
                bean.setPriorityLevel(rs.getString("PRIORITYLEVEL"));
                bean.setPriorityDes(rs.getString("PRIORITY_DES"));
                bean.setIssueAcquireStatus(rs.getString("ISSUACCSTATUS"));
                bean.setTableName(rs.getString("TABLENAME"));
                bean.setField1(rs.getString("FIELDNAME1"));
                bean.setField2(rs.getString("FIELDNAME2"));
                bean.setField3(rs.getString("FIELDNAME3"));
                bean.setField4(rs.getString("FIELDNAME4"));
                bean.setCardCategory(rs.getString("CARDCATEGORY"));
                bean.setCardCategoryDes(rs.getString("CATEGORY_DES"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setStatusDes(rs.getString("STATUS_DES"));

                questionBeanList.add(bean);

            }
            this.getRecoveryQuestionAndAnswerList(con, questionBeanList, cardNumber);

        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                ps.close();

            } catch (Exception e) {
            }
        }




        return answerBeanList;
    }

    public List<QuestionAnswerBean> getRecoveryQuestionAndAnswerList(Connection con, List<SecurityQuestionBean> questionList, String cardNumber) throws Exception {

        answerBeanList = new ArrayList<QuestionAnswerBean>();
        PreparedStatement ps = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        try {

            for (int t = 0; t < questionList.size(); t++) {
                try {

                    String select = "";
                    String answer = "";
                    if (questionList.get(t).getField1() != null) {
                        select = select + questionList.get(t).getTableName() + "." + questionList.get(t).getField1();
                    }
                    if (questionList.get(t).getField2() != null) {
                        select = select + "," + questionList.get(t).getTableName() + "." + questionList.get(t).getField2();
                    }
                    if (questionList.get(t).getField3() != null) {
                        select = select + "," + questionList.get(t).getTableName() + "." + questionList.get(t).getField3();
                    }
                    if (questionList.get(t).getField4() != null) {
                        select = select + "," + questionList.get(t).getTableName() + "." + questionList.get(t).getField4();
                    }

                    if (questionList.get(t).getTableName().equals("CARD") || questionList.get(t).getTableName().equals("CARDACCOUNT") || questionList.get(t).getTableName().equals("CARDCUSTOMER") || questionList.get(t).getTableName().equals("CARDMAINCUSTOMER")) {



                        String query = "SELECT " + select
                                + " FROM CARD,CARDACCOUNT,CARDCUSTOMER,CARDACCOUNTCUSTOMER "
                                + " WHERE CARDACCOUNTCUSTOMER.ACCOUNTNO= CARDACCOUNT.ACCOUNTNO AND CARDACCOUNTCUSTOMER.CUSTOMERID =CARDCUSTOMER.CUSTOMERID AND CARDACCOUNTCUSTOMER.CARDNUMBER=CARD.CARDNUMBER AND CARD.CARDNUMBER=" + cardNumber;

                        ps = con.prepareStatement(query);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            QuestionAnswerBean bean = new QuestionAnswerBean();
                            bean.setQuestion(questionList.get(t).getQuestion());

                            if (questionList.get(t).getField1() != null) {
                                answer = answer + rs.getString(questionList.get(t).getField1());
                            }
                            if (questionList.get(t).getField2() != null) {
                                answer = answer + " " + rs.getString(questionList.get(t).getField2());
                            }
                            if (questionList.get(t).getField3() != null) {
                                answer = answer + " " + rs.getString(questionList.get(t).getField3());
                            }
                            if (questionList.get(t).getField4() != null) {
                                answer = answer + " " + rs.getString(questionList.get(t).getField4());
                            }

                            bean.setAnswer(answer);
                            answerBeanList.add(bean);

                        }

                    }
                    if (questionList.get(t).getTableName().equals("CARDAPPLICATION") || questionList.get(t).getTableName().equals("CARDAPPLICATIONSTATUS") || questionList.get(t).getTableName().equals("CARDAPPLICATIONPERSONALDETAILS")) {
                        String query = "SELECT " + select
                                + " FROM CARDAPPLICATION,CARDAPPLICATIONSTATUS,CARDAPPLICATIONPERSONALDETAILS "
                                + " WHERE CARDAPPLICATION.APPLICATIONID= CARDAPPLICATIONSTATUS.APPLICATIONID AND CARDAPPLICATION.APPLICATIONID=CARDAPPLICATIONPERSONALDETAILS.APPLICATIONID AND CARDAPPLICATION.APPLICATIONID=(SELECT CD.applicationid FROM CARD CD WHERE CD.cardnumber=?)";

                        ps = con.prepareStatement(query);
                        ps.setString(1, cardNumber);
                        rs = ps.executeQuery();

                        if (rs.next()) {
                            QuestionAnswerBean bean = new QuestionAnswerBean();
                            bean.setQuestion(questionList.get(t).getQuestion());
                            if (questionList.get(t).getField1() != null) {
                                answer = answer + rs.getString(questionList.get(t).getField1());
                            }
                            if (questionList.get(t).getField2() != null) {
                                answer = answer + " " + rs.getString(questionList.get(t).getField2());
                            }
                            if (questionList.get(t).getField3() != null) {
                                answer = answer + " " + rs.getString(questionList.get(t).getField3());
                            }
                            if (questionList.get(t).getField4() != null) {
                                answer = answer + " " + rs.getString(questionList.get(t).getField4());
                            }

                            bean.setAnswer(answer);

                            answerBeanList.add(bean);

                        }
                    }
                } catch (Exception e) {
                    throw e;
                }

            }



        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                ps.close();

            } catch (Exception e) {
            }
        }
        return answerBeanList;

    }

    public boolean updateCollectionStatus(Connection con, String status, String collectioId) throws Exception {

        PreparedStatement updateCol = null;
        String strSqlBasic = null;
        try {
            strSqlBasic = "UPDATE COLLECTION CL SET CL.status=? WHERE CL.COLLECTIONID=?";

            updateCol = con.prepareStatement(strSqlBasic);

            updateCol.setString(1, status);
            updateCol.setString(2, collectioId);

            updateCol.executeUpdate();

            return true;

        } catch (Exception ee) {
            throw ee;
        } finally {
            updateCol.close();

        }
    }

    public boolean insertCollectionMemo(Connection con, String callerId, String collectionId, String memo, String user) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO COLLECTIONMEMO(CALLERID,MEMO,COLLECTIONID, "
                    + "LASTUPDATEDUSER) "
                    + "VALUES(?,?,?,?) ");


            insertStat.setString(1, callerId);
            insertStat.setString(2, memo);
            insertStat.setString(3, collectionId);
            insertStat.setString(4, user);


            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }
}
