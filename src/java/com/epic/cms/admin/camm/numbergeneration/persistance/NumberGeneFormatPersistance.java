/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.camm.numbergeneration.persistance;

import com.epic.cms.admin.camm.numbergeneration.bean.NumberFormatBean;
import com.epic.cms.admin.camm.numbergeneration.bean.NumberFormatFieldBean;
import com.epic.cms.admin.camm.numbergeneration.bean.NumberGenerationFormatBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardTypeMgtBean;
import com.epic.cms.system.util.datetime.SystemDateTime;
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
 * @author upul
 */
public class NumberGeneFormatPersistance {

    private ResultSet rs;

    /**
     * getNumberFormatFields  form database
     * @param con
     * @return
     * @throws Exception 
     */
    public List<NumberFormatFieldBean> getNumberFormatFields(Connection con) throws Exception {
        PreparedStatement getNumberFormatFieldsStat = null;
        List<NumberFormatFieldBean> formatFieldBeanLst = new ArrayList<NumberFormatFieldBean>();
        NumberFormatFieldBean formatFieldBean = null;

        try {
            getNumberFormatFieldsStat = con.prepareStatement("SELECT FIELDTYPECODE,DESCRIPTION  FROM CARDNUMBERFROMATFIELDTYPE WHERE DISPLAYSTATUS =? ");


            getNumberFormatFieldsStat.setString(1, StatusVarList.YESSTATUS);
            rs = getNumberFormatFieldsStat.executeQuery();

            while (rs.next()) {

                formatFieldBean = new NumberFormatFieldBean();
                formatFieldBean.setFormatCode(rs.getString("FIELDTYPECODE"));
                formatFieldBean.setDescription(rs.getString("DESCRIPTION"));

                formatFieldBeanLst.add(formatFieldBean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getNumberFormatFieldsStat.close();
        }
        return formatFieldBeanLst;
    }

    /**
     * getNumberFormatCodeList
     * @param con
     * @return
     * @throws Exception 
     */
    public List<NumberFormatFieldBean> getNumberFormatCodeList(Connection con) throws Exception {
        PreparedStatement getNumberFormatFieldsStat = null;
        List<NumberFormatFieldBean> formatFieldBeanLst = new ArrayList<NumberFormatFieldBean>();
        NumberFormatFieldBean formatFieldBean = null;

        try {
            getNumberFormatFieldsStat = con.prepareStatement("SELECT NUMBERFORMATCODE,DESCRIPTION,CARDTYPE,CARDNUMBERLENGTH,PRODUCTIONMODE  FROM CARDNUMBERFORMAT ");



            rs = getNumberFormatFieldsStat.executeQuery();

            while (rs.next()) {

                formatFieldBean = new NumberFormatFieldBean();
                formatFieldBean.setFormatCode(rs.getString("NUMBERFORMATCODE"));
                formatFieldBean.setDescription(rs.getString("DESCRIPTION"));
                formatFieldBean.setCardType(rs.getString("CARDTYPE"));
                formatFieldBean.setNumberLength(rs.getString("CARDNUMBERLENGTH"));
                formatFieldBean.setProductMode(rs.getString("PRODUCTIONMODE"));


                formatFieldBeanLst.add(formatFieldBean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getNumberFormatFieldsStat.close();
        }
        return formatFieldBeanLst;
    }

    /**
     * getSelectedNumberFormatCode
     * @param con
     * @return
     * @throws Exception 
     */
    public NumberFormatFieldBean getSelectedNumberFormatCode(Connection con, String formatCode) throws Exception {
        PreparedStatement getNumberFormatFieldsStat = null;

        NumberFormatFieldBean formatFieldBean = null;

        try {
            getNumberFormatFieldsStat = con.prepareStatement("SELECT NUMBERFORMATCODE,DESCRIPTION,CARDTYPE,CARDNUMBERLENGTH,PRODUCTIONMODE  FROM CARDNUMBERFORMAT  WHERE NUMBERFORMATCODE=? ");


            getNumberFormatFieldsStat.setString(1, formatCode);
            rs = getNumberFormatFieldsStat.executeQuery();

            while (rs.next()) {

                formatFieldBean = new NumberFormatFieldBean();
                formatFieldBean.setFormatCode(rs.getString("NUMBERFORMATCODE"));
                formatFieldBean.setDescription(rs.getString("DESCRIPTION"));
                formatFieldBean.setCardType(rs.getString("CARDTYPE"));
                formatFieldBean.setNumberLength(rs.getString("CARDNUMBERLENGTH"));
                formatFieldBean.setProductMode(rs.getString("PRODUCTIONMODE"));


            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getNumberFormatFieldsStat.close();
        }
        return formatFieldBean;
    }

    /**
     * getSelectedNumberFormatFields
     * @param con
     * @return
     * @throws Exception 
     */
    public List<NumberFormatBean> getSelectedNumberFormatFields(Connection con, String formatCode) throws Exception {
        PreparedStatement getNumberFormatFieldsStat = null;
        List<NumberFormatBean> formatFieldBeanLst = new ArrayList<NumberFormatBean>();
        NumberFormatBean formatBean = null;

        try {
            getNumberFormatFieldsStat = con.prepareStatement("SELECT NUMBERFORMATCODE,FIELDTYPE,FROMDIGIT,TODIGIT FROM CARDNUMBERFORMATFIELD WHERE NUMBERFORMATCODE=? ORDER BY TODIGIT  ");
            getNumberFormatFieldsStat.setString(1, formatCode);
            rs = getNumberFormatFieldsStat.executeQuery();

            while (rs.next()) {

                formatBean = new NumberFormatBean();
                formatBean.setFormatCode(rs.getString("NUMBERFORMATCODE"));
                formatBean.setFieldTypeCode(rs.getString("FIELDTYPE"));
                formatBean.setFromDigit(Integer.parseInt(rs.getString("FROMDIGIT")));
                formatBean.setToDigit(Integer.parseInt(rs.getString("TODIGIT")));

                formatFieldBeanLst.add(formatBean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getNumberFormatFieldsStat.close();
        }
        return formatFieldBeanLst;
    }

    /**
     * getCardTypes
     * @param con
     * @return
     * @throws Exception 
     */
    public List<CardTypeMgtBean> getCardTypes(Connection con) throws Exception {
        PreparedStatement getAllCardTypesStat = null;
        List<CardTypeMgtBean> cardTypeMgtBeanLst = new ArrayList<CardTypeMgtBean>();
        CardTypeMgtBean cardTypeMgtBean = null;

        try {
            getAllCardTypesStat = con.prepareStatement("SELECT CARDTYPECODE,DESCRIPTION FROM CARDTYPE WHERE STATUS=? ");


            getAllCardTypesStat.setString(1, StatusVarList.ACTIVE_STATUS);
            rs = getAllCardTypesStat.executeQuery();

            while (rs.next()) {

                cardTypeMgtBean = new CardTypeMgtBean();
                cardTypeMgtBean.setCardTypeCode(rs.getString("CARDTYPECODE"));
                cardTypeMgtBean.setDescription(rs.getString("DESCRIPTION"));

                cardTypeMgtBeanLst.add(cardTypeMgtBean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllCardTypesStat.close();
        }
        return cardTypeMgtBeanLst;
    }

    /**
     * insert number format details to CARDNUMBERFORMAT and CARDNUMBERFORMATFIELD tables
     * @param con
     * @param numFormatFieldBean
     * @param numFormatBeanList
     * @return
     * @throws Exception 
     */
    public int insertNumberFormatFieldRecord(Connection con, NumberGenerationFormatBean numFormatFieldBean, List<NumberFormatBean> numFormatBeanList, String numFormatCode, String assignBin[]) throws Exception {
        int resultId = -1;
        int j = 0;
        int k = 0;
        int result = -1;

        PreparedStatement insertStat1 = null;
        PreparedStatement insertStat2 = null;
        PreparedStatement insertStat3 = null;

        try {
            //insert one record at a time to CARDNUMBERFORMAT table
            String query = "INSERT INTO CARDNUMBERFORMAT "
                    + "(NUMBERFORMATCODE,DESCRIPTION,CARDTYPE,LASTUPDATEDUSER,"
                    + "CAREATEDTIME,CARDNUMBERLENGTH)"
                    + "VALUES(?,?,?,?,?,?)";

            insertStat1 = con.prepareStatement(query);

            insertStat1.setString(1, numFormatFieldBean.getFormatCode());
            insertStat1.setString(2, numFormatFieldBean.getDescription());
            insertStat1.setString(3, numFormatFieldBean.getCardType());
            insertStat1.setString(4, numFormatFieldBean.getLastUpdateUser());
            insertStat1.setTimestamp(5, SystemDateTime.getSystemDataAndTime());
            insertStat1.setString(6, numFormatFieldBean.getCardNumberLength());
          //  insertStat1.setString(7, numFormatFieldBean.getProductMode());

            resultId = insertStat1.executeUpdate();

            //insert multiple records at a time to CARDNUMBERFORMATFIELD table
            String loopQuery = "INSERT INTO CARDNUMBERFORMATFIELD "
                    + "(NUMBERFORMATCODE,FIELDTYPE,FROMDIGIT,TODIGIT)"
                    + "VALUES(?,?,?,?)";

            for (int i = 0; i < numFormatBeanList.size(); i++) {
                insertStat2 = con.prepareStatement(loopQuery);

                insertStat2.setString(1, numFormatBeanList.get(i).getFormatCode());
                insertStat2.setString(2, numFormatBeanList.get(i).getFieldTypeCode());
                insertStat2.setInt(3, numFormatBeanList.get(i).getFromDigit());
                insertStat2.setInt(4, numFormatBeanList.get(i).getToDigit());


                j = insertStat2.executeUpdate();

                if (j == 0) {
                    throw new Exception();
                }

            }

            for (int i = 0; i < assignBin.length; i++) {

                insertStat3 = con.prepareStatement("INSERT INTO CARDNUMBERFORMATBIN "
                        + "(NUMBERFORMATCODE,BIN,SEQUENCENO ) "
                        + " VALUES (?,?,?) ");

                insertStat3.setString(1, numFormatCode);
                insertStat3.setString(2, assignBin[i]);
                insertStat3.setString(3, generateSequenceNumber(numFormatBeanList));


                k = insertStat3.executeUpdate();
                if (k == 0) {
                    throw new Exception();
                }
            }


        } catch (Exception ex) {

            throw ex;

        } finally {

            try {
                if(null != insertStat1){
                insertStat1.close();
                }
                if(null != insertStat2){
                insertStat2.close();
                }
                if(null != insertStat3){
                insertStat3.close();
                }                
            } catch (Exception e) {
                throw e;
            }

        }
        if (resultId > 0 && j > 0 && k > 0) {
            result = 1;

        }

        return result;
    }

    /**
     * updateNumberFormatCodeDetail
     * @param con
     * @param numFormatFieldBean
     * @param numFormatBeanList
     * @return 
     */
    public int updateNumberFormatCodeDetail(Connection con, NumberGenerationFormatBean numFormatFieldBean, List<NumberFormatBean> numFormatBeanList, String numFormatCode, String assignBin[]) throws Exception {

        //initializing varialbls
        NumberGenerationFormatBean formatBean = null;
        int resultId = -1;
        int result1 = -1;
        int result2 = -1;
        int result = -1;
        int row = -1;

        PreparedStatement updateStat = null;
        PreparedStatement deleteStat = null;
        PreparedStatement insertStat = null;


        try {

            //insert one record at a time to CARDNUMBERFORMAT table
            String query = "UPDATE CARDNUMBERFORMAT NF SET NF.DESCRIPTION=?, "
                    + " NF.CARDTYPE=?, NF.CARDNUMBERLENGTH=?,LASTUPDATEDUSER=?,LASTUPDATEDTIME=?,PRODUCTIONMODE=? "
                    + " WHERE NF.NUMBERFORMATCODE=?";

            updateStat = con.prepareStatement(query);

            updateStat.setString(1, numFormatFieldBean.getDescription());
            updateStat.setString(2, numFormatFieldBean.getCardType());
            updateStat.setString(3, numFormatFieldBean.getCardNumberLength());
            updateStat.setString(4, numFormatFieldBean.getLastUpdateUser());
            updateStat.setTimestamp(5, SystemDateTime.getSystemDataAndTime());
            updateStat.setString(6, numFormatFieldBean.getProductMode());
            updateStat.setString(7, numFormatFieldBean.getFormatCode());


            resultId = updateStat.executeUpdate();

            if (resultId == 1) {

                //delete all card number format fields before update new

                String deleteQuery = "DELETE FROM CARDNUMBERFORMATFIELD NF WHERE NF.NUMBERFORMATCODE=? ";

                deleteStat = con.prepareStatement(deleteQuery);
                deleteStat.setString(1, numFormatFieldBean.getFormatCode());
                row = deleteStat.executeUpdate();

                if (row == 0) {
                    throw new Exception();
                }


                if (row != 0) {

                    //insert multiple records at a time to CARDNUMBERFORMATFIELD table
                    String loopQuery = "INSERT INTO CARDNUMBERFORMATFIELD "
                            + "(NUMBERFORMATCODE,FIELDTYPE,FROMDIGIT,TODIGIT)"
                            + "VALUES(?,?,?,?)";
                    System.out.println("numFormatBeanList.size())))))))))))))))))))))" + numFormatBeanList.size());
                    for (int i = 0; i < numFormatBeanList.size(); i++) {
                        insertStat = con.prepareStatement(loopQuery);

                        insertStat.setString(1, numFormatBeanList.get(i).getFormatCode());
                        insertStat.setString(2, numFormatBeanList.get(i).getFieldTypeCode());
                        insertStat.setInt(3, numFormatBeanList.get(i).getFromDigit());
                        insertStat.setInt(4, numFormatBeanList.get(i).getToDigit());


                        result1 = insertStat.executeUpdate();

                        if (result1 == 0) {
                            throw new Exception();
                        }

                    }
                }
            }
            if (resultId == 1) {

                //delete all card number format fields before update new

                String deleteQuery = "DELETE FROM CARDNUMBERFORMATBIN NF WHERE NF.NUMBERFORMATCODE=? ";

                deleteStat = con.prepareStatement(deleteQuery);
                deleteStat.setString(1, numFormatFieldBean.getFormatCode());
                row = deleteStat.executeUpdate();


                if (row == 0) {
                    throw new Exception();
                }

                if (row != 0) {

                    for (int i = 0; i < assignBin.length; i++) {

                        insertStat = con.prepareStatement("INSERT INTO CARDNUMBERFORMATBIN "
                                + "(NUMBERFORMATCODE,BIN,SEQUENCENO ) "
                                + " VALUES (?,?,?) ");

                        insertStat.setString(1, numFormatCode);
                        insertStat.setString(2, assignBin[i]);
                        insertStat.setString(3, generateSequenceNumber(numFormatBeanList));

                        int j = 0;
                        j = insertStat.executeUpdate();
                        result2 = j;
                        if (j == 0) {
                            throw new Exception();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw ex;

        } finally {

            try {
                insertStat.close();
                deleteStat.close();
                updateStat.close();
            } catch (Exception e) {
                throw e;
            }

        }
        if (result1 > 0 && result2 > 0) {
            result = 1;
        }
        return result;
    }

    /**
     * deleteNumberFormatCodeDetail
     * @param con
     * @param formatCode
     * @return
     * @throws Exception 
     */
    public int deleteNumberFormatCodeDetail(Connection con, String formatCode) throws Exception {

        //initializing varialbls        
        int resultId = -1;
        int result1 = -1;
        int result2 = -1;
        int result = -1;
        boolean success = false;

        PreparedStatement deleteStat = null;
        PreparedStatement deleteStats = null;
        PreparedStatement deleteStat1 = null;

        try {
            //delete one record at a time to CARDNUMBERFORMAT table
            //DELETE FROM CARDNUMBERFORMATFIELD NF WHERE NF.NUMBERFORMATCODE=?
            String query = "DELETE FROM CARDNUMBERFORMATFIELD NF WHERE NF.NUMBERFORMATCODE=? ";
            deleteStat = con.prepareStatement(query);
            deleteStat.setString(1, formatCode);
            resultId = deleteStat.executeUpdate();

            if (resultId > 0) {
                //delete all card number format fields before update new
                //DELETE FROM CARDNUMBERFORMATBIN CNB WHERE CNB.NUMBERFORMATCODE=?
                String deleteQuery = "DELETE FROM CARDNUMBERFORMATBIN CNB WHERE CNB.NUMBERFORMATCODE=? ";
                deleteStats = con.prepareStatement(deleteQuery);
                deleteStats.setString(1, formatCode);
                result1 = deleteStats.executeUpdate();


            }
            if (result1 == 0) {
                throw new SQLException();
            }
            if (result1 > 0) {
                //delete all card BIN details
                //DELETE FROM CARDNUMBERFORMAT NF WHERE NF.NUMBERFORMATCODE=?
                String deleteQuery1 = "DELETE FROM CARDNUMBERFORMAT NF WHERE NF.NUMBERFORMATCODE=? ";
                deleteStat1 = con.prepareStatement(deleteQuery1);
                deleteStat1.setString(1, formatCode);
                result2 = deleteStat1.executeUpdate();
            }

            if (result2 == 0) {
                throw new SQLException();
            }

        } catch (SQLException sqx) {
            throw sqx;

        } catch (Exception ex) {
            throw ex;

        } finally {

            try {
                deleteStat.close();
                deleteStats.close();
                deleteStat1.close();

            } catch (Exception e) {
                throw e;
            }
        }
        if (result1 > 0 && result2 > 0) {
            result = 1;
        }
        return result;
    }

    /**
     * get all production modes
     * @param con
     * @return
     * @throws SQLException 
     */
    public HashMap<String, String> getProductionModes(Connection con) throws SQLException {
        HashMap<String, String> prodModes = new HashMap<String, String>();
        PreparedStatement ps = null;
        String query = "SELECT PRODUCTIONMODECODE,DESCRIPTION FROM PRODUCTIONMODE";

        try {

            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                prodModes.put(rs.getString("PRODUCTIONMODECODE"), rs.getString("DESCRIPTION"));
            }

        } catch (SQLException sex) {
            throw sex;

        } finally {
            rs.close();
            ps.close();

        }
        return prodModes;
    }

    /**
     * retrieve the BIN which are assigned to the given card  number format
     * @param con
     * @param numFormatCode
     * @return
     * @throws Exception 
     */
    public HashMap<String, String> getAssignBinList(Connection con, String numFormatCode) throws Exception {

        PreparedStatement ps = null;
        HashMap<String, String> AssignBinList = null;

        try {
            ps = con.prepareStatement("SELECT CNB.BIN,CB.DESCRIPTION FROM CARDNUMBERFORMATBIN CNB,CARDBIN CB WHERE CB.BIN = CNB.BIN AND CNB.NUMBERFORMATCODE = ? ");
            ps.setString(1, numFormatCode);
            rs = ps.executeQuery();
            AssignBinList = new HashMap<String, String>();

            while (rs.next()) {

                AssignBinList.put(rs.getString("BIN"), rs.getString("DESCRIPTION"));
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }

        return AssignBinList;
    }

    /**
     * get the assigned BINs for the given format code and production mode
     * @param con
     * @param numFormatCode
     * @param productMode
     * @return
     * @throws Exception 
     */
    public HashMap<String, String> getAssignBinListOnCardTypeReset(Connection con, String numFormatCode) throws Exception {

        PreparedStatement ps = null;
        HashMap<String, String> preAssignBinList = null;

        try {
            ps = con.prepareStatement("SELECT CNB.BIN,CB.DESCRIPTION FROM CARDNUMBERFORMATBIN CNB,CARDBIN CB,CARDNUMBERFORMAT CNF "
                    + "WHERE CB.BIN = CNB.BIN AND CNB.NUMBERFORMATCODE = ? AND CNF.NUMBERFORMATCODE =? AND CB.ONUSSTATUS = 'YES' ");
            ps.setString(1, numFormatCode);
            ps.setString(2, numFormatCode);
           // ps.setString(3, productMode);
            rs = ps.executeQuery();
            preAssignBinList = new HashMap<String, String>();

            while (rs.next()) {

                preAssignBinList.put(rs.getString("BIN"), rs.getString("DESCRIPTION"));
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }

        return preAssignBinList;
    }

    /**
     * retrieve the BIN which are not assigned to the given card  number format
     * @param con
     * @param cardType
     * @param numFormatCode
     * @return
     * @throws Exception 
     */
    public HashMap<String, String> getNotAssignBinList(Connection con, String cardType, String numFormatCode) throws Exception {

        PreparedStatement ps = null;
        HashMap<String, String> NotAssignBinList = null;

        try {
            ps = con.prepareStatement("SELECT CB.BIN,CB.DESCRIPTION FROM CARDBIN CB WHERE CB.CARDTYPE = ? AND CB.STATUS = 'ACT' "
                    + "AND CB.BIN NOT IN (SELECT CNB.BIN FROM CARDNUMBERFORMATBIN CNB WHERE CNB.NUMBERFORMATCODE = ? )");
            ps.setString(1, cardType);
            ps.setString(2, numFormatCode);
            rs = ps.executeQuery();
            NotAssignBinList = new HashMap<String, String>();

            while (rs.next()) {

                NotAssignBinList.put(rs.getString("BIN"), rs.getString("DESCRIPTION"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }

        return NotAssignBinList;
    }

    /**\
     * get the BINs for the given card type and the production mode
     * @param con
     * @param cardType
     * @param productionMode
     * @return
     * @throws Exception 
     */
    public HashMap<String, String> getBinToCardType(Connection con, String cardType ) throws Exception {

        PreparedStatement ps = null;
        HashMap<String, String> NotAssignBinList = null;

        try {
            ps = con.prepareStatement("SELECT CB.BIN,CB.DESCRIPTION FROM CARDBIN CB WHERE CB.CARDTYPE = ?  AND CB.STATUS = 'ACT' AND CB.ONUSSTATUS = 'YES' "
                    + "AND CB.BIN NOT IN (SELECT CNB.BIN FROM CARDNUMBERFORMATBIN CNB)");
            ps.setString(1, cardType);
         //   ps.setString(2, productionMode);

            rs = ps.executeQuery();
            NotAssignBinList = new HashMap<String, String>();

            while (rs.next()) {

                NotAssignBinList.put(rs.getString("BIN"), rs.getString("DESCRIPTION"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }

        return NotAssignBinList;
    }

    /**
     * retrieve all the BINs for the given card type
     * @param con
     * @param cardType
     * @return
     * @throws Exception 
     */
    public HashMap<String, String> getCardProductBins(Connection con, String cardType) throws Exception {

        PreparedStatement ps = null;
        HashMap<String, String> cardProcuctBinList = null;

        try {

            ps = con.prepareStatement("SELECT CB.BIN,CB.DESCRIPTION FROM CARDBIN CB WHERE CB.CARDTYPE = ? AND CB.STATUS = 'ACT' ");
            ps.setString(1, cardType);
            rs = ps.executeQuery();

            cardProcuctBinList = new HashMap<String, String>();

            while (rs.next()) {

                cardProcuctBinList.put(rs.getString("BIN"), rs.getString("DESCRIPTION"));

            }

        } catch (Exception ex) {
            throw ex;

        } finally {
            rs.close();
            ps.close();
        }

        return cardProcuctBinList;
    }

    /**
     * get the sequence numbers for the assigned BINs
     * @param con
     * @param numFormatCode
     * @return
     * @throws Exception 
     */
    public HashMap<String, String> getSeqNoForUsedBINs(Connection con, String numFormatCode) throws Exception {

        PreparedStatement ps = null;
        HashMap<String, String> UsedBinList = null;

        try {
            ps = con.prepareStatement("SELECT CNB.BIN,CNB.SEQUENCENO FROM CARDNUMBERFORMATBIN CNB WHERE CNB.NUMBERFORMATCODE = ? ");
            ps.setString(1, numFormatCode);
            rs = ps.executeQuery();
            UsedBinList = new HashMap<String, String>();

            while (rs.next()) {

                UsedBinList.put(rs.getString("BIN"), rs.getString("SEQUENCENO"));
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }

        return UsedBinList;
    }

    /**
     * generate the sequence numbers according to the range
     * @param numFormatBeanList
     * @return 
     */
    private String generateSequenceNumber(List<NumberFormatBean> numFormatBeanList) {
        String seqNumber = "";
        int length = 0;

        for (int i = 0; i < numFormatBeanList.size(); i++) {
            if (numFormatBeanList.get(i).getFieldTypeCode().equals("FSEC")) {
                length = numFormatBeanList.get(i).getToDigit() - numFormatBeanList.get(i).getFromDigit();
                for (int j = 0; j < length + 1; j++) {
                    seqNumber = seqNumber.concat("0");
                }
            }
        }
        return seqNumber;
    }

    /**
     * get the sequence numbers for the given number format's BINs
     * @param con
     * @param formatCode
     * @return
     * @throws Exception 
     */
    public String getSequenceNumber(Connection con, String formatCode) throws Exception {

        PreparedStatement ps = null;
        String sequenceNo = "";

        try {

            ps = con.prepareStatement("SELECT DISTINCT CNFB.SEQUENCENO FROM CARDNUMBERFORMATBIN CNFB "
                    + "WHERE CNFB.NUMBERFORMATCODE = ? ");
            ps.setString(1, formatCode);

            rs = ps.executeQuery();

            while (rs.next()) {
                if (rs.getString("SEQUENCENO") != null) {
                    sequenceNo = rs.getString("SEQUENCENO");
                }
            }
        } catch (Exception ex) {
            throw ex;

        } finally {
            rs.close();
            ps.close();
        }

        return sequenceNo;
    }
}
