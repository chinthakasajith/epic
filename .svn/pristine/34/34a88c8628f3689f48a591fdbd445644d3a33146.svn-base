<%-- 
    Document   : balancetranferpaymentrequest
    Created on : Sep 5, 2016, 6:31:56 PM
    Author     : sajith_g
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html >
    <head>

        <style type="text/css">
            form.inset {border-style:inset; width: 510px; color: #0063DC;}
        </style>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/content.jsp"/>
        <title>EPIC_CMS_HOME</title>

        <script  type="text/javascript" >
            function invokeReset() {
                window.location = "${pageContext.request.contextPath}/LoadBTPaymentCardMgtServlet";
            }

            function invokeResetSetup() {

                document.getElementById("installmentAmount").value = "";

            }

            function invokeSearch(cardNo)
            {

                document.easypaymentrequest.action = "${pageContext.request.contextPath}/ManageBTPaymentRequestServlet";
                document.easypaymentrequest.submit();

            }

            function InvokeSetup(txnID, rrn, txnAmount)
            {

                window.location = "${pageContext.request.contextPath}/LoadBTPaymentSetupServlet?txnID=" + txnID + "&rrn=" + rrn + "&txnAmount=" + txnAmount;

            }
            function invokeAdd(cardNo)
            {

                document.addeasypaymentrequestform.action = "${pageContext.request.contextPath}/AddBTPaymentRequestServlet?cardno=" + cardNo;
                document.addeasypaymentrequestform.submit();

            }
            function calculateInsAmount() {
                if (document.getElementById("txnamount") != null && document.getElementById("txnamount").value !== "") {
                    $("#ErrorMsg").text("");
                    var txnamout = document.getElementById("txnamount").value;
                    var paymentplancode = document.getElementById("paymentPlanCode").value;
                    var duration = document.getElementById("duration" + paymentplancode).value;
                    var interestrate = document.getElementById("interestrate" + paymentplancode).value;
                    document.getElementById("installmentAmount").value = calculationFormula(txnamout,duration,interestrate);
                } else {
                    document.getElementById("installmentAmount").value = "";
                    $("#ErrorMsg").text("Txn Amount shouldn't be empty ...");
                }

            }
            
            //calculate installement amount
            function calculationFormula(txnamout,duration,interestrate){
                var txnamout=parseFloat(txnamout);
                var duration=parseFloat(duration);
                var interestrate=parseFloat(interestrate);
                
                return ((Math.ceil(((txnamout+(txnamout*interestrate)/100)/duration)*100))/100).toFixed(2);
            }
        </script>  
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.BALANCE_TRANSFER_PAYMENT_REQUEST%>'
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
                                        <td><font color="#FF0000"><b id="ErrorMsg" name="ErrorMsg"> ${errorMsg}</b></font></td>
                                        <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                        <td></td>
                                    </tr>
                                </table>

                                <!--/////////////////////////////////////////////End Default view  ///////////////////////////////////////////////////////////-->          


                                <%-- -------------------------add form start -------------------------------- --%>



                                <c:if test="${operationtype=='add'}" >
                                    <form method="POST" action="" name="addeasypaymentrequestform">
                                        <span>Other Bank Card Details :</span>
                                        <table border="0">

                                            <tbody>

                                                <tr>
                                                    <td style="width: 180px">Card Number</td>
                                                    <td>           
                                                        <font style="color: red;">*</font>:&nbsp;<input type="text"  value="${tempBean.otherbankCardNumber}" name="othercardno" id="othercardno" maxlength="16">
                                                        <input type="hidden" value="${tempBean.cardNumber}" name="cardno"/>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 180px">Expiry Date</td>
                                                    <td>           
                                                        <font style="color: red;">*</font>:&nbsp;<input type="text"  value="${tempBean.otherbankCardExpiryMonth}" name="expirymonth" id="expirymonth" maxlength="2" size="2">&nbsp;/&nbsp;
                                                        <input type="text"  value="${tempBean.otherbankCardExpiryYear}" name="expiryyear" id="expiryyear" maxlength="2" size="2">(Month/Year)
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 180px">Name on Card</td>
                                                    <td>           
                                                        <font style="color: red;">*</font>:&nbsp;<input type="text"  value="${tempBean.otherbankNameOnCard}" name="othercardname" id="othercardname" maxlength="16">
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 180px">Issuer </td>
                                                    <td><font style="color: red;">*</font>:
                                                        <select name="bankcode" id="bankcode">
                                                            <option value="" >--SELECT--</option>
                                                            <c:if test="${tempBean.bankcode != null}">
                                                                <c:forEach var="banklist" items="${banklist}">
                                                                    <c:if test="${tempBean.bankcode == banklist.bankCode}">
                                                                        <option value="${banklist.bankCode}" selected="true">${banklist.bankName}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </c:if> 
                                                            <c:forEach var="banklist" items="${banklist}">
                                                                <option value="${banklist.bankCode}">${banklist.bankName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Due Date</td>
                                                    <td><font style="color: red;">*</font>:&nbsp;<input  name="duedate" readonly maxlength="16" value="${tempBean.dueDate}" key="duedate" id="duedate"  />
                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $("#duedate").datepicker({
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
                                                    <td style="width: 180px">Balance Transfer Amount</td>
                                                    <td><font style="color: red;">*</font>:&nbsp;<input type="text"  value="${tempBean.txnAmount}" name="txnamount" id="txnamount" maxlength="16"></td>
                                                </tr>
                                                <tr><td style="height: 5px"></td></tr> 
                                                <tr>
                                                    <td style="width: 180px">Payment Plan </td>
                                                    <td><font style="color: red;">*</font>:
                                                        <select name="paymentPlanCode" id="paymentPlanCode" onchange="calculateInsAmount()">
                                                            <option value="" >--SELECT--</option>
                                                            <c:if test="${tempBean.paymentPlan != null}">
                                                                <c:forEach var="paymentPlan" items="${paymentPlanList}">
                                                                    <c:if test="${tempBean.paymentPlan == paymentPlan.paymentplancode}">
                                                                        <option value="${paymentPlan.paymentplancode}" selected="true">${paymentPlan.description}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </c:if> 
                                                            <c:forEach var="paymentPlan" items="${paymentPlanList}">
                                                                <option value="${paymentPlan.paymentplancode}">${paymentPlan.description}</option>
                                                            </c:forEach>
                                                        </select>&nbsp;LKR
                                                    </td>
                                                    <td>
                                                        <c:forEach var="paymentPlan" items="${paymentPlanList}">
                                                            <input type="hidden" value="${paymentPlan.duration}" id="duration${paymentPlan.paymentplancode}"/>
                                                            <input type="hidden" value="${paymentPlan.interestrate}" id="interestrate${paymentPlan.paymentplancode}"/>
                                                        </c:forEach>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 180px">Installment Amount</td>
                                                    <td><font style="color: red;">*</font>:&nbsp;<input type="text"  value="${tempBean.installmentAmount}" name="installmentAmount" id="installmentAmount" maxlength="16" readonly=""></td>
                                                </tr>

                                                <tr> <td style="height:12px;"></td></tr>
                                                <tr>
                                                    <td></td>
                                                    <td style="width: 300px">&nbsp;&nbsp;<input type="submit" value="Request" name="requestplan" style="width: 100px" onclick="invokeAdd()">
                                                        <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeReset()"></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.BALANCE_TRANSFER_PAYMENT_REQUEST%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" title="History View"/></a></td>
                                                </tr>


                                            </tbody>
                                        </table>
                                    </form>

                                </c:if>

                                <%-- -------------------------add form end -------------------------------- --%>

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