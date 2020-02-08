/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.cpmm.requestconfirmation.persistance;

import com.epic.cms.callcenter.customer.bean.CustomerContactDetailsChngeHolderBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.cpmm.requestconfirmation.bean.RequestConfirmationBean;
import com.epic.cms.system.util.datetime.SystemDateTime;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nisansala
 */
public class CardChangeApprovePersistence {

    ResultSet rs = null;
    List<RequestConfirmationBean> requestList = null;

    public List<RequestConfirmationBean> searchRequests(Connection con, RequestConfirmationBean inputBean) throws SQLException, Exception {
        //hold the statement to be executed
        PreparedStatement ps = null;
        String defaultQuery = null;


        try {
            defaultQuery = "SELECT CCR.CARDNUMBER,CCR.PRIORITYLEVEL,PL.DESCRIPTION AS PRIO_DES,CCR.REMARKS,CCR.CALLLOGID,CCR.REQUESTTYPE,CCR.REQUESTCARDTYPE,"
                    + "CCR.REQUESTCARDPRODUCT,CCR.STATUS "
                    + "FROM CARDCHANGEREQUEST CCR,STATUS S,PRIORITYLEVEL PL"
                    + " WHERE S.STATUSCODE= CCR.STATUS AND CCR.STATUS='INIT' AND CCR.PRIORITYLEVEL=PL.PRIORITYLEVELCODE";


            String condition = "";

            if (!inputBean.getCardNo().contentEquals("") || inputBean.getCardNo().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CCR.CARDNUMBER = '" + inputBean.getCardNo() + "'";
            }
            if (!inputBean.getPriorityLevel().contentEquals("") || inputBean.getPriorityLevel().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CCR.PRIORITYLEVEL = '" + inputBean.getPriorityLevel() + "'";
            }
            if (!inputBean.getStatus().contentEquals("") || inputBean.getStatus().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CCR.STATUS = '" + inputBean.getStatus() + "'";
            }
            if (!inputBean.getReasonCode().contentEquals("") || inputBean.getReasonCode().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CCR.REQUESTTYPE = '" + inputBean.getReasonCode() + "'";
            }
            if (!condition.equals("")) {
                defaultQuery += "  AND " + condition + " ORDER BY CCR.CARDNUMBER";
            } else {
                defaultQuery += " ORDER BY CCR.CARDNUMBER";
            }

            ps = con.prepareStatement(defaultQuery);
            rs = ps.executeQuery();

            requestList = new ArrayList<RequestConfirmationBean>();

            while (rs.next()) {
                RequestConfirmationBean resultBean = new RequestConfirmationBean();

                resultBean.setCardNo(rs.getString("CARDNUMBER"));
                resultBean.setPriorityLevel(rs.getString("PRIORITYLEVEL"));
                resultBean.setRemark(rs.getString("REMARKS"));
                resultBean.setStatus(rs.getString("STATUS"));
                resultBean.setCallLogID(rs.getString("CALLLOGID"));
                resultBean.setReasonCode(rs.getString("REQUESTTYPE"));
                resultBean.setReqCdType(rs.getString("REQUESTCARDTYPE"));
                resultBean.setReqCdProd(rs.getString("REQUESTCARDPRODUCT"));
                resultBean.setPriorityDes(rs.getString("PRIO_DES"));

                requestList.add(resultBean);
            }

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

        return requestList;
    }

    public RequestConfirmationBean viewOneRequest(Connection con, String cardNo, String operation) throws SQLException, Exception {
        RequestConfirmationBean reqBean = null;
        PreparedStatement ps = null;

        try {

            String query = "SELECT CCR.CARDNUMBER,CCR.PRIORITYLEVEL,PL.DESCRIPTION AS PRIO_DES,CCR.REMARKS,CCR.REQUESTTYPE,CCR.REQUESTCARDTYPE,"
                    + "CT.DESCRIPTION AS CUR_CTYPE_DES,CT1.DESCRIPTION AS REQ_CTYPE_DES,CP.DESCRIPTION AS CUR_PROD_DES,CP1.DESCRIPTION AS REQ_PROD_DES,"
                    + "CCR.REQUESTCARDPRODUCT,C.CREDITLIMIT,C.CASHLIMIT,C.EXPIERYDATE,C.CARDTYPE,C.CARDPRODUCT,C.CARDSTATUS,S.DESCRIPTION AS STATUS_DES "
                    + "FROM CARDCHANGEREQUEST CCR,STATUS S,PRIORITYLEVEL PL,CARD C,CARDTYPE CT,CARDTYPE CT1,CARDPRODUCT CP,CARDPRODUCT CP1"
                    + " WHERE S.STATUSCODE= C.CARDSTATUS AND CCR.STATUS='INIT' AND CCR.PRIORITYLEVEL=PL.PRIORITYLEVELCODE "
                    + "AND CCR.CARDNUMBER=? AND CCR.REQUESTTYPE=? AND C.CARDNUMBER=? AND C.CARDTYPE=CT.CARDTYPECODE AND C.CARDPRODUCT=CP.PRODUCTCODE AND "
                    + "CCR.REQUESTCARDTYPE=CT1.CARDTYPECODE "
                    + "AND CCR.REQUESTCARDPRODUCT=CP1.PRODUCTCODE";
            ps = con.prepareStatement(query);
            ps.setString(1, cardNo);
            ps.setString(2, operation);
            ps.setString(3, cardNo);
            rs = ps.executeQuery();

            while (rs.next()) {
                reqBean = new RequestConfirmationBean();

                reqBean.setCardNo(rs.getString("CARDNUMBER"));
                reqBean.setPriorityLevel(rs.getString("PRIORITYLEVEL"));
                reqBean.setRemark(rs.getString("REMARKS"));
                reqBean.setStatus(rs.getString("CARDSTATUS"));
                reqBean.setStatusDes(rs.getString("STATUS_DES"));
                reqBean.setCreditLimit(rs.getString("CREDITLIMIT"));
                reqBean.setReasonCode(rs.getString("REQUESTTYPE"));
                reqBean.setCashLimit(rs.getString("CASHLIMIT"));
                reqBean.setExpiryDate(rs.getString("EXPIERYDATE"));
                reqBean.setReqCdType(rs.getString("REQUESTCARDTYPE"));
                reqBean.setReqCdProd(rs.getString("REQUESTCARDPRODUCT"));
                reqBean.setCdType(rs.getString("CARDTYPE"));
                reqBean.setCdProd(rs.getString("CARDPRODUCT"));
                reqBean.setReqCdTypeDes(rs.getString("REQ_CTYPE_DES"));
                reqBean.setReqCdProdDes(rs.getString("REQ_PROD_DES"));
                reqBean.setCdTypeDes(rs.getString("CUR_CTYPE_DES"));
                reqBean.setCdProdDes(rs.getString("CUR_PROD_DES"));

            }

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

        return reqBean;
    }

    public int approveCardChange(RequestConfirmationBean confBean, String approveStatus, String operation, Connection con) throws SQLException, Exception {

        int row = -1;
        int result1 = -1;
        int result2 = -1;
        PreparedStatement ps;

        String updateQuery1 = "UPDATE CARDCHANGEREQUEST SET APPROVEDUSER=?,STATUS=?,LASTUPDATEDUSER=?,LASTUPDATEDTIME=? WHERE CARDNUMBER=? AND REQUESTTYPE=?";


        ps = con.prepareStatement(updateQuery1);

        ps.setString(1, confBean.getLastUpdatedUser());
        ps.setString(2, confBean.getStatus());
        ps.setString(3, confBean.getLastUpdatedUser());
        ps.setTimestamp(4, SystemDateTime.getSystemDataAndTime());
        ps.setString(5, confBean.getCardNo());
        ps.setString(6, operation);

        result1 = ps.executeUpdate();
        
        String updateQuery2 = "UPDATE CARD SET EMBOSSSTATUS=?,ENCODESTATUS=?,PINSTATUS=?,LASTUPDATEDUSER=?,LASTUPDATEDTIME=? WHERE CARDNUMBER=?";
        
        ps = con.prepareStatement(updateQuery2);

        ps.setString(1, confBean.getEmbossStatus());
        ps.setString(2, confBean.getEncodeStatus());
        ps.setString(3, confBean.getPINStatus());
        ps.setString(4, confBean.getLastUpdatedUser());
        ps.setTimestamp(5, SystemDateTime.getSystemDataAndTime());
        ps.setString(6, confBean.getCardNo());

        result2 = ps.executeUpdate();
        
        if(result1 == 1 && result2 == 1){
            row = 1;
        }
        else{
        throw new Exception();
        }

        return row;
    }

    public void updateMainCardCustomerDetails(CustomerPersonalBean customerPersonalBean, CustomerContactDetailsChngeHolderBean changeTracker, Connection con) throws Exception {
        
        PreparedStatement ps = null;
        StringBuffer updateColumnQry;

        try {
            
                int statementIndex=0;
                updateColumnQry =new StringBuffer("UPDATE CARDAPPLICATIONPERSONALDETAILS SET LASTUPDATEDUSER=? ,LASTUPDATEDTIME=?");
                               
                
                
                if(changeTracker.isPermanentAddressChanged()){
                    updateColumnQry.append(",PERMANENTADDRESS1=?, PERMANENTADDRESS2=?,PERMANANTADDRESS3=?,PERMANAENTCITY=? ");
                }
                
                if(changeTracker.isResidenceAddressChanged()){
                    updateColumnQry.append(",ADDRESS1=?, ADDRESS2=?,ADDRESS3=?, CITY=?");
                }
                
                if(changeTracker.isBillingAddressChanged()){
                    updateColumnQry.append(", BILLINGADDRESS1=?,BILLINGADDRESS2=?, BILLINGADDRESS3=?,BILLINGCITY=?");
                }
                
//                if(changeTracker.isCompanyAddressChanged()){
//                    updateColumnQry.append(", BUSINESSADDRESS1=?,BUSINESSADDRESS2=?, BUSINESSADDRESS3=?,BUSINESSCITY=?");
//                }
                
                if(changeTracker.isResPhoneNoChanged()){
                    updateColumnQry.append(",HOMETELEPHONENO=?");
                }
                
                if(changeTracker.isMobilePhoneNoChanged()){
                    updateColumnQry.append(",MOBILENO=?");
                }
                
                if(changeTracker.isOfficePhoneNoChanged()){
                    updateColumnQry.append(",OFFICEPHONENO=?");
                }
                
//                if(changeTracker.isEstMobContactNoChanged()){
//                    updateColumnQry.append(",CONTACTNUMBERSLAND=?");
//                }
//                
//                if(changeTracker.isEstLandContactNoChanged()){
//                    updateColumnQry.append(",CONTACTNUMBERSMOBILE=?");
//                }
//                
//                if(changeTracker.isFaxNoChanged()){
//                    updateColumnQry.append("FAXNUMBER=?");
//                }
                
                updateColumnQry.append(" WHERE APPLICATIONID=?");

                ps = con.prepareStatement(updateColumnQry.toString());
                
                //ps.setString(++statementIndex, customerPersonalBean.getApplicationId());

                ps.setString(++statementIndex, customerPersonalBean.getLastUpdateUser());
                ps.setTimestamp(++statementIndex, SystemDateTime.getSystemDataAndTime());
                

                if (changeTracker.isPermanentAddressChanged()) {
                    ps.setString(++statementIndex, customerPersonalBean.getPermentAddress1());
                    ps.setString(++statementIndex, customerPersonalBean.getPermentAddress2());
                    ps.setString(++statementIndex, customerPersonalBean.getPermentAddress3());
                    ps.setString(++statementIndex, customerPersonalBean.getPermentCity());

                }

                if (changeTracker.isResidenceAddressChanged()) {
                    ps.setString(++statementIndex, customerPersonalBean.getAddress1());
                    ps.setString(++statementIndex, customerPersonalBean.getAddress2());
                    ps.setString(++statementIndex, customerPersonalBean.getAddress3());
                    ps.setString(++statementIndex, customerPersonalBean.getCity());
                }

                if (changeTracker.isBillingAddressChanged()) {
                    ps.setString(++statementIndex, customerPersonalBean.getBillAddress1());
                    ps.setString(++statementIndex, customerPersonalBean.getBillAddress2());
                    ps.setString(++statementIndex, customerPersonalBean.getBillAddress3());
                    ps.setString(++statementIndex, customerPersonalBean.getBillCity());
                }

//                if (changeTracker.isCompanyAddressChanged()) {
//                    
//                    ps.setString(++statementIndex, customerPersonalBean.getCompanyAddress1());
//                    ps.setString(++statementIndex, customerPersonalBean.getCompanyAddress2());
//                    ps.setString(++statementIndex, customerPersonalBean.getCompanyAddress3());
//                    ps.setString(++statementIndex, customerPersonalBean.getCompanyCity());
//                }

                if (changeTracker.isResPhoneNoChanged()) {
                    ps.setString(++statementIndex, customerPersonalBean.getHomeTelNumber());
                }

                if (changeTracker.isMobilePhoneNoChanged()) {
                    ps.setString(++statementIndex, customerPersonalBean.getMobileNumber());
                }

                if (changeTracker.isOfficePhoneNoChanged()) {
                    ps.setString(++statementIndex, customerPersonalBean.getOfficeTelNumber());
                }

//                if (changeTracker.isEstMobContactNoChanged()) {
//                    ps.setString(++statementIndex, customerPersonalBean.getEstMobileNo());
//                }
//
//                if (changeTracker.isEstLandContactNoChanged()) {
//                    ps.setString(++statementIndex, customerPersonalBean.getEstLandNo());
//                }
//
//                if (changeTracker.isFaxNoChanged()) {
//                    ps.setString(++statementIndex, customerPersonalBean.getCompanyAddress1());
//                }

            ps.setString(++statementIndex, customerPersonalBean.getApplicationId());            
            ps.executeUpdate();

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
        
        
    }

    public void updateSupCardCustomerDetails(CustomerPersonalBean customerPersonalBean, CustomerContactDetailsChngeHolderBean changeTracker, Connection CMSCon) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void updateEstCardCustomerDetails(CustomerPersonalBean customerPersonalBean, CustomerContactDetailsChngeHolderBean changeTracker, Connection CMSCon) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void updateCustomerContactDetailsChnageRequest(String requestId, String lastUpdateUser, Connection con, String status) throws Exception {
        PreparedStatement ps=null; 
        
        try{
            String updateQuery1 = "UPDATE CUSCONTACTDETAILCHANGEREQUEST "
                    + "SET APPROVEDUSER=?,STATUS=?,LASTUPDATEDUSER=?,LASTUPDATEDTIME=? "
                    + "WHERE REQUESTID=? ";
        
        ps = con.prepareStatement(updateQuery1);
        ps.setString(1, lastUpdateUser);
        ps.setString(2, status);
        ps.setString(3, lastUpdateUser);
        ps.setTimestamp(4, SystemDateTime.getSystemDataAndTime());
        ps.setInt(5, Integer.parseInt(requestId));

        ps.executeUpdate();
            
        
        }catch (Exception ex) {

            throw ex;
        } finally {
                if(ps!=null){
                     ps.close();
                }
        }
        
    }
}
