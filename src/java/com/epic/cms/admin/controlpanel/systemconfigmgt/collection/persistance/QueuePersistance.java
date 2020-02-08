/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.collection.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.bean.CaseTypeBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.bean.LadderBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.bean.QueueBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ruwan_e
 */
public class QueuePersistance {

    public QueuePersistance() {
    }

    public Map<String, String> getAllUserRoles(Connection CMSCon) throws SQLException, Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;

        Map<String, String> userRoleList = null;
        try {
            userRoleList = new HashMap<String, String>();
            String query = "SELECT USERROLE, DESCRIPTION FROM USERROLE WHERE STATUS='ACT'";
            ps = CMSCon.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                userRoleList.put(rs.getString("USERROLE"), rs.getString("DESCRIPTION"));
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
        return userRoleList;
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

    public Map<String, String> getStatus(Connection CMSCon) throws SQLException, Exception {
        Map<String, String> statusMap = new HashMap<String, String>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "SELECT STATUSCODE, DESCRIPTION FROM STATUS WHERE STATUSCATEGORY = ?";

            ps = CMSCon.prepareStatement(query);
            ps.setString(1, StatusVarList.GENESTATUCAT);
            rs = ps.executeQuery();

            while (rs.next()) {

                statusMap.put(rs.getString("STATUSCODE"), rs.getString("DESCRIPTION"));
            }
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

    public ArrayList<QueueBean> getAllQueues(Connection CMSCon) throws SQLException, Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<QueueBean> queueList = null;

        try {
            queueList = new ArrayList<QueueBean>();
            String query = "SELECT Q.QUEUECODE, Q.DESCRIPTION, Q.STATUS, Q.ASSIGNUSERROLE, "
                    + "Q.LADDERCODE, ST.DESCRIPTION AS STATUSDES FROM COLLECTORQUEUE Q, "
                    + "STATUS ST WHERE ST.STATUSCODE = Q.STATUS";
            ps = CMSCon.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                QueueBean bean = new QueueBean();
                bean.setCode(rs.getString("QUEUECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setAssignUserRole(rs.getString("ASSIGNUSERROLE"));
                bean.setStatusDesc(rs.getString("STATUSDES"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setLadderCode(rs.getString("LADDERCODE"));
                queueList.add(bean);
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
        return queueList;
    }

    public boolean createQueue(QueueBean queue, Connection CMSCon) throws Exception {
        PreparedStatement ps = null;

        boolean success = false;

        try {

            String query = "INSERT INTO COLLECTORQUEUE (QUEUECODE,DESCRIPTION,STATUS,"
                    + "ASSIGNUSERROLE,LADDERCODE,LASTUPDATEDUSER) VALUES (?,?,?,?,?,?)";

            ps = CMSCon.prepareStatement(query);
            ps.setString(1, queue.getCode());
            ps.setString(2, queue.getDescription());
            ps.setString(3, queue.getStatus());
            ps.setString(4, queue.getAssignUserRole());
            ps.setString(5, queue.getLadderCode());
            ps.setString(6, queue.getLastUpdatedUser());

            ps.executeUpdate();
            success = true;

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
        return success;
    }

    public boolean addQueueCases(QueueBean queue, Connection CMSCon) throws Exception {
        PreparedStatement ps = null;

        boolean success = false;

        try {

            Map<String, String> caseTypes = queue.getCases();

            for (Map.Entry<String, String> entry : caseTypes.entrySet()) {

                String query = "INSERT INTO COLLECTORQUEUECASE (QUEUEID,CASEID,LADDERCODE) VALUES (?,?,?)";

                ps = CMSCon.prepareStatement(query);
                ps.setString(1, queue.getCode());
                ps.setString(2, entry.getKey());
                ps.setString(3, queue.getLadderCode());

                ps.executeUpdate();

            }
            success = true;

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
        return success;
    }

    public boolean deleteQueue(String code, Connection con) throws SQLException, Exception {
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        boolean success = false;
        try {
            String query = "DELETE FROM COLLECTORQUEUECASE WHERE QUEUEID = ?";

            ps1 = con.prepareStatement(query);
            ps1.setString(1, code);

            int i = ps1.executeUpdate();

            String query1 = "DELETE FROM COLLECTORQUEUE WHERE QUEUECODE = ?";

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

    public List<CaseTypeBean> getAssignedCases(Connection CMSCon, String code) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<CaseTypeBean> cases = null;

        try {
            cases = new ArrayList<CaseTypeBean>();

            String query = "SELECT CC.CASETYPECODE, CC.DESCRIPTION FROM "
                    + "COLLECTORCASE CC,COLLECTORQUEUECASE CQ "
                    + "WHERE CC.CASETYPECODE = CQ.CASEID AND CQ.QUEUEID = ?";

            ps = CMSCon.prepareStatement(query);
            ps.setString(1, code);
            rs = ps.executeQuery();

            while (rs.next()) {
                CaseTypeBean caseTypeBean = new CaseTypeBean();
                caseTypeBean.setCaseTypeCode(rs.getString(1));
                caseTypeBean.setDescription(rs.getString(2));

                cases.add(caseTypeBean);
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
        return cases;
    }

    public List<CaseTypeBean> getNotAssignedCaseTypeList(Connection CMSCon, String code) throws SQLException, Exception {
        List<CaseTypeBean> caseTypeBeanList = new ArrayList<CaseTypeBean>();
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {

            String query = "SELECT CL.CASEID,CC.DESCRIPTION FROM "
                    + "COLLECTORLADDERCASE CL,COLLECTORCASE CC "
                    + "WHERE CL.LADDERID = (SELECT LADDERCODE FROM COLLECTORQUEUE WHERE QUEUECODE=?) "
                    + "AND CL.CASEID NOT IN (SELECT CASEID FROM COLLECTORQUEUECASE WHERE QUEUEID=?) "
                    + "AND CL.CASEID = CC.CASETYPECODE";

            ps = CMSCon.prepareStatement(query);
            ps.setString(1, code);
            ps.setString(2, code);
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

    public boolean updateQueue(QueueBean queue, SystemAuditBean systemAuditBean, Connection CMSCon) throws Exception {
        boolean b = update(queue, CMSCon);
        boolean a = updateCases(queue, CMSCon);

        return a && b;
    }

    private boolean update(QueueBean bean, Connection CMSCon) throws Exception {
        Boolean success = false;
        PreparedStatement ps = null;

        try {
            String query = "UPDATE COLLECTORQUEUE SET "
                    + "ASSIGNUSERROLE=?, DESCRIPTION=?, STATUS=?, LADDERCODE=?,"
                    + "LASTUPDATEDUSER=? WHERE QUEUECODE = ?";

            ps = CMSCon.prepareStatement(query);

            ps.setString(1, bean.getAssignUserRole());
            ps.setString(2, bean.getDescription());
            ps.setString(3, bean.getStatus());
            ps.setString(4, bean.getLadderCode());
            ps.setString(5, bean.getLastUpdatedUser());
            ps.setString(6, bean.getCode());

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

    private boolean updateCases(QueueBean queue, Connection conn) throws Exception {
        Boolean success = false;
        PreparedStatement ps = null;

        try {
            deleteCases(queue.getCode(), conn);

            String query = "INSERT INTO COLLECTORQUEUECASE (QUEUEID, CASEID, LADDERCODE) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(query);

            for (Map.Entry<String, String> entry : queue.getCases().entrySet()) {
                ps.setString(1, queue.getCode());
                ps.setString(2, entry.getKey());
                ps.setString(3, queue.getLadderCode());
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

    public boolean deleteCases(String queueCode, Connection conn) throws SQLException, Exception {

        Boolean success = false;
        PreparedStatement ps = null;

        try {
            String query = "DELETE FROM COLLECTORQUEUECASE WHERE QUEUEID = ?";
            ps = conn.prepareStatement(query);

            ps.setString(1, queueCode);
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
}
