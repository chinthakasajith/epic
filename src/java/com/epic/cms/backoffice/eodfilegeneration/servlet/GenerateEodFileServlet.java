/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.eodfilegeneration.servlet;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserManager;
import com.epic.cms.backoffice.eodfilegeneration.bean.SettlementFileBean;
import com.epic.cms.backoffice.eodfilegeneration.bean.SettlementFileParamBean;
import com.epic.cms.backoffice.eodfilegeneration.bean.TxnToEodBean;
import com.epic.cms.backoffice.eodfilegeneration.businesslogic.EodFileGenerationMgtManager;
import com.epic.cms.cpmm.cardembossing.bean.CommonFilePathBean;
import com.epic.cms.cpmm.cardembossing.businesslogic.CardEmbossingMgtManager;
import com.epic.cms.system.util.exception.AccessDeniedException;
import com.epic.cms.system.util.exception.NewLoginSessionException;
import com.epic.cms.system.util.exception.SesssionExpException;
import com.epic.cms.system.util.session.SessionUser;
import com.epic.cms.system.util.session.SessionVarList;
import com.epic.cms.system.util.session.SessionVarName;
import com.epic.cms.system.util.variable.ApplicationVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.PageVarList;
import com.epic.cms.system.util.variable.SectionVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.system.util.variable.TaskVarList;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author chanuka
 */
public class GenerateEodFileServlet extends HttpServlet {

    private RequestDispatcher rd;
    private SystemUserManager systemUserManager;
    private SessionVarList sessionVarlist;
    HttpSession sessionObj;
    private SessionUser sessionUser;
    private List<String> userTaskList;
    private List<TxnToEodBean> txnToEodBeanlist = null;
    private SystemAuditBean systemAuditBean = null;
    private List<String> terminalList = null;
    private CommonFilePathBean commonFilePathBean = null;
    private CardEmbossingMgtManager cardEmbossingManager;
    private SettlementFileParamBean settlementFileParamBean = null;
    private List<SettlementFileBean> settlementFileBeanList = null;
    private String fileName = null;
    private String settleFileId = null;
    private float fileTotalAmount;
    private long totalTxnCount = 0;
    private String url = "/backoffice/eodfilegenerate/eodfilegeneratehome.jsp";

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






