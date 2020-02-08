<%-- 
    Document   : creditconfirmationuserroleschemamgt
    Created on : Jun 22, 2016, 2:14:18 PM
    Author     : chinthaka_r
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.CREDITCONFIRMSCEMAMGT%>'
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

        <script language="javascript">
            
 
            function selectAll(selectBox1,selectBox2) {
                for (var i = 0; i < selectBox1.options.length; i++) {
                    selectBox1.options[i].selected = true;
                }
                for (var i = 0; i < selectBox2.options.length; i++) {
                    selectBox2.options[i].selected = true;
                }
                document.userRoleSchemaform.action="${pageContext.request.contextPath}/ManageUserRoleCreditConfirmationSchemaServlet?schemaCode=${schemaCode}";
                document.userRoleSchemaform.submit();
            }
            function invokeReset() {

                window.location = "${pageContext.request.contextPath}/LoadCreditConfirmationSchemaMgtServlet";

            }
            
        </script>

        <title>EPIC CMS USER ROLE HOME</title>
    </head>
    <body>
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp">
                <c:param name="message" value="<%=MessageVarList.SESSION_EXPIRED%>"/>
            </c:redirect>
        </c:if>
        <div class="container">
            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

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

                                <%-- -------------------------add form start -------------------------------- --%>
                                <form name="userRoleSchemaform" action="" method="POST">
                                    <table>
                                        <tr>

                                            <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                            <td><b><font color="Green"> ${successMsg}</font></b> </td>

                                            <td></td>
                                        </tr>
                                    </table>
                                    <table  border="0">
                                        <tr>
                                            <td style="width: 100px" >Schema Code</td><td> <B> <c:out value="${schemaCode}"/></B></td>
                                        </tr>
                                        <tr> <td style="height:12px;"></td></tr>
                                    </table>
                                    <table border="0">
                                        <tr>
                                            <td><label><b>Not Assigned User Roles</b></label></td>
                                            <td></td>
                                            <td><label><b>Assigned User Roles</b></label></td>
                                        </tr>
                                        <tr></tr>
                                        <tr>
                                            <td>
                                                <select id=in name="notAssignedUserRoleList" style="width: 200px" multiple="true" size="10">
                                                    <c:forEach  var="userRole" items="${sessionScope.SessionObject.notAssignedUserRolesToSchemaList}">
                                                        <option value="${userRole.userRoleCode}" >${userRole.description}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <input type=button value=">" style="width: 30px" onclick=moveout()><br>
                                                <input type=button value="<" style="width: 30px" onclick=movein()><br>
                                                <input type=button value=">>" style="width: 30px" onclick=moveallout()><br>
                                                <input type=button value="<<" style="width: 30px" onclick=moveallin()>
                                            </td>
                                            <td>
                                                <select id=out name="assignedUserRoleList" style="width: 200px" multiple="true" size="10">
                                                    <c:forEach  var="userRole" items="${sessionScope.SessionObject.assignedUserRolesToSchemaList}">
                                                        <option value="${userRole.userRoleCode}" >${userRole.description}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                    </table>
                                    <table>
                                        <tr>
                                            <td><input type="submit" onclick="selectAll(assignedUserRoleList,notAssignedUserRoleList)" name="btnAssignSchema" value="Assign" style="width: 100px"></td>

                                            <td><input type="button"  name="back" value="Back" style="width: 100px" onclick="invokeReset()"></td>
                                        </tr>
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
