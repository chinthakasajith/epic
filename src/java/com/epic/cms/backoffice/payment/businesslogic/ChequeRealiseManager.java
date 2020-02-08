/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.payment.businesslogic;

import com.epic.cms.backoffice.payment.bean.ChequeBean;
import com.epic.cms.backoffice.payment.persistance.ChequeRealisePersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author badrika
 */
public class ChequeRealiseManager {

    private ChequeRealisePersistance perObj;
    private Connection CMSCon;
    private List<ChequeBean> searchList;

    public HashMap<String, String> getStatusList() throws Exception {

        HashMap<String, String> statusList = null;
        try {

            perObj = new ChequeRealisePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            statusList = perObj.getStatusList(CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {

            CMSCon.rollback();
            throw ex;

        } finally {

            DBConnection.dbConnectionClose(CMSCon);
        }
        return statusList;
    }

    public List<ChequeBean> searchCheque(ChequeBean searchBean) throws Exception {
        try {
            perObj = new ChequeRealisePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            searchList = perObj.searchCheque(CMSCon, searchBean);
            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }


        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;

        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return searchList;
    }

    public int chequeRealiseOrReturn(String checnum, String rtOrRe) throws Exception {
        int i = -1;
        try {
            perObj = new ChequeRealisePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            

            i = perObj.chequeRealiseOrReturn(CMSCon, checnum, rtOrRe);
            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }


        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;

        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return i;
    }
    //
}
