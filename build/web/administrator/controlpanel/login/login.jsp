<%-- 
    Document   : login
    Created on : Jan 10, 2012, 11:58:04 AM
    Author     : janaka_h
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    String errorMessage = null;

    errorMessage = request.getParameter("message");

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CMS Login Page</title>


        <link href="${pageContext.request.contextPath}/resources/css/login-box.css" rel="stylesheet" type="text/css" />

        <script>
            
            document.onkeypress=function(e){
                var e=window.event || e
                if(e.keyCode==13){
                    
                document.loginform.action = "${pageContext.request.contextPath}/Login";
                document.loginform.submit();
                }
               
            }
            function invokelogin(){
               
                document.loginform.action = "${pageContext.request.contextPath}/Login";
                document.loginform.submit();
                
            }
            
            function invokeloginTest(password){
                alert(password);
//                document.loginform.action = "${pageContext.request.contextPath}/Login";
//                document.loginform.submit();
                
            }
            
        </script>

    </head>
    <body style="background-color: #D6E5F4">


        <div style="padding: 100px 0 0 500px;">


            <div id="login-box">
    <!--            <form name="loginform" method="POST" action='<%=request.getContextPath()%>/Login'>
                    <table  border="0">
                        <tr>
                            <td colspan="3">
                                <font color="#FF0000"><b><%

                %></b></font></td>
</tr>
<tr>
    <td>User Name</td>
    <td><input type="text" class="input" name="username" size="20" value="" maxlength="64" style="width:150px " ></td>
    <td>&nbsp;</td>
</tr>
<tr>
    <td>Password</td>
    <td><input type="password" class="input" size="20" name="password" maxlength="255" style="width:150px "></td>
    <td>&nbsp;</td>
</tr>
<tr>
    <td>&nbsp;</td>
    <td><br><p class="submit"><input type="submit" value="Login"></p></td>
    <td>&nbsp;</td>
</tr>
</table>

</form>-->

                <H2>Login</H2>
                Please enter username and password to login to the system
                <br />
                <br />
                <form name="loginform" method="POST" >

                    <div id="login-box-name" style="margin-top:20px;">Username:</div>
                    <div id="login-box-field" style="margin-top:20px;">
                        <input name="username" class="form-login" title="Username" value="" size="30" maxlength="2048" /></div>
                    <div id="login-box-name">Password:</div>
                    <div id="login-box-field">
                        <input name="password" type="password" class="form-login" title="Password" value="" size="30" maxlength="2048" /></div>
                    <br />
                    <!--                    <span class="login-box-options">
                                            <input type="checkbox" name="1" value="1"> Remember Me <a href="#" style="margin-left:30px;">Forgot password?</a>
                                        </span>-->
                    <br />
                    <br />
                    <a href="#" onclick="invokelogin()"><img src="${pageContext.request.contextPath}/resources/images/button.png" width="103" height="42" style="margin-left:90px;" /></a> 
<!-- <a href="#" onclick="invokeloginTest(password.value)"><img src="${pageContext.request.contextPath}/resources/images/button.png" width="103" height="42" style="margin-left:90px;" /></a> -->

                </form>
            </div>

        </div>
        <div>

            <center><font color="#FF0000"><b><%




                if (errorMessage != null) {
                    out.print(errorMessage);
                }
                    %></b></font>
                    <font color="Green"> <b>${successMsg}</b></font>
                    <font color="#FF0000"><b> ${errorMsg}</b></font>
            </center>
        </div>

    </body>
</html>