            try {
                //set page code and task codes
                String pageCode = PageVarList.EOC_FILE_GENE;
                String taskCode = TaskVarList.EODFILEGEN;


                //check whethre userrole have an access for this page and task
                if (this.isValidAccess(sessionUser.getUserRole(), pageCode, taskCode)) {




                    String startTime = request.getParameter("stime");
                    String endTime = request.getParameter("etime");

                    request.setAttribute("etime", endTime);
                    request.setAttribute("stime", startTime);


                    EodFileGenerationMgtManager obj = new EodFileGenerationMgtManager();
                    settlementFileParamBean = obj.getSettlementFileParam();

                    settlementFileBeanList = obj.loadAllGeneratedEodFiles();
                    request.setAttribute("settlementFileBeanList", settlementFileBeanList);

                    String txnToEod = "";
                    String fileId = settlementFileParamBean.getFileId();
                    int batchseqNo = 0;



                    // generate the file id..............
                    //..............................
                    //,.,.,.,.,.,.,.,.,.,.,.,.,.


                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                    String dateNow = formatter.format(Calendar.getInstance().getTime());

                    txnToEod = settlementFileParamBean.getFileHeader() + "|" + fileId + "|" + dateNow;


                    terminalList = obj.getAllTeminalDetails(startTime, endTime);



                    for (int i = 0; i < terminalList.size(); i++) {

                        float batchTotalAmount = 0;

                        txnToEod += "\n" + settlementFileParamBean.getBatchHeader() + "|" + i;


                        txnToEodBeanlist = obj.getAllTxnToEod(terminalList.get(i), startTime, endTime);

                        for (int j = 0; j < txnToEodBeanlist.size(); j++) {

                            if (j == 0) {

                                txnToEod += "|" + txnToEodBeanlist.get(j).getTxnCurrency();

                                txnToEod += "|" + txnToEodBeanlist.get(j).getmId() + "|" + txnToEodBeanlist.get(j).gettId();

                                txnToEod += "|" + txnToEodBeanlist.get(j).getCountryCode();


                            }

                            txnToEod += "\n" + settlementFileParamBean.getTxnHeader() + "|" + txnToEodBeanlist.get(j).getTxnId() + "|" + txnToEodBeanlist.get(j).getMti();

                            txnToEod += "|" + txnToEodBeanlist.get(j).getResponseMti() + "|" + txnToEodBeanlist.get(j).getOffMti();

                            txnToEod += "|" + txnToEodBeanlist.get(j).getOffResponseMti() + "|" + txnToEodBeanlist.get(j).getProcessingCode();

                            txnToEod += "|" + txnToEodBeanlist.get(j).getOffProcessingCode() + "|" + txnToEodBeanlist.get(j).getRequestFrom();

                            txnToEod += "|" + txnToEodBeanlist.get(j).getListenerType() + "|" + txnToEodBeanlist.get(j).getChannelType();

                            txnToEod += "|" + txnToEodBeanlist.get(j).getOnOffStatus() + "|" + txnToEodBeanlist.get(j).getTxnTypeCode();

                            txnToEod += "|" + txnToEodBeanlist.get(j).getCardNo() + "|" + txnToEodBeanlist.get(j).getExpiryDate();

                            txnToEod += "|" + "card seq no" + "|" + txnToEodBeanlist.get(j).getTxnDate();

                            txnToEod += "|" + txnToEodBeanlist.get(j).getTxnTime() + "|" + txnToEodBeanlist.get(j).getBillAmount();

                            txnToEod += "|" + txnToEodBeanlist.get(j).getTxnCurrency() + "|" + txnToEodBeanlist.get(j).getSettlementAmount();

                            txnToEod += "|" + txnToEodBeanlist.get(j).getSettlementCurrency() + "|" + txnToEodBeanlist.get(j).getAuthCode();

                            txnToEod += "|" + txnToEodBeanlist.get(j).getTraceNo() + "|" + txnToEodBeanlist.get(j).getInvoiceNo();

                            txnToEod += "|" + txnToEodBeanlist.get(j).getRrn() + "|" + txnToEodBeanlist.get(j).getBatchNo();

                            txnToEod += "|" + txnToEodBeanlist.get(j).getResponseCode() + "|" + txnToEodBeanlist.get(j).getStatus();

                            txnToEod += "|" + txnToEodBeanlist.get(j).getPostEntryMode() + "|" + txnToEodBeanlist.get(j).getPostConditionCode();


                            txnToEod += "|" + txnToEodBeanlist.get(j).getMcc() + "|" + txnToEodBeanlist.get(j).getSettlementDate();

                            txnToEod += "|" + txnToEodBeanlist.get(j).getBin() + "|" + txnToEodBeanlist.get(j).getServiceCode();

                            txnToEod += "|" + txnToEodBeanlist.get(j).getAiic() + "|" + txnToEodBeanlist.get(j).getFiic();

                            txnToEod += "|" + txnToEodBeanlist.get(j).getCaic() + "|" + txnToEodBeanlist.get(j).getTag5f2a();

                            txnToEod += "|" + txnToEodBeanlist.get(j).getTag9a() + "|" + txnToEodBeanlist.get(j).getTag9c();

                            txnToEod += "|" + txnToEodBeanlist.get(j).getTag9c() + "|" + txnToEodBeanlist.get(j).getTag9f02();

                            txnToEod += "|" + txnToEodBeanlist.get(j).getTag9f03() + "|" + txnToEodBeanlist.get(j).getTag9f1A();

                            txnToEod += "|" + txnToEodBeanlist.get(j).getTag9f1E() + "|" + txnToEodBeanlist.get(j).getTag9f27();

                            txnToEod += "|" + txnToEodBeanlist.get(j).getTag9f33() + "|" + txnToEodBeanlist.get(j).getTag9f34();

                            txnToEod += "|" + txnToEodBeanlist.get(j).getTag9f35() + "|" + txnToEodBeanlist.get(j).getTag9f41();


                            batchTotalAmount += Float.parseFloat(txnToEodBeanlist.get(j).getBillAmount());

                            if (j == (txnToEodBeanlist.size() - 1)) {

                                txnToEod += "\n" + settlementFileParamBean.getBatchTailer() + "|" + txnToEodBeanlist.size() + "|" + batchTotalAmount;

                            }
                        }

                        totalTxnCount += txnToEodBeanlist.size();
                        fileTotalAmount += batchTotalAmount;

                    }

                    txnToEod += "\n" + settlementFileParamBean.getFileTailer() + "|" + terminalList.size() + "|" + totalTxnCount + "|" + fileTotalAmount;


                    if (terminalList != null && !terminalList.isEmpty()) {

                        String osType = this.getOS_Type();

                        if (osType.equals("WINDOWS")) {

                            String basePath = this.getFilePaths(osType, "EOD");
                            String rootPath = basePath + "EOD\\";

                            fileName = settlementFileParamBean.getFileNamePrefix() + dateNow + settlementFileParamBean.getFileExtention();
                            settleFileId = settlementFileParamBean.getFileNamePrefix() + dateNow;

                            this.createDirectory(rootPath);

                            File file = new File(rootPath, fileName);

                            file.createNewFile();


                            PrintWriter pw = new PrintWriter(new FileOutputStream(file, true));

                            pw.print(txnToEod);
                            pw.flush();
                            pw.close();

                        } else if (osType.equals("LINUX")) {


                            String basePath = this.getFilePaths(osType, "EOD");

                            String rootPath = basePath + "EOD/" + startTime;

                            fileName = settlementFileParamBean.getFileNamePrefix() + dateNow + settlementFileParamBean.getFileExtention();

                            this.createDirectory(rootPath);

                            File file = new File(rootPath, fileName);

                            file.createNewFile();


                            PrintWriter pw = new PrintWriter(new FileOutputStream(file, true));

                            pw.print(txnToEod);
                            pw.flush();
                            pw.close();
                        }

                        SettlementFileBean fileBean = new SettlementFileBean();

                        fileBean.setFileName(fileName);
                        fileBean.setFileId(settleFileId);
                        fileBean.setStatus(StatusVarList.INITIAL_STATUS);
                        fileBean.setLastUpdatedUser(sessionUser.getUserName());
                        fileBean.setIsTakenToEod(StatusVarList.NOSTATUS);
                        this.setAudittraceValue(request, fileName);

                        if (obj.insertSettlementFile(fileBean, systemAuditBean)) {

                            settlementFileBeanList = obj.loadAllGeneratedEodFiles();

                            request.setAttribute("settlementFileBeanList", settlementFileBeanList);
                        }

                        request.setAttribute("successMsg", MessageVarList.SUCCESS_EOD_FILE);

                    } else {

                        request.setAttribute("errorMsg", MessageVarList.NO_TXN_EOD_FILE);

                    }


                    rd = getServletContext().getRequestDispatcher(url);


                } else {

                    //if invalid throw accessdenied exception
                    throw new AccessDeniedException();

                }


                //set tasks to the session
                sessionVarlist.setUserPageTaskList(userTaskList);


            } catch (AccessDeniedException adex) {
                throw adex;

            }









        } //catch session exception
        catch (SesssionExpException sex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.SESSION_EXPIRED);

        } catch (NewLoginSessionException nlex) {

            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.LAST_SESSION_CLOSE);

        } //catch accessdenied exception
        catch (AccessDeniedException adex) {
            //redirect to login page
            rd = getServletContext().getRequestDispatcher("/administrator/controlpanel/login/login.jsp?message=" + MessageVarList.ACCESS_DENIED_TASK);


        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("errorMsg", MessageVarList.ERROR_GENE_EOD_FILE);
            rd = request.getRequestDispatcher(url);

        } finally {
            rd.forward(request, response);
            out.close();
        }
    }

    ///////////////////////////
    private boolean isValidAccess(String userrole, String pagecode, String task) throws Exception {
        boolean isValidTaskAccess = false;

        try {
            systemUserManager = new SystemUserManager();

            //get all tasks for userrole for this page
            userTaskList = systemUserManager.getTasksByPageCodeAndUserRole(userrole, pagecode);

            for (String usertask : userTaskList) {

                if (task.equals(usertask)) {
                    isValidTaskAccess = true;
                }

            }

            return isValidTaskAccess;
        } catch (Exception ex) {
            throw ex;
        }

    }

    private String getFilePaths(String osType, String fileType) throws Exception {

        String filePath = "";
        try {
            cardEmbossingManager = new CardEmbossingMgtManager();
            commonFilePathBean = cardEmbossingManager.getFilePaths(osType);


            if (fileType.equals("EMBOSS")) {
                filePath = commonFilePathBean.getEmbossFile();
            }
            if (fileType.equals("EMV")) {
                filePath = commonFilePathBean.getEmvFile();
            }
            if (fileType.equals("ENCODE")) {
                filePath = commonFilePathBean.getEncodefile();
            }
            if (fileType.equals("STATEMENT")) {
                filePath = commonFilePathBean.getStatement();
            }
            if (fileType.equals("LETTERS")) {
                filePath = commonFilePathBean.getLetters();
            }
            if (fileType.equals("EOD")) {
                filePath = commonFilePathBean.getEodFile();
            }
            if (fileType.equals("MOD")) {
                filePath = commonFilePathBean.getModFile();
            }
            if (fileType.equals("SCANNEDAPPLICATION")) {
                filePath = commonFilePathBean.getScanApplication();
            }
            if (fileType.equals("SCANNEDDOCUMENT")) {
                filePath = commonFilePathBean.getScandocument();
            }


            return filePath;

        } catch (Exception ex) {
            throw ex;
        }
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

    private void createDirectory(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private void setAudittraceValue(HttpServletRequest request, String fileName) throws Exception {


        try {
            systemAuditBean = new SystemAuditBean();
            ////get unique Id from the page.It may be the primary key---------------
            String uniqueId = request.getParameter("");

            systemAuditBean.setUserRoleCode(sessionVarlist.getCMSSessionUser().getUserRole());
            systemAuditBean.setUserName(sessionVarlist.getCMSSessionUser().getUserName());
            //set unique id
            systemAuditBean.setUniqueId(uniqueId);
            //set description 
            systemAuditBean.setDescription("Generate " + fileName + " EOD file by " + sessionVarlist.getCMSSessionUser().getUserName());
            systemAuditBean.setApplicationCode(ApplicationVarList.BACK_OFFICEE);
            systemAuditBean.setSectionCode(SectionVarList.EOD_FILE_GENR);
            systemAuditBean.setPageCode(PageVarList.EOC_FILE_GENE);
            systemAuditBean.setTaskCode(TaskVarList.EODFILEGEN);
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
