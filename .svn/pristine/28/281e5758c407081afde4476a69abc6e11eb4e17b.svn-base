/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.cardkeys.persistance;

import com.epic.cms.switchcontrol.cardkeys.bean.CardKeyProfilebean;
import com.epic.cms.switchcontrol.cardkeys.bean.CardKeybean;
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
public class CardKeyPersistance {

    public List<CardKeybean> getKeyBeanList(Connection conOnline, int cardKeyId) throws Exception {

        List<CardKeybean> keyBeanList = new ArrayList<CardKeybean>();

        ResultSet rs = null;
        PreparedStatement ps = null;

        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT CK.CARDKEYID,CK.DESCRIPTION ,CK.PVKA,CK.PVKA_KVC,CK.PVKB,CK.PVKB_KVC,CK.PVK,CK.PVK_KVC,CK.CVK1A,CK.CVK1A_KVC,CK.CVK1B,CK.CVK1B_KVC,CK.CVK1,CK.CVK1_KVC,CK.CVK2A,CK.CVK2A_KVC,CK.CVK2B,CK.CVK2B_KVC,CK.CVK2,CK.CVK2_KVC,CK.ICVKA,CK.ICVKA_KVC, ");
        sb.append(" CK.ICVKB,CK.ICVKB_KVC,CK.ICVK,CK.ICVK_KVC,CK.CSCKA,CK.CSCKA_KVC,CK.CSCKB,CK.CSCKB_KVC,CK.CSCK,CK.CSCK_KVC,CK.IMKAC,CK.IMKAC_KVC,CK.ZCMK,CK.ZCMK_KVC,CK.IMKSMI,CK.IMKSMI_KVC,CK.IMKSMC,CK.IMKSMC_KVC,CK.PPK,CK.PPK_KVC,CK.CARDKEYPROFILEID ");
        sb.append(" FROM ECMS_ONLINE_CARDKEYS CK , ECMS_ONLINE_CARDKEYPROFILE CKP WHERE CK.CARDKEYPROFILEID=CKP.ID AND CK.CARDKEYID = ? ");
        try {
            ps = conOnline.prepareStatement(sb.toString());
            ps.setInt(1, cardKeyId);
            rs = ps.executeQuery();

            while (rs.next()) {
                CardKeybean keyBean = new CardKeybean();

                keyBean.setId(rs.getString("CARDKEYID"));
                keyBean.setDescription(rs.getString("DESCRIPTION"));

                keyBean.setPvka(rs.getString("PVKA"));
                keyBean.setPvkaKVC(rs.getString("PVKA_KVC"));
                keyBean.setPvkb(rs.getString("PVKB"));
                keyBean.setPvkbKVC(rs.getString("PVKB_KVC"));
                keyBean.setPvk(rs.getString("PVK"));
                keyBean.setPvkKVC(rs.getString("PVK_KVC"));
                keyBean.setCvk1a(rs.getString("CVK1A"));
                keyBean.setCvk1aKVC(rs.getString("CVK1A_KVC"));
                keyBean.setCvk1b(rs.getString("CVK1B"));
                keyBean.setCvk1bKVC(rs.getString("CVK1B_KVC"));
                keyBean.setCvk1(rs.getString("CVK1"));
                keyBean.setCvk1KVC(rs.getString("CVK1_KVC"));
                keyBean.setCvk2a(rs.getString("CVK2A"));
                keyBean.setCvk2aKVC(rs.getString("CVK2A_KVC"));
                keyBean.setCvk2b(rs.getString("CVK2B"));
                keyBean.setCvk2bKVC(rs.getString("CVK2B_KVC"));
                keyBean.setCvk2(rs.getString("CVK2"));
                keyBean.setCvk2KVC(rs.getString("CVK2_KVC"));
                keyBean.setIcvka(rs.getString("ICVKA"));
                keyBean.setIcvkaKVC(rs.getString("ICVKA_KVC"));
                keyBean.setIcvkb(rs.getString("ICVKB"));
                keyBean.setIcvkbKVC(rs.getString("ICVKB_KVC"));
                keyBean.setIcvk(rs.getString("ICVK"));
                keyBean.setIcvkKVC(rs.getString("ICVK_KVC"));
                keyBean.setCscka(rs.getString("CSCKA"));
                keyBean.setCsckaKVC(rs.getString("CSCKA_KVC"));
                keyBean.setCsckb(rs.getString("CSCKB"));
                keyBean.setCsckbKVC(rs.getString("CSCKB_KVC"));
                keyBean.setCsck(rs.getString("CSCK"));
                keyBean.setCsckKVC(rs.getString("CSCK_KVC"));
                keyBean.setImkac(rs.getString("IMKAC"));
                keyBean.setImkacKVC(rs.getString("IMKAC_KVC"));
                keyBean.setZcmk(rs.getString("ZCMK"));
                keyBean.setZcmkKVC(rs.getString("ZCMK_KVC"));
                keyBean.setImksmi(rs.getString("IMKSMI"));
                keyBean.setImksmiKVC(rs.getString("IMKSMI_KVC"));
                keyBean.setImksmc(rs.getString("IMKSMC"));
                keyBean.setImksmcKVC(rs.getString("IMKSMC_KVC"));
                keyBean.setPpk(rs.getString("PPK"));
                keyBean.setPpkKVC(rs.getString("PPK_KVC"));

                keyBean.setCardkeyprofileid(rs.getString("CARDKEYPROFILEID"));

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

    public List<CardKeybean> getAllKeyBeanList(Connection conOnline) throws Exception {

        List<CardKeybean> keyBeanList = new ArrayList<CardKeybean>();

        ResultSet rs = null;
        PreparedStatement ps = null;

        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT CK.CARDKEYID,CK.DESCRIPTION ,CK.PVKA,CK.PVKA_KVC,CK.PVKB,CK.PVKB_KVC,CK.PVK,CK.PVK_KVC,CK.CVK1A,CK.CVK1A_KVC,CK.CVK1B,CK.CVK1B_KVC,CK.CVK1,CK.CVK1_KVC,CK.CVK2A,CK.CVK2A_KVC,CK.CVK2B,CK.CVK2B_KVC,CK.CVK2,CK.CVK2_KVC,CK.ICVKA,CK.ICVKA_KVC, ");
        sb.append(" CK.ICVKB,CK.ICVKB_KVC,CK.ICVK,CK.ICVK_KVC,CK.CSCKA,CK.CSCKA_KVC,CK.CSCKB,CK.CSCKB_KVC,CK.CSCK,CK.CSCK_KVC,CK.IMKAC,CK.IMKAC_KVC,CK.ZCMK,CK.ZCMK_KVC,CK.IMKSMI,CK.IMKSMI_KVC,CK.IMKSMC,CK.IMKSMC_KVC,CK.PPK,CK.PPK_KVC,CK.CARDKEYPROFILEID , CKP.DESCRIPTION AS PROFILEDES");
        sb.append(" FROM ECMS_ONLINE_CARDKEYS CK , ECMS_ONLINE_CARDKEYPROFILE CKP WHERE CK.CARDKEYPROFILEID=CKP.ID ");
        try {
            ps = conOnline.prepareStatement(sb.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                CardKeybean keyBean = new CardKeybean();

                keyBean.setId(rs.getString("CARDKEYID"));
                keyBean.setDescription(rs.getString("DESCRIPTION"));

                keyBean.setPvka(rs.getString("PVKA"));
                keyBean.setPvkaKVC(rs.getString("PVKA_KVC"));
                keyBean.setPvkb(rs.getString("PVKB"));
                keyBean.setPvkbKVC(rs.getString("PVKB_KVC"));
                keyBean.setPvk(rs.getString("PVK"));
                keyBean.setPvkKVC(rs.getString("PVK_KVC"));
                keyBean.setCvk1a(rs.getString("CVK1A"));
                keyBean.setCvk1aKVC(rs.getString("CVK1A_KVC"));
                keyBean.setCvk1b(rs.getString("CVK1B"));
                keyBean.setCvk1bKVC(rs.getString("CVK1B_KVC"));
                keyBean.setCvk1(rs.getString("CVK1"));
                keyBean.setCvk1KVC(rs.getString("CVK1_KVC"));
                keyBean.setCvk2a(rs.getString("CVK2A"));
                keyBean.setCvk2aKVC(rs.getString("CVK2A_KVC"));
                keyBean.setCvk2b(rs.getString("CVK2B"));
                keyBean.setCvk2bKVC(rs.getString("CVK2B_KVC"));
                keyBean.setCvk2(rs.getString("CVK2"));
                keyBean.setCvk2KVC(rs.getString("CVK2_KVC"));
                keyBean.setIcvka(rs.getString("ICVKA"));
                keyBean.setIcvkaKVC(rs.getString("ICVKA_KVC"));
                keyBean.setIcvkb(rs.getString("ICVKB"));
                keyBean.setIcvkbKVC(rs.getString("ICVKB_KVC"));
                keyBean.setIcvk(rs.getString("ICVK"));
                keyBean.setIcvkKVC(rs.getString("ICVK_KVC"));
                keyBean.setCscka(rs.getString("CSCKA"));
                keyBean.setCsckaKVC(rs.getString("CSCKA_KVC"));
                keyBean.setCsckb(rs.getString("CSCKB"));
                keyBean.setCsckbKVC(rs.getString("CSCKB_KVC"));
                keyBean.setCsck(rs.getString("CSCK"));
                keyBean.setCsckKVC(rs.getString("CSCK_KVC"));
                keyBean.setImkac(rs.getString("IMKAC"));
                keyBean.setImkacKVC(rs.getString("IMKAC_KVC"));
                keyBean.setZcmk(rs.getString("ZCMK"));
                keyBean.setZcmkKVC(rs.getString("ZCMK_KVC"));
                keyBean.setImksmi(rs.getString("IMKSMI"));
                keyBean.setImksmiKVC(rs.getString("IMKSMI_KVC"));
                keyBean.setImksmc(rs.getString("IMKSMC"));
                keyBean.setImksmcKVC(rs.getString("IMKSMC_KVC"));
                keyBean.setPpk(rs.getString("PPK"));
                keyBean.setPpkKVC(rs.getString("PPK_KVC"));
                keyBean.setCardKeyProfileDes(rs.getString("PROFILEDES"));

                keyBean.setCardkeyprofileid(rs.getString("CARDKEYPROFILEID"));

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

    public List<CardKeyProfilebean> getProfileKeyBeanList(Connection conOnline, int cardKeyId) throws Exception {

        List<CardKeyProfilebean> keyBeanList = new ArrayList<CardKeyProfilebean>();

        ResultSet rs = null;
        PreparedStatement ps = null;

        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT CKP.ID , CKP.DESCRIPTION , CKP.PVK , CKP.CVK1, CKP.CVK2, CKP.ICVK, CKP.CSCK , CKP.PPK , CKP.IMKAC , CKP.IMKSMC , CKP.IMKSMI , CKP.ZCMK, CKP.ISPVKPAIR, CKP.ISCVK1PAIR , CKP.ISCVK2PAIR , CKP.ISICVKPAIR, CKP.ISCSCKPAIR , CKP.DCVK ");
        sb.append(" FROM ECMS_ONLINE_CARDKEYS CK , ECMS_ONLINE_CARDKEYPROFILE CKP WHERE CK.CARDKEYPROFILEID=CKP.ID AND CK.CARDKEYID = ? ");
        try {
            ps = conOnline.prepareStatement(sb.toString());
            ps.setInt(1, cardKeyId);
            rs = ps.executeQuery();

            while (rs.next()) {
                CardKeyProfilebean profilebean = new CardKeyProfilebean();

                profilebean.setPvk(rs.getString("PVK"));
                profilebean.setCvk1(rs.getString("CVK1"));
                profilebean.setCvk2(rs.getString("CVK2"));
                profilebean.setICVK(rs.getString("ICVK"));
                profilebean.setCSCK(rs.getString("CSCK"));
                profilebean.setPPK(rs.getString("PPK"));
                profilebean.setIMKAC(rs.getString("IMKAC"));
                profilebean.setIMKSMC(rs.getString("IMKSMC"));
                profilebean.setIMKSMI(rs.getString("IMKSMI"));
                profilebean.setZCMK(rs.getString("ZCMK"));
                profilebean.setIsPVKpair(rs.getString("ISPVKPAIR"));
                profilebean.setIsCVK1Pair(rs.getString("ISCVK1PAIR"));
                profilebean.setIsCVK2pair(rs.getString("ISCVK2PAIR"));
                profilebean.setIsICVKpair(rs.getString("ISICVKPAIR"));
                profilebean.setIsCSCKpair(rs.getString("ISCSCKPAIR"));
                profilebean.setDescription(rs.getString("DESCRIPTION"));


                profilebean.setProfileId(rs.getString("ID"));

                keyBeanList.add(profilebean);
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

    public List<CardKeyProfilebean> getAllProfileKeyBeanList(Connection conOnline) throws Exception {

        List<CardKeyProfilebean> keyBeanList = new ArrayList<CardKeyProfilebean>();

        ResultSet rs = null;
        PreparedStatement ps = null;

        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT CKP.ID , CKP.DESCRIPTION , CKP.PVK , CKP.CVK1, CKP.CVK2, CKP.ICVK, CKP.CSCK , CKP.PPK , CKP.IMKAC , CKP.IMKSMC , CKP.IMKSMI , CKP.ZCMK, CKP.ISPVKPAIR, CKP.ISCVK1PAIR , CKP.ISCVK2PAIR , CKP.ISICVKPAIR, CKP.ISCSCKPAIR , CKP.DCVK ");
        sb.append(" FROM ECMS_ONLINE_CARDKEYPROFILE CKP  ");
        try {
            ps = conOnline.prepareStatement(sb.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                CardKeyProfilebean profilebean = new CardKeyProfilebean();

                profilebean.setPvk(rs.getString("PVK"));
                profilebean.setCvk1(rs.getString("CVK1"));
                profilebean.setCvk2(rs.getString("CVK2"));
                profilebean.setICVK(rs.getString("ICVK"));
                profilebean.setCSCK(rs.getString("CSCK"));
                profilebean.setPPK(rs.getString("PPK"));
                profilebean.setIMKAC(rs.getString("IMKAC"));
                profilebean.setIMKSMC(rs.getString("IMKSMC"));
                profilebean.setIMKSMI(rs.getString("IMKSMI"));
                profilebean.setZCMK(rs.getString("ZCMK"));
                profilebean.setIsPVKpair(rs.getString("ISPVKPAIR"));
                profilebean.setIsCVK1Pair(rs.getString("ISCVK1PAIR"));
                profilebean.setIsCVK2pair(rs.getString("ISCVK2PAIR"));
                profilebean.setIsICVKpair(rs.getString("ISICVKPAIR"));
                profilebean.setIsCSCKpair(rs.getString("ISCSCKPAIR"));
                profilebean.setDescription(rs.getString("DESCRIPTION"));


                profilebean.setProfileId(rs.getString("ID"));

                keyBeanList.add(profilebean);
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

    public boolean updatePVKAKeyDetails(Connection conOnline, CardKeybean keybean) throws Exception {
        boolean success = false;
        PreparedStatement updateState = null;
        try {
            updateState = conOnline.prepareStatement("UPDATE ECMS_ONLINE_CARDKEYS SET PVKA = ? , PVKA_KVC = ? WHERE CARDKEYID = ?");

            updateState.setString(1, keybean.getPvka());
            updateState.setString(2, keybean.getPvkaKVC());
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

    public boolean updatePVKBKeyDetails(Connection conOnline, CardKeybean keybean) throws Exception {
        boolean success = false;
        PreparedStatement updateState = null;
        try {
            updateState = conOnline.prepareStatement("UPDATE ECMS_ONLINE_CARDKEYS SET PVKB = ? , PVKB_KVC = ? WHERE CARDKEYID = ?");

            updateState.setString(1, keybean.getPvkb());
            updateState.setString(2, keybean.getPvkbKVC());
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

    public boolean updatePVKKeyDetails(Connection conOnline, CardKeybean keybean) throws Exception {
        boolean success = false;
        PreparedStatement updateState = null;
        try {
            updateState = conOnline.prepareStatement("UPDATE ECMS_ONLINE_CARDKEYS SET PVK = ? , PVK_KVC = ? WHERE CARDKEYID = ?");

            updateState.setString(1, keybean.getPvk());
            updateState.setString(2, keybean.getPvkKVC());
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

    public boolean updateCVK1AKeyDetails(Connection conOnline, CardKeybean keybean) throws Exception {
        boolean success = false;
        PreparedStatement updateState = null;
        try {
            updateState = conOnline.prepareStatement("UPDATE ECMS_ONLINE_CARDKEYS SET CVK1A = ? , CVK1A_KVC = ? WHERE CARDKEYID = ?");

            updateState.setString(1, keybean.getCvk1a());
            updateState.setString(2, keybean.getCvk1aKVC());
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

    public boolean updateCVK1BKeyDetails(Connection conOnline, CardKeybean keybean) throws Exception {
        boolean success = false;
        PreparedStatement updateState = null;
        try {
            updateState = conOnline.prepareStatement("UPDATE ECMS_ONLINE_CARDKEYS SET CVK1B = ? , CVK1B_KVC = ? WHERE CARDKEYID = ?");

            updateState.setString(1, keybean.getCvk1b());
            updateState.setString(2, keybean.getCvk1bKVC());
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

    public boolean updateCVK1KeyDetails(Connection conOnline, CardKeybean keybean) throws Exception {
        boolean success = false;
        PreparedStatement updateState = null;
        try {
            updateState = conOnline.prepareStatement("UPDATE ECMS_ONLINE_CARDKEYS SET CVK1 = ? , CVK1_KVC = ? WHERE CARDKEYID = ?");

            updateState.setString(1, keybean.getCvk1());
            updateState.setString(2, keybean.getCvk1KVC());
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

    public boolean updateCVK2AKeyDetails(Connection conOnline, CardKeybean keybean) throws Exception {
        boolean success = false;
        PreparedStatement updateState = null;
        try {
            updateState = conOnline.prepareStatement("UPDATE ECMS_ONLINE_CARDKEYS SET CVK2A = ? , CVK2A_KVC = ? WHERE CARDKEYID = ?");

            updateState.setString(1, keybean.getCvk2a());
            updateState.setString(2, keybean.getCvk2aKVC());
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

    public boolean updateCVK2BKeyDetails(Connection conOnline, CardKeybean keybean) throws Exception {
        boolean success = false;
        PreparedStatement updateState = null;
        try {
            updateState = conOnline.prepareStatement("UPDATE ECMS_ONLINE_CARDKEYS SET CVK2B = ? , CVK2B_KVC = ? WHERE CARDKEYID = ?");

            updateState.setString(1, keybean.getCvk2b());
            updateState.setString(2, keybean.getCvk2bKVC());
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

    public boolean updateCVK2KeyDetails(Connection conOnline, CardKeybean keybean) throws Exception {
        boolean success = false;
        PreparedStatement updateState = null;
        try {
            updateState = conOnline.prepareStatement("UPDATE ECMS_ONLINE_CARDKEYS SET CVK2 = ? , CVK2_KVC = ? WHERE CARDKEYID = ?");

            updateState.setString(1, keybean.getCvk2());
            updateState.setString(2, keybean.getCvk2KVC());
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

    public boolean updateICVKAKeyDetails(Connection conOnline, CardKeybean keybean) throws Exception {
        boolean success = false;
        PreparedStatement updateState = null;
        try {
            updateState = conOnline.prepareStatement("UPDATE ECMS_ONLINE_CARDKEYS SET ICVKA = ? , ICVKA_KVC = ? WHERE CARDKEYID = ?");

            updateState.setString(1, keybean.getIcvka());
            updateState.setString(2, keybean.getIcvkaKVC());
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

    public boolean updateICVKBKeyDetails(Connection conOnline, CardKeybean keybean) throws Exception {
        boolean success = false;
        PreparedStatement updateState = null;
        try {
            updateState = conOnline.prepareStatement("UPDATE ECMS_ONLINE_CARDKEYS SET ICVKB = ? , ICVKB_KVC = ? WHERE CARDKEYID = ?");

            updateState.setString(1, keybean.getIcvkb());
            updateState.setString(2, keybean.getIcvkbKVC());
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

    public boolean updateICVKKeyDetails(Connection conOnline, CardKeybean keybean) throws Exception {
        boolean success = false;
        PreparedStatement updateState = null;
        try {
            updateState = conOnline.prepareStatement("UPDATE ECMS_ONLINE_CARDKEYS SET ICVK = ? , ICVK_KVC = ? WHERE CARDKEYID = ?");

            updateState.setString(1, keybean.getIcvk());
            updateState.setString(2, keybean.getIcvkKVC());
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

    public boolean updateCSCKAKeyDetails(Connection conOnline, CardKeybean keybean) throws Exception {
        boolean success = false;
        PreparedStatement updateState = null;
        try {
            updateState = conOnline.prepareStatement("UPDATE ECMS_ONLINE_CARDKEYS SET CSCKA = ? , CSCKA_KVC = ? WHERE CARDKEYID = ?");

            updateState.setString(1, keybean.getCscka());
            updateState.setString(2, keybean.getCsckaKVC());
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

    public boolean updateCSCKBKeyDetails(Connection conOnline, CardKeybean keybean) throws Exception {
        boolean success = false;
        PreparedStatement updateState = null;
        try {
            updateState = conOnline.prepareStatement("UPDATE ECMS_ONLINE_CARDKEYS SET CSCKB = ? , CSCKB_KVC = ? WHERE CARDKEYID = ?");

            updateState.setString(1, keybean.getCsckb());
            updateState.setString(2, keybean.getCsckbKVC());
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

    public boolean updateCSCKKeyDetails(Connection conOnline, CardKeybean keybean) throws Exception {
        boolean success = false;
        PreparedStatement updateState = null;
        try {
            updateState = conOnline.prepareStatement("UPDATE ECMS_ONLINE_CARDKEYS SET CSCK = ? , CSCK_KVC = ? WHERE CARDKEYID = ?");

            updateState.setString(1, keybean.getCsck());
            updateState.setString(2, keybean.getCsckKVC());
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

    public boolean updateIMKACKeyDetails(Connection conOnline, CardKeybean keybean) throws Exception {
        boolean success = false;
        PreparedStatement updateState = null;
        try {
            updateState = conOnline.prepareStatement("UPDATE ECMS_ONLINE_CARDKEYS SET IMKAC = ? , IMKAC_KVC = ? WHERE CARDKEYID = ?");

            updateState.setString(1, keybean.getImkac());
            updateState.setString(2, keybean.getImkacKVC());
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

    public boolean updateZCMKKeyDetails(Connection conOnline, CardKeybean keybean) throws Exception {
        boolean success = false;
        PreparedStatement updateState = null;
        try {
            updateState = conOnline.prepareStatement("UPDATE ECMS_ONLINE_CARDKEYS SET ZCMK = ? , ZCMK_KVC = ? WHERE CARDKEYID = ?");

            updateState.setString(1, keybean.getZcmk());
            updateState.setString(2, keybean.getZcmkKVC());
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

    public boolean updateIMKSMIKeyDetails(Connection conOnline, CardKeybean keybean) throws Exception {
        boolean success = false;
        PreparedStatement updateState = null;
        try {
            updateState = conOnline.prepareStatement("UPDATE ECMS_ONLINE_CARDKEYS SET IMKSMI = ? , IMKSMI_KVC = ? WHERE CARDKEYID = ?");

            updateState.setString(1, keybean.getImksmi());
            updateState.setString(2, keybean.getImksmiKVC());
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

    public boolean updateIMKSMCKeyDetails(Connection conOnline, CardKeybean keybean) throws Exception {
        boolean success = false;
        PreparedStatement updateState = null;
        try {
            updateState = conOnline.prepareStatement("UPDATE ECMS_ONLINE_CARDKEYS SET IMKSMC = ? , IMKSMC_KVC = ? WHERE CARDKEYID = ?");

            updateState.setString(1, keybean.getImksmc());
            updateState.setString(2, keybean.getImksmcKVC());
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

    public boolean updatePPKKeyDetails(Connection conOnline, CardKeybean keybean) throws Exception {
        boolean success = false;
        PreparedStatement updateState = null;
        try {
            updateState = conOnline.prepareStatement("UPDATE ECMS_ONLINE_CARDKEYS SET PPK = ? , PPK_KVC = ? WHERE CARDKEYID = ?");

            updateState.setString(1, keybean.getPpk());
            updateState.setString(2, keybean.getPpkKVC());
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

    public boolean insertCardKeyDetails(Connection conOnline, CardKeybean keybean) throws Exception {
        boolean success = false;
        PreparedStatement insertState = null;
        try {
            insertState = conOnline.prepareStatement("INSERT INTO ECMS_ONLINE_CARDKEYS(CARDKEYID,DESCRIPTION,CARDKEYPROFILEID) VALUES(?,?,?)");

            insertState.setInt(1, Integer.parseInt(keybean.getId()));
            insertState.setString(2, keybean.getDescription());
            insertState.setInt(3, Integer.parseInt(keybean.getCardkeyprofileid()));
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

    public boolean updateCardKeyDetails(Connection conOnline, CardKeybean keybean) throws Exception {
        boolean success = false;
        PreparedStatement insertState = null;
        try {
            insertState = conOnline.prepareStatement("UPDATE ECMS_ONLINE_CARDKEYS SET DESCRIPTION = ? , CARDKEYPROFILEID = ? WHERE CARDKEYID  = ? ");

            insertState.setString(1, keybean.getDescription());
            insertState.setInt(2, Integer.parseInt(keybean.getCardkeyprofileid()));
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

    public int deleteCardKeyDetails(Connection conOnline, String keyId) throws Exception {
        int num = -1;
        PreparedStatement insertState = null;
        try {
            insertState = conOnline.prepareStatement("DELETE FROM ECMS_ONLINE_CARDKEYS WHERE CARDKEYID = ?");

            insertState.setInt(1, Integer.parseInt(keyId));
            num = insertState.executeUpdate();

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception e) {
            throw e;
        } finally {
            insertState.close();
        }
        return num;
    }
}
