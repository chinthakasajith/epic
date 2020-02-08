/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.capturedata.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DebitPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.CaptureDataManager;
import com.epic.cms.cpmm.cardembossing.bean.CommonFilePathBean;
import com.epic.cms.cpmm.cardembossing.businesslogic.CardEmbossingMgtManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.OracleMessage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
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
 * @author nalin
 */
public class UploadSecondryNicServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private CaptureDataManager manager;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private SessionVarList sessionVarlist;
    private DebitPersonalBean personalBean = null;
    private DebitPersonalBean debitPersonalBean = null;
    private CommonFilePathBean commonFilePathBean = null;
    private String url = "/camm/capturedata/debitcardapplicationform.jsp";

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
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


            sessionVarlist.setCMSSessionUser(sessionUser);
            sessionObj.setAttribute(SessionVarName.SESSION_OBJ, sessionVarlist);


            int fsize = 1024;
            // String documentRoot = "DOCUMENT";
            String rootPath = "";
            String applicationId = sessionVarlist.getApplicationId();

            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            DebitPersonalBean invalidMsgBean = new DebitPersonalBean();

            if (isMultipart) {
                DiskFileItemFactory factory = new DiskFileItemFactory();
                factory.setSizeThreshold(1 * 1024 * 1024);
                ServletFileUpload upload = new ServletFileUpload(factory);
                upload.setSizeMax(fsize * 1024);

                List items = upload.parseRequest(request);

                HashMap<String, FileItem> formMap = this.convertToMaps(items);

                this.setUserInputToBean(formMap);

                Iterator itr = items.iterator();
                while (itr.hasNext()) {
                    FileItem item = (FileItem) itr.next();

                    if (item.isFormField()) {
                        System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString());
                    } else {


                        String fileName = this.getGenerateFileName(item.getName(), applicationId);
                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + item.getFieldName());
                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + item.getContentType());




                        if (!(item.getSize() > fsize * 1024)) {


                            String osType = this.getOS_Type();
                            CardEmbossingMgtManager cardEmbossingManager = new CardEmbossingMgtManager();
                            commonFilePathBean = cardEmbossingManager.getFilePaths(osType);

                            if (osType.equals("WINDOWS")) {
                                rootPath = commonFilePathBean.getScandocument() + File.separator + applicationId + File.separator + "SECONDARY_NIC";

                                this.createDirectory(rootPath);

                            } else if (osType.equals("LINUX")) {
                                rootPath = commonFilePathBean.getScandocument() + File.separator + applicationId + File.separator + "SECONDARY_NIC";
                                this.createDirectory(rootPath);
                            }

                            File file = new File(rootPath, fileName);
                            item.write(file);

                            sessionVarlist.setSecondryNicFileName(fileName);
                            request.setAttribute("successMsg", MessageVarList.FILE_UPLOADED);

                        } else {

                            invalidMsgBean.setNicFileName(MessageVarList.IMAGE_SIZE_TOO_LARGE);
                            request.setAttribute("invalidMsgBean", invalidMsgBean);
                            break;
                        }

                    }
                }


            }
            this.checkCardStatus(applicationId);
            request.setAttribute("personalBean", debitPersonalBean);
            request.setAttribute("sPersonalBean", personalBean);
            rd = getServletContext().getRequestDispatcher(url);
            request.setAttribute("selectedtab", "1");

        } catch (SQLException ex) {

            request.setAttribute("errorMsg", OracleMessage.getMessege(ex.getMessage()));
            rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);

        } //catch session exception
        catch (SesssionExpException sex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);
            rd.forward(request, response);


        } //catch session exception
        catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);
            rd.forward(request, response);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);
            rd.forward(request, response);

        } catch (Exception ex) {

            request.setAttribute("errorMsg", MessageVarList.ERROR_UPLOAD_SIGNETURE);
            request.setAttribute("personalBean", personalBean);
            request.setAttribute("selectedtab", "0");
            request.setAttribute("disableTabIndex", "1");
            rd = getServletContext().getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
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

    public String getOS_Type() throws Exception {

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

    private String getGenerateFileName(String name, String id) throws Exception {
        String fileName = "";
        String extention = "JPG";

        try {
//            temp = name.split("\\.");
//            if (temp.length > 0) {
//                extention = temp[temp.length - 1];
//          

            fileName = id + "_SECONDARY_NIC" + "." + extention;

        } catch (Exception ex) {
            throw ex;
        }
        return fileName;
    }

    private void createDirectory(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public Boolean setUserInputToBean(HashMap<String, FileItem> formMap) throws Exception {
        Boolean success = true;
        try {

            personalBean = new DebitPersonalBean();

            personalBean.setApplicationId(sessionVarlist.getApplicationId());

            personalBean.setIdentificationType(formMap.get("identificationTypeS").getString());
            personalBean.setIdentificationNumber(formMap.get("identificationNumberS").getString());
            personalBean.setTitle(formMap.get("titleS").getString());
            personalBean.setBirthday(formMap.get("birthdayS").getString());
            personalBean.setFirstName(formMap.get("firstNameS").getString());
            personalBean.setMiddleName(formMap.get("middleNameS").getString());
            personalBean.setLastName(formMap.get("lastNameS").getString());
            personalBean.setAddress1(formMap.get("address1S").getString());
            personalBean.setAddress2(formMap.get("address2S").getString());
            personalBean.setAddress3(formMap.get("address3S").getString());
            personalBean.setNameOncard(formMap.get("nameOncardS").getString());
            personalBean.setCity(formMap.get("cityS").getString());
            personalBean.setMothersMaidenName(formMap.get("mothersMaidenNameS").getString());
            personalBean.setMobileNumber(formMap.get("mobileNumberS").getString());
            personalBean.setHomeTelNumber(formMap.get("homeTelNumberS").getString());
            personalBean.setOfficeTelNumber(formMap.get("officeTelNumberS").getString());

            personalBean.setAccountNo(formMap.get("accountNo").getString());
            personalBean.setAccNo1Type(formMap.get("accNo1Type").getString());
            personalBean.setAccNo1Currency(formMap.get("accNo1Currency").getString());

            personalBean.setAccountNo3(formMap.get("accountNo3").getString());
            personalBean.setAccNo3Type(formMap.get("accNo3Type").getString());
            personalBean.setAccNo3Currency(formMap.get("accNo3Currency").getString());

            personalBean.setJoin(sessionVarlist.getCardCategoryCode());

            personalBean.setLastUpdateUser(sessionUser.getUserName());


        } catch (Exception e) {
            success = false;
            throw e;

        }

        return success;
    }

    private void checkCardStatus(String applicationId) throws Exception {

        try {

            manager = new CaptureDataManager();
            debitPersonalBean = new DebitPersonalBean();
            debitPersonalBean = manager.checkCardApplicationStatus(applicationId);


        } catch (Exception ex) {
            throw ex;
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
