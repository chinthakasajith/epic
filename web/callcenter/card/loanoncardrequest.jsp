<%-- 
    Document   : loanoncardrequest
    Created on : Sep 14, 2016, 12:19:22 PM
    Author     : sajith_g
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
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

        <script  type="text/javascript" >
            function invokeReset() {
                window.location = "${pageContext.request.contextPath}/LoadEasyPaymentCardMgtServlet";
            }

            function invokeResetSetup() {

                document.getElementById("installmentAmount").value = "";
                document.getElementById("loanAmount").value = "";
                document.getElementById("remarks").value = "";
                
            }

            function invokeSearch(cardNo)
            {

                document.easypaymentrequest.action = "${pageContext.request.contextPath}/ManageEasyPaymentRequestServlet";
                document.easypaymentrequest.submit();

            }

            function InvokeSetup(txnID, rrn, txnAmount, formattedTxnAmount, txnCurrencyCode)
            {

                window.location = "${pageContext.request.contextPath}/LoadEasyPaymentSetupServlet?txnID=" + txnID + "&rrn=" + rrn + "&txnAmount=" + txnAmount + "&formattedTxnAmount=" + formattedTxnAmount + "&txnCurrencyCode=" + txnCurrencyCode;

            }
            function invokeAdd(cardNo)
            {

                document.addloanoncardrequestform.action = "${pageContext.request.contextPath}/AddLoanOnCardPlanRequestServlet?cardno=" + cardNo;
                document.addloanoncardrequestform.submit();

            }
            function calculateInsAmount() {
                if (document.getElementById("loanAmount") != null && document.getElementById("loanAmount").value !== "") {
                    $("#ErrorMsg").text("");
                    var loanamout = document.getElementById("loanAmount").value;
                    //check the loan amount is number
                    if (isNumber(loanamout)) {
                        $("#ErrorMsg").text("");
                        var paymentplancode = document.getElementById("paymentPlanCode").value;
                        var duration = document.getElementById("duration" + paymentplancode).value;
                        var interestrate = document.getElementById("interestrate" + paymentplancode).value;
                        document.getElementById("installmentAmount").value = calculationFormula(loanamout, duration, interestrate);
                    } else {
                        $("#ErrorMsg").text("Loan Amount should be a number.");
                    }

                } else {
                    document.getElementById("installmentAmount").value = "";
                    $("#ErrorMsg").text("Loan Amount shouldn't be empty.");
                }

            }

            function isNumber(n) {
                return !isNaN(parseFloat(n)) && isFinite(n);
            }

            //calculate installement amount
            function calculationFormula(loanamout, duration, interestrate) {
                var txnamout = parseFloat(loanamout);
                var duration = parseFloat(duration);
                var interestrate = parseFloat(interestrate);

                return ((Math.ceil(((txnamout + (txnamout * interestrate) / 100) / duration) * 100)) / 100).toFixed(2);
            }

        </script>  
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.LOADONCARD_REQUEST%>'
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
                                    <form method="POST" action="" name="addloanoncardrequestform">

                                        <input type="hidden" value="${tempBean.txnCurrencyCode}" name="txnCurrencyCode" id="txnCurrencyCode"/>

                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td style="width: 180px">Card Number</td>
                                                    <td>           
                                                        : ${tempBean.cardNumber}
                                                        <input type="hidden" value="${tempBean.cardNumber}" name="cardno"/>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td style="width: 180px">Loan Amount</td>
                                                    <td>:&nbsp;<input type="text"  value="${tempBean.loanAmount}" id="loanAmount" name="loanAmount" maxlength="16"></td>
                                                </tr>
                                                <tr><td style="height: 5px"></td></tr> 
                                                <tr>
                                                    <td style="width: 180px">Payment Plan </td>
                                                    <td><font style="color: red;">*</font>
                                                        <select name="paymentPlanCode" id="paymentPlanCode" onchange="calculateInsAmount()" value="${tempBean.paymentPlan}">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="paymentPlan" items="${paymentPlanList}">
                                                                <c:if test="${tempBean.paymentPlan != null && tempBean.paymentPlan eq paymentPlan.plancode}">
                                                                    <option value="${paymentPlan.paymentplancode}" selected>${paymentPlan.description}</option>
                                                                </c:if>
                                                            </c:forEach>

                                                            <c:forEach var="paymentPlan" items="${paymentPlanList}">
                                                                <option value="${paymentPlan.plancode}">${paymentPlan.description}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td>
                                                        <c:forEach var="paymentPlan" items="${paymentPlanList}">
                                                            <input type="hidden" value="${paymentPlan.duration}" id="duration${paymentPlan.plancode}"/>
                                                            <input type="hidden" value="${paymentPlan.interestrate}" id="interestrate${paymentPlan.plancode}"/>
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
                                                        <textarea name="remarks" id="remarks" maxlength="512"></textarea>                                                
                                                    </td>
                                                </tr>

                                                <tr> <td style="height:12px;"></td></tr>
                                                <tr>
                                                    <td></td>
                                                    <td style="width: 300px">&nbsp;&nbsp;<input type="submit" value="Request Plan" name="requestplan" style="width: 100px" onclick="invokeAdd()">
                                                        <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeResetSetup()"></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.LOADONCARD_REQUEST%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" title="History View"/></a></td>
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
