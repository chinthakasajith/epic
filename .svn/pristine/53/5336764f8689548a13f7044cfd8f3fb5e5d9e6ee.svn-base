<%-- 
    Document   : viewFile
    Created on : Jan 16, 2013, 3:12:16 PM
    Author     : ruwan_e
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/content.jsp"/> <!--        include content.jsp for all js and css inclusion-->

        <title>Epic CMS Log Viewer - ${type} -> ${category} -> <fmt:formatDate value="${date}" pattern="yyyy/MM/dd" /></title>

    </head>
    <body>
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>
        <div class="container" >
            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>
            <div class="main" >
                <jsp:include page="/subheader.jsp"/>
                <div class="content" >
                    <jsp:include page="/leftmenu.jsp"/>
                </div>
                <div id="content1" >
                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <div id-="data">
                                    <h1 style="background-color: #0d2c52; color: white">${type} &rarr; ${category} &rarr; <fmt:formatDate value="${date}" pattern="dd/MM/yyyy" /></h1>
                                </div>
                                <textarea id="fileText" readonly="true" rows="30%" style="border: none; width: 100%; font-family: Courier New,Courier, monospace;">
                                    ${content}
                                </textarea>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="clearer"><span></span></div>
            </div>
        </div>
    </body>
</html>
