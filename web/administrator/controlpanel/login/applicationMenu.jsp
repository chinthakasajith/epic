<%-- 
    Document   : applicationMenu
    Created on : Jan 13, 2012, 9:44:31 AM
    Author     : janaka_h
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Application page</title>


        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tablejs/jquery-1.4.2.min.js"></script>

        <script src="${pageContext.request.contextPath}/resources/js/gooeymenu.js">

        </script>
        <script>
            function invokeApplicationLogin(appCode){
                
                window.location = "${pageContext.request.contextPath}/ApplicationLogin?appCode="+appCode;                
                
            }
        </script>
        
        

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/gooeymenu.css" />


    </head>
    <body style="">      
        
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/controlpanel/login/login.jsp"/>
        </c:if>
        
        <ul id="gooeymenu1" class="gelbuttonmenu">            
            
            <c:forEach var="appList" items="${requestScope.applicationLst}">
                <li><a href="#"  onclick="invokeApplicationLogin('${appList.applicationCode}')"><img src="${pageContext.request.contextPath}/resources/images/gooeymenu/${appList.description}.png"/></a></li>
            </c:forEach>
            

        </ul>
     

        <script>
            gooeymenu.setup({id:'gooeymenu1', selectitem:1})
        </script>



    </body>
</html>
