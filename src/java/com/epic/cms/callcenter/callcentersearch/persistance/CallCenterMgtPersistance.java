/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.callcentersearch.persistance;

import com.epic.cms.admin.controlpanel.securityquesmgt.bean.SecurityQuestionBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SectionBean;
import com.epic.cms.application.common.persistance.StatusPersistence;
import com.epic.cms.callcenter.callcentersearch.bean.CallHistoryBean;
import com.epic.cms.callcenter.callcentersearch.bean.CallLogBean;
import com.epic.cms.callcenter.callcentersearch.bean.CustomerSearchBean;
import com.epic.cms.callcenter.callcentersearch.bean.MerchantSearchBean;
import com.epic.cms.callcenter.callcentersearch.bean.QuestionAnswerBean;
import com.epic.cms.callcenter.callcentersearch.bean.StandingOrderBean;
import com.epic.cms.callcenter.callcentersearch.bean.TransactionBean;
import com.epic.cms.callcenter.callcentersearch.bean.ViewDataBean;
import com.epic.cms.callcenter.callcentersearch.bean.ViewMerchantCustomerBean;
import com.epic.cms.callcenter.callcentersearch.bean.ViewMerchantLocationBean;
import com.epic.cms.callcenter.callcentersearch.bean.ViewTerminalBean;
import com.epic.cms.callcenter.customer.bean.CustomerContactDetailsChngeHolderBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author badrika
 */
public class CallCenterMgtPersistance {

    private ViewDataBean viewBean;
    private CustomerSearchBean advancedBean;
    List<ViewDataBean> appList, cusList, accList, cardList;//viewList
    private List<CustomerSearchBean> advancedList;
    private List<ViewMerchantCustomerBean> mercusList;
    private List<ViewMerchantLocationBean> locList;
    private List<ViewTerminalBean> terList;
    private List<SecurityQuestionBean> questionBeanList;
    List<QuestionAnswerBean> answerBeanList;
    private Map<SectionBean, List<PageBean>> sectionPageList = null;
    private ViewMerchantCustomerBean mercusBean;
    private ViewMerchantLocationBean locBean;
    private ViewTerminalBean terBean;
    private List<TransactionBean> txnList = null;

