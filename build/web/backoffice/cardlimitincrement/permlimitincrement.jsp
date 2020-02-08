<%-- 
    Document   : permlimitincrement
    Created on : Aug 15, 2012, 1:19:23 PM
    Author     : badrika
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>



<!DOCTYPE html>


<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

        <script type="text/javascript">
            function BackToLoad() {
                window.location = "${pageContext.request.contextPath}/LoadPermLimitIncrementServlet";
            }
            function BackToSearch() {
                window.location = "${pageContext.request.contextPath}/ApprovedPermLimitIncrementServlet";
            }
            function viewCard(cardnum, status) {
                window.location = "${pageContext.request.contextPath}/ViewPermLimitIncrementServlet?cardNum=" + cardnum + "&stat=" + status;
            }
            function ApproveInc() {
                document.approveform.action = "${pageContext.request.contextPath}/ConfirmPermLimitIncrementServlet";
                document.approveform.submit();
//                window.location = "${pageContext.request.contextPath}/ConfirmPermLimitIncrementServlet";
            }


        </script>
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.APPROVE_PERM_LIMIT_INC%>'
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

                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>

                                <c:if test="${operationtype=='search'}" >

                                    <form method="POST" action="${pageContext.request.contextPath}/ApprovedPermLimitIncrementServlet">
                                        <table border="0" cellpadding="0" cellspacing="10">


                                            <tbody>
                                                <tr>
                                                    <td>Card Number </td>

                                                    <td><input type="text" maxlength="64" name="cardNumber" value="${permBean.cardNumber}" class="deftext"/></td>
                                                </tr>


                                                <tr>
                                                    <td>Increment Type </td>

                                                    <td>
                                                        <select  name="incType"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>

                                                            <option value="CREDIT">CREDIT</option>
                                                            <option value="CASH">CASH</option>


                                                        </select>

                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Increment Amount</td>

                                                    <td><input type="text" maxlength="10" name="amount" value="${permBean.amount}" class="deftext"/></td>
                                                </tr>


                                                <tr>
                                                    <td>Request Date From</td>
                                                    <td> <input  name="fromdate1" readonly maxlength="16" value="${permBean.fromDate}" key="fromdate" id="fromdate1"  />

                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $("#fromdate1").datepicker({
                                                                    showOn: "button",
                                                                    buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                    changeMonth: true,
                                                                    changeYear: true,
                                                                    buttonImageOnly: true,
                                                                    dateFormat: "yy-mm-dd",
                                                                    showWeek: true,
                                                                    firstDay: 1
                                                                });
                                                            });
                                                        </script>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Request Date To</td>
                                                    <td><input  name="fromdate2" readonly maxlength="16" value="${permBean.toDate}" key="fromdate" id="fromdate2"  />

                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $("#fromdate2").datepicker({
                                                                    showOn: "button",
                                                                    buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                    changeMonth: true,
                                                                    changeYear: true,
                                                                    buttonImageOnly: true,
                                                                    dateFormat: "yy-mm-dd",
                                                                    showWeek: true,
                                                                    firstDay: 1
                                                                });
                                                            });
                                                        </script>
                                                    </td>
                                                </tr>



                                                <tr>
                                                    <td></td>

                                                    <td style="width: 300px;"> 
                                                        <input type="submit" value="Search" name="Search" class="defbutton"/>
                                                        <input onclick="BackToLoad()" type="reset" value="Reset" class="defbutton"/>
                                                    </td>

                                                </tr>

                                            </tbody>
                                        </table>
                                    </form>
                                </c:if>

                                <c:if test="${operationtype=='view'}" >

                                    <form method="POST" name="approveform" action="${pageContext.request.contextPath}/ConfirmPermLimitIncrementServlet">
                                        <table border="0" cellpadding="0" cellspacing="10">


                                            <tbody>
                                                <tr>
                                                    <td>Card Number </td>                                                    
                                                    <td>:${permBean2.cardNumber}</td>
                                                </tr>

                                                <tr>
                                                    <td>Increment Type</td>
                                                    <td>:${permBean2.incType}</td>
                                                </tr>

                                                <tr>
                                                    <td>Amount </td>                                  
                                                    <td>:${permBean2.amount}</td>
                                                </tr>

                                                <tr>
                                                    <td>Status </td>                                  
                                                    <td>:${permBean2.statusDes}</td>
                                                </tr>

                                                <tr>
                                                    <td>Remark </td>                                  
                                                    <td>:${permBean2.remark}</td>
                                                </tr>

                                                <tr>
                                                    <td>Online Credit Limit </td>                                  
                                                    <td>:${permBean2.onlineCreditLimit}</td>
                                                </tr>

                                                <tr>
                                                    <td>Online Cash Limit </td>                                  
                                                    <td>:${permBean2.onlineCashLimit}</td>
                                                </tr>

                                                <tr>
                                                    <td>Online Available Credit Limit </td>                                  
                                                    <td>:${permBean2.onlineAvlCreditLimit}</td>
                                                </tr>

                                                <tr>
                                                    <td>Online Available Cash Limit </td>                                  
                                                    <td>:${permBean2.onlineAvlCashLimit}</td>
                                                </tr>

                                                <tr>
                                                    <td>Requested User </td>                                  
                                                    <td>:${permBean2.requestedUser}</td>
                                                </tr>                                                                                              

                                                <tr>
                                                    <td>Last Updated User </td>                                  
                                                    <td>:${permBean2.lastUpdatedUser}</td>
                                                </tr>

                                                <tr>
                                                    <td>Last Updated Date </td>                                  
                                                    <td>:${permBean2.lastUpdatedTime}</td>
                                                </tr>

                                                <tr hidden="">
                                                    <td><input type="text" name="cardNumber" value="${permBean2.cardNumber}" hidden=""/></td>
                                                    <td><input type="text" name="incType" value="${permBean2.incType}" hidden=""/></td>                                                      
                                                </tr>
                                                <tr hidden="">
                                                    <td><input type="text" name="amount" value="${permBean2.amount}" hidden=""/></td> 
                                                    <td><input type="text" name="status" value="${permBean2.status}" hidden=""/></td>                                                      
                                                </tr>
                                                <tr hidden="">
                                                    <td><input type="text" name="onlineCreditLimit" value="${permBean2.onlineCreditLimit}" hidden=""/></td> 
                                                    <td><input type="text" name="onlineCashLimit" value="${permBean2.onlineCashLimit}" hidden=""/></td>                                                      
                                                </tr>
                                                <tr hidden="">
                                                    <td><input type="text" name="onlineTempCreditAmt" value="${permBean2.onlineTempCreditAmt}" hidden=""/></td> 
                                                    <td><input type="text" name="onlineTempCashAmt" value="${permBean2.onlineTempCashAmt}" hidden=""/></td>                                                      
                                                </tr>

                                                <tr hidden="">
                                                    <td><input type="text" name="onlineAvlCreditLimit" value="${permBean2.onlineAvlCreditLimit}" hidden=""/></td> 
                                                    <td><input type="text" name="onlineAvlCashLimit" value="${permBean2.onlineAvlCashLimit}" hidden=""/></td>                                                      
                                                </tr>


                                                <tr>
                                                    <td></td>
                                                    <td style="width: 300px;">
                                                        <input type="button" value="Approve" name="Approve" class="defbutton" onclick="ApproveInc()" />
                                                        <input type="button" value="Back" name="Back" class="defbutton" onclick="BackToLoad()"/>
                                                    </td>

                                                </tr>

                                            </tbody>
                                        </table>

                                    </form>

                                </c:if>



                                <!-- show table data -->
                                <br/>
                                <form name="viewTableForm" id="viewTableForm" method="post">
                                    <table border="1" class="display" id="tableview">
                                        <thead>
                                            <tr>
                                                <th>Card Number</th>
                                                <th>Increment Type</th>
                                                <th>Amount</th>
                                                <th>Status</th>
                                                <th>Remark</th>
                                                <th>Requested User</th>              
                                                <th>View</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  items="${sessionScope.SessionObject.permBeanList}" var="card">
                                                <tr>
                                                    <td>${card.cardNumber}</td>
                                                    <td>${card.incType}</td>
                                                    <td>${card.amount}</td>
                                                    <td>${card.statusDes}</td>
                                                    <td>${card.remark}</td>
                                                    <td>${card.requestedUser}</td>
                                                    <td><a  href='#' onclick="viewCard('${card.cardNumber}', '${card.status}')">View</a></td>

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
        <div class="footer"><jsp:include page="/footer.jsp"/></div>
    </body>
</html>
