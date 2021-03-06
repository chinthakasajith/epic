<%-- 
    Document   : cmsuserrolehome
    Created on : Jan 12, 2012, 11:16:55 AM
    Author     : chanuka
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

        <script language="javascript">


            function invokeUpdate()
            {

                document.updateuserRoleform.action = "${pageContext.request.contextPath}/UpdateConfirmedUserRoleServlet";
                document.updateuserRoleform.submit();
            }
            function invokeAdd()
            {

                document.adduserroleform.action = "${pageContext.request.contextPath}/AddUserRoleServlet";
                document.adduserroleform.submit();
            }

            function invokeReset()
            {

                window.location = "${pageContext.request.contextPath}/LoadUserRoleMgtServlet";
            }

            function updateReset(value)
            {

                window.location = "${pageContext.request.contextPath}/UpdateUserRoleServlet?userRoleCode=" + value;
            }
            
            function ConfirmDelete(userRoleCode)
            {
                $("#dialog-confirm").html("<p>Do you really want to delete "+userRoleCode+" user role ?</p>");
                $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 'auto',
                    width: 500,
                    modal: true,
                    buttons: {
                        "No": function () {
                            $(this).dialog("close");
                        },
                        "Yes": function () {
                            window.location = "${pageContext.request.contextPath}/DeleteUserRoleServlet?userRoleCode=" + userRoleCode;
                        }
                    }
                });

            }
            function invokeAddDuplicateUserRole() {
                if ($("#dpluserrolecode").attr('value') == "${userRoleBean.userRoleCode}") {
                    $("#dplErrorMsg").text("User role code can't be same");
                } else if ($("#dpluserroledescription").attr('value') == "${userRoleBean.description}") {
                    $("#dplErrorMsg").text("Description can't be same");
                }
                else {
                    $("#dplErrorMsg").text("");
                    var response = "";
                    var dpl_data = {
                        userrolecode: $("#dpluserrolecode").attr('value'),
                        userroledescription: $("#dpluserroledescription").attr('value'),
                        userlevelcode: $("#userlevelcode").attr('value'),
                        statuscode: $("#dplstatuscode").attr('value'),
                        oldUserRoleCode: "${userRoleBean.userRoleCode}"
                    };
                    $.ajax({
                        url: "${pageContext.request.contextPath}/AddDuplicateUserRoleServlet",
                        type: "POST",
                        data: dpl_data,
                        success: function (data) {
                            var ar = data.split(",", 2);
                            if (ar[0] == 'success') {
                                $("#succMes").val(ar[1]);
                                $("#succForm").submit();


                            } else {
                                $("#dplErrorMsg").text(data);
                            }
                        }
                    });
                }
            }

        </script>
        <script >
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.USERROLE%>'
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
        <script>
            function createDuplicateRole() {
                $(document).ready(function () {
                    $("#addDuplicateUserRoleform").show();
                    $("#userRoleViewForm").hide();
                });
            }
            function viewUserRole() {
                $(document).ready(function () {
                    $("#userRoleViewForm").show();
                    $("#addDuplicateUserRoleform").hide();
                });
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



                                <c:if test="${operationtype=='add'}" >
                                    <form method="POST" action="" name="adduserroleform">

                                        <table>
                                            <tr>

                                                <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                                <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                                <td></td>
                                            </tr>
                                        </table>

                                        <table border="0">

                                            <tbody>

                                                <tr>
                                                    <td style="width: 180px">User Role Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text"  value="${userRoleBean.userRoleCode}" name="userrolecode" maxlength="16"></td>
                                                    <td></td>
                                                </tr>
                                                <tr><td style="height: 5px"></td></tr>     
                                                <tr>
                                                    <td style="width: 180px">Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text"  name="userroledescription" value="${userRoleBean.description}" maxlength="64"></td>
                                                    <td></td>
                                                </tr>
                                                <tr><td style="height: 5px"></td></tr>     
                                                <tr>
                                                    <td style="width: 180px">User Level </td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="userlevelcode" id="userlevelcode">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="userLevel" items="${userLevelList}">
                                                                <c:if test="${userRoleBean.userLevelID==userLevel.userLevelID}">
                                                                    <option value="${userLevel.userLevelID}" selected>${userLevel.description}</option>
                                                                </c:if>
                                                                <c:if test="${userRoleBean.userLevelID!=userLevel.userLevelID}">
                                                                    <option value="${userLevel.userLevelID}" >${userLevel.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr><td style="height: 5px"></td></tr>     
                                                <tr>
                                                    <td style="width: 180px">Status </td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="statuscode" id="statuscode">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">
                                                                <c:if test="${userRoleBean.statusCode==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${userRoleBean.statusCode!=status.statusCode}">
                                                                    <option value="${status.statusCode}" >${status.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr> <td style="height:12px;"></td></tr>
                                                <tr>
                                                    <td></td>
                                                    <td style="width: 300px">&nbsp;&nbsp;<input type="submit" value="Add" name="add" style="width: 100px" onclick="invokeAdd()">
                                                        <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeReset()"></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.USERROLE%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" title="History View"/></a></td>
                                                </tr>


                                            </tbody>
                                        </table>
                                    </form>

                                </c:if>

                                <%-- -------------------------add form end -------------------------------- --%>



                                <%-- -------------------------view form start -------------------------------- --%>
                                <c:if test="${operationtype=='view'}" >
                                    <form id="userRoleViewForm" action="" method="POST" name="viewuserRoleform">

                                        <table>
                                            <tr>
                                                <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                                <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                                <td></td>
                                            </tr>
                                        </table>

                                        <table border="0">

                                            <tr>
                                                <td style="width: 180px">User Role Code</td>
                                                <td><font>:</font>&nbsp;${userRoleBean.userRoleCode}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>     
                                            <tr>
                                                <td style="width: 180px">Description</td>
                                                <td><font>:</font>&nbsp;${userRoleBean.description}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>     
                                            <tr>
                                                <td style="width: 180px">Level </td>
                                                <td><font>:</font>&nbsp;${userRoleBean.userLevelDes}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>     
                                            <tr>
                                                <td style="width: 180px">Status </td>
                                                <td><font>:</font>&nbsp;${userRoleBean.statusDes}</td>
                                            </tr>

                                            <tr> <td style="height:12px;"></td></tr>
                                            <tr>
                                                <td></td>
                                                <td >
                                                    <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeReset()">
                                                </td>
                                                <td style="width: 300px">
                                                    <input type="button" value="Duplicate" id="btnDuplicate" name="btnDuplicate" style="width:100px" onclick="createDuplicateRole()"/>
                                                </td>
                                                <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.USERROLE%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                            </tr>
                                        </table>

                                    </form>
                                    <form id="addDuplicateUserRoleform" name="addDuplicateUserRoleform" method="POST" style="display: none" >
                                        <table>
                                            <tr>

                                                <td><b><font color="#FF0000" id="dplErrorMsg"> ${errorMsg}</font></b> </td>
                                                <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                                <td></td>
                                            </tr>
                                        </table>
                                        <table border="0">

                                            <tbody>

                                                <tr>
                                                    <td style="width: 180px">User Role Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text"  value="${userRoleBean.userRoleCode}" name="userrolecode" id="dpluserrolecode" maxlength="16"></td>
                                                    <td></td>
                                                </tr>
                                                <tr><td style="height: 5px"></td></tr>     
                                                <tr>
                                                    <td style="width: 180px">Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text"  name="userroledescription" id="dpluserroledescription" value="${userRoleBean.description}" maxlength="64"></td>
                                                    <td></td>
                                                </tr>
                                                <tr><td style="height: 5px"></td></tr>     
                                                <tr>
                                                    <td style="width: 180px">User Level </td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="userlevelcode" id="userlevelcode">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="userLevel" items="${userLevelList}">
                                                                <c:if test="${userRoleBean.userLevelID==userLevel.userLevelID}">
                                                                    <option value="${userLevel.userLevelID}" selected>${userLevel.description}</option>
                                                                </c:if>
                                                                <c:if test="${userRoleBean.userLevelID!=userLevel.userLevelID}">
                                                                    <option value="${userLevel.userLevelID}" >${userLevel.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr><td style="height: 5px"></td></tr>     
                                                <tr>
                                                    <td style="width: 180px">Status </td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="statuscode" id="dplstatuscode">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">
                                                                <c:if test="${userRoleBean.statusCode==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${userRoleBean.statusCode!=status.statusCode}">
                                                                    <option value="${status.statusCode}" >${status.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr> <td style="height:12px;"></td></tr>
                                                <tr>
                                                    <td></td>
                                                    <td style="width: 300px">&nbsp;&nbsp;<input type="button" value="Save" name="add" id="add" style="width: 100px" onclick="invokeAddDuplicateUserRole()">
                                                        <input type="button" value="Back" name="back" style="width: 100px" onclick="viewUserRole()">
                                                    </td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.USERROLE%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" title="History View"/></a></td>
                                                </tr>


                                            </tbody>
                                        </table>
                                    </form>
                                    <form name="" id="succForm" action="${pageContext.request.contextPath}/LoadUserRoleMgtServlet">
                                        <input type="text" name="dplsuccessMsg" id="succMes"></input>
                                    </form>
                                </c:if>

                                <%-- -------------------------view form end -------------------------------- --%>






                                <%-- -------------------------update form start -------------------------------- --%>

                                <c:if test="${operationtype=='update'}" >
                                    <form method="POST" action="" name="updateuserRoleform">

                                        <table>
                                            <tr>
                                                <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                                <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                                <td></td>
                                            </tr>
                                        </table>
                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td style="width: 180px" >User Role Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" readonly  value="${userRoleBean.userRoleCode}" name="userrolecode" maxlength="16"></td>
                                                    <td></td>
                                                </tr>
                                                <tr><td style="height: 5px"></td></tr>     
                                                <tr>
                                                    <td style="width: 180px">Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text"  name="userroledescription" value="${userRoleBean.description}" maxlength="64"></td>
                                                    <td></td>
                                                </tr>
                                                <tr><td style="height: 5px"></td></tr>     
                                                <tr>
                                                    <td style="width: 180px">User Level </td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="userlevelcode" id="userlevelcode">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="userLevel" items="${userLevelList}">
                                                                <c:if test="${userRoleBean.userLevelID==userLevel.userLevelID}">
                                                                    <option value="${userLevel.userLevelID}" selected>${userLevel.description}</option>
                                                                </c:if>
                                                                <c:if test="${userRoleBean.userLevelID!=userLevel.userLevelID}">
                                                                    <option value="${userLevel.userLevelID}" >${userLevel.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr><td style="height: 5px"></td></tr>     
                                                <tr>
                                                    <td style="width: 180px">Status </td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select name="statuscode">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">
                                                                <c:if test="${userRoleBean.statusCode==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${userRoleBean.statusCode!=status.statusCode}">
                                                                    <option value="${status.statusCode}" >${status.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td><input type="hidden" name="oldvalue" value="${oldval}" /></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr> <td style="height:12px;"></td></tr>
                                                <tr>
                                                    <td></td>
                                                    <td style="width: 300px">&nbsp;&nbsp;<input type="submit" value="Update" name="update" onclick="invokeUpdate()" style="width: 100px">
                                                        <input type="button" value="Reset" name="reset" onclick="updateReset('${userRoleBean.userRoleCode}')" style="width: 100px"></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.USERROLE%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>


                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                </c:if>
                                <%-- -------------------------update form end -------------------------------- --%>





                                <table  border="1"  class="display" id="tableview">
                                    <thead>
                                        <tr>
                                            <th>User Role Code</th>
                                            <th>Description</th>
                                            <th>Status</th>

                                            <th>View</th>
                                            <th>Update</th>
                                            <th >Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody >
                                        <c:forEach var="userrole" items="${sessionScope.SessionObject.userRoleList}">
                                            <tr>
                                                <td >${userrole.userRoleCode}</td>
                                                <td >${userrole.description}</td>
                                                <td >${userrole.statusDes}</td>


                                                <td  ><a href='${pageContext.request.contextPath}/ViewUserRoleServlet?userRoleCode=<c:out value="${userrole.userRoleCode}"></c:out>'>View</a></td>
                                                <td  ><a href='${pageContext.request.contextPath}/UpdateUserRoleServlet?userRoleCode=<c:out value="${userrole.userRoleCode}"></c:out>'>Update</a></td>
                                                <td ><a  href='#' onclick="ConfirmDelete('${userrole.userRoleCode}')">Delete</a></td>

                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <!--confirmation dialog -->
                                <div id="dialog-confirm" title="Delete Confirmation"></div>
                                <!--   ------------------------- end developer area  --------------------------------                      -->
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

