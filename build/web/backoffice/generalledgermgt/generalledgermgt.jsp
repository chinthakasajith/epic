<%-- 
    Document   : generalledgermgt
    Created on : Nov 9, 2012, 4:39:40 PM
    Author     : badrika
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>

<!DOCTYPE html>
<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->
        <script type="text/javascript">

            function resetglForm() {
                window.location = "${pageContext.request.contextPath}/LoadGeneralLedgerMgtServlet";
            }

            function invokeUpdate(value, val2) {
                window.location = "${pageContext.request.contextPath}/UpdateGeneralLedgerMgtLoadServlet?accNo=" + value + "&upov=" + val2;
            }
            function invokeDelete(value, val2) {

//                answer = confirm("Do you really want to delete " + value + " GL Account?")
//                if (answer != 0)
//                {
//                    window.location = "${pageContext.request.contextPath}/UpdateGeneralLedgerMgtLoadServlet?accNo=" + value + "&upov=" + val2;
//                }
//                else
//                {
//                    window.location = "${pageContext.request.contextPath}/LoadGeneralLedgerMgtServlet";
//                }
                $("#dialog-confirm").html("<p>Do you really want to delete " + value + " GL Account?</p>");
                $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 'auto',
                    width: 500,
                    modal: true,
                    buttons: {
                        "No": function () {
                            window.location = "${pageContext.request.contextPath}/LoadGeneralLedgerMgtServlet";
                        },
                        "Yes": function () {
                            window.location = "${pageContext.request.contextPath}/UpdateGeneralLedgerMgtLoadServlet?accNo=" + value + "&upov=" + val2;
                        }
                    }
                });

            }

        </script>

        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.GL_MANAGER%>'
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

                    <jsp:include page="/leftmenu.jsp"/>

                </div>


                <div id="content1" >
                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->
                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <br/>
                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>

                                <c:if test="${operationtype=='add'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/AddGeneralLedgerMgtServlet" name="addglaccountform">


                                        <table border="0">
                                            <tr>
                                                <td>GL Account Number</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td><input type="text" name="glaccno" value="${glbean.glAccNo}" maxlength="20"/></td>
                                            </tr>
                                            <tr style="height: 3px;"> </tr>
                                            <tr>
                                                <td>GL Account Type</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select name="glacctype" class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="accType" items="${accTypeList}">
                                                            <c:if test="${glbean.glAccType==accType.glaccType}">
                                                                <option value="${accType.glaccType}" selected="">${accType.glaccDes}</option>
                                                            </c:if>
                                                            <c:if test="${glbean.glAccType!=accType.glaccType}">
                                                                <option value="${accType.glaccType}">${accType.glaccDes}</option>
                                                            </c:if>

                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr style="height: 3px;"> </tr>
                                            <tr>
                                                <td>Description </td>
                                                <td><font style="color: red;">*</font></td>
                                                <td><input type="text" maxlength="64" name="description" value="${glbean.description}" /></td>
                                            </tr>
                                            <tr style="height: 3px;"> </tr>

                                            <tr>
                                                <td>Status </td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select  name="status"  class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="status" items="${statusList}">

                                                            <c:if test="${glbean.status==status.statusCode}">
                                                                <option value="${status.statusCode}" selected>${status.description}</option>
                                                            </c:if>
                                                            <c:if test="${glbean.status!=status.statusCode}">
                                                                <option value="${status.statusCode}">${status.description}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select>

                                                </td>
                                            </tr>
                                            <tr style="height: 10px;"> </tr>

                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td style="width: 300px;"> 
                                                    <input type="submit" value="Add" name="Add" class="defbutton"/>
                                                    <input onclick="resetglForm()" type="reset" value="Reset" class="defbutton"/>
                                                </td>

                                            </tr>
                                            <tr style="height: 10px;"> </tr>

                                        </table>

                                    </form>
                                </c:if>

                                <c:if test="${operationtype=='update'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/UpdateGeneralLedgerMgtServlet" name="updateglaccountform">


                                        <table border="0">
                                            <tr>
                                                <td>GL Account Number</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td><input type="text" name="glaccno" value="${glbean.glAccNo}" maxlength="20" readonly="true"/></td>
                                            </tr>
                                            <tr style="height: 3px;"> </tr>
                                            <tr>
                                                <td>GL Account Type</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select name="glacctype" class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="accType" items="${accTypeList}">
                                                            <c:if test="${glbean.glAccType==accType.glaccType}">
                                                                <option value="${accType.glaccType}" selected="">${accType.glaccDes}</option>
                                                            </c:if>
                                                            <c:if test="${glbean.glAccType!=accType.glaccType}">
                                                                <option value="${accType.glaccType}">${accType.glaccDes}</option>
                                                            </c:if>

                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr style="height: 3px;"> </tr>
                                            <tr>
                                            <tr>
                                                <td>Description </td>
                                                <td><font style="color: red;">*</font></td>
                                                <td><input type="text" maxlength="64" name="description" value="${glbean.description}" /></td>
                                            </tr>
                                            <tr style="height: 3px;"> </tr>

                                            <tr>
                                                <td>Status </td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select  name="status"  class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" selected>--SELECT--</option>
                                                        <c:forEach var="status" items="${statusList}">

                                                            <c:if test="${glbean.status==status.statusCode}">
                                                                <option value="${status.statusCode}" selected>${status.description}</option>
                                                            </c:if>
                                                            <c:if test="${glbean.status!=status.statusCode}">
                                                                <option value="${status.statusCode}">${status.description}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select>

                                                </td>
                                            </tr>
                                            <tr style="height: 10px;"> </tr>
                                        </table>
                                        <table>
                                            <tr>
                                                <td style="width: 140px;"></td>
                                                <td ><input type="submit" value="Update" name="Update" class="defbutton"/></td>
                                                <td ><input onclick="invokeUpdate('${glbean.glAccNo}', 'updt')" type="button" value="Reset" class="defbutton"/></td>
                                                <td ><input onclick="resetglForm()" type="button" value="Back" class="defbutton"/></td>

                                            </tr>
                                            <tr style="height: 10px;"> </tr>

                                        </table>

                                    </form>
                                </c:if>

                                <c:if test="${operationtype=='view'}" >

                                    <form method="POST" action="">
                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td>GL Account Number </td>
                                                    <td></td>
                                                    <td>:${glbean.glAccNo}</td>
                                                </tr>
                                                <tr style="height: 3px;"></tr>
                                                <tr>
                                                    <td>GL Account Type </td>
                                                    <td></td>
                                                    <td>:${glbean.glAccTypeDes}</td>
                                                </tr>
                                                <tr style="height: 3px;"></tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td></td>
                                                    <td>:${glbean.description}</td>
                                                </tr>
                                                <tr style="height: 3px;"></tr>
                                                <tr>
                                                    <td>Status </td>
                                                    <td></td>                                         
                                                    <td>:${glbean.statusDes}</td>
                                                </tr>
                                                <tr style="height: 3px;"></tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;">
                                                        <input type="button" value="Back" name="Back" class="defbutton" onclick="resetglForm()"/>
                                                    </td>

                                                </tr>

                                                <tr style="height: 3px;"></tr>

                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>





                                <br/></br>
                                <form name="viewTableForm" id="viewTableForm" method="post">
                                    <table border="" class="display" id="tableview">
                                        <thead>
                                            <tr>
                                                <th>GL Acc No</th>
                                                <th>GL Acc Type</th>
                                                <th>Description</th>
                                                <th>Status</th>

                                                <th>View</th>
                                                <th>Update</th>              
                                                <th>Delete</th>

                                            </tr>
                                        </thead>
                                        <tbody>

                                            <c:forEach  items="${accList}" var="acc">  
                                                <tr>
                                                    <td>${acc.glAccNo}</td>
                                                    <td>${acc.glAccTypeDes}</td>
                                                    <td>${acc.description}</td>
                                                    <td>${acc.statusDes}</td>

                                                    <td><a  href='#' onclick="invokeUpdate('${acc.glAccNo}', 'viw')">View</a></td>
                                                    <td><a  href='#' onclick="invokeUpdate('${acc.glAccNo}', 'updt')">Update</a></td>
                                                    <td><a  href='#' onclick="invokeDelete('${acc.glAccNo}', 'del')">Delete</a></td>

                                                </tr>
                                            </c:forEach> 





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
                                              <!--confirmation dialog -->
        <div id="dialog-confirm" title="Delete Confirmation">

        </div>
        <div class="footer"><jsp:include page="/footer.jsp"/></div>
    </body>
</html>