    public Map<SectionBean, List<PageBean>> getSectionPageList(Connection con, String userRole, String application, String sectionCode) throws Exception {

        ResultSet rs = null;

        PreparedStatement sectionPageStat = null;
        sectionPageList = new Hashtable<SectionBean, List<PageBean>>();
        try {
            sectionPageStat = con.prepareStatement("SELECT USP.USERROLECODE,USP.SECTIONCODE,SEC.DESCRIPTION,SEC.SORTKEY as sectionsortkey,PG.PAGECODE,PG.DESCRIPTION AS PAGEDESCRIPTION,PG.URL,PG.ROOT,PG.SORTKEY "
                    + "FROM USERAPPLICATIONSECTION UAS,APPLICATION APP,USERSECTIONPAGE USP,SECTION SEC,PAGE PG,STATUS IST,USERROLE UR "
                    + "WHERE UAS.USERROLECODE=? AND UAS.APPLICATIONCODE=? AND UAS.SECTIONCODE=? AND UAS.APPLICATIONCODE=APP.APPLICATIONCODE AND UAS.SECTIONCODE=USP.SECTIONCODE AND USP.SECTIONCODE=SEC.SECTIONCODE AND USP.PAGECODE=PG.PAGECODE AND "
                    + "APP.STATUS=IST.STATUSCODE AND SEC.STATUS=IST.STATUSCODE AND PG.STATUS=IST.STATUSCODE AND USP.USERROLECODE= UR.USERROLE AND USP.USERROLECODE=? "
                    + "ORDER BY SEC.SORTKEY,SEC.SECTIONCODE, PG.SORTKEY ");

            sectionPageStat.setString(1, userRole);
            sectionPageStat.setString(2, application);
            sectionPageStat.setString(3, sectionCode);
            sectionPageStat.setString(4, userRole);

            rs = sectionPageStat.executeQuery();
            String currentSection = "";
            List<PageBean> pageList = null;
            SectionBean section = null;
            while (rs.next()) {
                if (!rs.getString("SECTIONCODE").equals(currentSection)) {
                    currentSection = rs.getString("SECTIONCODE");
                    if (pageList != null && section != null) {
                        sectionPageList.put(section, pageList);
                        pageList = null;
                        section = null;
                    }
                    section = new SectionBean();
                    section.setSectionCode(rs.getString("SECTIONCODE"));
                    section.setDescription(rs.getString("DESCRIPTION"));
                    section.setSortKey(rs.getString("sectionsortkey"));

                    pageList = new ArrayList<PageBean>();
                    PageBean page = new PageBean();
                    page.setPageCode(rs.getString("PAGECODE"));
                    page.setDescription(rs.getString("PAGEDESCRIPTION"));
                    page.setRoot(rs.getString("ROOT"));
                    page.setUrl(rs.getString("URL"));
                    page.setSortKey(rs.getString("SORTKEY"));

                    pageList.add(page);

                } else {

                    PageBean page = new PageBean();
                    page.setPageCode(rs.getString("PAGECODE"));
                    page.setDescription(rs.getString("PAGEDESCRIPTION"));
                    page.setRoot(rs.getString("ROOT"));
                    page.setUrl(rs.getString("URL"));
                    page.setSortKey(rs.getString("SORTKEY"));

                    pageList.add(page);
                }
            }
            if (pageList != null && section != null) {
                sectionPageList.put(section, pageList);
                pageList = null;
                section = null;
            }
            return sectionPageList;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    sectionPageStat.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    //searchApplication
    public List<ViewDataBean> searchApplication(Connection con, CustomerSearchBean filledBean) throws SQLException, Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;

        appList = new ArrayList<ViewDataBean>();

        try {

            String query2 = " SELECT DISTINCT CAP.APPLICATIONID AS APPID,CAP.IDENTIFICATIONTYPE,CAP.IDENTIFICATIONNO,CAP.PRIORITYLEVELCODE,"
                    + "CAP.BRANCHCODE,CAP.CREDITSCORE,CAP.CARDAPPLIACTIONDOMAIN,"
                    + "CAP.SYSRECOMENDEDCREDITLIMIT,CAP.CONFIRMEDCREDITLIMIT,CAP.CONFIRMEDCARDPRODUCT,CAP.STAFFSTATUS, CAP.PASSPORTEXPIRYDATE, "
                    + "CAP.CARDNUMBER, CAP.ASSIGNSTATUS, CAP.ASSIGNUSER, CAP.REFERANCIALEMPNO, CAP.NETEXPENSES, CAP.NETINCOME, CAP.NETPROFIT, "
                    + "CAP.CRIBREPORT, CAP.RECOMMENDLETTER, CAP.ACCTEMPLATECODE, CAP.BINPROFILE, CAP.CARDTEMPLATECODE, CAP.CUSTEMPLATECODE, "
                    + "CAP.PRODUCTIONMODE, CAP.CARDCATEGORY, CAP.CARDKEYID, CAP.SCORECARD, CAP.SYSRECOMENDEDCARDPRODUCT, CAP.SYSRECOMENDEDCURRENCY, "
                    + "CAP.CONFIRMEDCURRENCY, CAP.LOYALTYREQUIRED, CAP.SIGNATURE, CAP.PRIMARYSIGNATURE, CAP.JOINTSIGNATURE, CAP.REJECTREASONCODE, "
                    + "CAP.REJECTREMARKS, CAP.REJECTVERIFYREMARKS,"
                    + "CAS.APPLICATIONSTATUS AS APP_STATUS, ad.documentname as idtypedes "
                    + "FROM CARDAPPLICATION CAP,CARDAPPLICATIONSTATUS CAS, CARD C, APPLICATIONDOCUMENT AD, CARDACCOUNTCUSTOMER CAC "
                    + "WHERE CAP.APPLICATIONID=CAS.APPLICATIONID and ad.documenttypecode=cap.identificationtype AND ad.CARDCATEGORYCODE= cap.CARDCATEGORY ";
//                    + "and CAP.CONFIRMEDCARDPRODUCT=CP.PRODUCTCODE";

            String condition = "";

            // String cardnum = isCardExsists(con, filledBean);
            if (!filledBean.getCardnumber().equals("") || filledBean.getCardnumber().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "C.CARDNUMBER = '" + filledBean.getCardnumber() + "' AND C.IDNUMBER=CAP.IDENTIFICATIONNO ";
            }

            if (!filledBean.getAccount().equals("") || filledBean.getAccount().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CAC.ACCOUNTNO = '" + filledBean.getAccount() + "' AND C.CARDNUMBER=CAC.CARDNUMBER AND C.IDNUMBER=CAP.IDENTIFICATIONNO ";
            }

            if (!filledBean.getNic().equals("") || filledBean.getNic().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CAP.IDENTIFICATIONNO = '" + filledBean.getNic() + "' AND CAP.IDENTIFICATIONTYPE='NIC' ";
            }

            if (!filledBean.getPassport().equals("") || filledBean.getPassport().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CAP.IDENTIFICATIONNO = '" + filledBean.getPassport() + "' AND CAP.IDENTIFICATIONTYPE='PAS' ";
            }

            if (!filledBean.getCif().equals("") || filledBean.getCif().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CAC.CUSTOMERID = '" + filledBean.getCif() + "' AND C.CARDNUMBER=CAC.CARDNUMBER AND C.IDNUMBER=CAP.IDENTIFICATIONNO ";
            }

            if (!condition.equals("")) {

                query2 += " AND " + condition;
                ps = con.prepareStatement(query2);

            }

            rs = ps.executeQuery();

            while (rs.next()) {
                viewBean = new ViewDataBean();

                //CARDAPPLICATION AS CAP
                viewBean.setAppId(rs.getString("APPID"));
                viewBean.setIdTypeCAP(rs.getString("idtypedes"));//IDENTIFICATIONTYPE
                viewBean.setIdNumberCAP(rs.getString("IDENTIFICATIONNO"));
                viewBean.setPriorityLevelCode(rs.getString("PRIORITYLEVELCODE"));
                viewBean.setBranchCode(rs.getString("BRANCHCODE"));
                viewBean.setCreditScore(rs.getString("CREDITSCORE"));
                viewBean.setSysRecCreditLimit(rs.getString("SYSRECOMENDEDCREDITLIMIT"));
                viewBean.setConfCreditLimit(rs.getString("CONFIRMEDCREDITLIMIT"));
                viewBean.setConfCardProduct(rs.getString("CONFIRMEDCARDPRODUCT"));//
//                viewBean.setConfCardProduct(rs.getString("cpdes"));//CONFIRMEDCARDPRODUCT
                viewBean.setStaffStatusCAP(rs.getString("STAFFSTATUS"));

                viewBean.setPassportexpiredate(rs.getString("PASSPORTEXPIRYDATE"));
                viewBean.setCardnumbercap(rs.getString("CARDNUMBER"));
                viewBean.setAssignstatus(rs.getString("ASSIGNSTATUS"));
                viewBean.setAssignuser(rs.getString("ASSIGNUSER"));
                viewBean.setReferancialempno(rs.getString("REFERANCIALEMPNO"));
                viewBean.setNetexpenses(rs.getString("NETEXPENSES"));
                viewBean.setNetincome(rs.getString("NETINCOME"));
                viewBean.setNetprofit(rs.getString("NETPROFIT"));
                viewBean.setCribreport(rs.getString("CRIBREPORT"));
                viewBean.setRecommendletter(rs.getString("RECOMMENDLETTER"));
                viewBean.setAcctemplatecode(rs.getString("ACCTEMPLATECODE"));
                viewBean.setBinprofile(rs.getString("BINPROFILE"));
                viewBean.setCardtemplatecode(rs.getString("CARDTEMPLATECODE"));
                viewBean.setCustemplatecode(rs.getString("CUSTEMPLATECODE"));
                viewBean.setProductionmode(rs.getString("PRODUCTIONMODE"));
                viewBean.setCardcategory(rs.getString("CARDCATEGORY"));
                viewBean.setCardkeyid(rs.getString("CARDKEYID"));
                viewBean.setScorecard(rs.getString("SCORECARD"));
                viewBean.setSysrecomendedcardproduct(rs.getString("SYSRECOMENDEDCARDPRODUCT"));
                viewBean.setSysrecomendedcurrency(rs.getString("SYSRECOMENDEDCURRENCY"));
                viewBean.setConfirmedcurrency(rs.getString("CONFIRMEDCURRENCY"));
                viewBean.setLoyaltyrequired(rs.getString("LOYALTYREQUIRED"));
                viewBean.setSignature(rs.getString("SIGNATURE"));
                viewBean.setPrimarysignature(rs.getString("PRIMARYSIGNATURE"));
                viewBean.setJointsignature(rs.getString("JOINTSIGNATURE"));
                viewBean.setRejectreasoncode(rs.getString("REJECTREASONCODE"));
                viewBean.setRejectremarks(rs.getString("REJECTREMARKS"));
                viewBean.setRejectverifyremarks(rs.getString("REJECTVERIFYREMARKS"));

                if (rs.getString("CARDAPPLIACTIONDOMAIN").equals(StatusVarList.CREDIT)) {

                    String query1 = "select PD.TITLE,PD.INITIALS,PD.NAMEINFULL,PD.DATEOFBIRTH,"
                            + "PD.MARITALSTATUS,PD.MOBILENO,PD.EMAIL,PD.CITY,A.DESCRIPTION as citydes "
                            + "from CARDAPPLICATIONPERSONALDETAILS PD,AREA A "
                            + "where PD.APPLICATIONID=? and PD.CITY=A.AREACODE";

                    ps = con.prepareStatement(query1);
                    ps.setString(1, rs.getString("APPID"));
                    rs2 = ps.executeQuery();

                    while (rs2.next()) {
                        viewBean.setTitle(rs2.getString("TITLE"));
                        viewBean.setInitials(rs2.getString("INITIALS"));
//                        viewBean.setFirstName(rs2.getString("FIRSTNAME"));
//                        viewBean.setMiddleName(rs2.getString("MIDDLENAME"));
//                        viewBean.setLastName(rs2.getString("LASTNAME"));
                        viewBean.setDateofBirth(rs2.getString("DATEOFBIRTH"));
                        //viewBean.setGender(rs2.getString("GENDER"));
                        viewBean.setMaritalStatus(rs2.getString("MARITALSTATUS"));
                        viewBean.setMobileNo(rs2.getString("MOBILENO"));
                        viewBean.setEmail(rs2.getString("EMAIL"));
                        viewBean.setCity(rs2.getString("citydes"));//CITY
                    }
                }

                if (rs.getString("CARDAPPLIACTIONDOMAIN").equals(StatusVarList.DEBIT)) {

                    String query3 = "select PD.TITLE,PD.FIRSTNAME,PD.MIDDLENAME,PD.LASTNAME,PD.DATEOFBIRTH,"
                            + "PD.MOBILENO,PD.AREA,A.DESCRIPTION as citydes "
                            + "from DEBITCARDAPPLICATIONDETAILS PD,AREA A "
                            + "where PD.APPLICATIONID=? and PD.AREA=A.AREACODE";

                    ps = con.prepareStatement(query3);
                    ps.setString(1, rs.getString("APPID"));
                    rs3 = ps.executeQuery();

                    while (rs3.next()) {
                        viewBean.setTitle(rs3.getString("TITLE"));
                        viewBean.setFirstName(rs3.getString("FIRSTNAME"));
                        viewBean.setMiddleName(rs3.getString("MIDDLENAME"));
                        viewBean.setLastName(rs3.getString("LASTNAME"));
                        viewBean.setDateofBirth(rs3.getString("DATEOFBIRTH"));
                        viewBean.setMobileNo(rs3.getString("MOBILENO"));
                        viewBean.setCity(rs3.getString("citydes"));//AREA
                    }
                }
                //viewBean.setDesignation(rs.getString(""));
//                viewBean.setDesignation("gud luck");

//                viewBean.setStatusCAP(rs.getString("APP_STATUS"));get description one by one
                StatusPersistence sp = new StatusPersistence();
                String des = sp.getStatusDescription(con, rs.getString("APP_STATUS"));
                viewBean.setStatusCAP(des);
                /////

                appList.add(viewBean);

            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (rs2 != null) {
                    rs2.close();
                }
                if (rs3 != null) {
                    rs3.close();
                }
                ps.close();

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }

        return appList;

    }

    public List<CustomerSearchBean> searchAdvanced(Connection con, CustomerSearchBean filledBean) throws SQLException, Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;

        advancedList = new ArrayList<CustomerSearchBean>();

        try {

            String condition1 = "";
            String condition2 = "";            

            if (filledBean.getAdsearchtype().equals("adname")) {

                condition1 = " upper(CAPD.NAMEONCARD) LIKE upper('%" + filledBean.getAdsearchval() + "%') ";
                condition2 = " upper(SA.NAMEONCARD) LIKE upper('%" + filledBean.getAdsearchval() + "%') ";

            } else if (filledBean.getAdsearchtype().equals("adcard")) {

                condition1 = condition2 = " cd.cardnumber LIKE '%" + filledBean.getAdsearchval() + "%' ";

            } else if (filledBean.getAdsearchtype().equals("adnic")) {

                condition1 = " upper(CAPD.NIC) LIKE upper('%" + filledBean.getAdsearchval() + "%') ";
                condition2 = " upper(SA.NIC) LIKE upper('%" + filledBean.getAdsearchval() + "%') ";

            } else if (filledBean.getAdsearchtype().equals("adpassport")) {

                condition1 = " CAPD.PASSPORTNUMBER LIKE '%" + filledBean.getAdsearchval() + "%' ";
                condition2 = " SA.PASSPORTNUMBER LIKE '%" + filledBean.getAdsearchval() + "%' ";

            }
            
            String query = "select CAPD.NAMEONCARD, cd.cardnumber, CAPD.NIC,  CAPD.PASSPORTNUMBER "
                    + " FROM CARDAPPLICATIONPERSONALDETAILS CAPD "
                    + " left join card cd "
                    + " on cd.applicationid = CAPD.applicationid "
                    + " where " + condition1
                    + " union "
                    + " select SA.NAMEONCARD, cd.cardnumber,  SA.NIC, SA.PASSPORTNUMBER "
                    + " FROM SUPPLEMENTARYAPPLICATION SA "
                    + " left join card cd "
                    + " on cd.applicationid = SA.applicationid "
                    + " where " + condition2;

            if (!condition1.equals("") && !condition2.equals("")) {

                ps = con.prepareStatement(query);

            }

            rs = ps.executeQuery();

            while (rs.next()) {
                advancedBean = new CustomerSearchBean();

                advancedBean.setNameoncard(rs.getString("NAMEONCARD"));
                advancedBean.setCardnumber(rs.getString("cardnumber"));//IDENTIFICATIONTYPE
                advancedBean.setNic(rs.getString("NIC"));
                advancedBean.setPassport(rs.getString("PASSPORTNUMBER"));

                advancedList.add(advancedBean);

            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                ps.close();

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }

        return advancedList;

    }

    public List<ViewDataBean> searchCustomer(Connection con, CustomerSearchBean filledBean) throws SQLException, Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;

        cusList = new ArrayList<ViewDataBean>();

        try {

            String query = "SELECT DISTINCT CC.CUSTOMERID AS CC_CUSTOMERID ,CC.IDENTIFICATIONTYPE AS CC_IDENTIFICATIONTYPE,CC.IDENTIFICATIONNO AS CC_IDENTIFICATIONNO,"
                    + "CC.CUSTOMERNAME AS CC_CUSTOMERNAME,CC.CREDITLIMIT AS CC_CREDITLIMIT,CC.CASHLIMIT AS CC_CASHLIMIT,CC.CURRENCYCODE AS CC_CURRENCYCODE,CC.RISKPROFILECODE AS "
                    + "CC_RISKPROFILECODE,CC.STAFFSTATUS AS CC_STAFFSTATUS,CC.CUSTOMERSTATUS AS CC_CUSTOMERSTATUS,"
                    + "ST.DESCRIPTION AS STDES, ad.documentname as idtypedes, CUR.DESCRIPTION as curdes, "
                    + "C.ADDRESS1 AS AD1, C.ADDRESS2 AS AD2, C.ADDRESS3 AS AD3, C.ADDRESS4 AS AD4, "
                    + "PD.TITLE, PD.NAMEINFULL, PD.GENDER, PD.NATIONALITY, PD.NIC, PD.PASSPORTNUMBER, PD.PASSPORTEXPIREDATE, PD.AGE, PD.DATEOFBIRTH, "
                    + "PD.MARITALSTATUS, PD.NAMEONCARD, PD.MOTHERSMAIDENNAME, PD.EMAIL, PD.MOBILENO, PD.EMERGENCYCONTACTNO, PD.HOMETELEPHONENO, "
                    + "PD.OFFICEPHONENO, PD.ADDRESS1, PD.ADDRESS2, PD.ADDRESS3, PD.CITY, PD.RESIDEDISTRICT, PD.RESIDEPROVINCE, PD.BILLINGADDRESS1, "
                    + "PD.BILLINGADDRESS2, PD.BILLINGADDRESS3, PD.BILLINGCITY, PD.BILLINGDISTRICT, PD.BILLINGPROVINCE, PD.PERMANENTADDRESS1, "
                    + "PD.PERMANENTADDRESS2, PD.PERMANANTADDRESS3, PD.PERMANAENTCITY, PD.PERMANENTDISTRICT, PD.PERMANENTPROVINCE, PD.RELATIVENAME, "
                    + "PD.RELMOBILENO, PD.RELOFFICEPHONE, PD.RELRESIDANCEPHONE, PD.RELEMAIL, PD.RELATIONSHIP, PD.RELADDRESS1, PD.RELADDRESS2, "
                    + "PD.RELADDRESS3, PD.RELCITY, PD.RELDISTRICT, PD.RELPROVINCE, PD.SPOUSENAME, PD.SPOUSENIC, PD.SPOUSEPASSPORTNO, PD.SPOUSEPHONE, "
                    + "PD.SPOUSEEMAIL, PD.SPOUSEDATEOFBIRTH, PD.NOOFDEPENDANCE ,PD.APPLICATIONID "
                    + "FROM CARD C,CARDCUSTOMER CC, STATUS ST, CARDACCOUNTCUSTOMER CAC, APPLICATIONDOCUMENT AD, CURRENCY CUR, CARDAPPLICATIONPERSONALDETAILS PD "
                    + "WHERE rownum<=1 and C.CARDNUMBER=CAC.CARDNUMBER AND CAC.CUSTOMERID=CC.CUSTOMERID AND CC.CUSTOMERSTATUS=ST.STATUSCODE and ad.documenttypecode=CC.IDENTIFICATIONTYPE "
                    + "and CC.CURRENCYCODE=CUR.CURRENCYNUMCODE and (CC.IDENTIFICATIONNO=PD.NIC or CC.IDENTIFICATIONNO=PD.PASSPORTNUMBER)";

            String condition = "";

            //to check whether cards exist
            //   if (!isCardExsists(con, filledBean).equals("")) {
            if (!filledBean.getCardnumber().equals("") || filledBean.getCardnumber().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "C.CARDNUMBER = '" + filledBean.getCardnumber() + "' AND CAC.cardnumber= C.CARDNUMBER ";
            }

            if (!filledBean.getAccount().equals("") || filledBean.getAccount().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CAC.ACCOUNTNO = '" + filledBean.getAccount() + "'";
            }

            if (!filledBean.getNic().equals("") || filledBean.getNic().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CC.IDENTIFICATIONNO = '" + filledBean.getNic() + "' and CC.IDENTIFICATIONTYPE='NIC' ";
            }

            if (!filledBean.getPassport().equals("") || filledBean.getPassport().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CC.IDENTIFICATIONNO = '" + filledBean.getPassport() + "' and CC.IDENTIFICATIONTYPE='PAS' ";
            }

            if (!filledBean.getCif().equals("") || filledBean.getCif().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CAC.CUSTOMERID = '" + filledBean.getCif() + "'";
            }

            if (!condition.equals("")) {
                query += " AND " + condition;

            }

            ps = con.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {

                viewBean = new ViewDataBean();
                //CARDCUSTOMER AS CC
                viewBean.setCustmerIdCC(rs.getString("CC_CUSTOMERID"));
                viewBean.setIdTypeCC(rs.getString("idtypedes"));//CC_IDENTIFICATIONTYPE
                viewBean.setIdNumberCC(rs.getString("CC_IDENTIFICATIONNO"));
                viewBean.setCustomerName(rs.getString("CC_CUSTOMERNAME"));
                viewBean.setCreditLimitCC(rs.getString("CC_CREDITLIMIT"));
                viewBean.setCashLimitCC(rs.getString("CC_CASHLIMIT"));
                viewBean.setCurrencyCodeCC(rs.getString("curdes"));//CC_CURRENCYCODE
                viewBean.setRiskProfileCodeCC(rs.getString("CC_RISKPROFILECODE"));
                viewBean.setStaffStatusCC(rs.getString("CC_STAFFSTATUS"));
                //viewBean.setCustomerStatusCC(rs.getString("CC_CUSTOMERSTATUS"));
                viewBean.setCustomerStatusCC(rs.getString("STDES"));
                viewBean.setAppId(rs.getString("APPLICATIONID"));
                
                String address = "";
                if (rs.getString("AD1") != null) {
                    address = rs.getString("AD1");
                }
                if (rs.getString("AD2") != null) {
                    address = address + ", " + rs.getString("AD2");
                }
                if (rs.getString("AD3") != null) {
                    address = address + ", " + rs.getString("AD3");
                }

                viewBean.setAddressCC(address);

                viewBean.setTitlepd(rs.getString("TITLE"));
                viewBean.setNameinfull(rs.getString("NAMEINFULL"));
                viewBean.setGenderpd(rs.getString("GENDER"));
                viewBean.setNationality(rs.getString("NATIONALITY"));
                viewBean.setNic(rs.getString("NIC"));
                viewBean.setPassportnumber(rs.getString("PASSPORTNUMBER"));
                viewBean.setPassportexpiredate(rs.getString("PASSPORTEXPIREDATE"));
                viewBean.setAge(rs.getString("AGE"));
                viewBean.setDateofbirthpd(rs.getString("DATEOFBIRTH"));
                viewBean.setMaritalstatuspd(rs.getString("MARITALSTATUS"));
                viewBean.setNameoncardpd(rs.getString("NAMEONCARD"));
                viewBean.setMothersmaidenname(rs.getString("MOTHERSMAIDENNAME"));

                viewBean.setEmailpd(rs.getString("EMAIL"));
                viewBean.setMobilenopd(rs.getString("MOBILENO"));
                viewBean.setEmergencycontactno(rs.getString("EMERGENCYCONTACTNO"));
                viewBean.setHometelephoneno(rs.getString("HOMETELEPHONENO"));
                viewBean.setOfficephoneno(rs.getString("OFFICEPHONENO"));
                viewBean.setAddress1(rs.getString("ADDRESS1"));
                viewBean.setAddress2(rs.getString("ADDRESS2"));
                viewBean.setAddress3(rs.getString("ADDRESS3"));
                viewBean.setCitypd(rs.getString("CITY"));
                viewBean.setResidedistrict(rs.getString("RESIDEDISTRICT"));
                viewBean.setResideprovince(rs.getString("RESIDEPROVINCE"));
                viewBean.setBillingaddress1(rs.getString("BILLINGADDRESS1"));
                viewBean.setBillingaddress2(rs.getString("BILLINGADDRESS2"));
                viewBean.setBillingaddress3(rs.getString("BILLINGADDRESS3"));
                viewBean.setBillingcity(rs.getString("BILLINGCITY"));
                viewBean.setBillingdistrict(rs.getString("BILLINGDISTRICT"));
                viewBean.setBillingprovince(rs.getString("BILLINGPROVINCE"));
                viewBean.setPermanentaddress1(rs.getString("PERMANENTADDRESS1"));
                viewBean.setPermanentaddress2(rs.getString("PERMANENTADDRESS2"));
                viewBean.setPermanantaddress3(rs.getString("PERMANANTADDRESS3"));
                viewBean.setPermanaentcity(rs.getString("PERMANAENTCITY"));
                viewBean.setPermanentdistrict(rs.getString("PERMANENTDISTRICT"));
                viewBean.setPermanentprovince(rs.getString("PERMANENTPROVINCE"));

                viewBean.setRelativename(rs.getString("RELATIVENAME"));
                viewBean.setRelmobileno(rs.getString("RELMOBILENO"));
                viewBean.setRelofficephone(rs.getString("RELOFFICEPHONE"));
                viewBean.setRelresidancephone(rs.getString("RELRESIDANCEPHONE"));
                viewBean.setRelemail(rs.getString("RELEMAIL"));
                viewBean.setRelationship(rs.getString("RELATIONSHIP"));
                viewBean.setReladdress1(rs.getString("RELADDRESS1"));
                viewBean.setReladdress2(rs.getString("RELADDRESS2"));
                viewBean.setReladdress3(rs.getString("RELADDRESS3"));
                viewBean.setRelcity(rs.getString("RELCITY"));
                viewBean.setReldistrict(rs.getString("RELDISTRICT"));
                viewBean.setRelprovince(rs.getString("RELPROVINCE"));

                viewBean.setSpousename(rs.getString("SPOUSENAME"));
                viewBean.setSpousenic(rs.getString("SPOUSENIC"));
                viewBean.setSpousepassportno(rs.getString("SPOUSEPASSPORTNO"));
                viewBean.setSpousephone(rs.getString("SPOUSEPHONE"));
                viewBean.setSpouseemail(rs.getString("SPOUSEEMAIL"));
                viewBean.setSpousedateofbirth(rs.getString("SPOUSEDATEOFBIRTH"));
                viewBean.setNoofdependance(rs.getString("NOOFDEPENDANCE"));

                cusList.add(viewBean);

            }
            //  } //card details

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

        return cusList;

    }

    public List<ViewDataBean> searchAccount(Connection con, Connection CMSOnline, CustomerSearchBean filledBean) throws SQLException, Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rs2 = null;

        accList = new ArrayList<ViewDataBean>();

        try {

            String query = "SELECT DISTINCT CA.ACCOUNTNO AS CA_ACCOUNTNO,CAC.CUSTOMERID AS CA_CUSTOMERID,CA.BILLINGID AS CA_BILLINGID,CA.BILLINGDATE,CA.CREDITLIMIT AS CA_CREDITLIMIT,CA.CASHLIMIT AS "
                    + "CA_CASHLIMIT,CA.CURRENCYCODE AS CA_CURRENCYCODE,CA.RISKPROFILECODE AS CA_RISKPROFILECODE,CA.STATUS AS CA_STATUS, CA.ACCOUNTTYPE, CA.ACCOUNTOWNER, CA.LOYALTYPOINTS "
                    + "FROM CARD C,CARDACCOUNT CA,CARDCUSTOMER CC, CARDACCOUNTCUSTOMER CAC "
                    + "WHERE C.CARDNUMBER=CAC.CARDNUMBER AND CAC.CUSTOMERID=CC.CUSTOMERID AND CAC.ACCOUNTNO=CA.ACCOUNTNO ";

            String condition = "";

            //to check whether cards exist
            //   if (!isCardExsists(con, filledBean).equals("")) {
            if (!filledBean.getCardnumber().equals("") || filledBean.getCardnumber().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "C.CARDNUMBER = '" + filledBean.getCardnumber() + "'";
            }

            if (!filledBean.getAccount().equals("") || filledBean.getAccount().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CAC.ACCOUNTNO = '" + filledBean.getAccount() + "'";
            }

            if (!filledBean.getNic().equals("") || filledBean.getNic().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CC.IDENTIFICATIONNO = '" + filledBean.getNic() + "' and CC.IDENTIFICATIONTYPE='NIC' ";
            }

            if (!filledBean.getPassport().equals("") || filledBean.getPassport().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CC.IDENTIFICATIONNO = '" + filledBean.getPassport() + "' and CC.IDENTIFICATIONTYPE='PAS' ";
            }

            if (!filledBean.getCif().equals("") || filledBean.getCif().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CAC.CUSTOMERID = '" + filledBean.getCif() + "'";
            }

            if (!condition.equals("")) {
                query += " AND " + condition;

            }

            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            while (rs.next()) {
                viewBean = new ViewDataBean();

                //CARDACCOUNT AS CA
                viewBean.setAccountNoCA(rs.getString("CA_ACCOUNTNO"));
                viewBean.setCustmerIdCA(rs.getString("CA_CUSTOMERID"));
                viewBean.setBillingId(rs.getString("CA_BILLINGID"));
                viewBean.setBillingDate(sdf.format(rs.getString("BILLINGDATE")));
//                viewBean.setCreditLimitCA(rs.getString("CA_CREDITLIMIT"));
//                viewBean.setCashLimitCA(rs.getString("CA_CASHLIMIT"));
                viewBean.setCurrencyCodeCA(rs.getString("CA_CURRENCYCODE"));
                viewBean.setRiskProfileCodeCA(rs.getString("CA_RISKPROFILECODE"));
                viewBean.setStatus(rs.getString("CA_STATUS"));
                viewBean.setAccounttypeonac(rs.getString("ACCOUNTTYPE"));
                viewBean.setAccountowneronac(rs.getString("ACCOUNTOWNER"));
                viewBean.setLoyaltypointsonac(rs.getString("LOYALTYPOINTS"));

                if (!rs.getString("CA_ACCOUNTNO").equals("")) {

                    //get online account details
                    String query2 = "select RISKPROFILECODE, CURRENCYNOCODE, TXNCOUNT, CASHTXNCOUNT, TOTALTXNAMOUNT, TOTALCASHTXNAMOUNT, "
                            + "CREDITLIMIT, CASHLIMIT, OTBCREDIT, OTBCASH, STATUS, ACCOUNTTYPE, ACCOUNTOWNER, LOYALTYPOINTS "
                            + "from ECMS_ONLINE_ACCOUNT "
                            + "where ACCOUNTNUMBER=? ";

                    ps = CMSOnline.prepareStatement(query2);
                    ps.setString(1, rs.getString("CA_ACCOUNTNO"));
                    rs2 = ps.executeQuery();

                    while (rs2.next()) {
                        viewBean.setCreditLimitCA(rs2.getString("CREDITLIMIT"));
                        viewBean.setCashLimitCA(rs2.getString("CASHLIMIT"));
                        viewBean.setTxncountonac(rs2.getString("TXNCOUNT"));
                        viewBean.setCashtxncountonac(rs2.getString("CASHTXNCOUNT"));
                        viewBean.setTotaltxnamountonac(rs2.getString("TOTALTXNAMOUNT"));
                        viewBean.setTotalcashtxnamountonac(rs2.getString("TOTALCASHTXNAMOUNT"));
                        viewBean.setOtbcreditonac(rs2.getString("OTBCREDIT"));
                        viewBean.setOtbcashonac(rs2.getString("OTBCASH"));
                    }

                }

                accList.add(viewBean);

            }
            // } //card details

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

        return accList;

    }

    public List<ViewDataBean> searchCard(Connection con, Connection CMSOnline, CustomerSearchBean filledBean) throws SQLException, Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        cardList = new ArrayList<ViewDataBean>();

        try {

            String query = "SELECT DISTINCT C.CARDNUMBER,C.MAINCARDNO,C.CARDTYPE,C.CARDPRODUCT,C.CARDCATEGORYCODE,C.EXPIERYDATE,C.ACTIVATIONDATE,C.NAMEONCARD,"
                    + "C.CARDSTATUS,C.CREDITLIMIT,C.CASHLIMIT,C.OTBCREDIT,C.OTBCASH,C.TEMPCREDITAMOUNT,C.TEMPCASHAMOUNT,C.EMBOSSSTATUS,C.ENCODESTATUS,C.PINSTATUS,"
                    + "C.PINMAILSTATUS,C.CURRENCYNOCODE,C.PRIORITYLEVEL,C.ISSUEDDATE,C.RISKPROFILECODE,C.CARDDOMAIN, C.CARDDISTRIBUTESTATUS, C.PINDISTRIBUTIONSTATUS, "
                    + "CAC.ACCOUNTNO as accno, CAT.DESCRIPTION AS CATDES, ST.DESCRIPTION AS STDES, CT.DESCRIPTION as ctdes, CP.DESCRIPTION as cpdes "
                    + "FROM CARD C,CARDCATEGORY CAT,CARDACCOUNT CA,CARDCUSTOMER CC, STATUS ST,CARDACCOUNTCUSTOMER CAC,CARDTYPE CT, CARDPRODUCT CP "
                    + "WHERE C.CARDCATEGORYCODE=CAT.CATEGORYCODE AND C.CARDSTATUS=ST.STATUSCODE and C.CARDTYPE=CT.CARDTYPECODE "
                    + "AND C.CARDPRODUCT=CP.PRODUCTCODE AND CAC.cardnumber= C.CARDNUMBER ";

            String condition = "";

            //to check whether cards exist
            //   if (!isCardExsists(con, filledBean).equals("")) {
            if (!filledBean.getCardnumber().equals("") || filledBean.getCardnumber().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "C.CARDNUMBER = '" + filledBean.getCardnumber() + "' ";
            }

            if (!filledBean.getAccount().equals("") || filledBean.getAccount().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CAC.ACCOUNTNO = '" + filledBean.getAccount() + "' ";//AND CAC.cardnumber= C.CARDNUMBER
            }

            if (!filledBean.getNic().equals("") || filledBean.getNic().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CC.IDENTIFICATIONNO = '" + filledBean.getNic() + "' AND CC.IDENTIFICATIONTYPE='NIC' AND CC.IDENTIFICATIONNO= C.IDNUMBER ";
            }

            if (!filledBean.getPassport().equals("") || filledBean.getPassport().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CC.IDENTIFICATIONNO = '" + filledBean.getPassport() + "' AND CC.IDENTIFICATIONTYPE='PAS' AND CC.IDENTIFICATIONNO= C.IDNUMBER ";
            }

            if (!filledBean.getCif().equals("") || filledBean.getCif().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CAC.CUSTOMERID = '" + filledBean.getCif() + "' "; // AND CAC.cardnumber= C.CARDNUMBER
            }

            if (!condition.equals("")) {
                query += " AND " + condition;

            }

            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                viewBean = new ViewDataBean();

                //CARD AS C
                viewBean.setCardNumber(rs.getString("CARDNUMBER"));
                viewBean.setAccountNoC(rs.getString("accno"));//ACCOUNTNO
                viewBean.setMainCardNumber(rs.getString("MAINCARDNO"));
                viewBean.setCardDomain(rs.getString("CARDDOMAIN"));
                viewBean.setCardType(rs.getString("ctdes"));//CARDTYPE
                viewBean.setCardProduct(rs.getString("cpdes"));//CARDPRODUCT
                viewBean.setCardCatCode(rs.getString("CARDCATEGORYCODE"));
                viewBean.setCardCatCodeDes(rs.getString("CATDES"));
                viewBean.setExpDate(rs.getString("EXPIERYDATE"));

                if (rs.getDate("ACTIVATIONDATE") != null) {
                    viewBean.setActivDate(sdf.format(rs.getDate("ACTIVATIONDATE")));
                } else {
                    viewBean.setActivDate(rs.getString("ACTIVATIONDATE"));
                }
                viewBean.setNameonCard(rs.getString("NAMEONCARD"));
                //viewBean.setCardStatus(rs.getString("CARDSTATUS"));
                viewBean.setCardStatus(rs.getString("STDES"));
                viewBean.setCreditLimitC(rs.getString("CREDITLIMIT"));
                viewBean.setCashLimitC(rs.getString("CASHLIMIT"));
                viewBean.setOtbCredit(rs.getString("OTBCREDIT"));
                viewBean.setOtbCash(rs.getString("OTBCASH"));
                viewBean.setTempCrediAmt(rs.getString("TEMPCREDITAMOUNT"));
                viewBean.setTempCashAmt(rs.getString("TEMPCASHAMOUNT"));
                viewBean.setEmbossStatus(rs.getString("EMBOSSSTATUS"));
                viewBean.setEncodeStatus(rs.getString("ENCODESTATUS"));
                viewBean.setPinStatus(rs.getString("PINSTATUS"));
                viewBean.setPinMailStatus(rs.getString("PINMAILSTATUS"));
                viewBean.setCurencyNoCode(rs.getString("CURRENCYNOCODE"));
                viewBean.setPriorityLevel(rs.getString("PRIORITYLEVEL"));
                viewBean.setIssuedDate(rs.getString("ISSUEDDATE"));
                viewBean.setRiskProfileCodeC(rs.getString("RISKPROFILECODE"));
                viewBean.setCardDisStatus(rs.getString("CARDDISTRIBUTESTATUS"));
                viewBean.setPinDisStatus(rs.getString("PINDISTRIBUTIONSTATUS"));

                if (!rs.getString("CARDNUMBER").equals("")) {

                    //get last billing statement sumary
                    String query2 = "select OPENINGBALANCE,CLOSINGBALANCE,MINAMOUNT,PAYMENT,DUEDATE,STATEMENTSTARTDATE,STATEMENTENDDATE "
                            + "from BILLINGLASTSTATEMENTSUMMARY "
                            + "where CARDNO=? ";

                    ps = con.prepareStatement(query2);
                    ps.setString(1, rs.getString("CARDNUMBER"));
                    rs2 = ps.executeQuery();

                    while (rs2.next()) {
                        viewBean.setOpeningBalance(rs2.getString("OPENINGBALANCE"));
                        viewBean.setClosingBalance(rs2.getString("CLOSINGBALANCE"));
                        viewBean.setMinAmount(rs2.getString("MINAMOUNT"));
                        viewBean.setPayment(rs2.getString("PAYMENT"));

                        if (rs2.getDate("DUEDATE") != null) {
                            viewBean.setDueDate(sdf.format(rs2.getDate("DUEDATE")));
                        } else {
                        }
                        if (rs2.getDate("STATEMENTSTARTDATE") != null) {
                            viewBean.setStatementStartDate(sdf.format(rs2.getDate("STATEMENTSTARTDATE")));
                        } else {
                        }
                        if (rs2.getDate("STATEMENTENDDATE") != null) {
                            viewBean.setStatementEndDate(sdf.format(rs2.getDate("STATEMENTENDDATE")));
                        } else {
                        }

                    }

                    //get billing statement details
                    String query5 = "select * from (SELECT INTERESTRATE, CASHADVANCEINTERESTRATE, MINPAYMENTDUE, statementid, TIMESTAMP "
                            + "FROM BILLINGSTATEMENT WHERE CARDNO=? ORDER BY TIMESTAMP desc) "
                            + "where rownum <= 1 ";

                    ps = con.prepareStatement(query5);
                    ps.setString(1, rs.getString("CARDNUMBER"));
                    rs2 = ps.executeQuery();

                    while (rs2.next()) {
                        viewBean.setInterestRate(rs2.getString("INTERESTRATE"));
                        viewBean.setCashAdvancedInterestRate(rs2.getString("CASHADVANCEINTERESTRATE"));
                        viewBean.setMinPaymentDue(rs2.getString("MINPAYMENTDUE"));
                    }

                    //get online last 5 txns
                    String query3 = "select * from (select TXNTYPECODE,TXNAMOUNT,TXNCURRENCY,BILLINGAMOUNT,BILLINGCURRENCY,SETTLEMENTAMOUNT,SETTLEMENTCURRENCY,"
                            + "COUNTRYCODE,RRN,RESPONSECODE,STATUS,MCC,SETTLEMENTDATE "
                            + "from ECMS_ONLINE_TRANSACTION "
                            + "where CARDNO=? ORDER BY LASTUPDATETIME DESC) "
                            + "where ROWNUM <= 5 ";

                    ps = CMSOnline.prepareStatement(query3);
                    ps.setString(1, rs.getString("CARDNUMBER"));
                    rs2 = ps.executeQuery();

                    TransactionBean txnbean;
                    List<TransactionBean> onlinetxnList, backendtxnList;
                    onlinetxnList = new ArrayList<TransactionBean>();
                    backendtxnList = new ArrayList<TransactionBean>();

                    while (rs2.next()) {
                        txnbean = new TransactionBean();

                        txnbean.setTxnType(rs2.getString("TXNTYPECODE"));
                        txnbean.setTxnAmount(rs2.getString("TXNAMOUNT"));
                        txnbean.setTxnCurrency(rs2.getString("TXNCURRENCY"));
                        txnbean.setBillingAmount(rs2.getString("BILLINGAMOUNT"));
                        txnbean.setBillingCurrency(rs2.getString("BILLINGCURRENCY"));
                        txnbean.setSettlementAmount(rs2.getString("SETTLEMENTAMOUNT"));
                        txnbean.setSettlementCurrency(rs2.getString("SETTLEMENTCURRENCY"));
                        txnbean.setCountryCode(rs2.getString("COUNTRYCODE"));
                        txnbean.setRrn(rs2.getString("RRN"));
                        txnbean.setResponseCode(rs2.getString("RESPONSECODE"));
                        txnbean.setStatus(rs2.getString("STATUS"));
                        txnbean.setMcc(rs2.getString("MCC"));
                        txnbean.setSettlementDate(rs2.getString("SETTLEMENTDATE"));

                        onlinetxnList.add(txnbean);

                    }
                    viewBean.setOnlinetxnList(onlinetxnList);

                    //get backend last 5 txns
                    String query4 = "select * from (select TXNTYPECODE,TXNAMOUNT,TXNCURRENCY,BILLINGAMOUNT,BILLINGCURRENCY,SETTLEMENTAMOUNT,SETTLEMENTCURRENCY,"
                            + "COUNTRYCODE,RRN,RESPONSECODE,STATUS,MCC,SETTLEMENTDATE,EODSTATUS "
                            + "from TRANSACTION "
                            + "where CARDNO=? ORDER BY LASTUPDATETIME DESC) "
                            + "where ROWNUM <= 5 ";

                    ps = con.prepareStatement(query4);
                    ps.setString(1, rs.getString("CARDNUMBER"));
                    rs2 = ps.executeQuery();

                    while (rs2.next()) {
                        txnbean = new TransactionBean();

                        txnbean.setTxnType(rs2.getString("TXNTYPECODE"));
                        txnbean.setTxnAmount(rs2.getString("TXNAMOUNT"));
                        txnbean.setTxnCurrency(rs2.getString("TXNCURRENCY"));
                        txnbean.setBillingAmount(rs2.getString("BILLINGAMOUNT"));
                        txnbean.setBillingCurrency(rs2.getString("BILLINGCURRENCY"));
                        txnbean.setSettlementAmount(rs2.getString("SETTLEMENTAMOUNT"));
                        txnbean.setSettlementCurrency(rs2.getString("SETTLEMENTCURRENCY"));
                        txnbean.setCountryCode(rs2.getString("COUNTRYCODE"));
                        txnbean.setRrn(rs2.getString("RRN"));
                        txnbean.setResponseCode(rs2.getString("RESPONSECODE"));
                        txnbean.setStatus(rs2.getString("STATUS"));
                        txnbean.setMcc(rs2.getString("MCC"));
                        txnbean.setSettlementDate(rs2.getString("SETTLEMENTDATE"));
                        txnbean.setEodStatus(rs2.getString("EODSTATUS"));

                        backendtxnList.add(txnbean);

                    }
                    viewBean.setBackendtxnList(backendtxnList);

                    //get cardstanding order details                    
                    String query6 = "select STANDINGORDERID,DESCRIPTION,STARTDATE,ENDDATE,AMOUNT,CURRENCYTYPE,FREQUENCYTYPE,NEXTDATE,STATUS "
                            + "from CARDSTANDINGORDER "
                            + "where CARDNO=? ";

                    ps = con.prepareStatement(query6);
                    ps.setString(1, rs.getString("CARDNUMBER"));
                    rs2 = ps.executeQuery();

                    StandingOrderBean stdorderbean;
                    List<StandingOrderBean> stdorderList;
                    stdorderList = new ArrayList<StandingOrderBean>();

                    while (rs2.next()) {
                        stdorderbean = new StandingOrderBean();

                        stdorderbean.setId(rs2.getString("STANDINGORDERID"));
                        stdorderbean.setDescription(rs2.getString("DESCRIPTION"));
                        stdorderbean.setStartDate(rs2.getString("STARTDATE"));
                        stdorderbean.setEndDate(rs2.getString("ENDDATE"));
                        stdorderbean.setAmount(rs2.getString("AMOUNT"));
                        stdorderbean.setCurrencyType(rs2.getString("CURRENCYTYPE"));
                        stdorderbean.setFrequencyType(rs2.getString("FREQUENCYTYPE"));
                        stdorderbean.setNextDate(rs2.getString("NEXTDATE"));
                        stdorderbean.setStatus(rs2.getString("STATUS"));

                        stdorderList.add(stdorderbean);

                    }
                    viewBean.setStdorderList(stdorderList);
                    //end

                }

                cardList.add(viewBean);

            }
            //   } //card details

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

        return cardList;

    }

    public List<ViewMerchantCustomerBean> searchMerchantCustomer(Connection con, MerchantSearchBean filledBean) throws SQLException, Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;

        mercusList = new ArrayList<ViewMerchantCustomerBean>();

        try {

            String query = "SELECT DISTINCT MC.MERCHANTCUSTOMERNO, MC.MERCHANTNAME, MC.LEGALNAME, MC.ADDRESS1, MC.ADDRESS2, MC.ADDRESS3, MC.CITY, MC.POSTALCODE, MC.COUNTRY, "
                    + "MC.TELEPHONE, MC.FAX, MC.EMAIL, MC.CONTACTPERSONTITLE, MC.CONTACTPERSONFIRSTNAME, MC.CONTACTPERSONMIDDLENAME, MC.CONTACTPERSONLASTNAME, "
                    + "MC.LASTUPDATEDUSER, MC.COMMISSIONPROFILE, MC.FEEPROFILE, MC.RISKPROFILE, MC.BANKCODE, MC.BRANCHNAME, MC.ACCOUNTNUMBER, MC.ACCOUNTTYPE, "
                    + "MC.ACCOUNTNAME, MC.APPLICATIONDATE, MC.ACTIVATIONDATE, MC.STATUS, MC.LASTUPDATEDTIME, MC.CREATEDTIME, MC.PAYMENTMODE, MC.PAYMENTCYCLE, "
                    + "MC.STATEMENTCYCLE, MC.MERCHANTACCOUNTNO, MC.PAYMENTMAINTEINANCESTATUS, MC.STATEMENTMAINTEINANCESTATUS, ST.DESCRIPTION as stdes,"
                    + "A.DESCRIPTION as citydes, C.DESCRIPTION as contrydes "
                    + "FROM MERCHANTCUSTOMER MC, STATUS ST, MERCHANTLOCATION ML, TERMINAL T, COUNTRY C, AREA A "
                    + "WHERE ST.STATUSCODE=MC.STATUS and MC.CITY=A.AREACODE and MC.COUNTRY=C.COUNTRYALPHA3CODE ";

            String condition = "";

            //if (!isMerchantCustomerExsists(con, filledBean).equals("")) {
            if (!filledBean.getCustomerID().equals("") || filledBean.getCustomerID().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "MC.MERCHANTCUSTOMERNO = '" + filledBean.getCustomerID() + "'";
            }

            if (!filledBean.getAccountNo().equals("") || filledBean.getAccountNo().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "MC.ACCOUNTNUMBER = '" + filledBean.getAccountNo() + "'";
            }

            if (!filledBean.getMid().equals("") || filledBean.getMid().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "ML.MERCHANTID = '" + filledBean.getMid() + "' and MC.MERCHANTCUSTOMERNO=ML.MERCHANTCUSTOMERNO";
            }

            if (!filledBean.getTid().equals("") || filledBean.getTid().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "T.TERMINALID = '" + filledBean.getTid() + "' and T.MERCHANTID=ML.MERCHANTID and ML.MERCHANTCUSTOMERNO=MC.MERCHANTCUSTOMERNO";
            }

            if (!condition.equals("")) {
                query += " AND " + condition;

            }

            ps = con.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                mercusBean = new ViewMerchantCustomerBean();

                mercusBean.setMerchantCusNo(rs.getString("MERCHANTCUSTOMERNO"));
                mercusBean.setMerchantName(rs.getString("MERCHANTNAME"));
                mercusBean.setLegelName(rs.getString("LEGALNAME"));
                mercusBean.setAddress1(rs.getString("ADDRESS1"));
                mercusBean.setAddress2(rs.getString("ADDRESS2"));
                mercusBean.setAddress3(rs.getString("ADDRESS3"));
                mercusBean.setCity(rs.getString("citydes"));
                mercusBean.setPostalCode(rs.getString("POSTALCODE"));
                mercusBean.setCountry(rs.getString("contrydes"));
                mercusBean.setTele(rs.getString("TELEPHONE"));
                mercusBean.setFax(rs.getString("FAX"));
                mercusBean.setEmail(rs.getString("EMAIL"));
                mercusBean.setConPerTitle(rs.getString("CONTACTPERSONTITLE"));
                mercusBean.setConPerFirstName(rs.getString("CONTACTPERSONFIRSTNAME"));
                mercusBean.setConPerMiddleName(rs.getString("CONTACTPERSONMIDDLENAME"));
                mercusBean.setConPerLastName(rs.getString("CONTACTPERSONLASTNAME"));
                mercusBean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                mercusBean.setCommisionProfile(rs.getString("COMMISSIONPROFILE"));
                mercusBean.setFeeProfile(rs.getString("FEEPROFILE"));
                mercusBean.setRiskProfile(rs.getString("RISKPROFILE"));
                mercusBean.setBankCode(rs.getString("BANKCODE"));
                mercusBean.setBranchName(rs.getString("BRANCHNAME"));
                mercusBean.setAccNo(rs.getString("ACCOUNTNUMBER"));
                mercusBean.setAccType(rs.getString("ACCOUNTTYPE"));
                mercusBean.setAccName(rs.getString("ACCOUNTNAME"));
                mercusBean.setApplicationDate(rs.getString("APPLICATIONDATE"));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                if (rs.getString("ACTIVATIONDATE") != null) {
                    mercusBean.setActivationDate(sdf.format(rs.getString("ACTIVATIONDATE")));
                } else {
                }

                mercusBean.setStatus(rs.getString("stdes"));
                mercusBean.setLastUpdatedTime(rs.getString("LASTUPDATEDTIME"));
                mercusBean.setCreatedTime(rs.getString("CREATEDTIME"));
                mercusBean.setPaymentMode(rs.getString("PAYMENTMODE"));
                mercusBean.setPaymentCycle(rs.getString("PAYMENTCYCLE"));
                mercusBean.setStatementCycle(rs.getString("STATEMENTCYCLE"));
                mercusBean.setMerchantAccNo(rs.getString("MERCHANTACCOUNTNO"));
                mercusBean.setPaymentMaintainStatus(rs.getString("PAYMENTMAINTEINANCESTATUS"));
                mercusBean.setStatementMaintainStatus(rs.getString("STATEMENTMAINTEINANCESTATUS"));

                mercusList.add(mercusBean);

            }
            //} 

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

        return mercusList;

    }

