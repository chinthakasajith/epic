/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.system.util.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author mahesh_m
 */
public class ConnectionToETMRouter {
    private PrintWriter PW;
    private BufferedReader serverMsg;
    Socket clientScoket;
    BufferedReader inClint;
    BufferedReader outClient;
    String message;

    public String getServerMsg(String msg) throws UnknownHostException, Exception {
        
        try {
            //clientScoket = new Socket("192.168.1.91", 5000);
            clientScoket = new Socket("192.168.1.63", 8551);
            PW = new PrintWriter(clientScoket.getOutputStream(), true);
            PW.println(msg);
            serverMsg = new BufferedReader(new InputStreamReader(clientScoket.getInputStream()));
            message = serverMsg.readLine();
            System.out.println(">>>>>>>>>>>>>>>>>>>> "+message); 
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
