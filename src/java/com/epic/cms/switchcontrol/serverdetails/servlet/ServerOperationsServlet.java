/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.serverdetails.servlet;

import com.epic.cms.system.util.socket.ConnectionToETMRouter;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mahesh_m
 */
public class ServerOperationsServlet extends HttpServlet {

    static String serverStart = null;
    private RequestDispatcher rd;
    static String serverRestart = null;
    static String serverStop = null;
    static String clearLog = null;
    static String clearAllLog = null;
    static String backupLogs = null;
    static String removeBackups = null;
    static String systemRestart = null;
    static String systemShutdown = null;
    static ConnectionToETMRouter connectionToETM ;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        connectionToETM = new ConnectionToETMRouter();
        try {
            String index = request.getParameter("index");


            if (index.equals("0")) {
                this.startECMSServer(request);
            } else if (index.equals("1")) {
                this.restartECMSServer(request);
            } else if (index.equals("2")) {
                this.stopECMSServer(request);
            } else if (index.equals("3")) {
                this.clearLogs(request);
            } else if (index.equals("4")) {
                this.clearAllLogs(request);
            } else if (index.equals("5")) {
                this.backupLogs(request);
            } else if (index.equals("6")) {
                this.removerBackupLogs(request);
            } else if (index.equals("7")) {
                this.systemRestart(request);
            } else if (index.equals("8")) {
                this.systemShutdown(request);
            }
            
            
            rd = getServletContext().getRequestDispatcher("/LoadServerDetailsServlet");
            rd.forward(request, response);
            System.gc();
        } catch (Exception e) {
        } finally {
            
            out.close();
            

        }
    }

    public static synchronized void startECMSServer(HttpServletRequest request) {
        try {

            serverStart = connectionToETM.getServerMsg(StatusVarList.SERVER_START);
            String status = serverStart.substring(8, 10);

            if (status.equals("00")) {
                request.setAttribute("successMsg", MessageVarList.SERVER_START_SUCSESS);
            } else {
                request.setAttribute("errorMsg", MessageVarList.SERVER_START_FAIL);
            }

        } catch (Exception e) {
            request.setAttribute("errorMsg", MessageVarList.SERVER_START_FAIL);
        } finally {
            connectionToETM = null;

        }
    }

    public static synchronized void restartECMSServer(HttpServletRequest request) {
        try {

            serverRestart = connectionToETM.getServerMsg(StatusVarList.SERVER_SESSION_RESTART);
            String status = serverRestart.substring(8, 10);

            if (status.equals("00")) {
                request.setAttribute("successMsg", MessageVarList.SERVER_RESTART_SUCSESS);
            } else {
                request.setAttribute("errorMsg", MessageVarList.SERVER_RESTART_FAIL);
            }

        } catch (Exception e) {
            request.setAttribute("errorMsg", MessageVarList.SERVER_RESTART_FAIL);
        } finally {
            connectionToETM = null;

        }
    }

    public static synchronized void stopECMSServer(HttpServletRequest request) {
        try {

            serverStop = connectionToETM.getServerMsg(StatusVarList.SERVER_STOP);
            String status = serverStop.substring(8, 10);

            if (status.equals("00")) {
                request.setAttribute("successMsg", MessageVarList.SERVER_STOP_SUCSESS);
            } else {
                request.setAttribute("errorMsg", MessageVarList.SERVER_STOP_FAIL);
            }

        } catch (Exception e) {
            request.setAttribute("errorMsg", MessageVarList.SERVER_STOP_FAIL);
        } finally {
            connectionToETM = null;

        }
    }

    public static synchronized void clearLogs(HttpServletRequest request) {
        try {

            clearLog = connectionToETM.getServerMsg(StatusVarList.SERVER_CLEAR_LOGS);
            String status = clearLog.substring(8, 10);

            if (status.equals("00")) {
                request.setAttribute("successMsg", MessageVarList.SERVER_CLEAR_LOG_SUCSESS);
            } else {
                request.setAttribute("errorMsg", MessageVarList.SERVER_CLEAR_LOG_FAIL);
            }

        } catch (Exception e) {
            request.setAttribute("errorMsg", MessageVarList.SERVER_CLEAR_LOG_FAIL);
        } finally {
            connectionToETM = null;

        }
    }

    public static synchronized void clearAllLogs(HttpServletRequest request) {
        try {

            clearAllLog = connectionToETM.getServerMsg(StatusVarList.SERVER_CLEAR_ALL_LOGS);
            String status = clearAllLog.substring(8, 10);

            if (status.equals("00")) {
                request.setAttribute("successMsg", MessageVarList.SERVER_CLEAR_ALL_LOG_SUCSESS);
            } else {
                request.setAttribute("errorMsg", MessageVarList.SERVER_CLEAR_ALL_LOG_FAIL);
            }

        } catch (Exception e) {
            request.setAttribute("errorMsg", MessageVarList.SERVER_CLEAR_ALL_LOG_FAIL);
        } finally {
            connectionToETM = null;

        }
    }

    public static synchronized void backupLogs(HttpServletRequest request) {
        try {

            backupLogs = connectionToETM.getServerMsg(StatusVarList.SERVER_LOGS_BACKUP);
            String status = backupLogs.substring(8, 10);

            if (status.equals("00")) {
                request.setAttribute("successMsg", MessageVarList.SERVER_BACKUP_LOG_SUCSESS);
            } else {
                request.setAttribute("errorMsg", MessageVarList.SERVER_BACKUP_LOG_FAIL);
            }

        } catch (Exception e) {
            request.setAttribute("errorMsg", MessageVarList.SERVER_BACKUP_LOG_FAIL);
        } finally {
            connectionToETM = null;

        }
    }

    public static synchronized void removerBackupLogs(HttpServletRequest request) {
        try {

            removeBackups = connectionToETM.getServerMsg(StatusVarList.SERVER_REMOVE_BACKUP_LOGS);
            String status = removeBackups.substring(8, 10);

            if (status.equals("00")) {
                request.setAttribute("successMsg", MessageVarList.SERVER_REMOVE_BACKUP_LOG_SUCSESS);
            } else {
                request.setAttribute("errorMsg", MessageVarList.SERVER_REMOVE_BACKUP_LOG_FAIL);
            }

        } catch (Exception e) {
            request.setAttribute("errorMsg", MessageVarList.SERVER_REMOVE_BACKUP_LOG_FAIL);
        } finally {
            connectionToETM = null;

        }
    }

    public static synchronized void systemRestart(HttpServletRequest request) {
        try {

            systemRestart = connectionToETM.getServerMsg(StatusVarList.SERVER_RESTART);
            String status = systemRestart.substring(8, 10);

            if (status.equals("00")) {
                request.setAttribute("successMsg", MessageVarList.SYSTEM_RESTART_SUCSESS);
            } else {
                request.setAttribute("errorMsg", MessageVarList.SYSTEM_RESTART_FAIL);
            }

        } catch (Exception e) {
            request.setAttribute("errorMsg", MessageVarList.SYSTEM_RESTART_FAIL);
        } finally {
            connectionToETM = null;

        }
    }

    public static synchronized void systemShutdown(HttpServletRequest request) {
        try {

            systemShutdown = connectionToETM.getServerMsg(StatusVarList.SERVER_SHUTDOWN);
            String status = systemShutdown.substring(8, 10);

            if (status.equals("00")) {
                request.setAttribute("successMsg", MessageVarList.SYSTEM_SHUTDOWN_SUCSESS);
            } else {
                request.setAttribute("errorMsg", MessageVarList.SYSTEM_SHUTDOWN_FAIL);
            }

        } catch (Exception e) {
            request.setAttribute("errorMsg", MessageVarList.SYSTEM_SHUTDOWN_FAIL);
        } finally {
            connectionToETM = null;

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
