/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardTypeMgtBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.EmbossFileFormatBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.EmbossFileFormatDetailBean;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author badrika
 */
public class EmbossFileFormatMgtPersistance {

    private ResultSet rs;

    public List<EmbossFileFormatBean> getAllEmbossFileFormats(Connection cmsCon) throws Exception {

        List<EmbossFileFormatBean> allformats = new ArrayList<EmbossFileFormatBean>();
        PreparedStatement ps = null;

        try {
            String query = "SELECT EFF.CARDTYPE,EFF.FORMATCODE,EFF.DESCRIPTION AS EFFDES,EFF.RECORDCOUNT,EFF.RECORDLENGTH,EFF.RECORDFORMAT,"
                    + "ST.DESCRIPTION AS STDES,EFF.LASTUPDATEDTIME FROM EMBOSSFILEFORMAT EFF, STATUS ST WHERE EFF.STATUS=ST.STATUSCODE ";

            ps = cmsCon.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                EmbossFileFormatBean bean = new EmbossFileFormatBean();
                bean.setCardType(rs.getString("CARDTYPE"));
                bean.setFormatCode(rs.getString("FORMATCODE"));
                bean.setDescription(rs.getString("EFFDES"));
                bean.setRecordCount(rs.getString("RECORDCOUNT"));
                if (rs.getString("RECORDFORMAT").length() > 25) {
                    bean.setRecordFormatView(rs.getString("RECORDFORMAT").substring(0, 25));
                } else {
                    bean.setRecordFormatView(rs.getString("RECORDFORMAT"));
                }
                bean.setRecordLength(rs.getString("RECORDLENGTH"));
                bean.setStatusDes(rs.getString("STDES"));


                bean.setLastUpdatedTime(rs.getTimestamp("LASTUPDATEDTIME"));

                allformats.add(bean);
            }

            return allformats;

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

    /**
     * to take the list of status(active and de active)
     * @param cmsCon
     * @return
     * @throws Exception 
     */
    public List<StatusBean> getStatustList(Connection cmsCon) throws Exception {

        List<StatusBean> statustList = new ArrayList<StatusBean>();
        PreparedStatement ps = null;

        try {
            String query = "SELECT STATUSCODE, DESCRIPTION FROM STATUS WHERE STATUSCATEGORY = ?";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, StatusVarList.GENESTATUCAT);
            rs = ps.executeQuery();

            while (rs.next()) {
                StatusBean bean = new StatusBean();

                bean.setStatusCode(rs.getString("STATUSCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                statustList.add(bean);
            }

            return statustList;

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

    /**
     * to get all emboss file format details list
     * @param cmsCon
     * @return
     * @throws Exception 
     */
    public List<EmbossFileFormatDetailBean> getEmbossFileFormatDetailList(Connection cmsCon) throws Exception {

        List<EmbossFileFormatDetailBean> formatDetailList = new ArrayList<EmbossFileFormatDetailBean>();
        PreparedStatement ps = null;

        try {
            String query = "SELECT FIELDNAME FROM EMBOSSFIELDDETAIL ";

            ps = cmsCon.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                EmbossFileFormatDetailBean effdetailbean = new EmbossFileFormatDetailBean();

                effdetailbean.setFieldName(rs.getString("FIELDNAME"));


                formatDetailList.add(effdetailbean);
            }

            return formatDetailList;

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

    /**
     * to get all card types list
     * @param cmsCon
     * @return
     * @throws Exception 
     */
    public List<CardTypeMgtBean> getAllCardTypeList(Connection cmsCon) throws Exception {

        List<CardTypeMgtBean> cardTypeList = new ArrayList<CardTypeMgtBean>();
        PreparedStatement ps = null;

        try {
            String query = "SELECT CARDTYPECODE,DESCRIPTION FROM CARDTYPE ";

            ps = cmsCon.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                CardTypeMgtBean cardtypebean = new CardTypeMgtBean();

                cardtypebean.setCardTypeCode(rs.getString("CARDTYPECODE"));
                cardtypebean.setDescription(rs.getString("DESCRIPTION"));

                cardTypeList.add(cardtypebean);
            }
            return cardTypeList;

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

    /**
     * to verify card type to check the record is already exist
     * @param cmsCon
     * @param s
     * @return
     * @throws Exception 
     */
    public boolean verifyCType(Connection cmsCon, String s) throws Exception {
        boolean flag = false;
        PreparedStatement ps = null;

        try {
            String query = "SELECT CARDTYPE FROM EMBOSSFILEFORMAT WHERE CARDTYPE=?";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, s);

            rs = ps.executeQuery();
            while (rs.next()) {
                flag = true;
            }
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

        return flag;
    }

    /**
     * to get the length of record format
     * @param cmsCon
     * @param arr
     * @return
     * @throws Exception 
     */
    public int getFeildMaxLength(Connection cmsCon, String feild) throws Exception {
        int maxLen = 0;
        PreparedStatement ps = null;
        try {
            String query = "SELECT FIELDMAXLENGTH FROM EMBOSSFIELDDETAIL WHERE FIELDNAME =? ";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, feild);

            rs = ps.executeQuery();

            while (rs.next()) {
                String lenth = rs.getString("FIELDMAXLENGTH");
                maxLen = Integer.parseInt(lenth);
            }

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

        return maxLen;
    }

    /**
     * to add a new record
     * @param cmsCon
     * @param bean
     * @param recformat
     * @return
     * @throws Exception 
     */
    public int addNewEmbossFileFormat(Connection cmsCon, EmbossFileFormatBean bean, String recformat, String reclength) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {

            String query = "INSERT INTO EMBOSSFILEFORMAT (CARDTYPE,FORMATCODE,DESCRIPTION,RECORDCOUNT,RECORDLENGTH,STATUS,CREATEDTIME,LASTUPDATEDUSER,"
                    + "LASTUPDATEDTIME,RECORDFORMAT) VALUES(?,?,?,?,?,?,SYSDATE,?,?,?)";

            int rcount = Integer.parseInt(bean.getRecordCount()) / 2;

            String s = String.valueOf(rcount);

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, bean.getCardType());
            ps.setString(2, bean.getFormatCode());
            ps.setString(3, bean.getDescription());
            ps.setString(4, s);
            ps.setString(5, reclength);
            //   ps.setString(5, bean.getRecordCount());
            ps.setString(6, bean.getStatus());
            ps.setString(7, bean.getLastUpdatedUser());
            ps.setTimestamp(8, new Timestamp(bean.getLastUpdatedTime().getTime()));
            ps.setString(9, recformat);

            i = ps.executeUpdate();

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
        return i;
    }

    /**
     * to update existing record
     * @param cmsCon
     * @param bean
     * @param recformat
     * @return
     * @throws Exception 
     */
    public int updateEmbossFileFormat(Connection cmsCon, EmbossFileFormatBean bean, String recformat, String reclength) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {

            String query = "UPDATE EMBOSSFILEFORMAT SET DESCRIPTION=?,RECORDCOUNT=?,RECORDLENGTH=?,STATUS=?,"
                    + "LASTUPDATEDUSER=?,LASTUPDATEDTIME=?,RECORDFORMAT=? WHERE FORMATCODE=?";

            int rcount = Integer.parseInt(bean.getRecordCount()) / 2;
            String s = String.valueOf(rcount);

            ps = cmsCon.prepareStatement(query);

            ps.setString(1, bean.getDescription());
            ps.setString(2, s);
            ps.setString(3, reclength);
            ps.setString(4, bean.getStatus());
            ps.setString(5, bean.getLastUpdatedUser());
            ps.setTimestamp(6, new Timestamp(bean.getLastUpdatedTime().getTime()));
            ps.setString(7, recformat);
            ps.setString(8, bean.getFormatCode());

            i = ps.executeUpdate();

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
        return i;
    }

    /**
     * to delete a record
     * @param cmsCon
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteEmbossFileFomat(Connection cmsCon, String id) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM EMBOSSFILEFORMAT WHERE FORMATCODE=? ";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, id);

            i = ps.executeUpdate();

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
        return i;
    }

    /**
     * to view a selected record
     * @param con
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public EmbossFileFormatBean viewSelectedEmbossFileFormat(Connection con, String id) throws SQLException, Exception {

        EmbossFileFormatBean bean = new EmbossFileFormatBean();

        PreparedStatement ps = null;
        try {
            String query = "SELECT EFF.CARDTYPE,EFF.FORMATCODE,EFF.DESCRIPTION AS EFFDES,EFF.RECORDLENGTH,EFF.RECORDFORMAT,EFF.RECORDCOUNT,EFF.STATUS,"
                    + "ST.DESCRIPTION AS STDES,CT.DESCRIPTION AS CTDES FROM EMBOSSFILEFORMAT EFF, STATUS ST,CARDTYPE CT WHERE EFF.STATUS=ST.STATUSCODE "
                    + "AND EFF.CARDTYPE=CT.CARDTYPECODE AND EFF.FORMATCODE=? ";

            ps = con.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {

                bean.setCardType(rs.getString("CARDTYPE"));
                bean.setCardTypeDes(rs.getString("CTDES"));

                bean.setFormatCode(rs.getString("FORMATCODE"));
                bean.setDescription(rs.getString("EFFDES"));
                bean.setRecordLength(rs.getString("RECORDLENGTH"));

                String[] fieldArray = rs.getString("RECORDFORMAT").split("\\|");

                String[] arp1 = new String[12];
                String[] arp2 = new String[12];
                String[] arp3 = new String[12];
                String[] arp4 = new String[12];
                String[] arp5 = new String[12];
                String[] arp6 = new String[12];
                String[] arp7 = new String[12];


                String a1 = "";
                String a2 = "";
                String a3 = "";
                String a4 = "";
                String a5 = "";
                String a6 = "";
                String a7 = "";
                



                if (fieldArray.length < 13) {
                    bean.setrFormat1(rs.getString("RECORDFORMAT"));

                } else if (fieldArray.length < 25) {

                    for (int i = 0; i < 12; i++) {
                        arp1[i] = fieldArray[i];
                        a1 = a1 + arp1[i];
                        a1 = a1 + "|";
                    }

                    for (int j = 12; j < fieldArray.length; j++) {
                        arp2[j - 12] = fieldArray[j];
                        a2 = a2 + arp2[j - 12];
                        a2 = a2 + "|";
                    }

                    bean.setrFormat1(a1);
                    bean.setrFormat2(a2);


                } else if (fieldArray.length < 37) {

                    for (int i = 0; i < 12; i++) {
                        arp1[i] = fieldArray[i];
                        a1 = a1 + arp1[i];
                        a1 = a1 + "|";
                    }

                    for (int j = 12; j < 24; j++) {
                        arp2[j - 12] = fieldArray[j];
                        a2 = a2 + arp2[j - 12];
                        a2 = a2 + "|";
                    }

                    for (int k = 24; k < fieldArray.length; k++) {
                        arp3[k - 24] = fieldArray[k];
                        a3 = a3 + arp3[k - 24];
                        a3 = a3 + "|";
                    }

                    bean.setrFormat1(a1);
                    bean.setrFormat2(a2);
                    bean.setrFormat3(a3);

                } else if (fieldArray.length < 49) {
                    for (int i = 0; i < 12; i++) {
                        arp1[i] = fieldArray[i];
                        a1 = a1 + arp1[i];
                        a1 = a1 + "|";
                    }

                    for (int j = 12; j < 24; j++) {
                        arp2[j - 12] = fieldArray[j];
                        a2 = a2 + arp2[j - 12];
                        a2 = a2 + "|";
                    }

                    for (int k = 24; k < 36; k++) {
                        arp3[k - 24] = fieldArray[k];
                        a3 = a3 + arp3[k - 24];
                        a3 = a3 + "|";
                    }

                    for (int l = 36; l < fieldArray.length; l++) {
                        arp4[l - 36] = fieldArray[l];
                        a4 = a4 + arp4[l - 36];
                        a4 = a4 + "|";
                    }

                    bean.setrFormat1(a1);
                    bean.setrFormat2(a2);
                    bean.setrFormat3(a3);
                    bean.setrFormat4(a4);

                } else if (fieldArray.length < 61) {
                    for (int i = 0; i < 12; i++) {
                        arp1[i] = fieldArray[i];
                        a1 = a1 + arp1[i];
                        a1 = a1 + "|";
                    }

                    for (int j = 12; j < 24; j++) {
                        arp2[j - 12] = fieldArray[j];
                        a2 = a2 + arp2[j - 12];
                        a2 = a2 + "|";
                    }

                    for (int k = 24; k < 36; k++) {
                        arp3[k - 24] = fieldArray[k];
                        a3 = a3 + arp3[k - 24];
                        a3 = a3 + "|";
                    }

                    for (int l = 36; l < 48; l++) {
                        arp4[l - 36] = fieldArray[l];
                        a4 = a4 + arp4[l - 36];
                        a4 = a4 + "|";
                    }

                    for (int m = 48; m < fieldArray.length; m++) {
                        arp5[m - 48] = fieldArray[m];
                        a5 = a5 + arp5[m - 48];
                        a5 = a5 + "|";
                    }

                    bean.setrFormat1(a1);
                    bean.setrFormat2(a2);
                    bean.setrFormat3(a3);
                    bean.setrFormat4(a4);
                    bean.setrFormat5(a5);

                }else if (fieldArray.length < 73) {
                    for (int i = 0; i < 12; i++) {
                        arp1[i] = fieldArray[i];
                        a1 = a1 + arp1[i];
                        a1 = a1 + "|";
                    }

                    for (int j = 12; j < 24; j++) {
                        arp2[j - 12] = fieldArray[j];
                        a2 = a2 + arp2[j - 12];
                        a2 = a2 + "|";
                    }

                    for (int k = 24; k < 36; k++) {
                        arp3[k - 24] = fieldArray[k];
                        a3 = a3 + arp3[k - 24];
                        a3 = a3 + "|";
                    }

                    for (int l = 36; l < 48; l++) {
                        arp4[l - 36] = fieldArray[l];
                        a4 = a4 + arp4[l - 36];
                        a4 = a4 + "|";
                    }

                    for (int m = 48; m < 60; m++) {
                        arp5[m - 48] = fieldArray[m];
                        a5 = a5 + arp5[m - 48];
                        a5 = a5 + "|";
                    }
                    
                    for (int n = 60; n < fieldArray.length; n++) {
                        arp6[n - 60] = fieldArray[n];
                        a6 = a6 + arp6[n - 60];
                        a6 = a6 + "|";
                    }

                    bean.setrFormat1(a1);
                    bean.setrFormat2(a2);
                    bean.setrFormat3(a3);
                    bean.setrFormat4(a4);
                    bean.setrFormat5(a5);
                    bean.setrFormat6(a6);

                }else if(fieldArray.length < 85){
                    for (int i = 0; i < 12; i++) {
                        arp1[i] = fieldArray[i];
                        a1 = a1 + arp1[i];
                        a1 = a1 + "|";
                    }

                    for (int j = 12; j < 24; j++) {
                        arp2[j - 12] = fieldArray[j];
                        a2 = a2 + arp2[j - 12];
                        a2 = a2 + "|";
                    }

                    for (int k = 24; k < 36; k++) {
                        arp3[k - 24] = fieldArray[k];
                        a3 = a3 + arp3[k - 24];
                        a3 = a3 + "|";
                    }

                    for (int l = 36; l < 48; l++) {
                        arp4[l - 36] = fieldArray[l];
                        a4 = a4 + arp4[l - 36];
                        a4 = a4 + "|";
                    }

                    for (int m = 48; m < 60; m++) {
                        arp5[m - 48] = fieldArray[m];
                        a5 = a5 + arp5[m - 48];
                        a5 = a5 + "|";
                    }
                    
                    for (int n = 60; n < 72; n++) {
                        arp6[n - 60] = fieldArray[n];
                        a6 = a6 + arp6[n - 60];
                        a6 = a6 + "|";
                    }
                    
                    for (int o = 72; o < fieldArray.length; o++) {
                        arp7[o - 72] = fieldArray[o];
                        a7 = a7 + arp7[o - 72];
                        a7 = a7 + "|";
                    }

                    bean.setrFormat1(a1);
                    bean.setrFormat2(a2);
                    bean.setrFormat3(a3);
                    bean.setrFormat4(a4);
                    bean.setrFormat5(a5);
                    bean.setrFormat6(a6);
                    bean.setrFormat7(a7);
                }

                bean.setRecordFormat(rs.getString("RECORDFORMAT"));

                int tempCount = Integer.parseInt(rs.getString("RECORDCOUNT")) * 2;
                bean.setRecordCount(String.valueOf(tempCount));

                bean.setStatus(rs.getString("STATUS"));
                bean.setStatusDes(rs.getString("STDES"));

                //       String[] fieldArray = rs.getString("RECORDFORMAT").split("\\|");

                if (fieldArray.length > 0) {
                    bean.setRfFeild1(fieldArray[0]);
                }

                if (fieldArray.length > 1) {
                    bean.setRfSeperator1(fieldArray[1]);
                }

                if (fieldArray.length > 2) {
                    bean.setRfFeild2(fieldArray[2]);
                }

                if (fieldArray.length > 3) {
                    bean.setRfSeperator2(fieldArray[3]);
                }

                if (fieldArray.length > 4) {
                    bean.setRfFeild3(fieldArray[4]);
                }

                if (fieldArray.length > 5) {
                    bean.setRfSeperator3(fieldArray[5]);
                }

                if (fieldArray.length > 6) {
                    bean.setRfFeild4(fieldArray[6]);
                }

                if (fieldArray.length > 7) {
                    bean.setRfSeperator4(fieldArray[7]);
                }

                if (fieldArray.length > 8) {
                    bean.setRfFeild5(fieldArray[8]);
                }

                if (fieldArray.length > 9) {
                    bean.setRfSeperator5(fieldArray[9]);
                }

                if (fieldArray.length > 10) {
                    bean.setRfFeild6(fieldArray[10]);
                }

                if (fieldArray.length > 11) {
                    bean.setRfSeperator6(fieldArray[11]);
                }

                if (fieldArray.length > 12) {
                    bean.setRfFeild7(fieldArray[12]);
                }

                if (fieldArray.length > 13) {
                    bean.setRfSeperator7(fieldArray[13]);
                }

                if (fieldArray.length > 14) {
                    bean.setRfFeild8(fieldArray[14]);
                }

                if (fieldArray.length > 15) {
                    bean.setRfSeperator8(fieldArray[15]);
                }

                if (fieldArray.length > 16) {
                    bean.setRfFeild9(fieldArray[16]);
                }

                if (fieldArray.length > 17) {
                    bean.setRfSeperator9(fieldArray[17]);
                }

                if (fieldArray.length > 18) {
                    bean.setRfFeild10(fieldArray[18]);
                }

                if (fieldArray.length > 19) {
                    bean.setRfSeperator10(fieldArray[19]);
                }

                if (fieldArray.length > 20) {
                    bean.setRfFeild11(fieldArray[20]);
                }

                if (fieldArray.length > 21) {
                    bean.setRfSeperator11(fieldArray[21]);
                }

                if (fieldArray.length > 22) {
                    bean.setRfFeild12(fieldArray[22]);
                }

                if (fieldArray.length > 23) {
                    bean.setRfSeperator12(fieldArray[23]);
                }

                if (fieldArray.length > 24) {
                    bean.setRfFeild13(fieldArray[24]);
                }

                if (fieldArray.length > 25) {
                    bean.setRfSeperator13(fieldArray[25]);
                }

                if (fieldArray.length > 26) {
                    bean.setRfFeild14(fieldArray[26]);
                }

                if (fieldArray.length > 27) {
                    bean.setRfSeperator14(fieldArray[27]);
                }

                if (fieldArray.length > 28) {
                    bean.setRfFeild15(fieldArray[28]);
                }

                if (fieldArray.length > 29) {
                    bean.setRfSeperator15(fieldArray[29]);
                }

                if (fieldArray.length > 30) {
                    bean.setRfFeild16(fieldArray[30]);
                }

                if (fieldArray.length > 31) {
                    bean.setRfSeperator16(fieldArray[31]);
                }

                if (fieldArray.length > 32) {
                    bean.setRfFeild17(fieldArray[32]);
                }

                if (fieldArray.length > 33) {
                    bean.setRfSeperator17(fieldArray[33]);
                }

                if (fieldArray.length > 34) {
                    bean.setRfFeild18(fieldArray[34]);
                }

                if (fieldArray.length > 35) {
                    bean.setRfSeperator18(fieldArray[35]);
                }

                if (fieldArray.length > 36) {
                    bean.setRfFeild19(fieldArray[36]);
                }

                if (fieldArray.length > 37) {
                    bean.setRfSeperator19(fieldArray[37]);
                }

                if (fieldArray.length > 38) {
                    bean.setRfFeild20(fieldArray[38]);
                }

                if (fieldArray.length > 39) {
                    bean.setRfSeperator20(fieldArray[39]);
                }

                if (fieldArray.length > 40) {
                    bean.setRfFeild21(fieldArray[40]);
                }

                if (fieldArray.length > 41) {
                    bean.setRfSeperator21(fieldArray[41]);
                }

                if (fieldArray.length > 42) {
                    bean.setRfFeild22(fieldArray[42]);
                }

                if (fieldArray.length > 43) {
                    bean.setRfSeperator22(fieldArray[43]);
                }

                if (fieldArray.length > 44) {
                    bean.setRfFeild23(fieldArray[44]);
                }

                if (fieldArray.length > 45) {
                    bean.setRfSeperator23(fieldArray[45]);
                }

                if (fieldArray.length > 46) {
                    bean.setRfFeild24(fieldArray[46]);
                }

                if (fieldArray.length > 47) {
                    bean.setRfSeperator24(fieldArray[47]);
                }

                if (fieldArray.length > 48) {
                    bean.setRfFeild25(fieldArray[48]);
                }

                if (fieldArray.length > 49) {
                    bean.setRfSeperator25(fieldArray[49]);
                }

                if (fieldArray.length > 50) {
                    bean.setRfFeild26(fieldArray[50]);
                }

                if (fieldArray.length > 51) {
                    bean.setRfSeperator26(fieldArray[51]);
                }

                if (fieldArray.length > 52) {
                    bean.setRfFeild27(fieldArray[52]);
                }

                if (fieldArray.length > 53) {
                    bean.setRfSeperator27(fieldArray[53]);
                }

                if (fieldArray.length > 54) {
                    bean.setRfFeild28(fieldArray[54]);
                }

                if (fieldArray.length > 55) {
                    bean.setRfSeperator28(fieldArray[55]);
                }

                if (fieldArray.length > 56) {
                    bean.setRfFeild29(fieldArray[56]);
                }

                if (fieldArray.length > 57) {
                    bean.setRfSeperator29(fieldArray[57]);
                }

                if (fieldArray.length > 58) {
                    bean.setRfFeild30(fieldArray[58]);
                }

                if (fieldArray.length > 59) {
                    bean.setRfSeperator30(fieldArray[59]);
                }
                
                if (fieldArray.length > 60) {
                    bean.setRfFeild31(fieldArray[60]);
                }

                if (fieldArray.length > 61) {
                    bean.setRfSeperator31(fieldArray[61]);
                }

                if (fieldArray.length > 62) {
                    bean.setRfFeild32(fieldArray[62]);
                }

                if (fieldArray.length > 63) {
                    bean.setRfSeperator32(fieldArray[63]);
                }

                if (fieldArray.length > 64) {
                    bean.setRfFeild33(fieldArray[64]);
                }

                if (fieldArray.length > 65) {
                    bean.setRfSeperator33(fieldArray[65]);
                }

                if (fieldArray.length > 66) {
                    bean.setRfFeild34(fieldArray[66]);
                }

                if (fieldArray.length > 67) {
                    bean.setRfSeperator34(fieldArray[67]);
                }

                if (fieldArray.length > 68) {
                    bean.setRfFeild35(fieldArray[68]);
                }

                if (fieldArray.length > 69) {
                    bean.setRfSeperator35(fieldArray[69]);
                }

                if (fieldArray.length > 70) {
                    bean.setRfFeild36(fieldArray[70]);
                }

                if (fieldArray.length > 71) {
                    bean.setRfSeperator36(fieldArray[71]);
                }

                if (fieldArray.length > 72) {
                    bean.setRfFeild37(fieldArray[72]);
                }

                if (fieldArray.length > 73) {
                    bean.setRfSeperator37(fieldArray[73]);
                }

                if (fieldArray.length > 74) {
                    bean.setRfFeild38(fieldArray[74]);
                }

                if (fieldArray.length > 75) {
                    bean.setRfSeperator38(fieldArray[75]);
                }

                if (fieldArray.length > 76) {
                    bean.setRfFeild39(fieldArray[76]);
                }

                if (fieldArray.length > 77) {
                    bean.setRfSeperator39(fieldArray[77]);
                }

                if (fieldArray.length > 78) {
                    bean.setRfFeild40(fieldArray[78]);
                }

                if (fieldArray.length > 79) {
                    bean.setRfSeperator40(fieldArray[79]);
                }


            }
            return bean;

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
