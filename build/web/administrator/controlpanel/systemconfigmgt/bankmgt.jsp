<%-- 
    Document   : bankmgt
    Created on : Apr 17, 2012, 12:28:29 PM
    Author     : badrika
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

        <script type="text/javascript">

            function BackBankForm() {
                window.location = "${pageContext.request.contextPath}/LoadBankMgtServlet";
            }
            function resetBankForm() {
                window.location = "${pageContext.request.contextPath}/LoadBankMgtServlet";
            }


            function viewBank(value) {
                window.location = "${pageContext.request.contextPath}/ViewBankMgtServlet?id=" + value;
            }
            function updateBank(value) {
                document.viewTableForm.action = "${pageContext.request.contextPath}/UpdateBankMgtLoadServlet";
                document.getElementById('id').value = value;
                document.viewTableForm.submit();
                // window.location = "${pageContext.request.contextPath}/UpdateBankMgtLoadServlet?id="+value;
            }
            function deleteBank(value) {

//                answer = confirm("Do you really want to delete "+value+" Bank?")
//                if (answer !=0)
//                {
//                    window.location="${pageContext.request.contextPath}/DeleteBankMgtServlet?id="+value;
//                }
//                else
//                {
//                    window.location="${pageContext.request.contextPath}/LoadBankMgtServlet";
//                }
                $("#dialog-confirm").html("<p>Do you really want to delete " + value + " Bank?</p>");
                $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 'auto',
                    width: 500,
                    modal: true,
                    buttons: {
                        "No": function () {
                            window.location = "${pageContext.request.contextPath}/LoadBankMgtServlet";
                        },
                        "Yes": function () {
                            window.location = "${pageContext.request.contextPath}/DeleteBankMgtServlet?id="+value;
                        }
                    }
                });
            }
        </script>
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.BANK%>'
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

    </head>
    <body>
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container" >

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main" >
                <jsp:include page="/subheader.jsp"/>




                <div class="content" >

                    <td class="menubar"><jsp:include page="/leftmenu.jsp"/></td>

                </div>


                <div id="content1" >

                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------    -->
                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>
                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>

                                <c:if test="${operationtype=='add'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/AddBankMgtServlet">
                                        <table border="0">


                                            <tbody>
                                                <tr>
                                                    <td style="width: 100px;">Bank Code </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" maxlength="6" name="bankCode" value="${bankBean.bankCode}" class="deftext"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 100px;">Bank Name</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" maxlength="60" name="bankName" value="${bankBean.bankName}" class="deftext"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 100px;">Status </td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select  name="statuscode"  class="inputfield-mandatory" style="width: 168px;">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${bankBean.statusDes==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${bankBean.statusDes!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>

                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"> 
                                                        <input type="submit" value="Add" name="Add" class="defbutton"/>
                                                        <input onclick="resetBankForm()" type="reset" value="Reset" class="defbutton"/>
                                                    </td>

                                                </tr>

                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>


                                <c:if test="${operationtype=='update'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/UpdateBankMgtServlet">
                                        <table border="0">


                                            <tbody>
                                                <tr>
                                                    <td style="width: 100px;">Bank Code </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="bankCode" readonly="true" value="${bankBean.bankCode}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 100px;">Bank Name</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text" maxlength="60" name="bankName" value="${bankBean.bankName}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 100px;">Status </td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select  name="statuscode"  class="inputfield-mandatory" style="width: 168px;">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${bankBean.statusDes==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${bankBean.statusDes!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>

                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td><input type="hidden" name="oldvalue" value="${oldval}" /></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>                                           

                                            </tbody>
                                        </table>


                                        <table border="0">
                                            <tr>
                                                <td style="width: 113px;"></td>
                                                <td><input type="submit" value="Update" name="Update" class="defbutton"/></td>
                                                <td><input onclick="updateBank('${bankBean.bankCode}')" type="reset" value="Reset" class="defbutton"/></td>
                                                <td><input onclick="BackBankForm()" type="reset" value="Back" class="defbutton"/></td>
                                            </tr>
                                        </table>

                                    </form>
                                </c:if>


                                <c:if test="${operationtype=='view'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/ViewBankMgtServlet">
                                        <table border="0">


                                            <tbody>
                                                <tr>
                                                    <td>Bank Code </td>
                                                    <td></td>
                                                    <td style="width: 400px;">:${bankBean.bankCode}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Bank Name</td>
                                                    <td></td>
                                                    <td>:${bankBean.bankName}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Status </td>
                                                    <td></td>                                         
                                                    <td>:${bankBean.status}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>



                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;">
                                                        <input type="button" value="Back" name="Back" class="defbutton" onclick="BackBankForm()"/>
                                                    </td>

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
                                </c:if>


                                <!-- show table data -->
                                <br/>
                                <form name="viewTableForm" id="viewTableForm" method="post">
                                    <table border="1" class="display" id="scoreltableview2">
                                        <thead>
                                            <tr>
                                                <th>Bank Code</th>
                                                <th>Bank Name</th>
                                                <th>Status</th>
                                                <th>Last Updated Time</th>
                                                <th>View</th>
                                                <th>Update</th>              
                                                <th>Delete</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  items="${banklist}" var="bankBean">
                                                <tr>
                                                    <td>${bankBean.bankCode}</td>
                                                    <td>${bankBean.bankName}</td>
                                                    <td>${bankBean.statusDes}</td>
                                                    <td>${bankBean.lastUpdatedDate}</td>
                                                    <td><a  href='#' onclick="viewBank('${bankBean.bankCode}')">View</a></td>
                                                    <td><a  href='#' onclick="updateBank('${bankBean.bankCode}')">Update</a></td>
                                                    <td><a  href='#' onclick="deleteBank('${bankBean.bankCode}')">Delete</a></td>

                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                        <input type="hidden" id="id"  name="id" maxlength="16" />
                                    </table>

                                </form>




                                <!--   ------------------------- end developer area  --------------------------------  -->

                            </div>
                        </div>
                    </div>
                </div>
                <div class="clearer"><span></span></div>
            </div>

        </div>
                                            <!--confirmation dialog -->
        <div id="dialog-confirm" title="Delete Confirmation">

        </div>
        <div class="footer"><jsp:include page="/footer.jsp"/></div>
    </body>
</html>

