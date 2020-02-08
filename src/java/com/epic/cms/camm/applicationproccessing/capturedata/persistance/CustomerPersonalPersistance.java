/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.capturedata.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardCustomerBean;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationAssignBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.AreaBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.AssetBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.AssetLiabilityTypeBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.BankBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.BankBranchBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationStatusBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardBankDetailsBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardExpensesBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardIncomeBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerEmploymentBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DebitPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DocumentTypeBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DocumentUploadBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EmploymentNatureBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EmploymentTypeBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EstablishmentAssetsBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EstablishmentDetailsBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EstablishmentLiabilityBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.IdBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.IncomeTypeBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.LiabilityBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.OccupationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.SearchUserAssignAppBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.SupplementaryApplicationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.VerificationCategoryBean;
import com.epic.cms.system.util.logs.LogFileCreator;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author janaka_h
 */
public class CustomerPersonalPersistance {

    private List<ApplicationAssignBean> searchAssignAppList = null;
    private List<EmploymentTypeBean> empTypeList = null;
    private List<VerificationCategoryBean> verificationCateLst = null;
    private List<DocumentTypeBean> documentTypeLst = null;
    private List<OccupationBean> occupationList = null;
    private List<EmploymentNatureBean> natureList = null;
    private List<IncomeTypeBean> incomeLst = null;
    private List<BankBean> bankLst = null;
    private List<AreaBean> areaLst = null;
    private List<BankBranchBean> bankBranchLst = null;
    private CustomerPersonalBean customerPersonalBean = null;
    private SupplementaryApplicationBean customerSuplimentoryPersonalBean = null;
    private List<String> nationalityList = null;
    private CardCustomerBean customerBean;
    private List<CardProductBean> cardProductList = null;
    private List<CurrencyBean> currencyList = null;
    private DebitPersonalBean debitPersonalBean = null;
    private HashMap<String, String> accountTypeList = null;
    private List<AssetLiabilityTypeBean> assetsLiabilityTypeList = null;
    private List<AssetBean> assetsList = null;
    private List<LiabilityBean> liabilityList = null;
    /*
     * insert quaries.........................................................................................................................................................
     */

