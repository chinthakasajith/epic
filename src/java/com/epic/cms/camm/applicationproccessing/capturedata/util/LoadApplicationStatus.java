/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.capturedata.util;

import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationStatusBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardBankDetailsBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DocumentUploadBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EstablishmentAssetsBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EstablishmentDetailsBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.EstablishmentLiabilityBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.IdBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.ApplicationCheckingManager;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.CaptureDataManager;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author prageeth_s
 */
public class LoadApplicationStatus {

    private static CaptureDataManager manager;
    private static ApplicationCheckingManager checkingManager;
    private static CardApplicationStatusBean appStatusBean = null;
    private static CustomerPersonalBean customerPersonalBean = null;
    private static List<CardBankDetailsBean> bankDetailsBeanLst = null;
    private static List<DocumentUploadBean> documentDetailsBeanLst = null;
    private static EstablishmentDetailsBean establishmentDetailsBean = null;
    private static List<EstablishmentLiabilityBean> establishmentLiabilityList = null;
    private static List<EstablishmentAssetsBean> establishmentAssetList = null;

    public static void loadDefaultCardagainstFDApplicationStatus(String appliactionId, SessionVarList sessionVarlist, HttpServletRequest request, Boolean isDBSave, Integer actionSelectedTabIndex) throws Exception {
        getAllApplicationStatus(appliactionId);
        String loadTabIndex = "";
        String selectedTab = "0";

        if (appStatusBean != null) {
            if (appStatusBean.getApplicationStatus().equals(StatusVarList.APP_INITIATE) || appStatusBean.getApplicationStatus().equals(StatusVarList.APP_PROCESS)) {

                if (appStatusBean.getTableOne().equals("1")) {
                    getAllDetailsCustomer(appliactionId);
                    request.setAttribute("personalBean", customerPersonalBean);
                    sessionVarlist.setPersonalBean(customerPersonalBean);
                    selectedTab = "1";
                } else {
                    loadTabIndex = "0";
                }

                //----------Bank Details               
                if (isDBSave) {
                    if (appStatusBean.getTableFore().equals("1")) {
                        getAllBankDetails(appliactionId);
                        sessionVarlist.setSessionBankDetailList(bankDetailsBeanLst);
                        selectedTab = "2";
                    } else {
                        sessionVarlist.setSessionBankDetailList(new ArrayList<CardBankDetailsBean>());
                        loadTabIndex = loadTabIndex + "," + "1";
                    }
                } else {
                    if (appStatusBean.getTableFore().equals("1")) {
                        //getAllBankDetails(appliactionId);
                        //sessionVarlist.setSessionBankDetailList(bankDetailsBeanLst);
                        selectedTab = "2";
                    } else {
                        //sessionVarlist.setSessionBankDetailList(new ArrayList<CardBankDetailsBean>());
                        loadTabIndex = loadTabIndex + "," + "1";
                    }
                }
                //---------Documents--------------------------------------------
                if (isDBSave) {
                    if (appStatusBean.getTableFive().equals("1")) {
                        getAllDocumentDetails(appliactionId);
                        sessionVarlist.setSessionDocumentList(documentDetailsBeanLst);
                        selectedTab = "3";

                    } else {
                        sessionVarlist.setSessionDocumentList(new ArrayList<DocumentUploadBean>());
                        loadTabIndex = loadTabIndex + "," + "2";
                    }
                } else {
                    if (appStatusBean.getTableFive().equals("1")) {
                        selectedTab = "3";

                    } else {
                        loadTabIndex = loadTabIndex + "," + "2";
                    }
                }
                //---------Signature--------------------------------------------
                if (appStatusBean.getTableSix().equals("1")) {

                } else {
                    loadTabIndex = loadTabIndex + "," + "3";
                }
            }
        }
        request.setAttribute("loadTabIndex", loadTabIndex);
        request.setAttribute("selectedtab", selectedTab);
        if (actionSelectedTabIndex != null) { //this will use to keep selecting current tab. 
            request.setAttribute("selectedtab", actionSelectedTabIndex);
        }
    }

