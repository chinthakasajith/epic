<%-- 
    Document   : mainapplications
    Created on : Jun 14, 2012, 2:26:33 PM
    Author     : janaka_h
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <meta charset="utf-8">
        <title>Card Management System</title>

        <!-- Required CSS -->
        <link href="${pageContext.request.contextPath}/resources/applicationmenu/css/movingboxes.css" media="screen" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resources/applicationmenu/css/movingboxes-ie.css" media="screen" rel="stylesheet">
        <!--[if lt IE 9]>
        <link href="css/movingboxes-ie.css" rel="stylesheet" media="screen" />
        <![endif]-->

        <!-- Required script -->
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tablejs/jquery-1.4.2.min.js"></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/applicationmenu/js/jquery.movingboxes.js"></script>

        <!-- Demo only -->
        <link href="${pageContext.request.contextPath}/resources/applicationmenu/demo/demo.css" media="screen" rel="stylesheet">
        <style>
            /* Dimensions set via css in MovingBoxes version 2.2.2+ */
            #slider { width: 1105px; }
            #slider li { width: 250px; }
        </style>
        <script>
            $(function(){

                $('#slider').movingBoxes({
                    /* width and panelWidth options deprecated, but still work to keep the plugin backwards compatible
                        width: 500,
                        panelWidth: 0.5,
                     */
                    startPanel   : 3,      // start with this panel
                    wrap         : true,   // if true, the panel will "wrap" (it really rewinds/fast forwards) at the ends
                    buildNav     : true,   // if true, navigation links will be added
                    navFormatter : function(){ return "&#9679;"; } // function which returns the navigation text for each panel
                });

            });
        
        
        
            function invokeApplicationLogin(appCode){
                
                window.location = "${pageContext.request.contextPath}/ApplicationLogin?appCode="+appCode;                
                
            }
        </script>
    </head>
    <body>

        <div id="wrapper">

            <c:if test="${sessionScope.SessionObject==null}">
                <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
            </c:if>

            <div id="title">
                <!--		<a href="#"><img src="demo/movingboxes.png" alt="card management system"></a>-->
            </div>

            <div id="nav">
                <br />
                <br />
                <br />
                <br />
                <br />
            </div>
            <br>

            <!-- MovingBoxes Slider -->
            <ul id="slider">

                <c:forEach var="appList" items="${requestScope.applicationLst}">
                    <li><a href="#"  onclick="invokeApplicationLogin('${appList.applicationCode}')"><img src="${pageContext.request.contextPath}/resources/images/gooeymenu/${appList.description}.png" style="border-color: white;" /><font><b>${appList.description}</b></font></a></li>
                        </c:forEach>



            </ul> <!-- end Slider #1 -->

        </div> <!-- end wrapper -->

    </body>
</html>
