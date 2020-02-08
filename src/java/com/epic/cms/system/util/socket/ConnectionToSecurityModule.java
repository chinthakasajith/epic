/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.system.util.socket;

import com.epic.cms.system.util.exception.SecurityModuleException;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author mahesh_m
 */
public class ConnectionToSecurityModule {

    private PrintWriter PW;
    private BufferedReader serverMsg;
    Socket clientScoket;
    BufferedReader inClint;
    BufferedReader outClient;
    String message;

    public String getServerMsg(String msg) throws UnknownHostException, SecurityModuleException, Exception {
        
        try {
            clientScoket = new Socket("192.168.1.91", 6400);
            PW = new PrintWriter(clientScoket.getOutputStream(), true);
            PW.println(msg);
            serverMsg = new BufferedReader(new InputStreamReader(clientScoket.getInputStream()));
            message = serverMsg.readLine();
            if(message.equals(StatusVarList.ERROR_CONNECT_TO_SEC_MODULE_CODE)){ //Server IP is not registered in Security Module. 
                throw new SecurityModuleException(MessageVarList.ERROR_CONNECT_TO_SEC_MODULE_MESSAGE);
            }
            
        } catch (UnknownHostException ex) {
            throw ex;
            
        } finally {
            try {
                PW.flush();
                PW.close();
                serverMsg.close();
                clientScoket.close();
            } catch (Exception ex) {          
                throw ex;
            }
        }
        return message;
        
    }
}
