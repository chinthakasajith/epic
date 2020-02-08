/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.businesslogic;

import com.epic.cms.reportexp.cardapplication.bean.LimitExceedReportBean;
import com.epic.cms.reportexp.cardapplication.persistance.SearchLimitExceedReportPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author asitha_l
 */
public class SearchLimitExceedReportManager {
    
    private Connection CMSCon;
    private HashMap<String, String> cardTypeList = null;
    private SearchLimitExceedReportPersistance searchLimitExceedReportPersist = null;
    private List<LimitExceedReportBean> limitExceedReportList = null;
    
    /**
     * to retrieve all the card types
     * @return
     * @throws Exception 
     */
    public HashMap<String, String> getAllCardTypeList() throws Exception {
        try {
            searchLimitExceedReportPersist = new SearchLimitExceedReportPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardTypeList = searchLimitExceedReportPersist.getAllCardTypeList(CMSCon);
            CMSCon.commit();
        } catch (Exception ex) {
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
        return cardTypeList;
    }

    public List<LimitExceedReportBean> searchLimitExceedReport(LimitExceedReportBean limitExceedReportBean) throws SQLException, Exception {
         try {
            searchLimitExceedReportPersist = new SearchLimitExceedReportPersistance();
            limitExceedReportList = new ArrayList<LimitExceedReportBean>();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            limitExceedReportList = searchLimitExceedReportPersist.searchLimitExceedReport(CMSCon, limitExceedReportBean);

            CMSCon.commit();
        } catch (Exception ex) {
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
        return limitExceedReportList;
    }
    
}
