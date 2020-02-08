/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.capturedata.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.ProductionModeBean;
import com.epic.cms.admin.templatemgt.customertemplate.bean.CustomerTempBean;
import com.epic.cms.camm.applicationconfirmation.bean.CardBean;
import com.epic.cms.camm.applicationconfirmation.bean.PrimaryAppStatusBean;
import com.epic.cms.camm.applicationconfirmation.bean.PrimaryCardsDetailBean;
import com.epic.cms.camm.applicationconfirmation.bean.RejectReasonBean;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationAssignBean;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.SearchAssignAppBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardBankDetailsBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardDocumentBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardExpensesBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardIncomeBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerEmploymentBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DocumentUploadBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EstablishmentAssetsBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EstablishmentLiabilityBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.OccupationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.SupplementaryApplicationBean;
import com.epic.cms.system.util.variable.DataTypeVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author mahesh_m
 */
public class AppilcationCheckingPersistance {

    private ResultSet rs;
    private List<CustomerPersonalBean> personalDetailList;
    private List<ApplicationAssignBean> searchAssignAppList = null;
    CustomerPersonalBean bean = new CustomerPersonalBean();
    SupplementaryApplicationBean suppPersonal = new SupplementaryApplicationBean();
    CardApplicationBean cardApplication = new CardApplicationBean();
    CardBean recomendedDetails = new CardBean();
    CustomerEmploymentBean employementDetails = null;
    CardExpensesBean cardExpenses = null;
    List<CardIncomeBean> incomeDetailsList = null;
    List<CardBankDetailsBean> cardBankDetails = null;
    List<CardDocumentBean> cardDocumentList;
    List<DocumentUploadBean> cardDocumentDetails = null;
    private List<RejectReasonBean> rejectReasons;
    private List<ProductionModeBean> productionModeList;
    private List<PrimaryCardsDetailBean> primaryCardList;
    private List<CustomerTempBean> staffCusList;
    private List<OccupationBean> occupationList;

    public CustomerPersonalBean getPersonalDetails(Connection con, String applicationId) throws Exception {
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT CPD.RELEMAIL,CPD.APPLICATIONID,CPD.TITLE,CPD.INITIALS,CPD.FIRSTNAME,"
                    + "CPD.LASTNAME,CPD.MIDDLENAME,CPD.NAMEINFULL, CPD.DATEOFBIRTH,CPD.PLACEOFBIRTH,CPD.GENDER,CPD.MARITALSTATUS,CPD.NATIONALITY,"
                    + "CPD.NIC,CPD.BUSINESSREGNUMBER,CPD.DESIGNATION,CPD.PASSPORTNUMBER,CPD.PASSPORTEXPIREDATE,CPD.ADDRESS1,CPD.ADDRESS2,CPD.ADDRESS3,A.DESCRIPTION AS CITY,CPD.POSTALCODE,"
                    + "CPD.HOMETELEPHONENO,CPD.MOBILENO,CPD.SMSALERTMOBILENO,CPD.EMERGENCYCONTACTNO,CPD.DURATIONATTHEABOVEADDRESS,"
                    + "CPD.EMAIL,CPD.RECIDENCETYPE,CPD.MONTHLYRENTAL,CPD.MORGAGERENTAL,CPD.ACADEMICQUALIFICATION,"
                    + "CPD.PROFESSIONALQUALIFICATION,CPD.VEHICALTYPE,CPD.VEHICALNO,CPD.OWNERSHIP,CPD.RELIGION,CPD.BLOODGROUP,"
                    + "CPD.MOTHERSMAIDENNAME,CPD.NOOFDEPENDANCE,CPD.USECARDONLINE,CPD.LASTUPDATEDUSER,CPD.LASTUPDATEDTIME,"
                    + "CPD.CREATETIME,CPD.NAMEONCARD,CPD.OFFICEPHONENO,CPD.RELATIVENAME,CPD.RELATIONSHIP,CPD.RELADDRESS1,"
                    + "CPD.RELADDRESS2,CPD.SPOUSEEMAIL,CPD.RELADDRESS3,CPD.RELCITY,CPD.RELRESIDANCEPHONE,CPD.RELMOBILENO,CPD.RELEMPLOYER,"
                    + "CPD.RELOFFICEPHONE,CPD.SPOUSENAME,CPD.SPOUSEDATEOFBIRTH,CPD.SPOUSENIC,CPD.SPOUSEPASSPORTNO,"
                    + "CPD.SPOUSENAMEOFEMPLOYEE,CPD.SPOUSECOMPANYADDRESS,CPD.SPOUSEDESIGNATION,CPD.SPOUSEPHONE,"
                    + "CPD.SPOUSEMONTHLYINCOME,CPD.REQUESTCARDTYPE,CT.DESCRIPTION AS CARDTYPEDES,CPD.REQUESTCARDPRODUCT,CP.DESCRIPTION AS PRODUCTDES,CPD.REQUESTCREDITLIMIT,CPD.CURRENCYTYPE,A.DESCRIPTION AS RELCITYDES,C.CURRENCYALPHACODE,C.DESCRIPTION AS CURRENCYDES,ED.NAMEOFTHECOMPANY "
                    + "FROM CARDAPPLICATIONPERSONALDETAILS CPD "
                    + "LEFT JOIN    AREA A ON CPD.CITY = A.AREACODE "
                    + "LEFT JOIN    CARDPRODUCT CP ON CPD.REQUESTCARDPRODUCT = CP.PRODUCTCODE "
                    + "LEFT JOIN    CURRENCY C ON CPD.CURRENCYTYPE = C.CURRENCYNUMCODE "
                    + "LEFT JOIN    CARDTYPE CT ON CT.CARDTYPECODE = CPD.REQUESTCARDTYPE "
                    + "LEFT JOIN ESTABLISHMENTDETAILS ED "
                    + "ON ED.BUSINESSREGNUMBER    =CPD.BUSINESSREGNUMBER "
                    + "WHERE CPD.APPLICATIONID = ? "
                    + "");

