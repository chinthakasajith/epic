<%-- 
    Document   : customertemplateeditpage
    Created on : Jan 31, 2012, 2:56:28 PM
    Author     : janaka_h
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         
        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
       <!--        include content.jsp for all js and css inclusion-->


        <title>CUSTOMER TEMPLATE MANAGEMENT</title>
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
                <jsp:include page="/subheader.jsp"/>



                <div class="content" >

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/></td>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->
                                <table class="tit"> <tr> <td   class="center"><b>  SYSTEM USER MANAGEMENT </b></td> </tr><tr> <td>&nbsp;</td> </tr></table>

                                <table>
                                    <tr>
                                        <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                        <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                    </tr>
                                </table>
                                <!--   ------------------------- start operation edit area  --------------------------------                      -->
                                <c:if test="${operationtype=='edit'}">
                                    <form name="editform" method="post" action="<%=request.getContextPath()%>/UpdateServlet">
                                        <table cellpadding="0" cellspacing="10"   >
                                            <tr>
                                                <td style="width: 200px;"><b><u>Credential Details</u></b></td>
                                                <td></td>

                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Username</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text" name="username" readonly="" maxlength="16" value="${userBean.userName}"/></td>

                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">User Role</td>                                               
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select name="userroal">
                                                        <option value="">--Select User Role-- </option>
                                                        <c:if test="${userBean.userRoleCode != null}">
                                                            <c:forEach var="userRoleLst" items="${userRoleLst}">
                                                                <c:if test="${userBean.userRoleCode == userRoleLst.userRoleCode}">
                                                                    <option value="${userRoleLst.userRoleCode}" selected="true">${userRoleLst.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </c:if> 
                                                        <c:forEach var="userRoleLst" items="${userRoleLst}">
                                                            <c:if test="${userBean.userRoleCode != userRoleLst.userRoleCode}">
                                                                <option value="${userRoleLst.userRoleCode}">${userRoleLst.description}</option>
                                                            </c:if>
                                                        </c:forEach>

                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Status</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select name="status">
                                                        <option value="">--Select Status-- </option>
                                                        <c:if test="${userBean.statusCode != null}">
                                                            <c:forEach var="statusLst" items="${statusLst}">
                                                                <c:if test="${userBean.statusCode == statusLst.statusCode}">
                                                                    <option value="${statusLst.statusCode}" selected="true">${statusLst.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </c:if> 
                                                        <c:forEach var="statusLst" items="${statusLst}">
                                                            <c:if test="${userBean.statusCode != statusLst.statusCode}">
                                                                <option value="${statusLst.statusCode}">${statusLst.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select></td>

                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Dual Auth User</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select name="dualUserroal">
                                                        <option value="">--Select User Role-- </option>
                                                        <c:if test="${userBean.dualUserRoleCode != null}">
                                                            <c:forEach var="userRoleLst" items="${userRoleLst}">
                                                                <c:if test="${userBean.dualUserRoleCode == userRoleLst.userRoleCode}">
                                                                    <option value="${userRoleLst.userRoleCode}" selected="true">${userRoleLst.description}</option>
                                                                </c:if>
                                                            </c:forEach>

                                                        </c:if> 
                                                        <c:forEach var="userRoleLst" items="${userRoleLst}">
                                                            <c:if test="${userBean.dualUserRoleCode != userRoleLst.userRoleCode}">
                                                                <option value="${userRoleLst.userRoleCode}">${userRoleLst.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select></td>

                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">&nbsp;</td>
                                                <td></td>

                                            </tr>
                                            <tr>
                                                <td style="width: 200px;"><b><u>Personal Details</u></b></td>
                                                <td></td>
                                            </tr>                                            
                                            <tr>
                                                <td style="width: 200px;">Initials</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text" name="initials"  maxlength="16" value="${userBean.initials}"/></td>

                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">First Name</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text" name="firstname"  maxlength="32" value="${userBean.firstName}"/></td>

                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Last Name</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text" name="lastname"  maxlength="32" value="${userBean.lastName}"/></td>

                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Bank Name </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text" name="bankname"  maxlength="64" value="${userBean.bankname}"/></td>

                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Branch Name</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text" name="branchname"  maxlength="64" value="${userBean.branchname}"/></td>

                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Designation</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text" name="designation"  maxlength="256" value="${userBean.designation}"/></td>

                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Service Id</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text" name="serviseid"  maxlength="100" value="${userBean.serviseid}"/></td>

                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Contact Number</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text" name="telephone"  maxlength="16" value="${userBean.telNo}"/></td>

                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">NIC</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text" name="nic"  maxlength="10" value="${userBean.nic}"/></td>

                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Email Address</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text" name="email"  maxlength="100" value="${userBean.email}"/></td>

                                            </tr>
                                            
                                           
                                            <tr>
                                                <td style="width: 200px;">Date Of Birth</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input  name="birthday" maxlength="16" readonly class="inputfeild" value="${userBean.birthday}" key="birthday" id="birthday"  />
                                                    <script type="text/javascript">
                                                            $(function() {
                                                                $( "#birthday" ).datepicker({
                                                                    showOn: "button",
                                                                    buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                    changeMonth: true,
                                                                    changeYear: true,
                                                                    buttonImageOnly: true,
                                                                    dateFormat: "yy-mm-dd" ,
                                                                    showWeek: true,
                                                                    firstDay: 1
                                                                });
                                                            });
                                                    </script>

                                                </td>
                                                <td>&nbsp;</td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Expiry Date</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input  name="expdate" maxlength="16" readonly class="inputfeild" value="${userBean.expiryDateToString}" key="expdate" id="expdate"  />
                                                    <script type="text/javascript">
                                                            $(function() {
                                                                $( "#expdate" ).datepicker({
                                                                    showOn: "button",
                                                                    buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                    changeMonth: true,
                                                                    changeYear: true,
                                                                    buttonImageOnly: true,
                                                                    dateFormat: "yy-mm-dd" ,
                                                                    showWeek: true,
                                                                    firstDay: 1
                                                                });
                                                            });
                                                    </script>
                                                </td>
                                                <td>&nbsp;</td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;"><p>&nbsp;</p></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;"></td>
                                                <td>&nbsp;&nbsp;
                                                    <input type="submit" name="next" value="Save" style="width: 100px;"/>&nbsp;<input type="button" name="next" onclick="invokeReset('edit')" value="Reset" style="width: 100px;"/>&nbsp;<input type="button" name="cancel" value="Cancel" onclick="invokeCancel()"  style="width: 100px;"/></td>
                                            </tr>
                                        </table>

                                    </form>
                                </c:if>


                                <!--   ------------------------- end operation edit  area  --------------------------------                      -->

                               


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
