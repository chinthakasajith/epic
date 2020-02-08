/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.system.util.session;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SectionBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.UserRoleBean;
import com.epic.cms.application.common.bean.StatusBean;
import java.util.List;
import java.util.Map;

/**
 *
 * @author janaka_h
 */
public class SessionUser {
    private String userName;
    private String userRole;
    private int levelId;
    private List<UserRoleBean>  userRoleList = null ;
    private List<StatusBean> statusList = null;

    public List<StatusBean> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<StatusBean> statusList) {
        this.statusList = statusList;
    }

    
    
    public List<UserRoleBean> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<UserRoleBean> userRoleList) {
        this.userRoleList = userRoleList;
    }
        

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

   

  
    
    
    //    private HashMap addressMap;

    private Map<SectionBean,List<PageBean>> sectionPageList;



    public Map<SectionBean, List<PageBean>> getSectionPageList() {


        return sectionPageList;
    }


    public void setSectionPageList(Map<SectionBean, List<PageBean>> sectionPageList) {

        this.sectionPageList = sectionPageList;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }
    
    

    
}
