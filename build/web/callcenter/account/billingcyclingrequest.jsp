<%-- 
    Document   : billingcyclingrequest
    Created on : Sep 22, 2016, 10:41:01 AM
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
                window.location = "${pageContext.request.contextPath}/LoadBillingCyclingCardMgtServlet";
            }

            function invokeResetSetup() {

                document.getElementById("installmentAmount").value = "";

            }

            function invokeSearch(cardNo)
            {

                document.billingcyclingrequest.action = "${pageContext.request.contextPath}/ManageBillingCyclingRequestServlet";
                document.billingcyclingrequest.submit();

            }

            function InvokeSetup(txnID, rrn, txnAmount, formattedTxnAmount, txnCurrencyCode)
            {

                window.location = "${pageContext.request.contextPath}/LoadEasyPaymentSetupServlet?txnID=" + txnID + "&rrn=" + rrn + "&txnAmount=" + txnAmount + "&formattedTxnAmount=" + formattedTxnAmount + "&txnCurrencyCode=" + txnCurrencyCode;

            }
            function invokeAdd(cardNo)
            {

                document.addbillingcyclingrequestform.action = "${pageContext.request.contextPath}/AddEasyPaymentRequestServlet?cardno=" + cardNo;
                document.addbillingcyclingrequestform.submit();

            }

            function calculateInsAmount() {
                if (document.getElementById("formattedTxnAmount") != null && document.getElementById("formattedTxnAmount").value !== "") {
                    $("#ErrorMsg").text("");
                    var txnamout = document.getElementById("formattedTxnAmount").value;
                    //check the loan amount is number
                    if (isNumber(txnamout)) {
                        $("#ErrorMsg").text("");
                        var paymentplancode = document.getElementById("paymentPlanCode").value;
                        var duration = document.getElementById("duration" + paymentplancode).value;
                        var interestrate = document.getElementById("interestrate" + paymentplancode).value;
                        document.getElementById("installmentAmount").value = calculationFormula(txnamout, duration, interestrate);
                    } else {
                        $("#ErrorMsg").text("Txn Amount should be a number.");
                    }

                } else {
                    document.getElementById("installmentAmount").value = "";
                    $("#ErrorMsg").text("Txn Amount shouldn't be empty.");
                }

            }

            function isNumber(n) {
                return !isNaN(parseFloat(n)) && isFinite(n);
            }

            //calculate installement amount
            function calculationFormula(txnamout, duration, interestrate) {
                var txnamout = parseFloat(txnamout);
                var duration = parseFloat(duration);
                var interestrate = parseFloat(interestrate);

                return ((Math.ceil(((txnamout + (txnamout * interestrate) / 100) / duration) * 100)) / 100).toFixed(2);
            }

        </script>  
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.EASY_PAYMENT_REQUEST%>'
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


                                <c:if test="${operationtype=='search'}" >

                                    <form method="POST" name="billingcyclingrequest" action="${pageContext.request.contextPath}/ManageEasyPaymentRequestServlet">
                                        <table border="0" cellspacing="10" cellpadding="0">

                                            <tbody >

                                                <tr>
                                                    <td width="200px;">Card Number</td>
                                                    <td >           
                                                        : ${tempBean.cardNumber}
                                                        <input type="hidden" value="${tempBean.cardNumber}" name="cardno"/>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Transaction Amount Between </td>
                                                    <td>
                                                        <input type="text" name="amountTo" class="inputfield-Description-mandatory" maxlength="5" value='${amountTo}'>
                                                        &nbsp;&nbsp;AND&nbsp;&nbsp;
                                                        <input type="text" name="amountFrom" class="inputfield-Description-mandatory" maxlength="5" value='${amountFrom}'>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Date</td>
                                                    <td><input  name="fromdate1" readonly maxlength="16" value="${fromdate1}" key="fromdate" id="fromdate1"  />
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
                                                                $('#fromdate1').datepicker("setDate", "today");
                                                            });
                                                        </script>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td> 

                                                        <input type="button" class="defbutton" name="search" value="Search" onclick="invokeSearch('${tempBean.cardNumber}')"/>
                                                        <input type="reset" class="defbutton" name="reset" value="Reset" onclick="invokeReset('${tempBean.cardNumber}')"/>
                                                    </td>    
                                                    <td></td>
                                                </tr>
                                            </tbody>
                                        </table>                                     


                                    </form>
                                    <table  border="1"  class="display" id="scoreltableview3">
                                        <thead>
                                            <tr>

                                                <th>Date</th>
                                                <th>Time</th>
                                                <th>Accepter Name</th>
                                                <th>Txn Type</th>
                                                <th>Txn Amount</th>
                                                <th>Txn Currency</th>
                                                <th>Status</th>
                                                <th>Setup</th>
                                            </tr>
                                        </thead>
                                        <tbody>

                                            <c:forEach var="billingCyclingReq" items="${billingCyclingReqList}">
                                                <tr>

                                                    <td >${billingCyclingReq.txnDate}</td>
                                                    <td >${billingCyclingReq.txnTime}</td>
                                                    <td >${billingCyclingReq.accepterName}</td>
                                                    <td >${billingCyclingReq.subType}</td>
                                                    <td >${billingCyclingReq.formattedTxnAmount}</td>
                                                    <td >${billingCyclingReq.txnCurrency}</td>
                                                    <td >${billingCyclingReq.statusDes}</td>
                                                    <td ><a  href='#' onclick="InvokeSetup('${billingCyclingReq.txnId}', '${billingCyclingReq.rrn}', '${billingCyclingReq.txnAmount}', '${billingCyclingReq.formattedTxnAmount}', '${billingCyclingReq.txnCurrencyCode}')">Setup</a></td>


                                                </tr>
                                            </c:forEach>

                                        </tbody>
                                    </table>


                                </c:if>

                                <%-- -------------------------add form start -------------------------------- --%>



                                <c:if test="${operationtype=='add'}" >
                                    <form method="POST" action="" name="addbillingcyclingrequestform">
                                        <center><h2>Setup</h2></center>
                                        <input type="hidden" value="${tempBean.txnID}" id="txnId" name="txnId"/>
                                        <input type="hidden" value="${tempBean.rrn}" id="rrn" name="rrn"/>
                                        <input type="hidden" value="${tempBean.txnAmount}" name="txnamount" id="txnamount"/>
                                        <input type="hidden" value="${tempBean.txnCurrencyCode}" name="txnCurrencyCode" id="txnCurrencyCode"/>

                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td style="width: 180px">Account No :</td>
                                                    <td><label><c:out value="${sessionScope.SessionObject.accountId}"/> </label></td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 180px">Txn Amount</td>
                                                    <td>:&nbsp;<input type="text"  value="${tempBean.formattedTxnAmount}" id="formattedTxnAmount" name="formattedTxnAmount" maxlength="16" readonly=""></td>
                                                </tr>
                                                <tr><td style="height: 5px"></td></tr> 
                                                <tr>
                                                    <td style="width: 180px">Payment Plan </td>
                                                    <td><font style="color: red;">*</font>
                                                        <select name="paymentPlanCode" id="paymentPlanCode" onchange="calculateInsAmount()" value="${tempBean.paymentPlan}">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="paymentPlan" items="${paymentPlanList}">
                                                                <c:if test="${tempBean.paymentPlan != null && tempBean.paymentPlan eq paymentPlan.paymentplancode}">
                                                                    <option value="${paymentPlan.paymentplancode}" selected>${paymentPlan.description}</option>
                                                                </c:if>
                                                            </c:forEach>

                                                            <c:forEach var="paymentPlan" items="${paymentPlanList}">
                                                                <option value="${paymentPlan.paymentplancode}">${paymentPlan.description}</option>
                                                            </c:forEach>
                                                        </select>
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
                                                    <td>:&nbsp;<input type="text"  value="${tempBean.installmentAmount}" name="installmentAmount" id="installmentAmount" maxlength="16" readonly=""></td>
                                                </tr>
                                                <tr><td style="height: 12px"></td></tr>
                                                <tr>
                                                    <td>Remark</td>
                                                    <td>:
                                                        <textarea name="remarks" maxlength="512"></textarea>                                                
                                                    </td>
                                                </tr>

                                                <tr> <td style="height:12px;"></td></tr>
                                                <tr>
                                                    <td></td>
                                                    <td style="width: 300px">&nbsp;&nbsp;<input type="submit" value="Request Plan" name="requestplan" style="width: 100px" onclick="invokeAdd()">
                                                        <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeResetSetup()"></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.EASY_PAYMENT_REQUEST%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" title="History View"/></a></td>
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