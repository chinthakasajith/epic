/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.profilemgt.persistance;

import com.epic.cms.admin.controlpanel.profilemgt.bean.InterestProfileTransactionBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.InterestprofileBean;
import com.epic.cms.admin.controlpanel.profilemgt.bean.TransactionTypeBean;
import com.epic.cms.system.util.session.SessionUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author mahesh_m
 */
public class InterestProfilePersistance {
    private ResultSet rs;
    private List<InterestprofileBean> interestProfileBeanList;
    private List<TransactionTypeBean> transactionDetails;
    
    public List<InterestprofileBean> getInterestProfileDetails(Connection con) throws Exception {
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT IP.INTERESTPROFILECODE,IP.DESCRIPTION,IP.STATUS,S.DESCRIPTION,IP.INTERESTRATE,IP.INTERESTPERIODVALUE,IP.EFFECTIVEDATEFORNEWCUSOMER,IP.EFFECTIVEDATEFOREXISTCUSOMER,IP.INTERESTCALSTARTFROM,IP.INTERESTCALSTARTTO,IP.CHARGETYPE,IP.LASTUPDATEDUSER,IP.LASTUPDATEDTIME,IP.CREATETIME FROM INTERESTPROFILE IP,STATUS S WHERE IP.STATUS = S.STATUSCODE ");

            
            interestProfileBeanList = new ArrayList<InterestprofileBean>();

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                SimpleDateFormat dateformatYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
                StringBuilder effectDateForNewCustomer = new StringBuilder( dateformatYYYYMMDD.format( rs.getDate("EFFECTIVEDATEFORNEWCUSOMER") ) );
                StringBuilder effectDateForExistingCustomer = new StringBuilder( dateformatYYYYMMDD.format( rs.getDate("EFFECTIVEDATEFOREXISTCUSOMER") ) );
                InterestprofileBean interestProfile = new InterestprofileBean();

                interestProfile.setInterestFrofileCode(rs.getString("INTERESTPROFILECODE"));
                interestProfile.setDescription(rs.getString("DESCRIPTION"));
                interestProfile.setStatus(rs.getString("STATUS"));
                interestProfile.setStatusDescription(rs.getString("STATUS"));
                interestProfile.setInterestRate(Double.toString(rs.getDouble("INTERESTRATE")));
                interestProfile.setInterestPeriodValue(Integer.toString(rs.getInt("INTERESTPERIODVALUE")));
                interestProfile.setCustomValue(Integer.toString(rs.getInt("INTERESTPERIODVALUE")));
                interestProfile.setEffectiveDateForNewCustomer(effectDateForNewCustomer.toString());
                interestProfile.setEffectiveDateForExistCustomer(effectDateForExistingCustomer.toString());
                interestProfile.setInterestCalStartFrom(rs.getString("INTERESTCALSTARTFROM"));
                interestProfile.setInterestCalStartTo(rs.getString("INTERESTCALSTARTTO"));
                interestProfile.setChargeType(rs.getString("CHARGETYPE"));
              
                interestProfileBeanList.add(interestProfile);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return interestProfileBeanList;
    }
    
    
    
     public List<TransactionTypeBean> getTransactionTypeDetails(Connection con) throws Exception {
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT TT.TRANSACTIONCODE,TT.DESCRIPTION,TT.PROFILECATEGORYCODE,TT.FINANCIALSTATUS,TT.STATUS,S.DESCRIPTION FROM TRANSACTIONTYPE TT,STATUS S WHERE TT.STATUS = S.STATUSCODE ");

            
            transactionDetails = new ArrayList<TransactionTypeBean>();

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                TransactionTypeBean transaction = new TransactionTypeBean();

                transaction.setTransactionCode(rs.getString("TRANSACTIONCODE"));
                transaction.setDescription(rs.getString("DESCRIPTION"));
                transaction.setProfileCategoryCode(rs.getString("PROFILECATEGORYCODE"));
                transaction.setFinancialStatus(rs.getString("FINANCIALSTATUS"));
                transaction.setStatus(rs.getString("STATUS"));

                
                transactionDetails.add(transaction);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return transactionDetails;
    }
    
    
    public boolean insertInterestProfile(Connection con, InterestprofileBean interest) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO INTERESTPROFILE(INTERESTPROFILECODE,DESCRIPTION,STATUS,INTERESTRATE,INTERESTPERIODVALUE,EFFECTIVEDATEFORNEWCUSOMER,EFFECTIVEDATEFOREXISTCUSOMER,INTERESTCALSTARTFROM,INTERESTCALSTARTTO,CHARGETYPE,LASTUPDATEDUSER,LASTUPDATEDTIME,CREATETIME) VALUES(?,?,?,?,?,?,?,?,?,?,?,SYSDATE,SYSDATE) ");
      
