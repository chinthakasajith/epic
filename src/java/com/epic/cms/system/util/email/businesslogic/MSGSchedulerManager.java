/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cms.system.util.email.businesslogic;

import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.email.bean.MSGSchedulerBean;
import com.epic.cms.system.util.email.persistance.MSGSchedulerPersistance;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author sajith_g
 */
public class MSGSchedulerManager {
    
    private Connection CMSCon;
    private MSGSchedulerPersistance msgSchedulerPersistance;
    
    public int insertMSGScheduler(MSGSchedulerBean mSGSchedulerBean) throws Exception {

        int isAdd;
        try {
            msgSchedulerPersistance = new MSGSchedulerPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            isAdd = msgSchedulerPersistance.insertMSGSchduler(CMSCon, mSGSchedulerBean);
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }

        return isAdd;
    }
    
}