            getAllByCatStat.setString(1, applicationId);

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {
                bean.setRelEmail(rs.getString("RELEMAIL"));
                bean.setApplicationId(rs.getString("APPLICATIONID"));
                bean.setTitle(rs.getString("TITLE"));
                bean.setInitials(rs.getString("INITIALS"));
                bean.setFirstName(rs.getString("FIRSTNAME"));
                bean.setLastName(rs.getString("LASTNAME"));
                bean.setMiddleName(rs.getString("MIDDLENAME"));
                bean.setFullName(rs.getString("NAMEINFULL"));
                bean.setBirthday(rs.getString("DATEOFBIRTH"));
                bean.setPlaceOfbirth(rs.getString("PLACEOFBIRTH"));
                bean.setGender(rs.getString("GENDER"));
                bean.setMaritalStatus(rs.getString("MARITALSTATUS"));
                bean.setNationality(rs.getString("NATIONALITY"));
                bean.setNic(rs.getString("NIC"));
                bean.setPassportNumber(rs.getString("PASSPORTNUMBER"));
                bean.setPassportExpdate(rs.getString("PASSPORTEXPIREDATE"));
                bean.setAddress1(rs.getString("ADDRESS1"));
                bean.setAddress2(rs.getString("ADDRESS2"));
                bean.setAddress3(rs.getString("ADDRESS3"));
                bean.setCity(rs.getString("CITY"));
                bean.setPostalcode(rs.getString("POSTALCODE"));
                bean.setHomeTelNumber(rs.getString("HOMETELEPHONENO"));
                bean.setMobileNumber(rs.getString("MOBILENO"));
                bean.setSmsalertMobileNumber(rs.getString("SMSALERTMOBILENO"));
                bean.setEmergencyContactNumber(rs.getString("EMERGENCYCONTACTNO"));
                bean.setDurationofTheAddress(rs.getString("DURATIONATTHEABOVEADDRESS"));
                bean.setEmail(rs.getString("EMAIL"));
                bean.setResidenceType(rs.getString("RECIDENCETYPE"));
                bean.setMonthlyRental(rs.getString("MONTHLYRENTAL"));
                bean.setMorgageRental(rs.getString("MORGAGERENTAL"));
                bean.setAcedemicQualifications(rs.getString("ACADEMICQUALIFICATION"));
                bean.setProfessionalQualifications(rs.getString("PROFESSIONALQUALIFICATION"));
                bean.setVehicalType(rs.getString("VEHICALTYPE"));
                bean.setVehicalNo(rs.getString("VEHICALNO"));
                bean.setOwnership(rs.getString("OWNERSHIP"));
                bean.setReligion(rs.getString("RELIGION"));
                bean.setBloodgroup(rs.getString("BLOODGROUP"));
                bean.setMothersMaidenName(rs.getString("MOTHERSMAIDENNAME"));
                bean.setNumberOfDependance(rs.getString("NOOFDEPENDANCE"));
                bean.setUseCardOnline(rs.getString("USECARDONLINE"));
                bean.setLastUpdateUser(rs.getString("LASTUPDATEDUSER"));
                bean.setNameOncard(rs.getString("NAMEONCARD"));
                bean.setOfficeTelNumber(rs.getString("OFFICEPHONENO"));
                bean.setRelName(rs.getString("RELATIVENAME"));
                bean.setRelationship(rs.getString("RELATIONSHIP"));
                bean.setRelAddress1(rs.getString("RELADDRESS1"));
                bean.setRelAddress2(rs.getString("RELADDRESS2"));
                bean.setRelAddress3(rs.getString("RELADDRESS3"));
                bean.setRelCity(rs.getString("RELCITYDES"));
                bean.setRelResidencePhone(rs.getString("RELRESIDANCEPHONE"));
                bean.setRelMobile(rs.getString("RELMOBILENO"));
                bean.setRelCompany(rs.getString("RELEMPLOYER"));
                bean.setRelOfficeNumber(rs.getString("RELOFFICEPHONE"));
                bean.setSpouseName(rs.getString("SPOUSENAME"));
                bean.setSpouseDateofBirth(rs.getString("SPOUSEDATEOFBIRTH"));
                bean.setSpouseNic(rs.getString("SPOUSENIC"));
                bean.setSpousePassportNumber(rs.getString("SPOUSEPASSPORTNO"));
                bean.setSpouseNameofEmployee(rs.getString("SPOUSENAMEOFEMPLOYEE"));
                bean.setSpousecompanyAddress(rs.getString("SPOUSECOMPANYADDRESS"));
                bean.setSpouseDesignation(rs.getString("SPOUSEDESIGNATION"));
                bean.setSpousePhone(rs.getString("SPOUSEPHONE"));
                bean.setSpouseMail(rs.getString("SPOUSEEMAIL"));
                bean.setSpouseMonthlyIncome(rs.getString("SPOUSEMONTHLYINCOME"));
                bean.setCardType(rs.getString("REQUESTCARDTYPE"));
                bean.setCardTypeDes(rs.getString("CARDTYPEDES"));
                bean.setCardProduct(rs.getString("REQUESTCARDPRODUCT"));
                bean.setCardProductDes(rs.getString("PRODUCTDES"));
                bean.setCreditLimit(rs.getString("REQUESTCREDITLIMIT"));
                bean.setCardCurrency(rs.getString("CURRENCYTYPE"));
                bean.setCurrencyDes(rs.getString("CURRENCYDES"));
                bean.setCurrencyTypeAlphaCode(rs.getString("CURRENCYALPHACODE"));

                bean.setBusinessRegNumber(rs.getString("BUSINESSREGNUMBER"));
                bean.setDesignation(rs.getString("DESIGNATION"));
                bean.setCompanyName(rs.getString("NAMEOFTHECOMPANY"));
                // bean.setFullName(rs.getString("NAMEINFULL"));
                //bean.setCurrencyTypeAlphaCode(rs.getString("CURRENCYALPHACODE"));

            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return bean;
    }

