<%-- 
    Document   : locplanpayment
    Created on : Sep 20, 2016, 10:28:14 AM
    Author     : sajith_g
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

                document.updatepaymentplanform.action = "${pageContext.request.contextPath}/UpdateConfirmedLOCPaymentPlanServlet";
                document.updatepaymentplanform.submit();
            }
            function invokeAdd()
            {

                document.addpaymentplanform.action = "${pageContext.request.contextPath}/AddLOCPaymentPlanServlet";
                document.addpaymentplanform.submit();
            }

            function invokeReset()
            {

                window.location = "${pageContext.request.contextPath}/LoadLOCPaymentPlanMgtServlet";
            }

            function updateReset(value)
            {

                window.location = "${pageContext.request.contextPath}/UpdateLOCPaymentPlanServlet?paymentplancode=" + value;
            }

            function ConfirmDelete(paymentplancode)
            {
                $("#dialog-confirm").html("<p>Do you really want to delete " + paymentplancode + " load on card payment plan ?</p>");
                $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 'auto',
                    width: 500,
                    modal: true,
                    buttons: {
                        "No": function() {
                            $(this).dialog("close");
                        },
                        "Yes": function() {
                            window.location = "${pageContext.request.contextPath}/DeleteLOCPaymentPlanServlet?paymentplancode=" + paymentplancode;
                        }
                    }
                });

            }

        </script>
        <script >
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.LOCPAYMENTPLAN%>'
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



        <title>EPIC CMS LOAN ON CARD PAYMENT PLAN HOME</title>
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
                                    <form method="POST" action="" name="addpaymentplanform">

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
                                                    <td style="width: 180px">Payment Plan Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text"  value="${paymentPlanBean.plancode}" name="paymentplancode" maxlength="16"></td>
                                                    <td></td>
                                                </tr>
                                                <tr><td style="height: 5px"></td></tr>     
                                                <tr>
                                                    <td style="width: 180px">Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text"  name="paymentplandescription" value="${paymentPlanBean.description}" maxlength="64"></td>
                                                    <td></td>
                                                </tr>
                                                <tr><td style="height: 5px"></td></tr> 
                                                <tr>
                                                    <td style="width: 180px">Duration (Months)</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text"  value="${paymentPlanBean.duration}" name="duration" maxlength="2"></td>
                                                    <td></td>
                                                </tr>
                                                <tr><td style="height: 5px"></td></tr>     
                                                <tr>
                                                    <td style="width: 180px">Interest Rate (%)</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text"  name="interestrate" value="${paymentPlanBean.interestrate}" maxlength="5"></td>
                                                    <td></td>
                                                </tr>
                                                <tr><td style="height: 5px"></td></tr>
                                                <tr>
                                                    <td style="width: 180px">Status </td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="statuscode" id="statuscode">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">
                                                                <c:if test="${paymentPlanBean.statusCode==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${paymentPlanBean.statusCode!=status.statusCode}">
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
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.LOCPAYMENTPLAN%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" title="History View"/></a></td>
                                                </tr>


                                            </tbody>
                                        </table>
                                    </form>

                                </c:if>

                                <%-- -------------------------add form end -------------------------------- --%>



                                <%-- -------------------------view form start -------------------------------- --%>
                                <c:if test="${operationtype=='view'}" >
                                    <form id="paymentPlanViewForm" action="" method="POST" name="viewpaymentPlanform">

                                        <table>
                                            <tr>
                                                <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                                <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                                <td></td>
                                            </tr>
                                        </table>

                                        <table border="0">

                                            <tr>
                                                <td style="width: 180px">Payment Plan Code</td>
                                                <td><font>:</font>&nbsp;${paymentPlanBean.plancode}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>     
                                            <tr>
                                                <td style="width: 180px">Description</td>
                                                <td><font>:</font>&nbsp;${paymentPlanBean.description}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>
                                            <tr>
                                                <td style="width: 180px">Duration (Months)</td>
                                                <td><font>:</font>&nbsp;${paymentPlanBean.duration}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>
                                            <tr>
                                                <td style="width: 180px">Interest Rate (%)</td>
                                                <td><font>:</font>&nbsp;${paymentPlanBean.interestrate}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>
                                            <tr>
                                                <td style="width: 180px">Status </td>
                                                <td><font>:</font>&nbsp;${paymentPlanBean.statusDes}</td>
                                            </tr>

                                            <tr> <td style="height:12px;"></td></tr>
                                            <tr>
                                                <td></td>
                                                <td >
                                                    <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeReset()">
                                                </td>
                                                <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.LOCPAYMENTPLAN%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                            </tr>
                                        </table>

                                    </form>
                                </c:if>

                                <%-- -------------------------view form end -------------------------------- --%>






                                <%-- -------------------------update form start -------------------------------- --%>

                                <c:if test="${operationtype=='update'}" >
                                    <form method="POST" action="" name="updatepaymentplanform">

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
                                                    <td style="width: 180px">Payment Plan Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text"  value="${paymentPlanBean.plancode}" name="paymentplancode" maxlength="16" readonly=""></td>
                                                    <td></td>
                                                </tr>
                                                <tr><td style="height: 5px"></td></tr>     
                                                <tr>
                                                    <td style="width: 180px">Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text"  name="paymentplandescription" value="${paymentPlanBean.description}" maxlength="64"></td>
                                                    <td></td>
                                                </tr>
                                                <tr><td style="height: 5px"></td></tr> 
                                                <tr>
                                                    <td style="width: 180px">Duration (Months)</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text"  value="${paymentPlanBean.duration}" name="duration" maxlength="16"></td>
                                                    <td></td>
                                                </tr>
                                                <tr><td style="height: 5px"></td></tr>     
                                                <tr>
                                                    <td style="width: 180px">Interest Rate (%)</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text"  name="interestrate" value="${paymentPlanBean.interestrate}" maxlength="64"></td>
                                                    <td></td>
                                                </tr>
                                                <tr><td style="height: 5px"></td></tr>
                                                <tr>
                                                    <td style="width: 180px">Status </td>
                                                    <td><font style="color: red;">*</font>&nbsp;<select  name="statuscode" id="statuscode">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">
                                                                <c:if test="${paymentPlanBean.statusCode==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${paymentPlanBean.statusCode!=status.statusCode}">
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
                                                        <input type="button" value="Reset" name="reset" onclick="updateReset('${paymentPlanBean.plancode}')" style="width: 100px"></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.LOCPAYMENTPLAN%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>


                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                </c:if>
                                <%-- -------------------------update form end -------------------------------- --%>





                                <table  border="1"  class="display" id="tableview">
                                    <thead>
                                        <tr>
                                            <th>Plan Code</th>
                                            <th>Description</th>
                                            <th>Duration (Months)</th>
                                            <th>Interest Rate (%)</th>
                                            <th>Status</th>

                                            <th>View</th>
                                            <th>Update</th>
                                            <th >Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody >
                                        <c:forEach var="paymentplan" items="${paymentPlanList}">
                                            <tr>
                                                <td >${paymentplan.plancode}</td>
                                                <td >${paymentplan.description}</td>
                                                <td >${paymentplan.duration}</td>
                                                <td >${paymentplan.interestrate}</td>
                                                <td >${paymentplan.statusDes}</td>


                                                <td  ><a href='${pageContext.request.contextPath}/ViewLOCPaymentPlanServlet?paymentplancode=<c:out value="${paymentplan.plancode}"></c:out>'>View</a></td>
                                                <td  ><a href='${pageContext.request.contextPath}/UpdateLOCPaymentPlanServlet?paymentplancode=<c:out value="${paymentplan.plancode}"></c:out>'>Update</a></td>
                                                <td ><a  href='#' onclick="ConfirmDelete('${paymentplan.plancode}')">Delete</a></td>

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