<%-- 
    Document   : question
    Created on : May 17, 2012, 12:01:52 PM
    Author     : janaka_h
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script>
            
            function invorkQuestion(){
               window.location = "${pageContext.request.contextPath}/LoadQuestionVerifyPageServlet"; 
            }
            
        </script>
    </head>
    <body>
        <h1>Hello janaka, are you Going to create questions for Me ?</h1>
        
        <input type ="button" value="Load Question" onclick="invorkQuestion()"/>
    </body>
</html>
