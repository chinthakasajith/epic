<%-- 
    Document   : CALLCENTER
    Created on : Nov 21, 2012, 11:54:40 AM
    Author     : janaka_h
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<%@ page  import="com.epic.cms.system.util.session.SessionUser" %>
<%@ page  import="com.epic.cms.system.util.session.SessionVarList" %>
<%@ page  import="com.epic.cms.system.util.session.SessionVarName" %>
<%@ page  import="java.util.*" %>
<%@ page  import="java.util.Collections" %>
<%--<%@ page  import="com.janaka.jsp.bean.common.CMSStatusBean" %>--%>
<%@ page  import="com.epic.cms.system.util.comparator.CMSSectionComparator" %>
<%@ page  import="com.epic.cms.system.util.comparator.CMSPageComparator" %>
<%--<%@ page  import="com.epic.ecdr.rec.bean.controlpanel.systemusrmgt.RECUserRoleBean" %>--%>

<%@ page  import="com.epic.cms.admin.controlpanel.sysusermgt.bean.SectionBean" %>
<%@ page  import="com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.request.contextPath}/resources/css/styles/rcmsStyle.css" rel="stylesheet" type="text/css" />    
        
        <link href="${pageContext.request.contextPath}/resources/css/appstyle.css" rel="stylesheet" type="text/css"/>

        <title>::: EPIC CMS :::</title>


        

    </head>
    <body>

<!--        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/nagging-menu.js" charset="utf-8"></script>-->

        <%
            SessionVarList sessinvarlist = null;
            //List<TESTUserRoleBean>  userRoleList = null ;
            Map<SectionBean, List<PageBean>> sectionPageList = null;

            sessinvarlist = (SessionVarList) request.getSession().getAttribute(SessionVarName.SESSION_OBJ);
            SessionUser user = null;
            user = sessinvarlist.getCMSSessionUser();
            if (user != null) {
                // userRoleList=user.getUserRoleList();
                sectionPageList = sessinvarlist.getCallcenterSectionPageList();
            }
        %>






        <div id="toppage" align="center">

        </div>
        <div id="header" align="center">


            <div id="pagetitle">

            </div>
        </div>


        <div id="menu" class="default">
            <div class="glossymenu">

                <!--<a href="${pageContext.request.contextPath}/ApplicationLogin?appCode=CALLCENTER"><p><center><font size="2" style="font-weight: bold;">CALL CENTER</font></center> </p></a>-->
                <p></p><center><font size="2" style="font-weight: bold;" >CALL CENTER</font></center><p></p>


                <%
                    String s = "test";
                    //comparators
                    CMSSectionComparator secCompare = new CMSSectionComparator();
                    CMSPageComparator pageCompare = new CMSPageComparator();

                    Set<SectionBean> section = null;
                    section = sectionPageList.keySet();

                    TreeSet<SectionBean> sortedSet = new TreeSet<SectionBean>(secCompare);
                    for (SectionBean sec : section) {
                        sortedSet.add(sec);
                    }

                    int i = 0;
                    int j = 0;

                %>


                <%                    for (SectionBean sec : sortedSet) {
                        i = j + 1;
                        j = i;
                %>
                <a class="menuitem submenuheader" href="#"><%= sec.getDescription()%></a>
                <%
                    List<PageBean> pageList = new ArrayList<PageBean>();
                    pageList = sectionPageList.get(sec);

                    Collections.sort(pageList, pageCompare);
                %>
                <div class="submenu">
                    <ul>
                        <%
                            for (PageBean pageBean : pageList) {
                                j++;
                                String url = pageBean.getUrl() + "";

                        %>

                        <li><a href="#" onclick="invokeSetSessionMenu('CALLCENTER', '<%= url%>')"> <%= pageBean.getDescription()%></a></li>




                        <%
                            }
                        %>
                    </ul>
                </div>

                <%
                    }
                %>





            </div>



        </div><!-- close menu -->





    </body>
</html>
