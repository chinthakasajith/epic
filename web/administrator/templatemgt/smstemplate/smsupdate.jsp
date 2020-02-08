<%-- 
    Document   : smsmgt
    Created on : Jun 21, 2016, 8:00:00 AM
    Author     : sajith
--%>
<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script language="javascript">

            function invokeUpdate()
            {

                document.updatesmsform.action = "${pageContext.request.contextPath}/UpdateSMSTemplateServlet";
                document.updatesmsform.submit();

            }

            function invokeCancel()
            {

                window.location = "${pageContext.request.contextPath}/LoadSMSTempMgtServlet";

            }

            function invokeReset(id) {

                window.location = "${pageContext.request.contextPath}/LoadUpdateSMSTempFormServlet?templatecode=" + id;
            }


        </script>
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.SMSTEMPLATE%>'
                        },
                        function (data) {

                            if (data != '') {
                                $('.center').text(data)
                                var heading = data.split('→');
                                $('.heading').text(heading[1]);

                            }


                        });

            }

        </script> 



        <title>EPIC SMS TEMPLATE MANAGEMENT</title>
    </head>
    <body>

        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp">
                <c:param name="message" value="<%=MessageVarList.SESSION_EXPIRED%>"/>
            </c:redirect>
        </c:if>

        <div class="container">

            <div class="header">


            </div>


            <div class="main">
                <jsp:include page="/subheader.jsp"/>



                <div class="content" >

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/></td>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>


                                <form method="POST" action="" name="updatesmsform">
                                    <table>
                                        <tr>
                                            <td><label><b><font color="#FF0000"> ${errorMsg}</font></b></label></td>
                                            <td><label><b><font color="Green"> ${successMsg}</font></b></label></td>
                                        </tr>
                                    </table>
                                    <table cellpadding="0" cellspacing="10">
                                        <tbody>                                            
                                            <tr >
                                                <td>
                                                    <input type="hidden" name="oldValue" value="${oldValue}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Template Code</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text"  name="templatecode" value="${smsBean.templateCode}" maxlength="6"/>
                                                </td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td>Template Description</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text"  name="description" value="${smsBean.description}"/>
                                                </td>
                                                <td></td>
                                            </tr>

                                            <tr>
                                                <td>Message Body</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <textarea name="messageBody" id="messageBody" cols="15" rows="5" style="width: 60%;">${smsBean.messageBody}</textarea>
                                                </td>
                                                <td></td>
                                            </tr>

                                            <tr>
                                                <td>Status </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select  name="status" style="width: 163px">
                                                        <option value="" >------SELECT------</option>
                                                        <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">
                                                            <c:if test="${smsBean.status==status.statusCode}">
                                                                <option value="${status.statusCode}" selected>${status.description}</option>
                                                            </c:if>
                                                            <c:if test="${smsBean.status!=status.statusCode}">
                                                                <option value="${status.statusCode}" >${status.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>
                                        <td></td>
                                        <td>&nbsp;&nbsp;&nbsp;
                                            <input type="submit" value="Update" name="update" class="defbutton" onclick="invokeUpdate()"/>
                                            <input type="button" value="Reset" name="reset"  class="defbutton" onclick="invokeReset('${emailBean.templateCode}')" />
                                            <input type="button" value="Cancel" name="cancel" class="defbutton" onclick="invokeCancel()"/></td>
                                        <td></td>
                                        </tr>
                                        </tbody>

                                    </table>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="clearer"><span></span></div>
            </div>

        </div>
        <div class="footer"><jsp:include page="/footer.jsp"/></div>
    </body>
</html>



