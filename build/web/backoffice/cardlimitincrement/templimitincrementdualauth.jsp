<%-- 
    Document   : templimitincrementdualauth
    Created on : Aug 14, 2012, 2:45:52 PM
    Author     : nalin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- jsp:include page="/content.jsp"/ -->
        <script  type="text/javascript" charset="utf-8">
            
            function invokeSubmit()
            {                
                document.dualAuthForm.action="${pageContext.request.contextPath}/DualAuthApprovedTempLimitIncrementServlet";
                document.dualAuthForm.submit();                
                               
            }
            
            
        </script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Authentication Page</title>
    </head>
    <body>

        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container">
            <div class="header" style="width: 10px;" >
            </div>
            <div class="main" style="padding: 6px;">
                <div id="content1">
                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------    -->
                                 <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>
                                <form name="dualAuthForm">
                                    <table cellpadding="0" cellspacing="10" >
                                        <tr>
                                            <td><font style="color: red;">Dual Authentication</font></td>
                                        </tr>

                                        <tr>
                                            <td>User Name &nbsp:</td>
                                            <td><input type="text" name="userName" placeholder="User Name" /></td>
                                        </tr>
                                        <tr>
                                            <td>Password &nbsp&nbsp&nbsp:</td>
                                            <td><input type="password" name="password" placeholder="Password" /></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td><input type="submit" style="width: 100px" name="submit" value="Submit" onclick="invokeSubmit()" /></td>
                                        </tr>
                                    </table>
                                </form>
                                <!--   ------------------------- end developer area  --------------------------------                      -->

                            </div>
                        </div>
                    </div>
                </div>
                <div class="clearer"><span></span></div>
            </div>
        </div>
<!--        <div class="footer"><jsp:include page="/footer.jsp"/></div>-->
    </body>
</html>