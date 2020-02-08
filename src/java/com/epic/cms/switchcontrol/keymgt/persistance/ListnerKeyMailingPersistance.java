package com.epic.cms.switchcontrol.keymgt.persistance;

import com.epic.cms.switchcontrol.keymgt.bean.ListnerKeyMailBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jeevan
 */
public class ListnerKeyMailingPersistance {

    private ResultSet rs = null;
    private List<ListnerKeyMailBean> searchList = null;

    public List<ListnerKeyMailBean> getAllListnetTypes(Connection cmsCon) throws SQLException, Exception {
        List<ListnerKeyMailBean> listnerTypeList = new ArrayList<ListnerKeyMailBean>();
        PreparedStatement ps = null;
        String query = null;

        query = "SELECT LT.CODE, LT.DESCRIPTION FROM ECMS_ONLINE_LISTENER_TYPE LT";

        try {

            ps = cmsCon.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                ListnerKeyMailBean bean = new ListnerKeyMailBean();

                bean.setListnerTypeCode(rs.getString("CODE"));
                bean.setListnerTypeDesc(rs.getString("DESCRIPTION"));

                listnerTypeList.add(bean);
            }
            return listnerTypeList;

        } catch (SQLException sqlex) {
            throw sqlex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    public List<ListnerKeyMailBean> getAllCommunicationKeys(Connection cmsConOnline) throws SQLException, Exception {
        PreparedStatement ps = null;
        List<ListnerKeyMailBean> comKeyList = new ArrayList<ListnerKeyMailBean>();
        String query;

        query = "SELECT COMKEYID, DESCRIPTION FROM ECMS_ONLINE_COMMUNICATIONKEYS";

        try {

            ps = cmsConOnline.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                ListnerKeyMailBean bean = new ListnerKeyMailBean();

                bean.setComKeyId(rs.getString("COMKEYID"));
                bean.setComKeyDesc(rs.getString("DESCRIPTION"));

                comKeyList.add(bean);
            }
            return comKeyList;

        } catch (SQLException sqlex) {
            throw sqlex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (SQLException sqlex) {
                    throw sqlex;
                }
            }
        }
    }

    public List<ListnerKeyMailBean> searchKeyMailing(Connection cmsConOnline, ListnerKeyMailBean keyMailingBean) throws SQLException, Exception {
        PreparedStatement ps = null;
        String defaultQuery = null;

//        defaultQuery = "SELECT LI.LISTENERID, LI.LISTENERTYPE, LI.DESCRIPTION, LI.STATUS, LI.PERPORT, LI.TERPORT,"
//                + " LI.KEYEXCHANGESTATUS, LI.NOFCONNECTION, LI.TIMEOUT, LI.RUNMODE, CO.DESCRIPTION AS COM_KEY_DESC,"
//                + " LI.STATUSOFACQUIRING, LI.COMKEYID, LI.SECURITYSTATUS"
//                + " FROM ECMS_ONLINE_LISTENERS LI, ECMS_ONLINE_LISTENER_TYPE LT, ECMS_ONLINE_COMMUNICATIONKEYS CO"
//                + " WHERE LI.COMKEYID=CO.COMKEYID AND LT.CODE=LI.LISTENERID";

        defaultQuery = "SELECT LI.DESCRIPTION AS LISTENRE_NAME, LT.DESCRIPTION AS LISTENER_TYPE, CO.DESCRIPTION AS COM_KEY_DESC ,LI.LISTENERID, LI.COMKEYID"
                + " FROM ECMS_ONLINE_LISTENERS LI, ECMS_ONLINE_LISTENER_TYPE LT, ECMS_ONLINE_COMMUNICATIONKEYS CO"
                + " WHERE LI.COMKEYID=CO.COMKEYID AND LT.CODE=LI.LISTENERTYPE ";

        String condition = "";
        System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL"+keyMailingBean.getListnerTypeCode());
        try {

            if (!keyMailingBean.getListnerTypeDesc().isEmpty() || keyMailingBean.getListnerTypeDesc().length() > 0) {
                //          if (!condition.equals("")) {
                condition += " AND ";
                //         }
                condition += "UPPER(LI.DESCRIPTION) LIKE '%" + keyMailingBean.getListnerTypeDesc().toUpperCase() + "%'";
            }
            if (!keyMailingBean.getListnerTypeCode().isEmpty() || keyMailingBean.getListnerTypeCode().length() > 0) {
                //          if (!condition.equals("")) {
                condition += " AND ";
                //        }  
                condition += "LI.LISTENERID=" + keyMailingBean.getListnerTypeCode()+ " ";
            }
            if (!keyMailingBean.getComKeyId().isEmpty() || keyMailingBean.getComKeyId().length() > 0) {
                //       if (!condition.equals("")) {
                condition += " AND ";
                //        }
//                condition += "LI.COMKEYID LIKE '%" + keyMailingBean.getComKeyId() + "%'";
                condition += "LI.COMKEYID=" + keyMailingBean.getComKeyId() + " ";
            }

            if (!condition.equals("")) {
                defaultQuery += " " + condition;
            } else {
            }

            ps = cmsConOnline.prepareStatement(defaultQuery);
            rs = ps.executeQuery();

            searchList = new ArrayList<ListnerKeyMailBean>();

            while (rs.next()) {
                ListnerKeyMailBean bean = new ListnerKeyMailBean();

                /* bean.setListnerTypeDesc(rs.getString("DESCRIPTION"));
                bean.setListnerTypeCode(rs.getString("LISTENERTYPE"));
                bean.setComKeyId(rs.getString("COMKEYID"));
                 */
                bean.setListnerTypeDesc(rs.getString("LISTENRE_NAME"));
                bean.setListnerTypeCode(rs.getString("LISTENER_TYPE"));
                bean.setComKeyDesc(rs.getString("COM_KEY_DESC"));
                bean.setListenerId(rs.getString("LISTENERID"));
                bean.setComKeyId(rs.getString("COMKEYID"));

                searchList.add(bean);
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
                throw e;
            }
        }
        return searchList;
    }

    public boolean updateZMKKey(Connection cmsConOnline, ListnerKeyMailBean keyBean) throws SQLException, Exception {
        boolean isSucces = false;
        PreparedStatement ps = null;
        String query = null;

        query = "UPDATE ECMS_ONLINE_COMMUNICATIONKEYS SET ZMK=?, ZMK_KVC=? WHERE COMKEYID=?";

        try {

            ps = cmsConOnline.prepareStatement(query);

            ps.setString(1, keyBean.getZmk());
            ps.setString(2, keyBean.getZmk_kvc());
            ps.setString(3, keyBean.getComKeyId());

            ps.executeUpdate();
            isSucces = true;

        } catch (SQLException sqlex) {
            throw sqlex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                ps.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return isSucces;
    }

    public String getListernerName(Connection cmsConOnline) throws SQLException, Exception {
        //List<ListnerKeyMailBean> listnerTypeList = new ArrayList<ListnerKeyMailBean>();
        String listerNameList = null;
        PreparedStatement ps = null;
        String query = null;

        query = "SELECT DESCRIPTION FROM ECMS_ONLINE_LISTENERS";

        try {

            ps = cmsConOnline.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {

                listerNameList = rs.getString("DESCRIPTION");

            }
            return listerNameList;

        } catch (SQLException sqlex) {
            throw sqlex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }
}
