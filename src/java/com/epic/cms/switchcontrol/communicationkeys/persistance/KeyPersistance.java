/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.communicationkeys.persistance;

import com.epic.cms.switchcontrol.communicationkeys.bean.KeyBean;
import com.epic.cms.switchcontrol.communicationkeys.bean.LcsStatusBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author asela
 */
public class KeyPersistance {

    public List<KeyBean> getKeyBeanList(Connection conOnline) throws Exception {

        List<KeyBean> keyBeanList = new ArrayList<KeyBean>();

        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = conOnline.prepareStatement("SELECT C.COMKEYID,C.DESCRIPTION,C.ZMK,C.ZMK_KVC,C.AWK,C.AWK_KVC,C.IWK,C.IWK_KVC, S.DESCRIPTION AS LCSDES , S.CODE AS LCSCODE FROM ECMS_ONLINE_COMMUNICATIONKEYS C, ECMS_ONLINE_LCSTATUS S WHERE C.COMMUNICATIONTYPE=S.CODE");
            rs = ps.executeQuery();

            while (rs.next()) {
                KeyBean keyBean = new KeyBean();
                keyBean.setId(rs.getString("COMKEYID"));
                keyBean.setDescription(rs.getString("DESCRIPTION"));
                keyBean.setZmk(rs.getString("ZMK"));
                keyBean.setZmkkvc(rs.getString("ZMK_KVC"));
                keyBean.setAwk(rs.getString("AWK"));
                keyBean.setAwkkvc(rs.getString("AWK_KVC"));
                keyBean.setIwk(rs.getString("IWK"));
                keyBean.setIwkkvc(rs.getString("IWK_KVC"));
                keyBean.setCommunicationTypeDes(rs.getString("LCSDES"));
                keyBean.setCommunicationType(rs.getString("LCSCODE"));
                keyBeanList.add(keyBean);
            }

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception e) {
            throw e;
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
        return keyBeanList;
    }

    public List<LcsStatusBean> getLcsStatusBeanList(Connection conOnline) throws Exception {

        List<LcsStatusBean> lcsStatusBeanList = new ArrayList<LcsStatusBean>();

        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = conOnline.prepareStatement("SELECT CODE,DESCRIPTION FROM ECMS_ONLINE_LCSTATUS");
            rs = ps.executeQuery();

            while (rs.next()) {
                LcsStatusBean lcsStatusBean = new LcsStatusBean();
                lcsStatusBean.setCode(rs.getString("CODE"));
                lcsStatusBean.setDescription(rs.getString("DESCRIPTION"));

                lcsStatusBeanList.add(lcsStatusBean);
            }

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception e) {
            throw e;
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
        return lcsStatusBeanList;
    }

    public boolean insertCommunicationKeyDetails(Connection conOnline, KeyBean keybean) throws Exception {
        boolean success = false;
        PreparedStatement insertState = null;
        try {
            insertState = conOnline.prepareStatement("INSERT INTO ECMS_ONLINE_COMMUNICATIONKEYS(COMKEYID,DESCRIPTION,COMMUNICATIONTYPE) VALUES(?,?,?)");

            insertState.setInt(1, Integer.parseInt(keybean.getId()));
            insertState.setString(2, keybean.getDescription());
            insertState.setString(3, keybean.getCommunicationType());
            insertState.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception e) {
            throw e;
        } finally {
            insertState.close();
        }
        return success;
    }

    public boolean updateZMKKeyDetails(Connection conOnline, KeyBean keybean) throws Exception {
        boolean success = false;
        PreparedStatement updateState = null;
        try {
            updateState = conOnline.prepareStatement("UPDATE ECMS_ONLINE_COMMUNICATIONKEYS SET ZMK = ? , ZMK_KVC = ? WHERE COMKEYID = ?");

            updateState.setString(1, keybean.getZmk());
            updateState.setString(2, keybean.getZmkkvc());
            updateState.setInt(3, Integer.parseInt(keybean.getId()));

            updateState.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception e) {
            throw e;
        } finally {
            updateState.close();
        }
        return success;
    }

    public boolean updateAWKKeyDetails(Connection conOnline, KeyBean keybean) throws Exception {
        boolean success = false;
        PreparedStatement updateState = null;
        try {
            updateState = conOnline.prepareStatement("UPDATE ECMS_ONLINE_COMMUNICATIONKEYS SET AWK = ? , AWK_KVC = ? WHERE COMKEYID = ?");

            updateState.setString(1, keybean.getAwk());
            updateState.setString(2, keybean.getAwkkvc());
            updateState.setInt(3, Integer.parseInt(keybean.getId()));

            updateState.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception e) {
            throw e;
        } finally {
            updateState.close();
        }
        return success;
    }

    public boolean updateIWKKeyDetails(Connection conOnline, KeyBean keybean) throws Exception {
        boolean success = false;
        PreparedStatement updateState = null;
        try {
            updateState = conOnline.prepareStatement("UPDATE ECMS_ONLINE_COMMUNICATIONKEYS SET IWK = ? , IWK_KVC = ? WHERE COMKEYID = ?");

            updateState.setString(1, keybean.getIwk());
            updateState.setString(2, keybean.getIwkkvc());
            updateState.setInt(3, Integer.parseInt(keybean.getId()));

            updateState.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception e) {
            throw e;
        } finally {
            updateState.close();
        }
        return success;
    }

    public int deleteCommunicationKeyDetails(Connection conOnline, String keyId) throws Exception {
        int num = -1;
        PreparedStatement deleteState = null;
        try {
            deleteState = conOnline.prepareStatement("DELETE FROM ECMS_ONLINE_COMMUNICATIONKEYS WHERE COMKEYID = ?");

            deleteState.setInt(1, Integer.parseInt(keyId));
            num = deleteState.executeUpdate();

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception e) {
            throw e;
        } finally {
            deleteState.close();
        }
        return num;
    }
    
    public boolean updateCommunicationKeyDetails(Connection conOnline, KeyBean keybean) throws Exception {
        boolean success = false;
        PreparedStatement insertState = null;
        try {
            insertState = conOnline.prepareStatement("UPDATE ECMS_ONLINE_COMMUNICATIONKEYS SET DESCRIPTION = ? , COMMUNICATIONTYPE=? WHERE COMKEYID = ?");

            insertState.setString(1, keybean.getDescription());
            insertState.setString(2, keybean.getCommunicationType());
            insertState.setInt(3, Integer.parseInt(keybean.getId()));            
            insertState.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception e) {
            throw e;
        } finally {
            insertState.close();
        }
        return success;
    }


    
}