    //searchMerchantLocation
    public List<ViewMerchantLocationBean> searchMerchantLocation(Connection con, MerchantSearchBean filledBean) throws SQLException, Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;

        locList = new ArrayList<ViewMerchantLocationBean>();

        try {

            String query = "SELECT DISTINCT ML.MERCHANTID, ML.MERCHANTCUSTOMERNO, ML.DESCRIPTION, ML.CONTACTPERSONTITLE, ML.CONTACTPERSONFIRSTNAME, "
                    + "ML.CONTACTPERSONMIDDLENAME, ML.CONTACTPERSONLASTNAME, ML.CONTACTPERSONPOSITON, ML.ADDRESS1, ML.ADDRESS2, ML.ADDRESS3, ML.CITY, "
                    + "ML.POSTALCODE, ML.COUNTRY, ML.TELEPHONE, ML.FAX, ML.EMAIL, ML.ACTIVATIONDATE, ML.STATUS, ML.RISKPROFILE, ML.FEEPROFILE, ML.COMMITIONPROFILE, "
                    + "ML.LASTUPDATEDUSER, ML.LASTUPDATEDTIME, ML.CREATEDTIME, ML.MERCHANTACCOUNTNO, ML.STATEMENTCYCLE, ML.PAYMENTCYCLE, ML.PAYMENTMODE, "
                    + "ML.STATEMENTMAINTEINANCESTATUS, ML.BANKCODE, ML.BRANCHNAME, ML.ACCOUNTNUMBER, ML.ACCOUNTTYPE, ML.ACCOUNTNAME, ML.CURRENCYCODE, "
                    + "ST.DESCRIPTION as stdes, A.DESCRIPTION as citydes, C.DESCRIPTION as contrydes "
                    + "FROM MERCHANTCUSTOMER MC, STATUS ST, MERCHANTLOCATION ML, TERMINAL T, COUNTRY C, AREA A "
                    + "WHERE ST.STATUSCODE=MC.STATUS and ML.CITY=A.AREACODE and ML.COUNTRY=C.COUNTRYALPHA3CODE";

            String condition = "";

            //if (!isMerchantCustomerExsists(con, filledBean).equals("")) {
            if (!filledBean.getCustomerID().equals("") || filledBean.getCustomerID().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "MC.MERCHANTCUSTOMERNO = '" + filledBean.getCustomerID() + "' and MC.MERCHANTCUSTOMERNO=ML.MERCHANTCUSTOMERNO";
            }

            if (!filledBean.getAccountNo().equals("") || filledBean.getAccountNo().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "MC.ACCOUNTNUMBER = '" + filledBean.getAccountNo() + "' and MC.MERCHANTCUSTOMERNO=ML.MERCHANTCUSTOMERNO";
            }

            if (!filledBean.getMid().equals("") || filledBean.getMid().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "ML.MERCHANTID = '" + filledBean.getMid() + "'";
            }

            if (!filledBean.getTid().equals("") || filledBean.getTid().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "T.TERMINALID = '" + filledBean.getTid() + "' and T.MERCHANTID=ML.MERCHANTID";
            }

            if (!condition.equals("")) {
                query += " AND " + condition;

            }

            ps = con.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                locBean = new ViewMerchantLocationBean();

                locBean.setMerchantId(rs.getString("MERCHANTID"));
                locBean.setMerchantCusNo(rs.getString("MERCHANTCUSTOMERNO"));
                locBean.setDescription(rs.getString("DESCRIPTION"));
                locBean.setConPerTitle(rs.getString("CONTACTPERSONTITLE"));
                locBean.setConPerFirstName(rs.getString("CONTACTPERSONFIRSTNAME"));
                locBean.setConPerMiddleName(rs.getString("CONTACTPERSONMIDDLENAME"));
                locBean.setConPerLastName(rs.getString("CONTACTPERSONLASTNAME"));
                locBean.setConPerPosition(rs.getString("CONTACTPERSONPOSITON"));
                locBean.setAddress1(rs.getString("ADDRESS1"));
                locBean.setAddress2(rs.getString("ADDRESS2"));
                locBean.setAddress3(rs.getString("ADDRESS3"));
                locBean.setCity(rs.getString("citydes"));
                locBean.setPostalCode(rs.getString("POSTALCODE"));
                locBean.setCountry(rs.getString("contrydes"));
                locBean.setTele(rs.getString("TELEPHONE"));
                locBean.setFax(rs.getString("FAX"));
                locBean.setEmail(rs.getString("EMAIL"));
                locBean.setActivationDate(rs.getString("ACTIVATIONDATE"));
                locBean.setStatus(rs.getString("stdes"));
                locBean.setRiskProfile(rs.getString("RISKPROFILE"));
                locBean.setFeeProfile(rs.getString("FEEPROFILE"));
                locBean.setCommisionProfile(rs.getString("COMMITIONPROFILE"));
                locBean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                locBean.setLastUpdatedTime(rs.getString("LASTUPDATEDTIME"));
                locBean.setCreatedTime(rs.getString("CREATEDTIME"));
                locBean.setMerchantAccNo(rs.getString("MERCHANTACCOUNTNO"));
                locBean.setStatementCycle(rs.getString("STATEMENTCYCLE"));
                locBean.setPaymentCycle(rs.getString("PAYMENTCYCLE"));
                locBean.setPaymantMode(rs.getString("PAYMENTMODE"));
                locBean.setStatementMaintainStatus(rs.getString("STATEMENTMAINTEINANCESTATUS"));
                locBean.setBankCode(rs.getString("BANKCODE"));
                locBean.setBranchName(rs.getString("BRANCHNAME"));
                locBean.setAccNo(rs.getString("ACCOUNTNUMBER"));
                locBean.setAccType(rs.getString("ACCOUNTTYPE"));
                locBean.setAccName(rs.getString("ACCOUNTNAME"));
                locBean.setCurencyCode(rs.getString("CURRENCYCODE"));

                locList.add(locBean);

            }
            //} 

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

