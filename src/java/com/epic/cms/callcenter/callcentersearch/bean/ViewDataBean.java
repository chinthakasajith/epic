/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.callcentersearch.bean;

import java.util.List;

/**
 *
 * @author badrika
 */
public class ViewDataBean {

    //CARD AS C
    private String cardNumber;
    private String accountNoC;
    private String mainCardNumber;
    private String cardDomain;
    private String cardType;
    private String cardProduct;
    private String cardCatCode;
    private String cardCatCodeDes;
    private String expDate;
    private String activDate;
    private String nameonCard;
    private String cardStatus;
    private String creditLimitC;
    private String cashLimitC;
    private String otbCredit;
    private String otbCash;
    private String tempCrediAmt;
    private String tempCashAmt;
    private String embossStatus;
    private String encodeStatus;
    private String pinStatus;
    private String pinMailStatus;
    private String curencyNoCode;
    private String priorityLevel;
    private String issuedDate;
    private String riskProfileCodeC;
    private String cardDisStatus;
    private String pinDisStatus;

    //BILLINGLASTSTATEMENTSUMMARY
    private String openingBalance;
    private String closingBalance;
    private String minAmount;
    private String payment;
    private String dueDate;
    private String statementStartDate;
    private String statementEndDate;
    private List<TransactionBean> onlinetxnList;
    private List<TransactionBean> backendtxnList;
    private List<StandingOrderBean> stdorderList;

    //BILLINGSTATEMENT
    private String minPaymentDue;
    private String interestRate;
    private String cashAdvancedInterestRate;

    //CARDACCOUNT AS CA
    private String accountNoCA;
    private String custmerIdCA;
    private String billingId;
    private String currencyCodeCA;
    private String riskProfileCodeCA;
    private String status;
    private String accounttypeonac;
    private String accountowneronac;
    private String loyaltypointsonac;
    private String billingDate;

    //ECMS_ONLINE_ACCOUNT
    private String creditLimitCA;
    private String cashLimitCA;
    private String txncountonac;
    private String cashtxncountonac;
    private String totaltxnamountonac;
    private String totalcashtxnamountonac;
    private String otbcreditonac;
    private String otbcashonac;
    

    //CARDCUSTOMER AS CC
    private String custmerIdCC;
    private String idTypeCC;
    private String idNumberCC;
    private String customerName;
    private String creditLimitCC;
    private String cashLimitCC;
    private String currencyCodeCC;
    private String riskProfileCodeCC;
    private String staffStatusCC;
    private String customerStatusCC;
    private String addressCC;

    //CARDAPPLICATIONPERSONALDETAILS  
    private String titlepd;
    private String nameinfull;
    private String genderpd;
    private String nationality;
    private String nic;
    private String passportnumber;
    private String passportexpiredate;
    private String age;
    private String dateofbirthpd;
    private String maritalstatuspd;
    private String nameoncardpd;
    private String mothersmaidenname;
    private String emailpd;
    private String mobilenopd;
    private String emergencycontactno;
    private String hometelephoneno;
    private String officephoneno;
    private String address1;
    private String address2;
    private String address3;
    private String citypd;
    private String residedistrict;
    private String resideprovince;
    private String billingaddress1;
    private String billingaddress2;
    private String billingaddress3;
    private String billingcity;
    private String billingdistrict;
    private String billingprovince;
    private String permanentaddress1;
    private String permanentaddress2;
    private String permanantaddress3;
    private String permanaentcity;
    private String permanentdistrict;
    private String permanentprovince;
    private String relativename;
    private String relmobileno;
    private String relofficephone;
    private String relresidancephone;
    private String relemail;
    private String relationship;
    private String reladdress1;
    private String reladdress2;
    private String reladdress3;
    private String relcity;
    private String reldistrict;
    private String relprovince;
    private String spousename;
    private String spousenic;
    private String spousepassportno;
    private String spousephone;
    private String spouseemail;
    private String spousedateofbirth;
    private String noofdependance;

    //CARDAPPLICATION AS CAP
    private String appId;
    private String idTypeCAP;
    private String idNumberCAP;
    private String title;
    private String initials;
    private String firstName;
    private String middleName;
    private String lastName;
    private String dateofBirth;
    private String gender;
    private String maritalStatus;
    private String priorityLevelCode;
    private String branchCode;
    private String creditScore;
    private String sysRecCreditLimit;
    private String confCreditLimit;
    private String confCardProduct;
    private String staffStatusCAP;
    private String mobileNo;
    private String email;
    private String designation;
    private String city;
    private String statusCAP;

