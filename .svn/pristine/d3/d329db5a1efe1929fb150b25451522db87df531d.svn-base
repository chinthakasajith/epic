/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.collection.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.bean.CaseTypeBean;
import com.epic.cms.system.util.logs.LogFileCreator;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 *
 * @author asitha_l
 */
public class CaseMgtPersistance {

    private ResultSet rs;

    public Map<String, String> getStatus(Connection cmsCon) throws Exception {
        Map<String, String> statusMap = new HashMap<String, String>();
        PreparedStatement ps = null;

        try {
            String query = "SELECT STATUSCODE, DESCRIPTION FROM STATUS WHERE STATUSCATEGORY = ?";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, StatusVarList.GENESTATUCAT);
            rs = ps.executeQuery();

            while (rs.next()) {

                statusMap.put(rs.getString("STATUSCODE"), rs.getString("DESCRIPTION"));
            }
            statusMap = sortAlphabetically(statusMap);
            return statusMap;

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

    public Map<String, String> getCurrencyTypes(Connection cmsCon) throws Exception {
        Map<String, String> currencyMap = new HashMap<String, String>();
        PreparedStatement ps = null;

        try {
            String query = "SELECT CURRENCYNUMCODE,DESCRIPTION FROM CURRENCY";

            ps = cmsCon.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {

                currencyMap.put(rs.getString("CURRENCYNUMCODE"), rs.getString("DESCRIPTION"));
            }
            currencyMap = sortAlphabetically(currencyMap);
            return currencyMap;

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

    public Map<String, String> getCardStatus(Connection cmsCon) throws Exception {
        Map<String, String> cardStatusMap = new HashMap<String, String>();
        PreparedStatement ps = null;

        try {
            String query = "SELECT STATUSCODE, DESCRIPTION FROM STATUS WHERE STATUSCATEGORY = ?";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, StatusVarList.CARD_STATUS_CATEGORY);
            rs = ps.executeQuery();

            while (rs.next()) {

                cardStatusMap.put(rs.getString("STATUSCODE"), rs.getString("DESCRIPTION"));
            }
            cardStatusMap = sortAlphabetically(cardStatusMap);
            return cardStatusMap;

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

    private Map<String, String> sortAlphabetically(Map<String, String> input) {
        Map<String, String> tempMap = new HashMap<String, String>();
        for (String wsState : input.keySet()) {
            tempMap.put(wsState, input.get(wsState));
        }
        List<String> mapKeys = new ArrayList<String>(tempMap.keySet());
        List<String> mapValues = new ArrayList<String>(tempMap.values());
        HashMap<String, String> sortedMap = new LinkedHashMap<String, String>();
        TreeSet<String> sortedSet = new TreeSet<String>(mapValues);
        Object[] sortedArray = sortedSet.toArray();
        int size = sortedArray.length;
        for (int i = 0; i < size; i++) {
            sortedMap.put(mapKeys.get(mapValues.indexOf(sortedArray[i])),
                    (String) sortedArray[i]);
        }
        return sortedMap;
    }

    public boolean insertCaseType(Connection con, CaseTypeBean caseTypeBean) throws Exception {
        boolean success = false;
        PreparedStatement ps = null;
        try {

            String query = "INSERT INTO COLLECTORCASE (CASETYPECODE,"
                    + "DESCRIPTION,CURRENCY,STATUS,ENTRYCRITERIA,OVERDUEAMOUNTTYPE,"
                    + "OVERDUEAMOUNT,OVERDUEPERIOD,SEVERITYVALUE,ENTRYACCOUNTSTATUS,ENTRYCARDSTATUS,"
                    + "EXITOVERDUEAMOUNT,EXITOVERDUEAPERIOD,EXITACCOUNTSTATUS,EXITCARDSTATUS,AUTOMATESTATUS,"
                    + "MANUALSTATUS,REMARKS,OVERLIMITAMOUNTTYPE,OVERLIMITAMOUNT,EXITOVERLIMITAMOUNT)"
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            ps = con.prepareStatement(query);
            ps.setString(1, caseTypeBean.getCaseTypeCode());
            ps.setString(2, caseTypeBean.getDescription());
            ps.setString(3, caseTypeBean.getCurrencyTypeCode());
            ps.setString(4, caseTypeBean.getStatusCode());
            ps.setString(5, caseTypeBean.getEntryCriteriaCode());
            ps.setString(6, caseTypeBean.getOverDueAmountType());
            ps.setString(7, caseTypeBean.getEntryOverDueAmount());
            ps.setString(8, caseTypeBean.getEntryOverDuePeriodCode());
            ps.setString(9, caseTypeBean.getSeverityValue());
            ps.setString(10, caseTypeBean.getEntryAccStatusCode());
            ps.setString(11, caseTypeBean.getEntryCardStatusCode());
            ps.setString(12, caseTypeBean.getExitOverDueAmount());
            ps.setString(13, caseTypeBean.getExitOverDuePeriodCode());
            ps.setString(14, caseTypeBean.getExitAccStatusCode());
            ps.setString(15, caseTypeBean.getExitCardStatusCode());
            ps.setString(16, caseTypeBean.getProcessAutomatedStatus());
            ps.setString(17, caseTypeBean.getProcessManualStatus());
            ps.setString(18, caseTypeBean.getRemarks());
            ps.setString(19, caseTypeBean.getOverLimitAmountType());
            ps.setString(20, caseTypeBean.getEntryOverLimitAmount());
            ps.setString(21, caseTypeBean.getExitOverLimitAmount());

            int row = ps.executeUpdate();
            if (row == 1) {
                success = true;
            }

        } catch (SQLException ex) {
            LogFileCreator.writeErrorToLog(ex);
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
        return success;
    }

    public List<CaseTypeBean> getAllCases(Connection cmsCon) throws SQLException, Exception {
        List<CaseTypeBean> caseList = new ArrayList<CaseTypeBean>();
        PreparedStatement ps = null;

        try {
            String query = "SELECT CC.CASETYPECODE,CC.DESCRIPTION,CR.DESCRIPTION AS CURRENCY,"
                    + "CC.SEVERITYVALUE,CC.ENTRYCRITERIA,ST.DESCRIPTION AS STATUS,"
                    + "CC.CURRENCY AS CURRENCYCODE,CC.STATUS AS STATUSCODE,CC.ENTRYCRITERIA AS CRITERIACODE,"
                    + "CC.OVERDUEPERIOD AS ENTRYOVERDUEPERIOD,CC.OVERDUEAMOUNTTYPE,"
                    + "CC.OVERDUEAMOUNT AS ENTRYOVERDUEAMOUNT,CC.OVERLIMITAMOUNTTYPE,"
                    + "CC.OVERLIMITAMOUNT AS ENTRYOVERLIMITAMOUNT,CC.ENTRYACCOUNTSTATUS AS ENTRYACCOUNTSTATUSCODE,"
                    + "CC.ENTRYCARDSTATUS AS ENTRYCARDSTATUSCODE,CC.EXITOVERDUEAMOUNT,CC.EXITOVERDUEAPERIOD,"
                    + "CC.EXITOVERLIMITAMOUNT,CC.EXITACCOUNTSTATUS AS EXITACCOUNTSTATUSCODE,"
                    + "CC.EXITCARDSTATUS AS EXITCARDSTATUSCODE,CC.AUTOMATESTATUS,CC.MANUALSTATUS,"
                    + "CC.REMARKS FROM COLLECTORCASE CC,CURRENCY CR,STATUS ST "
                    + "WHERE CC.CURRENCY = CR.CURRENCYNUMCODE AND CC.STATUS = ST.STATUSCODE";

            ps = cmsCon.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                CaseTypeBean caseType = new CaseTypeBean();

                caseType.setCaseTypeCode(rs.getString("CASETYPECODE"));
                caseType.setDescription(rs.getString("DESCRIPTION"));
                caseType.setCurrencyTypeDes(rs.getString("CURRENCY"));
                caseType.setSeverityValue(rs.getString("SEVERITYVALUE"));
                caseType.setEntryCriteriaDes(rs.getString("ENTRYCRITERIA"));
                caseType.setStatusDes(rs.getString("STATUS"));

                caseType.setCurrencyTypeCode(rs.getString("CURRENCYCODE"));
                caseType.setStatusCode(rs.getString("STATUSCODE"));
                caseType.setEntryCriteriaCode(rs.getString("CRITERIACODE"));
                caseType.setEntryOverDuePeriodCode(rs.getString("ENTRYOVERDUEPERIOD"));
                caseType.setOverDueAmountType(rs.getString("OVERDUEAMOUNTTYPE"));
                caseType.setEntryOverDueAmount(rs.getString("ENTRYOVERDUEAMOUNT"));
                caseType.setOverLimitAmountType(rs.getString("OVERLIMITAMOUNTTYPE"));
                caseType.setEntryOverLimitAmount(rs.getString("ENTRYOVERLIMITAMOUNT"));
                caseType.setEntryAccStatusCode(rs.getString("ENTRYACCOUNTSTATUSCODE"));
                caseType.setEntryCardStatusCode(rs.getString("ENTRYCARDSTATUSCODE"));
                caseType.setExitOverDueAmount(rs.getString("EXITOVERDUEAMOUNT"));
                caseType.setExitOverDuePeriodCode(rs.getString("EXITOVERDUEAPERIOD"));
                caseType.setExitOverLimitAmount(rs.getString("EXITOVERLIMITAMOUNT"));
                caseType.setExitAccStatusCode(rs.getString("EXITACCOUNTSTATUSCODE"));
                caseType.setExitCardStatusCode(rs.getString("EXITCARDSTATUSCODE"));
                caseType.setProcessAutomatedStatus(rs.getString("AUTOMATESTATUS"));
                caseType.setProcessManualStatus(rs.getString("MANUALSTATUS"));
                caseType.setRemarks(rs.getString("REMARKS"));

                caseList.add(caseType);
            }

            return caseList;

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

    public boolean deleteCase(Connection con, String caseTypeCode) throws SQLException, Exception {
        PreparedStatement ps = null;
        boolean success = false;
        try {
            String query = "DELETE FROM COLLECTORCASE WHERE CASETYPECODE =?";

            ps = con.prepareStatement(query);
            ps.setString(1, caseTypeCode);

            int i = ps.executeUpdate();
            if (i == 1) {
                success = true;
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
        return success;
    }

    public boolean updateCaseType(Connection con, CaseTypeBean caseTypeBean) throws Exception {
        boolean success = false;
        PreparedStatement ps = null;
        try {

            String query = "UPDATE COLLECTORCASE SET CASETYPECODE=?,DESCRIPTION=?,CURRENCY=?,"
                    + "STATUS=?,ENTRYCRITERIA=?,OVERDUEAMOUNTTYPE=?,OVERDUEAMOUNT=?,OVERDUEPERIOD=?,"
                    + "SEVERITYVALUE=?,ENTRYACCOUNTSTATUS=?,ENTRYCARDSTATUS=?,EXITOVERDUEAMOUNT=?,"
                    + "EXITOVERDUEAPERIOD=?,EXITACCOUNTSTATUS=?,EXITCARDSTATUS=?,AUTOMATESTATUS=?,"
                    + "MANUALSTATUS=?,REMARKS=?,OVERLIMITAMOUNTTYPE=?,OVERLIMITAMOUNT=?,"
                    + "EXITOVERLIMITAMOUNT=? WHERE CASETYPECODE=?";

            ps = con.prepareStatement(query);
            ps.setString(1, caseTypeBean.getCaseTypeCode());
            ps.setString(2, caseTypeBean.getDescription());
            ps.setString(3, caseTypeBean.getCurrencyTypeCode());
            ps.setString(4, caseTypeBean.getStatusCode());
            ps.setString(5, caseTypeBean.getEntryCriteriaCode());
            ps.setString(6, caseTypeBean.getOverDueAmountType());
            ps.setString(7, caseTypeBean.getEntryOverDueAmount());
            ps.setString(8, caseTypeBean.getEntryOverDuePeriodCode());
            ps.setString(9, caseTypeBean.getSeverityValue());
            ps.setString(10, caseTypeBean.getEntryAccStatusCode());
            ps.setString(11, caseTypeBean.getEntryCardStatusCode());
            ps.setString(12, caseTypeBean.getExitOverDueAmount());
            ps.setString(13, caseTypeBean.getExitOverDuePeriodCode());
            ps.setString(14, caseTypeBean.getExitAccStatusCode());
            ps.setString(15, caseTypeBean.getExitCardStatusCode());
            ps.setString(16, caseTypeBean.getProcessAutomatedStatus());
            ps.setString(17, caseTypeBean.getProcessManualStatus());
            ps.setString(18, caseTypeBean.getRemarks());
            ps.setString(19, caseTypeBean.getOverLimitAmountType());
            ps.setString(20, caseTypeBean.getEntryOverLimitAmount());
            ps.setString(21, caseTypeBean.getExitOverLimitAmount());
            ps.setString(22, caseTypeBean.getCaseTypeCode());

            int row = ps.executeUpdate();
            if (row == 1) {
                success = true;
            }

        } catch (SQLException ex) {
            LogFileCreator.writeErrorToLog(ex);
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
        return success;
    }

    public CaseTypeBean getCaseById(Connection cmsCon, String caseTypeCode) throws Exception {
        CaseTypeBean caseTypeBean = new CaseTypeBean();
        PreparedStatement ps = null;

        try {
            String query = "SELECT CC.CASETYPECODE,CC.DESCRIPTION,CR.DESCRIPTION AS CURRENCY,"
                    + "CC.SEVERITYVALUE,CC.ENTRYCRITERIA,ST.DESCRIPTION AS STATUS,CC.OVERDUEPERIOD AS ENTRYOVERDUEPERIOD,"
                    + "CC.OVERDUEAMOUNTTYPE,CC.OVERDUEAMOUNT AS ENTRYOVERDUEAMOUNT,CC.OVERLIMITAMOUNTTYPE,"
                    + "CC.OVERLIMITAMOUNT AS ENTRYOVERLIMITAMOUNT,ST1.DESCRIPTION AS ENTRYACCOUNTSTATUSDES,"
                    + "ST2.DESCRIPTION AS ENTRYCARDSTATUSDES,CC.EXITOVERDUEAMOUNT,CC.EXITOVERDUEAPERIOD,"
                    + "CC.EXITOVERLIMITAMOUNT,ST3.DESCRIPTION AS EXITACCOUNTSTATUSDES,ST4.DESCRIPTION AS EXITCARDSTATUSDES,"
                    + "CC.AUTOMATESTATUS,CC.MANUALSTATUS,CC.REMARKS FROM COLLECTORCASE CC,CURRENCY CR,STATUS ST,STATUS ST1,"
                    + "STATUS ST2,STATUS ST3,STATUS ST4 WHERE CC.CURRENCY = CR.CURRENCYNUMCODE AND CC.STATUS = ST.STATUSCODE "
                    + "AND CC.ENTRYACCOUNTSTATUS = ST1.STATUSCODE AND CC.ENTRYCARDSTATUS = ST2.STATUSCODE AND "
                    + "CC.EXITACCOUNTSTATUS = ST3.STATUSCODE AND CC.EXITCARDSTATUS = ST4.STATUSCODE "
                    + "AND CC.CASETYPECODE = ?";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, caseTypeCode);
            rs = ps.executeQuery();

            while (rs.next()) {

                caseTypeBean.setCaseTypeCode(rs.getString("CASETYPECODE"));
                caseTypeBean.setDescription(rs.getString("DESCRIPTION"));
                caseTypeBean.setCurrencyTypeDes(rs.getString("CURRENCY"));
                caseTypeBean.setStatusDes(rs.getString("STATUS"));
                caseTypeBean.setEntryCriteriaDes(rs.getString("ENTRYCRITERIA"));
                caseTypeBean.setOverDueAmountType(rs.getString("OVERDUEAMOUNTTYPE"));
                caseTypeBean.setEntryOverDueAmount(rs.getString("ENTRYOVERDUEAMOUNT"));
                caseTypeBean.setEntryOverDuePeriodCode(rs.getString("ENTRYOVERDUEPERIOD"));
                caseTypeBean.setSeverityValue(rs.getString("SEVERITYVALUE"));
                caseTypeBean.setEntryAccStatusDes(rs.getString("ENTRYACCOUNTSTATUSDES"));
                caseTypeBean.setEntryCardStatusDes(rs.getString("ENTRYCARDSTATUSDES"));
                caseTypeBean.setExitOverDueAmount(rs.getString("EXITOVERDUEAMOUNT"));
                caseTypeBean.setExitOverDuePeriodCode(rs.getString("EXITOVERDUEAPERIOD"));
                caseTypeBean.setExitAccStatusDes(rs.getString("EXITACCOUNTSTATUSDES"));
                caseTypeBean.setExitCardStatusDes(rs.getString("EXITCARDSTATUSDES"));
                caseTypeBean.setProcessAutomatedStatus(rs.getString("AUTOMATESTATUS"));
                caseTypeBean.setProcessManualStatus(rs.getString("MANUALSTATUS"));
                caseTypeBean.setRemarks(rs.getString("REMARKS"));
                caseTypeBean.setOverLimitAmountType(rs.getString("OVERLIMITAMOUNTTYPE"));
                caseTypeBean.setEntryOverLimitAmount(rs.getString("ENTRYOVERLIMITAMOUNT"));
                caseTypeBean.setExitOverLimitAmount(rs.getString("EXITOVERLIMITAMOUNT"));

            }
            return caseTypeBean;

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
}
