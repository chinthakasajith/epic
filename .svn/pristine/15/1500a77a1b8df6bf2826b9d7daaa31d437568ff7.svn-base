/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cms.system.util.email.persistance;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean;
import com.epic.cms.system.util.email.bean.MSGSchedulerBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

/**
 *
 * @author sajith_g
 */
public class MSGSchedulerPersistance {
    
    public int insertMSGSchduler(Connection con, MSGSchedulerBean mSGSchedulerBean) throws Exception {
        int resultId=-1;
        PreparedStatement insertMSGSchedule = null;
        try {
            insertMSGSchedule = con.prepareStatement("INSERT INTO MSGSCHEDULER "
                    + "(STATUS,ALERTTYPE,MESSAGEBODY,MESSAGETITLE, "
                    + "RECEIVEREMAIL,CREATEDDATE,LASTUPDATEDDATE,LASTUPDATEDUSER) "
                    + "VALUES (?,?,?,?,?,SYSDATE,SYSDATE,?) ");
            insertMSGSchedule.setInt(1, mSGSchedulerBean.getStatus());
            insertMSGSchedule.setString(2, mSGSchedulerBean.getAlertType());
            insertMSGSchedule.setString(3, mSGSchedulerBean.getMessageBody());
            insertMSGSchedule.setString(4, mSGSchedulerBean.getMessageTitle());
            insertMSGSchedule.setString(5, mSGSchedulerBean.getReceiverEmail());
            insertMSGSchedule.setString(6, mSGSchedulerBean.getLastUpdatedUser());
            resultId = insertMSGSchedule.executeUpdate();

        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                insertMSGSchedule.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;
    }
    
}
