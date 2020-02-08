<%-- 
    Document   : changeuserpassword
    Created on : May 17, 2016, 9:44:02 AM
    Author     : prageeth_s
--%>
<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page import="com.epic.cms.system.util.variable.StatusVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html>
    <head>

        <style type="text/css">
            form.inset {border-style:inset; width: 510px; color: #0063DC;}
        </style>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/content.jsp"/>
        <title>EPIC_CMS_HOME</title>

        <script  type="text/javascript" charset="utf-8">


        </script>  
        <script>


            $(document).ready(function() {
            <%--var oTable = $('#example').dataTable();--%>
                var oTable = $('#example').dataTable({
                    "bJQueryUI": true,
                    "sPaginationType": "full_numbers"
                });
            });


            function invokeCancel() {

                window.location = "${pageContext.request.contextPath}/LoadSystemUserServlet";

            }

            function invokeUpdate()
            {

                document.updateMerchant.action = "${pageContext.request.contextPath}/UpdateConfirmedMerchantCategoryServlet";
                document.updateMerchant.submit();

            }

            function invokeLoadUpdate(mcc) {
                document.updateMerchant.action = "${pageContext.request.contextPath}/UpdateMerchantCategoryServlet?id=" + mcc;
                document.updateMerchant.submit();

            }






        </script>
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.SYSTEMUSER%>'
                        },
                function(data) {

                    if (data != '') {
                        $('.center').text(data)
                        var heading = data.split('→');
                        $('.heading').text(heading[1]);

                    }

                });

            }

        </script>  

    </head>
    <body>

        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
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

                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>

                                    <!--/////////////////////////////////////////////End Default view  ///////////////////////////////////////////////////////////-->          

                                    <!--/////////////////////////////////////////////Start View records  ///////////////////////////////////////////////////////////-->

                                <%
                                    pageContext.setAttribute("statusVar", StatusVarList.PASSWORD_RESET_REQUEST_SENT);
                                %>
                                <form method="POST" id="form" name="form" action="${pageContext.request.contextPath}/UpdateChangedUserPasswordServlet">
                                    <table border="0" cellspacing="10" cellpadding="0">

                                        <tbody>
                                            <tr>
                                                <td colspan="3">Note: you can request a password change here !</td>
                                            </tr>
                                            <tr></tr>
                                            <tr>
                                                <td>User Name</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;:&nbsp;
                                                    ${userBean.userName}
                                                    <input type="hidden" name="userName" value="${userBean.userName}">
                                                </td>
                                                <td style="width: 300px;" > </td>
                                            </tr>

                                            <tr>
                                                <td>User Role</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;:&nbsp;
                                                    ${userBean.userRoleDes} 
                                                    <input type="hidden" name="userRole" value="${userBean.userRoleDes}">
                                                    <input type="hidden" name="userRoleCode" value="${userBean.userRoleCode}">
                                                    <input type="hidden" name="dualUserRoleCode" value="${userBean.dualUserRoleCode}">
                                                    <input type="hidden" name="isEmailSent" value="${userBean.isEmailSent}">
                                                    <input type="hidden" name="status" value="${userBean.statusCode}">
                                                    <input type="hidden" name="email" value="${userBean.email}">
                                                </td>
                                                <td style="width: 300px;" > </td>
                                            </tr>
                                            <!--                                            <tr>
                                                                                            <td>New Password </td>
                                                                                            <td>&nbsp;</td>
                                                                                            <td><font style="color: red;">*</font>&nbsp;
                                                                                                <input type="password" name="userPassword" value="${userBean.password}" maxlength="32" /></td>
                                                                                            <td style="width: 300px;" > <label style="color: red">${invalidMsgBean.password} </label></td>
                                                                                        </tr>
                                            <c:if test="${invalidMsgBean.psWdMatch != null}">
                                                <tr>
                                                    <td></td>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;&nbsp;&nbsp;
                                                        <label style="color: red"  name="match" >${invalidMsgBean.psWdMatch}</label>    
                                                    </td>
                                                </tr>

                                            </c:if>                                         

                                            <tr>
                                                <td>Confirm Password </td>
                                                <td>&nbsp;</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="password" name="userCnformedPassword" value="${userBean.confirmPassword}" maxlength="32" /></td>
                                                <td style="width: 300px;" > <label style="color: red">${invalidMsgBean.confirmPassword} </label></td>
                                            </tr>-->
                                            <c:if test="${userBean.statusCode == statusVar}">
                                                <tr>
                                                    <td>Reject Remark </td>
                                                    <td>&nbsp;</td>
                                                    <td>:&nbsp;
                                                        <input type="text" name="rejectRemark" value="${userBean.rejectRemark}" /></td>
                                                    <td style="width: 300px;" > <label style="color: red">${invalidMsgBean.rejectRemark} </label></td>
                                                </tr>

                                            </c:if>
                                            <tr></tr>
                                            <tr>
                                                <td></td>
                                                <td></td>

                                                <td>&nbsp;&nbsp;
                                                    <c:if test="${userBean.statusCode != statusVar}">
                                                        <input type="submit" name="next" value="Send Change Password Request" class="buttonsarea" style="min-width: 240px;"/>&nbsp;<input type="button" name="cancel" value="Cancel" onClick="invokeCancel()" class="buttonsarea" />
                                                    </c:if>    
                                                    <c:if test="${userBean.statusCode == statusVar}">
                                                        <input type="submit" name="accept" value="<%=StatusVarList.DA_REQUEST_APPROVE_DES%>" class="buttonsarea"/>&nbsp;
                                                        <input type="submit" name="accept" value="<%=StatusVarList.DA_REQUEST_REJECT_DES%>" class="buttonsarea"/>
                                                    </c:if>
                                                </td>
                                                <td></td>

                                            </tr>

                                        </tbody>
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
        <div class="footer"><jsp:include page="/footer.jsp"/></div>
    </body>
</html>