    private String passportexpirydate;
    private String cardnumbercap;
    private String assignstatus;
    private String assignuser;
    private String referancialempno;
    private String netexpenses;
    private String netincome;
    private String netprofit;
    private String cribreport;
    private String recommendletter;
    private String acctemplatecode;
    private String binprofile;
    private String cardtemplatecode;
    private String custemplatecode;
    private String productionmode;
    private String cardcategory;
    private String cardkeyid;
    private String scorecard;
    private String sysrecomendedcardproduct;
    private String sysrecomendedcurrency;
    private String confirmedcurrency;
    private String loyaltyrequired;
    private String signature;
    private String primarysignature;
    private String jointsignature;
    private String rejectreasoncode;
    private String rejectremarks;
    private String rejectverifyremarks;

    public String getMinPaymentDue() {
        return minPaymentDue;
    }

    public void setMinPaymentDue(String minPaymentDue) {
        this.minPaymentDue = minPaymentDue;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public String getCashAdvancedInterestRate() {
        return cashAdvancedInterestRate;
    }

    public void setCashAdvancedInterestRate(String cashAdvancedInterestRate) {
        this.cashAdvancedInterestRate = cashAdvancedInterestRate;
    }

    public List<TransactionBean> getOnlinetxnList() {
        return onlinetxnList;
    }

    public void setOnlinetxnList(List<TransactionBean> onlinetxnList) {
        this.onlinetxnList = onlinetxnList;
    }

    public List<TransactionBean> getBackendtxnList() {
        return backendtxnList;
    }

    public void setBackendtxnList(List<TransactionBean> backendtxnList) {
        this.backendtxnList = backendtxnList;
    }

    public List<StandingOrderBean> getStdorderList() {
        return stdorderList;
    }

    public void setStdorderList(List<StandingOrderBean> stdorderList) {
        this.stdorderList = stdorderList;
    }

    public String getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(String openingBalance) {
        this.openingBalance = openingBalance;
    }

    public String getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(String closingBalance) {
        this.closingBalance = closingBalance;
    }

    public String getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(String minAmount) {
        this.minAmount = minAmount;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatementStartDate() {
        return statementStartDate;
    }

    public void setStatementStartDate(String statementStartDate) {
        this.statementStartDate = statementStartDate;
    }

    public String getStatementEndDate() {
        return statementEndDate;
    }

    public void setStatementEndDate(String statementEndDate) {
        this.statementEndDate = statementEndDate;
    }

    public String getCardDisStatus() {
        return cardDisStatus;
    }

    public void setCardDisStatus(String cardDisStatus) {
        this.cardDisStatus = cardDisStatus;
    }

    public String getPinDisStatus() {
        return pinDisStatus;
    }

    public void setPinDisStatus(String pinDisStatus) {
        this.pinDisStatus = pinDisStatus;
    }

    public String getCardDomain() {
        return cardDomain;
    }

    public void setCardDomain(String cardDomain) {
        this.cardDomain = cardDomain;
    }

    public String getCardCatCode() {
        return cardCatCode;
    }

    public void setCardCatCode(String cardCatCode) {
        this.cardCatCode = cardCatCode;
    }

    public String getCardCatCodeDes() {
        return cardCatCodeDes;
    }

    public void setCardCatCodeDes(String cardCatCodeDes) {
        this.cardCatCodeDes = cardCatCodeDes;
    }

    public String getCurencyNoCode() {
        return curencyNoCode;
    }

    public void setCurencyNoCode(String curencyNoCode) {
        this.curencyNoCode = curencyNoCode;
    }

    public String getRiskProfileCodeC() {
        return riskProfileCodeC;
    }

    public void setRiskProfileCodeC(String riskProfileCodeC) {
        this.riskProfileCodeC = riskProfileCodeC;
    }

    public String getAccountNoC() {
        return accountNoC;
    }

    public void setAccountNoC(String accountNoC) {
        this.accountNoC = accountNoC;
    }

    public String getActivDate() {
        return activDate;
    }

    public void setActivDate(String activDate) {
        this.activDate = activDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardProduct() {
        return cardProduct;
    }

    public void setCardProduct(String cardProduct) {
        this.cardProduct = cardProduct;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCashLimitC() {
        return cashLimitC;
    }

    public void setCashLimitC(String cashLimitC) {
        this.cashLimitC = cashLimitC;
    }

    public String getCreditLimitC() {
        return creditLimitC;
    }

    public void setCreditLimitC(String creditLimitC) {
        this.creditLimitC = creditLimitC;
    }

    public String getEmbossStatus() {
        return embossStatus;
    }

    public void setEmbossStatus(String embossStatus) {
        this.embossStatus = embossStatus;
    }

    public String getEncodeStatus() {
        return encodeStatus;
    }

    public void setEncodeStatus(String encodeStatus) {
        this.encodeStatus = encodeStatus;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getMainCardNumber() {
        return mainCardNumber;
    }

    public void setMainCardNumber(String mainCardNumber) {
        this.mainCardNumber = mainCardNumber;
    }

    public String getNameonCard() {
        return nameonCard;
    }

    public void setNameonCard(String nameonCard) {
        this.nameonCard = nameonCard;
    }

    public String getOtbCash() {
        return otbCash;
    }

    public void setOtbCash(String otbCash) {
        this.otbCash = otbCash;
    }

    public String getTempCashAmt() {
        return tempCashAmt;
    }

    public void setTempCashAmt(String tempCashAmt) {
        this.tempCashAmt = tempCashAmt;
    }

    public String getTempCrediAmt() {
        return tempCrediAmt;
    }

    public void setTempCrediAmt(String tempCrediAmt) {
        this.tempCrediAmt = tempCrediAmt;
    }

    public String getOtbCredit() {
        return otbCredit;
    }

    public void setOtbCredit(String otbCredit) {
        this.otbCredit = otbCredit;
    }

    public String getPinMailStatus() {
        return pinMailStatus;
    }

    public void setPinMailStatus(String pinMailStatus) {
        this.pinMailStatus = pinMailStatus;
    }

    public String getPinStatus() {
        return pinStatus;
    }

    public void setPinStatus(String pinStatus) {
        this.pinStatus = pinStatus;
    }

    public String getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(String priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    //CARDACCOUNT AS CA
    public String getAccountNoCA() {
        return accountNoCA;
    }

    public void setAccountNoCA(String accountNoCA) {
        this.accountNoCA = accountNoCA;
    }

    public String getBillingId() {
        return billingId;
    }

    public void setBillingId(String billingId) {
        this.billingId = billingId;
    }

    public String getCashLimitCA() {
        return cashLimitCA;
    }

    public void setCashLimitCA(String cashLimitCA) {
        this.cashLimitCA = cashLimitCA;
    }

    public String getTxncountonac() {
        return txncountonac;
    }

    public void setTxncountonac(String txncountonac) {
        this.txncountonac = txncountonac;
    }

    public String getCashtxncountonac() {
        return cashtxncountonac;
    }

    public void setCashtxncountonac(String cashtxncountonac) {
        this.cashtxncountonac = cashtxncountonac;
    }

    public String getTotaltxnamountonac() {
        return totaltxnamountonac;
    }

    public void setTotaltxnamountonac(String totaltxnamountonac) {
        this.totaltxnamountonac = totaltxnamountonac;
    }

    public String getTotalcashtxnamountonac() {
        return totalcashtxnamountonac;
    }

    public void setTotalcashtxnamountonac(String totalcashtxnamountonac) {
        this.totalcashtxnamountonac = totalcashtxnamountonac;
    }

    public String getOtbcreditonac() {
        return otbcreditonac;
    }

    public void setOtbcreditonac(String otbcreditonac) {
        this.otbcreditonac = otbcreditonac;
    }

    public String getOtbcashonac() {
        return otbcashonac;
    }

    public void setOtbcashonac(String otbcashonac) {
        this.otbcashonac = otbcashonac;
    }

    public String getAccounttypeonac() {
        return accounttypeonac;
    }

    public void setAccounttypeonac(String accounttypeonac) {
        this.accounttypeonac = accounttypeonac;
    }

    public String getAccountowneronac() {
        return accountowneronac;
    }

    public void setAccountowneronac(String accountowneronac) {
        this.accountowneronac = accountowneronac;
    }

    public String getLoyaltypointsonac() {
        return loyaltypointsonac;
    }

    public void setLoyaltypointsonac(String loyaltypointsonac) {
        this.loyaltypointsonac = loyaltypointsonac;
    }

    public String getCreditLimitCA() {
        return creditLimitCA;
    }

    public void setCreditLimitCA(String creditLimitCA) {
        this.creditLimitCA = creditLimitCA;
    }

    public String getCurrencyCodeCA() {
        return currencyCodeCA;
    }

    public void setCurrencyCodeCA(String currencyCodeCA) {
        this.currencyCodeCA = currencyCodeCA;
    }

    public String getRiskProfileCodeCA() {
        return riskProfileCodeCA;
    }

    public void setRiskProfileCodeCA(String riskProfileCodeCA) {
        this.riskProfileCodeCA = riskProfileCodeCA;
    }

    public String getCustmerIdCA() {
        return custmerIdCA;
    }

    public void setCustmerIdCA(String custmerIdCA) {
        this.custmerIdCA = custmerIdCA;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //CARDCUSTOMER AS CC
    //new
    public String getTitlepd() {
        return titlepd;
    }

    public void setTitlepd(String titlepd) {
        this.titlepd = titlepd;
    }

    public String getNameinfull() {
        return nameinfull;
    }

    public void setNameinfull(String nameinfull) {
        this.nameinfull = nameinfull;
    }

    public String getGenderpd() {
        return genderpd;
    }

    public void setGenderpd(String genderpd) {
        this.genderpd = genderpd;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getPassportnumber() {
        return passportnumber;
    }

    public void setPassportnumber(String passportnumber) {
        this.passportnumber = passportnumber;
    }

    public String getPassportexpiredate() {
        return passportexpiredate;
    }

    public void setPassportexpiredate(String passportexpiredate) {
        this.passportexpiredate = passportexpiredate;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDateofbirthpd() {
        return dateofbirthpd;
    }

    public void setDateofbirthpd(String dateofbirthpd) {
        this.dateofbirthpd = dateofbirthpd;
    }

    public String getMaritalstatuspd() {
        return maritalstatuspd;
    }

    public void setMaritalstatuspd(String maritalstatuspd) {
        this.maritalstatuspd = maritalstatuspd;
    }

    public String getNameoncardpd() {
        return nameoncardpd;
    }

    public void setNameoncardpd(String nameoncardpd) {
        this.nameoncardpd = nameoncardpd;
    }

    public String getMothersmaidenname() {
        return mothersmaidenname;
    }

    public void setMothersmaidenname(String mothersmaidenname) {
        this.mothersmaidenname = mothersmaidenname;
    }

    public String getEmailpd() {
        return emailpd;
    }

    public void setEmailpd(String emailpd) {
        this.emailpd = emailpd;
    }

    public String getMobilenopd() {
        return mobilenopd;
    }

    public void setMobilenopd(String mobilenopd) {
        this.mobilenopd = mobilenopd;
    }

    public String getEmergencycontactno() {
        return emergencycontactno;
    }

    public void setEmergencycontactno(String emergencycontactno) {
        this.emergencycontactno = emergencycontactno;
    }

    public String getHometelephoneno() {
        return hometelephoneno;
    }

    public void setHometelephoneno(String hometelephoneno) {
        this.hometelephoneno = hometelephoneno;
    }

    public String getOfficephoneno() {
        return officephoneno;
    }

    public void setOfficephoneno(String officephoneno) {
        this.officephoneno = officephoneno;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getCitypd() {
        return citypd;
    }

    public void setCitypd(String citypd) {
        this.citypd = citypd;
    }

    public String getResidedistrict() {
        return residedistrict;
    }

    public void setResidedistrict(String residedistrict) {
        this.residedistrict = residedistrict;
    }

    public String getResideprovince() {
        return resideprovince;
    }

    public void setResideprovince(String resideprovince) {
        this.resideprovince = resideprovince;
    }

    public String getBillingaddress1() {
        return billingaddress1;
    }

    public void setBillingaddress1(String billingaddress1) {
        this.billingaddress1 = billingaddress1;
    }

    public String getBillingaddress2() {
        return billingaddress2;
    }

    public void setBillingaddress2(String billingaddress2) {
        this.billingaddress2 = billingaddress2;
    }

    public String getBillingaddress3() {
        return billingaddress3;
    }

    public void setBillingaddress3(String billingaddress3) {
        this.billingaddress3 = billingaddress3;
    }

    public String getBillingcity() {
        return billingcity;
    }

    public void setBillingcity(String billingcity) {
        this.billingcity = billingcity;
    }

    public String getBillingdistrict() {
        return billingdistrict;
    }

    public void setBillingdistrict(String billingdistrict) {
        this.billingdistrict = billingdistrict;
    }

    public String getBillingprovince() {
        return billingprovince;
    }

    public void setBillingprovince(String billingprovince) {
        this.billingprovince = billingprovince;
    }

    public String getPermanentaddress1() {
        return permanentaddress1;
    }

    public void setPermanentaddress1(String permanentaddress1) {
        this.permanentaddress1 = permanentaddress1;
    }

    public String getPermanentaddress2() {
        return permanentaddress2;
    }

    public void setPermanentaddress2(String permanentaddress2) {
        this.permanentaddress2 = permanentaddress2;
    }

    public String getPermanantaddress3() {
        return permanantaddress3;
    }

    public void setPermanantaddress3(String permanantaddress3) {
        this.permanantaddress3 = permanantaddress3;
    }

    public String getPermanaentcity() {
        return permanaentcity;
    }

    public void setPermanaentcity(String permanaentcity) {
        this.permanaentcity = permanaentcity;
    }

    public String getPermanentdistrict() {
        return permanentdistrict;
    }

    public void setPermanentdistrict(String permanentdistrict) {
        this.permanentdistrict = permanentdistrict;
    }

    public String getPermanentprovince() {
        return permanentprovince;
    }

    public void setPermanentprovince(String permanentprovince) {
        this.permanentprovince = permanentprovince;
    }

    public String getRelativename() {
        return relativename;
    }

    public void setRelativename(String relativename) {
        this.relativename = relativename;
    }

    public String getRelmobileno() {
        return relmobileno;
    }

    public void setRelmobileno(String relmobileno) {
        this.relmobileno = relmobileno;
    }

    public String getRelofficephone() {
        return relofficephone;
    }

    public void setRelofficephone(String relofficephone) {
        this.relofficephone = relofficephone;
    }

    public String getRelresidancephone() {
        return relresidancephone;
    }

    public void setRelresidancephone(String relresidancephone) {
        this.relresidancephone = relresidancephone;
    }

    public String getRelemail() {
        return relemail;
    }

    public void setRelemail(String relemail) {
        this.relemail = relemail;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getReladdress1() {
        return reladdress1;
    }

    public void setReladdress1(String reladdress1) {
        this.reladdress1 = reladdress1;
    }

    public String getReladdress2() {
        return reladdress2;
    }

    public void setReladdress2(String reladdress2) {
        this.reladdress2 = reladdress2;
    }

    public String getReladdress3() {
        return reladdress3;
    }

    public void setReladdress3(String reladdress3) {
        this.reladdress3 = reladdress3;
    }

    public String getRelcity() {
        return relcity;
    }

    public void setRelcity(String relcity) {
        this.relcity = relcity;
    }

    public String getReldistrict() {
        return reldistrict;
    }

    public void setReldistrict(String reldistrict) {
        this.reldistrict = reldistrict;
    }

    public String getRelprovince() {
        return relprovince;
    }

    public void setRelprovince(String relprovince) {
        this.relprovince = relprovince;
    }

    public String getSpousename() {
        return spousename;
    }

    public void setSpousename(String spousename) {
        this.spousename = spousename;
    }

    public String getSpousenic() {
        return spousenic;
    }

    public void setSpousenic(String spousenic) {
        this.spousenic = spousenic;
    }

    public String getSpousepassportno() {
        return spousepassportno;
    }

    public void setSpousepassportno(String spousepassportno) {
        this.spousepassportno = spousepassportno;
    }

    public String getSpousephone() {
        return spousephone;
    }

    public void setSpousephone(String spousephone) {
        this.spousephone = spousephone;
    }

    public String getSpouseemail() {
        return spouseemail;
    }

    public void setSpouseemail(String spouseemail) {
        this.spouseemail = spouseemail;
    }

    public String getSpousedateofbirth() {
        return spousedateofbirth;
    }

    public void setSpousedateofbirth(String spousedateofbirth) {
        this.spousedateofbirth = spousedateofbirth;
    }

    public String getNoofdependance() {
        return noofdependance;
    }

    public void setNoofdependance(String noofdependance) {
        this.noofdependance = noofdependance;
    }

    //new
    public String getAddressCC() {
        return addressCC;
    }

    public void setAddressCC(String addressCC) {
        this.addressCC = addressCC;
    }

    public String getCashLimitCC() {
        return cashLimitCC;
    }

    public void setCashLimitCC(String cashLimitCC) {
        this.cashLimitCC = cashLimitCC;
    }

    public String getCreditLimitCC() {
        return creditLimitCC;
    }

    public void setCreditLimitCC(String creditLimitCC) {
        this.creditLimitCC = creditLimitCC;
    }

    public String getCurrencyCodeCC() {
        return currencyCodeCC;
    }

    public void setCurrencyCodeCC(String currencyCodeCC) {
        this.currencyCodeCC = currencyCodeCC;
    }

    public String getCustmerIdCC() {
        return custmerIdCC;
    }

    public void setCustmerIdCC(String custmerIdCC) {
        this.custmerIdCC = custmerIdCC;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerStatusCC() {
        return customerStatusCC;
    }

    public void setCustomerStatusCC(String customerStatusCC) {
        this.customerStatusCC = customerStatusCC;
    }

    public String getIdNumberCC() {
        return idNumberCC;
    }

    public void setIdNumberCC(String idNumberCC) {
        this.idNumberCC = idNumberCC;
    }

    public String getIdTypeCC() {
        return idTypeCC;
    }

    public void setIdTypeCC(String idTypeCC) {
        this.idTypeCC = idTypeCC;
    }

    public String getRiskProfileCodeCC() {
        return riskProfileCodeCC;
    }

    public void setRiskProfileCodeCC(String riskProfileCodeCC) {
        this.riskProfileCodeCC = riskProfileCodeCC;
    }

    public String getStaffStatusCC() {
        return staffStatusCC;
    }

    public void setStaffStatusCC(String staffStatusCC) {
        this.staffStatusCC = staffStatusCC;
    }

    //CARDAPPLICATION AS CAP
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getConfCardProduct() {
        return confCardProduct;
    }

    public void setConfCardProduct(String confCardProduct) {
        this.confCardProduct = confCardProduct;
    }

    public String getConfCreditLimit() {
        return confCreditLimit;
    }

    public void setConfCreditLimit(String confCreditLimit) {
        this.confCreditLimit = confCreditLimit;
    }

    public String getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(String creditScore) {
        this.creditScore = creditScore;
    }

    public String getDateofBirth() {
        return dateofBirth;
    }

    public void setDateofBirth(String dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdNumberCAP() {
        return idNumberCAP;
    }

    public void setIdNumberCAP(String idNumberCAP) {
        this.idNumberCAP = idNumberCAP;
    }

    public String getIdTypeCAP() {
        return idTypeCAP;
    }

    public void setIdTypeCAP(String idTypeCAP) {
        this.idTypeCAP = idTypeCAP;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPriorityLevelCode() {
        return priorityLevelCode;
    }

    public void setPriorityLevelCode(String priorityLevelCode) {
        this.priorityLevelCode = priorityLevelCode;
    }

    public String getStaffStatusCAP() {
        return staffStatusCAP;
    }

    public void setStaffStatusCAP(String staffStatusCAP) {
        this.staffStatusCAP = staffStatusCAP;
    }

    public String getStatusCAP() {
        return statusCAP;
    }

    public void setStatusCAP(String statusCAP) {
        this.statusCAP = statusCAP;
    }

    public String getPassportexpirydate() {
        return passportexpirydate;
    }

    public void setPassportexpirydate(String passportexpirydate) {
        this.passportexpirydate = passportexpirydate;
    }

    public String getCardnumbercap() {
        return cardnumbercap;
    }

    public void setCardnumbercap(String cardnumbercap) {
        this.cardnumbercap = cardnumbercap;
    }

    public String getAssignstatus() {
        return assignstatus;
    }

    public void setAssignstatus(String assignstatus) {
        this.assignstatus = assignstatus;
    }

    public String getAssignuser() {
        return assignuser;
    }

    public void setAssignuser(String assignuser) {
        this.assignuser = assignuser;
    }

    public String getReferancialempno() {
        return referancialempno;
    }

    public void setReferancialempno(String referancialempno) {
        this.referancialempno = referancialempno;
    }

    public String getNetexpenses() {
        return netexpenses;
    }

    public void setNetexpenses(String netexpenses) {
        this.netexpenses = netexpenses;
    }

    public String getNetincome() {
        return netincome;
    }

    public void setNetincome(String netincome) {
        this.netincome = netincome;
    }

    public String getNetprofit() {
        return netprofit;
    }

    public void setNetprofit(String netprofit) {
        this.netprofit = netprofit;
    }

    public String getCribreport() {
        return cribreport;
    }

    public void setCribreport(String cribreport) {
        this.cribreport = cribreport;
    }

    public String getRecommendletter() {
        return recommendletter;
    }

    public void setRecommendletter(String recommendletter) {
        this.recommendletter = recommendletter;
    }

    public String getAcctemplatecode() {
        return acctemplatecode;
    }

    public void setAcctemplatecode(String acctemplatecode) {
        this.acctemplatecode = acctemplatecode;
    }

    public String getBinprofile() {
        return binprofile;
    }

    public void setBinprofile(String binprofile) {
        this.binprofile = binprofile;
    }

    public String getCardtemplatecode() {
        return cardtemplatecode;
    }

    public void setCardtemplatecode(String cardtemplatecode) {
        this.cardtemplatecode = cardtemplatecode;
    }

    public String getCustemplatecode() {
        return custemplatecode;
    }

    public void setCustemplatecode(String custemplatecode) {
        this.custemplatecode = custemplatecode;
    }

    public String getProductionmode() {
        return productionmode;
    }

    public void setProductionmode(String productionmode) {
        this.productionmode = productionmode;
    }

    public String getCardcategory() {
        return cardcategory;
    }

    public void setCardcategory(String cardcategory) {
        this.cardcategory = cardcategory;
    }

    public String getCardkeyid() {
        return cardkeyid;
    }

    public void setCardkeyid(String cardkeyid) {
        this.cardkeyid = cardkeyid;
    }

    public String getScorecard() {
        return scorecard;
    }

    public void setScorecard(String scorecard) {
        this.scorecard = scorecard;
    }

    public String getSysrecomendedcardproduct() {
        return sysrecomendedcardproduct;
    }

    public void setSysrecomendedcardproduct(String sysrecomendedcardproduct) {
        this.sysrecomendedcardproduct = sysrecomendedcardproduct;
    }

    public String getSysrecomendedcurrency() {
        return sysrecomendedcurrency;
    }

    public void setSysrecomendedcurrency(String sysrecomendedcurrency) {
        this.sysrecomendedcurrency = sysrecomendedcurrency;
    }

    public String getConfirmedcurrency() {
        return confirmedcurrency;
    }

    public void setConfirmedcurrency(String confirmedcurrency) {
        this.confirmedcurrency = confirmedcurrency;
    }

    public String getLoyaltyrequired() {
        return loyaltyrequired;
    }

    public void setLoyaltyrequired(String loyaltyrequired) {
        this.loyaltyrequired = loyaltyrequired;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPrimarysignature() {
        return primarysignature;
    }

    public void setPrimarysignature(String primarysignature) {
        this.primarysignature = primarysignature;
    }

    public String getJointsignature() {
        return jointsignature;
    }

    public void setJointsignature(String jointsignature) {
        this.jointsignature = jointsignature;
    }

    public String getRejectreasoncode() {
        return rejectreasoncode;
    }

    public void setRejectreasoncode(String rejectreasoncode) {
        this.rejectreasoncode = rejectreasoncode;
    }

    public String getRejectremarks() {
        return rejectremarks;
    }

    public void setRejectremarks(String rejectremarks) {
        this.rejectremarks = rejectremarks;
    }

    public String getRejectverifyremarks() {
        return rejectverifyremarks;
    }

    public void setRejectverifyremarks(String rejectverifyremarks) {
        this.rejectverifyremarks = rejectverifyremarks;
    }

    public String getSysRecCreditLimit() {
        return sysRecCreditLimit;
    }

    public void setSysRecCreditLimit(String sysRecCreditLimit) {
        this.sysRecCreditLimit = sysRecCreditLimit;
    }

    //to use in merchant search
    private String merCusNum;
    private String name;
    //private String city;
    private String country;
    private String tele;
    private String terName;
    private String serialNum;
    private String manufacturer;
    private String model;

    //use in merchant search
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getMerCusNum() {
        return merCusNum;
    }

    public void setMerCusNum(String merCusNum) {
        this.merCusNum = merCusNum;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getTerName() {
        return terName;
    }

    public void setTerName(String terName) {
        this.terName = terName;
    }

    public String getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(String billingDate) {
        this.billingDate = billingDate;
    }
    
    
}