    private static void getAllApplicationStatus(String appliactionId) throws Exception {

        try {
            manager = new CaptureDataManager();
            appStatusBean = manager.getAllApplicationStatus(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private static void getAllDetailsCustomer(String appliactionId) throws Exception {
        try {
            manager = new CaptureDataManager();
            customerPersonalBean = manager.getAllDetailsCustomer(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private static void getAllBankDetails(String appliactionId) throws Exception {
        try {
            checkingManager = new ApplicationCheckingManager();
            bankDetailsBeanLst = checkingManager.getCardBankDetailsDetails(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private static void getAllDocumentDetails(String appliactionId) throws Exception {
        try {
            checkingManager = new ApplicationCheckingManager();
            documentDetailsBeanLst = checkingManager.getCardDocumentDetails(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    public static void loadDefaultCorporateApplicationStatus(String appliactionId, SessionVarList sessionVarlist, HttpServletRequest request, boolean isDBSave, Integer actionSelectedTabIndex) throws Exception {
        getAllApplicationStatus(appliactionId);
        String loadTabIndex = "";
        String selectedTab = "0";

        if (appStatusBean != null) {
            if (appStatusBean.getApplicationStatus().equals(StatusVarList.APP_INITIATE) || appStatusBean.getApplicationStatus().equals(StatusVarList.APP_PROCESS)) {

                if (appStatusBean.getTableOne().equals("1")) {
                    getAllDetailsCustomer(appliactionId);
                    request.setAttribute("personalBean", customerPersonalBean);
                    sessionVarlist.setPersonalBean(customerPersonalBean);
                    selectedTab = "1";
                } else {
                    loadTabIndex = "0";
                }
                //---------Documents--------------------------------------------
                if (isDBSave) {
                    if (appStatusBean.getTableFive().equals("1")) {
                        getAllDocumentDetails(appliactionId);
                        sessionVarlist.setSessionDocumentList(documentDetailsBeanLst);
                        selectedTab = "2";

                    } else {
                        sessionVarlist.setSessionDocumentList(new ArrayList<DocumentUploadBean>());
                        loadTabIndex = loadTabIndex + "," + "1";
                    }
                } else {
                    if (appStatusBean.getTableFive().equals("1")) {
                        selectedTab = "2";

                    } else {
                        loadTabIndex = loadTabIndex + "," + "1";
                    }
                }
                //---------Signature--------------------------------------------
                if (appStatusBean.getTableSix().equals("1")) {

                } else {
                    loadTabIndex = loadTabIndex + "," + "2";
                }
            }
        }
        request.setAttribute("loadTabIndex", loadTabIndex);
        request.setAttribute("selectedtab", selectedTab);
        if (actionSelectedTabIndex != null) { //this will use to keep selecting current tab. 
            request.setAttribute("selectedtab", actionSelectedTabIndex);
        }
    }

    public static void loadDefaultEstablishmentApplicationStatus(String appliactionId, SessionVarList sessionVarlist, HttpServletRequest request, Boolean isDBsave, IdBean idbean, Integer actionSelectedTabIndex) throws Exception {

        getAllApplicationStatus(appliactionId);
        String loadTabIndex = "";
        String selectedTab = "0";

        if (appStatusBean.getTableOne().equals("1")) {
            getAllDetailsEstablishment(appliactionId);
            request.setAttribute("establishmentDetailsBean", establishmentDetailsBean);
            sessionVarlist.setEstablishmentDetailsBean(establishmentDetailsBean);
            selectedTab = "1";

        } else {
            loadTabIndex = "0";
            establishmentDetailsBean = new EstablishmentDetailsBean();
            if (idbean != null) {
                establishmentDetailsBean.setIdentificationType(idbean.getIdType());
                establishmentDetailsBean.setIdentificationNumber(idbean.getIdNumber());
            }
            request.setAttribute("establishmentDetailsBean", establishmentDetailsBean);
        }

        //---------Assets and Liability Details----
        if (isDBsave) {
            if (appStatusBean.getTableTwo().equals("1")) {

                getAllAssetAndLiabilityDetails(establishmentDetailsBean.getBusinessRegNumber());
                sessionVarlist.setEstablishmentAssetList(establishmentAssetList);
                sessionVarlist.setEstablishmentLiabilityList(establishmentLiabilityList);
                selectedTab = "2";
            } else {
                sessionVarlist.setEstablishmentAssetList(new ArrayList<EstablishmentAssetsBean>());
                sessionVarlist.setEstablishmentLiabilityList(new ArrayList<EstablishmentLiabilityBean>());
                loadTabIndex = loadTabIndex + "," + "1";
            }
        } else {
            if (appStatusBean.getTableTwo().equals("1")) {
                selectedTab = "2";
            } else {
                loadTabIndex = loadTabIndex + "," + "1";
            }
        }

        //----------Bank Details
        if (isDBsave) {
            if (appStatusBean.getTableFore().equals("1")) {
                getAllBankDetails(appliactionId);
                sessionVarlist.setSessionBankDetailList(bankDetailsBeanLst);
                selectedTab = "3";
            } else {
                sessionVarlist.setSessionBankDetailList(new ArrayList<CardBankDetailsBean>());
                loadTabIndex = loadTabIndex + "," + "2";
            }

        } else {
            if (appStatusBean.getTableFore().equals("1")) {
                selectedTab = "3";
            } else {
                loadTabIndex = loadTabIndex + "," + "2";
            }
        }
        //---------Document Details----
        if (isDBsave) {
            if (appStatusBean.getTableFive().equals("1")) {
                getAllDocumentDetails(appliactionId);
                sessionVarlist.setSessionDocumentList(documentDetailsBeanLst);
                selectedTab = "4";

            } else {
                sessionVarlist.setSessionDocumentList(new ArrayList<DocumentUploadBean>());
                loadTabIndex = loadTabIndex + "," + "3";
            }

        } else {
            if (appStatusBean.getTableFive().equals("1")) {
                selectedTab = "4";
            } else {
                loadTabIndex = loadTabIndex + "," + "3";
            }
        }
        //----Signature------
        if (appStatusBean.getTableSix().equals("1")) {

        } else {
            loadTabIndex = loadTabIndex + "," + "4";
        }

        request.setAttribute("loadTabIndex", loadTabIndex);
        request.setAttribute("selectedtab", selectedTab);
        if (actionSelectedTabIndex != null) { //this will use to keep selecting current tab. 
            request.setAttribute("selectedtab", actionSelectedTabIndex);
        }
    }

    private static void getAllDetailsEstablishment(String appliactionId) throws Exception {
        try {
            manager = new CaptureDataManager();
            establishmentDetailsBean = manager.getAllDetailsEstablishment(appliactionId);

        } catch (Exception ex) {
            throw ex;
        }
    }

    private static void getAllAssetAndLiabilityDetails(String businessRegNo) throws Exception {
        try {
            checkingManager = new ApplicationCheckingManager();
            establishmentAssetList = checkingManager.getAllAssetDetails(businessRegNo);
            establishmentLiabilityList = checkingManager.getAllLiabilityDetails(businessRegNo);

        } catch (Exception ex) {
            throw ex;
        }
    }

    public static void loadDefaultCardagainstFDApplicationStatusInUpdate(String appliactionId, SessionVarList sessionVarlist, HttpServletRequest request, Boolean isDBSave, int updatedTab, boolean isSuccess) throws Exception {
        getAllApplicationStatus(appliactionId);

        if (appStatusBean != null) {
            if (appStatusBean.getApplicationStatus().equals(StatusVarList.APP_INITIATE) || appStatusBean.getApplicationStatus().equals(StatusVarList.APP_PROCESS)) {

//                getAllDetailsCustomer(appliactionId);
//                request.setAttribute("personalBean", customerPersonalBean);
//                sessionVarlist.setPersonalBean(customerPersonalBean);
                //----------Bank Details               
//                if (isDBSave) {
//                    getAllBankDetails(appliactionId);
//                    sessionVarlist.setSessionBankDetailList(bankDetailsBeanLst);
//                }
            }
            if (isDBSave) {
                getAllDetailsCustomer(appliactionId);
                request.setAttribute("personalBean", customerPersonalBean);
                sessionVarlist.setPersonalBean(customerPersonalBean);
            }
            //---------Documents--------------------------------------------
            if (isDBSave) {
                if (appStatusBean.getTableFive().equals("1")) {
                    getAllDocumentDetails(appliactionId);
                    sessionVarlist.setSessionDocumentList(documentDetailsBeanLst);

                }
            }
            //----------Bank Details               
            if (isDBSave) {
                getAllBankDetails(appliactionId);
                sessionVarlist.setSessionBankDetailList(bankDetailsBeanLst);
            }
            //---------Signature--------------------------------------------

            if (!isSuccess) {
                request.setAttribute("selectedtab", updatedTab);
            } else {
                request.setAttribute("selectedtab", updatedTab); //change in future
            }

        }
    }

    public static void loadDefaultCorporateApplicationStatusInUpdate(String appliactionId, SessionVarList sessionVarlist, HttpServletRequest request, boolean isDBSave, int updatedTab, boolean isSuccess) throws Exception {
        getAllApplicationStatus(appliactionId);

        if (appStatusBean != null) {
            if (appStatusBean.getApplicationStatus().equals(StatusVarList.APP_INITIATE) || appStatusBean.getApplicationStatus().equals(StatusVarList.APP_PROCESS) || appStatusBean.getApplicationStatus().equals(StatusVarList.APP_CHECKIN)) {

                getAllDetailsCustomer(appliactionId);
                request.setAttribute("personalBean", customerPersonalBean);
                sessionVarlist.setPersonalBean(customerPersonalBean);

                //---------Documents--------------------------------------------
                if (isDBSave) {
                    getAllDocumentDetails(appliactionId);
                    sessionVarlist.setSessionDocumentList(documentDetailsBeanLst);

                }
                //---------Signature-------------------------------------------

            }
            if (!isSuccess) {
                request.setAttribute("selectedtab", updatedTab);
            } else {
                request.setAttribute("selectedtab", updatedTab); //change in future
            }
        }
    }

    public static void loadDefaultEstablishmentApplicationStatusInUpdate(String appliactionId, SessionVarList sessionVarlist, HttpServletRequest request, Boolean isDBsave, IdBean idbean, int updatedTabIndex, boolean isSuccess) throws Exception {

        getAllApplicationStatus(appliactionId);

        getAllDetailsEstablishment(appliactionId);
        request.setAttribute("establishmentDetailsBean", establishmentDetailsBean);
        sessionVarlist.setEstablishmentDetailsBean(establishmentDetailsBean);

        //---------Assets and Liability Details----
        if (isDBsave) {

            getAllAssetAndLiabilityDetails(establishmentDetailsBean.getBusinessRegNumber());
            sessionVarlist.setEstablishmentAssetList(establishmentAssetList);
            sessionVarlist.setEstablishmentLiabilityList(establishmentLiabilityList);

        }

        //----------Bank Details
        if (isDBsave) {
            getAllBankDetails(appliactionId);
            sessionVarlist.setSessionBankDetailList(bankDetailsBeanLst);
        }
        //---------Document Details----
        if (isDBsave) {
            getAllDocumentDetails(appliactionId);
            sessionVarlist.setSessionDocumentList(documentDetailsBeanLst);
        }
        //----Signature------

        if (!isSuccess) {
            request.setAttribute("selectedtab", updatedTabIndex);
        } else {
            request.setAttribute("selectedtab", updatedTabIndex); //change in future
        }
    }

}
