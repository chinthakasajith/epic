/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.chanelconfig.persistance;

import com.epic.cms.switchcontrol.chanelconfig.bean.ChannelAdminMessagesBean;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author nipun_t
 */
public class ChannelAdminMessagePersistance {

    //////////////////////////////////////////////////////////////////////////////////////////
    /////////////////// Orr  Call to ConnectionToETMRouter Class////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    private ResultSet rs;
    private PrintWriter PW;
    private BufferedReader serverMsg;
    Socket clientScoket;
    BufferedReader inClint;
    BufferedReader outClient;
    String message;

    public ChannelAdminMessagesBean getServerInfo(Connection con) throws Exception {
        PreparedStatement getByCatStat = null;
        ChannelAdminMessagesBean onlineVersionInfo = new ChannelAdminMessagesBean();
        try {
            getByCatStat = con.prepareStatement("select IP,PORT,TIMEOUT from ecms_online_channels where channelname='VISA'");

            rs = getByCatStat.executeQuery();

            while (rs.next()) {

                onlineVersionInfo.setIp(rs.getString("IP"));
                onlineVersionInfo.setPort(rs.getString("PORT"));
                onlineVersionInfo.setTimeOut(rs.getString("TIMEOUT"));
                onlineVersionInfo.setConnectionType("Permanant Connection");

            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getByCatStat.close();
        }
        return onlineVersionInfo;
    }

    public  String getServerMsg(String msg) throws UnknownHostException, Exception {

        try {
            //clientScoket = new Socket("192.168.1.91", 5000);
            clientScoket = new Socket("192.168.20.32", 8551);
            clientScoket.setSoTimeout(20000); // if nessasary
            PW = new PrintWriter(clientScoket.getOutputStream(), true);
            PW.println(msg);
            serverMsg = new BufferedReader(new InputStreamReader(clientScoket.getInputStream()));
            message = serverMsg.readLine();
            System.out.println(">>>>>>>>>>>>>>>>>>>> " + message);
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