        return locList;

    }

    //searchTerminal
    public List<ViewTerminalBean> searchTerminal(Connection con, MerchantSearchBean filledBean) throws SQLException, Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;

        terList = new ArrayList<ViewTerminalBean>();

        try {

            String query = "SELECT DISTINCT T.TERMINALID, T.TERMINALNAME, T.SERIALNO, T.MANUFACTURER, T.MODEL, T.INSTALLATIONDATE, T.ACTIVATIONDATE, "
                    + "T.ALLOCATIONSTATUS, T.TERMINALSTATUS, T.LASTUPDATEDUSER, T.LASTUPDATEDTIME, T.CREATEDTIME, T.MERCHANTID, T.CURRENCYCODE, T.MCC,"
                    + "TMANU.DESCRIPTION as manudes, TMOD.DESCRIPTION as moddes, mcc.DESCRIPTION as mccdes "
                    + "FROM MERCHANTCUSTOMER MC, STATUS ST, MERCHANTLOCATION ML, TERMINAL T, TERMINALMANUFACTURE TMANU, TERMINALMODLE TMOD, MCC mcc "
                    + "WHERE ST.STATUSCODE=T.TERMINALSTATUS and T.MANUFACTURER=TMANU.MANUFACTURECODE and T.MODEL=TMOD.MODLECODE and T.MCC=mcc.MCCCODE ";

            String condition = "";

            //if (!isMerchantCustomerExsists(con, filledBean).equals("")) {
            if (!filledBean.getCustomerID().equals("") || filledBean.getCustomerID().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "MC.MERCHANTCUSTOMERNO = '" + filledBean.getCustomerID() + "' and MC.MERCHANTCUSTOMERNO=ML.MERCHANTCUSTOMERNO "
                        + "and T.MERCHANTID=ML.MERCHANTID";
            }

            if (!filledBean.getAccountNo().equals("") || filledBean.getAccountNo().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "MC.ACCOUNTNUMBER = '" + filledBean.getAccountNo() + "' and MC.MERCHANTCUSTOMERNO=ML.MERCHANTCUSTOMERNO "
                        + "and T.MERCHANTID=ML.MERCHANTID";
            }

            if (!filledBean.getMid().equals("") || filledBean.getMid().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "ML.MERCHANTID = '" + filledBean.getMid() + "' and T.MERCHANTID=ML.MERCHANTID";
            }

            if (!filledBean.getTid().equals("") || filledBean.getTid().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "T.TERMINALID = '" + filledBean.getTid() + "'";
            }

            if (!condition.equals("")) {
                query += " AND " + condition;

            }

            ps = con.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                terBean = new ViewTerminalBean();

                terBean.setTerminalId(rs.getString("TERMINALID"));
                terBean.setTerminalName(rs.getString("TERMINALNAME"));
                terBean.setSerialNo(rs.getString("SERIALNO"));
                terBean.setManufacturer(rs.getString("manudes"));
                terBean.setModel(rs.getString("moddes"));
                terBean.setInstalationDate(rs.getString("INSTALLATIONDATE"));
                terBean.setActivationDate(rs.getString("ACTIVATIONDATE"));
                terBean.setAlocationStatus(rs.getString("ALLOCATIONSTATUS"));
                terBean.setTerminalStatus(rs.getString("TERMINALSTATUS"));
                terBean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                terBean.setLastUpdatedTime(rs.getString("LASTUPDATEDTIME"));
                terBean.setCreatedTime(rs.getString("CREATEDTIME"));
                terBean.setMerchantId(rs.getString("MERCHANTID"));
                terBean.setCurencyCode(rs.getString("CURRENCYCODE"));
                terBean.setMcc(rs.getString("mccdes"));

                terList.add(terBean);

            }
            //} 

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

        return terList;

    }


    /*
     * 
     */
    public List<QuestionAnswerBean> getQuestionList(Connection con, String questionType, String questionCategory, String cardNumber, String applicationId) throws Exception {
        PreparedStatement ps = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        questionBeanList = new ArrayList<SecurityQuestionBean>();
        try {
            String query = "SELECT SQ.QUESTIONNO,SQ.QUESTION,SQ.PRIORITYLEVEL,PL.DESCRIPTION AS PRIORITY_DES,"
                    + "SQ.ISSUACCSTATUS,SQ.TABLENAME,SQ.FIELDNAME1,SQ.FIELDNAME2,SQ.FIELDNAME3,SQ.FIELDNAME4,"
                    + "SQ.CARDCATEGORY,CC.DESCRIPTION AS CATEGORY_DES,"
                    + "SQ.LASTUPDATEDUSER,SQ.LASTUPDATEDTIME,SQ.CREATEDTIME,SQ.STATUS,ST.DESCRIPTION AS STATUS_DES "
                    + "FROM SECURITYQUESTION SQ, STATUS ST, PRIORITYLEVEL PL, CARDCATEGORY CC "
                    + "WHERE SQ.STATUS=ST.STATUSCODE AND PL.PRIORITYLEVELCODE=SQ.PRIORITYLEVEL AND SQ.CARDCATEGORY=CC.CATEGORYCODE "
                    + "AND SQ.ISSUACCSTATUS=? AND SQ.QUESTIONTYPE=? ORDER BY SQ.QUESTIONNO ";
            ps = con.prepareStatement(query);
            ps.setString(1, questionType);
            ps.setString(2, questionCategory);
            rs = ps.executeQuery();
            while (rs.next()) {
                SecurityQuestionBean bean = new SecurityQuestionBean();

                bean.setQuestionNo(rs.getString("QUESTIONNO"));
                bean.setQuestion(rs.getString("QUESTION"));
                bean.setPriorityLevel(rs.getString("PRIORITYLEVEL"));
                bean.setPriorityDes(rs.getString("PRIORITY_DES"));
                bean.setIssueAcquireStatus(rs.getString("ISSUACCSTATUS"));
                bean.setTableName(rs.getString("TABLENAME"));
                bean.setField1(rs.getString("FIELDNAME1"));
                bean.setField2(rs.getString("FIELDNAME2"));
                bean.setField3(rs.getString("FIELDNAME3"));
                bean.setField4(rs.getString("FIELDNAME4"));
                bean.setCardCategory(rs.getString("CARDCATEGORY"));
                bean.setCardCategoryDes(rs.getString("CATEGORY_DES"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setStatusDes(rs.getString("STATUS_DES"));

                questionBeanList.add(bean);

            }
            this.getQuestionAndAnswerList(con, questionBeanList, cardNumber, applicationId);

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

        return answerBeanList;
    }

    public List<QuestionAnswerBean> getQuestionAndAnswerList(Connection con, List<SecurityQuestionBean> questionList, String cardNumber, String applicationId) throws Exception {
        answerBeanList = new ArrayList<QuestionAnswerBean>();
        PreparedStatement ps = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        try {

            for (int t = 0; t < questionList.size(); t++) {
                try {

                    String select = "";
                    String answer = "";
                    if (questionList.get(t).getField1() != null) {
                        select = select + questionList.get(t).getTableName() + "." + questionList.get(t).getField1();
                    }
                    if (questionList.get(t).getField2() != null) {
                        select = select + "," + questionList.get(t).getTableName() + "." + questionList.get(t).getField2();
                    }
                    if (questionList.get(t).getField3() != null) {
                        select = select + "," + questionList.get(t).getTableName() + "." + questionList.get(t).getField3();
                    }
                    if (questionList.get(t).getField4() != null) {
                        select = select + "," + questionList.get(t).getTableName() + "." + questionList.get(t).getField4();
                    }

                    if (questionList.get(t).getTableName().equals("CARD") || questionList.get(t).getTableName().equals("CARDACCOUNT") || questionList.get(t).getTableName().equals("CARDCUSTOMER") || questionList.get(t).getTableName().equals("CARDMAINCUSTOMER")) {

                        String query = "SELECT " + select
                                + " FROM CARD,CARDACCOUNT,CARDCUSTOMER,CARDACCOUNTCUSTOMER "
                                + " WHERE CARDACCOUNTCUSTOMER.ACCOUNTNO= CARDACCOUNT.ACCOUNTNO AND CARDACCOUNTCUSTOMER.CUSTOMERID =CARDCUSTOMER.CUSTOMERID AND CARDACCOUNTCUSTOMER.CARDNUMBER=CARD.CARDNUMBER AND CARD.CARDNUMBER=" + cardNumber;

                        ps = con.prepareStatement(query);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            QuestionAnswerBean bean = new QuestionAnswerBean();
                            bean.setQuestion(questionList.get(t).getQuestion());

                            if (questionList.get(t).getField1() != null) {
                                answer = answer + rs.getString(questionList.get(t).getField1());
                            }
                            if (questionList.get(t).getField2() != null) {
                                answer = answer + " " + rs.getString(questionList.get(t).getField2());
                            }
                            if (questionList.get(t).getField3() != null) {
                                answer = answer + " " + rs.getString(questionList.get(t).getField3());
                            }
                            if (questionList.get(t).getField4() != null) {
                                answer = answer + " " + rs.getString(questionList.get(t).getField4());
                            }

                            bean.setAnswer(answer);
                            answerBeanList.add(bean);

                        }

                    }
                    if (questionList.get(t).getTableName().equals("CARDAPPLICATION") || questionList.get(t).getTableName().equals("CARDAPPLICATIONSTATUS") || questionList.get(t).getTableName().equals("CARDAPPLICATIONPERSONALDETAILS")) {
                        String query = "SELECT " + select
                                + " FROM CARDAPPLICATION,CARDAPPLICATIONSTATUS,CARDAPPLICATIONPERSONALDETAILS "
                                + " WHERE CARDAPPLICATION.APPLICATIONID= CARDAPPLICATIONSTATUS.APPLICATIONID AND CARDAPPLICATION.APPLICATIONID=CARDAPPLICATIONPERSONALDETAILS.APPLICATIONID AND CARDAPPLICATION.APPLICATIONID=" + applicationId;

                        ps = con.prepareStatement(query);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            QuestionAnswerBean bean = new QuestionAnswerBean();
                            bean.setQuestion(questionList.get(t).getQuestion());
                            if (questionList.get(t).getField1() != null) {
                                answer = answer + rs.getString(questionList.get(t).getField1());
                            }
                            if (questionList.get(t).getField2() != null) {
                                answer = answer + " " + rs.getString(questionList.get(t).getField2());
                            }
                            if (questionList.get(t).getField3() != null) {
                                answer = answer + " " + rs.getString(questionList.get(t).getField3());
                            }
                            if (questionList.get(t).getField4() != null) {
                                answer = answer + " " + rs.getString(questionList.get(t).getField4());
                            }

                            bean.setAnswer(answer);

                            answerBeanList.add(bean);

                        }
                    }
                } catch (Exception e) {
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
                ps.close();

            } catch (Exception e) {
            }
        }
        return answerBeanList;

    }

    public String isCardExsists(Connection con, CustomerSearchBean searchbean) throws Exception {

        String card = "";
        PreparedStatement ps = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        try {
            if (!searchbean.getCardnumber().equals("")) {
                ps = con.prepareStatement("SELECT CARDNUMBER FROM CARD WHERE CARDNUMBER=" + searchbean.getCardnumber());
                rs = ps.executeQuery();
                if (rs.next()) {
                    card = rs.getString("CARDNUMBER");

                }
            } else {
                if (!searchbean.getAccount().equals("")) {
//                    ps = con.prepareStatement("SELECT C.CARDNUMBER FROM CARD C,CARDACCOUNT CA WHERE C.CUSTOMERID= CA.CUSTOMERID AND CA.ACCOUNTNO=" + searchbean.getAccount());
                    ps = con.prepareStatement("SELECT CARDNUMBER FROM CARDACCOUNTCUSTOMER WHERE ACCOUNTNO=" + searchbean.getAccount());
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        card = rs.getString("CARDNUMBER");

                    }

                } else {
                    if (!searchbean.getNic().equals("")) {
                        ps = con.prepareStatement("SELECT * FROM (SELECT CARDNUMBER, ROWNUM R FROM CARD WHERE IDNUMBER='" + searchbean.getNic() + "' ORDER BY CREATEDTIME DESC) WHERE ROWNUM=1");
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            card = rs.getString("CARDNUMBER");//

                        }

                    } else {
                        if (!searchbean.getPassport().equals("")) {
                            ps = con.prepareStatement("SELECT * FROM (SELECT CARDNUMBER, ROWNUM R FROM CARD WHERE IDNUMBER='" + searchbean.getPassport() + "' ORDER BY CREATEDTIME DESC) WHERE ROWNUM=1");
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                card = rs.getString("CARDNUMBER");//

                            }
                        } else {
                            if (!searchbean.getCif().equals("")) {
//                                ps = con.prepareStatement("SELECT * FROM (SELECT CARDNUMBER, ROWNUM R FROM CARD WHERE IDNUMBER='" + searchbean.getLicence() + "' ORDER BY CREATEDTIME DESC) WHERE ROWNUM=1");
                                ps = con.prepareStatement("SELECT CARDNUMBER FROM CARDACCOUNTCUSTOMER WHERE CUSTOMERID=" + searchbean.getCif());
                                rs = ps.executeQuery();
                                if (rs.next()) {
                                    card = rs.getString("CARDNUMBER");//

                                }
                            } else {
                            }
                        }

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
                ps.close();

            } catch (Exception e) {
            }
        }

        return card;
    }

    public String isApplicationsExsists(Connection con, CustomerSearchBean searchbean) throws Exception {
        String application = "";
        PreparedStatement ps = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        try {
            if (!searchbean.getNic().equals("")) {
                ps = con.prepareStatement("SELECT * FROM (SELECT APPLICATIONID, ROWNUM R FROM CARDAPPLICATION WHERE IDENTIFICATIONNO='" + searchbean.getNic() + "' ORDER BY CREATETIME DESC) WHERE ROWNUM=1");
                rs = ps.executeQuery();
                if (rs.next()) {
                    application = rs.getString("APPLICATIONID");//

                }

            } else {
                if (!searchbean.getPassport().equals("")) {
                    ps = con.prepareStatement("SELECT * FROM (SELECT APPLICATIONID, ROWNUM R FROM CARDAPPLICATION WHERE IDENTIFICATIONNO='" + searchbean.getPassport() + "' ORDER BY CREATETIME DESC) WHERE ROWNUM=1");
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        application = rs.getString("APPLICATIONID");//

                    }
                } else {
                    if (!searchbean.getCif().equals("")) {
                        ps = con.prepareStatement("SELECT * FROM (SELECT CA.APPLICATIONID, ROWNUM R FROM CARDAPPLICATION CA, CARDCUSTOMER CC WHERE CA.IDENTIFICATIONNO=CC.IDENTIFICATIONNO AND CC.CUSTOMERID='" + searchbean.getCif() + "' ORDER BY CA.CREATETIME DESC) WHERE ROWNUM=1");
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            application = rs.getString("APPLICATIONID");//

                        }
                    } else {
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
                ps.close();

            } catch (Exception e) {
            }
        }

        return application;
    }

    public String isMerchantCustomerExsists(Connection con, MerchantSearchBean searchbean) throws Exception {
        String customerid = "";
        PreparedStatement ps = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        try {
            if (!searchbean.getCustomerID().equals("")) {
                ps = con.prepareStatement("SELECT * FROM MERCHANTCUSTOMER M WHERE M.MERCHANTCUSTOMERNO='" + searchbean.getCustomerID() + "'");
                rs = ps.executeQuery();
                if (rs.next()) {
                    customerid = rs.getString("MERCHANTCUSTOMERNO");//

                }

            } else {
                if (!searchbean.getAccountNo().equals("")) {
                    ps = con.prepareStatement("SELECT * FROM MERCHANTCUSTOMER M WHERE M.ACCOUNTNUMBER='" + searchbean.getAccountNo() + "'");
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        customerid = rs.getString("MERCHANTCUSTOMERNO");//

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
                ps.close();

            } catch (Exception e) {
            }
        }

        return customerid;
    }

    public String isMidExsists(Connection con, MerchantSearchBean searchbean) throws Exception {
        String mid = "";
        PreparedStatement ps = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        try {
            if (!searchbean.getMid().equals("")) {
                ps = con.prepareStatement("SELECT * FROM MERCHANTLOCATION L WHERE L.MERCHANTID ='" + searchbean.getMid() + "'");
                rs = ps.executeQuery();
                if (rs.next()) {
                    mid = rs.getString("MERCHANTID");//

                }

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

        return mid;
    }

    public String isTidExsists(Connection con, MerchantSearchBean searchbean) throws Exception {
        String tid = "";
        PreparedStatement ps = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        try {
            if (!searchbean.getTid().equals("")) {
                ps = con.prepareStatement("SELECT * FROM TERMINAL T WHERE T.TERMINALID ='" + searchbean.getTid() + "'");
                rs = ps.executeQuery();
                if (rs.next()) {
                    tid = rs.getString("TERMINALID");//

                }

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

        return tid;
    }

    public List<QuestionAnswerBean> getAcquireQuestionList(Connection con, String questionType, String questionCategory, String id, String idType) throws Exception {
        PreparedStatement ps = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        questionBeanList = new ArrayList<SecurityQuestionBean>();
        try {
            String query = "SELECT SQ.QUESTIONNO,SQ.QUESTION,SQ.PRIORITYLEVEL,PL.DESCRIPTION AS PRIORITY_DES,"
                    + "SQ.ISSUACCSTATUS,SQ.TABLENAME,SQ.FIELDNAME1,SQ.FIELDNAME2,SQ.FIELDNAME3,SQ.FIELDNAME4,"
                    + "SQ.LASTUPDATEDUSER,SQ.LASTUPDATEDTIME,SQ.CREATEDTIME,SQ.STATUS,ST.DESCRIPTION AS STATUS_DES "
                    + "FROM SECURITYQUESTION SQ, STATUS ST, PRIORITYLEVEL PL "
                    + "WHERE SQ.STATUS=ST.STATUSCODE AND PL.PRIORITYLEVELCODE=SQ.PRIORITYLEVEL  "
                    + "AND SQ.ISSUACCSTATUS=? AND SQ.QUESTIONTYPE=? ORDER BY SQ.QUESTIONNO ";
            ps = con.prepareStatement(query);
            ps.setString(1, questionType);
            ps.setString(2, questionCategory);
            rs = ps.executeQuery();
            while (rs.next()) {
                SecurityQuestionBean bean = new SecurityQuestionBean();

                bean.setQuestionNo(rs.getString("QUESTIONNO"));
                bean.setQuestion(rs.getString("QUESTION"));
                bean.setPriorityLevel(rs.getString("PRIORITYLEVEL"));
                bean.setPriorityDes(rs.getString("PRIORITY_DES"));
                bean.setIssueAcquireStatus(rs.getString("ISSUACCSTATUS"));
                bean.setTableName(rs.getString("TABLENAME"));
                bean.setField1(rs.getString("FIELDNAME1"));
                bean.setField2(rs.getString("FIELDNAME2"));
                bean.setField3(rs.getString("FIELDNAME3"));
                bean.setField4(rs.getString("FIELDNAME4"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setStatusDes(rs.getString("STATUS_DES"));

                questionBeanList.add(bean);

            }
            this.getAcquireQuestionAndAnswerList(con, questionBeanList, id, idType);

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

        return answerBeanList;
    }

    private void getAcquireQuestionAndAnswerList(Connection con, List<SecurityQuestionBean> questionList, String id, String idType) {
        answerBeanList = new ArrayList<QuestionAnswerBean>();
        PreparedStatement ps = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        try {

            for (int t = 0; t < questionList.size(); t++) {
                try {

                    String select = "";
                    String answer = "";
                    String tableid = "";
                    if (idType.equals("customerId")) {

                        tableid = "MERCHANTCUSTOMERNO";
                    }
                    if (idType.equals("mid")) {

                        tableid = "MERCHANTID";
                    }
                    if (idType.equals("tid")) {

                        tableid = "TERMINALID";
                    }

                    if (questionList.get(t).getField1() != null) {
                        select = select + questionList.get(t).getTableName() + "." + questionList.get(t).getField1();
                    }
                    if (questionList.get(t).getField2() != null) {
                        select = select + "," + questionList.get(t).getTableName() + "." + questionList.get(t).getField2();
                    }
                    if (questionList.get(t).getField3() != null) {
                        select = select + "," + questionList.get(t).getTableName() + "." + questionList.get(t).getField3();
                    }
                    if (questionList.get(t).getField4() != null) {
                        select = select + "," + questionList.get(t).getTableName() + "." + questionList.get(t).getField4();
                    }

                    String query = "SELECT " + select + " FROM " + questionList.get(t).getTableName() + " WHERE " + tableid + "='" + id + "'";

                    ps = con.prepareStatement(query);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        QuestionAnswerBean bean = new QuestionAnswerBean();
                        bean.setQuestion(questionList.get(t).getQuestion());

                        if (questionList.get(t).getField1() != null) {
                            answer = answer + rs.getString(questionList.get(t).getField1());
                        }
                        if (questionList.get(t).getField2() != null) {
                            answer = answer + " " + rs.getString(questionList.get(t).getField2());
                        }
                        if (questionList.get(t).getField3() != null) {
                            answer = answer + " " + rs.getString(questionList.get(t).getField3());
                        }
                        if (questionList.get(t).getField4() != null) {
                            answer = answer + " " + rs.getString(questionList.get(t).getField4());
                        }

                        bean.setAnswer(answer);
                        answerBeanList.add(bean);

                    }

                } catch (Exception e) {
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
                ps.close();

            } catch (Exception e) {
            }
        }

    }
    /////////////////// Merchant Transaction Explorer

    public List<String> getMerchantOfCustomer(Connection CMSCon, String merchantCusNum) throws Exception {
        PreparedStatement getMerch = null;
        ResultSet rs = null;
        List<String> merchantList = new ArrayList<String>();
        try {
            getMerch = CMSCon.prepareStatement("SELECT ML.MERCHANTID FROM MERCHANTLOCATION ML "
                    + " WHERE ML.MERCHANTCUSTOMERNO = ?");

            getMerch.setString(1, merchantCusNum);
            rs = getMerch.executeQuery();

            while (rs.next()) {

                merchantList.add(rs.getString("MERCHANTID"));
            }

        } catch (SQLException sqlex) {
            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                getMerch.close();

            } catch (Exception ex) {
                throw ex;
            }
        }
        return merchantList;
    }

    public int getCountTxn(Connection con, String condition, String merchant) throws Exception {

        int count = 0;
        ResultSet rs = null;
        PreparedStatement getTxnCount = null;
        String strSqlBasic = null;
        try {

            strSqlBasic = "SELECT COUNT(*) AS TOTTXN "
                    + "FROM ECMS_ONLINE_TRANSACTION OL,ECMS_ONLINE_TXNTYPE TT,ECMS_ONLINE_STATUS ST  "
                    + "WHERE ST.CODE = OL.STATUS AND TT.TYPECODE=OL.TXNTYPECODE AND OL.MID = ? AND " + condition;

            //System.out.println(strSqlBasic);
            getTxnCount = con.prepareStatement(strSqlBasic);
            getTxnCount.setString(1, merchant);

            rs = getTxnCount.executeQuery();

            while (rs.next()) {

                count = rs.getInt("TOTTXN");

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getTxnCount.close();

        }

        return count;
    }

    public List<TransactionBean> getTxnExpDetails(Connection con, String condition, int start, int end, String merchantId, List<TransactionBean> txnList) throws Exception {
        ResultSet rs = null;
        PreparedStatement getTxnDt = null;
        String strSqlBasic = null;

        try {

            strSqlBasic = " SELECT * FROM ( SELECT * FROM ( SELECT OL.TXNID,OL.AUTHCODE,"
                    + " OL.BIN,OL.CARDNO,OL.EXPIRYDATE,OL.MID,OL.STATUS,OL.TID,OL.TRACENO,"
                    + " OL.TXNDATE,OL.TXNTIME,OL.TXNTYPECODE,OL.TXNCURRENCY,OL.TXNAMOUNT,"
                    + " OL.SETTLEMENTDATE,OL.SERVICECODE,"
                    + " ST.DESCRIPTION AS STATUSDES,  TT.DESCRYPTION AS TXNDES, ROWNUM R"
                    + " FROM ECMS_ONLINE_TRANSACTION OL,  "
                    + " ECMS_ONLINE_STATUS ST,ECMS_ONLINE_TXNTYPE TT "
                    + " WHERE ST.CODE = OL.STATUS AND TT.TYPECODE=OL.TXNTYPECODE AND OL.MID = ? AND "
                    + condition + "  )  WHERE R <=  " + end + "  ) WHERE R > " + start;

            System.out.println("************" + strSqlBasic);

            getTxnDt = con.prepareStatement(strSqlBasic);
            getTxnDt.setString(1, merchantId);

            rs = getTxnDt.executeQuery();
            //txnList = new ArrayList<TransactionBean>();

            while (rs.next()) {

                TransactionBean resultBean = new TransactionBean();

                resultBean.setAuthCode((rs.getString("AUTHCODE") == null) ? "--" : rs.getString("AUTHCODE"));
                resultBean.setBin((rs.getString("BIN") == null) ? "--" : rs.getString("BIN"));
                resultBean.setCardNumber((rs.getString("CARDNO") == null) ? "--" : rs.getString("CARDNO"));
                resultBean.setTxnAmount((rs.getString("TXNAMOUNT") == null) ? "--" : rs.getString("TXNAMOUNT"));
                resultBean.setExpiryDate((rs.getString("EXPIRYDATE") == null) ? "--" : rs.getString("EXPIRYDATE"));
                resultBean.setMerchantId((rs.getString("MID") == null) ? "--" : rs.getString("MID"));
                resultBean.setSettlementDate((rs.getString("SETTLEMENTDATE") == null) ? "--" : rs.getString("SETTLEMENTDATE"));
                resultBean.setTerminalId((rs.getString("TID") == null) ? "--" : rs.getString("TID"));
                resultBean.setTraceNum((rs.getString("TRACENO") == null) ? "--" : rs.getString("TRACENO"));
                resultBean.setTxnCurrency((rs.getString("txncurrency") == null) ? "--" : rs.getString("txncurrency"));
                resultBean.setTxnDate((rs.getString("TXNDATE") == null) ? "--" : rs.getString("TXNDATE"));
                resultBean.setTxnId((rs.getString("TXNID") == null) ? "--" : rs.getString("TXNID"));
                resultBean.setTxnTime((rs.getString("TXNTIME") == null) ? "--" : rs.getString("TXNTIME"));
                resultBean.setTxnType((rs.getString("TXNTYPECODE") == null) ? "--" : rs.getString("TXNTYPECODE"));
                resultBean.setTxnTypeDes((rs.getString("TXNDES") == null) ? "--" : rs.getString("TXNDES"));
                resultBean.setTxnStatus((rs.getString("status") == null) ? "--" : rs.getString("status"));
                resultBean.setTxnStatusSDes((rs.getString("STATUSDES") == null) ? "--" : rs.getString("STATUSDES"));

                txnList.add(resultBean);

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getTxnDt.close();

        }
        return txnList;
    }

    public List<TransactionBean> getMerchantTxnExpDetails(Connection con, String condition, int start, int end, String merchantId) throws Exception {
        ResultSet rs = null;
        PreparedStatement getTxnDt = null;
        String strSqlBasic = null;

        try {

            strSqlBasic = " SELECT * FROM ( SELECT * FROM ( SELECT OL.TXNID,OL.AUTHCODE,"
                    + " OL.BIN,OL.CARDNO,OL.EXPIRYDATE,OL.MID,OL.STATUS,OL.TID,OL.TRACENO,"
                    + " OL.TXNDATE,OL.TXNTIME,OL.TXNTYPECODE,OL.TXNCURRENCY,OL.TXNAMOUNT,"
                    + " OL.SETTLEMENTDATE,OL.SERVICECODE,"
                    + " ST.DESCRIPTION AS STATUSDES,  TT.DESCRYPTION AS TXNDES, ROWNUM R"
                    + " FROM ECMS_ONLINE_TRANSACTION OL,  "
                    + " ECMS_ONLINE_STATUS ST,ECMS_ONLINE_TXNTYPE TT "
                    + " WHERE ST.CODE = OL.STATUS AND TT.TYPECODE=OL.TXNTYPECODE AND OL.MID = ? AND "
                    + condition + "  )  WHERE R <=  " + end + "  ) WHERE R > " + start;

            System.out.println("************" + strSqlBasic);

            getTxnDt = con.prepareStatement(strSqlBasic);
            getTxnDt.setString(1, merchantId);

            rs = getTxnDt.executeQuery();
            txnList = new ArrayList<TransactionBean>();

            while (rs.next()) {

                TransactionBean resultBean = new TransactionBean();

                resultBean.setAuthCode((rs.getString("AUTHCODE") == null) ? "--" : rs.getString("AUTHCODE"));
                resultBean.setBin((rs.getString("BIN") == null) ? "--" : rs.getString("BIN"));
                resultBean.setCardNumber((rs.getString("CARDNO") == null) ? "--" : rs.getString("CARDNO"));
                resultBean.setTxnAmount((rs.getString("TXNAMOUNT") == null) ? "--" : rs.getString("TXNAMOUNT"));
                resultBean.setExpiryDate((rs.getString("EXPIRYDATE") == null) ? "--" : rs.getString("EXPIRYDATE"));
                resultBean.setMerchantId((rs.getString("MID") == null) ? "--" : rs.getString("MID"));
                resultBean.setSettlementDate((rs.getString("SETTLEMENTDATE") == null) ? "--" : rs.getString("SETTLEMENTDATE"));
                resultBean.setTerminalId((rs.getString("TID") == null) ? "--" : rs.getString("TID"));
                resultBean.setTraceNum((rs.getString("TRACENO") == null) ? "--" : rs.getString("TRACENO"));
                resultBean.setTxnCurrency((rs.getString("txncurrency") == null) ? "--" : rs.getString("txncurrency"));
                resultBean.setTxnDate((rs.getString("TXNDATE") == null) ? "--" : rs.getString("TXNDATE"));
                resultBean.setTxnId((rs.getString("TXNID") == null) ? "--" : rs.getString("TXNID"));
                resultBean.setTxnTime((rs.getString("TXNTIME") == null) ? "--" : rs.getString("TXNTIME"));
                resultBean.setTxnType((rs.getString("TXNTYPECODE") == null) ? "--" : rs.getString("TXNTYPECODE"));
                resultBean.setTxnTypeDes((rs.getString("TXNDES") == null) ? "--" : rs.getString("TXNDES"));
                resultBean.setTxnStatus((rs.getString("status") == null) ? "--" : rs.getString("status"));
                resultBean.setTxnStatusSDes((rs.getString("STATUSDES") == null) ? "--" : rs.getString("STATUSDES"));

                txnList.add(resultBean);

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getTxnDt.close();

        }
        return txnList;
    }

    //get the maximum call log id
    public String getMaxCallLogId(Connection con) throws SQLException, Exception {

        String id = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT MAX(CALLLOGID) FROM CALLLOG";

            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getString("MAX(CALLLOGID)");
            }

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
        return id;
    }

    //insert the call log record
    public int insertCallLog(Connection con, CallLogBean logBean) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "insert into CALLLOG (CALLLOGID, SEARCHKEY, CALLERTYPE, REFERENCETYPE, REFERENCENO, "
                    + "LASTUPDATEDUSER, LASTUPDATEDTIME, CREATEDTIME) values (?,?,?,?,?,?,SYSDATE, SYSDATE)";

            ps = con.prepareStatement(query);

            ps.setString(1, logBean.getCallLogId());
            ps.setString(2, logBean.getSearchKey());
            ps.setString(3, logBean.getCallerType());
            ps.setString(4, logBean.getReferType());
            ps.setString(5, logBean.getReferNo());
            ps.setString(6, logBean.getLastUpdatedUser());

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

    //insert call history record
    public int insertCallHistory(CallHistoryBean historyBean, Connection con) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;

        try {
            String query = "insert into CALLHISTORY (CALLLOGID, OPERATION, OLDVALUE, NEWVALUE, " //CALLHISTORYID, 
                    + "REMARKS, CARDNO, ACCOUNTNO, CUSTOMERID, APPLICATIONID, MID, TID, PAGECODE, "
                    + "LASTUPDATEDUSER, LASTUPDATEDTIME, CREATEDDATE) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE, SYSDATE)"; //?,

            ps = con.prepareStatement(query);

//            ps.setString(1, historyBean.getHistoryId());
            ps.setString(1, historyBean.getCallLogId());
            ps.setString(2, historyBean.getOperation());
            ps.setString(3, historyBean.getOldValue());
            ps.setString(4, historyBean.getNewValue());
            ps.setString(5, historyBean.getRemarks());
            ps.setString(6, historyBean.getCardNo());
            ps.setString(7, historyBean.getAccountNo());
            ps.setString(8, historyBean.getCustomerId());
            ps.setString(9, historyBean.getApplicationId());
            ps.setString(10, historyBean.getMid());
            ps.setString(11, historyBean.getTid());
            ps.setString(12, historyBean.getPageCode());
            ps.setString(13, historyBean.getLastUpdatedUser());

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

     //

    public void addCustomerDetailChangeRequest(CustomerPersonalBean customerPersonalBean, CustomerContactDetailsChngeHolderBean changeTracker, String cardNumber, String cardCategory,Connection con) throws SQLException, Exception {
        PreparedStatement ps = null;
        StringBuffer basicQuery,insertColumnQry, insertValueQry;

        try {
                basicQuery      =new StringBuffer("");
                insertColumnQry =new StringBuffer("INSERT INTO CUSCONTACTDETAILCHANGEREQUEST (APPLICATIONID,IDENTIFICATIONNUMBER ,CARDCATEGORY,CARDNUMBER, "
                                + "STATUS,LASTUPDATEDTIME,CREATEDTIME,REQUESTEDUSER,LASTUPDATEDUSER");
                insertValueQry  =new StringBuffer(" VALUES (?,?,?,?,?,SYSDATE,SYSDATE,?,?" );
                int statementIndex=0;
                
                if(changeTracker.isPermanentAddressChanged()){
                    insertColumnQry.append(",ISPERMANENTADDRESSCHANGED,PERMANENTADDRESS1, PERMANENTADDRESS2,PERMANANTADDRESS3, PERMANAENTCITY ");
                    insertValueQry.append(",?,?,?,?,?");
                }
                
                if(changeTracker.isResidenceAddressChanged()){
                    insertColumnQry.append(",ISRESIDENCEADDRESSCHANGED,ADDRESS1, ADDRESS2,ADDRESS3, CITY");
                    insertValueQry.append(",?,?,?,?,?");
                }
                
                if(changeTracker.isBillingAddressChanged()){
                    insertColumnQry.append(",ISBILLINGADDRESSCHANGED, BILLINGADDRESS1,BILLINGADDRESS2, BILLINGADDRESS3,BILLINGCITY");
                    insertValueQry.append(",?,?,?,?,?");
                }
                
                if(changeTracker.isCompanyAddressChanged()){
                    insertColumnQry.append(",ISCOMPANYADDRESSCHANGED, BUSINESSADDRESS1,BUSINESSADDRESS2, BUSINESSADDRESS3,BUSINESSCITY");
                    insertValueQry.append(",?,?,?,?,?");
                }
                
                if(changeTracker.isResPhoneNoChanged()){
                    insertColumnQry.append(",ISRESPHONENOCHANGED,HOMETELEPHONENO");
                    insertValueQry.append(",?,?");
                }
                
                if(changeTracker.isMobilePhoneNoChanged()){
                    insertColumnQry.append(",ISMOBILEPHONENOCHANGED,MOBILENO");
                    insertValueQry.append(",?,?");
                }
                
                if(changeTracker.isOfficePhoneNoChanged()){
                    insertColumnQry.append(",ISOFFICEPHONENOCHANGED,OFFICEPHONENO");
                    insertValueQry.append(",?,?");
                }
                
                if(changeTracker.isEstMobContactNoChanged()){
                    insertColumnQry.append(",ISESTMOBCONTACTNOCHANGED,CONTACTNUMBERSLAND");
                    insertValueQry.append(",?,?");
                }
                
                if(changeTracker.isEstLandContactNoChanged()){
                    insertColumnQry.append(",ISESTLANDCONTACTNOCHANGED,CONTACTNUMBERSMOBILE");
                    insertValueQry.append(",?,?");
                }
                
                if(changeTracker.isFaxNoChanged()){
                    insertColumnQry.append(",ISFAXNOCHANGED,FAXNUMBER");
                    insertValueQry.append(",?,?");
                }
                
                insertColumnQry.append(" )");
                insertValueQry.append(" )");
                basicQuery.append(insertColumnQry);
                basicQuery.append(insertValueQry);

                ps = con.prepareStatement(basicQuery.toString());
                
                ps.setString(++statementIndex, customerPersonalBean.getApplicationId());
                ps.setString(++statementIndex, customerPersonalBean.getIdentificationNumber());
                ps.setString(++statementIndex, cardCategory);
                ps.setString(++statementIndex, cardNumber);
                ps.setString(++statementIndex, StatusVarList.DA_REQUSET_INITIATE ); 
                ps.setString(++statementIndex, customerPersonalBean.getLastUpdateUser());
                ps.setString(++statementIndex, customerPersonalBean.getLastUpdateUser());
                

                if (changeTracker.isPermanentAddressChanged()) {
                    ps.setBoolean(++statementIndex, changeTracker.isPermanentAddressChanged());
                    ps.setString(++statementIndex, customerPersonalBean.getPermentAddress1());
                    ps.setString(++statementIndex, customerPersonalBean.getPermentAddress2());
                    ps.setString(++statementIndex, customerPersonalBean.getPermentAddress3());
                    ps.setString(++statementIndex, customerPersonalBean.getPermentCity());

                }

                if (changeTracker.isResidenceAddressChanged()) {
                    ps.setBoolean(++statementIndex, changeTracker.isResidenceAddressChanged());
                    ps.setString(++statementIndex, customerPersonalBean.getAddress1());
                    ps.setString(++statementIndex, customerPersonalBean.getAddress2());
                    ps.setString(++statementIndex, customerPersonalBean.getAddress3());
                    ps.setString(++statementIndex, customerPersonalBean.getCity());
                }

                if (changeTracker.isBillingAddressChanged()) {
                    ps.setBoolean(++statementIndex, changeTracker.isBillingAddressChanged());
                    ps.setString(++statementIndex, customerPersonalBean.getBillAddress1());
                    ps.setString(++statementIndex, customerPersonalBean.getBillAddress2());
                    ps.setString(++statementIndex, customerPersonalBean.getBillAddress3());
                    ps.setString(++statementIndex, customerPersonalBean.getBillCity());
                }

                if (changeTracker.isCompanyAddressChanged()) {
                    
                    ps.setBoolean(++statementIndex, changeTracker.isCompanyAddressChanged());
                    ps.setString(++statementIndex, customerPersonalBean.getCompanyAddress1());
                    ps.setString(++statementIndex, customerPersonalBean.getCompanyAddress2());
                    ps.setString(++statementIndex, customerPersonalBean.getCompanyAddress3());
                    ps.setString(++statementIndex, customerPersonalBean.getCompanyCity());
                }

                if (changeTracker.isResPhoneNoChanged()) {
                    ps.setBoolean(++statementIndex, changeTracker.isResPhoneNoChanged());
                    ps.setString(++statementIndex, customerPersonalBean.getHomeTelNumber());
                }

                if (changeTracker.isMobilePhoneNoChanged()) {
                    ps.setBoolean(++statementIndex, changeTracker.isMobilePhoneNoChanged());                    
                    ps.setString(++statementIndex, customerPersonalBean.getMobileNumber());
                }

                if (changeTracker.isOfficePhoneNoChanged()) {
                    ps.setBoolean(++statementIndex, changeTracker.isOfficePhoneNoChanged());                    
                    ps.setString(++statementIndex, customerPersonalBean.getOfficeTelNumber());
                }

                if (changeTracker.isEstMobContactNoChanged()) {
                    ps.setBoolean(++statementIndex, changeTracker.isEstMobContactNoChanged());                    
                    ps.setString(++statementIndex, customerPersonalBean.getEstMobileNo());
                }

                if (changeTracker.isEstLandContactNoChanged()) {
                    ps.setBoolean(++statementIndex, changeTracker.isEstLandContactNoChanged());                    
                    ps.setString(++statementIndex, customerPersonalBean.getEstLandNo());
                }

                if (changeTracker.isFaxNoChanged()) {
                    ps.setBoolean(++statementIndex, changeTracker.isFaxNoChanged());                    
                    ps.setString(++statementIndex, customerPersonalBean.getCompanyAddress1());
                }


            ps.executeUpdate();

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
    }

    public List<ViewDataBean> searchCustomerwithCategory(Connection con, CustomerSearchBean filledBean, String cardCategoryCode) throws SQLException, Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;

        cusList = new ArrayList<ViewDataBean>();

        try {
            String joinTable="";
            String retriveColumns="";
            String joinCondition="";
            
            
            switch (cardCategoryCode) {
                case StatusVarList.CARD_CATEGORY_SUPPLEMENTARY:
                    joinTable=" SUPPLEMENTARYAPPLICATION PD ";
                    retriveColumns="  PD.TITLE ," +
                            "  PD.APPLICATIONID ";
                    
                    joinCondition=" (CC.IDENTIFICATIONNO=PD.NIC " +
                            "OR CC.IDENTIFICATIONNO  =PD.PASSPORTNUMBER) ";
                    
                    break;
                case StatusVarList.CARD_CATEGORY_ESTABLISHMENT:
                    joinTable=" ESTABLISHMENTDETAILS PD ";
                    retriveColumns=" ' ' AS   TITLE ," +
                            "  PD.APPLICATIONID ";
                    
                    joinCondition=" CC.IDENTIFICATIONNO=PD.BUSINESSREGNUMBER ";
                            
                    
                    break;
            }
                
            String query =  " SELECT DISTINCT CC.CUSTOMERID AS CC_CUSTOMERID , " +
                            "  CC.IDENTIFICATIONTYPE       AS CC_IDENTIFICATIONTYPE, " +//
                            "  CC.IDENTIFICATIONNO         AS CC_IDENTIFICATIONNO, " +//
                            "  CC.CUSTOMERNAME             AS CC_CUSTOMERNAME, " +//
//                            "  CC.CREDITLIMIT              AS CC_CREDITLIMIT, " +
//                            "  CC.CASHLIMIT                AS CC_CASHLIMIT, " +
//                            "  CC.CURRENCYCODE             AS CC_CURRENCYCODE, " +
//                            "  CC.RISKPROFILECODE          AS CC_RISKPROFILECODE, " +
//                            "  CC.STAFFSTATUS              AS CC_STAFFSTATUS, " +
//                            "  CC.CUSTOMERSTATUS           AS CC_CUSTOMERSTATUS, " +
                            "  ST.DESCRIPTION              AS STDES, " +
                            "  ad.documentname             AS idtypedes, " +//
//                            "  CUR.DESCRIPTION             AS curdes, " +
                            "  C.ADDRESS1                  AS AD1, "+
                            "  C.ADDRESS2                  AS AD2, " +
                            "  C.ADDRESS3                  AS AD3, " +
//                          "  C.ADDRESS4                  AS AD4, " +
                            
                            "  C.CARDCATEGORYCODE          AS CARDCATEGORYCODE ,"+ 
                    
//                            "  PD.NAMEINFULL, " +
//                            "  PD.GENDER, " +
//                            "  PD.NATIONALITY, " +
//                            "  PD.NIC, " +
//                            "  PD.PASSPORTNUMBER, " +
//                            "  PD.PASSPORTEXPIREDATE, " +
//                            "  PD.AGE, " +
//                            "  PD.DATEOFBIRTH, " +
//                            "  PD.MARITALSTATUS, " +
//                            "  PD.NAMEONCARD, " +
//                            "  PD.MOTHERSMAIDENNAME, " +
//                            "  PD.EMAIL, " +
//                            "  PD.MOBILENO, " +
//                            "  PD.EMERGENCYCONTACTNO, " +
//                            "  PD.HOMETELEPHONENO, " +
//                            "  PD.OFFICEPHONENO, " +
//                            "  PD.ADDRESS1, " +
//                            "  PD.ADDRESS2, " +
//                            "  PD.ADDRESS3, " +
//                            "  PD.CITY, " +
//                            "  PD.RESIDEDISTRICT, " +
//                            "  PD.RESIDEPROVINCE, " +
//                            "  PD.BILLINGADDRESS1, " +
//                            "  PD.BILLINGADDRESS2, " +
//                            "  PD.BILLINGADDRESS3, " +
//                            "  PD.BILLINGCITY, " +
//                            "  PD.BILLINGDISTRICT, " +
//                            "  PD.BILLINGPROVINCE, " +
//                            "  PD.PERMANENTADDRESS1, " +
//                            "  PD.PERMANENTADDRESS2, " +
//                            "  PD.PERMANANTADDRESS3, " +
//                            "  PD.PERMANAENTCITY, " +
//                            "  PD.PERMANENTDISTRICT, " +
//                            "  PD.PERMANENTPROVINCE, " +
//                            "  PD.RELATIVENAME, " +
//                            "  PD.RELMOBILENO, " +
//                            "  PD.RELOFFICEPHONE, " +
//                            "  PD.RELRESIDANCEPHONE, " +
//                            "  PD.RELEMAIL, " +
//                            "  PD.RELATIONSHIP, " +
//                            "  PD.RELADDRESS1, " +
//                            "  PD.RELADDRESS2, " +
//                            "  PD.RELADDRESS3, " +
//                            "  PD.RELCITY, " +
//                            "  PD.RELDISTRICT, " +
//                            "  PD.RELPROVINCE, " +
//                            "  PD.SPOUSENAME, " +
//                            "  PD.SPOUSENIC, " +
//                            "  PD.SPOUSEPASSPORTNO, " +
//                            "  PD.SPOUSEPHONE, " +
//                            "  PD.SPOUSEEMAIL, " +
//                            "  PD.SPOUSEDATEOFBIRTH, " +
//                            "  PD.NOOFDEPENDANCE , " +
                            retriveColumns+
                            "FROM CARD C, " +
                            "  CARDCUSTOMER CC, " +
                            "  STATUS ST, " +
                            "  CARDACCOUNTCUSTOMER CAC, " +
                            "  APPLICATIONDOCUMENT AD, " +
                            "  CURRENCY CUR, " +
                            joinTable+
                            "WHERE rownum           <=1 " +
                            "AND C.CARDNUMBER        =CAC.CARDNUMBER " +
                            "AND CAC.CUSTOMERID      =CC.CUSTOMERID " +
                            "AND CC.CUSTOMERSTATUS   =ST.STATUSCODE " +
                            "AND ad.documenttypecode =CC.IDENTIFICATIONTYPE " +
                            "AND CC.CURRENCYCODE     =CUR.CURRENCYNUMCODE " +
                            "AND "+joinCondition;
            

            String condition = "";

            //to check whether cards exist
            //   if (!isCardExsists(con, filledBean).equals("")) {
            if (!filledBean.getCardnumber().equals("") || filledBean.getCardnumber().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "C.CARDNUMBER = '" + filledBean.getCardnumber() + "' AND CAC.cardnumber= C.CARDNUMBER ";
            }

            if (!filledBean.getAccount().equals("") || filledBean.getAccount().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CAC.ACCOUNTNO = '" + filledBean.getAccount() + "'";
            }

            if (!filledBean.getNic().equals("") || filledBean.getNic().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CC.IDENTIFICATIONNO = '" + filledBean.getNic() + "' and CC.IDENTIFICATIONTYPE='NIC' ";
            }

            if (!filledBean.getPassport().equals("") || filledBean.getPassport().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CC.IDENTIFICATIONNO = '" + filledBean.getPassport() + "' and CC.IDENTIFICATIONTYPE='PAS' ";
            }

            if (!filledBean.getCif().equals("") || filledBean.getCif().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CAC.CUSTOMERID = '" + filledBean.getCif() + "'";
            }

            if (!condition.equals("")) {
                query += " AND " + condition;

            }

            ps = con.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {

                viewBean = new ViewDataBean();
                //CARDCUSTOMER AS CC
                viewBean.setCustmerIdCC(rs.getString("CC_CUSTOMERID"));
                viewBean.setIdTypeCC(rs.getString("idtypedes"));//CC_IDENTIFICATIONTYPE
                viewBean.setIdNumberCC(rs.getString("CC_IDENTIFICATIONNO"));
                viewBean.setCustomerName(rs.getString("CC_CUSTOMERNAME"));
//                viewBean.setCreditLimitCC(rs.getString("CC_CREDITLIMIT"));
//                viewBean.setCashLimitCC(rs.getString("CC_CASHLIMIT"));
//                viewBean.setCurrencyCodeCC(rs.getString("curdes"));//CC_CURRENCYCODE
//                viewBean.setRiskProfileCodeCC(rs.getString("CC_RISKPROFILECODE"));
//                viewBean.setStaffStatusCC(rs.getString("CC_STAFFSTATUS"));
//                //viewBean.setCustomerStatusCC(rs.getString("CC_CUSTOMERSTATUS"));
                viewBean.setCustomerStatusCC(rs.getString("STDES"));
                viewBean.setAppId(rs.getString("APPLICATIONID"));
                
                String address = "";
                if (rs.getString("AD1") != null) {
                    address = rs.getString("AD1");
                }
                if (rs.getString("AD2") != null) {
                    address = address + ", " + rs.getString("AD2");
                }
                if (rs.getString("AD3") != null) {
                    address = address + ", " + rs.getString("AD3");
                }

                viewBean.setAddressCC(address);

                viewBean.setTitlepd(rs.getString("TITLE"));
//                viewBean.setNameinfull(rs.getString("NAMEINFULL"));
//                viewBean.setGenderpd(rs.getString("GENDER"));
//                viewBean.setNationality(rs.getString("NATIONALITY"));
//                viewBean.setNic(rs.getString("NIC"));
//                viewBean.setPassportnumber(rs.getString("PASSPORTNUMBER"));
//                viewBean.setPassportexpiredate(rs.getString("PASSPORTEXPIREDATE"));
//                viewBean.setAge(rs.getString("AGE"));
//                viewBean.setDateofbirthpd(rs.getString("DATEOFBIRTH"));
//                viewBean.setMaritalstatuspd(rs.getString("MARITALSTATUS"));
//                viewBean.setNameoncardpd(rs.getString("NAMEONCARD"));
//                viewBean.setMothersmaidenname(rs.getString("MOTHERSMAIDENNAME"));
//
//                viewBean.setEmailpd(rs.getString("EMAIL"));
//                viewBean.setMobilenopd(rs.getString("MOBILENO"));
//                viewBean.setEmergencycontactno(rs.getString("EMERGENCYCONTACTNO"));
//                viewBean.setHometelephoneno(rs.getString("HOMETELEPHONENO"));
//                viewBean.setOfficephoneno(rs.getString("OFFICEPHONENO"));
//                viewBean.setAddress1(rs.getString("ADDRESS1"));
//                viewBean.setAddress2(rs.getString("ADDRESS2"));
//                viewBean.setAddress3(rs.getString("ADDRESS3"));
//                viewBean.setCitypd(rs.getString("CITY"));
//                viewBean.setResidedistrict(rs.getString("RESIDEDISTRICT"));
//                viewBean.setResideprovince(rs.getString("RESIDEPROVINCE"));
//                viewBean.setBillingaddress1(rs.getString("BILLINGADDRESS1"));
//                viewBean.setBillingaddress2(rs.getString("BILLINGADDRESS2"));
//                viewBean.setBillingaddress3(rs.getString("BILLINGADDRESS3"));
//                viewBean.setBillingcity(rs.getString("BILLINGCITY"));
//                viewBean.setBillingdistrict(rs.getString("BILLINGDISTRICT"));
//                viewBean.setBillingprovince(rs.getString("BILLINGPROVINCE"));
//                viewBean.setPermanentaddress1(rs.getString("PERMANENTADDRESS1"));
//                viewBean.setPermanentaddress2(rs.getString("PERMANENTADDRESS2"));
//                viewBean.setPermanantaddress3(rs.getString("PERMANANTADDRESS3"));
//                viewBean.setPermanaentcity(rs.getString("PERMANAENTCITY"));
//                viewBean.setPermanentdistrict(rs.getString("PERMANENTDISTRICT"));
//                viewBean.setPermanentprovince(rs.getString("PERMANENTPROVINCE"));
//
//                viewBean.setRelativename(rs.getString("RELATIVENAME"));
//                viewBean.setRelmobileno(rs.getString("RELMOBILENO"));
//                viewBean.setRelofficephone(rs.getString("RELOFFICEPHONE"));
//                viewBean.setRelresidancephone(rs.getString("RELRESIDANCEPHONE"));
//                viewBean.setRelemail(rs.getString("RELEMAIL"));
//                viewBean.setRelationship(rs.getString("RELATIONSHIP"));
//                viewBean.setReladdress1(rs.getString("RELADDRESS1"));
//                viewBean.setReladdress2(rs.getString("RELADDRESS2"));
//                viewBean.setReladdress3(rs.getString("RELADDRESS3"));
//                viewBean.setRelcity(rs.getString("RELCITY"));
//                viewBean.setReldistrict(rs.getString("RELDISTRICT"));
//                viewBean.setRelprovince(rs.getString("RELPROVINCE"));
//
//                viewBean.setSpousename(rs.getString("SPOUSENAME"));
//                viewBean.setSpousenic(rs.getString("SPOUSENIC"));
//                viewBean.setSpousepassportno(rs.getString("SPOUSEPASSPORTNO"));
//                viewBean.setSpousephone(rs.getString("SPOUSEPHONE"));
//                viewBean.setSpouseemail(rs.getString("SPOUSEEMAIL"));
//                viewBean.setSpousedateofbirth(rs.getString("SPOUSEDATEOFBIRTH"));
//                viewBean.setNoofdependance(rs.getString("NOOFDEPENDANCE"));
                  viewBean.setCardCatCode(rs.getString("CARDCATEGORYCODE"));
                
                cusList.add(viewBean);

            }
            //  } //card details

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

        return cusList;

    }
}