            insertStat.setString(1, interest.getInterestFrofileCode());
            insertStat.setString(2, interest.getDescription());
            insertStat.setString(3, interest.getStatus());
            insertStat.setDouble(4, Double.parseDouble(interest.getInterestRate()));
            
            if(interest.getInterestPeriodValue().equals("daily")){
                insertStat.setInt(5, 1);
            }else if(interest.getInterestPeriodValue().equals("annual")){
                insertStat.setInt(5, 365);
            }else if(interest.getInterestPeriodValue().equals("custom")){
                insertStat.setInt(5, Integer.parseInt(interest.getCustomValue()));
            }
            
            
             SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
             Date convertedEffectDateForNew = dateFormat.parse(interest.getEffectiveDateForNewCustomer());
             Date convertedEffectDateForExist = dateFormat.parse(interest.getEffectiveDateForExistCustomer());
            
//            insertStat.setTimestamp(6, new Timestamp(interest.getEffectiveDateForNewCustomer().getTime()));
//            insertStat.setTimestamp(7, new Timestamp(interest.getEffectiveDateForExistCustomer().getTime()));
//            
            insertStat.setTimestamp(6,new Timestamp(convertedEffectDateForNew.getTime()) );
            insertStat.setTimestamp(7,new Timestamp(convertedEffectDateForExist.getTime()) );
 
            insertStat.setString(8, interest.getInterestCalStartFrom());
            insertStat.setString(9, interest.getInterestCalStartTo());
            insertStat.setString(10, interest.getChargeType());
            insertStat.setString(11, interest.getLastUpdatedUser());


            insertStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } catch(Exception e){
            throw e;
        }finally {
            insertStat.close();
        }
        return success;
    }
  
    
     public boolean insertToInterestProfileTransaction(Connection con, InterestprofileBean interest,String transactionCode) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO INTERESTPROFILETRANSACTION(INTERESTPROFILE,TRANSACTIONCODE,LASTUPDATEDUSER,LASTUPDATEDDATE,CREATETIME) VALUES(?,?,?,SYSDATE,SYSDATE) ");
      
            insertStat.setString(1, interest.getInterestFrofileCode());
            insertStat.setString(2, transactionCode);
            insertStat.setString(3, interest.getLastUpdatedUser());
            insertStat.executeUpdate();
            
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } catch(Exception e){
            throw e;
        }finally {
            insertStat.close();
        }
        return success;
    }
  
    
    
    
    
     public List<TransactionTypeBean> getNotAssignedTransactionTypeDescription(Connection con,String pageCode) throws SQLException, Exception {


        List<TransactionTypeBean> transactions = new ArrayList<TransactionTypeBean>();

        PreparedStatement ps = null;
        try {
            String query = "SELECT TT.TRANSACTIONCODE,TT.DESCRIPTION FROM TRANSACTIONTYPE TT "
                    + "WHERE TRANSACTIONCODE NOT IN "
                    + "(SELECT TRANSACTIONCODE FROM INTERESTPROFILETRANSACTION WHERE INTERESTPROFILE = ?)";
            
            ps = con.prepareStatement(query);
            ps.setString(1, pageCode);
            rs = ps.executeQuery();

            while (rs.next()) {
               TransactionTypeBean bean = new TransactionTypeBean();
               bean.setTransactionCode(rs.getString("TRANSACTIONCODE"));
               bean.setDescription(rs.getString("DESCRIPTION"));
                transactions.add(bean);
            }

            return transactions;
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
    
    
      public List<TransactionTypeBean> getAssignedList(Connection con, String interestProfileCode) throws Exception {


        List<TransactionTypeBean> assignedList = new ArrayList<TransactionTypeBean>();
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT TRANSACTIONCODE FROM INTERESTPROFILETRANSACTION WHERE INTERESTPROFILE = ? ");


            getAllByCatStat.setString(1, interestProfileCode);
            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {
                TransactionTypeBean assignedtasks = new TransactionTypeBean();
                assignedtasks.setTransactionCode(rs.getString("TRANSACTIONCODE"));
                assignedList.add(assignedtasks);
            }
        } catch (SQLException ex) {
     
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return assignedList;
    } 
      
   
     public TransactionTypeBean getTransactionDescriptionByTransactionCode(Connection con, String transactionCode) throws Exception {
//        String description = null;
        TransactionTypeBean bean = null;
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT TRANSACTIONCODE,DESCRIPTION FROM TRANSACTIONTYPE WHERE TRANSACTIONCODE = ? ");

            getAllByCatStat.setString(1, transactionCode);


            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {
                bean = new TransactionTypeBean();
                bean.setTransactionCode(rs.getString("TRANSACTIONCODE"));
                bean.setDescription( rs.getString("DESCRIPTION"));
            }
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return bean;
    }
  
     
     
      public boolean updateInterestProfile(Connection con, InterestprofileBean interest) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE INTERESTPROFILE SET DESCRIPTION=?,STATUS=?,INTERESTRATE=?,INTERESTPERIODVALUE=?,EFFECTIVEDATEFORNEWCUSOMER=?,EFFECTIVEDATEFOREXISTCUSOMER=?,INTERESTCALSTARTFROM=?,INTERESTCALSTARTTO=?,CHARGETYPE=?,LASTUPDATEDUSER=?,LASTUPDATEDTIME=SYSDATE WHERE INTERESTPROFILECODE=? ");

            updateStat.setString(1, interest.getDescription());
            updateStat.setString(2, interest.getStatus());
            updateStat.setDouble(3, Double.parseDouble(interest.getInterestRate()));
           
            if(interest.getInterestPeriodValue().equals("daily")){
                updateStat.setInt(4, 1);
            }else if(interest.getInterestPeriodValue().equals("annual")){
                updateStat.setInt(4, 365);
            }else if(interest.getInterestPeriodValue().equals("custom")){
                updateStat.setInt(4, Integer.parseInt(interest.getCustomValue()));
            }
            
             SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
             Date convertedEffectDateForNew = dateFormat.parse(interest.getEffectiveDateForNewCustomer());
             Date convertedEffectDateForExist = dateFormat.parse(interest.getEffectiveDateForExistCustomer());
            
            updateStat.setTimestamp(5,new Timestamp(convertedEffectDateForNew.getTime()) );
            updateStat.setTimestamp(6,new Timestamp(convertedEffectDateForExist.getTime()) );
             
            updateStat.setString(7, interest.getInterestCalStartFrom());
            updateStat.setString(8, interest.getInterestCalStartTo());
            updateStat.setString(9, interest.getChargeType());
            updateStat.setString(10, interest.getLastUpdatedUser());
            updateStat.setString(11, interest.getInterestFrofileCode());

            updateStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }
      
      
       public boolean  deleteFromInterestProfileTransaction(Connection con, String interestProfileCode) throws Exception {
        boolean status = false;
        TransactionTypeBean bean = null;
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("DELETE FROM INTERESTPROFILETRANSACTION WHERE INTERESTPROFILE = ? ");

            getAllByCatStat.setString(1, interestProfileCode);


            rs = getAllByCatStat.executeQuery();
            
            status = true;
        } catch (Exception ex) {
            status = false;
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return status;
    }   
  
    
     
     public List<InterestProfileTransactionBean> getInterestProfileTransactionDetailsByInterestProfCode(Connection con,String interestProfileCode) throws SQLException, Exception {


        List<InterestProfileTransactionBean> interestProfileTransactionList = new ArrayList<InterestProfileTransactionBean>();

        PreparedStatement ps = null;
        try {
            String query = "SELECT IPT.TRANSACTIONCODE,IPT.LASTUPDATEDUSER,IPT.LASTUPDATEDDATE,IPT.CREATETIME FROM INTERESTPROFILETRANSACTION IPT WHERE IPT.INTERESTPROFILE=?";
            ps = con.prepareStatement(query);
            ps.setString(1, interestProfileCode);

            rs = ps.executeQuery();

            while (rs.next()) {
                InterestProfileTransactionBean bean = new InterestProfileTransactionBean();
               
                bean.setTransactionCode(rs.getString("TRANSACTIONCODE"));
                bean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                bean.setLastUpdatedTime(rs.getDate("LASTUPDATEDDATE"));
                bean.setCreatedTime(rs.getDate("CREATETIME"));

                interestProfileTransactionList.add(bean);
            }

            return interestProfileTransactionList;
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
    
    
    public boolean deleteInterest(Connection con, InterestprofileBean interest) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {

            deleteStat = con.prepareStatement("DELETE INTERESTPROFILE WHERE INTERESTPROFILECODE=? ");
            deleteStat.setString(1, interest.getInterestFrofileCode());

            deleteStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            success = false;
            throw ex;
        } finally {
            deleteStat.close();
        }
        return success;
    }
      
    
     public boolean insertToInterestProfileTransactionAllData(Connection con,InterestProfileTransactionBean interestProfileTransaction) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        
        
        try {
            insertStat = con.prepareStatement("INSERT INTO INTERESTPROFILETRANSACTION(INTERESTPROFILE,TRANSACTIONCODE,LASTUPDATEDUSER,LASTUPDATEDDATE,CREATETIME) VALUES(?,?,?,?,?) ");
            insertStat.setString(1, interestProfileTransaction.getInterestProfileCode());
            insertStat.setString(2, interestProfileTransaction.getTransactionCode());
            insertStat.setString(3, interestProfileTransaction.getLastUpdatedUser());
            insertStat.setDate(4, interestProfileTransaction.getLastUpdatedTime());
            insertStat.setDate(5, interestProfileTransaction.getCreatedTime());
            

            insertStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

     
       
}