    public SupplementaryApplicationBean getSupplementaryPersonalDetails(Connection con, String applicationId) throws Exception {
        PreparedStatement getAllByCatStat = null;
        try {
            String sql = "SELECT SUP.APPLICATIONID,SUP.TITLE,SUP.FIRSTNAME,SUP.LASTNAME,"
                    + "SUP.MIDDLENAME,SUP.DATEOFBIRTH,SUP.GENDER,SUP.NIC,SUP.PASSPORTNUMBER,SUP.PASSPORTEXPIREDDATE,SUP.NATIONALITY,"
                    + "SUP.RELATIONSHIP,SUP.NAMEONCARD,SUP.ADDRESS1,SUP.ADDRESS2,SUP.ADDRESS3,SUP.CITY,SUP.POSTALCODE,SUP.PRIMARYCARDNUMBER,"
                    + "SUP.PRIMARYITTYPE,SUP.PRIMARYID,SUP.HOMETELEPHONE,SUP.MOBILE,SUP.ADDRESSSAMEASPRIMARY,SUP.NAMEWITHINITIAL,"
                    + "SUP.STATEMENTSTATUS,SUP.BILLINGADDRESS1,SUP.BILLINGADDRESS2,SUP.BILLINGADDRESS3,SUP.BILLINGCITY,SUP.PRIMARYAPPLICATIONID,"
                    + "SUP.OCCUPATION,SUP.EMPLOYMENTTYPE,SUP.REQUESTCARDTYPE,SUP.REQUESTCARDPRODUCT,SUP.REQUESTCREDITLIMIT,SUP.PERCENTAGECREDITLIMIT,CAD.CURRENCYTYPE AS CURRENCYTYPE,CTYPE.DESCRIPTION AS CTYPEDES,CPRODUCT.DESCRIPTION AS CPRODUCTDES,AREA.DESCRIPTION AS CITYDES,OCCUPATIONTYPE.DESCRIPTION AS OCCUPATIONDES "
                    + " FROM SUPPLEMENTARYAPPLICATION SUP "
                    + " LEFT JOIN CARDAPPLICATIONPERSONALDETAILS CAD "
                    + " ON SUP.PRIMARYAPPLICATIONID=CAD.APPLICATIONID "
                    + " JOIN CARDTYPE CTYPE "
                    + " ON SUP.REQUESTCARDTYPE=CTYPE.CARDTYPECODE "
                    + " JOIN CARDPRODUCT CPRODUCT"
                    + " ON SUP.REQUESTCARDPRODUCT=CPRODUCT.PRODUCTCODE"
                    + " JOIN AREA "
                    + " ON SUP.CITY=AREA.AREACODE"
                    + " JOIN OCCUPATIONTYPE ON SUP.OCCUPATION=OCCUPATIONTYPE.OCCUPATIONTYPECODE"
                    + " WHERE SUP.APPLICATIONID = ?";
            getAllByCatStat = con.prepareStatement(sql);

            getAllByCatStat.setString(1, applicationId);

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                suppPersonal.setApplicationId(rs.getString("APPLICATIONID"));
                suppPersonal.setTitle(rs.getString("TITLE"));
                suppPersonal.setFirstName(rs.getString("FIRSTNAME"));
                suppPersonal.setLastName(rs.getString("LASTNAME"));
                suppPersonal.setMiddleName(rs.getString("MIDDLENAME"));
                suppPersonal.setBirthday(rs.getString("DATEOFBIRTH"));
                suppPersonal.setGender(rs.getString("GENDER"));
                suppPersonal.setNic(rs.getString("NIC"));
                suppPersonal.setPassportNumber(rs.getString("PASSPORTNUMBER"));
                suppPersonal.setPassportExpdate(rs.getString("PASSPORTEXPIREDDATE"));
                suppPersonal.setNationality(rs.getString("NATIONALITY"));
                suppPersonal.setRelationShip(rs.getString("RELATIONSHIP"));
                suppPersonal.setNameOncard(rs.getString("NAMEONCARD"));
                suppPersonal.setAddress1(rs.getString("ADDRESS1"));
                suppPersonal.setAddress2(rs.getString("ADDRESS2"));
                suppPersonal.setAddress3(rs.getString("ADDRESS3"));
                suppPersonal.setCity(rs.getString("CITY"));
                suppPersonal.setPostalcode(rs.getString("POSTALCODE"));
                suppPersonal.setPrimaryCardNumber(rs.getString("PRIMARYCARDNUMBER"));
                suppPersonal.setPrimaryCardType(rs.getString("PRIMARYITTYPE"));
                suppPersonal.setPrimaryId(rs.getString("PRIMARYID"));
                suppPersonal.setHomeTelNumber(rs.getString("HOMETELEPHONE"));
                suppPersonal.setMobileNumber(rs.getString("MOBILE"));
                suppPersonal.setAdressSameAsPrimary(rs.getString("ADDRESSSAMEASPRIMARY"));
                suppPersonal.setOccupation(rs.getString("OCCUPATION"));
                suppPersonal.setEmployementType(rs.getString("EMPLOYMENTTYPE"));
                suppPersonal.setCardType(rs.getString("REQUESTCARDTYPE"));
                suppPersonal.setCardProduct(rs.getString("REQUESTCARDPRODUCT"));
                suppPersonal.setCreditLimit(rs.getString("REQUESTCREDITLIMIT"));
                suppPersonal.setNameWithinitials(rs.getString("NAMEWITHINITIAL"));
                suppPersonal.setStatementStatus(rs.getString("STATEMENTSTATUS"));
                suppPersonal.setBillingAdress1(rs.getString("BILLINGADDRESS1"));
                suppPersonal.setBillingAdress2(rs.getString("BILLINGADDRESS2"));
                suppPersonal.setBillingAdress3(rs.getString("BILLINGADDRESS3"));
                suppPersonal.setBillingCity(rs.getString("BILLINGCITY"));
                suppPersonal.setPrimaryCardApplicationId(rs.getString("PRIMARYAPPLICATIONID"));
                suppPersonal.setCardCurrency(rs.getString("CURRENCYTYPE"));
                suppPersonal.setCardTypeDes(rs.getString("CTYPEDES"));
                suppPersonal.setCardProductDes(rs.getString("CPRODUCTDES"));
                suppPersonal.setCityDes(rs.getString("CITYDES"));
                suppPersonal.setOccupationTypeDes(rs.getString("OCCUPATIONDES"));
                suppPersonal.setPercentageValue(rs.getString("PERCENTAGECREDITLIMIT"));

            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return suppPersonal;
    }

    public CardApplicationBean getApplicationCardDetails(Connection con, String applicationId) throws Exception {
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT APPLICATIONID,IDENTIFICATIONTYPE,IDENTIFICATIONNO,PRIORITYLEVELCODE,"
                    + "REFERANCIALEMPNO,BRANCHCODE,ASSIGNUSER,ASSIGNSTATUS,LASTUPDATEDUSER,LASTUPDATEDTIME,CREATETIME,CREDITSCORE, NVL(STAFFSTATUS, 'NO') AS STAFFSTATUS ,CARDCATEGORY,cc.DESCRIPTION as ccdes,CROFFICERRECPRODTYPE,CROFFICERRECCRLIMIT "
                    + "FROM CARDAPPLICATION, CARDCATEGORY cc WHERE cc.CATEGORYCODE=CARDCATEGORY and APPLICATIONID = ?");

            getAllByCatStat.setString(1, applicationId);

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                cardApplication.setApplicationId(rs.getString("APPLICATIONID"));
                cardApplication.setIdentificationType(rs.getString("IDENTIFICATIONTYPE"));
                cardApplication.setIdentificationNumber(rs.getString("IDENTIFICATIONNO"));
                cardApplication.setPrioritylevelCode(rs.getString("PRIORITYLEVELCODE"));
                cardApplication.setReferencialEmpNum(rs.getString("REFERANCIALEMPNO"));
                cardApplication.setBranchCode(rs.getString("BRANCHCODE"));
                cardApplication.setAssignUser(rs.getString("ASSIGNUSER"));
                cardApplication.setAssignStatus(rs.getString("ASSIGNSTATUS"));
                cardApplication.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                cardApplication.setCreditScore(rs.getString("CREDITSCORE"));
                cardApplication.setStaffStatus(rs.getString("STAFFSTATUS"));
                cardApplication.setAppTypeDes(rs.getString("ccdes"));
                cardApplication.setCardCategoryCode(rs.getString("CARDCATEGORY"));
                //new by chinthaka
                cardApplication.setcOfficerRecCardProduct(rs.getString("CROFFICERRECPRODTYPE"));
                cardApplication.setcOfficerRecCrditLimt(rs.getString("CROFFICERRECCRLIMIT"));

            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return cardApplication;
    }

    public CardBean getSysRecomendeddDetails(Connection con, String primaryCardNo) throws Exception {
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT CARDTYPE,CARDTYPE.DESCRIPTION AS CTYPEDES,CARDPRODUCT,CREDITLIMIT FROM CARD,CARDTYPE WHERE CARD.CARDTYPE=CARDTYPE.CARDTYPECODE AND CARDNUMBER = ?");

            getAllByCatStat.setString(1, primaryCardNo);

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                recomendedDetails.setCardtype(rs.getString("CARDTYPE"));
                recomendedDetails.setCardProduct(rs.getString("CARDPRODUCT"));
                recomendedDetails.setCreditLimit(rs.getString("CREDITLIMIT"));
                recomendedDetails.setCardTypeDes(rs.getString("CTYPEDES"));

            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return recomendedDetails;
    }

