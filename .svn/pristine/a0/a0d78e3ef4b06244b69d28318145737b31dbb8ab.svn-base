/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfirmation.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationconfirmation.businesslogic.ApplicationConfirmationManager;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationHistoryBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.CheckedApplicantDetailsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.businesslogic.DocumentVerifyManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author mahesh_m
 */
public class UploadRcomondationLetterServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private File file;
    private String applicationid = null;
    private String cOfficerRecLimit = null;
    private String cOfficerRecProduct = null;
    private String sysRecCreditLimit = null;
    private SystemAuditBean systemAuditBean = null;
    private ApplicationConfirmationManager confirmManager;
    private ApplicationHistoryBean historybean = null;
    private CardApplicationBean cardapplicationbean = null;
    private String errorMessage = null;
    private String url = "/camm/documentverification/documentviewhome.jsp";
    private boolean isOfficerReviewAndConfirm = false;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        applicationid = request.getParameter("applicationId");
        cOfficerRecLimit = request.getParameter("crdtOfficerRecCreditLimit").trim();
        cOfficerRecProduct = request.getParameter("crdtOfficerRecProduct").trim();
        String officerReviewAndConfirm = request.getParameter("officerReviewAndConfirm");
        if (officerReviewAndConfirm != null && officerReviewAndConfirm.equals("YES")) {
            isOfficerReviewAndConfirm = true;
        } else {
            isOfficerReviewAndConfirm = false;
        }
        try {

            sessionObj = request.getSession(false);
            try {
                systemUserManager = new SystemUserManager();
                sessionVarlist = (SessionVarList) sessionObj.getAttribute(SessionVarName.SESSION_OBJ);
                sessionUser = sessionVarlist.getCMSSessionUser();

                //check system user is in same session or not
                try {

                    if (!systemUserManager.validateUserSession(sessionUser.getUserName(), sessionObj.getId())) {
                        //useer not in same session.
                        throw new NewLoginSessionException();

                    }

                } catch (NewLoginSessionException nlex) {
                    //throw lst login close exception
                    throw new NewLoginSessionException();

                }

            } catch (NullPointerException ex) {
                //throw session null exception
                throw new SesssionExpException();
            }

            try {

                ApplicationConfirmationManager confirmationManager = new ApplicationConfirmationManager();

                ServletFileUpload uploadData = new ServletFileUpload(new DiskFileItemFactory());
                List formData = uploadData.parseRequest(request);
                HashMap<String, FileItem> formMap = this.convertToMaps(formData);

                //File Upload .....................................
                confirmManager = new ApplicationConfirmationManager();
                String osType = getOS_Type();

                String letterHomeDirectory = confirmManager.getLetterDirectory(osType);

//                file = new File(letterHomeDirectory + applicationid + File.separator+"RECOMMENDATION_LETTER"+File.separator);
//                file = new File(letterHomeDirectory + applicationid + "/"+"RECOMMENDATION_LETTER");
                file = new File(letterHomeDirectory + applicationid + "/" + "RECOMMENDATION_LETTER");
                if (!file.exists()) {
                    file.mkdirs();
                }

                FileItem uploadItem = formMap.get("recommendationLetter");
                confirmManager = new ApplicationConfirmationManager();
                String cardCategory = confirmManager.getApplicationCategory(applicationid);

                if (!validateUserInputs(cOfficerRecLimit, cOfficerRecProduct, uploadItem)) {
                    request.setAttribute("applicationid", applicationid);
                    request.setAttribute("errorMsg", errorMessage);
                    request.setAttribute("climit", cOfficerRecLimit);
                    request.setAttribute("cproduct", cOfficerRecProduct);

                    if (cardCategory.equals(StatusVarList.CARD_CATEGORY_MAIN)) {
                        if (isOfficerReviewAndConfirm) {
                            rd = getServletContext().getRequestDispatcher("/LoadCreditofficerDetailsAndConfirmServlet");
                        } else {
                            rd = getServletContext().getRequestDispatcher("/LoadCreditofficerDetailsServlet");
                        }
                    } else if (cardCategory.equals(StatusVarList.CARD_CATEGORY_SUPPLEMENTARY)) {
                        if (isOfficerReviewAndConfirm) {
                            rd = getServletContext().getRequestDispatcher("/LoadSupplementaryCreditofficerDetailsAndConfirmServlet");
                        } else {
                            rd = getServletContext().getRequestDispatcher("/LoadSupplementaryCreditofficerDetailsServlet");
                        }
                    } else if (cardCategory.equals(StatusVarList.CARD_AGAINST_FD_CODE)) {
                        if (isOfficerReviewAndConfirm) {
                            rd = getServletContext().getRequestDispatcher("/LoadCardAgainstFDCreditOfficerDetailsAndConfirmServlet");
                        } else {
                            rd = getServletContext().getRequestDispatcher("/LoadCardAgainstFDCreditofficerDetailsServlet");
                        }
                    } else if (cardCategory.equals(StatusVarList.CARD_CATEGORY_COPORATE)) {
                        if (isOfficerReviewAndConfirm) {
                            rd = getServletContext().getRequestDispatcher("/LoadCorporateCreditOfficerDetailsAndConfirmServlet");
                        }else{
                            rd = getServletContext().getRequestDispatcher("/LoadCorporateCreditofficerDetailsServlet");
                        }
                    } else if (cardCategory.equals(StatusVarList.CARD_CATEGORY_ESTABLISHMENT)) {
                        if (isOfficerReviewAndConfirm) {
                            rd = getServletContext().getRequestDispatcher("/LoadEstablishmentCreditOfficerDetailsAndConfirmServlet");
                        } else {
                            rd = getServletContext().getRequestDispatcher("/LoadEstablishmentCreditofficerDetailsServlet");
                        }
                    }
                    request.setAttribute("isConfirmMode", 0);
                    rd.include(request, response);

                } else {
                    uploadItem.write(new File(letterHomeDirectory + applicationid + "/RECOMMENDATION_LETTER/" + "Recommendation Letter.pdf"));

                    this.setAudittraceValue(request);
                    this.setApplicationHistoryBean();

                    this.setCardApplicationBean();

                    confirmationManager.updateCardApplicationDocument(applicationid, systemAuditBean, historybean, cardapplicationbean, StatusVarList.APP_CREDIT_OFFICER_REVIEW_COMPLETE);

                    request.setAttribute("applicationid", applicationid);
                    request.setAttribute("successMsg", MessageVarList.APP_CREDIT_OFFICER_REVIEW_SUCCESS);
                    request.setAttribute("isConfirmMode", 1);

                    if (cardCategory.equals(StatusVarList.CARD_CATEGORY_MAIN)) {
                        if (isOfficerReviewAndConfirm) {
                           // rd = getServletContext().getRequestDispatcher("/LoadCreditOfficerReviewAndConfirmServlet");
                            response.sendRedirect(request.getContextPath() + "/LoadCreditofficerDetailsAndConfirmServlet?applicationid=" + applicationid);
                            return;
                        } else {
                            rd = getServletContext().getRequestDispatcher("/LoadCreditOfficerServlet");
                        }
                    } else if (cardCategory.equals(StatusVarList.CARD_CATEGORY_SUPPLEMENTARY)) {
                        if (isOfficerReviewAndConfirm) {
                            //rd = getServletContext().getRequestDispatcher("/LoadSupplementaryCreditofficerDetailsAndConfirmServlet");
                            response.sendRedirect(request.getContextPath() + "/LoadSupplementaryCreditofficerDetailsAndConfirmServlet?applicationid=" + applicationid);
                            return;
                        } else {
                            rd = getServletContext().getRequestDispatcher("/LoadCreditOfficerServlet");
                        }
                    } else if (cardCategory.equals(StatusVarList.CARD_CATEGORY_COPORATE)) {
                        if(isOfficerReviewAndConfirm){
                            response.sendRedirect(request.getContextPath() + "/LoadCorporateCreditOfficerDetailsAndConfirmServlet?applicationid=" + applicationid);
                            return;
                        }else{
                            rd = getServletContext().getRequestDispatcher("/LoadCreditOfficerServlet");
                        }
                    } else if (cardCategory.equals(StatusVarList.CARD_CATEGORY_ESTABLISHMENT)) {
                        if (isOfficerReviewAndConfirm) {
                            response.sendRedirect(request.getContextPath() + "/LoadEstablishmentCreditOfficerDetailsAndConfirmServlet?applicationid=" + applicationid);
                            return;
                        } else {
                            rd = getServletContext().getRequestDispatcher("/LoadCreditOfficerServlet");
                        }
                    } else if (cardCategory.equals(StatusVarList.CARD_AGAINST_FD_CODE)) {
                        if (isOfficerReviewAndConfirm) {
                            response.sendRedirect(request.getContextPath() + "/LoadCardAgainstFDCreditOfficerDetailsAndConfirmServlet?applicationid=" + applicationid);
                            return;
                        } else {
                            rd = getServletContext().getRequestDispatcher("/LoadCreditOfficerServlet");
                        }
                    }
                    rd.include(request, response);
                }

            } catch (Exception e) {
                request.setAttribute("applicationid", applicationid);
                request.setAttribute("errorMsg", "Error Occured While Uploading!");

                if (isOfficerReviewAndConfirm) {
                    rd = getServletContext().getRequestDispatcher("/LoadCreditofficerDetailsAndConfirmServlet");
                } else {
                    rd = getServletContext().getRequestDispatcher("/LoadCreditofficerDetailsServlet");
                }
                rd.include(request, response);

            }

        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);
            rd.forward(request, response);
        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);
        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("errorMsg", "Error Occured While Uploading!");
            request.setAttribute("applicationid", applicationid);

            if (isOfficerReviewAndConfirm) {
                rd = getServletContext().getRequestDispatcher("/LoadCreditofficerDetailsAndConfirmServlet");
            } else {
                rd = getServletContext().getRequestDispatcher("/LoadCreditofficerDetailsServlet");
            }
            rd.include(request, response);

        } finally {
            out.close();
        }
    }

    private HashMap<String, FileItem> convertToMaps(List<FileItem> aFileItems) {
        HashMap<String, FileItem> fFileParams = new HashMap<String, FileItem>();
        for (FileItem item : aFileItems) {
            fFileParams.put(item.getFieldName(), item);
        }
        return fFileParams;
    }

    private void setAudittraceValue(HttpServletRequest request) throws Exception {

        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            //String uniqueId = request.getParameter(applicationid);

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(applicationid);
            //set description 
            systemAuditBean.setDescription("Uploaded Recomendation Letter regarding to " + applicationid + " " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.CAMM_APPLICATION);
            systemAuditBean.setSectionCode(SectionVarList.APPLICATIONAPPROVE);
            systemAuditBean.setPageCode(PageVarList.CREDITOFFICER);
            systemAuditBean.setTaskCode(TaskVarList.UPLOAD);
            systemAuditBean.setIp(request.getRemoteAddr());
            //add remarks here
            systemAuditBean.setRemarks("");
            //set field name which is being changed(only if)
            systemAuditBean.setFieldName("");
            //set old value of change if required
            systemAuditBean.setOldValue("");
            //set new value of change if required
            systemAuditBean.setNewValue("");

            systemAuditBean.setLastUpdateduser(sessionVarlist.getCMSSessionUser().getUserName());

        } catch (Exception ex) {
            throw ex;
        }
    }

    public static String getOS_Type() throws Exception {

        String osType = "";
        String osName = "";
        osName = System.getProperty("os.name", "").toLowerCase();

        // For WINDOWS
        if (osName.contains("windows")) {
            osType = "WINDOWS";
        } else {
            // For LINUX
            if (osName.contains("linux")) {
                osType = "LINUX";
            } else {
                throw new Exception("Cannot identify the Operating System.");
            }
        }

        return osType;
    }

    public void setApplicationHistoryBean() {
        historybean = new ApplicationHistoryBean();

        historybean.setApplicationId(applicationid);
        historybean.setApplicationLevel("CORC");
        historybean.setLastUpdatedUser(sessionVarlist.getCMSSessionUser().getUserName());
        historybean.setRemarks("Credit Officer Recomendation Completed");
        historybean.setStatus(StatusVarList.HISTORY_COMPLETE);
    }

    private void setCardApplicationBean() {
        cardapplicationbean = new CardApplicationBean();

        cardapplicationbean.setcOfficerRecCrditLimt(cOfficerRecLimit);
        cardapplicationbean.setcOfficerRecCardProduct(cOfficerRecProduct);
    }

    //validate
    public boolean validateUserInputs(String climt, String cproduct, FileItem fileitem) throws Exception {
        boolean isValid = true;
        try {
            if (cproduct.contentEquals("") || cproduct.length() <= 0) {
                isValid = false;
                errorMessage = MessageVarList.CREDIT_OFFICER_REC_PRODUCT_EMPTY_MAIN;
            } else if (climt.contentEquals("") || climt.length() <= 0) {
                isValid = false;
                errorMessage = MessageVarList.CREDIT_OFFICER_REC_CREDIT_LIMIT_EMPTY_MAIN;
            } else if (!UserInputValidator.isNumeric(climt)) {
                isValid = false;
                errorMessage = MessageVarList.CREDIT_OFFICER_REC_CREDIT_LIMIT_INVALID_MAIN;
            } else if (fileitem.getSize() == 0) {
                isValid = false;
                errorMessage = MessageVarList.CREDIT_OFFICER_REC_FILE_EMPTY;
            }
        } catch (Exception e) {
            isValid = false;
        }
        return isValid;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
