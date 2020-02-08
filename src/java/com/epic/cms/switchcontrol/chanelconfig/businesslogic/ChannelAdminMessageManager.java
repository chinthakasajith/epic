/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.chanelconfig.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.application.common.persistance.StatusPersistence;
import com.epic.cms.switchcontrol.chanelconfig.bean.ChannelAdminMessagesBean;
import com.epic.cms.switchcontrol.chanelconfig.bean.ChannelConfigBean;
import com.epic.cms.switchcontrol.chanelconfig.bean.ChannelTypeBean;
import com.epic.cms.switchcontrol.chanelconfig.bean.ConnectionTypeBean;
import com.epic.cms.switchcontrol.chanelconfig.bean.StatusTypeBean;
import com.epic.cms.switchcontrol.chanelconfig.persistance.ChannelAdminMessagePersistance;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author nipun_t
 */
public class ChannelAdminMessageManager {

    private static ChannelAdminMessageManager setInstance;
    private static ChannelAdminMessagePersistance channeConfigPer = new ChannelAdminMessagePersistance();
    ;
    private StatusPersistence statusPer;
    private Connection CMSCon;
    private Connection CMSConOnline;
    private SystemAuditManager sysAuditManager;
    private List<ChannelConfigBean> channelList = null;
    private List<ConnectionTypeBean> connectionTypeList = null;
    private List<ChannelTypeBean> channelTypeList = null;
    private List<StatusTypeBean> statusList = null;
    private List<StatusTypeBean> statusEDList = null;

    /**
     *
     * @return
     */
    public synchronized static ChannelAdminMessageManager getChannelConfigManager() {
        if (setInstance == null) {
            setInstance = new ChannelAdminMessageManager();
        }
        return setInstance;
    }

    /**
     *
     * @param type
     * @return
     * @throws Exception
     */
    public static int sendReq(ChannelAdminMessagesBean channel, SystemAuditBean systemAuditBean) throws Exception {
        int success ;

        String inputMsg = "0101" + channel.getOperationType() + "01";
        System.out.println("message =" + inputMsg);
        String returnMsg = channeConfigPer.getServerMsg(inputMsg);
        System.out.println("response="+returnMsg);
        success = Integer.parseInt(returnMsg.substring(9));
        return success;
    }

    public ChannelAdminMessagesBean getServerInfo() throws Exception {
        ChannelAdminMessagesBean serverVersionInfo = new ChannelAdminMessagesBean();
        try {
            channeConfigPer = new ChannelAdminMessagePersistance();
            CMSCon = DBConnectionToOnlineDB.getConnection();
            CMSCon.setAutoCommit(false);
            serverVersionInfo = channeConfigPer.getServerInfo(CMSCon);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(CMSCon);
        }
        return serverVersionInfo;
    }

}