    public int insertPersonalRecord(Connection con, CustomerPersonalBean personalBean) throws Exception {
        int resultId;

        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO CARDAPPLICATIONPERSONALDETAILS "
                    + "(APPLICATIONID,TITLE,NAMEINFULL,NOOFDEPENDANCE,PASSPORTEXPIREDATE,NAMEONCARD,DATEOFBIRTH,PLACEOFBIRTH,GENDER,MARITALSTATUS,NATIONALITY,NIC,PASSPORTNUMBER,ADDRESS1, "
                    + "ADDRESS2,ADDRESS3,CITY,HOMETELEPHONENO,MOBILENO,OFFICEPHONENO,EMAIL,RECIDENCETYPE,SMSALERTSTATUS,BLOODGROUP,"
                    + "MOTHERSMAIDENNAME,RELATIVENAME,RELATIONSHIP,RELADDRESS1,RELADDRESS2,RELADDRESS3,RELCITY,RELRESIDANCEPHONE,RELMOBILENO,RELEMPLOYER,RELOFFICEPHONE,SPOUSENAME,SPOUSENIC,"
                    + "SPOUSENAMEOFEMPLOYEE,SPOUSECOMPANYADDRESS,SPOUSEDESIGNATION,SPOUSEPHONE,SPOUSEEMAIL,SPOUSEMONTHLYINCOME,VEHICALTYPE,VEHICALNO,OWNERSHIP,PROFESSIONALQUALIFICATION,"
                    + "ACADEMICQUALIFICATION,REQUESTCARDTYPE,REQUESTCARDPRODUCT,REQUESTCREDITLIMIT,NAMEWITHINITIAL,PERMANENTADDRESS1,PERMANENTADDRESS2,PERMANANTADDRESS3,PERMANAENTCITY,"
                    + "BILLINGADDRESS1,BILLINGADDRESS2,BILLINGADDRESS3,BILLINGCITY,RELEMAIL,DURATIONATTHEABOVEADDRESS,CURRENCYTYPE,PERMANENTPROVINCE,PERMANENTDISTRICT,RESIDEPROVINCE,"
                    + "RESIDEDISTRICT,BILLINGPROVINCE,BILLINGDISTRICT,RELDISTRICT,RELPROVINCE,DESIGNATION,BUSINESSREGNUMBER,FIRSTNAME,LASTNAME,MIDDLENAME) "
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

            insertStat.setString(1, personalBean.getApplicationId());
            insertStat.setString(2, personalBean.getTitle());
            insertStat.setString(3, personalBean.getFullName());
            insertStat.setString(4, personalBean.getNoOfDependens());
            insertStat.setString(5, personalBean.getPassportExpdate());
            insertStat.setString(6, personalBean.getNameOncard());
            insertStat.setString(7, personalBean.getBirthday());
            insertStat.setString(8, personalBean.getPlaceOfbirth());
            insertStat.setString(9, personalBean.getGender());
            insertStat.setString(10, personalBean.getMaritalStatus());
            insertStat.setString(11, personalBean.getNationality());
            insertStat.setString(12, personalBean.getNic());
            insertStat.setString(13, personalBean.getPassportNumber());
            insertStat.setString(14, personalBean.getAddress1());
            insertStat.setString(15, personalBean.getAddress2());
            insertStat.setString(16, personalBean.getAddress3());
            insertStat.setString(17, personalBean.getCity());
            insertStat.setString(18, personalBean.getHomeTelNumber());
            insertStat.setString(19, personalBean.getMobileNumber());
            insertStat.setString(20, personalBean.getOfficeTelNumber());
            insertStat.setString(21, personalBean.getEmail());
            insertStat.setString(22, personalBean.getResidenceType());
            insertStat.setString(23, personalBean.getSmsAlertStatus());
            insertStat.setString(24, personalBean.getBloodgroup());
            insertStat.setString(25, personalBean.getMothersMaidenName());
            insertStat.setString(26, personalBean.getRelName());
            insertStat.setString(27, personalBean.getRelationship());
            insertStat.setString(28, personalBean.getRelAddress1());
            insertStat.setString(29, personalBean.getRelAddress2());
            insertStat.setString(30, personalBean.getRelAddress3());
            insertStat.setString(31, personalBean.getRelCity());
            insertStat.setString(32, personalBean.getRelResidencePhone());
            insertStat.setString(33, personalBean.getRelMobile());
            insertStat.setString(34, personalBean.getRelCompany());
            insertStat.setString(35, personalBean.getRelOfficeNumber());
            insertStat.setString(36, personalBean.getSpouseName());
            insertStat.setString(37, personalBean.getSpouseNic());
            insertStat.setString(38, personalBean.getSpouseNameofEmployee());
            insertStat.setString(39, personalBean.getSpousecompanyAddress());
            insertStat.setString(40, personalBean.getSpouseDesignation());
            insertStat.setString(41, personalBean.getSpousePhone());
            insertStat.setString(42, personalBean.getSpouseMail());
            insertStat.setString(43, personalBean.getSpouseMonthlyIncome());
            insertStat.setString(44, personalBean.getVehicalType());
            insertStat.setString(45, personalBean.getVehicalNo());
            insertStat.setString(46, personalBean.getOwnership());
            insertStat.setString(47, personalBean.getProfessionalQualifications());
            insertStat.setString(48, personalBean.getAcedemicQualifications());
            insertStat.setString(49, personalBean.getCardType());
            insertStat.setString(50, personalBean.getCardProduct());
            insertStat.setString(51, personalBean.getCreditLimit());
            insertStat.setString(52, personalBean.getNameWithInitials());
            insertStat.setString(53, personalBean.getPermentAddress1());
            insertStat.setString(54, personalBean.getPermentAddress2());
            insertStat.setString(55, personalBean.getPermentAddress3());
            insertStat.setString(56, personalBean.getPermentCity());
            insertStat.setString(57, personalBean.getBillAddress1());
            insertStat.setString(58, personalBean.getBillAddress2());
            insertStat.setString(59, personalBean.getBillAddress3());
            insertStat.setString(60, personalBean.getBillCity());
            insertStat.setString(61, personalBean.getRelEmail());
            insertStat.setString(62, personalBean.getDurationofTheAddress());
            insertStat.setString(63, personalBean.getCardCurrency());
            insertStat.setString(64, personalBean.getProvince());
            insertStat.setString(65, personalBean.getDistrict());
            insertStat.setString(66, personalBean.getResProvince());
            insertStat.setString(67, personalBean.getResDistrict());
            insertStat.setString(68, personalBean.getBillProvince());
            insertStat.setString(69, personalBean.getBillDistrict());
            insertStat.setString(70, personalBean.getRelDistrict());
            insertStat.setString(71, personalBean.getRelProvince());

            insertStat.setString(72, personalBean.getDesignation());
            insertStat.setString(73, personalBean.getBusinessRegNumber());
            insertStat.setString(74, personalBean.getFirstName());
            insertStat.setString(75, personalBean.getMiddleName());
            insertStat.setString(76, personalBean.getLastName());

            resultId = insertStat.executeUpdate();

        } catch (Exception ex) {
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

    public int insertSuplimentoryPersonalRecord(Connection con, SupplementaryApplicationBean personalBean) throws Exception {
        int resultId;

        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO SUPPLEMENTARYAPPLICATION "
                    + "(APPLICATIONID,TITLE,FIRSTNAME,LASTNAME,MIDDLENAME,NAMEONCARD,DATEOFBIRTH,GENDER,NATIONALITY,NIC,PASSPORTNUMBER,PASSPORTEXPIREDDATE,ADDRESS1, "
                    + "ADDRESS2,ADDRESS3,CITY,PRIMARYCARDNUMBER,PRIMARYID,HOMETELEPHONE,MOBILE,ADDRESSSAMEASPRIMARY,OCCUPATION,EMPLOYMENTTYPE,REQUESTCARDTYPE,REQUESTCARDPRODUCT,"
                    + "REQUESTCREDITLIMIT,NAMEWITHINITIAL,BILLINGADDRESS1,BILLINGADDRESS2,BILLINGADDRESS3,BILLINGCITY,PRIMARYAPPLICATIONID,RELATIONSHIP,PERCENTAGECREDITLIMIT,CLPERORFIXED,PRIMARYITTYPE)"
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

            insertStat.setString(1, personalBean.getApplicationId());
            insertStat.setString(2, personalBean.getTitle());
            insertStat.setString(3, personalBean.getFirstName());
            insertStat.setString(4, personalBean.getLastName());
            insertStat.setString(5, personalBean.getMiddleName());
            insertStat.setString(6, personalBean.getNameOncard());
            insertStat.setString(7, personalBean.getBirthday());
            insertStat.setString(8, personalBean.getGender());
            insertStat.setString(9, personalBean.getNationality());
            insertStat.setString(10, personalBean.getNic());
            insertStat.setString(11, personalBean.getPassportNumber());
            insertStat.setString(12, personalBean.getPassportExpdate());
            insertStat.setString(13, personalBean.getAddress1());
            insertStat.setString(14, personalBean.getAddress2());
            insertStat.setString(15, personalBean.getAddress3());
            insertStat.setString(16, personalBean.getCity());
            insertStat.setString(17, personalBean.getPrimaryCardNumber());
            insertStat.setString(18, personalBean.getPrimaryId());
            insertStat.setString(19, personalBean.getHomeTelNumber());
            insertStat.setString(20, personalBean.getMobileNumber());
            insertStat.setString(21, "NO");
            insertStat.setString(22, personalBean.getOccupation());
            insertStat.setString(23, personalBean.getEmployementType());
            insertStat.setString(24, personalBean.getCardType());
            insertStat.setString(25, personalBean.getCardProduct());
            insertStat.setString(26, personalBean.getCreditLimit());
            insertStat.setString(27, personalBean.getNameWithinitials());
            insertStat.setString(28, personalBean.getBillingAdress1());
            insertStat.setString(29, personalBean.getBillingAdress2());
            insertStat.setString(30, personalBean.getBillingAdress3());
            insertStat.setString(31, personalBean.getBillingCity());
            insertStat.setString(32, personalBean.getPrimaryCardApplicationId());
            insertStat.setString(33, personalBean.getRelationShip());
            insertStat.setString(34, personalBean.getPercentageValue());
            insertStat.setString(35, personalBean.getClimitReqType());
            insertStat.setString(36, personalBean.getPrimaryIdType());

            resultId = insertStat.executeUpdate();

        } catch (Exception ex) {
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

    public int insertEmploymentRecord(Connection con, CustomerEmploymentBean employmentBean) throws Exception {
        int resultId = 0;

        PreparedStatement insertStat = null;
        PreparedStatement updateStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO CARDAPPLICATIONEMPDETAILS "
                    + "(APPLICATIONID,EMPLOYMENTSTATUS,OFFICETELEPHONE,OCCUPATION,DESIGNATION,NATUREOFBUSINESS,NOOFEMPLOYEES,COMPANYNAME,ANNUALTURNOVER,NETPROFIT,EMPLOYMENTTYPE,OFFICEADDRESS1,OFFICEADDRESS2,OFFICEADDRESS3,OTHEROCCUPATION,SELFEMPLOYEECOMPANYNAME,SELFEMPLOYEENOOFEMPLOYEES,OTHEREMPLOYMENTTYPE,OTHERNATUREOFBUSINESS,SERVICEYEARS,SERVICEMONTH,STAFFSTATUS,COMPANYAGE,EMPLOYMENTDURATIONYEARS,EMPLOYMENTDURATIONMONTHS,PREEMPLOYERNAME,PREEMPLOYERADDRESS1,PREEMPLOYERADDRESS2,PREEMPLOYERADDRESS3,PREMPLOYMENTDURATIONYEARS,PREMPLOYMENTDURATIONMONTHS) "
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

            insertStat.setString(1, employmentBean.getApplicationId());
            insertStat.setString(2, employmentBean.getEmploymentStatus());
            insertStat.setString(3, employmentBean.getOfficePhone());
            insertStat.setString(4, employmentBean.getOccupation());
            insertStat.setString(5, employmentBean.getDesignation());
            insertStat.setString(6, employmentBean.getBusinessNature());
            insertStat.setString(7, employmentBean.getNumberOfEmployees());
            insertStat.setString(8, employmentBean.getCompanyName());
            insertStat.setString(9, employmentBean.getAnnualTurnOver());
            insertStat.setString(10, employmentBean.getNetProfit());
            insertStat.setString(11, employmentBean.getEmploymentType());
            insertStat.setString(12, employmentBean.getAdress1());
            insertStat.setString(13, employmentBean.getAdress2());
            insertStat.setString(14, employmentBean.getAdress3());
            insertStat.setString(15, employmentBean.getOtherOccupation());
            insertStat.setString(16, employmentBean.getSelfEmpCompanyname());
            insertStat.setString(17, employmentBean.getSelfEmpNoOfEmployee());
            insertStat.setString(18, employmentBean.getOtherEmploymentType());
            insertStat.setString(19, employmentBean.getOtherBusinessNature());
            insertStat.setInt(20, Integer.parseInt(employmentBean.getServiceYear()));
            insertStat.setInt(21, Integer.parseInt(employmentBean.getServiceMonth()));
            insertStat.setString(22, employmentBean.getStaffStatus());
            //new by chinthaka
            if (employmentBean.getAgeOfCompany().isEmpty()) {
                insertStat.setString(23, employmentBean.getAgeOfCompany());
            } else {
                insertStat.setInt(23, Integer.parseInt(employmentBean.getAgeOfCompany()));
            }
            insertStat.setInt(24, Integer.parseInt(employmentBean.getEmploymentDurationInYears()));
            insertStat.setInt(25, Integer.parseInt(employmentBean.getEmploymentDurationInMonths()));
            insertStat.setString(26, employmentBean.getpEmployerName());
            insertStat.setString(27, employmentBean.getpEmployerAddress1());
            insertStat.setString(28, employmentBean.getpEmployerAddress2());
            insertStat.setString(29, employmentBean.getpEmployerAddress3());
            insertStat.setInt(30, Integer.parseInt(employmentBean.getpEmploymentDurationInYears()));
            insertStat.setInt(31, Integer.parseInt(employmentBean.getpEmploymentDurationInMonths()));

            int resultId1 = insertStat.executeUpdate();

            updateStat = con.prepareStatement("UPDATE CARDAPPLICATION SET STAFFSTATUS=? WHERE APPLICATIONID=?");
            updateStat.setString(1, employmentBean.getStaffStatus());
            updateStat.setString(2, employmentBean.getApplicationId());

            int resultId2 = updateStat.executeUpdate();

            if (resultId1 == 1 && resultId2 == 1) {
                resultId = 1;
            } else {
                throw new Exception();
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                insertStat.close();
                updateStat.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;
    }

    public int insertIncomeAndExpensesRecord(Connection con, CardExpensesBean expensesBean, List<CardIncomeBean> incomeList) throws Exception {
        int resultId = 0;

        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO CARDAPPLICATIONEXPENSES "
                    + "(APPLICATIONID,RENTAMOUNT,LOANINSTALLMENTAMOUNT,LEASEAMOUNT,CREDITCARDSBILL,OTHERBORROWS,INSURANCEINSTALLMENT,HOUSEHOLDEREXPENSES,OTHEREXPENSES ) "
                    + " VALUES (?,?,?,?,?,?,?,?,?) ");

            insertStat.setString(1, expensesBean.getApplicationId());
            insertStat.setString(2, expensesBean.getRentAmount());
            insertStat.setString(3, expensesBean.getLoanInstallmentAmount());
            insertStat.setString(4, expensesBean.getLeaseAmount());
            insertStat.setString(5, expensesBean.getCreditCardbill());
            insertStat.setString(6, expensesBean.getOtherBorrows());
            insertStat.setString(7, expensesBean.getInsuranceInstallment());
            insertStat.setString(8, expensesBean.getHouseHolderExpenses());
            insertStat.setString(9, expensesBean.getOtherExpenses());

            int resultId1 = insertStat.executeUpdate();

            for (int i = 0; i < incomeList.size(); i++) {

                insertStat = con.prepareStatement("INSERT INTO CARDAPPLICATIONINCOME "
                        + "(APPLICATIONID,INCOMECATEGORYCODE,AMOUNT,LASTUPDATEDUSER ) "
                        + " VALUES (?,?,?,?) ");

                insertStat.setString(1, incomeList.get(i).getApplicationId());
                insertStat.setString(2, incomeList.get(i).getIncomeCatogaryCode());
                insertStat.setString(3, incomeList.get(i).getAmount());
                insertStat.setString(4, expensesBean.getLastUpdateduser());

                int j = 0;
                j = insertStat.executeUpdate();
                if (j == 0) {
                    throw new Exception();
                }

            }
            insertStat = con.prepareStatement("UPDATE CARDAPPLICATION SET NETINCOME=?,NETEXPENSES=?,NETPROFIT=? WHERE APPLICATIONID=?");
            insertStat.setString(1, expensesBean.getNetIncome());
            insertStat.setString(2, expensesBean.getTotal());
            insertStat.setString(3, expensesBean.getNetProfit());
            insertStat.setString(4, expensesBean.getApplicationId());

            int s = insertStat.executeUpdate();

            if (s == 1 && resultId1 == 1) {
                resultId = 1;
            }

        } catch (Exception ex) {
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

    public int insertBankDetailsRecords(Connection con, List<CardBankDetailsBean> accountList) throws Exception {
        
        int resultId = 0;
        if (accountList.size()>0) {
            resultId = 1;
        }
        for (int i = 0; i < accountList.size(); i++) {
            
            PreparedStatement insertStat = null;

            try {

                insertStat = con.prepareStatement("INSERT INTO CARDAPPLICATIONBANKDETAILS "
                        + "(APPLICATIONID,BANKCODE,BRANCHCODE,ACCOUNTTYPE,ACCOUNTNO,ACCOUNTSINCEYEARS,ACCOUNTSINCEMONTHS,ISAUTOSETTLE ) "
                        + " VALUES (?,?,?,?,?,?,?,?) ");

                insertStat.setString(1, accountList.get(i).getApplicationid());
                insertStat.setString(2, accountList.get(i).getBankCode());
                insertStat.setString(3, accountList.get(i).getBranchCode());
                insertStat.setString(4, accountList.get(i).getAccountType());
                insertStat.setString(5, accountList.get(i).getAccountNumber());
                insertStat.setString(6, accountList.get(i).getSinceYear());
                insertStat.setString(7, accountList.get(i).getSinceMonth());
                insertStat.setString(8, "NO");

                int j = 0;
                j = insertStat.executeUpdate();
                if (j == 0) {
                    resultId = 0;
                    throw new Exception();
                }

            } catch (Exception ex) {
                throw ex;
            } finally {
                try {
                    insertStat.close();

                } catch (Exception e) {
                    throw e;
                }

            }
        }
        return resultId;
    }

    public int insertDocumentUploadRecords(Connection con, List<DocumentUploadBean> sessionDocumentList) throws Exception {
        int resultId = 0;

        PreparedStatement insertStat = null;
        try {

            for (int k = 0; k < sessionDocumentList.size(); k++) {

                insertStat = con.prepareStatement("INSERT INTO CARDAPPLICATIONDOCUMENT "
                        + "(APPLICATIONID,VERIFICATIONCATEGORY,DOCUMENTTYPE,FILENAME ) "
                        + " VALUES (?,?,?,?) ");

                insertStat.setString(1, sessionDocumentList.get(k).getApplicationId());
                insertStat.setString(2, sessionDocumentList.get(k).getVerificationCategory());
                insertStat.setString(3, sessionDocumentList.get(k).getDocumentType());
                insertStat.setString(4, sessionDocumentList.get(k).getFileName());

                resultId = insertStat.executeUpdate();
                if (resultId == 0) {
                    break;
                }

            }

        } catch (Exception ex) {
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

    public int insertSignatureRecord(Connection con, String applicationId, String fileName) throws Exception {
        int resultId = 0;

        PreparedStatement insertStat = null;
        try {

            insertStat = con.prepareStatement("UPDATE CARDAPPLICATION SET SIGNATURE=? WHERE APPLICATIONID= ? ");

            insertStat.setString(1, fileName);
            insertStat.setString(2, applicationId);

            resultId = insertStat.executeUpdate();

        } catch (Exception ex) {
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

    //insert recommended details
    public int insertRecommendedDetails(Connection con, String appId, CustomerPersonalBean recBean) throws Exception {
        int resultId = 0;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("UPDATE CARDAPPLICATIONPERSONALDETAILS SET RECOMNAME=?,RECOMCREDITCARD=?,RECOMTELEPHONE=?,RECOMDATE=? WHERE APPLICATIONID=?");
            insertStat.setString(1, recBean.getRecName());
            insertStat.setString(2, recBean.getRecCreditCardnum());
            insertStat.setString(3, recBean.getRecPhone());
            if (recBean.getRecDate() != "") {
                insertStat.setTimestamp(4, new Timestamp(this.convertStringToDate(recBean.getRecDate()).getTime()));
            } else {
                insertStat.setString(4, "");
            }
            insertStat.setString(5, appId);

            resultId = insertStat.executeUpdate();
        } catch (Exception ex) {
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

    public int updateBankDetailsRecords(Connection con, List<CardBankDetailsBean> accountList, String appId) throws Exception {
        int resultId = 1;

        PreparedStatement insertStat = null;
        try {

            insertStat = con.prepareStatement("DELETE FROM CARDAPPLICATIONBANKDETAILS WHERE APPLICATIONID=?");
            insertStat.setString(1, appId);
            insertStat.executeUpdate();

            for (int i = 0; i < accountList.size(); i++) {

                insertStat = con.prepareStatement("INSERT INTO CARDAPPLICATIONBANKDETAILS "
                        + "(APPLICATIONID,BANKCODE,BRANCHCODE,ACCOUNTTYPE,ACCOUNTNO,ACCOUNTSINCEYEARS,ACCOUNTSINCEMONTHS,ISAUTOSETTLE) "
                        + " VALUES (?,?,?,?,?,?,?,?) ");

                insertStat.setString(1, appId);
                insertStat.setString(2, accountList.get(i).getBankCode());
                insertStat.setString(3, accountList.get(i).getBranchCode());
                insertStat.setString(4, accountList.get(i).getAccountType());
                insertStat.setString(5, accountList.get(i).getAccountNumber());
                insertStat.setString(6, accountList.get(i).getSinceYear());
                insertStat.setString(7, accountList.get(i).getSinceMonth());
                insertStat.setString(8, "NO");

                int j = 0;
                j = insertStat.executeUpdate();
                if (j == 0) {
                    resultId = 0;
                    throw new Exception();
                }

            }

        } catch (Exception ex) {
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

    //update automatic settlement bank details
    public int updateAutomaticSettlementBankDetails(Connection con, CardBankDetailsBean autoSettleDetailsBean) throws Exception {
        int resultId = 0;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("UPDATE CARDAPPLICATIONBANKDETAILS SET ISAUTOSETTLE=?, AUTOSETPERCENTAGE=? WHERE APPLICATIONID=? AND ACCOUNTNO=? AND BANKCODE=?");
            ps.setString(1, "YES");
            ps.setString(2, autoSettleDetailsBean.getAutoSettlePerValue());
            ps.setString(3, autoSettleDetailsBean.getApplicationid());
            ps.setString(4, autoSettleDetailsBean.getAutoSettleAccNo());
            ps.setString(5, autoSettleDetailsBean.getConfigBankCode());

            resultId = ps.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                ps.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return resultId;

    }

    public int updateDocumentUploadRecords(Connection con, List<DocumentUploadBean> sessionDocumentList, String id) throws Exception {
        int resultId = 0;

        PreparedStatement insertStat = null;
        try {

            insertStat = con.prepareStatement("DELETE FROM CARDAPPLICATIONDOCUMENT WHERE APPLICATIONID=?");
            insertStat.setString(1, id);
            insertStat.executeUpdate();

            for (int k = 0; k < sessionDocumentList.size(); k++) {

                insertStat = con.prepareStatement("INSERT INTO CARDAPPLICATIONDOCUMENT "
                        + "(APPLICATIONID,VERIFICATIONCATEGORY,DOCUMENTTYPE,FILENAME ) "
                        + " VALUES (?,?,?,?) ");

                insertStat.setString(1, sessionDocumentList.get(k).getApplicationId());
                insertStat.setString(2, sessionDocumentList.get(k).getVerificationCategory());
                insertStat.setString(3, sessionDocumentList.get(k).getDocumentType());
                insertStat.setString(4, sessionDocumentList.get(k).getFileName());

                resultId = insertStat.executeUpdate();
                if (resultId == 0) {
                    break;
                }

            }

        } catch (Exception ex) {
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

    /*
     * update quaries.........................................................................................................................................................
     */
    public int updatePersonalRecord(Connection con, CustomerPersonalBean personalBean) throws Exception {
        int resultId;

        PreparedStatement upsateStat = null;
        try {
            upsateStat = con.prepareStatement("UPDATE CARDAPPLICATIONPERSONALDETAILS SET "
                    + "TITLE=?,NAMEINFULL=?,NOOFDEPENDANCE=?,NAMEONCARD=?,DATEOFBIRTH=?,PLACEOFBIRTH=?,GENDER=?,MARITALSTATUS=?,NATIONALITY=?,NIC=?,PASSPORTNUMBER=?,PASSPORTEXPIREDATE=?,ADDRESS1=?, "
                    + "ADDRESS2=?,ADDRESS3=?,CITY=?,HOMETELEPHONENO=?,MOBILENO=?,OFFICEPHONENO=?,EMAIL=?,RECIDENCETYPE=?,SMSALERTSTATUS=? ,BLOODGROUP=?,MOTHERSMAIDENNAME=?,RELATIVENAME=?,RELATIONSHIP=? "
                    + ",RELADDRESS1=?,RELADDRESS2=?,RELADDRESS3=?,RELCITY=?,RELRESIDANCEPHONE=?,RELMOBILENO=?,RELEMPLOYER=?,RELOFFICEPHONE=?,SPOUSENAME=?,SPOUSENIC=?,SPOUSENAMEOFEMPLOYEE=?,SPOUSECOMPANYADDRESS=?,SPOUSEDESIGNATION=? "
                    + ",SPOUSEPHONE=?,SPOUSEEMAIL=?,SPOUSEMONTHLYINCOME=?,VEHICALTYPE=?,VEHICALNO=?,OWNERSHIP=?,PROFESSIONALQUALIFICATION=?,ACADEMICQUALIFICATION=?,REQUESTCARDTYPE=?,REQUESTCARDPRODUCT=?,REQUESTCREDITLIMIT=?,"
                    + "NAMEWITHINITIAL=?,PERMANENTADDRESS1=?,PERMANENTADDRESS2=?,PERMANANTADDRESS3=?,PERMANAENTCITY=?,BILLINGADDRESS1=?,BILLINGADDRESS2=?,BILLINGADDRESS3=?,BILLINGCITY=?,RELEMAIL=?,DURATIONATTHEABOVEADDRESS=?,CURRENCYTYPE=? WHERE APPLICATIONID=? ");

            upsateStat.setString(1, personalBean.getTitle());
            upsateStat.setString(2, personalBean.getFullName());
            upsateStat.setString(3, personalBean.getNoOfDependens());
            upsateStat.setString(4, personalBean.getNameOncard());
            upsateStat.setString(5, personalBean.getBirthday());
            upsateStat.setString(6, personalBean.getPlaceOfbirth());
            upsateStat.setString(7, personalBean.getGender());
            upsateStat.setString(8, personalBean.getMaritalStatus());
            upsateStat.setString(9, personalBean.getNationality());
            upsateStat.setString(10, personalBean.getNic());
            upsateStat.setString(11, personalBean.getPassportNumber());
            upsateStat.setString(12, personalBean.getPassportExpdate());
            upsateStat.setString(13, personalBean.getAddress1());
            upsateStat.setString(14, personalBean.getAddress2());
            upsateStat.setString(15, personalBean.getAddress3());
            upsateStat.setString(16, personalBean.getCity());
            upsateStat.setString(17, personalBean.getHomeTelNumber());
            upsateStat.setString(18, personalBean.getMobileNumber());
            upsateStat.setString(19, personalBean.getOfficeTelNumber());
            upsateStat.setString(20, personalBean.getEmail());
            upsateStat.setString(21, personalBean.getResidenceType());
            upsateStat.setString(22, personalBean.getSmsAlertStatus());
            upsateStat.setString(23, personalBean.getBloodgroup());
            upsateStat.setString(24, personalBean.getMothersMaidenName());
            upsateStat.setString(25, personalBean.getRelName());
            upsateStat.setString(26, personalBean.getRelationship());
            upsateStat.setString(27, personalBean.getRelAddress1());
            upsateStat.setString(28, personalBean.getRelAddress2());
            upsateStat.setString(29, personalBean.getRelAddress3());
            upsateStat.setString(30, personalBean.getRelCity());
            upsateStat.setString(31, personalBean.getRelResidencePhone());
            upsateStat.setString(32, personalBean.getRelMobile());
            upsateStat.setString(33, personalBean.getRelCompany());
            upsateStat.setString(34, personalBean.getRelOfficeNumber());
            upsateStat.setString(35, personalBean.getSpouseName());
            upsateStat.setString(36, personalBean.getSpouseNic());
            upsateStat.setString(37, personalBean.getSpouseNameofEmployee());
            upsateStat.setString(38, personalBean.getSpousecompanyAddress());
            upsateStat.setString(39, personalBean.getSpouseDesignation());
            upsateStat.setString(40, personalBean.getSpousePhone());
            upsateStat.setString(41, personalBean.getSpouseMail());
            upsateStat.setString(42, personalBean.getSpouseMonthlyIncome());
            upsateStat.setString(43, personalBean.getVehicalType());
            upsateStat.setString(44, personalBean.getVehicalNo());
            upsateStat.setString(45, personalBean.getOwnership());
            upsateStat.setString(46, personalBean.getProfessionalQualifications());
            upsateStat.setString(47, personalBean.getAcedemicQualifications());
            upsateStat.setString(48, personalBean.getCardType());
            upsateStat.setString(49, personalBean.getCardProduct());
            upsateStat.setString(50, personalBean.getCreditLimit());
            upsateStat.setString(51, personalBean.getNameWithInitials());
            upsateStat.setString(52, personalBean.getPermentAddress1());
            upsateStat.setString(53, personalBean.getPermentAddress2());
            upsateStat.setString(54, personalBean.getPermentAddress3());
            upsateStat.setString(55, personalBean.getPermentCity());
            upsateStat.setString(56, personalBean.getBillAddress1());
            upsateStat.setString(57, personalBean.getBillAddress2());
            upsateStat.setString(58, personalBean.getBillAddress3());
            upsateStat.setString(59, personalBean.getBillCity());
            upsateStat.setString(60, personalBean.getRelEmail());
            upsateStat.setString(61, personalBean.getDurationofTheAddress());
            upsateStat.setString(62, personalBean.getCardCurrency());
            upsateStat.setString(63, personalBean.getApplicationId());

            resultId = upsateStat.executeUpdate();

        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                upsateStat.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;
    }

    public int updateSuplimentoryPersonalRecord(Connection con, SupplementaryApplicationBean personalBean) throws Exception {
        int resultId;

        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("UPDATE SUPPLEMENTARYAPPLICATION SET "
                    + "TITLE=?,FIRSTNAME=?,LASTNAME=?,MIDDLENAME=?,NAMEONCARD=?,DATEOFBIRTH=?,GENDER=?,NATIONALITY=?,NIC=?,PASSPORTNUMBER=?,PASSPORTEXPIREDDATE=?,ADDRESS1=?, "
                    + "ADDRESS2=?,ADDRESS3=?,CITY=?,PRIMARYCARDNUMBER=?,PRIMARYID=?,HOMETELEPHONE=?,MOBILE=?,ADDRESSSAMEASPRIMARY=?,OCCUPATION=?,EMPLOYMENTTYPE=?,REQUESTCARDTYPE=?,REQUESTCARDPRODUCT=?,"
                    + "REQUESTCREDITLIMIT=?,NAMEWITHINITIAL=?,BILLINGADDRESS1=?,BILLINGADDRESS2=?,BILLINGADDRESS3=?,BILLINGCITY=?,PRIMARYAPPLICATIONID=?,RELATIONSHIP=?,PERCENTAGECREDITLIMIT=?,CLPERORFIXED=?,PRIMARYITTYPE=? WHERE APPLICATIONID=?");

            insertStat.setString(1, personalBean.getTitle());
            insertStat.setString(2, personalBean.getFirstName());
            insertStat.setString(3, personalBean.getLastName());
            insertStat.setString(4, personalBean.getMiddleName());
            insertStat.setString(5, personalBean.getNameOncard());
            insertStat.setString(6, personalBean.getBirthday());
            insertStat.setString(7, personalBean.getGender());
            insertStat.setString(8, personalBean.getNationality());
            insertStat.setString(9, personalBean.getNic());
            insertStat.setString(10, personalBean.getPassportNumber());
            insertStat.setString(11, personalBean.getPassportExpdate());
            insertStat.setString(12, personalBean.getAddress1());
            insertStat.setString(13, personalBean.getAddress2());
            insertStat.setString(14, personalBean.getAddress3());
            insertStat.setString(15, personalBean.getCity());
            insertStat.setString(16, personalBean.getPrimaryCardNumber());
            insertStat.setString(17, personalBean.getPrimaryId());
            insertStat.setString(18, personalBean.getHomeTelNumber());
            insertStat.setString(19, personalBean.getMobileNumber());
            insertStat.setString(20, "NO");
            insertStat.setString(21, personalBean.getOccupation());
            insertStat.setString(22, personalBean.getEmployementType());
            insertStat.setString(23, personalBean.getCardType());
            insertStat.setString(24, personalBean.getCardProduct());
            insertStat.setString(25, personalBean.getCreditLimit());
            insertStat.setString(26, personalBean.getNameWithinitials());
            insertStat.setString(27, personalBean.getBillingAdress1());
            insertStat.setString(28, personalBean.getBillingAdress2());
            insertStat.setString(29, personalBean.getBillingAdress3());
            insertStat.setString(30, personalBean.getBillingCity());
            insertStat.setString(31, personalBean.getPrimaryCardApplicationId());
            insertStat.setString(32, personalBean.getRelationShip());
            insertStat.setString(33, personalBean.getPercentageValue());
            insertStat.setString(34, personalBean.getClimitReqType());
            insertStat.setString(35, personalBean.getPrimaryIdType());
            insertStat.setString(36, personalBean.getApplicationId());

            resultId = insertStat.executeUpdate();

        } catch (Exception ex) {
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

    public int UpdateEmploymentRecord(Connection con, CustomerEmploymentBean employmentBean) throws Exception {
        int resultId = 0;

        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE CARDAPPLICATIONEMPDETAILS SET "
                    + " EMPLOYMENTSTATUS=?,OFFICETELEPHONE=?,OCCUPATION=?,DESIGNATION=?,NATUREOFBUSINESS=?,NOOFEMPLOYEES=?,COMPANYNAME=?,ANNUALTURNOVER=?,NETPROFIT=?,EMPLOYMENTTYPE=?,OFFICEADDRESS1=?,OFFICEADDRESS2=?,OFFICEADDRESS3=?,OTHEROCCUPATION=?,SELFEMPLOYEECOMPANYNAME=?,SELFEMPLOYEENOOFEMPLOYEES=?,OTHEREMPLOYMENTTYPE=?,OTHERNATUREOFBUSINESS=?,SERVICEYEARS=?,SERVICEMONTH=?,STAFFSTATUS=?,"
                    + " COMPANYAGE=?,EMPLOYMENTDURATIONYEARS=?,EMPLOYMENTDURATIONMONTHS=?,PREEMPLOYERNAME=?,PREEMPLOYERADDRESS1=?,PREEMPLOYERADDRESS2=?,PREEMPLOYERADDRESS3=?,PREMPLOYMENTDURATIONYEARS=?,PREMPLOYMENTDURATIONMONTHS=? WHERE APPLICATIONID=? ");

            updateStat.setString(1, employmentBean.getEmploymentStatus());
            updateStat.setString(2, employmentBean.getOfficePhone());
            updateStat.setString(3, employmentBean.getOccupation());
            updateStat.setString(4, employmentBean.getDesignation());
            updateStat.setString(5, employmentBean.getBusinessNature());
            updateStat.setString(6, employmentBean.getNumberOfEmployees());
            updateStat.setString(7, employmentBean.getCompanyName());
            updateStat.setString(8, employmentBean.getAnnualTurnOver());
            updateStat.setString(9, employmentBean.getNetProfit());
            updateStat.setString(10, employmentBean.getEmploymentType());
            updateStat.setString(11, employmentBean.getAdress1());
            updateStat.setString(12, employmentBean.getAdress2());
            updateStat.setString(13, employmentBean.getAdress3());
            updateStat.setString(14, employmentBean.getOtherOccupation());
            updateStat.setString(15, employmentBean.getSelfEmpCompanyname());
            updateStat.setString(16, employmentBean.getSelfEmpNoOfEmployee());
            updateStat.setString(17, employmentBean.getOtherEmploymentType());
            updateStat.setString(18, employmentBean.getOtherBusinessNature());
            updateStat.setInt(19, Integer.parseInt(employmentBean.getServiceYear()));
            updateStat.setInt(20, Integer.parseInt(employmentBean.getServiceMonth()));
            updateStat.setString(21, employmentBean.getStaffStatus());
            //new by chinthaka
            if (employmentBean.getAgeOfCompany().isEmpty()) {
                updateStat.setString(22, employmentBean.getAgeOfCompany());
            } else {
                updateStat.setInt(22, Integer.parseInt(employmentBean.getAgeOfCompany()));
            }
            updateStat.setInt(23, Integer.parseInt(employmentBean.getEmploymentDurationInYears()));
            updateStat.setInt(24, Integer.parseInt(employmentBean.getEmploymentDurationInMonths()));
            updateStat.setString(25, employmentBean.getpEmployerName());
            updateStat.setString(26, employmentBean.getpEmployerAddress1());
            updateStat.setString(27, employmentBean.getpEmployerAddress2());
            updateStat.setString(28, employmentBean.getpEmployerAddress3());
            updateStat.setInt(29, Integer.parseInt(employmentBean.getpEmploymentDurationInYears()));
            updateStat.setInt(30, Integer.parseInt(employmentBean.getpEmploymentDurationInMonths()));

            updateStat.setString(31, employmentBean.getApplicationId());

            int resultId1 = updateStat.executeUpdate();

            updateStat = con.prepareStatement("UPDATE CARDAPPLICATION SET STAFFSTATUS=? WHERE APPLICATIONID=?");
            updateStat.setString(1, employmentBean.getStaffStatus());
            updateStat.setString(2, employmentBean.getApplicationId());

            int resultId2 = updateStat.executeUpdate();

            if (resultId1 == 1 && resultId2 == 1) {
                resultId = 1;
            } else {
                throw new Exception();
            }

        } catch (Exception ex) {
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

    public int updateIncomeAndExpensesRecord(Connection con, CardExpensesBean expensesBean, List<CardIncomeBean> incomeList) throws Exception {
        int resultId = 0;
        int resultId1;

        PreparedStatement insertStat = null;
        PreparedStatement deleteStat = null;
        try {
            insertStat = con.prepareStatement("UPDATE CARDAPPLICATIONEXPENSES SET "
                    + "RENTAMOUNT=?,LOANINSTALLMENTAMOUNT=?,LEASEAMOUNT=?,CREDITCARDSBILL=?,OTHERBORROWS=?,INSURANCEINSTALLMENT=?,HOUSEHOLDEREXPENSES=?,OTHEREXPENSES=? WHERE APPLICATIONID=? ");

            insertStat.setString(1, expensesBean.getRentAmount());
            insertStat.setString(2, expensesBean.getLoanInstallmentAmount());
            insertStat.setString(3, expensesBean.getLeaseAmount());
            insertStat.setString(4, expensesBean.getCreditCardbill());
            insertStat.setString(5, expensesBean.getOtherBorrows());
            insertStat.setString(6, expensesBean.getInsuranceInstallment());
            insertStat.setString(7, expensesBean.getHouseHolderExpenses());
            insertStat.setString(8, expensesBean.getOtherExpenses());
            insertStat.setString(9, expensesBean.getApplicationId());

            resultId1 = insertStat.executeUpdate();

            deleteStat = con.prepareStatement("DELETE FROM CARDAPPLICATIONINCOME WHERE APPLICATIONID=?");
            deleteStat.setString(1, expensesBean.getApplicationId());
            deleteStat.executeUpdate();

            for (int i = 0; i < incomeList.size(); i++) {

                insertStat = con.prepareStatement("INSERT INTO CARDAPPLICATIONINCOME "
                        + "(APPLICATIONID,INCOMECATEGORYCODE,AMOUNT,LASTUPDATEDUSER ) "
                        + " VALUES (?,?,?,?) ");

                insertStat.setString(1, expensesBean.getApplicationId());
                insertStat.setString(2, incomeList.get(i).getIncomeCatogaryCode());
                insertStat.setString(3, incomeList.get(i).getAmount());
                insertStat.setString(4, expensesBean.getLastUpdateduser());

                int j = 0;
                j = insertStat.executeUpdate();
                if (j == 0) {
                    throw new Exception();
                }

            }

            insertStat = con.prepareStatement("UPDATE CARDAPPLICATION SET NETINCOME=?,NETEXPENSES=?,NETPROFIT=? WHERE APPLICATIONID=?");
            insertStat.setString(1, expensesBean.getNetIncome());
            insertStat.setString(2, expensesBean.getTotal());
            insertStat.setString(3, expensesBean.getNetProfit());
            insertStat.setString(4, expensesBean.getApplicationId());

            int s = insertStat.executeUpdate();

            if (s == 1 && resultId1 == 1) {
                resultId = 1;
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                insertStat.close();
                deleteStat.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;
    }

    public boolean updateStatusOfCardApplicationStatus(String applicationId, String columnName, Connection con) throws Exception {

        PreparedStatement stmt = null;		// Holds the statement to be executed
        try {

            stmt = con.prepareStatement("UPDATE CARDAPPLICATIONSTATUS SET " + columnName + "=?  WHERE APPLICATIONID= " + applicationId);
            stmt.setString(1, "1");
            stmt.executeUpdate();

        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                stmt.close();

            } catch (Exception e) {
            }
        }
        return true;
    }

    public boolean updateCardApplicationStatus(String applicationId, String value, Connection con) throws Exception {

        PreparedStatement stmt = null;		// Holds the statement to be executed
        try {

            stmt = con.prepareStatement("UPDATE CARDAPPLICATIONSTATUS SET APPLICATIONSTATUS =?  WHERE APPLICATIONID= " + applicationId);
            stmt.setString(1, value);
            stmt.executeUpdate();

        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                stmt.close();

            } catch (Exception e) {
            }
        }
        return true;
    }

    public boolean updatePrimeryCardApplicationDocument(Connection CMSCon, DebitPersonalBean personalBean) throws Exception {

        PreparedStatement stmt = null;
        try {

            stmt = CMSCon.prepareStatement("UPDATE CARDAPPLICATION SET PRIMARYSIGNATURE = ? WHERE APPLICATIONID = ?");

            stmt.setString(1, personalBean.getSignatureFileName());
            stmt.setString(2, personalBean.getApplicationId());
            stmt.executeUpdate();

        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                stmt.close();

            } catch (Exception e) {
                throw e;
            }
        }
        return true;
    }

    public boolean updateUploadedNic(Connection CMSCon, DebitPersonalBean personalBean, String lastUpdatedUser) throws Exception {

        PreparedStatement stmt = null;
        try {

            stmt = CMSCon.prepareStatement("INSERT INTO CARDAPPLICATIONDOCUMENT "
                    + " (APPLICATIONID,VERIFICATIONCATEGORY,DOCUMENTTYPE,"
                    + " FILENAME,LASTUPDATEDUSER,LASTUPDATEDTIME,CREATETIME)"
                    + " VALUES(?,?,?,?,?,SYSDATE,SYSDATE)");

            stmt.setString(1, personalBean.getApplicationId());
            stmt.setString(2, StatusVarList.ID_VERIFICATION_CATEGORY);
            stmt.setString(3, StatusVarList.NATIONAL_IDENTITY_CARD);
            stmt.setString(4, personalBean.getNicFileName());
            stmt.setString(5, lastUpdatedUser);

            stmt.executeUpdate();

        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                stmt.close();

            } catch (Exception e) {
                throw e;
            }
        }
        return true;
    }

    public boolean updateSecondryCardApplicationDocument(Connection CMSCon, DebitPersonalBean personalBean) throws Exception {

        PreparedStatement stmt = null;
        try {

            stmt = CMSCon.prepareStatement("UPDATE CARDAPPLICATION SET JOINTSIGNATURE = ? WHERE APPLICATIONID = ?");

            stmt.setString(1, personalBean.getSignatureFileName());
            stmt.setString(2, personalBean.getApplicationId());
            stmt.executeUpdate();

        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                stmt.close();

            } catch (Exception e) {
                throw e;
            }
        }
        return true;
    }

    public List<ApplicationAssignBean> userAssignApplicationsSearch(Connection con, SearchUserAssignAppBean searchBean) throws Exception {

        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query

        try {

            strSqlBasic = " SELECT DISTINCT a.APPLICATIONID,ad.DOCUMENTNAME,  a.IDENTIFICATIONTYPE ,  a.IDENTIFICATIONNO,  a.PRIORITYLEVELCODE, "
                    + " cc.DESCRIPTION AS CARDCATEGORY, a.CARDCATEGORY as CARDCATEGORYCODE,  pl.DESCRIPTION AS PRIORITYNAME,  a.REFERANCIALEMPNO,  a.BRANCHCODE, "
                    + " bk.DESCRPTION AS BRANCHNAME   ,  a.ASSIGNUSER, a.ASSIGNSTATUS,  st.DESCRIPTION , "
                    + " a.LASTUPDATEDUSER ,  a.LASTUPDATEDTIME  ,  a.CREATETIME  FROM CARDAPPLICATION a ,cardapplicationstatus cs,"
                    + "STATUS st ,PRIORITYLEVEL pl  ,BANKBRANCH bk ,CARDCATEGORY cc,APPLICATIONDOCUMENT ad ";

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
            if (!searchBean.getCardcategory().contentEquals("") || searchBean.getCardcategory().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.CARDCATEGORY = '" + searchBean.getCardcategory() + "'";
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

                strSqlBasic += " WHERE a.applicationid = cs.applicationid  AND a.IDENTIFICATIONTYPE=ad.DOCUMENTTYPECODE AND a.CARDCATEGORY=ad.CARDCATEGORYCODE AND cs.applicationstatus = st.statuscode AND a.PRIORITYLEVELCODE = pl.PRIORITYLEVELCODE AND a.BRANCHCODE = bk.BRANCHCODE(+) AND (bk.bankcode = (SELECT BANKCODE FROM COMMONPARAMETER) OR a.branchcode is null) AND a.CARDCATEGORY= cc.CATEGORYCODE AND (cs.applicationstatus ='INIT' OR cs.applicationstatus ='DPRC') AND a.CARDAPPLIACTIONDOMAIN='CREDIT' AND " + condition + " ORDER BY a.APPLICATIONID ";
            } else {
                strSqlBasic += " WHERE a.applicationid = cs.applicationid  AND a.IDENTIFICATIONTYPE=ad.DOCUMENTTYPECODE AND a.CARDCATEGORY=ad.CARDCATEGORYCODE AND cs.applicationstatus = st.statuscode AND a.PRIORITYLEVELCODE = pl.PRIORITYLEVELCODE AND a.BRANCHCODE = bk.BRANCHCODE(+) AND (bk.bankcode = (SELECT BANKCODE FROM COMMONPARAMETER) OR a.branchcode is null) AND a.CARDCATEGORY = cc.CATEGORYCODE AND (cs.applicationstatus ='INIT' OR cs.applicationstatus ='DPRC') AND a.CARDAPPLIACTIONDOMAIN='CREDIT' ORDER BY a.APPLICATIONID ";
            }

            stmt = con.prepareStatement(strSqlBasic);
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
                resultAssign.setCardCategory(rs.getString("CARDCATEGORY"));
                resultAssign.setPriorityDescription(rs.getString("PRIORITYNAME"));
                resultAssign.setReferralBranchId(rs.getString("BRANCHCODE"));
                resultAssign.setReferralBranchName(rs.getString("BRANCHNAME"));
                resultAssign.setReferralEmpNo(rs.getString("REFERANCIALEMPNO"));
                resultAssign.setAssignStatus(rs.getString("DESCRIPTION"));
                resultAssign.setCardCategoryCode(rs.getString("CARDCATEGORYCODE"));
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

    public List<ApplicationAssignBean> userModifyApplicationsSearch(Connection con, SearchUserAssignAppBean searchBean) throws Exception {

        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query

        try {

            strSqlBasic = "SELECT app.APPLICATIONID,app.CARDCATEGORY,cc.DESCRIPTION as CARDCATEGORYDESCRIPTION,cs.APPLICATIONSTATUS,app.REFERANCIALEMPNO,st.DESCRIPTION,app.ASSIGNUSER, pl.DESCRIPTION as PRIORITYNAME,"
                    + " bb.DESCRPTION as BRANCHNAME,app.LASTUPDATEDUSER,app.LASTUPDATEDTIME,app.CREATETIME as branch "
                    + "FROM CARDAPPLICATION app,CARDAPPLICATIONSTATUS cs, STATUS st,PRIORITYLEVEL pl, BANKBRANCH bb, CARDCATEGORY cc "
                    + "WHERE app.APPLICATIONID= cs.APPLICATIONID AND (cs.APPLICATIONSTATUS='CHIN' OR cs.APPLICATIONSTATUS='VONH') AND st.STATUSCODE= cs.APPLICATIONSTATUS AND "
                    + "app.PRIORITYLEVELCODE= pl.PRIORITYLEVELCODE AND app.BRANCHCODE= bb.BRANCHCODE(+) AND (bb.bankcode = (SELECT BANKCODE FROM COMMONPARAMETER) OR app.branchcode is null) AND app.CARDAPPLIACTIONDOMAIN='CREDIT' "
                    + "AND cc.CATEGORYCODE=app.CARDCATEGORY ";

            String condition = "";

            if (!searchBean.getApplicationId().contentEquals("") || searchBean.getApplicationId().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "app.APPLICATIONID = '" + searchBean.getApplicationId() + "'";
            }

            if (!searchBean.getPriorityLevel().contentEquals("") || searchBean.getPriorityLevel().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "app.PRIORITYLEVELCODE = '" + searchBean.getPriorityLevel() + "'";
            }

            if (!searchBean.getAssignUser().contentEquals("") || searchBean.getAssignUser().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "app.ASSIGNUSER = '" + searchBean.getAssignUser() + "'";
            }

            if (!searchBean.getFromDate().contentEquals("") && !searchBean.getToDate().contentEquals("")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "app.CREATETIME >= to_Date('" + this.stringTodate(searchBean.getFromDate()) + "','yy-mm-dd') AND app.CREATETIME <= to_Date('" + this.stringTodate(searchBean.getToDate()) + "','yy-mm-dd')";
            } else {

                if (!searchBean.getFromDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "app.CREATETIME >= to_Date('" + this.stringTodate(searchBean.getFromDate()) + "','yy-mm-dd')";
                }
                if (!searchBean.getToDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "app.CREATETIME <= to_Date('" + this.stringTodate(searchBean.getToDate()) + "','yy-mm-dd')";
                }
            }

            if (!condition.equals("")) {
                strSqlBasic += "  AND " + condition + " ORDER BY app.APPLICATIONID";
            } else {
                strSqlBasic += " ORDER BY app.APPLICATIONID";
            }

            stmt = con.prepareStatement(strSqlBasic);
            rs = stmt.executeQuery();

            searchAssignAppList = new ArrayList<ApplicationAssignBean>();

            while (rs.next()) {

                ApplicationAssignBean resultAssign = new ApplicationAssignBean();

                resultAssign.setApplicationId(rs.getString("APPLICATIONID"));
                resultAssign.setAssignUser(rs.getString("ASSIGNUSER"));
//                resultAssign.setCreatedTime(rs.getTimestamp("CREATETIME"));
//                resultAssign.setIdentityNo(rs.getString("IDENTIFICATIONNO"));
//                resultAssign.setIdentityType(rs.getString("IDENTIFICATIONTYPE"));
                resultAssign.setLastUpdatedTime(rs.getTimestamp("LASTUPDATEDTIME"));
                resultAssign.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
//                resultAssign.setPriorityLevel(rs.getString("PRIORITYLEVELCODE"));
                resultAssign.setPriorityDescription(rs.getString("PRIORITYNAME"));
//                resultAssign.setReferralBranchId(rs.getString("BRANCHCODE"));
                resultAssign.setReferralBranchName(rs.getString("BRANCHNAME"));
                resultAssign.setReferralEmpNo(rs.getString("REFERANCIALEMPNO"));
                resultAssign.setAssignStatus(rs.getString("DESCRIPTION"));
                resultAssign.setCardCategoryCode(rs.getString("CARDCATEGORY"));
                resultAssign.setCardCategoryDes(rs.getString("CARDCATEGORYDESCRIPTION"));

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

    public CustomerPersonalBean getAllDetailsCustomer(Connection con, String applicationId) throws Exception {

        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query

        try {

            strSqlBasic = "SELECT cd.* FROM CARDAPPLICATIONPERSONALDETAILS cd WHERE cd.APPLICATIONID=?";

            stmt = con.prepareStatement(strSqlBasic);
            stmt.setString(1, applicationId);

            rs = stmt.executeQuery();

            while (rs.next()) {
                customerPersonalBean = new CustomerPersonalBean();

                CustomerPersonalBean resultCustomer = new CustomerPersonalBean();

                resultCustomer.setApplicationId(rs.getString("APPLICATIONID"));
                resultCustomer.setTitle(rs.getString("TITLE"));
                resultCustomer.setFirstName(rs.getString("FIRSTNAME"));
                resultCustomer.setLastName(rs.getString("LASTNAME"));
                resultCustomer.setMiddleName(rs.getString("MIDDLENAME"));
                resultCustomer.setNameOncard(rs.getString("NAMEONCARD"));
                resultCustomer.setBirthday(rs.getString("DATEOFBIRTH"));
                resultCustomer.setPlaceOfbirth(rs.getString("PLACEOFBIRTH"));
                resultCustomer.setGender(rs.getString("GENDER"));
                resultCustomer.setMaritalStatus(rs.getString("MARITALSTATUS"));
                resultCustomer.setNationality(rs.getString("NATIONALITY"));
                resultCustomer.setNic(rs.getString("NIC"));
                resultCustomer.setPassportNumber(rs.getString("PASSPORTNUMBER"));
                resultCustomer.setPassportExpdate(rs.getString("PASSPORTEXPIREDATE"));
                resultCustomer.setAddress1(rs.getString("ADDRESS1"));
                resultCustomer.setAddress2(rs.getString("ADDRESS2"));
                resultCustomer.setAddress3(rs.getString("ADDRESS3"));
                resultCustomer.setCity(rs.getString("CITY"));
                resultCustomer.setHomeTelNumber(rs.getString("HOMETELEPHONENO"));
                resultCustomer.setMobileNumber(rs.getString("MOBILENO"));
                resultCustomer.setOfficeTelNumber(rs.getString("OFFICEPHONENO"));
                resultCustomer.setEmail(rs.getString("EMAIL"));
                resultCustomer.setResidenceType(rs.getString("RECIDENCETYPE"));
                //resultCustomer.setReligion(rs.getString("RELIGION"));
                resultCustomer.setBloodgroup(rs.getString("BLOODGROUP"));
                resultCustomer.setMothersMaidenName(rs.getString("MOTHERSMAIDENNAME"));
                resultCustomer.setRelName(rs.getString("RELATIVENAME"));
                resultCustomer.setRelationship(rs.getString("RELATIONSHIP"));
                resultCustomer.setRelAddress1(rs.getString("RELADDRESS1"));
                resultCustomer.setRelAddress2(rs.getString("RELADDRESS2"));
                resultCustomer.setRelAddress3(rs.getString("RELADDRESS3"));
                resultCustomer.setRelCity(rs.getString("RELCITY"));
                resultCustomer.setRelResidencePhone(rs.getString("RELRESIDANCEPHONE"));
                resultCustomer.setRelMobile(rs.getString("RELMOBILENO"));
                resultCustomer.setRelCompany(rs.getString("RELEMPLOYER"));
                resultCustomer.setRelOfficeNumber(rs.getString("RELOFFICEPHONE"));
                resultCustomer.setRelEmail(rs.getString("RELEMAIL"));
                resultCustomer.setSpouseName(rs.getString("SPOUSENAME"));
                resultCustomer.setSpouseNic(rs.getString("SPOUSENIC"));
                resultCustomer.setSpouseNameofEmployee(rs.getString("SPOUSENAMEOFEMPLOYEE"));
                resultCustomer.setSpousecompanyAddress(rs.getString("SPOUSECOMPANYADDRESS"));
                resultCustomer.setSpouseDesignation(rs.getString("SPOUSEDESIGNATION"));
                resultCustomer.setSpousePhone(rs.getString("SPOUSEPHONE"));
                resultCustomer.setSpouseMail(rs.getString("SPOUSEEMAIL"));
                resultCustomer.setSpouseMonthlyIncome(rs.getString("SPOUSEMONTHLYINCOME"));
                resultCustomer.setVehicalType(rs.getString("VEHICALTYPE"));
                resultCustomer.setVehicalNo(rs.getString("VEHICALNO"));
                resultCustomer.setOwnership(rs.getString("OWNERSHIP"));
                resultCustomer.setProfessionalQualifications(rs.getString("PROFESSIONALQUALIFICATION"));
                resultCustomer.setAcedemicQualifications(rs.getString("ACADEMICQUALIFICATION"));
                resultCustomer.setNameWithInitials(rs.getString("NAMEWITHINITIAL"));
                resultCustomer.setPermentAddress1(rs.getString("PERMANENTADDRESS1"));
                resultCustomer.setPermentAddress2(rs.getString("PERMANENTADDRESS2"));
                resultCustomer.setPermentAddress3(rs.getString("PERMANANTADDRESS3"));
                resultCustomer.setPermentCity(rs.getString("PERMANAENTCITY"));
                resultCustomer.setBillAddress1(rs.getString("BILLINGADDRESS1"));
                resultCustomer.setBillAddress2(rs.getString("BILLINGADDRESS2"));
                resultCustomer.setBillAddress3(rs.getString("BILLINGADDRESS3"));
                resultCustomer.setBillCity(rs.getString("BILLINGCITY"));
                resultCustomer.setCardType(rs.getString("REQUESTCARDTYPE"));
                resultCustomer.setCardProduct(rs.getString("REQUESTCARDPRODUCT"));
                resultCustomer.setCreditLimit(rs.getString("REQUESTCREDITLIMIT"));
                resultCustomer.setCardCurrency(rs.getString("CURRENCYTYPE"));
                resultCustomer.setDurationofTheAddress(rs.getString("DURATIONATTHEABOVEADDRESS"));
                //by chinthaka
                resultCustomer.setFullName(rs.getString("NAMEINFULL"));
                resultCustomer.setNoOfDependens(rs.getString("NOOFDEPENDANCE"));
                resultCustomer.setPassportExpdate(rs.getString("PASSPORTEXPIREDATE"));
                resultCustomer.setSmsAlertStatus(rs.getString("SMSALERTSTATUS"));
                resultCustomer.setDesignation(rs.getString("DESIGNATION"));
                resultCustomer.setBusinessRegNumber(rs.getString("BUSINESSREGNUMBER"));

                //get recommendation details
                resultCustomer.setRecName(rs.getString("RECOMNAME"));
                resultCustomer.setRecCreditCardnum(rs.getString("RECOMCREDITCARD"));
                resultCustomer.setRecPhone(rs.getString("RECOMTELEPHONE"));
                resultCustomer.setRecDate(rs.getString("RECOMDATE"));

//                if (customerPersonalBean.getNic() != null) {
//                    customerPersonalBean.setIdentificationNumber(customerPersonalBean.getNic());
//                }
//                if (customerPersonalBean.getPassportNumber() != null) {
//                    customerPersonalBean.setIdentificationNumber(customerPersonalBean.getPassportNumber());
//                }
                IdBean bean = new IdBean();
                bean = this.getIdentifyDetails(con, applicationId);
                resultCustomer.setIdentificationNumber(bean.getIdNumber());
                resultCustomer.setIdentificationType(bean.getIdType());

                if (resultCustomer.getPermentCity() != null) {
                    String result = this.getDistrictAndProvince(con, resultCustomer.getPermentCity());
                    String[] array = result.split(",");
                    resultCustomer.setDistrict(array[0]);
                    resultCustomer.setProvince(array[1]);

                }
                if (resultCustomer.getCity() != null) {
                    String result = this.getDistrictAndProvince(con, resultCustomer.getCity());
                    String[] array = result.split(",");
                    resultCustomer.setResDistrict(array[0]);
                    resultCustomer.setResProvince(array[1]);
                }
                if (resultCustomer.getBillCity() != null) {
                    String result = this.getDistrictAndProvince(con, resultCustomer.getBillCity());
                    String[] array = result.split(",");
                    resultCustomer.setBillDistrict(array[0]);
                    resultCustomer.setBillProvince(array[1]);
                }

                customerPersonalBean = resultCustomer;

            }

        } catch (SQLException exSql) {
            throw exSql;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }

        return customerPersonalBean;
    }

    public SupplementaryApplicationBean getAllDetailsSuplimentoryCustomer(Connection con, String applicationId) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query

        try {

            strSqlBasic = "SELECT cd.* FROM SUPPLEMENTARYAPPLICATION cd WHERE cd.APPLICATIONID=?";

            stmt = con.prepareStatement(strSqlBasic);
            stmt.setString(1, applicationId);

            rs = stmt.executeQuery();

            while (rs.next()) {
                customerSuplimentoryPersonalBean = new SupplementaryApplicationBean();

                SupplementaryApplicationBean resultCustomer = new SupplementaryApplicationBean();

                resultCustomer.setApplicationId(rs.getString("APPLICATIONID"));
                resultCustomer.setTitle(rs.getString("TITLE"));
                resultCustomer.setFirstName(rs.getString("FIRSTNAME"));
                resultCustomer.setLastName(rs.getString("LASTNAME"));
                resultCustomer.setMiddleName(rs.getString("MIDDLENAME"));
                resultCustomer.setBirthday(rs.getString("DATEOFBIRTH"));
                resultCustomer.setGender(rs.getString("GENDER"));
                resultCustomer.setNic(rs.getString("NIC"));
                resultCustomer.setPassportNumber(rs.getString("PASSPORTNUMBER"));
                resultCustomer.setNationality(rs.getString("NATIONALITY"));
                resultCustomer.setRelationShip(rs.getString("RELATIONSHIP"));
                resultCustomer.setNameOncard(rs.getString("NAMEONCARD"));
                resultCustomer.setAddress1(rs.getString("ADDRESS1"));
                resultCustomer.setAddress2(rs.getString("ADDRESS2"));
                resultCustomer.setAddress3(rs.getString("ADDRESS3"));
                resultCustomer.setCity(rs.getString("CITY"));
                resultCustomer.setPostalcode(rs.getString("POSTALCODE"));
                resultCustomer.setPrimaryCardNumber(rs.getString("PRIMARYCARDNUMBER"));
                //resultCustomer.setp(rs.getString("PRIMARYITTYPE"));
                resultCustomer.setPrimaryId(rs.getString("PRIMARYID"));
                resultCustomer.setHomeTelNumber(rs.getString("HOMETELEPHONE"));
                resultCustomer.setMobileNumber(rs.getString("MOBILE"));
                resultCustomer.setAdressSameAsPrimary(rs.getString("ADDRESSSAMEASPRIMARY"));
                resultCustomer.setOccupation(rs.getString("OCCUPATION"));
                resultCustomer.setEmployementType(rs.getString("EMPLOYMENTTYPE"));
                resultCustomer.setCardType(rs.getString("REQUESTCARDTYPE"));
                resultCustomer.setCardProduct(rs.getString("REQUESTCARDPRODUCT"));
                resultCustomer.setCreditLimit(rs.getString("REQUESTCREDITLIMIT"));
                resultCustomer.setNameWithinitials(rs.getString("NAMEWITHINITIAL"));
                //resultCustomer.set(rs.getString("STATEMENTSTATUS"));
                resultCustomer.setBillingAdress1(rs.getString("BILLINGADDRESS1"));
                resultCustomer.setBillingAdress2(rs.getString("BILLINGADDRESS2"));
                resultCustomer.setBillingAdress3(rs.getString("BILLINGADDRESS3"));
                resultCustomer.setBillingCity(rs.getString("BILLINGCITY"));
                resultCustomer.setPrimaryCardApplicationId(rs.getString("PRIMARYAPPLICATIONID"));

                resultCustomer.setClimitReqType(rs.getString("CLPERORFIXED"));
                resultCustomer.setPercentageValue(rs.getString("PERCENTAGECREDITLIMIT"));

                if (resultCustomer.getNic() != null) {
                    resultCustomer.setIdentificationNumber(resultCustomer.getNic());
                }
                if (resultCustomer.getPassportNumber() != null) {
                    resultCustomer.setIdentificationNumber(resultCustomer.getPassportNumber());
                }

                if (resultCustomer.getCity() != null) {
                    String result = this.getDistrictAndProvince(con, resultCustomer.getCity());
                    String[] array = result.split(",");
                    resultCustomer.setResDistrict(array[0]);
                    resultCustomer.setResProvince(array[1]);
                }
                if (resultCustomer.getBillingCity() != null) {
                    String result = this.getDistrictAndProvince(con, resultCustomer.getBillingCity());
                    String[] array = result.split(",");
                    resultCustomer.setBillDistrict(array[0]);
                    resultCustomer.setBillProvince(array[1]);
                }

                customerSuplimentoryPersonalBean = resultCustomer;

            }

        } catch (SQLException exSql) {
            throw exSql;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }

        return customerSuplimentoryPersonalBean;
    }

    //convert string to Date type
    private String stringTodate(String date) throws ParseException {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        Date convertedDate = dateFormat.parse(dateString);

        return (dateFormat.format(convertedDate));

    }

    //convert string to date
    public Date convertStringToDate(String date) throws ParseException {
        String dateString = date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = dateFormat.parse(dateString);
        return convertedDate;
    }

    public List<EmploymentTypeBean> getAllEmplymentLst(Connection con) throws Exception {

        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        empTypeList = new ArrayList<EmploymentTypeBean>();
        try {
            stmt = con.prepareStatement("SELECT EMPLOYMENTTYPECODE,DESCRIPTION,SORTKEY FROM EMPLOYMENTTYPE ORDER BY SORTKEY ASC  ");
            rs = stmt.executeQuery();

            while (rs.next()) {
                EmploymentTypeBean bean = new EmploymentTypeBean();

                bean.setTypeCode(rs.getString("EMPLOYMENTTYPECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                empTypeList.add(bean);
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
        return empTypeList;
    }

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

    public List<EmploymentNatureBean> getAllNatureLst(Connection con) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        natureList = new ArrayList<EmploymentNatureBean>();
        try {
            stmt = con.prepareStatement("SELECT BUSINESSNATURECODE,DESCRIPTION,SORTKEY FROM EMPLOYMENTNATURE ORDER BY SORTKEY ASC  ");
            rs = stmt.executeQuery();

            while (rs.next()) {
                EmploymentNatureBean bean = new EmploymentNatureBean();

                bean.setNatureCode(rs.getString("BUSINESSNATURECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                natureList.add(bean);
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
        return natureList;
    }

    public CardApplicationStatusBean getAllApplicationStatus(String applicationId, Connection con) throws Exception {
        CardApplicationStatusBean bean = new CardApplicationStatusBean();
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        try {
            stmt = con.prepareStatement("SELECT APPLICATIONID,TAB01STATUS,TAB02STATUS,TAB03STATUS,TAB04STATUS,TAB05STATUS,TAB06STATUS,APPLICATIONSTATUS FROM CARDAPPLICATIONSTATUS WHERE APPLICATIONID =?  ");
            stmt.setString(1, applicationId);
            rs = stmt.executeQuery();

            while (rs.next()) {

                bean.setApplicationId(rs.getString("APPLICATIONID"));
                bean.setTableOne(rs.getString("TAB01STATUS"));
                bean.setTableTwo(rs.getString("TAB02STATUS"));
                bean.setTableThree(rs.getString("TAB03STATUS"));
                bean.setTableFore(rs.getString("TAB04STATUS"));
                bean.setTableFive(rs.getString("TAB05STATUS"));
                bean.setTableSix(rs.getString("TAB06STATUS"));
                bean.setApplicationStatus(rs.getString("APPLICATIONSTATUS"));
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

    public List<IncomeTypeBean> getAllIncomeTypes(Connection con) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        incomeLst = new ArrayList<IncomeTypeBean>();
        try {
            stmt = con.prepareStatement("SELECT INCOMECATEGORYCODE,DESCRIPTION FROM INCOMECATEGORY ");
            rs = stmt.executeQuery();

            while (rs.next()) {
                IncomeTypeBean bean = new IncomeTypeBean();

                bean.setIncomeCode(rs.getString("INCOMECATEGORYCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                incomeLst.add(bean);
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

        return incomeLst;
    }

    public List<BankBean> getAllBanks(Connection con) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        bankLst = new ArrayList<BankBean>();
        try {
            stmt = con.prepareStatement("SELECT BANKCODE,BANKNAME,STATUS FROM BANK ORDER BY BANKNAME ");
            rs = stmt.executeQuery();

            while (rs.next()) {
                BankBean bean = new BankBean();

                bean.setBankCode(rs.getString("BANKCODE"));
                bean.setBankName(rs.getString("BANKNAME"));
                bean.setStatus(rs.getString("STATUS"));

                bankLst.add(bean);
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

        return bankLst;
    }

    public List<BankBean> getBankByBankCode(Connection con, String bankCode) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        bankLst = new ArrayList<BankBean>();
        try {
            stmt = con.prepareStatement("SELECT BANKCODE,BANKNAME,STATUS FROM BANK WHERE BANKCODE=? ");
            stmt.setString(1, bankCode);
            rs = stmt.executeQuery();

            while (rs.next()) {
                BankBean bean = new BankBean();

                bean.setBankCode(rs.getString("BANKCODE"));
                bean.setBankName(rs.getString("BANKNAME"));
                bean.setStatus(rs.getString("STATUS"));

                bankLst.add(bean);
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

        return bankLst;
    }

    public List<BankBranchBean> getAllBankBranches(Connection con) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        bankBranchLst = new ArrayList<BankBranchBean>();
        try {
            stmt = con.prepareStatement("SELECT BRANCHCODE,BANKCODE,DESCRPTION FROM BANKBRANCH ORDER BY DESCRPTION");
            rs = stmt.executeQuery();

            while (rs.next()) {
                BankBranchBean bean = new BankBranchBean();

                bean.setBranchCode(rs.getString("BRANCHCODE"));
                bean.setBankCode(rs.getString("BANKCODE"));
                bean.setDescription(rs.getString("DESCRPTION"));

                bankBranchLst.add(bean);
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

        return bankBranchLst;
    }

    public List<BankBranchBean> getBankBranches(Connection con, String bankCode) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        bankBranchLst = new ArrayList<BankBranchBean>();
        try {
            stmt = con.prepareStatement("SELECT BRANCHCODE,DESCRPTION FROM BANKBRANCH WHERE BANKCODE=? ORDER BY DESCRPTION");
            stmt.setString(1, bankCode);
            rs = stmt.executeQuery();

            while (rs.next()) {
                BankBranchBean bean = new BankBranchBean();

                bean.setBranchCode(rs.getString("BRANCHCODE"));
                bean.setDescription(rs.getString("DESCRPTION"));

                bankBranchLst.add(bean);
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

        return bankBranchLst;
    }

    public List<AreaBean> getAllAreaList(Connection con) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        areaLst = new ArrayList<AreaBean>();
        try {
            stmt = con.prepareStatement("SELECT AREACODE,DESCRIPTION,DISTRICTCODE FROM AREA ORDER BY DESCRIPTION ASC ");
            rs = stmt.executeQuery();

            while (rs.next()) {
                AreaBean bean = new AreaBean();

                bean.setAreaCode(rs.getString("AREACODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setDistricCode(rs.getString("DISTRICTCODE"));

                areaLst.add(bean);
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

        return areaLst;
    }

    public List<String> getAllNationality(Connection con) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        nationalityList = new ArrayList<String>();
        try {
            stmt = con.prepareStatement("SELECT COUNTRYNUMCODE,COUNTRYALPHA2CODE,COUNTRYALPHA3CODE,NATIONALITY FROM COUNTRY WHERE NATIONALITY IS NOT NULL");
            rs = stmt.executeQuery();

            while (rs.next()) {

                nationalityList.add(rs.getString("NATIONALITY"));

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

        return nationalityList;
    }

    public String getDistrictAndProvince(Connection con, String area) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        String result = "";
        try {
            stmt = con.prepareStatement("SELECT D.DISTRICTCODE,D.DISTRICTNAME,D.PROVINCE FROM DISTRICTPROVINCE D,AREA AR WHERE AR.DISTRICTCODE=D.DISTRICTCODE AND AR.AREACODE=? ");
            stmt.setString(1, area);
            rs = stmt.executeQuery();

            if (rs.next()) {

                result = rs.getString("DISTRICTNAME") + "," + rs.getString("PROVINCE");

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

        return result;
    }

    public String getCardProductDropDownLst(Connection con, String cardType) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        String result = "";
        String codes = "";
        String description = "";
        try {
            stmt = con.prepareStatement("SELECT PRODUCTCODE,CARDTYPE,DESCRIPTION,STATUS FROM CARDPRODUCT WHERE CARDTYPE=? ");
            stmt.setString(1, cardType);
            rs = stmt.executeQuery();

            while (rs.next()) {

                if (!codes.equals("")) {
                    codes += "," + rs.getString("PRODUCTCODE");
                } else {
                    codes = rs.getString("PRODUCTCODE");
                }

                if (!description.equals("")) {
                    description += "," + rs.getString("DESCRIPTION");
                } else {
                    description = rs.getString("DESCRIPTION");
                }

            }
            result = codes + "|" + description;

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

        return result;
    }

    public List<VerificationCategoryBean> getAllVerificationCategory(Connection con) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        verificationCateLst = new ArrayList<VerificationCategoryBean>();
        try {
            stmt = con.prepareStatement("SELECT VERIFICATIONCATEGORYCODE,DESCRIPTON FROM VERIFICATIONCATEGORY ");
            rs = stmt.executeQuery();

            while (rs.next()) {
                VerificationCategoryBean bean = new VerificationCategoryBean();
                bean.setCategoryCode(rs.getString("VERIFICATIONCATEGORYCODE"));
                bean.setCategoryName(rs.getString("DESCRIPTON"));

                verificationCateLst.add(bean);
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

        return verificationCateLst;
    }

    public List<DocumentTypeBean> getDocumentTypeByCategory(Connection con, String category) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        documentTypeLst = new ArrayList<DocumentTypeBean>();
        try {
            stmt = con.prepareStatement("SELECT DOCUMENTTYPECODE,DOCUMENTNAME FROM APPLICATIONDOCUMENT WHERE VERIFICATIONCATEGORY=? ");
            stmt.setString(1, category);
            rs = stmt.executeQuery();

            while (rs.next()) {
                DocumentTypeBean bean = new DocumentTypeBean();
                bean.setTypeCode(rs.getString("DOCUMENTTYPECODE"));
                bean.setDescription(rs.getString("DOCUMENTNAME"));

                documentTypeLst.add(bean);
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

        return documentTypeLst;
    }

    public String getSignatureName(Connection con, String applicationId) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        String result = "";
        try {
            stmt = con.prepareStatement("SELECT APPLICATIONID,SIGNATURE FROM CARDAPPLICATION WHERE APPLICATIONID=? ");
            stmt.setString(1, applicationId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                result = rs.getString("SIGNATURE");

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

        return result;
    }

    public IdBean getIdentifyDetails(Connection con, String appliactionId) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        IdBean bean = new IdBean();
        try {
            stmt = con.prepareStatement("SELECT APPLICATIONID,IDENTIFICATIONTYPE,IDENTIFICATIONNO,PASSPORTEXPIRYDATE FROM CARDAPPLICATION WHERE APPLICATIONID=? ");
            stmt.setString(1, appliactionId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                bean.setIdNumber(rs.getString("IDENTIFICATIONNO"));
                bean.setIdType(rs.getString("IDENTIFICATIONTYPE"));
                bean.setExpDate(rs.getString("PASSPORTEXPIRYDATE"));

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

    public CardCustomerBean getPreviousCustomerDetails(Connection con, String identityNo) throws Exception {
        customerBean = new CardCustomerBean();
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        try {
            stmt = con.prepareStatement("SELECT CUSTOMERID,CUSTOMERNAME,IDENTIFICATIONNO FROM CARDCUSTOMER WHERE IDENTIFICATIONNO=? ");
            stmt.setString(1, identityNo);
            rs = stmt.executeQuery();

            if (rs.next()) {
                customerBean.setCustomerId(rs.getString("CUSTOMERID"));
                customerBean.setCustomerName(rs.getString("CUSTOMERNAME"));
                customerBean.setIdNumber(rs.getString("IDENTIFICATIONNO"));

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
        return customerBean;
    }

    public List<ApplicationAssignBean> debitCardApplicationsSearch(Connection con, SearchUserAssignAppBean searchBean) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query

        try {

            strSqlBasic = " SELECT a.APPLICATIONID,  a.IDENTIFICATIONTYPE ,  a.IDENTIFICATIONNO,  a.PRIORITYLEVELCODE, "
                    + " cc.DESCRIPTION AS CARDCATEGORY,  pl.DESCRIPTION AS PRIORITYNAME,  a.REFERANCIALEMPNO,  a.BRANCHCODE, "
                    + " bk.DESCRPTION AS BRANCHNAME   ,  a.ASSIGNUSER, a.ASSIGNSTATUS,  st.DESCRIPTION , "
                    + " a.LASTUPDATEDUSER ,  a.LASTUPDATEDTIME  ,  a.CREATETIME  FROM CARDAPPLICATION a ,cardapplicationstatus cs,"
                    + "STATUS st ,PRIORITYLEVEL pl  ,BANKBRANCH bk ,CARDCATEGORY cc ";

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
            if (!searchBean.getCardcategory().contentEquals("") || searchBean.getCardcategory().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.CARDCATEGORY = '" + searchBean.getCardcategory() + "'";
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
                condition += "a.CREATETIME >= to_Date('" + this.stringTodate(searchBean.getFromDate()) + "','yy-mm-dd') AND a.CREATETIME < to_Date('" + this.stringTodate(searchBean.getToDate()) + "','yy-mm-dd')+1";
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
                    condition += "a.CREATETIME < to_Date('" + this.stringTodate(searchBean.getToDate()) + "','yy-mm-dd')+1";
                }
            }

            if (!condition.equals("")) {

                strSqlBasic += " WHERE a.applicationid = cs.applicationid  AND cs.applicationstatus = st.statuscode AND a.PRIORITYLEVELCODE = pl.PRIORITYLEVELCODE AND a.BRANCHCODE = bk.BRANCHCODE(+) AND (bk.bankcode = (SELECT BANKCODE FROM COMMONPARAMETER) OR a.branchcode is null) AND a.CARDCATEGORY= cc.CATEGORYCODE AND (cs.applicationstatus ='" + StatusVarList.INITIAL_STATUS + "' OR cs.applicationstatus ='" + StatusVarList.APP_PROCESS + "') AND a.CARDAPPLIACTIONDOMAIN='" + StatusVarList.DEBIT + "' AND " + condition + " ORDER BY a.APPLICATIONID";
            } else {
                strSqlBasic += " WHERE a.applicationid = cs.applicationid  AND cs.applicationstatus = st.statuscode AND a.PRIORITYLEVELCODE = pl.PRIORITYLEVELCODE AND a.BRANCHCODE = bk.BRANCHCODE(+) AND (bk.bankcode = (SELECT BANKCODE FROM COMMONPARAMETER) OR a.branchcode is null) AND a.CARDCATEGORY = cc.CATEGORYCODE AND (cs.applicationstatus ='" + StatusVarList.INITIAL_STATUS + "' OR cs.applicationstatus ='" + StatusVarList.APP_PROCESS + "') AND a.CARDAPPLIACTIONDOMAIN='" + StatusVarList.DEBIT + "' ORDER BY a.APPLICATIONID ";
            }

            stmt = con.prepareStatement(strSqlBasic);
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
                resultAssign.setCardCategory(rs.getString("CARDCATEGORY"));
                resultAssign.setPriorityDescription(rs.getString("PRIORITYNAME"));
                resultAssign.setReferralBranchId(rs.getString("BRANCHCODE"));
                resultAssign.setReferralBranchName(rs.getString("BRANCHNAME"));
                resultAssign.setReferralEmpNo(rs.getString("REFERANCIALEMPNO"));
                resultAssign.setAssignStatus(rs.getString("DESCRIPTION"));

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

    public List<CardProductBean> getAllCardProduct(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllCardType = null;
        try {

            getAllCardType = con.prepareStatement("SELECT TC.PRODUCTCODE,TC.DESCRIPTION "
                    + "FROM CARDPRODUCT TC ");

            rs = getAllCardType.executeQuery();
            cardProductList = new ArrayList<CardProductBean>();

            while (rs.next()) {

                CardProductBean bean = new CardProductBean();

                bean.setProductCode(rs.getString("PRODUCTCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                cardProductList.add(bean);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllCardType.close();

        }

        return cardProductList;
    }

    public List<CardProductBean> getAllCardProductByCardType(Connection con, String cardType) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllCardType = null;
        try {

            getAllCardType = con.prepareStatement("SELECT TC.PRODUCTCODE,TC.DESCRIPTION "
                    + " FROM CARDPRODUCT TC WHERE TC.CARDTYPE=? AND TC.CARDDOMAIN = ?"
                    + " ORDER BY TC.DESCRIPTION ");

            getAllCardType.setString(1, cardType);
            getAllCardType.setString(2, StatusVarList.DEBIT);
            rs = getAllCardType.executeQuery();
            cardProductList = new ArrayList<CardProductBean>();

            while (rs.next()) {

                CardProductBean bean = new CardProductBean();

                bean.setProductCode(rs.getString("PRODUCTCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                cardProductList.add(bean);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllCardType.close();

        }

        return cardProductList;
    }

    public boolean insertPrimaryDebitCardApplication(Connection CMSCon, DebitPersonalBean personalBean) throws Exception {
        boolean successInsert = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = CMSCon.prepareStatement("INSERT INTO DEBITCARDAPPLICATIONDETAILS"
                    + " (APPLICATIONID,IDENTIFICATIONTYPE,IDENTIFICATIONNO,TITLE,DATEOFBIRTH,FIRSTNAME,MIDDLENAME,"
                    + " LASTNAME,ADDRESS1,"
                    + " ADDRESS2,ADDRESS3,NAMEONCARD,AREA,MOTHERSMAIDENNAME,MOBILENO,HOMETELEPHONENO,"
                    + " ACCOUNTNO1,REQUESTEDCARDTYPE,CARDPRODUCT,LOYALTYREQUIRED,LASTUPDATEDUSER,OFFICEPHONENO,"
                    + " ACCTYPE1,ACCCURRENCY1,ACCOUNTNO2,ACCTYPE2,ACCCURRENCY2,LASTUPDATEDTIME,CREATETIME)"
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,SYSDATE)");

            insertStat.setString(1, personalBean.getApplicationId());
            insertStat.setString(2, personalBean.getIdentificationType());
            insertStat.setString(3, personalBean.getIdentificationNumber());
            insertStat.setString(4, personalBean.getTitle());
            insertStat.setString(5, personalBean.getBirthday());
            insertStat.setString(6, personalBean.getFirstName());
            insertStat.setString(7, personalBean.getMiddleName());
            insertStat.setString(8, personalBean.getLastName());
            insertStat.setString(9, personalBean.getAddress1());
            insertStat.setString(10, personalBean.getAddress2());
            insertStat.setString(11, personalBean.getAddress3());
            insertStat.setString(12, personalBean.getNameOncard());
            insertStat.setString(13, personalBean.getCity());
            insertStat.setString(14, personalBean.getMothersMaidenName());
            insertStat.setString(15, personalBean.getMobileNumber());
            insertStat.setString(16, personalBean.getHomeTelNumber());
            insertStat.setString(17, personalBean.getAccountNo());
            insertStat.setString(18, personalBean.getCardType());
            insertStat.setString(19, personalBean.getCardProduct());
            insertStat.setString(20, personalBean.getLoyalityRequired());
            insertStat.setString(21, personalBean.getLastUpdateUser());
            insertStat.setString(22, personalBean.getOfficeTelNumber());

            insertStat.setString(23, personalBean.getAccNo1Type());
            insertStat.setString(24, personalBean.getAccNo1Currency());
            insertStat.setString(25, personalBean.getAccountNo2());
            insertStat.setString(26, personalBean.getAccNo2Type());
            insertStat.setString(27, personalBean.getAccNo2Currency());

            insertStat.executeUpdate();
            successInsert = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                insertStat.close();

            } catch (Exception e) {
                throw e;
            }

        }
        return successInsert;

    }

    public boolean updatePrimaryDebitCardApplication(Connection CMSCon, DebitPersonalBean personalBean) throws Exception {
        boolean successInsert = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = CMSCon.prepareStatement("UPDATE DEBITCARDAPPLICATIONDETAILS SET"
                    + " OFFICEPHONENO=?,IDENTIFICATIONTYPE=?,IDENTIFICATIONNO=?,TITLE=?,"
                    + " DATEOFBIRTH=?,FIRSTNAME=?,MIDDLENAME=?,LASTNAME=?,ADDRESS1=?,ADDRESS2=?,ADDRESS3=?,"
                    + " NAMEONCARD=?,AREA=?,MOTHERSMAIDENNAME=?,MOBILENO=?,HOMETELEPHONENO=?,LASTUPDATEDUSER=?,"
                    + " ACCOUNTNO1=?,REQUESTEDCARDTYPE=?,CARDPRODUCT=?, ACCTYPE1=?,ACCCURRENCY1=?,ACCOUNTNO2=?,ACCTYPE2=?,"
                    + " ACCCURRENCY2=?,LOYALTYREQUIRED=?,"
                    + " LASTUPDATEDTIME=SYSDATE WHERE APPLICATIONID=?");

            updateStat.setString(1, personalBean.getOfficeTelNumber());
            updateStat.setString(2, personalBean.getIdentificationType());
            updateStat.setString(3, personalBean.getIdentificationNumber());
            updateStat.setString(4, personalBean.getTitle());
            updateStat.setString(5, personalBean.getBirthday());
            updateStat.setString(6, personalBean.getFirstName());
            updateStat.setString(7, personalBean.getMiddleName());
            updateStat.setString(8, personalBean.getLastName());
            updateStat.setString(9, personalBean.getAddress1());
            updateStat.setString(10, personalBean.getAddress2());
            updateStat.setString(11, personalBean.getAddress3());
            updateStat.setString(12, personalBean.getNameOncard());
            updateStat.setString(13, personalBean.getCity());
            updateStat.setString(14, personalBean.getMothersMaidenName());
            updateStat.setString(15, personalBean.getMobileNumber());
            updateStat.setString(16, personalBean.getHomeTelNumber());
            updateStat.setString(17, personalBean.getLastUpdateUser());
            updateStat.setString(18, personalBean.getAccountNo());
            updateStat.setString(19, personalBean.getCardType());
            updateStat.setString(20, personalBean.getCardProduct());

            updateStat.setString(21, personalBean.getAccNo1Type());
            updateStat.setString(22, personalBean.getAccNo1Currency());
            updateStat.setString(23, personalBean.getAccountNo2());
            updateStat.setString(24, personalBean.getAccNo2Type());
            updateStat.setString(25, personalBean.getAccNo2Currency());

            updateStat.setString(26, personalBean.getLoyalityRequired());

            updateStat.setString(27, personalBean.getApplicationId());

            updateStat.executeUpdate();
            successInsert = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                updateStat.close();

            } catch (Exception e) {
                throw e;
            }

        }
        return successInsert;

    }

    public boolean insertDebitCardSecondaryApplication(Connection CMSCon, DebitPersonalBean personalBean) throws Exception {
        boolean successInsert = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = CMSCon.prepareStatement("UPDATE DEBITCARDAPPLICATIONDETAILS SET"
                    + " SOFFICEPHONENO=?,SIDENTIFICATIONTYPE=?,SIDENTIFICATIONNO=?,STITLE=?,"
                    + " SDATEOFBIRTH=?,SFIRSTNAME=?,SMIDDLENAME=?,SLASTNAME=?,SADDRESS1=?,SADDRESS2=?,SADDRESS3=?,"
                    + " SNAMEONCARD=?,SAREA=?,SMOTHERSMAIDENNAME=?,SMOBILENO=?,SHOMETELEPHONENO=?,LASTUPDATEDUSER=?,"
                    + " LASTUPDATEDTIME=SYSDATE,ACCOUNTNO3=?,ACCOUNTNO4=?,ACCTYPE3=?,ACCTYPE4=?,ACCCURRENCY3=?,ACCCURRENCY4=? "
                    + " WHERE APPLICATIONID=?");

            updateStat.setString(1, personalBean.getOfficeTelNumber());
            updateStat.setString(2, personalBean.getIdentificationType());
            updateStat.setString(3, personalBean.getIdentificationNumber());
            updateStat.setString(4, personalBean.getTitle());
            updateStat.setString(5, personalBean.getBirthday());
            updateStat.setString(6, personalBean.getFirstName());
            updateStat.setString(7, personalBean.getMiddleName());
            updateStat.setString(8, personalBean.getLastName());
            updateStat.setString(9, personalBean.getAddress1());
            updateStat.setString(10, personalBean.getAddress2());
            updateStat.setString(11, personalBean.getAddress3());
            updateStat.setString(12, personalBean.getNameOncard());
            updateStat.setString(13, personalBean.getCity());
            updateStat.setString(14, personalBean.getMothersMaidenName());
            updateStat.setString(15, personalBean.getMobileNumber());
            updateStat.setString(16, personalBean.getHomeTelNumber());

            updateStat.setString(17, personalBean.getLastUpdateUser());

            updateStat.setString(18, personalBean.getAccountNo());
            updateStat.setString(19, personalBean.getAccountNo3());
            updateStat.setString(20, personalBean.getAccNo1Type());
            updateStat.setString(21, personalBean.getAccNo3Type());
            updateStat.setString(22, personalBean.getAccNo1Currency());
            updateStat.setString(23, personalBean.getAccNo3Currency());

            updateStat.setString(24, personalBean.getApplicationId());

            updateStat.executeUpdate();
            successInsert = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                updateStat.close();

            } catch (Exception e) {
                throw e;
            }

        }
        return successInsert;

    }

    public boolean checkApplicationExist(Connection CMSCon, String applicationId) throws Exception {
        ResultSet rs = null;
        boolean isExist = false;
        PreparedStatement checkStat = null;

        try {
            checkStat = CMSCon.prepareStatement("SELECT D.APPLICATIONID FROM DEBITCARDAPPLICATIONDETAILS D"
                    + " WHERE D.APPLICATIONID=?");

            checkStat.setString(1, applicationId);

            rs = checkStat.executeQuery();

            while (rs.next()) {

                isExist = true;
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            checkStat.close();

        }
        return isExist;
    }

    public boolean checkCardApplicationStatus(Connection CMSCon, String applicationId) throws Exception {
        ResultSet rs = null;
        boolean isExist = false;
        PreparedStatement checkStat = null;
        try {
            checkStat = CMSCon.prepareStatement("SELECT C.APPLICATIONID FROM CARDAPPLICATIONSTATUS C"
                    + " WHERE C.APPLICATIONID=? AND APPLICATIONSTATUS=?");

            checkStat.setString(1, applicationId);
            checkStat.setString(2, StatusVarList.APP_PROCESS);

            rs = checkStat.executeQuery();

            while (rs.next()) {

                isExist = true;
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            checkStat.close();

        }
        return isExist;
    }

    public DebitPersonalBean getDebitCardApplicationPrimeryDetails(Connection CMSCon, String applicationId) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllStat = null;
        debitPersonalBean = new DebitPersonalBean();
        try {

            getAllStat = CMSCon.prepareStatement("SELECT D.TITLE,D.FIRSTNAME,D.LASTNAME,D.MIDDLENAME,"
                    + " D.DATEOFBIRTH,D.ADDRESS1,D.ADDRESS2,D.ADDRESS3,D.AREA,D.HOMETELEPHONENO,D.OFFICEPHONENO,"
                    + " D.MOBILENO,D.MOTHERSMAIDENNAME,D.NAMEONCARD,D.REQUESTEDCARDTYPE,D.CARDPRODUCT,D.LOYALTYREQUIRED,"
                    + " D.ACCOUNTNO1,ACCTYPE1,ACCCURRENCY1,ACCOUNTNO2,ACCTYPE2,ACCCURRENCY2"
                    + " FROM DEBITCARDAPPLICATIONDETAILS D WHERE D.APPLICATIONID = ?");

            getAllStat.setString(1, applicationId);
            rs = getAllStat.executeQuery();

            while (rs.next()) {

                debitPersonalBean.setTitle(rs.getString("TITLE"));
                debitPersonalBean.setFirstName(rs.getString("FIRSTNAME"));
                debitPersonalBean.setLastName(rs.getString("LASTNAME"));
                debitPersonalBean.setMiddleName(rs.getString("MIDDLENAME"));
                debitPersonalBean.setBirthday(rs.getString("DATEOFBIRTH"));
                debitPersonalBean.setAddress1(rs.getString("ADDRESS1"));
                debitPersonalBean.setAddress2(rs.getString("ADDRESS2"));
                debitPersonalBean.setAddress3(rs.getString("ADDRESS2"));
                debitPersonalBean.setCity(rs.getString("AREA"));
                debitPersonalBean.setHomeTelNumber(rs.getString("HOMETELEPHONENO"));
                debitPersonalBean.setOfficeTelNumber(rs.getString("OFFICEPHONENO"));
                debitPersonalBean.setMobileNumber(rs.getString("MOBILENO"));
                debitPersonalBean.setMothersMaidenName(rs.getString("MOTHERSMAIDENNAME"));
                debitPersonalBean.setNameOncard(rs.getString("NAMEONCARD"));
                debitPersonalBean.setCardType(rs.getString("REQUESTEDCARDTYPE"));
                debitPersonalBean.setCardProduct(rs.getString("CARDPRODUCT"));
                debitPersonalBean.setLoyalityRequired(rs.getString("LOYALTYREQUIRED"));
                debitPersonalBean.setAccountNo(rs.getString("ACCOUNTNO1"));
                debitPersonalBean.setAccNo1Type(rs.getString("ACCTYPE1"));
                debitPersonalBean.setAccNo1Currency(rs.getString("ACCCURRENCY1"));
//                
                debitPersonalBean.setAccountNo2(rs.getString("ACCOUNTNO2"));
                debitPersonalBean.setAccNo2Type(rs.getString("ACCTYPE2"));
                debitPersonalBean.setAccNo2Currency(rs.getString("ACCCURRENCY2"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllStat.close();

        }

        return debitPersonalBean;

    }

    public String getDebitCardApplicationPrimarySignature(Connection CMSCon, String applicationId) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllStat = null;
        String signature = null;
        try {

            getAllStat = CMSCon.prepareStatement("SELECT CA.PRIMARYSIGNATURE FROM CARDAPPLICATION CA WHERE CA.APPLICATIONID = ?");

            getAllStat.setString(1, applicationId);
            rs = getAllStat.executeQuery();

            while (rs.next()) {

                signature = rs.getString("PRIMARYSIGNATURE");

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllStat.close();

        }

        return signature;

    }

    public String getDebitCardApplicationPrimaryNIC(Connection CMSCon, String applicationId) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllStat = null;
        String nic = null;
        try {

            getAllStat = CMSCon.prepareStatement("SELECT CA.FILENAME "
                    + " FROM CARDAPPLICATIONDOCUMENT CA WHERE CA.APPLICATIONID = ? "
                    + " AND CA.DOCUMENTTYPE = ?");

            getAllStat.setString(1, applicationId);
            getAllStat.setString(2, StatusVarList.NATIONAL_IDENTITY_CARD);
            rs = getAllStat.executeQuery();

            while (rs.next()) {

                nic = rs.getString("FILENAME");

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllStat.close();

        }

        return nic;

    }

    public DebitPersonalBean getDebitCardApplicationDetails(Connection CMSCon, String applicationId) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllStat = null;
        debitPersonalBean = new DebitPersonalBean();
        try {

            getAllStat = CMSCon.prepareStatement("SELECT D.TITLE,D.FIRSTNAME,D.LASTNAME,D.MIDDLENAME,"
                    + " D.DATEOFBIRTH,D.ADDRESS1,D.ADDRESS2,D.ADDRESS3,D.AREA,D.HOMETELEPHONENO,D.OFFICEPHONENO,"
                    + " D.MOBILENO,D.MOTHERSMAIDENNAME,D.NAMEONCARD,D.REQUESTEDCARDTYPE,D.CARDPRODUCT,D.ACCOUNTNO1,"
                    + " D.LOYALTYREQUIRED,ACCTYPE1,ACCCURRENCY1,ACCOUNTNO2,ACCTYPE2,ACCCURRENCY2,D.ACCOUNTNO4,ACCTYPE4,ACCCURRENCY4,"
                    + " D.IDENTIFICATIONNO,D.IDENTIFICATIONTYPE,D.SIDENTIFICATIONNO,D.SIDENTIFICATIONTYPE,"
                    + " D.SHOMETELEPHONENO,D.SOFFICEPHONENO,D.SMOBILENO,D.SMOTHERSMAIDENNAME,D.SNAMEONCARD,"
                    + " D.STITLE,D.SFIRSTNAME,D.SLASTNAME,D.SMIDDLENAME,D.SDATEOFBIRTH,D.SADDRESS1,D.SADDRESS2,D.SADDRESS3,D.SAREA "
                    + " FROM DEBITCARDAPPLICATIONDETAILS D WHERE D.APPLICATIONID = ?");

            getAllStat.setString(1, applicationId);
            rs = getAllStat.executeQuery();

            while (rs.next()) {

                debitPersonalBean.setTitle(rs.getString("TITLE"));
                debitPersonalBean.setFirstName(rs.getString("FIRSTNAME"));
                debitPersonalBean.setLastName(rs.getString("LASTNAME"));
                debitPersonalBean.setMiddleName(rs.getString("MIDDLENAME"));
                debitPersonalBean.setBirthday(rs.getString("DATEOFBIRTH"));
                debitPersonalBean.setAddress1(rs.getString("ADDRESS1"));
                debitPersonalBean.setAddress2(rs.getString("ADDRESS2"));
                debitPersonalBean.setAddress3(rs.getString("ADDRESS2"));
                debitPersonalBean.setCity(rs.getString("AREA"));
                debitPersonalBean.setHomeTelNumber(rs.getString("HOMETELEPHONENO"));
                debitPersonalBean.setOfficeTelNumber(rs.getString("OFFICEPHONENO"));
                debitPersonalBean.setMobileNumber(rs.getString("MOBILENO"));
                debitPersonalBean.setMothersMaidenName(rs.getString("MOTHERSMAIDENNAME"));
                debitPersonalBean.setNameOncard(rs.getString("NAMEONCARD"));
                debitPersonalBean.setCardType(rs.getString("REQUESTEDCARDTYPE"));
                debitPersonalBean.setCardProduct(rs.getString("CARDPRODUCT"));
                debitPersonalBean.setLoyalityRequired(rs.getString("LOYALTYREQUIRED"));
                debitPersonalBean.setAccountNo(rs.getString("ACCOUNTNO1"));
                debitPersonalBean.setAccNo1Type(rs.getString("ACCTYPE1"));
                debitPersonalBean.setAccNo1Currency(rs.getString("ACCCURRENCY1"));
//                
                debitPersonalBean.setAccountNo2(rs.getString("ACCOUNTNO2"));
                debitPersonalBean.setAccNo2Type(rs.getString("ACCTYPE2"));
                debitPersonalBean.setAccNo2Currency(rs.getString("ACCCURRENCY2"));
                debitPersonalBean.setAccountNo3(rs.getString("ACCOUNTNO4"));
                debitPersonalBean.setAccNo3Type(rs.getString("ACCTYPE4"));
                debitPersonalBean.setAccNo3Currency(rs.getString("ACCCURRENCY4"));
                debitPersonalBean.setIdentificationNumber(rs.getString("IDENTIFICATIONNO"));
                debitPersonalBean.setIdentificationType(rs.getString("IDENTIFICATIONTYPE"));
                debitPersonalBean.setsIdentificationType(rs.getString("SIDENTIFICATIONTYPE"));
                debitPersonalBean.setsIdentificationNumber(rs.getString("SIDENTIFICATIONNO"));
                debitPersonalBean.setsTitle(rs.getString("STITLE"));
                debitPersonalBean.setsFirstName(rs.getString("SFIRSTNAME"));
                debitPersonalBean.setsLastName(rs.getString("SLASTNAME"));
                debitPersonalBean.setsMiddleName(rs.getString("SMIDDLENAME"));
                debitPersonalBean.setsBirthday(rs.getString("SDATEOFBIRTH"));
                debitPersonalBean.setsAddress1(rs.getString("SADDRESS1"));
                debitPersonalBean.setsAddress2(rs.getString("SADDRESS2"));
                debitPersonalBean.setsAddress3(rs.getString("SADDRESS3"));
                debitPersonalBean.setsCity(rs.getString("SAREA"));
                debitPersonalBean.setsHomeTelNumber(rs.getString("SHOMETELEPHONENO"));
                debitPersonalBean.setsOfficeTelNumber(rs.getString("SOFFICEPHONENO"));
                debitPersonalBean.setsMobileNumber(rs.getString("SMOBILENO"));
                debitPersonalBean.setsMothersMaidenName(rs.getString("SMOTHERSMAIDENNAME"));
                debitPersonalBean.setsNameOncard(rs.getString("SNAMEONCARD"));

            }

        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
            throw ex;
        } finally {
            rs.close();
            getAllStat.close();

        }

        return debitPersonalBean;

    }

    public String getBankBranchesDropDownLst(Connection con, String bankcode) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        String result = "";
        String codes = "";
        String description = "";
        try {
            stmt = con.prepareStatement("SELECT BRANCHCODE,BANKCODE,DESCRPTION FROM BANKBRANCH WHERE BANKCODE=? ORDER BY DESCRPTION ASC");
            stmt.setString(1, bankcode);
            rs = stmt.executeQuery();

            while (rs.next()) {

                if (!codes.equals("")) {
                    codes += "," + rs.getString("BRANCHCODE");
                } else {
                    codes = rs.getString("BRANCHCODE");
                }

                if (!description.equals("")) {
                    description += "," + rs.getString("DESCRPTION");
                } else {
                    description = rs.getString("DESCRPTION");
                }

            }
            if (!codes.equals("")) {
                result = codes + "|" + description;
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

        return result;
    }

    public List<CurrencyBean> getCurrencyList(Connection con) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        currencyList = new ArrayList<CurrencyBean>();
        try {
            stmt = con.prepareStatement("SELECT DISTINCT C.CURRENCYNUMCODE,C.CURRENCYALPHACODE,C.DESCRIPTION FROM CURRENCY C,CARDBIN B WHERE  B.CURRENCYTYPECODE=C.CURRENCYNUMCODE AND B.STATUS ='ACT' ORDER BY C.DESCRIPTION ASC");
            rs = stmt.executeQuery();

            while (rs.next()) {
                CurrencyBean bean = new CurrencyBean();
                bean.setCurrencyCode(rs.getString("CURRENCYNUMCODE"));
                bean.setCurrencyDes(rs.getString("DESCRIPTION"));

                currencyList.add(bean);
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

        return currencyList;
    }

    public HashMap<String, String> getAllAccountType(Connection con) throws Exception {

        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;

        try {
            getAllUserRole = con.prepareStatement("SELECT A.ACCOUNTTYPECODE,A.DESCRIPTION FROM ACCOUNTTYPE A");

            rs = getAllUserRole.executeQuery();
            accountTypeList = new HashMap<String, String>();

            while (rs.next()) {

                accountTypeList.put(rs.getString("ACCOUNTTYPECODE"), rs.getString("DESCRIPTION"));

            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();

        }

        return accountTypeList;
    }

    public String getCardCategory(Connection con, String AppId) throws Exception {
        String categoryCode = null;
        ResultSet rs = null;
        PreparedStatement getCat = null;

        try {
            getCat = con.prepareStatement("SELECT C.CARDCATEGORY "
                    + " FROM CARDAPPLICATION C WHERE C.APPLICATIONID=?");

            getCat.setString(1, AppId);
            rs = getCat.executeQuery();

            while (rs.next()) {

                categoryCode = rs.getString("CARDCATEGORY");

            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getCat.close();
        }

        return categoryCode;
    }

    public String getCommonCurrency(Connection CMSCon) throws Exception {
        String comminCurrency = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {
            stmt = CMSCon.prepareStatement("SELECT C.BASECURRENCY FROM COMMONPARAMETER C ");

            rs = stmt.executeQuery();

            while (rs.next()) {

                comminCurrency = rs.getString("BASECURRENCY");

            }

        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            rs.close();
            stmt.close();
        }
        return comminCurrency;
    }

//      public int insertDocumentUpload(Connection con, List<DocumentUploadBean> sessionDocumentList) throws Exception {
//        int resultId = 0;
//
//        PreparedStatement insertStat = null;
//        try {
//        
//                insertStat = con.prepareStatement("INSERT INTO CARDAPPLICATIONDOCUMENT "
//                        + "(APPLICATIONID,VERIFICATIONCATEGORY,DOCUMENTTYPE,FILENAME ) "
//                        + " VALUES (?,?,?,?) ");
//
//                insertStat.setString(1, ));
//                insertStat.setString(2, );
//                insertStat.setString(3,);
//                insertStat.setString(4, );
//
//
//                resultId = insertStat.executeUpdate();
//              
//
//        } catch (Exception ex) {
//            throw ex;
//        } finally {
//            try {
//                insertStat.close();
//
//            } catch (Exception e) {
//                throw e;
//            }
//
//        }
//
//        return resultId;
//    }
    private String Zeropad(String value) {

        if (value.length() == 1) {
            value = "00" + value;
        }
        if (value.length() == 2) {
            value = "0" + value;
        }

        return value;
    }

    public void addRemark(Connection CMSCon, CustomerPersonalBean personalBean) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public String getPrimaryResidenceDetailsByAppID(Connection con, String primaryApplicationId) throws Exception {

        String primResDetails = "";
        ResultSet rs = null;
        PreparedStatement getCat = null;

        try {

            String query = "SELECT ca.ADDRESS1, "
                    + "  ca.ADDRESS2, "
                    + "  ca.ADDRESS3, "
                    + "  ca.CITY , "
                    + "  dp.DISTRICTNAME, "
                    + "  dp.PROVINCE "
                    + "FROM CARDAPPLICATIONPERSONALDETAILS ca "
                    + "LEFT JOIN AREA ar "
                    + "ON ca.CITY= ar.AREACODE "
                    + "LEFT JOIN DISTRICTPROVINCE dp "
                    + "ON ar.DISTRICTCODE    = dp.DISTRICTCODE "
                    + "WHERE ca.APPLICATIONID=? ";

            getCat = con.prepareStatement(query);

            getCat.setString(1, primaryApplicationId);
            rs = getCat.executeQuery();

            while (rs.next()) {
                primResDetails = rs.getString("ADDRESS1")
                        + "::" + rs.getString("ADDRESS2")
                        + "::" + rs.getString("ADDRESS3")
                        + "::" + rs.getString("CITY")
                        + "::" + rs.getString("DISTRICTNAME")
                        + "::" + rs.getString("PROVINCE");

            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getCat.close();
        }

        return primResDetails;
    }

    public String getPrimaryResidenceDetailsByCardNumber(Connection con, String primaryCArdNum) throws Exception {
        String primResDetails = "";
        ResultSet rs = null;
        PreparedStatement getCat = null;
        try {
            String query = "SELECT ca.ADDRESS1,"
                    + "  ca.ADDRESS2,"
                    + "  ca.ADDRESS3,"
                    + "  ca.CITY ,"
                    + "  dp.DISTRICTNAME,"
                    + "  dp.PROVINCE "
                    + "FROM CARDAPPLICATIONPERSONALDETAILS ca "
                    + "LEFT JOIN AREA ar "
                    + "ON ca.CITY= ar.AREACODE "
                    + "LEFT JOIN DISTRICTPROVINCE dp "
                    + "ON AR.DISTRICTCODE = DP.DISTRICTCODE "
                    + "JOIN CARD CD "
                    + "ON CA.APPLICATIONID= CD.APPLICATIONID "
                    + "WHERE cd.CARDNUMBER  =?";
            getCat = con.prepareStatement(query);
            getCat.setString(1, primaryCArdNum);
            rs = getCat.executeQuery();
            while (rs.next()) {
                primResDetails = rs.getString("ADDRESS1")
                        + "::" + rs.getString("ADDRESS2")
                        + "::" + rs.getString("ADDRESS3")
                        + "::" + rs.getString("CITY")
                        + "::" + rs.getString("DISTRICTNAME")
                        + "::" + rs.getString("PROVINCE");
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getCat.close();
        }
        return primResDetails;
    }

    public String getPrimaryBillingDetailsByAppId(Connection con, String primaryApplicationId) throws Exception {
        String primBillingDetails = "";
        ResultSet rs = null;
        PreparedStatement getCat = null;

        try {

            String query = "SELECT ca.BILLINGADDRESS1, "
                    + "  ca.BILLINGADDRESS2, "
                    + "  ca.BILLINGADDRESS3, "
                    + "  ca.BILLINGCITY , "
                    + "  dp.DISTRICTNAME, "
                    + "  dp.PROVINCE "
                    + "FROM CARDAPPLICATIONPERSONALDETAILS ca "
                    + "LEFT JOIN AREA ar "
                    + "ON ca.BILLINGCITY= ar.AREACODE "
                    + "LEFT JOIN DISTRICTPROVINCE dp "
                    + "ON ar.DISTRICTCODE    = dp.DISTRICTCODE "
                    + "WHERE ca.APPLICATIONID=? ";

            getCat = con.prepareStatement(query);

            getCat.setString(1, primaryApplicationId);
            rs = getCat.executeQuery();

            while (rs.next()) {
                primBillingDetails = rs.getString("BILLINGADDRESS1")
                        + "::" + rs.getString("BILLINGADDRESS2")
                        + "::" + rs.getString("BILLINGADDRESS3")
                        + "::" + rs.getString("BILLINGCITY")
                        + "::" + rs.getString("DISTRICTNAME")
                        + "::" + rs.getString("PROVINCE");

            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getCat.close();
        }

        return primBillingDetails;
    }

    public String getPrimaryBillingDetailsByCArdNum(Connection con, String primaryCardNumber) throws Exception {
        String primBillingDetails = "";
        ResultSet rs = null;
        PreparedStatement getCat = null;
        try {
            String query = "SELECT ca.BILLINGADDRESS1,"
                    + "ca.BILLINGADDRESS2,"
                    + "ca.BILLINGADDRESS3,"
                    + "ca.BILLINGCITY ,"
                    + "DP.DISTRICTNAME,"
                    + "dp.PROVINCE "
                    + "FROM CARDAPPLICATIONPERSONALDETAILS ca "
                    + "LEFT JOIN AREA ar "
                    + "ON CA.BILLINGCITY= AR.AREACODE "
                    + "LEFT JOIN DISTRICTPROVINCE dp "
                    + "ON AR.DISTRICTCODE = DP.DISTRICTCODE "
                    + "LEFT JOIN CARD CD "
                    + "ON CA.APPLICATIONID = CD.APPLICATIONID "
                    + "WHERE cd.CARDNUMBER =?";
            getCat = con.prepareStatement(query);
            getCat.setString(1, primaryCardNumber);
            rs = getCat.executeQuery();
            while (rs.next()) {
                primBillingDetails = rs.getString("BILLINGADDRESS1")
                        + "::" + rs.getString("BILLINGADDRESS2")
                        + "::" + rs.getString("BILLINGADDRESS3")
                        + "::" + rs.getString("BILLINGCITY")
                        + "::" + rs.getString("DISTRICTNAME")
                        + "::" + rs.getString("PROVINCE");
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getCat.close();
        }
        return primBillingDetails;
    }

    public boolean updateCardApplicationDocumentForSupplementaryUser(String applicationId, String primeApplicationId, Connection CMSCon) throws Exception {

        PreparedStatement stmt = null;
        String staffStatus = "NO";
        ResultSet rs = null;

        try {

            stmt = CMSCon.prepareStatement("SELECT C.STAFFSTATUS "
                    + " FROM CARDAPPLICATION C WHERE C.APPLICATIONID=?");

            stmt.setString(1, primeApplicationId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                staffStatus = rs.getString("STAFFSTATUS");
            }

            stmt = CMSCon.prepareStatement("UPDATE CARDAPPLICATION SET STAFFSTATUS = ? WHERE APPLICATIONID = ?");
            stmt.setString(1, staffStatus);
            stmt.setString(2, applicationId);
            stmt.executeUpdate();

        } catch (SQLException sqlex) {
            throw sqlex;
        } catch (Exception ex) {
            throw ex;
        } finally {

            try {
                stmt.close();
                rs.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return true;
    }

    public String getPrimaryCardNumber(Connection con, String primaryApplicationId) throws Exception {
        String primaryCardNo = "";
        ResultSet rs = null;
        PreparedStatement getCardNo = null;

        try {
            String sql = "SELECT CARDNUMBER FROM CARD WHERE APPLICATIONID = ?";

            getCardNo = con.prepareStatement(sql);

            getCardNo.setString(1, primaryApplicationId);
            rs = getCardNo.executeQuery();

            while (rs.next()) {
                primaryCardNo = rs.getString("CARDNUMBER");

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getCardNo.close();
        }

        return primaryCardNo;
    }

    //get primary application id
    public String getPrimaryApplicationId(Connection con, String primaryCardNum) throws Exception {
        String primaryAppId = "";
        ResultSet rs = null;
        PreparedStatement getAppId = null;
        try {
            String sql = "SELECT APPLICATIONID FROM CARD WHERE CARDNUMBER=?";
            getAppId = con.prepareStatement(sql);
            getAppId.setString(1, primaryCardNum);
            rs = getAppId.executeQuery();
            while (rs.next()) {
                primaryAppId = rs.getString("APPLICATIONID");
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAppId.close();
        }
        return primaryAppId;
    }

    //get PrimaryId
    public String getPrimaryIdDetails(Connection con, String primaryAppId) throws Exception {
        String primaryIdData = "";
        ResultSet rs = null;
        PreparedStatement getPrimId = null;
        try {
            String sql = "SELECT IDENTIFICATIONNO,IDENTIFICATIONTYPE FROM CARDAPPLICATION WHERE APPLICATIONID=?";
            getPrimId = con.prepareStatement(sql);
            getPrimId.setString(1, primaryAppId);
            rs = getPrimId.executeQuery();
            while (rs.next()) {
                primaryIdData = rs.getString("IDENTIFICATIONNO") + ":" + rs.getString("IDENTIFICATIONTYPE");
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getPrimId.close();
        }
        return primaryIdData;
    }

    //get pcredit limit of primary card 
    public String getPrimaryCardCreditLimit(Connection con, String primaryCardNum) throws Exception {
        String cLimit = "";
        ResultSet rs = null;
        PreparedStatement getCreditLimit = null;
        try {
            String sql = "SELECT CREDITLIMIT FROM CARD WHERE CARDNUMBER = ?";
            getCreditLimit = con.prepareStatement(sql);
            getCreditLimit.setString(1, primaryCardNum);
            rs = getCreditLimit.executeQuery();
            while (rs.next()) {
                cLimit = rs.getString("CREDITLIMIT");
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getCreditLimit.close();
        }
        return cLimit;
    }

    public List<EstablishmentDetailsBean> getEstablishedCompanyList(Connection con) throws Exception {

        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        List<EstablishmentDetailsBean> list = new ArrayList<EstablishmentDetailsBean>();
        try {
            stmt = con.prepareStatement("SELECT DISTINCT E.NAMEOFTHECOMPANY,E.BUSINESSREGNUMBER "
                    + "FROM ESTABLISHMENTDETAILS E, CARD C "
                    + "WHERE E.APPLICATIONID =C.APPLICATIONID ");
            rs = stmt.executeQuery();

            while (rs.next()) {
                EstablishmentDetailsBean bean = new EstablishmentDetailsBean();
                bean.setCompanyName(rs.getString("NAMEOFTHECOMPANY"));
                bean.setBusinessRegNumber(rs.getString("BUSINESSREGNUMBER"));

                list.add(bean);
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

    public EstablishmentDetailsBean getAllDetailsEstablishment(Connection con, String applicationId) throws Exception {

        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query
        EstablishmentDetailsBean establishmentDetailsBean = new EstablishmentDetailsBean();

        try {

            strSqlBasic = "SELECT ED.*, CP.DESCRIPTION CARDPRODUCTDES,C.DESCRIPTION AS CURRENCYDES,CT.DESCRIPTION AS CARDTYPEDES "
                    + "FROM ESTABLISHMENTDETAILS ED "
                    + "LEFT JOIN    CARDPRODUCT CP ON ED.REQUESTCARDPRODUCT = CP.PRODUCTCODE "
                    + "LEFT JOIN    CURRENCY C ON ED.CURRENCYTYPE = C.CURRENCYNUMCODE "
                    + "LEFT JOIN    CARDTYPE CT ON CT.CARDTYPECODE = ED.REQUESTCARDTYPE "
                    + "WHERE ED.APPLICATIONID=?";

            stmt = con.prepareStatement(strSqlBasic);
            stmt.setString(1, applicationId);

            rs = stmt.executeQuery();

            while (rs.next()) {

                establishmentDetailsBean.setCompanyName(rs.getString("NAMEOFTHECOMPANY"));
                establishmentDetailsBean.setNatureOfTheBusiness(rs.getString("NATUREOFBUSINESS"));
                establishmentDetailsBean.setAnnualTurnover(rs.getString("ANNUALTURNOVER"));
                establishmentDetailsBean.setShareCapital(rs.getString("SHARECAPITAL"));
                establishmentDetailsBean.setNetProfit(rs.getString("NETPROFIT"));
                establishmentDetailsBean.setNetAssets(rs.getString("NETASSETS"));

                establishmentDetailsBean.setApplicationId(rs.getString("APPLICATIONID"));
                establishmentDetailsBean.setAddress1(rs.getString("BUSINESSADDRESS1"));
                establishmentDetailsBean.setAddress2(rs.getString("BUSINESSADDRESS2"));
                establishmentDetailsBean.setAddress3(rs.getString("BUSINESSADDRESS3"));
                establishmentDetailsBean.setContactPersLandTelNumber(rs.getString("CONTACTNUMBERSLAND"));
                establishmentDetailsBean.setContactPersMobileNumber(rs.getString("CONTACTNUMBERSMOBILE"));
                establishmentDetailsBean.setEmail(rs.getString("CONTACTEMAIL"));
                establishmentDetailsBean.setFaxNo(rs.getString("CONTACTFAXNUMBER"));
                establishmentDetailsBean.setCardType(rs.getString("REQUESTCARDTYPE"));
                establishmentDetailsBean.setCardProduct(rs.getString("REQUESTCARDPRODUCT"));
                establishmentDetailsBean.setCreditLimit(rs.getString("REQUESTCREDITLIMIT"));
                establishmentDetailsBean.setCardCurrency(rs.getString("CURRENCYTYPE"));
                establishmentDetailsBean.setContactPersonFullName(rs.getString("CONTACTPERSON"));
                establishmentDetailsBean.setBusinessRegNumber(rs.getString("BUSINESSREGNUMBER"));

                establishmentDetailsBean.setCardProductDes(rs.getString("CARDPRODUCTDES"));
                establishmentDetailsBean.setCurrencyDes(rs.getString("CURRENCYDES"));
                establishmentDetailsBean.setCardTypeDes(rs.getString("CARDTYPEDES"));

                IdBean bean = new IdBean();
                bean = this.getIdentifyDetails(con, applicationId);
                establishmentDetailsBean.setIdentificationNumber(bean.getIdNumber());
                establishmentDetailsBean.setIdentificationType(bean.getIdType());

            }

        } catch (SQLException exSql) {
            throw exSql;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }

        return establishmentDetailsBean;
    }

    public int updateEstablishmentDetailRecord(Connection con, EstablishmentDetailsBean establishmentDetailsBean) throws Exception {
        int resultId;

        PreparedStatement upsateStat = null;
        try {
            upsateStat = con.prepareStatement("UPDATE ESTABLISHMENTDETAILS SET "
                    + " BUSINESSADDRESS1=? ,BUSINESSADDRESS2=?,BUSINESSADDRESS3=? ,"
                    + " CONTACTNUMBERSLAND=? ,CONTACTNUMBERSMOBILE=?,CONTACTEMAIL=?,CONTACTFAXNUMBER=? ,"
                    + " REQUESTCARDTYPE=?,REQUESTCARDPRODUCT=?,REQUESTCREDITLIMIT=?,CURRENCYTYPE=? ,"
                    + " SHARECAPITAL=?,NETPROFIT=?,NETASSETS=?,ANNUALTURNOVER=? ,"
                    + " NAMEOFTHECOMPANY=?,NATUREOFBUSINESS=?,CONTACTPERSON=? "
                    + " WHERE APPLICATIONID=? AND BUSINESSREGNUMBER=? ");

            upsateStat.setString(1, establishmentDetailsBean.getAddress1());
            upsateStat.setString(2, establishmentDetailsBean.getAddress2());
            upsateStat.setString(3, establishmentDetailsBean.getAddress3());

            upsateStat.setString(4, establishmentDetailsBean.getContactPersLandTelNumber());
            upsateStat.setString(5, establishmentDetailsBean.getContactPersMobileNumber());
            upsateStat.setString(6, establishmentDetailsBean.getEmail());
            upsateStat.setString(7, establishmentDetailsBean.getFaxNo());
            upsateStat.setString(8, establishmentDetailsBean.getCardType());
            upsateStat.setString(9, establishmentDetailsBean.getCardProduct());
            upsateStat.setString(10, establishmentDetailsBean.getCreditLimit());
            upsateStat.setString(11, establishmentDetailsBean.getCardCurrency());

            upsateStat.setString(12, establishmentDetailsBean.getShareCapital());
            upsateStat.setString(13, establishmentDetailsBean.getNetProfit());
            upsateStat.setString(14, establishmentDetailsBean.getNetAssets());
            upsateStat.setString(15, establishmentDetailsBean.getAnnualTurnover());

            upsateStat.setString(16, establishmentDetailsBean.getCompanyName());
            upsateStat.setString(17, establishmentDetailsBean.getNatureOfTheBusiness());
            upsateStat.setString(18, establishmentDetailsBean.getContactPersonFullName());

            upsateStat.setString(19, establishmentDetailsBean.getApplicationId());
            upsateStat.setString(20, establishmentDetailsBean.getIdentificationNumber());

            resultId = upsateStat.executeUpdate();

        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                upsateStat.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;
    }

    public List<AssetLiabilityTypeBean> getAssetsLiabilityTypeList(Connection CMSCon) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        assetsLiabilityTypeList = new ArrayList<AssetLiabilityTypeBean>();
        try {
            stmt = CMSCon.prepareStatement("SELECT DISTINCT ALT.TYPECODE,ALT.DESCRIPTION  "
                    + "FROM ASSETORLIABILITYTYPE ALT WHERE ALT.STATUS ='ACT' ");
            rs = stmt.executeQuery();

            while (rs.next()) {
                AssetLiabilityTypeBean bean = new AssetLiabilityTypeBean();
                bean.setTypeCode(rs.getString("TYPECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                assetsLiabilityTypeList.add(bean);
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

        return assetsLiabilityTypeList;
    }

    public List<AssetBean> getAssetsList(Connection CMSCon) throws Exception {

        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        assetsList = new ArrayList<AssetBean>();
        try {
            stmt = CMSCon.prepareStatement("SELECT DISTINCT AST.ASSETCODE,AST.DESCRIPTION  "
                    + "FROM ASSET AST WHERE AST.STATUS ='ACT' ");
            rs = stmt.executeQuery();

            while (rs.next()) {
                AssetBean bean = new AssetBean();
                bean.setAssetCode(rs.getString("ASSETCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                assetsList.add(bean);
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

        return assetsList;
    }

    public List<LiabilityBean> getLiabilityList(Connection CMSCon) throws Exception {

        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        liabilityList = new ArrayList<LiabilityBean>();
        try {
            stmt = CMSCon.prepareStatement("SELECT DISTINCT LBT.LIABILITYCODE,LBT.DESCRIPTION  "
                    + "FROM LIABILITY LBT WHERE LBT.STATUS ='ACT' ");
            rs = stmt.executeQuery();

            while (rs.next()) {
                LiabilityBean bean = new LiabilityBean();
                bean.setLiabilityCode(rs.getString("LIABILITYCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                liabilityList.add(bean);
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

        return liabilityList;

    }

    public int updateEstablishmentAssetDetailsRecords(Connection con, List<EstablishmentAssetsBean> accountList, String businessRegNumber) throws Exception {
        int resultId = 1;
        PreparedStatement insertStat = null;
        try {

            insertStat = con.prepareStatement("DELETE FROM ESTABLISHMENTASSETS WHERE BUSINESSREGNUMBER=?");
            insertStat.setString(1, businessRegNumber);
            insertStat.executeUpdate();

            for (int i = 0; i < accountList.size(); i++) {

                insertStat = con.prepareStatement("INSERT INTO ESTABLISHMENTASSETS "
                        + "(BUSINESSREGNUMBER,ASSETTYPECODE,ASSETCODE,VALUE,STATUS,CREATEDDATE,LASTUPDATEDDATE,LASTUPDATEDUSER ) "
                        + " VALUES (?,?,?,?,?,SYSDATE,SYSDATE,?) ");

                insertStat.setString(1, accountList.get(i).getBusinessRegNumber());
                insertStat.setString(2, accountList.get(i).getAssetTypeCode());
                insertStat.setString(3, accountList.get(i).getAssetCode());
                insertStat.setString(4, accountList.get(i).getAssetValue());
                insertStat.setString(5, StatusVarList.ACTIVE_STATUS);
                insertStat.setString(6, accountList.get(i).getLastUpdateduser());

                int j = 0;
                j = insertStat.executeUpdate();
                if (j == 0) {
                    resultId = 0;
                    throw new Exception();
                }

            }

        } catch (Exception ex) {
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

    public int insertEstablishmentAssetDetailsRecords(Connection con, List<EstablishmentAssetsBean> accountList) throws Exception {
        int resultId = 1;

        PreparedStatement insertStat = null;
        try {

            for (int i = 0; i < accountList.size(); i++) {

                insertStat = con.prepareStatement("INSERT INTO ESTABLISHMENTASSETS "
                        + "(BUSINESSREGNUMBER,ASSETTYPECODE,ASSETCODE,VALUE,STATUS,CREATEDDATE,LASTUPDATEDDATE,LASTUPDATEDUSER ) "
                        + " VALUES (?,?,?,?,?,SYSDATE,SYSDATE,?) ");

                insertStat.setString(1, accountList.get(i).getBusinessRegNumber());
                insertStat.setString(2, accountList.get(i).getAssetTypeCode());
                insertStat.setString(3, accountList.get(i).getAssetCode());
                insertStat.setInt(4, Integer.parseInt(accountList.get(i).getAssetValue()));
                insertStat.setString(5, StatusVarList.ACTIVE_STATUS);
                insertStat.setString(6, accountList.get(i).getLastUpdateduser());

                int j = 0;
                j = insertStat.executeUpdate();
                if (j == 0) {
                    resultId = 0;
                    throw new Exception();
                }

            }

        } catch (Exception ex) {
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

    public int updateEstablishmentLiabilityDetailsRecords(Connection con, List<EstablishmentLiabilityBean> accountList, String businessRegNumber) throws Exception {
        int resultId = 1;

        PreparedStatement insertStat = null;
        try {

            insertStat = con.prepareStatement("DELETE FROM ESTABLISHMENTLIABILITY WHERE BUSINESSREGNUMBER=?");
            insertStat.setString(1, businessRegNumber);
            insertStat.executeUpdate();

            for (int i = 0; i < accountList.size(); i++) {

                insertStat = con.prepareStatement("INSERT INTO ESTABLISHMENTLIABILITY "
                        + "(BUSINESSREGNUMBER,LIABILITYTYPECODE,LIABILITYCODE,VALUE,STATUS,CREATEDDATE,LASTUPDATEDDATE,LASTUPDATEDUSER ) "
                        + " VALUES (?,?,?,?,?,SYSDATE,SYSDATE,?) ");

                insertStat.setString(1, accountList.get(i).getBusinessRegNumber());
                insertStat.setString(2, accountList.get(i).getLiabilityTypeCode());
                insertStat.setString(3, accountList.get(i).getLiabilityCode());
                insertStat.setString(4, accountList.get(i).getLiabilityValue());
                insertStat.setString(5, StatusVarList.ACTIVE_STATUS);
                insertStat.setString(6, accountList.get(i).getLastUpdateduser());

                int j = 0;
                j = insertStat.executeUpdate();
                if (j == 0) {
                    resultId = 0;
                    throw new Exception();
                }

            }

        } catch (Exception ex) {
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

    public int insertEstablishmentLiabilityDetailsRecords(Connection con, List<EstablishmentLiabilityBean> accountList) throws Exception {
        int resultId = 1;

        PreparedStatement insertStat = null;
        try {

            for (int i = 0; i < accountList.size(); i++) {

                insertStat = con.prepareStatement("INSERT INTO ESTABLISHMENTLIABILITY "
                        + "(BUSINESSREGNUMBER,LIABILITYTYPECODE,LIABILITYCODE,VALUE,STATUS,CREATEDDATE,LASTUPDATEDDATE,LASTUPDATEDUSER ) "
                        + " VALUES (?,?,?,?,?,SYSDATE,SYSDATE,?) ");

                insertStat.setString(1, accountList.get(i).getBusinessRegNumber());
                insertStat.setString(2, accountList.get(i).getLiabilityTypeCode());
                insertStat.setString(3, accountList.get(i).getLiabilityCode());
                insertStat.setString(4, accountList.get(i).getLiabilityValue());
                insertStat.setString(5, StatusVarList.ACTIVE_STATUS);
                insertStat.setString(6, accountList.get(i).getLastUpdateduser());

                int j = 0;
                j = insertStat.executeUpdate();
                if (j == 0) {
                    resultId = 0;
                    throw new Exception();
                }

            }

        } catch (Exception ex) {
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

    public String getPrimaryCardNoFromApplication(Connection con, String primaryId) throws Exception {

        PreparedStatement stmt = null;
        String strSqlBasic = "";
        ResultSet rs = null;
        String primaryCardNo = "";

        try {
            strSqlBasic = "SELECT CARDNUMBER FROM CARDAPPLICATION WHERE IDENTIFICATIONNO = ? ";

            stmt = con.prepareStatement(strSqlBasic);
            stmt.setString(1, primaryId);

            rs = stmt.executeQuery();

            while (rs.next()) {
                primaryCardNo = rs.getString("CARDNUMBER");
            }

        } catch (SQLException exSql) {
            throw exSql;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }

        return primaryCardNo;
    }

    public EstablishmentDetailsBean getAllDetailsEstablishmentByBusinessRegNumber(Connection CMSCon, String businessRegNumber) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query
        EstablishmentDetailsBean establishmentDetailsBean = new EstablishmentDetailsBean();

        try {

            strSqlBasic = "SELECT ED.*, CP.DESCRIPTION CARDPRODUCTDES,C.DESCRIPTION AS CURRENCYDES,CT.DESCRIPTION AS CARDTYPEDES "
                    + "FROM ESTABLISHMENTDETAILS ED "
                    + "LEFT JOIN    CARDPRODUCT CP ON ED.REQUESTCARDPRODUCT = CP.PRODUCTCODE "
                    + "LEFT JOIN    CURRENCY C ON ED.CURRENCYTYPE = C.CURRENCYNUMCODE "
                    + "LEFT JOIN    CARDTYPE CT ON CT.CARDTYPECODE = ED.REQUESTCARDTYPE "
                    + "WHERE ED.BUSINESSREGNUMBER=?";

            stmt = CMSCon.prepareStatement(strSqlBasic);
            stmt.setString(1, businessRegNumber);

            rs = stmt.executeQuery();

            while (rs.next()) {

                establishmentDetailsBean.setCompanyName(rs.getString("NAMEOFTHECOMPANY"));
                establishmentDetailsBean.setNatureOfTheBusiness(rs.getString("NATUREOFBUSINESS"));
                establishmentDetailsBean.setAnnualTurnover(rs.getString("ANNUALTURNOVER"));
                establishmentDetailsBean.setShareCapital(rs.getString("SHARECAPITAL"));
                establishmentDetailsBean.setNetProfit(rs.getString("NETPROFIT"));
                establishmentDetailsBean.setNetAssets(rs.getString("NETASSETS"));

                establishmentDetailsBean.setApplicationId(rs.getString("APPLICATIONID"));
                establishmentDetailsBean.setAddress1(rs.getString("BUSINESSADDRESS1"));
                establishmentDetailsBean.setAddress2(rs.getString("BUSINESSADDRESS2"));
                establishmentDetailsBean.setAddress3(rs.getString("BUSINESSADDRESS3"));
                establishmentDetailsBean.setContactPersLandTelNumber(rs.getString("CONTACTNUMBERSLAND"));
                establishmentDetailsBean.setContactPersMobileNumber(rs.getString("CONTACTNUMBERSMOBILE"));
                establishmentDetailsBean.setEmail(rs.getString("CONTACTEMAIL"));
                establishmentDetailsBean.setFaxNo(rs.getString("CONTACTFAXNUMBER"));
                establishmentDetailsBean.setCardType(rs.getString("REQUESTCARDTYPE"));
                establishmentDetailsBean.setCardProduct(rs.getString("REQUESTCARDPRODUCT"));
                establishmentDetailsBean.setCreditLimit(rs.getString("REQUESTCREDITLIMIT"));
                establishmentDetailsBean.setCardCurrency(rs.getString("CURRENCYTYPE"));
                establishmentDetailsBean.setContactPersonFullName(rs.getString("CONTACTPERSON"));
                establishmentDetailsBean.setBusinessRegNumber(rs.getString("BUSINESSREGNUMBER"));

                establishmentDetailsBean.setCardProductDes(rs.getString("CARDPRODUCTDES"));
                establishmentDetailsBean.setCurrencyDes(rs.getString("CURRENCYDES"));
                establishmentDetailsBean.setCardTypeDes(rs.getString("CARDTYPEDES"));

            }

        } catch (SQLException exSql) {
            throw exSql;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }

        return establishmentDetailsBean;
    }

    public CustomerPersonalBean getSupplementaryCustomerDetails(Connection con, String applicationId) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query

        try {

            strSqlBasic = "SELECT cd.* FROM SUPPLEMENTARYAPPLICATION cd WHERE cd.APPLICATIONID=?";

            stmt = con.prepareStatement(strSqlBasic);
            stmt.setString(1, applicationId);

            rs = stmt.executeQuery();

            while (rs.next()) {

                customerPersonalBean = new CustomerPersonalBean();

                customerPersonalBean.setApplicationId(rs.getString("APPLICATIONID"));
                customerPersonalBean.setTitle(rs.getString("TITLE"));
                customerPersonalBean.setFirstName(rs.getString("FIRSTNAME"));
                customerPersonalBean.setLastName(rs.getString("LASTNAME"));
                customerPersonalBean.setMiddleName(rs.getString("MIDDLENAME"));
                customerPersonalBean.setBirthday(rs.getString("DATEOFBIRTH"));
                customerPersonalBean.setGender(rs.getString("GENDER"));
                customerPersonalBean.setNic(rs.getString("NIC"));
                customerPersonalBean.setPassportNumber(rs.getString("PASSPORTNUMBER"));
                customerPersonalBean.setNationality(rs.getString("NATIONALITY"));
                //customerPersonalBean.setRelationShip(rs.getString("RELATIONSHIP"));
                customerPersonalBean.setNameOncard(rs.getString("NAMEONCARD"));
                customerPersonalBean.setAddress1(rs.getString("ADDRESS1"));
                customerPersonalBean.setAddress2(rs.getString("ADDRESS2"));
                customerPersonalBean.setAddress3(rs.getString("ADDRESS3"));
                customerPersonalBean.setCity(rs.getString("CITY"));
                customerPersonalBean.setPostalcode(rs.getString("POSTALCODE"));
                //customerPersonalBean.setPrimaryCardNumber(rs.getString("PRIMARYCARDNUMBER"));
                //resultCustomer.setp(rs.getString("PRIMARYITTYPE"));
                //customerPersonalBean.setPrimaryId(rs.getString("PRIMARYID"));
                customerPersonalBean.setHomeTelNumber(rs.getString("HOMETELEPHONE"));
                customerPersonalBean.setMobileNumber(rs.getString("MOBILE"));
                //customerPersonalBean.setAdressSameAsPrimary(rs.getString("ADDRESSSAMEASPRIMARY"));
                //customerPersonalBean.setOccupation(rs.getString("OCCUPATION"));
                //customerPersonalBean.setEmployementType(rs.getString("EMPLOYMENTTYPE"));
                customerPersonalBean.setCardType(rs.getString("REQUESTCARDTYPE"));
                customerPersonalBean.setCardProduct(rs.getString("REQUESTCARDPRODUCT"));
                customerPersonalBean.setCreditLimit(rs.getString("REQUESTCREDITLIMIT"));
                //customerPersonalBean.setNameWithinitials(rs.getString("NAMEWITHINITIAL"));
                //resultCustomer.set(rs.getString("STATEMENTSTATUS"));
                customerPersonalBean.setBillAddress1(rs.getString("BILLINGADDRESS1"));
                customerPersonalBean.setBillAddress2(rs.getString("BILLINGADDRESS2"));
                customerPersonalBean.setBillAddress3(rs.getString("BILLINGADDRESS3"));
                customerPersonalBean.setBillCity(rs.getString("BILLINGCITY"));
                //customerPersonalBean.setPrimaryCardApplicationId(rs.getString("PRIMARYAPPLICATIONID"));

                //customerPersonalBean.setClimitReqType(rs.getString("CLPERORFIXED"));
                //customerPersonalBean.setPercentageValue(rs.getString("PERCENTAGECREDITLIMIT"));

                if (customerPersonalBean.getNic() != null) {
                    customerPersonalBean.setIdentificationNumber(customerPersonalBean.getNic());
                }
                if (customerPersonalBean.getPassportNumber() != null) {
                    customerPersonalBean.setIdentificationNumber(customerPersonalBean.getPassportNumber());
                }

                if (customerPersonalBean.getCity() != null) {
                    String result = this.getDistrictAndProvince(con, customerPersonalBean.getCity());
                    String[] array = result.split(",");
                    customerPersonalBean.setResDistrict(array[0]);
                    customerPersonalBean.setResProvince(array[1]);
                }
                if (customerPersonalBean.getBillCity() != null) {
                    String result = this.getDistrictAndProvince(con, customerPersonalBean.getBillCity());
                    String[] array = result.split(",");
                    customerPersonalBean.setBillDistrict(array[0]);
                    customerPersonalBean.setBillProvince(array[1]);
                }
            }

        } catch (SQLException exSql) {
            throw exSql;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }

        return customerPersonalBean;
    }

    public CustomerPersonalBean getEstablishmentCustomerDetails(Connection CMSCon, String applicationId) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query
        CustomerPersonalBean customerPersonalBean = new CustomerPersonalBean();

        try {

            strSqlBasic = "SELECT ED.*, CP.DESCRIPTION CARDPRODUCTDES,C.DESCRIPTION AS CURRENCYDES,CT.DESCRIPTION AS CARDTYPEDES "
                    + "FROM ESTABLISHMENTDETAILS ED "
                    + "LEFT JOIN    CARDPRODUCT CP ON ED.REQUESTCARDPRODUCT = CP.PRODUCTCODE "
                    + "LEFT JOIN    CURRENCY C ON ED.CURRENCYTYPE = C.CURRENCYNUMCODE "
                    + "LEFT JOIN    CARDTYPE CT ON CT.CARDTYPECODE = ED.REQUESTCARDTYPE "
                    + "WHERE ED.APPLICATIONID=?";

            stmt = CMSCon.prepareStatement(strSqlBasic);
            stmt.setString(1, applicationId);

            rs = stmt.executeQuery();

            while (rs.next()) {

                customerPersonalBean.setCompanyName(rs.getString("NAMEOFTHECOMPANY"));              
                customerPersonalBean.setApplicationId(rs.getString("APPLICATIONID"));
                customerPersonalBean.setCompanyAddress1(rs.getString("BUSINESSADDRESS1"));
                customerPersonalBean.setCompanyAddress2(rs.getString("BUSINESSADDRESS2"));
                customerPersonalBean.setCompanyAddress3(rs.getString("BUSINESSADDRESS3"));
                customerPersonalBean.setEstLandNo(rs.getString("CONTACTNUMBERSLAND"));
                customerPersonalBean.setEstMobileNo(rs.getString("CONTACTNUMBERSMOBILE"));
                customerPersonalBean.setEmail(rs.getString("CONTACTEMAIL"));
                customerPersonalBean.setFaxNo(rs.getString("CONTACTFAXNUMBER"));
                customerPersonalBean.setCardType(rs.getString("REQUESTCARDTYPE"));
                customerPersonalBean.setCardProduct(rs.getString("REQUESTCARDPRODUCT"));
                customerPersonalBean.setCreditLimit(rs.getString("REQUESTCREDITLIMIT"));
                customerPersonalBean.setCardCurrency(rs.getString("CURRENCYTYPE"));
                customerPersonalBean.setFullName(rs.getString("CONTACTPERSON"));
                customerPersonalBean.setBusinessRegNumber(rs.getString("BUSINESSREGNUMBER"));
                customerPersonalBean.setIdentificationNumber(rs.getString("BUSINESSREGNUMBER"));
                customerPersonalBean.setCardProductDes(rs.getString("CARDPRODUCTDES"));
                customerPersonalBean.setCurrencyDes(rs.getString("CURRENCYDES"));
                customerPersonalBean.setCardTypeDes(rs.getString("CARDTYPEDES"));

            }

        } catch (SQLException exSql) {
            throw exSql;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }

        return customerPersonalBean;
    }
}
