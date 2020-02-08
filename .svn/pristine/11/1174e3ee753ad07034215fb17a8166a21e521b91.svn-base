<%-- 
    Document   : imageviewhome
    Created on : Jun 29, 2012, 2:19:10 PM
    Author     : nalin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <style type="text/css">

        </style>






        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tablejs/jquery-1.4.2.min.js"></script>
<!--        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/windowjs/jquery.rotate.js"></script>-->
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/windowjs/jquery-1.6.4.min.js"></script>

<!--        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/windowjs/imgRotateCanvas.js"></script>-->

<!--        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/windowjs/imagepanner.js"></script>-->

        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/windowjs/wadda.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/windowjs/commons.js"></script>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/windowcss/dhtmlwindow.css" type="text/css" />

        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/imagezoomer/jquery-ui-1.7.1.custom.css" type="text/css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/imagezoomer/jquery.gzoom.css" type="text/css" />


        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/windowjs/dhtmlwindow.js"></script> 

        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/imagezoomer/jquery.gzoom.js"></script> 
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/imagezoomer/jquery.mousewheel.js"></script> 
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/imagezoomer/ui.core.min.js"></script> 
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/imagezoomer/ui.slider.min.js"></script> 
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/imagezoomer/jQueryRotate.js"></script> 




        <script type= "text/javascript">
            /*<![CDATA[*/
            $(function() {
                $("#zoom01").gzoom({
                    sW: 300,
                    sH: 350,
                    lW: 1400,
                    lH: 1050,
                    lighbox : false
                });		

                var value = 0
                $("#zoom01").rotate({ 
                    bind: { 
                        click: function(){
                            value +=90;
                            $(this).rotate({ animateTo:value})
                        }
                    }    
                });		
            });
            /*]]>*/
        </script>




    </head>
    <body>

        <div id="container" style="margin:auto">
            <div id="zoom01" class="zoom" data-canzoom="yes" style="height: 200px; border: 1px solid rgb(206, 206, 206); overflow: hidden; width: 100px; ">
                <img style ="width:200px; height:200px" id="image" src="${pageContext.request.contextPath}/LoadUploadedImageToCreditOfficerServlet?documentType=<%=request.getParameter("documentType")%>&applicationId=<%=request.getParameter("applicationId")%>"/>
            </div>
        </div>


    </body>
</html>
