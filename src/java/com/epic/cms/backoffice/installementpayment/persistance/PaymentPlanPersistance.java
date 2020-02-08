/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.installementpayment.persistance;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.ApplicationModuleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SectionBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.TaskBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.UserRoleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.UserRolePrivilegeBean;
import com.epic.cms.backoffice.installementpayment.bean.PaymentPlanBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sajith_g
 */
public class PaymentPlanPersistance {
    
    private ResultSet rs = null;
    private List<PaymentPlanBean> paymentPlanList = null;
    private List<UserRolePrivilegeBean> appSecPageTaskList = null;
    private List<ApplicationModuleBean> userRoleAppliacationList = null;
    private List<SectionBean> sectionList = null;
    private List<PageBean> pageList = null;
    private List<TaskBean> taskList = null;
    private List<UserRoleBean> userRoleListUnassignedToSchema = null;
    private List<UserRoleBean> userRoleListAssignedToSchema = null;
    /**
     * this method insert the payment plan
     *
     * @param con
     * @param paymentplan
     * @return success - boolean
     * @throws Exception
     */
    public boolean insertPaymentPlan(Connection con, PaymentPlanBean paymentplan) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO PAYMENTPLAN(PAYMENTPLANCODE,DESCRIPTION, "
                    + "DURATION,INTERESTRATE,STATUS,LASTUPDATEDUSER,LASTUPDATEDTIME,CREATETIME) "
                    + "VALUES(?,?,?,?,?,?,?,?) ");
            insertStat.setString(1, paymentplan.getPaymentplancode());
            insertStat.setString(2, paymentplan.getDescription());
            insertStat.setString(3, paymentplan.getDuration());
            insertStat.setString(4, paymentplan.getInterestrate());
            insertStat.setString(5, paymentplan.getStatusCode());
            insertStat.setString(6, paymentplan.getLastUpdatedUser());
            insertStat.setTimestamp(7, new Timestamp(paymentplan.getLastUpdatedTime().getTime()));
            insertStat.setTimestamp(8, new Timestamp(paymentplan.getCreatedTime().getTime()));

            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }
    
    /**
     * this method return all the user plans
     *
     * @param con
     * @return userlevelList-List<UserLevelBean>
     * @throws Exception
     */
    public List<PaymentPlanBean> getAllPaymentPlans(Connection con) throws Exception {
        PreparedStatement getAllPaymentPlans = null;
        try {
            getAllPaymentPlans = con.prepareStatement("SELECT PP.PAYMENTPLANCODE,PP.DESCRIPTION,PP.DURATION,PP.INTERESTRATE, "
                    + "PP.LASTUPDATEDUSER,PP.CREATETIME,PP.LASTUPDATEDTIME, AST.DESCRIPTION AS STDESCRIPTION,PP.STATUS "
                    + "FROM PAYMENTPLAN PP, STATUS AST WHERE PP.STATUS = AST.STATUSCODE");
            rs = getAllPaymentPlans.executeQuery();
            paymentPlanList = new ArrayList<PaymentPlanBean>();
            while (rs.next()) {

                PaymentPlanBean paymentPlan = new PaymentPlanBean();

                paymentPlan.setPaymentplancode(rs.getString("PAYMENTPLANCODE"));
                paymentPlan.setDescription(rs.getString("DESCRIPTION"));
                paymentPlan.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                paymentPlan.setCreatedTime(rs.getDate("CREATETIME"));
                paymentPlan.setStatusDes(rs.getString("STDESCRIPTION"));
                paymentPlan.setStatusCode(rs.getString("STATUS"));
                paymentPlan.setDuration(rs.getString("DURATION"));
                paymentPlan.setInterestrate(rs.getString("INTERESTRATE"));

                paymentPlanList.add(paymentPlan);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            rs.close();
            getAllPaymentPlans.close();

        }

        return paymentPlanList;
    }
    
    /**
     * this method return all the ACTIVE user plans
     *
     * @param con
     * @return userlevelList-List<UserLevelBean>
     * @throws Exception
     */
    public List<PaymentPlanBean> getAllActivePaymentPlans(Connection con) throws Exception {
        PreparedStatement getAllPaymentPlans = null;
        try {
            getAllPaymentPlans = con.prepareStatement("SELECT PP.PAYMENTPLANCODE,PP.DESCRIPTION,PP.DURATION,PP.INTERESTRATE, "
                    + "PP.LASTUPDATEDUSER,PP.CREATETIME,PP.LASTUPDATEDTIME, AST.DESCRIPTION AS STDESCRIPTION,PP.STATUS "
                    + "FROM PAYMENTPLAN PP, STATUS AST WHERE PP.STATUS = AST.STATUSCODE AND PP.STATUS='ACT'");
            rs = getAllPaymentPlans.executeQuery();
            paymentPlanList = new ArrayList<PaymentPlanBean>();
            while (rs.next()) {

                PaymentPlanBean paymentPlan = new PaymentPlanBean();

                paymentPlan.setPaymentplancode(rs.getString("PAYMENTPLANCODE"));
                paymentPlan.setDescription(rs.getString("DESCRIPTION"));
                paymentPlan.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                paymentPlan.setCreatedTime(rs.getDate("CREATETIME"));
                paymentPlan.setStatusDes(rs.getString("STDESCRIPTION"));
                paymentPlan.setStatusCode(rs.getString("STATUS"));
                paymentPlan.setDuration(rs.getString("DURATION"));
                paymentPlan.setInterestrate(rs.getString("INTERESTRATE"));

                paymentPlanList.add(paymentPlan);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            rs.close();
            getAllPaymentPlans.close();

        }

        return paymentPlanList;
    }
    
    /**
     * this method delete the payment plan
     *
     * @param con
     * @param paymentPlan
     * @return success - boolean
     * @throws Exception
     */
    public boolean deletePaymentPlan(Connection con, PaymentPlanBean paymentPlan) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = con.prepareStatement("DELETE PAYMENTPLAN WHERE PAYMENTPLANCODE=? ");
            deleteStat.setString(1, paymentPlan.getPaymentplancode());

            deleteStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            deleteStat.close();
        }
        return success;
    }
    
    /**
     * this method update the payment plan
     *
     * @param con
     * @param paymentplan
     * @return success -boolean
     * @throws Exception
     */
    public boolean updatePaymentPlan(Connection con, PaymentPlanBean paymentplan) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE PAYMENTPLAN SET DESCRIPTION=?, "
                    + "STATUS=?,DURATION=?,INTERESTRATE=?,LASTUPDATEDUSER=?,LASTUPDATEDTIME=? "
                    + "WHERE PAYMENTPLANCODE=? ");

            updateStat.setString(1, paymentplan.getDescription());
            updateStat.setString(2, paymentplan.getStatusCode());
            updateStat.setString(3, paymentplan.getDuration());
            updateStat.setString(4, paymentplan.getInterestrate());
            updateStat.setString(5, paymentplan.getLastUpdatedUser());
            updateStat.setTimestamp(6, new Timestamp(paymentplan.getLastUpdatedTime().getTime()));
            updateStat.setString(7, paymentplan.getPaymentplancode());

            updateStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }
    
    
}
