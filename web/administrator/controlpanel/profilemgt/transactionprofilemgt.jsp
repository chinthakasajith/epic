<%-- 
    Document   : transactionprofilemgt
    Created on : Jun 6, 2012, 3:40:50 PM
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

            function invokeReset() {
                window.location = "${pageContext.request.contextPath}/LoadTransactionProfileServlet";
            }

            function invokeview(value) {
                window.location = "${pageContext.request.contextPath}/ViewTransactionProfileServlet?id=" + value;
            }

            function invokeAdd()
            {

                document.addprofileform.action = "${pageContext.request.contextPath}/AddTransactionProfileServlet";
                document.addprofileform.submit();

            }

            function selectAll(selectBox) {
                for (var i = 0; i < selectBox.options.length; i++) {
                    selectBox.options[i].selected = true;
                }
                invokeAdd();
            }

            function selectAllForUpdate(selectBox, val) {
                for (var i = 0; i < selectBox.options.length; i++) {
                    selectBox.options[i].selected = true;
                }
                invokeupdateConf(val);
            }



            function invokeupdate(value) {
                document.viewTableForm.action = "${pageContext.request.contextPath}/UpdateTransactionProfileFormServlet";
                document.getElementById('id').value = value;
                document.viewTableForm.submit();

            }

            function invokeupdateConf(value) {
                document.updateprofileform.action = "${pageContext.request.contextPath}/UpdateTransactionProfileServlet";
                document.getElementById('id').value = value;
                document.updateprofileform.submit();

            }



            function invokedelete(value) {

//                answer = confirm("Do you really want to delete "+value+" Transaction Profile?")
//                if (answer !=0)
//                {
//                    window.location="${pageContext.request.contextPath}/DeleteTransactionProfileServlet?id="+value;
//                }
//                else
//                {
//                    window.location="${pageContext.request.contextPath}/LoadTransactionProfileServlet";
//                }
                $("#dialog-confirm").html("<p>Do you really want to delete " + value + " Transaction Profile?</p>");
                $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 'auto',
                    width: 500,
                    modal: true,
                    buttons: {
                        "No": function () {
                            window.location = "${pageContext.request.contextPath}/LoadTransactionProfileServlet";
                        },
                        "Yes": function () {
                            window.location = "${pageContext.request.contextPath}/DeleteTransactionProfileServlet?id=" + value;
                        }
                    }
                });

            }




        </script>
        <script >
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.TRANSACTIONPROFILE%>'
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



                <div class="content" style="height: 500px">

                    <td class="menubar"><jsp:include page="/leftmenu.jsp"/></td>

                </div>


                <div id="content1" >

                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">


                                <%--  start developer area --%>


                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>


                                <%--  start add form --%>

                                <c:if test="${operationtype=='add'}" >
                                    <form method="POST" action="" name="addprofileform">
                                        <table border="0">


                                            <tbody>
                                                <tr>
                                                    <td>Profile Code </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" maxlength="8" name="profileCode" value="${transactionProfilebean.profileCode}" /></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="description" maxlength="64" value="${transactionProfilebean.description}" /></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td>Status </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td>
                                                        <select  name="statuscode"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${transactionProfilebean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${transactionProfilebean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                </tr>

                                                <tr><td style="height: 10px"></td></tr>

                                            <table>
                                                <tbody>
                                                    <tr>
                                                        <td colspan="3">Transactions </td>
                                                    </tr>
                                                    <tr><td style="height: 5px"></td></tr>
                                                    <tr>
                                                        <td>
                                                            <h4><b>All Transactions</b></h4>
                                                            <select name="notassignlist" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                                <c:forEach  var="transaction" items="${sessionScope.SessionObject.transactionTypeList}">
                                                                    <option value="${transaction.transactionCode}" >${transaction.description}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                        <td align="center" >
                                                            <input type=button value=">" onclick=moveout() class="buttonsize"><br>
                                                            <input type=button value="<" onclick=movein() class="buttonsize"><br>
                                                            <input type=button value=">>" onclick=moveallout() class="buttonsize"><br>
                                                            <input type=button value="<<" onclick=moveallin() class="buttonsize">
                                                        </td>
                                                        <td>
                                                            <h4><b>Assigned Transactions</b></h4>


                                                            <select name="assignlist" style="width: 200px" id=out multiple="multiple"   size=10>

                                                                <c:forEach  var="ass" items="${tranProfileTranList}">
                                                                    <option value="${ass.txnDes}" >${ass.txnDes}</option>
                                                                </c:forEach>

                                                            </select>


                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>

                                            <tr><td style="height: 5px"></td></tr>

                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td style="width: 300px;"> 
                                                    <input type="button" value="Add" name="Add" onclick="selectAll(assignlist)" class="defbutton"/>
                                                    <input onclick="invokeReset()" type="reset" value="Reset" class="defbutton"/>
                                                </td>

                                            </tr>

                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>

                                <%--  start update form --%>

                                <c:if test="${operationtype=='update'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/UpdateTransactionProfileServlet" name="updateprofileform">
                                        <table border="0">


                                            <tbody>
                                                <tr>
                                                    <td>Profile Code </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" maxlength="8" readonly="true" name="profileCode" value="${transactionProfilebean.profileCode}" /></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="description" maxlength="64" value="${transactionProfilebean.description}" /></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td>Status </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td>
                                                        <select  name="statuscode"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${transactionProfilebean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${transactionProfilebean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                </tr>

                                                <tr><td style="height: 10px"></td></tr>

                                            <table>
                                                <tbody>
                                                    <tr>
                                                        <td colspan="3">Transactions </td>
                                                    </tr>
                                                    <tr><td style="height: 5px"></td></tr>
                                                    <tr>
                                                        <td>
                                                            <h4><b>Not Assigned Transactions</b></h4>
                                                            <select name="notassignlist" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                                <c:forEach  var="notassig" items="${notassignedTransList}">
                                                                    <option value="${notassig.txnCode}" >${notassig.txnDes}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                        <td align="center" >
                                                            <input type=button value=">" onclick=moveout() class="buttonsize"><br>
                                                            <input type=button value="<" onclick=movein() class="buttonsize"><br>
                                                            <input type=button value=">>" onclick=moveallout() class="buttonsize"><br>
                                                            <input type=button value="<<" onclick=moveallin() class="buttonsize">
                                                        </td>
                                                        <td>
                                                            <h4><b>Assigned Transactions</b></h4>


                                                            <select name="assignlist" style="width: 200px" id=out multiple="multiple"   size=10>

                                                                <c:forEach var="assig" items="${assignedTransList}">
                                                                    <option value="${assig.txnCode}" >${assig.txnDes}</option>
                                                                </c:forEach>

                                                            </select>


                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>

                                            <table>
                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td></td>
                                                    <td><input type="button" value="Update" name="Update" onclick="selectAllForUpdate(assignlist, '${transactionProfilebean.profileCode}')" class="defbutton"/></td>
                                                    <td><input onclick="invokeupdate('${transactionProfilebean.profileCode}')" type="reset" value="Reset" class="defbutton"/></td>
                                                    <td><input onclick="invokeReset()" type="reset" value="Back" class="defbutton"/></td>

                                                </tr>
                                            </table>


                                    </form>
                                </c:if>


                                <%--  start view form --%>


                                <c:if test="${operationtype=='view'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/ViewTransactionProfileServlet">
                                        <table border="0">


                                            <tbody>
                                                <tr>
                                                    <td>Profile Code </td>
                                                    <td></td>
                                                    <td>:${transactionProfilebean.profileCode}</td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td></td>
                                                    <td>:${transactionProfilebean.description}</td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td>Status </td>
                                                    <td></td>                                         
                                                    <td>:${transactionProfilebean.statusDes}</td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>


                                            <table>
                                                </tbody>
                                                <tr>

                                                    <td>
                                                        <h4><b>Not Assigned Transactions</b></h4>
                                                        <select name="notassignlist" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                            <c:forEach  var="notassig" items="${notassignedTransList}">
                                                                <option value="${notassig.txnCode}" >${notassig.txnDes}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>

                                                    <td style="width:  20px"></td>


                                                    <td>
                                                        <h4><b>Assigned Transactions</b></h4>
                                                        <select name="assignlist" style="width: 200px" id=out multiple="multiple"   size=10>
                                                            <c:forEach var="assig" items="${assignedTransList}">
                                                                <option value="${assig.txnCode}" >${assig.txnDes}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                </tbody>
                                            </table>



                                            <tr><td style="height: 5px"></td></tr>


                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td style="width: 300px;">
                                                    <input type="button" value="Back" name="Back" class="defbutton" onclick="invokeReset()"/>
                                                </td>

                                            </tr>

                                            <tr><td style="height: 12px"></td></tr>


                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>

















                                <%-- show table data --%>
                                <br/>
                                <form name="viewTableForm" id="viewTableForm" method="post">
                                    <table border="1" class="display" id="tableview">
                                        <thead>
                                            <tr>
                                                <th>Profile Code</th>
                                                <th>Description</th>
                                                <th>Status</th>

                                                <th>View</th>
                                                <th>Update</th>              
                                                <th>Delete</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  items="${tranProfileList}" var="list">
                                                <tr>
                                                    <td>${list.profileCode}</td>
                                                    <td>${list.description}</td>
                                                    <td>${list.statusDes}</td>


                                                    <td><a  href='#' onclick="invokeview('${list.profileCode}')">View</a></td>
                                                    <td><a  href='#' onclick="invokeupdate('${list.profileCode}')">Update</a></td>
                                                    <td><a  href='#' onclick="invokedelete('${list.profileCode}')">Delete</a></td>


                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                        <input type="hidden" id="id"  name="id" maxlength="16" />
                                    </table>

                                </form>








                                <%--  end developer area --%>           

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

