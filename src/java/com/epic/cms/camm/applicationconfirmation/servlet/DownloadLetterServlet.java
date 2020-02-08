/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfirmation.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.camm.applicationconfirmation.businesslogic.ApplicationConfirmationManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CustomerPersonalBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.SupplementaryApplicationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.businesslogic.ApplicationCheckingManager;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.MessageVarList;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mahesh_m
 */
public class DownloadLetterServlet extends HttpServlet {

    private SystemUserManager systemUserManager;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private SessionVarList sessionVarlist;
    private RequestDispatcher rd;
    private ApplicationCheckingManager checkingmanager;
    private CustomerPersonalBean personalDetail;
    private SupplementaryApplicationBean suppPersonalDetail;
    private CardApplicationBean cardApplicationList;
    private ApplicationConfirmationManager confirmManager;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //    PrintWriter out = response.getWriter();
        OutputStream out = response.getOutputStream();
        String applicationid = request.getParameter("applicationId");
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
                confirmManager = new ApplicationConfirmationManager();
                String osType = getOS_Type();
                
                String letterHomeDirectory = confirmManager.getLetterDirectory(osType);
                
                File imageFile = new File(letterHomeDirectory + applicationid + "/"+"RECOMMENDATION_LETTER" +"/" + "Recommendation Letter.pdf");

                FileInputStream fis = new FileInputStream(imageFile);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];

                for (int readNum; (readNum = fis.read(buf)) != -1;) {
                    bos.write(buf, 0, readNum);
                    //no doubt here is 0
                /*Writes len bytes from the specified byte array starting at offset 
                    off to this byte array output stream.*/
                }

                byte[] bytes = bos.toByteArray();


                //-----------------------------------------------------
                response.setContentType("pdf");
                //OutputStream o = response.getOutputStream();
                
                out.write(bytes);
                out.flush();
                out.close();

            } catch (IOException ex) {
//                try {
//                    SystemRecomendedCredit systemRecomended = new SystemRecomendedCredit();
//                    request.setAttribute("selectedtab", "0");
//                    this.getCardApplicationDetails(applicationid);
//                    this.getDataFromCardApplicationPersonalTable(applicationid);
//                    this.getDataFromCardSuppApplicationPersonalTable(applicationid);
//
//                    RecommendSchemBean sysRecomended = new RecommendSchemBean();
//                    if (cardApplicationList.getCreditScore() != null) {
//                        int creditLimit = systemRecomended.getSystemRecomendedCreditLimit(Integer.parseInt(cardApplicationList.getCreditScore()), personalDetail.getCardType());
//                        request.setAttribute("creditLimit", creditLimit);
//                    }

//                    request.setAttribute("errorMsg", MessageVarList.RECOMMENDATION_LETTER_DOWNLOAD_FALE);
//                    request.setAttribute("cardApplicationList", cardApplicationList);

//                     out.write(MessageVarList.RECOMMENDATION_LETTER_DOWNLOAD_FALE.getBytes());
                     String message = "<script language='javascript' type='text/javascript'>window.top.window.displayMsg('No Recommendation Letter Found');</script>";
                     
                   out.write(message.getBytes());
//                     response.setContentType("text/html;charset=UTF-8");
//
//                    confirmManager = new ApplicationConfirmationManager();
//
//                    String cardCategory = confirmManager.getApplicationCategory(applicationid);
//
//                    if (cardCategory.equals(StatusVarList.CARD_CATEGORY_MAIN)) {
//                        request.setAttribute("personalDetail", personalDetail);
//                        rd = request.getRequestDispatcher("/camm/applicationconfirmation/applicationapproveview.jsp");
//                    } else if (cardCategory.equals(StatusVarList.CARD_CATEGORY_SUPPLEMENTARY)) {
//                        request.setAttribute("personalDetail", suppPersonalDetail);
//                        rd = request.getRequestDispatcher("/camm/applicationconfirmation/supplemantaryapplicationapproveview.jsp");
//                    } else if (cardCategory.equals(StatusVarList.CARD_CATEGORY_COPORATE)) {
//                    }
//
//                    rd.include(request, response);


//                } catch (Exception e1) {
////                    request.setAttribute("selectedtab", "0");
////                    request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
////                    rd = getServletContext().getRequestDispatcher("/camm/applicationconfirmation/applicationapproveview.jsp");
////                    rd.forward(request, response);
//                     out.print( MessageVarList.UNKNOW_ERROR);
//                }
            }

        } catch (Exception e) {
//            request.setAttribute("selectedtab", "0");
//            request.setAttribute("errorMsg", MessageVarList.UNKNOW_ERROR);
//            rd = getServletContext().getRequestDispatcher("/camm/applicationconfirmation/applicationapproveview.jsp");
//            rd.forward(request, response);
            e.printStackTrace();
            
            out.write( MessageVarList.UNKNOW_ERROR.getBytes());
        } finally {
            // out.close();
        }
    }

    private void getDataFromCardApplicationPersonalTable(String applicationId) throws Exception {
        try {
            checkingmanager = new ApplicationCheckingManager();
            personalDetail = checkingmanager.getPersonalDetails(applicationId);
        } catch (Exception e) {
            throw e;
        }
    }

    private void getCardApplicationDetails(String applicationId) throws Exception {
        try {
            checkingmanager = new ApplicationCheckingManager();
            cardApplicationList = checkingmanager.getCardInfomationDetails(applicationId);
        } catch (Exception e) {
            throw e;
        }
    }

    private void getDataFromCardSuppApplicationPersonalTable(String applicationId) throws Exception {
        try {
            checkingmanager = new ApplicationCheckingManager();
            suppPersonalDetail = checkingmanager.getSupplementaryPersonalDetails(applicationId);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public static String getOS_Type() throws Exception{

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
