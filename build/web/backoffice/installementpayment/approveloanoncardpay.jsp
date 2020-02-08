<%-- 
    Document   : approveloanoncardpay
    Created on : Sep 19, 2016, 9:27:50 AM
    Author     : sajith_g
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
                window.location = "${pageContext.request.contextPath}/LoadLoanOnCardPayConfirmationServlet";
            }

            function viewCard(reqid, txnid) {
                window.location = "${pageContext.request.contextPath}/SearchLoanOnCardPayRequestServlet?reqid=" + reqid + "&txnid=" + txnid + "&view=view";
            }

            function ApproveInc() {
                document.approveform.action = "${pageContext.request.contextPath}/SearchLoanOnCardPayRequestServlet?view=approve";
                document.approveform.submit();
//                window.location = "${pageContext.request.contextPath}/ConfirmPermLimitIncrementServlet";
            }
            
            function Reject() {
                document.approveform.action = "${pageContext.request.contextPath}/SearchLoanOnCardPayRequestServlet?view=reject";
                document.approveform.submit();
            }


        </script>
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.APPROVE_LOANONCARD_PAY%>'
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

                                    <form method="POST" action="${pageContext.request.contextPath}/SearchLoanOnCardPayRequestServlet?view=search">
                                        <table border="0" cellpadding="0" cellspacing="10">


                                            <tbody>
                                                <tr>
                                                    <td>Card Number </td>

                                                    <td><input type="text" maxlength="64" name="cardno" value="${permBean.cardno}" class="deftext"/></td>
                                                </tr>


                                                <tr>
                                                    <td>Payment Plan </td>

                                                    <td>
                                                        <select name="paymentplan" id="paymentplan" >
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="paymentPlan" items="${paymentPlanList}">
                                                                <option value="${paymentPlan.plancode}">${paymentPlan.description}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    
                                                    
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Loan Amount</td>

                                                    <td><input type="text" maxlength="10" name="amount" value="${permBean.amount}" class="deftext"/></td>
                                                </tr>


                                                <tr>
                                                    <td>Request Date From</td>
                                                    <td> <input  name="fromdate" readonly maxlength="16" value="${permBean.fromdate}" key="fromdate" id="fromdate"  />

                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $("#fromdate").datepicker({
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
                                                    <td><input  name="todate" readonly maxlength="16" value="${permBean.todate}" key="todate" id="todate"  />

                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $("#todate").datepicker({
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

                                    <form method="POST" name="approveform" action="${pageContext.request.contextPath}/SearchLoanOnCardPayRequestServlet?view=approve">
                                        <table border="0" cellpadding="0" cellspacing="10">


                                            <tbody>
                                                <tr>
                                                    <td>Card Number </td>                                                    
                                                    <td>:${permBean2.cardno}</td>
                                                </tr>

                                                <tr>
                                                    <td>Transfer Amount</td>
                                                    <td>:${permBean2.loanamount}</td>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Preferred Payment Plan</td>
                                                    <td>:${permBean2.paymentplan} - ${permBean2.interestrate} % p.a. - ${permBean2.plandurarion} months</td>
                                                </tr>

                                                <tr>
                                                    <td>Installment Amount </td>                                  
                                                    <td>:${permBean2.installment}</td>
                                                </tr>

                                                <tr>
                                                    <td>Requested User </td>                                  
                                                    <td>:${permBean2.requesteduser}</td>
                                                </tr> 
                                                
                                                <tr>
                                                        <td>Remarks</td>
                                                        <td><textarea id="remarks" style="" name="remarks" value="${permBean2.remark}" >${permBean2.remark}</textarea></td>
                                                    </tr>

                                                <tr hidden="">
                                                    <td><input type="text" name="cardNumber" value="${permBean2.cardno}" hidden=""/></td>
                                                    <td><input type="text" name="creditOrCash" value="${permBean2.loanamount}" hidden=""/></td>                                                      
                                                                                                          
                                                </tr>


                                                <tr>
                                                    <td></td>
                                                    <td style="width: 300px;">
                                                        <input type="button" value="Approve" name="Approve" class="defbutton" onclick="ApproveInc()" />
                                                        <input type="button" value="Reject" name="reject" class="defbutton" onclick="Reject()"/>
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
                                                <th>Amount</th>
                                                <th>Payment Plan</th>
                                                <th>Installment</th>
                                                <th>Status</th>
                                                <th>Requested User</th>
                                                              
                                                <th>view</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  items="${requestList}" var="card">
                                                <tr>
                                                    <td>${card.cardno}</td>
                                                    <td>${card.loanamount}</td>
                                                    <td>${card.paymentplan}</td>
                                                    <td>${card.installment}</td>
                                                    <td>${card.statusCode}</td>
                                                    <td>${card.requesteduser}</td>
                                                    
                                                    <td><a  href='#' onclick="viewCard('${card.requestid}', '${card.txnid}')">View</a></td>

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
