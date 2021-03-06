<%-- 
    Document   : controlpanelhome
    Created on : Jan 10, 2012, 5:13:40 PM
    Author     : janaka_h
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>

<!DOCTYPE html>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->


<!--        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/default.css" media="screen"/>

        <style type="text/css" title="currentStyle">
            @import "${pageContext.request.contextPath}/resources/css/demo_page.css";
            @import "${pageContext.request.contextPath}/resources/css/demo_table.css";
            @import "${pageContext.request.contextPath}/resources/css/demo_table_jui.css";
        </style>

        <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
        <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/resources/js/jquery.dataTables.js"></script>-->
        <script type="text/javascript">

            function update(value) {
                window.location = "${pageContext.request.contextPath}/UpdatePasswordPolicyViewServlet?id=" + value;
            }
            function resetToback() {
                window.location = "${pageContext.request.contextPath}/LoadPasswordPolicyServlet";
            }
//            function resetTobackUp(){
//                window.location = "${pageContext.request.contextPath}/UpdatePasswordPolicyViewServlet";
//            }

        </script>
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.PASSWORDPOLICY%>'
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
                                
                                            <font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                     
                                <c:if test="${operationtype=='add'}" >

                                    <form method="POST">

                                        <table border="0">
                                            <tbody>
                                                <tr>
                                                    <td>Minimum Length</td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="minLength" value="" /></td>
                                                    <td>Allowed Repeat Characters</td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="" value="" /></td>
                                                </tr>
                                                <tr>

                                                </tr>
                                                <tr>
                                                    <td>Maximum Length</td>
                                                    <td></td>
                                                    <td><input type="text" name="maxLength" value="" /></td>
                                                    <td>Password Expiry Period</td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="" value="" /></td>
                                                </tr>
                                                <tr>

                                                </tr>
                                                <tr>
                                                    <td>Minimum Special Characters</td>
                                                    <td></td>
                                                    <td><input type="text" name="minSplCh" value="" /></td>
                                                    <td>Password Expiry Notification Period</td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="" value="" /></td>
                                                </tr>
                                                <tr>

                                                </tr>
                                                <tr>
                                                    <td>Minimum UpperCase Characters</td>
                                                    <td></td>
                                                    <td><input type="text" name="minUpcaseCh" value="" /></td>
                                                    <td>No. of History Passwords</td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="" value="" /></td>
                                                </tr>
                                                <tr>

                                                </tr>
                                                <tr>
                                                    <td>Minimum Numerical Characters</td>
                                                    <td></td>
                                                    <td><input type="text" name="minNuCh" value="" /></td>
                                                    <td>Idle Account Expiry Period</td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="" value="" /></td>
                                                </tr>
                                                <tr>

                                                </tr>
                                                <tr>
                                                    <td>Minimum LowerCase Characters</td>
                                                    <td></td>
                                                    <td><input type="text" name="minLwCh" value="" /></td>
                                                    <td>No. of Invalid Login Attempts</td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="" value="" /></td>
                                                </tr>
                                                <tr>

                                                </tr>
                                                <tr>
                                                    <td>First Password Expiry Period</td>
                                                    <td></td>
                                                    <td><input type="text" name="firstPassExpiryPeriod" value="" /></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>

                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"><input type="reset" value="Reset" style="width: 100px"/></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.PASSWORDPOLICY%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                                </tr>
                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>
                                <c:if test="${operationtype=='edit'}" >

                                    <form method="POST" action="${pageContext.request.contextPath}/UpdatePasswordPolicyServlet">

                                        <table border="0">
                                            <tbody>
                                                <tr>
                                                    <td>Minimum Length</td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="minLength" value="${passwdBean.minLen}" /></td>
                                                    <td>Allowed Repeat Characters</td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="allowedReptCharacters" value="${passwdBean.allowedReptCharacters}" /></td>
                                                </tr>
                                                <tr>
                                                    <td><input type="hidden" name="id" value="${passwdBean.policyID}"  /></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Maximum Length</td>
                                                    <td></td>
                                                    <td><input type="text" name="maxLength" value="${passwdBean.maxLen}" /></td>
                                                    <td>Password Expiry Period</td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="passwordExpiryPeriod" value="${passwdBean.passwordExpiryPeriod}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Minimum Special Characters</td>
                                                    <td></td>
                                                    <td><input type="text" name="minSplCh" value="${passwdBean.minSpclCharacterLen}" /> e.g $,%,@,!,#</td>
                                                    <td>Password Expiry Notification Period</td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="passwordExpNotifyPeriod" value="${passwdBean.passwordExpNotifyPeriod}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Minimum UpperCase Characters</td>
                                                    <td></td>
                                                    <td><input type="text" name="minUpcaseCh" value="${passwdBean.minUpperCase}" /></td>
                                                    <td>No. of History Passwords</td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="noOfHistoryPasswords" value="${passwdBean.noOfHistoryPasswords}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Minimum Numerical Characters</td>
                                                    <td></td>
                                                    <td><input type="text" name="minNuCh" value="${passwdBean.minNumCharacter}" /></td>
                                                    <td>Idle Account Expiry Period</td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="accountExpiryPeriod" value="${passwdBean.accountExpiryPeriod}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Minimum LowerCase Characters</td>
                                                    <td></td>
                                                    <td><input type="text" name="minLwCh" value="${passwdBean.minLowerCaseCharacter}" /></td>
                                                    <td>No. of Invalid Login Attempts</td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="noOfInvalidLoginAtmps" value="${passwdBean.minLowerCaseCharacter}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>First Password Expiry Period</td>
                                                    <td></td>
                                                    <td><input type="text" name="firstPassExpiryPeriod" value="${passwdBean.firstPassExpiryPeriod}" /></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"> <input type="submit" value="Update" name="Update" style="width: 100px"/><input onclick="resetToback()" type="reset" value="Reset" style="width: 100px"/></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.PASSWORDPOLICY%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                                </tr>
                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>

                                <c:if test="${operationtype=='update'}" >

                                    <form method="POST" action="${pageContext.request.contextPath}/UpdatePasswordPolicyServlet">

                                        <table border="0">
                                            <tbody>
                                                <tr>
                                                    <td>Minimum Length</td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="minLength" value="${passwd.minLen}" /></td>
                                                    <td>Allowed Repeat Characters</td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="allowedReptCharacters" value="${passwd.allowedReptCharacters}" /></td>
                                                </tr>
                                                <tr>
                                                    <td><input type="hidden" name="id" value="${passwd.policyID}"  /></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Maximum Length</td>
                                                    <td></td>
                                                    <td><input type="text" name="maxLength" value="${passwd.maxLen}" /></td>
                                                    <td>Password Expiry Period</td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="passwordExpiryPeriod" value="${passwd.passwordExpiryPeriod}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Minimum Special Characters</td>
                                                    <td></td>
                                                    <td><input type="text" name="minSplCh" value="${passwd.minSpclCharacterLen}" /> e.g $,%,@,!,#</td>
                                                    <td>Password Expiry Notification Period</td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="passwordExpNotifyPeriod" value="${passwd.passwordExpNotifyPeriod}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Minimum UpperCase Characters</td>
                                                    <td></td>
                                                    <td><input type="text" name="minUpcaseCh" value="${passwd.minUpperCase}" /></td>
                                                    <td>No. of History Passwords</td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="noOfHistoryPasswords" value="${passwd.noOfHistoryPasswords}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Minimum Numerical Characters</td>
                                                    <td></td>
                                                    <td><input type="text" name="minNuCh" value="${passwd.minNumCharacter}" /></td>
                                                    <td>Idle Account Expiry Period</td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="accountExpiryPeriod" value="${passwd.accountExpiryPeriod}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Minimum LowerCase Characters</td>
                                                    <td></td>
                                                    <td><input type="text" name="minLwCh" value="${passwd.minLowerCaseCharacter}" /></td>
                                                    <td>No. of Invalid Login Attempts</td>
                                                    <td>&nbsp;</td>
                                                    <td><input type="text" name="noOfInvalidLoginAtmps" value="${passwd.noOfInvalidLoginAtmps}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>First Password Expiry Period</td>
                                                    <td></td>
                                                    <td><input type="text" name="firstPassExpiryPeriod" value="${passwdBean.firstPassExpiryPeriod}" /></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"><input type="submit" value="Update" name="Update" style="width: 100px"/><input onclick="resetToback()" type="reset" value="Reset" style="width: 100px"/></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.PASSWORDPOLICY%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                                </tr>
                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>

                                &nbsp;
                                &nbsp;
                                &nbsp;
                                &nbsp;
                                &nbsp;

                                <table border="1" class="display" id="scoreltableview5">
                                    <thead>
                                        <tr>

                                            <th>Minimum Length</th>
                                            <th>Maximum Length</th>
                                            <th>Minimum Special Characters</th>
                                            <th>Minimum UpperCase Characters</th>
                                            <th>Minimum Numerical Characters</th>
                                            <th>Minimum LowerCase Characters</th>                                           
                                            <th>Allowed Repeat Characters</th>
                                            <th>Password Expiry Period</th>
                                            <th>Password Expiry Notification Period</th>
                                            <th>No. of History Passwords</th>
                                            <th>Idle Account Expiry Period</th>
                                            <th>No. of Invalid Login Attempts</th>
                                            <th>First Password Expiry Period</th>

                                            <th>Update</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>

                                            <td>${passwdBean.minLen}</td>
                                            <td>${passwdBean.maxLen}</td>
                                            <td>${passwdBean.minSpclCharacterLen}</td>
                                            <td>${passwdBean.minUpperCase}</td>
                                            <td>${passwdBean.minNumCharacter}</td>
                                            <td>${passwdBean.minLowerCaseCharacter}</td>                                          
                                            <td>${passwdBean.allowedReptCharacters}</td>
                                            <td>${passwdBean.passwordExpiryPeriod}</td>
                                            <td>${passwdBean.passwordExpNotifyPeriod}</td>
                                            <td>${passwdBean.noOfHistoryPasswords}</td>
                                            <td>${passwdBean.accountExpiryPeriod}</td>
                                            <td>${passwdBean.noOfInvalidLoginAtmps}</td>
                                            <td>${passwdBean.firstPassExpiryPeriod}</td>

                                            <td><a  href='#'   onclick="update('${passwdBean.policyID}')">Update</a></td>
                                        </tr>
                                    </tbody>
                                </table>



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
