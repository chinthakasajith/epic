/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.numbergenaration.persistance;

import com.epic.cms.admin.templatemgt.accounttemplate.bean.AccountTempBean;
import com.epic.cms.admin.templatemgt.customertemplate.bean.CustomerTempBean;
import com.epic.cms.admin.templatemgt.debitcardtemplate.bean.DebitCardTemplateBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardAccountBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardAccountCustomerBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardApplicationBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardBinBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardCorporateCusDetailBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardCustomerBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardEstablishmentCustomerDetailsBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardFDCustomerDetailsBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardMainCustomerDetailsBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardNumberFormateBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardSuplimentoryCustomerBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardTempBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.NumberFormateFieldTableBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.NumberGenarationDataBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.NumberGenarationSearchBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.OnlineAccountTableBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.OnlineCardTableBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.OnlineCustomerTableBean;
import com.epic.cms.camm.applicationconfirmation.bean.DebitCardAccountTemplateBean;
import com.epic.cms.system.util.logs.LogFileCreator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author janaka_h
 */
public class NumberGenarationPersistance {

    private List<NumberGenarationDataBean> searchList = null;
    private List<NumberFormateFieldTableBean> fieldList = null;
    private List<CardNumberFormateBean> numberFormateList;
    private List<NumberFormateFieldTableBean> numberFormateFieldList;
    private List<CardBinBean> binList;

    public List<NumberGenarationDataBean> getNumberGenarationSearch(Connection con, NumberGenarationSearchBean searchBean) throws Exception {

        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query



        try {

            String sql = this.makeSqlforNumberGenanarationSearch(searchBean);



            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            searchList = new ArrayList<NumberGenarationDataBean>();

            while (rs.next()) {


                NumberGenarationDataBean resultAssign = new NumberGenarationDataBean();

                resultAssign.setApplicationId(rs.getString("APPLICATIONID"));
                resultAssign.setNameOnCard(rs.getString("NAMEONCARD"));
                resultAssign.setCardType(rs.getString("CARDTYPE"));
                resultAssign.setCardProduct(rs.getString("CARDPRODUCT"));
                resultAssign.setPriorityLevel(rs.getString("PRIORITYLEVEL"));
                resultAssign.setCreateTime(rs.getTime("CREATETIME"));




                searchList.add(resultAssign);

            }

        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }

        return searchList;
    }

    private String makeSqlforNumberGenanarationSearch(NumberGenarationSearchBean searchBean) {

        String strSqlBasic = " SELECT DISTINCT CA.APPLICATIONID ,PL.DESCRIPTION AS PRIORITYLEVEL,CAP.NAMEONCARD ,CA.CREATETIME,CP.DESCRIPTION AS CARDPRODUCT, CT.DESCRIPTION AS CARDTYPE"
                + " FROM CARDAPPLICATION CA ,PRIORITYLEVEL PL ,CARDAPPLICATIONPERSONALDETAILS CAP,CARDPRODUCT CP,CARDTYPE CT,CARDAPPLICATIONSTATUS CAS "
                + " WHERE CA.CARDAPPLIACTIONDOMAIN = 'CREDIT' AND CA.PRIORITYLEVELCODE=PL.PRIORITYLEVELCODE AND CA.APPLICATIONID =CAP.APPLICATIONID AND CA.APPLICATIONID =CAS.APPLICATIONID AND CP.CARDTYPE  =CT.CARDTYPECODE AND CA.CONFIRMEDCARDPRODUCT=CP.PRODUCTCODE AND (CAS.APPLICATIONSTATUS='ACOM' OR CAS.APPLICATIONSTATUS='NRPL') ";

        String strSqlSuplmntry = " SELECT DISTINCT CA.APPLICATIONID ,PL.DESCRIPTION AS PRIORITYLEVEL,SA.NAMEONCARD ,CA.CREATETIME,CP.DESCRIPTION AS CARDPRODUCT, CT.DESCRIPTION AS CARDTYPE"
                + " FROM CARDAPPLICATION CA ,PRIORITYLEVEL PL ,SUPPLEMENTARYAPPLICATION SA,CARDPRODUCT CP,CARDTYPE CT,CARDAPPLICATIONSTATUS CAS "
                + " WHERE CA.CARDAPPLIACTIONDOMAIN = 'CREDIT' AND CA.PRIORITYLEVELCODE=PL.PRIORITYLEVELCODE AND CA.APPLICATIONID =SA.APPLICATIONID AND CA.APPLICATIONID =CAS.APPLICATIONID AND CP.CARDTYPE  =CT.CARDTYPECODE AND CA.CONFIRMEDCARDPRODUCT=CP.PRODUCTCODE AND (CAS.APPLICATIONSTATUS='ACOM' OR CAS.APPLICATIONSTATUS='NRPL') ";
        
        String strSqlEstablishment =" SELECT DISTINCT CA.APPLICATIONID ,PL.DESCRIPTION AS PRIORITYLEVEL, '' AS NAMEONCARD ,CA.CREATETIME,CP.DESCRIPTION AS CARDPRODUCT, CT.DESCRIPTION AS CARDTYPE"
                + " FROM CARDAPPLICATION CA ,PRIORITYLEVEL PL ,CARDPRODUCT CP,CARDTYPE CT,CARDAPPLICATIONSTATUS CAS "
                + " WHERE CA.CARDCATEGORY='E' AND CA.CARDAPPLIACTIONDOMAIN = 'CREDIT' AND CA.PRIORITYLEVELCODE=PL.PRIORITYLEVELCODE AND  CA.APPLICATIONID =CAS.APPLICATIONID AND CP.CARDTYPE  =CT.CARDTYPECODE AND CA.CONFIRMEDCARDPRODUCT=CP.PRODUCTCODE AND (CAS.APPLICATIONSTATUS='ACOM' OR CAS.APPLICATIONSTATUS='NRPL') ";
        

        String condition = "";

//
        if (!searchBean.getApplicationId().contentEquals("") || searchBean.getApplicationId().length() > 0) {

            condition += " AND CA.APPLICATIONID LIKE '%" + searchBean.getApplicationId() + "%'";
        }

        if (!searchBean.getPriorityLevel().contentEquals("") || searchBean.getPriorityLevel().length() > 0) {

            condition += " AND CA.PRIORITYLEVELCODE = '" + searchBean.getPriorityLevel() + "'";
        }


        if (!searchBean.getNameOnCard().contentEquals("") || searchBean.getNameOnCard().length() > 0) {

            condition += "AND CAP.NAMEONCARD LIKE '%" + searchBean.getNameOnCard() + "%'";
        }
        if (!searchBean.getCardType().contentEquals("") || searchBean.getCardType().length() > 0) {

            condition += "AND CT.CARDTYPECODE ='" + searchBean.getCardType() + "'";
        }
        if (!searchBean.getCardProduct().contentEquals("") || searchBean.getCardProduct().length() > 0) {

            condition += "AND CA.CONFIRMEDCARDPRODUCT ='" + searchBean.getCardProduct() + "'";
        }
//
//
//
//        if (!searchBean.getFromDate().contentEquals("") && !searchBean.getToDate().contentEquals("")) {
//            if (!condition.equals("")) {
//                condition += " AND ";
//            }
//            condition += "app.CREATETIME >= to_Date('" + this.stringTodate(searchBean.getFromDate()) + "','yy-mm-dd') AND app.CREATETIME <= to_Date('" + this.stringTodate(searchBean.getToDate()) + "','yy-mm-dd')";
//        } else {
//
//            if (!searchBean.getFromDate().contentEquals("")) {
//                if (!condition.equals("")) {
//                    condition += " AND ";
//                }
//                condition += "app.CREATETIME >= to_Date('" + this.stringTodate(searchBean.getFromDate()) + "','yy-mm-dd')";
//            }
//            if (!searchBean.getToDate().contentEquals("")) {
//                if (!condition.equals("")) {
//                    condition += " AND ";
//                }
//                condition += "app.CREATETIME <= to_Date('" + this.stringTodate(searchBean.getToDate()) + "','yy-mm-dd')";
//            }
//        }
//
//
//
//
        if (!condition.equals("")) {
            strSqlBasic += condition;
            strSqlSuplmntry += condition;
            strSqlEstablishment += condition;
        }
        
        strSqlBasic += " UNION ALL ";
        strSqlBasic += strSqlSuplmntry;
        strSqlBasic += " UNION ALL ";
        strSqlBasic += strSqlEstablishment;
        return strSqlBasic;
    }

    private List<NumberFormateFieldTableBean> getNumberFormateFieldByFormateCode(Connection con, String formateCode) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        fieldList = new ArrayList<NumberFormateFieldTableBean>();
        try {
            stmt = con.prepareStatement("SELECT COUNTRYNUMCODE,COUNTRYALPHA2CODE,COUNTRYALPHA3CODE,NATIONALITY FROM COUNTRY WHERE NATIONALITY IS NOT NULL");
            rs = stmt.executeQuery();

            while (rs.next()) {
                NumberFormateFieldTableBean bean = new NumberFormateFieldTableBean();

                fieldList.add(bean);

            }


        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }




        return fieldList;
    }

    public List<CardNumberFormateBean> getNumberFormatesByCardType(Connection con, String cardType) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        numberFormateList = new ArrayList<CardNumberFormateBean>();
        try {
            stmt = con.prepareStatement("SELECT NUMBERFORMATCODE,DESCRIPTION,CARDTYPE,CARDNUMBERLENGTH,LASTSEQUENCENO FROM CARDNUMBERFORMAT WHERE CARDTYPE=?");
            stmt.setString(1, cardType);
            rs = stmt.executeQuery();

            while (rs.next()) {
                CardNumberFormateBean bean = new CardNumberFormateBean();
                bean.setFormatCode(rs.getString("NUMBERFORMATCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                numberFormateList.add(bean);

            }


        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }




        return numberFormateList;
    }

    public CardNumberFormateBean getNumberFormateDetailsFromCode(Connection con, String formateCode) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        CardNumberFormateBean bean = new CardNumberFormateBean();
        try {
            stmt = con.prepareStatement("SELECT NUMBERFORMATCODE,DESCRIPTION,CARDTYPE,CARDNUMBERLENGTH,LASTSEQUENCENO FROM CARDNUMBERFORMAT WHERE NUMBERFORMATCODE=?");
            stmt.setString(1, formateCode);
            rs = stmt.executeQuery();

            if (rs.next()) {


                bean.setSequenceNumber(rs.getString("LASTSEQUENCENO"));
                bean.setCardType(rs.getString("CARDTYPE"));
                bean.setCardNumberLenght(rs.getString("CARDNUMBERLENGTH"));



            }


        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }


        return bean;


    }

    public List<CardBinBean> getBinNumberByCardType(Connection con, String cardType) throws Exception {

        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        binList = new ArrayList<CardBinBean>();
        try {
            stmt = con.prepareStatement("SELECT BIN,DESCRIPTION,CARDTYPE FROM CARDBIN WHERE CARDTYPE=?");
            stmt.setString(1, cardType);
            rs = stmt.executeQuery();

            while (rs.next()) {
                CardBinBean bean = new CardBinBean();
                bean.setBinCode(rs.getString("BIN"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                binList.add(bean);

            }


        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }




        return binList;
    }

    public List<NumberFormateFieldTableBean> getNumberFormateFieldsFromCode(Connection con, String numberFormate) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        numberFormateFieldList = new ArrayList<NumberFormateFieldTableBean>();
        try {
            stmt = con.prepareStatement("SELECT NUMBERFORMATCODE,FIELDTYPE,FROMDIGIT,TODIGIT FROM CARDNUMBERFORMATFIELD WHERE NUMBERFORMATCODE=? ORDER BY FROMDIGIT ASC ");
            stmt.setString(1, numberFormate);
            rs = stmt.executeQuery();

            while (rs.next()) {
                NumberFormateFieldTableBean bean = new NumberFormateFieldTableBean();
                bean.setFormatCode(rs.getString("NUMBERFORMATCODE"));
                bean.setFieldTypeCode(rs.getString("FIELDTYPE"));
                bean.setFromDigit(rs.getInt("FROMDIGIT"));
                bean.setToDigit(rs.getInt("TODIGIT"));

                numberFormateFieldList.add(bean);

            }


        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }



        return numberFormateFieldList;
    }

    public CardApplicationBean getApplicationDetailsFromID(Connection con, String applicationId) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        CardApplicationBean bean = new CardApplicationBean();
        try {
            stmt = con.prepareStatement("SELECT CA.APPLICATIONID,CA.CARDCATEGORY,CA.PRIORITYLEVELCODE,CA.CONFIRMEDCREDITLIMIT,CA.BRANCHCODE,CA.CONFIRMEDCARDPRODUCT,CA.IDENTIFICATIONTYPE,CA.IDENTIFICATIONNO,CA.BINPROFILE,CA.STAFFSTATUS,CA.CUSTEMPLATECODE,CA.ACCTEMPLATECODE,CA.CARDTEMPLATECODE,CA.PRODUCTIONMODE,CA.CARDAPPLIACTIONDOMAIN,CA.CARDKEYID, "
                    + "CP.CARDTYPE,CP.DISPLAYDIGITS AS PRODUCTNUMBER, BB.DISPLAYDIGIT AS BRANCHNUMBER FROM CARDAPPLICATION CA,CARDPRODUCT CP,BANKBRANCH BB WHERE APPLICATIONID=? AND CA.CONFIRMEDCARDPRODUCT=CP.PRODUCTCODE AND CA.BRANCHCODE= BB.BRANCHCODE(+) ");
            stmt.setString(1, applicationId);
            rs = stmt.executeQuery();

            if (rs.next()) {

                bean.setApplicationId(rs.getString("APPLICATIONID"));
                bean.setCardCategory(rs.getString("CARDCATEGORY"));
                bean.setBranchCode(rs.getString("BRANCHNUMBER"));
                bean.setCardProductdisplayNo(rs.getString("PRODUCTNUMBER"));
                bean.setConfirmCardProduct(rs.getString("CONFIRMEDCARDPRODUCT"));
                bean.setConfirmCreditLimit(rs.getString("CONFIRMEDCREDITLIMIT"));
                bean.setPriorityLevelCode(rs.getString("PRIORITYLEVELCODE"));
                bean.setCardType(rs.getString("CARDTYPE"));
                bean.setIdNumber(rs.getString("IDENTIFICATIONNO").toUpperCase());
                bean.setIdType(rs.getString("IDENTIFICATIONTYPE"));
                bean.setBinprofile(rs.getString("BINPROFILE"));
                bean.setStaffStatus(rs.getString("STAFFSTATUS"));
                bean.setCusTempalte(rs.getString("CUSTEMPLATECODE"));
                bean.setAcctemplate(rs.getString("ACCTEMPLATECODE"));
                bean.setCardtemplate(rs.getString("CARDTEMPLATECODE"));
                bean.setProductionMode(rs.getString("PRODUCTIONMODE"));
                bean.setCardDomain(rs.getString("CARDAPPLIACTIONDOMAIN"));
                bean.setCardKey(rs.getString("CARDKEYID"));




            }


        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }



        return bean;
    }

    public String getMaxCusIDFromCardCustomer(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getMaxCusIdState = null;
        String maxId = null;
        try {
            getMaxCusIdState = con.prepareStatement("select coalesce(Max(cc.CUSTOMERID),'0') as MAXCUSTOMERID from CARDCUSTOMER cc");

            rs = getMaxCusIdState.executeQuery();


            while (rs.next()) {

                maxId = rs.getString("MAXCUSTOMERID");

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getMaxCusIdState.close();

        }

        return maxId;
    }

    public int insertIntoCardTable(Connection con, CardBean cardbean) throws Exception {
        int resultId;

        PreparedStatement insertStat1 = null;
        try {
            insertStat1 = con.prepareStatement("INSERT INTO CARD "
                    + "(CARDNUMBER,MAINCARDNO,CARDTYPE,CARDPRODUCT,EXPIERYDATE,NAMEONCARD,CARDSTATUS,CREDITLIMIT,CASHLIMIT,OTBCREDIT, "
                    + "OTBCASH,EMBOSSSTATUS,ENCODESTATUS,PINSTATUS,LASTUPDATEDUSER,PRIORITYLEVEL,IDTYPE,IDNUMBER,ISSUEDDATE,NOGENARATEDUSER,PINMAILSTATUS,"
                    + "LASTUPDATEDTIME,CREATEDTIME,CARDNUMBERPRINTEDONCARD,SERVICECODE,LOYALTYPOINT,REDEEMPOINT,CURRENCYNOCODE,RISKPROFILECODE,TEMPCREDITAMOUNT,"
                    + "CARDCATEGORYCODE,APPLICATIONID,REISSUETHRESHHOLSPERIOD,ADDRESS1,ADDRESS2,ADDRESS3,ADDRESS4,PRODUCTIONMODE,CARDDOMAIN,CARDKEYID ) "
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,SYSDATE,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

            insertStat1.setString(1, cardbean.getCardNumber());
            insertStat1.setString(2, cardbean.getMainCardNumber());
            insertStat1.setString(3, cardbean.getCardType());
            insertStat1.setString(4, cardbean.getCardProduct());
            insertStat1.setString(5, cardbean.getExprioryDate());
            insertStat1.setString(6, cardbean.getNameOnCard());
            insertStat1.setString(7, cardbean.getCardStatus());
            insertStat1.setString(8, cardbean.getCreditLimit());
            insertStat1.setString(9, cardbean.getCashLimit());
            insertStat1.setString(10, cardbean.getAvailableBalence());
            insertStat1.setString(11, cardbean.getCashAvailableBalence());
            insertStat1.setString(12, cardbean.getEmbossStatus());
            insertStat1.setString(13, cardbean.getEncodeStatus());
            insertStat1.setString(14, cardbean.getPinStatus());
            insertStat1.setString(15, cardbean.getLastUpdatedUser());
            insertStat1.setString(16, cardbean.getPriorityLevel());
            insertStat1.setString(17, cardbean.getIdType());
            insertStat1.setString(18, cardbean.getIdNumber());
            insertStat1.setString(19, cardbean.getIssueDate());
            insertStat1.setString(20, cardbean.getNoGenaratedUser());
            insertStat1.setString(21, cardbean.getPinmailStatus());
            insertStat1.setString(22, cardbean.getCardNumberPrintedOnCard());
            insertStat1.setString(23, cardbean.getServiceCode());
            insertStat1.setString(24, cardbean.getLoyalityPoint());
            insertStat1.setString(25, cardbean.getRedeemPoint());
            insertStat1.setString(26, cardbean.getCurenncyCode());
            insertStat1.setString(27, cardbean.getRiskProfile());
            insertStat1.setString(28, cardbean.getTempotbAmount());
            insertStat1.setString(29, cardbean.getCardCategory());
            insertStat1.setString(30, cardbean.getApplicationId());
            insertStat1.setString(31, cardbean.getReissueTresholPeriod());
            insertStat1.setString(32, cardbean.getAddress1());
            insertStat1.setString(33, cardbean.getAddress2());
            insertStat1.setString(34, cardbean.getAddress3());
            insertStat1.setString(35, cardbean.getAddress4());
            insertStat1.setString(36, cardbean.getProductionMode());
            insertStat1.setString(37, cardbean.getCardDomain());
            insertStat1.setString(38, cardbean.getCardKey());



            resultId = insertStat1.executeUpdate();

        } catch (SQLException sex) {
            LogFileCreator.writeErrorToNumberGenLog(sex);
            throw sex;
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        }
        finally {
            try {
                insertStat1.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;
    }

    public int insertIntoCardCustomerTable(Connection con, CardCustomerBean cardcusbean) throws Exception {
        int resultId;

        PreparedStatement insertStat2 = null;
        try {
            insertStat2 = con.prepareStatement("INSERT INTO CARDCUSTOMER "
                    + "(CUSTOMERID,IDENTIFICATIONTYPE,IDENTIFICATIONNO,CUSTOMERNAME,CUSTOMERSTATUS,LASTUPDATEDUSER,RISKPROFILECODE,CURRENCYCODE,STAFFSTATUS )"
                    + " VALUES (?,?,?,?,?,?,?,?,?)");

            insertStat2.setString(1, cardcusbean.getCustomerId());
            insertStat2.setString(2, cardcusbean.getIdType());
            insertStat2.setString(3, cardcusbean.getIdNumber());
            insertStat2.setString(4, cardcusbean.getCustomerName());
            insertStat2.setString(5, cardcusbean.getCustomerStatus());
            insertStat2.setString(6, cardcusbean.getLastUpdatedUser());
            insertStat2.setString(7, cardcusbean.getRiskProfileCode());
            insertStat2.setString(8, cardcusbean.getCurrencyCode());
            insertStat2.setString(9, cardcusbean.getStaffStatus());




            resultId = insertStat2.executeUpdate();

       } catch (SQLException sex) {
            LogFileCreator.writeErrorToNumberGenLog(sex);
            throw sex;
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        } finally {
            try {
                insertStat2.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;
    }

    public int insertIntoCardMainCustomerTable(Connection con, CardMainCustomerDetailsBean cardmaincusbean) throws Exception {
        int resultId;
        
        System.out.println("cardmaincusbean.getCustomerId() ************* "+cardmaincusbean.getCustomerId());

        PreparedStatement insertStat3 = null;
        try {
            insertStat3 = con.prepareStatement("INSERT INTO CARDMAINCUSTOMERDETAIL "
                    + "(CUSTOMERID,INITIALS,FIRSTNAME,MIDDLENAME,LASTNAME,CREDITSCORE,CREDITLIMIT,DATEOFBIRTH,GENDER,MARITALSTATUS,NATIONALITY,PERMANENTADDRESS1, "
                    + "PERMANENTADDRESS2,PERMANENTADDRESS3 ,BILLINGADDRESS1,BILLINGADDRESS2,BILLINGADDRESS3,CITY,HOMETELEPHONE,MOBILENO,EMAIL,CONTACTNO,OFFICETELEPHONE, "
                    + "COMPANYNAME,OCCUPATION,DESIGNATION,LASTUPDATEDUSER,LASTUPDATEDTIME,CREATEDTIME ) "
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,SYSDATE) ");

            insertStat3.setString(1, cardmaincusbean.getCustomerId());
            insertStat3.setString(2, cardmaincusbean.getInitials());
            insertStat3.setString(3, cardmaincusbean.getFirstName());
            insertStat3.setString(4, cardmaincusbean.getMiddleName());
            insertStat3.setString(5, cardmaincusbean.getLastName());
            insertStat3.setString(6, cardmaincusbean.getCreditScore());
            insertStat3.setString(7, cardmaincusbean.getCreditLimit());
            insertStat3.setTimestamp(8, new Timestamp(this.stringTodate(cardmaincusbean.getDateOfBirth()).getTime()));
            insertStat3.setString(9, cardmaincusbean.getGender());
            insertStat3.setString(10, cardmaincusbean.getMaritalStatus());
            insertStat3.setString(11, cardmaincusbean.getNationality());
            insertStat3.setString(12, cardmaincusbean.getPermenentAddress1());
            insertStat3.setString(13, cardmaincusbean.getPermenentAddress2());
            insertStat3.setString(14, cardmaincusbean.getPermenentAddress3());
            insertStat3.setString(15, cardmaincusbean.getBillingAddress1());
            insertStat3.setString(16, cardmaincusbean.getBillingAddress2());
            insertStat3.setString(17, cardmaincusbean.getBillingAddress3());
            insertStat3.setString(18, cardmaincusbean.getCity());
            insertStat3.setString(19, cardmaincusbean.getHomePhone());
            insertStat3.setString(20, cardmaincusbean.getMobile());
            insertStat3.setString(21, cardmaincusbean.getEmail());
            insertStat3.setString(22, cardmaincusbean.getContactNumber());
            insertStat3.setString(23, cardmaincusbean.getOfficePhone());
            insertStat3.setString(24, cardmaincusbean.getCompanyName());
            insertStat3.setString(25, cardmaincusbean.getOccupation());
            insertStat3.setString(26, cardmaincusbean.getDesignation());
            insertStat3.setString(27, cardmaincusbean.getLastupdatedUser());



            resultId = insertStat3.executeUpdate();

       } catch (SQLException sex) {
            LogFileCreator.writeErrorToNumberGenLog(sex);
            throw sex;
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        } finally {
            try {
                insertStat3.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;
    }
    
    
    public int insertIntoCardCorporateCustomerTable(Connection con, CardCorporateCusDetailBean cardCopbean) throws Exception {
        int resultId;
        
        System.out.println("cardmaincusbean.getCustomerId() ************* "+cardCopbean.getCustomerId());

        PreparedStatement insertStat3 = null;
        try {
            insertStat3 = con.prepareStatement("INSERT INTO CARDCOOPCUSTOMERDETAILS "
                    + "(CUSTOMERID,INITIALS,FIRSTNAME,MIDDLENAME,LASTNAME,CREDITSCORE,CREDITLIMIT,DATEOFBIRTH,GENDER,MARITALSTATUS,NATIONALITY,PERMANENTADDRESS1, "
                    + "PERMANENTADDRESS2,PERMANENTADDRESS3 ,BILLINGADDRESS1,BILLINGADDRESS2,BILLINGADDRESS3,CITY,HOMETELEPHONE,MOBILENO,EMAIL,CONTACTNO,OFFICETELEPHONE, "
                    + "COMPANYNAME,OCCUPATION,DESIGNATION,BUSINESSREGNUMBER,LASTUPDATEDUSER,LASTUPDATEDTIME,CREATEDTIME ) "
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,SYSDATE) ");

            insertStat3.setString(1, cardCopbean.getCustomerId());
            insertStat3.setString(2, cardCopbean.getInitials());
            insertStat3.setString(3, cardCopbean.getFirstName());
            insertStat3.setString(4, cardCopbean.getMiddleName());
            insertStat3.setString(5, cardCopbean.getLastName());
            insertStat3.setString(6, cardCopbean.getCreditScore());
            insertStat3.setString(7, cardCopbean.getCreditLimit());
            insertStat3.setTimestamp(8, new Timestamp(this.stringTodate(cardCopbean.getDateOfBirth()).getTime()));
            insertStat3.setString(9, cardCopbean.getGender());
            insertStat3.setString(10, cardCopbean.getMaritalStatus());
            insertStat3.setString(11, cardCopbean.getNationality());
            insertStat3.setString(12, cardCopbean.getPermenentAddress1());
            insertStat3.setString(13, cardCopbean.getPermenentAddress2());
            insertStat3.setString(14, cardCopbean.getPermenentAddress3());
            insertStat3.setString(15, cardCopbean.getBillingAddress1());
            insertStat3.setString(16, cardCopbean.getBillingAddress2());
            insertStat3.setString(17, cardCopbean.getBillingAddress3());
            insertStat3.setString(18, cardCopbean.getCity());
            insertStat3.setString(19, cardCopbean.getHomePhone());
            insertStat3.setString(20, cardCopbean.getMobile());
            insertStat3.setString(21, cardCopbean.getEmail());
            insertStat3.setString(22, cardCopbean.getContactNumber());
            insertStat3.setString(23, cardCopbean.getOfficePhone());
            insertStat3.setString(24, cardCopbean.getCompanyName());
            insertStat3.setString(25, cardCopbean.getOccupation());
            insertStat3.setString(26, cardCopbean.getDesignation());
            insertStat3.setString(27, cardCopbean.getBusinessRegumber());
            insertStat3.setString(28, cardCopbean.getLastupdatedUser());



            resultId = insertStat3.executeUpdate();

       } catch (SQLException sex) {
            LogFileCreator.writeErrorToNumberGenLog(sex);
            throw sex;
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        } finally {
            try {
                insertStat3.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;
    }

    
    public int insertIntoCardFDCustomerTable(Connection con, CardFDCustomerDetailsBean cardFDCustomerDetailsBean) throws Exception {
        int resultId;
        
        System.out.println("cardmaincusbean.getCustomerId() ************* "+cardFDCustomerDetailsBean.getCustomerId());

        PreparedStatement insertStat3 = null;
        try {
            insertStat3 = con.prepareStatement("INSERT INTO CARDFDCUSTOMERDETAIL "
                    + "(CUSTOMERID,INITIALS,FIRSTNAME,MIDDLENAME,LASTNAME,CREDITSCORE,CREDITLIMIT,DATEOFBIRTH,GENDER,MARITALSTATUS,NATIONALITY,PERMANENTADDRESS1, "
                    + "PERMANENTADDRESS2,PERMANENTADDRESS3 ,BILLINGADDRESS1,BILLINGADDRESS2,BILLINGADDRESS3,CITY,HOMETELEPHONE,MOBILENO,EMAIL,CONTACTNO,OFFICETELEPHONE, "
                    + "COMPANYNAME,OCCUPATION,DESIGNATION,LASTUPDATEDUSER,LASTUPDATEDTIME,CREATEDTIME ) "
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,SYSDATE) ");

            insertStat3.setString(1, cardFDCustomerDetailsBean.getCustomerId());
            insertStat3.setString(2, cardFDCustomerDetailsBean.getInitials());
            insertStat3.setString(3, cardFDCustomerDetailsBean.getFirstName());
            insertStat3.setString(4, cardFDCustomerDetailsBean.getMiddleName());
            insertStat3.setString(5, cardFDCustomerDetailsBean.getLastName());
            insertStat3.setString(6, cardFDCustomerDetailsBean.getCreditScore());
            insertStat3.setString(7, cardFDCustomerDetailsBean.getCreditLimit());
            insertStat3.setTimestamp(8, new Timestamp(this.stringTodate(cardFDCustomerDetailsBean.getDateOfBirth()).getTime()));
            insertStat3.setString(9, cardFDCustomerDetailsBean.getGender());
            insertStat3.setString(10, cardFDCustomerDetailsBean.getMaritalStatus());
            insertStat3.setString(11, cardFDCustomerDetailsBean.getNationality());
            insertStat3.setString(12, cardFDCustomerDetailsBean.getPermenentAddress1());
            insertStat3.setString(13, cardFDCustomerDetailsBean.getPermenentAddress2());
            insertStat3.setString(14, cardFDCustomerDetailsBean.getPermenentAddress3());
            insertStat3.setString(15, cardFDCustomerDetailsBean.getBillingAddress1());
            insertStat3.setString(16, cardFDCustomerDetailsBean.getBillingAddress2());
            insertStat3.setString(17, cardFDCustomerDetailsBean.getBillingAddress3());
            insertStat3.setString(18, cardFDCustomerDetailsBean.getCity());
            insertStat3.setString(19, cardFDCustomerDetailsBean.getHomePhone());
            insertStat3.setString(20, cardFDCustomerDetailsBean.getMobile());
            insertStat3.setString(21, cardFDCustomerDetailsBean.getEmail());
            insertStat3.setString(22, cardFDCustomerDetailsBean.getContactNumber());
            insertStat3.setString(23, cardFDCustomerDetailsBean.getOfficePhone());
            insertStat3.setString(24, cardFDCustomerDetailsBean.getCompanyName());
            insertStat3.setString(25, cardFDCustomerDetailsBean.getOccupation());
            insertStat3.setString(26, cardFDCustomerDetailsBean.getDesignation());
            insertStat3.setString(27, cardFDCustomerDetailsBean.getLastupdatedUser());



            resultId = insertStat3.executeUpdate();

       } catch (SQLException sex) {
            LogFileCreator.writeErrorToNumberGenLog(sex);
            throw sex;
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        } finally {
            try {
                insertStat3.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;
    }
    
    
    
    
    public int insertIntoCardSupCustomerTable(Connection con, CardSuplimentoryCustomerBean cardSupCusDetailBean) throws Exception {
        int resultId;

        PreparedStatement insertStat4 = null;
        try {
            insertStat4 = con.prepareStatement("INSERT INTO CARDSUPPCUSTOMERDETAILS "
                    + "(CUSTOMERID,FIRSTNAME,LASTNAME,MIDDLENAME,DATEOFBIRTH,GENDER,NIC,PASSPORTNUMBER,NATIONALITY,RELATIONSHIP,NAMEONCARD, "
                    + "ADDRESS1,ADDRESS2 ,ADDRESS3,CITY,PRIMARYCARDNUMBER,PRIMARYID,HOMETELEPHONE,MOBILE,OCCUPATION,EMPLOYMENTTYPE,NAMEWITHINITIAL,STATEMENTSTATUS, "
                    + "BILLINGADDRESS1,BILLINGADDRESS2,BILLINGADDRESS3,BILLINGCITY,LASTUPDATEDUSER,LASTUPDATEDTIME,CREATEDTIME ) "
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,SYSDATE) ");

            insertStat4.setString(1, cardSupCusDetailBean.getCustomerId());
            insertStat4.setString(2, cardSupCusDetailBean.getFirstName());
            insertStat4.setString(3, cardSupCusDetailBean.getLastName());
            insertStat4.setString(4, cardSupCusDetailBean.getMiddleName());
            insertStat4.setString(5, cardSupCusDetailBean.getBirthday());
            insertStat4.setString(6, cardSupCusDetailBean.getGender());
            insertStat4.setString(7, cardSupCusDetailBean.getNic());
            insertStat4.setString(8, cardSupCusDetailBean.getPassportNumber());
            insertStat4.setString(9, cardSupCusDetailBean.getNationality());
            insertStat4.setString(10, cardSupCusDetailBean.getRelationShip());
            insertStat4.setString(11, cardSupCusDetailBean.getNameOncard());
            insertStat4.setString(12, cardSupCusDetailBean.getAddress1());
            insertStat4.setString(13, cardSupCusDetailBean.getAddress2());
            insertStat4.setString(14, cardSupCusDetailBean.getAddress3());
            insertStat4.setString(15, cardSupCusDetailBean.getCity());
            insertStat4.setString(16, cardSupCusDetailBean.getPrimaryCardNumber());
            insertStat4.setString(17, cardSupCusDetailBean.getPrimaryId());
            insertStat4.setString(18, cardSupCusDetailBean.getHomeTelNumber());
            insertStat4.setString(19, cardSupCusDetailBean.getMobileNumber());
            insertStat4.setString(20, cardSupCusDetailBean.getOccupation());
            insertStat4.setString(21, cardSupCusDetailBean.getEmployementType());
            insertStat4.setString(22, cardSupCusDetailBean.getNameWithinitials());
            insertStat4.setString(23, cardSupCusDetailBean.getStatementStatus());
            insertStat4.setString(24, cardSupCusDetailBean.getBillingAdress1());
            insertStat4.setString(25, cardSupCusDetailBean.getBillingAdress2());
            insertStat4.setString(26, cardSupCusDetailBean.getBillingAdress3());
            insertStat4.setString(27, cardSupCusDetailBean.getBillingCity());
            insertStat4.setString(28, cardSupCusDetailBean.getLastUpdateUser());



            resultId = insertStat4.executeUpdate();

        } catch (SQLException sex) {
            LogFileCreator.writeErrorToNumberGenLog(sex);
            throw sex;
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        } finally {
            try {
                insertStat4.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;
    }

    public int insertIntoCardAccountTable(Connection con, CardAccountBean cardaccountbean) throws Exception {
        int resultId;

        PreparedStatement insertStat5 = null;
        try {
            insertStat5 = con.prepareStatement("INSERT INTO CARDACCOUNT "
                    + "(ACCOUNTNO,STATUS,LASTUPDATEDUSER,CREATEDTIME,LASTUPDATEDTIME,RISKPROFILECODE,CARDDOMAIN,LOYALTYPOINTS,ACCOUNTOWNER,ACCOUNTTYPE,"
                    + " CARDNUMBER, CREDITLIMIT, CASHLIMIT, CURRENCYCODE )"
                    + " VALUES (?,?,?,SYSDATE,SYSDATE,?, ?, ?, ?, ?, ?, ?, ?, ?)");

            insertStat5.setString(1, cardaccountbean.getAccountNumber());
//            insertStat5.setString(2, cardaccountbean.getBillingId());
            insertStat5.setString(2, cardaccountbean.getStatus());
            insertStat5.setString(3, cardaccountbean.getLastupdatedUser());
            insertStat5.setString(4, cardaccountbean.getRiskProfileCode());
            insertStat5.setString(5, cardaccountbean.getCardDomain());
            insertStat5.setString(6, cardaccountbean.getLoyalityPoints());
            insertStat5.setString(7, cardaccountbean.getAccountOwner());
            insertStat5.setString(8, cardaccountbean.getAccountType());
            insertStat5.setString(9, cardaccountbean.getCardNumber());
            insertStat5.setString(10, cardaccountbean.getCreditLimit());
            insertStat5.setString(11, cardaccountbean.getCashAdvanceLimit());
            insertStat5.setString(12, cardaccountbean.getCurrencyCode());

            resultId = insertStat5.executeUpdate();

       } catch (SQLException sex) {
            LogFileCreator.writeErrorToNumberGenLog(sex);
            throw sex;
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        } finally {
            try {
                insertStat5.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;
    }

    public int insertIntoOnlineCardTable(Connection con, OnlineCardTableBean onlinebean) throws Exception {//
        int resultId;

        PreparedStatement insertStat6 = null;
        try {
            insertStat6 = con.prepareStatement("INSERT INTO ECMS_ONLINE_CARD "
                    + "(CARDNUMBER,EFFECTDATE,EXPDATE,CURRENCYNOCODE,PINCOUNT,TXNCOUNT,CASHTXNCOUNT,TOTALTXNAMOUNT,TOTALCASHTXNAMOUNT,CREDITLIMIT,CASHLIMIT,"
                    + " OTBCREDIT,OTBCASH,TEMPCREDITAMOUNT,TEMPCASHAMOUNT,TEMPCREDITLIMITINCR,TEMPCASHLIMITINCR,TLISTATUS,BIN,EMVSTATUS,RISKPROFILECODE,STATUS,"
                    + "LASTUPDATEUSER,SERVICECODE,CARDDOMAIN,PINISSUEDEXPDATE,CARDKEYID )"
                    + " VALUES (?,SYSDATE,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            insertStat6.setString(1, onlinebean.getCardNumber());
//            insertStat6.setString(4, onlinebean.getEffectdate());
            insertStat6.setString(2, onlinebean.getExpiryDate());//exp date
            insertStat6.setString(3, onlinebean.getCurrencyCode());//currency
            insertStat6.setInt(4, Integer.parseInt(onlinebean.getPinCount()));
            insertStat6.setInt(5, Integer.parseInt(onlinebean.getTxnCount()));
            insertStat6.setInt(6, Integer.parseInt(onlinebean.getCashTxnCount()));
            insertStat6.setDouble(7, Double.parseDouble(onlinebean.getTotalTxnAmount()));
            insertStat6.setDouble(8, Double.parseDouble(onlinebean.getTotalCashTxnAmount()));
            insertStat6.setDouble(9, Double.parseDouble(onlinebean.getCreditLimit()));
            insertStat6.setDouble(10, Double.parseDouble(onlinebean.getCashlimit()));
            insertStat6.setDouble(11, Double.parseDouble(onlinebean.getOtbCredit()));
            insertStat6.setDouble(12, Double.parseDouble(onlinebean.getOtbCash()));
            insertStat6.setDouble(13, Double.parseDouble(onlinebean.getTempCreditAmount()));
            insertStat6.setDouble(14, Double.parseDouble(onlinebean.getTempCashAmount()));
            insertStat6.setDouble(15, Double.parseDouble(onlinebean.getTempCreditLimitNcr()));
            insertStat6.setDouble(16, Double.parseDouble(onlinebean.getTempCashLimitNcr()));
            insertStat6.setInt(17, Integer.parseInt(onlinebean.getTliStatus()));
            insertStat6.setString(18, onlinebean.getCardBin());//cardtype ---->card bin
//            insertStat6.setInt(19, Integer.parseInt(onlinebean.getEod()));
            insertStat6.setInt(19, Integer.parseInt(onlinebean.getEmvStatus()));//paymode ---> emvsatatus
            insertStat6.setString(20, onlinebean.getRiskProfileCode());
            insertStat6.setInt(21, Integer.parseInt(onlinebean.getStatus()));
            insertStat6.setString(22, onlinebean.getLastUpdatedUser());
            insertStat6.setString(23, onlinebean.getServiceCode());
            insertStat6.setString(24, onlinebean.getCardDomain());
            insertStat6.setString(25, onlinebean.getExpiryDate());//same as exp date
            insertStat6.setString(26, onlinebean.getCardKey());//




            resultId = insertStat6.executeUpdate();

        } catch (SQLException sex) {
            LogFileCreator.writeErrorToNumberGenLog(sex);
            throw sex;
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        } finally {
            try {
                insertStat6.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;
    }

    public int insertIntoOnlineAccountTable(Connection con, OnlineAccountTableBean onlinebean) throws Exception {//
        int resultId;

        System.out.println("onlinebean.getAccountNumber(): "+onlinebean.getAccountNumber());
        
        PreparedStatement insertStat7 = null;
        try {
            insertStat7 = con.prepareStatement("INSERT INTO ECMS_ONLINE_ACCOUNT "
                    + "(ACCOUNTNUMBER,RISKPROFILECODE,CURRENCYNOCODE,TXNCOUNT,CASHTXNCOUNT,TOTALTXNAMOUNT,TOTALCASHTXNAMOUNT,CREDITLIMIT,CASHLIMIT,OTBCREDIT,OTBCASH,STATUS,LOYALTYPOINTS )"
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");

            insertStat7.setString(1, onlinebean.getAccountNumber());
            insertStat7.setString(2, onlinebean.getRiskProfileCode());
            insertStat7.setString(3, onlinebean.getCurrencyCode());
            insertStat7.setInt(4, Integer.parseInt(onlinebean.getTxnCount()));
            insertStat7.setInt(5, Integer.parseInt(onlinebean.getCashTxnCount()));
            insertStat7.setDouble(6, Double.parseDouble(onlinebean.getTotalTxnAmount()));
            insertStat7.setDouble(7, Double.parseDouble(onlinebean.getTotalCashTxnAmount()));
            insertStat7.setDouble(8, Double.parseDouble(onlinebean.getCreditLimit()));
            insertStat7.setDouble(9, Double.parseDouble(onlinebean.getCashLimit()));
            insertStat7.setDouble(10, Double.parseDouble(onlinebean.getOtbCredit()));
            insertStat7.setDouble(11, Double.parseDouble(onlinebean.getOtbCash()));
            insertStat7.setInt(12, Integer.parseInt(onlinebean.getStatus()));
            insertStat7.setString(13, onlinebean.getLoyalityPoints());




            resultId = insertStat7.executeUpdate();

        } catch (SQLException sex) {
            LogFileCreator.writeErrorToNumberGenLog(sex);
            throw sex;
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        } finally {
            try {
                insertStat7.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;
    }

    public int insertIntoOnlineCustomerTable(Connection con, OnlineCustomerTableBean onlinebean) throws Exception {//
        int resultId;

        PreparedStatement insertStat8 = null;
        try {
            insertStat8 = con.prepareStatement("INSERT INTO ECMS_ONLINE_CUSTOMER "
                    + "(CUSTOMERID,RISKPROFILECODE,CURRENCYNOCODE,TXNCOUNT,CASHTXNCOUNT,TOTALTXNAMOUNT,TOTALCASHTXNAMOUNT,CREDITLIMIT,CASHLIMIT,OTBCREDIT,OTBCASH,STATUS )"
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");

            insertStat8.setString(1, onlinebean.getCustomerid());
            insertStat8.setString(2, onlinebean.getRiskProfileCode());
            insertStat8.setString(3, onlinebean.getCurrencyCode());
            insertStat8.setInt(4, Integer.parseInt(onlinebean.getTxnCount()));
            insertStat8.setInt(5, Integer.parseInt(onlinebean.getCashTxnCount()));
            insertStat8.setDouble(6, Double.parseDouble(onlinebean.getTotalTxnAmount()));
            insertStat8.setDouble(7, Double.parseDouble(onlinebean.getTotalCashTxnAmount()));
            insertStat8.setDouble(8, Double.parseDouble(onlinebean.getCreditLimit()));
            insertStat8.setDouble(9, Double.parseDouble(onlinebean.getCashLimit()));
            insertStat8.setDouble(10, Double.parseDouble(onlinebean.getOtbCredit()));
            insertStat8.setDouble(11, Double.parseDouble(onlinebean.getOtbCash()));
            insertStat8.setInt(12, Integer.parseInt(onlinebean.getStatus()));




            resultId = insertStat8.executeUpdate();

       } catch (SQLException sex) {
            LogFileCreator.writeErrorToNumberGenLog(sex);
            throw sex;
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        } finally {
            try {
                insertStat8.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;
    }

    public Date stringTodate(String date) throws ParseException {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        Date convertedDate = dateFormat.parse(dateString);

        return convertedDate;

    }
    
    public String stringToDate(String date) throws ParseException {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        Date convertedDate = dateFormat.parse(dateString);


        return (dateFormat.format(convertedDate));



    }

    public CardCustomerBean getCustomerCardDetailsById(Connection con, String primaryId) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        CardCustomerBean bean = new CardCustomerBean();
        try {
            stmt = con.prepareStatement("SELECT CUSTOMERID,IDENTIFICATIONTYPE,IDENTIFICATIONNO ,CUSTOMERNAME,CUSTOMERSTATUS FROM CARDCUSTOMER WHERE UPPER(IDENTIFICATIONNO)=?");
            stmt.setString(1, primaryId.toUpperCase());
            rs = stmt.executeQuery();

            if (rs.next()) {


                bean.setCustomerId(rs.getString("CUSTOMERID"));
                bean.setCustomerName(rs.getString("CUSTOMERNAME"));
                bean.setIdNumber(rs.getString("IDENTIFICATIONNO"));
                bean.setCustomerStatus(rs.getString("CUSTOMERSTATUS"));
                bean.setIdType(rs.getString("IDENTIFICATIONTYPE"));



            }


        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }


        return bean;
    }

    public String getMaxAccnoFromCardAccount(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getMaxCusIdState = null;
        String maxAcc = null;
        try {
            getMaxCusIdState = con.prepareStatement("select coalesce(Max(cac.ACCOUNTNO),'0') as MAXACCOUNTNO from CARDACCOUNT cac where cac.CARDDOMAIN!='DEBIT' ");

            rs = getMaxCusIdState.executeQuery();


            while (rs.next()) {

                maxAcc = rs.getString("MAXACCOUNTNO");

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getMaxCusIdState.close();

        }
        maxAcc = String.valueOf(Long.parseLong(maxAcc));

        return maxAcc;
    }

    public String getPrimaryAccountNumberFromCardAccont(Connection con, String customerId) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        String acc = "";
        try {
            //stmt = con.prepareStatement("SELECT ACCOUNTNO,CUSTOMERID,STATUS FROM CARDACCOUNT WHERE CUSTOMERID=?");
            stmt = con.prepareStatement("SELECT ACCOUNTNO FROM CARDACCOUNTCUSTOMER WHERE CUSTOMERID=? AND ISPRIMARY='YES' ");
            stmt.setString(1, customerId);
            rs = stmt.executeQuery();

            if (rs.next()) {


                acc = rs.getString("ACCOUNTNO");


            }


        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }


        return acc;
    }

    public int updateSequenceNumberAndStatus(Connection con, String bin, String sequence, String status, String appId) throws Exception {
        int sq = 0, p = 0, q = 0, resultId = 0;
        String seq = "";

        PreparedStatement insertStat = null;
        try {
            CardNumberFormateBean bean = new CardNumberFormateBean();

            if(sequence!=null && !sequence.trim().equals("") && !sequence.trim().equals("null")){
                sq = Integer.parseInt(sequence);
                seq = this.getZeropadSeq(String.valueOf(sq + 1), sequence.length());
                insertStat = con.prepareStatement("UPDATE CARDNUMBERFORMATBIN SET SEQUENCENO=? WHERE BIN=?");

                insertStat.setString(1, seq);
                insertStat.setString(2, bin);
                p = insertStat.executeUpdate();
            } else {
                // if the number format does not contain a sequence:
                p = 1;
            }
            
            
            insertStat = con.prepareStatement("UPDATE CARDAPPLICATIONSTATUS  SET APPLICATIONSTATUS=? WHERE APPLICATIONID=?");
            insertStat.setString(1, status);
            insertStat.setString(2, appId);
            q = insertStat.executeUpdate();


            if (p == 1 && q == 1) {
                resultId = 1;
            } else {
                resultId = 0;
            }


        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        } finally {
            try {
                insertStat.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;
    }
    
    public int updateCardNumberForApp(Connection con, String appId, CardBean cardBean) throws Exception
    {
        int resultId = -1;                
               
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE CARDAPPLICATION SET CARDNUMBER = ? WHERE APPLICATIONID = ?");
            
            updateStat.setString(1, cardBean.getCardNumber());
            updateStat.setString(2, appId);

            resultId = updateStat.executeUpdate();
            
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        } finally {
            try {
                updateStat.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;
    }

    private String getZeropadSeq(String seq, int length) {
        String padLine = "";
        String referanceNo = "";

        if (seq.length() < length) {
            for (int x = 0; x < length - seq.length(); x++) {
                padLine += "0";
            }
            referanceNo = padLine + seq;
        } else if (seq.length() == length) {
            referanceNo = seq;
        }



        return referanceNo;

    }

    public CustomerTempBean getCustomerTemplateDetails(Connection con, String customerTemCode) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        CustomerTempBean bean = new CustomerTempBean();
        try {
            stmt = con.prepareStatement("SELECT TEMPLATECODE,CURRENCYCODE,RISKPROFILECODE ,TRNSACTIONPROFILECODE,STATUS FROM CUSTOMERTEMPLATE WHERE TEMPLATECODE=?");
            stmt.setString(1, customerTemCode);
            rs = stmt.executeQuery();

            if (rs.next()) {

                bean.setTemplateCode(rs.getString("TEMPLATECODE"));
                bean.setCurrencyCode(rs.getString("CURRENCYCODE"));
                bean.setCusRiskProfile(rs.getString("RISKPROFILECODE"));
                bean.setTxnProfCode(rs.getString("TRNSACTIONPROFILECODE"));
                bean.setStatus(rs.getString("STATUS"));


            }


        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }


        return bean;
    }

    public AccountTempBean getAccountTemplateDetails(Connection con, String accountTemCode) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        AccountTempBean bean = new AccountTempBean();
        try {
            stmt = con.prepareStatement("SELECT TRNSACTIONPROFILECODE, TEMPLATECODE,CURRENCYCODE,RISKPROFILECODE ,STATUS,BILLINGCYCLECODE FROM ACCOUNTTEMPLATE WHERE TEMPLATECODE=?");
            stmt.setString(1, accountTemCode);
            rs = stmt.executeQuery();

            if (rs.next()) {


                bean.setTemplateCode(rs.getString("TEMPLATECODE"));
                bean.setCurrencyCode(rs.getString("CURRENCYCODE"));
                bean.setAccRskProf(rs.getString("RISKPROFILECODE"));
                bean.setBillCycle(rs.getString("BILLINGCYCLECODE"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setTxnProfCode(rs.getString("TRNSACTIONPROFILECODE"));


            }


        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }


        return bean;
    }

    public DebitCardAccountTemplateBean geDebitAccountTemplateDetails(Connection con, String accountTemCode) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        DebitCardAccountTemplateBean bean = new DebitCardAccountTemplateBean();
        try {

            stmt = con.prepareStatement("SELECT TEMPLATECODE,CURRENCYCODE,RISKPROFILECODE,TRANSACTIONFEEPROFILECODE ,STATUS FROM DEBITCARDACCOUNTTEMPLATE WHERE TEMPLATECODE=?");
            stmt.setString(1, accountTemCode);
            rs = stmt.executeQuery();

            if (rs.next()) {


                bean.setTemplateCode(rs.getString("TEMPLATECODE"));
                bean.setCurrencyCode(rs.getString("CURRENCYCODE"));
                bean.setRiskProfileCode(rs.getString("RISKPROFILECODE"));
                bean.setTransProfileCode(rs.getString("TRANSACTIONFEEPROFILECODE"));
                bean.setStatus(rs.getString("STATUS"));


            }


        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }


        return bean;
    }

    public CardTempBean getCardTemplateDetails(Connection con, String cardTemCode) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        CardTempBean bean = new CardTempBean();
        try {
            stmt = con.prepareStatement("SELECT TRNSACTIONPROFILECODE,TEMPLATECODE,CURRENCYCODE,RISKPROFILECODE ,"
                    + "CASHADVANCERATE,NEWCARDEXPPERIOD,STATUS,SERVICECODE,REISSUETHRESHHOLD,"
                    + "ACCOUNTTEMPLATECODE FROM CARDTEMPLATE WHERE TEMPLATECODE=?");
            stmt.setString(1, cardTemCode);
            rs = stmt.executeQuery();

            if (rs.next()) {


                bean.setTemplateCode(rs.getString("TEMPLATECODE"));
                bean.setCurrencyNumCode(rs.getString("CURRENCYCODE"));
                bean.setRiskProfileCode(rs.getString("RISKPROFILECODE"));
                bean.setCashAdvancedRate(rs.getString("CASHADVANCERATE"));
                bean.setExpiryPeriod(rs.getString("NEWCARDEXPPERIOD"));
                bean.setServiceCode(rs.getString("SERVICECODE"));
                bean.setReissueTresholPeriod(rs.getString("REISSUETHRESHHOLD"));
                bean.setAccountTempCode(rs.getString("ACCOUNTTEMPLATECODE"));
                bean.setTxnProfCode(rs.getString("TRNSACTIONPROFILECODE"));



            }


        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }


        return bean;
    }

    public DebitCardTemplateBean getDebitCardTemplateDetails(Connection con, String cardTemCode) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        DebitCardTemplateBean bean = new DebitCardTemplateBean();
        try {

            stmt = con.prepareStatement("SELECT TEMPLATECODE,CURRENCYCODE,RISKPROFILECODE ,"
                    + "CASHADVANCERATE,CARDEXPPERIOD,STATUS,SERVICECODE,REISSUETHRESHHOLD,"
                    + "DEBITACCOUNTTEMPLATECODE,TRNSACTIONPROFILECODE FROM DEBITCARDTEMPLATE WHERE TEMPLATECODE=?");
            stmt.setString(1, cardTemCode);
            rs = stmt.executeQuery();

            if (rs.next()) {


                bean.setTemplateCode(rs.getString("TEMPLATECODE"));
                bean.setCurrencyCode(rs.getString("CURRENCYCODE"));
                bean.setRiskProf(rs.getString("RISKPROFILECODE"));
                bean.setCashAdvanceRate(rs.getString("CASHADVANCERATE"));
                bean.setExpPeriod(rs.getString("CARDEXPPERIOD"));
                bean.setServiceCode(rs.getString("SERVICECODE"));
                bean.setReissueThreshPeriod(rs.getString("REISSUETHRESHHOLD"));
                bean.setAccTempName(rs.getString("DEBITACCOUNTTEMPLATECODE"));
                bean.setTxnProf(rs.getString("TRNSACTIONPROFILECODE"));


            }


        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }


        return bean;
    }

    public String getNumberFormateBinNumber(Connection con, String binNumber) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        String value = "";

        try {
            stmt = con.prepareStatement("SELECT NUMBERFORMATCODE,BIN,SEQUENCENO FROM CARDNUMBERFORMATBIN WHERE BIN=?");
            stmt.setString(1, binNumber);
            rs = stmt.executeQuery();

            if (rs.next()) {


                value = rs.getString("NUMBERFORMATCODE") + "," + rs.getString("SEQUENCENO");


            }


        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }


        return value;
    }

    public String isReplaceAppication(Connection con, String applicationId) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        String replaceCard = "";

        try {
            stmt = con.prepareStatement("SELECT APPLICATIONID,APPLICATIONSTATUS FROM CARDAPPLICATIONSTATUS WHERE APPLICATIONID=?");
            stmt.setString(1, applicationId);
            rs = stmt.executeQuery();

            if (rs.next()) {


                if (rs.getString("APPLICATIONSTATUS").equals("NRPL")) {

                    stmt = con.prepareStatement("SELECT APPLICATIONID,CARDNUMBER FROM CARD WHERE APPLICATIONID=? AND CARDSTATUS=?");
                    stmt.setString(1, applicationId);
                    stmt.setString(2, "TBLK");
                    rs = stmt.executeQuery();

                    if (rs.next()) {
                        replaceCard = rs.getString("CARDNUMBER");
                    }
                }


            }


        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }


        return replaceCard;
    }

    public CardBean getBackendCard(Connection con, String replaceCard) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        CardBean replaceBean = new CardBean();
        String sql = "";
        
        try {
            sql = "SELECT C.ACTIVATIONDATE, CAC.ACCOUNTNO, C.APPLICATIONID, C.OTBCREDIT, "
                + " C.CARDCATEGORYCODE, C.CARDNUMBER, C.CARDNUMBERPRINTEDONCARD, C.CARDPRODUCT, C.CARDSTATUS, "
                + " C.CARDTYPE, C.OTBCASH, C.CASHLIMIT, C.CREDITLIMIT, C.CURRENCYNOCODE, CAC.CUSTOMERID, "
                + " C.EMBOSSSTATUS, C.ENCODESTATUS, C.EXPIERYDATE, C.IDNUMBER, C.IDTYPE, C.ISSUEDDATE, "
                + " C.LOYALTYPOINT, C.MAINCARDNO, C.NAMEONCARD, C.NOGENARATEDUSER, C.OFFSET, C.PINSTATUS, "
                + " C.PINMAILSTATUS, C.PRIORITYLEVEL, C.REDEEMPOINT, C.REISSUETHRESHHOLSPERIOD, C.RISKPROFILECODE, "
                + " C.SERVICECODE, C.TEMPCASHAMOUNT, C.TEMPCREDITAMOUNT "
                + " FROM CARD C, CARDACCOUNTCUSTOMER CAC "
                + " WHERE C.CARDNUMBER=CAC.CARDNUMBER "
                + " AND C.CARDNUMBER=? ";
            
            stmt = con.prepareStatement(sql);
            stmt.setString(1, replaceCard);
            rs = stmt.executeQuery();

            if (rs.next()) {
                replaceBean.setAccountNumber(rs.getString("ACCOUNTNO"));
                replaceBean.setActivationDate(rs.getString("ACTIVATIONDATE"));
                replaceBean.setApplicationId(rs.getString("APPLICATIONID"));
                replaceBean.setAvailableBalence(rs.getString("OTBCREDIT"));
                replaceBean.setCardCategory(rs.getString("CARDCATEGORYCODE"));
                replaceBean.setCardNumber(rs.getString("CARDNUMBER"));
                replaceBean.setCardNumberPrintedOnCard(rs.getString("CARDNUMBERPRINTEDONCARD"));
                replaceBean.setCardProduct(rs.getString("CARDPRODUCT"));
                replaceBean.setCardStatus(rs.getString("CARDSTATUS"));
                replaceBean.setCardType(rs.getString("CARDTYPE"));
                replaceBean.setCashAvailableBalence(rs.getString("OTBCASH"));
                replaceBean.setCashLimit(rs.getString("CASHLIMIT"));
                replaceBean.setCreditLimit(rs.getString("CREDITLIMIT"));
                replaceBean.setCurenncyCode(rs.getString("CURRENCYNOCODE"));
                replaceBean.setCustomerId(rs.getString("CUSTOMERID"));
                replaceBean.setEmbossStatus(rs.getString("EMBOSSSTATUS"));
                replaceBean.setEncodeStatus(rs.getString("ENCODESTATUS"));
                replaceBean.setExprioryDate(rs.getString("EXPIERYDATE"));
                replaceBean.setIdNumber(rs.getString("IDNUMBER"));
                replaceBean.setIdType(rs.getString("IDTYPE"));
                replaceBean.setIssueDate(rs.getString("ISSUEDDATE"));
                replaceBean.setLoyalityPoint(rs.getString("LOYALTYPOINT"));
                replaceBean.setMainCardNumber(rs.getString("MAINCARDNO"));
                replaceBean.setNameOnCard(rs.getString("NAMEONCARD"));
                replaceBean.setNoGenaratedUser(rs.getString("NOGENARATEDUSER"));
                replaceBean.setOffset(rs.getString("OFFSET"));
                replaceBean.setPinStatus(rs.getString("PINSTATUS"));
                replaceBean.setPinmailStatus(rs.getString("PINMAILSTATUS"));
                replaceBean.setPriorityLevel(rs.getString("PRIORITYLEVEL"));
                replaceBean.setRedeemPoint(rs.getString("REDEEMPOINT"));
                replaceBean.setReissueTresholPeriod(rs.getString("REISSUETHRESHHOLSPERIOD"));
                replaceBean.setRiskProfile(rs.getString("RISKPROFILECODE"));
                replaceBean.setServiceCode(rs.getString("SERVICECODE"));
                replaceBean.setTempCashAmount(rs.getString("TEMPCASHAMOUNT"));
                replaceBean.setTempotbAmount(rs.getString("TEMPCREDITAMOUNT"));


            }


        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }


        return replaceBean;
    }

    public OnlineCardTableBean getOnlineCard(Connection OnlineCon, String replaceCard) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        OnlineCardTableBean replaceBean = new OnlineCardTableBean();
        String sql = "";
        
        try {
            sql = "SELECT  CAC.ACCOUNTNUMBER,  C.CASHTXNCOUNT,  C.CASHLIMIT, C.CARDNUMBER, " +
                    "  C.CREDITLIMIT,  C.CURRENCYNOCODE,  CAC.CUSTOMERID,  C.EFFECTDATE,  C.EXPDATE,  C.NEWEXPDATE,  C.OTBCASH,  C.OTBCREDIT," +
                    "  C.PINCOUNT,  C.RISKPROFILECODE,  C.SERVICECODE,  C.STATUS,  C.TEMPCASHAMOUNT,  C.TEMPCASHLIMITINCR,  C.TEMPCREDITAMOUNT, " +
                    "  C.TEMPCREDITLIMITINCR,  C.TLISTATUS,  C.TOTALCASHTXNAMOUNT,  C.TOTALTXNAMOUNT,  C.TXNCOUNT " +
                    "  FROM ECMS_ONLINE_CARD C,  ECMS_ONLINE_CARD_ACC_CUS CAC " +
                    "  WHERE C.CARDNUMBER = CAC.CARDNUMBER " +
                    "  AND C.CARDNUMBER  = ?";
            stmt = OnlineCon.prepareStatement(sql);
            stmt.setString(1, replaceCard);
            rs = stmt.executeQuery();

            if (rs.next()) {

                replaceBean.setAccountNumber(rs.getString("ACCOUNTNUMBER"));
                replaceBean.setCardNumber(rs.getString("CARDNUMBER"));
                //replaceBean.setCardType(rs.getString("CARDTYPE"));
                replaceBean.setCashTxnCount(rs.getString("CASHTXNCOUNT"));
                replaceBean.setCashlimit(rs.getString("CASHLIMIT"));
                replaceBean.setCreditLimit(rs.getString("CREDITLIMIT"));
                replaceBean.setCurrencyCode(rs.getString("CURRENCYNOCODE"));
                replaceBean.setCustomerid(rs.getString("CUSTOMERID"));
                replaceBean.setEffectdate(rs.getString("EFFECTDATE"));
                //replaceBean.setEod(rs.getString("EOD"));
                replaceBean.setExpiryDate(rs.getString("EXPDATE"));
                replaceBean.setNewExpiryDate(rs.getString("NEWEXPDATE"));
                replaceBean.setOtbCash(rs.getString("OTBCASH"));
                replaceBean.setOtbCredit(rs.getString("OTBCREDIT"));
                //replaceBean.setPayMode(rs.getString("PAYTYPE"));
                replaceBean.setPinCount(rs.getString("PINCOUNT"));
                replaceBean.setRiskProfileCode(rs.getString("RISKPROFILECODE"));
                replaceBean.setServiceCode(rs.getString("SERVICECODE"));
                replaceBean.setStatus(rs.getString("STATUS"));
                replaceBean.setTempCashAmount(rs.getString("TEMPCASHAMOUNT"));
                replaceBean.setTempCashLimitNcr(rs.getString("TEMPCASHLIMITINCR"));
                replaceBean.setTempCreditAmount(rs.getString("TEMPCREDITAMOUNT"));
                replaceBean.setTempCreditLimitNcr(rs.getString("TEMPCREDITLIMITINCR"));
                replaceBean.setTliStatus(rs.getString("TLISTATUS"));
                replaceBean.setTotalCashTxnAmount(rs.getString("TOTALCASHTXNAMOUNT"));
                replaceBean.setTotalTxnAmount(rs.getString("TOTALTXNAMOUNT"));
                replaceBean.setTxnCount(rs.getString("TXNCOUNT"));


            }


        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }


        return replaceBean;
    }

    public List<NumberGenarationDataBean> getDebitNumberGenarationSearch(Connection con, NumberGenarationSearchBean searchBean) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query



        try {

            String sql = this.makeSqlforDebitNumberGenanarationSearch(searchBean);



            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            searchList = new ArrayList<NumberGenarationDataBean>();

            while (rs.next()) {


                NumberGenarationDataBean resultAssign = new NumberGenarationDataBean();

                resultAssign.setApplicationId(rs.getString("APPLICATIONID"));
                resultAssign.setNameOnCard(rs.getString("NAMEONCARD"));
                resultAssign.setCardType(rs.getString("CARDTYPE"));
                resultAssign.setCardProduct(rs.getString("CARDPRODUCT"));
                resultAssign.setPriorityLevel(rs.getString("PRIORITYLEVEL"));
                resultAssign.setCreateTime(rs.getTimestamp("CREATETIME"));




                searchList.add(resultAssign);

            }

        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }

        return searchList;
    }

    private String makeSqlforDebitNumberGenanarationSearch(NumberGenarationSearchBean searchBean) throws Exception{

        String strSqlBasic = " SELECT CA.APPLICATIONID ,PL.DESCRIPTION AS PRIORITYLEVEL,DCP.NAMEONCARD ,CA.CREATETIME,CP.DESCRIPTION AS CARDPRODUCT, CT.DESCRIPTION AS CARDTYPE"
                + " FROM CARDAPPLICATION CA ,PRIORITYLEVEL PL ,DEBITCARDAPPLICATIONDETAILS DCP,CARDPRODUCT CP,CARDTYPE CT,CARDAPPLICATIONSTATUS CAS "
                + " WHERE CA.CARDAPPLIACTIONDOMAIN = 'DEBIT' AND CA.PRIORITYLEVELCODE=PL.PRIORITYLEVELCODE AND CA.APPLICATIONID =DCP.APPLICATIONID AND CA.APPLICATIONID =CAS.APPLICATIONID AND CP.CARDTYPE  =CT.CARDTYPECODE AND CA.CONFIRMEDCARDPRODUCT=CP.PRODUCTCODE AND (CAS.APPLICATIONSTATUS='ACOM' OR CAS.APPLICATIONSTATUS='NRPL') ";



        String condition = "";

//
        if (!searchBean.getApplicationId().contentEquals("") || searchBean.getApplicationId().length() > 0) {

            condition += " AND CA.APPLICATIONID LIKE '%" + searchBean.getApplicationId() + "%'";
        }

        if (!searchBean.getPriorityLevel().contentEquals("") || searchBean.getPriorityLevel().length() > 0) {

            condition += " AND CA.PRIORITYLEVELCODE = '" + searchBean.getPriorityLevel() + "'";
        }


        if (!searchBean.getNameOnCard().contentEquals("") || searchBean.getNameOnCard().length() > 0) {

            condition += "AND DCP.NAMEONCARD LIKE '%" + searchBean.getNameOnCard() + "%'";
        }
        if (!searchBean.getCardType().contentEquals("") || searchBean.getCardType().length() > 0) {

            condition += "AND CT.CARDTYPECODE ='" + searchBean.getCardType() + "'";
        }
        if (!searchBean.getCardProduct().contentEquals("") || searchBean.getCardProduct().length() > 0) {

            condition += "AND CA.CONFIRMEDCARDPRODUCT ='" + searchBean.getCardProduct() + "'";
        }
        
        if (!searchBean.getFromDate().contentEquals("") && !searchBean.getToDate().contentEquals("")) {
            condition += "AND CA.CREATETIME >= to_Date('" + this.stringToDate(searchBean.getFromDate()) + "','yy-mm-dd') AND CA.CREATETIME < to_Date('" + this.stringToDate(searchBean.getToDate()) + "','yy-mm-dd')+1";
        } else {

            if (!searchBean.getFromDate().contentEquals("")) {
                condition += "AND CA.CREATETIME >= to_Date('" + this.stringToDate(searchBean.getFromDate()) + "','yy-mm-dd')";
            }
            if (!searchBean.getToDate().contentEquals("")) {
                condition += "AND CA.CREATETIME < to_Date('" + this.stringToDate(searchBean.getToDate()) + "','yy-mm-dd')+1";
            }
        }
        
//
//
//
//        if (!searchBean.getFromDate().contentEquals("") && !searchBean.getToDate().contentEquals("")) {
//            if (!condition.equals("")) {
//                condition += " AND ";
//            }
//            condition += "app.CREATETIME >= to_Date('" + this.stringTodate(searchBean.getFromDate()) + "','yy-mm-dd') AND app.CREATETIME <= to_Date('" + this.stringTodate(searchBean.getToDate()) + "','yy-mm-dd')";
//        } else {
//
//            if (!searchBean.getFromDate().contentEquals("")) {
//                if (!condition.equals("")) {
//                    condition += " AND ";
//                }
//                condition += "app.CREATETIME >= to_Date('" + this.stringTodate(searchBean.getFromDate()) + "','yy-mm-dd')";
//            }
//            if (!searchBean.getToDate().contentEquals("")) {
//                if (!condition.equals("")) {
//                    condition += " AND ";
//                }
//                condition += "app.CREATETIME <= to_Date('" + this.stringTodate(searchBean.getToDate()) + "','yy-mm-dd')";
//            }
//        }
//
//
//
//
        if (!condition.equals("")) {
            strSqlBasic += condition;
        }
        return strSqlBasic;
    }

    public boolean isCACExsit(Connection con, CardAccountCustomerBean bean) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        boolean isExist = false;

        try {
            stmt = con.prepareStatement("SELECT * FROM CARDACCOUNTCUSTOMER WHERE CARDNUMBER=? AND ACCOUNTNO=? AND CUSTOMERID=?");
            stmt.setString(1, bean.getCardNumber());
            stmt.setString(2, bean.getAccountNumber());
            stmt.setString(3, bean.getCustomerId());
            rs = stmt.executeQuery();

            if (rs.next()) {
                isExist = true;

            }


        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }


        return isExist;
    }

    public int insertIntoCACTable(Connection con, CardAccountCustomerBean bean) throws Exception {
        int resultId;

        PreparedStatement insertStat10 = null;
        try {
            insertStat10 = con.prepareStatement("INSERT INTO CARDACCOUNTCUSTOMER "
                    + "(CARDNUMBER,ACCOUNTNO,CUSTOMERID,ISPRIMARY )"
                    + " VALUES (?,?,?,?)");

            insertStat10.setString(1, bean.getCardNumber());
            insertStat10.setString(2, bean.getAccountNumber());
            insertStat10.setString(3, bean.getCustomerId());
            insertStat10.setString(4, bean.getPrimaryStatus());





            resultId = insertStat10.executeUpdate();

        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        } finally {
            try {
                insertStat10.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;
    }

    public int insertIntoOnlineCACTable(Connection con, CardAccountCustomerBean bean) throws Exception {
        int resultId;

        PreparedStatement insertStat11 = null;
        try {
            insertStat11 = con.prepareStatement("INSERT INTO ECMS_ONLINE_CARD_ACC_CUS "
                    + "(CARDNUMBER,ACCOUNTNUMBER,CUSTOMERID,ISPRIMARY )"
                    + " VALUES (?,?,?,?)");

            insertStat11.setString(1, bean.getCardNumber());
            insertStat11.setString(2, bean.getAccountNumber());
            insertStat11.setString(3, bean.getCustomerId());
            if(bean.getPrimaryStatus().equals("YES")){
            insertStat11.setString(4, "1");
            }else{
            insertStat11.setString(4, "2");
            }





            resultId = insertStat11.executeUpdate();

        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        } finally {
            try {
                insertStat11.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;
    }

    public boolean isAccountExsit(Connection con, String accountNo) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        boolean isExist = false;

        try {
            stmt = con.prepareStatement("SELECT ACCOUNTNO FROM CARDACCOUNT WHERE ACCOUNTNO=? ");
            stmt.setString(1, accountNo);
            rs = stmt.executeQuery();

            if (rs.next()) {
                isExist = true;

            }


        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }


        return isExist;
    }

    public List<CardAccountCustomerBean> getCustomerDebitCardLst(Connection con, String customerId) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        List<CardAccountCustomerBean> list = new ArrayList<CardAccountCustomerBean>();


        try {
            stmt = con.prepareStatement("SELECT C.CUSTOMERID, C.ACCOUNTNO, C.CARDNUMBER, D.CARDTYPE from CARDACCOUNTCUSTOMER C,CARD D WHERE C.CUSTOMERID=? and C.CARDNUMBER= D.CARDNUMBER ");
            stmt.setString(1, customerId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                CardAccountCustomerBean bean = new CardAccountCustomerBean();
                bean.setCustomerId(rs.getString("CUSTOMERID"));
                bean.setAccountNumber(rs.getString("ACCOUNTNO"));
                bean.setCardNumber(rs.getString("CARDNUMBER"));
                bean.setCardType(rs.getString("CARDTYPE"));

            }


        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }


        return list;
    }

    public int insertIntoCardTransactionTables(Connection con, Connection OnlineCon, CardBean cardBean) throws Exception {
        int resultId=0;

        PreparedStatement insertStat13 = null;
        PreparedStatement onlineStat = null;
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        try {

            stmt = con.prepareStatement("SELECT TT.TRANSACTIONCODE,TT.ONLINETXNTYPE  FROM TRANSACTIONPROFILETRANSACTION TFT,TRANSACTIONTYPE TT WHERE TFT.PROFILECODE=? AND TFT.TXNCODE=TT.TRANSACTIONCODE ");
            stmt.setString(1, cardBean.getTxnProfileCode());
            rs = stmt.executeQuery();

            while (rs.next()) {
                insertStat13 = con.prepareStatement("INSERT INTO CARDTRANSACTIONTYPE "
                        + "(CARDNUMBER,TRANSACTIONCODE ) "
                        + " VALUES (?,?)");

                insertStat13.setString(1, cardBean.getCardNumber());
                insertStat13.setString(2, rs.getString("TRANSACTIONCODE"));


                int a = insertStat13.executeUpdate();
                
                onlineStat = OnlineCon.prepareStatement("INSERT INTO ECMS_ONLINE_CARD_TXN "
                        + "(CARDNO,TXNTYPECODE ) "
                        + " VALUES (?,?)");

                onlineStat.setString(1, cardBean.getCardNumber());
                onlineStat.setString(2, rs.getString("ONLINETXNTYPE"));


                int b = onlineStat.executeUpdate();
                
                if(a==1 && b==1){
                resultId =1;
                }else{
                 resultId =0;
                 throw new Exception();
                }

            }

        } catch (SQLException sex) {
            LogFileCreator.writeErrorToNumberGenLog(sex);
            throw sex;
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        } finally {
            try {
                insertStat13.close();
                onlineStat.close();
                stmt.close();

            } catch (Exception e) {
                 LogFileCreator.writeErrorToNumberGenLog(e);
            }

        }

        return resultId;


    }
    public int insertIntoAccountTransactionTables(Connection con, Connection OnlineCon, CardAccountBean accountBean) throws Exception {
        int resultId=0;

        PreparedStatement insertStat14 = null;
        PreparedStatement onlineStat3 = null;
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        try {

            stmt = con.prepareStatement("SELECT TT.TRANSACTIONCODE,TT.ONLINETXNTYPE  FROM TRANSACTIONPROFILETRANSACTION TFT,TRANSACTIONTYPE TT WHERE TFT.PROFILECODE=? AND TFT.TXNCODE=TT.TRANSACTIONCODE ");
            stmt.setString(1, accountBean.getTxnProfileCode());
            rs = stmt.executeQuery();

            while (rs.next()) {
                insertStat14 = con.prepareStatement("INSERT INTO ACCOUNTTRANSACTIONTYPE "
                        + "(ACCOUNTNUMBER,TRANSACTIONCODE ) "
                        + " VALUES (?,?)");

                insertStat14.setString(1, accountBean.getAccountNumber());
                insertStat14.setString(2, rs.getString("TRANSACTIONCODE"));


                int a = insertStat14.executeUpdate();
                
                onlineStat3 = OnlineCon.prepareStatement("INSERT INTO ECMS_ONLINE_ACCOUNT_TXN "
                        + "(ACCOUNTNUMBER,TXNTYPECODE ) "
                        + " VALUES (?,?)");

                onlineStat3.setString(1, accountBean.getAccountNumber());
                onlineStat3.setString(2, rs.getString("ONLINETXNTYPE"));


                int b = onlineStat3.executeUpdate();
                
                if(a==1 && b==1){
                resultId =1;
                }else{
                 resultId =0;
                 throw new Exception();
                }

            }

       } catch (SQLException sex) {
            LogFileCreator.writeErrorToNumberGenLog(sex);
            throw sex;
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        } finally {
            try {
                insertStat14.close();
                onlineStat3.close();
                stmt.close();

            } catch (Exception e) {
                 LogFileCreator.writeErrorToNumberGenLog(e);
            }

        }

        return resultId;


    }
    
    
    public int insertIntoCustomerTransactionTables(Connection con, Connection OnlineCon, CardCustomerBean customerBean) throws Exception {
        int resultId=0;

        PreparedStatement insertStat12 = null;
        PreparedStatement onlineStat2 = null;
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        try {

            stmt = con.prepareStatement("SELECT TT.TRANSACTIONCODE,TT.ONLINETXNTYPE  FROM TRANSACTIONPROFILETRANSACTION TFT,TRANSACTIONTYPE TT WHERE TFT.PROFILECODE=? AND TFT.TXNCODE=TT.TRANSACTIONCODE ");
            stmt.setString(1, customerBean.getTxnProfileCode());
            rs = stmt.executeQuery();

            while (rs.next()) {
                insertStat12 = con.prepareStatement("INSERT INTO CUSTOMERTRANSACTIONTYPE "
                        + "(CUSTOMERID,TRANSACTIONCODE ) "
                        + " VALUES (?,?)");

                insertStat12.setString(1, customerBean.getCustomerId());
                insertStat12.setString(2, rs.getString("TRANSACTIONCODE"));


                int a = insertStat12.executeUpdate();
                
                onlineStat2 = OnlineCon.prepareStatement("INSERT INTO ECMS_ONLINE_CUSTOMER_TXN "
                        + "(CUSTOMERID,TXNTYPECODE ) "
                        + " VALUES (?,?)");

                onlineStat2.setString(1, customerBean.getCustomerId());
                onlineStat2.setString(2, rs.getString("ONLINETXNTYPE"));


                int b = onlineStat2.executeUpdate();
                
                if(a==1 && b==1){
                resultId =1;
                }else{
                 resultId =0;
                 throw new Exception();
                }

            }

       } catch (SQLException sex) {
            LogFileCreator.writeErrorToNumberGenLog(sex);
            throw sex;
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        } finally {
            try {
                insertStat12.close();
                onlineStat2.close();
                stmt.close();

            } catch (Exception e) {
                 LogFileCreator.writeErrorToNumberGenLog(e);
            }

        }

        return resultId;


    }

    public boolean isCardExist(Connection CMSCon, String cno) throws Exception {
        boolean isCArdExist=false;
        ResultSet rs = null;
        PreparedStatement prst = null;
        try {

            String query = "SELECT ca.CARDNUMBER "
                         + "FROM CARD ca "
                         + "WHERE ca.CARDNUMBER=? ";

            prst = CMSCon.prepareStatement(query);
            prst.setString(1, cno);
            rs = prst.executeQuery();
            
            if(rs.next()) {
               isCArdExist=true; 
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            if(rs!=null)rs.close();
            if(prst!=null)prst.close();
        }

        return isCArdExist;
    }

    public int insertIntoCardEstCustomerTable(Connection CMSCon, CardEstablishmentCustomerDetailsBean cardEstablishmentCustomerDetailsBean) throws Exception{
        int resultId;

        PreparedStatement insertStat4 = null;
        try {
            insertStat4 = CMSCon.prepareStatement("INSERT INTO CARDESTCUSTOMERDETAILS  "+
                    " ( BUSINESSADDRESS1 ,BUSINESSADDRESS2,BUSINESSADDRESS3 ,"+
                    " CONTACTNUMBERSLAND ,CONTACTNUMBERSMOBILE,CONTACTEMAIL,CONTACTFAXNUMBER ,"+
                    " NAMEOFTHECOMPANY,NATUREOFBUSINESS,CONTACTPERSON ,LASTUPDATEDUSER,CREDITLIMIT,CUSTOMERID,BUSINESSREGNUMBER,CREATEDDATE,LASTUPDATEDDATE ) "+
                    " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,SYSDATE) ");
            
            insertStat4.setString(1,cardEstablishmentCustomerDetailsBean.getEstablishmentDetailsBean().getAddress1());
            insertStat4.setString(2,cardEstablishmentCustomerDetailsBean.getEstablishmentDetailsBean().getAddress2());
            insertStat4.setString(3,cardEstablishmentCustomerDetailsBean.getEstablishmentDetailsBean().getAddress3()); 
			
            insertStat4.setString(4,cardEstablishmentCustomerDetailsBean.getEstablishmentDetailsBean().getContactPersLandTelNumber());
            insertStat4.setString(5,cardEstablishmentCustomerDetailsBean.getEstablishmentDetailsBean().getContactPersMobileNumber());
            insertStat4.setString(6,cardEstablishmentCustomerDetailsBean.getEstablishmentDetailsBean().getEmail());
            insertStat4.setString(7,cardEstablishmentCustomerDetailsBean.getEstablishmentDetailsBean().getFaxNo());            

            
            insertStat4.setString(8,cardEstablishmentCustomerDetailsBean.getEstablishmentDetailsBean().getCompanyName());
            insertStat4.setString(9,cardEstablishmentCustomerDetailsBean.getEstablishmentDetailsBean().getNatureOfTheBusiness());
            insertStat4.setString(10,cardEstablishmentCustomerDetailsBean.getEstablishmentDetailsBean().getContactPersonFullName());
            insertStat4.setString(11,cardEstablishmentCustomerDetailsBean.getLastUpdateUser());
            insertStat4.setString(12,cardEstablishmentCustomerDetailsBean.getCreditLimit());
            insertStat4.setString(13,cardEstablishmentCustomerDetailsBean.getCustomerId());
            insertStat4.setString(14,cardEstablishmentCustomerDetailsBean.getEstablishmentDetailsBean().getBusinessRegNumber());


            resultId = insertStat4.executeUpdate();

        } catch (SQLException sex) {
            LogFileCreator.writeErrorToNumberGenLog(sex);
            throw sex;
        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            throw ex;
        } finally {
            try {
                insertStat4.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;
    }

    public CardBean getEstablishmentCardBeanByBusinssRegNo(Connection CMSCon, String businessRegNumber) throws Exception{
        
        CardBean bean = new CardBean();
        ResultSet rs=null;
        PreparedStatement getAllByCatStat = null;
        try {
            String sql="SELECT CA.CARDNUMBER "
                      + "FROM CARD CA, ESTABLISHMENTDETAILS ED  "
                      + "WHERE CA.APPLICATIONID=ED.APPLICATIONID "
                      + "AND ED.BUSINESSREGNUMBER=? ";
            getAllByCatStat = CMSCon.prepareStatement(sql);

            getAllByCatStat.setString(1, businessRegNumber);

            
            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {
                bean.setCardNumber(rs.getString("CARDNUMBER"));

            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }        
        return bean;
    }
}
