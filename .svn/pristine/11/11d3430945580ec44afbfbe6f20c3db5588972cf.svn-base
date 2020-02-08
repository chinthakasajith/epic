/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.collection.persistance;

import com.epic.cms.admin.controlpanel.securityquesmgt.bean.SecurityQuestionBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardBinBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.bean.CaseTypeBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.bean.LadderBean;
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
 * @author ruwan_e
 */
public class LadderPersistance {

    private List<CardBinBean> cardBinBeanLst = null;

    public Map<String, String> getStatus(Connection cmsCon) throws Exception {
        Map<String, String> statusMap = new HashMap<String, String>();
        PreparedStatement ps = null;
        ResultSet rs = null;

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

    public CaseTypeBean getCaseById(Connection CMSCon) throws SQLException, Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;

        CaseTypeBean caseBean = null;

        try {
            caseBean = new CaseTypeBean();
            String query = "select * from CMSBACKENDDEV.COLLECTORCASE where ";
            ps = CMSCon.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                CaseTypeBean bean = new CaseTypeBean();
                bean.setCaseTypeCode(rs.getString("CASETYPECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return caseBean;
    }

    public Map<String, String> getAllCasesMap(Connection CMSCon) throws SQLException, Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;

        Map<String, String> questionList = null;

        try {
            questionList = new HashMap<String, String>();
            String query = "select CASETYPECODE, DESCRIPTION  from CMSBACKENDDEV.COLLECTORCASE where STATUS='ACT'";
            ps = CMSCon.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                questionList.put(rs.getString("CASETYPECODE"), rs.getString("DESCRIPTION"));
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return questionList;
    }

    public ArrayList<CaseTypeBean> getAllCases(Connection CMSCon) throws SQLException, Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<CaseTypeBean> questionList = null;

        try {
            questionList = new ArrayList<CaseTypeBean>();
            String query = "select CASETYPECODE, DESCRIPTION  from COLLECTORCASE where STATUS='ACT'";
            ps = CMSCon.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                CaseTypeBean bean = new CaseTypeBean();
                bean.setCaseTypeCode(rs.getString("CASETYPECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                questionList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return questionList;
    }

    public ArrayList<CaseTypeBean> getCasesByLadderId(Connection CMSCon, long id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            List<SecurityQuestionBean> questionList = new ArrayList<SecurityQuestionBean>();
            String query = "SELECT * FROM COLLECTORLADDER";
            ps = CMSCon.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
//                SecurityQuestionBean bean = new SecurityQuestionBean();
//
//                bean.setQuestionNo(rs.getString("QUESTIONNO"));
//                bean.setQuestion(rs.getString("QUESTION"));
//                bean.setPriorityLevel(rs.getString("PRIORITYLEVEL"));
//                bean.setPriorityDes(rs.getString("PRIORITY_DES"));
//                bean.setIssueAcquireStatus(rs.getString("ISSUACCSTATUS"));
//                bean.setQuestionType(rs.getString("QUESTIONTYPE"));
//                bean.setTableName(rs.getString("TABLENAME"));
//                bean.setField1(rs.getString("FIELDNAME1"));
//                bean.setField2(rs.getString("FIELDNAME2"));
//                bean.setField3(rs.getString("FIELDNAME3"));
//                bean.setField4(rs.getString("FIELDNAME4"));
//                bean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
//                bean.setLastUpdatedTime(rs.getTimestamp("LASTUPDATEDTIME"));
//                bean.setCreatedTime(rs.getTimestamp("CREATEDTIME"));
//                bean.setStatus(rs.getString("STATUS"));
//                bean.setStatusDes(rs.getString("STATUS_DES"));
//
//                caseBean.add(bean);
            }
//            return caseBean;
        } catch (SQLException ex) {
//            throw ex;
        } finally {
            try {
                if (ps != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
//                throw e;
            }
        }
        return new ArrayList<CaseTypeBean>();
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

    public Map<String, String> getCardTypes(Connection CMSCon) throws SQLException, Exception {
        Map<String, String> cardTypesMap = new HashMap<String, String>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "select CARDTYPECODE, DESCRIPTION "
                    + "from CMSBACKENDDEV.CARDTYPE where STATUS='ACT'";

            ps = CMSCon.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {

                cardTypesMap.put(rs.getString("CARDTYPECODE"), rs.getString("DESCRIPTION"));
            }
            cardTypesMap = sortAlphabetically(cardTypesMap);
            return cardTypesMap;

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

    public Map<String, String> getAllCardProducts(Connection CMSCon) throws SQLException, Exception {
        Map<String, String> cardProductsMap = new HashMap<String, String>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "select PRODUCTCODE, DESCRIPTION from CMSBACKENDDEV.CARDPRODUCT";

            ps = CMSCon.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {

                cardProductsMap.put(rs.getString("PRODUCTCODE"), rs.getString("DESCRIPTION"));
            }
            cardProductsMap = sortAlphabetically(cardProductsMap);
            return cardProductsMap;

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

    public Map<String, String> getCardProductsByCardType(String cardType, Connection CMSCon) throws SQLException, Exception {
        Map<String, String> cardProductsMap = new HashMap<String, String>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "select PRODUCTCODE, DESCRIPTION,  CARDTYPE from CARDPRODUCT  "
                    + "WHERE CARDTYPE = " + cardType;

            ps = CMSCon.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                cardProductsMap.put(rs.getString("PRODUCTCODE"), rs.getString("DESCRIPTION"));
            }
            cardProductsMap = sortAlphabetically(cardProductsMap);
            return cardProductsMap;

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

    public boolean createLadder(LadderBean bean, Connection CMSCon) throws Exception {
        boolean b = create(bean, CMSCon);
        boolean a = false;
        Map<String, String> caseTypes = bean.getCaseTypes();

        for (Map.Entry<String, String> entry : caseTypes.entrySet()) {
            a = addCases(bean.getCode(), entry.getKey(), CMSCon);
        }

        if (a && b) {
            return true;
        }
        return false;
    }

    public boolean create(LadderBean bean, Connection CMSCon) throws Exception {
        Boolean success = false;
        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO COLLECTORLADDER (LADDERCODE,DESCRIPTION,STATUS,CARDTYPE,CARDPRODUCT,LASTUPDATEDUSER,CREATEDTIME,LASTUPDATEDTIME,CARDTYPEALLSTATUS,CARDPRODUCTALLSTATUS) "
                    + "VALUES (?,?,?,?,?,?,SYSDATE,SYSDATE,?,?)";

            ps = CMSCon.prepareStatement(query);

            ps.setString(1, bean.getCode());
            ps.setString(2, bean.getDescription());
            ps.setString(3, bean.getStatus());
            ps.setString(4, bean.getCardType());
            ps.setString(5, bean.getCardProduct());
            ps.setString(6, bean.getLastUpdatedUser());

            if (bean.getCardType().equals("ALL")) {
                ps.setString(7, "YES");
            } else {
                ps.setString(7, "NO");
            }

            if (bean.getCardProduct() == null) {
                ps.setString(8, "YES");
            } else {
                ps.setString(8, "NO");
            }


            ps.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
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

    public boolean addCases(String ladderCode, String caseId, Connection conn) throws SQLException, Exception {

        Boolean success = false;
        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO COLLECTORLADDERCASE (LADDERID, CASEID) VALUES (?, ?)";
            ps = conn.prepareStatement(query);

            ps.setString(1, ladderCode);
            ps.setString(2, caseId);
            ps.executeUpdate();

            success = true;
        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
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

    public boolean updateLadder(LadderBean bean, Connection CMSCon) throws Exception {
        boolean b = update(bean, CMSCon);
        boolean a = updateCases(bean.getCode(), bean.getCaseTypes(), CMSCon);
        if (a && b) {
            return true;
        }
        return false;
    }

    public boolean update(LadderBean bean, Connection CMSCon) throws Exception {
        Boolean success = false;
        PreparedStatement ps = null;

        try {
            String query = "UPDATE COLLECTORLADDER SET DESCRIPTION = ?,STATUS = ?,"
                    + "CARDTYPE = ?,CARDPRODUCT = ?, LASTUPDATEDUSER = ?,"
                    + " CARDTYPEALLSTATUS =?,CARDPRODUCTALLSTATUS = ? WHERE LADDERCODE = ? ";

            ps = CMSCon.prepareStatement(query);

            ps.setString(1, bean.getDescription());
            ps.setString(2, bean.getStatus());
            ps.setString(3, bean.getCardType());
            ps.setString(4, bean.getCardProduct());
            ps.setString(5, bean.getLastUpdatedUser());
            ps.setString(6, boolToYesNo(bean.getCardType() == "ALL"));
            ps.setString(7, boolToYesNo(bean.getCardProduct() == "ALL"));
            ps.setString(8, bean.getCode());

            ps.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            ex.printStackTrace();
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

    public boolean updateCases(String ladderCode, Map<String, String> caseTypes, Connection conn) throws SQLException, Exception {

        Boolean success = false;
        PreparedStatement ps = null;

        try {
            deleteCases(ladderCode, conn);

            String query = "INSERT INTO COLLECTORLADDERCASE (LADDERID, CASEID) VALUES (?, ?)";
            ps = conn.prepareStatement(query);

            for (Map.Entry<String, String> entry : caseTypes.entrySet()) {
                ps.setString(1, ladderCode);
                ps.setString(2, entry.getKey());
                ps.executeUpdate();
            }

            success = true;
        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
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

    public boolean deleteCases(String ladderCode, Connection conn) throws SQLException, Exception {

        Boolean success = false;
        PreparedStatement ps = null;

        try {
            String query = "DELETE FROM COLLECTORLADDERCASE WHERE LADDERID = ?";
            ps = conn.prepareStatement(query);

            ps.setString(1, ladderCode);
            ps.executeUpdate();

            success = true;
        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
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

    public ArrayList<LadderBean> getAllLadders(Connection CMSCon) throws SQLException, Exception {

        ArrayList<LadderBean> ladders = new ArrayList<LadderBean>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "SELECT CL.LADDERCODE, CL.DESCRIPTION, CL.STATUS, "
                    + "CL.CARDPRODUCT, CL.CARDTYPE, ST.DESCRIPTION AS STATUSDES, "
                    + "CP.DESCRIPTION AS CARDPRODUCTDES, CT.DESCRIPTION AS CARDTYPEDES "
                    + "FROM COLLECTORLADDER CL, STATUS ST, CARDPRODUCT CP, CARDTYPE CT "
                    + "WHERE ST.STATUSCODE = CL.STATUS AND "
                    + "CP.PRODUCTCODE = CL.CARDPRODUCT AND CT.CARDTYPECODE = CL.CARDTYPE";

            ps = CMSCon.prepareStatement(query);
            rs = ps.executeQuery();


            while (rs.next()) {
                LadderBean l = new LadderBean();

                l.setCode(rs.getString("LADDERCODE"));
                l.setDescription(rs.getString("DESCRIPTION"));
                l.setStatus(rs.getString("STATUS"));
                l.setCardProduct(rs.getString("CARDPRODUCT"));
                l.setCardType(rs.getString("CARDTYPE"));
                l.setStatusDesc(rs.getString("STATUSDES"));
                l.setCardProductDesc(rs.getString("CARDPRODUCTDES"));
                l.setCardTypeDesc(rs.getString("CARDTYPEDES"));
                ladders.add(l);
            }

            return ladders;

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

//        ArrayList<LadderBean> ladders = new ArrayList<LadderBean>();
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//
//        try {
//            String query = "select * from COLLECTORLADDER";
//
//            ps = CMSCon.prepareStatement(query);
//            rs = ps.executeQuery();
//
//
//            while (rs.next()) {
//                LadderBean l = new LadderBean();
//
//                l.setCode(rs.getString("LADDERCODE"));
//                l.setDescription(rs.getString("DESCRIPTION"));
//                l.setStatus(rs.getString("STATUS"));
//                l.setCardProduct(rs.getString("CARDPRODUCT"));
//                l.setCardType(rs.getString("CARDTYPE"));
//
//                ladders.add(l);
//            }
//
//            return ladders;
//
//        } catch (SQLException ex) {
//            throw ex;
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                    ps.close();
//                }
//            } catch (Exception e) {
//                throw e;
//            }
//        }
    }

    public ArrayList<LadderBean> getAllLaddersDesc(Connection CMSCon) throws SQLException, Exception {
        ArrayList<LadderBean> ladders = new ArrayList<LadderBean>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "SELECT CL.LADDERCODE, CL.DESCRIPTION, CL.STATUS, "
                    + "CL.CARDPRODUCT, CL.CARDTYPE, ST.DESCRIPTION AS STATUSDES, "
                    + "CP.DESCRIPTION AS CARDPRODUCTDES, CT.DESCRIPTION AS CARDTYPEDES "
                    + "FROM COLLECTORLADDER CL, STATUS ST, CARDPRODUCT CP, CARDTYPE CT "
                    + "WHERE ST.STATUSCODE = CL.STATUS AND "
                    + "CP.PRODUCTCODE = CL.CARDPRODUCT AND CT.CARDTYPECODE = CL.CARDTYPE";

            ps = CMSCon.prepareStatement(query);
            rs = ps.executeQuery();


            while (rs.next()) {
                LadderBean l = new LadderBean();

                l.setCode(rs.getString("LADDERCODE"));
                l.setDescription(rs.getString("DESCRIPTION"));
                l.setStatus(rs.getString("STATUS"));
                l.setCardProduct(rs.getString("CARDPRODUCT"));
                l.setCardType(rs.getString("CARDTYPE"));
                l.setStatusDesc(rs.getString("STATUSDES"));
                l.setCardProductDesc(rs.getString("CARDPRODUCTDES"));
                l.setCardTypeDesc(rs.getString("CARDTYPEDES"));
                ladders.add(l);
            }

            return ladders;

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

    public ArrayList<LadderBean> getAllLadders2(Connection CMSCon) throws SQLException, Exception {
        ArrayList<LadderBean> ladders = new ArrayList<LadderBean>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "SELECT CL.LADDERCODE,CL.DESCRIPTION AS LADDERDES, "
                    + "CL.STATUS, ST.DESCRIPTION AS STATUSDES, CL.CARDTYPE, "
                    + "CT.DESCRIPTION AS CARDTYPEDES, CL.CARDPRODUCT, "
                    + "CP.DESCRIPTION AS CARDPRODUCTDES, CL.CARDPRODUCTALLSTATUS, "
                    + "CL.CARDTYPEALLSTATUS "
                    + "FROM COLLECTORLADDER CL "
                    + "LEFT JOIN CARDTYPE CT ON CL.CARDTYPE = CT.CARDTYPECODE "
                    + "LEFT JOIN CARDPRODUCT CP ON CL.CARDPRODUCT = CP.PRODUCTCODE "
                    + "LEFT JOIN STATUS ST ON ST.STATUSCODE = CL.STATUS";

            ps = CMSCon.prepareStatement(query);
            rs = ps.executeQuery();


            while (rs.next()) {
                LadderBean l = new LadderBean();

                l.setCode(rs.getString("LADDERCODE"));
                l.setDescription(rs.getString("LADDERDES"));
                l.setStatus(rs.getString("STATUS"));
                l.setStatusDesc(rs.getString("STATUSDES"));
                l.setCardType(rs.getString("CARDTYPE"));
                l.setCardTypeDesc(rs.getString("CARDTYPEDES"));
                l.setCardProduct(rs.getString("CARDPRODUCT"));
                l.setCardProductDesc(rs.getString("CARDPRODUCTDES"));
                l.setAllCardProducts(yesNoToBool(rs.getString("CARDPRODUCTALLSTATUS")));
                l.setAllCardTypes(yesNoToBool(rs.getString("CARDTYPEALLSTATUS")));
                ladders.add(l);
            }

            return ladders;

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

    private String boolToYesNo(boolean b) {
        if (b) {
            return "YES";
        }
        return "NO";
    }

    private boolean yesNoToBool(String b) {
        return "YES".equals(b);
    }

    public boolean isUniqueCode(String code, Connection CMSCon) throws SQLException, Exception {
        ArrayList<LadderBean> ladders = new ArrayList<LadderBean>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "select * from COLLECTORLADDER where LADDERCODE='"
                    + code + "'";

            ps = CMSCon.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }

            return false;

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

    public List<CaseTypeBean> getNotAssignedCaseTypeDescriptions(Connection con, String ladderCode) throws SQLException, Exception {

        List<CaseTypeBean> caseTypeBeanList = new ArrayList<CaseTypeBean>();
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            String query = "SELECT C.CASETYPECODE, C.DESCRIPTION FROM "
                    + "COLLECTORCASE C WHERE C.CASETYPECODE "
                    + "NOT IN (SELECT CASEID FROM COLLECTORLADDERCASE WHERE LADDERID=?);";

            ps = con.prepareStatement(query);
            ps.setString(1, ladderCode);
            rs = ps.executeQuery();

            while (rs.next()) {
                CaseTypeBean caseTypeBean = new CaseTypeBean();
                caseTypeBean.setCaseTypeCode(rs.getString(1));
                caseTypeBean.setDescription(rs.getString(2));

                caseTypeBeanList.add(caseTypeBean);
            }

            return caseTypeBeanList;
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

    public Map<String, String> caseArrayToMap(String[] cases, Connection CMSCon) throws SQLException, Exception {
        Map<String, String> allCasesMap = this.getAllCasesMap(CMSCon);
        for (int i = 0; i < allCasesMap.size(); i++) {
            if (!allCasesMap.containsKey(cases[i])) {
                allCasesMap.remove(cases[i]);
            }
        }
        return allCasesMap;
    }

    public List<CaseTypeBean> getNotAssignedCaseTypeList(String ladderCode, Connection con) throws SQLException, Exception {
        List<CaseTypeBean> caseTypeBeanList = new ArrayList<CaseTypeBean>();
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
//            String query = "SELECT C.CASETYPECODE, C.DESCRIPTION FROM "
//                    + "COLLECTORCASE C WHERE C.CASETYPECODE "
//                    + "NOT IN (SELECT CASEID FROM COLLECTORLADDERCASE WHERE LADDERID=?);";

            String query = "SELECT CASETYPECODE,DESCRIPTION FROM COLLECTORCASE "
                    + "WHERE CASETYPECODE NOT IN "
                    + "(SELECT CASEID FROM COLLECTORLADDERCASE WHERE LADDERID=?)";

            ps = con.prepareStatement(query);
            ps.setString(1, ladderCode);
            rs = ps.executeQuery();

            while (rs.next()) {
                CaseTypeBean caseTypeBean = new CaseTypeBean();
                caseTypeBean.setCaseTypeCode(rs.getString(1));
                caseTypeBean.setDescription(rs.getString(2));

                caseTypeBeanList.add(caseTypeBean);
            }

            return caseTypeBeanList;
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

    public List<CaseTypeBean> getAssignedCaseTypeList(String ladderCode, Connection con) throws SQLException, Exception {
        List<CaseTypeBean> caseTypeBeanList = new ArrayList<CaseTypeBean>();
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
//            String query = "SELECT C.CASETYPECODE, C.DESCRIPTION FROM "
//                    + "COLLECTORCASE C WHERE C.CASETYPECODE "
//                    + "NOT IN (SELECT CASEID FROM COLLECTORLADDERCASE WHERE LADDERID=?);";

            String query = "SELECT C.CASETYPECODE, C.DESCRIPTION FROM COLLECTORLADDERCASE CC,"
                    + "COLLECTORCASE C WHERE CC.CASEID = C.CASETYPECODE AND CC.LADDERID=?";

            ps = con.prepareStatement(query);
            ps.setString(1, ladderCode);
            rs = ps.executeQuery();

            while (rs.next()) {
                CaseTypeBean caseTypeBean = new CaseTypeBean();
                caseTypeBean.setCaseTypeCode(rs.getString(1));
                caseTypeBean.setDescription(rs.getString(2));

                caseTypeBeanList.add(caseTypeBean);
            }

            return caseTypeBeanList;
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

    public List<CaseTypeBean> getCasesByLadderCode(String ladderCode, Connection CMSCon) throws SQLException, Exception {
        List<CaseTypeBean> caseTypeBeanList = new ArrayList<CaseTypeBean>();
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            String query = "SELECT C.CASETYPECODE, C.DESCRIPTION FROM COLLECTORCASE C "
                    + "WHERE C.CASETYPECODE IN (SELECT CASEID FROM COLLECTORLADDERCASE "
                    + "WHERE LADDERID ='" + ladderCode + "')";

            ps = CMSCon.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                CaseTypeBean caseTypeBean = new CaseTypeBean();
                caseTypeBean.setCaseTypeCode(rs.getString(1));
                caseTypeBean.setDescription(rs.getString(2));

                caseTypeBeanList.add(caseTypeBean);
            }

            return caseTypeBeanList;
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

    public Map<String, String> getCaseDescription(String[] caseDesc, Connection CMSCon) throws SQLException, Exception {
        Map<String, String> caseTypeBeanList = new HashMap<String, String>();
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            String query = "SELECT C.CASETYPECODE, C.DESCRIPTION FROM COLLECTORCASE C WHERE C.CASETYPECODE = ?";

            ps = CMSCon.prepareStatement(query);

            for (int i = 0; i < caseDesc.length; i++) {
                ps.setString(1, caseDesc[i]);
                rs = ps.executeQuery();
                while (rs.next()) {
                    caseTypeBeanList.put(rs.getString("CASETYPECODE"), rs.getString("DESCRIPTION"));
                }

            }

            return caseTypeBeanList;

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

    public boolean deleteLadder(String code, Connection con) throws SQLException, Exception {
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        boolean success = false;
        try {
            String query = "DELETE FROM COLLECTORLADDERCASE WHERE LADDERID = ?";

            ps1 = con.prepareStatement(query);
            ps1.setString(1, code);

            int i = ps1.executeUpdate();

            String query1 = "DELETE FROM COLLECTORLADDER WHERE LADDERCODE = ?";

            ps2 = con.prepareStatement(query1);
            ps2.setString(1, code);

            int j = ps2.executeUpdate();

            if (i >= 1 && j == 1) {
                success = true;
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (ps1 != null) {
                    ps1.close();
                }
                if (ps2 != null) {
                    ps2.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return success;
    }
}
