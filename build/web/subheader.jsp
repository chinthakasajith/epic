<%-- 
    Document   : subheader
    Created on : Jun 10, 2012, 3:21:13 PM
    Author     : janaka_h
    Description : all the sub header applied here
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html >

    <head>
        <style type="text/css">
            <!--
            .style1 {font-weight: bold}
            -->
        </style>

        <script>
            function invorkHome() {
                window.location = "${pageContext.request.contextPath}/Login?action=home";

            }
        </script>
    </head>
    <body>



        <div align="right" style="margin-right: 15px">
            <img src="${pageContext.request.contextPath}/resources/images/user.png"/>&nbsp;<font style="font-weight: bold;color: #FFFFFF;">${sessionScope.SessionObject.CMSSessionUser.userName}</font><font style="font-weight: bold;color: #D20000;">|</font>
            <a href='<%=request.getContextPath()%>/ViewChangePasswordServlet' style="text-decoration: none;"><font style="color: #FFFFFF;font-weight: bold;text-decoration: none; ">Change Password</font>  </a> 


            <a href='#' onclick="invorkHome()" ><img src="${pageContext.request.contextPath}/resources/images/home.png"/></a>&nbsp;&nbsp;
            <a href='<%=request.getContextPath()%>/LogoutServlet' style="text-decoration: none;"><font style="color: #FFFFFF;font-weight: bold;">Log Out</font></a>


        </div>
        <div align="left">
            <table > <tr> <td   class="center" style="color: #D20000;font-weight: bold;text-decoration: none; "> 

                    </td> </tr></table>

        </div>

        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="${pageContext.request.contextPath}/administrator/controlpanel/login/login.jsp"/>
        </c:if>
    </body></html>