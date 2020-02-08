/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.keymgt.persistance;

import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardMainCustomerDetailsBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardSuplimentoryCustomerBean;
import com.epic.cms.camm.applicationconfirmation.bean.CardBean;
import com.epic.cms.cpmm.pingenarationandmail.been.CardKeyBean;
import com.epic.cms.switchcontrol.cardkeys.bean.CardKeyProfilebean;
import com.epic.cms.switchcontrol.communicationkeys.bean.KeyBean;
import com.epic.cms.switchcontrol.keymgt.bean.ChanelKeysBean;
import com.epic.cms.switchcontrol.keymgt.bean.ChannelKeyMailingBean;
import com.epic.cms.switchcontrol.keymgt.bean.OnlinekeysBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author mahesh_m
 */
public class KeyManagementPersistance {

    private ResultSet rs;

    public boolean addZMKValues(Connection con, String zmkkey, String zmkkvc, String chanelId) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;

        try {
            uptByCatStat = con.prepareStatement("INSERT INTO ECMS_ONLINE_CHANNEL_KEYS(CHANNELID,ZMK,ZMKKVC) VALUES(?,?,?)");

            uptByCatStat.setString(1, chanelId);
            uptByCatStat.setString(2, zmkkey);
            uptByCatStat.setString(3, zmkkvc);

            rs = uptByCatStat.executeQuery();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return result;
    }

    public boolean addimk_acValues(Connection con, String imk_ac, String imk_ackvc, String chanelId) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("INSERT INTO ECMS_ONLINE_CHANNEL_KEYS(CHANNELID,IMK_AC,IMK_ACKVC) VALUES(?,?,?)");

            uptByCatStat.setString(1, chanelId);
            uptByCatStat.setString(2, imk_ac);
            uptByCatStat.setString(3, imk_ackvc);

