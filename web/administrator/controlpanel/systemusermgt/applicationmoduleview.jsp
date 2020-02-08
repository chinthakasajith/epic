<%-- 
    Document   : controlpanelhome
    Created on : Jan 10, 2012, 5:13:40 PM
    Author     : janaka_h
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>


<!DOCTYPE html>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>EPIC_CMS_HOME</title>
    </head>
    <body>
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container">

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main">




                <div class="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->





                                <c:if test="${SessionObject.operationtype=='edit'}" >
                                    <table>
                                        <tr>
                                            <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                                <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>
                                    <table class="tit"> <tr> <td   class="center">  APPLICATION MANAGEMENT VIEW</td> </tr><tr> <td>&nbsp;</td> </tr></table>
                                    <form method="POST" action="${pageContext.request.contextPath}/LoadAppLicationModuleServlet">

                                        <table border="0">
                                            <tbody>
                                                <tr>
                                                    <td>Application Code</td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" readonly="" maxlength="16" name="editAppCode" value="${SessionObject.appBean.applicationCode}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" readonly="" name="editAppdes" value="${SessionObject.appBean.description}" /></td>
                                                </tr
                                                <tr>
                                                    <td>Status</td>
                                                    <td>&nbsp;</td>
                                                    <td><select name="editStatus" disabled="">
                                                            <c:forEach var="vRule" items="${SessionObject.secList}">


                                                                <c:if test="${vRule.statusCode ==SessionObject.appBean.status}">
                                                                    <option selected="" value="${vRule.statusCode}">${vRule.description}</option>
                                                                </c:if>
                                                                <c:if test="${vRule.statusCode !=SessionObject.appBean.status}">
                                                                    <option value="${vRule.statusCode}">${vRule.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                    &nbsp;
                                    &nbsp;
                                    &nbsp;
                                    &nbsp;
                                    &nbsp;

                                </c:if>

                                <!--   ------------------------- end developer area  --------------------------------                      -->

                            </div>

                        </div>
                    </div>








                </div>
                <div class="clearer"><span></span></div>
            </div>

        </div>

    </body>
</html>