    public List<RejectReasonBean> getRejectReasonList(Connection con) throws Exception {
        PreparedStatement getAllByCatStat = null;
        rejectReasons = new ArrayList<RejectReasonBean>();
        try {
            getAllByCatStat = con.prepareStatement("SELECT REASONCODE,DESCRIPTION,LASTUPDATEDUSER,LASTUPDATEDTIME,CREATETIME FROM APPLICATIONREJECTREASON");

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                RejectReasonBean bean = new RejectReasonBean();

                bean.setReasonCode(rs.getString("REASONCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                bean.setLastUpdatedTime(rs.getString("LASTUPDATEDTIME"));
                bean.setCreatedTime(rs.getString("CREATETIME"));

                rejectReasons.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return rejectReasons;
    }

    public List<ProductionModeBean> getProductionMode(Connection con) throws Exception {
        PreparedStatement getAllByCatStat = null;
        productionModeList = new ArrayList<ProductionModeBean>();
        try {
            getAllByCatStat = con.prepareStatement("SELECT PRODUCTIONMODECODE,DESCRIPTION FROM PRODUCTIONMODE");

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                ProductionModeBean bean = new ProductionModeBean();

                bean.setProductionModeCode(rs.getString("PRODUCTIONMODECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                productionModeList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return productionModeList;
    }

    public String getBaseCurrency(Connection con) throws Exception {
        PreparedStatement getAllByCatStat = null;
        String baseCurrency = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT BASECURRENCY FROM COMMONPARAMETER");

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                baseCurrency = rs.getString("BASECURRENCY");
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return baseCurrency;
    }

    public String getCurrencyAlphaCode(Connection con, String currencyCode) throws Exception {
        PreparedStatement getAllByCatStat = null;
        String currencyAlphaCode = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT CURRENCYALPHACODE FROM CURRENCY WHERE CURRENCYNUMCODE = ?");
            getAllByCatStat.setString(1, currencyCode);
            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                currencyAlphaCode = rs.getString("CURRENCYALPHACODE");
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return currencyAlphaCode;
    }

    public String getSellingRate(Connection con, String currencyCode) throws Exception {
        PreparedStatement getAllByCatStat = null;
        String sellingrate = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT SELLINGRATE FROM CURRENCYEXCHANGERATE WHERE CURRENCYCODE = ?");

            getAllByCatStat.setString(1, currencyCode);

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                sellingrate = rs.getString("SELLINGRATE");
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return sellingrate;
    }

    public List<CustomerTempBean> getStaffCusTemplates(Connection con, String staffStatus, String currency) throws Exception {
        PreparedStatement getAllByCatStat = null;
        staffCusList = new ArrayList<CustomerTempBean>();
        try {
            getAllByCatStat = con.prepareStatement("SELECT TEMPLATECODE,TEMPLATENAME FROM CUSTOMERTEMPLATE WHERE STAFFSTATUS = ? AND STATUS = ? AND CURRENCYCODE = ?");

            getAllByCatStat.setString(1, staffStatus);
            getAllByCatStat.setString(2, StatusVarList.ACTIVE_STATUS);
            getAllByCatStat.setString(3, currency);
            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                CustomerTempBean bean = new CustomerTempBean();

                bean.setTemplateCode(rs.getString("TEMPLATECODE"));
                bean.setTemplateName(rs.getString("TEMPLATENAME"));

                staffCusList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return staffCusList;
    }

    public boolean UpdateCardApplicationStatus(Connection con, String applicationId, String status) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("UPDATE CARDAPPLICATIONSTATUS SET APPLICATIONSTATUS=? WHERE APPLICATIONID = ?");

            uptByCatStat.setString(1, status);
            uptByCatStat.setString(2, applicationId);

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

    public boolean updateCardApplication(Connection con, String applicationId, String sysRecoCredit, String sysRecCardproduct, String creditLimit, String cardProduct, String productionMode, String cardKey, String binProfile, String custemCode, String acctempCode, String cardtempCode, String currency, String cif) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("UPDATE CARDAPPLICATION SET SYSRECOMENDEDCREDITLIMIT = ?,SYSRECOMENDEDCARDPRODUCT = ?,CONFIRMEDCREDITLIMIT = ?,CONFIRMEDCARDPRODUCT = ?,PRODUCTIONMODE = ?,BINPROFILE = ?,CUSTEMPLATECODE = ?,ACCTEMPLATECODE = ?,CARDTEMPLATECODE = ?,SYSRECOMENDEDCURRENCY = ?,CONFIRMEDCURRENCY = ?,CARDKEYID = ?,CIF= ? WHERE APPLICATIONID = ?");

            uptByCatStat.setString(1, sysRecoCredit);
            uptByCatStat.setString(2, sysRecCardproduct);
            uptByCatStat.setString(3, creditLimit);
            uptByCatStat.setString(4, cardProduct);
            uptByCatStat.setString(5, productionMode);
            uptByCatStat.setString(6, binProfile);
            uptByCatStat.setString(7, custemCode);
            uptByCatStat.setString(8, acctempCode);
            uptByCatStat.setString(9, cardtempCode);
            uptByCatStat.setString(10, currency);
            uptByCatStat.setString(11, currency);
            uptByCatStat.setString(12, cardKey);
            uptByCatStat.setString(13, cif);
            uptByCatStat.setString(14, applicationId);

            uptByCatStat.executeUpdate();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            uptByCatStat.close();
        }
        return result;
    }

    public boolean addRemarksToPersonalInfo(Connection con, String applicationId, String tabNumber, String remarks, String lastUpdatedUser) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("INSERT INTO CARDAPPLICATIONCHEKINGREMARKS(APPLICATIONID,TABNUMBER,REMARKS,LASTUPDATEDUSER,LASTUPDATEDTIME,CREATETIME) VALUES(?,?,?,?,SYSDATE,SYSDATE)");

            uptByCatStat.setString(1, applicationId);
            uptByCatStat.setString(2, tabNumber);
            uptByCatStat.setString(3, remarks);
            uptByCatStat.setString(4, lastUpdatedUser);

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

    public boolean addRemarksToEmployement(Connection con, String applicationId, String remarks) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("UPDATE CARDEMPLOYMENTDETAILS SET REMARKS=? WHERE APPLICATIONID = ?");

            uptByCatStat.setString(1, remarks);
            uptByCatStat.setString(2, applicationId);

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

    public boolean addRemarksToIncomeExpenses(Connection con, String applicationId, String remarks) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("UPDATE CARDEXPENSES SET REMARKS=? WHERE APPLICATIONID = ?");

            uptByCatStat.setString(1, remarks);
            uptByCatStat.setString(2, applicationId);

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

    public boolean addRemarksToBankDetails(Connection con, String applicationId, String remarks) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("UPDATE CARDBANKDETAILS SET REMARKS=? WHERE APPLICATIONID = ?");

            uptByCatStat.setString(1, remarks);
            uptByCatStat.setString(2, applicationId);

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

    public CustomerEmploymentBean getApplicationEmployementDetails(Connection con, String applicationId) throws Exception {
        PreparedStatement getAllByCatStat = null;

        try {
            getAllByCatStat = con.prepareStatement("SELECT APPLICATIONID,EMPLOYMENTSTATUS,OFFICETELEPHONE, OCCUPATION," //O.DESCRIPTION AS OCCUPATION
                    + "DESIGNATION, NATUREOFBUSINESS, NOOFEMPLOYEES,COMPANYNAME,ANNUALTURNOVER,NETPROFIT,"//EN.DESCRIPTION AS NATUREOFBUSINESS,
                    + "LASTUPDATEDTIME,CREATEDDATE,LASTUPDATEDUSER,EMPLOYMENTTYPE,OFFICEADDRESS1,OFFICEADDRESS2,"
                    + "OFFICEADDRESS3,OTHEROCCUPATION,SELFEMPLOYEECOMPANYNAME,SELFEMPLOYEENOOFEMPLOYEES,OTHEREMPLOYMENTTYPE,"
                    + "OTHERNATUREOFBUSINESS,SERVICEMONTH,SERVICEYEARS,STAFFSTATUS,"
                    + "COMPANYAGE,EMPLOYMENTDURATIONYEARS,EMPLOYMENTDURATIONMONTHS,PREEMPLOYERNAME,PREEMPLOYERADDRESS1,PREEMPLOYERADDRESS2,PREEMPLOYERADDRESS3,PREMPLOYMENTDURATIONYEARS,PREMPLOYMENTDURATIONMONTHS "
                    + " FROM CARDAPPLICATIONEMPDETAILS CE " //,OCCUPATIONTYPE O,EMPLOYMENTNATURE EN
                    + " WHERE  APPLICATIONID = ?");//CE.OCCUPATION = O.OCCUPATIONTYPECODE AND CE.NATUREOFBUSINESS = EN.BUSINESSNATURECODE AND
            //get descriptions seperately, not in where clouse since some data may not be there in CARDAPPLICATIONEMPDETAILS

            getAllByCatStat.setString(1, applicationId);

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                employementDetails = new CustomerEmploymentBean();

                employementDetails.setApplicationId(rs.getString("APPLICATIONID"));
                employementDetails.setEmploymentStatus(rs.getString("EMPLOYMENTSTATUS"));
                employementDetails.setOfficePhone(rs.getString("OFFICETELEPHONE"));
                employementDetails.setOccupation(rs.getString("OCCUPATION"));
                employementDetails.setDesignation(rs.getString("DESIGNATION"));
                employementDetails.setBusinessNature(rs.getString("NATUREOFBUSINESS"));
                employementDetails.setNumberOfEmployees(rs.getString("NOOFEMPLOYEES"));
                employementDetails.setCompanyName(rs.getString("COMPANYNAME"));
                employementDetails.setAnnualTurnOver(rs.getString("ANNUALTURNOVER"));
                employementDetails.setNetProfit(rs.getString("NETPROFIT"));
                employementDetails.setEmploymentType(rs.getString("EMPLOYMENTTYPE"));
                employementDetails.setAdress1(rs.getString("OFFICEADDRESS1"));
                employementDetails.setAdress2(rs.getString("OFFICEADDRESS2"));
                employementDetails.setAdress3(rs.getString("OFFICEADDRESS3"));
                employementDetails.setOtherOccupation(rs.getString("OTHEROCCUPATION"));
                employementDetails.setSelfEmpCompanyname(rs.getString("SELFEMPLOYEECOMPANYNAME"));
                employementDetails.setSelfEmpNoOfEmployee(rs.getString("SELFEMPLOYEENOOFEMPLOYEES"));
                employementDetails.setOtherEmploymentType(rs.getString("OTHEREMPLOYMENTTYPE"));
                employementDetails.setOtherBusinessNature(rs.getString("OTHERNATUREOFBUSINESS"));
                employementDetails.setBusnessFrom(rs.getString("SERVICEMONTH"));
                employementDetails.setServiceMonth(rs.getString("SERVICEMONTH"));
                employementDetails.setServiceYear(rs.getString("SERVICEYEARS"));
                employementDetails.setStaffStatus(rs.getString("STAFFSTATUS"));
                //new by chinthaka
                employementDetails.setAgeOfCompany(rs.getString("COMPANYAGE"));
                employementDetails.setEmploymentDurationInYears(rs.getString("EMPLOYMENTDURATIONYEARS"));
                employementDetails.setEmploymentDurationInMonths(rs.getString("EMPLOYMENTDURATIONMONTHS"));
                employementDetails.setpEmployerName(rs.getString("PREEMPLOYERNAME"));
                employementDetails.setpEmployerAddress1(rs.getString("PREEMPLOYERADDRESS1"));
                employementDetails.setpEmployerAddress2(rs.getString("PREEMPLOYERADDRESS2"));
                employementDetails.setpEmployerAddress3(rs.getString("PREEMPLOYERADDRESS3"));
                employementDetails.setpEmploymentDurationInYears(rs.getString("PREMPLOYMENTDURATIONYEARS"));
                employementDetails.setpEmploymentDurationInMonths(rs.getString("PREMPLOYMENTDURATIONMONTHS"));

            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return employementDetails;
    }

    public List<CardIncomeBean> getIncomeDetails(Connection con, String applicationId) throws Exception {
        PreparedStatement getAllByCatStat = null;
        incomeDetailsList = new ArrayList<CardIncomeBean>();
        try {
            getAllByCatStat = con.prepareStatement("SELECT IC.DESCRIPTION,CI.INCOMECATEGORYCODE,CI.AMOUNT FROM CARDAPPLICATIONINCOME CI,INCOMECATEGORY IC WHERE CI.INCOMECATEGORYCODE=IC.INCOMECATEGORYCODE AND APPLICATIONID = ?");

            getAllByCatStat.setString(1, applicationId);

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {
                CardIncomeBean bean = new CardIncomeBean();

                bean.setIncomeCatogary(rs.getString("DESCRIPTION"));
                bean.setIncomeCatogaryCode(rs.getString("INCOMECATEGORYCODE"));
                bean.setAmount(rs.getString("AMOUNT"));

                incomeDetailsList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return incomeDetailsList;
    }

    public List<CardBankDetailsBean> getBankDetails(Connection con, String applicationId) throws Exception {
        PreparedStatement getAllByCatStat = null;
        cardBankDetails = new ArrayList<CardBankDetailsBean>();
        try {
            getAllByCatStat = con.prepareStatement("SELECT B.BANKNAME,BB.DESCRPTION,ACCOUNTTYPE,ACCOUNTNO,ACCOUNTSINCEYEARS,ACCOUNTSINCEMONTHS,CA.BANKCODE,CA.BRANCHCODE,CA.ISAUTOSETTLE,CA.AUTOSETPERCENTAGE FROM CARDAPPLICATIONBANKDETAILS CA,BANK B,BANKBRANCH BB WHERE CA.BANKCODE=B.BANKCODE AND CA.BRANCHCODE=BB.BRANCHCODE AND B.BANKCODE=BB.BANKCODE AND APPLICATIONID = ?");

            getAllByCatStat.setString(1, applicationId);

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {
                CardBankDetailsBean bean = new CardBankDetailsBean();

                bean.setBankName(rs.getString("BANKNAME"));
                bean.setBankCode(rs.getString("BANKCODE"));
                bean.setBranchCode(rs.getString("BRANCHCODE"));
                bean.setBankNameDes(rs.getString("BANKNAME"));
                bean.setBranchName(rs.getString("DESCRPTION"));
                bean.setAccountType(rs.getString("ACCOUNTTYPE"));
                bean.setAccountNumber(rs.getString("ACCOUNTNO"));
                bean.setSinceYear(rs.getString("ACCOUNTSINCEYEARS"));
                bean.setSinceMonth(rs.getString("ACCOUNTSINCEMONTHS"));
                bean.setAccountSince("Year: " + rs.getString("ACCOUNTSINCEYEARS") + " Months: " + rs.getString("ACCOUNTSINCEMONTHS"));
                //set auto settlement details to bean
                bean.setIsAutoSettle(rs.getString("ISAUTOSETTLE"));
                bean.setAutoSettlePerValue(rs.getString("AUTOSETPERCENTAGE"));

                cardBankDetails.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return cardBankDetails;
    }

    public List<CardDocumentBean> getDocumentDetailsForConfirmation(Connection con, String applicationId) throws Exception {
        PreparedStatement getAllByCatStat = null;
        cardDocumentList = new ArrayList<CardDocumentBean>();
        try {
            getAllByCatStat = con.prepareStatement("SELECT CAD.APPLICATIONID,CAD.VERIFICATIONCATEGORY"
                    + ",AD.DOCUMENTNAME,FILENAME FROM CARDAPPLICATIONDOCUMENT CAD,APPLICATIONDOCUMENT AD, CARDAPPLICATION CA "
                    + "WHERE CAD.DOCUMENTTYPE = AD.DOCUMENTTYPECODE AND AD.CARDCATEGORYCODE=CA.CARDCATEGORY AND CAD.APPLICATIONID=CA.APPLICATIONID AND CAD.APPLICATIONID = ?");

            getAllByCatStat.setString(1, applicationId);

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {
                CardDocumentBean bean = new CardDocumentBean();

                bean.setApplicationId(applicationId);
                bean.setVerificationCategory(rs.getString("VERIFICATIONCATEGORY"));
                bean.setDocumentType(rs.getString("DOCUMENTNAME"));
                bean.setFileName(rs.getString("FILENAME"));
                cardDocumentList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return cardDocumentList;
    }

    public CardExpensesBean getCardExpensesDetails(Connection con, String applicationId) throws Exception {
        PreparedStatement getAllByCatStat = null;
        String total = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT RENTAMOUNT,LOANINSTALLMENTAMOUNT,LEASEAMOUNT,"
                    + "CREDITCARDSBILL,OTHERBORROWS,INSURANCEINSTALLMENT,HOUSEHOLDEREXPENSES,OTHEREXPENSES"
                    + " FROM CARDAPPLICATIONEXPENSES WHERE APPLICATIONID = ?");

            getAllByCatStat.setString(1, applicationId);

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {
                cardExpenses = new CardExpensesBean();

                cardExpenses.setRentAmount(rs.getString("RENTAMOUNT"));
                cardExpenses.setLoanInstallmentAmount(rs.getString("LOANINSTALLMENTAMOUNT"));
                cardExpenses.setLeaseAmount(rs.getString("LEASEAMOUNT"));
                cardExpenses.setCreditCardbill(rs.getString("CREDITCARDSBILL"));
                cardExpenses.setOtherBorrows(rs.getString("OTHERBORROWS"));
                cardExpenses.setInsuranceInstallment(rs.getString("INSURANCEINSTALLMENT"));
                cardExpenses.setHouseHolderExpenses(rs.getString("HOUSEHOLDEREXPENSES"));
                cardExpenses.setOtherExpenses(rs.getString("OTHEREXPENSES"));

                total = Double.toString(Double.parseDouble(rs.getString("RENTAMOUNT")) + Double.parseDouble(rs.getString("LOANINSTALLMENTAMOUNT")) + Double.parseDouble(rs.getString("LEASEAMOUNT")) + Double.parseDouble(rs.getString("CREDITCARDSBILL")) + Double.parseDouble(rs.getString("OTHERBORROWS")) + Double.parseDouble(rs.getString("INSURANCEINSTALLMENT")) + Double.parseDouble(rs.getString("HOUSEHOLDEREXPENSES")) + Double.parseDouble(rs.getString("OTHEREXPENSES")));
                cardExpenses.setTotal(total);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return cardExpenses;
    }

    /*
     * auther janaka hennadi
     */
    public List<DocumentUploadBean> getDocumentDetails(Connection con, String applicationId) throws SQLException {
        PreparedStatement getAllByCatStat = null;
        cardDocumentDetails = new ArrayList<DocumentUploadBean>();
        try {
            getAllByCatStat = con.prepareStatement("SELECT D.APPLICATIONID,D.VERIFICATIONCATEGORY,D.DOCUMENTTYPE,D.FILENAME FROM CARDAPPLICATIONDOCUMENT D WHERE D.APPLICATIONID = ?");

            getAllByCatStat.setString(1, applicationId);

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {
                DocumentUploadBean bean = new DocumentUploadBean();

                bean.setApplicationId(rs.getString("APPLICATIONID"));
                bean.setVerificationCategory(rs.getString("VERIFICATIONCATEGORY"));
                bean.setDocumentType(rs.getString("DOCUMENTTYPE"));
                bean.setFileName(rs.getString("FILENAME"));
                cardDocumentDetails.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return cardDocumentDetails;
    }

    public PrimaryAppStatusBean getPrimaryApplicationStatusDetails(Connection con, String applicationId) throws SQLException {
        PrimaryAppStatusBean primaryApp = new PrimaryAppStatusBean();
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT CT.DESCRIPTION,CP.DESCRIPTION AS CARDPRODUCT,"
                    + "CA.CONFIRMEDCREDITLIMIT,S.DESCRIPTION AS APPLICATIONSTATUS FROM CARDAPPLICATION CA,"
                    + "CARDAPPLICATIONPERSONALDETAILS CAP,CARDTYPE CT,CARDPRODUCT CP,"
                    + " CARDAPPLICATIONSTATUS CAS,STATUS S WHERE CA.APPLICATIONID = CAS.APPLICATIONID "
                    + "AND CA.APPLICATIONID = CAP.APPLICATIONID AND CA.CONFIRMEDCARDPRODUCT = CP.PRODUCTCODE "
                    + "AND CAP.REQUESTCARDTYPE = CT.CARDTYPECODE AND CAS.APPLICATIONSTATUS = S.STATUSCODE AND CA.APPLICATIONID = ?");

            getAllByCatStat.setString(1, applicationId);

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {
                primaryApp.setApplicationId(applicationId);
                primaryApp.setCardType(rs.getString("DESCRIPTION"));
                primaryApp.setCardProduct(rs.getString("CARDPRODUCT"));
                primaryApp.setCreditLimit(rs.getString("CONFIRMEDCREDITLIMIT"));
                primaryApp.setAppStatust(rs.getString("APPLICATIONSTATUS"));
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return primaryApp;
    }

    public List<PrimaryCardsDetailBean> getPrimaryCardHoldersCards(Connection con, String primaryCardNo) throws SQLException {

        PreparedStatement getAllByCatStat = null;
        primaryCardList = new ArrayList<PrimaryCardsDetailBean>();
        try {
            getAllByCatStat = con.prepareStatement("SELECT C.CARDNUMBER,CT.DESCRIPTION AS CARDTYPE,"
                    + "CP.DESCRIPTION AS CARDPRODUCT,C.CREDITLIMIT FROM CARD C,CARDTYPE CT,"
                    + "CARDPRODUCT CP WHERE C.CARDTYPE = CT.CARDTYPECODE AND C.CARDPRODUCT = CP.PRODUCTCODE AND C.MAINCARDNO = ?");

            getAllByCatStat.setString(1, primaryCardNo);

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {
                PrimaryCardsDetailBean bean = new PrimaryCardsDetailBean();

                bean.setCardNumber(rs.getString("CARDNUMBER"));
                bean.setCardType(rs.getString("CARDTYPE"));
                bean.setCardproduct(rs.getString("CARDPRODUCT"));
                bean.setCreditLimit(rs.getString("CREDITLIMIT"));

                primaryCardList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return primaryCardList;
    }

    public List<ApplicationAssignBean> generalSearch(Connection con, SearchAssignAppBean searchBean) throws Exception {

        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query

        try {

            strSqlBasic = "SELECT DISTINCT a.APPLICATIONID,a.IDENTIFICATIONTYPE,ad.DOCUMENTNAME,a.IDENTIFICATIONNO,a.PRIORITYLEVELCODE , pl.DESCRIPTION AS PRIORITYNAME, a.REFERANCIALEMPNO , "
                    + "a.ASSIGNUSER, a.ASSIGNSTATUS , st.DESCRIPTION ,a.LASTUPDATEDUSER,a.LASTUPDATEDTIME,a.CREATETIME,a.CARDCATEGORY,cc.DESCRIPTION AS CATEGORYDESCRIPTION "
                    + "FROM CARDAPPLICATION a ,STATUS st,PRIORITYLEVEL pl ,CARDAPPLICATIONSTATUS cas,CARDCATEGORY cc,APPLICATIONDOCUMENT ad";

            String condition = "";

            if (!searchBean.getApplicationId().contentEquals("") || searchBean.getApplicationId().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.APPLICATIONID = '" + searchBean.getApplicationId() + "'";
            }

            if (!searchBean.getPriorityLevel().contentEquals("") || searchBean.getPriorityLevel().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.PRIORITYLEVELCODE = '" + searchBean.getPriorityLevel() + "'";
            }

            if (!searchBean.getAssignUser().contentEquals("") || searchBean.getAssignUser().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.ASSIGNUSER = '" + searchBean.getAssignUser() + "'";
            }

            if (!searchBean.getFromDate().contentEquals("") && !searchBean.getToDate().contentEquals("")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.CREATETIME >= to_Date('" + this.stringTodate(searchBean.getFromDate()) + "','yy-mm-dd') AND a.CREATETIME <= to_Date('" + this.stringTodate(searchBean.getToDate()) + "','yy-mm-dd')";
            }
            if (!searchBean.getCardCategory().contentEquals("") || searchBean.getCardCategory().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.CARDCATEGORY = '" + searchBean.getCardCategory() + "'";
            } else {

                if (!searchBean.getFromDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "a.CREATETIME >= to_Date('" + this.stringTodate(searchBean.getFromDate()) + "','yy-mm-dd')";
                }
                if (!searchBean.getToDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "a.CREATETIME <= to_Date('" + this.stringTodate(searchBean.getToDate()) + "','yy-mm-dd')";
                }
            }

            if (!condition.equals("")) {
                strSqlBasic += " WHERE cas.APPLICATIONSTATUS = st.STATUSCODE AND a.IDENTIFICATIONTYPE=ad.DOCUMENTTYPECODE AND a.CARDCATEGORY=ad.CARDCATEGORYCODE AND a.PRIORITYLEVELCODE = pl.PRIORITYLEVELCODE AND  a.APPLICATIONID = cas.APPLICATIONID AND cc.CATEGORYCODE = a.CARDCATEGORY AND " + condition + " AND (cas.APPLICATIONSTATUS = ? OR cas.APPLICATIONSTATUS = ?) AND a.CARDAPPLIACTIONDOMAIN = ? ORDER BY a.APPLICATIONID";
            } else {
                strSqlBasic += " WHERE cas.APPLICATIONSTATUS = st.STATUSCODE AND a.IDENTIFICATIONTYPE=ad.DOCUMENTTYPECODE AND a.CARDCATEGORY=ad.CARDCATEGORYCODE AND a.PRIORITYLEVELCODE = pl.PRIORITYLEVELCODE AND a.APPLICATIONID = cas.APPLICATIONID AND cc.CATEGORYCODE = a.CARDCATEGORY AND (cas.APPLICATIONSTATUS = ? OR cas.APPLICATIONSTATUS = ?) AND a.CARDAPPLIACTIONDOMAIN = ? ORDER BY a.APPLICATIONID";
            }

            stmt = con.prepareStatement(strSqlBasic);
            System.out.println(strSqlBasic);

//            stmt.setString(1, StatusVarList.CARD_CATEGORY_MAIN);
            stmt.setString(1, StatusVarList.APP_FILLING_COMPLETE);
            stmt.setString(2, StatusVarList.APP_CHECKOUT);
            stmt.setString(3, DataTypeVarList.CARD_DOMAIN_CREDIT);

            rs = stmt.executeQuery();

            searchAssignAppList = new ArrayList<ApplicationAssignBean>();

            while (rs.next()) {

                ApplicationAssignBean resultAssign = new ApplicationAssignBean();

                resultAssign.setApplicationId(rs.getString("APPLICATIONID"));
                resultAssign.setAssignUser(rs.getString("ASSIGNUSER"));
                resultAssign.setCreatedTime(rs.getTimestamp("CREATETIME"));
                resultAssign.setIdentityNo(rs.getString("IDENTIFICATIONNO"));
                resultAssign.setIdentityType(rs.getString("IDENTIFICATIONTYPE"));
                resultAssign.setLastUpdatedTime(rs.getTimestamp("LASTUPDATEDTIME"));
                resultAssign.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                resultAssign.setPriorityLevel(rs.getString("PRIORITYLEVELCODE"));
                resultAssign.setPriorityDescription(rs.getString("PRIORITYNAME"));
                resultAssign.setReferralEmpNo(rs.getString("REFERANCIALEMPNO"));
                resultAssign.setAssignStatus(rs.getString("ASSIGNSTATUS"));
                resultAssign.setAssignStatusDescription(rs.getString("DESCRIPTION"));
                resultAssign.setCardCategory(rs.getString("CARDCATEGORY"));
                resultAssign.setCardCategoryDes(rs.getString("CATEGORYDESCRIPTION"));
                resultAssign.setIdentityTypeName(rs.getString("DOCUMENTNAME"));

                searchAssignAppList.add(resultAssign);

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

        return searchAssignAppList;
    }

    private String stringTodate(String date) throws ParseException {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        Date convertedDate = dateFormat.parse(dateString);

        return (dateFormat.format(convertedDate));

    }

    public CustomerPersonalBean getPersonalDetailsUsingTable(Connection con, String applicationId) throws Exception {
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT CPD.APPLICATIONID,CPD.TITLE,CPD.FIRSTNAME,"
                    + "CPD.LASTNAME,CPD.MIDDLENAME,CPD.DATEOFBIRTH,CPD.GENDER,CPD.NATIONALITY,"
                    + "CPD.NIC,CPD.PASSPORTNUMBER,CPD.PASSPORTEXPIREDATE,CPD.ADDRESS1,CPD.ADDRESS2,CPD.ADDRESS3,A.DESCRIPTION AS CITY,CPD.POSTALCODE,"
                    + "CPD.HOMETELEPHONENO,CPD.MOBILENO,CPD.SMSALERTMOBILENO,"
                    + "CPD.EMAIL,CPD.ACADEMICQUALIFICATION,"
                    + "CPD.PROFESSIONALQUALIFICATION,CPD.RELIGION,CPD.BLOODGROUP,"
                    + "CPD.MOTHERSMAIDENNAME,CPD.LASTUPDATEDUSER,CPD.LASTUPDATEDTIME,"
                    + "CPD.CREATETIME,CPD.NAMEONCARD,CPD.OFFICEPHONENO,"
                    + "CPD.REQUESTCARDTYPE,CT.DESCRIPTION AS CARDTYPEDES,CPD.REQUESTCARDPRODUCT,CP.DESCRIPTION AS PRODUCTDES,CPD.REQUESTCREDITLIMIT,CPD.CURRENCYTYPE,C.DESCRIPTION AS CURRENCYDES"
                    + " FROM CARDCOOPRATEAPPLICATIONDETAILS CPD,AREA A,CURRENCY C,CARDPRODUCT CP,CARDTYPE CT WHERE CPD.CITY = A.AREACODE AND CPD.CURRENCYTYPE = C.CURRENCYNUMCODE AND CPD.REQUESTCARDPRODUCT = CP.PRODUCTCODE AND CT.CARDTYPECODE = CPD.REQUESTCARDTYPE AND APPLICATIONID = ?");

            getAllByCatStat.setString(1, applicationId);

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {
                // bean.setRelEmail(rs.getString("RELEMAIL"));
                bean.setApplicationId(rs.getString("APPLICATIONID"));
                bean.setTitle(rs.getString("TITLE"));
                //bean.setInitials(rs.getString("INITIALS"));
                //bean.setFirstName(rs.getString("FIRSTNAME"));
                //bean.setLastName(rs.getString("LASTNAME"));
                //bean.setMiddleName(rs.getString("MIDDLENAME"));
                bean.setBirthday(rs.getString("DATEOFBIRTH"));
                //bean.setPlaceOfbirth(rs.getString("PLACEOFBIRTH"));
                bean.setGender(rs.getString("GENDER"));
                //bean.setMaritalStatus(rs.getString("MARITALSTATUS"));
                bean.setNationality(rs.getString("NATIONALITY"));
                bean.setNic(rs.getString("NIC"));
                bean.setPassportNumber(rs.getString("PASSPORTNUMBER"));
                bean.setPassportExpdate(rs.getString("PASSPORTEXPIREDATE"));
                bean.setAddress1(rs.getString("ADDRESS1"));
                bean.setAddress2(rs.getString("ADDRESS2"));
                bean.setAddress3(rs.getString("ADDRESS3"));
                bean.setCity(rs.getString("CITY"));
                bean.setPostalcode(rs.getString("POSTALCODE"));
                bean.setHomeTelNumber(rs.getString("HOMETELEPHONENO"));
                bean.setMobileNumber(rs.getString("MOBILENO"));
                bean.setSmsalertMobileNumber(rs.getString("SMSALERTMOBILENO"));
                //  bean.setEmergencyContactNumber(rs.getString("EMERGENCYCONTACTNO"));
                //  bean.setDurationofTheAddress(rs.getString("DURATIONATTHEABOVEADDRESS"));
                bean.setEmail(rs.getString("EMAIL"));
                //  bean.setResidenceType(rs.getString("RECIDENCETYPE"));
                //  bean.setMonthlyRental(rs.getString("MONTHLYRENTAL"));
                //  bean.setMorgageRental(rs.getString("MORGAGERENTAL"));
                bean.setAcedemicQualifications(rs.getString("ACADEMICQUALIFICATION"));
                bean.setProfessionalQualifications(rs.getString("PROFESSIONALQUALIFICATION"));
                //   bean.setVehicalType(rs.getString("VEHICALTYPE"));
                //   bean.setVehicalNo(rs.getString("VEHICALNO"));
                //   bean.setOwnership(rs.getString("OWNERSHIP"));
                //   bean.setReligion(rs.getString("RELIGION"));
                bean.setBloodgroup(rs.getString("BLOODGROUP"));
                bean.setMothersMaidenName(rs.getString("MOTHERSMAIDENNAME"));
                //    bean.setNumberOfDependance(rs.getString("NOOFDEPENDANCE"));
                //    bean.setUseCardOnline(rs.getString("USECARDONLINE"));
                bean.setLastUpdateUser(rs.getString("LASTUPDATEDUSER"));
                bean.setNameOncard(rs.getString("NAMEONCARD"));
                bean.setOfficeTelNumber(rs.getString("OFFICEPHONENO"));
                //     bean.setRelName(rs.getString("RELATIVENAME"));
                //     bean.setRelationship(rs.getString("RELATIONSHIP"));
                //      bean.setRelAddress1(rs.getString("BILLINGADDRESS1"));
                //      bean.setRelAddress2(rs.getString("BILLINGADDRESS2"));
                //     bean.setRelAddress3(rs.getString("BILLINGADDRESS3"));
                //      bean.setRelCity(rs.getString("BILLINGCITY"));
                //  bean.setRelResidencePhone(rs.getString("RELRESIDANCEPHONE"));
                //  bean.setRelMobile(rs.getString("RELMOBILENO"));
                //   bean.setRelCompany(rs.getString("RELEMPLOYER"));
                //    bean.setRelOfficeNumber(rs.getString("RELOFFICEPHONE"));
                //     bean.setSpouseName(rs.getString("SPOUSENAME"));
                //    bean.setSpouseDateofBirth(rs.getString("SPOUSEDATEOFBIRTH"));
                //     bean.setSpouseNic(rs.getString("SPOUSENIC"));
                //     bean.setSpousePassportNumber(rs.getString("SPOUSEPASSPORTNO"));
                //    bean.setSpouseNameofEmployee(rs.getString("SPOUSENAMEOFEMPLOYEE"));
                //     bean.setSpousecompanyAddress(rs.getString("SPOUSECOMPANYADDRESS"));
                //     bean.setSpouseDesignation(rs.getString("SPOUSEDESIGNATION"));
                //bean.setSpousePhone(rs.getString("SPOUSEPHONE"));
                // bean.setSpouseMail(rs.getString("SPOUSEEMAIL"));
                // bean.setSpouseMonthlyIncome(rs.getString("SPOUSEMONTHLYINCOME"));
                bean.setCardType(rs.getString("REQUESTCARDTYPE"));
                bean.setCardTypeDes(rs.getString("CARDTYPEDES"));
                bean.setCardProduct(rs.getString("REQUESTCARDPRODUCT"));
                bean.setCardProductDes(rs.getString("PRODUCTDES"));
                bean.setCreditLimit(rs.getString("REQUESTCREDITLIMIT"));
                bean.setCardCurrency(rs.getString("CURRENCYTYPE"));
                bean.setCurrencyDes(rs.getString("CURRENCYDES"));

            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return bean;
    }

    public List<EstablishmentAssetsBean> getAllAssetDetails(Connection CMSCon, String businessRegNo) throws SQLException {
        PreparedStatement getAllByCatStat = null;
        List<EstablishmentAssetsBean> list = new ArrayList<EstablishmentAssetsBean>();
        try {
            getAllByCatStat = CMSCon.prepareStatement("SELECT DISTINCT "
                    + "EA.BUSINESSREGNUMBER "
                    + ",EA.ASSETTYPECODE "
                    + ",EA.ASSETCODE "
                    + ",EA.VALUE "
                    + ", AL.DESCRIPTION AS TYPEDESCRIPTION "
                    + ",A.DESCRIPTION "
                    + "FROM  "
                    + "ESTABLISHMENTASSETS EA, ASSET A , ASSETORLIABILITYTYPE AL "
                    + "WHERE  EA.ASSETTYPECODE=AL.TYPECODE "
                    + "AND A.ASSETCODE=EA.ASSETCODE AND BUSINESSREGNUMBER=? ");

            getAllByCatStat.setString(1, businessRegNo);

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {
                EstablishmentAssetsBean bean = new EstablishmentAssetsBean();

                bean.setAssetCode(rs.getString("ASSETCODE"));
                bean.setAssetTypeCode(rs.getString("ASSETTYPECODE"));
                bean.setAssetValue(rs.getString("VALUE"));
                bean.setBusinessRegNumber(rs.getString("BUSINESSREGNUMBER"));
                bean.setNameDes(rs.getString("DESCRIPTION"));
                bean.setTypeDes(rs.getString("TYPEDESCRIPTION"));

                list.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return list;
    }

    public List<EstablishmentLiabilityBean> getAllLiabilityDetails(Connection CMSCon, String businessRegNo) throws SQLException {
        PreparedStatement getAllByCatStat = null;
        List<EstablishmentLiabilityBean> list = new ArrayList<EstablishmentLiabilityBean>();
        try {
            getAllByCatStat = CMSCon.prepareStatement("SELECT DISTINCT "
                    + "EL.BUSINESSREGNUMBER "
                    + ",EL.LIABILITYTYPECODE "
                    + ",EL.LIABILITYCODE "
                    + ",EL.VALUE "
                    + ", AL.DESCRIPTION AS TYPEDESCRIPTION "
                    + ",L.DESCRIPTION "
                    + "FROM  "
                    + "ESTABLISHMENTLIABILITY EL, LIABILITY L , ASSETORLIABILITYTYPE AL "
                    + "WHERE  EL.LIABILITYTYPECODE=AL.TYPECODE "
                    + "AND L.LIABILITYCODE=EL.LIABILITYCODE AND BUSINESSREGNUMBER=? ");

            getAllByCatStat.setString(1, businessRegNo);

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {
                EstablishmentLiabilityBean bean = new EstablishmentLiabilityBean();

                bean.setLiabilityCode(rs.getString("LIABILITYCODE"));
                bean.setLiabilityTypeCode(rs.getString("LIABILITYTYPECODE"));
                bean.setLiabilityValue(rs.getString("VALUE"));
                bean.setBusinessRegNumber(rs.getString("BUSINESSREGNUMBER"));
                bean.setNameDes(rs.getString("DESCRIPTION"));
                bean.setTypeDes(rs.getString("TYPEDESCRIPTION"));

                list.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return list;
    }

    public CardBean getCardByApplicationId(Connection CMSCon, String applicationId) throws Exception {

        CardBean bean = new CardBean();

        PreparedStatement getAllByCatStat = null;
        try {
            String sql = "SELECT CA.CREDITLIMIT FROM CARD CA "
                    + "WHERE  CA.APPLICATIONID = ?";
            getAllByCatStat = CMSCon.prepareStatement(sql);

            getAllByCatStat.setString(1, applicationId);

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {
                bean.setCreditLimit(rs.getString("CREDITLIMIT"));

            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return bean;

    }

    //get card product list for requested card type by chinthaka
    public List<CardProductBean> getCardProductsToRequestedCardType(Connection con) throws Exception {
        PreparedStatement getCardProducts = null;
        List<CardProductBean> cardProductList = new ArrayList<CardProductBean>();
        try {
            getCardProducts = con.prepareStatement("SELECT PRODUCTCODE,DESCRIPTION FROM CARDPRODUCT WHERE CARDDOMAIN=?");
            getCardProducts.setString(1, StatusVarList.CREDIT);
            rs = getCardProducts.executeQuery();
            while (rs.next()) {
                CardProductBean cpbean = new CardProductBean();
                cpbean.setProductCode(rs.getString("PRODUCTCODE"));
                cpbean.setDescription(rs.getString("DESCRIPTION"));
                cardProductList.add(cpbean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getCardProducts.close();
        }
        return cardProductList;
    }

    public boolean isCreditAmountWithinTheUserConfirmRange(Connection CMSCon, String userRole, double creditOfficerRecCreditLimit) throws Exception {
        boolean isInTheRange = false;
        PreparedStatement stmt = null;
        try {
            stmt = CMSCon.prepareStatement("SELECT MINLIMIT,MAXLIMIT FROM CREDITLIMITSCHEMA WHERE SCHEMACODE IN (SELECT SCHEMACODE FROM CREDITLIMTSCHEMAUSERROLE WHERE USERROLECODE= ?) ORDER BY MINLIMIT ASC");
            stmt.setString(1, userRole);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Double MINLIMIT = Double.parseDouble(rs.getString("MINLIMIT"));
                Double MAXLIMIT = Double.parseDouble(rs.getString("MAXLIMIT"));

                if (creditOfficerRecCreditLimit > MINLIMIT && creditOfficerRecCreditLimit <= MAXLIMIT) {
                    isInTheRange = true;
                }

            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            stmt.close();
        }
        return isInTheRange;
    }

    //get CIF from FM_CLIENT table (must be change future)

    public String getCifNumberToIdentificationNumber(Connection CMSCon, String idNum) throws Exception {
        PreparedStatement stmt = null;
        String cif = null;
        try {
            stmt = CMSCon.prepareStatement("SELECT CLIENT_NO FROM FM_CLIENT WHERE GLOBAL_ID= ?");
            stmt.setString(1, idNum);
            rs = stmt.executeQuery();
            while (rs.next()) {
                cif = rs.getString("CLIENT_NO");
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            stmt.close();
        }
        return cif;
    }

    //get All Occupation List

    public List<OccupationBean> getAllOccupationLst(Connection con) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        occupationList = new ArrayList<OccupationBean>();
        try {
            stmt = con.prepareStatement("SELECT OCCUPATIONTYPECODE,DESCRIPTION,SORTKEY FROM OCCUPATIONTYPE ORDER BY DESCRIPTION ASC  ");
            rs = stmt.executeQuery();

            while (rs.next()) {
                OccupationBean bean = new OccupationBean();

                bean.setOccupationTypeCode(rs.getString("OCCUPATIONTYPECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                occupationList.add(bean);
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
        return occupationList;
    }

}