            rs = uptByCatStat.executeQuery();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return result;
    }

    public boolean addzcmkValues(Connection con, String zcmk, String zcmkkvc, String chanelId) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("INSERT INTO ECMS_ONLINE_CHANNEL_KEYS(CHANNELID,ZCMK,ZCMKKVC) VALUES(?,?,?)");

            uptByCatStat.setString(1, chanelId);
            uptByCatStat.setString(2, zcmk);
            uptByCatStat.setString(3, zcmkkvc);

            rs = uptByCatStat.executeQuery();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return result;
    }

    public boolean addppkValues(Connection con, String ppk, String ppkkvc, String chanelId) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("INSERT INTO ECMS_ONLINE_CHANNEL_KEYS(CHANNELID,PPK,PPK_KVC) VALUES(?,?,?)");

            uptByCatStat.setString(1, chanelId);
            uptByCatStat.setString(2, ppk);
            uptByCatStat.setString(3, ppkkvc);

            rs = uptByCatStat.executeQuery();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return result;
    }

    public boolean addVISACVVA(Connection con, String cvvaKey, String cvvakvc, String chanelId) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("INSERT INTO ECMS_ONLINE_CHANNEL_KEYS(CHANNELID,CVKA,CVKA_KVC) VALUES(?,?,?)");

            uptByCatStat.setString(1, chanelId);
            uptByCatStat.setString(2, cvvaKey);
            uptByCatStat.setString(3, cvvakvc);

            rs = uptByCatStat.executeQuery();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return result;
    }

    public boolean addVISAPVVA(Connection con, String pvvaKey, String pvvakvc, String chanelId) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("INSERT INTO ECMS_ONLINE_CHANNEL_KEYS(CHANNELID,PVKA,PVKA_KVC) VALUES(?,?,?)");

            uptByCatStat.setString(1, chanelId);
            uptByCatStat.setString(2, pvvaKey);
            uptByCatStat.setString(3, pvvakvc);

            rs = uptByCatStat.executeQuery();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return result;
    }

    public boolean addVISAPVVB(Connection con, String pvvbKey, String pvvbkvc, String chanelId) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("INSERT INTO ECMS_ONLINE_CHANNEL_KEYS(CHANNELID,PVKB,PVKB_KVC) VALUES(?,?,?)");

            uptByCatStat.setString(1, chanelId);
            uptByCatStat.setString(2, pvvbKey);
            uptByCatStat.setString(3, pvvbkvc);

            rs = uptByCatStat.executeQuery();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return result;
    }

    public boolean addTPK(Connection con, String tpkKey, String tpkkvc, String chanelId) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("INSERT INTO ECMS_ONLINE_CHANNEL_KEYS(CHANNELID,TPK,TPK_KVC) VALUES(?,?,?)");

            uptByCatStat.setString(1, chanelId);
            uptByCatStat.setString(2, tpkKey);
            uptByCatStat.setString(3, tpkkvc);

            rs = uptByCatStat.executeQuery();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return result;
    }

    public boolean addVISACVVB(Connection con, String cvvbKey, String cvvbkvc, String chanelId) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("INSERT INTO ECMS_ONLINE_CHANNEL_KEYS(CHANNELID,PVKB,CVKB_KVC) VALUES(?,?,?)");

            uptByCatStat.setString(1, chanelId);
            uptByCatStat.setString(2, cvvbKey);
            uptByCatStat.setString(3, cvvbkvc);

            rs = uptByCatStat.executeQuery();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return result;
    }

    public boolean addAWKValues(Connection con, String awkkey, String awkkvc, String chanelId) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("INSERT INTO ECMS_ONLINE_CHANNEL_KEYS(CHANNELID,AWK,AWKKVC) VALUES(?,?,?)");

            uptByCatStat.setString(1, chanelId);
            uptByCatStat.setString(2, awkkey);
            uptByCatStat.setString(3, awkkvc);

            rs = uptByCatStat.executeQuery();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return result;
    }

    public boolean addIWKValues(Connection con, String iwkkey, String iwkkvc, String chanelId) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("INSERT INTO ECMS_ONLINE_CHANNEL_KEYS(CHANNELID,IWK,IWKKVC) VALUES(?,?,?)");

            uptByCatStat.setString(1, chanelId);
            uptByCatStat.setString(2, iwkkey);
            uptByCatStat.setString(3, iwkkvc);

            rs = uptByCatStat.executeQuery();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return result;
    }

    public boolean addCSKAValues(Connection con, String cskakey, String cskakvc, String chanelId) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("INSERT INTO ECMS_ONLINE_CHANNEL_KEYS(CHANNELID,CSCKA,CSCKA_KVC) VALUES(?,?,?)");

            uptByCatStat.setString(1, chanelId);
            uptByCatStat.setString(2, cskakey);
            uptByCatStat.setString(3, cskakvc);

            rs = uptByCatStat.executeQuery();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return result;
    }

    public boolean addCSKBValues(Connection con, String cskbkey, String cskbkvc, String chanelId) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("INSERT INTO ECMS_ONLINE_CHANNEL_KEYS(CHANNELID,CSCKB,CSCKB_KVC) VALUES(?,?,?)");

            uptByCatStat.setString(1, chanelId);
            uptByCatStat.setString(2, cskbkey);
            uptByCatStat.setString(3, cskbkvc);

            rs = uptByCatStat.executeQuery();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return result;
    }

    public boolean updateZMKValues(Connection con, String zmkkey, String zmkkvc, String chanelId) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("UPDATE ECMS_ONLINE_CHANNEL_KEYS SET ZMK = ?,ZMKKVC = ? WHERE CHANNELID = ?");

            uptByCatStat.setString(1, zmkkey);
            uptByCatStat.setString(2, zmkkvc);
            uptByCatStat.setString(3, chanelId);

            rs = uptByCatStat.executeQuery();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return result;
    }

    public boolean updateimk_ac(Connection con, String imk_ac, String imk_ackvc, String chanelId) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("UPDATE ECMS_ONLINE_CHANNEL_KEYS SET IMK_AC = ?,IMK_ACKVC = ? WHERE CHANNELID = ?");

            uptByCatStat.setString(1, imk_ac);
            uptByCatStat.setString(2, imk_ackvc);
            uptByCatStat.setString(3, chanelId);

            rs = uptByCatStat.executeQuery();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return result;
    }

    public boolean updatezcmk(Connection con, String zcmk, String zcmkkvc, String chanelId) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("UPDATE ECMS_ONLINE_CHANNEL_KEYS SET ZCMK = ?,ZCMKKVC = ? WHERE CHANNELID = ?");

            uptByCatStat.setString(1, zcmk);
            uptByCatStat.setString(2, zcmkkvc);
            uptByCatStat.setString(3, chanelId);

            rs = uptByCatStat.executeQuery();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return result;
    }

    public boolean updateppk(Connection con, String ppk, String ppkkvc, String chanelId) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("UPDATE ECMS_ONLINE_CHANNEL_KEYS SET PPK = ?,PPK_KVC = ? WHERE CHANNELID = ?");

            uptByCatStat.setString(1, ppk);
            uptByCatStat.setString(2, ppkkvc);
            uptByCatStat.setString(3, chanelId);

            rs = uptByCatStat.executeQuery();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return result;
    }

    public boolean updateVISACVVAalues(Connection con, String cvva, String cvvakvc, String chanelId) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("UPDATE ECMS_ONLINE_CHANNEL_KEYS SET CVKA = ?,CVKA_KVC = ? WHERE CHANNELID = ?");

            uptByCatStat.setString(1, cvva);
            uptByCatStat.setString(2, cvvakvc);
            uptByCatStat.setString(3, chanelId);

            rs = uptByCatStat.executeQuery();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return result;
    }

    public boolean updateVISAPVVAalues(Connection con, String pvva, String pvvakvc, String channelId) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("UPDATE ECMS_ONLINE_CHANNEL_KEYS SET PVKA = ?,PVKA_KVC = ? WHERE CHANNELID = ?");

            uptByCatStat.setString(1, pvva);
            uptByCatStat.setString(2, pvvakvc);
            uptByCatStat.setString(3, channelId);

            rs = uptByCatStat.executeQuery();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return result;
    }

    public boolean updateVISAPVVBalues(Connection con, String pvvb, String pvvbkvc, String chanelId) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("UPDATE ECMS_ONLINE_CHANNEL_KEYS SET PVKB = ?,PVKB_KVC = ? WHERE CHANNELID = ?");

            uptByCatStat.setString(1, pvvb);
            uptByCatStat.setString(2, pvvbkvc);
            uptByCatStat.setString(3, chanelId);

            rs = uptByCatStat.executeQuery();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return result;
    }
    //-------------------------------------------------------------------------------------------------------------------

    public boolean addPVVValues(Connection con, String pvv, String pvvkvc, String chanelId) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("INSERT INTO ECMS_ONLINE_CHANNEL_KEYS(CHANNELID,PVK,PVK_KVC) VALUES(?,?,?)");

            uptByCatStat.setString(1, chanelId);
            uptByCatStat.setString(2, pvv);
            uptByCatStat.setString(3, pvvkvc);

            rs = uptByCatStat.executeQuery();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return result;
    }

    public boolean updatePVVValues(Connection con, String pvv, String pvvkvc, String chanelId) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("UPDATE ECMS_ONLINE_CHANNEL_KEYS SET PVK = ?,PVK_KVC = ? WHERE CHANNELID = ?");

            uptByCatStat.setString(1, pvv);
            uptByCatStat.setString(2, pvvkvc);
            uptByCatStat.setString(3, chanelId);

            rs = uptByCatStat.executeQuery();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return result;
    }
    //---------------------------------------------------------------------------------------------------------------

    public boolean updateTPKalues(Connection con, String tpk, String tpkkvc, String chanelId) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("UPDATE ECMS_ONLINE_CHANNEL_KEYS SET TPK = ?,TPK_KVC = ? WHERE CHANNELID = ?");

            uptByCatStat.setString(1, tpk);
            uptByCatStat.setString(2, tpkkvc);
            uptByCatStat.setString(3, chanelId);

            rs = uptByCatStat.executeQuery();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return result;
    }

    public boolean updateVISACVVBalues(Connection con, String cvvb, String cvvbkvc, String chanelId) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("UPDATE ECMS_ONLINE_CHANNEL_KEYS SET CVKB = ?,CVKB_KVC = ? WHERE CHANNELID = ?");

            uptByCatStat.setString(1, cvvb);
            uptByCatStat.setString(2, cvvbkvc);
            uptByCatStat.setString(3, chanelId);

            rs = uptByCatStat.executeQuery();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return result;
    }

    public boolean updateAWKalues(Connection con, String awkkey, String awkkvc, String chanelId) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("UPDATE ECMS_ONLINE_CHANNEL_KEYS SET AWK = ?,AWKKVC = ? WHERE CHANNELID = ?");

            uptByCatStat.setString(1, awkkey);
            uptByCatStat.setString(2, awkkvc);
            uptByCatStat.setString(3, chanelId);

            rs = uptByCatStat.executeQuery();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return result;
    }

    public boolean updateIWKalues(Connection con, String iwkkey, String iwkkvc, String chanelId) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("UPDATE ECMS_ONLINE_CHANNEL_KEYS SET IWK = ?,IWKKVC = ? WHERE CHANNELID = ?");

            uptByCatStat.setString(1, iwkkey);
            uptByCatStat.setString(2, iwkkvc);
            uptByCatStat.setString(3, chanelId);

            rs = uptByCatStat.executeQuery();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return result;
    }

    public boolean updateCSKAValues(Connection con, String cskakey, String cskakvc, String chanelId) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("UPDATE ECMS_ONLINE_CHANNEL_KEYS SET CSCKA = ?,CSCKA_KVC = ? WHERE CHANNELID = ?");

            uptByCatStat.setString(1, cskakey);
            uptByCatStat.setString(2, cskakvc);
            uptByCatStat.setString(3, chanelId);

            rs = uptByCatStat.executeQuery();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return result;
    }

    public boolean updateCSKBValues(Connection con, String cskbkey, String cskbkvc, String chanelId) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("UPDATE ECMS_ONLINE_CHANNEL_KEYS SET CSCKB = ?,CSCKB_KVC = ? WHERE CHANNELID = ?");

            uptByCatStat.setString(1, cskbkey);
            uptByCatStat.setString(2, cskbkvc);
            uptByCatStat.setString(3, chanelId);

            rs = uptByCatStat.executeQuery();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return result;
    }

    public ChanelKeysBean getChanelDetails(Connection con, String chanelId) throws Exception {
        PreparedStatement uptByCatStat = null;
        ChanelKeysBean zmkDetails = new ChanelKeysBean();
        try {
            uptByCatStat = con.prepareStatement("SELECT KEYID,CHANNELID,AWK,AWKKVC,IWK,IWKKVC,ZMK,ZMKKVC,PVKA,PVKA_KVC,"
                    + "PVKB,PVKB_KVC,CVKA,CVKA_KVC,CVKB,CVKB_KVC,IMK_AC,IMK_ACKVC,ZCMK,ZCMKKVC,PPK,PPK_KVC,TPK,TPK_KVC,"
                    + "CSCKA,CSCKA_KVC,CSCKB,CSCKB_KVC,PVK_KVC,PVK "
                    + "FROM ECMS_ONLINE_CHANNEL_KEYS "
                    + "WHERE CHANNELID = ?");

            uptByCatStat.setString(1, chanelId);

            rs = uptByCatStat.executeQuery();
            while (rs.next()) {

                zmkDetails.setKeyId(rs.getString("KEYID"));
                zmkDetails.setChanelId(chanelId);
                zmkDetails.setAwk(rs.getString("AWK"));
                zmkDetails.setAwkKVC(rs.getString("AWKKVC"));
                zmkDetails.setIwk(rs.getString("IWK"));
                zmkDetails.setIwkKVC(rs.getString("IWKKVC"));
                zmkDetails.setZmk(rs.getString("ZMK"));
                zmkDetails.setZmkKVC(rs.getString("ZMKKVC"));
                zmkDetails.setPvva(rs.getString("PVKA"));
                zmkDetails.setPvvaKVC(rs.getString("PVKA_KVC"));
                zmkDetails.setPvvb(rs.getString("PVKB"));
                zmkDetails.setPvvbKVC(rs.getString("PVKB_KVC"));
                zmkDetails.setCvva(rs.getString("CVKA"));
                zmkDetails.setCvvaKVC(rs.getString("CVKA_KVC"));
                zmkDetails.setCvvb(rs.getString("CVKB"));
                zmkDetails.setCvvbKVC(rs.getString("CVKB_KVC"));
                zmkDetails.setImk_ac(rs.getString("IMK_AC"));
                zmkDetails.setImk_acKVC(rs.getString("IMK_ACKVC"));
                zmkDetails.setZcmk(rs.getString("ZCMK"));
                zmkDetails.setZcmkKVC(rs.getString("ZCMKKVC"));
                zmkDetails.setPpk(rs.getString("PPK"));
                zmkDetails.setPpkKVC(rs.getString("PPK_KVC"));
                zmkDetails.setTpk(rs.getString("TPK"));
                zmkDetails.setTpkKVC(rs.getString("TPK_KVC"));
                zmkDetails.setCska(rs.getString("CSCKA"));
                zmkDetails.setCskaKVC(rs.getString("CSCKA_KVC"));
                zmkDetails.setCskb(rs.getString("CSCKB"));
                zmkDetails.setCskbKVC(rs.getString("CSCKB_KVC"));
                zmkDetails.setPvv(rs.getString("PVK"));
                zmkDetails.setPvvKVC(rs.getString("PVK_KVC"));


            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return zmkDetails;
    }

    public CardMainCustomerDetailsBean getCardMainCusDetailbean(Connection con, String customerId) throws Exception {
        PreparedStatement uptByCatStat = null;
        CardMainCustomerDetailsBean cardMainBean = new CardMainCustomerDetailsBean();
        try {
            uptByCatStat = con.prepareStatement("SELECT CM.PERMANENTADDRESS1,CM.PERMANENTADDRESS2,CM.PERMANENTADDRESS3,A.DESCRIPTION FROM CARDMAINCUSTOMERDETAIL CM,AREA A WHERE CM.CITY = A.AREACODE AND CM.CUSTOMERID = ?");

            uptByCatStat.setString(1, customerId);

            rs = uptByCatStat.executeQuery();
            while (rs.next()) {
                cardMainBean.setPermenentAddress1(rs.getString("PERMANENTADDRESS1"));
                cardMainBean.setPermenentAddress2(rs.getString("PERMANENTADDRESS2"));
                cardMainBean.setPermenentAddress3(rs.getString("PERMANENTADDRESS3"));
                cardMainBean.setCity(rs.getString("DESCRIPTION"));
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return cardMainBean;
    }

    public CardSuplimentoryCustomerBean getSuppCusDetails(Connection con, String customerId) throws Exception {
        PreparedStatement uptByCatStat = null;
        CardSuplimentoryCustomerBean cardSuppBean = new CardSuplimentoryCustomerBean();
        try {
            uptByCatStat = con.prepareStatement("SELECT ADDRESS1,ADDRESS2,ADDRESS3,CITY FROM CARDSUPPCUSTOMERDETAILS WHERE CUSTOMERID = ?");

            uptByCatStat.setString(1, customerId);

            rs = uptByCatStat.executeQuery();
            while (rs.next()) {
                cardSuppBean.setAddress1(rs.getString("ADDRESS1"));
                cardSuppBean.setAddress2(rs.getString("ADDRESS2"));
                cardSuppBean.setAddress3(rs.getString("ADDRESS3"));
                cardSuppBean.setCity(rs.getString("CITY"));
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return cardSuppBean;
    }

    public String getChanelId(Connection con, String chanelType) throws Exception {
        PreparedStatement uptByCatStat = null;
        String chanelId = null;
        try {
            uptByCatStat = con.prepareStatement("SELECT CHANNELID FROM ECMS_ONLINE_CHANNELS WHERE CHANNELTYPE = ?");

            uptByCatStat.setString(1, chanelType);

            rs = uptByCatStat.executeQuery();
            while (rs.next()) {
                chanelId = rs.getString("CHANNELID");

            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return chanelId;
    }

    public String getPinMethod(Connection con) throws Exception {
        PreparedStatement uptByCatStat = null;
        String pinMethod = null;
        try {
            uptByCatStat = con.prepareStatement("SELECT PINMETHODID FROM CARDPINMETHOD WHERE STATUS = ?");

            uptByCatStat.setString(1, StatusVarList.ACTIVE_STATUS);

            rs = uptByCatStat.executeQuery();
            while (rs.next()) {
                pinMethod = rs.getString("PINMETHODID");

            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return pinMethod;
    }

    public String getPVI(Connection con) throws Exception {
        PreparedStatement uptByCatStat = null;
        String pvi = null;
        try {
            uptByCatStat = con.prepareStatement("SELECT PVI FROM ECMS_ONLINE_CONFIG");


            rs = uptByCatStat.executeQuery();
            while (rs.next()) {
                pvi = rs.getString("PVI");

            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return pvi;
    }

    public String getCardCategory(Connection con, String cardNumber) throws Exception {
        PreparedStatement uptByCatStat = null;
        String cardCategory = null;
        try {
            uptByCatStat = con.prepareStatement("SELECT CARDCATEGORYCODE FROM CARD WHERE CARDNUMBER = ?");

            uptByCatStat.setString(1, cardNumber);

            rs = uptByCatStat.executeQuery();
            while (rs.next()) {
                cardCategory = rs.getString("CARDCATEGORYCODE");

            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return cardCategory;
    }

    public String getCustomerId(Connection con, String cardNumber) throws Exception {
        PreparedStatement uptByCatStat = null;
        String customerId = null;
        try {
            uptByCatStat = con.prepareStatement("SELECT CUSTOMERID FROM CARD WHERE CARDNUMBER = ?");

            uptByCatStat.setString(1, cardNumber);

            rs = uptByCatStat.executeQuery();
            while (rs.next()) {
                customerId = rs.getString("CUSTOMERID");

            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return customerId;
    }

    public CardBean getCardDetails(Connection con, String cardNumber) throws Exception {
        PreparedStatement uptByCatStat = null;
        String expDate = null;
        CardBean card = null;
        try {
            uptByCatStat = con.prepareStatement("SELECT EXPIERYDATE,SERVICECODE,PRODUCTIONMODE,CARDKEYID FROM CARD WHERE CARDNUMBER = ?");

            uptByCatStat.setString(1, cardNumber);

            rs = uptByCatStat.executeQuery();

            card = new CardBean();

            while (rs.next()) {
                card.setServiceCode(rs.getString("SERVICECODE"));
                card.setExpiryDate(rs.getString("EXPIERYDATE"));
                card.setProductionMode(rs.getString("PRODUCTIONMODE"));
                card.setCardKeyId(rs.getString("CARDKEYID"));
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return card;
    }

    public String getCardKeyId(Connection con, String binNumber) throws Exception {
        PreparedStatement uptByCatStat = null;
        String cardKeyId = null;
        try {
            uptByCatStat = con.prepareStatement("SELECT CARDKEYID FROM ECMS_ONLINE_BIN WHERE BINNO = ?");

            uptByCatStat.setString(1, binNumber);

            rs = uptByCatStat.executeQuery();


            while (rs.next()) {
                cardKeyId = rs.getString("CARDKEYID");

            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return cardKeyId;
    }

    public CardKeyBean getCardkeyDetails(Connection con, String keyId) throws Exception {
        PreparedStatement uptByCatStat = null;
        CardKeyBean cardkeyDetails = new CardKeyBean();
        try {
            uptByCatStat = con.prepareStatement("SELECT CK.CARDKEYID,CK.DESCRIPTION ,CK.PVKA,CK.PVKA_KVC,"
                    + "CK.PVKB,CK.PVKB_KVC,CK.PVK,CK.PVK_KVC,CK.CVK1A,CK.CVK1A_KVC,"
                    + "CK.CVK1B,CK.CVK1B_KVC,CK.CVK1,CK.CVK1_KVC,CK.CVK2A,CK.CVK2A_KVC,"
                    + "CK.CVK2B,CK.CVK2B_KVC,CK.CVK2,CK.CVK2_KVC,CK.ICVKA,CK.ICVKA_KVC,CK.ICVKB,"
                    + "CK.ICVKB_KVC,CK.ICVK,CK.ICVK_KVC,CK.CSCKA,CK.CSCKA_KVC,CK.CSCKB,CK.CSCKB_KVC,"
                    + "CK.CSCK,CK.CSCK_KVC,CK.IMKAC,CK.IMKAC_KVC,CK.ZCMK,CK.ZCMK_KVC,CK.IMKSMI,CK.IMKSMI_KVC,"
                    + "CK.IMKSMC,CK.IMKSMC_KVC,CK.PPK,CK.PPK_KVC,CK.CARDKEYPROFILEID FROM ECMS_ONLINE_CARDKEYS CK WHERE CARDKEYID = ?");

            uptByCatStat.setString(1, keyId);

            rs = uptByCatStat.executeQuery();


            while (rs.next()) {
                cardkeyDetails.setDescription(rs.getString("DESCRIPTION"));
                cardkeyDetails.setPvka(rs.getString("PVKA"));
                cardkeyDetails.setPvkaKVC(rs.getString("PVKA_KVC"));
                cardkeyDetails.setPvkb(rs.getString("PVKB"));
                cardkeyDetails.setPvkbKVC(rs.getString("PVKB_KVC"));
                cardkeyDetails.setPvk(rs.getString("PVK"));
                cardkeyDetails.setPvkKVC(rs.getString("PVK_KVC"));
                cardkeyDetails.setCvk1a(rs.getString("CVK1A"));
                cardkeyDetails.setCvk1aKVC(rs.getString("CVK1A_KVC"));
                cardkeyDetails.setCvk1b(rs.getString("CVK1B"));
                cardkeyDetails.setCvk1bKVC(rs.getString("CVK1B_KVC"));
                cardkeyDetails.setCvk1(rs.getString("CVK1"));
                cardkeyDetails.setCvk1KVC(rs.getString("CVK1_KVC"));
                cardkeyDetails.setCvk2a(rs.getString("CVK2A"));
                cardkeyDetails.setCvk2aKVC(rs.getString("CVK2A_KVC"));
                cardkeyDetails.setCvk2b(rs.getString("CVK2B"));
                cardkeyDetails.setCvk2bKVC(rs.getString("CVK2B_KVC"));
                cardkeyDetails.setCvk2(rs.getString("CVK2"));
                cardkeyDetails.setCvk2KVC(rs.getString("CVK2_KVC"));
                cardkeyDetails.setIcvka(rs.getString("ICVKA"));
                cardkeyDetails.setIcvkaKVC(rs.getString("ICVKA_KVC"));
                cardkeyDetails.setIcvkb(rs.getString("ICVKB"));
                cardkeyDetails.setIcvkbKVC(rs.getString("ICVKB_KVC"));
                cardkeyDetails.setIcvk(rs.getString("ICVK"));
                cardkeyDetails.setIcvkKVC(rs.getString("ICVK_KVC"));
                cardkeyDetails.setCscka(rs.getString("CSCKA"));
                cardkeyDetails.setCsckaKVC(rs.getString("CSCKA_KVC"));
                cardkeyDetails.setCsckb(rs.getString("CSCKB"));
                cardkeyDetails.setCsckbKVC(rs.getString("CSCKB_KVC"));
                cardkeyDetails.setCsck(rs.getString("CSCK"));
                cardkeyDetails.setCsckKVC(rs.getString("CSCK_KVC"));
                cardkeyDetails.setImkac(rs.getString("IMKAC"));
                cardkeyDetails.setImkacKVC(rs.getString("IMKAC_KVC"));
                cardkeyDetails.setZcmk(rs.getString("ZCMK"));
                cardkeyDetails.setZcmkKVC(rs.getString("ZCMK_KVC"));
                cardkeyDetails.setImksmi(rs.getString("IMKSMI"));
                cardkeyDetails.setImksmiKVC(rs.getString("IMKSMI_KVC"));
                cardkeyDetails.setImksmc(rs.getString("IMKSMC"));
                cardkeyDetails.setImksmcKVC(rs.getString("IMKSMC_KVC"));
                cardkeyDetails.setPpk(rs.getString("PPK"));
                cardkeyDetails.setPpkKVC(rs.getString("PPK_KVC"));
                cardkeyDetails.setCardkeyprofileid(rs.getString("CARDKEYPROFILEID"));
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return cardkeyDetails;
    }

    public CardKeyProfilebean getCardkeyprofileBean(Connection con, String profileid) throws Exception {
        PreparedStatement uptByCatStat = null;
        CardKeyProfilebean keyProfileBean = new CardKeyProfilebean();
        try {
            uptByCatStat = con.prepareStatement("SELECT ID , DESCRIPTION , PVK ,"
                    + " CVK1,CVK2,ICVK,CSCK ,PPK ,IMKAC ,"
                    + " IMKSMC ,IMKSMI ,ZCMK, ISPVKPAIR,ISCVK1PAIR , "
                    + "ISCVK2PAIR ,ISICVKPAIR,ISCSCKPAIR ,DCVK FROM ECMS_ONLINE_CARDKEYPROFILE WHERE ID = ? ");

            uptByCatStat.setString(1, profileid);

            rs = uptByCatStat.executeQuery();


            while (rs.next()) {
                keyProfileBean.setPvk(rs.getString("PVK"));
                keyProfileBean.setCvk1(rs.getString("CVK1"));
                keyProfileBean.setCvk2(rs.getString("CVK2"));
                keyProfileBean.setICVK(rs.getString("ICVK"));
                keyProfileBean.setCSCK(rs.getString("CSCK"));
                keyProfileBean.setPPK(rs.getString("PPK"));
                keyProfileBean.setIMKAC(rs.getString("IMKAC"));
                keyProfileBean.setIMKSMC(rs.getString("IMKSMC"));
                keyProfileBean.setIMKSMI(rs.getString("IMKSMI"));
                keyProfileBean.setZCMK(rs.getString("ZCMK"));
                keyProfileBean.setIsPVKpair(rs.getString("ISPVKPAIR")); 
                keyProfileBean.setIsCVK1Pair(rs.getString("ISCVK1PAIR"));
                keyProfileBean.setIsCVK2pair(rs.getString("ISCVK2PAIR"));
                keyProfileBean.setIsICVKpair(rs.getString("ISICVKPAIR"));
                keyProfileBean.setIsCSCKpair(rs.getString("ISCSCKPAIR"));
                keyProfileBean.setDescription(rs.getString("DESCRIPTION"));
                keyProfileBean.setProfileId(rs.getString("ID"));
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return keyProfileBean;
    }

    public OnlinekeysBean getOnlineKeyDetails(Connection con, String keyId) throws Exception {
        PreparedStatement uptByCatStat = null;
        OnlinekeysBean onlineKey = new OnlinekeysBean();
        try {
            uptByCatStat = con.prepareStatement("SELECT KEYID,PVKA,PVKAKVC,PVKB,PVKBKVC,CVKA,CVKAKVC,CVKB,CVKBKVC,TPK,TPKKVC FROM ECMS_ONLINE_KEYS WHERE KEYID = ?");

            uptByCatStat.setString(1, keyId);

            rs = uptByCatStat.executeQuery();
            while (rs.next()) {

                onlineKey.setKeyId(keyId);
                onlineKey.setPvva(rs.getString("PVKA"));
                onlineKey.setPvvakvc(rs.getString("PVKAKVC"));
                onlineKey.setPvvb(rs.getString("PVKB"));
                onlineKey.setPvvbkvc(rs.getString("PVKBKVC"));
                onlineKey.setCvva(rs.getString("CVKA"));
                onlineKey.setCvvakvc(rs.getString("CVKAKVC"));
                onlineKey.setCvvb(rs.getString("CVKB"));
                onlineKey.setCvvbkvc(rs.getString("CVKBKVC"));
                onlineKey.setTpk(rs.getString("TPK"));
                onlineKey.setTpkkvc(rs.getString("TPKKVC"));


            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return onlineKey;
    }

    public boolean isChanelAvailable(Connection con, String chanelId) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean status;
        try {
            uptByCatStat = con.prepareStatement("SELECT COUNT(*) AS COUNT FROM ECMS_ONLINE_CHANNEL_KEYS WHERE CHANNELID = ?");

            uptByCatStat.setString(1, chanelId);

            rs = uptByCatStat.executeQuery();
            if (rs.next()) {
                int count = Integer.parseInt(rs.getString("COUNT").trim());
                if (count > 0) {
                    status = true;
                } else {
                    status = false;
                }
            } else {
                status = false;
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return status;
    }

    public boolean isKeyIdAvailable(Connection con, String keyId) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean status;
        try {
            uptByCatStat = con.prepareStatement("SELECT COUNT(*) AS COUNT FROM ECMS_ONLINE_KEYS WHERE KEYID = ?");

            uptByCatStat.setString(1, keyId);

            rs = uptByCatStat.executeQuery();
            if (rs.next()) {
                int count = Integer.parseInt(rs.getString("COUNT").trim());
                if (count > 0) {
                    status = true;
                } else {
                    status = false;
                }
            } else {
                status = false;
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return status;
    }

    public HashMap<String, String> getChannelTypes(Connection con) throws SQLException {
        HashMap<String, String> channelTypes =new HashMap<String, String>();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("SELECT CODE,DESCRIPTION FROM ECMS_ONLINE_CHANNEL_TYPE");
            rs = ps.executeQuery();
            while (rs.next()) {
                channelTypes.put(rs.getString("CODE"),rs.getString("DESCRIPTION"));
            }
            
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return channelTypes;       
    }

    public HashMap<String, String> getCommunicationKeys(Connection con) throws SQLException {
        HashMap<String, String> communicationKeys =new HashMap<String, String>();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("SELECT COMKEYID,DESCRIPTION FROM ECMS_ONLINE_COMMUNICATIONKEYS");
            rs = ps.executeQuery();
            while (rs.next()) {
                communicationKeys.put(rs.getString("COMKEYID"),rs.getString("DESCRIPTION"));
            }
            
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return communicationKeys; 
    }

    public List<ChannelKeyMailingBean> searchChannelKeyMailing(Connection con, ChannelKeyMailingBean channelKeyMailingBean) throws SQLException, Exception {
        PreparedStatement ps = null;
        String query = null;
        ResultSet rs = null;
        List<ChannelKeyMailingBean> searchResultList=new ArrayList<ChannelKeyMailingBean>();
        
        try {
            query = " SELECT EOC.CHANNELID,EOC.CHANNELNAME,EOC.IP,EOC.PORT,EOC.COMMUNICATIONKEYID AS COMMUNICATIONKEYIDCODE,"
                    + " EOCK.DESCRIPTION AS COMMUNICATIONKEYIDDESC,EOCT.DESCRIPTION AS CHANNELTYPEDESC"
                    + " FROM ECMS_ONLINE_CHANNELS EOC,ECMS_ONLINE_COMMUNICATIONKEYS EOCK,ECMS_ONLINE_CHANNEL_TYPE EOCT"
                    + " WHERE EOC.COMMUNICATIONKEYID = EOCK.COMKEYID AND EOC.CHANNELTYPE=EOCT.CODE";

            String condition = "";
            
            String channelID=channelKeyMailingBean.getChannelID();
            String channelName=channelKeyMailingBean.getChannelName();
            String channelType=channelKeyMailingBean.getChannelTypeCode();
            String communicationKey=channelKeyMailingBean.getCommunicationKeyCode();       

            if (!channelID.contentEquals("") || channelID.length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " UPPER(EOC.CHANNELID) LIKE '%" + channelID.toUpperCase() + "%'";
            }
            if (!channelName.contentEquals("") || channelName.length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " UPPER(EOC.CHANNELNAME) LIKE '%" + channelName.toUpperCase() + "%'";
            }
            if (!channelType.contentEquals("") || channelType.length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " EOC.CHANNELTYPE = " + channelType + " ";
            }
            if (!communicationKey.contentEquals("") || communicationKey.length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " EOC.COMMUNICATIONKEYID = " + communicationKey + " ";
            }
            if (!condition.equals("")) {
                query += "  AND " + condition + " ORDER BY EOC.CHANNELID";
            } else {
                query += " ORDER BY EOC.CHANNELID";
            }

            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                ChannelKeyMailingBean resultBean = new ChannelKeyMailingBean();

                resultBean.setChannelID(rs.getString("CHANNELID"));
                resultBean.setChannelName(rs.getString("CHANNELNAME"));
                resultBean.setIp(rs.getString("IP"));
                resultBean.setPort(rs.getString("PORT"));
                resultBean.setCommunicationKeyCode(rs.getString("COMMUNICATIONKEYIDCODE"));
                resultBean.setCommunicationKeyDesc(rs.getString("COMMUNICATIONKEYIDDESC"));
                resultBean.setChannelTypeDesc(rs.getString("CHANNELTYPEDESC"));

                searchResultList.add(resultBean);
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
            }
        }
        return searchResultList;
    }

    public ChannelKeyMailingBean getChannelKeyMailingData(Connection con, String communicationKey) throws SQLException, Exception {
        
        ChannelKeyMailingBean channelKeyMailingBean = new ChannelKeyMailingBean();
        PreparedStatement ps = null;
        String query = null;
        ResultSet rs = null;
        
        try {
           query="SELECT EOC.CHANNELID,EOC.CHANNELNAME,EOC.IP,EOC.PORT,EOC.TIMEOUT,EOCONT.DESCRIPTION AS CONNECTIONTYPEDESC,"
                   + "EOCT.DESCRIPTION AS CHANNELTYPEDESC,EOS.DESCRIPTION AS ECHOSTATUSDESC,EOS1.DESCRIPTION AS SIGNONSTATUSDESC,"
                   + "EOS2.DESCRIPTION AS DKESTATUSDESC,EOC.DEKTIMEPERIOD,EOC.ECHOTIMEPERIOD,EOS3.DESCRIPTION AS STATUSDESC,"
                   + "EOC.CREATETIME,EOC.LASTUPDATETIME,EOC.LASTUPDATEUSER,EOC.ECHODIRECTION,EOC.KEYEXCHANGEDIRECTION,"
                   + "EOC.INTERFACESTATUS,EOCK.DESCRIPTION AS COMMUNICATIONKEYIDDESC,EOC.HDESID,EOC.HSRCID,EOC.AIIC32,EOC.FIIC33 "
                   + "FROM ECMS_ONLINE_CHANNELS EOC,ECMS_ONLINE_COMMUNICATIONKEYS EOCK,ECMS_ONLINE_CHANNEL_TYPE EOCT,"
                   + "ECMS_ONLINE_CONNECTION_TYPE EOCONT,ECMS_ONLINE_STATUS EOS,ECMS_ONLINE_STATUS EOS1,ECMS_ONLINE_STATUS EOS2,"
                   + "ECMS_ONLINE_STATUS EOS3 WHERE EOC.COMMUNICATIONKEYID = EOCK.COMKEYID AND EOC.CHANNELTYPE=EOCT.CODE "
                   + "AND EOC.CONNECTIONTYPE=EOCONT.CODE AND EOC.ECHOSTATUS=EOS.CODE AND EOC.SIGNONSTATUS=EOS1.CODE "
                   + "AND EOC.DKESTATUS=EOS2.CODE AND EOC.STATUS=EOS3.CODE AND EOC.COMMUNICATIONKEYID=? ";
           
           ps = con.prepareStatement(query);
           ps.setString(1, communicationKey);
           rs = ps.executeQuery();
            while (rs.next()) {
                channelKeyMailingBean.setCommunicationKeyCode(communicationKey);
                channelKeyMailingBean.setChannelID(rs.getString("CHANNELID"));
                channelKeyMailingBean.setChannelName(rs.getString("CHANNELNAME"));
                channelKeyMailingBean.setIp(rs.getString("IP"));
                channelKeyMailingBean.setPort(rs.getString("PORT"));
                channelKeyMailingBean.setTimeout(rs.getString("TIMEOUT"));
                channelKeyMailingBean.setConnectionTypeDesc(rs.getString("CONNECTIONTYPEDESC"));
                channelKeyMailingBean.setChannelTypeDesc(rs.getString("CHANNELTYPEDESC"));
                channelKeyMailingBean.setECHOstatusDesc(rs.getString("ECHOSTATUSDESC"));
                channelKeyMailingBean.setSIGNONstatusDesc(rs.getString("SIGNONSTATUSDESC"));
                channelKeyMailingBean.setDKEstatusDesc(rs.getString("DKESTATUSDESC"));
                channelKeyMailingBean.setDEKtimePeriod(rs.getString("DEKTIMEPERIOD"));
                channelKeyMailingBean.setECHOimePeriod(rs.getString("ECHOTIMEPERIOD"));
                channelKeyMailingBean.setStatusDesc(rs.getString("STATUSDESC"));
                channelKeyMailingBean.setCreateTime(rs.getString("CREATETIME"));
                channelKeyMailingBean.setLastUpdateTime(rs.getString("LASTUPDATETIME"));
                channelKeyMailingBean.setLastUpdateUser(rs.getString("LASTUPDATEUSER"));
                channelKeyMailingBean.setECHOdirection(rs.getString("ECHODIRECTION"));
                channelKeyMailingBean.setKeyExchangeDirection(rs.getString("KEYEXCHANGEDIRECTION"));
                channelKeyMailingBean.setInterfaceStatus(rs.getString("INTERFACESTATUS"));
                channelKeyMailingBean.setCommunicationKeyDesc(rs.getString("COMMUNICATIONKEYIDDESC"));
                channelKeyMailingBean.setHDESID(rs.getString("HDESID"));
                channelKeyMailingBean.setHSRCID(rs.getString("HSRCID"));
                channelKeyMailingBean.setAIIC32(rs.getString("AIIC32"));
                channelKeyMailingBean.setFIIC33(rs.getString("FIIC33"));
            }    
        }catch (SQLException sqlex) {
            throw sqlex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                ps.close();

            } catch (Exception e) {
            }
        }
        return channelKeyMailingBean;
    }

    public boolean updateZMKKey(Connection conToOnline, KeyBean keyBean) throws SQLException, Exception {
        boolean isSuccess = false;
        PreparedStatement ps = null;
        try {
            ps = conToOnline.prepareStatement("UPDATE ECMS_ONLINE_COMMUNICATIONKEYS SET ZMK =?,ZMK_KVC=? WHERE COMKEYID=?");

            ps.setString(1, keyBean.getZmk());
            ps.setString(2, keyBean.getZmkkvc());
            ps.setString(3, keyBean.getId());
            
            ps.executeUpdate();
            isSuccess = true;
            
        } catch (SQLException ex) {
            throw ex;
        } catch (Exception e) {
            throw e;
        } finally {
            ps.close();
        }
        return isSuccess;
    }
}
