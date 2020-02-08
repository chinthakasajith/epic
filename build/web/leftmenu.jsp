<%-- 
    Document   : leftmenu
    Created on : Jan 10, 2012, 5:23:54 PM
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

        <title>::: EPIC CMS :::</title>


        <style type="text/css">

            body,td,th {
                font-family: Verdana, Arial, Helvetica, sans-serif;
                font-size: small;
                color: #3F2C1C;
            }
            body {
                margin-left: 0px;
                margin-top: 0px;
                margin-right: 0px;
                margin-bottom: 0px;
            }
            .notificationBox:hover {

                background-color:#FF9900;

            }

            .linkTree:hover{
                text-decoration:underline;
                font-weight: bold;
                color: #FF9900;
            }

            .linkTree{
                text-decoration:none;
            }

            #popDiv {
                background-image: url(resources/images/bg.png);
                background-repeat: repeat;
                padding: 25px;
                height: 100%;
                width: 100%;
                display: none;
                position: absolute;
            }
            #headDiv h2 {
                padding:0;
                margin:0;
                color: #ebebeb;
                font: bold 30px "Calibri", Arial;
            }
        </style>







        <style type="text/css">

            .glossymenu{
                margin: 5px 0;
                padding: 0;
                width: 200px; /*width of menu*/
                border: 1px solid #7F6C5D;
                border-bottom-width: 0;
                background:#7F6C5D;
                color: #000000;
                border-radius: 5px;
                


/*                -moz-box-shadow:    3px 3px 5px 6px #ccc;
                -webkit-box-shadow: 3px 3px 5px 6px #ccc;
                box-shadow:         3px 3px 5px 6px #ccc;*/
                border-left:solid 1px #999999; border-right:solid 1px #999999;
                border-bottom:solid 1px #999999; border-top:dotted 1px #CCCCCC;
            }

            .glossymenu a.menuitem{
                background: linear-gradient(to right, #C8BDAE, #FFFFFF);
                font-family: arial,sans-serif;
                font-size: small;
                color: #3F2C1C;
                display: block;
                position: relative; /*To help in the anchoring of the ".statusicon" icon image*/
                width: auto;
                padding: 4px 0;
                padding-left: 10px;
                text-decoration: none;
                border-bottom: 1px solid #FFFFFF;
            }

            .submenu{
                background: white;
                font-family: arial,sans-serif;
                font-size: small;
                color: #24A0E6;
                display: block;
                position: relative; /*To help in the anchoring of the ".statusicon" icon image*/
                width: auto;
                padding: 4px 0;
                padding-left: 10px;
                text-decoration: none;
            }



            .glossymenu a.menuitem:visited, .glossymenu .menuitem:active{
                color: #3F2C1C;
            }

            .glossymenu a.menuitem .statusicon{ /*CSS for icon image that gets dynamically added to headers*/
                position: absolute;
                top: 5px;
                right: 5px;
                border: none;
            }

            .glossymenu a.menuitem:hover{
                background: #FFFFFF;
            }

            .glossymenu div.submenu{ /*DIV that contains each sub menu*/
               background: linear-gradient(to right, #DBD1C7, #FFFFFF);
               
            }

            .glossymenu div.submenu ul{ /*UL of each sub menu*/
                list-style-type: none;
                margin: 0;
                padding: 0;
                /*                                         background-image: url(${pageContext.request.contextPath}/resources/images/glossyback21.gif);*/
            }

            .glossymenu div.submenu ul li{
                border-bottom: 1px solid blue;

            }

            .glossymenu div.submenu ul li a{
                display: block;
                font-family: arial,sans-serif;
                font-size: small;
                color: #3F2C1C;
                text-decoration: none;
                padding: 2px 0;
                padding-left: 10px;
            }

            .glossymenu div.submenu ul li a:hover{
                background: #FFFFFF;
                /* background-image: url(${pageContext.request.contextPath}/resources/images/glossyback21.gif);*/
                colorz: white;
            }

        </style>


    </head>
    <body>

        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/nagging-menu.js" charset="utf-8"></script>

        <%
            SessionVarList sessinvarlist = null;
            //List<TESTUserRoleBean>  userRoleList = null ;
            Map<SectionBean, List<PageBean>> sectionPageList = null;


            sessinvarlist = (SessionVarList) request.getSession().getAttribute(SessionVarName.SESSION_OBJ);
            SessionUser user = null;
            user = sessinvarlist.getCMSSessionUser();
            if (user != null) {
                // userRoleList=user.getUserRoleList();
                sectionPageList = user.getSectionPageList();
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
                <p><center><font size="2" style="color: #FFFFFF;font-weight: bold;"> <% out.print(sessinvarlist.getLoggedApplicationDes()); %></font></center> </p>


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


                <%
                    for (SectionBean sec : sortedSet) {
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
                                String url = request.getContextPath() + pageBean.getUrl() + "";
                        %>

                        <li><a href="<%= url%>"> <%= pageBean.getDescription()%></a></li>



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